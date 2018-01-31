package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.AuditFilter;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvProcessingResult;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.Set;

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
     * Writes errors information into csv output stream.
     *
     * @param csvProcessingResult instance of {@link CsvProcessingResult}
     * @param outputStream        instance of {@link OutputStream}
     */
    void writeErrorsToFile(CsvProcessingResult<Usage> csvProcessingResult, OutputStream outputStream);

    /**
     * Inserts usages.
     *
     * @param usageBatch usage batch
     * @param usages     list of {@link Usage}s
     * @return count of inserted usages
     */
    int insertUsages(UsageBatch usageBatch, List<Usage> usages);

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
     * Updates {@link Scenario} id, updated user name and status to 'LOCKED' for {@link Usage}s.
     *
     * @param usages   list of {@link Usage}s
     * @param scenario {@link Scenario}
     */
    void addUsagesToScenario(List<Usage> usages, Scenario scenario);

    /**
     * Updates RH account number, payee account number, net amount, service fee amount and RH participation flag
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
     * Gets duplicate detail ids of {@link Usage}s which are already presented in database.
     *
     * @param detailIds list of detail ids
     * @return set of duplicate detail ids
     */
    Set<Long> getDuplicateDetailIds(List<Long> detailIds);

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
     * @return list of {@link Usage}s
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
     * Finds list of {@link Usage}s with {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#RH_NOT_FOUND}
     * status and sends Wr Wrk Insts to RMS for rights assignment.
     * Updates {@link Usage}s status to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#SENT_FOR_RA}
     * and writes audit with job id information only after successful sending.
     */
    void sendForRightsAssignment();
}
