package com.copyright.rup.dist.foreign.vui.scenario.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;

/**
 * Mediator for {@link ICommonScenariosWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
public interface IScenariosMediator extends IMediator {

    /**
     * Called when selected scenario was changed.
     *
     * @param scenario a selected {@link Scenario} or {@code null} - if no scenario selected
     */
    void selectedScenarioChanged(Scenario scenario);
}
