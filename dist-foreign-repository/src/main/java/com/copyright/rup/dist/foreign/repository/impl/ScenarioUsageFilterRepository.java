package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IScenarioUsageFilterRepository;

import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IScenarioUsageFilterRepository}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/05/2018
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class ScenarioUsageFilterRepository extends BaseRepository implements IScenarioUsageFilterRepository {

    private static final long serialVersionUID = 7389593674237605447L;
    private static final String CREATE_USER_KEY = "createUser";
    private static final String UPDATE_USER_KEY = "updateUser";

    @Override
    public void insert(ScenarioUsageFilter usageFilter) {
        Objects.requireNonNull(usageFilter);
        checkArgument(StringUtils.isNotBlank(usageFilter.getId()));
        insert("ScenarioUsageFilterMapper.insert", usageFilter);
    }

    @Override
    public void insertRhAccountNumbers(String filterId, Set<Long> rhAccountNumbers) {
        checkArgument(StringUtils.isNotBlank(filterId));
        checkArgument(CollectionUtils.isNotEmpty(rhAccountNumbers));
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put("filterId", filterId);
        rhAccountNumbers.forEach(rhAccountNumber -> {
            params.put("rhAccountNumber", rhAccountNumber);
            params.put(CREATE_USER_KEY, StoredEntity.DEFAULT_USER);
            params.put(UPDATE_USER_KEY, StoredEntity.DEFAULT_USER);
            insert("ScenarioUsageFilterMapper.insertRhAccountNumbers", params);
        });
    }

    @Override
    public void insertUsageBatchesIds(String filterId, Set<String> usageBatchesIds) {
        checkArgument(StringUtils.isNotBlank(filterId));
        checkArgument(CollectionUtils.isNotEmpty(usageBatchesIds));
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put("filterId", filterId);
        usageBatchesIds.forEach(usageBatchesId -> {
            params.put("usageBatchId", usageBatchesId);
            params.put(CREATE_USER_KEY, StoredEntity.DEFAULT_USER);
            params.put(UPDATE_USER_KEY, StoredEntity.DEFAULT_USER);
            insert("ScenarioUsageFilterMapper.insertUsageBatchesIds", params);
        });
    }

    @Override
    public ScenarioUsageFilter findByScenarioId(String scenarioId) {
        checkArgument(StringUtils.isNotBlank(scenarioId));
        return selectOne("ScenarioUsageFilterMapper.findByScenarioId", scenarioId);
    }

    @Override
    public void deleteByScenarioId(String scenarioId) {
        checkArgument(StringUtils.isNotBlank(scenarioId));
        deleteRhAccountNumbersByScenarioId(scenarioId);
        deleteUsageBatchesIdsByScenarioId(scenarioId);
        delete("ScenarioUsageFilterMapper.deleteByScenarioId", scenarioId);
    }

    private void deleteRhAccountNumbersByScenarioId(String scenarioId) {
        checkArgument(StringUtils.isNotBlank(scenarioId));
        delete("ScenarioUsageFilterMapper.deleteRhAccountNumbersByScenarioId", scenarioId);
    }

    private void deleteUsageBatchesIdsByScenarioId(String scenarioId) {
        checkArgument(StringUtils.isNotBlank(scenarioId));
        delete("ScenarioUsageFilterMapper.deleteUsageBatchesIdsByScenarioId", scenarioId);
    }
}
