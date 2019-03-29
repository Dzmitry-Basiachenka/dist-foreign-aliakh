package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.ui.audit.api.IAuditController;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidget;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.vaadin.widget.api.TabController;

import com.vaadin.ui.TabSheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of controller for {@link IMainWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
@Component("MainController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MainWidgetController extends TabController<IMainWidget> implements IMainWidgetController {

    @Autowired
    private IUsagesController usagesController;
    @Autowired
    private IScenariosController scenariosController;
    @Autowired
    private IAuditController auditController;

    @Override
    public IUsagesController getUsagesController() {
        return usagesController;
    }

    @Override
    public IScenariosController getScenariosController() {
        return scenariosController;
    }

    @Override
    public void onScenarioCreated(ScenarioCreateEvent event) {
        selectTab(scenariosController.getWidget());
        scenariosController.getWidget().selectScenario(event.getScenarioId());
    }

    @Override
    public IAuditController getAuditController() {
        return auditController;
    }

    @Override
    protected TabSheet getTabSheet() {
        return (TabSheet) getWidget();
    }

    @Override
    protected IMainWidget instantiateWidget() {
        return new MainWidget();
    }
}
