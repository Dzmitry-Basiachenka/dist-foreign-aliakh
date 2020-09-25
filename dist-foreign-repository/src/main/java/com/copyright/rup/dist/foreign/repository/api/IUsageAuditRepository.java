package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.report.BatchStatistic;
import com.copyright.rup.dist.foreign.domain.report.UsageStatistic;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for usage audit repository.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/16/2017
 *
 * @author Uladzislau_Shalamitski
 */
public interface IUsageAuditRepository {
    /**
     * Inserts {@link UsageAuditItem}.
     *
     * @param auditItem instance of {@link UsageAuditItem}
     */
    void insert(UsageAuditItem auditItem);

    /**
     * Deletes {@link UsageAuditItem}s by given batch identifier.
     *
     * @param batchId batch identifier
     */
    void deleteByBatchId(String batchId);

    /**
     * Deletes {@link UsageAuditItem}s by given batch identifier and
     * {@link com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum#UD}.
     *
     * @param batchId batch identifier
     */
    void deleteForSalUsageData(String batchId);

    /**
     * Deletes {@link UsageAuditItem}s for archived usages by given batch identifier.
     *
     * @param batchId batch identifier
     */
    void deleteForArchivedByBatchId(String batchId);

    /**
     * Deletes {@link UsageAuditItem}s by given usage identifier.
     *
     * @param usageId usage identifier
     */
    void deleteByUsageId(String usageId);

    /**
     * Deletes {@link UsageAuditItem}s by scenario id.
     *
     * @param scenarioId scenario identifier
     */
    void deleteByScenarioId(String scenarioId);

    /**
     * Deletes SCENARIO_EXCLUDED {@link UsageAuditItem}s by scenario id.
     *
     * @param scenarioId scenario identifier
     */
    void deleteForExcludedByScenarioId(String scenarioId);

    /**
     * Retrieves list of {@link UsageAuditItem}s by {@link com.copyright.rup.dist.foreign.domain.Usage} identifier.
     *
     * @param usageId usage identifier
     * @return list of {@link UsageAuditItem}s
     */
    List<UsageAuditItem> findByUsageId(String usageId);

    /**
     * Finds batches statistic by batch name and date (the date is optional).
     *
     * @param batchName batch name
     * @param date      date to compare with batch audit create date
     * @return list of {@link BatchStatistic}
     */
    List<BatchStatistic> findBatchesStatisticByBatchNameAndDate(String batchName, LocalDate date);

    /**
     * Finds batches statistic by start and end dates.
     *
     * @param dateFrom start date to compare with batch audit create date
     * @param dateTo   end date to compare with batch audit create date
     * @return list of {@link BatchStatistic}
     */
    List<BatchStatistic> findBatchesStatisticByDateFromAndDateTo(LocalDate dateFrom, LocalDate dateTo);

    /**
     * Gets usage statistic.
     *
     * @param usageId usage id
     * @return instance of {@link UsageStatistic}
     */
    UsageStatistic getUsageStatistic(String usageId);
}
