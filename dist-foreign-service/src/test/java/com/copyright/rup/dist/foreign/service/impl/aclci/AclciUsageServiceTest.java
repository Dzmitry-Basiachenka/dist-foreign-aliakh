package com.copyright.rup.dist.foreign.service.impl.aclci;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IAclciUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclciUsageService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/12/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class AclciUsageServiceTest {

    private static final String USER_NAME = "user@copyright.com";
    private static final String BATCH_ID = "eef46972-c381-4cb6-ab0a-c3e537ba708a";
    private static final String BATCH_NAME = "ACLCI Usage Batch";
    private static final String USAGE_ID_1 = "3fc118c8-7834-4ef1-8b80-ddb188e66fa1";
    private static final String USAGE_ID_2 = "50060739-dcdf-42b7-8ca0-709556fe2aab";
    private static final String ACLCI_PRODUCT_FAMILY = "ACLCI";

    private final AclciUsageService aclciUsageService = new AclciUsageService();

    private IAclciUsageRepository aclciUsageRepository;
    private IUsageAuditService usageAuditService;
    private IChainExecutor<Usage> chainExecutor;

    @Before
    public void setUp() {
        aclciUsageRepository = createMock(IAclciUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        chainExecutor = createMock(IChainExecutor.class);
        Whitebox.setInternalState(aclciUsageService, aclciUsageRepository);
        Whitebox.setInternalState(aclciUsageService, usageAuditService);
        Whitebox.setInternalState(aclciUsageService, chainExecutor);
        Whitebox.setInternalState(aclciUsageService, "usagesBatchSize", 1000);
    }

    @Test
    public void testInsertUsages() {
        mockStatic(RupContextUtils.class);
        Usage usage = new Usage();
        usage.setProductFamily(ACLCI_PRODUCT_FAMILY);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        aclciUsageRepository.insert(usage);
        expectLastCall().once();
        usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED,
            "Uploaded in 'ACLCI Usage Batch' Batch");
        expectLastCall().once();
        replay(RupContextUtils.class, aclciUsageRepository, usageAuditService);
        aclciUsageService.insertUsages(buildUsageBatch(), Collections.singletonList(usage));
        verify(RupContextUtils.class, aclciUsageRepository, usageAuditService);
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(BATCH_ID);
        usageBatch.setName(BATCH_NAME);
        usageBatch.setPaymentDate(LocalDate.of(2022, 6, 30));
        return usageBatch;
    }

    @Test
    public void testSendForMatching() {
        List<String> usageIds = Arrays.asList(USAGE_ID_1, USAGE_ID_2);
        Usage usage1 = new Usage();
        usage1.setStatus(UsageStatusEnum.NEW);
        Usage usage2 = new Usage();
        usage2.setStatus(UsageStatusEnum.NEW);
        List<Usage> usages = Arrays.asList(usage1, usage2);
        expect(aclciUsageRepository.findByIds(usageIds)).andReturn(usages).once();
        Capture<Runnable> captureRunnable = new Capture<>();
        chainExecutor.execute(capture(captureRunnable));
        expectLastCall().once();
        chainExecutor.execute(usages, ChainProcessorTypeEnum.MATCHING);
        expectLastCall().once();
        replay(chainExecutor, aclciUsageRepository);
        aclciUsageService.sendForMatching(usageIds, "Batch name");
        assertNotNull(captureRunnable);
        Runnable runnable = captureRunnable.getValue();
        assertNotNull(runnable);
        runnable.run();
        verify(chainExecutor, aclciUsageRepository);
    }

    @Test
    public void testGetUsagesByIds() {
        List<String> usageIds = Collections.singletonList(USAGE_ID_1);
        List<Usage> usages = Collections.singletonList(new Usage());
        expect(aclciUsageRepository.findByIds(usageIds)).andReturn(usages).once();
        replay(aclciUsageRepository);
        assertEquals(usages, aclciUsageService.getUsagesByIds(usageIds));
        verify(aclciUsageRepository);
    }
}
