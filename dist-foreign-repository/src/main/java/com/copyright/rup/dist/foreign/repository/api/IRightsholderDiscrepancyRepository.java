package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;

import java.util.List;

/**
 * Represents interface of repository for {@link RightsholderDiscrepancy}ies.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/18/2018
 *
 * @author Ihar Suvorau
 */
public interface IRightsholderDiscrepancyRepository {

    /**
     * Inserts all passed {@link RightsholderDiscrepancy}ies into database and binds them to scenario.
     *
     * @param rightsholderDiscrepancies the list of {@link RightsholderDiscrepancy}ies to insert
     * @param scenarioId                the scenario identifier to bind with {@link RightsholderDiscrepancy}ies
     */
    void insertAll(List<RightsholderDiscrepancy> rightsholderDiscrepancies, String scenarioId);

    /**
     * Finds the list of {@link RightsholderDiscrepancy}ies by passed scenario identifier.
     *
     * @param scenarioId the scenario identifier
     * @param pageable   instance of {@link Pageable}
     * @param sort       instance of {@link Sort}
     * @return the list of {@link RightsholderDiscrepancy}ies
     */
    List<RightsholderDiscrepancy> findByScenarioId(String scenarioId, Pageable pageable, Sort sort);

    /**
     * Deletes all {@link RightsholderDiscrepancy}ies by scenario identifier.
     *
     * @param scenarioId the scenario identifier
     */
    void deleteByScenarioId(String scenarioId);
}
