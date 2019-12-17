package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Common interface for drill down by rightsholder widget.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/9/19
 *
 * @author Stanislau Rudak
 */
public interface ICommonDrillDownByRightsholderWidget extends IWidget<ICommonDrillDownByRightsholderController> {

    /**
     * @return value from search field.
     */
    String getSearchValue();
}
