package com.copyright.rup.dist.foreign.vui.scenario.api;

import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IController;

import java.util.List;

/**
 * Interface for controller to view scenario audit actions.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 12/15/2017
 *
 * @author Uladziuslau Shalamitski
 */
public interface IScenarioHistoryController extends IController<IScenarioHistoryWidget> {

    /**
     * Gets audit items for scenario.
     *
     * @param scenarioId scenario id
     * @return list of {@link ScenarioAuditItem}s
     */
    List<ScenarioAuditItem> getActions(String scenarioId);
}
