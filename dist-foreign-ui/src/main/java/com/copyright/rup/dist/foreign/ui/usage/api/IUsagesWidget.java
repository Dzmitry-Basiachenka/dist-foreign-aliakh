package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.vaadin.widget.api.IMediatorProvider;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for usages widget.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/2017
 *
 * @author Mikita Hladkikh
 */
public interface IUsagesWidget extends IWidget<IUsagesController>, IRefreshable, IMediatorProvider {

    /**
     * Fires {@link com.vaadin.ui.Component.Event}.
     *
     * @param event an {@link com.vaadin.ui.Component.Event} to be fired
     * @see com.vaadin.server.AbstractClientConnector#fireEvent(EventObject)
     */
    void fireWidgetEvent(Event event);
}
