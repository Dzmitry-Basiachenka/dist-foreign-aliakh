package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantSetRepository;

import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link AclGrantSetRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
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
public class AclGrantSetRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "acl-grant-set-repository-integration-test/";
    private static final String ACL_GRANT_SET_ID = "c598a928-2857-403c-bebc-9073ce56dbcc";
    private static final String ACL_GRANT_SET_NAME = "ACL Grant Set 2021";

    @Autowired
    private IAclGrantSetRepository aclGrantSetRepository;

    @Test
    @TestData(fileName = "rollback-only.groovy")
    public void testInsert() {
        AclGrantSet grantSet = buildAclGrantSet();
        aclGrantSetRepository.insert(grantSet);
        AclGrantSet actualGrantSet = aclGrantSetRepository.findById(ACL_GRANT_SET_ID);
        assertNotNull(actualGrantSet);
        assertEquals(grantSet.getId(), actualGrantSet.getId());
        assertEquals(grantSet.getName(), actualGrantSet.getName());
        assertEquals(grantSet.getGrantPeriod(), actualGrantSet.getGrantPeriod());
        assertEquals(grantSet.getPeriods(), actualGrantSet.getPeriods());
        assertEquals(grantSet.getLicenseType(), actualGrantSet.getLicenseType());
        assertEquals(grantSet.getEditable(), actualGrantSet.getEditable());
        assertEquals(grantSet, actualGrantSet);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "is-grant-set-exist.groovy")
    public void testIsGrantSetExist() {
        assertTrue(aclGrantSetRepository.isGrantSetExist(ACL_GRANT_SET_NAME));
        assertFalse(aclGrantSetRepository.isGrantSetExist("ACL Grant Set 2022"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-all.groovy")
    public void testFindAll() {
        List<AclGrantSet> grantSets = aclGrantSetRepository.findAll();
        assertEquals(2, grantSets.size());
        assertEquals("8e5930c0-318c-4cf3-bde7-452a2d572f03", grantSets.get(0).getId());
        assertEquals("b4f502c7-583f-4bb8-aa37-7d60db3a9cb1", grantSets.get(1).getId());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-grant-periods.groovy")
    public void testFindGrantPeriods() {
        List<Integer> grantSets = aclGrantSetRepository.findGrantPeriods();
        assertEquals(3, grantSets.size());
        assertEquals(Arrays.asList(202212, 202112, 202012), grantSets);
    }

    private AclGrantSet buildAclGrantSet() {
        AclGrantSet grantSet = new AclGrantSet();
        grantSet.setId(ACL_GRANT_SET_ID);
        grantSet.setName(ACL_GRANT_SET_NAME);
        grantSet.setGrantPeriod(202112);
        grantSet.setPeriods(Sets.newHashSet(202106, 202112));
        grantSet.setLicenseType("ACL");
        grantSet.setEditable(true);
        return grantSet;
    }
}
