package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.impl.csv.ResearchedUsagesCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;

import java.util.Collection;
import java.util.List;

/**
 * Interface for FAS and FAS2 usages controller.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @author Uladzislau Shalamitski
 */
public interface IFasUsageController extends ICommonUsageController<IFasUsageWidget, IFasUsageController> {

    /**
     * Gets RRO from PRM by account number.
     *
     * @param rroAccountNumber RRO account number
     * @return RRO {@link Rightsholder}
     */
    Rightsholder getRro(Long rroAccountNumber);

    /**
     * @return CLA account number.
     */
    Long getClaAccountNumber();

    /**
     * Inserts usage batch and it's usages.
     *
     * @param usageBatch {@link UsageBatch} instance
     * @param usages     list of {@link Usage}s
     * @return count of inserted usages
     */
    int loadUsageBatch(UsageBatch usageBatch, Collection<Usage> usages);

    /**
     * Updates researched usage details.
     *
     * @param researchedUsages list of {@link ResearchedUsage}s
     */
    void loadResearchedUsages(List<ResearchedUsage> researchedUsages);

    /**
     * Creates a {@link Scenario} by entered scenario name and description.
     *
     * @param scenarioName name of scenario
     * @param description  description for creating scenario
     * @return created scenario
     */
    Scenario createScenario(String scenarioName, String description);

    /**
     * @return instance of {@link IStreamSource} for sending for research.
     */
    IStreamSource getSendForResearchUsagesStreamSource();

    /**
     * Return instance of {@link IStreamSource} for errors result.
     *
     * @param fileName         name of processed file
     * @param processingResult information about errors
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getErrorResultStreamSource(String fileName, DistCsvProcessor.ProcessingResult processingResult);

    /**
     * Gets instance of processor for given product family.
     *
     * @param productFamily product family
     * @return instance of {@link UsageCsvProcessor}
     */
    UsageCsvProcessor getCsvProcessor(String productFamily);

    /**
     * Gets instance of CSV processor for researched usage details.
     *
     * @return instance of {@link ResearchedUsagesCsvProcessor}
     */
    ResearchedUsagesCsvProcessor getResearchedUsagesCsvProcessor();

    /**
     * @return rightsholders account numbers that are not presented in database based on applied usage filter.
     */
    List<Long> getInvalidRightsholders();
}
