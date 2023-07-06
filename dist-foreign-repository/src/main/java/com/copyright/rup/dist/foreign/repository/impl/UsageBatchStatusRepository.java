package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchStatusRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IUsageBatchStatusRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/10/2021
 *
 * @author Ihar Suvorau
 */
@Repository
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class UsageBatchStatusRepository extends BaseRepository implements IUsageBatchStatusRepository {

    private static final String START_DATE_KEY = "startDate";

    @Override
    public List<UsageBatchStatus> findUsageBatchStatusesFas(Set<String> batchIds) {
        return selectList("IUsageBatchStatusMapper.findUsageBatchStatusesFas", batchIds);
    }

    @Override
    public List<UsageBatchStatus> findUsageBatchStatusesNts(Set<String> batchIds) {
        return selectList("IUsageBatchStatusMapper.findUsageBatchStatusesNts", batchIds);
    }

    @Override
    public List<UsageBatchStatus> findUsageBatchStatusesAacl(Set<String> batchIds) {
        return selectList("IUsageBatchStatusMapper.findUsageBatchStatusesAacl", batchIds);
    }

    @Override
    public List<UsageBatchStatus> findUsageBatchStatusesSal(Set<String> batchIds) {
        return selectList("IUsageBatchStatusMapper.findUsageBatchStatusesSal", batchIds);
    }

    @Override
    public List<UsageBatchStatus> findUsageBatchStatusesUdm(LocalDate startDate) {
        return selectList("IUsageBatchStatusMapper.findUsageBatchStatusesUdm",
            Map.of(START_DATE_KEY, Objects.requireNonNull(startDate)));
    }

    @Override
    public Set<String> findFasUsageBatchIdsEligibleForStatistic(String productFamily, LocalDate startDate) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("productFamily", productFamily);
        params.put(START_DATE_KEY, startDate);
        return new HashSet<>(
            selectList("IUsageBatchStatusMapper.findFasUsageBatchIdsEligibleForStatistic", params));
    }

    @Override
    public Set<String> findUsageBatchIdsEligibleForStatistic(String productFamily, LocalDate startDate) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("productFamily", productFamily);
        params.put(START_DATE_KEY, startDate);
        return new HashSet<>(
            selectList("IUsageBatchStatusMapper.findUsageBatchIdsEligibleForStatistic", params));
    }

    @Override
    public boolean isBatchProcessingCompleted(String batchId, Set<UsageStatusEnum> intermediateStatuses) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("batchId", batchId);
        params.put("statuses", intermediateStatuses);
        return selectOne("IUsageBatchStatusMapper.isBatchProcessingCompleted", params);
    }
}
