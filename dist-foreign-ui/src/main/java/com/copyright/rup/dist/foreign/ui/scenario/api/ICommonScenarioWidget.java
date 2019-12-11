package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Common interface for scenario widget.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @param <W> type of widget
 * @param <C> type of controller
 * @author Stanislau Rudak
 */
public interface ICommonScenarioWidget<W extends ICommonScenarioWidget<W, C>, C extends ICommonScenarioController<W, C>>
    extends IWidget<C> {

    /**
     * @return value from search field.
     */
    String getSearchValue();

    /**
     * Applies search value for table container.
     */
    void applySearch();
}
