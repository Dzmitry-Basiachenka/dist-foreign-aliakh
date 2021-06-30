package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UsageAuditItem;

import java.util.List;

/**
 * Interface for UDM usage audit repository.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmUsageAuditRepository {

    /**
     * Inserts {@link UsageAuditItem}.
     *
     * @param auditItem instance of {@link UsageAuditItem}
     */
    void insert(UsageAuditItem auditItem);

    /**
     * Finds list of {@link UsageAuditItem}s by {@link com.copyright.rup.dist.foreign.domain.UdmUsage} identifier.
     *
     * @param udmUsageId {@link com.copyright.rup.dist.foreign.domain.UdmUsage} id
     * @return list of {@link UsageAuditItem}s
     */
    List<UsageAuditItem> findByUdmUsageId(String udmUsageId);

    /**
     * Deletes {@link UsageAuditItem}s by {@link com.copyright.rup.dist.foreign.domain.UdmBatch} identifier.
     *
     * @param udmBatchId batch identifier
     */
    void deleteByBatchId(String udmBatchId);
}
