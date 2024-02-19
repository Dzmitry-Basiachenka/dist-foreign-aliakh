package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioAuditRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IAclScenarioAuditRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class AclScenarioAuditRepository extends BaseRepository implements IAclScenarioAuditRepository {

    private static final long serialVersionUID = 7105040532174861053L;

    @Override
    public void insert(ScenarioAuditItem auditItem) {
        insert("IAclScenarioAuditMapper.insert", Objects.requireNonNull(auditItem));
    }

    @Override
    public List<ScenarioAuditItem> findByScenarioId(String scenarioId) {
        return selectList("IAclScenarioAuditMapper.findByScenarioId", Objects.requireNonNull(scenarioId));
    }

    @Override
    public void deleteByScenarioId(String scenarioId) {
        delete("IAclScenarioAuditMapper.deleteByScenarioId", Objects.requireNonNull(scenarioId));
    }
}
