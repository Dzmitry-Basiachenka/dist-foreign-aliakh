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
     * @return UDM Completed Assignments Report controller.
     */
    ICompletedAssignmentsReportController getCompletedAssignmentsReportController();

    /**
     * @return UDM Verified Details by Source Report controller.
     */
    IUdmVerifiedDetailsBySourceReportController getUdmVerifiedDetailsBySourceReportController();

    /**
     * @return Udm Usable Details by Country Report controller.
     */
    IUdmUsableDetailsByCountryReportController getUdmUsableDetailsByCountryReportController();
}
