package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantSetRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

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

    private static final String ACL_GRANT_SET_ID = "c598a928-2857-403c-bebc-9073ce56dbcc";

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

    private AclGrantSet buildAclGrantSet() {
        AclGrantSet grantSet = new AclGrantSet();
        grantSet.setId(ACL_GRANT_SET_ID);
        grantSet.setName("ACL Grant Set 2021");
        grantSet.setGrantPeriod(202112);
        grantSet.setPeriods(Arrays.asList(202106, 202112));
        grantSet.setLicenseType("ACL");
        grantSet.setEditable(true);
        return grantSet;
    }
}
