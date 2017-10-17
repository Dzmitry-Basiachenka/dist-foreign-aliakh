package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UsageAuditItem;

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
     * Retrieves list of {@link UsageAuditItem}s by {@link com.copyright.rup.dist.foreign.domain.Usage} identifier.
     *
     * @param usageId usage identifier
     * @return list of {@link UsageAuditItem}s
     */
    List<UsageAuditItem> findByUsageId(String usageId);
}
