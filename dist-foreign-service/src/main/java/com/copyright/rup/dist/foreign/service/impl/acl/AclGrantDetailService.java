package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.AclGrantTypeOfUseStatusEnum;
import com.copyright.rup.dist.foreign.domain.AclIneligibleRightsholder;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantDetailRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import io.micrometer.core.annotation.Timed;

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
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    private IUdmBaselineService udmBaselineService;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;

    @Transactional
    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void insert(List<AclGrantDetail> grantDetails) {
        int size = grantDetails.size();
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert ACL grant details. Started. AclGrantDetailsCount={}, UserName={}", size, userName);
        grantDetails.forEach(aclGrantDetail -> aclGrantDetailRepository.insert(aclGrantDetail));
        LOGGER.info("Insert ACL grant details. Finished. AclGrantDetailsCount={}, UserName={}", size, userName);
    }

    @Transactional
    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void addToGrantSet(AclGrantSet grantSet, List<AclGrantDetailDto> grantDetails) {
        int size = grantDetails.size();
        String grantSetId = grantSet.getId();
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Upload ACL grant details. Started. GrantSetId={}, AclGrantDetailsCount={}, UserName={}",
            grantSetId, size, userName);
        Map<Long, String> workToTitlesMap = udmBaselineService.getWrWrkInstToSystemTitles(grantSet.getPeriods());
        grantDetails.stream().map(this::convertDtoToDetail)
            .forEach(aclGrantDetail -> {
                aclGrantDetail.setGrantSetId(grantSetId);
                aclGrantDetail.setSystemTitle(workToTitlesMap.get(aclGrantDetail.getWrWrkInst()));
                aclGrantDetailRepository.insert(aclGrantDetail);
            });
        Set<AclIneligibleRightsholder> ineligibleRightsholders =
            prmIntegrationService.getIneligibleRightsholders(createPeriodEndDate(grantSet.getGrantPeriod()),
                grantSet.getLicenseType());
        grantDetails.forEach(grant -> grant.setEligible(getEligibleStatus(grant, ineligibleRightsholders)));
        updateGrants(grantDetails, true);
        LOGGER.info("Upload ACL grant details. Finished. GrantSetId={}, AclGrantDetailsCount={}, UserName={}",
            grantSetId, size, userName);
    }

    @Override
    public int getCount(AclGrantDetailFilter filter) {
        return !filter.isEmpty() ? aclGrantDetailRepository.findCountByFilter(filter) : 0;
    }

    @Override
    public List<AclGrantDetailDto> getDtos(AclGrantDetailFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? aclGrantDetailRepository.findDtosByFilter(filter, pageable, sort)
            : List.of();
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void updateGrants(Collection<AclGrantDetailDto> aclGrantDetailDtos, boolean doUpdateTouStatus) {
        String userName = RupContextUtils.getUserName();
        if (doUpdateTouStatus) {
            Map<Long, List<AclGrantDetailDto>> wrWrkInstToGrants =
                aclGrantDetailDtos.stream().collect(Collectors.groupingBy(AclGrantDetailDto::getWrWrkInst));
            wrWrkInstToGrants.forEach((wrWrkInst, grants) -> {
                if (grants.size() == TYPE_OF_USE_COUNT) {
                    AclGrantDetailDto pairForGrant =
                        aclGrantDetailRepository.findPairForGrantById(grants.get(0).getId());
                    if (Objects.nonNull(pairForGrant)) {
                        grants.add(pairForGrant);
                    }
                }
                updateTypeOfUseStatus(grants);
                grants.forEach(grant -> {
                    grant.setUpdateUser(userName);
                    aclGrantDetailRepository.updateGrant(grant);
                });
            });
        } else {
            aclGrantDetailDtos.forEach(grant -> {
                grant.setUpdateUser(userName);
                aclGrantDetailRepository.updateGrant(grant);
            });
        }
        updateRightsholders(aclGrantDetailDtos);
    }

    @Override
    public void setEligibleFlag(List<AclGrantDetail> grantDetails, LocalDate date, String licenseType) {
        Set<AclIneligibleRightsholder> ineligibleRightsholders =
            prmIntegrationService.getIneligibleRightsholders(date, licenseType);
        grantDetails.forEach(grant -> grant.setEligible(getEligibleStatus(grant, ineligibleRightsholders)));
    }

    @Override
    public void deleteGrantDetails(String grantSetId) {
        aclGrantDetailRepository.deleteByGrantSetId(grantSetId);
    }

    @Override
    public boolean isGrantDetailExist(String grantSetId, Long wrWrkInst, String typeOfUse) {
        return aclGrantDetailRepository.isGrantDetailExist(grantSetId, wrWrkInst, typeOfUse);
    }

    @Override
    public int copyGrantDetails(String sourceGrantSetId, String targetGrantSetId, String userName) {
        return aclGrantDetailRepository.copyGrantDetailsByGrantSetId(sourceGrantSetId, targetGrantSetId, userName)
            .size();
    }

    private boolean getEligibleStatus(AclGrantDetail grant, Set<AclIneligibleRightsholder> ineligibleRightsholders) {
        return ineligibleRightsholders.stream()
            .noneMatch(
                ineligibleRightsholder -> ineligibleRightsholder.getRhAccountNumber().equals(grant.getRhAccountNumber())
                    && ineligibleRightsholder.getTypeOfUse().equals(grant.getTypeOfUse()));
    }

    private void updateTypeOfUseStatus(List<AclGrantDetailDto> grants) {
        List<AclGrantDetailDto> grantDetailDtos =
            grants.stream()
                .filter(grant -> Objects.nonNull(grant) && "GRANT".equals(grant.getGrantStatus()))
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

    private void updateRightsholders(Collection<AclGrantDetailDto> aclGrantDetailDtos) {
        Set<Long> accountNumbers = aclGrantDetailDtos.stream().map(AclGrantDetailDto::getRhAccountNumber)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(accountNumbers)) {
            rightsholderService.updateRightsholders(accountNumbers);
        }
    }

    private boolean isDifferentRh(List<AclGrantDetailDto> grants) {
        return grants.stream()
            .map(AclGrantDetailDto::getRhAccountNumber)
            .distinct().count() > TYPE_OF_USE_COUNT;
    }

    private boolean isDifferentTypesOfUse(List<AclGrantDetailDto> grants) {
        return grants.size() > TYPE_OF_USE_COUNT;
    }

    private AclGrantDetail convertDtoToDetail(AclGrantDetailDto grantDetailDto) {
        AclGrantDetail grantDetail = new AclGrantDetail();
        grantDetail.setId(grantDetailDto.getId());
        grantDetail.setEligible(grantDetailDto.getEligible());
        grantDetail.setManualUploadFlag(grantDetailDto.getManualUploadFlag());
        grantDetail.setGrantStatus(grantDetailDto.getGrantStatus());
        grantDetail.setRhAccountNumber(grantDetailDto.getRhAccountNumber());
        grantDetail.setWrWrkInst(grantDetailDto.getWrWrkInst());
        grantDetail.setTypeOfUse(grantDetailDto.getTypeOfUse());
        grantDetail.setTypeOfUseStatus(grantDetailDto.getTypeOfUseStatus());
        return grantDetail;
    }

    private LocalDate createPeriodEndDate(Integer period) {
        int year = period / 100;
        int month = period % 100;
        return 6 == month ? LocalDate.of(year, month, 30) : LocalDate.of(year, month, 31);
    }
}
