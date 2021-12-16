package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.IUdmReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IUdmReportWidget;
import com.copyright.rup.dist.foreign.ui.report.impl.report.ReportStreamSource;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.IController;
import com.vaadin.server.Page;
import com.vaadin.server.ResourceReference;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Window;

/**
 * Implementation of {@link IUdmReportWidget}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/16/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmReportWidget extends MenuBar implements IUdmReportWidget {

    private IUdmReportController controller;

    @Override
    public void refresh() {
        removeItems();
        MenuItem rootItem = addItem(ForeignUi.getMessage("tab.reports"), null);
        rootItem.setStyleName("reports-menu-root");
        String weeklySurveyReport = ForeignUi.getMessage("menu.report.weekly_survey_report");
        rootItem.addItem(weeklySurveyReport, menuItem ->
            this.openReportWindow(weeklySurveyReport, controller.getUdmWeeklySurveyReportController()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public UdmReportWidget init() {
        this.addStyleName("reports-menu");
        refresh();
        return this;
    }

    @Override
    public void setController(IUdmReportController controller) {
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
