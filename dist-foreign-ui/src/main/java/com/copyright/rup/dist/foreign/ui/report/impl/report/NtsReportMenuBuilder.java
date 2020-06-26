package com.copyright.rup.dist.foreign.ui.report.impl.report;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IReportMenuBuilder;
import com.copyright.rup.dist.foreign.ui.report.api.IReportWidget;

import com.vaadin.ui.MenuBar.MenuItem;

/**
 * Implementation of {@link IReportMenuBuilder} to build report menu for NTS product families.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/12/2019
 *
 * @author Aliaksanr Liakh
 */
public class NtsReportMenuBuilder implements IReportMenuBuilder {

    @Override
    public void addItems(IReportController controller, IReportWidget widget, MenuItem rootItem) {
        String taxNotificationReport = ForeignUi.getMessage("menu.report.tax_notification_report");
        rootItem.addItem(ForeignUi.getMessage("menu.report.nts_batch_summary"), menuItem ->
            widget.generateReport(controller.getNtsWithdrawnBatchSummaryReportStreamSource()));
        rootItem.addItem(taxNotificationReport, menuItem ->
            widget.openReportWindow(taxNotificationReport, controller.getTaxNotificationReportController()));
    }
}
