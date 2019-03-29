package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.foreign.ui.scenario.impl.ScenarioController;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for viewing scenario widget.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/31/17
 *
 * @author Ihar Suvorau
 */
public interface IScenarioWidget extends IWidget<ScenarioController>, IRefreshable {

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

    /**
     * @return value from search field.
     */
    String getSearchValue();

    /**
     * Applies search value for table container.
     */
    void applySearch();

    /**
     * Refresh table.
     */
    void refreshTable();
}
