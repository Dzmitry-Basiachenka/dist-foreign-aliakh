package com.copyright.rup.dist.foreign.ui.usage.api.sal;

import com.copyright.rup.vaadin.widget.api.IRefreshable;

/**
 * Interface for SAL detail for rightsholder update window.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 11/21/2023
 *
 * @author Dzmitry Basiachenka
 */
public interface ISalDetailForRightsholderUpdateWindow extends IRefreshable {

    /**
     * Refreshes data provider and updates usages grid.
     */
    void refreshDataProvider();
}
