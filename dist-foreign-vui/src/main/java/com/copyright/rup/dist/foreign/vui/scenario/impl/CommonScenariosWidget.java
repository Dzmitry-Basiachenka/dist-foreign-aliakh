package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.splitlayout.SplitLayout;

/**
 * Contains common functionality for scenarios widget.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
public abstract class CommonScenariosWidget extends SplitLayout implements ICommonScenariosWidget {

    private static final long serialVersionUID = 3055943773030155962L;

    private ICommonScenariosController controller;

    @Override
    @SuppressWarnings("unchecked")
    public CommonScenariosWidget init() {
        //TODO: {dbasiachenka} implement
        return this;
    }

    @Override
    public void refresh() {
        new Div();// will be removed. for PMD
        //TODO: will implement later
    }

    /**
     * Handles click on 'Edit Name' button.
     */
    protected void onEditNameButtonClicked() {
        Windows.showModalWindow(new EditScenarioNameWindow(controller, controller.getWidget().getSelectedScenario()));
    }

    protected ICommonScenariosController getController() {
        return controller;
    }

    @Override
    public void setController(ICommonScenariosController controller) {
        this.controller = controller;
    }
}
