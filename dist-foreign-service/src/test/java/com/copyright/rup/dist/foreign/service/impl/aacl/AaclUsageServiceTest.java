package com.copyright.rup.dist.foreign.service.impl.aacl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.domain.PayeeAccountAggregateLicenseeClassesPair;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.impl.InconsistentUsageStateException;

import com.google.common.collect.Lists;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link AaclUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/23/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class})
public class AaclUsageServiceTest {

    private static final String SEARCH = "search";
    private static final String USER_NAME = "user@copyright.com";
    private static final String SCENARIO_ID = "10bd15c1-b907-457e-94c0-9d6bb66e706f";
    private static final String BATCH_ID = "eef46972-c381-4cb6-ab0a-c3e537ba708a";
    private static final String USAGE_ID = "d7d15c9f-39f5-4d51-b72b-48a80f7f5388";
    private static final String FUND_POOL_ID = "9a71a608-d6a5-4783-b6a2-9665d6ba7d45";
    private static final AggregateLicenseeClass ALC_1 = buildAggregateLicenseeClass(108);
    private static final AggregateLicenseeClass ALC_2 = buildAggregateLicenseeClass(110);
    private static final AggregateLicenseeClass ALC_3 = buildAggregateLicenseeClass(113);
    private static final AggregateLicenseeClass ALC_4 = buildAggregateLicenseeClass(115);
    private static final DetailLicenseeClass DLC_1 = buildDetailLicenseeClass(141, ALC_1);
    private static final DetailLicenseeClass DLC_2 = buildDetailLicenseeClass(143, ALC_1);
    private static final DetailLicenseeClass DLC_3 = buildDetailLicenseeClass(145, ALC_2);
    private static final DetailLicenseeClass DLC_4 = buildDetailLicenseeClass(146, ALC_3);


    private final AaclUsageService aaclUsageService = new AaclUsageService();

    private IUsageAuditService usageAuditService;
    private IFundPoolService fundPoolService;
    private ILicenseeClassService licenseeClassService;
    private IAaclUsageRepository aaclUsageRepository;
    private IUsageArchiveRepository usageArchiveRepository;
    private IRightsholderService rightsholderService;
    private IPrmIntegrationService prmIntegrationService;
    private IChainExecutor<Usage> chainExecutor;

    @Rule
    private final ExpectedException exception = ExpectedException.none();

    private static AggregateLicenseeClass buildAggregateLicenseeClass(Integer id) {
        AggregateLicenseeClass alc = new AggregateLicenseeClass();
        alc.setId(id);
        return alc;
    }

    private static DetailLicenseeClass buildDetailLicenseeClass(Integer id, AggregateLicenseeClass alc) {
        DetailLicenseeClass dlc = new DetailLicenseeClass();
        dlc.setId(id);
        dlc.setAggregateLicenseeClass(alc);
        return dlc;
    }

    @Before
    public void setUp() {
        usageAuditService = createMock(IUsageAuditService.class);
        fundPoolService = createMock(IFundPoolService.class);
        licenseeClassService = createMock(ILicenseeClassService.class);
        aaclUsageRepository = createMock(IAaclUsageRepository.class);
        usageArchiveRepository = createMock(IUsageArchiveRepository.class);
        rightsholderService = createMock(IRightsholderService.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        chainExecutor = createMock(IChainExecutor.class);
        Whitebox.setInternalState(aaclUsageService, usageAuditService);
        Whitebox.setInternalState(aaclUsageService, fundPoolService);
        Whitebox.setInternalState(aaclUsageService, licenseeClassService);
        Whitebox.setInternalState(aaclUsageService, aaclUsageRepository);
        Whitebox.setInternalState(aaclUsageService, usageArchiveRepository);
        Whitebox.setInternalState(aaclUsageService, rightsholderService);
        Whitebox.setInternalState(aaclUsageService, prmIntegrationService);
        Whitebox.setInternalState(aaclUsageService, chainExecutor);
        Whitebox.setInternalState(aaclUsageService, "usagesBatchSize", 100);
    }

    @Test
    public void testInsertUsages() {
        mockStatic(RupContextUtils.class);
        Usage usage = new Usage();
        usage.setProductFamily("AACL");
        usage.setAaclUsage(new AaclUsage());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        aaclUsageRepository.insert(usage);
        expectLastCall().once();
        usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED, "Uploaded in 'AACL Batch' Batch");
        expectLastCall().once();
        replay(aaclUsageRepository, usageAuditService, RupContextUtils.class);
        aaclUsageService.insertUsages(buildUsageBatch(), Collections.singletonList(usage));
        verify(aaclUsageRepository, usageAuditService, RupContextUtils.class);
    }

