package com.copyright.rup.dist.foreign.ui.scenario.api.acl;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for widget to view ACL scenario audit actions.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/30/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IAclScenarioHistoryWidget extends IWidget<IAclScenarioHistoryController> {

    /**
     * Populates widget with actions of ACL scenario.
     *
     * @param scenario {@link AclScenario} to show history from
     */
    void populateHistory(AclScenario scenario);
}
