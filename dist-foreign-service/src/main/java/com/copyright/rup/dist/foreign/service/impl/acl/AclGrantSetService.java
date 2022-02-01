package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantSetRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantSetService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private IUdmBaselineRepository udmBaselineRepository;
    @Autowired
    private IAclGrantService aclGrantService;
    @Autowired
    private IAclGrantSetRepository aclGrantSetRepository;
    @Autowired
    private IAclGrantDetailService aclGrantDetailService;

    @Transactional
    @Override
    public int insert(AclGrantSet grantSet) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert ACL grant set. Started. AclGrantSet={}, UserName={}", grantSet, userName);
        grantSet.setId(RupPersistUtils.generateUuid());
        grantSet.setCreateUser(userName);
        grantSet.setUpdateUser(userName);
        List<AclGrantDetail> grantDetails = aclGrantService.createAclGrantDetails(grantSet,
            udmBaselineRepository.findWrWrkInstToSystemTitles(grantSet.getPeriods()));
        aclGrantSetRepository.insert(grantSet);
        aclGrantDetailService.insert(grantDetails);
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
}
