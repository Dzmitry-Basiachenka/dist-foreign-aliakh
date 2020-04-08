package com.copyright.rup.dist.foreign.service.api.aacl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

/**
 * Represents service interface for AACL specific scenarios business logic.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/08/2020
 *
 * @author Anton Azarenka
 */
public interface IAaclScenarioService {

    /**
     * Deletes {@link Scenario} by given identifier.
     *
     * @param scenario instance of {@link Scenario}
     */
    void deleteScenario(Scenario scenario);

    /**
     * Creates AACL scenario and adds usages to it.
     *
     * @param scenarioName name of scenario
     * @param aaclFields   instance of {@link AaclFields}
     * @param description  description
     * @param usageFilter  instance of {@link UsageFilter} for usages to be added
     * @return {@link Scenario}
     */
    Scenario createScenario(String scenarioName, AaclFields aaclFields, String description,
                            UsageFilter usageFilter);
}
