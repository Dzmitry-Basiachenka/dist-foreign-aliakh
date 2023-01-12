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
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
@Transactional
public class AclGrantServiceIntegrationTest {

    private static final String GRANT = "GRANT";
    private static final String PRINT = "PRINT";
    private static final String DIGITAL = "DIGITAL";
    private static final String SYSTEM_TITLE_1 = "Red Riding Hood's maths adventure";
    private static final String SYSTEM_TITLE_2 = "Embracing watershed politics";
    private static final String SYSTEM_TITLE_3 = "Farewell to the leftist working class";
    private static final String SYSTEM_TITLE_4 = "I've discovered energy!";
    private static final String RMS_RESPONSE = "acl/grants/rms_grants_response.json";
    private static final String USER_NAME = "user@copyright.com";
    private static final String DIFFERENT_RH = "Different RH";
    private static final String PRINT_DIGITAL = "Print&Digital";
    private static final String DIGITAL_ONLY = "Digital Only";
    private static final String PRINT_ONLY = "Print Only";
    private static final Long WR_WRK_INST_1 = 136797639L;
    private static final Long WR_WRK_INST_2 = 159246556L;
    private static final Long WR_WRK_INST_3 = 309812565L;
    private static final Long WR_WRK_INST_4 = 144114260L;
    private static final Long WR_WRK_INST_5 = 4875964215L;
    private static final Long WR_WRK_INST_6 = 4875964316L;
    private static final Long RH_ACCOUNT_1 = 1000014080L;
    private static final Long RH_ACCOUNT_2 = 1000002760L;
    private static final Long RH_ACCOUNT_3 = 1000004023L;
    private static final Long RH_ACCOUNT_4 = 2000017000L;
    private static final Long RH_ACCOUNT_5 = 1000025853L;
    private static final Long RH_ACCOUNT_6 = 600009865L;
    private static final Long RH_ACCOUNT_7 = 700009877L;

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
    public void testCreateAclGrantDetailsWithPrintIneligible() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("acl/grants/rms_grants_request_1.json", RMS_RESPONSE);
        testHelper.expectGetRmsRights("acl/grants/rms_grants_request_2.json", RMS_RESPONSE);
        testHelper.expectGetRmsRights("acl/grants/rms_grants_request_3.json", RMS_RESPONSE);
        testHelper.expectGetRmsRights("acl/grants/rms_grants_request_4.json", RMS_RESPONSE);
        testHelper.expectPrmIneligibleParentCall("acl/ineligible.rightsholders/rightsholder_parents_acl_print.json");
        testHelper.expectPrmIneligibleCall("d145f685-994e-4b47-8748-c1ad375da3f9", "ACLPRINT",
            "acl/ineligible.rightsholders/ineligible_rightsholder_print.json");
        List<AclGrantDetail> actualDetails = grantService.createAclGrantDetails(buildGrantSet(),
            createWrWrkInstToSystemTitleMap(), USER_NAME);
        assertEquals(8, actualDetails.size());
        List<AclGrantDetail> expectedDetails = buildAclGrantDetails();
        IntStream.range(0, actualDetails.size()).forEach(i ->
            verifyAclGrantDetails(expectedDetails.get(i), actualDetails.get(i)));
    }

    @Test
    public void testCreateNonEligibleAclGrantDetails() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("acl/grants/rms_grants_request_1.json", RMS_RESPONSE);
        testHelper.expectGetRmsRights("acl/grants/rms_grants_request_2.json", RMS_RESPONSE);
        testHelper.expectGetRmsRights("acl/grants/rms_grants_request_3.json", RMS_RESPONSE);
        testHelper.expectGetRmsRights("acl/grants/rms_grants_request_4.json", RMS_RESPONSE);
        testHelper.expectPrmIneligibleParentCall("acl/ineligible.rightsholders/rightsholder_parents_acl.json");
        testHelper.expectPrmIneligibleCall("4ae6bd83-07f0-44fe-836c-af233c0ea0af", "ACLDIGITAL",
            "acl/ineligible.rightsholders/ineligible_rightsholder_acldigital.json");
        testHelper.expectPrmIneligibleCall("d145f685-994e-4b47-8748-c1ad375da3f9", "ACLPRINT",
            "acl/ineligible.rightsholders/ineligible_rightsholder_aclprint.json");
        List<AclGrantDetail> actualDetails = grantService.createAclGrantDetails(buildGrantSet(),
            createWrWrkInstToSystemTitleMap(), USER_NAME);
        assertEquals(8, actualDetails.size());
        List<AclGrantDetail> expectedDetails = List.of(
            buildAclGrantDetail(
                DIGITAL, RH_ACCOUNT_1, WR_WRK_INST_1, DIFFERENT_RH, SYSTEM_TITLE_3, false),
            buildAclGrantDetail(
                PRINT, RH_ACCOUNT_2, WR_WRK_INST_1, DIFFERENT_RH, SYSTEM_TITLE_3, false),
            buildAclGrantDetail(
                PRINT, RH_ACCOUNT_3, WR_WRK_INST_2, PRINT_DIGITAL, SYSTEM_TITLE_2, false),
            buildAclGrantDetail(
                DIGITAL, RH_ACCOUNT_3, WR_WRK_INST_2, PRINT_DIGITAL, SYSTEM_TITLE_2, false),
            buildAclGrantDetail(DIGITAL, RH_ACCOUNT_4, WR_WRK_INST_3, DIGITAL_ONLY, SYSTEM_TITLE_1, false),
            buildAclGrantDetail(PRINT, RH_ACCOUNT_5, WR_WRK_INST_4, PRINT_ONLY, SYSTEM_TITLE_4, false),
            buildAclGrantDetail(DIGITAL, RH_ACCOUNT_6, WR_WRK_INST_5, DIGITAL_ONLY, SYSTEM_TITLE_1, false),
            buildAclGrantDetail(PRINT, RH_ACCOUNT_7, WR_WRK_INST_6, PRINT_ONLY, SYSTEM_TITLE_1, false));
        IntStream.range(0, actualDetails.size()).forEach(i ->
            verifyAclGrantDetails(expectedDetails.get(i), actualDetails.get(i)));
    }

    @Test
    public void testCreateAclGrantDetailsWithDigitalIneligible() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("acl/grants/rms_grants_request_1.json", RMS_RESPONSE);
        testHelper.expectGetRmsRights("acl/grants/rms_grants_request_2.json", RMS_RESPONSE);
        testHelper.expectGetRmsRights("acl/grants/rms_grants_request_3.json", RMS_RESPONSE);
        testHelper.expectGetRmsRights("acl/grants/rms_grants_request_4.json", RMS_RESPONSE);
        testHelper.expectPrmIneligibleParentCall("acl/ineligible.rightsholders/rightsholder_parents_acl_digital.json");
        testHelper.expectPrmIneligibleCall("4ae6bd83-07f0-44fe-836c-af233c0ea0af", "ACLDIGITAL",
            "acl/ineligible.rightsholders/ineligible_rightsholder_acldigital.json");
        List<AclGrantDetail> actualDetails = grantService.createAclGrantDetails(buildGrantSet(),
            createWrWrkInstToSystemTitleMap(), USER_NAME);
        assertEquals(8, actualDetails.size());
        List<AclGrantDetail> expectedDetails = List.of(
            buildAclGrantDetail(
                DIGITAL, RH_ACCOUNT_1, WR_WRK_INST_1, DIFFERENT_RH, SYSTEM_TITLE_3, false),
            buildAclGrantDetail(
                PRINT, RH_ACCOUNT_2, WR_WRK_INST_1, DIFFERENT_RH, SYSTEM_TITLE_3, true),
            buildAclGrantDetail(
                PRINT, RH_ACCOUNT_3, WR_WRK_INST_2, PRINT_DIGITAL, SYSTEM_TITLE_2, true),
            buildAclGrantDetail(
                DIGITAL, RH_ACCOUNT_3, WR_WRK_INST_2, PRINT_DIGITAL, SYSTEM_TITLE_2, false),
            buildAclGrantDetail(DIGITAL, RH_ACCOUNT_4, WR_WRK_INST_3, DIGITAL_ONLY, SYSTEM_TITLE_1, false),
            buildAclGrantDetail(PRINT, RH_ACCOUNT_5, WR_WRK_INST_4, PRINT_ONLY, SYSTEM_TITLE_4, true),
            buildAclGrantDetail(DIGITAL, RH_ACCOUNT_6, WR_WRK_INST_5, DIGITAL_ONLY, SYSTEM_TITLE_1, false),
            buildAclGrantDetail(PRINT, RH_ACCOUNT_7, WR_WRK_INST_6, PRINT_ONLY, SYSTEM_TITLE_1, true));
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
        return List.of(
            buildAclGrantDetail(
                DIGITAL, RH_ACCOUNT_1, WR_WRK_INST_1, DIFFERENT_RH, SYSTEM_TITLE_3, true),
            buildAclGrantDetail(
                PRINT, RH_ACCOUNT_2, WR_WRK_INST_1, DIFFERENT_RH, SYSTEM_TITLE_3, false),
            buildAclGrantDetail(
                PRINT, RH_ACCOUNT_3, WR_WRK_INST_2, PRINT_DIGITAL, SYSTEM_TITLE_2, false),
            buildAclGrantDetail(
                DIGITAL, RH_ACCOUNT_3, WR_WRK_INST_2, PRINT_DIGITAL, SYSTEM_TITLE_2, true),
            buildAclGrantDetail(DIGITAL, RH_ACCOUNT_4, WR_WRK_INST_3, DIGITAL_ONLY, SYSTEM_TITLE_1, true),
            buildAclGrantDetail(PRINT, RH_ACCOUNT_5, WR_WRK_INST_4, PRINT_ONLY, SYSTEM_TITLE_4, true),
            buildAclGrantDetail(DIGITAL, RH_ACCOUNT_6, WR_WRK_INST_5, DIGITAL_ONLY, SYSTEM_TITLE_1, true),
            buildAclGrantDetail(PRINT, RH_ACCOUNT_7, WR_WRK_INST_6, PRINT_ONLY, SYSTEM_TITLE_1, true)
        );
    }

    private Map<Long, String> createWrWrkInstToSystemTitleMap() {
        Map<Long, String> wrWrkInstToSystemTitles = new TreeMap<>(Comparator.naturalOrder());
        wrWrkInstToSystemTitles.put(WR_WRK_INST_4, SYSTEM_TITLE_4);
        wrWrkInstToSystemTitles.put(WR_WRK_INST_3, SYSTEM_TITLE_1);
        wrWrkInstToSystemTitles.put(WR_WRK_INST_2, SYSTEM_TITLE_2);
        wrWrkInstToSystemTitles.put(WR_WRK_INST_1, SYSTEM_TITLE_3);
        wrWrkInstToSystemTitles.put(12345678L, "Autologous and cancer stem cell gene therapy");
        wrWrkInstToSystemTitles.put(578123123L, SYSTEM_TITLE_1);
        wrWrkInstToSystemTitles.put(WR_WRK_INST_5, SYSTEM_TITLE_1);
        wrWrkInstToSystemTitles.put(WR_WRK_INST_6, SYSTEM_TITLE_1);
        wrWrkInstToSystemTitles.put(4875964317L, SYSTEM_TITLE_1);
        wrWrkInstToSystemTitles.put(4875964318L, SYSTEM_TITLE_1);
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
