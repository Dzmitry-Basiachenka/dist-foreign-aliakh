package com.copyright.rup.dist.foreign.vui.report.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

/**
 * Interface for scenario report widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/22/2020
 *
 * @author Ihar Suvorau
 */
public interface ICommonScenarioReportWidget extends IWidget<ICommonScenarioReportController> {

    /**
     * @return the selected scenario.
     */
    Scenario getScenario();
}
