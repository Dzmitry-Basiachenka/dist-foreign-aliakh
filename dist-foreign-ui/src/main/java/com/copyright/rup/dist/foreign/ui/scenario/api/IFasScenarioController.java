package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;

import java.util.List;

/**
 * Controller interface for {@link IFasScenarioWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
public interface IFasScenarioController extends ICommonScenarioController<IFasScenarioWidget, IFasScenarioController> {

    /**
     * Handles click on "Exclude By RRO" button.
     */
    void onExcludeByRroClicked();

    /**
     * Handles click on "Exclude By Payee" button.
     */
    void onExcludeByPayeeClicked();

    /**
     * @return all source RROs belonging to the scenario.
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
    // TODO {srudak} rename?
    void fireWidgetEvent(ExcludeUsagesEvent event);
}
