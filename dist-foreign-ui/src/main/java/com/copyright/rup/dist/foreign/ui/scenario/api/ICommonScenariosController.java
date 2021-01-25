package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Controller interface for {@link ICommonScenariosWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
public interface ICommonScenariosController extends IController<ICommonScenariosWidget> {

    /**
     * @return list of {@link Scenario}s.
     */
    List<Scenario> getScenarios();

    /**
     * Checks whether {@link Scenario} with specified name already exists in database.
     *
     * @param scenarioName name of {@link Scenario} to check
     * @return {@code true} if {@link Scenario} with specified name already exists in database,
     * {@code false} - if doesn't
     */
    boolean scenarioExists(String scenarioName);

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
     * Handles click on 'Edit Name' button.
     */
    void onEditNameButtonClicked();

    /**
     * Handles actions with {@link Scenario}.
     *
     * @param actionType scenario action type
     */
    void handleAction(ScenarioActionTypeEnum actionType);

    /**
     * Updates name for selected {@link Scenario}.
     *
     * @param newScenarioName new scenario name to set
     */
    void editScenarioName(String newScenarioName);

    /**
     * Refreshes selected scenario.
     */
    void refreshScenario();

    /**
     * @return HTML filter representation for selected scenario.
     */
    String getCriteriaHtmlRepresentation();
}
