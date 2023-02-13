package com.copyright.rup.dist.foreign.service.api.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.Set;

/**
 * Represents service interface for SAL specific scenarios business logic.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/24/2020
 *
 * @author Aliaksandr Liakh
 */
public interface ISalScenarioService {

    /**
     * Gets {@link Scenario} name based on SAL fund pool id.
     *
     * @param fundPoolId fund pool id
     * @return {@link Scenario} name or {@code null} if none found
     */
    String getScenarioNameByFundPoolId(String fundPoolId);

    /**
     * Creates scenario and adds usages to it.
     *
     * @param scenarioName name of scenario
     * @param fundPoolId   attached fund pool identifier
     * @param description  description
     * @param usageFilter  instance of {@link UsageFilter} for usages to be added
     * @return {@link Scenario}
     */
    Scenario createScenario(String scenarioName, String fundPoolId, String description, UsageFilter usageFilter);

    /**
     * Deletes {@link Scenario} by given identifier.
     *
     * @param scenario instance of {@link Scenario}
     */
    void deleteScenario(Scenario scenario);

    /**
     * Sends given SAL {@link Scenario} to LM.
     *
     * @param scenario instance of {@link Scenario} to send to LM
     */
    void sendToLm(Scenario scenario);

    /**
     * Change {@link Scenario}s state.
     *
     * @param scenarios set of {@link Scenario}s
     * @param status    new {@link ScenarioStatusEnum} status
     * @param action    action
     * @param reason    reason specified by user
     */
    void changeScenariosState(Set<Scenario> scenarios, ScenarioStatusEnum status, ScenarioActionTypeEnum action,
                              String reason);
}
