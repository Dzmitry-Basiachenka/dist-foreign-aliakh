package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.vaadin.widget.api.IMediatorProvider;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Common interface for Scenarios widget.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @param <W> type of widget
 * @param <C> type of controller
 * @author Stanislau Rudak
 */
public interface ICommonScenariosWidget<W extends ICommonScenariosWidget<W, C>,
    C extends ICommonScenariosController<W, C>>
    extends IWidget<C>, IRefreshable, IMediatorProvider {

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
