package com.copyright.rup.dist.foreign.vui.audit.api;

import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

/**
 * Common interface for audit widgets.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/17/2019
 *
 * @author Aliaksanr Liakh
 */
public interface ICommonAuditWidget extends IWidget<ICommonAuditController> {

    /**
     * @return controller for the widget.
     */
    ICommonAuditController getController();

    /**
     * @return search value string.
     */
    String getSearchValue();
}
