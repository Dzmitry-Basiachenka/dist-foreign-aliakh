package com.copyright.rup.dist.foreign.ui.scenario.api.acl;

import com.copyright.rup.dist.foreign.domain.AclScenario;

/**
 * Controller interface for {@link IAclScenarioDetailsByRightsholderWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/17/2022
 *
 * @author Mikita Maistrenka
 */
public interface IAclScenarioDetailsByRightsholderController extends IAclCommonScenarioDetailsController {

    /**
     * Initializes and shows the ACL scenario details widget by rightsholder.
     * Sets selected {@link AclScenario} to the widget.
     *
     * @param accountNumber selected account number
     * @param rhName        rightsholder name
     * @param scenario      selected {@link AclScenario}
     */
    void showWidget(Long accountNumber, String rhName, AclScenario scenario);
}
