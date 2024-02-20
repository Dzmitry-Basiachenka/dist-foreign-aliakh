package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;

import java.io.Serializable;
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
public interface IScenarioUsageFilterRepository extends Serializable {

    /**
     * Inserts new usage filter.
     *
     * @param usageFilter usage filter instance
     */
    void insert(ScenarioUsageFilter usageFilter);

    /**
     * Inserts all RH account numbers values related to the specified filter.
     *
     * @param filterId         the id of filter
     * @param rhAccountNumbers the set of RH account numbers
     */
    void insertRhAccountNumbers(String filterId, Set<Long> rhAccountNumbers);

    /**
     * Inserts all usage batches ids related to the specified filter.
     *
     * @param filterId        the id of filter
     * @param usageBatchesIds the set of usage batches ids
     */
    void insertUsageBatchesIds(String filterId, Set<String> usageBatchesIds);

    /**
     * Finds filter for scenario with specified id.
     *
     * @param scenarioId the id of scenario
     * @return usage filter instance or null, if scenario has no usage filter
     */
    ScenarioUsageFilter findByScenarioId(String scenarioId);

    /**
     * Deletes filter by scenario id.
     *
     * @param scenarioId the id of scenario
     */
    void deleteByScenarioId(String scenarioId);
}
