package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.domain.UdmValue;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IUdmBaselineRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/02/21
 *
 * @author Anton Azarenka
 */
@Repository
public class UdmBaselineRepository extends AclBaseRepository implements IUdmBaselineRepository {

    private static final long serialVersionUID = -408453695048346187L;
    private static final String FILTER_KEY = "filter";
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";

    @Override
    public List<UdmBaselineDto> findDtosByFilter(UdmBaselineFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(FILTER_KEY, escapeSqlLikePattern(Objects.requireNonNull(filter)));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUdmBaselineMapper.findDtosByFilter", parameters);
    }

    @Override
    public int findCountByFilter(UdmBaselineFilter filter) {
        return selectOne("IUdmBaselineMapper.findCountByFilter",
            ImmutableMap.of(FILTER_KEY, escapeSqlLikePattern(Objects.requireNonNull(filter))));
    }

    @Override
    public List<Integer> findPeriods() {
        return selectList("IUdmBaselineMapper.findPeriods");
    }

    @Override
    public List<UdmValue> findNotPopulatedValuesFromBaseline(Integer period) {
        return selectList("IUdmBaselineMapper.findNotPopulatedValuesFromBaseline", Objects.requireNonNull(period));
    }

    @Override
    public int populateValueId(Integer period, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("period", Objects.requireNonNull(period));
        parameters.put("updateUser", Objects.requireNonNull(userName));
        return selectOne("IUdmBaselineMapper.populateValueId", parameters);
    }

    @Override
    public void removeUdmUsageFromBaselineById(String udmUsageId) {
        update("IUdmBaselineMapper.removeUdmUsageFromBaselineById", Objects.requireNonNull(udmUsageId));
    }

    @Override
    public Map<Long, String> findWrWrkInstToSystemTitles(Set<Integer> periods) {
        var handler = new WrWrkInstToSystemTitlesResultHandler();
        getTemplate().select("IUdmBaselineMapper.findWrWrkInstToSystemTitleMap", Objects.requireNonNull(periods),
            handler);
        return handler.getWrWrkInstToSystemTitles();
    }

    private UdmBaselineFilter escapeSqlLikePattern(UdmBaselineFilter udmBaselineFilter) {
        UdmBaselineFilter filterCopy = new UdmBaselineFilter(udmBaselineFilter);
        filterCopy.setSystemTitleExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getSystemTitleExpression()));
        filterCopy.setUsageDetailIdExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getUsageDetailIdExpression()));
        filterCopy.setSurveyCountryExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getSurveyCountryExpression()));
        return filterCopy;
    }
}
