package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;

import java.util.List;

/**
 * Interface for ACL scenario audit service.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/23/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IAclScenarioAuditService {

    /**
     * Logs ACL scenario action.
     *
     * @param scenarioId   scenario id
     * @param actionType   scenario action type
     * @param actionReason action reason
     */
    void logAction(String scenarioId, ScenarioActionTypeEnum actionType, String actionReason);

    /**
     * Returns list of ACL scenario actions.
     *
     * @param scenarioId scenario id
     * @return list of {@link ScenarioAuditItem}s
     */
    List<ScenarioAuditItem> getActions(String scenarioId);
}
