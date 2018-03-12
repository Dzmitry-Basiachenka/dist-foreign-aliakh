package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.ScenarioUsageFilter;

import java.util.Set;

/**
 * Represents interface of repository for scenario usages filters.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/05/2018
 *
 * @author Aliaksandr Liakh
 */
public interface IScenarioUsageFilterRepository {

    /**
     * Inserts new usage filter.
     *
     * @param usageFilter usage filter instance.
     */
    void insert(ScenarioUsageFilter usageFilter);

    /**
     * Inserts all RH account numbers values related to the specified filter.
     *
     * @param filterId         the id of filter.
     * @param rhAccountNumbers the set of account numbers.
     */
    void insertRhAccountNumbers(String filterId, Set<Long> rhAccountNumbers);

    /**
     * Inserts all usage batches ids related to the specified filter.
     *
     * @param filterId        the id of filter.
     * @param usageBatchesIds the set of license numbers.
     */
    void insertUsageBatchesIds(String filterId, Set<String> usageBatchesIds);

    /**
     * Finds filter for scenario with specified id.
     *
     * @param scenarioId the id of scenario.
     * @return list of filter for scenario.
     */
    ScenarioUsageFilter findByScenarioId(String scenarioId);

    /**
     * Deletes filter by scenario id.
     *
     * @param scenarioId the id of scenario.
     */
    void deleteByScenarioId(String scenarioId);
}
