package com.copyright.rup.dist.foreign.ui.report.impl.report;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IReportWidget;

import com.vaadin.ui.MenuBar.MenuItem;

/**
 * Implementation of {@link IReportMenuBuilder} to build report menu for FAS, FAS2 product families.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/12/2019
 *
 * @author Aliaksanr Liakh
 */
public class FasReportMenuBuilder implements IReportMenuBuilder {

    @Override
    public void addItems(IReportController controller, IReportWidget widget, MenuItem rootItem) {
        String undistributedLiabilitiesReport = ForeignUi.getMessage("menu.report.undistributed_liabilities");
        String serviceFeeTrueUpReport = ForeignUi.getMessage("menu.report.service_fee_true_up");
        String summaryMarketReport = ForeignUi.getMessage("menu.report.market_summary");
        String ownershipAdjustmentReport = ForeignUi.getMessage("menu.report.ownership_adjustment_report");
        rootItem.addItem(ForeignUi.getMessage("menu.report.batch_summary"), menuItem ->
            widget.generateReport(controller.getFasBatchSummaryReportStreamSource()));
        rootItem.addItem(summaryMarketReport, menuItem ->
            widget.openReportWindow(summaryMarketReport, controller.getSummaryMarketReportController()));
        rootItem.addItem(ForeignUi.getMessage("menu.report.research_status"), menuItem ->
            widget.generateReport(controller.getResearchStatusReportStreamSource()));
        rootItem.addItem(serviceFeeTrueUpReport, menuItem ->
            widget.openReportWindow(serviceFeeTrueUpReport, controller.getServiceFeeTrueUpReportController()));
        rootItem.addItem(undistributedLiabilitiesReport, menuItem ->
            widget.openReportWindow(undistributedLiabilitiesReport,
                controller.getUndistributedLiabilitiesReportController()));
        rootItem.addItem(ownershipAdjustmentReport, menuItem ->
            widget.openReportWindow(ownershipAdjustmentReport, controller.getOwnershipAdjustmentReportController()));
    }
}
