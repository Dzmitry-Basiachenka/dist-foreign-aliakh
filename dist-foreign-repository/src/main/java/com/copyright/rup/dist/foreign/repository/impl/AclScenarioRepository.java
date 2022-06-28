package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of {@link IAclScenarioRepository} for MyBatis.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/24/2022
 *
 * @author Dzmitry Basiachenka
 */
@Repository
public class AclScenarioRepository extends BaseRepository implements IAclScenarioRepository {

    @Override
    public List<AclScenario> findAll() {
        return selectList("IAclScenarioMapper.findAll");
    }

    @Override
    public int findCountByName(String name) {
        checkArgument(StringUtils.isNotBlank(name));
        return selectOne("IAclScenarioMapper.findCountByName", name);
    }
}
