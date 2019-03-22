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
     * Retrieves list of {@link UsageAuditItem}s by {@link com.copyright.rup.dist.foreign.domain.Usage} identifier.
     *
     * @param usageId usage identifier
     * @return list of {@link UsageAuditItem}s
     */
    List<UsageAuditItem> findByUsageId(String usageId);

    /**
     * Finds batches statistic.
     *
     * @param batchName batch name
     * @param date      action date
     * @param dateFrom  action start date
     * @param dateTo    action end date
     * @return list of {@link BatchStatistic}
     */
    List<BatchStatistic> findBatchesStatistic(String batchName, LocalDate date, LocalDate dateFrom, LocalDate dateTo);

    /**
     * Gets usage statistic.
     *
     * @param usageId usage id
     * @return instance of {@link UsageStatistic}
     */
    UsageStatistic getUsageStatistic(String usageId);
}
