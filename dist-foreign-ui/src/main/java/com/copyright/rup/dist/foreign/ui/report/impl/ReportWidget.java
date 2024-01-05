package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IReportWidget;
import com.copyright.rup.dist.foreign.ui.report.impl.report.ReportMenuBuilder;
import com.copyright.rup.dist.foreign.ui.report.impl.report.ReportStreamSource;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.server.Page;
import com.vaadin.server.ResourceReference;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Window;

/**
 * Implementation of {@link IReportWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
public class ReportWidget extends MenuBar implements IReportWidget {

    private static final long serialVersionUID = 6710773464631535911L;

    private IReportController controller;

    @Override
    public void refresh() {
        removeItems();
        IProductFamilyProvider productFamilyProvider = controller.getProductFamilyProvider();
        String productFamily = productFamilyProvider.getSelectedProductFamily();
        ReportMenuBuilder builder = ReportMenuBuilder.valueOf(productFamily);
        if (builder.isVisible()) {
            MenuItem rootItem = addItem(ForeignUi.getMessage("tab.reports"), null);
            rootItem.setStyleName("reports-menu-root");
            builder.addItems(controller, this, rootItem);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ReportWidget init() {
        VaadinUtils.addComponentStyle(this, "reports-menu");
        refresh();
        return this;
    }

    @Override
    public void setController(IReportController controller) {
        this.controller = controller;
    }

    @Override
    public void openReportWindow(String reportCaption, IController reportController) {
        Window reportWindow = (Window) reportController.initWidget();
        reportWindow.setCaption(reportCaption);
        Windows.showModalWindow(reportWindow);
    }

    @Override
    public void generateReport(IStreamSource streamSource) {
        VaadinSession session = VaadinSession.getCurrent();
        session.lock();
        try {
            ReportStreamSource resource = new ReportStreamSource(streamSource);
            setResource(resource.getFilename(), resource);
            ResourceReference resourceReference = ResourceReference.create(resource, this, resource.getFilename());
            Page.getCurrent().open(resourceReference.getURL(), null);
        } finally {
            session.unlock();
        }
    }
}
