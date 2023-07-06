package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioAuditRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import io.micrometer.core.annotation.Timed;

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
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class AclScenarioAuditRepository extends BaseRepository implements IAclScenarioAuditRepository {

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
