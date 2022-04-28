package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IAclFundPoolRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void insertAclFundPool(AclFundPool fundPool, List<AclFundPoolDetail> fundPoolDetails) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert ACL fund pool. Started. AclFundPool={}, UserName={}", fundPool, userName);
        fundPool.setId(RupPersistUtils.generateUuid());
        fundPool.setCreateUser(userName);
        fundPool.setUpdateUser(userName);
        fundPoolRepository.insert(fundPool);
        fundPoolDetails.forEach(detail -> {
            detail.setFundPoolId(fundPool.getId());
            detail.setLicenseType(fundPool.getLicenseType());
        });
        insertAclFundPoolDetails(fundPoolDetails);
        LOGGER.info("Insert ACL fund pool. Finished. AclFundPool={}, UserName={}", fundPool, userName);
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
}
