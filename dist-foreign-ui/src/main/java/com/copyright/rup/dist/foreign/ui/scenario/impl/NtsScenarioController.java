package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.INtsDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.INtsScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.INtsScenarioWidget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
}
