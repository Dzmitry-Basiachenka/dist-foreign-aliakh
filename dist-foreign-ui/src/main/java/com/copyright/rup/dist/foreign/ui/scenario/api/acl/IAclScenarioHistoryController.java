package com.copyright.rup.dist.foreign.ui.scenario.api.acl;

import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for controller to view ACL scenario audit actions.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/30/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IAclScenarioHistoryController extends IController<IAclScenarioHistoryWidget> {

    /**
     * Gets audit items for ACL scenario.
     *
     * @param scenarioId scenario id
     * @return list of {@link ScenarioAuditItem}s
     */
    List<ScenarioAuditItem> getActions(String scenarioId);
}
