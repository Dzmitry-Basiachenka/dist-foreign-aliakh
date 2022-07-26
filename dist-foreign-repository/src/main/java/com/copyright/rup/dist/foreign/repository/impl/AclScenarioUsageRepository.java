package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetail;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioUsageRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link IAclScenarioUsageRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/22/2022
 *
 * @author Mikita Maistrenka
 */
@Repository
public class AclScenarioUsageRepository extends AclBaseRepository implements IAclScenarioUsageRepository {

    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String SEARCH_VALUE_KEY = "searchValue";
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";
    private static final String UPDATE_USER = "updateUser";
    private static final String CREATE_USER = "createUser";

    @Override
    public void addToAclScenario(AclScenario scenario, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put("scenario", Objects.requireNonNull(scenario));
        params.put(CREATE_USER, Objects.requireNonNull(userName));
        params.put(UPDATE_USER, Objects.requireNonNull(userName));
        insert("IAclScenarioUsageMapper.addToScenario", params);
    }

    @Override
    public void addScenarioShares(AclScenario scenario, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put("scenario", Objects.requireNonNull(scenario));
        params.put(CREATE_USER, Objects.requireNonNull(userName));
        params.put(UPDATE_USER, Objects.requireNonNull(userName));
        insert("IAclScenarioUsageMapper.addScenarioShares", params);
    }

    @Override
    public void populatePubTypeWeights(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(UPDATE_USER, Objects.requireNonNull(userName));
        update("IAclScenarioUsageMapper.populatePubTypeWeights", params);
    }

    @Override
    public void calculateScenarioShares(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(UPDATE_USER, Objects.requireNonNull(userName));
        update("IAclScenarioUsageMapper.calculateScenarioShares", params);
    }

    @Override
    public void calculateScenarioAmounts(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(UPDATE_USER, Objects.requireNonNull(userName));
        update("IAclScenarioUsageMapper.calculateScenarioAmounts", params);
    }

    @Override
    public void deleteZeroAmountShares(String scenarioId) {
        delete("IAclScenarioUsageMapper.deleteZeroAmountShares", Objects.requireNonNull(scenarioId));
    }

    @Override
    public void deleteZeroAmountUsages(String scenarioId) {
        delete("IAclScenarioUsageMapper.deleteZeroAmountUsages", Objects.requireNonNull(scenarioId));
    }

    @Override
    public List<AclScenarioDetail> findScenarioDetailsByScenarioId(String scenarioId) {
        return selectList("IAclScenarioUsageMapper.findScenarioDetailsByScenarioId", scenarioId);
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
        return selectList("IAclScenarioUsageMapper.findAclRightsholderTotalsHoldersByScenarioId", parameters);
    }

    @Override
    public int findAclRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        return selectOne("IAclScenarioUsageMapper.findAclRightsholderTotalsHolderCountByScenarioId", parameters);
    }
}
