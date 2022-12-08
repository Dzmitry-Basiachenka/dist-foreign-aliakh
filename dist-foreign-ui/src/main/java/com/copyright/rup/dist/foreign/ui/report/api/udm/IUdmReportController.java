package com.copyright.rup.dist.foreign.ui.report.api.udm;

import com.copyright.rup.vaadin.widget.api.IController;

/**
 * Controller for UDM report menu.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/16/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmReportController extends IController<IUdmReportWidget> {

    /**
     * @return UDM Weekly Survey Report controller.
     */
    IUdmWeeklySurveyReportController getUdmWeeklySurveyReportController();

    /**
     * @return UDM Weekly Survey Report controller.
     */
    IUdmSurveyLicenseeReportController getUdmSurveyLicenseeReportController();

    /**
     * @return UDM Survey Dashboard Report controller.
     */
    IUdmSurveyDashboardReportController getUdmSurveyDashboardReportController();

    /**
     * @return UDM Completed Assignments Report controller.
     */
    IUdmCommonUserNamesReportController getCompletedAssignmentsReportController();

    /**
     * @return UDM Verified Details by Source Report controller.
     */
    IUdmVerifiedDetailsBySourceReportController getUdmVerifiedDetailsBySourceReportController();

    /**
     * @return Udm Usable Details by Country Report controller.
     */
    IUdmUsableDetailsByCountryReportController getUdmUsableDetailsByCountryReportController();

    /**
     * @return UDM Usage Edits in Baseline Report controller.
     */
    IUdmUsageEditsInBaselineReportController getUdmUsageEditsInBaselineReportController();

    /**
     * @return UDM Usages by Status Report controller.
     */
    IUdmUsagesByStatusReportController getUdmUsagesByStatusReportController();

    /**
     * @return UDM Values by Status Report controller.
     */
    IUdmValuesByStatusReportController getUdmValuesByStatusReportController();

    /**
     * @return UDM Baseline Value Updates Report controller.
     */
    IUdmCommonUserNamesReportController getUdmBaselineValueUpdatesReportController();
}
