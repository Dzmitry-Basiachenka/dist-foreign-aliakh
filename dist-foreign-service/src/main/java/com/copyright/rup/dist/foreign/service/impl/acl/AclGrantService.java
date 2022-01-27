package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.common.domain.RmsGrant;
import com.copyright.rup.dist.common.integration.rest.rms.IRmsRightsService;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.AclGrantTypeOfUseStatusEnum;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantService;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IAclGrantService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Anton Azarenka
 */
@Service
public class AclGrantService implements IAclGrantService {

    private static final int TYPE_OF_USE_COUNT = 1;
    private static final Set<String> STATUSES = ImmutableSet.of("GRANT", "DENY");
    private static final Set<String> TYPE_OF_USES = ImmutableSet.of("PRINT", "DIGITAL");
    private static final Map<String, AclGrantTypeOfUseStatusEnum> TOU_TO_STATUSES = ImmutableMap.of(
        "PRINT", AclGrantTypeOfUseStatusEnum.PRINT,
        "DIGITAL", AclGrantTypeOfUseStatusEnum.DIGITAL
    );

    @Value("#{$RUP{dist.foreign.rest.rms.rights.partition_size}}")
    private int rightsPartitionSize;
    @Autowired
    @Qualifier("df.service.rmsCacheService")
    private IRmsRightsService rmsRightsService;

    @Override
    @Transactional
    public List<AclGrantDetail> createAclGrantDetails(AclGrantSet grantSet, List<Long> wrWrkInsts) {
        LocalDate periodEndDate = createPeriodEndDate(grantSet.getGrantPeriod());
        Map<Long, List<RmsGrant>> wrWrkInstToGrants = new HashMap<>();
        Iterables.partition(wrWrkInsts, rightsPartitionSize).forEach(wrWrkInstsPart ->
            wrWrkInstToGrants.putAll(rmsRightsService.getGrants(wrWrkInstsPart, periodEndDate, STATUSES, TYPE_OF_USES,
                    ImmutableSet.copyOf(Collections.singleton(grantSet.getLicenseType())))
                .stream()
                .collect(Collectors.groupingBy(RmsGrant::getWrWrkInst))));
        List<AclGrantDetail> grantDetails = new ArrayList<>();
        wrWrkInstToGrants.forEach((wrWrkInst, grants) -> grants.forEach(grant ->
            grantDetails.add(
                buildAclGrantDetail(grantSet.getId(), grant, isDifferentRh(grants), isDifferentTypesOfUse(grants)))));
        return grantDetails;
    }

    private AclGrantDetail buildAclGrantDetail(String grantSetId, RmsGrant grant, boolean differentRh,
                                               boolean differentTypeOfUse) {
        AclGrantDetail aclGrantDetail = new AclGrantDetail();
        aclGrantDetail.setGrantSetId(grantSetId);
        aclGrantDetail.setEligible(true);
        aclGrantDetail.setTypeOfUse(grant.getTypeOfUse());
        aclGrantDetail.setRhAccountNumber(grant.getWorkGroupOwnerOrgNumber().longValueExact());
        aclGrantDetail.setWrWrkInst(grant.getWrWrkInst());
        if (differentRh) {
            aclGrantDetail.setTypeOfUseStatus(AclGrantTypeOfUseStatusEnum.DIFFERENT_RH.toString());
        } else {
            aclGrantDetail.setTypeOfUseStatus(
                differentTypeOfUse ? AclGrantTypeOfUseStatusEnum.PRINT_DIGITAL.toString() : TOU_TO_STATUSES.get(
                    grant.getTypeOfUse()).toString());
        }
        return aclGrantDetail;
    }

    private boolean isDifferentRh(List<RmsGrant> grants) {
        return grants.stream()
            .collect(Collectors.groupingBy(RmsGrant::getWorkGroupOwnerOrgNumber))
            .size() > TYPE_OF_USE_COUNT;
    }

    private boolean isDifferentTypesOfUse(List<RmsGrant> grants) {
        return grants.size() > TYPE_OF_USE_COUNT;
    }

    private LocalDate createPeriodEndDate(Integer period) {
        int year = period / 100;
        int month = period % 100;
        return 6 == month ? LocalDate.of(year, month, 30) : LocalDate.of(year, month, 31);
    }
}
