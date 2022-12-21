package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
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
import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.api.aclci.IAclciUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
@PrepareForTest({RupContextUtils.class, RupPersistUtils.class})
public class UsageBatchServiceTest {

    private static final Integer FISCAL_YEAR = 2017;
    private static final String BATCH_NAME = "JAACC_11Dec16";
    private static final String BATCH_UID = "80fa89fe-460b-413e-a192-8685fe36bde1";
    private static final String USAGE_UID = "36beb0f6-11a3-47b0-bb11-81d6342e8ae9";
    private static final String USER_NAME = "User Name";
    private static final Long RRO_ACCOUNT_NUMBER = 123456789L;
    private static final String RRO_NAME = "RRO Name";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String NTS_PRODUCT_FAMILY = "NTS";

    private IPiIntegrationService piIntegrationService;
    private IUsageBatchRepository usageBatchRepository;
    private IUsageService usageService;
    private IFasUsageService fasUsageService;
    private INtsUsageService ntsUsageService;
    private ISalUsageService salUsageService;
    private IAclciUsageService aclciUsageService;
    private IAaclUsageService aaclUsageService;
    private IRightsholderService rightsholderService;
    private UsageBatchService usageBatchService;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        piIntegrationService = createMock(IPiIntegrationService.class);
        usageBatchRepository = createMock(IUsageBatchRepository.class);
        usageService = createMock(IUsageService.class);
        fasUsageService = createMock(IFasUsageService.class);
        ntsUsageService = createMock(INtsUsageService.class);
        salUsageService = createMock(ISalUsageService.class);
        aaclUsageService = createMock(IAaclUsageService.class);
        aclciUsageService = createMock(IAclciUsageService.class);
        rightsholderService = createMock(IRightsholderService.class);
        usageBatchService = new UsageBatchService();
        Whitebox.setInternalState(usageBatchService, piIntegrationService);
        Whitebox.setInternalState(usageBatchService, usageBatchRepository);
        Whitebox.setInternalState(usageBatchService, usageService);
        Whitebox.setInternalState(usageBatchService, fasUsageService);
        Whitebox.setInternalState(usageBatchService, ntsUsageService);
        Whitebox.setInternalState(usageBatchService, salUsageService);
        Whitebox.setInternalState(usageBatchService, aaclUsageService);
        Whitebox.setInternalState(usageBatchService, aclciUsageService);
        Whitebox.setInternalState(usageBatchService, rightsholderService);
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
        Capture<UsageBatch> captureUsageBatch = newCapture();
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
        Capture<UsageBatch> captureUsageBatch = newCapture();
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setRro(buildRro());
        List<String> usageIds = Collections.singletonList("c1965b73-7800-455c-beb3-98a430512f20");
        usageBatchRepository.insert(capture(captureUsageBatch));
        expectLastCall().once();
        rightsholderService.updateRighstholdersAsync(Collections.singleton(RRO_ACCOUNT_NUMBER));
        expectLastCall().once();
        expect(ntsUsageService.insertUsages(usageBatch)).andReturn(usageIds).once();
        usageBatchRepository.updateInitialUsagesCount(eq(usageIds.size()), anyString(), eq(USER_NAME));
        expectLastCall().once();
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
    public void testInsertAaclBatch() {
        mockStatic(RupContextUtils.class);
        Capture<UsageBatch> captureUsageBatch = newCapture();
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(BATCH_NAME);
        usageBatch.setPaymentDate(LocalDate.of(2019, 6, 30));
        usageBatch.setNumberOfBaselineYears(3);
        Usage uploadedUsage = new Usage();
        uploadedUsage.setId("8e12b833-af38-4d49-96d0-221d2665f0dc");
        List<Usage> uploadedUsages = Collections.singletonList(uploadedUsage);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        usageBatchRepository.insert(capture(captureUsageBatch));
        expectLastCall().once();
        aaclUsageService.insertUsages(usageBatch, uploadedUsages);
        expectLastCall().once();
        expect(aaclUsageService.insertUsagesFromBaseline(usageBatch))
            .andReturn(Collections.singletonList("b3f2727e-b023-49f0-970a-94dd3537ec6e")).once();
        usageBatchRepository.updateInitialUsagesCount(eq(2), anyString(), eq(USER_NAME));
        expectLastCall().once();
        replay(usageBatchRepository, aaclUsageService, RupContextUtils.class);
        assertEquals(Arrays.asList("8e12b833-af38-4d49-96d0-221d2665f0dc", "b3f2727e-b023-49f0-970a-94dd3537ec6e"),
            usageBatchService.insertAaclBatch(usageBatch, uploadedUsages));
        UsageBatch insertedUsageBatch = captureUsageBatch.getValue();
        assertNotNull(insertedUsageBatch.getId());
        assertEquals(USER_NAME, insertedUsageBatch.getCreateUser());
        assertEquals(USER_NAME, insertedUsageBatch.getUpdateUser());
        verify(usageBatchRepository, aaclUsageService, RupContextUtils.class);
    }

