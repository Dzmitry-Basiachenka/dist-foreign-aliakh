package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;

import java.util.List;

/**
 * Interface for scenario audit service.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 12/13/2017
 *
 * @author Uladzislau_Shalamitski
 */
public interface IScenarioAuditService {

    /**
     * Logs scenario action.
     *
     * @param scenarioId   scenario identifier
     * @param actionType   scenario action type
     * @param actionReason action reason
     */
    void logAction(String scenarioId, ScenarioActionTypeEnum actionType, String actionReason);

    /**
     * Deletes scenario actions by scenario identifier.
     *
     * @param scenarioId scenario identifier
     */
    void deleteActions(String scenarioId);

    /**
     * Returns list of scenario actions.
     *
     * @param scenarioId scenario identifier
     * @return list of scenario {@link ScenarioAuditItem}.
     */
    List<ScenarioAuditItem> getActions(String scenarioId);
}
