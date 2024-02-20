package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;

import java.io.Serializable;
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
public interface IRightsholderDiscrepancyRepository extends Serializable {

    /**
     * Inserts given {@link RightsholderDiscrepancy}ies into database and binds them to scenario.
     *
     * @param rightsholderDiscrepancies the list of {@link RightsholderDiscrepancy}ies to insert
     * @param scenarioId                the scenario identifier to bind with {@link RightsholderDiscrepancy}ies
     */
    void insertAll(List<RightsholderDiscrepancy> rightsholderDiscrepancies, String scenarioId);

    /**
     * Finds {@link RightsholderDiscrepancy}ies count based on scenario identifier and status.
     *
     * @param scenarioId the scenario identifier
     * @param status     instance of {@link RightsholderDiscrepancyStatusEnum}
     * @return count of {@link RightsholderDiscrepancy}ies
     */
    int findCountByScenarioIdAndStatus(String scenarioId, RightsholderDiscrepancyStatusEnum status);

    /**
     * Finds list of old rightsholder account numbers where new account number is empty for specified scenario.
     *
     * @param scenarioId the scenario identifier
     * @return list of account numbers
     */
    List<Long> findProhibitedAccountNumbers(String scenarioId);

    /**
     * Finds the list of {@link RightsholderDiscrepancy}ies by passed scenario identifier and status.
     *
     * @param scenarioId the scenario identifier
     * @param status     instance of {@link RightsholderDiscrepancyStatusEnum}
     * @param pageable   instance of {@link Pageable}
     * @param sort       instance of {@link Sort}
     * @return the list of {@link RightsholderDiscrepancy}ies
     */
    List<RightsholderDiscrepancy> findByScenarioIdAndStatus(String scenarioId, RightsholderDiscrepancyStatusEnum status,
                                                            Pageable pageable, Sort sort);

    /**
     * Deletes all {@link RightsholderDiscrepancy}ies by scenario identifier and status.
     *
     * @param scenarioId the scenario identifier
     * @param status     instance of {@link RightsholderDiscrepancyStatusEnum}
     */
    void deleteByScenarioIdAndStatus(String scenarioId, RightsholderDiscrepancyStatusEnum status);

    /**
     * Deletes all {@link RightsholderDiscrepancy}ies by scenario identifier.
     *
     * @param scenarioId the scenario identifier
     */
    void deleteByScenarioId(String scenarioId);

    /**
     * Updates status to {@link RightsholderDiscrepancyStatusEnum#APPROVED} by scenario identifier.
     *
     * @param scenarioId the scenario identifier
     */
    void approveByScenarioId(String scenarioId);
}
