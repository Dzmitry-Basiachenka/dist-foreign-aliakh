package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.foreign.vui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenarioHistoryWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IScenarioHistoryController}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 12/18/2017
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScenarioHistoryController extends CommonController<IScenarioHistoryWidget>
    implements IScenarioHistoryController {

    private static final long serialVersionUID = -8573518566620993896L;

    @Override
    protected IScenarioHistoryWidget instantiateWidget() {
        return new ScenarioHistoryWidget();
    }
}
