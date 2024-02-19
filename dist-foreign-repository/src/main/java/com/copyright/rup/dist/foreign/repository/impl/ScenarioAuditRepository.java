package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IScenarioAuditRepository;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link IScenarioAuditRepository}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/16/2017
 *
 * @author Uladzislau_Shalamitski
 */
@Repository
public class ScenarioAuditRepository extends BaseRepository implements IScenarioAuditRepository {

    private static final long serialVersionUID = 5959131205287186750L;

    @Override
    public void insert(ScenarioAuditItem auditItem) {
        insert("IScenarioAuditMapper.insert", Objects.requireNonNull(auditItem));
    }

    @Override
    public void deleteByScenarioId(String scenarioId) {
        checkArgument(StringUtils.isNotBlank(scenarioId));
        delete("IScenarioAuditMapper.deleteByScenarioId", scenarioId);
    }

    @Override
    public List<ScenarioAuditItem> findByScenarioId(String scenarioId) {
        checkArgument(StringUtils.isNotBlank(scenarioId));
        return selectList("IScenarioAuditMapper.findByScenarioId", scenarioId);
    }

    @Override
    public boolean isAuditItemExist(String scenarioId, ScenarioActionTypeEnum actionType) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("scenarioId", Objects.requireNonNull(scenarioId));
        parameters.put("actionType", Objects.requireNonNull(actionType));
        return selectOne("IScenarioAuditMapper.isAuditItemExist", parameters);
    }
}
