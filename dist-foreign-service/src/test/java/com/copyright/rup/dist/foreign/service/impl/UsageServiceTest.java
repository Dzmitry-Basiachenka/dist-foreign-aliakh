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
import com.copyright.rup.dist.foreign.domain.RightsholderPayeeProductFamilyHolder;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.common.util.CalculationUtils;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

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
import java.util.Map;
import java.util.Set;

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
@PrepareForTest({RupContextUtils.class, RupPersistUtils.class, CalculationUtils.class})
public class UsageServiceTest {

    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String USAGE_ID_1 = "Usage id 1";
    private static final String USAGE_ID_2 = "Usage id 2";
    private static final String SCENARIO_ID = "78179a10-ad9e-432e-8aae-30b91fd14ed1";
    private static final String PAID_SCENARIO_ID = "e4a81dff-719b-4f73-bb0d-fcfc23ea2395";
    private static final String BATCH_ID = "3e2d710d-8753-4432-ad7b-25e327c97e94";
    private static final String USER_NAME = "User name";
    private static final Long RH_ACCOUNT_NUMBER = 1000001534L;
    private static final Long RH_ACCOUNT_NUMBER_2 = 7000481360L;
    private static final Long PAYEE_ACCOUNT_NUMBER = 12387456L;
    private static final String RH_ID = "9f9d59ce-4e03-41c1-81dc-0577afee2705";
    private static final String RH_ID_2 = "3d2791ed-4d7e-4096-839b-972ac3132e1f";
    private static final String PAYEE_ID = "507d63f2-bb93-499e-8537-78ec185c492a";
    private static final Set<String> RH_IDS = Sets.newHashSet(RH_ID, PAYEE_ID);
    private static final Map<String, Long> IDS_TO_ACCOUNT_NUMBER_MAP =
        ImmutableMap.of(RH_ID, RH_ACCOUNT_NUMBER, PAYEE_ID, PAYEE_ACCOUNT_NUMBER);

    private Scenario scenario;
    private IUsageRepository usageRepository;
    private IUsageArchiveRepository usageArchiveRepository;
    private IUsageAuditService usageAuditService;
    private IScenarioAuditService scenarioAuditService;
    private IRightsholderService rightsholderService;
    private IChainExecutor<Usage> chainExecutor;
    private UsageService usageService;

    @Rule
    private final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setCreateUser(USER_NAME);
        scenario.setProductFamily(FAS_PRODUCT_FAMILY);
        usageRepository = createMock(IUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        usageArchiveRepository = createMock(IUsageArchiveRepository.class);
        scenarioAuditService = createMock(IScenarioAuditService.class);
        rightsholderService = createMock(IRightsholderService.class);
        chainExecutor = createMock(IChainExecutor.class);
        usageService = new UsageService();
        Whitebox.setInternalState(usageService, usageRepository);
        Whitebox.setInternalState(usageService, usageAuditService);
        Whitebox.setInternalState(usageService, usageArchiveRepository);
        Whitebox.setInternalState(usageService, scenarioAuditService);
        Whitebox.setInternalState(usageService, rightsholderService);
        Whitebox.setInternalState(usageService, chainExecutor);
    }

    @Test
    public void testDeleteUsageBatchDetails() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(BATCH_ID);
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
        expectLastCall().once();
        replay(usageRepository, usageAuditService);
        usageService.deleteById(usageId);
        verify(usageRepository, usageAuditService);
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
    public void testGetInvalidRightsholdersByFilter() {
        UsageFilter filter = new UsageFilter();
        List<Long> accountNumbers = Arrays.asList(1000000001L, 1000000002L);
        expect(usageRepository.findInvalidRightsholdersByFilter(filter)).andReturn(accountNumbers).once();
        replay(usageRepository);
        assertSame(accountNumbers, usageService.getInvalidRightsholdersByFilter(filter));
        verify(usageRepository);
    }

