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
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclGrantSetRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "acl-grant-set-repository-integration-test/";
    private static final String ACL_GRANT_SET_ID = "c598a928-2857-403c-bebc-9073ce56dbcc";
    private static final String ACL_GRANT_SET_NAME = "ACL Grant Set 2021";
    private static final String LICENSE_TYPE = "ACL";

    @Autowired
    private IAclGrantSetRepository aclGrantSetRepository;

    @Test
    @TestData(fileName = "rollback-only.groovy")
    public void testInsert() {
        AclGrantSet grantSet = buildAclGrantSet(ACL_GRANT_SET_ID, ACL_GRANT_SET_NAME, 202112, LICENSE_TYPE);
        aclGrantSetRepository.insert(grantSet);
        AclGrantSet actualGrantSet = aclGrantSetRepository.findById(ACL_GRANT_SET_ID);
        assertNotNull(actualGrantSet);
        verifyGrantSet(grantSet, actualGrantSet);
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

    @Test
    @TestData(fileName = "rollback-only.groovy")
    public void testDeleteById() {
        AclGrantSet grantSet = buildAclGrantSet(ACL_GRANT_SET_ID, ACL_GRANT_SET_NAME, 202112, LICENSE_TYPE);
        aclGrantSetRepository.insert(grantSet);
        assertEquals(1, aclGrantSetRepository.findAll().size());
        aclGrantSetRepository.deleteById(grantSet.getId());
        assertEquals(0, aclGrantSetRepository.findAll().size());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-grant-set-by-period-and-license-type.groovy")
    public void testFindGrantSetsByLicenseTypeAndPeriod() {
        AclGrantSet expectedGrantSet1 =
            buildAclGrantSet("9950ea35-41a4-48f5-9d14-b2182f771f66", "ACL Grant Set_1", 202212, LICENSE_TYPE);
        AclGrantSet expectedGrantSet2 =
            buildAclGrantSet("1e06dd5e-6ab1-442a-95fc-65dec5a61658", "ACL Grant Set_2", 202212, LICENSE_TYPE);
        AclGrantSet expectedGrantSet3 =
            buildAclGrantSet("f7a5bc79-2be2-4384-a113-34155828a4aa", "ACL Grant Set_3", 202212, LICENSE_TYPE);
        expectedGrantSet3.setEditable(false);
        List<AclGrantSet> grantSets =
            aclGrantSetRepository.findGrantSetsByLicenseTypeAndPeriod(LICENSE_TYPE, 202212, true);
        assertEquals(3, grantSets.size());
        verifyGrantSet(expectedGrantSet1, grantSets.get(0));
        verifyGrantSet(expectedGrantSet2, grantSets.get(1));
        verifyGrantSet(expectedGrantSet3, grantSets.get(2));
        grantSets = aclGrantSetRepository.findGrantSetsByLicenseTypeAndPeriod(LICENSE_TYPE, 202212, false);
        assertEquals(1, grantSets.size());
        verifyGrantSet(expectedGrantSet3, grantSets.get(0));
    }

    private void verifyGrantSet(AclGrantSet expectedGrantSet, AclGrantSet actualGrantSet) {
        assertEquals(expectedGrantSet.getId(), actualGrantSet.getId());
        assertEquals(expectedGrantSet.getName(), actualGrantSet.getName());
        assertEquals(expectedGrantSet.getGrantPeriod(), actualGrantSet.getGrantPeriod());
        assertEquals(expectedGrantSet.getPeriods(), actualGrantSet.getPeriods());
        assertEquals(expectedGrantSet.getLicenseType(), actualGrantSet.getLicenseType());
        assertEquals(expectedGrantSet.getEditable(), actualGrantSet.getEditable());
        assertEquals(expectedGrantSet, actualGrantSet);
    }

    private AclGrantSet buildAclGrantSet(String id, String name, Integer period, String licenseType) {
        AclGrantSet grantSet = new AclGrantSet();
        grantSet.setId(id);
        grantSet.setName(name);
        grantSet.setGrantPeriod(period);
        grantSet.setPeriods(Sets.newHashSet(202106, 202112));
        grantSet.setLicenseType(licenseType);
        grantSet.setEditable(true);
        return grantSet;
    }
}
