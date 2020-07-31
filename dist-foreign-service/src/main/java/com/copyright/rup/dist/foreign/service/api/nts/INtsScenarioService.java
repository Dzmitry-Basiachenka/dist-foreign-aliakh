package com.copyright.rup.dist.foreign.service.api.nts;

import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.List;

/**
 * Represents service interface for NTS specific scenario business logic.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/18/2020
 *
 * @author Ihar Suvorau
 */
public interface INtsScenarioService {

    /**
     * Creates scenario and adds usages to it.
     *
     * @param scenarioName name of scenario
     * @param ntsFields    instance of {@link NtsFields}
     * @param description  description
     * @param usageFilter  instance of {@link UsageFilter} for usages to be added
     * @return {@link Scenario}
     */
    Scenario createScenario(String scenarioName, NtsFields ntsFields, String description, UsageFilter usageFilter);

    /**
     * Deletes {@link Scenario} by given identifier.
     *
     * @param scenario instance of {@link Scenario}
     */
    void deleteScenario(Scenario scenario);

    /**
     * Gets {@link Scenario} name based on fund pool id.
     *
     * @param fundPoolId fund pool id
     * @return {@link Scenario} name or {@code null} if none found
     */
    String getScenarioNameByFundPoolId(String fundPoolId);

    /**
     * Sends given {@link Scenario} to LM.
     *
     * @param scenario {@link Scenario} instance
     */
    void sendToLm(Scenario scenario);

    /**
     * Finds all {@link RightsholderPayeePair}s within the {@link Scenario} with given id.
     *
     * @param scenarioId       {@link Scenario} id
     * @return list of {@link RightsholderPayeePair}s
     */
    List<RightsholderPayeePair> getRightsholdersByScenarioId(String scenarioId);
}
