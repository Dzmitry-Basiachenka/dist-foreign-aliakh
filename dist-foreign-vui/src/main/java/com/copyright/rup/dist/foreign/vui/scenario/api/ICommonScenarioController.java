package com.copyright.rup.dist.foreign.vui.scenario.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget.ISearchController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IController;

/**
 * Controller interface for {@link ICommonScenarioWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
public interface ICommonScenarioController extends IController<ICommonScenarioWidget>, ISearchController {

    /**
     * @return current {@link Scenario}.
     */
    Scenario getScenario();

    /**
     * Sets the {@link Scenario}.
     *
     * @param scenario a {@link Scenario} to use
     */
    void setScenario(Scenario scenario);

    /**
     * @return boolean result that shows whether scenario is empty or not.
     */
    boolean isScenarioEmpty();
}
