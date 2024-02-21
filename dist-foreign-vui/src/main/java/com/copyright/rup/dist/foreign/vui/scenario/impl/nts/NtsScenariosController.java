package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenarioController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenariosWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonScenariosController;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link INtsScenariosController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/2019
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsScenariosController extends CommonScenariosController implements INtsScenariosController {

    private static final long serialVersionUID = -876763418798742517L;

    @Autowired
    private IScenarioHistoryController scenarioHistoryController;
    @Autowired
    private INtsScenarioController scenarioController;

    @Override
    public void sendToLm() {
        //TODO: {dbasiachenka} implement
    }

    @Override
    public String getCriteriaHtmlRepresentation() {
        //TODO: {dbasiachenka} implement
        return StringUtils.EMPTY;
    }

    @Override
    public void onDeleteButtonClicked() {
        //TODO: {dbasiachenka} implement
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
        //TODO: {dbasiachenka} implement
        return scenarioController.initWidget();
    }
}
