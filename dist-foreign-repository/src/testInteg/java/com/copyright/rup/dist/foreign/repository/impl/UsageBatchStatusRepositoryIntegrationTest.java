package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchStatusRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Verifies {@link WorkClassificationRepository}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 02/10/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=usage-batch-status-repository-test-data-init.groovy"})
@Transactional
public class UsageBatchStatusRepositoryIntegrationTest {

    @Autowired
    private IUsageBatchStatusRepository usageBatchStatusRepository;

    @Test
    public void testGetUsageBatchStatusesFas() {
        List<UsageBatchStatus> usageBatchStatuses = usageBatchStatusRepository.getUsageBatchStatusesFas();
        assertEquals(2, usageBatchStatuses.size());
        assertUsageBatchStatus(
            buildUsageBatchStatusFas("FAS completed batch", 8, "Completed", 0, 0, 1, 0, 1, 0, 2, 1, 3),
            usageBatchStatuses.get(0));
        assertUsageBatchStatus(
            buildUsageBatchStatusFas("FAS in progress batch", 9, "In Progress", 1, 1, 2, 1, 0, 1, 1, 0, 2),
            usageBatchStatuses.get(1));
    }

    private void assertUsageBatchStatus(UsageBatchStatus expected, UsageBatchStatus actual) {
        assertEquals(expected.getBatchName(), actual.getBatchName());
        assertEquals(expected.getTotalCount(), actual.getTotalCount());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getNewCount(), actual.getNewCount());
        assertEquals(expected.getWorkFoundCount(), actual.getWorkFoundCount());
        assertEquals(expected.getWorkNotFoundCount(), actual.getWorkNotFoundCount());
        assertEquals(expected.getNtsWithdrawnCount(), actual.getNtsWithdrawnCount());
        assertEquals(expected.getWorkResearchCount(), actual.getWorkResearchCount());
        assertEquals(expected.getRhFoundCount(), actual.getRhFoundCount());
        assertEquals(expected.getRhNotFoundCount(), actual.getRhNotFoundCount());
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
}
