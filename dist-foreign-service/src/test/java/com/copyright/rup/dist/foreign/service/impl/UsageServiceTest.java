package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
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

    private static final String USAGE_ID_1 = "Usage id 1";
    private static final String USAGE_ID_2 = "Usage id 2";
    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private static final String USER_NAME = "User name";
    private static final Long RH_ACCOUNT_NUMBER = 1000001534L;
    private IUsageRepository usageRepository;
    private IUsageAuditService usageAuditService;
    private IUsageService usageService;
    private IPrmIntegrationService prmIntegrationService;

    @Before
    public void setUp() {
        usageRepository = createMock(IUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        usageService = new UsageService();
        Whitebox.setInternalState(usageService, "usageRepository", usageRepository);
        Whitebox.setInternalState(usageService, "usageAuditService", usageAuditService);
        Whitebox.setInternalState(usageService, "prmIntegrationService", prmIntegrationService);
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
        replay(usageRepository);
        usageService.writeScenarioUsagesCsvReport(SCENARIO_ID, outputStream);
        verify(usageRepository);
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
    public void testGetUsagesWithAmounts() {
        UsageFilter usageFilter = new UsageFilter();
        Usage usage = buildUsage(USAGE_ID_1);
        expect(usageRepository.findWithAmountsAndRightsholders(usageFilter))
            .andReturn(Lists.newArrayList(usage)).once();
        expect(prmIntegrationService.isRightsholderParticipating(usage.getRightsholder().getAccountNumber()))
            .andReturn(true).once();
        expect(prmIntegrationService.getRhParticipatingServiceFee(true))
            .andReturn(new BigDecimal("0.16000")).once();
        replay(usageRepository, prmIntegrationService);
        assertTrue(CollectionUtils.isNotEmpty(usageService.getUsagesWithAmounts(usageFilter)));
        verify(usageRepository, prmIntegrationService);
    }

    @Test
    public void testAddUsagesToScenario() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setCreateUser(USER_NAME);
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
    public void testGetRightsholderTotalsHoldersByScenarioId() {
        List<RightsholderTotalsHolder> rightsholderTotalsHolders = Lists.newArrayList(new RightsholderTotalsHolder());
        Pageable pageable = new Pageable(0, 1);
        expect(usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY, pageable,
            null)).andReturn(rightsholderTotalsHolders).once();
        replay(usageRepository);
        List<RightsholderTotalsHolder> result =
            usageService.getRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY, pageable, null);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(usageRepository);
    }

    @Test
    public void testGetRightsholderTotalsHolderCountByScenarioId() {
        expect(usageRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, StringUtils.EMPTY))
            .andReturn(5).once();
        replay(usageRepository);
        assertEquals(5, usageService.getRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, StringUtils.EMPTY));
        verify(usageRepository);
    }

    @Test
    public void testGetCountByScenarioIdAndRhAccountNumber() {
        expect(usageRepository.findCountByScenarioIdAndRhAccountNumber(RH_ACCOUNT_NUMBER, SCENARIO_ID,
            StringUtils.EMPTY)).andReturn(5).once();
        replay(usageRepository);
        assertEquals(5, usageService.getCountByScenarioIdAndRhAccountNumber(RH_ACCOUNT_NUMBER, SCENARIO_ID,
            StringUtils.EMPTY));
        verify(usageRepository);
    }

    @Test
    public void testGetByScenarioIdAndRhAccountNumber() {
        List<UsageDto> usages = Lists.newArrayList(new UsageDto(), new UsageDto());
        Pageable pageable = new Pageable(0, 2);
        expect(usageRepository.findByScenarioIdAndRhAccountNumber(RH_ACCOUNT_NUMBER, SCENARIO_ID, null, pageable,
            null)).andReturn(usages);
        replay(usageRepository);
        List<UsageDto> result =
            usageService.getByScenarioIdAndRhAccountNumber(RH_ACCOUNT_NUMBER, SCENARIO_ID, null, pageable, null);
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(usageRepository);
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
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
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

    private void verifyUsage(Usage usage, BigDecimal grossAmount) {
        assertNotNull(usage);
        assertEquals(grossAmount, usage.getGrossAmount());
        assertEquals(USER_NAME, usage.getCreateUser());
        assertEquals(USER_NAME, usage.getUpdateUser());
    }

    private Usage buildUsage(String usageId) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.getRightsholder().setId(RupPersistUtils.generateUuid());
        usage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        usage.setGrossAmount(new BigDecimal("100.00"));
        return usage;
    }
}
