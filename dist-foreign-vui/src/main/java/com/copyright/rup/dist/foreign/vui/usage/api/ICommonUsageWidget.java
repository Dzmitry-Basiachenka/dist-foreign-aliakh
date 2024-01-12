package com.copyright.rup.dist.foreign.vui.usage.api;

import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediatorProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IRefreshable;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

/**
 * Common interface for usages tab widgets.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/05/2019
 *
 * @author Uladzislau Shalamitski
 */
public interface ICommonUsageWidget extends IWidget<ICommonUsageController>, IRefreshable, IMediatorProvider {

    /**
     * @return controller for given widget.
     */
    ICommonUsageController getController();

    /**
     * Fires {@link ComponentEvent}.
     *
     * @param event an {@link ComponentEvent} to be fired
     */
    void fireWidgetEvent(ComponentEvent<Component> event);
}
