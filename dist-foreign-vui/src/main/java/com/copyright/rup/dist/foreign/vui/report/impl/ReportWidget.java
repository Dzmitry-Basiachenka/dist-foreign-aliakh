package com.copyright.rup.dist.foreign.vui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.report.api.IReportController;
import com.copyright.rup.dist.foreign.vui.report.api.IReportWidget;
import com.copyright.rup.dist.foreign.vui.report.impl.report.ReportMenuBuilder;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IController;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.menubar.MenuBar;

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
        removeAll();
        var productFamilyProvider = controller.getProductFamilyProvider();
        var productFamily = productFamilyProvider.getSelectedProductFamily();
        var builder = ReportMenuBuilder.valueOf(productFamily);
        if (builder.isVisible()) {
            MenuItem rootItem = addItem(ForeignUi.getMessage("tab.reports"));
            setClassName("reports-menu-root");
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
        var reportWindow = (Dialog) reportController.initWidget();
        reportWindow.setHeaderTitle(reportCaption);
        Windows.showModalWindow(reportWindow);
    }

    @Override
    public void generateReport(IStreamSource streamSource) {
        var fileDownloader = new OnDemandFileDownloader(streamSource.getSource());
        add(fileDownloader);
        fileDownloader.getElement().callJsFunction("click");
    }
}
