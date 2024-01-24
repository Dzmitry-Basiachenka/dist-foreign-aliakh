package com.copyright.rup.dist.foreign.vui.scenario.api;

import com.copyright.rup.dist.foreign.domain.Scenario;

/**
 * Interface provides method for handling scenario actions.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 12/13/17
 *
 * @author Ihar Suvorau
 */
public interface IActionHandler {

    /**
     * Handles action.
     *
     * @param scenario instance of {@link Scenario}
     * @param reason   reason of the action
     */
    void handleAction(Scenario scenario, String reason);
}
