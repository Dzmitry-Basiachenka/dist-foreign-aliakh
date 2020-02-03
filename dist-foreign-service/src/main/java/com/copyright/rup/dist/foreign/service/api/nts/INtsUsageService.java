package com.copyright.rup.dist.foreign.service.api.nts;

import com.copyright.rup.dist.foreign.domain.UsageBatch;

import java.util.List;

/**
 * Represents service interface for NTS specific usages business logic.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/28/2020
 *
 * @author Aliaksandr Liakh
 */
public interface INtsUsageService {

    /**
     * Inserts usages.
     *
     * @param usageBatch instance of {@link UsageBatch}
     * @return list of inserted usages' ids
     */
    List<String> insertUsages(UsageBatch usageBatch);

    /**
     * Deletes usages from Pre-Service fee fund.
     * Reverts status of usages to {@link UsageStatusEnum#NTS_WITHDRAWN}.
     *
     * @param fundPoolId fund pool id
     */
    void deleteFromPreServiceFeeFund(String fundPoolId);

    /**
     * Deletes usages with Wr Wrk Insts that were classified as BELLETRISTIC.
     *
     * @param scenarioId scenario id
     */
    void deleteBelletristicByScenarioId(String scenarioId);

    /**
     * Deletes usages from NTS scenario. Reverts status of usages to {@link UsageStatusEnum#ELIGIBLE},
     * sets scenario id to {@code null}, sets gross amount to 0.
     *
     * @param scenarioId scenario id
     */
    void deleteFromScenario(String scenarioId);
}
