package com.copyright.rup.dist.foreign.vui.scenario.api.fas;

import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediatorProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IRefreshable;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

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
     * Refresh table.
     */
    void refreshTable();

    /**
     * Fires widget event.
     *
     * @param event vaadin event
     */
    void fireWidgetEvent(ComponentEvent<Component> event);
}
