package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenarioHistoryWidget;

import com.vaadin.flow.component.dialog.Dialog;

/**
 * Implementation of {@link IScenarioHistoryWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 12/18/2017
 *
 * @author Uladzislau Shalamitski
 */
public class ScenarioHistoryWidget extends Dialog implements IScenarioHistoryWidget, IDateFormatter {

    private static final long serialVersionUID = 5583830395581962221L;

    @Override
    @SuppressWarnings("unchecked")
    public ScenarioHistoryWidget init() {
        //TODO: {dbasiachenka} implement
        return this;
    }

    @Override
    public void populateHistory(Scenario scenario) {
        //TODO: {dbasiachenka} implement
    }

    @Override
    public void setController(IScenarioHistoryController controller) {
        //TODO: {dbasiachenka} implement
    }
}
