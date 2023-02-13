package com.copyright.rup.dist.foreign.ui.scenario.api.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;

import java.util.Set;

/**
 * Interface provides method for handling SAL scenarios actions.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/09/23
 *
 * @author Mikita Maistrenka
 */
public interface ISalScenarioActionHandler {

    /**
     * Handles action.
     *
     * @param scenarios set of {@link Scenario}s
     * @param reason    reason of the action
     */
    void handleAction(Set<Scenario> scenarios, String reason);
}
