package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.report.UsageBatchStatistic;
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
    private static final String REASON = "Uploaded in 'ABC' Batch";

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
        Capture<UsageAuditItem> usageAuditItemCapture = new Capture<>();
        usageAuditRepository.insert(capture(usageAuditItemCapture));
        expectLastCall().once();
        replay(usageAuditRepository);
        usageAuditService.logAction(USAGE_UID, UsageActionTypeEnum.LOADED, REASON);
        verifyCapturedAuditItem(usageAuditItemCapture, USAGE_UID);
        verify(usageAuditRepository);
    }

    @Test
    public void testLogActionWithUsageIds() {
        Capture<UsageAuditItem> usageAuditItemCapture1 = new Capture<>();
        usageAuditRepository.insert(capture(usageAuditItemCapture1));
        expectLastCall().once();
        Capture<UsageAuditItem> usageAuditItemCapture2 = new Capture<>();
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
    public void testGetUsageAudit() {
        String usageId = RupPersistUtils.generateUuid();
        List<UsageAuditItem> items = Collections.emptyList();
        expect(usageAuditRepository.findByUsageId(usageId)).andReturn(items).once();
        replay(usageAuditRepository);
        assertSame(items, usageAuditService.getUsageAudit(usageId));
        verify(usageAuditRepository);
    }

    @Test
    public void testGetBatchStatistic() {
        String batchName = "Batch Name";
        LocalDate date = LocalDate.now();
        UsageBatchStatistic statistic = buildStatistic();
        BigDecimal percent10 = new BigDecimal("10.0");
        expect(usageAuditRepository.findBatchStatistic(batchName, date)).andReturn(statistic).once();
        replay(usageAuditRepository);
        usageAuditService.getBatchStatistic(batchName, date);
        assertEquals(batchName, statistic.getBatchName());
        assertEquals(date, statistic.getDate());
        assertEquals(new BigDecimal("20.0"), statistic.getMatchedPercent());
        assertEquals(percent10, statistic.getWorksNotFoundPercent());
        assertEquals(percent10, statistic.getNtsWithDrawnPercent());
        assertEquals(percent10, statistic.getRhNotFoundPercent());
        assertEquals(percent10, statistic.getEligiblePercent());
        assertEquals(percent10, statistic.getSendForRaPercent());
        assertEquals(new BigDecimal("50.0"), statistic.getPaidPercent());
        verify(usageAuditRepository);
    }

    private void verifyCapturedAuditItem(Capture<UsageAuditItem> usageAuditItemCapture, String expectedUsageItemId) {
        UsageAuditItem usageAuditItem = usageAuditItemCapture.getValue();
        assertEquals(REASON, usageAuditItem.getActionReason());
        assertEquals(expectedUsageItemId, usageAuditItem.getUsageId());
        assertEquals(UsageActionTypeEnum.LOADED, usageAuditItem.getActionType());
    }

    private UsageBatchStatistic buildStatistic() {
        UsageBatchStatistic statistic = new UsageBatchStatistic();
        statistic.setLoadedCount(10);
        statistic.setLoadedAmount(new BigDecimal("1000.00"));
        statistic.setMatchedCount(2);
        statistic.setMatchedAmount(new BigDecimal("300.00"));
        statistic.setWorksNotFoundCount(1);
        statistic.setWorksNotFoundAmount(new BigDecimal("50.00"));
        statistic.setNtsWithDrawnCount(1);
        statistic.setNtsWithDrawnAmount(new BigDecimal("150.00"));
        statistic.setRhNotFoundCount(1);
        statistic.setRhNotFoundAmount(new BigDecimal("130.00"));
        statistic.setSendForRaCount(1);
        statistic.setSendForRaAmount(new BigDecimal("130.00"));
        statistic.setEligibleCount(1);
        statistic.setEligibleAmount(new BigDecimal("170.00"));
        statistic.setPaidCount(5);
        statistic.setPaidAmount(new BigDecimal("500.00"));
        return statistic;
    }
}