    @Test
    public void testInsertAaclBatchWithZeroBaselineYears() {
        mockStatic(RupContextUtils.class);
        Capture<UsageBatch> captureUsageBatch = newCapture();
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(BATCH_NAME);
        usageBatch.setPaymentDate(LocalDate.of(2019, 6, 30));
        usageBatch.setNumberOfBaselineYears(0);
        Usage uploadedUsage = new Usage();
        uploadedUsage.setId("086ce041-c11a-45af-8229-824ca897cc97");
        List<Usage> uploadedUsages = Collections.singletonList(uploadedUsage);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        usageBatchRepository.insert(capture(captureUsageBatch));
        expectLastCall().once();
        aaclUsageService.insertUsages(usageBatch, uploadedUsages);
        expectLastCall().once();
        usageBatchRepository.updateInitialUsagesCount(eq(1), anyString(), eq(USER_NAME));
        expectLastCall().once();
        replay(usageBatchRepository, aaclUsageService, RupContextUtils.class);
        assertEquals(Collections.singletonList("086ce041-c11a-45af-8229-824ca897cc97"),
            usageBatchService.insertAaclBatch(usageBatch, uploadedUsages));
        UsageBatch insertedUsageBatch = captureUsageBatch.getValue();
        assertNotNull(insertedUsageBatch.getId());
        assertEquals(USER_NAME, insertedUsageBatch.getCreateUser());
        assertEquals(USER_NAME, insertedUsageBatch.getUpdateUser());
        verify(usageBatchRepository, aaclUsageService, RupContextUtils.class);
    }

    @Test
    public void testInsertAaclBatchWithNoBaselineUsagesInserted() {
        mockStatic(RupContextUtils.class);
        Capture<UsageBatch> captureUsageBatch = newCapture();
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(BATCH_NAME);
        usageBatch.setPaymentDate(LocalDate.of(2019, 6, 30));
        usageBatch.setNumberOfBaselineYears(3);
        Usage uploadedUsage = new Usage();
        uploadedUsage.setId("abc1a3de-151b-465e-8b90-9fedd13d6e79");
        List<Usage> uploadedUsages = Collections.singletonList(uploadedUsage);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        usageBatchRepository.insert(capture(captureUsageBatch));
        expectLastCall().once();
        aaclUsageService.insertUsages(usageBatch, uploadedUsages);
        expectLastCall().once();
        expect(aaclUsageService.insertUsagesFromBaseline(usageBatch)).andReturn(Collections.emptyList()).once();
        usageBatchRepository.updateInitialUsagesCount(eq(1), anyString(), eq(USER_NAME));
        expectLastCall().once();
        replay(usageBatchRepository, aaclUsageService, RupContextUtils.class);
        assertEquals(Collections.singletonList("abc1a3de-151b-465e-8b90-9fedd13d6e79"),
            usageBatchService.insertAaclBatch(usageBatch, uploadedUsages));
        UsageBatch insertedUsageBatch = captureUsageBatch.getValue();
        assertNotNull(insertedUsageBatch.getId());
        assertEquals(USER_NAME, insertedUsageBatch.getCreateUser());
        assertEquals(USER_NAME, insertedUsageBatch.getUpdateUser());
        verify(usageBatchRepository, aaclUsageService, RupContextUtils.class);
    }

