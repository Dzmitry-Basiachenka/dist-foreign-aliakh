package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
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
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import com.google.common.collect.Table;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UsageService}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/03/17
 *
 * @author Aliaksei Pchelnikau
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class, RupPersistUtils.class})
public class UsageServiceTest {

    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String USAGE_ID_1 = "Usage id 1";
    private static final String USAGE_ID_2 = "Usage id 2";
    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private static final String USER_NAME = "User name";
    private static final Long RH_ACCOUNT_NUMBER = 1000001534L;
    private static final Long PAYEE_ACCOUNT_NUMBER = 12387456L;
    private static final String RH_ID = RupPersistUtils.generateUuid();
    private Scenario scenario;
    private IUsageRepository usageRepository;
    private IUsageArchiveRepository usageArchiveRepository;
    private IUsageAuditService usageAuditService;
    private IScenarioAuditService scenarioAuditService;
    private IRightsholderService rightsholderService;
    private UsageService usageService;
    private IPrmIntegrationService prmIntegrationService;
    private IChainExecutor<Usage> chainExecutor;

    @Rule
    private final ExpectedException exception = ExpectedException.none();

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setCreateUser(USER_NAME);
        usageRepository = createMock(IUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        usageArchiveRepository = createMock(IUsageArchiveRepository.class);
        scenarioAuditService = createMock(IScenarioAuditService.class);
        rightsholderService = createMock(IRightsholderService.class);
        chainExecutor = createMock(IChainExecutor.class);
        usageService = new UsageService();
        Whitebox.setInternalState(usageService, "chainExecutor", chainExecutor);
        Whitebox.setInternalState(usageService, "usageRepository", usageRepository);
        Whitebox.setInternalState(usageService, "usageAuditService", usageAuditService);
        Whitebox.setInternalState(usageService, "prmIntegrationService", prmIntegrationService);
        Whitebox.setInternalState(usageService, "usageArchiveRepository", usageArchiveRepository);
        Whitebox.setInternalState(usageService, "scenarioAuditService", scenarioAuditService);
        Whitebox.setInternalState(usageService, "rightsholderService", rightsholderService);
        Whitebox.setInternalState(usageService, "claAccountNumber", 2000017000L);
    }

    @Test
    public void testGetUsagesCount() {
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        expect(usageRepository.findCountByFilter(filter)).andReturn(1).once();
        replay(usageRepository);
        usageService.getUsagesCount(filter);
        verify(usageRepository);
    }

    @Test
    public void testGetUsageCountEmptyFilter() {
        assertEquals(0, usageService.getUsagesCount(new UsageFilter()));
    }

    @Test
    public void testGetUsageDtos() {
        List<UsageDto> usagesWithBatch = Lists.newArrayList(new UsageDto());
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("detailId", Sort.Direction.ASC);
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        expect(usageRepository.findDtosByFilter(filter, pageable, sort)).andReturn(usagesWithBatch).once();
        replay(usageRepository);
        List<UsageDto> result = usageService.getUsageDtos(filter, pageable, sort);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(usageRepository);
    }

