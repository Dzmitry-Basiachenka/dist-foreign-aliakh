package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvProcessingResult;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.impl.CreateScenarioWindow.ScenarioCreateEvent;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.ui.component.lazytable.IBeanLoader;
import com.copyright.rup.vaadin.widget.api.IController;

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
public interface IUsagesController extends IController<IUsagesWidget>, IBeanLoader<UsageDto> {

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
     * @return list of {@link UsageBatch}es available for deleting.
     */
    List<UsageBatch> getUsageBatches();

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
     * @param usageBatch    {@link UsageBatch} instance
     * @param usages        list of {@link Usage}s
     * @return count of inserted usages
     */
    int loadUsageBatch(UsageBatch usageBatch, Collection<Usage> usages);

    /**
     * Creates a scenario by entered scenario name and description.
     *
     * @param scenarioName name of scenario
     * @param description  description for creating scenario
     * @return created scenario id
     */
    String createScenario(String scenarioName, String description);

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
     * Return instance of {@link IStreamSource} for errors result.
     *
     * @param csvProcessingResult information about errors
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getErrorResultStreamSource(CsvProcessingResult csvProcessingResult);

    /**
     * @return instance of {@link UsageCsvProcessor}.
     */
    UsageCsvProcessor getCsvProcessor();

    /**
     * @return {@code true} if product family filter is not empty and selected status filter equals to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE}, {@code false} - otherwise.
     */
    boolean isProductFamilyAndStatusFiltersApplied();

    /**
     * @return {@code true} if product family filter contains single product family (except for NTS),
     * {@code false} - otherwise.
     */
    //TODO {dbaraukova} remove check that product family is not NTS after implementing create NTS scenario logic
    boolean isSigleProductFamilySelected();

    /**
     * @return {@code true} if {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#WORK_NOT_FOUND}
     * status applied, otherwise {@code false}.
     */
    boolean isWorkNotFoundStatusApplied();
}
