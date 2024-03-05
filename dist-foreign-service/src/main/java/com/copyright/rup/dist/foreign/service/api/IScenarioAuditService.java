package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Interface for scenario audit service.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 12/13/2017
 *
 * @author Uladzislau_Shalamitski
 */
public interface IScenarioAuditService extends Serializable {

    /**
     * Logs scenario action.
     *
     * @param scenarioId   scenario identifier
     * @param actionType   scenario action type
     * @param actionReason action reason
     */
    void logAction(String scenarioId, ScenarioActionTypeEnum actionType, String actionReason);

    /**
     * Logs scenario action for multiple scenarios based on set of scenario identifiers.
     *
     * @param scenarioIds  scenarios ids
     * @param actionType   {@link ScenarioActionTypeEnum} instance
     * @param actionReason action reason
     */
    void logAction(Set<String> scenarioIds, ScenarioActionTypeEnum actionType, String actionReason);

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
     * @return list of {@link ScenarioAuditItem}s
     */
    List<ScenarioAuditItem> getActions(String scenarioId);

    /**
     * Checks whether {@link ScenarioActionTypeEnum} exists for specified scenario id.
     *
     * @param scenarioId scenario identifier
     * @param actionType instance of {@link ScenarioActionTypeEnum}
     * @return {@code true} - if corresponding record exists, {@code false} - otherwise
     */
    boolean isAuditItemExist(String scenarioId, ScenarioActionTypeEnum actionType);
}
