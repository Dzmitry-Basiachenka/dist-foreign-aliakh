package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.vaadin.widget.SearchWidget.ISearchController;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.data.provider.QuerySortOrder;

import java.util.List;

/**
 * Interface for controller of viewing scenario widget.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/31/17
 *
 * @author Ihar Suvorau
 */
public interface IScenarioController extends IController<IScenarioWidget>, ISearchController {

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
     * Handles click on "Exclude By RRO" button.
     */
    void onExcludeByRroClicked();

    /**
     * Handles click on "Exclude By Payee" button.
     */
    void onExcludeByPayeeClicked();

    /**
     * @return boolean result that shows whether scenario is empty or not.
     */
    boolean isScenarioEmpty();

    /**
     * @return current {@link Scenario}.
     */
    Scenario getScenario();

    /**
     * @return scenario {@link Scenario} with calculated amounts and last audit action.
     */
    Scenario getScenarioWithAmountsAndLastAction();

    /**
     * @return all source RROs belonging to the {@link Scenario}.
     */
    List<Rightsholder> getSourceRros();

    /**
     * Finds all {@link RightsholderPayeePair}s belonging to the source RRO with given account number within the
     * chosen scenario.
     *
     * @param rroAccountNumber RRO account number
     * @return list of {@link RightsholderPayeePair}s
     */
    List<RightsholderPayeePair> getRightsholdersPayeePairs(Long rroAccountNumber);

    /**
     * Exclude details by rightsholders' account numbers only for given RRO.
     *
     * @param rroAccountNumber RRO account number
     * @param accountNumbers   list of rightsholders' account numbers
     * @param reason           reason
     */
    void deleteFromScenario(Long rroAccountNumber, List<Long> accountNumbers, String reason);

    /**
     * Fires {@link ExcludeUsagesEvent}.
     *
     * @param event an instance of {@link ExcludeUsagesEvent}
     */
    void fireWidgetEvent(ExcludeUsagesEvent event);

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
