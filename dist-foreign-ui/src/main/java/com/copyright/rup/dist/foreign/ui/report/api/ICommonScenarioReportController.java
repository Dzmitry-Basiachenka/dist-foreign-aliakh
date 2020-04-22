package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for common scenario report controller.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/22/2020
 *
 * @author Ihar Suvorau
 */
public interface ICommonScenarioReportController extends IController<IScenarioReportWidget>,
    ICsvReportProvider {

    /**
     * @return list of all {@link Scenario}s.
     */
    List<Scenario> getScenarios();
}
