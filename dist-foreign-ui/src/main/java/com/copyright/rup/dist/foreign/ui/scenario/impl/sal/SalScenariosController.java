package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenariosController;

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
        // TODO implement deleting for SAL scenario
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
