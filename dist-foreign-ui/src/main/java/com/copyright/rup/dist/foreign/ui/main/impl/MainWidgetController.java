package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.ui.audit.api.IAuditController;
import com.copyright.rup.dist.foreign.ui.main.api.IControllerProvider;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidget;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.vaadin.widget.api.TabController;

import com.vaadin.ui.TabSheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private static final int SCENARIOS_TAB_INDEX = 1;

    @Autowired
    @Qualifier("dist.foreign.usagesControllerProvider")
    private IControllerProvider<ICommonUsageController> usagesControllerProvider;
    @Autowired
    @Qualifier("dist.foreign.scenariosControllerProvider")
    private IControllerProvider<IScenariosController> scenariosControllerProvider;
    @Autowired
    @Qualifier("dist.foreign.auditControllerProvider")
    private IControllerProvider<IAuditController> auditControllerProvider;

    @Override
    public IControllerProvider<ICommonUsageController> getUsagesControllerProvider() {
        return usagesControllerProvider;
    }

    @Override
    public IControllerProvider<IScenariosController> getScenariosControllerProvider() {
        return scenariosControllerProvider;
    }

    @Override
    public IControllerProvider<IAuditController> getAuditControllerProvider() {
        return auditControllerProvider;
    }

    @Override
    public void onScenarioCreated(ScenarioCreateEvent event) {
        getTabSheet().setSelectedTab(SCENARIOS_TAB_INDEX);
        getScenariosControllerProvider().getController().getWidget().selectScenario(event.getScenarioId());
    }

    @Override
    public void onProductFamilyChanged() {
        refreshWidget();
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
