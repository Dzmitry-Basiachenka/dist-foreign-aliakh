package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.ui.audit.api.IAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidget;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
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

    private IMainWidgetController controller;

    @Override
    @SuppressWarnings("unchecked")
    public MainWidget init() {
        VaadinUtils.addComponentStyle(this, Cornerstone.MAIN_TABSHEET);
        SwitchableWidget<IUsagesWidget, IUsagesController> usagesWidget =
            new SwitchableWidget<>(controller.getUsagesControllerProvider(),
                widget -> widget.addListener(ScenarioCreateEvent.class,
                    controller, IMainWidgetController.ON_SCENARIO_CREATED));
        SwitchableWidget<IScenariosWidget, IScenariosController> scenariosWidget =
            new SwitchableWidget<>(controller.getScenariosControllerProvider(), widget -> {});
        SwitchableWidget<IAuditWidget, IAuditController> auditWidget =
            new SwitchableWidget<>(controller.getAuditControllerProvider(), widget -> {});
        addTab(usagesWidget, ForeignUi.getMessage("tab.usages"));
        addTab(scenariosWidget, ForeignUi.getMessage("tab.scenario"));
        addTab(auditWidget, ForeignUi.getMessage("tab.audit"));
        addListener(TabSheet.SelectedTabChangeEvent.class, controller, ITabChangeController.TAB_CHANGE_HANDLER);
        return this;
    }

    @Override
    public void setController(IMainWidgetController controller) {
        this.controller = controller;
    }
}
