package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.PayeeAccountAggregateLicenseeClassesPair;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Interface for AACL usage repository.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/21/2020
 *
 * @author Ihar Suvorau
 */
public interface IAaclUsageRepository {

    /**
     * Inserts AACL usage into database.
     *
     * @param usage {@link Usage} instance
     */
    void insert(Usage usage);

    /**
     * Inserts AACL usage from baseline.
     *
     * @param periods  baseline usage periods
     * @param batchId  AACL {@link com.copyright.rup.dist.foreign.domain.UsageBatch} id to add created usages to
     * @param userName name of user
     * @return list of inserted {@link Usage} ids
     */
    List<String> insertFromBaseline(Set<Integer> periods, String batchId, String userName);

    /**
     * Updates classified fields for AACL usages in database.
     *
     * @param usages   {@link AaclClassifiedUsage} instance
     * @param userName name of user
     */
    void updateClassifiedUsages(List<AaclClassifiedUsage> usages, String userName);

    /**
     * Deletes AACL {@link Usage} by given id.
     *
     * @param usageId usage identifier
     */
    void deleteById(String usageId);

    /**
     * Updates given AACL {@link Usage} in case of its version is the same as in database.
     *
     * @param usage {@link Usage} to update
     * @return id of updated record, otherwise {@code null}
     */
    String updateProcessedUsage(Usage usage);

    /**
     * Finds baseline periods.
     *
     * @param startPeriod           period to start from
     * @param numberOfBaselineYears number of baseline years
     * @return set of baseline periods
     */
    Set<Integer> findBaselinePeriods(int startPeriod, int numberOfBaselineYears);

    /**
     * Finds usages based on scenario identifier.
     *
     * @param scenarioId scenario id
     * @return the list of {@link Usage}s
     */
    List<Usage> findByScenarioId(String scenarioId);

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
     * Finds list of AACL {@link Usage}s by their ids.
     *
     * @param usageIds list of {@link Usage}s identifiers
     * @return list of {@link Usage}s
     */
    List<Usage> findByIds(List<String> usageIds);

    /**
     * Finds count of referenced usages in the df_usage_aacl table by ids.
     *
     * @param usageIds set of usage ids
     * @return the count of usages
     */
    int findReferencedAaclUsagesCountByIds(String... usageIds);

    /**
     * Finds list of AACL {@link UsageDto}s by usage filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> findDtosByFilter(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds AACL usages count based on applied filter.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the count of usages
     */
    int findCountByFilter(UsageFilter filter);

    /**
     * Finds list of AACL usage periods.
     *
     * @return list of AACL usage periods
     */
    List<Integer> findUsagePeriods();

    /**
     * Finds list of AACL usage periods by {@link UsageFilter}.
     *
     * @param filter {@link UsageFilter} instance
     * @return list of AACL usage periods found by {@link UsageFilter}
     */
    List<Integer> findUsagePeriodsByFilter(UsageFilter filter);

    /**
     * Verifies whether {@link Usage}s found by defined {@link UsageFilter} have specified status or not.
     *
     * @param filter {@link UsageFilter} instance
     * @param status {@link UsageStatusEnum} instance
     * @return {@code true} - if there are no {@link Usage}s found by defined {@link UsageFilter}
     * with status different from specified , {@code false} - otherwise
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
     * Finds rightsholders account numbers that are not presented in database based on {@link UsageFilter}.
     *
     * @param filter instance of {@link UsageFilter}
     * @return list of rightsholders account numbers
     */
    List<Long> findInvalidRightsholdersByFilter(UsageFilter filter);

    /**
     * Deletes all {@link Usage}s from the batch with given id.
     *
     * @param batchId {@link com.copyright.rup.dist.foreign.domain.UsageBatch} id
     */
    void deleteByBatchId(String batchId);

    /**
     * Finds whether there are usages that match {@link UsageFilter} and have the given Detail Licensee Class.
     *
     * @param filter                {@link UsageFilter} instance
     * @param detailLicenseeClassId Detail Licensee Class id
     * @return {@code true} if such usages exist, {@code false} otherwise
     */
    boolean usagesExistByDetailLicenseeClassAndFilter(UsageFilter filter, Integer detailLicenseeClassId);

    /**
     * Attaches usages to scenario.
     *
     * @param scenarioId id of scenario to add usages to
     * @param filter     {@link UsageFilter} instance
     * @param userName   user name
     */
    void addToScenario(String scenarioId, UsageFilter filter, String userName);

