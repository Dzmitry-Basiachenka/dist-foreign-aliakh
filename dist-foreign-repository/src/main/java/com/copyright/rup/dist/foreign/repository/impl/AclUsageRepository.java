package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageRepository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IAclUsageRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class AclUsageRepository extends AclBaseRepository implements IAclUsageRepository {

    @Override
    public List<String> populateAclUsages(String usageBatchId, Set<Integer> periods, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put("usageBatchId", Objects.requireNonNull(usageBatchId));
        parameters.put("periods", Objects.requireNonNull(periods));
        parameters.put("updateUser", Objects.requireNonNull(userName));
        parameters.put("createUser", userName);
        return selectList("IAclUsageMapper.populateAclUsages", parameters);
    }

    @Override
    public void update(AclUsageDto aclUsageDto) {
        //TODO {dbasiachenka} implement
    }

    @Override
    public List<AclUsageDto> findByIds(List<String> usageIds) {
        return selectList("IAclUsageMapper.findByIds", Objects.requireNonNull(usageIds));
    }

    @Override
    public int findCountByFilter(AclUsageFilter filter) {
        return selectOne("IAclUsageMapper.findCountByFilter",
            ImmutableMap.of("filter", escapeSqlLikePattern(Objects.requireNonNull(filter))));
    }

    @Override
    public List<AclUsageDto> findDtosByFilter(AclUsageFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("filter", escapeSqlLikePattern(Objects.requireNonNull(filter)));
        parameters.put("pageable", pageable);
        parameters.put("sort", sort);
        return selectList("IAclUsageMapper.findDtosByFilter", parameters);
    }

    @Override
    public List<Integer> findPeriods() {
        return selectList("IAclUsageMapper.findPeriods");
    }

    private AclUsageFilter escapeSqlLikePattern(AclUsageFilter filter) {
        AclUsageFilter filterCopy = new AclUsageFilter(filter);
        filterCopy.setUsageDetailIdExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getUsageDetailIdExpression()));
        filterCopy.setSystemTitleExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getSystemTitleExpression()));
        filterCopy.setSurveyCountryExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getSurveyCountryExpression()));
        return filterCopy;
    }
}
