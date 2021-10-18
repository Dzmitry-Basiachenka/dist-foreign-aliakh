package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface for UDM value controller.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmValueController extends IController<IUdmValueWidget> {

    /**
     * {@link #onFilterChanged(FilterChangedEvent)}.
     */
    Method ON_FILTER_CHANGED =
        ReflectTools.findMethod(IUdmValueController.class, "onFilterChanged", FilterChangedEvent.class);

    /**
     * Gets all available value periods.
     *
     * @return list of periods
     */
    List<Integer> getPeriods();

    /**
     * Gets all available baseline periods.
     *
     * @return list of periods
     */
    List<Integer> getBaselinePeriods();

    /**
     * Populates value batch.
     *
     * @param period period of usage
     * @return count of populated values
     */
    int populatesValueBatch(Integer period);

    /**
     * Publishes value batch to baseline.
     *
     * @param period period of value batch to publish
     * @return count of published values
     */
    int publishToBaseline(Integer period);

    /**
     * Checks whether a value batch for given period is allowed for publishing.
     * In order to publish the entire batch, all work values must have: Pub Type, Content Unit Price.
     * And all work values must NOT be in the following statuses: NEW, RSCHD_IN_THE_PREV_PERIOD
     *
     * @param period period of value batch to check
     * @return {@code true} if value batch for given period is allowed for publishing, otherwise {@code false}
     */
    boolean isAllowedForPublishing(Integer period);

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
    List<UdmValueDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders);

    /**
     * Initializes {@link IUdmValueFilterWidget}.
     *
     * @return initialized {@link IUdmValueFilterWidget}
     */
    IUdmValueFilterWidget initValuesFilterWidget();

    /**
     * Assigns provided values to logged in user.
     *
     * @param valueIds set of value ids to assign to logged in user
     */
    void assignValues(Set<String> valueIds);

    /**
     * Un-assigns provided values.
     *
     * @param valueIds set of value ids to un-assign
     */
    void unassignValues(Set<String> valueIds);

    /**
     * Handles changes of filter.
     *
     * @param event event
     */
    void onFilterChanged(FilterChangedEvent event);

    /**
     * Updates UDM value.
     *
     * @param udmValueDto {@link UdmValueDto} to update
     */
    void updateValue(UdmValueDto udmValueDto);

    /**
     * Gets map of currency codes to currency names.
     *
     * @return map of currency codes to currency names
     */
    Map<String, String> getCurrencyCodesToCurrencyNamesMap();

    /**
     * Gets list of all {@link PublicationType}s.
     *
     * @return list of {@link PublicationType}
     */
    List<PublicationType> getPublicationTypes();

    /**
     * @return threshold value for size of UDM records.
     */
    int getUdmRecordThreshold();
}
