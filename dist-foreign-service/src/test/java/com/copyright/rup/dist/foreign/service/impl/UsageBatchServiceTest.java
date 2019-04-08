package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verify;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Verifies {@link UsageBatchService}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/03/2017
 *
 * @author Mikalai Bezmen
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class UsageBatchServiceTest {

    private static final Integer FISCAL_YEAR = 2017;
    private static final String BATCH_NAME = "JAACC_11Dec16";
    private static final String USER_NAME = "User Name";
    private static final Long RRO_ACCOUNT_NUMBER = 123456789L;
    private static final String RRO_NAME = "RRO Name";
    private static final String FAS_PRODUCT_FAMILY = "FAS";

    private IUsageBatchRepository usageBatchRepository;
    private IUsageService usageService;
    private IRightsholderService rightsholderService;
    private UsageBatchService usageBatchService;
    private IChainExecutor<Usage> chainExecutor;
    private ExecutorService executorService;
    private IUsageRepository usageRepository;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        usageBatchRepository = createMock(IUsageBatchRepository.class);
        usageService = createMock(IUsageService.class);
        rightsholderService = createMock(IRightsholderService.class);
        chainExecutor = createMock(IChainExecutor.class);
        executorService = createMock(ExecutorService.class);
        usageRepository = createMock(IUsageRepository.class);
        usageBatchService = new UsageBatchService();
        Whitebox.setInternalState(usageBatchService, "usageBatchRepository", usageBatchRepository);
        Whitebox.setInternalState(usageBatchService, "usageService", usageService);
        Whitebox.setInternalState(usageBatchService, "rightsholderService", rightsholderService);
        Whitebox.setInternalState(usageBatchService, "chainExecutor", chainExecutor);
        Whitebox.setInternalState(usageBatchService, "executorService", executorService);
        Whitebox.setInternalState(usageBatchService, "usageRepository", usageRepository);
        Whitebox.setInternalState(usageBatchService, "usagesBatchSize", 100);
    }

    @Test
    public void testGetFiscalYears() {
        expect(usageBatchRepository.findFiscalYearsByProductFamily(FAS_PRODUCT_FAMILY))
            .andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        replay(usageBatchRepository);
        List<Integer> result = usageBatchService.getFiscalYears(FAS_PRODUCT_FAMILY);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(FISCAL_YEAR));
        verify(usageBatchRepository);
    }

    @Test
    public void testGetUsageBatches() {
        List<UsageBatch> usageBatches = Collections.singletonList(new UsageBatch());
        expect(usageBatchRepository.findAll()).andReturn(usageBatches).once();
        replay(usageBatchRepository);
        assertEquals(usageBatches, usageBatchService.getUsageBatches());
        verify(usageBatchRepository);
    }

    @Test
    public void testGetUsageBatchesByProductFamily() {
        expect(usageBatchRepository.findByProductFamily(FAS_PRODUCT_FAMILY)).andReturn(Collections.emptyList()).once();
        replay(usageBatchRepository);
        usageBatchService.getUsageBatches(FAS_PRODUCT_FAMILY);
        verify(usageBatchRepository);
    }

    @Test
    public void testGetUsageBatchesForPreServiceFeeFunds() {
        expect(usageBatchRepository.findUsageBatchesForPreServiceFeeFunds()).andReturn(Collections.emptyList()).once();
        replay(usageBatchRepository);
        usageBatchService.getUsageBatchesForPreServiceFeeFunds();
        verify(usageBatchRepository);
    }

    @Test
    public void testUsageBatchExists() {
        expect(usageBatchRepository.findCountByName(BATCH_NAME)).andReturn(1).once();
        replay(usageBatchRepository);
        assertTrue(usageBatchService.usageBatchExists(BATCH_NAME));
        verify(usageBatchRepository);
    }

    @Test
    public void testUsageBatchDoesNotExist() {
        expect(usageBatchRepository.findCountByName(BATCH_NAME)).andReturn(0).once();
        replay(usageBatchRepository);
        assertFalse(usageBatchService.usageBatchExists(BATCH_NAME));
        verify(usageBatchRepository);
    }

    @Test
    public void testInsertFasBatch() {
        mockStatic(RupContextUtils.class);
        Capture<UsageBatch> captureUsageBatch = new Capture<>();
        UsageBatch usageBatch = new UsageBatch();
        Rightsholder rro = buildRro();
        usageBatch.setRro(rro);
        Usage usage1 = new Usage();
        usage1.setRightsholder(buildRightsholder(1000001534L));
        Usage usage2 = new Usage();
        usage2.setRightsholder(buildRightsholder(1000009522L));
        usage2.setStatus(UsageStatusEnum.NEW);
        List<Usage> usages = Lists.newArrayList(usage1, usage2);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        usageBatchRepository.insert(capture(captureUsageBatch));
        expectLastCall().once();
        Map<Long, Rightsholder> rightsholderMap = Maps.newHashMapWithExpectedSize(1);
        rightsholderMap.put(RRO_ACCOUNT_NUMBER, rro);
        rightsholderService.updateRightsholder(rro);
        expectLastCall().once();
        rightsholderService.updateRighstholdersAsync(Sets.newHashSet(1000001534L, 1000009522L));
        expectLastCall().once();
        expect(usageService.insertUsages(usageBatch, usages)).andReturn(2).once();
        replayAll();
        assertEquals(2, usageBatchService.insertFasBatch(usageBatch, usages));
        UsageBatch insertedUsageBatch = captureUsageBatch.getValue();
        assertNotNull(insertedUsageBatch);
        assertEquals(USER_NAME, insertedUsageBatch.getUpdateUser());
        assertEquals(USER_NAME, insertedUsageBatch.getCreateUser());
        verifyAll();
    }

    @Test
    public void testDeleteUsageBatch() {
        mockStatic(RupContextUtils.class);
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(RupPersistUtils.generateUuid());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        usageService.deleteUsageBatchDetails(usageBatch);
        expectLastCall().once();
        usageBatchRepository.deleteUsageBatch(usageBatch.getId());
        expectLastCall().once();
        replay(usageService, usageBatchRepository, RupContextUtils.class);
        usageBatchService.deleteUsageBatch(usageBatch);
        verify(usageService, usageBatchRepository, RupContextUtils.class);
    }

    @Test
    public void testSendForMatching() {
        Capture<Runnable> captureRunnable = new Capture<>();
        executorService.execute(capture(captureRunnable));
        expectLastCall().once();
        replay(chainExecutor, executorService);
        usageBatchService.sendForMatching(Arrays.asList(new Usage(), new Usage()));
        assertNotNull(captureRunnable);
        verify(chainExecutor, executorService);
    }

    @Test
    public void testSendForGettingRights() {
        Capture<Runnable> captureRunnable = new Capture<>();
        executorService.execute(capture(captureRunnable));
        expectLastCall().once();
        replay(chainExecutor, executorService);
        usageBatchService.sendForGettingRights(Arrays.asList(new Usage(), new Usage()), BATCH_NAME);
        assertNotNull(captureRunnable);
        verify(chainExecutor, executorService);
    }

    @Test
    public void testGetAndSendForGettingRights() {
        List<String> usageIds = Arrays.asList(RupPersistUtils.generateUuid(), RupPersistUtils.generateUuid());
        Usage usage1 = new Usage();
        usage1.setStatus(UsageStatusEnum.WORK_FOUND);
        Usage usage2 = new Usage();
        usage2.setStatus(UsageStatusEnum.WORK_FOUND);
        List<Usage> usages = Arrays.asList(usage1, usage2);
        expect(usageRepository.findByIds(usageIds)).andReturn(usages).once();
        Capture<Runnable> captureRunnable = new Capture<>();
        executorService.execute(capture(captureRunnable));
        expectLastCall().once();
        chainExecutor.execute(usages, ChainProcessorTypeEnum.RIGHTS);
        expectLastCall().once();
        replay(chainExecutor, executorService, usageRepository);
        usageBatchService.getAndSendForGettingRights(usageIds, BATCH_NAME);
        assertNotNull(captureRunnable);
        Runnable runnable = captureRunnable.getValue();
        runnable.run();
        verify(chainExecutor, executorService, usageRepository);
    }

    private Rightsholder buildRro() {
        Rightsholder rightsholder = buildRightsholder(RRO_ACCOUNT_NUMBER);
        rightsholder.setName(RRO_NAME);
        return rightsholder;
    }

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }
}
