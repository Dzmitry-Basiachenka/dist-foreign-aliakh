package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.AuditFilter;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import java.io.PipedOutputStream;
import java.util.List;
import java.util.Set;

/**
 * Interface for Usage repository.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/02/17
 *
 * @author Darya Baraukova
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
public interface IUsageRepository {

    /**
     * Inserts Usage into database.
     *
     * @param usage {@link Usage} instance
     */
    void insert(Usage usage);

    /**
     * Finds the usage based on applied filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> findByFilter(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds usages count based on applied filter.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the count of usages
     */
    int findCountByFilter(UsageFilter filter);

    /**
     * Finds usages based on scenario identifier.
     *
     * @param scenarioId scenario id
     * @return the list of {@link Usage}
     */
    List<Usage> findByScenarioId(String scenarioId);

    /**
     * Finds usages according to given {@link UsageFilter} and writes them to the output stream in CSV format.
     *
     * @param filter            instance of {@link UsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUsagesCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Finds usages by scenario id and writes them into the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Deletes all {@link Usage}s from the batch with given id.
     *
     * @param batchId {@link com.copyright.rup.dist.foreign.domain.UsageBatch} id
     */
    void deleteUsages(String batchId);

    /**
     * Deletes all {@link Usage}s for specified scenario.
     *
     * @param scenarioId scenario identifier
     */
    void deleteByScenarioId(String scenarioId);

    /**
     * Finds the {@link Usage}s only with information about gross amount, net amount, reported value and rightsholder
     * based on {@link UsageFilter}.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the list of {@link Usage}s only with information about gross amount, net amount and reported value
     */
    List<Usage> findWithAmountsAndRightsholders(UsageFilter filter);

    /**
     * Updates scenario id, updated user name, status to 'LOCKED', payee account number,
     * net amount, service fee amount and RH participation status for {@link Usage}s.
     *
     * @param usages list of {@link Usage}s
     */
    void addToScenario(List<Usage> usages);

    /**
     * Deletes {@link Usage}s from scenario. Reverts status of {@link Usage}s
     * to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE} sets scenario id, payee account
     * number, service fee to {@code null}, sets rh participating flag to {@code false}, service fee amount and net
     * amount to 0.
     *
     * @param scenarioId scenario identifier
     * @param updateUser name of user who performed this action
     */
    void deleteFromScenario(String scenarioId, String updateUser);

    /**
     * Deletes {@link Usage}s from scenario. Reverts status of {@link Usage}s
     * to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE}, sets scenario id, payee account
     * number, service fee to {@code null}, sets rh participating flag to {@code false}, service fee amount and net
     * amount to 0 for usages with given ids.
     *
     * @param usagesIds list of {@link Usage}s ids
     * @param userName  user name
     */
    void deleteFromScenario(List<String> usagesIds, String userName);

    /**
     * Gets duplicate detail ids of {@link Usage}s which are already presented in database.
     *
     * @param detailIds list of detail ids
     * @return set of duplicate detail ids
     */
    Set<Long> findDuplicateDetailIds(List<Long> detailIds);

    /**
     * Gets list of {@link RightsholderTotalsHolder}s based on {@link com.copyright.rup.dist.foreign.domain.Scenario}
     * identifier.
     *
     * @param scenarioId  {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param searchValue search value
     * @param pageable    instance of {@link Pageable}
     * @param sort        instance of {@link Sort}
     * @return list of {@link RightsholderTotalsHolder}s
     */
    List<RightsholderTotalsHolder> findRightsholderTotalsHoldersByScenarioId(String scenarioId, String searchValue,
                                                                             Pageable pageable, Sort sort);

    /**
     * Gets count of {@link RightsholderTotalsHolder}s based on {@link com.copyright.rup.dist.foreign.domain.Scenario}
     * identifier.
     *
     * @param scenarioId  com.copyright.rup.dist.foreign.domain.Scenario identifier
     * @param searchValue search value
     * @return count of {@link RightsholderTotalsHolder}s
     */
    int findRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue);

    /**
     * Gets count of usage details based on {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier and
     * rightsholder account number.
     *
     * @param accountNumber selected rightsholder account number
     * @param scenarioId    {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param searchValue   search value
     * @return count of usage details
     */
    int findCountByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue);

    /**
     * Gets list of {@link UsageDto}s based on {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier and
     * rightsholder account number.
     *
     * @param accountNumber selected rightsholder account number
     * @param scenarioId    {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param searchValue   search value
     * @param pageable      instance of {@link Pageable}
     * @param sort          instance of {@link Sort}
     * @return list of {@link UsageDto}s
     */
    List<UsageDto> findByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue,
                                                      Pageable pageable, Sort sort);

    /**
     * Gets list of {@link Usage}s identifiers based on {@link com.copyright.rup.dist.foreign.domain.Scenario}
     * identifier, RRO account number and list of rightsholder account numbers.
     *
     * @param scenarioId       {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param rroAccountNumber RRO account number
     * @param accountNumbers   list of {@link com.copyright.rup.dist.common.domain.Rightsholder}s account numbers
     * @return list of {@link Usage}s identifiers
     */
    List<String> findIdsByScenarioIdRroAccountNumberRhAccountNumbers(String scenarioId, Long rroAccountNumber,
                                                                     List<Long> accountNumbers);

    /**
     * Finds {@link Usage} by provided detail id.
     *
     * @param detailId usage details id
     * @return found {@link Usage} instance
     */
    Usage findByDetailId(Long detailId);

    /**
     * Finds list of Wr Wrk Insts from non-archived usages by {@link UsageStatusEnum}.
     *
     * @param status {@link UsageStatusEnum} instance
     * @return list of found Wr Wrk Insts
     */
    List<Long> findWrWrkInstsByStatus(UsageStatusEnum status);

    /**
     * Finds count of {@link UsageDto}s by specified {@link AuditFilter}.
     *
     * @param filter {@link AuditFilter}
     * @return count of {@link Usage}s matching filter
     */
    int findCountForAudit(AuditFilter filter);

    /**
     * Finds list of {@link UsageDto}s matching specified filter.
     *
     * @param filter   {@link AuditFilter}
     * @param pageable limit and offset
     * @param sort     sort criteria
     * @return list of {@link Usage}s
     */
    List<UsageDto> findForAudit(AuditFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds usages by given {@link AuditFilter} and writes them to the given {@link PipedOutputStream}.
     *
     * @param filter            filter
     * @param pipedOutputStream stream
     * @throws RupRuntimeException in case when IOException appears during writing report
     */
    void writeAuditCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) throws RupRuntimeException;

    /**
     * Finds list of {@link Usage}s by array of {@link UsageStatusEnum}s.
     *
     * @param statuses the array of {@link UsageStatusEnum}s
     * @return the list of {@link Usage}s
     */
    List<Usage> findByStatuses(UsageStatusEnum... statuses);

    /**
     * Updates status of {@link Usage} based on usage identifier.
     *
     * @param usageId usage identifier
     * @param status  instance of {@link UsageStatusEnum}
     */
    void updateStatus(String usageId, UsageStatusEnum status);

    /**
     * Updates status and RH account number of {@link Usage} based on usage identifier.
     *
     * @param usageId         usage identifier
     * @param status          instance of {@link UsageStatusEnum}
     * @param rhAccountNumber RH account number
     */
    void updateStatusAndRhAccountNumber(String usageId, UsageStatusEnum status, Long rhAccountNumber);
}
