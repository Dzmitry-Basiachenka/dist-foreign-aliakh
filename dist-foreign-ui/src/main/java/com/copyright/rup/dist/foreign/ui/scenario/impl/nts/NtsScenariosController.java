package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenariosController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.ui.Window;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link INtsScenariosController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsScenariosController extends CommonScenariosController implements INtsScenariosController {

    @Autowired
    private IScenarioHistoryController scenarioHistoryController;
    @Autowired
    private INtsScenarioController scenarioController;

    @Override
    public void onViewButtonClicked() {
        scenarioController.setScenario(getWidget().getSelectedScenario());
        Window scenarioWindow = (Window) scenarioController.initWidget();
        Windows.showModalWindow(scenarioWindow);
        scenarioWindow.setPositionY(30);
    }

    @Override
    protected INtsScenariosWidget instantiateWidget() {
        return new NtsScenariosWidget(scenarioHistoryController);
    }

    @Override
    protected INtsScenarioController getScenarioController() {
        return scenarioController;
    }

    @Override
    protected ICommonScenarioWidget initScenarioWidget() {
        return scenarioController.initWidget();
    }
}