    /**
     * Marks usages by Payee Account # as
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#SCENARIO_EXCLUDED}.
     *
     * @param scenarioId          scenario identifier
     * @param payeeAccountNumbers set of payee account numbers
     * @param userName            user name
     * @return set of excluded usages ids
     */
    Set<String> excludeFromScenarioByPayees(String scenarioId, Set<Long> payeeAccountNumbers, String userName);

    /**
     * Calculates gross_amount, service_fee, service_fee_amount and net_amount for all scenario usages.
     *
     * @param scenarioId scenario identifier
     * @param userName   user name
     */
    void calculateAmounts(String scenarioId, String userName);

    /**
     * Marks usages with zero gross amount as
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#SCENARIO_EXCLUDED}.
     *
     * @param scenarioId scenario identifier
     * @param userName   user name
     */
    void excludeZeroAmountUsages(String scenarioId, String userName);

    /**
     * Finds list of {@link PayeeTotalHolder}s by filter.
     *
     * @param filter instance of {@link ExcludePayeeFilter}
     * @return list of {@link PayeeTotalHolder}s
     */
    List<PayeeTotalHolder> findPayeeTotalHoldersByFilter(ExcludePayeeFilter filter);

    /**
     * Finds list of {@link UsageDto}s matching specified filter.
     *
     * @param filter   {@link AuditFilter}
     * @param pageable limit and offset
     * @param sort     sort criteria
     * @return list of {@link UsageDto}s
     */
    List<UsageDto> findForAudit(AuditFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds count of {@link UsageDto}s by AACL {@link AuditFilter}.
     *
     * @param filter {@link AuditFilter}
     * @return count of {@link UsageDto}s matching filter
     */
    int findCountForAudit(AuditFilter filter);

    /**
     * Sets publication type weight for scenario usages with the given publication type.
     *
     * @param scenarioId        scenario id
     * @param publicationTypeId publication type id
     * @param weight            publication type weight
     * @param userName          update user name
     */
    void updatePublicationTypeWeight(String scenarioId, String publicationTypeId, BigDecimal weight, String userName);

    /**
     * Gets count of usage details based on {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier and
     * rightsholder account number.
     *
     * @param scenarioId    {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param accountNumber selected rightsholder account number
     * @param searchValue   search value
     * @return count of usage details
     */
    int findCountByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber, String searchValue);

    /**
     * Gets list of {@link UsageDto}s based on {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier and
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
     * Deletes usages from scenario. Updates usages status from
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#SCENARIO_EXCLUDED} and
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#LOCKED} to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE}.
     * Sets scenario id to {@code null}, sets amounts values to 0, sets shares and weights values to {@code null}.
     *
     * @param scenarioId scenario id
     * @param userName   user name
     */
    void deleteFromScenario(String scenarioId, String userName);

    /**
     * Add newly uploaded scenario usages to baseline.
     *
     * @param scenarioId {@link com.copyright.rup.dist.foreign.domain.Scenario} id
     * @param userName   user name
     */
    void addToBaselineByScenarioId(String scenarioId, String userName);

    /**
     * Finds all baseline usages.
     *
     * @return list of baseline {@link Usage}s
     */
    List<Usage> findBaselineUsages();

    /**
     * Deletes all {@link UsageStatusEnum#LOCKED} AACL {@link Usage}s for specified scenario, retaining references in
     * df_usage_aacl table.
     *
     * @param scenarioId scenario identifier
     */
    void deleteLockedByScenarioId(String scenarioId);

    /**
     * Deletes all {@link UsageStatusEnum#SCENARIO_EXCLUDED} AACL {@link Usage}s with references in df_usage_aacl for
     * specified scenario.
     *
     * @param scenarioId scenario identifier
     */
    void deleteExcludedByScenarioId(String scenarioId);

    /**
     * Finds {@link PayeeAccountAggregateLicenseeClassesPair}s by scenario identifier.
     *
     * @param scenarioId {@link com.copyright.rup.dist.foreign.domain.Scenario} id
     * @return list of {@link PayeeAccountAggregateLicenseeClassesPair}s
     */
    List<PayeeAccountAggregateLicenseeClassesPair> findPayeeAggClassesPairsByScenarioId(String scenarioId);
}
