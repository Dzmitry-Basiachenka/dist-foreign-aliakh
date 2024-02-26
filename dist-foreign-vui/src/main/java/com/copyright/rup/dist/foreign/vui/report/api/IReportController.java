package com.copyright.rup.dist.foreign.vui.report.api;

import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IController;

import com.vaadin.flow.component.contextmenu.MenuItem;

/**
 * Controller for report menu.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
public interface IReportController extends IController<IReportWidget> {

    /**
     * Handles global product family selection.
     */
    void onProductFamilyChanged();

    /**
     * @return product family provider.
     */
    IProductFamilyProvider getProductFamilyProvider();

    /**
     * Adds menu items to a root menu item.
     *
     * @param productFamily product family
     * @param rootItem      root item
     */
    void addItems(String productFamily, MenuItem rootItem);

    /**
     * Returns visible flag for provided product family.
     *
     * @param productFamily product family
     * @return <code>true</code> if visible, otherwise <code>flase</code>
     */
    boolean isReportVisible(String productFamily);
}
