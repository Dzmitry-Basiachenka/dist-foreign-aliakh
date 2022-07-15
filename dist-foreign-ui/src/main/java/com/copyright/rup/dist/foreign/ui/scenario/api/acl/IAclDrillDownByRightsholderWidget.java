package com.copyright.rup.dist.foreign.ui.scenario.api.acl;

import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for drill down by rightsholder widget for ACL product family.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/14/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclDrillDownByRightsholderWidget extends IWidget<IAclDrillDownByRightsholderController> {

    /**
     * @return value from search field.
     */
    String getSearchValue();
}
