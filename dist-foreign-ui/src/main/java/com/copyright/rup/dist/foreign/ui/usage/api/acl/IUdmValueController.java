package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.ExchangeRate;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.ui.Window;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;
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
     * @param udmValues set of values to assign to logged in user
     */
    void assignValues(Set<UdmValueDto> udmValues);

    /**
     * Un-assigns provided values.
     *
     * @param udmValues set of values to un-assign
     */
    void unassignValues(Set<UdmValueDto> udmValues);

    /**
     * Handles changes of filter.
     *
     * @param event event
     */
    void onFilterChanged(FilterChangedEvent event);

    /**
     * Updates UDM value.
     *
     * @param udmValueDto   {@link UdmValueDto} to update
     * @param actionReasons list of audit action reasons
     */
    void updateValue(UdmValueDto udmValueDto, List<String> actionReasons);

    /**
     * Gets list of all currencies.
     *
     * @return list of {@link Currency}
     */
    List<Currency> getAllCurrencies();

    /**
     * Gets list of all {@link PublicationType}s.
     *
     * @return list of {@link PublicationType}
     */
    List<PublicationType> getAllPublicationTypes();

    /**
     * @return threshold value for size of UDM records.
     */
    int getUdmRecordThreshold();

    /**
     * @return list of all price types.
     */
    List<String> getAllPriceTypes();

    /**
     * @return list of all price access types.
     */
    List<String> getAllPriceAccessTypes();

    /**
     * Gets exchange rate of foreign currency to USD.
     *
     * @param foreignCurrencyCode foreign currency code to load exchange rate
     * @param date                date when the currency data was updated from the service provider
     * @return instance of {@link ExchangeRate}
     */
    ExchangeRate getExchangeRate(String foreignCurrencyCode, LocalDate date);

    /**
     * Calculates and applies content unit price of UDM proxy values to UDM values.
     *
     * @param period period of usage
     * @return count of updated UDM values
     */
    int calculateProxyValues(Integer period);

    /**
     * Shows modal window with UDM value history.
     *
     * @param udmValueId    {@link com.copyright.rup.dist.foreign.domain.UdmValue} id
     * @param closeListener listener to handle window close event
     */
    void showUdmValueHistory(String udmValueId, Window.CloseListener closeListener);

    /**
     * @return instance of {@link IStreamSource} for export.
     */
    IStreamSource getExportValuesStreamSource();

    /**
     * Checks whether a values for given period is allowed for recalculating.
     *
     * @param period period to check
     * @return {@code true} if value batch for given period is allowed for recalculating, otherwise {@code false}
     */
    boolean isAllowedForRecalculating(Integer period);
}
