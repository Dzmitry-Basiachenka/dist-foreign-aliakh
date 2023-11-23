package com.copyright.rup.dist.foreign.vui.usage.impl;

import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageWidget;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;

/**
 * Common usages widget for {@link ICommonUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @author Uladzislau Shalamitski
 */
public abstract class CommonUsageWidget extends SplitLayout implements ICommonUsageWidget {

    private ICommonUsageController controller;

    @Override
    public ICommonUsageController getController() {
        return controller;
    }

    @Override
    public void setController(ICommonUsageController controller) {
        this.controller = controller;
    }

    /**
     * @return instance of {@link HorizontalLayout} that contains available buttons.
     */
    protected abstract HorizontalLayout initButtonsLayout();
}
