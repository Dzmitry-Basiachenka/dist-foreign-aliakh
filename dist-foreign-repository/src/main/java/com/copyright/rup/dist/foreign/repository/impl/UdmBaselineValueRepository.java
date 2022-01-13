package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineValueRepository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
public class UdmBaselineValueRepository extends BaseRepository implements IUdmBaselineValueRepository {

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
            setEscapeSqlLikePatternForFilterExpression(filterCopy.getSystemTitleExpression()));
        filterCopy.setComment(escapeSqlLikePattern(filterCopy.getComment()));
        return filterCopy;
    }

    private FilterExpression<String> setEscapeSqlLikePatternForFilterExpression(
        FilterExpression<String> filterExpression) {
        return Objects.nonNull(filterExpression.getOperator())
            ? new FilterExpression<>(filterExpression.getOperator(),
            StringUtils.replaceEach(escapeSqlLikePattern(filterExpression.getFieldFirstValue()),
                new String[]{"'"}, new String[]{"''"}),
            filterExpression.getFieldSecondValue())
            : filterExpression;
    }
}
