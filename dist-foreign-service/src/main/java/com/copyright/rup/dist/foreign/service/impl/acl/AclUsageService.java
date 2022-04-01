package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.foreign.repository.api.IAclUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Implementation of {@link IAclUsageService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/01/2022
 *
 * @author Aliaksandr Liakh
 */
@Service
public class AclUsageService implements IAclUsageService {

    @Autowired
    private IAclUsageRepository aclUsageRepository;

    @Override
    public int populateAclUsages(String usageBatchId, Set<Integer> periods, String userName) {
        return aclUsageRepository.populateAclUsages(usageBatchId, periods, userName).size();
    }
}
