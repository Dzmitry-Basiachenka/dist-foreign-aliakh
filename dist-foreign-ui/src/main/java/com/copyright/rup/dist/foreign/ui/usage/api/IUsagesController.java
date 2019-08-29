package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.PreServiceFeeFund;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.impl.csv.ResearchedUsagesCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * Gets list of {@link UsageBatch}es suitable for including in Pre-Service fee funds.
     *
     * @return list of found {@link UsageBatch}es
     */
    List<UsageBatch> getUsageBatchesForPreServiceFeeFunds();

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
     * Gets {@link Scenario} name associated with Pre-Service fee fund identifier.
     *
     * @param fundId Pre-Service fee fund identifier
     * @return {@link Scenario} name
     */
    String getScenarioNameAssociatedWithPreServiceFeeFund(String fundId);

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
     * @return count of inserted usages
     */
    int loadNtsBatch(UsageBatch usageBatch);

    /**
     * Gets all {@link PreServiceFeeFund}s.
     *
     * @return list of {@link PreServiceFeeFund}s
     */
    List<PreServiceFeeFund> getPreServiceSeeFunds();

    /**
     * Gets {@link PreServiceFeeFund}s not attached to scenario.
     *
     * @return list of {@link PreServiceFeeFund}s
     */
    List<PreServiceFeeFund> getPreServiceFeeFundsNotAttachedToScenario();

    /**
     * Deletes Pre-Service fee fund.
     *
     * @param fundPool {@link PreServiceFeeFund} to delete
     */
    void deletePreServiceFeeFund(PreServiceFeeFund fundPool);

    /**
     * Gets Pre-Service fee fund names associated with batch identifier.
     *
     * @param batchId batch identifier
     * @return list of names
     */
    List<String> getPreServiceFeeFundNamesByUsageBatchId(String batchId);

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
     * Creates a {@link Scenario} by entered scenario name, rightholder's minimum amount and description.
     *
     * @param scenarioName name of scenario
     * @param ntsFields    NTS scenario specific fields
     * @param description  description for creating scenario
     * @return created scenario
     */
    Scenario createNtsScenario(String scenarioName, NtsFields ntsFields, String description);

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
     * {@code false} - otherwise
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

    /**
     * Gets instance of {@link IStreamSource} for for pre-service fee fund filtered batches CSV report.
     *
     * @param batches          list of batches
     * @param totalGrossAmount total gross amount
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getPreServiceFeeFundBatchesStreamSource(List<UsageBatch> batches, BigDecimal totalGrossAmount);

    /**
     * Gets instance of {@link IFundPoolService}.
     *
     * @return instance of {@link IFundPoolService}.
     */
    IFundPoolService getFundPoolService();

    /**
     * Gets list of batch names containing usages with unclassified works.
     *
     * @param batchIds set of batch ids
     * @return list of found batches names
     */
    List<String> getBatchNamesWithUnclassifiedWorks(Set<String> batchIds);

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
     * Gets names of processing batches (with usages in statuses besides ELIGIBLE, UNCLASSIFIED).
     *
     * @param batchesIds set of batches ids
     * @return list of batches names
     */
    List<String> getProcessingBatchesNames(Set<String> batchesIds);

    /**
     * Gets map of batches names to scenario names associated with the given batches.
     *
     * @param batchesIds set of batches ids
     * @return map of batches names to scenario names
     */
    Map<String, String> getBatchesNamesToScenariosNames(Set<String> batchesIds);
}
