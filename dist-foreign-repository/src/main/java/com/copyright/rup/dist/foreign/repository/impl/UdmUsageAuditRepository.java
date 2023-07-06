package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageAuditRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IUdmUsageAuditRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/2021
 *
 * @author Aliaksandr Liakh
 */
@Repository
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class UdmUsageAuditRepository extends BaseRepository implements IUdmUsageAuditRepository {

    @Override
    public void insert(UsageAuditItem auditItem) {
        insert("IUdmUsageAuditMapper.insert", Objects.requireNonNull(auditItem));
    }

    @Override
    public List<UsageAuditItem> findByUdmUsageId(String udmUsageId) {
        return selectList("IUdmUsageAuditMapper.findByUdmUsageId", Objects.requireNonNull(udmUsageId));
    }

    @Override
    public void deleteByBatchId(String udmBatchId) {
        delete("IUdmUsageAuditMapper.deleteByBatchId", Objects.requireNonNull(udmBatchId));
    }
}
