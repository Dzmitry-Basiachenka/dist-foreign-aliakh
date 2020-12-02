package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.Scenario;
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
 * @author Uladzislau Shalamitski
 */
public interface ICommonUsageController extends IController<ICommonUsageWidget> {

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
     * Gets {@link ICommonUsageFilterController}.
     *
     * @return instance of {@link ICommonUsageFilterController}
     */
    ICommonUsageFilterController getUsageFilterController();

    /**
     * @return instance of {@link IStreamSource} for export.
     */
    IStreamSource getExportUsagesStreamSource();

    /**
     * Initializes {@link ICommonUsageFilterWidget}.
     *
     * @return initialized {@link ICommonUsageFilterWidget}
     */
    ICommonUsageFilterWidget initUsagesFilterWidget();

    /**
     * Handles changes of filter.
     *
     * @param event event
     */
    void onFilterChanged(FilterChangedEvent event);

    /**
     * Gets RH from PRM by account number.
     *
     * @param rhAccountNumber RH account number
     * @return {@link Rightsholder}
     */
    Rightsholder getRightsholder(Long rhAccountNumber);

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
     * Creates a {@link Scenario} by entered scenario name and description.
     *
     * @param scenarioName name of scenario
     * @param description  description for creating scenario
     * @return created scenario
     */
    Scenario createScenario(String scenarioName, String description);

    /**
     * Gets Additional Funds' names by usage batch id.
     *
     * @param batchId batch id
     * @return list of found Additional Funds' names
     */
    List<String> getAdditionalFundNamesByUsageBatchId(String batchId);

    /**
     * Handles {@link com.copyright.rup.dist.foreign.domain.Scenario} creation.
     *
     * @param event {@link ScenarioCreateEvent}
     */
    void onScenarioCreated(ScenarioCreateEvent event);

    /**
     * Vefiries whether all filtered {@link com.copyright.rup.dist.foreign.domain.Usage}s in specified status or not.
     *
     * @param status {@link UsageStatusEnum} instance
     * @return {@code true} - if all filtered usages have specified {@link UsageStatusEnum},
     * {@code false} - otherwise
     */
    boolean isValidFilteredUsageStatus(UsageStatusEnum status);

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
     * Checks whether scenario with provided name exists or not.
     *
     * @param name scenario name
     * @return {@code true} - if scenario exists, {@code false} - otherwise
     */
    boolean scenarioExists(String name);
}
