package com.copyright.rup.dist.foreign.ui.report.impl.report;

import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IReportWidget;

import com.vaadin.ui.MenuBar.MenuItem;

/**
 * Interface to build report menus.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/12/2019
 *
 * @author Aliaksanr Liakh
 */
public interface IReportMenuBuilder {

    /**
     * Add menu items to a root menu item.
     *
     * @param controller instance of {@link IReportController}
     * @param widget     instance of {@link IReportWidget}
     * @param rootItem   the root menu item
     */
    void addItems(IReportController controller, IReportWidget widget, MenuItem rootItem);
}
