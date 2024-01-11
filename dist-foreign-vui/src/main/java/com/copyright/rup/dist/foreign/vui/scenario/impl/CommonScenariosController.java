package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Common controller for {@link ICommonScenariosWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
public abstract class CommonScenariosController extends CommonController<ICommonScenariosWidget>
    implements ICommonScenariosController {

    private static final long serialVersionUID = -3846695471033412890L;

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IScenarioService scenarioService;

    protected IUsageService getUsageService() {
        return usageService;
    }

    @Override
    public boolean scenarioExists(String name) {
        return scenarioService.scenarioExists(name);
    }

    @Override
    public void editScenarioName(String scenarioId, String newScenarioName) {
        scenarioService.updateName(scenarioId, newScenarioName);
    }
}
