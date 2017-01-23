package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidget;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidgetController;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.widget.api.ITabChangeController;

import com.vaadin.ui.Label;
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
        addTab(controller.getUsagesController().initWidget(),
            ForeignUi.getMessage("tab.usage"));
        addTab(new Label(), ForeignUi.getMessage("tab.scenario"));
        addTab(new Label(), ForeignUi.getMessage("tab.audit"));
        addListener(TabSheet.SelectedTabChangeEvent.class, controller,
            ITabChangeController.TAB_CHANGE_HANDLER);
        return this;
    }

    @Override
    public void setController(IMainWidgetController controller) {
        this.controller = controller;
    }
}
