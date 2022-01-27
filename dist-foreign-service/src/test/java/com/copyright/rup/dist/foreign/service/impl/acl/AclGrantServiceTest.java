package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
    private static final Set<String> STATUSES = ImmutableSet.of(GRANT, "DENY");
    private static final Set<String> TYPE_OF_USES = ImmutableSet.of(PRINT, DIGITAL);
    private static final int RIGHTS_PARTITION_SIZE = 5;

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
        List<Long> wrWrkInst = Arrays.asList(144114260L, 309812565L, 159246556L, 136797639L, 12345678L);
        LocalDate periodEndDate = LocalDate.of(2021, 6, 30);
        expect(rmsRightsService.getGrants(wrWrkInst, periodEndDate, STATUSES, TYPE_OF_USES,
            Collections.singleton(ACL))).andReturn(buildRmsGrants()).once();
        replay(rmsRightsService);
        List<AclGrantDetail> actualGrantDetails = aclGrantService.createAclGrantDetails(buildGrantSet(), wrWrkInst);
        List<AclGrantDetail> expectedGrantDetails = buildAclGrantDetails();
        IntStream.range(0, actualGrantDetails.size()).forEach(i ->
            verifyAclGrantDetails(expectedGrantDetails.get(i), actualGrantDetails.get(i)));
        verify(rmsRightsService);
    }

    private void verifyAclGrantDetails(AclGrantDetail expectedDetail, AclGrantDetail actualDetail) {
        assertEquals(expectedDetail.getGrantSetId(), actualDetail.getGrantSetId());
        assertEquals(expectedDetail.getEligible(), actualDetail.getEligible());
        assertEquals(expectedDetail.getRhAccountNumber(), actualDetail.getRhAccountNumber());
        assertEquals(expectedDetail.getTypeOfUseStatus(), actualDetail.getTypeOfUseStatus());
        assertEquals(expectedDetail.getWrWrkInst(), actualDetail.getWrWrkInst());
        assertEquals(expectedDetail.getTypeOfUse(), actualDetail.getTypeOfUse());
    }

    private List<AclGrantDetail> buildAclGrantDetails() {
        return Arrays.asList(
            buildAclGrantDetail(DIGITAL, 1000014080L, 136797639L, "Different RH"),
            buildAclGrantDetail(PRINT, 1000002760L, 136797639L, "Different RH"),
            buildAclGrantDetail(PRINT, 1000004023L, 159246556L, "Print&Digital"),
            buildAclGrantDetail(DIGITAL, 1000004023L, 159246556L, "Print&Digital"),
            buildAclGrantDetail(DIGITAL, 2000017000L, 309812565L, "Digital Only"),
            buildAclGrantDetail(PRINT, 1000025853L, 144114260L, "Print Only")
        );
    }

    private AclGrantDetail buildAclGrantDetail(String typeOfUse, Long rhAccountNumber, Long wrWrkInst,
                                               String typeOfUseStatus) {
        AclGrantDetail aclGrantDetail = new AclGrantDetail();
        aclGrantDetail.setGrantSetId("4f1e3f3d-bbef-428a-9d19-de048c1f5ad5");
        aclGrantDetail.setEligible(true);
        aclGrantDetail.setTypeOfUse(typeOfUse);
        aclGrantDetail.setRhAccountNumber(rhAccountNumber);
        aclGrantDetail.setWrWrkInst(wrWrkInst);
        aclGrantDetail.setTypeOfUseStatus(typeOfUseStatus);
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
            buildRmsGrant(DIGITAL, new BigDecimal("1000014080"), 136797639L)
        ));
    }

    private RmsGrant buildRmsGrant(String typeOfUse, BigDecimal ownerOrgNumber, Long wrWrkInst) {
        RmsGrant grant = new RmsGrant();
        grant.setProductFamily(ACL);
        grant.setLicenseType(ACL);
        grant.setStatus(GRANT);
        grant.setWorkGroupOwnerOrgNumber(ownerOrgNumber);
        grant.setWrWrkInst(wrWrkInst);
        grant.setTypeOfUse(typeOfUse);
        return grant;
    }
}
