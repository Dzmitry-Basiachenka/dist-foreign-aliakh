package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.List;
import java.util.Map;
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
     * Inserts Usage into database.
     *
     * @param usage {@link Usage} instance
     */
    void insert(Usage usage);

    /**
     * Updates researched usage details.
     *
     * @param researchedUsages list of {@link ResearchedUsage}s
     */
    void updateResearchedUsages(List<ResearchedUsage> researchedUsages);

    /**
     * Deletes {@link Usage}s from scenario.
     * Reverts status of {@link Usage}s to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE},
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
     * Redesignates {@link Usage}s.
     * Sets status of {@link Usage}s to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#NTS_WITHDRAWN},
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

    /**
     * Finds {@link Usage}s for reconcile based on scenario identifier.
     *
     * @param scenarioId scenario identifier
     * @return the list of {@link Usage}
     */
    List<Usage> findForReconcile(String scenarioId);

    /**
     * Finds rightsholder information based on scenario identifier.
     *
     * @param scenarioId scenario id
     * @return map where key is rightsholder account number, value is {@link Usage} with rightsholder, participating
     * status and payee account number
     */
    Map<Long, Usage> findRightsholdersInformation(String scenarioId);

    /**
     * Finds the {@link Usage}s only with information about gross amount, net amount, reported value and rightsholder
     * based on {@link UsageFilter}.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the list of {@link Usage}s only with information about gross amount, net amount and reported value
     */
    List<Usage> findWithAmountsAndRightsholders(UsageFilter filter);
}
