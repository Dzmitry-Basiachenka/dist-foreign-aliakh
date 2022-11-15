package com.copyright.rup.dist.foreign.service.impl.sal;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.GradeGroupEnum;
import com.copyright.rup.dist.foreign.domain.SalUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.ISalUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

import com.google.common.collect.ImmutableMap;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Verifies {@link SalUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class})
public class SalUsageServiceTest {

    private static final String USER_NAME = "user@copyright.com";
    private static final String USAGE_ID_1 = "d7d15c9f-39f5-4d51-b72b-48a80f7f5388";
    private static final String USAGE_ID_2 = "c72554d7-687e-4173-8406-dbddef74da98";
    private static final String USAGE_ID_3 = "64923002-0873-4175-be01-4a9d64aeb99d";
    private static final String RIGHTSHOLDER_ID = "4914f51d-866c-4e48-8b03-fb4b29b1a5f3";
    private static final String SCENARIO_ID = "2d50e235-34cd-4c38-9e4c-ecd2e748e9ff";
    private static final String SEARCH = "search";
    private final ISalUsageService salUsageService = new SalUsageService();

    private IUsageRepository usageRepository;
    private ISalUsageRepository salUsageRepository;
    private IUsageAuditService usageAuditService;
    private IChainExecutor<Usage> chainExecutor;
    private IUsageArchiveRepository usageArchiveRepository;

    @Before
    public void setUp() {
        usageRepository = createMock(IUsageRepository.class);
        salUsageRepository = createMock(ISalUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        usageArchiveRepository = createMock(IUsageArchiveRepository.class);
        chainExecutor = createMock(IChainExecutor.class);
        Whitebox.setInternalState(salUsageService, usageRepository);
        Whitebox.setInternalState(salUsageService, salUsageRepository);
        Whitebox.setInternalState(salUsageService, usageArchiveRepository);
        Whitebox.setInternalState(salUsageService, usageAuditService);
        Whitebox.setInternalState(salUsageService, chainExecutor);
        Whitebox.setInternalState(salUsageService, "usagesBatchSize", 100);
    }

    @Test
    public void testGetUsagesCount() {
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2020);
        expect(salUsageRepository.findCountByFilter(filter)).andReturn(1).once();
        replay(salUsageRepository);
        assertEquals(1, salUsageService.getUsagesCount(filter));
        verify(salUsageRepository);
    }

    @Test
    public void testGetUsageCountEmptyFilter() {
        assertEquals(0, salUsageService.getUsagesCount(new UsageFilter()));
    }

