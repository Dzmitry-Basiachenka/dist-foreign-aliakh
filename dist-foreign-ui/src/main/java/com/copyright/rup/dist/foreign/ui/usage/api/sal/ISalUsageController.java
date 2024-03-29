package com.copyright.rup.dist.foreign.ui.usage.api.sal;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.SalGradeGroupEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.service.impl.csv.SalItemBankCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.SalUsageDataCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;

import java.util.List;
import java.util.Set;

/**
 * Interface for SAL usages controller.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Ihar Suvorau
 */
public interface ISalUsageController extends ICommonUsageController {

    /**
     * Gets licensee name by provided account number.
     *
     * @param licenseeAccountNumber licensee account number
     * @return licensee name
     */
    String getLicenseeName(Long licenseeAccountNumber);

    /**
     * Inserts item bank and its usages.
     *
     * @param itemBank {@link UsageBatch} instance
     * @param usages   list of {@link Usage}s
     */
    void loadItemBank(UsageBatch itemBank, List<Usage> usages);

    /**
     * Inserts usage data.
     *
     * @param itemBank {@link UsageBatch} instance
     * @param usages   list of {@link Usage}s
     */
    void loadUsageData(UsageBatch itemBank, List<Usage> usages);

    /**
     * @return instance of {@link SalItemBankCsvProcessor}
     */
    SalItemBankCsvProcessor getSalItemBankCsvProcessor();

    /**
     * Checks whether SAL fund pool with provided name already exists or not.
     *
     * @param name SAL fund pool name
     * @return {@code true} if fund pool exists, otherwise {@code false}
     */
    boolean fundPoolExists(String name);

    /**
     * Gets presented grade groups from filtered UD usages.
     *
     * @return list of {@link SalGradeGroupEnum}
     */
    List<SalGradeGroupEnum> getUsageDataGradeGroups();

    /**
     * Gets SAL usage data CSV processor.
     *
     * @param itemBankId item bank id
     * @return instance of {@link SalUsageDataCsvProcessor}
     */
    SalUsageDataCsvProcessor getSalUsageDataCsvProcessor(String itemBankId);

    /**
     * Return instance of {@link IStreamSource} for errors result.
     *
     * @param fileName         name of processed file
     * @param processingResult information about errors
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult processingResult);

    /**
     * @return instance of {@link IStreamSource} for export fund pools.
     */
    IStreamSource getExportFundPoolsStreamSource();

    /**
     * @return list of existing {@link FundPool}s.
     */
    List<FundPool> getFundPools();

    /**
     * Gets {@link com.copyright.rup.dist.foreign.domain.Scenario} name associated with fund pool.
     *
     * @param fundPoolId fund pool id
     * @return {@link com.copyright.rup.dist.foreign.domain.Scenario} name or {@code null} if none found
     */
    String getScenarioNameAssociatedWithFundPool(String fundPoolId);

    /**
     * Deletes {@link FundPool}.
     *
     * @param fundPool a {@link FundPool} to delete
     */
    void deleteFundPool(FundPool fundPool);

    /**
     * @return list of {@link FundPool}s that are not attached to a scenario.
     */
    List<FundPool> getFundPoolsNotAttachedToScenario();

    /**
     * Gets names of processing batches (with usages in statuses besides WORK_NOT_FOUND, ELIGIBLE).
     *
     * @param batchesIds set of batches ids
     * @return list of batches names
     */
    List<String> getProcessingBatchesNames(Set<String> batchesIds);

    /**
     * Gets names of batches with usages in status, different from ELIGIBLE.
     *
     * @param batchesIds set of batches ids
     * @return list of batches names
     */
    List<String> getIneligibleBatchesNames(Set<String> batchesIds);

    /**
     * Checks whether usage details exist in the given batch.
     *
     * @param batchId batch id
     * @return {@code true} - if usage details exist, {@code false} - otherwise
     */
    boolean usageDataExists(String batchId);

    /**
     * Checks whether usage details exist for any of provided IB details.
     *
     * @param itemBankDetails set of IB details
     * @return {@code true} - if usage detail exist, {@code false} - otherwise
     */
    boolean usageDataExists(Set<UsageDto> itemBankDetails);

    /**
     * Deletes {@link Usage}s with detail type UD.
     *
     * @param usageBatch {@link UsageBatch} to delete usage details
     */
    void deleteUsageData(UsageBatch usageBatch);

    /**
     * Deletes selected usages from IB batch and associated UD details if any.
     *
     * @param usagesToExclude selected IB usages
     */
    void excludeUsageDetails(Set<UsageDto> usagesToExclude);

    /**
     * @return list of SAL {@link UsageBatch}es that are not attached to a scenario.
     */
    List<UsageBatch> getBatchesNotAttachedToScenario();

    /**
     * Calculates Total Gross Amount, Item Bank Gross Amount, Grade K-5 Gross Amount, Grade 6-8 Gross Amount,
     * Grade 9-12 Gross Amount for given SAL {@link FundPool}.
     *
     * @param fundPool SAL fund pool to calculate
     * @return calculated SAL {@link FundPool}
     */
    FundPool calculateFundPoolAmounts(FundPool fundPool);

    /**
     * Creates SAL scenario by entered scenario name and description.
     *
     * @param scenarioName name of scenario
     * @param fundPoolId   fund pool identifier
     * @param description  description for creating scenario
     * @return created {@link Scenario}
     */
    Scenario createSalScenario(String scenarioName, String fundPoolId, String description);

    /**
     * Creates SAL Fund Pool.
     *
     * @param fundPool instance of {@link FundPool}
     */
    void createFundPool(FundPool fundPool);

    /**
     * Gets {@link UsageBatch} by filtered identifier.
     *
     * @return instance of {@link UsageBatch}
     */
    UsageBatch getSelectedUsageBatch();

    /**
     * Gets list of {@link UsageDto}s for RH update by applied filter.
     *
     * @return list of found {@link UsageDto}s
     */
    List<UsageDto> getUsageDtosForRhUpdate();

    /**
     * Updates usage with provided RH account number
     * and changes status to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE}.
     *
     * @param usageIds        set of usage ids
     * @param rhAccountNumber RH account number
     * @param reason          reason for RH update
     */
    void updateToEligibleWithRhAccountNumber(Set<String> usageIds, Long rhAccountNumber, String reason);

    /**
     * Verifies whether status filter is applied
     * and has value {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#RH_NOT_FOUND}
     * or {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#WORK_NOT_GRANTED}.
     *
     * @return {@code true} - if status filter is applied, {@code false} - otherwise
     */
    boolean isValidStatusFilterApplied();

    /**
     * @return maximum records size to allow Select All button.
     */
    int getGridRecordThreshold();
}
