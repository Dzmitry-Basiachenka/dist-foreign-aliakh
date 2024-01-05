package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclReportController;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclReportWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Window;

/**
 * Implementation of {@link IAclReportWidget}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/03/2022
 *
 * @author Ihar Suvorau
 */
public class AclReportWidget extends MenuBar implements IAclReportWidget {

    private static final long serialVersionUID = -3905081625930117794L;

    private IAclReportController controller;

    @Override
    public void refresh() {
        removeItems();
        MenuItem rootItem = addItem(ForeignUi.getMessage("tab.reports"), null);
        rootItem.setStyleName("reports-menu-root");
        String liabilitiesByAggLicClassReport = ForeignUi.getMessage("menu.report.liabilities_by_agg_lc");
        rootItem.addItem(liabilitiesByAggLicClassReport,
            menuItem -> openReportWindow(liabilitiesByAggLicClassReport,
                controller.getAclLiabilitiesByAggLicClassReportController()));
        String liabilityDetailsReport = ForeignUi.getMessage("menu.report.liability_details");
        rootItem.addItem(liabilityDetailsReport,
            menuItem -> openReportWindow(liabilityDetailsReport, controller.getAclLiabilityDetailsReportController()));
        String liabilitiesByRhReport = ForeignUi.getMessage("menu.report.liabilities_by_rightsholder");
        rootItem.addItem(liabilitiesByRhReport,
            menuItem -> openReportWindow(liabilitiesByRhReport, controller.getAclLiabilitiesByRhReportController()));
        String taxNotificationReport = ForeignUi.getMessage("menu.report.tax_notification_report");
        rootItem.addItem(taxNotificationReport,
            menuItem -> openReportWindow(taxNotificationReport, controller.getTaxNotificationReportController()));
        String fundPoolByAggLcReport = ForeignUi.getMessage("menu.report.fund_pools_by_agg_lc_report");
        rootItem.addItem(fundPoolByAggLcReport,
            menuItem -> openReportWindow(fundPoolByAggLcReport, controller.getFundPoolByAggLcReportController()));
        String comparisonByAggLcClassAndTitleReport =
            ForeignUi.getMessage("menu.report.comparison_by_agg_lc_cl_and_title_report");
        rootItem.addItem(comparisonByAggLcClassAndTitleReport,
            menuItem -> openReportWindow(comparisonByAggLcClassAndTitleReport,
                controller.getComparisonByAggLcClassAndTitleReportController()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public AclReportWidget init() {
        this.addStyleName("reports-menu");
        refresh();
        return this;
    }

    @Override
    public void setController(IAclReportController controller) {
        this.controller = controller;
    }

    @Override
    public void openReportWindow(String reportCaption, IController reportController) {
        Window reportWindow = (Window) reportController.initWidget();
        reportWindow.setCaption(reportCaption);
        Windows.showModalWindow(reportWindow);
    }
}
