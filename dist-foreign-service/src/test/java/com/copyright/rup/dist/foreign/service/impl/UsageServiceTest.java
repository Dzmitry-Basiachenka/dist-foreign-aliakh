package com.copyright.rup.dist.foreign.service.impl;

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

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.AuditFilter;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.integration.rms.api.IRmsIntegrationService;
import com.copyright.rup.dist.foreign.integration.rms.api.RightsAssignmentResult;
import com.copyright.rup.dist.foreign.integration.rms.api.RightsAssignmentResult.RightsAssignmentResultStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.PipedOutputStream;
import java.math.BigDecimal;
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
@PrepareForTest(RupContextUtils.class)
public class UsageServiceTest {

    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String USAGE_ID_1 = "Usage id 1";
    private static final String USAGE_ID_2 = "Usage id 2";
    private static final String USAGE_ID_3 = "Usage id 3";
    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private static final String USER_NAME = "User name";
    private static final Long RH_ACCOUNT_NUMBER = 1000001534L;
    private static final String ACTION_REASON_1 =
        "Detail was made eligible for NTS because sum of gross amounts, grouped by standard number, is less than $100";
    private static final String ACTION_REASON_2 =
        "Detail was made eligible for NTS because sum of gross amounts, grouped by work title, is less than $100";
    private static final String ACTION_REASON_3 =
        "Detail was made eligible for NTS because gross amount is less than $100";
    private static final String USAGE_ID = "usageId";
    private static final String STANDARD_NUMBER = "standardNumber";
    private static final String WORK_TITLE = "workTitle";
    private Scenario scenario;
    private IUsageRepository usageRepository;
    private IUsageArchiveRepository usageArchiveRepository;
    private IUsageAuditService usageAuditService;
    private UsageService usageService;
    private IPrmIntegrationService prmIntegrationService;
    private IRmsIntegrationService rmsIntegrationService;
    private IWorkMatchingService workMatchingService;

