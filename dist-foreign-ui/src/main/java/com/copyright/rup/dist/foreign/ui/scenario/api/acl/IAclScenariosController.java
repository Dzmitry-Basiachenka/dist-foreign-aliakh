package com.copyright.rup.dist.foreign.ui.scenario.api.acl;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for ACL scenarios controller.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclScenariosController extends IController<IAclScenariosWidget> {

    /**
     * Gets {@link AclScenario} with calculated amounts and last audit action.
     *
     * @param scenario selected {@link AclScenario}
     * @return scenario {@link AclScenario}
     */
    AclScenario getScenarioWithAmountsAndLastAction(AclScenario scenario);

    /**
     * @return HTML filter representation for selected scenario.
     */
    String getCriteriaHtmlRepresentation();

    /**
     * @return list of all {@link AclScenario}s.
     */
    List<AclScenario> getScenarios();
}
