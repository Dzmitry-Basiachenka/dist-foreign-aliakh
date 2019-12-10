package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.ui.scenario.api.IFasDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IFasDrillDownByRightsholderWidget;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IFasDrillDownByRightsholderController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/9/19
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasDrillDownByRightsholderController extends
    CommonDrillDownByRightsholderController<IFasDrillDownByRightsholderWidget, IFasDrillDownByRightsholderController>
    implements IFasDrillDownByRightsholderController {

    @Override
    protected IFasDrillDownByRightsholderWidget instantiateWidget() {
        return new FasDrillDownByRightsholderWidget();
    }
}
