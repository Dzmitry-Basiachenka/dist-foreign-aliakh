package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineValueRepository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IUdmBaselineValueRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/29/2021
 *
 * @author Anton Azarenka
 */
@Repository
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class UdmBaselineValueRepository extends AclBaseRepository implements IUdmBaselineValueRepository {

    @Override
    public List<Integer> findPeriods() {
        return selectList("IUdmBaselineValueMapper.findPeriods");
    }

    @Override
    public List<UdmValueBaselineDto> findDtosByFilter(UdmBaselineValueFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("filter", escapeSqlLikePattern(Objects.requireNonNull(filter)));
        parameters.put("pageable", pageable);
        parameters.put("sort", sort);
        return selectList("IUdmBaselineValueMapper.findDtosByFilter", parameters);
    }

    @Override
    public int findCountByFilter(UdmBaselineValueFilter filter) {
        return selectOne("IUdmBaselineValueMapper.findCountByFilter",
            ImmutableMap.of("filter", escapeSqlLikePattern(Objects.requireNonNull(filter))));
    }

    private UdmBaselineValueFilter escapeSqlLikePattern(UdmBaselineValueFilter udmUsageFilter) {
        UdmBaselineValueFilter filterCopy = new UdmBaselineValueFilter(udmUsageFilter);
        filterCopy.setSystemTitleExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getSystemTitleExpression()));
        filterCopy.setCommentExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getCommentExpression()));
        return filterCopy;
    }
}
