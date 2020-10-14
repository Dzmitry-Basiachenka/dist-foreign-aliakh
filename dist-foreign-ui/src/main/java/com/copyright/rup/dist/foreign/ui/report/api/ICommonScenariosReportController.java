package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for common scenarios report controller.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/14/2020
 *
 * @author Uladzislau Shalamitski
 */
public interface ICommonScenariosReportController extends IController<ICommonScenariosReportWidget>,
    ICsvReportProvider {

    /**
     * @return list of all {@link Scenario}s.
     */
    List<Scenario> getScenarios();
}
