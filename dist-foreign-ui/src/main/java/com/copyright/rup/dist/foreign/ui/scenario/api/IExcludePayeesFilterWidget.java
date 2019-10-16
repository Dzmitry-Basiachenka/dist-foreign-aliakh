package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeesFilter;
import com.copyright.rup.vaadin.widget.api.IFilterWidget;

/**
 * Interface for exclude payees filter controller.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
public interface IExcludePayeesFilterWidget extends IFilterWidget<IExcludePayeesFilterController> {

    /**
     * @return currently applied {@link ExcludePayeesFilter}.
     */
    ExcludePayeesFilter getAppliedFilter();
}
