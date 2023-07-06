package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclciUsageRepository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IAclciUsageRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/08/2022
 *
 * @author Aliaksandr Liakh
 */
@Repository
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class AclciUsageRepository extends BaseRepository implements IAclciUsageRepository {

    private static final int MAX_VARIABLES_COUNT = 32000;
    private static final String FILTER_KEY = "filter";
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";

    @Override
    public void insert(Usage usage) {
        insert("IAclciUsageMapper.insert", Objects.requireNonNull(usage));
    }

    @Override
    public List<Usage> findByIds(List<String> usageIds) {
        List<Usage> result = new ArrayList<>();
        Iterables.partition(Objects.requireNonNull(usageIds), MAX_VARIABLES_COUNT)
            .forEach(partition -> result.addAll(selectList("IAclciUsageMapper.findByIds", partition)));
        return result;
    }

    @Override
    public int findCountByFilter(UsageFilter filter) {
        return selectOne("IAclciUsageMapper.findCountByFilter",
            ImmutableMap.of(FILTER_KEY, Objects.requireNonNull(filter)));
    }

    @Override
    public List<UsageDto> findDtosByFilter(UsageFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IAclciUsageMapper.findDtosByFilter", parameters);
    }

    @Override
    public void updateToEligibleByIds(Set<String> usageIds, Long rhAccountNumber, Long wrWrkInst, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put("rhAccountNumber", Objects.requireNonNull(rhAccountNumber));
        parameters.put("wrWrkInst", wrWrkInst);
        parameters.put("status", UsageStatusEnum.ELIGIBLE);
        parameters.put("updateUser", Objects.requireNonNull(userName));
        Iterables.partition(Objects.requireNonNull(usageIds), MAX_VARIABLES_COUNT).forEach(partition -> {
            parameters.put("usageIds", partition);
            update("IAclciUsageMapper.updateToEligibleByIds", parameters);
        });
    }

    @Override
    public void deleteByBatchId(String batchId) {
        delete("IAclciUsageMapper.deleteByBatchId", Objects.requireNonNull(batchId));
    }
}
