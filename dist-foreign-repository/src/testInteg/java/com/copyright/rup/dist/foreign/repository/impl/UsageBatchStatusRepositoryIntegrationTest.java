package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchStatusRepository;

import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UsageBatchStatusRepository}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 02/10/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class UsageBatchStatusRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "usage-batch-status-repository-integration-test/";
    private static final String COMPLETED_STATUS = "COMPLETED";
    private static final String IN_PROGRESS_STATUS = "IN_PROGRESS";

    @Autowired
    private IUsageBatchStatusRepository usageBatchStatusRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "find-usage-batch-statuses-fas.groovy")
    public void testFindUsageBatchStatusesFas() {
        List<UsageBatchStatus> usageBatchStatuses =
            usageBatchStatusRepository.findUsageBatchStatusesFas(
                Sets.newHashSet("cf56b889-82fe-4990-b111-9c56ce986281", "515a78e7-2a92-4b15-859a-fd9f70e80982"));
        assertEquals(2, usageBatchStatuses.size());
        assertUsageBatchStatus(
            buildUsageBatchStatusFas("FAS completed batch", 8, COMPLETED_STATUS, 0, 0, 1, 0, 1, 0, 2, 1, 3),
            usageBatchStatuses.get(0));
        assertUsageBatchStatus(
            buildUsageBatchStatusFas("FAS in progress batch", 9, IN_PROGRESS_STATUS, 1, 1, 2, 1, 0, 1, 1, 0, 2),
            usageBatchStatuses.get(1));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-usage-batch-statuses-nts.groovy")
    public void testFindUsageBatchStatusesNts() {
        List<UsageBatchStatus> usageBatchStatuses =
            usageBatchStatusRepository.findUsageBatchStatusesNts(
                Sets.newHashSet("359de82f-374b-4d53-88ab-0be3982b22aa", "a34417b5-12c1-48e2-9aed-d3861b49545b"));
        assertEquals(2, usageBatchStatuses.size());
        assertUsageBatchStatus(
            buildUsageBatchStatusNts("NTS completed batch", 5, COMPLETED_STATUS, 2, 0, 0, 0, 0, 1, 2),
            usageBatchStatuses.get(0));
        assertUsageBatchStatus(
            buildUsageBatchStatusNts("NTS in progress batch", 9, IN_PROGRESS_STATUS, 3, 1, 2, 1, 1, 0, 1),
            usageBatchStatuses.get(1));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-usage-batch-statuses-aacl.groovy")
    public void testFindUsageBatchStatusesAacl() {
        List<UsageBatchStatus> usageBatchStatuses =
            usageBatchStatusRepository.findUsageBatchStatusesAacl(
                Sets.newHashSet("f77ab6ea-56d3-45dc-8926-9a8cd448f229", "3d7c9de0-3d14-42e4-a500-fb10344a77ff"));
        assertEquals(2, usageBatchStatuses.size());
        assertUsageBatchStatus(
            buildUsageBatchStatusAacl("AACL completed batch", 8, COMPLETED_STATUS, 3, 0, 1, 0, 0, 2, 2),
            usageBatchStatuses.get(0));
        assertUsageBatchStatus(
            buildUsageBatchStatusAacl("AACL in progress batch", 7, IN_PROGRESS_STATUS, 1, 1, 0, 2, 2, 0, 1),
            usageBatchStatuses.get(1));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-usage-batch-statuses-sal.groovy")
    public void testFindUsageBatchStatusesSal() {
        List<UsageBatchStatus> usageBatchStatuses =
            usageBatchStatusRepository.findUsageBatchStatusesSal(
                Sets.newHashSet("2a9ac95c-a44d-436c-b754-d69bb7e63993", "b324671c-1ae2-4d1f-9dce-d9b80900df55"));
        assertEquals(2, usageBatchStatuses.size());
        assertUsageBatchStatus(
            buildUsageBatchStatusSal("SAL completed batch", 8, COMPLETED_STATUS, 0, 2, 0, 1, 1, 0, 4),
            usageBatchStatuses.get(0));
        assertUsageBatchStatus(
            buildUsageBatchStatusSal("SAL in progress batch", 9, IN_PROGRESS_STATUS, 2, 1, 1, 0, 2, 1, 2),
            usageBatchStatuses.get(1));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-batch-ids-eligible-for-statistic.groovy")
    public void testFindFasUsageBatchIdsEligibleForStatistic() {
        assertEquals(Collections.singleton("515a78e7-2a92-4b15-859a-fd9f70e80982"),
            usageBatchStatusRepository.findFasUsageBatchIdsEligibleForStatistic("FAS", LocalDate.of(2021, 2, 14)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-batch-ids-eligible-for-statistic.groovy")
    public void testFindUsageBatchIdsEligibleForStatistic() {
        assertEquals(Collections.singleton("359de82f-374b-4d53-88ab-0be3982b22aa"),
            usageBatchStatusRepository.findUsageBatchIdsEligibleForStatistic("NTS", LocalDate.of(2021, 2, 14)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "is-batch-processing-complete.groovy")
    public void testIsBatchProcessingCompleted() {
        assertTrue(usageBatchStatusRepository.isBatchProcessingCompleted("515a78e7-2a92-4b15-859a-fd9f70e80982",
            Sets.newHashSet(UsageStatusEnum.NEW, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_FOUND)));
        assertFalse(usageBatchStatusRepository.isBatchProcessingCompleted("a34417b5-12c1-48e2-9aed-d3861b49545b",
            Sets.newHashSet(UsageStatusEnum.WORK_FOUND, UsageStatusEnum.NON_STM_RH, UsageStatusEnum.US_TAX_COUNTRY,
                UsageStatusEnum.RH_FOUND)));
    }

    private void assertUsageBatchStatus(UsageBatchStatus expected, UsageBatchStatus actual) {
        assertEquals(expected.getBatchName(), actual.getBatchName());
        assertEquals(expected.getTotalCount(), actual.getTotalCount());
        assertEquals(expected.getExcludedCount(), actual.getExcludedCount());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getNewCount(), actual.getNewCount());
        assertEquals(expected.getWorkFoundCount(), actual.getWorkFoundCount());
        assertEquals(expected.getWorkNotFoundCount(), actual.getWorkNotFoundCount());
        assertEquals(expected.getNtsWithdrawnCount(), actual.getNtsWithdrawnCount());
        assertEquals(expected.getWorkResearchCount(), actual.getWorkResearchCount());
        assertEquals(expected.getRhFoundCount(), actual.getRhFoundCount());
        assertEquals(expected.getNonStmRhCount(), actual.getNonStmRhCount());
        assertEquals(expected.getUsTaxCountryCount(), actual.getUsTaxCountryCount());
        assertEquals(expected.getUnclassifiedCount(), actual.getUnclassifiedCount());
        assertEquals(expected.getRhNotFoundCount(), actual.getRhNotFoundCount());
        assertEquals(expected.getWorkNotGrantedCount(), actual.getWorkNotGrantedCount());
        assertEquals(expected.getSentForRaCount(), actual.getSentForRaCount());
        assertEquals(expected.getEligibleCount(), actual.getEligibleCount());
    }

    private UsageBatchStatus buildUsageBatchStatusFas(String name, int totalCount, String status, int newCount,
                                                      int workFoundCount, int workNotFoundCount, int ntsWithdrawnCount,
                                                      int workResearchCount, int rhFoundCount, int rhNotFoundCount,
                                                      int sentForRaCount, int eligibleCount) {
        UsageBatchStatus batchStatus = new UsageBatchStatus();
        batchStatus.setBatchName(name);
        batchStatus.setTotalCount(totalCount);
        batchStatus.setStatus(status);
        batchStatus.setNewCount(newCount);
        batchStatus.setWorkFoundCount(workFoundCount);
        batchStatus.setWorkNotFoundCount(workNotFoundCount);
        batchStatus.setNtsWithdrawnCount(ntsWithdrawnCount);
        batchStatus.setWorkResearchCount(workResearchCount);
        batchStatus.setRhFoundCount(rhFoundCount);
        batchStatus.setRhNotFoundCount(rhNotFoundCount);
        batchStatus.setSentForRaCount(sentForRaCount);
        batchStatus.setEligibleCount(eligibleCount);
        return batchStatus;
    }

    private UsageBatchStatus buildUsageBatchStatusNts(String name, int totalCount, String status, int excludedCount,
                                                      int workFoundCount, int rhFoundCount, int nonStmRhCount,
                                                      int usTaxCountryCount, int unclassifiedCount, int eligibleCount) {
        UsageBatchStatus batchStatus = new UsageBatchStatus();
        batchStatus.setBatchName(name);
        batchStatus.setTotalCount(totalCount);
        batchStatus.setStatus(status);
        batchStatus.setExcludedCount(excludedCount);
        batchStatus.setWorkFoundCount(workFoundCount);
        batchStatus.setRhFoundCount(rhFoundCount);
        batchStatus.setNonStmRhCount(nonStmRhCount);
        batchStatus.setUsTaxCountryCount(usTaxCountryCount);
        batchStatus.setUnclassifiedCount(unclassifiedCount);
        batchStatus.setEligibleCount(eligibleCount);
        return batchStatus;
    }

    private UsageBatchStatus buildUsageBatchStatusAacl(String name, int totalCount, String status, int excludedCount,
                                                       int newCount, int workNotFoundCount, int workFoundCount,
                                                       int rhFoundCount, int workResearchCount, int eligibleCount) {
        UsageBatchStatus batchStatus = new UsageBatchStatus();
        batchStatus.setBatchName(name);
        batchStatus.setTotalCount(totalCount);
        batchStatus.setStatus(status);
        batchStatus.setExcludedCount(excludedCount);
        batchStatus.setNewCount(newCount);
        batchStatus.setWorkNotFoundCount(workNotFoundCount);
        batchStatus.setWorkFoundCount(workFoundCount);
        batchStatus.setRhFoundCount(rhFoundCount);
        batchStatus.setWorkResearchCount(workResearchCount);
        batchStatus.setEligibleCount(eligibleCount);
        return batchStatus;
    }

    private UsageBatchStatus buildUsageBatchStatusSal(String name, int totalCount, String status, int newCount,
                                                      int workNotFoundCount, int workFoundCount,
                                                      int workNotGrantedCount, int rhNotFoundCount, int rhFoundCount,
                                                      int eligibleCount) {
        UsageBatchStatus batchStatus = new UsageBatchStatus();
        batchStatus.setBatchName(name);
        batchStatus.setTotalCount(totalCount);
        batchStatus.setStatus(status);
        batchStatus.setNewCount(newCount);
        batchStatus.setWorkNotFoundCount(workNotFoundCount);
        batchStatus.setWorkFoundCount(workFoundCount);
        batchStatus.setWorkNotGrantedCount(workNotGrantedCount);
        batchStatus.setRhNotFoundCount(rhNotFoundCount);
        batchStatus.setRhFoundCount(rhFoundCount);
        batchStatus.setEligibleCount(eligibleCount);
        return batchStatus;
    }
}
