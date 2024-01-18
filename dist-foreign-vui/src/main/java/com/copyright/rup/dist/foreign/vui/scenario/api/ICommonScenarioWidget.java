package com.copyright.rup.dist.foreign.vui.scenario.api;

import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

/**
 * Common interface for scenario widget.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
public interface ICommonScenarioWidget extends IWidget<ICommonScenarioController> {

    /**
     * @return value from search field.
     */
    String getSearchValue();

    /**
     * Applies search value for table container.
     */
    void applySearch();
}
