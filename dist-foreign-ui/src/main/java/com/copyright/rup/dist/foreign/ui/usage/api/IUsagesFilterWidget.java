package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.vaadin.widget.api.IFilterWidget;

/**
 * Interface for widget for usage filtering.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/11/2017
 *
 * @author Mikita Hladkikh
 */
public interface IUsagesFilterWidget extends IFilterWidget<IUsagesFilterController> {

    /**
     * @return {@link UsageFilter}.
     */
    UsageFilter getFilter();

    /**
     * @return applied {@link UsageFilter}.
     */
    UsageFilter getAppliedFilter();

    /**
     * @return selected Product Family.
     */
    String getSelectedProductFamily();
}
