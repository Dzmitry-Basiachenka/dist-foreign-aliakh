package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;

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
     * Gets count of {@link RightsholderDiscrepancy}ies based on scenario identifier.
     *
     * @param scenarioId the scenario identifier
     * @return count of {@link RightsholderDiscrepancy}ies
     */
    int getInProgressDiscrepanciesCountByScenarioId(String scenarioId);

    /**
     * Gets list of old rightsholder account numbers where new account number is empty for specified scenario.
     *
     * @param scenarioId the scenario identifier
     * @return list of account numbers
     */
    List<Long> getProhibitedAccountNumbers(String scenarioId);

    /**
     * Gets the list of {@link RightsholderDiscrepancy}ies by scenario identifier.
     *
     * @param scenarioId the scenario identifier
     * @param status     instance of {@link RightsholderDiscrepancyStatusEnum}
     * @param pageable   instance of {@link Pageable}
     * @param sort       instance of {@link Sort}
     * @return the list of {@link RightsholderDiscrepancy}ies
     */
    List<RightsholderDiscrepancy> getDiscrepanciesByScenarioIdAndStatus(String scenarioId,
                                                                        RightsholderDiscrepancyStatusEnum status,
                                                                        Pageable pageable, Sort sort);

    /**
     * Deletes all {@link RightsholderDiscrepancy}ies by scenario identifier and status.
     *
     * @param scenarioId the scenario identifier
     * @param status     instance of {@link RightsholderDiscrepancyStatusEnum}
     */
    void deleteDiscrepanciesByScenarioIdAndStatus(String scenarioId, RightsholderDiscrepancyStatusEnum status);

    /**
     * Updates status to {@link RightsholderDiscrepancyStatusEnum#APPROVED} by scenario identifier.
     *
     * @param scenarioId the scenario identifier
     */
    void approveDiscrepanciesByScenarioId(String scenarioId);
}
