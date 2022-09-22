package com.copyright.rup.dist.foreign.service.impl.nts;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.INtsUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Verifies {@link NtsUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/28/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class})
public class NtsUsageServiceTest {

    private static final String REASON = "reason";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String USER_NAME = "user@copyright.com";
    private static final String BATCH_ID = "3e2d710d-8753-4432-ad7b-25e327c97e94";
    private static final String SCENARIO_ID = "78179a10-ad9e-432e-8aae-30b91fd14ed1";
    private static final BigDecimal SERVICE_FEE = new BigDecimal("0.32000");

    private final NtsUsageService ntsUsageService = new NtsUsageService();

    private INtsUsageRepository ntsUsageRepository;
    private IRightsholderService rightsholderService;
    private IPrmIntegrationService prmIntegrationService;
    private IUsageArchiveRepository usageArchiveRepository;
    private IUsageAuditService usageAuditService;
    private IUsageService usageService;
    private IChainExecutor<Usage> chainExecutor;

    @Before
    public void setUp() {
        ntsUsageRepository = createMock(INtsUsageRepository.class);
        rightsholderService = createMock(IRightsholderService.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        usageAuditService = createMock(IUsageAuditService.class);
        usageArchiveRepository = createMock(IUsageArchiveRepository.class);
        usageService = createMock(IUsageService.class);
        chainExecutor = createMock(IChainExecutor.class);
        Whitebox.setInternalState(ntsUsageService, ntsUsageRepository);
        Whitebox.setInternalState(ntsUsageService, rightsholderService);
        Whitebox.setInternalState(ntsUsageService, prmIntegrationService);
        Whitebox.setInternalState(ntsUsageService, usageArchiveRepository);
        Whitebox.setInternalState(ntsUsageService, usageAuditService);
        Whitebox.setInternalState(ntsUsageService, usageService);
        Whitebox.setInternalState(ntsUsageService, chainExecutor);
        Whitebox.setInternalState(ntsUsageService, "usagesBatchSize", 100);
    }

    @Test
    public void testInsertUsages() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        UsageBatch usageBatch = new UsageBatch();
        List<String> usageIds = Collections.singletonList("92acae56-e328-4cb9-b2ab-ad696a01d71c");
        expect(ntsUsageRepository.insertUsages(usageBatch, USER_NAME)).andReturn(usageIds).once();
        replay(RupContextUtils.class, ntsUsageRepository);
        assertEquals(usageIds, ntsUsageService.insertUsages(usageBatch));
        verify(RupContextUtils.class, ntsUsageRepository);
    }

