package com.copyright.rup.dist.foreign.ui.scenario.api.acl;

import com.copyright.rup.dist.foreign.domain.AclScenario;

/**
 * Interface provides method for handling ACL scenario actions.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/25/2022
 *
 * @author Anton Azarenka
 */
public interface IAclScenarioActionHandler {

    /**
     * Handles action.
     *
     * @param scenario instance of {@link AclScenario}
     * @param reason   reason of the action
     */
    void handleAction(AclScenario scenario, String reason);
}
