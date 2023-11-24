package com.copyright.rup.dist.foreign.ui.usage.api.aclci;

import com.copyright.rup.vaadin.widget.api.IRefreshable;

/**
 * Interface for ACLCI usage update window.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 11/24/2023
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclciUsageUpdateWindow extends IRefreshable {

    /**
     * Refreshes data provider and updates usages grid.
     */
    void refreshDataProvider();
}
