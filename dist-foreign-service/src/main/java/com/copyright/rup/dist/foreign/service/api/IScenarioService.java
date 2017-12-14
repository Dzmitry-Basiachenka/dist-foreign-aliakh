package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageFilter;

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
     * Creates {@link Scenario}. Also calculates gross total and reported total for {@link Scenario}.
     *
     * @param scenarioName name of scenario
     * @param description  description
     * @param usageFilter  instance of {@link UsageFilter}
     * @return scenario id
     */
    String createScenario(String scenarioName, String description, UsageFilter usageFilter);

    /**
     * Deletes {@link Scenario} by given identifier.
     *
     * @param scenarioId scenario identifier
     */
    void deleteScenario(String scenarioId);

    /**
     * Gets reported total, gross amount, service fee amount and net amount for selected {@link Scenario}.
     *
     * @param scenarioId {@link Scenario} id
     * @return {@link Scenario} with amounts
     */
    Scenario getScenarioWithAmounts(String scenarioId);

    /**
     * Gets all source RROs belonging to the {@link Scenario} with given id.
     *
     * @param scenarioId {@link Scenario} id
     * @return list of source RROs for given scenario
     */
    List<Rightsholder> getSourceRros(String scenarioId);

    /**
     * Finds all {@link RightsholderPayeePair}s belonging to the source RRO with given account number within the
     * scenario with given id.
     *
     * @param scenarioId       {@link Scenario} id
     * @param rroAccountNumber RRO account number
     * @return list of {@link RightsholderPayeePair}s
     */
    List<RightsholderPayeePair> getRightsholdersByScenarioAndSourceRro(String scenarioId, Long rroAccountNumber);

    /**
     * Submits given scenario for approval with provided reason if any.
     *
     * @param scenario scenario instance
     * @param reason   reason specified by user
     */
    void submit(Scenario scenario, String reason);

    /**
     * Rejects given scenario with provided reason if any.
     *
     * @param scenario scenario instance
     * @param reason   reason specified by user
     */
    void reject(Scenario scenario, String reason);

    /**
     * Approves given scenario with provided reason if any.
     *
     * @param scenario scenario instance
     * @param reason   reason specified by user
     */
    void approve(Scenario scenario, String reason);
}
