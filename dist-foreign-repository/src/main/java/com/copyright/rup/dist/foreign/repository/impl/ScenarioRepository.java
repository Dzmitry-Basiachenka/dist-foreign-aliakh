package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link IScenarioRepository} for MyBatis.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
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
    public void refresh(Scenario scenario) {
        update("IScenarioMapper.refresh", Objects.requireNonNull(scenario));
    }

    @Override
    public int findCountByName(String name) {
        checkArgument(StringUtils.isNotBlank(name));
        return selectOne("IScenarioMapper.findCountByName", name);
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
    public void remove(String scenarioId) {
        checkArgument(StringUtils.isNotBlank(scenarioId));
        delete("IScenarioMapper.remove", scenarioId);
    }

    @Override
    public List<Rightsholder> findSourceRros(String scenarioId) {
        return selectList("IScenarioMapper.findSourceRros", scenarioId);
    }

    @Override
    public Scenario findWithAmountsAndLastAction(String scenarioId) {
        return selectOne("IScenarioMapper.findWithAmountsAndLastAction", Objects.requireNonNull(scenarioId));
    }

    @Override
    public Scenario findArchivedWithAmountsAndLastAction(String scenarioId) {
        return selectOne("IScenarioMapper.findArchivedWithAmountsAndLastAction", Objects.requireNonNull(scenarioId));
    }

    @Override
    public void updateStatus(Scenario scenario) {
        update("IScenarioMapper.updateStatus", Objects.requireNonNull(scenario));
    }

    @Override
    public List<RightsholderPayeePair> findRightsholdersByScenarioIdAndSourceRro(String scenarioId,
                                                                                 Long rroAccountNumber) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("scenarioId", Objects.requireNonNull(scenarioId));
        params.put("rroAccountNumber", Objects.requireNonNull(rroAccountNumber));
        return selectList("IScenarioMapper.findRightsholdersByScenarioIdAndSourceRro", params);
    }
}
