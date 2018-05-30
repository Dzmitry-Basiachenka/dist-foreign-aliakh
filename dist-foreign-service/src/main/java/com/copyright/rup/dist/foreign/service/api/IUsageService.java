package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Represents interface of service for usage business logic.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/03/17
 *
 * @author Aliaksei Pchelnikau
 * @author Mikalai Bezmen
 * @author Aliaksandr Radkevich
 */
public interface IUsageService {

    /**
     * Gets list of {@link UsageDto}s based on applied filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> getUsages(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Gets usages count based on applied filter.
     *
     * @param filter instance of {@link UsageFilter}.
     * @return count of usages
     */
    int getUsagesCount(UsageFilter filter);

    /**
     * Inserts usages.
     *
     * @param usageBatch usage batch
     * @param usages     list of {@link Usage}s
     * @return count of inserted usages
     */
    int insertUsages(UsageBatch usageBatch, Collection<Usage> usages);

    /**
     * Deletes all {@link Usage}s associated with the given {@link UsageBatch}.
     *
     * @param usageBatch {@link UsageBatch} to delete usages from
     */
    void deleteUsageBatchDetails(UsageBatch usageBatch);

    /**
     * Gets the {@link Usage}s based on {@link UsageFilter}.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the list of {@link Usage}s only with information about gross amount, net amount, reported value and
     * rightsholder
     */
    List<Usage> getUsagesWithAmounts(UsageFilter filter);

    /**
     * Gets rightsholders account numbers that are not presented in database based on {@link UsageFilter}.
     *
     * @param filter instance of {@link UsageFilter}
     * @return list of rightsholders account numbers
     */
    List<Long> getInvalidRightsholdersByFilter(UsageFilter filter);

    /**
     * Gets the {@link Usage}s based on {@link Scenario} identifier.
     *
     * @param scenarioId identifier of {@link Scenario}
     * @return the list of {@link Usage}s
     */
    List<Usage> getUsagesByScenarioId(String scenarioId);

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
     * Gets the {@link Usage}s based on {@link UsageFilter}, recalculates amounts and add to scenario.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param scenario instance of {@link Scenario}
     */
    void recalculateUsagesForRefresh(UsageFilter filter, Scenario scenario);

    /**
     * Updates {@link Scenario} id, updated user name and status to 'LOCKED' for {@link Usage}s.
     *
     * @param usages   list of {@link Usage}s
     * @param scenario {@link Scenario}
     */
    void addUsagesToScenario(List<Usage> usages, Scenario scenario);

    /**
     * Updates RH account number, payee account number, net amount, service fee amount and RH participating flag
     * for {@link Usage}s.
     *
     * @param usages list of {@link Usage}s
     */
    void updateRhPayeeAndAmounts(List<Usage> usages);

    /**
     * Deletes {@link Usage}s from scenario. Reverts status of {@link Usage}s
     * to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE}, sets scenario id, payee account
     * number, service fee to {@code null}, sets rh participating flag to {@code false}, service fee amount and net
     * amount to 0 for given scenario.
     *
     * @param scenarioId scenario identifier
     */
    void deleteFromScenario(String scenarioId);

    /**
     * Deletes {@link Usage}s from scenario. Reverts status of {@link Usage}s
     * to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE}, sets scenario id, payee account
     * number, service fee to {@code null}, sets rh participating flag to {@code false}, service fee amount and net
     * amount to 0 for usages with rightsholders from given list of account numbers for specified RRO.
     *
     * @param scenario         {@link Scenario}
     * @param rroAccountNumber RRO account number
     * @param accountNumbers   list of {@link com.copyright.rup.dist.common.domain.Rightsholder}s account numbers
     * @param reason           reason provided by user
     */
    void deleteFromScenario(Scenario scenario, Long rroAccountNumber, List<Long> accountNumbers, String reason);

    /**
     * Checks if usage with usage id and status exists in database.
     *
     * @param usageId    usage id
     * @param statusEnum status of usage
     * @return {@code true} if detail id is present, {@code false} otherwise
     */
    boolean isUsageIdExists(String usageId, UsageStatusEnum statusEnum);

    /**
     * Moves {@link Usage}s to the archive for given {@link Scenario}.
     *
     * @param scenario {@link Scenario}
     * @return list of moved to archived {@link Usage}s
     */
    List<Usage> moveToArchive(Scenario scenario);

