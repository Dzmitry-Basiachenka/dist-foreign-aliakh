package com.copyright.rup.dist.foreign.ui.status.api;

import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Common interface for usage batch status widgets.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Ihar Suvorau
 */
public interface ICommonBatchStatusWidget extends IWidget<ICommonBatchStatusController>, IRefreshable {

    /**
     * @return controller for the widget.
     */
    ICommonBatchStatusController getController();
}
