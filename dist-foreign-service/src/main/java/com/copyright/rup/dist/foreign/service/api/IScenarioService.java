package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.List;
import java.util.Set;

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
     * Gets list of {@link Scenario}s by product family.
     *
     * @param productFamily product family
     * @return list of {@link Scenario}s
     */
    List<Scenario> getScenarios(String productFamily);

    /**
     * Gets list of {@link Scenario}s by product families and status.
     *
     * @param productFamilies set of product families
     * @param statuses        set of statuses
     * @return list of {@link Scenario}s
     */
    List<Scenario> getScenariosByProductFamiliesAndStatuses(Set<String> productFamilies,
                                                            Set<ScenarioStatusEnum> statuses);

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
     * Creates {@link Scenario}. Also calculates gross total and reported total for {@link Scenario}.
     *
     * @param scenarioName name of scenario
     * @param description  description
     * @param usageFilter  instance of {@link UsageFilter}
     * @return {@link Scenario}
     */
    Scenario createScenario(String scenarioName, String description, UsageFilter usageFilter);

    /**
     * Updates {@link Scenario} name by provided identifier.
     *
     * @param scenarioId {@link Scenario} identifier
     * @param name       new scenario name
     */
    void updateName(String scenarioId, String name);

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
     * Updates scenario status to {@link com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum#ARCHIVED} if
     * all usages are in {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ARCHIVED} status.
     *
     * @return count of archieved scenarios
     */
    int archiveScenarios();
}
