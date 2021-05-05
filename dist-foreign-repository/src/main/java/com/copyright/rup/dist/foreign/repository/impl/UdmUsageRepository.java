package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of UDM usage repository.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/29/21
 *
 * @author Uladzislau Shalamitski
 */
@Repository
public class UdmUsageRepository extends BaseRepository implements IUdmUsageRepository {

    private static final String FILTER_KEY = "filter";
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";

    @Override
    public void insert(UdmUsage udmUsage) {
        insert("IUdmUsageMapper.insert", Objects.requireNonNull(udmUsage));
    }

    @Override
    public boolean isOriginalDetailIdExist(String originalDetailId) {
        return selectOne("IUdmUsageMapper.isOriginalDetailIdExist", Objects.requireNonNull(originalDetailId));
    }

    @Override
    public List<UdmUsageDto> findDtosByFilter(UdmUsageFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUdmUsageMapper.findDtosByFilter", parameters);
    }

    @Override
    public int findCountByFilter(UdmUsageFilter filter) {
        return selectOne("IUdmUsageMapper.findCountByFilter",
            ImmutableMap.of(FILTER_KEY, Objects.requireNonNull(filter)));
    }

    @Override
    public List<UdmUsage> findByIds(List<String> udmBatchIds) {
        return selectList("IUdmUsageMapper.findByIds", Objects.requireNonNull(udmBatchIds));
    }
}
