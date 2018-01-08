package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of Usage archive repository.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/08/18
 *
 * @author Ihar Suvorau
 */
@Repository
public class UsageArchiveRepository extends BaseRepository implements IUsageArchiveRepository {

    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";
    private static final String SEARCH_VALUE_KEY = "searchValue";
    private static final String SCENARIO_ID_KEY = "scenarioId";

    @Override
    public void insert(Usage usage) {
        insert("IUsageArchiveMapper.insert", Objects.requireNonNull(usage));
    }

    @Override
    public List<RightsholderTotalsHolder> findRightsholderTotalsHoldersByScenarioId(String scenarioId,
                                                                                    String searchValue,
                                                                                    Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageArchiveMapper.findRightsholderTotalsHoldersByScenarioId", parameters);
    }

    @Override
    public int findRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        return selectOne("IUsageArchiveMapper.findRightsholderTotalsHolderCountByScenarioId", parameters);
    }

    @Override
    public List<UsageDto> findByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber,
                                                             String searchValue, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(5);
        parameters.put("accountNumber", Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageArchiveMapper.findByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public int findCountByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("accountNumber", Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        return selectOne("IUsageArchiveMapper.findCountByScenarioIdAndRhAccountNumber", parameters);
    }
}
