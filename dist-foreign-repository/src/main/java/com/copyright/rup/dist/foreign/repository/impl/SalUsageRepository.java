package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.ISalUsageRepository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link ISalUsageRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class SalUsageRepository extends BaseRepository implements ISalUsageRepository {

    private static final int MAX_VARIABLES_COUNT = 32000;

    @Override
    public void insert(Usage usage) {
        insert("ISalUsageMapper.insert", Objects.requireNonNull(usage));
    }

    @Override
    public List<Usage> findByIds(List<String> usageIds) {
        List<Usage> result = new ArrayList<>();
        Iterables.partition(Objects.requireNonNull(usageIds), MAX_VARIABLES_COUNT)
            .forEach(partition -> result.addAll(selectList("ISalUsageMapper.findByIds", partition)));
        return result;
    }

    @Override
    public int findCountByFilter(UsageFilter filter) {
        return selectOne("ISalUsageMapper.findCountByFilter",
            ImmutableMap.of("filter", Objects.requireNonNull(filter)));
    }

    @Override
    public List<UsageDto> findDtosByFilter(UsageFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("filter", Objects.requireNonNull(filter));
        parameters.put("pageable", pageable);
        parameters.put("sort", sort);
        return selectList("ISalUsageMapper.findDtosByFilter", parameters);
    }
}