    @Test
    public void testGetUsageDtos() {
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2020);
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("detailId", Sort.Direction.ASC);
        List<UsageDto> usages = Collections.singletonList(new UsageDto());
        expect(salUsageRepository.findDtosByFilter(filter, pageable, sort)).andReturn(usages).once();
        replay(salUsageRepository);
        assertEquals(usages, salUsageService.getUsageDtos(filter, pageable, sort));
        verify(salUsageRepository);
    }

    @Test
    public void testGetUsagesDtosEmptyFilter() {
        List<UsageDto> usages = salUsageService.getUsageDtos(new UsageFilter(), null, null);
        assertNotNull(usages);
        assertTrue(usages.isEmpty());
    }

    @Test
    public void testWorkPortionIdExists() {
        expect(salUsageRepository.workPortionIdExists("1201064IB2199")).andReturn(true).once();
        replay(salUsageRepository);
        assertTrue(salUsageService.workPortionIdExists("1201064IB2199"));
        verify(salUsageRepository);
    }

    @Test
    public void testWorkPortionIdExistsInBatch() {
        expect(salUsageRepository.workPortionIdExists("2312175IB3200", "b72159b1-479d-4d73-8168-556a60800602"))
            .andReturn(true).once();
        replay(salUsageRepository);
        assertTrue(salUsageService.workPortionIdExists("2312175IB3200", "b72159b1-479d-4d73-8168-556a60800602"));
        verify(salUsageRepository);
    }

    @Test
    public void testInsertItemBankDetails() {
        mockStatic(RupContextUtils.class);
        Usage usage = new Usage();
        usage.setProductFamily("SAL");
        usage.setSalUsage(new SalUsage());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        salUsageRepository.insertItemBankDetail(usage);
        expectLastCall().once();
        usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED, "Uploaded in 'SAL Batch' Batch");
        expectLastCall().once();
        replay(salUsageRepository, usageAuditService, RupContextUtils.class);
        salUsageService.insertItemBankDetails(buildUsageBatch(), Collections.singletonList(usage));
        verify(salUsageRepository, usageAuditService, RupContextUtils.class);
    }

    @Test
    public void testSendForMatching() {
        List<String> usageIds = Arrays.asList(USAGE_ID_1, USAGE_ID_2);
        Usage usage1 = new Usage();
        usage1.setStatus(UsageStatusEnum.NEW);
        Usage usage2 = new Usage();
        usage2.setStatus(UsageStatusEnum.NEW);
        List<Usage> usages = Arrays.asList(usage1, usage2);
        expect(salUsageRepository.findByIds(usageIds)).andReturn(usages).once();
        Capture<Runnable> captureRunnable = new Capture<>();
        chainExecutor.execute(capture(captureRunnable));
        expectLastCall().once();
        chainExecutor.execute(usages, ChainProcessorTypeEnum.MATCHING);
        expectLastCall().once();
        replay(chainExecutor, salUsageRepository);
        salUsageService.sendForMatching(usageIds, "Batch name");
        assertNotNull(captureRunnable);
        Runnable runnable = captureRunnable.getValue();
        assertNotNull(runnable);
        runnable.run();
        verify(chainExecutor, salUsageRepository);
    }

    @Test
    public void testGetUsagesByIds() {
        List<String> usageIds = Collections.singletonList(USAGE_ID_1);
        List<Usage> usages = Collections.singletonList(new Usage());
        expect(salUsageRepository.findByIds(usageIds)).andReturn(usages).once();
        replay(salUsageRepository);
        assertEquals(usages, salUsageService.getUsagesByIds(usageIds));
        verify(salUsageRepository);
    }

    @Test
    public void testGetItemBankDetailGradeByWorkPortionId() {
        expect(salUsageRepository.findItemBankDetailGradeByWorkPortionId("2401064IB2188")).andReturn("K").once();
        replay(salUsageRepository);
        assertEquals("K", salUsageService.getItemBankDetailGradeByWorkPortionId("2401064IB2188"));
        verify(salUsageRepository);
    }

    @Test
    public void testUsageDetailsExist() {
        String batchId = "78a76524-ed6e-4998-9833-2e2051830d42";
        expect(salUsageRepository.usageDataExist(batchId)).andReturn(true).once();
        replay(salUsageRepository);
        assertTrue(salUsageService.usageDataExists(batchId));
        verify(salUsageRepository);
    }

    @Test
    public void testDeleteUsageData() {
        UsageBatch usageBatch = buildUsageBatch();
        usageAuditService.deleteActionsForSalUsageData(usageBatch.getId());
        expectLastCall().once();
        salUsageRepository.deleteUsageData(usageBatch.getId());
        expectLastCall().once();
        replay(usageAuditService, salUsageRepository);
        salUsageService.deleteUsageData(usageBatch);
        verify(usageAuditService, salUsageRepository);
    }

    @Test
    public void testDeleteUsageBatchDetails() {
        UsageBatch usageBatch = buildUsageBatch();
        usageAuditService.deleteActionsByBatchId(usageBatch.getId());
        expectLastCall().once();
        salUsageRepository.deleteByBatchId(usageBatch.getId());
        expectLastCall().once();
        replay(usageAuditService, salUsageRepository);
        salUsageService.deleteUsageBatchDetails(usageBatch);
        verify(usageAuditService, salUsageRepository);
    }

    @Test
    public void testGetUsageDataGradeGroups() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Collections.singleton("cdd46087-87b9-4ecd-ab6f-9b5dcf0f82bf"));
        List<GradeGroupEnum> gradeGroups = Collections.singletonList(GradeGroupEnum.ITEM_BANK);
        expect(salUsageRepository.findUsageDataGradeGroups(filter)).andReturn(gradeGroups).once();
        replay(salUsageRepository);
        assertSame(gradeGroups, salUsageService.getUsageDataGradeGroups(filter));
        verify(salUsageRepository);
    }

    @Test
    public void testPopulatePayees() {
        IRightsholderService rightsholderService = createMock(IRightsholderService.class);
        IPrmIntegrationService prmIntegrationService = createMock(IPrmIntegrationService.class);
        Whitebox.setInternalState(salUsageService, rightsholderService);
        Whitebox.setInternalState(salUsageService, prmIntegrationService);
        String scenarioId = "fe08f50c-bea8-4856-8787-3e3e9e46669c";
        expect(rightsholderService.getByScenarioId(scenarioId)).andReturn(
            Collections.singletonList(buildRightsholder(RIGHTSHOLDER_ID, 2000073957L))).once();
        expect(prmIntegrationService.getRollUps(Collections.singleton(RIGHTSHOLDER_ID)))
            .andReturn(buildRollupsMap()).once();
        salUsageRepository.updatePayeeByAccountNumber(2000073957L, scenarioId, 1000005413L, "SYSTEM");
        expectLastCall().once();
        rightsholderService.updateRighstholdersAsync(Collections.singleton(1000005413L));
        expectLastCall().once();
        replay(salUsageRepository, rightsholderService, prmIntegrationService);
        salUsageService.populatePayees(scenarioId);
        verify(salUsageRepository, rightsholderService, prmIntegrationService);
    }

    @Test
    public void testGetCountByScenarioAndRhAccountNumber() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        expect(salUsageRepository.findCountByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000009422L, SEARCH))
            .andReturn(10).once();
        replay(salUsageRepository);
        assertEquals(10, salUsageService.getCountByScenarioAndRhAccountNumber(scenario, 1000009422L, SEARCH));
        verify(salUsageRepository);
    }

    @Test
    public void testGetCountByScenarioAndRhAccountNumberForArchived() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        expect(usageArchiveRepository.findSalCountByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000009422L, SEARCH))
            .andReturn(10).once();
        replay(usageArchiveRepository);
        assertEquals(10, salUsageService.getCountByScenarioAndRhAccountNumber(scenario, 1000009422L, SEARCH));
        verify(usageArchiveRepository);
    }

    @Test
    public void testGetByScenarioAndRhAccountNumber() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        List<UsageDto> usageDtos = Collections.singletonList(new UsageDto());
        expect(salUsageRepository.findByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000009422L, SEARCH, null, null))
            .andReturn(usageDtos).once();
        replay(salUsageRepository);
        assertEquals(usageDtos,
            salUsageService.getByScenarioAndRhAccountNumber(scenario, 1000009422L, SEARCH, null, null));
        verify(salUsageRepository);
    }

    @Test
    public void testGetByScenarioAndRhAccountNumberForArchived() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        List<UsageDto> usageDtos = Collections.singletonList(new UsageDto());
        expect(
            usageArchiveRepository.findSalByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000009422L, SEARCH, null, null))
            .andReturn(usageDtos).once();
        replay(usageArchiveRepository);
        assertEquals(usageDtos,
            salUsageService.getByScenarioAndRhAccountNumber(scenario, 1000009422L, SEARCH, null, null));
        verify(usageArchiveRepository);
    }

    @Test
    public void testDeleteFromScenario() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        String scenarioId = "36ef8941-5ac3-4894-9f3e-246ee9fbcd56";
        salUsageRepository.deleteFromScenario(scenarioId, USER_NAME);
        expectLastCall().once();
        replay(RupContextUtils.class, salUsageRepository);
        salUsageService.deleteFromScenario(scenarioId);
        verify(RupContextUtils.class, salUsageRepository);
    }

    @Test
    public void testMoveToArchive() {
        mockStatic(RupContextUtils.class);
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName("Scenario name");
        scenario.setStatus(ScenarioStatusEnum.APPROVED);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(usageArchiveRepository.copyToArchiveByScenarioId(SCENARIO_ID, USER_NAME))
            .andReturn(Collections.singletonList(USAGE_ID_1)).once();
        usageRepository.deleteByScenarioId(SCENARIO_ID);
        expectLastCall().once();
        replay(RupContextUtils.class, usageArchiveRepository, usageRepository);
        salUsageService.moveToArchive(scenario);
        verify(RupContextUtils.class, usageArchiveRepository, usageRepository);
    }

    @Test
    public void testUpdateToEligibleWithRhAccountNumber() {
        mockStatic(RupContextUtils.class);
        IRightsholderService rightsholderService = createMock(IRightsholderService.class);
        Whitebox.setInternalState(salUsageService, rightsholderService);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        salUsageRepository.updateRhAccountNumberAndStatusByIds(Collections.singleton(USAGE_ID_3),
            1000023401L, UsageStatusEnum.ELIGIBLE, USER_NAME);
        expectLastCall().once();
        usageAuditService.logAction(USAGE_ID_3, UsageActionTypeEnum.RH_UPDATED, "Manual update");
        expectLastCall().once();
        usageAuditService.logAction(USAGE_ID_3, UsageActionTypeEnum.ELIGIBLE,
            "Usage has become eligible. RH was updated to 1000023401");
        expectLastCall().once();
        rightsholderService.updateRighstholdersAsync(Collections.singleton(1000023401L));
        expectLastCall().once();
        replay(RupContextUtils.class, salUsageRepository, usageAuditService, rightsholderService);
        salUsageService.updateToEligibleWithRhAccountNumber(USAGE_ID_3, 1000023401L, "Manual update");
        verify(RupContextUtils.class, salUsageRepository, usageAuditService, rightsholderService);
    }

    private Map<String, Map<String, Rightsholder>> buildRollupsMap() {
        Map<String, Map<String, Rightsholder>> rollUps = new HashMap<>();
        rollUps.put(RIGHTSHOLDER_ID, ImmutableMap.of(
            "*", buildRightsholder("fb37dc6f-1c17-4da4-9a7c-6a614d6811ce", 1000023401L),
            "SAL", buildRightsholder("8496882a-9c08-4211-845c-ce407878ec8a", 1000005413L)));
        return rollUps;
    }

    private Rightsholder buildRightsholder(String rhId, Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId(rhId);
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch batch = new UsageBatch();
        batch.setId("38ea9a39-0e20-4c3d-8054-0e88d403dd67");
        batch.setName("SAL Batch");
        batch.setPaymentDate(LocalDate.of(2019, 6, 30));
        return batch;
    }
}
