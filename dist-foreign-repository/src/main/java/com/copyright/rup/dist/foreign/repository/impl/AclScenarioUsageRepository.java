package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
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
