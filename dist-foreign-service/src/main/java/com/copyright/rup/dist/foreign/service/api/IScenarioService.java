package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.List;

/**
 * Interface for scenario service.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/15/17
 *
 * @author Aliaksandr Radkevich
 * @author Ihar Suvorau
 * @author Mikalai Bezmen
 */
public interface IScenarioService {

    /**
     * @return list of {@link Scenario}s.
     */
    List<Scenario> getScenarios();

    /**
     * Checks whether {@link Scenario} with specified name already exists in database.
     *
     * @param scenarioName name of {@link Scenario} to check
     * @return {@code true} if {@link Scenario} with specified name already exists in database,
     * {@code false} - if doesn't
     */
    boolean scenarioExists(String scenarioName);

    /**
     * Gets list of {@link Scenario}s names based on usage batch.
     *
     * @param usageBatchId usage batch id
     * @return list of {@link Scenario}s names
     */
    List<String> getScenariosNamesByUsageBatchId(String usageBatchId);

    /**
     * Gets {@link Scenario} name based on fund pool identifier.
     *
     * @param fundId fund pool identifier
     * @return {@link Scenario} name
     */
    String getScenarioNameByPreServiceFeeFundId(String fundId);

    /**
     * Creates {@link Scenario}. Also calculates gross total and reported total for {@link Scenario}.
     *
     * @param scenarioName name of scenario
     * @param description  description
     * @param usageFilter  instance of {@link UsageFilter}
     * @return {@link Scenario}
     */
    Scenario createScenario(String scenarioName, String description, UsageFilter usageFilter);

    /**
     * Creates NTS scenario and adds usages to it.
     *
     * @param scenarioName name of scenario
     * @param ntsFields    instance of {@link NtsFields}
     * @param description  description
     * @param usageFilter  instance of {@link UsageFilter} for usages to be added
     * @return {@link Scenario}
     */
    Scenario createNtsScenario(String scenarioName, NtsFields ntsFields, String description, UsageFilter usageFilter);

    /**
     * Deletes {@link Scenario} by given identifier.
     *
     * @param scenario instance of {@link Scenario}
     */
    void deleteScenario(Scenario scenario);

    /**
     * Refreshes {@link Scenario} by usage filter, if ones exists.
     *
     * @param scenario instance of {@link Scenario}
     */
    void refreshScenario(Scenario scenario);

    /**
     * Gets reported total, gross amount, service fee amount, net amount and last audit action
     * for selected {@link Scenario}.
     *
     * @param scenario instance of {@link Scenario}
     * @return {@link Scenario} with amounts
     */
    Scenario getScenarioWithAmountsAndLastAction(Scenario scenario);

    /**
     * Gets all source RROs belonging to the {@link Scenario} with given id.
     *
     * @param scenarioId {@link Scenario} id
     * @return list of source RROs for given scenario
     */
    List<Rightsholder> getSourceRros(String scenarioId);

    /**
     * Finds all {@link RightsholderPayeePair}s belonging to the source RRO with given account number within the
     * {@link Scenario} with given id.
     *
     * @param scenarioId       {@link Scenario} id
     * @param rroAccountNumber RRO account number
     * @return list of {@link RightsholderPayeePair}s
     */
    List<RightsholderPayeePair> getRightsholdersByScenarioAndSourceRro(String scenarioId, Long rroAccountNumber);

    /**
     * Submits given {@link Scenario} for approval with provided reason if any.
     *
     * @param scenario {@link Scenario} instance
     * @param reason   reason specified by user
     */
    void submit(Scenario scenario, String reason);

    /**
     * Rejects given {@link Scenario} with provided reason if any.
     *
     * @param scenario {@link Scenario} instance
     * @param reason   reason specified by user
     */
    void reject(Scenario scenario, String reason);

    /**
     * Approves given {@link Scenario} with provided reason if any.
     *
     * @param scenario {@link Scenario} instance
     * @param reason   reason specified by user
     */
    void approve(Scenario scenario, String reason);

    /**
     * Sends given {@link Scenario} to LM.
     *
     * @param scenario {@link Scenario} instance
     */
    void sendToLm(Scenario scenario);

    /**
     * Gets ownership changes for specified {@link Scenario} using RMS service and saves in database.
     *
     * @param scenario {@link Scenario} instance
     */
    void reconcileRightsholders(Scenario scenario);

    /**
     * Updates usages RH and Payee participating flags and amounts for specified {@link Scenario} using PRM service.
     *
     * @param scenario {@link Scenario} instance
     */
    void updateParticipatingAndAmounts(Scenario scenario);

    /**
     * Approves ownership changes for {@link Scenario}.
     *
     * @param scenario instance of {@link Scenario}
     */
    void approveOwnershipChanges(Scenario scenario);

    /**
     * Updates scenario status to {@link com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum#ARCHIVED} if
     * all usages are in {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ARCHIVED} status.
     *
     * @return count of archieved scenarios
     */
    int archiveScenarios();
}
