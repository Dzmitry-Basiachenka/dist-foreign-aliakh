package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.WithdrawnFundPool;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.impl.csv.ResearchedUsagesCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.impl.CreateScenarioWindow.ScenarioCreateEvent;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * Interface for controller for {@link IUsagesWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/16/17
 *
 * @author Mikita Hladkikh
 * @author Mikalai Bezmen
 */
public interface IUsagesController extends IController<IUsagesWidget> {

    /**
     * {@link #onFilterChanged(FilterChangedEvent)}.
     */
    Method ON_FILTER_CHANGED =
        ReflectTools.findMethod(IUsagesController.class, "onFilterChanged", FilterChangedEvent.class);

    /**
     * {@link #onScenarioCreated(ScenarioCreateEvent)}.
     */
    Method ON_SCENARIO_CREATED =
        ReflectTools.findMethod(IUsagesController.class, "onScenarioCreated", ScenarioCreateEvent.class);

    /**
     * Initializes {@link IUsagesFilterWidget}.
     *
     * @return initialized {@link IUsagesFilterWidget}
     */
    IUsagesFilterWidget initUsagesFilterWidget();

    /**
     * Handles changes of filter.
     *
     * @param event event
     */
    void onFilterChanged(FilterChangedEvent event);

    /**
     * Gets RRO from PRM by account number.
     *
     * @param rroAccountNumber RRO account number
     * @return RRO {@link Rightsholder}
     */
    Rightsholder getRro(Long rroAccountNumber);

    /**
     * Checks whether usage batch with provided name already exists or not.
     *
     * @param name usage batch name
     * @return {@code true} - if usage batch exists, {@code false} - otherwise
     */
    boolean usageBatchExists(String name);

    /**
     * @return number of items.
     */
    int getSize();

    /**
     * Loads specified number of beans from the storage with given start index.
     *
     * @param startIndex start index
     * @param count      items count to load
     * @param sortOrders sort orders
     * @return list of items to be displayed on UI
     */
    List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders);

    /**
     * Gets list of {@link UsageBatch}es associated with selected Product Family.
     *
     * @param productFamily Product Family
     * @return list of found {@link UsageBatch}es
     */
    List<UsageBatch> getUsageBatches(String productFamily);

    /**
     * @return list of available markets.
     */
    List<String> getMarkets();

    /**
     * @return CLA account number.
     */
    Long getClaAccountNumber();

    /**
     * Deletes {@link UsageBatch} and all it's details.
     *
     * @param usageBatch {@link UsageBatch} to delete
     */
    void deleteUsageBatch(UsageBatch usageBatch);

    /**
     * Gets a list of scenarios names that are associated with at least one
     * {@link com.copyright.rup.dist.foreign.domain.Usage} from the {@link UsageBatch} with given id.
     *
     * @param batchId {@link UsageBatch} id
     * @return a list of scenarios names
     */
    List<String> getScenariosNamesAssociatedWithUsageBatch(String batchId);

    /**
     * Inserts usage batch and it's usages.
     *
     * @param usageBatch {@link UsageBatch} instance
     * @param usages     list of {@link Usage}s
     * @return count of inserted usages
     */
    int loadUsageBatch(UsageBatch usageBatch, Collection<Usage> usages);

    /**
     * Gets count of archived usages based on batch information.
     *
     * @param usageBatch {@link UsageBatch} instance
     * @return usages count
     */
    int getUsagesCountForNtsBatch(UsageBatch usageBatch);

    /**
     * Inserts NTS batch and assigns eligible for NTS usages to it based on fund pool information.
     *
     * @param usageBatch {@link UsageBatch} to insert
     */
    void loadNtsBatch(UsageBatch usageBatch);

    /**
     * Gets all additional funds.
     *
     * @return list of {@link WithdrawnFundPool}s
     */
    List<WithdrawnFundPool> getAdditionalFunds();

    /**
     * Updates researched usage details.
     *
     * @param researchedUsages collection of {@link ResearchedUsage}s
     */
    void loadResearchedUsages(Collection<ResearchedUsage> researchedUsages);

    /**
     * Creates a {@link Scenario} by entered scenario name and description.
     *
     * @param scenarioName name of scenario
     * @param description  description for creating scenario
     * @return created scenario
     */
    Scenario createScenario(String scenarioName, String description);

    /**
     * @return instance of {@link IScenarioService}.
     */
    IScenarioService getScenarioService();

    /**
     * Handles {@link com.copyright.rup.dist.foreign.domain.Scenario} creation.
     *
     * @param event {@link ScenarioCreateEvent}
     */
    void onScenarioCreated(ScenarioCreateEvent event);

    /**
     * @return instance of {@link IStreamSource} for export.
     */
    IStreamSource getExportUsagesStreamSource();

    /**
     * @return instance of {@link IStreamSource} for sending for research.
     */
    IStreamSource getSendForResearchUsagesStreamSource();

    /**
     * @return instance of {@link IWorkClassificationController}.
     */
    IWorkClassificationController getWorkClassificationController();

    /**
     * Vefiries whether all filtered {@link Usage}s in specifies status or not.
     *
     * @param status {@link UsageStatusEnum} instance
     * @return {@code true} - if all filtered {@link Usage}s have specified {@link UsageStatusEnum},
     * {@link false} - otherwise
     */
    boolean isValidUsagesState(UsageStatusEnum status);

    /**
     * Return instance of {@link IStreamSource} for errors result.
     *
     * @param fileName         name of processed file
     * @param processingResult information about errors
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult processingResult);

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
     * @return selected product family from filter
     * or empty string in case when filter is empty or more than one product family selected.
     */
    String getSelectedProductFamily();

    /**
     * @return rightsholders account numbers that are not presented in database based on applied usage filter.
     */
    List<Long> getInvalidRightsholders();

    /**
     * Resets filter.
     */
    void clearFilter();
}
