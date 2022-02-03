package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;

import com.copyright.rup.dist.common.domain.RmsGrant;
import com.copyright.rup.dist.common.integration.rest.rms.IRmsRightsService;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantService;

import com.google.common.collect.ImmutableSet;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Verifies {@link AclGrantService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Anton Azarenka
 */
public class AclGrantServiceTest {

    private static final String GRANT = "GRANT";
    private static final String PRINT = "PRINT";
    private static final String DIGITAL = "DIGITAL";
    private static final String ACL = "ACL";
    private static final String SYSTEM_TITLE = "Red Riding Hood's maths adventure";
    private static final Set<String> STATUS = ImmutableSet.of(GRANT);
    private static final Set<String> TYPE_OF_USES = ImmutableSet.of(PRINT, DIGITAL);
    private static final int RIGHTS_PARTITION_SIZE = 6;

    private final IAclGrantService aclGrantService = new AclGrantService();
    private IRmsRightsService rmsRightsService;

    @Before
    public void setUp() {
        rmsRightsService = createMock(IRmsRightsService.class);
        Whitebox.setInternalState(aclGrantService, rmsRightsService);
        Whitebox.setInternalState(aclGrantService, RIGHTS_PARTITION_SIZE);
    }

    @Test
    public void testCreateAclGrantDetails() {
        Map<Long, String> wrWrkInstToSystemTitles = createWrWrkInstToSystemTitleMap();
        LocalDate periodEndDate = LocalDate.of(2021, 6, 30);
        expect(
            rmsRightsService.getGrants(new ArrayList<>(wrWrkInstToSystemTitles.keySet()), periodEndDate, STATUS,
                TYPE_OF_USES, Collections.singleton(ACL))).andReturn(buildRmsGrants()).once();
        replay(rmsRightsService);
        List<AclGrantDetail> actualGrantDetails =
            aclGrantService.createAclGrantDetails(buildGrantSet(), wrWrkInstToSystemTitles, "SYSTEM");
        List<AclGrantDetail> expectedGrantDetails = buildAclGrantDetails();
        assertEquals(expectedGrantDetails.size(), actualGrantDetails.size());
        IntStream.range(0, actualGrantDetails.size()).forEach(i ->
            verifyAclGrantDetails(expectedGrantDetails.get(i), actualGrantDetails.get(i)));
        verify(rmsRightsService);
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
                DIGITAL, 1000014080L, 136797639L, "Different RH", "Farewell to the leftist working class", GRANT),
            buildAclGrantDetail(
                PRINT, 1000002760L, 136797639L, "Different RH", "Farewell to the leftist working class", GRANT),
            buildAclGrantDetail(
                PRINT, 1000004023L, 159246556L, "Print&Digital", "Embracing watershed politics", GRANT),
            buildAclGrantDetail(
                DIGITAL, 1000004023L, 159246556L, "Print&Digital", "Embracing watershed politics", GRANT),
            buildAclGrantDetail(DIGITAL, 2000017000L, 309812565L, "Digital Only", SYSTEM_TITLE, GRANT),
            buildAclGrantDetail(PRINT, 1000025853L, 144114260L, "Print Only", "I've discovered energy!", GRANT),
            buildAclGrantDetail(DIGITAL, 1000046080L, 578123123L, "Digital Only", SYSTEM_TITLE, GRANT)
        );
    }

    private Map<Long, String> createWrWrkInstToSystemTitleMap() {
        Map<Long, String> wrWrkInstToSystemTitles = new HashMap<>();
        wrWrkInstToSystemTitles.put(144114260L, "I've discovered energy!");
        wrWrkInstToSystemTitles.put(309812565L, SYSTEM_TITLE);
        wrWrkInstToSystemTitles.put(159246556L, "Embracing watershed politics");
        wrWrkInstToSystemTitles.put(136797639L, "Farewell to the leftist working class");
        wrWrkInstToSystemTitles.put(12345678L, "Autologous and cancer stem cell gene therapy");
        wrWrkInstToSystemTitles.put(578123123L, SYSTEM_TITLE);
        return wrWrkInstToSystemTitles;
    }

    private AclGrantDetail buildAclGrantDetail(String typeOfUse, Long rhAccountNumber, Long wrWrkInst,
                                               String typeOfUseStatus, String systemTitle, String grantStatus) {
        AclGrantDetail aclGrantDetail = new AclGrantDetail();
        aclGrantDetail.setGrantSetId("4f1e3f3d-bbef-428a-9d19-de048c1f5ad5");
        aclGrantDetail.setEligible(true);
        aclGrantDetail.setTypeOfUse(typeOfUse);
        aclGrantDetail.setRhAccountNumber(rhAccountNumber);
        aclGrantDetail.setWrWrkInst(wrWrkInst);
        aclGrantDetail.setTypeOfUseStatus(typeOfUseStatus);
        aclGrantDetail.setSystemTitle(systemTitle);
        aclGrantDetail.setGrantStatus(grantStatus);
        return aclGrantDetail;
    }

    private AclGrantSet buildGrantSet() {
        AclGrantSet aclGrantSet = new AclGrantSet();
        aclGrantSet.setId("4f1e3f3d-bbef-428a-9d19-de048c1f5ad5");
        aclGrantSet.setGrantPeriod(202106);
        aclGrantSet.setLicenseType(ACL);
        return aclGrantSet;
    }

    private Set<RmsGrant> buildRmsGrants() {
        return new HashSet<>(Arrays.asList(
            buildRmsGrant(PRINT, new BigDecimal("1000025853"), 144114260L),
            buildRmsGrant(DIGITAL, new BigDecimal("2000017000"), 309812565L),
            buildRmsGrant(DIGITAL, new BigDecimal("1000004023"), 159246556L),
            buildRmsGrant(PRINT, new BigDecimal("1000004023"), 159246556L),
            buildRmsGrant(PRINT, new BigDecimal("1000002760"), 136797639L),
            buildRmsGrant(DIGITAL, new BigDecimal("1000014080"), 136797639L),
            buildRmsGrant(DIGITAL, new BigDecimal("1000046080"), 578123123L)
        ));
    }

    private RmsGrant buildRmsGrant(String typeOfUse, BigDecimal ownerOrgNumber, Long wrWrkInst) {
        RmsGrant grant = new RmsGrant();
        grant.setProductFamily(ACL);
        grant.setLicenseType(ACL);
        grant.setRightStatus(GRANT);
        grant.setWorkGroupOwnerOrgNumber(ownerOrgNumber);
        grant.setWrWrkInst(wrWrkInst);
        grant.setTypeOfUse(typeOfUse);
        return grant;
    }
}
