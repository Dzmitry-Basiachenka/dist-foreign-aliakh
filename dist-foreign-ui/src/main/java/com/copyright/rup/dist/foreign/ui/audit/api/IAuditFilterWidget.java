package com.copyright.rup.dist.foreign.ui.audit.api;

import com.copyright.rup.dist.foreign.domain.AuditFilter;
import com.copyright.rup.vaadin.widget.api.IFilterWidget;

/**
 * Interface for audit filter controller.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/12/18
 *
 * @author Aliaksandr Radkevich
 */
public interface IAuditFilterWidget extends IFilterWidget<IAuditFilterController> {

    /**
     * @return currently applied {@link AuditFilter}.
     */
    AuditFilter getAppliedFilter();
}
