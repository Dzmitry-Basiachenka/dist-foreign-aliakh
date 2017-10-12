package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for drill down by rightsholder widget.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/11/17
 *
 * @author Ihar Suvorau
 */
public interface IDrillDownByRightsholderWidget extends IWidget<IDrillDownByRightsholderController> {

    /**
     * @return value from search field.
     */
    String getSearchValue();
}
