package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;

import java.util.List;

/**
 * Represents interface of service for rightsholder discrepancy business logic.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/19/18
 *
 * @author Ihar Suvorau
 */
public interface IRightsholderDiscrepancyService {

    /**
     * Inserts {@link RightsholderDiscrepancy}ies into database.
     *
     * @param rightsholderDiscrepancies the list of {@link RightsholderDiscrepancy}ies to insert
     * @param scenarioId                the scenario identifier
     */
    void insertDiscrepancies(List<RightsholderDiscrepancy> rightsholderDiscrepancies, String scenarioId);

    /**
     * Gets the list of {@link RightsholderDiscrepancy}ies by scenario identifier.
     *
     * @param scenarioId the scenario identifier
     * @param pageable   instance of {@link Pageable}
     * @param sort       instance of {@link Sort}
     * @return the list of {@link RightsholderDiscrepancy}ies
     */
    List<RightsholderDiscrepancy> getDiscrepanciesByScenarioId(String scenarioId, Pageable pageable, Sort sort);

    /**
     * Deletes all {@link RightsholderDiscrepancy}ies by scenario identifier.
     *
     * @param scenarioId the scenario identifier
     */
    void deleteDiscrepanciesByScenarioId(String scenarioId);
}
