package com.copyright.rup.dist.foreign.service.api.aacl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PayeeAccountAggregateLicenseeClassesPair;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IPaidUsageService;

import java.util.List;
import java.util.Set;

/**
 * Represents service interface for AACL specific usages business logic.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/22/2020
 *
 * @author Ihar Suvorau
 */
public interface IAaclUsageService extends IPaidUsageService {

    /**
     * Inserts AACL usages.
     *
     * @param usageBatch usage batch
     * @param usages     list of {@link Usage}s
     */
    void insertUsages(UsageBatch usageBatch, List<Usage> usages);

    /**
     * Inserts AACL usages from baseline.
     *
     * @param usageBatch usage batch
     * @return list of ids of inserted usages
     */
    List<String> insertUsagesFromBaseline(UsageBatch usageBatch);

    /**
     * Updates classified AACL usages.
     *
     * @param usages list of {@link AaclClassifiedUsage}s
     * @return count of updated usages
     */
    int updateClassifiedUsages(List<AaclClassifiedUsage> usages);

    /**
     * Gets list of {@link Usage}s by specified {@link Usage} ids.
     *
     * @param usageIds list of {@link Usage} ids
     * @return list of {@link Usage}s
     */
    List<Usage> getUsagesByIds(List<String> usageIds);

    /**
     * Gets list of AACL {@link UsageDto}s based on applied filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Updates AACL {@link Usage} and verifies that version of {@link Usage} is the same as in database.
     * Throws an {@link com.copyright.rup.dist.foreign.service.impl.InconsistentUsageStateException} in case of
     * there are {@link Usage} version discrepancies.
     *
     * @param usage {@link Usage} to update
     */
    void updateProcessedUsage(Usage usage);

    /**
     * Gets AACL usages count based on applied filter.
     *
     * @param filter instance of {@link UsageFilter}.
     * @return count of usages
     */
    int getUsagesCount(UsageFilter filter);

    /**
     * Gets list of AACL usage periods.
     *
     * @return list of AACL usage periods
     */
    List<Integer> getUsagePeriods();

    /**
     * Gets list of default {@link UsageAge}s by list of periods.
     *
     * @param periods list of periods
     * @return list of default {@link UsageAge}s
     */
    List<UsageAge> getDefaultUsageAges(List<Integer> periods);

    /**
     * Gets list of {@link UsageAge}s by {@link UsageFilter}.
     *
     * @param filter {@link UsageFilter} instance
     * @return list of found {@link UsageAge}s
     */
    List<UsageAge> getUsageAges(UsageFilter filter);

    /**
     * Deletes AACL {@link Usage} with given id.
     *
     * @param usageId usage identifier
     */
    void deleteById(String usageId);

    /**
     * Verifies whether all usages found by defined {@link UsageFilter} have specified status or not.
     *
     * @param filter {@link UsageFilter} instance
     * @param status {@link UsageStatusEnum} instance
     * @return {@code true} - if all filtered usages have specified {@link UsageStatusEnum},
     * {@code false} - otherwise
     */
    boolean isValidFilteredUsageStatus(UsageFilter filter, UsageStatusEnum status);

    /**
     * Verifies whether all filtered {@link Usage}s are eligible for sending for classification.
     *
     * @param filter {@link UsageFilter} instance
     * @return {@code true} if all filtered usages aren't baseline and have RH_FOUND status, {@code false} - otherwise
     */
    boolean isValidForClassification(UsageFilter filter);

    /**
     * Gets rightsholders account numbers that are not presented in database based on {@link UsageFilter}.
     *
     * @param filter instance of {@link UsageFilter}
     * @return list of rightsholders account numbers
     */
    List<Long> getInvalidRightsholdersByFilter(UsageFilter filter);

    /**
     * Calculates gross_amount, service_fee, service_fee_amount and net_amount for all scenario usages.
     *
     * @param scenarioId scenario identifier
     * @param userName   user name
     */
    void calculateAmounts(String scenarioId, String userName);