    @Test
    public void testInsertSalBatch() {
        mockStatic(RupContextUtils.class);
        Capture<UsageBatch> captureUsageBatch = newCapture();
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(BATCH_NAME);
        usageBatch.setPaymentDate(LocalDate.of(2019, 6, 30));
        Usage uploadedUsage = new Usage();
        uploadedUsage.setId("945b9f3c-f878-44a2-9570-19e2053c529a");
        List<Usage> uploadedUsages = Collections.singletonList(uploadedUsage);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        usageBatchRepository.insert(capture(captureUsageBatch));
        expectLastCall().once();
        salUsageService.insertItemBankDetails(usageBatch, uploadedUsages);
        expectLastCall().once();
        usageBatchRepository.updateInitialUsagesCount(eq(1), anyString(), eq(USER_NAME));
        expectLastCall().once();
        replay(usageBatchRepository, salUsageService, RupContextUtils.class);
        assertEquals(Collections.singletonList("945b9f3c-f878-44a2-9570-19e2053c529a"),
            usageBatchService.insertSalBatch(usageBatch, uploadedUsages));
        UsageBatch insertedItemBank = captureUsageBatch.getValue();
        assertNotNull(insertedItemBank.getId());
        assertEquals(USER_NAME, insertedItemBank.getCreateUser());
        assertEquals(USER_NAME, insertedItemBank.getUpdateUser());
        verify(usageBatchRepository, salUsageService, RupContextUtils.class);
    }

    @Test
    public void testAddUsageDataToSalBatch() {
        mockStatic(RupContextUtils.class);
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(BATCH_NAME);
        usageBatch.setPaymentDate(LocalDate.of(2019, 6, 30));
        usageBatch.setInitialUsagesCount(2);
        Usage uploadedUsage = new Usage();
        uploadedUsage.setId("3d394b30-66ad-49f7-9531-211b1fa35e9b");
        List<Usage> uploadedUsages = Collections.singletonList(uploadedUsage);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        salUsageService.insertUsageDataDetails(usageBatch, uploadedUsages, USER_NAME);
        expectLastCall().once();
        usageBatchRepository.updateInitialUsagesCount(eq(3), anyString(), eq(USER_NAME));
        expectLastCall().once();
        replay(usageBatchRepository, salUsageService, RupContextUtils.class);
        usageBatchService.addUsageDataToSalBatch(usageBatch, uploadedUsages);
        verify(usageBatchRepository, salUsageService, RupContextUtils.class);
    }