    @Before
    public void setUp() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setCreateUser(USER_NAME);
        usageRepository = createMock(IUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        usageArchiveRepository = createMock(IUsageArchiveRepository.class);
        rmsIntegrationService = createMock(IRmsIntegrationService.class);
        workMatchingService = createMock(IWorkMatchingService.class);
        usageService = new UsageService();
        Whitebox.setInternalState(usageService, "usageRepository", usageRepository);
        Whitebox.setInternalState(usageService, "usageAuditService", usageAuditService);
        Whitebox.setInternalState(usageService, "prmIntegrationService", prmIntegrationService);
        Whitebox.setInternalState(usageService, "usageArchiveRepository", usageArchiveRepository);
        Whitebox.setInternalState(usageService, "rmsIntegrationService", rmsIntegrationService);
        Whitebox.setInternalState(usageService, "workMatchingService", workMatchingService);
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
    public void testGetUsages() {
        List<UsageDto> usagesWithBatch = Lists.newArrayList(new UsageDto());
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("detailId", Sort.Direction.ASC);
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        expect(usageRepository.findByFilter(filter, pageable, sort)).andReturn(usagesWithBatch).once();
        replay(usageRepository);
        List<UsageDto> result = usageService.getUsages(filter, pageable, sort);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(usageRepository);
    }

    @Test
    public void testGetUsagesEmptyFilter() {
        List<UsageDto> result = usageService.getUsages(new UsageFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testWriteUsageCsvReport() {
        PipedOutputStream outputStream = new PipedOutputStream();
        UsageFilter usageFilter = new UsageFilter();
        usageRepository.writeUsagesCsvReport(usageFilter, outputStream);
        expectLastCall().once();
        replay(usageRepository);
        usageService.writeUsageCsvReport(usageFilter, outputStream);
        verify(usageRepository);
    }

    @Test
    public void testWriteScenarioUsagesCsvReport() {
        PipedOutputStream outputStream = new PipedOutputStream();
        usageRepository.writeScenarioUsagesCsvReport(SCENARIO_ID, outputStream);
        expectLastCall().once();
        usageArchiveRepository.writeScenarioUsagesCsvReport(SCENARIO_ID, outputStream);
        expectLastCall().once();
        replay(usageRepository, usageArchiveRepository);
        usageService.writeScenarioUsagesCsvReport(scenario, outputStream);
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        usageService.writeScenarioUsagesCsvReport(scenario, outputStream);
        verify(usageRepository, usageArchiveRepository);
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
        usageRepository.deleteUsages(usageBatch.getId());
        expectLastCall().once();
        usageAuditService.deleteActions(usageBatch.getId());
        expectLastCall().once();
        replay(usageRepository);
        usageService.deleteUsageBatchDetails(usageBatch);
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
    public void testGetUsagesWithAmounts() {
        UsageFilter usageFilter = new UsageFilter();
        Usage usage = buildUsage(USAGE_ID_1);
        expect(usageRepository.findWithAmountsAndRightsholders(usageFilter))
            .andReturn(Lists.newArrayList(usage)).once();
        expect(prmIntegrationService.isRightsholderParticipating(usage.getRightsholder().getAccountNumber(),
            usage.getProductFamily()))
            .andReturn(true).once();
        expect(prmIntegrationService.getRhParticipatingServiceFee(true))
            .andReturn(new BigDecimal("0.16000")).once();
        replay(usageRepository, prmIntegrationService);
        assertTrue(CollectionUtils.isNotEmpty(usageService.getUsagesWithAmounts(usageFilter)));
        verify(usageRepository, prmIntegrationService);
    }

    @Test
    public void testAddUsagesToScenario() {
        Usage usage1 = buildUsage(USAGE_ID_1);
        Usage usage2 = buildUsage(USAGE_ID_2);
        Capture<List<String>> rightsholdersIdsCapture = new Capture<>();
        List<Usage> usages = Lists.newArrayList(usage1, usage2);
        usageRepository.addToScenario(usages);
        expectLastCall().once();
        expect(prmIntegrationService.getRollUps(capture(rightsholdersIdsCapture)))
            .andReturn(HashBasedTable.create()).once();
        replay(usageRepository, prmIntegrationService);
        usageService.addUsagesToScenario(Lists.newArrayList(usage1, usage2), scenario);
        verify(usageRepository, prmIntegrationService);
    }

    @Test
    public void testGetRightsholderTotalsHoldersByScenario() {
        List<RightsholderTotalsHolder> rightsholderTotalsHolders = Lists.newArrayList(new RightsholderTotalsHolder());
        Pageable pageable = new Pageable(0, 1);
        expect(usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY, pageable,
            null)).andReturn(rightsholderTotalsHolders).once();
        expect(usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY,
            pageable, null)).andReturn(rightsholderTotalsHolders).once();
        replay(usageRepository, usageArchiveRepository);
        assertResult(
            usageService.getRightsholderTotalsHoldersByScenario(scenario, StringUtils.EMPTY, pageable, null), 1);
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        assertResult(
            usageService.getRightsholderTotalsHoldersByScenario(scenario, StringUtils.EMPTY, pageable, null), 1);
        verify(usageRepository, usageArchiveRepository);
    }

    @Test
    public void testGetRightsholderTotalsHolderCountByScenario() {
        expect(usageRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, StringUtils.EMPTY))
            .andReturn(5).once();
        expect(usageArchiveRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, StringUtils.EMPTY))
            .andReturn(3).once();
        replay(usageRepository, usageArchiveRepository);
        assertEquals(5, usageService.getRightsholderTotalsHolderCountByScenario(scenario, StringUtils.EMPTY));
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        assertEquals(3, usageService.getRightsholderTotalsHolderCountByScenario(scenario, StringUtils.EMPTY));
        verify(usageRepository, usageArchiveRepository);
    }

