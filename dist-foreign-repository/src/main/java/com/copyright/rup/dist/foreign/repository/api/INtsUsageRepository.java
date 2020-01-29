package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UsageBatch;

import java.util.List;

/**
 * Interface for NTS usage repository.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/28/2020
 *
 * @author Aliaksandr Liakh
 */
public interface INtsUsageRepository {

    /**
     * Inserts usages from archived FAS usages based on NTS Batch criteria (Market Period From/To, Markets).
     * Belletristic usages and usages that do not meet minimum Cutoff Amounts will not be inserted.
     *
     * @param usageBatch instance of {@link UsageBatch}
     * @param userName   user name
     * @return list of inserted usages' ids
     */
    List<String> insertUsages(UsageBatch usageBatch, String userName);

    /**
     * Deletes usages from Pre-Service fee fund.
     * Updates usages status to {@link UsageStatusEnum#NTS_WITHDRAWN}.
     *
     * @param fundPoolId fund pool id
     * @param userName   user name
     */
    void deleteFromPreServiceFeeFund(String fundPoolId, String userName);

    /**
     * Deletes usages with Wr Wrk Insts that were classified as BELLETRISTIC.
     *
     * @param scenarioId scenario id
     */
    void deleteBelletristicByScenarioId(String scenarioId);

    /**
     * Deletes usages from scenario. Updates usages associated with scenario in
     * {@link UsageStatusEnum#NTS_EXCLUDED} and {@link UsageStatusEnum#LOCKED} statuses.
     * Reverts status to {@link UsageStatusEnum#ELIGIBLE}, sets scenario id to {@code null}, sets gross amount to 0.
     *
     * @param scenarioId scenario id
     * @param userName   user name
     */
    void deleteFromScenario(String scenarioId, String userName);
}
