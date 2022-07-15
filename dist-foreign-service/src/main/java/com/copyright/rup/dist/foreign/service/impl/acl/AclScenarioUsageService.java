package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IAclScenarioUsageService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/13/2022
 *
 * @author Ihar Suvorau
 */
@Service
public class AclScenarioUsageService implements IAclScenarioUsageService {

    @Autowired
    private IAclUsageRepository aclUsageRepository;

    @Override
    public void addUsagesToAclScenario(AclScenario aclScenario, String username) {
        aclUsageRepository.addToAclScenario(aclScenario, username);
    }

    @Override
    public void addScenarioShares(AclScenario aclScenario, String username) {
        aclUsageRepository.addScenarioShares(aclScenario, username);
    }

    @Override
    public void populatePubTypeWeights(String scenarioId, String userName) {
        aclUsageRepository.populatePubTypeWeights(scenarioId, userName);
    }
}
