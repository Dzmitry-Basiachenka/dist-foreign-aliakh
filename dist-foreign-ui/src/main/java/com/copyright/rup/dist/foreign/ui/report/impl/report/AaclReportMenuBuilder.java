package com.copyright.rup.dist.foreign.ui.report.impl.report;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IReportMenuBuilder;
import com.copyright.rup.dist.foreign.ui.report.api.IReportWidget;

import com.vaadin.ui.MenuBar.MenuItem;

/**
 * Implementation of {@link IReportMenuBuilder} to build report menu for AACL product families.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/22/2020
 *
 * @author Ihar Suvorau
 */
public class AaclReportMenuBuilder implements IReportMenuBuilder {

    @Override
    public void addItems(IReportController controller, IReportWidget widget, MenuItem rootItem) {
        String workSharesByAggLcClassReport =
            ForeignUi.getMessage("menu.report.work_shares_by_agg_lc_class");
        String workSharesByAggLcClassSummaryReport =
            ForeignUi.getMessage("menu.report.work_shares_by_agg_lc_class_summary");
        String aaclBaselineUsagesReport = ForeignUi.getMessage("menu.report.baseline_usages_report");
        String taxNotificationReport = ForeignUi.getMessage("menu.report.tax_notification_report");
        rootItem.addItem(workSharesByAggLcClassReport, menuItem -> widget.openReportWindow(workSharesByAggLcClassReport,
            controller.getWorkSharesByAggLcClassReportController()));
        rootItem.addItem(workSharesByAggLcClassSummaryReport,
            menuItem -> widget.openReportWindow(workSharesByAggLcClassSummaryReport,
                controller.getWorkSharesByAggLcClassSummaryReportController()));
        rootItem.addItem(aaclBaselineUsagesReport, menuItem -> widget.openReportWindow(aaclBaselineUsagesReport,
            controller.getAaclBaselineUsagesReportController()));
        rootItem.addItem(ForeignUi.getMessage("menu.report.undistributed_liabilities"),
            menuItem -> widget.generateReport(controller.getAaclUndistributedLiabilitiesReportStreamSource()));
        rootItem.addItem(taxNotificationReport, menuItem ->
            widget.openReportWindow(taxNotificationReport, controller.getTaxNotificationReportController()));
    }
}
