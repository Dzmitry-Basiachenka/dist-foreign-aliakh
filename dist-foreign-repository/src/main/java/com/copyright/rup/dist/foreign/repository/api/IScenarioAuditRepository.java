package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for scenario audit repository.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 12/13/2017
 *
 * @author Uladzislau_Shalamitski
 */
public interface IScenarioAuditRepository extends Serializable {

    /**
     * Inserts {@link ScenarioAuditItem}.
     *
     * @param auditItem instance of {@link ScenarioAuditItem}
     */
    void insert(ScenarioAuditItem auditItem);

    /**
     * Deletes {@link ScenarioAuditItem}s by given scenario identifier.
     *
     * @param scenarioId scenario identifier
     */
    void deleteByScenarioId(String scenarioId);

    /**
     * Retrieves list of {@link ScenarioAuditItem}s by scenario identifier.
     *
     * @param scenarioId scenario identifier
     * @return list of {@link ScenarioAuditItem}s
     */
    List<ScenarioAuditItem> findByScenarioId(String scenarioId);

    /**
     * Checks whether {@link ScenarioActionTypeEnum} exists for specified scenario id.
     *
     * @param scenarioId scenario identifier
     * @param actionType instance of {@link ScenarioActionTypeEnum}
     * @return {@code true} - if corresponding record exists, {@code false} - otherwise
     */
    boolean isAuditItemExist(String scenarioId, ScenarioActionTypeEnum actionType);
}
