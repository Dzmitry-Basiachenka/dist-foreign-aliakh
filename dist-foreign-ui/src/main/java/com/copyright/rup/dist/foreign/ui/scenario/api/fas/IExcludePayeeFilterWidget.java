package com.copyright.rup.dist.foreign.ui.scenario.api.fas;

import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
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
public interface IExcludePayeeFilterWidget extends IFilterWidget<IExcludePayeeFilterController> {

    /**
     * @return currently applied {@link ExcludePayeeFilter}.
     */
    ExcludePayeeFilter getAppliedFilter();
}
