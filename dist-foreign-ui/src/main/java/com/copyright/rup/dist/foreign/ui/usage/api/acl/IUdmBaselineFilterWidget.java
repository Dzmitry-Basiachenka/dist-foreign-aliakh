package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.vaadin.widget.api.IFilterWidget;

/**
 * Interface for widget for UDM baseline filtering.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Uladzislau Shalamitski
 */
public interface IUdmBaselineFilterWidget extends IFilterWidget<IUdmBaselineFilterController> {

    /**
     * @return {@link UdmBaselineFilter}.
     */
    UdmBaselineFilter getFilter();

    /**
     * @return applied {@link UdmBaselineFilter}.
     */
    UdmBaselineFilter getAppliedFilter();
}
