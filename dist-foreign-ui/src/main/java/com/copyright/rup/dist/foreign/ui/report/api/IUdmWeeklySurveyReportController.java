package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for widget for UDM Weekly Survey Report.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/15/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmWeeklySurveyReportController extends IController<IUdmWeeklySurveyReportWidget>,
    ICsvReportProvider {

    /**
     * @return list of all periods.
     */
    List<Integer> getAllPeriods();
}
