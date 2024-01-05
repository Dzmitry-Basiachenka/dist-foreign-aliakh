package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmReportWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.IController;

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

    private static final long serialVersionUID = -668631129185002124L;

    private IUdmReportController controller;

    @Override
    public void refresh() {
        removeItems();
        MenuItem rootItem = addItem(ForeignUi.getMessage("tab.reports"), null);
        rootItem.setStyleName("reports-menu-root");
        if (ForeignSecurityUtils.hasManagerPermission() || ForeignSecurityUtils.hasSpecialistPermission()
            || ForeignSecurityUtils.hasViewOnlyPermission() || ForeignSecurityUtils.hasApproverPermission()) {
            String weeklySurveyReport = ForeignUi.getMessage("menu.report.weekly_survey_report");
            rootItem.addItem(weeklySurveyReport, menuItem ->
                this.openReportWindow(weeklySurveyReport, controller.getUdmWeeklySurveyReportController()));
            String surveyLicenseeReport = ForeignUi.getMessage("menu.report.survey_licensee_report");
            rootItem.addItem(surveyLicenseeReport, menuItem ->
                this.openReportWindow(surveyLicenseeReport, controller.getUdmSurveyLicenseeReportController()));
            String surveyDashboardReport = ForeignUi.getMessage("menu.report.survey_dashboard_report");
            rootItem.addItem(surveyDashboardReport, menuItem -> this.openReportWindow(surveyDashboardReport,
                controller.getUdmSurveyDashboardReportController()));
            String verifiedDetailsBySourceReport =
                ForeignUi.getMessage("menu.report.verified_details_by_source_report");
            rootItem.addItem(verifiedDetailsBySourceReport, menuItem ->
                this.openReportWindow(verifiedDetailsBySourceReport,
                    controller.getUdmVerifiedDetailsBySourceReportController()));
            String usableDetailsByCountry = ForeignUi.getMessage("menu.report.usable_details_by_country");
            rootItem.addItem(usableDetailsByCountry, menuItem -> this.openReportWindow(usableDetailsByCountry,
                controller.getUdmUsableDetailsByCountryReportController()));
            String usageEditsInBaselineReport = ForeignUi.getMessage("menu.report.usage_edits_in_baseline_report");
            rootItem.addItem(usageEditsInBaselineReport, menuItem ->
                this.openReportWindow(usageEditsInBaselineReport,
                    controller.getUdmUsageEditsInBaselineReportController()));
            String baselineValueUpdatesReport = ForeignUi.getMessage("menu.report.baseline_value_updates");
            rootItem.addItem(baselineValueUpdatesReport,
                menuItem -> this.openReportWindow(baselineValueUpdatesReport,
                    controller.getUdmBaselineValueUpdatesReportController()));
        }
        String completeAssignmentsReport = ForeignUi.getMessage("menu.report.completed_assignments_report");
        rootItem.addItem(completeAssignmentsReport, menuItem ->
            this.openReportWindow(completeAssignmentsReport, controller.getCompletedAssignmentsReportController()));
        String usagesByStatusReport = ForeignUi.getMessage("menu.report.usages_by_status_report");
        rootItem.addItem(usagesByStatusReport,
            menuItem -> this.openReportWindow(usagesByStatusReport, controller.getUdmUsagesByStatusReportController()));
        String valuesByStatusReport = ForeignUi.getMessage("menu.report.values_by_status_report");
        rootItem.addItem(valuesByStatusReport,
            menuItem -> this.openReportWindow(valuesByStatusReport, controller.getUdmValuesByStatusReportController()));
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
}
