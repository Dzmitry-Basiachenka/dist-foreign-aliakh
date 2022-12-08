package com.copyright.rup.dist.foreign.ui.report.api.udm;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for UdmSurveyDashboardReportController.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/07/2022
 *
 * @author Anton Azarenka
 */
public interface IUdmSurveyDashboardReportController
    extends IController<IUdmSurveyDashboardReportWidget>, ICsvReportProvider {

    /**
     * @return list of all periods.
     */
    List<Integer> getPeriods();
}
