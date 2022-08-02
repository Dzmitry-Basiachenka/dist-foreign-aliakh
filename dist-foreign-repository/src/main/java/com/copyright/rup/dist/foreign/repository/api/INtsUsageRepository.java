package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UsageBatch;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
     * Finds count of archived usages based on markets and period for batch creation.
     *
     * @param marketPeriodFrom market period from
     * @param marketPeriodTo   market period to
     * @param markets          set of selected markets
     * @return usages count
     */
    int findCountForBatch(Integer marketPeriodFrom, Integer marketPeriodTo, Set<String> markets);

    /**
     * Finds count of unclassified usages to be updated based on set of Wr Wrk Insts.
     *
     * @param wrWrkInsts set of Wr Wrk Insts
     * @return count of usages
     */
    int findUnclassifiedUsagesCountByWrWrkInsts(Set<Long> wrWrkInsts);

    /**
     * Calculates service fee and net amounts for usages with given RH account number and scenario id.
     * Sets payee account number, participating flag and service fee percent.
     *
     * @param rhAccountNumber    RH account number
     * @param scenarioId         scenario id
     * @param serviceFee         service fee
     * @param rhParticipating    RH participating flag
     * @param payeeAccountNumber payee account number
     * @param userName           user name
     */
    void calculateAmountsAndUpdatePayeeByAccountNumber(Long rhAccountNumber, String scenarioId, BigDecimal serviceFee,
                                                       boolean rhParticipating, Long payeeAccountNumber,
                                                       String userName);

    /**
     * Proportionally distributes Post Service Fee Amount among scenario usages above minimum.
     *
     * @param scenarioId scenario id
     */
    void applyPostServiceFeeAmount(String scenarioId);

    /**
     * Deletes all NTS {@link com.copyright.rup.dist.foreign.domain.Usage}s for specified scenario.
     *
     * @param scenarioId scenario identifier
     */
    void deleteByScenarioId(String scenarioId);

    /**
     * Deletes usages from NTS Fund Pool.
     * Updates usages status to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#NTS_WITHDRAWN}.
     *
     * @param fundPoolId fund pool id
     * @param userName   user name
     */
    void deleteFromNtsFundPool(String fundPoolId, String userName);

    /**
     * Deletes usages with Wr Wrk Insts that were classified as BELLETRISTIC.
     *
     * @param scenarioId scenario id
     */
    void deleteBelletristicByScenarioId(String scenarioId);

    /**
     * Deletes usages from scenario. Updates usages associated with scenario in
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#SCENARIO_EXCLUDED} and
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#LOCKED} statuses.
     * Reverts status to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE},
     * sets scenario id to {@code null}, sets gross amount to 0.
     *
     * @param scenarioId scenario id
     * @param userName   user name
     */
    void deleteFromScenario(String scenarioId, String userName);

    /**
     * Finds {@link com.copyright.rup.dist.foreign.domain.Usage} ids with belletristic classification or
     * classified usages in
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#UNCLASSIFIED} status.
     *
     * @return list of {@link com.copyright.rup.dist.foreign.domain.Usage} ids
     */
    List<String> findUsageIdsForClassificationUpdate();

    /**
     * Updates status to UNCLASSIFIED for ELIGIBLE usages with defined works without classification.
     *
     * @param wrWrkInsts list of Wr Wrk Insts
     * @param userName   user name
     */
    void updateUsagesStatusToUnclassified(List<Long> wrWrkInsts, String userName);

    /**
     * Updates usages with status NTS_WITHDRAWN from given batches to status TO_BE_DISTRIBUTED
     * and adds the usages to the NTS fund pool.
     *
     * @param fundPoolId id of fund pool
     * @param batchIds   set of ids of usage batches
     * @param userName   user name
     */
    void addWithdrawnUsagesToNtsFundPool(String fundPoolId, Set<String> batchIds, String userName);

    /**
     * Deletes {@link com.copyright.rup.dist.foreign.domain.Usage}s from scenario.
     * Sets status of {@link com.copyright.rup.dist.foreign.domain.Usage}s to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#SCENARIO_EXCLUDED}
     * sets scenario id, payee account number, service fee to {@code null}, service fee amount, gross amount and
     * net amount to 0.
     *
     * @param scenarioId     {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param accountNumbers set of rightsholders' account numbers
     * @param userName       user name
     * @return set of {@link com.copyright.rup.dist.foreign.domain.Usage}'s identifiers
     */
    Set<String> deleteFromScenarioByRightsholder(String scenarioId, Set<Long> accountNumbers, String userName);

    /**
     * Proportionally distributes Amounts among scenario usages.
     *
     * @param scenarioId     {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param accountNumbers set of rightsholders' account numbers
     */
    void recalculateAmountsFromExcludedRightshoders(String scenarioId, Set<Long> accountNumbers);
}
