package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for ACL scenario audit repository.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IAclScenarioAuditRepository extends Serializable {

    /**
     * Inserts {@link ScenarioAuditItem}.
     *
     * @param auditItem instance of {@link ScenarioAuditItem}
     */
    void insert(ScenarioAuditItem auditItem);

    /**
     * Retrieves list of {@link ScenarioAuditItem}s by ACL scenario id.
     *
     * @param scenarioId scenario id
     * @return list of {@link ScenarioAuditItem}s
     */
    List<ScenarioAuditItem> findByScenarioId(String scenarioId);

    /**
     * Deletes {@link ScenarioAuditItem}s by given scenario identifier.
     *
     * @param scenarioId scenario identifier
     */
    void deleteByScenarioId(String scenarioId);
}