    @Test
    public void testDeleteUsageBatch() {
        mockStatic(RupContextUtils.class);
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(BATCH_UID);
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
        usageBatch.setId(BATCH_UID);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        aaclUsageService.deleteUsageBatchDetails(usageBatch);
        expectLastCall().once();
        usageBatchRepository.deleteUsageBatch(usageBatch.getId());
        expectLastCall().once();
        replay(aaclUsageService, usageBatchRepository, RupContextUtils.class);
        usageBatchService.deleteAaclUsageBatch(usageBatch);
        verify(aaclUsageService, usageBatchRepository, RupContextUtils.class);
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
    public void testGetProcessingNtsBatchesNames() {
        Set<String> batchesIds = Collections.singleton(RupPersistUtils.generateUuid());
        List<String> batchesNames = Collections.singletonList(BATCH_NAME);
        EnumSet<UsageStatusEnum> processingStatuses =
            EnumSet.of(UsageStatusEnum.ELIGIBLE, UsageStatusEnum.UNCLASSIFIED, UsageStatusEnum.LOCKED,
                UsageStatusEnum.SCENARIO_EXCLUDED);
        expect(usageBatchRepository.findIneligibleForScenarioBatchNames(batchesIds, processingStatuses))
            .andReturn(batchesNames).once();
        replay(usageBatchRepository);
        assertEquals(batchesNames, usageBatchService.getProcessingNtsBatchesNames(batchesIds));
        verify(usageBatchRepository);
    }

    @Test
    public void testGetProcessingAaclBatchesNames() {
        Set<String> batchesIds = Collections.singleton(RupPersistUtils.generateUuid());
        List<String> batchesNames = Collections.singletonList(BATCH_NAME);
        EnumSet<UsageStatusEnum> processingStatuses =
            EnumSet.of(UsageStatusEnum.ELIGIBLE, UsageStatusEnum.WORK_RESEARCH, UsageStatusEnum.RH_FOUND,
                UsageStatusEnum.WORK_NOT_FOUND, UsageStatusEnum.LOCKED, UsageStatusEnum.SCENARIO_EXCLUDED);
        expect(usageBatchRepository.findIneligibleForScenarioBatchNames(batchesIds, processingStatuses))
            .andReturn(batchesNames).once();
        replay(usageBatchRepository);
        assertEquals(batchesNames, usageBatchService.getProcessingAaclBatchesNames(batchesIds));
        verify(usageBatchRepository);
    }

    @Test
    public void testGetIneligibleBatchesNames() {
        Set<String> batchesIds = Collections.singleton(RupPersistUtils.generateUuid());
        List<String> batchesNames = Collections.singletonList(BATCH_NAME);
        expect(
            usageBatchRepository.findIneligibleForScenarioBatchNames(batchesIds, EnumSet.of(UsageStatusEnum.ELIGIBLE)))
            .andReturn(batchesNames).once();
        replay(usageBatchRepository);
        assertEquals(batchesNames, usageBatchService.getIneligibleBatchesNames(batchesIds));
        verify(usageBatchRepository);
    }

    @Test
    public void testGetBatchesNamesToScenariosNames() {
        String batchId = RupPersistUtils.generateUuid();
        Set<String> batchesIds = Collections.singleton(batchId);
        Map<String, String> batchesNamesToScenariosNames = ImmutableMap.of(BATCH_NAME, "test scenario name");
        expect(usageBatchRepository.findBatchesNamesToScenariosNames(batchesIds))
            .andReturn(batchesNamesToScenariosNames).once();
        replay(usageBatchRepository);
        assertEquals(batchesNamesToScenariosNames, usageBatchService.getBatchesNamesToScenariosNames(batchesIds));
        verify(usageBatchRepository);
    }

    @Test
    public void testGetBatchNamesForRightsAssignment() {
        List<String> expectedList = Collections.singletonList("FAS Batch");
        expect(usageBatchRepository.findBatchNamesForRightsAssignment()).andReturn(expectedList).once();
        replay(usageBatchRepository);
        assertEquals(expectedList, usageBatchService.getBatchNamesForRightsAssignment());
        verify(usageBatchRepository);
    }

    @Test
    public void testDeleteSalUsageBatch() {
        mockStatic(RupContextUtils.class);
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(BATCH_UID);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        salUsageService.deleteUsageBatchDetails(usageBatch);
        expectLastCall().once();
        usageBatchRepository.deleteUsageBatch(usageBatch.getId());
        expectLastCall().once();
        replay(salUsageService, usageBatchRepository, RupContextUtils.class);
        usageBatchService.deleteSalUsageBatch(usageBatch);
        verify(salUsageService, usageBatchRepository, RupContextUtils.class);
    }

    @Test
    public void testGetSalNotAttachedToScenario() {
        List<UsageBatch> expectedList = Collections.singletonList(new UsageBatch());
        expect(usageBatchRepository.findSalNotAttachedToScenario()).andReturn(expectedList).once();
        replay(usageBatchRepository);
        assertEquals(expectedList, usageBatchService.getSalNotAttachedToScenario());
        verify(usageBatchRepository);
    }

    @Test
    public void testGetSalLicensees() {
        List<SalLicensee> licensees = Collections.singletonList(new SalLicensee());
        expect(usageBatchRepository.findSalLicensees()).andReturn(licensees).once();
        replay(usageBatchRepository);
        assertSame(licensees, usageBatchService.getSalLicensees());
        verify(usageBatchRepository);
    }

    @Test
    public void testInsertAclciBatch() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        mockStatic(RupPersistUtils.class);
        expect(RupPersistUtils.generateUuid()).andReturn(BATCH_UID).once();
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(BATCH_NAME);
        usageBatch.setPaymentDate(LocalDate.of(2022, 6, 30));
        Usage usage = new Usage();
        usage.setId(USAGE_UID);
        List<Usage> usages = Collections.singletonList(usage);
        Capture<UsageBatch> usageBatchCapture = newCapture();
        usageBatchRepository.insert(capture(usageBatchCapture));
        expectLastCall().once();
        aclciUsageService.insertUsages(usageBatch, usages);
        expectLastCall().once();
        usageBatchRepository.updateInitialUsagesCount(eq(1), eq(BATCH_UID), eq(USER_NAME));
        expectLastCall().once();
        replay(RupContextUtils.class, RupPersistUtils.class, aclciUsageService, usageBatchRepository);
        assertEquals(Collections.singletonList(USAGE_UID), usageBatchService.insertAclciBatch(usageBatch, usages));
        UsageBatch insertedUsageBatch = usageBatchCapture.getValue();
        assertEquals(BATCH_UID, insertedUsageBatch.getId());
        assertEquals(USER_NAME, insertedUsageBatch.getCreateUser());
        assertEquals(USER_NAME, insertedUsageBatch.getUpdateUser());
        verify(RupContextUtils.class, RupPersistUtils.class, aclciUsageService, usageBatchRepository);
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
