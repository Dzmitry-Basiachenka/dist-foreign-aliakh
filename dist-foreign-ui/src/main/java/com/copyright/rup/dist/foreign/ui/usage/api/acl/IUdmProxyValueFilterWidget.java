package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.vaadin.widget.api.IFilterWidget;

/**
 * Interface for widget for UDM proxy value filtering.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Uladzislau Shalamitski
 */
public interface IUdmProxyValueFilterWidget extends IFilterWidget<IUdmProxyValueFilterController> {

    /**
     * @return {@link UdmProxyValueFilter}.
     */
    UdmProxyValueFilter getFilter();

    /**
     * @return applied {@link UdmProxyValueFilter}.
     */
    UdmProxyValueFilter getAppliedFilter();
}
