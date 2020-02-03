package com.copyright.rup.dist.foreign.service.api.nts;

import com.copyright.rup.dist.foreign.domain.Scenario;
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
     * Gets count of archived usages based on fund pool information.
     *
     * @param usageBatch instance of {@link UsageBatch}
     * @return usages count
     */
    int getUsagesCountForBatch(UsageBatch usageBatch);

    /**
     * Updates under minimum usages grouped by Wr Wrk Inst in {@link UsageStatusEnum#RH_NOT_FOUND} status.
     * Sets product family and {@link UsageStatusEnum#NTS_WITHDRAWN} status.
     *
     * @return updated usages ids
     */
    List<String> updateNtsWithdrawnUsagesAndGetIds();

    /**
     * Populates payee and calculates amounts for usages of given scenario.
     * Calculates service fee and service fee amount based on RHs participation status,
     * proportionally distributes Post Service Fee Amount among usages and updates gross and net amounts,
     * sets participation status.
     *
     * @param scenario scenario
     */
    void populatePayeeAndCalculateAmountsForScenarioUsages(Scenario scenario);

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
