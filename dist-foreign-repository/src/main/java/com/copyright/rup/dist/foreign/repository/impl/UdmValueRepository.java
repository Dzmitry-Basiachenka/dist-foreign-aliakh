package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmValueRepository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link IUdmValueRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/21
 *
 * @author Anton Azarenka
 */
@Repository
public class UdmValueRepository extends BaseRepository implements IUdmValueRepository {

    @Override
    public int findCountByFilter(UdmValueFilter filter) {
        return selectOne("IUdmValueMapper.findCountByFilter",
            ImmutableMap.of("filter", escapeSqlLikePattern(Objects.requireNonNull(filter))));
    }

    @Override
    public List<UdmValueDto> findDtosByFilter(UdmValueFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("filter", escapeSqlLikePattern(Objects.requireNonNull(filter)));
        parameters.put("pageable", pageable);
        parameters.put("sort", sort);
        return selectList("IUdmValueMapper.findDtosByFilter", parameters);
    }

    @Override
    public List<Integer> findPeriods() {
        return selectList("IUdmValueMapper.findPeriods");
    }

    private UdmValueFilter escapeSqlLikePattern(UdmValueFilter udmUsageFilter) {
        UdmValueFilter filterCopy = new UdmValueFilter(udmUsageFilter);
        filterCopy.setComment(escapeSqlLikePattern(filterCopy.getComment()));
        return filterCopy;
    }
}
