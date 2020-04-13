package com.copyright.rup.dist.foreign.ui.audit.api;

import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Common interface for audit widgets.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/17/2019
 *
 * @author Aliaksanr Liakh
 */
public interface ICommonAuditWidget extends IWidget<ICommonAuditController>, IRefreshable {

    /**
     * @return controller for the widget.
     */
    ICommonAuditController getController();

    /**
     * @return search value string.
     */
    String getSearchValue();
}
