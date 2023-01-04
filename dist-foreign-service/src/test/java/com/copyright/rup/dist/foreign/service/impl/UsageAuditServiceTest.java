package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.report.BatchStatistic;
import com.copyright.rup.dist.foreign.repository.impl.UsageAuditRepository;

import com.google.common.collect.ImmutableSet;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UsageAuditService}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/16/2017
 *
 * @author Uladzislau_Shalamitski
 */
public class UsageAuditServiceTest {

    private static final String USAGE_UID = "4eef3a40-b274-11e7-abc4-cec278b6b50a";
    private static final String USAGE_UID_2 = "5eef3a40-b274-11e7-abc4-cec278b6b50a";
    private static final String BATCH_UID = "f2104232-b274-11e7-abc4-cec278b6b50a";
    private static final String SCENARIO_UID = RupPersistUtils.generateUuid();
    private static final String REASON = "Uploaded in 'ABC' Batch";
    private static final String BATCH_NAME = "Test Batch Name";
    private static final BigDecimal AMOUNT_ZERO = new BigDecimal("0.00");

    private UsageAuditService usageAuditService;
    private UsageAuditRepository usageAuditRepository;

    @Before
    public void setUp() {
        usageAuditService = new UsageAuditService();
        usageAuditRepository = createMock(UsageAuditRepository.class);
        Whitebox.setInternalState(usageAuditService, "usageAuditRepository", usageAuditRepository);
    }

    @Test
    public void testLogAction() {
        Capture<UsageAuditItem> usageAuditItemCapture = newCapture();
        usageAuditRepository.insert(capture(usageAuditItemCapture));
        expectLastCall().once();
        replay(usageAuditRepository);
        usageAuditService.logAction(USAGE_UID, UsageActionTypeEnum.LOADED, REASON);
        verifyCapturedAuditItem(usageAuditItemCapture, USAGE_UID);
        verify(usageAuditRepository);
    }

    @Test
    public void testLogActionWithUsageIds() {
        Capture<UsageAuditItem> usageAuditItemCapture1 = newCapture();
        usageAuditRepository.insert(capture(usageAuditItemCapture1));
        expectLastCall().once();
        Capture<UsageAuditItem> usageAuditItemCapture2 = newCapture();
        usageAuditRepository.insert(capture(usageAuditItemCapture2));
        expectLastCall().once();
        replay(usageAuditRepository);
        usageAuditService.logAction(ImmutableSet.of(USAGE_UID, USAGE_UID_2),
            UsageActionTypeEnum.LOADED, REASON);
        verifyCapturedAuditItem(usageAuditItemCapture1, USAGE_UID);
        verifyCapturedAuditItem(usageAuditItemCapture2, USAGE_UID_2);
        verify(usageAuditRepository);
    }

    @Test
    public void testDeleteActionsByBatchId() {
        usageAuditRepository.deleteByBatchId(BATCH_UID);
        expectLastCall().once();
        replay(usageAuditRepository);
        usageAuditService.deleteActionsByBatchId(BATCH_UID);
        verify(usageAuditRepository);
    }

    @Test
    public void testDeleteActionsByUsageId() {
        usageAuditRepository.deleteByUsageId(USAGE_UID);
        expectLastCall().once();
        replay(usageAuditRepository);
        usageAuditService.deleteActionsByUsageId(USAGE_UID);
        verify(usageAuditRepository);
    }

    @Test
    public void testDeleteActionsByScenarioId() {
        usageAuditRepository.deleteByScenarioId(SCENARIO_UID);
        expectLastCall().once();
        replay(usageAuditRepository);
        usageAuditService.deleteActionsByScenarioId(SCENARIO_UID);
        verify(usageAuditRepository);
    }

    @Test
    public void testGetUsageAudit() {
        String usageId = RupPersistUtils.generateUuid();
        List<UsageAuditItem> items = Collections.emptyList();
        expect(usageAuditRepository.findByUsageId(usageId)).andReturn(items).once();
        replay(usageAuditRepository);
        assertSame(items, usageAuditService.getUsageAudit(usageId));
        verify(usageAuditRepository);
    }

    @Test
    public void testGetBatchesStatisticByBatchName() {
        List<BatchStatistic> statistics = buildStatistics();
        expect(usageAuditRepository.findBatchesStatisticByBatchNameAndDate(BATCH_NAME, null))
            .andReturn(statistics).once();
        replay(usageAuditRepository);
        assertEquals(usageAuditService.getBatchesStatisticByBatchNameAndDate(BATCH_NAME, null), statistics);
        verify(usageAuditRepository);
    }

