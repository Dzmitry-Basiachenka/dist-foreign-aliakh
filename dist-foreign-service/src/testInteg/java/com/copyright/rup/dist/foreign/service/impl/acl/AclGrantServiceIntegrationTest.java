package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantService;
import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * Verifies correctness getting and creates ACL grants.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/31/2022
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
@Transactional
public class AclGrantServiceIntegrationTest {

    private static final String GRANT = "GRANT";
    private static final String PRINT = "PRINT";
    private static final String DIGITAL = "DIGITAL";
    private static final String SYSTEM_TITLE = "Red Riding Hood's maths adventure";
    private static final String RMS_RESPONSE = "acl/grants/rms_grants_response.json";
    private static final String USER_NAME = "user@copyright.com";

    @Autowired
    private IAclGrantService grantService;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;
    @Autowired
    private ServiceTestHelper testHelper;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    public void testCreateAclGrantDetails() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("acl/grants/rms_grants_request_1.json", RMS_RESPONSE);
        testHelper.expectGetRmsRights("acl/grants/rms_grants_request_2.json", RMS_RESPONSE);
        testHelper.expectGetRmsRights("acl/grants/rms_grants_request_3.json", RMS_RESPONSE);
        testHelper.expectGetRmsRights("acl/grants/rms_grants_request_4.json", RMS_RESPONSE);
        testHelper.expectPrmIneligibleParentCall("acl/ineligible.rightsholders/rightsholder_parents.json");
        testHelper.expectPrmIneligibleCall("d145f685-994e-4b47-8748-c1ad375da3f9", "ACLPRINT",
            "acl/ineligible.rightsholders/ineligible_rightsholder_print.json");
        List<AclGrantDetail> actualDetails = grantService.createAclGrantDetails(buildGrantSet(),
            createWrWrkInstToSystemTitleMap(), USER_NAME);
        assertEquals(8, actualDetails.size());
        List<AclGrantDetail> expectedDetails = buildAclGrantDetails();
        IntStream.range(0, actualDetails.size()).forEach(i ->
            verifyAclGrantDetails(expectedDetails.get(i), actualDetails.get(i)));
    }

    private void verifyAclGrantDetails(AclGrantDetail expectedDetail, AclGrantDetail actualDetail) {
        assertNotNull(actualDetail.getId());
        assertEquals(expectedDetail.getGrantSetId(), actualDetail.getGrantSetId());
        assertEquals(expectedDetail.getEligible(), actualDetail.getEligible());
        assertEquals(expectedDetail.getRhAccountNumber(), actualDetail.getRhAccountNumber());
        assertEquals(expectedDetail.getTypeOfUseStatus(), actualDetail.getTypeOfUseStatus());
        assertEquals(expectedDetail.getWrWrkInst(), actualDetail.getWrWrkInst());
        assertEquals(expectedDetail.getTypeOfUse(), actualDetail.getTypeOfUse());
        assertEquals(expectedDetail.getGrantStatus(), actualDetail.getGrantStatus());
        assertEquals(expectedDetail.getSystemTitle(), actualDetail.getSystemTitle());
        assertEquals(expectedDetail.getCreateUser(), actualDetail.getCreateUser());
        assertEquals(expectedDetail.getUpdateUser(), actualDetail.getUpdateUser());
        assertFalse(actualDetail.getManualUploadFlag());
    }

    private List<AclGrantDetail> buildAclGrantDetails() {
        return Arrays.asList(
            buildAclGrantDetail(
                DIGITAL, 1000014080L, 136797639L, "Different RH", "Farewell to the leftist working class", true),
            buildAclGrantDetail(
                PRINT, 1000002760L, 136797639L, "Different RH", "Farewell to the leftist working class", false),
            buildAclGrantDetail(
                PRINT, 1000004023L, 159246556L, "Print&Digital", "Embracing watershed politics", false),
            buildAclGrantDetail(
                DIGITAL, 1000004023L, 159246556L, "Print&Digital", "Embracing watershed politics", true),
            buildAclGrantDetail(DIGITAL, 2000017000L, 309812565L, "Digital Only", SYSTEM_TITLE, true),
            buildAclGrantDetail(PRINT, 1000025853L, 144114260L, "Print Only", "I've discovered energy!", true),
            buildAclGrantDetail(DIGITAL, 600009865L, 4875964215L, "Digital Only", SYSTEM_TITLE, true),
            buildAclGrantDetail(PRINT, 700009877L, 4875964316L, "Print Only", SYSTEM_TITLE, true)
        );
    }

    private Map<Long, String> createWrWrkInstToSystemTitleMap() {
        Map<Long, String> wrWrkInstToSystemTitles = new TreeMap<>(Comparator.naturalOrder());
        wrWrkInstToSystemTitles.put(144114260L, "I've discovered energy!");
        wrWrkInstToSystemTitles.put(309812565L, SYSTEM_TITLE);
        wrWrkInstToSystemTitles.put(159246556L, "Embracing watershed politics");
        wrWrkInstToSystemTitles.put(136797639L, "Farewell to the leftist working class");
        wrWrkInstToSystemTitles.put(12345678L, "Autologous and cancer stem cell gene therapy");
        wrWrkInstToSystemTitles.put(578123123L, SYSTEM_TITLE);
        wrWrkInstToSystemTitles.put(4875964215L, SYSTEM_TITLE);
        wrWrkInstToSystemTitles.put(4875964316L, SYSTEM_TITLE);
        wrWrkInstToSystemTitles.put(4875964317L, SYSTEM_TITLE);
        wrWrkInstToSystemTitles.put(4875964318L, SYSTEM_TITLE);
        return wrWrkInstToSystemTitles;
    }

    private AclGrantDetail buildAclGrantDetail(String typeOfUse, Long rhAccountNumber, Long wrWrkInst,
                                               String typeOfUseStatus, String systemTitle, boolean eligible) {
        AclGrantDetail aclGrantDetail = new AclGrantDetail();
        aclGrantDetail.setGrantSetId("c72b8f6a-0923-43f8-b0d6-797de523de2d");
        aclGrantDetail.setEligible(eligible);
        aclGrantDetail.setTypeOfUse(typeOfUse);
        aclGrantDetail.setRhAccountNumber(rhAccountNumber);
        aclGrantDetail.setWrWrkInst(wrWrkInst);
        aclGrantDetail.setTypeOfUseStatus(typeOfUseStatus);
        aclGrantDetail.setSystemTitle(systemTitle);
        aclGrantDetail.setGrantStatus(GRANT);
        aclGrantDetail.setCreateUser(USER_NAME);
        aclGrantDetail.setUpdateUser(USER_NAME);
        return aclGrantDetail;
    }

    private AclGrantSet buildGrantSet() {
        AclGrantSet aclGrantSet = new AclGrantSet();
        aclGrantSet.setId("c72b8f6a-0923-43f8-b0d6-797de523de2d");
        aclGrantSet.setGrantPeriod(202106);
        aclGrantSet.setLicenseType("ACL");
        aclGrantSet.setName("Grand set name");
        aclGrantSet.setCreateUser(USER_NAME);
        aclGrantSet.setUpdateUser(USER_NAME);
        aclGrantSet.setEditable(true);
        return aclGrantSet;
    }
}
