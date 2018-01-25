package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;

import java.util.List;

/**
 * Interface for usage audit service.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/16/2017
 *
 * @author Uladzislau_Shalamitski
 * @author Aliaksandr Radkevich
 */
public interface IUsageAuditService {

    /**
     * Logs usage action without {@link Scenario}.
     *
     * @param usageId      usage identifier
     * @param actionType   usage action type
     * @param actionReason action reason
     */
    void logAction(String usageId, UsageActionTypeEnum actionType, String actionReason);

    /**
     * Logs usage action with {@link Scenario}.
     *
     * @param usageId      usage identifier
     * @param scenario     instance of {@link Scenario}
     * @param actionType   usage action type
     * @param actionReason action reason
     */
    void logAction(String usageId, Scenario scenario, UsageActionTypeEnum actionType, String actionReason);

    /**
     * Deletes usage actions by usage identifier.
     *
     * @param usageId usage identifier
     */
    void deleteActions(String usageId);

    /**
     * Gets all actions for usage with given id.
     *
     * @param usageId usage id
     * @return list of {@link UsageAuditItem}
     */
    List<UsageAuditItem> getUsageAudit(String usageId);
}
