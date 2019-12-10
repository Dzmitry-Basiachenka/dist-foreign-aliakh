package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Common interface for usages controllers.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @param <C> controller instance
 * @param <W> widget instance
 * @author Uladzislau Shalamitski
 */
public interface ICommonUsageController<W extends ICommonUsageWidget<W, C>,
    C extends ICommonUsageController<W, C>> extends IController<W> {

    /**
     * {@link #onFilterChanged(FilterChangedEvent)}.
     */
    Method ON_FILTER_CHANGED =
        ReflectTools.findMethod(ICommonUsageController.class, "onFilterChanged", FilterChangedEvent.class);

    /**
     * {@link #onScenarioCreated(ScenarioCreateEvent)}.
     */
    Method ON_SCENARIO_CREATED =
        ReflectTools.findMethod(ICommonUsageController.class, "onScenarioCreated", ScenarioCreateEvent.class);

    /**
     * Handles changes of filter.
     *
     * @param event event
     */
    void onFilterChanged(FilterChangedEvent event);

    /**
     * Handles {@link com.copyright.rup.dist.foreign.domain.Scenario} creation.
     *
     * @param event {@link ScenarioCreateEvent}
     */
    void onScenarioCreated(ScenarioCreateEvent event);

    /**
     * Initializes {@link IUsagesFilterWidget}.
     *
     * @return initialized {@link IUsagesFilterWidget}
     */
    IUsagesFilterWidget initUsagesFilterWidget();

    /**
     * Vefiries whether all filtered {@link com.copyright.rup.dist.foreign.domain.Usage}s in specifies status or not.
     *
     * @param status {@link UsageStatusEnum} instance
     * @return {@code true} - if all filtered {@link com.copyright.rup.dist.foreign.domain.Usage}s
     * have specified {@link UsageStatusEnum},{@code false} - otherwise
     */
    boolean isValidUsagesState(UsageStatusEnum status);

    /**
     * Gets list of {@link UsageBatch}es associated with selected Product Family.
     *
     * @param productFamily Product Family
     * @return list of found {@link UsageBatch}es
     */
    List<UsageBatch> getUsageBatches(String productFamily);

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
     * Checks whether usage batch with provided name already exists or not.
     *
     * @param name usage batch name
     * @return {@code true} - if usage batch exists, {@code false} - otherwise
     */
    boolean usageBatchExists(String name);

    /**
     * Checks whether scenario with provided name already exists or not.
     *
     * @param name scenario name
     * @return {@code true} - if scenario exists, {@code false} - otherwise
     */
    boolean scenarioExists(String name);

    /**
     * Gets Pre-Service fee fund names associated with batch identifier.
     *
     * @param batchId batch identifier
     * @return list of names
     */
    List<String> getPreServiceFeeFundNamesByUsageBatchId(String batchId);

    /**
     * @return instance of {@link IStreamSource} for export.
     */
    IStreamSource getExportUsagesStreamSource();

    /**
     * Resets filter.
     */
    void clearFilter();

    /**
     * @return number of items.
     */
    int getBeansCount();

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
     * @return globally selected product family.
     */
    String getSelectedProductFamily();
}
