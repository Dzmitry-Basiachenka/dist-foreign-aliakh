package com.copyright.rup.dist.foreign.service.api.fas;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.List;
import java.util.Set;

/**
 * Represents service interface for FAS specific usages business logic.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/22/2020
 *
 * @author Ihar Suvorau
 */
public interface IFasUsageService {

    /**
     * Gets list of {@link UsageDto}s based on applied filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Gets usages count based on applied filter.
     *
     * @param filter instance of {@link UsageFilter}.
     * @return count of usages
     */
    int getUsagesCount(UsageFilter filter);

    /**
     * Moves FAS {@link com.copyright.rup.dist.foreign.domain.Usage}s to the archive for given {@link Scenario}.
     *
     * @param scenario {@link Scenario}
     * @return list of moved to archive {@link com.copyright.rup.dist.foreign.domain.Usage}s ids
     */
    List<String> moveToArchive(Scenario scenario);

    /**
     * Deletes {@link com.copyright.rup.dist.foreign.domain.Usage}s from scenario.
     * Reverts status of {@link com.copyright.rup.dist.foreign.domain.Usage}s to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE}, sets scenario id, payee account number,
     * service fee to {@code null}, sets rh and payee participating flags to {@code false}, service fee amount and
     * net amount to 0 for usages with payees from given list of account numbers.
     *
     * @param scenarioId     {@link Scenario} identifier
     * @param accountNumbers set of payees' account numbers
     * @param reason         reason provided by user
     */
    void deleteFromScenarioByPayees(String scenarioId, Set<Long> accountNumbers, String reason);

    /**
     * Redesignates {@link com.copyright.rup.dist.foreign.domain.Usage}s.
     * Sets status of {@link com.copyright.rup.dist.foreign.domain.Usage}s to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#NTS_WITHDRAWN}, sets product family to NTS,
     * sets scenario id, payee account number, service fee to {@code null}, sets rh and payee participating flags
     * to {@code false}, service fee amount and net amount to 0 for usages with payees from given list of account
     * numbers and in given scenario.
     *
     * @param scenarioId     {@link Scenario} identifier
     * @param accountNumbers set of payees' account numbers
     * @param reason         reason provided by user
     */
    void redesignateToNtsWithdrawnByPayees(String scenarioId, Set<Long> accountNumbers, String reason);

    /**
     * Updates researched usage details.
     *
     * @param researchedUsages list of {@link ResearchedUsage}s
     */
    void loadResearchedUsages(List<ResearchedUsage> researchedUsages);

    /**
     * Updates researched usage details, sets WORK_FOUND status and adds log action.
     * Is used only by {@link IFasUsageService#loadResearchedUsages(List)}.
     * Due to the fact that default mechanisms of proxying in Spring are Dynamic Proxy and CGLIB,
     * it was implemented as public method to support declarative transaction.
     *
     * @param researchedUsages list of {@link ResearchedUsage}s
     */
    void markAsWorkFound(List<ResearchedUsage> researchedUsages);
}
