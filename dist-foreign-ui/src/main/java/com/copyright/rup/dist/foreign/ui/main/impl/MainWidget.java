package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidget;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosWidget;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusController;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclCalculationController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclCalculationWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmWidget;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.ITabChangeController;

import com.vaadin.ui.TabSheet;

/**
 * Main widget of the application.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
public class MainWidget extends TabSheet implements IMainWidget {

    private static final long serialVersionUID = -6721393535731738442L;

    private IMainWidgetController controller;

    private SwitchableWidget<ICommonUsageWidget, ICommonUsageController> usagesWidget;
    private SwitchableWidget<ICommonScenariosWidget, ICommonScenariosController> scenariosWidget;
    private SwitchableWidget<ICommonAuditWidget, ICommonAuditController> auditWidget;
    private SwitchableWidget<ICommonBatchStatusWidget, ICommonBatchStatusController> batchStatusWidget;
    private SwitchableWidget<IUdmWidget, IUdmController> udmWidget;
    private SwitchableWidget<IAclCalculationWidget, IAclCalculationController> aclCalculationWidget;

    private Tab usagesTab;
    private Tab scenarioTab;
    private Tab auditTab;
    private Tab batchStatusTab;
    private Tab udmTab;
    private Tab calculationsTab;

    @Override
    @SuppressWarnings("unchecked")
    public MainWidget init() {
        VaadinUtils.addComponentStyle(this, Cornerstone.MAIN_TABSHEET);
        usagesWidget = new SwitchableWidget<>(controller.getUsagesControllerProvider(),
            widget -> widget.addListener(ScenarioCreateEvent.class,
                controller, IMainWidgetController.ON_SCENARIO_CREATED));
        scenariosWidget = new SwitchableWidget<>(controller.getScenariosControllerProvider(), widget -> {});
        auditWidget = new SwitchableWidget<>(controller.getAuditControllerProvider(), widget -> {});
        batchStatusWidget = new SwitchableWidget<>(controller.getBatchStatusControllerProvider(), widget -> {});
        udmWidget = new SwitchableWidget<>(controller.getUdmControllerProvider(), widget -> {});
        aclCalculationWidget = new SwitchableWidget<>(controller.getAclCalculationControllerProvider(), widget -> {});
        usagesTab = addTab(usagesWidget, ForeignUi.getMessage("tab.usages"));
        scenarioTab = addTab(scenariosWidget, ForeignUi.getMessage("tab.scenarios"));
        auditTab = addTab(auditWidget, ForeignUi.getMessage("tab.audit"));
        batchStatusTab = addTab(batchStatusWidget, ForeignUi.getMessage("tab.batch_status"));
        udmTab = addTab(udmWidget, ForeignUi.getMessage("tab.udm"));
        udmTab.getComponent().addStyleName("sub-tab");
        calculationsTab = addTab(aclCalculationWidget, ForeignUi.getMessage("tab.calculations"));
        calculationsTab.getComponent().addStyleName("sub-tab");
        addListener(TabSheet.SelectedTabChangeEvent.class, controller, ITabChangeController.TAB_CHANGE_HANDLER);
        updateProductFamily();
        return this;
    }

    @Override
    public void updateProductFamily() {
        usagesTab.setVisible(usagesWidget.updateProductFamily());
        scenarioTab.setVisible(scenariosWidget.updateProductFamily());
        auditTab.setVisible(auditWidget.updateProductFamily());
        batchStatusTab.setVisible(batchStatusWidget.updateProductFamily());
        udmTab.setVisible(udmWidget.updateProductFamily());
        calculationsTab.setVisible(aclCalculationWidget.updateProductFamily()
            && !ForeignSecurityUtils.hasResearcherPermission());
    }

    @Override
    public void setController(IMainWidgetController controller) {
        this.controller = controller;
    }
}
