package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenarioController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.PipedOutputStream;

/**
 * Implementation of {@link ISalScenarioController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/22/2020
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalScenarioController extends CommonScenarioController implements ISalScenarioController {

    private static final long serialVersionUID = 4763053341116713554L;

    @Autowired
    private ISalDrillDownByRightsholderController drillDownByRightsholderController;

    @Override
    protected ISalScenarioWidget instantiateWidget() {
        return new SalScenarioWidget(this);
    }

    @Override
    protected ICommonDrillDownByRightsholderController getDrillDownByRightsholderController() {
        return drillDownByRightsholderController;
    }

    @Override
    protected void writeScenarioUsagesCsvReport(Scenario scenarioForReport, PipedOutputStream pos) {
        getReportService().writeSalScenarioUsagesCsvReport(scenarioForReport, pos);
    }
}
