package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.vaadin.widget.api.IMediatorProvider;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Scenarios widget interface.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/14/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
public interface IScenariosWidget extends IWidget<IScenariosController>, IRefreshable, IMediatorProvider {

    /**
     * Selects {@link Scenario} with specified id.
     *
     * @param scenarioId scenario id to be selected
     */
    void selectScenario(Object scenarioId);

    /**
     * @return selected {@link Scenario} or {@code null} if no one selected.
     */
    Scenario getSelectedScenario();

    /**
     * Refreshes metadata information for selected {@link Scenario}.
     */
    void refreshSelectedScenario();
}
