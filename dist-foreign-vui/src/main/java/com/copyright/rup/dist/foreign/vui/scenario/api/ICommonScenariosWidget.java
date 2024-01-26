package com.copyright.rup.dist.foreign.vui.scenario.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediatorProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IRefreshable;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

/**
 * Common interface for Scenarios widget.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
public interface ICommonScenariosWidget extends IWidget<ICommonScenariosController>, IRefreshable, IMediatorProvider {

    /**
     * Selects specified {@link Scenario} in grid.
     *
     * @param scenario scenario to select
     */
    void selectScenario(Scenario scenario);

    /**
     * @return selected {@link Scenario} or {@code null} if no one selected.
     */
    Scenario getSelectedScenario();

    /**
     * Refreshes metadata information for selected {@link Scenario}.
     */
    void refreshSelectedScenario();
}
