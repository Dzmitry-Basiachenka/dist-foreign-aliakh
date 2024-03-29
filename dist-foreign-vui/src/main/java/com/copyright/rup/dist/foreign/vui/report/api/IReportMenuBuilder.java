package com.copyright.rup.dist.foreign.vui.report.api;

import com.vaadin.flow.component.contextmenu.MenuItem;

/**
 * Interface to build report menus.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/12/2019
 *
 * @param <T> instance of {@link ICommonReportController}
 * @author Aliaksanr Liakh
 */
public interface IReportMenuBuilder<T extends ICommonReportController> {

    /**
     * Add menu items to a root menu item.
     *
     * @param controller instance of {@link IReportController}
     * @param widget     instance of {@link IReportWidget}
     * @param rootItem   the root menu item
     */
    void addItems(T controller, IReportWidget widget, MenuItem rootItem);
}
