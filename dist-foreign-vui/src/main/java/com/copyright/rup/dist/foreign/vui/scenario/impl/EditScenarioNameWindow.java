package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosController;

/**
 * Modal window for editing {@link Scenario} name.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 01/22/21
 *
 * @author Ihar Suvorau
 */
class EditScenarioNameWindow extends CommonEditScenarioNameWindow {

    private static final long serialVersionUID = -8227881821301132345L;

    private final Scenario scenario;
    private final ICommonScenariosController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ICommonScenariosController}
     * @param scenario   selected {@link Scenario}
     */
    EditScenarioNameWindow(ICommonScenariosController controller, Scenario scenario) {
        super(scenario.getName(), "edit-scenario-name-window");
        this.controller = controller;
        this.scenario = scenario;
        super.validateBinder();
    }

    @Override
    protected boolean isScenarioExists(String scenarioName) {
        return controller.scenarioExists(scenarioName);
    }

    @Override
    protected void updateScenarioName(String scenarioName) {
        controller.editScenarioName(scenario.getId(), scenarioName);
        controller.getWidget().refresh();
    }
}
