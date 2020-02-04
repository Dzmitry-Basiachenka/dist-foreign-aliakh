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
     * Gets count of archived usages based on markets and period for batch creation.
     *
     * @param marketPeriodFrom market period from
     * @param marketPeriodTo   market period to
     * @param markets          set of selected markets
     * @return usages count
     */
    int findCountForBatch(Integer marketPeriodFrom, Integer marketPeriodTo, Set<String> markets);

    /**
     * Updates under minimum usages grouped by Wr Wrk Inst in {@link UsageStatusEnum#RH_NOT_FOUND} status.
     * Sets NTS product family and {@link UsageStatusEnum#NTS_WITHDRAWN} status.
     *
     * @return updated usages ids
     */
    List<String> updateNtsWithdrawnUsagesAndGetIds();

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
