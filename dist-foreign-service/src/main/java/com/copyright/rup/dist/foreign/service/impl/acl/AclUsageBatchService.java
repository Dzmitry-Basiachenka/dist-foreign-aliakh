package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageBatchRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link IAclUsageBatchService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/01/2022
 *
 * @author Aliaksandr Liakh
 */
@Service
public class AclUsageBatchService implements IAclUsageBatchService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IAclUsageBatchRepository aclUsageBatchRepository;
    @Autowired
    private IAclUsageService aclUsageService;

    @Override
    public boolean isAclUsageBatchExist(String usageBatchName) {
        return aclUsageBatchRepository.isAclUsageBatchExist(usageBatchName);
    }

    @Transactional
    @Override
    public int insert(AclUsageBatch usageBatch) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert ACL usage batch. Started. AclUsageBatch={}, UserName={}", usageBatch, userName);
        usageBatch.setId(RupPersistUtils.generateUuid());
        usageBatch.setCreateUser(userName);
        usageBatch.setUpdateUser(userName);
        aclUsageBatchRepository.insert(usageBatch);
        int count = aclUsageService.populateAclUsages(usageBatch.getId(), usageBatch.getPeriods(), userName);
        LOGGER.info("Insert ACL usage batch. Finished. AclUsageBatch={}, AclUsagesCount={}, UserName={}",
            usageBatch, count, userName);
        return count;
    }
}
