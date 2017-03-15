package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Controller class for {@link ScenariosWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/14/17
 *
 * @author Aliaksandr Radkevich
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScenariosController extends CommonController<IScenariosWidget> implements IScenariosController {

    @Override
    public List<Scenario> getScenarios() {
        // TODO: {aradkevich} get scenarios from database after implementing service
        return Collections.emptyList();
    }

    @Override
    protected IScenariosWidget instantiateWidget() {
        return new ScenariosWidget();
    }
}
