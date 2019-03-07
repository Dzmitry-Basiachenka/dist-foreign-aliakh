package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.report.UsageBatchStatistic;
import com.copyright.rup.dist.foreign.domain.report.UsageStatistic;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Integration test for {@link UsageAuditRepository}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/16/2017
 *
 * @author Uladzislau_Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=usage-audit-repository-test-data-init.groovy"})
@TransactionConfiguration
@Transactional
public class UsageAuditRepositoryIntegrationTest {

    private static final String USAGE_UID = "3ab5e80b-89c0-4d78-9675-54c7ab284450";
    private static final String USAGE_UID_2 = "3fb43e60-3352-4db4-9080-c30b8a6f6600";
    private static final String AMOUNT_ZERO = "0.00";

    @Autowired
    private IUsageAuditRepository usageAuditRepository;

    @Test
    public void testInsertAndFind() {
        List<UsageAuditItem> usageAuditItems = usageAuditRepository.findByUsageId(USAGE_UID);
        assertEquals(1, CollectionUtils.size(usageAuditItems));
        usageAuditRepository.insert(buildUsageAuditItem());
        usageAuditItems = usageAuditRepository.findByUsageId(USAGE_UID);
        assertEquals(2, CollectionUtils.size(usageAuditItems));
        UsageAuditItem auditItem = usageAuditItems.get(0);
        assertEquals(UsageActionTypeEnum.WORK_NOT_FOUND, auditItem.getActionType());
        assertEquals(USAGE_UID, auditItem.getUsageId());
        assertEquals("Usage has no standard number and title", auditItem.getActionReason());
        auditItem = usageAuditItems.get(1);
        assertEquals(UsageActionTypeEnum.LOADED, auditItem.getActionType());
        assertEquals("Uploaded in 'Test Batch 1' Batch", auditItem.getActionReason());
        assertEquals(USAGE_UID, auditItem.getUsageId());
    }

    @Test
    public void testDeleteByBatchId() {
        assertEquals(1, CollectionUtils.size(usageAuditRepository.findByUsageId(USAGE_UID)));
        usageAuditRepository.deleteByBatchId("56282dbc-2468-48d4-b926-93d3458a656a");
        assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId(USAGE_UID)));
    }

    @Test
    public void testDeleteForArchivedByBatchId() {
        assertEquals(1,
            CollectionUtils.size(usageAuditRepository.findByUsageId("422d33c0-4594-451e-a1ca-412c023299aa")));
        usageAuditRepository.deleteForArchivedByBatchId("56282dbc-2468-48d4-b926-93d3458a656a");
        assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId("422d33c0-4594-451e-a1ca-412c023299aa")));
    }

    @Test
    public void testDeleteByUsageId() {
        assertEquals(1, CollectionUtils.size(usageAuditRepository.findByUsageId(USAGE_UID)));
        usageAuditRepository.deleteByUsageId(USAGE_UID);
        assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId(USAGE_UID)));
    }

    @Test
    public void testFindFasBatchStatistic() {
        UsageBatchStatistic statistic = usageAuditRepository.findBatchStatistic("FAS batch statistic", null);
        assertNotNull(statistic);
        assertEquals(10, statistic.getLoadedCount());
        assertEquals(new BigDecimal("995.00"), statistic.getLoadedAmount());
        assertEquals(2, statistic.getMatchedCount());
        assertEquals(new BigDecimal("190.00"), statistic.getMatchedAmount());
        assertEquals(1, statistic.getWorksNotFoundCount());
        assertEquals(new BigDecimal("55.00"), statistic.getWorksNotFoundAmount());
        assertEquals(1, statistic.getNtsWithDrawnCount());
        assertEquals(new BigDecimal("150.00"), statistic.getNtsWithDrawnAmount());
        assertEquals(1, statistic.getRhNotFoundCount());
        assertEquals(new BigDecimal("80.00"), statistic.getRhNotFoundAmount());
        assertEquals(1, statistic.getEligibleCount());
        assertEquals(new BigDecimal("110.00"), statistic.getEligibleAmount());
        assertEquals(1, statistic.getSendForRaCount());
        assertEquals(new BigDecimal("80.00"), statistic.getSendForRaAmount());
        assertEquals(5, statistic.getPaidCount());
        assertEquals(new BigDecimal("500.00"), statistic.getPaidAmount());
    }

    @Test
    public void testFindNtsBatchStatistic() {
        UsageBatchStatistic statistic = usageAuditRepository.findBatchStatistic("NTS batch statistic", null);
        assertNotNull(statistic);
        assertEquals(10, statistic.getLoadedCount());
        assertEquals(new BigDecimal("995.00"), statistic.getLoadedAmount());
        assertEquals(0, statistic.getMatchedCount());
        assertEquals(new BigDecimal(AMOUNT_ZERO), statistic.getMatchedAmount());
        assertEquals(0, statistic.getWorksNotFoundCount());
        assertEquals(new BigDecimal(AMOUNT_ZERO), statistic.getWorksNotFoundAmount());
        assertEquals(0, statistic.getNtsWithDrawnCount());
        assertEquals(new BigDecimal(AMOUNT_ZERO), statistic.getNtsWithDrawnAmount());
        assertEquals(0, statistic.getRhNotFoundCount());
        assertEquals(new BigDecimal(AMOUNT_ZERO), statistic.getRhNotFoundAmount());
        assertEquals(5, statistic.getEligibleCount());
        assertEquals(new BigDecimal("500.00"), statistic.getEligibleAmount());
        assertEquals(0, statistic.getSendForRaCount());
        assertEquals(new BigDecimal(AMOUNT_ZERO), statistic.getSendForRaAmount());
        assertEquals(0, statistic.getPaidCount());
        assertEquals(new BigDecimal(AMOUNT_ZERO), statistic.getPaidAmount());
    }

    @Test
    public void testGetUsageStatistic() {
        UsageStatistic statistic = usageAuditRepository.getUsageStatistic(USAGE_UID_2);
        assertEquals(USAGE_UID_2, statistic.getUsageId());
        assertEquals(UsageStatusEnum.LOCKED, statistic.getStatus());
        assertEquals(0, statistic.getMatchingMs());
        assertEquals(1121, statistic.getRightsMs());
        assertEquals(1075, statistic.getEligibilityMs());
    }

    private UsageAuditItem buildUsageAuditItem() {
        UsageAuditItem usageAuditItem = new UsageAuditItem();
        usageAuditItem.setId(RupPersistUtils.generateUuid());
        usageAuditItem.setUsageId(USAGE_UID);
        usageAuditItem.setActionType(UsageActionTypeEnum.WORK_NOT_FOUND);
        usageAuditItem.setActionReason("Usage has no standard number and title");
        return usageAuditItem;
    }
}
