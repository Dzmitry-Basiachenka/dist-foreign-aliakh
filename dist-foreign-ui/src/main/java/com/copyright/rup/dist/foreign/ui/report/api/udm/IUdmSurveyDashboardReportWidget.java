package com.copyright.rup.dist.foreign.ui.report.api.udm;

import com.copyright.rup.vaadin.widget.api.IWidget;

import java.util.Set;

/**
 * Interface for widget for UDM Survey Dashboard Report.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/07/2022
 *
 * @author Anton Azarenka
 */
public interface IUdmSurveyDashboardReportWidget extends IWidget<IUdmSurveyDashboardReportController> {

    /**
     * @return selected periods to generate report.
     */
    Set<Integer> getSelectedPeriods();
}
