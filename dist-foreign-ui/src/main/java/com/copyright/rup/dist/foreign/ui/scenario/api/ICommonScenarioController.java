package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.vaadin.widget.SearchWidget.ISearchController;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.data.provider.QuerySortOrder;

import java.util.List;

/**
 * Controller interface for {@link ICommonScenarioWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @param <W> type of widget
 * @param <C> type of controller
 * @author Stanislau Rudak
 */
public interface ICommonScenarioController<W extends ICommonScenarioWidget<W, C>,
    C extends ICommonScenarioController<W, C>> extends IController<W>, ISearchController {

    /**
     * @return instance of {@link IStreamSource} for export details.
     */
    IStreamSource getExportScenarioUsagesStreamSource();

    /**
     * @return instance of {@link IStreamSource} for export scenario.
     */
    IStreamSource getExportScenarioRightsholderTotalsStreamSource();

    /**
     * Handles click on "Rightsholder Account Number" button.
     *
     * @param accountNumber    rightsholder account number
     * @param rightsholderName rightsholder name
     */
    void onRightsholderAccountNumberClicked(Long accountNumber, String rightsholderName);

    /**
     * @return boolean result that shows whether scenario is empty or not.
     */
    boolean isScenarioEmpty();

    /**
     * @return current {@link Scenario}.
     */
    Scenario getScenario();

    /**
     * Sets the {@link Scenario}.
     *
     * @param scenario a {@link Scenario} to use
     */
    void setScenario(Scenario scenario);

    /**
     * @return scenario {@link Scenario} with calculated amounts and last audit action.
     */
    Scenario getScenarioWithAmountsAndLastAction();

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
    List<RightsholderTotalsHolder> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders);
}
