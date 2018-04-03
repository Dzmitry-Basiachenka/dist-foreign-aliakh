package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.common.exception.RupRuntimeException;
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
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;

import java.io.PipedOutputStream;
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
     * Writes usages found by filter into csv output stream.
     *
     * @param filter            instance of {@link UsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes scenario usages into csv output stream.
     *
     * @param scenario          {@link Scenario}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream pipedOutputStream);

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
     * Gets the {@link Usage}s based on {@link Scenario} identifier.
     *
     * @param scenarioId identifier of {@link Scenario}
     * @return the list of {@link Usage}s
     */
    List<Usage> getUsagesByScenarioId(String scenarioId);

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
     * Checks if provided detail id exists in database.
     *
     * @param detailId detail id to search
     * @return {@code true} if detail id is present, {@code false} otherwise
     */
    boolean isDetailIdExists(Long detailId);

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
     * Writes usages found by {@link AuditFilter} into CSV output stream.
     *
     * @param filter            {@link AuditFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     * @throws RupRuntimeException in case when IOException appears during writing report
     */
    void writeAuditCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) throws RupRuntimeException;

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
     * @return list of {@link Usage}s that does not have Wr Wrk Inst.
     */
    List<Usage> getUsagesWithBlankWrWrkInst();

    /**
     * Updates researched usage details.
     *
     * @param researchedUsages collection of {@link ResearchedUsage}s
     */
    void loadResearchedUsages(Collection<ResearchedUsage> researchedUsages);

    /**
     * Finds count of usages by detail id.
     *
     * @param detailId usage detail id
     * @return count of usages
     */
    int findCountByDetailId(Long detailId);

    /**
     * Finds usage status by detail id.
     *
     * @param detailId usage detail id
     * @return usage status
     */
    UsageStatusEnum findStatusByDetailId(Long detailId);
}