    @Test
    public void testGetRightsholderTotalsHoldersBySentToLmScenario() {
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            Collections.singletonList(new RightsholderTotalsHolder());
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
        List<UsageDto> usages = Arrays.asList(new UsageDto(), new UsageDto());
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
        List<String> usagesIds = Arrays.asList(RupPersistUtils.generateUuid(), RupPersistUtils.generateUuid());
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
        usageService.deleteFromScenario(scenario.getId(), 2000017011L, accountNumbers, "Action reason");
        verify(usageRepository, usageAuditService, RupContextUtils.class);
    }

    @Test
    public void testGetCountForAudit() {
        AuditFilter filter = new AuditFilter();
        expect(usageRepository.findCountForAudit(filter)).andReturn(1).once();
        replay(usageRepository);
        assertEquals(1, usageService.getCountForAudit(filter));
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
        PaidUsage paidUsage = buildPaidUsage(UsageStatusEnum.PAID, false, null);
        expect(rightsholderService.findAccountNumbersByRightsholderIds(RH_IDS))
            .andReturn(IDS_TO_ACCOUNT_NUMBER_MAP).once();
        expect(usageArchiveRepository.findByIds(ImmutableList.of(USAGE_ID_1)))
            .andReturn(ImmutableList.of(usage))
            .once();
        paidUsage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        paidUsage.getPayee().setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        usageArchiveRepository.updatePaidInfo(paidUsage);
        expectLastCall().once();
        usageAuditService.logAction(USAGE_ID_1, UsageActionTypeEnum.PAID,
            "Usage has been paid according to information from the LM");
        expectLastCall().once();
        replay(usageArchiveRepository, usageAuditService, rightsholderService);
        usageService.updatePaidInfo(Collections.singletonList(usage));
        verify(usageArchiveRepository, usageAuditService, rightsholderService);
    }

