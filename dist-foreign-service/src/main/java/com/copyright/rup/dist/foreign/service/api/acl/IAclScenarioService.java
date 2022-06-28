package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.AclScenario;

import java.util.List;

/**
 * Interface for ACL scenario service.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 06/23/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclScenarioService {

    /**
     * Gets list of {@link AclScenario}s.
     *
     * @return list of {@link AclScenario}s
     */
    List<AclScenario> getScenarios();

    /**
     * Checks whether {@link AclScenario} with specified name already exists in database.
     *
     * @param scenarioName name of {@link AclScenario} to check
     * @return {@code true} if {@link AclScenario} with specified name already exists in database,
     * {@code false} - if doesn't
     */
    boolean aclScenarioExists(String scenarioName);
}
