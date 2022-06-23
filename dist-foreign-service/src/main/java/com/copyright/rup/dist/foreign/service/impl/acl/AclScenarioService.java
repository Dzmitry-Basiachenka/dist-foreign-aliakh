package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;

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

    @Override
    public List<AclScenario> getScenarios() {
        //TODO {dbasiachenka} implement
        return Collections.emptyList();
    }
}
