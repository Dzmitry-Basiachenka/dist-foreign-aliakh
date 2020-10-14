package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.sal.ISalScenarioService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenariosController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link ISalScenariosController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/22/2020
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalScenariosController extends CommonScenariosController implements ISalScenariosController {

    @Autowired
    private IScenarioHistoryController scenarioHistoryController;
    @Autowired
    private ISalScenarioController scenarioController;
    @Autowired
    private ISalScenarioService salScenarioService;

    @Override
    public void sendToLm() {
        // TODO implement sending to LM for SAL scenario
    }

    @Override
    public String getCriteriaHtmlRepresentation() {
        return ""; // TODO implement criteria representation for SAL scenario
    }

    @Override
    public void onDeleteButtonClicked() {
        Scenario scenario = getWidget().getSelectedScenario();
        Windows.showConfirmDialog(ForeignUi.getMessage("message.confirm.delete_action", scenario.getName(), "scenario"),
            () -> {
                salScenarioService.deleteScenario(scenario);
                getWidget().refresh();
            });
    }

    @Override
    protected ISalScenariosWidget instantiateWidget() {
        return new SalScenariosWidget(this, scenarioHistoryController);
    }

    @Override
    protected ICommonScenarioController getScenarioController() {
        return scenarioController;
    }

    @Override
    protected ICommonScenarioWidget initScenarioWidget() {
        return scenarioController.initWidget();
    }
}