    /**
     * Deletes {@link com.copyright.rup.dist.foreign.domain.Usage}s from scenario with zero gross amounts.
     *
     * @param scenarioId scenario identifier
     * @param userName   user name
     */
    void excludeZeroAmountUsages(String scenarioId, String userName);

    /**
     * Deletes all {@link Usage}s associated with the given AACL {@link UsageBatch}.
     *
     * @param usageBatch {@link UsageBatch} to delete usages from
     */
    void deleteUsageBatchDetails(UsageBatch usageBatch);

    /**
     * Finds AACL usages by their ids and sends them on queue for PI matching process.
     *
     * @param usageIds  list of usages ids
     * @param batchName batch name
     */
    void sendForMatching(List<String> usageIds, String batchName);

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
     * Gets count of items by filter.
     *
     * @param filter {@link AuditFilter}
     * @return count of items by filter
     */
    int getCountForAudit(AuditFilter filter);

    /**
     * Adds usages to the scenario.
     *
     * @param scenario {@link Scenario} to add usages to
     * @param filter   {@link UsageFilter} instance
     */
    void addUsagesToScenario(Scenario scenario, UsageFilter filter);

    /**
     * Gets rollups from PRM service and populates for specified scenario.
     *
     * @param scenarioId scenario identifier
     */
    void populatePayees(String scenarioId);

    /**
     * Gets aggregate licensee classes that have money that can't be distributed.
     *
     * @param fundPoolId fund pool id
     * @param filter     {@link UsageFilter} instance
     * @param mapping    {@link DetailLicenseeClass} to {@link AggregateLicenseeClass} mapping
     * @return list of {@link AggregateLicenseeClass}es
     */
    List<AggregateLicenseeClass> getAggregateClassesNotToBeDistributed(String fundPoolId, UsageFilter filter,
                                                                       List<DetailLicenseeClass> mapping);

    /**
     * Gets {@link PayeeAccountAggregateLicenseeClassesPair}s by scenario identifier.
     *
     * @param scenarioId {@link com.copyright.rup.dist.foreign.domain.Scenario} id
     * @return list of {@link PayeeAccountAggregateLicenseeClassesPair}s
     */
    List<PayeeAccountAggregateLicenseeClassesPair> getPayeeAggClassesPairsByScenarioId(String scenarioId);

    /**
     * Deletes {@link com.copyright.rup.dist.foreign.domain.Usage}s from scenario.
     * Recalculates amounts within each Aggregate licensee class for excluded payee account numbers.
     * Marks usages as {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#SCENARIO_EXCLUDED},
     * sets scenario id, payee account number, amounts to zero for usages with payees from given list of account numbers
     * in given set of scenarios.
     *
     * @param scenarioId     {@link Scenario} identifier
     * @param accountNumbers set of payees' account numbers
     * @param reason         reason provided by user
     */
    void excludeDetailsFromScenarioByPayees(String scenarioId, Set<Long> accountNumbers, String reason);

    /**
     * Gets list of {@link PayeeTotalHolder}s by filter.
     *
     * @param filter instance of {@link ExcludePayeeFilter}
     * @return list of {@link PayeeTotalHolder}s
     */
    List<PayeeTotalHolder> getPayeeTotalHoldersByFilter(ExcludePayeeFilter filter);

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
     * Deletes usages from AACL scenario. Reverts status of usages to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE}, sets scenario id to {@code null},
     * sets amounts values to 0, sets shares and weights values to {@code null}.
     *
     * @param scenarioId scenario id
     */
    void deleteFromScenario(String scenarioId);

    /**
     * Moves AACL {@link com.copyright.rup.dist.foreign.domain.Usage}s to the archive for given {@link Scenario}.
     *
     * @param scenario {@link Scenario}
     * @return list of moved to archive {@link com.copyright.rup.dist.foreign.domain.Usage}s ids
     */
    List<String> moveToArchive(Scenario scenario);
}