    /**
     * Gets {@link RightsholderTotalsHolder}s based on {@link Scenario}.
     *
     * @param scenario    instance of {@link Scenario}
     * @param searchValue search value
     * @param pageable    instance of {@link Pageable}
     * @param sort        instance of {@link Sort}
     * @return list of {@link RightsholderTotalsHolder}s
     */
    List<RightsholderTotalsHolder> getRightsholderTotalsHoldersByScenario(Scenario scenario, String searchValue,
                                                                          Pageable pageable, Sort sort);

    /**
     * Gets count of {@link RightsholderTotalsHolder}s based on {@link Scenario}.
     *
     * @param scenario    instance of {@link Scenario}
     * @param searchValue search value
     * @return count of {@link RightsholderTotalsHolder}s
     */
    int getRightsholderTotalsHolderCountByScenario(Scenario scenario, String searchValue);

    /**
     * Gets boolean result that shows whether scenario is empty or not.
     *
     * @param scenario instance of {@link Scenario}
     * @return boolean result that shows whether scenario is empty or not.
     */
    boolean isScenarioEmpty(Scenario scenario);

    /**
     * Gets count of usage details based on {@link Scenario} and rightsholder account number.
     *
     * @param accountNumber selected rightsholder account number
     * @param scenario      instance of {@link Scenario}
     * @param searchValue   search value
     * @return count of usage details
     */
    int getCountByScenarioAndRhAccountNumber(Long accountNumber, Scenario scenario, String searchValue);

    /**
     * Gets list of {@link UsageDto}s based on {@link Scenario} and rightsholder account number.
     *
     * @param accountNumber selected rightsholder account number
     * @param scenario      instance of {@link Scenario}
     * @param searchValue   search value
     * @param pageable      instance of {@link Pageable}
     * @param sort          instance of {@link Sort}
     * @return list of {@link UsageDto}s
     */
    List<UsageDto> getByScenarioAndRhAccountNumber(Long accountNumber, Scenario scenario, String searchValue,
                                                   Pageable pageable, Sort sort);

    /**
     * Gets count of items by filter.
     *
     * @param filter {@link AuditFilter}
     * @return count of items by filter
     */
    int getAuditItemsCount(AuditFilter filter);

    /**
     * Gets list of {@link UsageDto}s by filter.
     *
     * @param filter   {@link AuditFilter}
     * @param pageable {@link Pageable}
     * @param sort     {@link Sort}
     * @return list of {@link UsageDto}s
     */
    List<UsageDto> getForAudit(AuditFilter filter, Pageable pageable, Sort sort);

    /**
     * Retrieves list of product families of non archived details.
     *
     * @return list of product families
     */
    List<String> getProductFamilies();

    /**
     * Retrieves list of product families of both archived and non archived details.
     *
     * @return list of product families
     */
    List<String> getProductFamiliesForAudit();

    /**
     * Updates paid infromation for {@link PaidUsage}s
     * and status to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#PAID}.
     *
     * @param usages list of {@link PaidUsage}s to update
     */
    void updatePaidInfo(List<PaidUsage> usages);

    /**
     * @return count of {@link Usage}s with standard number and {@link UsageStatusEnum#NEW} status.
     */
    int getStandardNumbersCount();

    /**
     * @return count of {@link Usage}s without standard number, with title and {@link UsageStatusEnum#NEW} status.
     */
    int getTitlesCount();

    /**
     * Gets list of {@link Usage}s that have standard numbers.
     *
     * @param limit  maximum size of list
     * @param offset number of excluded records
     * @return list of {@link Usage}s
     */
    List<Usage> getUsagesWithStandardNumber(int limit, int offset);

    /**
     * Gets list of {@link Usage}s that have no standard numbers but have titles.
     *
     * @param limit  maximum size of list
     * @param offset number of excluded records
     * @return list of {@link Usage}s
     */
    List<Usage> getUsagesWithTitle(int limit, int offset);

    /**
     * @return list of {@link Usage}s without standard number and title with with {@link UsageStatusEnum#NEW} status.
     */
    List<Usage> getUsagesWithoutStandardNumberAndTitle();

    /**
     * Updates researched usage details.
     *
     * @param researchedUsages collection of {@link ResearchedUsage}s
     */
    void loadResearchedUsages(Collection<ResearchedUsage> researchedUsages);

    /**
     * Gets list of {@link PaidUsage} available for sending to CRM.
     * Sends {@link PaidUsage} to CRM and updates their status to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ARCHIVED}.
     * Also updates scenario status to {@link com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum#ARCHIVED}
     * if all usages from scenario were sent to CRM.
     */
    void sendToCrm();
}
