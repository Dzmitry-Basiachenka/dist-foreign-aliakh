package com.copyright.rup.dist.foreign.ui.usage.api.fas;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.service.impl.csv.ResearchedUsagesCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;

import java.util.List;
import java.util.Set;

/**
 * Interface for FAS/FAS2 usages controller.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/16/19
 *
 * @author Darya Baraukova
 */
public interface IFasUsageController extends ICommonUsageController {

    /**
     * Inserts usage batch and its usages.
     *
     * @param usageBatch {@link UsageBatch} instance
     * @param usages     list of {@link Usage}s
     * @return count of inserted usages
     */
    int loadUsageBatch(UsageBatch usageBatch, List<Usage> usages);

    /**
     * @return CLA account number.
     */
    Long getClaAccountNumber();

    /**
     * Gets instance of CSV processor for researched usage details.
     *
     * @return instance of {@link ResearchedUsagesCsvProcessor}
     */
    ResearchedUsagesCsvProcessor getResearchedUsagesCsvProcessor();

    /**
     * Gets instance of processor for given product family.
     *
     * @param productFamily product family
     * @return instance of {@link UsageCsvProcessor}
     */
    UsageCsvProcessor getCsvProcessor(String productFamily);

    /**
     * Return instance of {@link IStreamSource} for errors result.
     *
     * @param fileName         name of processed file
     * @param processingResult information about errors
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult processingResult);

    /**
     * @return instance of {@link IStreamSource} for sending for research.
     */
    IStreamSource getSendForResearchUsagesStreamSource();

    /**
     * Updates researched usage details.
     *
     * @param researchedUsages list of {@link ResearchedUsage}s
     */
    void loadResearchedUsages(List<ResearchedUsage> researchedUsages);

    /**
     * Gets {@link UsageDto}s for updating.
     *
     * @return list of {@link UsageDto}s
     */
    List<UsageDto> getUsageDtosToUpdate();

    /**
     * Gets records threshold.
     *
     * @return records threshold
     */
    int getRecordsThreshold();

    /**
     * Updates usages Wr Wrk Inst and sends them for PI matching and getting rights.
     *
     * @param usageIds  usage ids
     * @param wrWrkInst Wr Wrk Inst
     * @param reason    action reason
     */
    void updateUsages(Set<String> usageIds, Long wrWrkInst, String reason);
}
