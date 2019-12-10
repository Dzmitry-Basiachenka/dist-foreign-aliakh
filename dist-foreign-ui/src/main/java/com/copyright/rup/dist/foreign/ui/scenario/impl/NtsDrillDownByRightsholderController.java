package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.ui.scenario.api.INtsDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.INtsDrillDownByRightsholderWidget;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation for {@link INtsDrillDownByRightsholderController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/9/19
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsDrillDownByRightsholderController extends
    CommonDrillDownByRightsholderController<INtsDrillDownByRightsholderWidget, INtsDrillDownByRightsholderController>
    implements INtsDrillDownByRightsholderController {

    @Override
    protected INtsDrillDownByRightsholderWidget instantiateWidget() {
        return new NtsDrillDownByRightsholderWidget();
    }
}
