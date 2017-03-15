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
    public Scenario findById(String id) {
        checkArgument(StringUtils.isNotBlank(id));
        return selectOne("IScenarioMapper.findById", id);
    }

    @Override
    public List<Scenario> getScenarios() {
        return selectList("IScenarioMapper.getScenarios");
    }
}
