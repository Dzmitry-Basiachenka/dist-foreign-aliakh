package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantDetailRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
