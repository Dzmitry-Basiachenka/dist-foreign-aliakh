package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmValue;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmValueRepository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
    public void insert(UdmValue value) {
        insert("IUdmValueMapper.insert", Objects.requireNonNull(value));
    }

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

    @Override
    public void updateAssignee(Set<String> valueIds, String assignee, String updateUser) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("valueIds", Objects.requireNonNull(valueIds));
        parameters.put("updateUser", Objects.requireNonNull(updateUser));
        parameters.put("assignee", assignee);
        update("IUdmValueMapper.updateAssignee", parameters);
    }

    @Override
    public List<String> findAssignees() {
        return selectList("IUdmValueMapper.findAssignees");
    }

    @Override
    public List<String> findLastValuePeriods() {
        return selectList("IUdmValueMapper.findLastValuePeriods");
    }

    @Override
    public boolean isAllowedForPublishing(Integer period) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("period", Objects.requireNonNull(period));
        parameters.put("statuses", Arrays.asList(UdmValueStatusEnum.NEW, UdmValueStatusEnum.RSCHD_IN_THE_PREV_PERIOD));
        return selectOne("IUdmValueMapper.isAllowedForPublishing", parameters);
    }

    @Override
    public int publishToBaseline(Integer period, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("period", Objects.requireNonNull(period));
        parameters.put("updateUser", Objects.requireNonNull(userName));
        parameters.put("createUser", userName);
        return selectOne("IUdmValueMapper.publishToBaseline", parameters);
    }

    private UdmValueFilter escapeSqlLikePattern(UdmValueFilter udmUsageFilter) {
        UdmValueFilter filterCopy = new UdmValueFilter(udmUsageFilter);
        filterCopy.setSystemTitleExpression(
            setEscapeSqlLikePatternForFilterExpression(filterCopy.getSystemTitleExpression()));
        filterCopy.setSystemStandardNumberExpression(
            setEscapeSqlLikePatternForFilterExpression(filterCopy.getSystemStandardNumberExpression()));
        filterCopy.setRhNameExpression(setEscapeSqlLikePatternForFilterExpression(filterCopy.getRhNameExpression()));
        filterCopy.setLastPriceComment(escapeSqlLikePattern(filterCopy.getLastPriceComment()));
        filterCopy.setLastContentComment(escapeSqlLikePattern(filterCopy.getLastContentComment()));
        filterCopy.setComment(escapeSqlLikePattern(filterCopy.getComment()));
        return filterCopy;
    }

    private FilterExpression<String> setEscapeSqlLikePatternForFilterExpression(
        FilterExpression<String> filterExpression) {
        return Objects.nonNull(filterExpression.getOperator())
            && filterExpression.getOperator().equals(FilterOperatorEnum.CONTAINS)
            ? new FilterExpression<>(filterExpression.getOperator(),
            escapeSqlLikePattern(filterExpression.getFieldFirstValue()), filterExpression.getFieldSecondValue())
            : filterExpression;
    }
}
