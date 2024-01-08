package com.copyright.rup.dist.foreign.vui.usage.api;

import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IFilterWidget;

/**
 * Interface for common widget for usage filtering.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 12/12/2019
 *
 * @author Uladzislau Shalamitski
 */
public interface ICommonUsageFilterWidget extends IFilterWidget<ICommonUsageFilterController> {

    /**
     * @return {@link UsageFilter}.
     */
    UsageFilter getFilter();

    /**
     * @return applied {@link UsageFilter}.
     */
    UsageFilter getAppliedFilter();
}
