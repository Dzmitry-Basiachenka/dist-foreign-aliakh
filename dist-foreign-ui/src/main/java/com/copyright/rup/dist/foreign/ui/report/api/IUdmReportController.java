package com.copyright.rup.dist.foreign.ui.report.api;

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
}
