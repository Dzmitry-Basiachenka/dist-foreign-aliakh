package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for UDM usage filter widget.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/03/2021
 *
 * @author Dzmitry Basiachenka
 */
public interface IUdmUsageFilterWidget extends IWidget<IUdmUsageFilterController> {

    /**
     * @return {@link UdmUsageFilter}.
     */
    UdmUsageFilter getFilter();

    /**
     * @return applied {@link UdmUsageFilter}.
     */
    UdmUsageFilter getAppliedFilter();
}
