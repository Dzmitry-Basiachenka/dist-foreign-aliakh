package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.RightsholderAclTotalsHolder;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link IAclScenarioService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/23/2022
 *
 * @author Dzmitry Basiachenka
 */
@Service
public class AclScenarioService implements IAclScenarioService {

    @Autowired
    private IAclScenarioRepository aclScenarioRepository;

    @Override
    public List<AclScenario> getScenarios() {
        return aclScenarioRepository.findAll();
    }

    @Override
    public boolean aclScenarioExists(String scenarioName) {
        return 0 < aclScenarioRepository.findCountByName(scenarioName);
    }

    @Override
    public AclScenarioDto getAclScenarioWithAmountsAndLastAction(String scenarioId) {
        return aclScenarioRepository.findWithAmountsAndLastAction(scenarioId);
    }

    @Override
    public List<RightsholderAclTotalsHolder> getRightsholderAclTotalsHoldersByScenarioId(String scenarioId,
                                                                                         String searchValue,
                                                                                         Pageable pageable, Sort sort) {
        //TODO {dbasiachenka} implement
        return Collections.emptyList();
    }

    @Override
    public int getRightsholderAclTotalsHolderCountByScenarioId(String scenarioId, String searchValue) {
        //TODO {dbasiachenka} implement
        return 0;
    }
}
