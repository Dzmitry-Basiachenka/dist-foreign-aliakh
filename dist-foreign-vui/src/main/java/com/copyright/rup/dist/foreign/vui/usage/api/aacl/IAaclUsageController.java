package com.copyright.rup.dist.foreign.vui.usage.api.aacl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.impl.csv.AaclFundPoolCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.AaclUsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.ClassifiedUsageCsvProcessor;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface for AACL usages controller.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakh
 */
public interface IAaclUsageController extends ICommonUsageController {

    /**
     * Inserts usage batch and its usages.
     *
     * @param usageBatch {@link UsageBatch} instance
     * @param usages     list of {@link Usage}s
     * @return count of inserted usages
     */
    int loadUsageBatch(UsageBatch usageBatch, List<Usage> usages);

    /**
     * Gets {@link FundPool} by id.
     *
     * @param fundPoolId {@link FundPool} id
     * @return found {@link FundPool} or {@code null} if none found
     */
    FundPool getFundPoolById(String fundPoolId);

    /**
     * @return list of existing {@link FundPool}s.
     */
    List<FundPool> getFundPools();

    /**
     * @return list of {@link FundPool}s that are not attached to a scenario.
     */
    List<FundPool> getFundPoolsNotAttachedToScenario();

    /**
     * Gets {@link FundPoolDetail}s by {@link FundPool} id.
     *
     * @param fundPoolId {@link FundPool} id
     * @return list of {@link FundPoolDetail}s
     */
    List<FundPoolDetail> getFundPoolDetails(String fundPoolId);

    /**
     * Gets {@link Scenario} name associated with fund pool.
     *
     * @param fundPoolId fund pool id
     * @return {@link Scenario} name or {@code null} if none found
     */
    String getScenarioNameAssociatedWithFundPool(String fundPoolId);

    /**
     * Deletes {@link FundPool}.
     *
     * @param fundPool a {@link FundPool} to delete
     */
    void deleteFundPool(FundPool fundPool);

    /**
     * Gets instance of CSV processor.
     *
     * @return instance of {@link AaclUsageCsvProcessor}
     */
    AaclUsageCsvProcessor getCsvProcessor();

    /**
     * Gets instance of CSV processor for classified usage details.
     *
     * @return instance of {@link ClassifiedUsageCsvProcessor}
     */
    ClassifiedUsageCsvProcessor getClassifiedUsageCsvProcessor();

    /**
     * @return instance of {@link IStreamSource} for sending for classification.
     */
    IStreamSource getSendForClassificationUsagesStreamSource();

    /**
     * Updates classified usage details.
     *
     * @param classifiedUsages list of {@link AaclClassifiedUsage}s
     * @return the number of usages that were updated, but not deleted
     */
    int loadClassifiedUsages(List<AaclClassifiedUsage> classifiedUsages);

    /**
     * Return instance of {@link IStreamSource} for errors result.
     *
     * @param fileName         name of processed file
     * @param processingResult information about errors
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult processingResult);

    /**
     * Checks whether AACL fund pool with provided name already exists or not.
     *
     * @param name AACL fund pool name
     * @return {@code true} - if AACL fund pool exists, {@code false} - otherwise
     */
    boolean fundPoolExists(String name);

    /**
     * Gets instance of AACL fund pool CSV processor.
     *
     * @return instance of {@link AaclFundPoolCsvProcessor}
     */
    AaclFundPoolCsvProcessor getAaclFundPoolCsvProcessor();

    /**
     * Inserts AACL fund pool and its details.
     *
     * @param fundPool instance of {@link FundPool}
     * @param details  list of {@link FundPoolDetail}s
     * @return count of inserted details
     */
    int insertFundPool(FundPool fundPool, List<FundPoolDetail> details);

    /**
     * Gets list of default {@link UsageAge}s by list of periods.
     *
     * @param periods list of periods
     * @return list of default {@link UsageAge}s
     */
    List<UsageAge> getDefaultUsageAges(List<Integer> periods);

    /**
     * Gets list of {@link UsageAge}s by filter.
     *
     * @return list of found {@link UsageAge}s
     */
    List<UsageAge> getUsageAges();

    /**
     * Gets list of all {@link PublicationType}s.
     *
     * @return list of {@link PublicationType}.
     */
    List<PublicationType> getPublicationTypes();

    /**
     * Gets list of all {@link DetailLicenseeClass}s.
     *
     * @return list of {@link DetailLicenseeClass}.
     */
    List<DetailLicenseeClass> getDetailLicenseeClasses();

    /**
     * Gets names of processing batches (with usages in statuses besides WORK_NOT_FOUND, RH_FOUND, WORK_RESEARCH,
     * ELIGIBLE).
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
     * Gets map of batches names to scenario names associated with the given batches.
     *
     * @param batchesIds set of batches ids
     * @return map of batches names to scenario names
     */
    Map<String, String> getBatchesNamesToScenariosNames(Set<String> batchesIds);

    /**
     * Gets aggregate licensee classes that have money that can't be distributed.
     *
     * @param fundPoolId fund pool id
     * @param mapping    {@link DetailLicenseeClass} to {@link AggregateLicenseeClass} mapping
     * @return list of {@link AggregateLicenseeClass}es
     */
    List<AggregateLicenseeClass> getAggregateClassesNotToBeDistributed(String fundPoolId,
                                                                       List<DetailLicenseeClass> mapping);

    /**
     * Creates AACL {@link Scenario} by entered scenario name and description.
     *
     * @param scenarioName name of scenario
     * @param aaclFields   AACL scenario specific fields
     * @param description  description for creating scenario
     * @return created scenario
     */
    Scenario createAaclScenario(String scenarioName, AaclFields aaclFields, String description);

    /**
     * Verifies whether all filtered {@link Usage}s are eligible for sending for classification.
     *
     * @return {@code true} if all filtered usages aren't baseline and have RH_FOUND status, {@code false} - otherwise
     */
    boolean isValidForClassification();
}