    @Test
    public void testInsertUsagesFromBaseline() {
        mockStatic(RupContextUtils.class);
        Set<Integer> baselinePeriods = Collections.singleton(2019);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(aaclUsageRepository.findBaselinePeriods(2019, 3))
            .andReturn(baselinePeriods).once();
        expect(aaclUsageRepository.insertFromBaseline(baselinePeriods, BATCH_ID, USER_NAME))
            .andReturn(Collections.singletonList(USAGE_ID)).once();
        usageAuditService.logAction(Collections.singleton(USAGE_ID), UsageActionTypeEnum.LOADED,
            "Pulled from baseline for 'AACL Batch' Batch");
        expectLastCall().once();
        replay(aaclUsageRepository, usageAuditService, RupContextUtils.class);
        assertEquals(Collections.singletonList(USAGE_ID), aaclUsageService.insertUsagesFromBaseline(buildUsageBatch()));
        verify(aaclUsageRepository, usageAuditService, RupContextUtils.class);
    }

    @Test
    public void testInsertUsagesFromBaselineWithNoUsages() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(aaclUsageRepository.findBaselinePeriods(2019, 3))
            .andReturn(Collections.emptySet()).once();
        replay(aaclUsageRepository, usageAuditService, RupContextUtils.class);
        assertEquals(Collections.emptyList(), aaclUsageService.insertUsagesFromBaseline(buildUsageBatch()));
        verify(aaclUsageRepository, usageAuditService, RupContextUtils.class);
    }

    @Test
    public void testCountForAudit() {
        AuditFilter filter = new AuditFilter();
        expect(aaclUsageRepository.findCountForAudit(filter)).andReturn(1).once();
        replay(aaclUsageRepository);
        assertEquals(1, aaclUsageService.getCountForAudit(filter));
        verify(aaclUsageRepository);
    }

    @Test
    public void testUpdateClassifiedUsages() {
        mockStatic(RupContextUtils.class);
        AaclClassifiedUsage usage1 = buildClassifiedUsage();
        AaclClassifiedUsage usage2 = buildClassifiedUsage();
        usage2.setPublicationType("disqualified");
        usage2.setDetailId(USAGE_ID);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        aaclUsageRepository.deleteById(USAGE_ID);
        expectLastCall().once();
        usageAuditService.deleteActionsByUsageId(USAGE_ID);
        expectLastCall().once();
        aaclUsageRepository.updateClassifiedUsages(Collections.singletonList(usage1), USER_NAME);
        expectLastCall().once();
        usageAuditService.logAction(usage1.getDetailId(), UsageActionTypeEnum.ELIGIBLE,
            "Usages has become eligible after classification");
        expectLastCall().once();
        replay(aaclUsageRepository, usageAuditService, RupContextUtils.class);
        assertEquals(1, aaclUsageService.updateClassifiedUsages(Arrays.asList(usage1, usage2)));
        verify(aaclUsageRepository, usageAuditService, RupContextUtils.class);
    }

    @Test
    public void testUpdateProcessedUsageProductFamily() {
        Usage usage = new Usage();
        usage.setProductFamily("AACL");
        expect(aaclUsageRepository.updateProcessedUsage(usage))
            .andReturn("fbf3b27f-2031-41a0-812e-111bb668e180").once();
        replay(aaclUsageRepository);
        aaclUsageService.updateProcessedUsage(usage);
        verify(aaclUsageRepository);
    }

    @Test
    public void testUpdateProcessedUsageWrongVersion() {
        Usage usage = new Usage();
        usage.setStatus(UsageStatusEnum.NEW);
        usage.setId("ca62ea7e-4185-4c56-b12b-c53fbad1d6b8");
        usage.setVersion(2);
        exception.expect(InconsistentUsageStateException.class);
        exception.expectMessage("Usage is in inconsistent state. UsageId=ca62ea7e-4185-4c56-b12b-c53fbad1d6b8," +
            " Status=NEW, RecordVersion=2");
        expect(aaclUsageRepository.updateProcessedUsage(usage)).andReturn(null).once();
        replay(aaclUsageRepository);
        aaclUsageService.updateProcessedUsage(usage);
        verify(aaclUsageRepository);
    }

    @Test
    public void testGetUsagesCount() {
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        expect(aaclUsageRepository.findCountByFilter(filter)).andReturn(1).once();
        replay(aaclUsageRepository);
        aaclUsageService.getUsagesCount(filter);
        verify(aaclUsageRepository);
    }

    @Test
    public void testGetUsageCountEmptyFilter() {
        assertEquals(0, aaclUsageService.getUsagesCount(new UsageFilter()));
    }

    @Test
    public void testGetUsagesByIds() {
        List<String> usageIds = Collections.singletonList(USAGE_ID);
        List<Usage> usages = Collections.singletonList(new Usage());
        expect(aaclUsageRepository.findByIds(usageIds)).andReturn(usages).once();
        replay(aaclUsageRepository);
        assertEquals(usages, aaclUsageService.getUsagesByIds(usageIds));
        verify(aaclUsageRepository);
    }

    @Test
    public void testGetUsagesByIdsWithEmptyUsages() {
        replay(aaclUsageRepository);
        assertEquals(Collections.emptyList(), aaclUsageService.getUsagesByIds(Collections.emptyList()));
        verify(aaclUsageRepository);
    }

    @Test
    public void testGetUsageDtos() {
        List<UsageDto> usagesWithBatch = Collections.singletonList(new UsageDto());
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("detailId", Sort.Direction.ASC);
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        expect(aaclUsageRepository.findDtosByFilter(filter, pageable, sort)).andReturn(usagesWithBatch).once();
        replay(aaclUsageRepository);
        List<UsageDto> result = aaclUsageService.getUsageDtos(filter, pageable, sort);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(aaclUsageRepository);
    }

    @Test
    public void testGetUsagesDtosEmptyFilter() {
        List<UsageDto> result = aaclUsageService.getUsageDtos(new UsageFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetUsagePeriods() {
        List<Integer> usagePeriods = Collections.singletonList(2020);
        expect(aaclUsageRepository.findUsagePeriods()).andReturn(usagePeriods).once();
        replay(aaclUsageRepository);
        assertEquals(usagePeriods, aaclUsageService.getUsagePeriods());
        verify(aaclUsageRepository);
    }

    @Test
    public void testGetDefaultUsageAges() {
        verifyUsageAges(aaclUsageService.getDefaultUsageAges(buildUsagePeriods()));
    }

    @Test
    public void testGetUsageAges() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Collections.singleton("8adb441e-a709-4f58-8dc0-9264bfac2e23"));
        expect(aaclUsageRepository.findUsagePeriodsByFilter(filter)).andReturn(buildUsagePeriods()).once();
        replay(aaclUsageRepository);
        verifyUsageAges(aaclUsageService.getUsageAges(filter));
        verify(aaclUsageRepository);
    }

    @Test
    public void testDeleteById() {
        String usageId = "7adb441e-d709-4f58-8dc0-9264bfac2e19 ";
        usageAuditService.deleteActionsByUsageId(usageId);
        expectLastCall().once();
        aaclUsageRepository.deleteById(usageId);
        expectLastCall().once();
        replay(aaclUsageRepository, usageAuditService);
        aaclUsageService.deleteById(usageId);
        verify(aaclUsageRepository, usageAuditService);
    }

    @Test
    public void testGetInvalidRightsholdersByFilter() {
        UsageFilter filter = new UsageFilter();
        expect(aaclUsageRepository.findInvalidRightsholdersByFilter(filter))
            .andReturn(Collections.singletonList(7000000001L)).once();
        replay(aaclUsageRepository);
        assertEquals(Collections.singletonList(7000000001L), aaclUsageService.getInvalidRightsholdersByFilter(filter));
        verify(aaclUsageRepository);
    }

    @Test
    public void testExcludeDetailsFromScenarioByPayees() {
        mockStatic(RupContextUtils.class);
        aaclUsageRepository.excludeFromScenarioByPayees(SCENARIO_ID, Collections.singleton(7000000001L), USER_NAME);
        expectLastCall().once();
        aaclUsageRepository.calculateAmounts(SCENARIO_ID, USER_NAME);
        expectLastCall().once();
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        replay(aaclUsageRepository, RupContextUtils.class);
        aaclUsageService.excludeDetailsFromScenarioByPayees(SCENARIO_ID, Collections.singleton(7000000001L), USER_NAME);
        verify(aaclUsageRepository, RupContextUtils.class);
    }

    @Test
    public void testCalculateAmounts() {
        aaclUsageRepository.calculateAmounts(SCENARIO_ID, USER_NAME);
        expectLastCall().once();
        replay(aaclUsageRepository);
        aaclUsageService.calculateAmounts(SCENARIO_ID, USER_NAME);
        verify(aaclUsageRepository);
    }

    @Test
    public void testExcludeZeroAmountUsages() {
        aaclUsageRepository.excludeZeroAmountUsages(SCENARIO_ID, USER_NAME);
        expectLastCall().once();
        replay(aaclUsageRepository);
        aaclUsageService.excludeZeroAmountUsages(SCENARIO_ID, USER_NAME);
        verify(aaclUsageRepository);
    }

    @Test
    public void testIsValidForClassification() {
        UsageFilter filter = new UsageFilter();
        expect(aaclUsageRepository.isValidForClassification(filter)).andReturn(true).once();
        replay(aaclUsageRepository);
        assertTrue(aaclUsageService.isValidForClassification(filter));
        verify(aaclUsageRepository);
    }

    @Test
    public void testIsValidForClassificationNegativeResult() {
        UsageFilter filter = new UsageFilter();
        expect(aaclUsageRepository.isValidForClassification(filter)).andReturn(false).once();
        replay(aaclUsageRepository);
        assertFalse(aaclUsageService.isValidForClassification(filter));
        verify(aaclUsageRepository);
    }

    @Test
    public void testDeleteUsageBatchDetails() {
        usageAuditService.deleteActionsByBatchId(BATCH_ID);
        expectLastCall().once();
        aaclUsageRepository.deleteByBatchId(BATCH_ID);
        expectLastCall().once();
        replay(aaclUsageRepository, usageAuditService);
        aaclUsageService.deleteUsageBatchDetails(buildUsageBatch());
        verify(aaclUsageRepository, usageAuditService);
    }

    @Test
    public void testSendForMatching() {
        List<String> usageIds = Arrays.asList(RupPersistUtils.generateUuid(), RupPersistUtils.generateUuid());
        Usage usage1 = new Usage();
        usage1.setStatus(UsageStatusEnum.NEW);
        Usage usage2 = new Usage();
        usage2.setStatus(UsageStatusEnum.NEW);
        List<Usage> usages = Arrays.asList(usage1, usage2);
        expect(aaclUsageRepository.findByIds(usageIds)).andReturn(usages).once();
        Capture<Runnable> captureRunnable = new Capture<>();
        chainExecutor.execute(capture(captureRunnable));
        expectLastCall().once();
        chainExecutor.execute(usages, ChainProcessorTypeEnum.MATCHING);
        expectLastCall().once();
        replay(chainExecutor, aaclUsageRepository);
        aaclUsageService.sendForMatching(usageIds, "Batch name");
        assertNotNull(captureRunnable);
        Runnable runnable = captureRunnable.getValue();
        assertNotNull(runnable);
        runnable.run();
        verify(chainExecutor, aaclUsageRepository);
    }

    @Test
    public void testAddUsagesToScenario() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        AaclFields aaclFields = new AaclFields();
        aaclFields.setPublicationTypes(Arrays.asList(
            buildPubType("eb500edd-c882-43a4-8945-454958c8fd52", BigDecimal.ONE),
            buildPubType("77707191-bdbd-4034-b37b-0968aedaa346", BigDecimal.TEN)));
        scenario.setAaclFields(aaclFields);
        scenario.setUpdateUser(USER_NAME);
        UsageFilter usageFilter = new UsageFilter();
        aaclUsageRepository.addToScenario(SCENARIO_ID, usageFilter, USER_NAME);
        expectLastCall().once();
        aaclUsageRepository.updatePublicationTypeWeight(SCENARIO_ID, "eb500edd-c882-43a4-8945-454958c8fd52",
            BigDecimal.ONE, USER_NAME);
        expectLastCall().once();
        aaclUsageRepository.updatePublicationTypeWeight(SCENARIO_ID, "77707191-bdbd-4034-b37b-0968aedaa346",
            BigDecimal.TEN, USER_NAME);
        expectLastCall().once();
        replay(aaclUsageRepository);
        aaclUsageService.addUsagesToScenario(scenario, usageFilter);
        verify(aaclUsageRepository);
    }

    @Test
    public void testPopulatePayees() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId("378cafcf-cf21-4036-b223-5bd48b09c41f");
        rightsholder.setAccountNumber(2000073957L);
        expect(rightsholderService.getByScenarioId(SCENARIO_ID))
            .andReturn(Collections.singletonList(rightsholder)).once();
        expect(prmIntegrationService.getRollUps(Collections.singleton("378cafcf-cf21-4036-b223-5bd48b09c41f")))
            .andReturn(Collections.emptyMap()).once();
        aaclUsageRepository.updatePayeeByAccountNumber(2000073957L, SCENARIO_ID, 2000073957L, "SYSTEM");
        expectLastCall().once();
        rightsholderService.updateRighstholdersAsync(Collections.singleton(2000073957L));
        expectLastCall().once();
        replay(aaclUsageRepository, rightsholderService, prmIntegrationService);
        aaclUsageService.populatePayees(SCENARIO_ID);
        verify(aaclUsageRepository, rightsholderService, prmIntegrationService);
    }

    @Test
    public void testGetAggregateClassesNotToBeDistributedWithAllValid() {
        List<DetailLicenseeClass> classes = Arrays.asList(DLC_1, DLC_2, DLC_3, DLC_4);
        List<FundPoolDetail> fundPoolDetails = Arrays.asList(
            buildFundPoolDetail(ALC_1, BigDecimal.TEN),
            buildFundPoolDetail(ALC_2, BigDecimal.TEN),
            buildFundPoolDetail(ALC_3, BigDecimal.ZERO),
            buildFundPoolDetail(ALC_4, BigDecimal.ZERO));
        UsageFilter usageFilter = new UsageFilter();
        expect(licenseeClassService.getAggregateLicenseeClasses())
            .andReturn(Arrays.asList(ALC_1, ALC_2, ALC_3, ALC_4)).once();
        expect(fundPoolService.getDetailsByFundPoolId(FUND_POOL_ID)).andReturn(fundPoolDetails).once();
        expect(aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(usageFilter, 141)).andReturn(false).once();
        expect(aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(usageFilter, 143)).andReturn(true).once();
        expect(aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(usageFilter, 145)).andReturn(true).once();
        replay(licenseeClassService, fundPoolService, aaclUsageRepository);
        List<AggregateLicenseeClass> result =
            aaclUsageService.getAggregateClassesNotToBeDistributed(FUND_POOL_ID, usageFilter, classes);
        verify(licenseeClassService, fundPoolService, aaclUsageRepository);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetAggregateClassesNotToBeDistributedWithUnmapped() {
        List<DetailLicenseeClass> classes = Arrays.asList(DLC_1, DLC_2, DLC_3, DLC_4);
        List<FundPoolDetail> fundPoolDetails = Arrays.asList(
            buildFundPoolDetail(ALC_1, BigDecimal.TEN),
            buildFundPoolDetail(ALC_2, BigDecimal.TEN),
            buildFundPoolDetail(ALC_3, BigDecimal.ZERO),
            buildFundPoolDetail(ALC_4, BigDecimal.TEN));
        UsageFilter usageFilter = new UsageFilter();
        expect(licenseeClassService.getAggregateLicenseeClasses())
            .andReturn(Arrays.asList(ALC_1, ALC_2, ALC_3, ALC_4)).once();
        expect(fundPoolService.getDetailsByFundPoolId(FUND_POOL_ID)).andReturn(fundPoolDetails).once();
        expect(aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(usageFilter, 141)).andReturn(false).once();
        expect(aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(usageFilter, 143)).andReturn(true).once();
        expect(aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(usageFilter, 145)).andReturn(true).once();
        replay(licenseeClassService, fundPoolService, aaclUsageRepository);
        List<AggregateLicenseeClass> result =
            aaclUsageService.getAggregateClassesNotToBeDistributed(FUND_POOL_ID, usageFilter, classes);
        verify(licenseeClassService, fundPoolService, aaclUsageRepository);
        assertEquals(Collections.singletonList(ALC_4), result);
    }

    @Test
    public void testGetAggregateClassesNotToBeDistributedWithNoUsages() {
        List<DetailLicenseeClass> classes = Arrays.asList(DLC_1, DLC_2, DLC_3, DLC_4);
        List<FundPoolDetail> fundPoolDetails = Arrays.asList(
            buildFundPoolDetail(ALC_1, BigDecimal.TEN),
            buildFundPoolDetail(ALC_2, BigDecimal.TEN),
            buildFundPoolDetail(ALC_3, BigDecimal.ZERO),
            buildFundPoolDetail(ALC_4, BigDecimal.ZERO));
        UsageFilter usageFilter = new UsageFilter();
        expect(licenseeClassService.getAggregateLicenseeClasses())
            .andReturn(Arrays.asList(ALC_1, ALC_2, ALC_3, ALC_4)).once();
        expect(fundPoolService.getDetailsByFundPoolId(FUND_POOL_ID)).andReturn(fundPoolDetails).once();
        expect(aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(usageFilter, 141)).andReturn(false).once();
        expect(aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(usageFilter, 143)).andReturn(true).once();
        expect(aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(usageFilter, 145)).andReturn(false).once();
        replay(licenseeClassService, fundPoolService, aaclUsageRepository);
        List<AggregateLicenseeClass> result =
            aaclUsageService.getAggregateClassesNotToBeDistributed(FUND_POOL_ID, usageFilter, classes);
        verify(licenseeClassService, fundPoolService, aaclUsageRepository);
        assertEquals(Collections.singletonList(ALC_2), result);
    }

    @Test
    public void testGetAggregateClassesNotToBeDistributedWithUnmappedAndNoUsages() {
        List<DetailLicenseeClass> classes = Arrays.asList(DLC_1, DLC_2, DLC_3, DLC_4);
        List<FundPoolDetail> fundPoolDetails = Arrays.asList(
            buildFundPoolDetail(ALC_1, BigDecimal.TEN),
            buildFundPoolDetail(ALC_2, BigDecimal.TEN),
            buildFundPoolDetail(ALC_3, BigDecimal.ZERO),
            buildFundPoolDetail(ALC_4, BigDecimal.TEN));
        UsageFilter usageFilter = new UsageFilter();
        expect(licenseeClassService.getAggregateLicenseeClasses())
            .andReturn(Arrays.asList(ALC_1, ALC_2, ALC_3, ALC_4)).once();
        expect(fundPoolService.getDetailsByFundPoolId(FUND_POOL_ID)).andReturn(fundPoolDetails).once();
        expect(aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(usageFilter, 141)).andReturn(false).once();
        expect(aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(usageFilter, 143)).andReturn(true).once();
        expect(aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(usageFilter, 145)).andReturn(false).once();
        replay(licenseeClassService, fundPoolService, aaclUsageRepository);
        List<AggregateLicenseeClass> result =
            aaclUsageService.getAggregateClassesNotToBeDistributed(FUND_POOL_ID, usageFilter, classes);
        verify(licenseeClassService, fundPoolService, aaclUsageRepository);
        assertEquals(Arrays.asList(ALC_2, ALC_4), result);
    }

    @Test
    public void testGetPayeeAggClassesPairsByScenarioId() {
        List<PayeeAccountAggregateLicenseeClassesPair> pairs = new ArrayList<>();
        pairs.add(new PayeeAccountAggregateLicenseeClassesPair());
        expect(aaclUsageRepository.findPayeeAggClassesPairsByScenarioId(SCENARIO_ID)).andReturn(pairs).once();
        replay(aaclUsageRepository);
        assertSame(pairs, aaclUsageService.getPayeeAggClassesPairsByScenarioId(SCENARIO_ID));
        verify(aaclUsageRepository);
    }

    @Test
    public void testGetPayeeTotalHoldersByEmptyFilter() {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        replay(aaclUsageRepository);
        assertEquals(Collections.emptyList(), aaclUsageService.getPayeeTotalHoldersByFilter(scenario, filter));
        verify(aaclUsageRepository);
    }

    @Test
    public void testGetPayeeTotalHoldersByFilter() {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setNetAmountMinThreshold(BigDecimal.TEN);
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        List<PayeeTotalHolder> payeeTotalHolders = Collections.singletonList(new PayeeTotalHolder());
        expect(aaclUsageRepository.findPayeeTotalHoldersByFilter(filter)).andReturn(payeeTotalHolders).once();
        replay(aaclUsageRepository);
        assertSame(payeeTotalHolders, aaclUsageService.getPayeeTotalHoldersByFilter(scenario, filter));
        assertEquals(Collections.singleton(SCENARIO_ID), filter.getScenarioIds());
        verify(aaclUsageRepository);
    }

    @Test
    public void testGetCountByScenarioAndRhAccountNumber() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        expect(aaclUsageRepository.findCountByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000009422L, SEARCH))
            .andReturn(10).once();
        replay(aaclUsageRepository);
        assertEquals(10, aaclUsageService.getCountByScenarioAndRhAccountNumber(scenario, 1000009422L, SEARCH));
        verify(aaclUsageRepository);
    }

    @Test
    public void testGetCountByScenarioAndRhAccountNumberForArchived() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        expect(usageArchiveRepository.findAaclCountByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000009422L, SEARCH))
            .andReturn(10).once();
        replay(usageArchiveRepository);
        assertEquals(10, aaclUsageService.getCountByScenarioAndRhAccountNumber(scenario, 1000009422L, SEARCH));
        verify(usageArchiveRepository);
    }

    @Test
    public void testGetByScenarioAndRhAccountNumber() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        List<UsageDto> usageDtos = Collections.singletonList(new UsageDto());
        expect(aaclUsageRepository.findByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000009422L, SEARCH, null, null))
            .andReturn(usageDtos).once();
        replay(aaclUsageRepository);
        assertEquals(usageDtos,
            aaclUsageService.getByScenarioAndRhAccountNumber(scenario, 1000009422L, SEARCH, null, null));
        verify(aaclUsageRepository);
    }

    @Test
    public void testGetByScenarioAndRhAccountNumberForArchived() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        List<UsageDto> usageDtos = Collections.singletonList(new UsageDto());
        expect(
            usageArchiveRepository.findAaclByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000009422L, SEARCH, null, null))
            .andReturn(usageDtos).once();
        replay(usageArchiveRepository);
        assertEquals(usageDtos,
            aaclUsageService.getByScenarioAndRhAccountNumber(scenario, 1000009422L, SEARCH, null, null));
        verify(usageArchiveRepository);
    }

    @Test
    public void testMoveToArchive() {
        mockStatic(RupContextUtils.class);
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName("Scenario name");
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        List<String> usageIds = Collections.singletonList(USAGE_ID);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        aaclUsageRepository.addToBaselineByScenarioId(SCENARIO_ID, USER_NAME);
        expectLastCall().once();
        expect(usageArchiveRepository.copyToArchiveByScenarioId(SCENARIO_ID, USER_NAME))
            .andReturn(usageIds).once();
        usageAuditService.deleteActionsForExcludedByScenarioId(SCENARIO_ID);
        expectLastCall().once();
        aaclUsageRepository.deleteLockedByScenarioId(SCENARIO_ID);
        expectLastCall().once();
        aaclUsageRepository.deleteExcludedByScenarioId(SCENARIO_ID);
        expectLastCall().once();
        replay(aaclUsageRepository, usageArchiveRepository, usageAuditService, RupContextUtils.class);
        assertEquals(usageIds, aaclUsageService.moveToArchive(scenario));
        verify(aaclUsageRepository, usageArchiveRepository, usageAuditService, RupContextUtils.class);
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch batch = new UsageBatch();
        batch.setId(BATCH_ID);
        batch.setName("AACL Batch");
        batch.setPaymentDate(LocalDate.of(2019, 6, 30));
        batch.setNumberOfBaselineYears(3);
        return batch;
    }

    private AaclClassifiedUsage buildClassifiedUsage() {
        AaclClassifiedUsage usage = new AaclClassifiedUsage();
        usage.setDetailId(USAGE_ID);
        usage.setPublicationType("Book");
        usage.setDiscipline("Life Sciences");
        usage.setEnrollmentProfile("EXGP");
        usage.setWrWrkInst(122830308L);
        usage.setComment("Comment");
        return usage;
    }

    private UsageAge buildUsageAge(Integer period, BigDecimal weight) {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(period);
        usageAge.setWeight(weight);
        return usageAge;
    }

    private PublicationType buildPubType(String id, BigDecimal weight) {
        PublicationType pubType = new PublicationType();
        pubType.setId(id);
        pubType.setWeight(weight);
        return pubType;
    }

    private FundPoolDetail buildFundPoolDetail(AggregateLicenseeClass alc, BigDecimal amount) {
        FundPoolDetail detail = new FundPoolDetail();
        detail.setAggregateLicenseeClass(alc);
        detail.setGrossAmount(amount);
        return detail;
    }

    private List<Integer> buildUsagePeriods() {
        return Lists.newArrayList(2020, 2019, 2017, 2016, 2014, 2012);
    }

    private void verifyUsageAges(List<UsageAge> usageAges) {
        assertEquals(6, usageAges.size());
        assertEquals(buildUsageAge(2020, new BigDecimal("1.00")), usageAges.get(0));
        assertEquals(buildUsageAge(2019, new BigDecimal("0.75")), usageAges.get(1));
        assertEquals(buildUsageAge(2017, new BigDecimal("0.50")), usageAges.get(2));
        assertEquals(buildUsageAge(2016, new BigDecimal("0.25")), usageAges.get(3));
        assertEquals(buildUsageAge(2014, new BigDecimal("0.00")), usageAges.get(4));
        assertEquals(buildUsageAge(2012, new BigDecimal("0.00")), usageAges.get(5));
    }
}
