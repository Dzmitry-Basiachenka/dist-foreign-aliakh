package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidget;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.report.api.IUdmReportWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosWidget;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusController;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmWidget;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.RootWidget;
import com.copyright.rup.vaadin.widget.api.ITabChangeController;

import com.vaadin.ui.AbsoluteLayout;
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

    private static final String UDM_REPORT_MENU_CSS_POSITION_MANAGER = "left: 415px; top: 29px;";
    private static final String UDM_REPORT_MENU_CSS_POSITION_SPECIALIST = "left: 540px; top: 29px;";
    private static final String UDM_REPORT_MENU_CSS_POSITION_VIEW_ONLY = "left: 415px; top: 29px;";

    private IMainWidgetController controller;

    private SwitchableWidget<IUdmWidget, IUdmController> udmWidget;
    private SwitchableWidget<ICommonUsageWidget, ICommonUsageController> usagesWidget;
    private SwitchableWidget<ICommonScenariosWidget, ICommonScenariosController> scenariosWidget;
    private SwitchableWidget<ICommonAuditWidget, ICommonAuditController> auditWidget;
    private SwitchableWidget<ICommonBatchStatusWidget, ICommonBatchStatusController> batchStatusWidget;

    private Tab udmTab;
    private IUdmReportWidget udmReportWidget;
    private Tab usagesTab;
    private Tab scenarioTab;
    private Tab auditTab;
    private Tab batchStatusTab;

    @Override
    @SuppressWarnings("unchecked")
    public MainWidget init() {
        VaadinUtils.addComponentStyle(this, Cornerstone.MAIN_TABSHEET);
        udmWidget = new SwitchableWidget<>(controller.getUdmControllerProvider(), widget -> {});
        usagesWidget = new SwitchableWidget<>(controller.getUsagesControllerProvider(),
            widget -> widget.addListener(ScenarioCreateEvent.class,
                controller, IMainWidgetController.ON_SCENARIO_CREATED));
        scenariosWidget = new SwitchableWidget<>(controller.getScenariosControllerProvider(), widget -> {});
        auditWidget = new SwitchableWidget<>(controller.getAuditControllerProvider(), widget -> {});
        batchStatusWidget = new SwitchableWidget<>(controller.getBatchStatusControllerProvider(), widget -> {});
        udmTab = addTab(udmWidget, ForeignUi.getMessage("tab.udm"));
        udmTab.getComponent().addStyleName("sub-tab");
        usagesTab = addTab(usagesWidget, ForeignUi.getMessage("tab.usages"));
        scenarioTab = addTab(scenariosWidget, ForeignUi.getMessage("tab.scenario"));
        auditTab = addTab(auditWidget, ForeignUi.getMessage("tab.audit"));
        batchStatusTab = addTab(batchStatusWidget, ForeignUi.getMessage("tab.batch_status"));
        addListener(TabSheet.SelectedTabChangeEvent.class, controller, ITabChangeController.TAB_CHANGE_HANDLER);
        updateProductFamily();
        return this;
    }

    @Override
    public void updateProductFamily() {
        udmTab.setVisible(udmWidget.updateProductFamily());
        if (udmTab.isVisible()) {
            udmReportWidget = controller.getUdmReportController().initWidget();
            if (ForeignSecurityUtils.hasManagerPermission()) {
                getAbsoluteLayout().addComponent(udmReportWidget, UDM_REPORT_MENU_CSS_POSITION_MANAGER);
            } else if (ForeignSecurityUtils.hasSpecialistPermission()) {
                getAbsoluteLayout().addComponent(udmReportWidget, UDM_REPORT_MENU_CSS_POSITION_SPECIALIST);
            } else if (ForeignSecurityUtils.hasViewOnlyPermission()) {
                getAbsoluteLayout().addComponent(udmReportWidget, UDM_REPORT_MENU_CSS_POSITION_VIEW_ONLY);
            }
        } else {
            if (null != udmReportWidget) {
                getAbsoluteLayout().removeComponent(udmReportWidget);
            }
        }
        usagesTab.setVisible(usagesWidget.updateProductFamily());
        scenarioTab.setVisible(scenariosWidget.updateProductFamily());
        auditTab.setVisible(auditWidget.updateProductFamily());
        batchStatusTab.setVisible(batchStatusWidget.updateProductFamily());
    }

    @Override
    public void setController(IMainWidgetController controller) {
        this.controller = controller;
    }

    private AbsoluteLayout getAbsoluteLayout() {
        return ((RootWidget) this.getUI().getContent()).getAbsoluteLayout();
    }
}
