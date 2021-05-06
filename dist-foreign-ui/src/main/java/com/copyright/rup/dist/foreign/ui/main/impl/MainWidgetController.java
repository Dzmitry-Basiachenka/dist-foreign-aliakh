package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditController;
import com.copyright.rup.dist.foreign.ui.main.api.IControllerProvider;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidget;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
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

    private static final int SCENARIOS_TAB_INDEX = 2;

    @Autowired
    @Qualifier("dist.foreign.udmUsagesControllerProvider")
    private IControllerProvider<IUdmUsageController> udmUsagesControllerProvider;
    @Autowired
    @Qualifier("dist.foreign.usagesControllerProvider")
    private IControllerProvider<ICommonUsageController> usagesControllerProvider;
    @Autowired
    @Qualifier("dist.foreign.scenariosControllerProvider")
    private IControllerProvider<ICommonScenariosController> scenariosControllerProvider;
    @Autowired
    @Qualifier("dist.foreign.auditControllerProvider")
    private IControllerProvider<ICommonAuditController> auditControllerProvider;
    @Autowired
    @Qualifier("dist.foreign.batchStatusControllerProvider")
    private IControllerProvider<ICommonBatchStatusController> batchStatusControllerProvider;

    @Override
    public IControllerProvider<IUdmUsageController> getUdmUsagesControllerProvider() {
        return udmUsagesControllerProvider;
    }

    @Override
    public IControllerProvider<ICommonUsageController> getUsagesControllerProvider() {
        return usagesControllerProvider;
    }

    @Override
    public IControllerProvider<ICommonScenariosController> getScenariosControllerProvider() {
        return scenariosControllerProvider;
    }

    @Override
    public IControllerProvider<ICommonAuditController> getAuditControllerProvider() {
        return auditControllerProvider;
    }

    @Override
    public IControllerProvider<ICommonBatchStatusController> getBatchStatusControllerProvider() {
        return batchStatusControllerProvider;
    }

    @Override
    public void onScenarioCreated(ScenarioCreateEvent event) {
        getTabSheet().setSelectedTab(SCENARIOS_TAB_INDEX);
        getScenariosControllerProvider().getController().get().getWidget().selectScenario(event.getScenarioId());
    }

    @Override
    public void onProductFamilyChanged() {
        getWidget().updateProductFamily();
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
