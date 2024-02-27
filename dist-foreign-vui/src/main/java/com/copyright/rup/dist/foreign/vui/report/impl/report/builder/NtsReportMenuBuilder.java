package com.copyright.rup.dist.foreign.vui.report.impl.report.builder;

import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.report.api.IReportMenuBuilder;
import com.copyright.rup.dist.foreign.vui.report.api.IReportWidget;
import com.copyright.rup.dist.foreign.vui.report.api.nts.INtsReportController;

import com.vaadin.flow.component.contextmenu.MenuItem;

/**
 * Implementation of {@link IReportMenuBuilder} to build report menu for NTS product families.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/12/2019
 *
 * @author Aliaksanr Liakh
 */
public class NtsReportMenuBuilder implements IReportMenuBuilder<INtsReportController> {

    @Override
    public void addItems(INtsReportController controller, IReportWidget widget, MenuItem rootItem) {
        var subMenu = rootItem.getSubMenu();
        String taxNotificationReport = ForeignUi.getMessage("menu.report.tax_notification_report");
        String serviceFeeTrueUpReport = ForeignUi.getMessage("menu.report.service_fee_true_up");
        subMenu.addItem(ForeignUi.getMessage("menu.report.nts_batch_summary"), menuItem ->
            widget.generateReport(controller.getNtsWithdrawnBatchSummaryReportStreamSource()));
        subMenu.addItem(ForeignUi.getMessage("menu.report.undistributed_liabilities"), menuItem ->
            widget.generateReport(controller.getNtsUndistributedLiabilitiesReportStreamSource()));
        subMenu.addItem(taxNotificationReport, menuItem ->
            widget.openReportWindow(taxNotificationReport, controller.getTaxNotificationReportController()));
        subMenu.addItem(serviceFeeTrueUpReport, menuItem ->
            widget.openReportWindow(serviceFeeTrueUpReport, controller.getNtsServiceFeeTrueUpReportController()));
        subMenu.addItem(ForeignUi.getMessage("menu.report.nts_fund_pools"), menuItem ->
            widget.generateReport(controller.getNtsFundPoolsReportStreamSource()));
        //TODO: {vaadin23} will implement later
    }
}
