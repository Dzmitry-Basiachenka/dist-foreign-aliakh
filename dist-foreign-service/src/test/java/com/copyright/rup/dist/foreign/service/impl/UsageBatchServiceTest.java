package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
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
import java.util.Set;
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
    private static final String BATCH_UID = RupPersistUtils.generateUuid();
    private static final String USER_NAME = "User Name";
    private static final Long RRO_ACCOUNT_NUMBER = 123456789L;
    private static final String RRO_NAME = "RRO Name";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String NTS_PRODUCT_FAMILY = "NTS";

    private IAaclUsageService aaclUsageService;
    private IUsageBatchRepository usageBatchRepository;
    private IUsageService usageService;
    private INtsUsageService ntsUsageService;
    private IFasUsageService fasUsageService;
    private IRightsholderService rightsholderService;
    private UsageBatchService usageBatchService;
    private IChainExecutor<Usage> chainExecutor;
    private ExecutorService executorService;
    private IPiIntegrationService piIntegrationService;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        usageBatchRepository = createMock(IUsageBatchRepository.class);
        aaclUsageService = createMock(IAaclUsageService.class);
        usageService = createMock(IUsageService.class);
        fasUsageService = createMock(IFasUsageService.class);
        ntsUsageService = createMock(INtsUsageService.class);
        rightsholderService = createMock(IRightsholderService.class);
        chainExecutor = createMock(IChainExecutor.class);
        executorService = createMock(ExecutorService.class);
        piIntegrationService = createMock(IPiIntegrationService.class);
        usageBatchService = new UsageBatchService();
        Whitebox.setInternalState(usageBatchService, piIntegrationService);
        Whitebox.setInternalState(usageBatchService, usageBatchRepository);
        Whitebox.setInternalState(usageBatchService, aaclUsageService);
        Whitebox.setInternalState(usageBatchService, usageService);
        Whitebox.setInternalState(usageBatchService, fasUsageService);
        Whitebox.setInternalState(usageBatchService, ntsUsageService);
        Whitebox.setInternalState(usageBatchService, rightsholderService);
        Whitebox.setInternalState(usageBatchService, chainExecutor);
        Whitebox.setInternalState(usageBatchService, executorService);
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
    public void testGetUsageBatchById() {
        expect(usageBatchRepository.findById(BATCH_UID)).andReturn(new UsageBatch()).once();
        replay(usageBatchRepository);
        usageBatchService.getUsageBatchById(BATCH_UID);
        verify(usageBatchRepository);
    }

    @Test
    public void testGetUsageBatchesForNtsFundPool() {
        expect(usageBatchRepository.findUsageBatchesForNtsFundPool()).andReturn(Collections.emptyList()).once();
        replay(usageBatchRepository);
        usageBatchService.getUsageBatchesForNtsFundPool();
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
        Usage usage1 = buildUsage(buildRightsholder(1000001534L), 5498456456L, UsageStatusEnum.WORK_FOUND);
        Usage usage2 = buildUsage(buildRightsholder(1000009522L), null, UsageStatusEnum.NEW);
        Usage usage3 = buildUsage(buildRightsholder(1000009535L), 7974545646L, UsageStatusEnum.ELIGIBLE);
        List<Usage> usages = Lists.newArrayList(usage1, usage2, usage3);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        usageBatchRepository.insert(capture(captureUsageBatch));
        expectLastCall().once();
        rightsholderService.updateRightsholder(rro);
        expectLastCall().once();
        rightsholderService.updateRighstholdersAsync(Sets.newHashSet(1000001534L, 1000009522L, 1000009535L));
        expectLastCall().once();
        expect(piIntegrationService.findWorkByWrWrkInst(5498456456L)).andReturn(buildWork()).once();
        expect(piIntegrationService.findWorkByWrWrkInst(7974545646L)).andReturn(new Work()).once();
        expect(fasUsageService.insertUsages(usageBatch, usages)).andReturn(3).once();
        replayAll();
        assertEquals(3, usageBatchService.insertFasBatch(usageBatch, usages));
        UsageBatch insertedUsageBatch = captureUsageBatch.getValue();
        assertNotNull(insertedUsageBatch);
        assertEquals(USER_NAME, insertedUsageBatch.getUpdateUser());
        assertEquals(USER_NAME, insertedUsageBatch.getCreateUser());
        assertEquals("VALISBN10", usage1.getStandardNumberType());
        assertNull(usage2.getStandardNumberType());
        assertNull(usage2.getStandardNumberType());
        verifyAll();
    }

    @Test
    public void testInsertNtsBatch() {
        Capture<UsageBatch> captureUsageBatch = new Capture<>();
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setRro(buildRro());
        List<String> usageIds = Collections.singletonList("c1965b73-7800-455c-beb3-98a430512f20");
        usageBatchRepository.insert(capture(captureUsageBatch));
        expectLastCall().once();
        rightsholderService.updateRighstholdersAsync(Collections.singleton(RRO_ACCOUNT_NUMBER));
        expectLastCall().once();
        expect(ntsUsageService.insertUsages(usageBatch)).andReturn(usageIds).once();
        replay(usageBatchRepository, rightsholderService, ntsUsageService);
        assertEquals(usageIds, usageBatchService.insertNtsBatch(usageBatch, USER_NAME));
        UsageBatch insertedUsageBatch = captureUsageBatch.getValue();
        assertNotNull(insertedUsageBatch);
        assertNotNull(insertedUsageBatch.getId());
        assertEquals(NTS_PRODUCT_FAMILY, insertedUsageBatch.getProductFamily());
        assertEquals(USER_NAME, insertedUsageBatch.getUpdateUser());
        assertEquals(USER_NAME, insertedUsageBatch.getCreateUser());
        verify(usageBatchRepository, rightsholderService, ntsUsageService);
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
    public void testDeleteAaclUsageBatch() {
        mockStatic(RupContextUtils.class);
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(RupPersistUtils.generateUuid());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        aaclUsageService.deleteUsageBatchDetails(usageBatch);
        expectLastCall().once();
        usageBatchRepository.deleteUsageBatch(usageBatch.getId());
        expectLastCall().once();
        replay(usageService, usageBatchRepository, RupContextUtils.class);
        usageBatchService.deleteAaclUsageBatch(usageBatch);
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
        expect(usageService.getUsagesByIds(usageIds)).andReturn(usages).once();
        Capture<Runnable> captureRunnable = new Capture<>();
        executorService.execute(capture(captureRunnable));
        expectLastCall().once();
        chainExecutor.execute(usages, ChainProcessorTypeEnum.RIGHTS);
        expectLastCall().once();
        replay(chainExecutor, executorService, usageService);
        usageBatchService.getAndSendForGettingRights(usageIds, BATCH_NAME);
        assertNotNull(captureRunnable);
        Runnable runnable = captureRunnable.getValue();
        runnable.run();
        verify(chainExecutor, executorService, usageService);
    }

    @Test
    public void testGetBatchNamesWithUnclassifiedWorks() {
        Set<String> batchIds = Collections.singleton(BATCH_UID);
        expect(usageBatchRepository.findBatchNamesWithoutUsagesForClassification(batchIds, null))
            .andReturn(Collections.singletonList("Batch with unclassified usages")).once();
        replay(usageBatchRepository);
        usageBatchService.getBatchNamesWithUnclassifiedWorks(Collections.singleton(BATCH_UID));
        verify(usageBatchRepository);
    }

    @Test
    public void testGetClassifcationToBatchNamesWithoutUsagesForStmOrNonStm() {
        Set<String> batchIds = Collections.singleton(BATCH_UID);
        expect(usageBatchRepository.findBatchNamesWithoutUsagesForClassification(batchIds, "STM"))
            .andReturn(Collections.singletonList("Batch without STM usages")).once();
        expect(usageBatchRepository.findBatchNamesWithoutUsagesForClassification(batchIds, "NON-STM"))
            .andReturn(Collections.emptyList()).once();
        replay(usageBatchRepository);
        usageBatchService.getClassifcationToBatchNamesWithoutUsagesForStmOrNonStm(batchIds);
        verify(usageBatchRepository);
    }

    @Test
    public void testGetProcessingBatchesNames() {
        Set<String> batchesIds = Collections.singleton(RupPersistUtils.generateUuid());
        List<String> batchesNames = Collections.singletonList("test batch name");
        expect(usageBatchRepository.findProcessingBatchesNames(batchesIds)).andReturn(batchesNames).once();
        replay(usageBatchRepository);
        assertEquals(batchesNames, usageBatchService.getProcessingBatchesNames(batchesIds));
        verify(usageBatchRepository);
    }

    @Test
    public void testGetBatchesNamesToScenariosNames() {
        String batchId = RupPersistUtils.generateUuid();
        Set<String> batchesIds = Collections.singleton(batchId);
        Map<String, String> batchesNamesToScenariosNames = ImmutableMap.of("test batch name", "test scenario name");
        expect(usageBatchRepository.findBatchesNamesToScenariosNames(batchesIds))
            .andReturn(batchesNamesToScenariosNames).once();
        replay(usageBatchRepository);
        assertEquals(batchesNamesToScenariosNames, usageBatchService.getBatchesNamesToScenariosNames(batchesIds));
        verify(usageBatchRepository);
    }

    @Test
    public void testGetBatchNamesAvailableForRightsAssignment() {
        List<String> expectedList = Collections.singletonList("FAS Batch");
        expect(usageBatchRepository.findBatchNamesWithRhNotFoundUsages()).andReturn(expectedList).once();
        replay(usageBatchRepository);
        assertEquals(expectedList, usageBatchService.getBatchNamesAvailableForRightsAssignment());
        verify(usageBatchRepository);
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

    private Usage buildUsage(Rightsholder rightsholder, Long wrWrkInst, UsageStatusEnum status) {
        Usage usage = new Usage();
        usage.setRightsholder(rightsholder);
        usage.setWrWrkInst(wrWrkInst);
        usage.setStatus(status);
        return usage;
    }

    private Work buildWork() {
        Work work = new Work();
        work.setMainIdnoType("VALISBN10");
        return work;
    }
}
