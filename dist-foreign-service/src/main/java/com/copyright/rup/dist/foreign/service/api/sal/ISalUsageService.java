package com.copyright.rup.dist.foreign.service.api.sal;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.GradeGroupEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IPaidUsageService;

import java.util.List;
import java.util.Set;

/**
 * Interface for SAL usages service.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Aliaksandr Liakh
 */
public interface ISalUsageService extends IPaidUsageService {

    /**
     * Inserts SAL item bank details.
     *
     * @param usageBatch usage batch
     * @param usages     list of {@link Usage}s
     */
    void insertItemBankDetails(UsageBatch usageBatch, List<Usage> usages);

    /**
     * Inserts SAL usage data details.
     *
     * @param usageBatch usage batch
     * @param usages     list of {@link Usage}s
     * @param userName   user name
     */
    void insertUsageDataDetails(UsageBatch usageBatch, List<Usage> usages, String userName);

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
     * Gets usages count by usage filter.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the count of usages
     */
    int getUsagesCount(UsageFilter filter);

    /**
     * Gets list of {@link UsageDto}s by usage filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds SAL usages by their ids and sends them on queue for PI matching process.
     *
     * @param usageIds  list of usages ids
     * @param batchName batch name
     */
    void sendForMatching(List<String> usageIds, String batchName);

    /**
     * Adds usages to the scenario by filter.
     *
     * @param scenario {@link Scenario} to add usages to
     * @param filter   instance of {@link UsageFilter}
     */
    void addUsagesToScenario(Scenario scenario, UsageFilter filter);

    /**
     * Gets rollups from PRM service and populates for specified scenario.
     *
     * @param scenarioId scenario identifier
     */
    void populatePayees(String scenarioId);

    /**
     * Gets list of {@link Usage}s by specified {@link Usage} ids.
     *
     * @param usageIds list of {@link Usage} ids
     * @return list of {@link Usage}s
     */
    List<Usage> getUsagesByIds(List<String> usageIds);

    /**
     * Gets {@link com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum#IB} detail grade by work portion id.
     *
     * @param workPortionId work portion id
     * @return {@link com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum#IB} detail grade
     */
    String getItemBankDetailGradeByWorkPortionId(String workPortionId);

    /**
     * Checks whether usage details exist in the given batch.
     *
     * @param batchId batch id
     * @return {@code true} - if usage details exist, {@code false} - otherwise
     */
    boolean usageDataExists(String batchId);

    /**
     * Deletes {@link Usage}s with detail type UD.
     *
     * @param usageBatch {@link UsageBatch} to delete usage details
     */
    void deleteUsageData(UsageBatch usageBatch);

    /**
     * Deletes all {@link Usage}s associated with the given SAL {@link UsageBatch}.
     *
     * @param usageBatch {@link UsageBatch} to delete usages from
     */
    void deleteUsageBatchDetails(UsageBatch usageBatch);

    /**
     * Gets grade groups from filtered UD usages.
     *
     * @param filter instance of {@link UsageFilter}
     * @return list of {@link GradeGroupEnum}
     */
    List<GradeGroupEnum> getUsageDataGradeGroups(UsageFilter filter);

    /**
     * Deletes usages from scenario. Updates usages status from
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#LOCKED} to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE}.
     * Sets scenario id to {@code null}, payee account number to {@code null}, amounts to 0.
     *
     * @param scenarioId scenario id
     */
    void deleteFromScenario(String scenarioId);

    /**
     * Gets list of {@link UsageDto}s based on {@link Scenario} and rightsholder account number.
     *
     * @param scenario      a {@link Scenario}
     * @param accountNumber selected rightsholder account number
     * @param searchValue   search value
     * @param pageable      instance of {@link Pageable}
     * @param sort          instance of {@link Sort}
     * @return list of {@link UsageDto}s
     */
    List<UsageDto> getByScenarioAndRhAccountNumber(Scenario scenario, Long accountNumber, String searchValue,
                                                   Pageable pageable, Sort sort);

    /**
     * Gets count of usage details based on {@link Scenario} and rightsholder account number.
     *
     * @param scenario      a {@link Scenario}
     * @param accountNumber selected rightsholder account number
     * @param searchValue   search value
     * @return count of usage details
     */
    int getCountByScenarioAndRhAccountNumber(Scenario scenario, Long accountNumber, String searchValue);

    /**
     * Calculates gross_amount, service_fee, service_fee_amount and net_amount for all scenario usages.
     *
     * @param scenarioId scenario identifier
     * @param userName   user name
     */
    void calculateAmounts(String scenarioId, String userName);

    /**
     * Moves SAL {@link com.copyright.rup.dist.foreign.domain.Usage}s to the archive for given {@link Scenario}.
     *
     * @param scenario {@link Scenario}
     * @return list of moved to archive {@link com.copyright.rup.dist.foreign.domain.Usage}s ids
     */
    List<String> moveToArchive(Scenario scenario);

    /**
     * Updates usage with provided RH account number
     * and {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE} status,
     * writes audit with specified reason.
     *
     * @param usageId         set of usage ids
     * @param rhAccountNumber RH account number
     * @param reason          reason for RH update
     */
    void updateToEligibleWithRhAccountNumber(Set<String> usageId, Long rhAccountNumber, String reason);

    /**
     * Gets count of {@link UsageDto}s by {@link AuditFilter}.
     *
     * @param filter {@link AuditFilter}
     * @return count of items by filter
     */
    int getCountForAudit(AuditFilter filter);

    /**
     * Gets list of {@link UsageDto}s by {@link AuditFilter}.
     *
     * @param filter   {@link AuditFilter}
     * @param pageable {@link Pageable}
     * @param sort     {@link Sort}
     * @return list of {@link UsageDto}s
     */
    List<UsageDto> getForAudit(AuditFilter filter, Pageable pageable, Sort sort);
}
