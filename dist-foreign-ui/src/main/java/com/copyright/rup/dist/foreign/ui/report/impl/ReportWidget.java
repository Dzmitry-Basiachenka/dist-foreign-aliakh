package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IReportWidget;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.widget.api.IController;

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

    private IReportController controller;
    private MenuItem rootItem;

    @Override
    @SuppressWarnings("unchecked")
    public ReportWidget init() {
        VaadinUtils.addComponentStyle(this, "reports-menu");
        rootItem = addItem(ForeignUi.getMessage("tab.reports"), null);
        rootItem.setStyleName("reports-menu-root");
        addReportItem("Report1", controller.getReportWindowController());
        return this;
    }

    @Override
    public void setController(IReportController controller) {
        this.controller = controller;
    }

    private void addReportItem(String reportCaption, IController reportController) {
        rootItem.addItem(reportCaption, new ReportSelectCommand(reportController, reportCaption));
    }

    private static class ReportSelectCommand implements Command {

        private IController reportController;
        private String reportCaption;

        /**
         * Constructor.
         *
         * @param reportController controller for window.
         * @param reportCaption    caption for window.
         */
        ReportSelectCommand(IController reportController, String reportCaption) {
            this.reportController = reportController;
            this.reportCaption = reportCaption;
        }

        @Override
        public void menuSelected(MenuItem selectedItem) {
            Windows.showModalWindow(new Window());
        }
    }

}
