package com.copyright.rup.dist.foreign.vui.report.impl;

import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.report.api.IReportController;
import com.copyright.rup.dist.foreign.vui.report.api.IReportWidget;
import com.copyright.rup.dist.foreign.vui.report.api.IUndistributedLiabilitiesReportController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IReportController}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReportController extends CommonController<IReportWidget> implements IReportController {

    private static final long serialVersionUID = -395767068632612665L;

    @Autowired
    private IUndistributedLiabilitiesReportController undistributedLiabilitiesReportController;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;

    @Override
    public void onProductFamilyChanged() {
        refreshWidget();
    }

    @Override
    public IProductFamilyProvider getProductFamilyProvider() {
        return productFamilyProvider;
    }

    @Override
    public IUndistributedLiabilitiesReportController getUndistributedLiabilitiesReportController() {
        return undistributedLiabilitiesReportController;
    }

    @Override
    protected IReportWidget instantiateWidget() {
        return new ReportWidget();
    }
}
