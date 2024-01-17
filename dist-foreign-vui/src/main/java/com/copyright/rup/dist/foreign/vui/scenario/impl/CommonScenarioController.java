package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenarioController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import java.io.PipedOutputStream;

/**
 * Common controller for {@link ICommonScenarioWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
public abstract class CommonScenarioController extends CommonController<ICommonScenarioWidget>
    implements ICommonScenarioController {

    private static final long serialVersionUID = 5857000239462224435L;

    /**
     * Writes scenario usages into csv output stream.
     *
     * @param scenarioForReport a {@link Scenario}
     * @param pos               a {@link PipedOutputStream} instance
     */
    protected abstract void writeScenarioUsagesCsvReport(Scenario scenarioForReport, PipedOutputStream pos);
}