    @Test
    public void testGetCountByScenarioAndRhAccountNumber() {
        expect(usageRepository.findCountByScenarioIdAndRhAccountNumber(RH_ACCOUNT_NUMBER, SCENARIO_ID,
            StringUtils.EMPTY)).andReturn(5).once();
        expect(usageArchiveRepository.findCountByScenarioIdAndRhAccountNumber(SCENARIO_ID, RH_ACCOUNT_NUMBER,
            StringUtils.EMPTY)).andReturn(3).once();
        replay(usageRepository, usageArchiveRepository);
        assertEquals(5, usageService.getCountByScenarioAndRhAccountNumber(RH_ACCOUNT_NUMBER, scenario,
            StringUtils.EMPTY));
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
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
            null)).andReturn(usages).once();
        replay(usageRepository, usageArchiveRepository);
        assertResult(
            usageService.getByScenarioAndRhAccountNumber(RH_ACCOUNT_NUMBER, scenario, null, pageable, null), 2);
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
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
    public void testUpdateRhPayeeAndAmounts() {
        Usage usage = buildUsage(RupPersistUtils.generateUuid());
        expect(prmIntegrationService.isRightsholderParticipating(usage.getRightsholder().getAccountNumber(),
            usage.getProductFamily())).andReturn(false).once();
        expect(prmIntegrationService.getRhParticipatingServiceFee(false)).andReturn(new BigDecimal("0.16")).once();
        usageRepository.updateRhPayeeAndAmounts(Collections.singletonList(usage));
        expectLastCall().once();
        replay(usageRepository, prmIntegrationService);
        usageService.updateRhPayeeAndAmounts(Collections.singletonList(usage));
        assertEquals(new BigDecimal("0.16"), usage.getServiceFee());
        assertEquals(new BigDecimal("16.0000000000"), usage.getServiceFeeAmount());
        assertEquals(new BigDecimal("84.0000000000"), usage.getNetAmount());
        verify(usageRepository, prmIntegrationService);
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
        usageAuditService.logAction(usagesIds.get(0), scenario, UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO,
            "Action reason");
        expectLastCall().once();
        usageAuditService.logAction(usagesIds.get(1), scenario, UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO,
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
        Sort sort = Sort.create(new Object[]{"detailId"}, false);
        expect(usageRepository.findForAudit(filter, pageable, sort)).andReturn(Collections.emptyList()).once();
        replay(usageRepository);
        assertEquals(Collections.emptyList(), usageService.getForAudit(filter, pageable, sort));
        verify(usageRepository);
    }

    @Test
    public void writeAuditCsvReport() {
        AuditFilter filter = new AuditFilter();
        PipedOutputStream stream = new PipedOutputStream();
        usageRepository.writeAuditCsvReport(filter, stream);
        expectLastCall().once();
        replay(usageRepository);
        usageService.writeAuditCsvReport(filter, stream);
        verify(usageRepository);
    }

    @Test
    public void testSendForRightsAssignment() {
        RightsAssignmentResult result = new RightsAssignmentResult(RightsAssignmentResultStatusEnum.SUCCESS);
        result.setJobId("b5015e54-c38a-4fc8-b889-c644640085a4");
        expect(usageRepository.findByStatuses(UsageStatusEnum.RH_NOT_FOUND))
            .andReturn(Lists.newArrayList(buildUsage(USAGE_ID_1))).once();
        expect(rmsIntegrationService.sendForRightsAssignment(Sets.newHashSet(123160519L)))
            .andReturn(result).once();
        usageRepository.updateStatus(USAGE_ID_1, UsageStatusEnum.SENT_FOR_RA);
        expectLastCall().once();
        usageAuditService.logAction(USAGE_ID_1, UsageActionTypeEnum.SENT_FOR_RA,
            "Sent for RA: job id 'b5015e54-c38a-4fc8-b889-c644640085a4'");
        replay(usageRepository, rmsIntegrationService, usageAuditService);
        usageService.sendForRightsAssignment();
        verify(usageRepository, rmsIntegrationService, usageAuditService);
    }

    @Test
    public void testSendForRightsAssignmentRaError() {
        expect(usageRepository.findByStatuses(UsageStatusEnum.RH_NOT_FOUND))
            .andReturn(Lists.newArrayList(buildUsage(USAGE_ID_1))).once();
        expect(rmsIntegrationService.sendForRightsAssignment(Sets.newHashSet(123160519L)))
            .andReturn(new RightsAssignmentResult(RightsAssignmentResultStatusEnum.RA_ERROR)).once();
        replay(usageRepository, rmsIntegrationService, usageAuditService);
        usageService.sendForRightsAssignment();
        verify(usageRepository, rmsIntegrationService, usageAuditService);
    }

    @Test
    public void testSendForRightsAssignmentNoUsagesForRa() {
        expect(usageRepository.findByStatuses(UsageStatusEnum.RH_NOT_FOUND))
            .andReturn(Collections.emptyList()).once();
        replay(usageRepository, rmsIntegrationService, usageAuditService);
        usageService.sendForRightsAssignment();
        verify(usageRepository, rmsIntegrationService, usageAuditService);
    }

    @Test
    public void testGetProductFamilies() {
        expect(usageRepository.findProductFamiliesForFilter())
            .andReturn(Lists.newArrayList(FAS_PRODUCT_FAMILY, NTS_PRODUCT_FAMILY))
            .once();
        replay(usageRepository);
        List<String> productFamilies = usageService.getProductFamilies();
        assertEquals(2, CollectionUtils.size(productFamilies));
        assertEquals(FAS_PRODUCT_FAMILY, productFamilies.get(0));
        assertEquals(NTS_PRODUCT_FAMILY, productFamilies.get(1));
        verify(usageRepository);
    }

    @Test
    public void testGetProductFamiliesForAudit() {
        expect(usageRepository.findProductFamiliesForAuditFilter())
            .andReturn(Lists.newArrayList(FAS_PRODUCT_FAMILY, NTS_PRODUCT_FAMILY))
            .once();
        replay(usageRepository);
        List<String> productFamilies = usageService.getProductFamiliesForAudit();
        assertEquals(2, CollectionUtils.size(productFamilies));
        assertEquals(FAS_PRODUCT_FAMILY, productFamilies.get(0));
        assertEquals(NTS_PRODUCT_FAMILY, productFamilies.get(1));
        verify(usageRepository);
    }

    @Test
    public void testUpdatePaidInfo() {
        PaidUsage paidUsage = new PaidUsage();
        paidUsage.setDetailId(12345678L);
        paidUsage.setCheckNumber("578945");
        paidUsage.setCccEventId("53256");
        paidUsage.setDistributionName("FDA March 17");
        Usage usage = buildUsage(USAGE_ID_1);
        expect(usageRepository.findByDetailId(12345678L)).andReturn(usage).once();
        usageArchiveRepository.updatePaidInfo(paidUsage);
        expectLastCall().once();
        usageAuditService.logAction(USAGE_ID_1, UsageActionTypeEnum.PAID,
            "Usage has been paid according to information from the LM");
        expectLastCall().once();
        replay(usageRepository, usageArchiveRepository, usageAuditService);
        usageService.updatePaidInfo(Collections.singletonList(paidUsage));
        verify(usageRepository, usageArchiveRepository, usageAuditService);
    }

    @Test
    public void testUpdatePaidInfoNotFoundUsage() {
        PaidUsage paidUsage = new PaidUsage();
        paidUsage.setDetailId(12345678L);
        paidUsage.setCheckNumber("578945");
        paidUsage.setCccEventId("53256");
        paidUsage.setDistributionName("FDA March 17");
        expect(usageRepository.findByDetailId(12345678L)).andReturn(null).once();
        replay(usageRepository, usageArchiveRepository, usageAuditService);
        usageService.updatePaidInfo(Collections.singletonList(paidUsage));
        verify(usageRepository, usageArchiveRepository, usageAuditService);
    }

    @Test
    public void testFindWorksAndUpdateStatuses() {
        Usage usage1 = buildUsage(USAGE_ID_1);
        usage1.setStandardNumber("10.1147/RD.206.0542");
        Usage usage2 = buildUsage(USAGE_ID_2);
        usage2.setWorkTitle("Merry.go.round");
        Usage usage3 = buildUsage(USAGE_ID_3);
        usage3.setGrossAmount(new BigDecimal("99.00"));
        usage3.setStandardNumber(null);
        usage3.setWorkTitle(null);
        List<Usage> usages = Lists.newArrayList(usage1, usage2, usage3);
        List<Usage> matchedByIdno = Lists.newArrayList(usage1);
        List<Usage> matchedByTitle = Lists.newArrayList(usage2);
        expect(usageRepository.findUsagesWithBlankWrWrkInst()).andReturn(usages).once();
        expect(workMatchingService.matchByIdno(usages)).andReturn(matchedByIdno).once();
        expect(workMatchingService.matchByTitle(usages)).andReturn(matchedByTitle).once();
        usageRepository.updateStatusAndWrWrkInst(matchedByIdno);
        expectLastCall().once();
        usageRepository.updateStatusAndWrWrkInst(matchedByTitle);
        expectLastCall().once();
        usageRepository.updateStatusAndProductFamily(Lists.newArrayList(usage3));
        expectLastCall().once();
        usageAuditService.logAction(usage1.getId(), UsageActionTypeEnum.WORK_FOUND,
            "Wr Wrk Inst 123160519 was found in PI by standard number 10.1147/RD.206.0542");
        expectLastCall().once();
        usageAuditService.logAction(usage2.getId(), UsageActionTypeEnum.WORK_FOUND,
            "Wr Wrk Inst 123160519 was found in PI by title 'Merry.go.round'");
        expectLastCall().once();
        usageAuditService.logAction(usage3.getId(), UsageActionTypeEnum.ELIGIBLE_FOR_NTS, ACTION_REASON_3);
        expectLastCall().once();
        replay(usageRepository, workMatchingService, usageAuditService);
        usageService.findWorksAndUpdateStatuses();
        verify(usageRepository, workMatchingService, usageAuditService);
    }

    @Test
    public void testMakeUsagesEligibleForNtsGroupByStandardNumber() {
        List<Usage> usages = Lists.newArrayList();
        usages.add(buildUsageWithStandardNumber(USAGE_ID + "1", STANDARD_NUMBER + "1", 99));
        usages.add(buildUsageWithStandardNumber(USAGE_ID + "2", STANDARD_NUMBER + "2", 100));
        usages.add(buildUsageWithStandardNumber(USAGE_ID + "3", STANDARD_NUMBER + "3", 50));
        usages.add(buildUsageWithStandardNumber(USAGE_ID + "4", STANDARD_NUMBER + "3", 49));
        usages.add(buildUsageWithStandardNumber(USAGE_ID + "5", STANDARD_NUMBER + "4", 50));
        usages.add(buildUsageWithStandardNumber(USAGE_ID + "6", STANDARD_NUMBER + "4", 50));
        Capture<List<Usage>> usagesCapture = new Capture<>();
        usageRepository.updateStatusAndProductFamily(capture(usagesCapture));
        EasyMock.expectLastCall();
        usageAuditService.logAction(usages.get(0).getId(), UsageActionTypeEnum.ELIGIBLE_FOR_NTS, ACTION_REASON_1);
        expectLastCall().once();
        usageAuditService.logAction(usages.get(2).getId(), UsageActionTypeEnum.ELIGIBLE_FOR_NTS, ACTION_REASON_1);
        expectLastCall().once();
        usageAuditService.logAction(usages.get(3).getId(), UsageActionTypeEnum.ELIGIBLE_FOR_NTS, ACTION_REASON_1);
        expectLastCall().once();
        replay(usageRepository, usageAuditService);
        usageService.makeUsagesEligibleForNts(usages);
        List<Usage> actualUsages = usagesCapture.getValue();
        assertEquals(3, actualUsages.size());
        verifyUsagesEligibleForNts(actualUsages.get(0), USAGE_ID + "1");
        verifyUsagesEligibleForNts(actualUsages.get(1), USAGE_ID + "3");
        verifyUsagesEligibleForNts(actualUsages.get(2), USAGE_ID + "4");
        verify(usageRepository, usageAuditService);
    }

    @Test
    public void testMakeUsagesEligibleForNtsGroupByWorkTitle() {
        List<Usage> usages = Lists.newArrayList();
        usages.add(buildUsageWithWorkTitle(USAGE_ID + "1", WORK_TITLE + "1", 99));
        usages.add(buildUsageWithWorkTitle(USAGE_ID + "2", WORK_TITLE + "2", 100));
        usages.add(buildUsageWithWorkTitle(USAGE_ID + "3", WORK_TITLE + "3", 50));
        usages.add(buildUsageWithWorkTitle(USAGE_ID + "4", WORK_TITLE + "3", 49));
        usages.add(buildUsageWithWorkTitle(USAGE_ID + "5", WORK_TITLE + "4", 50));
        usages.add(buildUsageWithWorkTitle(USAGE_ID + "6", WORK_TITLE + "4", 50));
        Capture<List<Usage>> usagesCapture = new Capture<>();
        usageRepository.updateStatusAndProductFamily(capture(usagesCapture));
        EasyMock.expectLastCall();
        usageAuditService.logAction(usages.get(0).getId(), UsageActionTypeEnum.ELIGIBLE_FOR_NTS, ACTION_REASON_2);
        expectLastCall().once();
        usageAuditService.logAction(usages.get(2).getId(), UsageActionTypeEnum.ELIGIBLE_FOR_NTS, ACTION_REASON_2);
        expectLastCall().once();
        usageAuditService.logAction(usages.get(3).getId(), UsageActionTypeEnum.ELIGIBLE_FOR_NTS, ACTION_REASON_2);
        expectLastCall().once();
        replay(usageRepository, usageAuditService);
        usageService.makeUsagesEligibleForNts(usages);
        List<Usage> actualUsages = usagesCapture.getValue();
        assertEquals(3, actualUsages.size());
        verifyUsagesEligibleForNts(actualUsages.get(0), USAGE_ID + "1");
        verifyUsagesEligibleForNts(actualUsages.get(1), USAGE_ID + "3");
        verifyUsagesEligibleForNts(actualUsages.get(2), USAGE_ID + "4");
        verify(usageRepository, usageAuditService);
    }

    @Test
    public void testMakeUsagesEligibleForNtsGroupBySelf() {
        List<Usage> usages = Lists.newArrayList();
        usages.add(buildUsage(USAGE_ID + "1", 99));
        usages.add(buildUsage(USAGE_ID + "2", 100));
        Capture<List<Usage>> usagesCapture = new Capture<>();
        usageRepository.updateStatusAndProductFamily(capture(usagesCapture));
        EasyMock.expectLastCall();
        usageAuditService.logAction(usages.get(0).getId(), UsageActionTypeEnum.ELIGIBLE_FOR_NTS, ACTION_REASON_3);
        expectLastCall().once();
        replay(usageRepository, usageAuditService);
        usageService.makeUsagesEligibleForNts(usages);
        List<Usage> actualUsages = usagesCapture.getValue();
        assertEquals(1, actualUsages.size());
        verifyUsagesEligibleForNts(actualUsages.get(0), USAGE_ID + "1");
        verify(usageRepository, usageAuditService);
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
        usage.getRightsholder().setId(RupPersistUtils.generateUuid());
        usage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        usage.setGrossAmount(new BigDecimal("100.00"));
        usage.setNetAmount(new BigDecimal("68.0000000000"));
        usage.setServiceFeeAmount(new BigDecimal("32.0000000000"));
        usage.setServiceFee(new BigDecimal("0.32"));
        usage.setProductFamily("FAS");
        return usage;
    }

    private Usage buildUsageWithStandardNumber(String id, String standardNumber, int grossAmount) {
        Usage usage = new Usage();
        usage.setId(id);
        usage.setStandardNumber(standardNumber);
        usage.setGrossAmount(BigDecimal.valueOf(grossAmount));
        return usage;
    }

    private Usage buildUsageWithWorkTitle(String id, String workTitle, int grossAmount) {
        Usage usage = new Usage();
        usage.setId(id);
        usage.setStandardNumber(null);
        usage.setWorkTitle(workTitle);
        usage.setGrossAmount(BigDecimal.valueOf(grossAmount));
        return usage;
    }

    private Usage buildUsage(String id, int grossAmount) {
        Usage usage = new Usage();
        usage.setId(id);
        usage.setStandardNumber(null);
        usage.setWorkTitle(null);
        usage.setGrossAmount(BigDecimal.valueOf(grossAmount));
        return usage;
    }

    private void verifyUsagesEligibleForNts(Usage usage, String usageId) {
        assertEquals(usageId, usage.getId());
        assertEquals(UsageStatusEnum.ELIGIBLE, usage.getStatus());
        assertEquals(NTS_PRODUCT_FAMILY, usage.getProductFamily());
    }
}
