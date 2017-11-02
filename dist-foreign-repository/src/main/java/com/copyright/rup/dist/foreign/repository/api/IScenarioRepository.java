package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Scenario;

import java.util.List;

/**
 * Interface for Scenario repository.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Ihar Suvorau
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
public interface IScenarioRepository {

    /**
     * Inserts {@link Scenario} into database.
     *
     * @param scenario {@link Scenario} instance
     */
    void insert(Scenario scenario);

    /**
     * Gets {@link Scenario}s count with specified name.
     *
     * @param name {@link Scenario} name
     * @return count of found {@link Scenario}s
     */
    int getCountByName(String name);

    /**
     * @return list of all {@link Scenario}s.
     */
    List<Scenario> findAll();

    /**
     * Finds {@link Scenario}s names associated with any of usages from selected usage batch.
     *
     * @param usageBatchId identifier of usage batch
     * @return found list of {@link Scenario}s names
     */
    List<String> findNamesByUsageBatchId(String usageBatchId);

    /**
     * Removes {@link Scenario} by given identifier.
     *
     * @param scenarioId {@link Scenario} identifier
     */
    void remove(String scenarioId);

    /**
     * Finds all source RROs belonging to the {@link Scenario} with given id.
     *
     * @param scenarioId {@link Scenario} id
     * @return list of source RROs for given scenario
     */
    List<Rightsholder> findSourceRros(String scenarioId);
}
