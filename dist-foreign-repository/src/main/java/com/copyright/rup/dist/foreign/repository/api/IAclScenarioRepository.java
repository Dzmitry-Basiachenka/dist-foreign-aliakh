package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.AclScenario;

import java.util.List;

/**
 * Interface for ACL scenario repository.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/24/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclScenarioRepository {

    /**
     * Finds list of {@link AclScenario}s.
     *
     * @return list of {@link AclScenario}s
     */
    List<AclScenario> findAll();

    /**
     * Finds {@link AclScenario}s count with specified name.
     *
     * @param name {@link AclScenario} name
     * @return count of found {@link AclScenario}s
     */
    int findCountByName(String name);
}
