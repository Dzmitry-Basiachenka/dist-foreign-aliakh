package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.INtsDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.INtsScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.INtsScenarioWidget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.PipedOutputStream;

/**
 * Implementation of {@link INtsScenarioController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsScenarioController extends CommonScenarioController<INtsScenarioWidget, INtsScenarioController>
    implements INtsScenarioController {

    @Autowired
    private INtsDrillDownByRightsholderController drillDownByRightsholderController;

    @Override
    protected INtsScenarioWidget instantiateWidget() {
        return new NtsScenarioWidget();
    }

    @Override
    protected ICommonDrillDownByRightsholderController<?, ?> getDrillDownByRightsholderController() {
        return drillDownByRightsholderController;
    }

    @Override
    protected void writeScenarioUsagesCsvReport(Scenario scenarioForReport, PipedOutputStream pos) {
        getReportService().writeNtsScenarioUsagesCsvReport(scenarioForReport, pos);
    }
}
