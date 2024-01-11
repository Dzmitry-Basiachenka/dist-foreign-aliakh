package com.copyright.rup.dist.foreign.vui.scenario.api;

import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IController;

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
     * Checks whether {@link Scenario} with specified name already exists in database.
     *
     * @param scenarioName name of {@link Scenario} to check
     * @return {@code true} if {@link Scenario} with specified name already exists in database,
     * {@code false} - if doesn't
     */
    boolean scenarioExists(String scenarioName);

    /**
     * Updates name for selected {@link Scenario}.
     *
     * @param scenarioId      id of selected {@link Scenario}
     * @param newScenarioName new scenario name to set
     */
    void editScenarioName(String scenarioId, String newScenarioName);
}
