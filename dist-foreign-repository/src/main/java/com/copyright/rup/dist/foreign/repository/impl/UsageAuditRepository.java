package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.report.BatchStatistic;
import com.copyright.rup.dist.foreign.domain.report.UsageStatistic;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
    public void deleteForSalUsageDataByBatchId(String batchId) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("batchId", Objects.requireNonNull(batchId));
        params.put("detailType", SalDetailTypeEnum.UD);
        delete("IUsageAuditMapper.deleteForSalUsageDataByBatchId", params);
    }

    @Override
    public void deleteForSalUsageDataByWorkPortionIds(Set<String> workPortionIds) {
        delete("IUsageAuditMapper.deleteForSalUsageDataByWorkPortionIds", workPortionIds);
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
    public void deleteByScenarioId(String scenarioId) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("scenarioId", Objects.requireNonNull(scenarioId));
        params.put("status", UsageStatusEnum.SCENARIO_EXCLUDED);
        delete("IUsageAuditMapper.deleteByScenarioId", params);
    }

    @Override
    public void deleteForExcludedByScenarioId(String scenarioId) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("scenarioId", Objects.requireNonNull(scenarioId));
        params.put("status", UsageStatusEnum.SCENARIO_EXCLUDED);
        delete("IUsageAuditMapper.deleteForExcludedByScenarioId", params);
    }

    @Override
    public List<UsageAuditItem> findByUsageId(String usageId) {
        return selectList("IUsageAuditMapper.findByUsageId", Objects.requireNonNull(usageId));
    }

    @Override
    public List<BatchStatistic> findBatchesStatisticByBatchNameAndDate(String batchName, LocalDate date) {
        checkArgument(StringUtils.isNotBlank(batchName));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("batchName", batchName);
        parameters.put("date", date);
        return selectList("IUsageAuditMapper.findBatchesStatistic", parameters);
    }

    @Override
    public List<BatchStatistic> findBatchesStatisticByDateFromAndDateTo(LocalDate dateFrom, LocalDate dateTo) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("dateFrom", Objects.requireNonNull(dateFrom));
        parameters.put("dateTo", Objects.requireNonNull(dateTo));
        return selectList("IUsageAuditMapper.findBatchesStatistic", parameters);
    }

    @Override
    public UsageStatistic getUsageStatistic(String usageId) {
        return selectOne("IUsageAuditMapper.getUsageStatistic", Objects.requireNonNull(usageId));
    }
}
