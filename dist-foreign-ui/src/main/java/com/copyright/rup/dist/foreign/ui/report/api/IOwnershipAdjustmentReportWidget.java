package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for widget to generate Ownership Adjustment Report.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/27/2019
 *
 * @author Aliaksandr Liakh
 */
public interface IOwnershipAdjustmentReportWidget extends IWidget<IOwnershipAdjustmentReportController> {

    /**
     * @return the selected scenario.
     */
    Scenario getScenario();
}
