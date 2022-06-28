package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageBatchRepository;

import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link AclUsageBatchRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclUsageBatchRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "acl-usage-batch-repository-integration-test/";
    private static final String ACL_USAGE_BATCH_ID = "381b1cce-6bc9-4be9-9a02-3eb3750ae682";
    private static final String ACL_USAGE_BATCH_NAME = "ACL Usage Batch 2021";

    @Autowired
    private IAclUsageBatchRepository aclUsageBatchRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "is-acl-usage-batch-exist.groovy")
    public void testIsAclUsageBatchExist() {
        assertTrue(aclUsageBatchRepository.isAclUsageBatchExist(ACL_USAGE_BATCH_NAME));
        assertFalse(aclUsageBatchRepository.isAclUsageBatchExist("ACL Usage Batch 2022"));
    }

    @Test
    @TestData(fileName = "rollback-only.groovy")
    public void testInsert() {
        AclUsageBatch usageBatch = buildAclUsageBatch();
        aclUsageBatchRepository.insert(usageBatch);
        AclUsageBatch actualUsageBatch = aclUsageBatchRepository.findById(ACL_USAGE_BATCH_ID);
        verifyAclUsageBatch(usageBatch, actualUsageBatch);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-all.groovy")
    public void testFindAll() {
        AclUsageBatch aclUsageBatch1 = buildAclUsageBatch("dd559563-379d-4632-abea-922d2821746d",
            "ACL Usage Batch 2022", 202212, Sets.newHashSet(202206, 202212), true);
        AclUsageBatch aclUsageBatch2 = buildAclUsageBatch("446fba70-c15b-45ae-b53d-ba0de3dad0b5",
            "ACL Usage Batch 2021", 202112, Sets.newHashSet(202106, 202112), false);
        List<AclUsageBatch> usageBatches = aclUsageBatchRepository.findAll();
        assertEquals(2, usageBatches.size());
        verifyAclUsageBatch(aclUsageBatch1, usageBatches.get(0));
        verifyAclUsageBatch(aclUsageBatch2, usageBatches.get(1));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-usage-batch-by-period.groovy")
    public void testFindUsageBatchesByPeriod() {
        AclUsageBatch expectedUsageBatch1 = buildAclUsageBatch("fb5fb8ce-26e8-4417-97db-9a5116ba4061",
            "ACL Usage Batch 2022", 202212, Sets.newHashSet(202206, 202212), true);
        AclUsageBatch expectedUsageBatch2 = buildAclUsageBatch("0825074e-f5fc-4eb6-a4b8-a452e63f1aeb",
            "ACL Usage Batch 2021", 202212, Sets.newHashSet(202106, 202112), false);
        List<AclUsageBatch> usageBatches = aclUsageBatchRepository.findUsageBatchesByPeriod(202212, true);
        assertEquals(1, usageBatches.size());
        verifyAclUsageBatch(expectedUsageBatch1, usageBatches.get(0));
        usageBatches = aclUsageBatchRepository.findUsageBatchesByPeriod(202212, false);
        assertEquals(1, usageBatches.size());
        verifyAclUsageBatch(expectedUsageBatch2, usageBatches.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-periods.groovy")
    public void testFindAllPeriods() {
        List<Integer> periods = aclUsageBatchRepository.findPeriods();
        assertEquals(2, periods.size());
        assertEquals(Integer.valueOf(202212), periods.get(0));
        assertEquals(Integer.valueOf(202112), periods.get(1));
    }

    private AclUsageBatch buildAclUsageBatch() {
        return buildAclUsageBatch(ACL_USAGE_BATCH_ID, ACL_USAGE_BATCH_NAME, 202112, Sets.newHashSet(202106, 202112),
            true);
    }

    private AclUsageBatch buildAclUsageBatch(String id, String name, Integer distributionPeriod, Set<Integer> periods,
                                             boolean isEditable) {
        AclUsageBatch usageBatch = new AclUsageBatch();
        usageBatch.setId(id);
        usageBatch.setName(name);
        usageBatch.setDistributionPeriod(distributionPeriod);
        usageBatch.setPeriods(periods);
        usageBatch.setEditable(isEditable);
        return usageBatch;
    }

    private void verifyAclUsageBatch(AclUsageBatch expectedUsageBatch, AclUsageBatch actualUsageBatch) {
        assertNotNull(actualUsageBatch);
        assertEquals(expectedUsageBatch.getId(), actualUsageBatch.getId());
        assertEquals(expectedUsageBatch.getName(), actualUsageBatch.getName());
        assertEquals(expectedUsageBatch.getDistributionPeriod(), actualUsageBatch.getDistributionPeriod());
        assertEquals(expectedUsageBatch.getPeriods(), actualUsageBatch.getPeriods());
        assertEquals(expectedUsageBatch.getEditable(), actualUsageBatch.getEditable());
        assertEquals(expectedUsageBatch, actualUsageBatch);
    }
}
