package com.copyright.rup.dist.foreign.service.impl.fas;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IFasUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import org.apache.commons.collections4.CollectionUtils;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Verifies {@link FasUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/23/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class})
public class FasUsageServiceTest {

    private static final String REASON = "reason";
    private static final String USER_NAME = "SYSTEM";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final Long PAYEE_ACCOUNT_NUMBER = 1000023401L;
    private static final String RH_ID = "31d3e671-ad3f-4277-81fd-a52958dc0fbe";

    private final FasUsageService usageService = new FasUsageService();
    private IUsageRepository usageRepository;
    private IFasUsageRepository fasUsageRepository;
    private IUsageArchiveRepository usageArchiveRepository;
    private IUsageAuditService usageAuditService;
    private IChainExecutor<Usage> chainExecutor;
    private IPiIntegrationService piIntegrationService;
    private IPrmIntegrationService prmIntegrationService;
    private IRightsholderService rightsholderService;
    private Scenario scenario;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        scenario = new Scenario();
        scenario.setId("78179a10-ad9e-432e-8aae-30b91fd14ed1");
        scenario.setCreateUser("user@copyright.com");
        scenario.setProductFamily("FAS");
        usageRepository = createMock(IUsageRepository.class);
        usageArchiveRepository = createMock(IUsageArchiveRepository.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        fasUsageRepository = createMock(IFasUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        chainExecutor = createMock(IChainExecutor.class);
        piIntegrationService = createMock(IPiIntegrationService.class);
        rightsholderService = EasyMock.createMock(IRightsholderService.class);
        Whitebox.setInternalState(usageService, usageRepository);
        Whitebox.setInternalState(usageService, usageArchiveRepository);
        Whitebox.setInternalState(usageService, fasUsageRepository);
        Whitebox.setInternalState(usageService, usageAuditService);
        Whitebox.setInternalState(usageService, piIntegrationService);
        Whitebox.setInternalState(usageService, prmIntegrationService);
        Whitebox.setInternalState(usageService, rightsholderService);
        Whitebox.setInternalState(usageService, chainExecutor);
        Whitebox.setInternalState(usageService, "claAccountNumber", 2000017000L);
    }

    @Test
    public void testUpdateNtsWithdrawnUsagesAndGetIds() {
        List<String> ids = List.of("55e6ab53-3fb2-494f-8cb2-94ebf8a9f8d3");
        expect(fasUsageRepository.updateNtsWithdrawnUsagesAndGetIds()).andReturn(ids).once();
        replay(fasUsageRepository);
        assertEquals(ids, fasUsageRepository.updateNtsWithdrawnUsagesAndGetIds());
        verify(fasUsageRepository);
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
    public void testGetUsagesForReconcile() {
        List<Usage> usages = List.of(new Usage());
        expect(fasUsageRepository.findForReconcile(scenario.getId())).andReturn(usages).once();
        replay(fasUsageRepository);
        assertSame(usages, usageService.getUsagesForReconcile(scenario.getId()));
        verify(fasUsageRepository);
    }

    @Test
    public void testRecalculateUsagesForRefresh() {
        UsageFilter filter = new UsageFilter();
        Usage usage1 = buildUsage("f74d01fd-560e-4a38-ba62-8c74667f25e5");
        Usage usage2 = buildUsageWithPayee("314157b6-c497-4b64-9a8f-a9e485eed79f");
        expect(fasUsageRepository.findRightsholdersInformation(scenario.getId()))
            .andReturn(ImmutableMap.of(usage2.getRightsholder().getAccountNumber(), usage2)).once();
        expect(fasUsageRepository.findWithAmountsAndRightsholders(filter)).andReturn(List.of(usage1)).once();
        expect(prmIntegrationService.getRollUps(Set.of())).andReturn(Map.of()).once();
        expect(prmIntegrationService.getPreferences(Set.of())).andReturn(Map.of()).once();
        expect(prmIntegrationService.getRhParticipatingServiceFee(false)).andReturn(new BigDecimal("0.32")).once();
        usageRepository.addToScenario(List.of(usage1));
        expectLastCall().once();
        rightsholderService.updateUsagesPayeesAsync(List.of(usage1));
        expectLastCall().once();
        replay(usageRepository, prmIntegrationService, rightsholderService, fasUsageRepository);
        usageService.recalculateUsagesForRefresh(filter, scenario);
        verify(usageRepository, prmIntegrationService, rightsholderService, fasUsageRepository);
    }

    @Test
    public void testAddUsagesToScenario() {
        Usage usage1 = buildUsage("d0167f38-e707-43c2-89a7-09abbf558548");
        Usage usage2 = buildUsage("5e4d4020-7066-4a36-bfe2-9efd710287ae");
        List<Usage> usages = Arrays.asList(usage1, usage2);
        Map<String, Map<String, Rightsholder>> rollUps = new HashMap<>();
        Rightsholder payee = new Rightsholder();
        payee.setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        rollUps.put(RH_ID, ImmutableMap.of(FAS_PRODUCT_FAMILY, payee));
        Map<String, Table<String, String, Object>> preferences = new HashMap<>();
        expect(prmIntegrationService.getRollUps(Set.of(RH_ID))).andReturn(rollUps).once();
        expect(prmIntegrationService.getPreferences(Sets.newHashSet(payee.getId(), RH_ID)))
            .andReturn(preferences).once();
        usageRepository.addToScenario(usages);
        expectLastCall().once();
        expect(prmIntegrationService.isRightsholderParticipating(preferences, usage1.getRightsholder().getId(),
            usage1.getProductFamily())).andReturn(true).once();
        expect(prmIntegrationService.isRightsholderParticipating(preferences, usage2.getRightsholder().getId(),
            usage2.getProductFamily())).andReturn(true).once();
        expect(prmIntegrationService.isRightsholderParticipating(preferences, payee.getId(), FAS_PRODUCT_FAMILY))
            .andReturn(true).times(2);
        expect(prmIntegrationService.getRhParticipatingServiceFee(true))
            .andReturn(new BigDecimal("0.16000")).times(2);
        rightsholderService.updateUsagesPayeesAsync(Arrays.asList(usage1, usage2));
        expectLastCall().once();
        replay(usageRepository, prmIntegrationService, rightsholderService);
        usageService.addUsagesToScenario(Arrays.asList(usage1, usage2), scenario);
        verify(usageRepository, prmIntegrationService, rightsholderService);
    }

    @Test
    public void testGetUsagesWithAmounts() {
        UsageFilter usageFilter = new UsageFilter();
        Usage usage = buildUsage("0f6c7f4d-b31b-48e6-a851-c02a0ce16c2d");
        expect(fasUsageRepository.findWithAmountsAndRightsholders(usageFilter)).andReturn(List.of(usage)).once();
        replay(fasUsageRepository);
        assertTrue(CollectionUtils.isNotEmpty(usageService.getUsagesWithAmounts(usageFilter)));
        verify(fasUsageRepository);
    }

    @Test
    public void testGetUsageDtos() {
        List<UsageDto> usagesWithBatch = List.of(new UsageDto());
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
    public void testDeleteFromScenarioByPayees() {
        Set<String> usageIds =
            Sets.newHashSet("095eaefe-37de-4adb-928e-be0b888094b9", "7ee9c1ab-57ca-45cf-8a8d-83d7baeb6a9c");
        Set<Long> accountNumbers = Sets.newHashSet(2000017001L, 2000078999L);
        Set<String> scenarioIds = Set.of(scenario.getId());
        expect(fasUsageRepository.deleteFromScenarioByPayees(scenarioIds, accountNumbers, USER_NAME))
            .andReturn(usageIds).once();
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, REASON);
        expectLastCall().once();
        replay(fasUsageRepository, usageAuditService);
        usageService.deleteFromScenarioByPayees(scenarioIds, accountNumbers, REASON);
        verify(fasUsageRepository, usageAuditService);
    }

    @Test
    public void testRedisignateToNtsWithdrawnByPayees() {
        Set<String> usageIds =
            Sets.newHashSet("2db4877e-e89c-40f3-8c9a-21d8903fac88", "4a324301-d4a4-4639-ac4f-8a75d86b6048");
        Set<Long> accountNumbers = Sets.newHashSet(2000017001L, 2000078999L);
        Set<String> scenarioIds = Set.of(scenario.getId());
        expect(fasUsageRepository.redesignateToNtsWithdrawnByPayees(scenarioIds, accountNumbers, USER_NAME))
            .andReturn(usageIds).once();
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, REASON);
        expectLastCall().once();
        replay(fasUsageRepository, usageAuditService);
        usageService.redesignateToNtsWithdrawnByPayees(scenarioIds, accountNumbers, REASON);
        verify(fasUsageRepository, usageAuditService);
    }

    @Test
    public void testGetAccountNumbersInvalidForExclude() {
        Set<String> scenarioIds = Set.of(scenario.getId());
        Set<Long> payeeAccountNumbers = Set.of(PAYEE_ACCOUNT_NUMBER);
        expect(fasUsageRepository.findAccountNumbersInvalidForExclude(scenarioIds, payeeAccountNumbers))
            .andReturn(payeeAccountNumbers).once();
        replay(fasUsageRepository);
        assertSame(payeeAccountNumbers,
            usageService.getAccountNumbersInvalidForExclude(scenarioIds, payeeAccountNumbers));
        verify(fasUsageRepository);
    }

    @Test
    public void testLoadResearchedUsages() {
        String usageId1 = "721ca627-09bc-4204-99f4-6acae415fa5d";
        String usageId2 = "9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a";
        ResearchedUsage researchedUsage1 = buildResearchedUsage(usageId1, "Title1", "742354894", 987654321L);
        ResearchedUsage researchedUsage2 = buildResearchedUsage(usageId2, "Title2", "879456165", 876543210L);
        List<ResearchedUsage> researchedUsages = List.of(researchedUsage1, researchedUsage2);
        expect(piIntegrationService.findWorkByWrWrkInst(987654321L)).andReturn(buildWork("VALISSN")).once();
        expect(piIntegrationService.findWorkByWrWrkInst(876543210L)).andReturn(new Work()).once();
        fasUsageRepository.updateResearchedUsages(researchedUsages);
        expectLastCall().once();
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
        replay(usageRepository, usageAuditService, chainExecutor, piIntegrationService, fasUsageRepository);
        usageService.loadResearchedUsages(researchedUsages);
        assertEquals("VALISSN", researchedUsage1.getStandardNumberType());
        assertNull(researchedUsage2.getStandardNumberType());
        verify(usageRepository, usageAuditService, chainExecutor, piIntegrationService, fasUsageRepository);
    }

    @Test
    public void testMoveToArchivedFas() {
        mockStatic(RupContextUtils.class);
        List<String> usageIds = List.of(RupPersistUtils.generateUuid());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(usageArchiveRepository.copyToArchiveByScenarioId(scenario.getId(), USER_NAME))
            .andReturn(usageIds).once();
        usageRepository.deleteByScenarioId(scenario.getId());
        expectLastCall().once();
        replay(usageRepository, usageArchiveRepository, RupContextUtils.class);
        usageService.moveToArchive(scenario);
        verify(usageRepository, usageArchiveRepository, RupContextUtils.class);
    }

    @Test
    public void testClaAccountNumber() {
        assertEquals(2000017000L, usageService.getClaAccountNumber(), 0);
    }

    private Work buildWork(String standardNumberType) {
        Work work = new Work();
        work.setMainIdnoType(standardNumberType);
        return work;
    }

    private Usage buildUsage(String usageId) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setWrWrkInst(123160519L);
        usage.getRightsholder().setId(RH_ID);
        usage.getRightsholder().setAccountNumber(1000001534L);
        usage.setGrossAmount(new BigDecimal("100.00"));
        usage.setNetAmount(new BigDecimal("68.0000000000"));
        usage.setServiceFeeAmount(new BigDecimal("32.0000000000"));
        usage.setServiceFee(new BigDecimal("0.32"));
        usage.setProductFamily("FAS");
        return usage;
    }

    private Usage buildUsageWithPayee(String usageId) {
        Usage usage = buildUsage(usageId);
        usage.getPayee().setId("0b54122c-07f9-4c97-9a50-8bb2abd5b19e");
        usage.getPayee().setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        return usage;
    }

    private ResearchedUsage buildResearchedUsage(String usageId, String title, String standardNumber, Long wrWrkInst) {
        ResearchedUsage researchedUsage = new ResearchedUsage();
        researchedUsage.setUsageId(usageId);
        researchedUsage.setSystemTitle(title);
        researchedUsage.setStandardNumber(standardNumber);
        researchedUsage.setWrWrkInst(wrWrkInst);
        return researchedUsage;
    }
}
