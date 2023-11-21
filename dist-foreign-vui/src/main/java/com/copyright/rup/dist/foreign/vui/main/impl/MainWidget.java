package com.copyright.rup.dist.foreign.vui.main.impl;

import com.copyright.rup.dist.foreign.vui.main.api.IMainWidget;
import com.copyright.rup.dist.foreign.vui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.themes.Cornerstone;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;

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
        updateProductFamily();
        setSizeFull();
        addThemeVariants(TabSheetVariant.LUMO_TABS_SMALL);
        controller.refreshWidget();
        return this;
    }

    @Override
    public void updateProductFamily() {
        //TODO {vaadin23} will implement later
    }

    @Override
    public void setController(IMainWidgetController controller) {
        this.controller = controller;
    }
}