    @Test
    public void testExcludeRightsHoldersFromScenario() {
        Set<String> usageIds =
            Sets.newHashSet("095eaefe-37de-4adb-928e-be0b888094b9", "7ee9c1ab-57ca-45cf-8a8d-83d7baeb6a9c");
        Set<Long> accountNumbers = Sets.newHashSet(2000017001L, 2000078999L);
        ntsUsageRepository.recalculateAmountsFromExcludedRightshoders(SCENARIO_ID, accountNumbers);
        expectLastCall().once();
        expect(ntsUsageRepository.deleteFromScenarioByRightsholder(SCENARIO_ID, accountNumbers,
            RupContextUtils.getUserName())).andReturn(usageIds).once();
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, REASON);
        expectLastCall().once();
        replay(ntsUsageRepository, usageAuditService);
        ntsUsageService.deleteFromScenarioByRightsholders(SCENARIO_ID, accountNumbers, REASON);
        verify(ntsUsageRepository, usageAuditService);
    }

    @Test
    public void testGetUsagesCountForBatch() {
        UsageBatch usageBatch = new UsageBatch();
        NtsFields ntsFields = new NtsFields();
        ntsFields.setFundPoolPeriodFrom(2019);
        ntsFields.setFundPoolPeriodTo(2020);
        ntsFields.setMarkets(Collections.singleton("Edu"));
        usageBatch.setNtsFields(ntsFields);
        expect(ntsUsageRepository.findCountForBatch(2019, 2020, Collections.singleton("Edu")))
            .andReturn(1).once();
        replay(ntsUsageRepository);
        assertEquals(1, ntsUsageService.getUsagesCountForBatch(usageBatch));
        verify(ntsUsageRepository);
    }

    @Test
    public void testPopulatePayeeAndCalculateAmountsForScenarioUsages() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        Scenario.NtsFields ntsFields = new Scenario.NtsFields();
        ntsFields.setPostServiceFeeAmount(new BigDecimal("100.500"));
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setNtsFields(ntsFields);
        Rightsholder rh1 = buildRightsholder(1000009522L);
        Rightsholder rh2 = buildRightsholder(2000009522L);
        Map<String, Map<String, Rightsholder>> rollUps = new HashMap<>();
        Rightsholder payee1 = buildRightsholder(1000004422L);
        Rightsholder payee2 = buildRightsholder(2000004422L);
        rollUps.put(rh1.getId(), ImmutableMap.of(NTS_PRODUCT_FAMILY, payee1));
        rollUps.put(rh2.getId(), ImmutableMap.of(NTS_PRODUCT_FAMILY, payee2));
        Set<String> rhIds = Sets.newHashSet(rh2.getId(), rh1.getId());
        expect(prmIntegrationService.getRollUps(rhIds)).andReturn(rollUps).once();
        Map<String, Table<String, String, Object>> preferences = new HashMap<>();
        expect(prmIntegrationService.getPreferences(rhIds)).andReturn(preferences).once();
        expect(rightsholderService.getByScenarioId(SCENARIO_ID)).andReturn(Arrays.asList(rh1, rh2)).once();
        expect(prmIntegrationService.isRightsholderParticipating(preferences, rh1.getId(), NTS_PRODUCT_FAMILY))
            .andReturn(true).once();
        expect(prmIntegrationService.isRightsholderParticipating(preferences, rh2.getId(), NTS_PRODUCT_FAMILY))
            .andReturn(false).once();
        expect(prmIntegrationService.getRhParticipatingServiceFee(true)).andReturn(SERVICE_FEE).once();
        expect(prmIntegrationService.getRhParticipatingServiceFee(false)).andReturn(SERVICE_FEE).once();
        ntsUsageRepository.calculateAmountsAndUpdatePayeeByAccountNumber(rh1.getAccountNumber(), SCENARIO_ID,
            SERVICE_FEE, true, 1000004422L, USER_NAME);
        expectLastCall().once();
        ntsUsageRepository.calculateAmountsAndUpdatePayeeByAccountNumber(rh2.getAccountNumber(), SCENARIO_ID,
            SERVICE_FEE, false, 2000004422L, USER_NAME);
        expectLastCall().once();
        ntsUsageRepository.applyPostServiceFeeAmount(scenario.getId());
        expectLastCall().once();
        rightsholderService.updateRighstholdersAsync(Sets.newHashSet(2000004422L, 1000004422L));
        expectLastCall().once();
        replay(RupContextUtils.class, ntsUsageRepository, prmIntegrationService, rightsholderService);
        ntsUsageService.populatePayeeAndCalculateAmountsForScenarioUsages(scenario);
        verify(RupContextUtils.class, ntsUsageRepository, prmIntegrationService, rightsholderService);
    }

    @Test
    public void testDeleteFromNtsFundPool() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        String fundPoolId = "d40dc124-83dd-463b-9102-4ff383cc94b1";
        ntsUsageRepository.deleteFromNtsFundPool(fundPoolId, USER_NAME);
        expectLastCall().once();
        replay(RupContextUtils.class, ntsUsageRepository);
        ntsUsageService.deleteFromNtsFundPool(fundPoolId);
        verify(RupContextUtils.class, ntsUsageRepository);
    }

    @Test
    public void testDeleteBelletristicByScenarioId() {
        String scenarioId = "3b387481-b643-47df-acc0-728cd6878d17";
        ntsUsageRepository.deleteBelletristicByScenarioId(scenarioId);
        expectLastCall().once();
        replay(ntsUsageRepository);
        ntsUsageService.deleteBelletristicByScenarioId(scenarioId);
        verify(ntsUsageRepository);
    }

    @Test
    public void testDeleteUsagesFromNtsScenario() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        String scenarioId = "fcfb154b-52b4-4c17-bde9-19677e94fa87";
        ntsUsageRepository.deleteFromScenario(scenarioId, USER_NAME);
        expectLastCall().once();
        replay(RupContextUtils.class, ntsUsageRepository);
        ntsUsageService.deleteFromScenario(scenarioId);
        verify(RupContextUtils.class, ntsUsageRepository);
    }

    @Test
    public void testGetUnclassifiedUsagesCount() {
        Set<Long> wrWrkInsts = Collections.singleton(987654321L);
        expect(ntsUsageRepository.findUnclassifiedUsagesCountByWrWrkInsts(wrWrkInsts)).andReturn(2).once();
        replay(ntsUsageRepository);
        ntsUsageService.getUnclassifiedUsagesCount(wrWrkInsts);
        verify(ntsUsageRepository);
    }

    @Test
    public void testMoveToArchivedNts() {
        mockStatic(RupContextUtils.class);
        Scenario scenario = new Scenario();
        scenario.setId("96768299-4996-4ad1-b0af-7580412a72ea");
        List<String> usageIds = Collections.singletonList(RupPersistUtils.generateUuid());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(usageArchiveRepository.copyNtsToArchiveByScenarioId(scenario.getId(), USER_NAME))
            .andReturn(usageIds).once();
        usageAuditService.deleteActionsByScenarioId(scenario.getId());
        expectLastCall().once();
        ntsUsageRepository.deleteByScenarioId("96768299-4996-4ad1-b0af-7580412a72ea");
        expectLastCall().once();
        usageArchiveRepository.moveFundUsagesToArchive("96768299-4996-4ad1-b0af-7580412a72ea");
        expectLastCall().once();
        replay(ntsUsageRepository, usageArchiveRepository, usageAuditService, RupContextUtils.class);
        ntsUsageService.moveToArchive(scenario);
        verify(ntsUsageRepository, usageArchiveRepository, usageAuditService, RupContextUtils.class);
    }

    @Test
    public void testAddWithdrawnUsagesToNtsFundPool() {
        String fundId = "e6042ad1-51e8-48d8-8b06-f8b2e684d993";
        Set<String> batchIds = Collections.singleton(BATCH_ID);
        ntsUsageRepository.addWithdrawnUsagesToNtsFundPool(fundId, batchIds, USER_NAME);
        expectLastCall().once();
        replay(ntsUsageRepository);
        ntsUsageService.addWithdrawnUsagesToNtsFundPool(fundId, batchIds, USER_NAME);
        verify(ntsUsageRepository);
    }

    @Test
    public void testSendForGettingRights() {
        List<String> usageIds = Arrays.asList(RupPersistUtils.generateUuid(), RupPersistUtils.generateUuid());
        Usage usage1 = new Usage();
        usage1.setStatus(UsageStatusEnum.WORK_FOUND);
        Usage usage2 = new Usage();
        usage2.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
        List<Usage> usages = Arrays.asList(usage1, usage2);
        expect(usageService.getUsagesByIds(usageIds)).andReturn(usages).once();
        Capture<Runnable> captureRunnable = new Capture<>();
        chainExecutor.execute(capture(captureRunnable));
        expectLastCall().once();
        chainExecutor.execute(Collections.singletonList(usage1), ChainProcessorTypeEnum.RIGHTS);
        expectLastCall().once();
        replay(chainExecutor, usageService);
        ntsUsageService.sendForGettingRights(usageIds, "Batch name");
        assertNotNull(captureRunnable);
        Runnable runnable = captureRunnable.getValue();
        runnable.run();
        verify(chainExecutor, usageService);
    }

    private Rightsholder buildRightsholder(Long accountNUmber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId(RupPersistUtils.generateUuid());
        rightsholder.setAccountNumber(accountNUmber);
        return rightsholder;
    }
}
