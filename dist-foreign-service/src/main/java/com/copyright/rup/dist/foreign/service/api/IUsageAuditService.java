package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.report.BatchStatistic;

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
     * Deletes archived usages actions by batch identifier.
     *
     * @param batchId batch identifier
     */
    void deleteActionsForArchivedByBatchId(String batchId);

    /**
     * Deletes usage actions by usage identifier.
     *
     * @param usageId usage identifier
     */
    void deleteActionsByUsageId(String usageId);

    /**
     * Deletes usage actions by scenario id.
     * Audit records for {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#SCENARIO_EXCLUDED} usages
     * that were excluded from specified scenario also will be deleted.
     *
     * @param scenarioId scenario id
     */
    void deleteActionsByScenarioId(String scenarioId);

    /**
     * Deletes usage actions by scenario id for
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#SCENARIO_EXCLUDED} usages only.
     *
     * @param scenarioId scenario id
     */
    void deleteActionsForExcludedByScenarioId(String scenarioId);

    /**
     * Gets all actions for usage with given id.
     *
     * @param usageId usage id
     * @return list of {@link UsageAuditItem}
     */
    List<UsageAuditItem> getUsageAudit(String usageId);

    /**
     * Gets batches statistic by batch name and date (the date is optional).
     *
     * @param batchName batch name
     * @param date      date to compare with batch audit create date
     * @return list of {@link BatchStatistic}
     */
    List<BatchStatistic> getBatchesStatisticByBatchNameAndDate(String batchName, LocalDate date);

    /**
     * Gets batches statistic by start and end dates.
     *
     * @param dateFrom start date to compare with batch audit create date
     * @param dateTo   end date to compare with batch audit create date
     * @return list of {@link BatchStatistic}
     */
    List<BatchStatistic> getBatchesStatisticByDateFromAndDateTo(LocalDate dateFrom, LocalDate dateTo);

    /**
     * Deletes {@link UsageAuditItem}s by given batch identifier and
     * {@link com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum#UD}.
     *
     * @param batchId batch identifier
     */
    void deleteActionsForSalUsageData(String batchId);
}
