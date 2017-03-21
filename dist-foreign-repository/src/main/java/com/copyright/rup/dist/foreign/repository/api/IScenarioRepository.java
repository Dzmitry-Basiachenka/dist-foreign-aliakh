package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.Scenario;

import java.util.List;

/**
 * Interface for Scenario repository.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/15/17
 *
 * @author Ihar Suvorau
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
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
     * Gets {@link Scenario}s count with specified name.
     *
     * @param name {@link Scenario} name
     * @return count of found {@link Scenario}s
     */
    int getCountByName(String name);

    /**
     * @return list of {@link Scenario}s.
     */
    List<Scenario> getScenarios();

    /**
     * Finds {@link Scenario}s names associated with any of usages from selected usage batch.
     *
     * @param usageBatchId identifier of usage batch
     * @return found list of {@link Scenario}s names
     */
    List<String> findScenariosNamesByUsageBatchId(String usageBatchId);

    /**
     * Deletes {@link Scenario} by given identifier.
     *
     * @param scenarioId {@link Scenario} identifier
     */
    void deleteScenario(String scenarioId);
}
