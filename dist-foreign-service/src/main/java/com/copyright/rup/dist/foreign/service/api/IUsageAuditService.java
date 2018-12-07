package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.report.UsageBatchStatistic;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
     * Logs usage action.
     *
     * @param usageId      usage identifier
     * @param actionType   usage action type
     * @param actionReason action reason
     */
    void logAction(String usageId, UsageActionTypeEnum actionType, String actionReason);

    /**
     * Logs usage action for multiple usages based on set of usage identifiers.
     *
     * @param usageIds     set of usage identifiers
     * @param actionType   usage action type
     * @param actionReason action reason
     */
    void logAction(Set<String> usageIds, UsageActionTypeEnum actionType, String actionReason);

    /**
     * Deletes usage actions by batch identifier.
     *
     * @param batchId batch identifier
     */
    void deleteActionsByBatchId(String batchId);

    /**
     * Deletes usage actions by usage identifier.
     *
     * @param usageId usage identifier
     */
    void deleteActionsByUsageId(String usageId);

    /**
     * Gets all actions for usage with given id.
     *
     * @param usageId usage id
     * @return list of {@link UsageAuditItem}
     */
    List<UsageAuditItem> getUsageAudit(String usageId);

    /**
     * Gets {@link UsageBatchStatistic} for specified batch name and date.
     * Calculates percentage for each usage statuses.
     *
     * @param batchName batch name
     * @param date      date
     * @return instance of {@link UsageBatchStatistic}
     */
    UsageBatchStatistic getBatchStatistic(String batchName, LocalDate date);
}
