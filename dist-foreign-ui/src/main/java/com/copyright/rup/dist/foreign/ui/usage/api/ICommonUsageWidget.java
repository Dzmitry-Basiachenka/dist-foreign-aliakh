package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.vaadin.widget.api.IMediatorProvider;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Common interface for usages tab widgets.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @author Uladzislau Shalamitski
 */
public interface ICommonUsageWidget extends IWidget<ICommonUsageController>, IRefreshable, IMediatorProvider {

    /**
     * @return controller for given widget.
     */
    ICommonUsageController getController();

    /**
     * Fires {@link com.vaadin.ui.Component.Event}.
     *
     * @param event an {@link com.vaadin.ui.Component.Event} to be fired
     * @see com.vaadin.server.AbstractClientConnector
     */
    void fireWidgetEvent(Event event);
}
