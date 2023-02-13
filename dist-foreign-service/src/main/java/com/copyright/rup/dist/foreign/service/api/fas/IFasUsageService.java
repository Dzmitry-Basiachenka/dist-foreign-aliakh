package com.copyright.rup.dist.foreign.service.api.fas;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.List;
import java.util.Map;
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
     * Inserts usages.
     *
     * @param usageBatch usage batch
     * @param usages     list of {@link Usage}s
     * @return count of inserted usages
     */
    int insertUsages(UsageBatch usageBatch, List<Usage> usages);

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
     * net amount to 0 for usages with payees from given list of account numbers in given set of scenarios.
     *
     * @param scenarioIds    set of {@link Scenario} identifier
     * @param accountNumbers set of payees' account numbers
     * @param reason         reason provided by user
     */
    void deleteFromScenarioByPayees(Set<String> scenarioIds, Set<Long> accountNumbers, String reason);

    /**
     * Redesignates {@link com.copyright.rup.dist.foreign.domain.Usage}s.
     * Sets status of {@link com.copyright.rup.dist.foreign.domain.Usage}s to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#NTS_WITHDRAWN}, sets product family to NTS,
     * sets scenario id, payee account number, service fee to {@code null}, sets rh and payee participating flags
     * to {@code false}, service fee amount and net amount to 0 for usages with payees from given list of account
     * numbers and in given set of scenarios.
     *
     * @param scenarioIds    set of {@link Scenario} identifier
     * @param accountNumbers set of payees' account numbers
     * @param reason         reason provided by user
     */
    void redesignateToNtsWithdrawnByPayees(Set<String> scenarioIds, Set<Long> accountNumbers, String reason);

    /**
     * Gets set of payee account numbers that are invalid for exclude due to different payee participating flag.
     *
     * @param scenarioIds    set of scenarios identifier
     * @param accountNumbers set of payees' account numbers
     * @return set of invalid payees
     */
    Set<Long> getAccountNumbersInvalidForExclude(Set<String> scenarioIds, Set<Long> accountNumbers);

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

    /**
     * Gets the {@link Usage}s based on {@link UsageFilter}.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the list of {@link Usage}s only with information about gross amount, net amount, reported value and
     * rightsholder
     */
    List<Usage> getUsagesWithAmounts(UsageFilter filter);

    /**
     * Updates {@link Scenario} id, updated user name and status to 'LOCKED' for {@link Usage}s.
     *
     * @param usages   list of {@link Usage}s
     * @param scenario {@link Scenario}
     */
    void addUsagesToScenario(List<Usage> usages, Scenario scenario);

    /**
     * Updates RH account number, payee account number, net amount, service fee amount and
     * RH and Payee participating flags for {@link Usage}s.
     *
     * @param usages list of {@link Usage}s
     */
    void updateRhPayeeAmountsAndParticipating(List<Usage> usages);

    /**
     * Gets the {@link Usage}s based on {@link UsageFilter}, recalculates amounts and add to scenario.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param scenario instance of {@link Scenario}
     */
    void recalculateUsagesForRefresh(UsageFilter filter, Scenario scenario);

    /**
     * Updates under minimum usages grouped by Wr Wrk Inst in
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#RH_NOT_FOUND} status.
     * Sets {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#NTS_WITHDRAWN} status.
     *
     * @return updated usages ids
     */
    List<String> updateNtsWithdrawnUsagesAndGetIds();

    /**
     * Gets the {@link Usage}s for reconcile based on {@link Scenario} identifier.
     *
     * @param scenarioId identifier of {@link Scenario}
     * @return the list of {@link Usage}s
     */
    List<Usage> getUsagesForReconcile(String scenarioId);

    /**
     * Finds rightsholder information based on scenario identifier.
     *
     * @param scenarioId scenario id
     * @return map where key is rightsholder account number, value is {@link Usage} with rightsholder, participating
     * status and payee account number
     */
    Map<Long, Usage> getRightsholdersInformation(String scenarioId);

    /**
     * @return CLA account number.
     */
    Long getClaAccountNumber();

    /**
     * Gets records threshold.
     *
     * @return records threshold
     */
    int getRecordsThreshold();
}
