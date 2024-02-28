package com.copyright.rup.dist.foreign.vui.report.impl.builder;

import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.report.api.IReportMenuBuilder;
import com.copyright.rup.dist.foreign.vui.report.api.IReportWidget;
import com.copyright.rup.dist.foreign.vui.report.api.fas.IFasReportController;

import com.vaadin.flow.component.contextmenu.MenuItem;

/**
 * Implementation of {@link IReportMenuBuilder} to build report menu for FAS, FAS2 product families.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/12/2019
 *
 * @author Aliaksanr Liakh
 */
public class FasReportMenuBuilder implements IReportMenuBuilder<IFasReportController> {

    @Override
    public void addItems(IFasReportController controller, IReportWidget widget, MenuItem rootItem) {
        var subMenu = rootItem.getSubMenu();
        var undistributedLiabilitiesReport = ForeignUi.getMessage("menu.report.undistributed_liabilities");
        var summaryMarketReport = ForeignUi.getMessage("menu.report.market_summary");
        var serviceFeeTrueUpReport = ForeignUi.getMessage("menu.report.service_fee_true_up");
        var ownershipAdjustmentReport = ForeignUi.getMessage("menu.report.ownership_adjustment_report");
        var taxNotificationReport = ForeignUi.getMessage("menu.report.tax_notification_report");
        subMenu.addItem(ForeignUi.getMessage("menu.report.batch_summary"), menuItem ->
            widget.generateReport(controller.getFasBatchSummaryReportStreamSource()));
        subMenu.addItem(summaryMarketReport, menuItem ->
            widget.openReportWindow(summaryMarketReport, controller.getSummaryMarketReportController()));
        subMenu.addItem(ForeignUi.getMessage("menu.report.research_status"), menuItem ->
            widget.generateReport(controller.getResearchStatusReportStreamSource()));
        subMenu.addItem(serviceFeeTrueUpReport, menuItem ->
            widget.openReportWindow(serviceFeeTrueUpReport, controller.getFasServiceFeeTrueUpReportController()));
        subMenu.addItem(undistributedLiabilitiesReport,
            menuItem -> widget.openReportWindow(undistributedLiabilitiesReport,
                controller.getUndistributedLiabilitiesReportController()));
        subMenu.addItem(ownershipAdjustmentReport, menuItem ->
            widget.openReportWindow(ownershipAdjustmentReport, controller.getOwnershipAdjustmentReportController()));
        subMenu.addItem(taxNotificationReport, menuItem ->
            widget.openReportWindow(taxNotificationReport, controller.getTaxNotificationReportController()));
    }
}
