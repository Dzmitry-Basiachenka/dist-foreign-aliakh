package com.copyright.rup.dist.foreign.vui.report.api;

import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IController;

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
}
