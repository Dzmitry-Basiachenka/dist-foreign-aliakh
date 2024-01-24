package com.copyright.rup.dist.foreign.vui.scenario.api.fas;

import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.IFilterSaveAction;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IFilterWidget;

/**
 * Interface for exclude payees filter controller.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
public interface IFasExcludePayeeFilterWidget extends IFilterWidget<IFasExcludePayeeFilterController> {

    /**
     * @return currently applied {@link ExcludePayeeFilter}.
     */
    ExcludePayeeFilter getAppliedFilter();

    /**
     * Sets filter save action.
     *
     * @param action action to be performed
     */
    void setFilterSaveAction(IFilterSaveAction action);
}
