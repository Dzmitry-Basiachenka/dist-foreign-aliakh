package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.Scenario;

/**
 * Interface for Scenario repository.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/15/17
 *
 * @author Ihar Suvorau
 */
// TODO {isuvorau} use one pattern for naming repository methods
public interface IScenarioRepository {

    /**
     * Inserts {@link Scenario} into database.
     *
     * @param scenario {@link Scenario} instance
     */
    void insert(Scenario scenario);

    /**
     * Finds {@link Scenario} by provided identifier.
     *
     * @param id scenario identifier
     * @return found {@link Scenario} instance
     */
    Scenario findById(String id);

    /**
     * Gets scenarios count with specified name.
     *
     * @param name scenario name
     * @return count of found scenarios
     */
    int getCountByName(String name);
}
