package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IUsageAuditRepository}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/16/2017
 *
 * @author Uladzislau_Shalamitski
 */
@Repository
public class UsageAuditRepository extends BaseRepository implements IUsageAuditRepository {

    @Override
    public void insert(UsageAuditItem auditItem) {
        insert("IUsageAuditMapper.insert", Objects.requireNonNull(auditItem));
    }

    @Override
    public void deleteByBatchId(String batchId) {
        delete("IUsageAuditMapper.deleteByBatchId", Objects.requireNonNull(batchId));
    }

    @Override
    public List<UsageAuditItem> findByUsageId(String usageId) {
        return selectList("IUsageAuditMapper.findByUsageId", Objects.requireNonNull(usageId));
    }
}
