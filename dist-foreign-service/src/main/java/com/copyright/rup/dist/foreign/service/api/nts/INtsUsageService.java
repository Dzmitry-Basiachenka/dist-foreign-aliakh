package com.copyright.rup.dist.foreign.service.api.nts;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;

import java.util.List;
import java.util.Set;

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
     * Gets count of unclassified usages to be updated based on set of Wr Wrk Insts.
     *
     * @param wrWrkInsts set of Wr Wrk Insts
     * @return usages count
     */
    int getUnclassifiedUsagesCount(Set<Long> wrWrkInsts);

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
     * Deletes usages from NTS fund pool.
     * Reverts status of usages to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#NTS_WITHDRAWN}.
     *
     * @param fundPoolId fund pool id
     */
    void deleteFromNtsFundPool(String fundPoolId);

    /**
     * Deletes usages with Wr Wrk Insts that were classified as BELLETRISTIC.
     *
     * @param scenarioId scenario id
     */
    void deleteBelletristicByScenarioId(String scenarioId);

    /**
     * Deletes usages from NTS scenario. Reverts status of usages to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE}, sets scenario id to {@code null},
     * sets gross amount to 0.
     *
     * @param scenarioId scenario id
     */
    void deleteFromScenario(String scenarioId);

    /**
     * Moves NTS {@link com.copyright.rup.dist.foreign.domain.Usage}s to the archive for given {@link Scenario}.
     *
     * @param scenario {@link Scenario}
     * @return list of moved to archive {@link com.copyright.rup.dist.foreign.domain.Usage}s ids
     */
    List<String> moveToArchive(Scenario scenario);

    /**
     * @return list of supported markets.
     */
    List<String> getMarkets();

    /**
     * Updates usages with status {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#NTS_WITHDRAWN}
     * from given batches to status TO_BE_DISTRIBUTED
     * and adds the usages to the fund pool.
     *
     * @param fundPoolId id of fund pool
     * @param batchIds   set of ids of usage batches
     * @param userName   user name
     */
    void addWithdrawnUsagesToNtsFundPool(String fundPoolId, Set<String> batchIds, String userName);

    /**
     * Finds NTS usages by their ids and sends them to queue for getting Rights process.
     *
     * @param usageIds  list of usages ids
     * @param batchName batch name
     */
    void sendForGettingRights(List<String> usageIds, String batchName);
}
