package com.copyright.rup.dist.foreign.vui.main.impl;

import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditController;
import com.copyright.rup.dist.foreign.vui.main.api.IControllerProvider;
import com.copyright.rup.dist.foreign.vui.main.api.IMainWidget;
import com.copyright.rup.dist.foreign.vui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.report.api.IReportController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.vui.status.api.ICommonBatchStatusController;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.TabController;

import com.vaadin.flow.component.tabs.TabSheet;

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

    private static final long serialVersionUID = 1942785770570959518L;
    private static final int SCENARIOS_TAB_INDEX = 1;

    @Autowired
    private IProductFamilyProvider productFamilyProvider;
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
    @Autowired
    @Qualifier("df.reportController")
    private IReportController reportController;

    @Override
    public void onProductFamilyChanged() {
        getWidget().updateProductFamily();
        refreshWidget();
    }

    @Override
    public IReportController getReportController() {
        return reportController;
    }

    @Override
    public IProductFamilyProvider getProductFamilyProvider() {
        return productFamilyProvider;
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
        getTabSheet().setSelectedTab(getTabSheet().getTabAt(SCENARIOS_TAB_INDEX));
        getScenariosControllerProvider()
            .getController()
            .orElseThrow()
            .getWidget()
            .selectScenario(event.getScenarioId());
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
