package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetail;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageAge;
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

    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String SEARCH_VALUE_KEY = "searchValue";
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";

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
        insert("IAclUsageMapper.update", Objects.requireNonNull(aclUsageDto));
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
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IAclUsageMapper.findDtosByFilter", parameters);
    }

    @Override
    public List<Integer> findPeriods() {
        return selectList("IAclUsageMapper.findPeriods");
    }

    @Override
    public List<UsageAge> findDefaultUsageAgesWeights() {
        return selectList("IAclUsageMapper.findDefaultUsageAgesWeights");
    }

    @Override
    public List<AclRightsholderTotalsHolder> findAclRightsholderTotalsHoldersByScenarioId(String scenarioId,
                                                                                          String searchValue,
                                                                                          Pageable pageable,
                                                                                          Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IAclUsageMapper.findAclRightsholderTotalsHoldersByScenarioId", parameters);
    }

    @Override
    public int findAclRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        return selectOne("IAclUsageMapper.findAclRightsholderTotalsHolderCountByScenarioId", parameters);
    }

    @Override
    public void addToAclScenario(AclScenario scenario, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put("scenario", Objects.requireNonNull(scenario));
        params.put("createUser", Objects.requireNonNull(userName));
        params.put("updateUser", Objects.requireNonNull(userName));
        insert("IAclUsageMapper.addToScenario", params);
    }

    @Override
    public void addScenarioShares(AclScenario scenario, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put("scenario", Objects.requireNonNull(scenario));
        params.put("createUser", Objects.requireNonNull(userName));
        params.put("updateUser", Objects.requireNonNull(userName));
        insert("IAclUsageMapper.addScenarioShares", params);
    }

    @Override
    public List<AclScenarioDetail> findScenarioDetailsByScenarioId(String scenarioId) {
        return selectList("IAclUsageMapper.findScenarioDetailsByScenarioId", scenarioId);
    }

    @Override
    public int findCountInvalidUsages(String batchId, String grantSetId, Integer distributionPeriod,
                                      List<Integer> periodPriors) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put("batchId", Objects.requireNonNull(batchId));
        params.put("grantSetId", Objects.requireNonNull(grantSetId));
        params.put("periodPriors", Objects.requireNonNull(periodPriors));
        params.put("distributionPeriod", Objects.requireNonNull(distributionPeriod));
        return selectOne("IAclUsageMapper.findCountInvalidUsages", params);
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
