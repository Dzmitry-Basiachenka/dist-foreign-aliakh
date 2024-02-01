package com.copyright.rup.dist.foreign.vui.usage.api.nts;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface for NTS usages controller.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/16/2019
 *
 * @author Darya Baraukova
 */
public interface INtsUsageController extends ICommonUsageController {

    /**
     * Inserts NTS batch and assigns eligible for NTS usages to it based on fund pool information.
     *
     * @param usageBatch {@link UsageBatch} to insert
     * @return count of inserted usages
     */
    int loadNtsBatch(UsageBatch usageBatch);

    /**
     * Creates Additional Fund.
     *
     * @param fundPool {@link FundPool} instance
     * @param batchIds set of batch ids
     */
    void createAdditionalFund(FundPool fundPool, Set<String> batchIds);

    /**
     * Checks whether Additional Fund with provided name exists.
     *
     * @param name fund pool name
     * @return {@code true} - if Additional Fund exists, {@code false} - otherwise
     */
    boolean additionalFundExists(String name);

    /**
     * Gets all Additional Funds.
     *
     * @return list of {@link FundPool}s
     */
    List<FundPool> getAdditionalFunds();

    /**
     * @return list of available markets.
     */
    List<String> getMarkets();

    /**
     * Gets list of {@link UsageBatch}es suitable for including in Additional Funds.
     *
     * @return list of found {@link UsageBatch}es
     */
    List<UsageBatch> getUsageBatchesForAdditionalFunds();

    /**
     * Gets Additional Funds not attached to scenario.
     *
     * @return list of {@link FundPool}s
     */
    List<FundPool> getAdditionalFundsNotAttachedToScenario();

    /**
     * Deletes Additional Fund.
     *
     * @param fundPool {@link FundPool} to delete
     */
    void deleteAdditionalFund(FundPool fundPool);

    /**
     * Gets count of archived usages based on batch information.
     *
     * @param usageBatch {@link UsageBatch} instance
     * @return usages count
     */
    int getUsagesCountForNtsBatch(UsageBatch usageBatch);

    /**
     * @return instance of {@link IWorkClassificationController}.
     */
    IWorkClassificationController getWorkClassificationController();

    /**
     * Gets {@link Scenario} name associated with Additional Fund.
     *
     * @param fundPoolId fund pool id
     * @return {@link Scenario} name or {@code null} if none found
     */
    String getScenarioNameAssociatedWithAdditionalFund(String fundPoolId);

    /**
     * Gets map of STM and NON-STM classification to list of batch names
     * that don't have at least one usages related to corresponding STM and NON-STM classification
     * when corresponding Fund Pool amount is greater than zero.
     *
     * @param batchIds set of batch ids
     * @return map with key - classficiation, value - list of batch names with invalid classification state of usages
     */
    Map<String, List<String>> getBatchNamesWithInvalidStmOrNonStmUsagesState(Set<String> batchIds);

    /**
     * Gets list of batch names containing usages with unclassified works.
     *
     * @param batchIds set of batch ids
     * @return list of found batches names
     */
    List<String> getBatchNamesWithUnclassifiedWorks(Set<String> batchIds);

    /**
     * Gets instance of {@link IStreamSource} for Additional Fund filtered batches CSV report.
     *
     * @param batches          list of batches
     * @param totalGrossAmount total gross amount
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getAdditionalFundBatchesStreamSource(List<UsageBatch> batches, BigDecimal totalGrossAmount);

    /**
     * Gets names of processing batches (with usages in statuses besides ELIGIBLE, UNCLASSIFIED).
     *
     * @param batchesIds set of batches ids
     * @return list of batches names
     */
    List<String> getProcessingBatchesNames(Set<String> batchesIds);

    /**
     * Creates NTS {@link Scenario} by entered scenario name, rightholder's minimum amount and description.
     *
     * @param scenarioName name of scenario
     * @param ntsFields    NTS scenario specific fields
     * @param description  description for creating scenario
     * @return created scenario
     */
    Scenario createNtsScenario(String scenarioName, Scenario.NtsFields ntsFields, String description);

    /**
     * Gets map of batches names to scenario names associated with the given batches.
     *
     * @param batchesIds set of batches ids
     * @return map of batches names to scenario names
     */
    Map<String, String> getBatchesNamesToScenariosNames(Set<String> batchesIds);
}
