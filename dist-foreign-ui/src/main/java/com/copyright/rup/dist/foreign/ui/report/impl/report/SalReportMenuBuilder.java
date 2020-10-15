package com.copyright.rup.dist.foreign.ui.report.impl.report;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IReportMenuBuilder;
import com.copyright.rup.dist.foreign.ui.report.api.IReportWidget;

import com.vaadin.ui.MenuBar.MenuItem;

/**
 * Implementation of {@link IReportMenuBuilder} to build report menu for SAL product family.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/14/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalReportMenuBuilder implements IReportMenuBuilder {

    @Override
    public void addItems(IReportController controller, IReportWidget widget, MenuItem rootItem) {
        String liabilitiesByRh = ForeignUi.getMessage("menu.report.liabilities_by_rh");
        rootItem.addItem(liabilitiesByRh, menuItem ->
            widget.openReportWindow(liabilitiesByRh, controller.getLiabilitiesByRhReportController()));
        String liabilitiesSummaryByRhAndWork = ForeignUi.getMessage("menu.report.liabilities_summary_by_rh_and_work");
        rootItem.addItem(liabilitiesSummaryByRhAndWork,
            menuItem -> widget.openReportWindow(liabilitiesSummaryByRhAndWork,
                controller.getLiabilitiesSummaryByRhAndWorkReportController()));
    }
}
