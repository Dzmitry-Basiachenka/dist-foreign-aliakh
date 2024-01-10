package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosWidget;

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
    public void setController(ICommonScenariosController controller) {
        this.controller = controller;
    }

    protected ICommonScenariosController getController() {
        return controller;
    }
}
