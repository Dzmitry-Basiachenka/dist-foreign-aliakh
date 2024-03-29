package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.report.BatchStatistic;
import com.copyright.rup.dist.foreign.domain.report.UsageStatistic;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class UsageAuditRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "usage-audit-repository-integration-test/";
    private static final String FIND_BATCHES_STATISTIC = FOLDER_NAME + "find-batches-statistic.groovy";
    private static final String USAGE_UID_1 = "3ab5e80b-89c0-4d78-9675-54c7ab284450";
    private static final String USAGE_UID_2 = "3fb43e60-3352-4db4-9080-c30b8a6f6600";
    private static final String USAGE_UID_3 = "ea85a226-8a4b-45e3-82f8-1233a9cd7ecb";
    private static final String USAGE_UID_4 = "4b5751aa-6258-44c6-b839-a1ec0edfcf4d";
    private static final BigDecimal AMOUNT_ZERO = new BigDecimal("0.00");
    private static final BigDecimal AMOUNT_TEN = new BigDecimal("10.00");

    @Autowired
    private IUsageAuditRepository usageAuditRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-usage-id.groovy")
    public void testFindByUsageId() {
        List<UsageAuditItem> auditItems = usageAuditRepository.findByUsageId(USAGE_UID_1);
        assertEquals(1, CollectionUtils.size(auditItems));
        UsageAuditItem auditItem = auditItems.get(0);
        assertEquals(UsageActionTypeEnum.LOADED, auditItem.getActionType());
        assertEquals("Uploaded in 'Test Batch 1' Batch", auditItem.getActionReason());
        assertEquals(USAGE_UID_1, auditItem.getUsageId());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-usage-id.groovy")
    public void testInsert() {
        List<UsageAuditItem> auditItems = usageAuditRepository.findByUsageId(USAGE_UID_1);
        assertEquals(1, CollectionUtils.size(auditItems));
        usageAuditRepository.insert(buildUsageAuditItem());
        auditItems = usageAuditRepository.findByUsageId(USAGE_UID_1);
        assertEquals(2, CollectionUtils.size(auditItems));
        UsageAuditItem auditItem1 = auditItems.get(0);
        assertEquals(UsageActionTypeEnum.WORK_NOT_FOUND, auditItem1.getActionType());
        assertEquals(USAGE_UID_1, auditItem1.getUsageId());
        assertEquals("Usage has no standard number and title", auditItem1.getActionReason());
        UsageAuditItem auditItem2 = auditItems.get(1);
        assertEquals(UsageActionTypeEnum.LOADED, auditItem2.getActionType());
        assertEquals("Uploaded in 'Test Batch 1' Batch", auditItem2.getActionReason());
        assertEquals(USAGE_UID_1, auditItem2.getUsageId());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-by-batch-id.groovy")
    public void testDeleteByBatchId() {
        assertEquals(1, CollectionUtils.size(usageAuditRepository.findByUsageId(USAGE_UID_1)));
        usageAuditRepository.deleteByBatchId("56282dbc-2468-48d4-b926-93d3458a656a");
        assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId(USAGE_UID_1)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-for-sal-usage-data-by-batch-id.groovy")
    public void testDeleteForSalUsageDataByBatchId() {
        assertEquals(1,
            CollectionUtils.size(usageAuditRepository.findByUsageId("51e60822-5b4c-4fa8-9922-05e93065f216")));
        assertEquals(1,
            CollectionUtils.size(usageAuditRepository.findByUsageId("9793bfb8-2628-485a-9ceb-0d7652691a1b")));
        assertEquals(1,
            CollectionUtils.size(usageAuditRepository.findByUsageId("ad91d122-aedc-4b41-a09e-f7f055f5cb85")));
        usageAuditRepository.deleteForSalUsageDataByBatchId("129b0e2d-8c2a-4c66-8dcf-456106c823d8");
        assertEquals(1,
            CollectionUtils.size(usageAuditRepository.findByUsageId("51e60822-5b4c-4fa8-9922-05e93065f216")));
        assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId("9793bfb8-2628-485a-9ceb-0d7652691a1b")));
        assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId("ad91d122-aedc-4b41-a09e-f7f055f5cb85")));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-for-sal-usage-data-by-work-portion-ids.groovy")
    public void testDeleteForSalUsageDataByWorkPortionIds() {
        assertFalse(
            CollectionUtils.isEmpty(usageAuditRepository.findByUsageId("7a87ddf6-1a11-4f6f-8f52-96f520d2f859")));
        assertFalse(
            CollectionUtils.isEmpty(usageAuditRepository.findByUsageId("ebb0e28c-6c59-417d-9586-d252025e217d")));
        assertFalse(
            CollectionUtils.isEmpty(usageAuditRepository.findByUsageId("078a6760-61d6-4d4e-a283-a67f7766d80c")));
        usageAuditRepository.deleteForSalUsageDataByWorkPortionIds(Set.of("1101024IB2192"));
        assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId("7a87ddf6-1a11-4f6f-8f52-96f520d2f859")));
        assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId("ebb0e28c-6c59-417d-9586-d252025e217d")));
        assertFalse(
            CollectionUtils.isEmpty(usageAuditRepository.findByUsageId("078a6760-61d6-4d4e-a283-a67f7766d80c")));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-for-archived-by-batch-id.groovy")
    public void testDeleteForArchivedByBatchId() {
        assertEquals(1,
            CollectionUtils.size(usageAuditRepository.findByUsageId("422d33c0-4594-451e-a1ca-412c023299aa")));
        usageAuditRepository.deleteForArchivedByBatchId("56282dbc-2468-48d4-b926-93d3458a656a");
        assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId("422d33c0-4594-451e-a1ca-412c023299aa")));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-by-batch-id.groovy")
    public void testDeleteByUsageId() {
        assertEquals(1, CollectionUtils.size(usageAuditRepository.findByUsageId(USAGE_UID_1)));
        usageAuditRepository.deleteByUsageId(USAGE_UID_1);
        assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId(USAGE_UID_1)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-by-scenario-id.groovy")
    public void testDeleteByScenarioId() {
        assertEquals(1, CollectionUtils.size(usageAuditRepository.findByUsageId(USAGE_UID_3)));
        assertEquals(2, CollectionUtils.size(usageAuditRepository.findByUsageId(USAGE_UID_4)));
        usageAuditRepository.deleteByScenarioId("fccc8c31-5259-472a-a9ca-508a8ed1cbc0");
        assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId(USAGE_UID_3)));
        assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId(USAGE_UID_4)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-by-scenario-id.groovy")
    public void testDeleteForExcludedByScenarioId() {
        assertEquals(1, CollectionUtils.size(usageAuditRepository.findByUsageId(USAGE_UID_3)));
        assertEquals(2, CollectionUtils.size(usageAuditRepository.findByUsageId(USAGE_UID_4)));
        usageAuditRepository.deleteForExcludedByScenarioId("fccc8c31-5259-472a-a9ca-508a8ed1cbc0");
        assertEquals(1, CollectionUtils.size(usageAuditRepository.findByUsageId(USAGE_UID_3)));
        assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId(USAGE_UID_4)));
    }

    @Test
    @TestData(fileName = FIND_BATCHES_STATISTIC)
    public void testFindFasBatchesStatisticByBatchName() {
        List<BatchStatistic> statistics = usageAuditRepository.findBatchesStatisticByBatchNameAndDate(
            "FAS batch statistic", null);
        assertNotNull(statistics);
        assertEquals(1, statistics.size());
        assertFasBatchStatistics(statistics.get(0));
    }

    @Test
    @TestData(fileName = FIND_BATCHES_STATISTIC)
    public void testFindFasBatchesStatisticByBatchNameAndDate() {
        List<BatchStatistic> statistics = usageAuditRepository.findBatchesStatisticByBatchNameAndDate(
            "FAS batch statistic", LocalDate.of(2019, 4, 1));
        assertNotNull(statistics);
        assertEquals(1, statistics.size());
        assertFasBatchStatistics(statistics.get(0));
    }

    @Test
    @TestData(fileName = FIND_BATCHES_STATISTIC)
    public void testFindNtsBatchesStatisticByBatchName() {
        List<BatchStatistic> statistics = usageAuditRepository.findBatchesStatisticByBatchNameAndDate(
            "NTS batch statistic", null);
        assertNotNull(statistics);
        assertEquals(1, statistics.size());
        assertNtsBatchStatistics(statistics.get(0));
    }

    @Test
    @TestData(fileName = FIND_BATCHES_STATISTIC)
    public void testFindNtsBatchesStatisticByBatchNameAndDate() {
        List<BatchStatistic> statistics = usageAuditRepository.findBatchesStatisticByBatchNameAndDate(
            "NTS batch statistic", LocalDate.of(2019, 4, 1));
        assertNotNull(statistics);
        assertEquals(1, statistics.size());
        assertNtsBatchStatistics(statistics.get(0));
    }

    @Test
    @TestData(fileName = FIND_BATCHES_STATISTIC)
    public void testFindBatchesStatisticByDateFromAndDateTo() throws IOException {
        List<BatchStatistic> actualStatistics = usageAuditRepository.findBatchesStatisticByDateFromAndDateTo(
            LocalDate.of(2013, 1, 1), LocalDate.of(2050, 1, 1));
        assertNotNull(actualStatistics);
        assertEquals(3, actualStatistics.size());
        assertEquals(loadExpectedBatchStatisticsList(),
            actualStatistics);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-usage-statistic.groovy")
    public void testFindUsageStatistic() {
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
        usageAuditItem.setUsageId(USAGE_UID_1);
        usageAuditItem.setActionType(UsageActionTypeEnum.WORK_NOT_FOUND);
        usageAuditItem.setActionReason("Usage has no standard number and title");
        return usageAuditItem;
    }

    private List<BatchStatistic> loadExpectedBatchStatisticsList() throws IOException {
        String content = TestUtils.fileToString(this.getClass(), "json/batch_statistics_by_datefrom_dateto.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, new TypeReference<List<BatchStatistic>>() {
        });
    }

    private void assertFasBatchStatistics(BatchStatistic statistic) {
        assertNotNull(statistic);
        assertEquals("FAS batch statistic", statistic.getBatchName());
        assertEquals(10, statistic.getTotalCount());
        assertEquals(new BigDecimal("995.00"), statistic.getTotalAmount());
        assertEquals(2, statistic.getMatchedCount());
        assertEquals(new BigDecimal("190.00"), statistic.getMatchedAmount());
        assertEquals(new BigDecimal("20.00"), statistic.getMatchedPercent());
        assertEquals(1, statistic.getWorksNotFoundCount());
        assertEquals(new BigDecimal("55.00"), statistic.getWorksNotFoundAmount());
        assertEquals(AMOUNT_TEN, statistic.getWorksNotFoundPercent());
        assertEquals(1, statistic.getNtsWithdrawnCount());
        assertEquals(new BigDecimal("150.00"), statistic.getNtsWithdrawnAmount());
        assertEquals(AMOUNT_TEN, statistic.getNtsWithdrawnPercent());
        assertEquals(1, statistic.getRhNotFoundCount());
        assertEquals(new BigDecimal("80.00"), statistic.getRhNotFoundAmount());
        assertEquals(AMOUNT_TEN, statistic.getRhNotFoundPercent());
        assertEquals(1, statistic.getRhFoundCount());
        assertEquals(new BigDecimal("110.00"), statistic.getRhFoundAmount());
        assertEquals(AMOUNT_TEN, statistic.getRhFoundPercent());
        assertEquals(0, statistic.getEligibleCount());
        assertEquals(AMOUNT_ZERO, statistic.getEligibleAmount());
        assertEquals(AMOUNT_ZERO, statistic.getEligiblePercent());
        assertEquals(1, statistic.getSendForRaCount());
        assertEquals(new BigDecimal("80.00"), statistic.getSendForRaAmount());
        assertEquals(AMOUNT_TEN, statistic.getSendForRaPercent());
        assertEquals(5, statistic.getPaidCount());
        assertEquals(new BigDecimal("500.00"), statistic.getPaidAmount());
        assertEquals(new BigDecimal("50.00"), statistic.getPaidPercent());
    }

    private void assertNtsBatchStatistics(BatchStatistic statistic) {
        assertNotNull(statistic);
        assertEquals("NTS batch statistic", statistic.getBatchName());
        assertEquals(10, statistic.getTotalCount());
        assertEquals(new BigDecimal("995.00"), statistic.getTotalAmount());
        assertEquals(0, statistic.getMatchedCount());
        assertEquals(AMOUNT_ZERO, statistic.getMatchedAmount());
        assertEquals(AMOUNT_ZERO, statistic.getMatchedPercent());
        assertEquals(0, statistic.getWorksNotFoundCount());
        assertEquals(AMOUNT_ZERO, statistic.getWorksNotFoundAmount());
        assertEquals(AMOUNT_ZERO, statistic.getWorksNotFoundPercent());
        assertEquals(0, statistic.getNtsWithdrawnCount());
        assertEquals(AMOUNT_ZERO, statistic.getNtsWithdrawnAmount());
        assertEquals(AMOUNT_ZERO, statistic.getNtsWithdrawnPercent());
        assertEquals(0, statistic.getRhNotFoundCount());
        assertEquals(AMOUNT_ZERO, statistic.getRhNotFoundAmount());
        assertEquals(AMOUNT_ZERO, statistic.getRhNotFoundPercent());
        assertEquals(5, statistic.getRhFoundCount());
        assertEquals(new BigDecimal("500.00"), statistic.getRhFoundAmount());
        assertEquals(new BigDecimal("50.00"), statistic.getRhFoundPercent());
        assertEquals(4, statistic.getEligibleCount());
        assertEquals(new BigDecimal("400.00"), statistic.getEligibleAmount());
        assertEquals(new BigDecimal("40.00"), statistic.getEligiblePercent());
        assertEquals(0, statistic.getSendForRaCount());
        assertEquals(AMOUNT_ZERO, statistic.getSendForRaAmount());
        assertEquals(AMOUNT_ZERO, statistic.getSendForRaPercent());
        assertEquals(0, statistic.getPaidCount());
        assertEquals(AMOUNT_ZERO, statistic.getPaidAmount());
        assertEquals(AMOUNT_ZERO, statistic.getPaidPercent());
    }
}
