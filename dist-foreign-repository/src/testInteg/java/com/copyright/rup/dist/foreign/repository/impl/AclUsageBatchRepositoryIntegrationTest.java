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
        assertNotNull(actualUsageBatch);
        assertEquals(usageBatch.getId(), actualUsageBatch.getId());
        assertEquals(usageBatch.getName(), actualUsageBatch.getName());
        assertEquals(usageBatch.getDistributionPeriod(), actualUsageBatch.getDistributionPeriod());
        assertEquals(usageBatch.getPeriods(), actualUsageBatch.getPeriods());
        assertEquals(usageBatch.getEditable(), actualUsageBatch.getEditable());
        assertEquals(usageBatch, actualUsageBatch);
    }

    private AclUsageBatch buildAclUsageBatch() {
        AclUsageBatch usageBatch = new AclUsageBatch();
        usageBatch.setId(ACL_USAGE_BATCH_ID);
        usageBatch.setName(ACL_USAGE_BATCH_NAME);
        usageBatch.setDistributionPeriod(202112);
        usageBatch.setPeriods(Sets.newHashSet(202106, 202112));
        usageBatch.setEditable(true);
        return usageBatch;
    }
}
