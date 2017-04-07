package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosWidget;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.ui.Window;

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
    @Autowired
    private ScenarioController scenarioController;

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
        scenarioController.setScenario(getWidget().getSelectedScenario());
        IScenarioWidget scenarioWidget = scenarioController.initWidget();
        Window scenarioWindow = (Window) scenarioWidget;
        Windows.showModalWindow(scenarioWindow);
        scenarioWindow.setPositionY(30);
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
