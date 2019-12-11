package com.copyright.rup.dist.foreign.ui.scenario.api;

/**
 * Interface for scenario view widget for NTS product family.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
public interface INtsScenarioWidget extends ICommonScenarioWidget<INtsScenarioWidget, INtsScenarioController> {

    /**
     * Fires {@link com.vaadin.ui.Component.Event}.
     *
     * @param event an {@link com.vaadin.ui.Component.Event} to be fired
     * @see com.vaadin.server.AbstractClientConnector#fireEvent(java.util.EventObject)
     */
    void fireWidgetEvent(Event event);

    /**
     * Adds {@link IExcludeUsagesListener} on widget.
     *
     * @param listener instance of {@link IExcludeUsagesListener}
     */
    void addListener(IExcludeUsagesListener listener);
}