    @Test
    public void testGetUsagesDtosEmptyFilter() {
        List<UsageDto> result = usageService.getUsageDtos(new UsageFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testInsertUsages() {
        mockStatic(RupContextUtils.class);
        Capture<Usage> captureUsage1 = new Capture<>();
        Capture<Usage> captureUsage2 = new Capture<>();
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setGrossAmount(new BigDecimal("12.00"));
        usageBatch.setName("ABC");
        Usage usage1 = new Usage();
        usage1.setReportedValue(BigDecimal.TEN);
        Usage usage2 = new Usage();
        usage2.setReportedValue(BigDecimal.ONE);
        List<Usage> usages = Lists.newArrayList(usage1, usage2);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        usageRepository.insert(capture(captureUsage1));
        expectLastCall().once();
        usageAuditService.logAction(usage1.getId(), UsageActionTypeEnum.LOADED, "Uploaded in 'ABC' Batch");
        expectLastCall().once();
        usageRepository.insert(capture(captureUsage2));
        expectLastCall().once();
        usageAuditService.logAction(usage2.getId(), UsageActionTypeEnum.LOADED, "Uploaded in 'ABC' Batch");
        expectLastCall().once();
        replay(usageRepository, usageAuditService, RupContextUtils.class);
        assertEquals(2, usageService.insertUsages(usageBatch, usages));
        verifyUsage(captureUsage1.getValue(), new BigDecimal("10.9090909090"));
        verifyUsage(captureUsage2.getValue(), new BigDecimal("1.0909090909"));
        verify(usageRepository, usageAuditService, RupContextUtils.class);
    }

    @Test
    public void testDeleteUsageBatchDetails() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(RupPersistUtils.generateUuid());
        usageRepository.deleteByBatchId(usageBatch.getId());
        expectLastCall().once();
        usageAuditService.deleteActionsByBatchId(usageBatch.getId());
        expectLastCall().once();
        replay(usageRepository);
        usageService.deleteUsageBatchDetails(usageBatch);
        verify(usageRepository);
    }

    @Test
    public void testDeleteById() {
        String usageId = RupPersistUtils.generateUuid();
        usageAuditService.deleteActionsByUsageId(usageId);
        expectLastCall().once();
        usageRepository.deleteById(usageId);
        expectLastCall();
        replay(usageRepository);
        usageService.deleteById(usageId);
        verify(usageRepository);
    }

    @Test
    public void testGetUsagesByScenario() {
        List<Usage> usages = Collections.singletonList(new Usage());
        expect(usageRepository.findByScenarioId(scenario.getId())).andReturn(usages).once();
        replay(usageRepository);
        assertSame(usages, usageService.getUsagesByScenarioId(scenario.getId()));
        verify(usageRepository);
    }

    @Test
    public void testGetUsagesByScenarioIdForRefresh() {
        List<Usage> usages = Collections.singletonList(new Usage());
        expect(usageRepository.findByScenarioId(scenario.getId())).andReturn(usages).once();
        replay(usageRepository);
        assertSame(usages, usageService.getUsagesByScenarioId(scenario.getId()));
        verify(usageRepository);
    }

    @Test
    public void testGetUsagesForReconcile() {
        List<Usage> usages = Collections.singletonList(new Usage());
        expect(usageRepository.findForReconcile(scenario.getId())).andReturn(usages).once();
        replay(usageRepository);
        assertSame(usages, usageService.getUsagesForReconcile(scenario.getId()));
        verify(usageRepository);
    }

    @Test
    public void testGetInvalidRightsholdersByFilter() {
        UsageFilter filter = new UsageFilter();
        List<Long> accountNumbers = Arrays.asList(1000000001L, 1000000002L);
        expect(usageRepository.findInvalidRightsholdersByFilter(filter)).andReturn(accountNumbers).once();
        replay(usageRepository);
        assertSame(accountNumbers, usageService.getInvalidRightsholdersByFilter(filter));
        verify(usageRepository);
    }

    @Test
    public void testGetUsagesWithAmounts() {
        UsageFilter usageFilter = new UsageFilter();
        Usage usage = buildUsage(USAGE_ID_1);
        expect(usageRepository.findWithAmountsAndRightsholders(usageFilter))
            .andReturn(Lists.newArrayList(usage)).once();
        replay(usageRepository);
        assertTrue(CollectionUtils.isNotEmpty(usageService.getUsagesWithAmounts(usageFilter)));
        verify(usageRepository);
    }

    @Test
    public void testAddUsagesToScenario() {
        Usage usage1 = buildUsage(USAGE_ID_1);
        Usage usage2 = buildUsage(USAGE_ID_2);
        Capture<List<String>> rightsholdersIdsCapture = new Capture<>();
        List<Usage> usages = Lists.newArrayList(usage1, usage2);
        Table<String, String, Long> rollUps = HashBasedTable.create();
        rollUps.put(RH_ID, FAS_PRODUCT_FAMILY, PAYEE_ACCOUNT_NUMBER);
        usageRepository.addToScenario(usages);
        expectLastCall().once();
        expect(prmIntegrationService.getRollUps(capture(rightsholdersIdsCapture)))
            .andReturn(rollUps).once();
        expect(prmIntegrationService.isRightsholderParticipating(usage1.getRightsholder().getId(),
            usage1.getProductFamily())).andReturn(true).once();
        expect(prmIntegrationService.isRightsholderParticipating(usage2.getRightsholder().getId(),
            usage2.getProductFamily())).andReturn(true).once();
        expect(prmIntegrationService.getRhParticipatingServiceFee(true))
            .andReturn(new BigDecimal("0.16000")).times(2);
        rightsholderService.updateRighstholdersAsync(Collections.singleton(PAYEE_ACCOUNT_NUMBER));
        expectLastCall().once();
        replay(usageRepository, prmIntegrationService, rightsholderService);
        usageService.addUsagesToScenario(Lists.newArrayList(usage1, usage2), scenario);
        verify(usageRepository, prmIntegrationService, rightsholderService);
    }

    @Test
    public void testGetRightsholderTotalsHoldersBySentToLmScenario() {
        List<RightsholderTotalsHolder> rightsholderTotalsHolders = Lists.newArrayList(new RightsholderTotalsHolder());
        Pageable pageable = new Pageable(0, 1);
        expect(usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY, pageable,
            null)).andReturn(rightsholderTotalsHolders).once();
        expect(usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY,
            pageable, null)).andReturn(rightsholderTotalsHolders).times(2);
        replay(usageRepository, usageArchiveRepository);
        assertResult(
            usageService.getRightsholderTotalsHoldersByScenario(scenario, StringUtils.EMPTY, pageable, null), 1);
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        assertResult(
            usageService.getRightsholderTotalsHoldersByScenario(scenario, StringUtils.EMPTY, pageable, null), 1);
        scenario.setStatus(ScenarioStatusEnum.ARCHIVED);
        assertResult(
            usageService.getRightsholderTotalsHoldersByScenario(scenario, StringUtils.EMPTY, pageable, null), 1);
        verify(usageRepository, usageArchiveRepository);
    }

    @Test
    public void testGetRightsholderTotalsHolderCountByScenario() {
        expect(usageRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, StringUtils.EMPTY))
            .andReturn(5).once();
        expect(usageArchiveRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, StringUtils.EMPTY))
            .andReturn(3).times(2);
        replay(usageRepository, usageArchiveRepository);
        assertEquals(5, usageService.getRightsholderTotalsHolderCountByScenario(scenario, StringUtils.EMPTY));
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        assertEquals(3, usageService.getRightsholderTotalsHolderCountByScenario(scenario, StringUtils.EMPTY));
        scenario.setStatus(ScenarioStatusEnum.ARCHIVED);
        assertEquals(3, usageService.getRightsholderTotalsHolderCountByScenario(scenario, StringUtils.EMPTY));
        verify(usageRepository, usageArchiveRepository);
    }

    @Test
    public void testIsScenarioEmpty() {
        expect(usageRepository.isScenarioEmpty(SCENARIO_ID)).andReturn(true).once();
        replay(usageRepository);
        assertTrue(usageService.isScenarioEmpty(scenario));
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        assertFalse(usageService.isScenarioEmpty(scenario));
        scenario.setStatus(ScenarioStatusEnum.ARCHIVED);
        assertFalse(usageService.isScenarioEmpty(scenario));
        verify(usageRepository);
    }

    @Test
    public void testGetCountByScenarioAndRhAccountNumber() {
        expect(usageRepository.findCountByScenarioIdAndRhAccountNumber(RH_ACCOUNT_NUMBER, SCENARIO_ID,
            StringUtils.EMPTY)).andReturn(5).once();
        expect(usageArchiveRepository.findCountByScenarioIdAndRhAccountNumber(SCENARIO_ID, RH_ACCOUNT_NUMBER,
            StringUtils.EMPTY)).andReturn(3).times(2);
        replay(usageRepository, usageArchiveRepository);
        assertEquals(5, usageService.getCountByScenarioAndRhAccountNumber(RH_ACCOUNT_NUMBER, scenario,
            StringUtils.EMPTY));
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        assertEquals(3, usageService.getCountByScenarioAndRhAccountNumber(RH_ACCOUNT_NUMBER, scenario,
            StringUtils.EMPTY));
        scenario.setStatus(ScenarioStatusEnum.ARCHIVED);
        assertEquals(3, usageService.getCountByScenarioAndRhAccountNumber(RH_ACCOUNT_NUMBER, scenario,
            StringUtils.EMPTY));
        verify(usageRepository, usageArchiveRepository);
    }

    @Test
    public void testGetByScenarioAndRhAccountNumber() {
        List<UsageDto> usages = Lists.newArrayList(new UsageDto(), new UsageDto());
        Pageable pageable = new Pageable(0, 2);
        expect(usageRepository.findByScenarioIdAndRhAccountNumber(RH_ACCOUNT_NUMBER, SCENARIO_ID, null, pageable,
            null)).andReturn(usages).once();
        expect(usageArchiveRepository.findByScenarioIdAndRhAccountNumber(SCENARIO_ID, RH_ACCOUNT_NUMBER, null, pageable,
            null)).andReturn(usages).times(2);
        replay(usageRepository, usageArchiveRepository);
        assertResult(
            usageService.getByScenarioAndRhAccountNumber(RH_ACCOUNT_NUMBER, scenario, null, pageable, null), 2);
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        assertResult(
            usageService.getByScenarioAndRhAccountNumber(RH_ACCOUNT_NUMBER, scenario, null, pageable, null), 2);
        scenario.setStatus(ScenarioStatusEnum.ARCHIVED);
        assertResult(
            usageService.getByScenarioAndRhAccountNumber(RH_ACCOUNT_NUMBER, scenario, null, pageable, null), 2);
        verify(usageRepository, usageArchiveRepository);
    }

    @Test
    public void testDeleteUsagesFromScenario() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        usageRepository.deleteFromScenario(SCENARIO_ID, USER_NAME);
        expectLastCall().once();
        replay(usageRepository, RupContextUtils.class);
        usageService.deleteFromScenario(SCENARIO_ID);
        verify(usageRepository, RupContextUtils.class);
    }

    @Test
    public void testDeleteFromScenarioByAccountNumbers() {
        List<String> usagesIds = Lists.newArrayList(RupPersistUtils.generateUuid(), RupPersistUtils.generateUuid());
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        List<Long> accountNumbers = Collections.singletonList(RH_ACCOUNT_NUMBER);
        usageRepository.deleteFromScenario(usagesIds, USER_NAME);
        expectLastCall().once();
        expect(usageRepository.findIdsByScenarioIdRroAccountNumberRhAccountNumbers(scenario.getId(), 2000017011L,
            accountNumbers)).andReturn(usagesIds).once();
        usageAuditService.logAction(usagesIds.get(0), UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO,
            "Action reason");
        expectLastCall().once();
        usageAuditService.logAction(usagesIds.get(1), UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO,
            "Action reason");
        expectLastCall().once();
        replay(usageRepository, usageAuditService, RupContextUtils.class);
        usageService.deleteFromScenario(scenario, 2000017011L, accountNumbers, "Action reason");
        verify(usageRepository, usageAuditService, RupContextUtils.class);
    }

    @Test
    public void testMoveToArchived() {
        Usage usage1 = buildUsage(RupPersistUtils.generateUuid());
        Usage usage2 = buildUsage(RupPersistUtils.generateUuid());
        List<Usage> usages = Lists.newArrayList(usage1, usage2);
        expect(usageRepository.findByScenarioId(scenario.getId())).andReturn(usages).once();
        usageArchiveRepository.insert(usage1);
        expectLastCall().once();
        usageArchiveRepository.insert(usage2);
        expectLastCall().once();
        usageRepository.deleteByScenarioId(scenario.getId());
        expectLastCall().once();
        replay(usageRepository, usageArchiveRepository);
        usageService.moveToArchive(scenario);
        verify(usageRepository, usageArchiveRepository);
    }

    @Test
    public void testRecalculateUsagesForRefresh() {
        UsageFilter filter = new UsageFilter();
        Usage usage1 = buildUsage(RupPersistUtils.generateUuid());
        Usage usage2 = buildUsageWithPayee(RupPersistUtils.generateUuid());
        Table<String, String, Long> rollUps = HashBasedTable.create();
        rollUps.put(RH_ID, FAS_PRODUCT_FAMILY, PAYEE_ACCOUNT_NUMBER);
        expect(usageRepository.findRightsholdersInformation(SCENARIO_ID)).andReturn(
            ImmutableMap.of(usage2.getRightsholder().getAccountNumber(), usage2)).once();
        expect(usageRepository.findWithAmountsAndRightsholders(filter)).andReturn(Collections.singletonList(usage1))
            .once();
        expect(prmIntegrationService.getRollUps(Collections.EMPTY_SET)).andReturn(rollUps).once();
        expect(prmIntegrationService.getRhParticipatingServiceFee(false)).andReturn(new BigDecimal("0.32")).once();
        usageRepository.addToScenario(Collections.singletonList(usage1));
        expectLastCall().once();
        rightsholderService.updateRighstholdersAsync(Collections.singleton(PAYEE_ACCOUNT_NUMBER));
        expectLastCall().once();
        replay(usageRepository, prmIntegrationService, rightsholderService);
        usageService.recalculateUsagesForRefresh(filter, scenario);
        verify(usageRepository, prmIntegrationService, rightsholderService);
    }

    @Test
    public void testGetAuditItemsCount() {
        AuditFilter filter = new AuditFilter();
        expect(usageRepository.findCountForAudit(filter)).andReturn(1).once();
        replay(usageRepository);
        assertEquals(1, usageService.getAuditItemsCount(filter));
        verify(usageRepository);
    }

    @Test
    public void testGetForAudit() {
        AuditFilter filter = new AuditFilter();
        Pageable pageable = new Pageable(0, 10);
        Sort sort = new Sort("detailId", Direction.DESC);
        expect(usageRepository.findForAudit(filter, pageable, sort)).andReturn(Collections.emptyList()).once();
        replay(usageRepository);
        assertEquals(Collections.emptyList(), usageService.getForAudit(filter, pageable, sort));
        verify(usageRepository);
    }

    @Test
    public void testUpdatePaidInfo() {
        PaidUsage usage = buildPaidUsage(UsageStatusEnum.SENT_TO_LM, false, null);
        PaidUsage usageInPaidStatus = buildPaidUsage(UsageStatusEnum.PAID, false, null);
        expect(usageArchiveRepository.findUsageInformationById(ImmutableList.of(USAGE_ID_1)))
            .andReturn(ImmutableList.of(usage))
            .once();
        usageArchiveRepository.updatePaidInfo(usageInPaidStatus);
        expectLastCall().once();
        usageAuditService.logAction(USAGE_ID_1, UsageActionTypeEnum.PAID,
            "Usage has been paid according to information from the LM");
        expectLastCall().once();
        replay(usageArchiveRepository, usageAuditService);
        usageService.updatePaidInfo(Collections.singletonList(usage));
        verify(usageArchiveRepository, usageAuditService);
    }

    @Test
    public void testUpdatePaidInfoSplitOriginalDetail() {
        PaidUsage usage = buildPaidUsage(UsageStatusEnum.SENT_TO_LM, false, true);
        PaidUsage usageInPaidStatus = buildPaidUsage(UsageStatusEnum.PAID, false, true);
        expect(usageArchiveRepository.findUsageInformationById(ImmutableList.of(USAGE_ID_1)))
            .andReturn(ImmutableList.of(usage))
            .once();
        usageArchiveRepository.updatePaidInfo(usageInPaidStatus);
        expectLastCall().once();
        usageAuditService.logAction(USAGE_ID_1, UsageActionTypeEnum.PAID,
            "Usage has been adjusted based on Split process");
        expectLastCall().once();
        scenarioAuditService.logAction("e4a81dff-719b-4f73-bb0d-fcfc23ea2395",
            ScenarioActionTypeEnum.UPDATED_AFTER_SPLIT, "Scenario has been updated after Split process");
        expectLastCall().once();
        replay(usageArchiveRepository, usageAuditService, scenarioAuditService);
        usageService.updatePaidInfo(Collections.singletonList(usage));
        verify(usageArchiveRepository, usageAuditService, scenarioAuditService);
    }

    @Test
    public void testUpdatePaidInfoSplitNewDetail() {
        mockStatic(RupPersistUtils.class);
        PaidUsage usageFromLm = buildPaidUsage(null, false, false);
        Usage storedUsage = buildUsage(USAGE_ID_1);
        expect(usageArchiveRepository.findUsageInformationById(ImmutableList.of(USAGE_ID_1)))
            .andReturn(ImmutableList.of(storedUsage))
            .once();
        String newUsageId = "93c24edf-bc4d-406e-bd54-9a7379a6e532";
        expect(RupPersistUtils.generateUuid())
            .andReturn(newUsageId)
            .once();
        usageArchiveRepository.insertPaid(buildPaidUsageToInsert(usageFromLm, storedUsage, newUsageId));
        expectLastCall().once();
        usageAuditService.logAction(newUsageId, UsageActionTypeEnum.PAID,
            "Usage has been created based on Split process");
        expectLastCall().once();
        replay(usageArchiveRepository, usageAuditService, RupPersistUtils.class);
        usageService.updatePaidInfo(Collections.singletonList(usageFromLm));
        verify(usageArchiveRepository, usageAuditService, RupPersistUtils.class);
    }

    @Test
    public void testUpdatePostDistributionPaidInfo() {
        mockStatic(RupPersistUtils.class);
        PaidUsage usageFromLm = buildPaidUsage(null, true, null);
        Usage storedUsage = buildUsage(USAGE_ID_1);
        expect(usageArchiveRepository.findUsageInformationById(ImmutableList.of(USAGE_ID_1)))
            .andReturn(ImmutableList.of(storedUsage))
            .once();
        String newUsageId = "2a1aa09c-0de7-49f7-a41e-f2a05c7bb43f";
        expect(RupPersistUtils.generateUuid())
            .andReturn(newUsageId)
            .once();
        usageArchiveRepository.insertPaid(buildPaidUsageToInsert(usageFromLm, storedUsage, newUsageId));
        expectLastCall().once();
        usageAuditService.logAction(newUsageId, UsageActionTypeEnum.PAID,
            "Usage has been created based on Post-Distribution process");
        expectLastCall().once();
        replay(usageArchiveRepository, usageAuditService, RupPersistUtils.class);
        usageService.updatePaidInfo(Collections.singletonList(usageFromLm));
        verify(usageArchiveRepository, usageAuditService, RupPersistUtils.class);
    }

    @Test
    public void testUpdatePostDistributionPaidInfoSplitOriginalUsage() {
        mockStatic(RupPersistUtils.class);
        PaidUsage usageFromLm = buildPaidUsage(null, true, true);
        Usage storedUsage = buildUsage(USAGE_ID_1);
        expect(usageArchiveRepository.findUsageInformationById(ImmutableList.of(USAGE_ID_1)))
            .andReturn(ImmutableList.of(storedUsage))
            .once();
        String newUsageId = "93c24edf-bc4d-406e-bd54-9a7379a6e532";
        expect(RupPersistUtils.generateUuid())
            .andReturn(newUsageId)
            .once();
        usageArchiveRepository.insertPaid(buildPaidUsageToInsert(usageFromLm, storedUsage, newUsageId));
        expectLastCall().once();
        usageAuditService.logAction(newUsageId, UsageActionTypeEnum.PAID,
            "Usage has been created based on Post-Distribution and Split processes");
        expectLastCall().once();
        replay(usageArchiveRepository, usageAuditService, RupPersistUtils.class);
        usageService.updatePaidInfo(Collections.singletonList(usageFromLm));
        verify(usageArchiveRepository, usageAuditService, RupPersistUtils.class);
    }

    @Test
    public void testUpdatePostDistributionPaidInfoSplitNewUsage() {
        mockStatic(RupPersistUtils.class);
        PaidUsage usageFromLm = buildPaidUsage(null, true, false);
        Usage storedUsage = buildUsage(USAGE_ID_1);
        expect(usageArchiveRepository.findUsageInformationById(ImmutableList.of(USAGE_ID_1)))
            .andReturn(ImmutableList.of(storedUsage))
            .once();
        String newUsageId = "ef671fd5-6610-450e-9326-255a48230e3b";
        expect(RupPersistUtils.generateUuid())
            .andReturn(newUsageId)
            .once();
        usageArchiveRepository.insertPaid(buildPaidUsageToInsert(usageFromLm, storedUsage, newUsageId));
        expectLastCall().once();
        usageAuditService.logAction(newUsageId, UsageActionTypeEnum.PAID,
            "Usage has been created based on Post-Distribution and Split processes");
        expectLastCall().once();
        replay(usageArchiveRepository, usageAuditService, RupPersistUtils.class);
        usageService.updatePaidInfo(Collections.singletonList(usageFromLm));
        verify(usageArchiveRepository, usageAuditService, RupPersistUtils.class);
    }

    @Test
    public void testUpdatePaidInfoNotFoundUsage() {
        PaidUsage paidUsage = new PaidUsage();
        paidUsage.setId(USAGE_ID_1);
        expect(usageArchiveRepository.findUsageInformationById(ImmutableList.of(USAGE_ID_1)))
            .andReturn(Lists.newArrayList()).once();
        replay(usageArchiveRepository);
        usageService.updatePaidInfo(Collections.singletonList(paidUsage));
        verify(usageArchiveRepository);
    }

    @Test
    public void testLoadResearchedUsages() {
        String usageId1 = "721ca627-09bc-4204-99f4-6acae415fa5d";
        String usageId2 = "9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a";
        ResearchedUsage researchedUsage1 = new ResearchedUsage();
        researchedUsage1.setUsageId(usageId1);
        researchedUsage1.setSystemTitle("Title1");
        researchedUsage1.setWrWrkInst(987654321L);
        ResearchedUsage researchedUsage2 = new ResearchedUsage();
        researchedUsage2.setUsageId(usageId2);
        researchedUsage2.setSystemTitle("Title2");
        researchedUsage2.setWrWrkInst(876543210L);
        List<ResearchedUsage> researchedUsages = ImmutableList.of(researchedUsage1, researchedUsage2);
        usageRepository.updateResearchedUsages(researchedUsages);
        usageAuditService.logAction(usageId1, UsageActionTypeEnum.WORK_FOUND,
            "Wr Wrk Inst 987654321 was added based on research");
        expectLastCall().once();
        usageAuditService.logAction(usageId2, UsageActionTypeEnum.WORK_FOUND,
            "Wr Wrk Inst 876543210 was added based on research");
        expectLastCall().once();
        List<Usage> usages = Arrays.asList(buildUsage(usageId1), buildUsage(usageId2));
        expect(usageRepository.findByIds(Arrays.asList(usageId1, usageId2)))
            .andReturn(usages)
            .once();
        chainExecutor.execute(usages, ChainProcessorTypeEnum.RIGHTS);
        expectLastCall().once();
        replay(usageRepository, usageAuditService, chainExecutor);
        usageService.loadResearchedUsages(researchedUsages);
        verify(usageRepository, usageAuditService, chainExecutor);
    }

    @Test
    public void testGetUsagesByIds() {
        List<String> usageIds = Collections.singletonList(USAGE_ID_1);
        List<Usage> usages = Collections.singletonList(buildUsage(USAGE_ID_1));
        expect(usageRepository.findByIds(eq(usageIds))).andReturn(usages).once();
        replay(usageRepository);
        assertEquals(usages, usageService.getUsagesByIds(usageIds));
        verify(usageRepository);
    }

    @Test
    public void testGetUsagesByIdsEmptyIds() {
        replay(usageRepository);
        assertEquals(Collections.emptyList(), usageService.getUsagesByIds(Collections.emptyList()));
        verify(usageRepository);
    }

    @Test
    public void testGetUsageIdsByStatusAndProductFamily() {
        List<String> usageIds = Collections.singletonList(USAGE_ID_1);
        expect(usageRepository.findIdsByStatusAndProductFamily(UsageStatusEnum.NEW, FAS_PRODUCT_FAMILY))
            .andReturn(usageIds)
            .once();
        replay(usageRepository);
        assertEquals(usageIds,
            usageService.getUsageIdsByStatusAndProductFamily(UsageStatusEnum.NEW, FAS_PRODUCT_FAMILY));
        verify(usageRepository);
    }

    @Test
    public void testGetUsagesByStatusAndProductFamily() {
        List<Usage> usages = Collections.singletonList(buildUsage(USAGE_ID_1));
        expect(usageRepository.findByStatusAndProductFamily(UsageStatusEnum.NEW, FAS_PRODUCT_FAMILY))
            .andReturn(usages)
            .once();
        replay(usageRepository);
        assertEquals(usages, usageService.getUsagesByStatusAndProductFamily(UsageStatusEnum.NEW, FAS_PRODUCT_FAMILY));
        verify(usageRepository);
    }

    @Test
    public void testIsValidUsagesState() {
        UsageFilter usageFilter = new UsageFilter();
        expect(usageRepository.isValidUsagesState(usageFilter, UsageStatusEnum.WORK_NOT_FOUND)).andReturn(true).once();
        replay(usageRepository);
        usageService.isValidUsagesState(usageFilter, UsageStatusEnum.WORK_NOT_FOUND);
        verify(usageRepository);
    }

    @Test
    public void testUpdateProcessedUsage() {
        String usageId = RupPersistUtils.generateUuid();
        Usage usage = buildUsage(usageId);
        expect(usageRepository.updateProcessedUsage(usage)).andReturn(usageId).once();
        replay(usageRepository);
        usageService.updateProcessedUsage(usage);
        verify(usageRepository);
    }

    @Test
    public void testUpdateProcessedUsageWrongVersion() {
        Usage usage = buildUsage(RupPersistUtils.generateUuid());
        exception.expect(InconsistentUsageStateException.class);
        exception.expectMessage("Usage is in inconsistent state");
        expect(usageRepository.updateProcessedUsage(usage)).andReturn(null).once();
        replay(usageRepository);
        usageService.updateProcessedUsage(usage);
        verify(usageRepository);
    }

    private void assertResult(List<?> result, int size) {
        assertNotNull(result);
        assertEquals(size, result.size());
    }

    private void verifyUsage(Usage usage, BigDecimal grossAmount) {
        assertNotNull(usage);
        assertEquals(grossAmount, usage.getGrossAmount());
        assertEquals(USER_NAME, usage.getCreateUser());
        assertEquals(USER_NAME, usage.getUpdateUser());
    }

    private Usage buildUsage(String usageId) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setWrWrkInst(123160519L);
        usage.getRightsholder().setId(RH_ID);
        usage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        usage.setGrossAmount(new BigDecimal("100.00"));
        usage.setNetAmount(new BigDecimal("68.0000000000"));
        usage.setServiceFeeAmount(new BigDecimal("32.0000000000"));
        usage.setServiceFee(new BigDecimal("0.32"));
        usage.setProductFamily(FAS_PRODUCT_FAMILY);
        return usage;
    }

    private Usage buildUsageWithPayee(String usageId) {
        Usage usage = buildUsage(usageId);
        usage.getPayee().setId(RupPersistUtils.generateUuid());
        usage.getPayee().setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        return usage;
    }

    private PaidUsage buildPaidUsage(UsageStatusEnum status, boolean postDistributionFlag, Boolean splitParentFlag) {
        PaidUsage paidUsage = new PaidUsage();
        paidUsage.setId(USAGE_ID_1);
        paidUsage.setStatus(status);
        paidUsage.setCheckNumber("578945");
        paidUsage.setCccEventId("53256");
        paidUsage.setDistributionName("FDA March 17");
        paidUsage.setGrossAmount(new BigDecimal("50.00"));
        paidUsage.setNetAmount(new BigDecimal("40.00"));
        paidUsage.setServiceFeeAmount(new BigDecimal("10.00"));
        paidUsage.setServiceFee(new BigDecimal("0.32"));
        paidUsage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        paidUsage.setProductFamily(FAS_PRODUCT_FAMILY);
        paidUsage.setWrWrkInst(123160519L);
        paidUsage.setPostDistributionFlag(postDistributionFlag);
        paidUsage.setSplitParentFlag(splitParentFlag);
        paidUsage.setScenarioId("e4a81dff-719b-4f73-bb0d-fcfc23ea2395");
        return paidUsage;
    }

    private PaidUsage buildPaidUsageToInsert(PaidUsage usageFromLm, Usage storedUsage, String usegeId) {
        PaidUsage paidUsage = new PaidUsage();
        paidUsage.setId(usegeId);
        paidUsage.setCheckNumber(usageFromLm.getCheckNumber());
        paidUsage.setCccEventId(usageFromLm.getCccEventId());
        paidUsage.setDistributionName(usageFromLm.getDistributionName());
        paidUsage.setArticle(storedUsage.getArticle());
        paidUsage.setStatus(UsageStatusEnum.PAID);
        paidUsage.getRightsholder().setAccountNumber(usageFromLm.getRightsholder().getAccountNumber());
        paidUsage.setWrWrkInst(storedUsage.getWrWrkInst());
        paidUsage.setProductFamily(storedUsage.getProductFamily());
        paidUsage.setNetAmount(usageFromLm.getNetAmount());
        paidUsage.setServiceFee(usageFromLm.getServiceFee());
        paidUsage.setGrossAmount(usageFromLm.getGrossAmount());
        paidUsage.setServiceFeeAmount(usageFromLm.getServiceFeeAmount());
        return paidUsage;
    }
}
