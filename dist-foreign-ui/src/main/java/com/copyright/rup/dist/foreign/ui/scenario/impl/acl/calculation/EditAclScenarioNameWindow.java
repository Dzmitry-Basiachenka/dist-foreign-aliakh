package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonEditScenarioNameWindow;

/**
 * Modal window for editing {@link AclScenario} name.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 12/21/2023
 *
 * @author Dzmitry Basiachenka
 */
class EditAclScenarioNameWindow extends CommonEditScenarioNameWindow {

    private static final long serialVersionUID = 2994801496112034669L;

    private final IAclScenariosController controller;
    private final AclScenario scenario;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclScenariosController}
     * @param scenario   selected {@link AclScenario}
     */
    EditAclScenarioNameWindow(IAclScenariosController controller, AclScenario scenario) {
        super(scenario.getName(), "edit-acl-scenario-name-window");
        this.controller = controller;
        this.scenario = scenario;
        super.validateBinder();
    }

    @Override
    protected boolean isScenarioExists(String scenarioName) {
        return controller.aclScenarioExists(scenarioName);
    }

    @Override
    protected void updateScenarioName(String scenarioName) {
        controller.editAclScenarioName(scenario.getId(), scenarioName);
        controller.getWidget().refresh();
    }
}
