package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.vaadin.widget.api.IFilterWidget;

/**
 * Interface for widget for UDM baseline value filtering.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
public interface IUdmBaselineValueFilterWidget extends IFilterWidget<IUdmBaselineValueFilterController> {

    /**
     * @return {@link UdmBaselineValueFilter}.
     */
    UdmBaselineValueFilter getFilter();

    /**
     * @return {@link UdmBaselineValueFilter}.
     */
    UdmBaselineValueFilter getAppliedFilter();
}
