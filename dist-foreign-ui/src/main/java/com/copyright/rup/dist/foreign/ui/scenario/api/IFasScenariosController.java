package com.copyright.rup.dist.foreign.ui.scenario.api;

import java.util.List;

/**
 * Controller interface for {@link IFasScenariosWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
public interface IFasScenariosController
    extends ICommonScenariosController<IFasScenariosWidget, IFasScenariosController> {

    /**
     * @return rightsholders account numbers that are not presented in database based on applied usage filter.
     */
    List<Long> getInvalidRightsholders();

    /**
     * Handles click on 'Reconcile Rightsholders' button.
     */
    void onReconcileRightsholdersButtonClicked();

    /**
     * Handles click on 'Refresh Scenario' button.
     */
    void onRefreshScenarioButtonClicked();
}
