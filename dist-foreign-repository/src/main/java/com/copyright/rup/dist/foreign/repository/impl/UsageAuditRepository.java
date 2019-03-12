package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.report.BatchStatistic;
import com.copyright.rup.dist.foreign.domain.report.UsageStatistic;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
    public void deleteForArchivedByBatchId(String batchId) {
        delete("IUsageAuditMapper.deleteForArchivedByBatchId", Objects.requireNonNull(batchId));
    }

    @Override
    public void deleteByUsageId(String usageId) {
        delete("IUsageAuditMapper.deleteByUsageId", Objects.requireNonNull(usageId));
    }

    @Override
    public List<UsageAuditItem> findByUsageId(String usageId) {
        return selectList("IUsageAuditMapper.findByUsageId", Objects.requireNonNull(usageId));
    }

    @Override
    public List<BatchStatistic> findBatchesStatistic(String batchName, LocalDate date,
                                                     LocalDate dateFrom, LocalDate dateTo) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put("batchName", batchName);
        parameters.put("date", date);
        parameters.put("dateFrom", dateFrom);
        parameters.put("dateTo", dateTo);
        return selectList("IUsageAuditMapper.findBatchesStatistic", parameters);
    }

    @Override
    public UsageStatistic getUsageStatistic(String usageId) {
        return selectOne("IUsageAuditMapper.getUsageStatistic", Objects.requireNonNull(usageId));
    }
}
