package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;

import java.util.List;

/**
 * Interface for Scenario repository.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Ihar Suvorau
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
public interface IScenarioRepository {

    /**
     * Inserts {@link Scenario} into database.
     *
     * @param scenario {@link Scenario} instance
     */
    void insert(Scenario scenario);

    /**
     * Refreshes {@link Scenario} in database.
     *
     * @param scenario {@link Scenario} instance
     */
    void refresh(Scenario scenario);

    /**
     * Gets {@link Scenario}s count with specified name.
     *
     * @param name {@link Scenario} name
     * @return count of found {@link Scenario}s
     */
    int findCountByName(String name);

    /**
     * @return list of all {@link Scenario}s.
     */
    List<Scenario> findAll();

    /**
     * Finds {@link Scenario}s names associated with any of usages from selected usage batch.
     *
     * @param usageBatchId identifier of usage batch
     * @return found list of {@link Scenario}s names
     */
    List<String> findNamesByUsageBatchId(String usageBatchId);

    /**
     * Removes {@link Scenario} by given identifier.
     *
     * @param scenarioId {@link Scenario} identifier
     */
    void remove(String scenarioId);

    /**
     * Updates {@link Scenario} status.
     *
     * @param scenario scenario to update
     */
    void updateStatus(Scenario scenario);

    /**
     * Updates scenarios status by provided ids.
     *
     * @param scenarioIds list of scenarios ids
     * @param status      {@link ScenarioStatusEnum} instance
     */
    void updateStatus(List<String> scenarioIds, ScenarioStatusEnum status);

    /**
     * Finds all source RROs belonging to the {@link Scenario} with given id.
     *
     * @param scenarioId {@link Scenario} id
     * @return list of source RROs for given scenario
     */
    List<Rightsholder> findSourceRros(String scenarioId);

    /**
     * Gets reported total, gross amount, service fee amount, net amount and last audited action
     * for selected {@link Scenario}.
     *
     * @param scenarioId {@link Scenario} id
     * @return {@link Scenario} with amounts
     */
    Scenario findWithAmountsAndLastAction(String scenarioId);

    /**
     * Gets reported total, gross amount, service fee amount, net amount and last audited action
     * for selected {@link Scenario} base on archived usages.
     *
     * @param scenarioId {@link Scenario} id
     * @return {@link Scenario} with amounts
     */
    Scenario findArchivedWithAmountsAndLastAction(String scenarioId);

    /**
     * Finds all {@link RightsholderPayeePair}s belonging to the source RRO with given account number within the
     * scenario with given id.
     *
     * @param scenarioId       {@link Scenario} id
     * @param rroAccountNumber RRO account number
     * @return list of {@link RightsholderPayeePair}s
     */
    List<RightsholderPayeePair> findRightsholdersByScenarioIdAndSourceRro(String scenarioId, Long rroAccountNumber);

    /**
     * Finds scenarios ids in {@link ScenarioStatusEnum#SENT_TO_LM} containing only usages
     * in {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ARCHIVED} status.
     *
     * @return list of found scenarios ids
     */
    List<String> findIdsForArchiving();
}
