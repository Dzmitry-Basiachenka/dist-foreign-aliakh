package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of {@link IScenarioRepository} for MyBatis.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/15/17
 *
 * @author Ihar Suvorau
 * @author Mikalai Bezmen
 */
@Repository
public class ScenarioRepository extends BaseRepository implements IScenarioRepository {

    @Override
    public void insert(Scenario scenario) {
        insert("IScenarioMapper.insert", checkNotNull(scenario));
    }

    @Override
    public int getCountByName(String name) {
        checkArgument(StringUtils.isNotBlank(name));
        return selectOne("IScenarioMapper.getCountByName", name);
    }

    @Override
    public List<Scenario> findAll() {
        return selectList("IScenarioMapper.findAll");
    }

    @Override
    public List<String> findNamesByUsageBatchId(String usageBatchId) {
        checkArgument(StringUtils.isNotBlank(usageBatchId));
        return selectList("IScenarioMapper.findNamesByUsageBatchId", usageBatchId);
    }

    @Override
    public void deleteScenario(String scenarioId) {
        delete("IScenarioMapper.deleteScenario", checkNotNull(scenarioId));
    }

    /**
     * Finds {@link Scenario} by provided identifier.
     *
     * @param id {@link Scenario} identifier
     * @return found {@link Scenario} instance
     */
    Scenario findById(String id) {
        checkArgument(StringUtils.isNotBlank(id));
        return selectOne("IScenarioMapper.findById", id);
    }
}
