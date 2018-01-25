package com.copyright.rup.dist.foreign.ui.audit.api;

import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for audit widget.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/11/18
 *
 * @author Aliaksandr Radkevich
 */
public interface IAuditWidget extends IWidget<IAuditController>, IRefreshable {

    /**
     * @return search value string.
     */
    String getSearchValue();
}
