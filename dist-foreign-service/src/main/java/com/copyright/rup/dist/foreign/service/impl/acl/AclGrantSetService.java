package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantSetRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantSetService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IAclGrantSetService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/27/2022
 *
 * @author Aliaksandr Liakh
 */
@Service
public class AclGrantSetService implements IAclGrantSetService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUdmBaselineService udmBaselineService;
    @Autowired
    private IAclGrantService aclGrantService;
    @Autowired
    private IAclGrantSetRepository aclGrantSetRepository;
    @Autowired
    private IAclGrantDetailService aclGrantDetailService;
    @Autowired
    private IRightsholderService rightsholderService;

    @Transactional
    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public int insert(AclGrantSet grantSet) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert ACL grant set. Started. AclGrantSet={}, UserName={}", grantSet, userName);
        grantSet.setId(RupPersistUtils.generateUuid());
        grantSet.setCreateUser(userName);
        grantSet.setUpdateUser(userName);
        List<AclGrantDetail> grantDetails = aclGrantService.createAclGrantDetails(grantSet,
            udmBaselineService.getWrWrkInstToSystemTitles(grantSet.getPeriods()), userName);
        aclGrantSetRepository.insert(grantSet);
        aclGrantDetailService.insert(grantDetails);
        rightsholderService.updateRightsholders(
            grantDetails.stream().map(AclGrantDetail::getRhAccountNumber).collect(Collectors.toSet()));
        LOGGER.info("Insert ACL grant set. Finished. AclGrantSet={}, AclGrantDetailsCount={}, UserName={}",
            grantSet, grantDetails.size(), userName);
        return grantDetails.size();
    }

    @Override
    public boolean isGrantSetExist(String grantSetName) {
        return aclGrantSetRepository.isGrantSetExist(grantSetName);
    }

    @Override
    public List<AclGrantSet> getAll() {
        return aclGrantSetRepository.findAll();
    }

    @Override
    public List<Integer> getGrantPeriods() {
        return aclGrantSetRepository.findGrantPeriods();
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void deleteAclGrantSet(AclGrantSet grantSet) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete ACL grant set. Started. GrantSetName={}, UserName={}", grantSet.getName(), userName);
        aclGrantDetailService.deleteGrantDetails(grantSet.getId());
        aclGrantSetRepository.deleteById(grantSet.getId());
        LOGGER.info("Delete ACL grant set. Finished. GrantSetName={}, UserName={}", grantSet.getName(), userName);
    }

    @Override
    public List<AclGrantSet> getGrantSetsByLicenseTypeAndPeriod(String licenseType, Integer period,
                                                                boolean editableFlag) {
        return aclGrantSetRepository.findGrantSetsByLicenseTypeAndPeriod(licenseType, period, editableFlag);
    }

    @Override
    public AclGrantSet getById(String grantSetId) {
        return aclGrantSetRepository.findById(grantSetId);
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public int copyGrantSet(AclGrantSet aclGrantSet, String sourceGrantSetId) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Copy ACL grant set. Started. AclGrantSet={}, UserName={}", aclGrantSet, userName);
        String targetGrantSetId = RupPersistUtils.generateUuid();
        aclGrantSet.setId(targetGrantSetId);
        aclGrantSet.setCreateUser(userName);
        aclGrantSet.setUpdateUser(userName);
        aclGrantSetRepository.insert(aclGrantSet);
        int countInsertedGrantDetails =
            aclGrantDetailService.copyGrantDetails(sourceGrantSetId, targetGrantSetId, userName);
        LOGGER.info("Copy ACL grant set. Finished. AclGrantSet={}, AclGrantSetCount={}, UserName={}",
            aclGrantSet, countInsertedGrantDetails, userName);
        return countInsertedGrantDetails;
    }
}
