package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.foreign.domain.AclScenarioDetail;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioUsageArchiveRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link IAclScenarioUsageArchiveRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/31/2022
 *
 * @author Anton Azarenka
 */
@Repository
public class AclScenarioUsageArchiveRepository extends AclBaseRepository implements IAclScenarioUsageArchiveRepository {

    private static final String SCENARIO_ID_KEY = "scenarioId";

    @Override
    public List<String> copyScenarioDetailsToArchiveByScenarioId(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put("createUser", Objects.requireNonNull(userName));
        params.put("updateUser", userName);
        return selectList("IAclScenarioUsageArchiveMapper.copyScenarioDetailsToArchiveByScenarioId", params);
    }

    @Override
    public List<String> copyScenarioSharesToArchiveByScenarioId(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put("createUser", Objects.requireNonNull(userName));
        params.put("updateUser", userName);
        return selectList("IAclScenarioUsageArchiveMapper.copyScenarioSharesToArchiveByScenarioId", params);
    }

    @Override
    public List<AclScenarioDetail> findByScenarioId(String scenarioId) {
        return selectList("IAclScenarioUsageArchiveMapper.findScenarioDetailsByScenarioId", scenarioId);
    }
}
