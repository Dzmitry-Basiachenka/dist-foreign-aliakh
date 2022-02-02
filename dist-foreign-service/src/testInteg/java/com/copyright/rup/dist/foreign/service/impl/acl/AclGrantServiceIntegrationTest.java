package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantService;
import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
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
        testHelper.expectGetRmsRights("acl/grants/rms_grants_144114260_request.json", RMS_RESPONSE);
        testHelper.expectGetRmsRights("acl/grants/rms_grants_309812565_request.json", RMS_RESPONSE);
        testHelper.expectGetRmsRights("acl/grants/rms_grants_159246556_request.json", RMS_RESPONSE);
        testHelper.expectGetRmsRights("acl/grants/rms_grants_136797639_request.json", RMS_RESPONSE);
        testHelper.expectGetRmsRights("acl/grants/rms_grants_578123123_request.json", RMS_RESPONSE);
        List<AclGrantDetail> actualDetails = grantService.createAclGrantDetails(buildGrantSet(),
            ImmutableMap.of(
                144114260L, "I've discovered energy!",
                309812565L, "Red Riding Hood's maths adventure",
                159246556L, "Embracing watershed politics",
                136797639L, "Farewell to the leftist working class",
                578123123L, "Red Riding Hood's maths adventure"));
        assertEquals(6, actualDetails.size());
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
    }

    private List<AclGrantDetail> buildAclGrantDetails() {
        return Arrays.asList(
            buildAclGrantDetail(
                DIGITAL, 1000014080L, 136797639L, "Different RH", "Farewell to the leftist working class"),
            buildAclGrantDetail(
                PRINT, 1000002760L, 136797639L, "Different RH", "Farewell to the leftist working class"),
            buildAclGrantDetail(
                PRINT, 1000004023L, 159246556L, "Print&Digital", "Embracing watershed politics"),
            buildAclGrantDetail(
                DIGITAL, 1000004023L, 159246556L, "Print&Digital", "Embracing watershed politics"),
            buildAclGrantDetail(DIGITAL, 2000017000L, 309812565L, "Digital Only", SYSTEM_TITLE),
            buildAclGrantDetail(PRINT, 1000025853L, 144114260L, "Print Only", "I've discovered energy!"),
            buildAclGrantDetail(DIGITAL, 1000046080L, 578123123L, "Digital Only", SYSTEM_TITLE)
        );
    }

    private AclGrantDetail buildAclGrantDetail(String typeOfUse, Long rhAccountNumber, Long wrWrkInst,
                                               String typeOfUseStatus, String systemTitle) {
        AclGrantDetail aclGrantDetail = new AclGrantDetail();
        aclGrantDetail.setGrantSetId("c72b8f6a-0923-43f8-b0d6-797de523de2d");
        aclGrantDetail.setEligible(true);
        aclGrantDetail.setTypeOfUse(typeOfUse);
        aclGrantDetail.setRhAccountNumber(rhAccountNumber);
        aclGrantDetail.setWrWrkInst(wrWrkInst);
        aclGrantDetail.setTypeOfUseStatus(typeOfUseStatus);
        aclGrantDetail.setSystemTitle(systemTitle);
        aclGrantDetail.setGrantStatus(GRANT);
        return aclGrantDetail;
    }

    private AclGrantSet buildGrantSet() {
        AclGrantSet aclGrantSet = new AclGrantSet();
        aclGrantSet.setId("c72b8f6a-0923-43f8-b0d6-797de523de2d");
        aclGrantSet.setGrantPeriod(202106);
        aclGrantSet.setLicenseType("ACL");
        aclGrantSet.setName("Grand set name");
        aclGrantSet.setCreateUser("user@copyright.com");
        aclGrantSet.setUpdateUser("user@copyright.com");
        aclGrantSet.setEditable(true);
        return aclGrantSet;
    }
}
