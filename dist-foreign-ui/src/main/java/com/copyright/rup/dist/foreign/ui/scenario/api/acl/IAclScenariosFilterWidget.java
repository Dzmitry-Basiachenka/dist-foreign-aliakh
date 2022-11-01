package com.copyright.rup.dist.foreign.ui.scenario.api.acl;

import com.copyright.rup.dist.foreign.domain.filter.AclScenarioFilter;
import com.copyright.rup.vaadin.widget.api.IFilterWidget;

/**
 * Interface for widget for ACL scenarios filtering.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 10/31/2022
 *
 * @author Mikita Maistrenka
 */
public interface IAclScenariosFilterWidget extends IFilterWidget<IAclScenariosFilterController> {

    /**
     * @return {@link AclScenarioFilter}.
     */
    AclScenarioFilter getFilter();

    /**
     * @return applied {@link AclScenarioFilter}.
     */
    AclScenarioFilter getAppliedFilter();
}
