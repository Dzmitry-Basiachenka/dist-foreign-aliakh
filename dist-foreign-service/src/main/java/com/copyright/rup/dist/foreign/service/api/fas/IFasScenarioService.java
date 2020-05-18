package com.copyright.rup.dist.foreign.service.api.fas;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;

import java.util.List;

/**
 * Represents service interface for FAS specific scenario business logic.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/18/2020
 *
 * @author Ihar Suvorau
 */
public interface IFasScenarioService {

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
    void updateRhPayeeParticipating(Scenario scenario);

    /**
     * Approves ownership changes for {@link Scenario}.
     *
     * @param scenario instance of {@link Scenario}
     */
    void approveOwnershipChanges(Scenario scenario);
}
