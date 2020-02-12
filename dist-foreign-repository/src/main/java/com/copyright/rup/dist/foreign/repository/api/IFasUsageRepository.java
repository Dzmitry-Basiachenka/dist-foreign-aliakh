package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.ResearchedUsage;

import java.util.List;
import java.util.Set;

/**
 * Interface for FAS usage repository.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 02/11/2020
 *
 * @author Ihar Suvorau
 */
public interface IFasUsageRepository {

    /**
     * Updates researched usage details.
     *
     * @param researchedUsages list of {@link ResearchedUsage}s
     */
    void updateResearchedUsages(List<ResearchedUsage> researchedUsages);

    /**
     * Deletes {@link com.copyright.rup.dist.foreign.domain.Usage}s from scenario.
     * Reverts status of {@link com.copyright.rup.dist.foreign.domain.Usage}s
     * to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE},
     * sets scenario id, payee account number, service fee to {@code null}, sets rh and payee participating flags to
     * {@code false}, service fee amount and net amount to 0 for usages with payees from given list of account numbers.
     *
     * @param scenarioId     {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param accountNumbers set of payees' account numbers
     * @param userName       user name
     * @return set of excluded usages' identifiers
     */
    Set<String> deleteFromScenarioByPayees(String scenarioId, Set<Long> accountNumbers, String userName);

    /**
     * Redesignates {@link com.copyright.rup.dist.foreign.domain.Usage}s.
     * Sets status of {@link com.copyright.rup.dist.foreign.domain.Usage}s
     * to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#NTS_WITHDRAWN},
     * sets product family to NTS, sets scenario id, payee account number, service fee to {@code null},
     * sets rh and payee participating flags to {@code false}, service fee amount and net amount to 0
     * for usages with payees from given list of account numbers and in given scenario.
     *
     * @param scenarioId     {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param accountNumbers set of payees' account numbers
     * @param userName       user name
     * @return set of redesignated usages' identifiers
     */
    Set<String> redesignateToNtsWithdrawnByPayees(String scenarioId, Set<Long> accountNumbers, String userName);
}
