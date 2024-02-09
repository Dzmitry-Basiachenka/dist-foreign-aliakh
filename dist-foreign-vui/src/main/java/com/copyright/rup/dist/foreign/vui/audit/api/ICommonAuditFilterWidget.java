package com.copyright.rup.dist.foreign.vui.audit.api;

import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.IFilterSaveAction;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IFilterWidget;

/**
 * Interface for audit filter controller.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/17/2019
 *
 * @author Aliaksanr Liakh
 */
public interface ICommonAuditFilterWidget extends IFilterWidget<ICommonAuditFilterController> {

    /**
     * @return currently applied {@link AuditFilter}.
     */
    AuditFilter getAppliedFilter();

    /**
     * @return {@link AuditFilter}.
     */
    AuditFilter getFilter();

    /**
     * Sets filter save action.
     *
     * @param action action to be performed
     */
    void setFilterSaveAction(IFilterSaveAction action);
}
