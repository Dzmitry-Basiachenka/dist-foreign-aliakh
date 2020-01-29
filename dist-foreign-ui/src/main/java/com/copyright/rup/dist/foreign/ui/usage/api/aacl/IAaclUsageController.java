package com.copyright.rup.dist.foreign.ui.usage.api.aacl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.impl.csv.AaclUsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.ClassifiedUsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;

import java.util.Collection;
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
     * Inserts usage batch and it's usages.
     *
     * @param usageBatch {@link UsageBatch} instance
     * @param usages     list of {@link Usage}s
     * @return count of inserted usages
     */
    int loadUsageBatch(UsageBatch usageBatch, Collection<Usage> usages);

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
}