    @Test
    public void testUpdatePaidInfoWithRightsholderChange() {
        PaidUsage oldUsage = buildPaidUsage(UsageStatusEnum.SENT_TO_LM, false, null);
        PaidUsage newUsage = buildPaidUsage(UsageStatusEnum.SENT_TO_LM, false, null);
        newUsage.getRightsholder().setId(RH_ID_2);
        newUsage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER_2);
        expect(rightsholderService.findAccountNumbersByRightsholderIds(Sets.newHashSet(RH_ID_2, PAYEE_ID)))
            .andReturn(ImmutableMap.of(RH_ID_2, RH_ACCOUNT_NUMBER_2, PAYEE_ID, PAYEE_ACCOUNT_NUMBER)).once();
        expect(usageArchiveRepository.findByIds(ImmutableList.of(USAGE_ID_1)))
            .andReturn(ImmutableList.of(oldUsage))
            .once();
        PaidUsage paidUsage = buildPaidUsage(UsageStatusEnum.PAID, false, null);
        paidUsage.getRightsholder().setId(RH_ID_2);
        paidUsage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER_2);
        paidUsage.getPayee().setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        usageArchiveRepository.updatePaidInfo(paidUsage);
        expectLastCall().once();
        usageAuditService.logAction(USAGE_ID_1, UsageActionTypeEnum.PAID,
            "Usage has been paid according to information from the LM" +
                " with RH change from 1000001534 to 7000481360");
        expectLastCall().once();
        replay(usageArchiveRepository, usageAuditService, rightsholderService);
        usageService.updatePaidInfo(Collections.singletonList(newUsage));
        verify(usageArchiveRepository, usageAuditService, rightsholderService);
    }

    @Test
    public void testUpdatePaidInfoSplitOriginalDetail() {
        PaidUsage usage = buildPaidUsage(UsageStatusEnum.SENT_TO_LM, false, true);
        PaidUsage paidUsage = buildPaidUsage(UsageStatusEnum.PAID, false, true);
        expect(rightsholderService.findAccountNumbersByRightsholderIds(RH_IDS))
            .andReturn(IDS_TO_ACCOUNT_NUMBER_MAP).once();
        expect(usageArchiveRepository.findByIds(ImmutableList.of(USAGE_ID_1)))
            .andReturn(ImmutableList.of(usage))
            .once();
        paidUsage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        paidUsage.getPayee().setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        usageArchiveRepository.updatePaidInfo(paidUsage);
        expectLastCall().once();
        expect(scenarioAuditService.isAuditItemExist(PAID_SCENARIO_ID, ScenarioActionTypeEnum.UPDATED_AFTER_SPLIT))
            .andReturn(false).once();
        usageAuditService.logAction(USAGE_ID_1, UsageActionTypeEnum.PAID,
            "Usage has been adjusted based on Split process");
        expectLastCall().once();
        scenarioAuditService.logAction(PAID_SCENARIO_ID, ScenarioActionTypeEnum.UPDATED_AFTER_SPLIT,
            "Scenario has been updated after Split process");
        expectLastCall().once();
        replay(usageArchiveRepository, usageAuditService, scenarioAuditService, rightsholderService);
        usageService.updatePaidInfo(Collections.singletonList(usage));
        verify(usageArchiveRepository, usageAuditService, scenarioAuditService, rightsholderService);
    }

    @Test
    public void testUpdatePaidInfoSplitOriginalDetailWithExistingScenarioAudit() {
        PaidUsage usage = buildPaidUsage(UsageStatusEnum.SENT_TO_LM, false, true);
        PaidUsage paidUsage = buildPaidUsage(UsageStatusEnum.PAID, false, true);
        expect(rightsholderService.findAccountNumbersByRightsholderIds(RH_IDS))
            .andReturn(IDS_TO_ACCOUNT_NUMBER_MAP).once();
        expect(usageArchiveRepository.findByIds(ImmutableList.of(USAGE_ID_1)))
            .andReturn(ImmutableList.of(usage))
            .once();
        paidUsage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        paidUsage.getPayee().setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        usageArchiveRepository.updatePaidInfo(paidUsage);
        expectLastCall().once();
        expect(scenarioAuditService.isAuditItemExist(PAID_SCENARIO_ID, ScenarioActionTypeEnum.UPDATED_AFTER_SPLIT))
            .andReturn(true).once();
        usageAuditService.logAction(USAGE_ID_1, UsageActionTypeEnum.PAID,
            "Usage has been adjusted based on Split process");
        expectLastCall().once();
        replay(usageArchiveRepository, usageAuditService, scenarioAuditService, rightsholderService);
        usageService.updatePaidInfo(Collections.singletonList(usage));
        verify(usageArchiveRepository, usageAuditService, scenarioAuditService, rightsholderService);
    }

    @Test
    public void testUpdatePaidInfoSplitOriginalDetailWithRightsholderChange() {
        PaidUsage oldUsage = buildPaidUsage(UsageStatusEnum.SENT_TO_LM, false, true);
        PaidUsage newUsage = buildPaidUsage(UsageStatusEnum.SENT_TO_LM, false, true);
        newUsage.getRightsholder().setId(RH_ID_2);
        newUsage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER_2);
        expect(rightsholderService.findAccountNumbersByRightsholderIds(Sets.newHashSet(RH_ID_2, PAYEE_ID)))
            .andReturn(ImmutableMap.of(RH_ID_2, RH_ACCOUNT_NUMBER_2, PAYEE_ID, PAYEE_ACCOUNT_NUMBER)).once();
        expect(usageArchiveRepository.findByIds(ImmutableList.of(USAGE_ID_1)))
            .andReturn(ImmutableList.of(oldUsage))
            .once();
        PaidUsage paidUsage = buildPaidUsage(UsageStatusEnum.PAID, false, true);
        paidUsage.getRightsholder().setId(RH_ID_2);
        paidUsage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER_2);
        paidUsage.getPayee().setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        usageArchiveRepository.updatePaidInfo(paidUsage);
        expectLastCall().once();
        expect(scenarioAuditService.isAuditItemExist(PAID_SCENARIO_ID, ScenarioActionTypeEnum.UPDATED_AFTER_SPLIT))
            .andReturn(false).once();
        usageAuditService.logAction(USAGE_ID_1, UsageActionTypeEnum.PAID,
            "Usage has been adjusted based on Split process with RH change from 1000001534 to 7000481360");
        expectLastCall().once();
        scenarioAuditService.logAction(PAID_SCENARIO_ID, ScenarioActionTypeEnum.UPDATED_AFTER_SPLIT,
            "Scenario has been updated after Split process");
        expectLastCall().once();
        replay(usageArchiveRepository, usageAuditService, scenarioAuditService, rightsholderService);
        usageService.updatePaidInfo(Collections.singletonList(newUsage));
        verify(usageArchiveRepository, usageAuditService, scenarioAuditService, rightsholderService);
    }

    @Test
    public void testUpdatePaidInfoSplitNewDetail() {
        mockStatic(RupPersistUtils.class);
        PaidUsage usageFromLm = buildPaidUsage(null, false, false);
        Usage storedUsage = buildUsage(USAGE_ID_1);
        expect(rightsholderService.findAccountNumbersByRightsholderIds(RH_IDS))
            .andReturn(IDS_TO_ACCOUNT_NUMBER_MAP).once();
        expect(usageArchiveRepository.findByIds(ImmutableList.of(USAGE_ID_1)))
            .andReturn(ImmutableList.of(storedUsage))
            .once();
        String newUsageId = "93c24edf-bc4d-406e-bd54-9a7379a6e532";
        expect(RupPersistUtils.generateUuid())
            .andReturn(newUsageId)
            .once();
        usageFromLm.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        usageFromLm.getPayee().setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        usageArchiveRepository.insertPaid(buildPaidUsageToInsert(usageFromLm, storedUsage, newUsageId));
        expectLastCall().once();
        usageAuditService.logAction(newUsageId, UsageActionTypeEnum.PAID,
            "Usage has been created based on Split process");
        expectLastCall().once();
        replay(usageArchiveRepository, usageAuditService, rightsholderService, RupPersistUtils.class);
        usageService.updatePaidInfo(Collections.singletonList(usageFromLm));
        verify(usageArchiveRepository, usageAuditService, rightsholderService, RupPersistUtils.class);
    }

    @Test
    public void testUpdatePostDistributionPaidInfo() {
        mockStatic(RupPersistUtils.class);
        PaidUsage usageFromLm = buildPaidUsage(null, true, null);
        Usage storedUsage = buildUsage(USAGE_ID_1);
        expect(rightsholderService.findAccountNumbersByRightsholderIds(RH_IDS))
            .andReturn(IDS_TO_ACCOUNT_NUMBER_MAP).once();
        expect(usageArchiveRepository.findByIds(ImmutableList.of(USAGE_ID_1)))
            .andReturn(ImmutableList.of(storedUsage))
            .once();
        String newUsageId = "2a1aa09c-0de7-49f7-a41e-f2a05c7bb43f";
        expect(RupPersistUtils.generateUuid())
            .andReturn(newUsageId)
            .once();
        usageFromLm.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        usageFromLm.getPayee().setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        usageArchiveRepository.insertPaid(buildPaidUsageToInsert(usageFromLm, storedUsage, newUsageId));
        expectLastCall().once();
        usageAuditService.logAction(newUsageId, UsageActionTypeEnum.PAID,
            "Usage has been created based on Post-Distribution process");
        expectLastCall().once();
        replay(usageArchiveRepository, usageAuditService, rightsholderService, RupPersistUtils.class);
        usageService.updatePaidInfo(Collections.singletonList(usageFromLm));
        verify(usageArchiveRepository, usageAuditService, rightsholderService, RupPersistUtils.class);
    }

    @Test
    public void testUpdatePostDistributionPaidInfoSplitOriginalUsage() {
        mockStatic(RupPersistUtils.class);
        PaidUsage usageFromLm = buildPaidUsage(null, true, true);
        Usage storedUsage = buildUsage(USAGE_ID_1);
        expect(rightsholderService.findAccountNumbersByRightsholderIds(RH_IDS))
            .andReturn(IDS_TO_ACCOUNT_NUMBER_MAP).once();
        expect(usageArchiveRepository.findByIds(ImmutableList.of(USAGE_ID_1)))
            .andReturn(ImmutableList.of(storedUsage))
            .once();
        String newUsageId = "93c24edf-bc4d-406e-bd54-9a7379a6e532";
        expect(RupPersistUtils.generateUuid())
            .andReturn(newUsageId)
            .once();
        usageFromLm.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        usageFromLm.getPayee().setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        usageArchiveRepository.insertPaid(buildPaidUsageToInsert(usageFromLm, storedUsage, newUsageId));
        expectLastCall().once();
        usageAuditService.logAction(newUsageId, UsageActionTypeEnum.PAID,
            "Usage has been created based on Post-Distribution and Split processes");
        expectLastCall().once();
        replay(usageArchiveRepository, usageAuditService, rightsholderService, RupPersistUtils.class);
        usageService.updatePaidInfo(Collections.singletonList(usageFromLm));
        verify(usageArchiveRepository, usageAuditService, rightsholderService, RupPersistUtils.class);
    }

    @Test
    public void testUpdatePostDistributionPaidInfoSplitNewUsage() {
        mockStatic(RupPersistUtils.class);
        PaidUsage usageFromLm = buildPaidUsage(null, true, false);
        Usage storedUsage = buildUsage(USAGE_ID_1);
        expect(rightsholderService.findAccountNumbersByRightsholderIds(RH_IDS))
            .andReturn(IDS_TO_ACCOUNT_NUMBER_MAP).once();
        expect(usageArchiveRepository.findByIds(ImmutableList.of(USAGE_ID_1)))
            .andReturn(ImmutableList.of(storedUsage))
            .once();
        String newUsageId = "ef671fd5-6610-450e-9326-255a48230e3b";
        expect(RupPersistUtils.generateUuid())
            .andReturn(newUsageId)
            .once();
        usageFromLm.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        usageFromLm.getPayee().setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        usageArchiveRepository.insertPaid(buildPaidUsageToInsert(usageFromLm, storedUsage, newUsageId));
        expectLastCall().once();
        usageAuditService.logAction(newUsageId, UsageActionTypeEnum.PAID,
            "Usage has been created based on Post-Distribution and Split processes");
        expectLastCall().once();
        replay(usageArchiveRepository, usageAuditService, rightsholderService, RupPersistUtils.class);
        usageService.updatePaidInfo(Collections.singletonList(usageFromLm));
        verify(usageArchiveRepository, usageAuditService, rightsholderService, RupPersistUtils.class);
    }

    @Test
    public void testUpdatePaidInfoNotFoundUsage() {
        PaidUsage paidUsage = new PaidUsage();
        paidUsage.setId(USAGE_ID_1);
        expect(usageArchiveRepository.findByIds(ImmutableList.of(USAGE_ID_1)))
            .andReturn(Collections.emptyList()).once();
        replay(usageArchiveRepository, rightsholderService);
        usageService.updatePaidInfo(Collections.singletonList(paidUsage));
        verify(usageArchiveRepository, rightsholderService);
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
    public void testGetArchivedUsagesForSendToLmByIds() {
        List<String> usageIds = Collections.singletonList(USAGE_ID_1);
        List<Usage> usages = Collections.singletonList(buildUsage(USAGE_ID_1));
        expect(usageArchiveRepository.findForSendToLmByIds(eq(usageIds))).andReturn(usages).once();
        replay(usageArchiveRepository);
        assertEquals(usages, usageService.getArchivedUsagesForSendToLmByIds(usageIds));
        verify(usageArchiveRepository);
    }

    @Test
    public void testGetArchivedUsagesForSendToLmByIdsEmptyIds() {
        replay(usageArchiveRepository);
        assertEquals(Collections.emptyList(), usageService.getArchivedUsagesForSendToLmByIds(Collections.emptyList()));
        verify(usageArchiveRepository);
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
    public void testIsValidFilteredUsageStatus() {
        UsageFilter usageFilter = new UsageFilter();
        expect(usageRepository.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.WORK_NOT_FOUND))
            .andReturn(true).once();
        replay(usageRepository);
        usageService.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.WORK_NOT_FOUND);
        verify(usageRepository);
    }

    @Test
    public void testUpdateStatusAndRhAccountNumber() {
        Set<String> ids = Sets.newHashSet(USAGE_ID_1, USAGE_ID_2);
        usageRepository.updateStatusAndRhAccountNumber(ids, UsageStatusEnum.ELIGIBLE, RH_ACCOUNT_NUMBER);
        expectLastCall().once();
        replay(usageRepository);
        usageRepository.updateStatusAndRhAccountNumber(ids, UsageStatusEnum.ELIGIBLE, RH_ACCOUNT_NUMBER);
        verify(usageRepository);
    }

    @Test
    public void testUpdateProcessedUsage() {
        String usageId = "3d1f1d34-c307-42be-99d2-075dbcdb7838";
        Usage usage = buildUsage(usageId);
        expect(usageRepository.updateProcessedUsage(usage)).andReturn(usageId).once();
        replay(usageRepository);
        usageService.updateProcessedUsage(usage);
        verify(usageRepository);
    }

    @Test
    public void testUpdateProcessedUsageWrongVersion() {
        Usage usage = buildUsage("ca62ea7e-4185-4c56-b12b-c53fbad1d6b8");
        usage.setStatus(UsageStatusEnum.US_TAX_COUNTRY);
        usage.setVersion(2);
        exception.expect(InconsistentUsageStateException.class);
        exception.expectMessage("Usage is in inconsistent state. UsageId=ca62ea7e-4185-4c56-b12b-c53fbad1d6b8," +
            " Status=US_TAX_COUNTRY, RecordVersion=2");
        expect(usageRepository.updateProcessedUsage(usage)).andReturn(null).once();
        replay(usageRepository);
        usageService.updateProcessedUsage(usage);
        verify(usageRepository);
    }

    @Test
    public void testGetWrWrkInstToUsageIdsForRightsAssignment() {
        expect(usageRepository.findWrWrkInstToUsageIdsByBatchNameAndUsageStatus("FAS Distribution 05/07/2018",
            UsageStatusEnum.RH_NOT_FOUND)).andReturn(null).once();
        replay(usageRepository);
        usageService.getWrWrkInstToUsageIdsForRightsAssignment("FAS Distribution 05/07/2018");
        verify(usageRepository);
    }

    @Test
    public void testSendForMatching() {
        Usage usage1 = new Usage();
        usage1.setStatus(UsageStatusEnum.NEW);
        Usage usage2 = new Usage();
        usage2.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
        Capture<Runnable> captureRunnable = new Capture<>();
        chainExecutor.execute(capture(captureRunnable));
        expectLastCall().once();
        chainExecutor.execute(Collections.singletonList(usage1), ChainProcessorTypeEnum.MATCHING);
        expectLastCall().once();
        replay(chainExecutor);
        usageService.sendForMatching(Arrays.asList(usage1, usage2));
        assertNotNull(captureRunnable);
        Runnable runnable = captureRunnable.getValue();
        assertNotNull(runnable);
        runnable.run();
        verify(chainExecutor);
    }

    @Test
    public void testSendForGettingRights() {
        Usage usage1 = new Usage();
        usage1.setStatus(UsageStatusEnum.WORK_FOUND);
        Usage usage2 = new Usage();
        usage2.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
        Capture<Runnable> captureRunnable = new Capture<>();
        chainExecutor.execute(capture(captureRunnable));
        expectLastCall().once();
        chainExecutor.execute(Collections.singletonList(usage1), ChainProcessorTypeEnum.RIGHTS);
        expectLastCall().once();
        replay(chainExecutor);
        usageService.sendForGettingRights(Arrays.asList(usage1, usage2), "Batch name");
        assertNotNull(captureRunnable);
        Runnable runnable = captureRunnable.getValue();
        assertNotNull(runnable);
        runnable.run();
        verify(chainExecutor);
    }

    @Test
    public void testGetRightsholderPayeeProductFamilyHoldersByScenarioIds() {
        Set<String> scenarioIds = Collections.singleton(SCENARIO_ID);
        List<RightsholderPayeeProductFamilyHolder> holders =
            Collections.singletonList(new RightsholderPayeeProductFamilyHolder());
        expect(usageRepository.findRightsholderPayeeProductFamilyHoldersByScenarioIds(scenarioIds))
            .andReturn(holders).once();
        replay(usageRepository);
        assertEquals(holders, usageService.getRightsholderPayeeProductFamilyHoldersByScenarioIds(scenarioIds));
        verify(usageRepository);
    }

    private void assertResult(List<?> result, int size) {
        assertNotNull(result);
        assertEquals(size, result.size());
    }

    private Usage buildUsage(String usageId) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setBatchId(BATCH_ID);
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

    private PaidUsage buildPaidUsage(UsageStatusEnum status, boolean postDistributionFlag, Boolean splitParentFlag) {
        PaidUsage paidUsage = new PaidUsage();
        paidUsage.setId(USAGE_ID_1);
        paidUsage.setBatchId(BATCH_ID);
        paidUsage.setStatus(status);
        paidUsage.setCheckNumber("578945");
        paidUsage.setCccEventId("53256");
        paidUsage.setDistributionName("FDA March 17");
        paidUsage.setGrossAmount(new BigDecimal("50.00"));
        paidUsage.setNetAmount(new BigDecimal("40.00"));
        paidUsage.setServiceFeeAmount(new BigDecimal("10.00"));
        paidUsage.setServiceFee(new BigDecimal("0.32"));
        paidUsage.getRightsholder().setId(RH_ID);
        paidUsage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        paidUsage.getPayee().setId(PAYEE_ID);
        paidUsage.getPayee().setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        paidUsage.setProductFamily(FAS_PRODUCT_FAMILY);
        paidUsage.setWrWrkInst(123160519L);
        paidUsage.setPostDistributionFlag(postDistributionFlag);
        paidUsage.setSplitParentFlag(splitParentFlag);
        paidUsage.setScenarioId(PAID_SCENARIO_ID);
        return paidUsage;
    }

    private PaidUsage buildPaidUsageToInsert(PaidUsage usageFromLm, Usage storedUsage, String usegeId) {
        PaidUsage paidUsage = new PaidUsage();
        paidUsage.setId(usegeId);
        paidUsage.setBatchId(storedUsage.getBatchId());
        paidUsage.setCheckNumber(usageFromLm.getCheckNumber());
        paidUsage.setCccEventId(usageFromLm.getCccEventId());
        paidUsage.setDistributionName(usageFromLm.getDistributionName());
        paidUsage.setArticle(storedUsage.getArticle());
        paidUsage.setStatus(UsageStatusEnum.PAID);
        paidUsage.getRightsholder().setAccountNumber(usageFromLm.getRightsholder().getAccountNumber());
        paidUsage.getRightsholder().setId(usageFromLm.getRightsholder().getId());
        paidUsage.getPayee().setAccountNumber(usageFromLm.getPayee().getAccountNumber());
        paidUsage.getPayee().setId(usageFromLm.getPayee().getId());
        paidUsage.setWrWrkInst(storedUsage.getWrWrkInst());
        paidUsage.setProductFamily(storedUsage.getProductFamily());
        paidUsage.setNetAmount(usageFromLm.getNetAmount());
        paidUsage.setServiceFee(usageFromLm.getServiceFee());
        paidUsage.setGrossAmount(usageFromLm.getGrossAmount());
        paidUsage.setServiceFeeAmount(usageFromLm.getServiceFeeAmount());
        return paidUsage;
    }
}
