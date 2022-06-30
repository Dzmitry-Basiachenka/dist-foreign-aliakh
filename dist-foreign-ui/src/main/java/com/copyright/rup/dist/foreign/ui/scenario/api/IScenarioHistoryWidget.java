package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for widget to show actions per scenario.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 12/15/2017
 *
 * @author Uladziuslau Shalamitski
 */
public interface IScenarioHistoryWidget extends IWidget<IScenarioHistoryController> {

    /**
     * Populates widget with actions of the scenario.
     *
     * @param scenario {@link Scenario} to show history from.
     */
    void populateHistory(Scenario scenario);
}
