package com.copyright.rup.dist.foreign.ui.scenario.api.nts;

import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioWidget;

/**
 * Interface for scenario view widget for NTS product family.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
public interface INtsScenarioWidget extends ICommonScenarioWidget {

    /**
     * Fires {@link com.vaadin.ui.Component.Event}.
     *
     * @param event an {@link com.vaadin.ui.Component.Event} to be fired
     * @see com.vaadin.server.AbstractClientConnector#fireEvent(java.util.EventObject)
     */
    void fireWidgetEvent(Event event);
}
