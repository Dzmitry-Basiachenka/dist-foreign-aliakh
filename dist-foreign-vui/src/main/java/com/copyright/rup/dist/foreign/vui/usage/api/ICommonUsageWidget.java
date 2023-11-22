package com.copyright.rup.dist.foreign.vui.usage.api;

import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

/**
 * Common interface for usages tab widgets.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @author Uladzislau Shalamitski
 */
public interface ICommonUsageWidget extends IWidget<ICommonUsageController> {

    /**
     * @return controller for given widget.
     */
    ICommonUsageController getController();
}
