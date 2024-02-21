package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenarioController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenarioWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonScenarioController;

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
 * Date: 12/10/2019
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsScenarioController extends CommonScenarioController implements INtsScenarioController {

    private static final long serialVersionUID = -3984338084924174512L;

    @Autowired
    private INtsDrillDownByRightsholderController drillDownByRightsholderController;

    @Override
    protected INtsScenarioWidget instantiateWidget() {
        return new NtsScenarioWidget();
    }

    @Override
    protected ICommonDrillDownByRightsholderController getDrillDownByRightsholderController() {
        return drillDownByRightsholderController;
    }

    @Override
    protected void writeScenarioUsagesCsvReport(Scenario scenarioForReport, PipedOutputStream pos) {
        getReportService().writeNtsScenarioUsagesCsvReport(scenarioForReport, pos);
    }
}
