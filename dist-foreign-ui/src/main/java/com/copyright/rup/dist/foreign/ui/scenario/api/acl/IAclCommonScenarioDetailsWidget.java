package com.copyright.rup.dist.foreign.ui.scenario.api.acl;

import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Common interface for ACL scenario details widgets.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/14/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclCommonScenarioDetailsWidget extends IWidget<IAclCommonScenarioDetailsController> {

    /**
     * @return value from search field.
     */
    String getSearchValue();
}
