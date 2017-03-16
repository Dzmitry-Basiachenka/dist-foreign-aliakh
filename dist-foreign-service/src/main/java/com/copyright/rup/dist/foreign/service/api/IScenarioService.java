package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.Scenario;

import java.util.List;

/**
 * Interface for scenario service.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/15/17
 *
 * @author Aliaksandr Radkevich
 */
public interface IScenarioService {

    /**
     * @return list of {@link Scenario}s.
     */
    List<Scenario> getScenarios();

    /**
     * Checks whether {@link Scenario} with specified name already exists in database.
     *
     * @param scenarioName name of Scenario to check
     * @return {@code true} if Scenario with specified name already exists in database, {@code false} - if doesn't
     */
    boolean isScenarioExists(String scenarioName);
}
