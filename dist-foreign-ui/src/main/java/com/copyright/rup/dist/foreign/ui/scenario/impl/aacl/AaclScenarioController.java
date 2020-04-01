package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenarioController;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.PipedOutputStream;

/**
 * Implementation of {@link IAaclScenarioController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/27/20
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AaclScenarioController extends CommonScenarioController implements IAaclScenarioController {

    @Override
    protected IAaclScenarioWidget instantiateWidget() {
        return new AaclScenarioWidget(this);
    }

    @Override
    protected ICommonDrillDownByRightsholderController getDrillDownByRightsholderController() {
        throw new NotImplementedException("Drill down-down by RH is not yet implemented");
    }

    @Override
    protected void writeScenarioUsagesCsvReport(Scenario scenarioForReport, PipedOutputStream pos) {
        throw new NotImplementedException("Export of scenario usages is not yet implemented");
    }
}
