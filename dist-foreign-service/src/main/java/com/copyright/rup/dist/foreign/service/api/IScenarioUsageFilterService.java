package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;

/**
 * Represents interface of service for scenario usages filters.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/05/2018
 *
 * @author Aliaksandr Liakh
 */
public interface IScenarioUsageFilterService {

    /**
     * Inserts new usage filter.
     *
     * @param scenarioId  the id of scenario
     * @param usageFilter usage filter instance
     */
    void insert(String scenarioId, ScenarioUsageFilter usageFilter);

    /**
     * Removes all filters related to the scenario.
     *
     * @param scenarioId the id of scenario
     */
    void removeByScenarioId(String scenarioId);

    /**
     * Gets usage filter criteria for scenario.
     *
     * @param scenarioId the id of scenario
     * @return usage filter instance or null, if scenario has no usage filter
     */
    ScenarioUsageFilter getByScenarioId(String scenarioId);
}
