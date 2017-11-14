package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.scenario.impl.ExcludeRightsholdersWindow;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.ui.component.lazytable.IBeanLoader;
import com.copyright.rup.vaadin.widget.SearchWidget.ISearchController;
import com.copyright.rup.vaadin.widget.api.IController;

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
public interface IScenarioController extends IController<IScenarioWidget>, ISearchController,
    IBeanLoader<RightsholderTotalsHolder> {

    /**
     * @return instance of {@link IStreamSource} for export.
     */
    IStreamSource getExportScenarioUsagesStreamSource();

    /**
     * Handles click on "Rightsholder Account Number" button.
     *
     * @param accountNumber    rightsholder account number
     * @param rightsholderName rightsholder name
     */
    void onRightsholderAccountNumberClicked(Long accountNumber, String rightsholderName);

    /**
     * Handles click on "Exclude Details" button.
     */
    void onExcludeDetailsClicked();

    /**
     * @return boolean result that shows whether scenario is empty or not.
     */
    boolean isScenarioEmpty();

    /**
     * @return current {@link Scenario}.
     */
    Scenario getScenario();

    /**
     * @return scenario {@link Scenario} with calculated amounts.
     */
    Scenario getScenarioWithAmounts();

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
     * Fires {@link ExcludeRightsholdersWindow.ExcludeUsagesEvent}.
     *
     * @param event an instance of {@link ExcludeRightsholdersWindow.ExcludeUsagesEvent}
     */
    void fireWidgetEvent(ExcludeRightsholdersWindow.ExcludeUsagesEvent event);
}
