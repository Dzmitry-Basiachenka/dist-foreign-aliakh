package com.copyright.rup.dist.foreign.vui.report.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.report.api.IReportController;
import com.copyright.rup.dist.foreign.vui.report.api.IReportMenuBuilder;
import com.copyright.rup.dist.foreign.vui.report.api.IReportWidget;
import com.copyright.rup.dist.foreign.vui.report.impl.builder.FasReportMenuBuilder;
import com.copyright.rup.dist.foreign.vui.report.impl.builder.NtsReportMenuBuilder;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import com.vaadin.flow.component.contextmenu.MenuItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link IReportController}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
@Component("df.reportController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReportController extends CommonController<IReportWidget> implements IReportController {

    private static final long serialVersionUID = -395767068632612665L;

    private final Map<String, IReportMenuBuilder> reportMenuBuilderMap;

    @Autowired
    private ReportControllerProvider reportControllerProvider;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;

    /**
     * Controller.
     */
    public ReportController() {
        reportMenuBuilderMap = new HashMap<>();
        reportMenuBuilderMap.put(FdaConstants.FAS_PRODUCT_FAMILY, new FasReportMenuBuilder());
        reportMenuBuilderMap.put(FdaConstants.CLA_FAS_PRODUCT_FAMILY, new FasReportMenuBuilder());
        reportMenuBuilderMap.put(FdaConstants.NTS_PRODUCT_FAMILY, new NtsReportMenuBuilder());
    }

    @Override
    public void onProductFamilyChanged() {
        refreshWidget();
    }

    @Override
    public IProductFamilyProvider getProductFamilyProvider() {
        return productFamilyProvider;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addItems(String productFamily, MenuItem rootItem) {
        reportMenuBuilderMap.get(productFamily)
            .addItems(reportControllerProvider.getController(productFamily), getWidget(), rootItem);
    }

    @Override
    public boolean isReportVisible(String productFamily) {
        return Objects.nonNull(reportMenuBuilderMap.get(productFamily));
    }

    @Override
    protected IReportWidget instantiateWidget() {
        return new ReportWidget();
    }
}
