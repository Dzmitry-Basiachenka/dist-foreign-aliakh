package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.GradeGroupEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.List;
import java.util.Set;

/**
 * Interface for SAL usages repository.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Aliaksandr Liakh
 */
public interface ISalUsageRepository {

    /**
     * Inserts SAL item bank detail into database.
     *
     * @param usage {@link Usage} instance
     */
    void insertItemBankDetail(Usage usage);

    /**
     * Inserts SAL usage data detail into database.
     *
     * @param usage {@link Usage} instance
     */
    void insertUsageDataDetail(Usage usage);

    /**
     * Finds list of SAL {@link Usage}s by their ids.
     *
     * @param usageIds list of {@link Usage}s identifiers
     * @return list of {@link Usage}s
     */
    List<Usage> findByIds(List<String> usageIds);

    /**
     * Finds usages count by usage filter.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the count of usages
     */
    int findCountByFilter(UsageFilter filter);

    /**
     * Finds list of {@link UsageDto}s by usage filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> findDtosByFilter(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Checks whether provided Work Portion ID already exists in the system.
     *
     * @param workPortionId work portion id
     * @return {@code true} - if Work Portion ID exists, {@code false} - otherwise
     */
    boolean workPortionIdExists(String workPortionId);

    /**
     * Checks whether provided Work Portion ID exists in the given batch.
     *
     * @param workPortionId work portion id
     * @param batchId       batch id
     * @return {@code true} - if Work Portion ID exists, {@code false} - otherwise
     */
    boolean workPortionIdExists(String workPortionId, String batchId);

    /**
     * Finds {@link com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum#IB} detail grade by work portion id.
     *
     * @param workPortionId work portion id
     * @return {@link com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum#IB} detail grade
     */
    String findItemBankDetailGradeByWorkPortionId(String workPortionId);

    /**
     * Checks whether usage details exists in the given batch.
     *
     * @param batchId batch id
     * @return {@code true} - if usage details exists, {@code false} - otherwise
     */
    boolean usageDataExist(String batchId);

    /**
     * Removes all usage details with detail type UD.
     *
     * @param batchId batch id
     */
    void deleteUsageData(String batchId);

    /**
     * Deletes all {@link Usage}s from the batch with given id.
     *
     * @param batchId {@link com.copyright.rup.dist.foreign.domain.UsageBatch} id
     */
    void deleteByBatchId(String batchId);

    /**
     * Finds grade groups from filtered UD usages.
     *
     * @param filter instance of {@link UsageFilter}
     * @return list of {@link GradeGroupEnum}
     */
    List<GradeGroupEnum> findUsageDataGradeGroups(UsageFilter filter);

    /**
     * Attaches usages to scenario.
     *
     * @param scenarioId id of scenario to add usages to
     * @param filter     {@link UsageFilter} instance
     * @param userName   user name
     */
    void addToScenario(String scenarioId, UsageFilter filter, String userName);

    /**
     * Sets payee account number for usages with given RH account number and scenario id.
     *
     * @param rhAccountNumber    RH account number
     * @param scenarioId         scenario id
     * @param payeeAccountNumber payee account number
     * @param userName           user name
     */
    void updatePayeeByAccountNumber(Long rhAccountNumber, String scenarioId, Long payeeAccountNumber, String userName);

    /**
     * Deletes usages from scenario. Updates usages status from
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#LOCKED} to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE}.
     * Sets scenario id to {@code null}, payee account number to {@code null}, amounts to 0.
     *
     * @param scenarioId scenario id
     * @param userName   user name
     */
    void deleteFromScenario(String scenarioId, String userName);

    /**
     * Finds count of usage details based on {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier and
     * rightsholder account number.
     *
     * @param scenarioId    {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param accountNumber selected rightsholder account number
     * @param searchValue   search value
     * @return count of usage details
     */
    int findCountByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber, String searchValue);

    /**
     * Finds list of {@link UsageDto}s based on {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier and
     * rightsholder account number.
     *
     * @param scenarioId    {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param accountNumber selected rightsholder account number
     * @param searchValue   search value
     * @param pageable      instance of {@link Pageable}
     * @param sort          instance of {@link Sort}
     * @return list of {@link UsageDto}s
     */
    List<UsageDto> findByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber, String searchValue,
                                                      Pageable pageable, Sort sort);

    /**
     * Finds usages based on scenario identifier.
     *
     * @param scenarioId scenario id
     * @return the list of {@link Usage}s
     */
    List<Usage> findByScenarioId(String scenarioId);

    /**
     * Calculates gross_amount, service_fee, service_fee_amount and net_amount for all scenario usages.
     *
     * @param scenarioId scenario identifier
     * @param userName   user name
     */
    void calculateAmounts(String scenarioId, String userName);

    /**
     * Updates {@link Usage} RH account number and status by provided id.
     *
     * @param usageIds        set of usage ids
     * @param rhAccountNumber RH account number
     * @param status          {@link UsageStatusEnum} instance
     * @param userName        user name
     */
    void updateRhAccountNumberAndStatusByIds(Set<String> usageIds, Long rhAccountNumber, UsageStatusEnum status,
                                             String userName);

    /**
     * Finds count of {@link UsageDto}s by {@link AuditFilter}.
     *
     * @param filter {@link AuditFilter}
     * @return count of {@link UsageDto}s matching filter
     */
    int findCountForAudit(AuditFilter filter);

    /**
     * Finds list of {@link UsageDto}s by {@link AuditFilter}.
     *
     * @param filter   {@link AuditFilter}
     * @param pageable limit and offset
     * @param sort     sort criteria
     * @return list of {@link UsageDto}s
     */
    List<UsageDto> findForAudit(AuditFilter filter, Pageable pageable, Sort sort);
}
