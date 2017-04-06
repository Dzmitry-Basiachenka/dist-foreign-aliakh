package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosWidget;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller class for {@link ScenariosWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/14/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScenariosController extends CommonController<IScenariosWidget> implements IScenariosController {

    @Autowired
    private IScenarioService scenarioService;

    @Override
    public List<Scenario> getScenarios() {
        return scenarioService.getScenarios();
    }

    @Override
    public void onDeleteButtonClicked() {
        final Scenario scenario = getWidget().getSelectedScenario();
        String message = ForeignUi.getMessage("message.confirm.delete_action", scenario.getName(), "scenario");
        Windows.showConfirmDialog(message, () -> deleteScenario(scenario));
    }

    @Override
    public void onViewButtonClicked() {
        // TODO {isuvorau} open ScenarioWidget and set Scenario to ScenarioController
    }

    @Override
    protected IScenariosWidget instantiateWidget() {
        return new ScenariosWidget();
    }

    private void deleteScenario(Scenario scenario) {
        scenarioService.deleteScenario(scenario.getId());
        getWidget().refresh();
    }
}
