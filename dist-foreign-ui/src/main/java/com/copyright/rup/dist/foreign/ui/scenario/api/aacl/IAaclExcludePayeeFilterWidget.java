package com.copyright.rup.dist.foreign.ui.scenario.api.aacl;

import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.vaadin.widget.api.IFilterWidget;

/**
 * Interface for exclude AACL payees filter controller.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/05/2020
 *
 * @author Ihar Suvorau
 */
public interface IAaclExcludePayeeFilterWidget extends IFilterWidget<IAaclExcludePayeeFilterController> {

    /**
     * @return currently applied {@link ExcludePayeeFilter}.
     */
    ExcludePayeeFilter getAppliedFilter();
}
