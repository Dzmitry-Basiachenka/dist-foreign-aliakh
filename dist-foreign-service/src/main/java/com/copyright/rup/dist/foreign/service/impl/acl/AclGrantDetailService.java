package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.AclGrantTypeOfUseStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantDetailRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IAclGrantDetailService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/28/2022
 *
 * @author Aliaksandr Liakh
 */
@Service
public class AclGrantDetailService implements IAclGrantDetailService {

    private static final int TYPE_OF_USE_COUNT = 1;
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IAclGrantDetailRepository aclGrantDetailRepository;

    @Transactional
    @Override
    public void insert(List<AclGrantDetail> grantDetails) {
        int size = grantDetails.size();
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert ACL grant details. Started. AclGrantDetailsCount={}, UserName={}", size, userName);
        grantDetails.forEach(aclGrantDetail -> aclGrantDetailRepository.insert(aclGrantDetail));
        LOGGER.info("Insert ACL grant details. Finished. AclGrantDetailsCount={}, UserName={}", size, userName);
    }

    @Override
    public int getCount(AclGrantDetailFilter filter) {
        return !filter.isEmpty() ? aclGrantDetailRepository.findCountByFilter(filter) : 0;
    }

    @Override
    public List<AclGrantDetailDto> getDtos(AclGrantDetailFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? aclGrantDetailRepository.findDtosByFilter(filter, pageable, sort)
            : Collections.emptyList();
    }

    @Override
    public void updateGrants(Set<AclGrantDetailDto> aclGrantDetailDtos, boolean doUpdateTouStatus) {
        if (doUpdateTouStatus) {
            Map<Long, List<AclGrantDetailDto>> wrWrkInstToGrants =
                aclGrantDetailDtos.stream().collect(Collectors.groupingBy(AclGrantDetailDto::getWrWrkInst));
            wrWrkInstToGrants.forEach((wrWrkInst, grants) -> {
                if (grants.size() == TYPE_OF_USE_COUNT) {
                    grants.add(aclGrantDetailRepository.findPairForGrantById(grants.get(0).getId()));
                }
                updateTypeOfUseStatus(grants);
                grants.forEach(grant -> aclGrantDetailRepository.updateGrant(grant));
            });
        } else {
            aclGrantDetailDtos.forEach(grant -> aclGrantDetailRepository.updateGrant(grant));
        }
    }

    private void updateTypeOfUseStatus(List<AclGrantDetailDto> grants) {
        List<AclGrantDetailDto> grantDetailDtos =
            grants.stream()
                .filter(grant -> "GRANT".equals(grant.getGrantStatus()))
                .collect(Collectors.toList());
        grantDetailDtos.forEach(grant -> {
            if (isDifferentRh(grantDetailDtos)) {
                grant.setTypeOfUseStatus(AclGrantTypeOfUseStatusEnum.DIFFERENT_RH.toString());
            } else {
                grant.setTypeOfUseStatus(isDifferentTypesOfUse(grantDetailDtos)
                    ? AclGrantTypeOfUseStatusEnum.PRINT_DIGITAL.toString()
                    : AclGrantTypeOfUseStatusEnum.valueOf(grant.getTypeOfUse()).toString());
            }
        });
    }

    private boolean isDifferentRh(List<AclGrantDetailDto> grants) {
        return grants.stream()
            .collect(Collectors.groupingBy(AclGrantDetailDto::getRhAccountNumber))
            .size() > TYPE_OF_USE_COUNT;
    }

    private boolean isDifferentTypesOfUse(List<AclGrantDetailDto> grants) {
        return grants.size() > TYPE_OF_USE_COUNT;
    }
}