    @Test
    public void testGetBatchesStatisticByBatchNameAndDate() {
        LocalDate date = LocalDate.now();
        List<BatchStatistic> statistics = buildStatistics();
        expect(usageAuditRepository.findBatchesStatisticByBatchNameAndDate(BATCH_NAME, date))
            .andReturn(statistics).once();
        replay(usageAuditRepository);
        assertEquals(usageAuditService.getBatchesStatisticByBatchNameAndDate(BATCH_NAME, date), statistics);
        verify(usageAuditRepository);
    }

    @Test
    public void testGetBatchesStatisticByDateFromAndDateTo() {
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = dateFrom.plusDays(1);
        List<BatchStatistic> statistics = buildStatistics();
        expect(usageAuditRepository.findBatchesStatisticByDateFromAndDateTo(dateFrom, dateTo))
            .andReturn(statistics).once();
        replay(usageAuditRepository);
        assertEquals(usageAuditService.getBatchesStatisticByDateFromAndDateTo(dateFrom, dateTo), statistics);
        verify(usageAuditRepository);
    }

    @Test
    public void testGetBatchesStatisticByDateFromAfterDateTo() {
        try {
            LocalDate dateFrom = LocalDate.now();
            LocalDate dateTo = dateFrom.minusDays(1);
            usageAuditService.getBatchesStatisticByDateFromAndDateTo(dateFrom, dateTo);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("The parameter 'dateFrom' must be less than or equal to the parameter 'dateTo'",
                e.getMessage());
        }
    }

    @Test
    public void testDeleteActionsForSalUsageData(){
        String batchId = "7ab04433-7663-43e0-bc19-3aacb4d7b4c7";
        usageAuditRepository.deleteForSalUsageDataByBatchId(batchId);
        expectLastCall().once();
        replay(usageAuditRepository);
        usageAuditService.deleteActionsForSalUsageData(batchId);
        verify(usageAuditRepository);
    }

    private void verifyCapturedAuditItem(Capture<UsageAuditItem> usageAuditItemCapture, String expectedUsageItemId) {
        UsageAuditItem usageAuditItem = usageAuditItemCapture.getValue();
        assertEquals(REASON, usageAuditItem.getActionReason());
        assertEquals(expectedUsageItemId, usageAuditItem.getUsageId());
        assertEquals(UsageActionTypeEnum.LOADED, usageAuditItem.getActionType());
    }

    private List<BatchStatistic> buildStatistics() {
        BatchStatistic statistic = new BatchStatistic();
        statistic.setBatchName(BATCH_NAME);
        statistic.setTotalCount(2);
        statistic.setTotalAmount(new BigDecimal("1000.00"));
        statistic.setMatchedCount(1);
        statistic.setMatchedAmount(new BigDecimal("500.00"));
        statistic.setMatchedPercent(new BigDecimal("50.00"));
        statistic.setWorksNotFoundCount(0);
        statistic.setWorksNotFoundAmount(AMOUNT_ZERO);
        statistic.setWorksNotFoundPercent(AMOUNT_ZERO);
        statistic.setMultipleMatchingCount(0);
        statistic.setMultipleMatchingAmount(AMOUNT_ZERO);
        statistic.setMultipleMatchingPercent(AMOUNT_ZERO);
        statistic.setNtsWithdrawnCount(0);
        statistic.setNtsWithdrawnAmount(AMOUNT_ZERO);
        statistic.setNtsWithdrawnPercent(AMOUNT_ZERO);
        statistic.setRhNotFoundCount(0);
        statistic.setRhNotFoundAmount(AMOUNT_ZERO);
        statistic.setRhNotFoundPercent(AMOUNT_ZERO);
        statistic.setRhFoundCount(0);
        statistic.setRhFoundAmount(AMOUNT_ZERO);
        statistic.setRhFoundPercent(AMOUNT_ZERO);
        statistic.setEligibleCount(1);
        statistic.setEligibleAmount(new BigDecimal("500.00"));
        statistic.setEligiblePercent(new BigDecimal("50.00"));
        statistic.setSendForRaCount(0);
        statistic.setSendForRaAmount(AMOUNT_ZERO);
        statistic.setSendForRaPercent(AMOUNT_ZERO);
        statistic.setPaidCount(0);
        statistic.setPaidAmount(AMOUNT_ZERO);
        statistic.setPaidPercent(AMOUNT_ZERO);
        return List.of(statistic);
    }
}
