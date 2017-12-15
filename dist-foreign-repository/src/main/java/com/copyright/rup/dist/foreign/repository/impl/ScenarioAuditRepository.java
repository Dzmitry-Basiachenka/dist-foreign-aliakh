package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IScenarioAuditRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
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
}
