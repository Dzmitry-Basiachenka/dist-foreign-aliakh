package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclFundPoolRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link IAclFundPoolService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/27/2022
 *
 * @author Anton Azarenka
 */
@Service
public class AclFundPoolService implements IAclFundPoolService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IAclFundPoolRepository fundPoolRepository;

    @Transactional
    @Override
    public void insertManualAclFundPool(AclFundPool fundPool, List<AclFundPoolDetail> fundPoolDetails) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert manual ACL fund pool. Started. AclFundPool={}, UserName={}", fundPool, userName);
        fundPool.setId(RupPersistUtils.generateUuid());
        fundPool.setCreateUser(userName);
        fundPool.setUpdateUser(userName);
        fundPoolRepository.insert(fundPool);
        fundPoolDetails.forEach(detail -> {
            detail.setFundPoolId(fundPool.getId());
            detail.setLicenseType(fundPool.getLicenseType());
        });
        insertAclFundPoolDetails(fundPoolDetails);
        LOGGER.info("Insert manual ACL fund pool. Finished. AclFundPool={}, UserName={}", fundPool, userName);
    }

    @Transactional
    @Override
    public int insertLdmtAclFundPool(AclFundPool fundPool) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert LDMT ACL fund pool. Started. AclFundPool={}, UserName={}", fundPool, userName);
        fundPool.setId(RupPersistUtils.generateUuid());
        fundPool.setCreateUser(userName);
        fundPool.setUpdateUser(userName);
        fundPoolRepository.insert(fundPool);
        int count = fundPoolRepository.addLdmtDetailsToFundPool(fundPool.getId(), fundPool.getLicenseType(), userName);
        LOGGER.info("Insert LDMT ACL fund pool. Finished. AclFundPool={}, UserName={}", fundPool, userName);
        return count;
    }

    @Transactional
    @Override
    public void insertAclFundPoolDetails(List<AclFundPoolDetail> fundPoolDetails) {
        int size = fundPoolDetails.size();
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert ACL fund pool details. Started. AclFundPoolDetailsCount={}, UserName={}", size, userName);
        fundPoolDetails.forEach(fundPoolDetail -> fundPoolRepository.insertDetail(fundPoolDetail));
        LOGGER.info("Insert ACL fund pool details. Finished. AclFundPoolDetailsCount={}, UserName={}", size, userName);
    }

    @Override
    public boolean fundPoolExists(String name) {
        return fundPoolRepository.isFundPoolExists(name);
    }

    @Override
    public boolean isLdmtDetailExist(String licenseType) {
        return fundPoolRepository.isLdmtDetailExist(licenseType);
    }

    @Override
    public List<AclFundPoolDetailDto> getDtosByFilter(AclFundPoolDetailFilter filter) {
        return !filter.isEmpty() ? fundPoolRepository.findDtosByFilter(filter) : Collections.emptyList();
    }

    @Override
    public List<AclFundPool> getAll() {
        return fundPoolRepository.findAll();
    }

    @Override
    public List<Integer> getPeriods() {
        return fundPoolRepository.findPeriods();
    }

    @Override
    @Transactional
    public void deleteAclFundPool(AclFundPool fundPool) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete ACL fund fool. Started. FundPoolName={}, UserName={}", fundPool.getName(), userName);
        fundPoolRepository.deleteDetailsByFundPoolId(fundPool.getId());
        fundPoolRepository.deleteById(fundPool.getId());
        LOGGER.info("Delete ACL fund fool. Finished. FundPoolName={}, UserName={}", fundPool.getName(), userName);
    }

    @Override
    public List<AclFundPool> getFundPoolsByLicenseTypeAndPeriod(String licenseType, Integer period) {
        return fundPoolRepository.findFundPoolsByLicenseTypeAndPeriod(licenseType, period);
    }

    @Override
    public AclFundPool getById(String fundPoolId) {
        return fundPoolRepository.findById(fundPoolId);
    }
}
