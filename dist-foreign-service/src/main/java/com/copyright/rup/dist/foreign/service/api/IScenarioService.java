package com.copyright.rup.dist.foreign.service.api;

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
    boolean isScenarioExists(String scenarioName);

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
     */
    void createScenario(String scenarioName, String description, UsageFilter usageFilter);
}
