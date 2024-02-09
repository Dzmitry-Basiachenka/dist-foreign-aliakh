package com.copyright.rup.dist.foreign.vui.report.impl.report;

import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.report.api.IReportController;
import com.copyright.rup.dist.foreign.vui.report.api.IReportMenuBuilder;
import com.copyright.rup.dist.foreign.vui.report.api.IReportWidget;

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
public class FasReportMenuBuilder implements IReportMenuBuilder {

    @Override
    public void addItems(IReportController controller, IReportWidget widget, MenuItem rootItem) {
        var subMenu = rootItem.getSubMenu();
        var undistributedLiabilitiesReport = ForeignUi.getMessage("menu.report.undistributed_liabilities");
        subMenu.addItem(ForeignUi.getMessage("menu.report.batch_summary"), menuItem ->
            widget.generateReport(controller.getFasBatchSummaryReportStreamSource()));
        subMenu.addItem(ForeignUi.getMessage("menu.report.research_status"), menuItem ->
            widget.generateReport(controller.getResearchStatusReportStreamSource()));
        subMenu.addItem(undistributedLiabilitiesReport,
            menuItem -> widget.openReportWindow(undistributedLiabilitiesReport,
                controller.getUndistributedLiabilitiesReportController()));
        //TODO {vaadin23} will implement later
    }
}
