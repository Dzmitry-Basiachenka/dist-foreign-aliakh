package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Scenarios controller interface.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/14/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
public interface IScenariosController extends IController<IScenariosWidget> {

    /**
     * @return list of {@link Scenario}s.
     */
    List<Scenario> getScenarios();

    /**
     * Gets {@link Scenario} with calculated amounts and last audit action.
     *
     * @param scenario selected {@link Scenario}
     * @return scenario {@link Scenario}
     */
    Scenario getScenarioWithAmountsAndLastAction(Scenario scenario);

    /**
     * Handles click on 'Delete' button.
     */
    void onDeleteButtonClicked();

    /**
     * Handles click on 'View' button.
     */
    void onViewButtonClicked();

    /**
     * Handles actions with {@link Scenario}.
     *
     * @param actionType scenario action type
     */
    void handleAction(ScenarioActionTypeEnum actionType);

    /**
     * Handles send to LM action.
     */
    void sendToLm();

    /**
     * Handles click on 'Reconcile Rightsholders' button.
     */
    void onReconcileRightsholdersButtonClicked();

    /**
     * Handles refresh Scenario action.
     */
    void refreshScenario();

    /**
     * @return HTML filter representation for selected scenario.
     */
    String getCriteriaHtmlRepresentation();
}
