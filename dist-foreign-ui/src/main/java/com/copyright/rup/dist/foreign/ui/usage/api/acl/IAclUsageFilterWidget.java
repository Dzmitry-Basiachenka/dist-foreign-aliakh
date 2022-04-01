package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.vaadin.widget.api.IFilterWidget;

/**
 * Interface for widget for ACL usage filtering.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 03/31/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclUsageFilterWidget extends IFilterWidget<IAclUsageFilterController> {

    /**
     * @return {@link AclUsageFilter}.
     */
    AclUsageFilter getFilter();

    /**
     * @return applied {@link AclUsageFilter}.
     */
    AclUsageFilter getAppliedFilter();
}
