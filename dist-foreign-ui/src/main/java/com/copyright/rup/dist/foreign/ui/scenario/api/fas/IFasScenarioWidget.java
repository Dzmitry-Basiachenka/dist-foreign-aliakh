package com.copyright.rup.dist.foreign.ui.scenario.api.fas;

import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.vaadin.widget.api.IMediatorProvider;
import com.copyright.rup.vaadin.widget.api.IRefreshable;

/**
 * Interface for scenario view widget for FAS product family.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
public interface IFasScenarioWidget extends ICommonScenarioWidget, IRefreshable, IMediatorProvider {

    /**
     * Fires {@link com.vaadin.ui.Component.Event}.
     *
     * @param event an {@link com.vaadin.ui.Component.Event} to be fired
     * @see com.vaadin.server.AbstractClientConnector#fireEvent(java.util.EventObject)
     */
    void fireWidgetEvent(Event event);

    /**
     * Refresh table.
     */
    void refreshTable();
}
