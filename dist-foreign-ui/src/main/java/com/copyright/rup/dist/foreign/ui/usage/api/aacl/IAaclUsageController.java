package com.copyright.rup.dist.foreign.ui.usage.api.aacl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
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
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;

import java.util.List;

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
     * @return list of existing {@link FundPool}s.
     */
    List<FundPool> getFundPools();

    /**
     * Gets {@link FundPoolDetail}s by {@link FundPool} id.
     *
     * @param fundPoolId {@link FundPool} id
     * @return list of {@link FundPoolDetail}s
     */
    List<FundPoolDetail> getFundPoolDetails(String fundPoolId);

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
     * Creates AACL {@link Scenario} by entered scenario name and description.
     *
     * @param scenarioName name of scenario
     * @param aaclFields   AACL scenario specific fields
     * @param description  description for creating scenario
     * @return created scenario
     */
    Scenario createAaclScenario(String scenarioName, AaclFields aaclFields, String description);
}
