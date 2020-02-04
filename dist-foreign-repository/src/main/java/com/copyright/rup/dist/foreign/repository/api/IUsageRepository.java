package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
     * Finds list of {@link UsageDto}s by usage filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> findDtosByFilter(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds list of {@link Usage} ids by specified {@link UsageStatusEnum} and product family.
     *
     * @param status        {@link UsageStatusEnum} instance
     * @param productFamily product family
     * @return the list of found {@link Usage} ids
     */
    List<String> findIdsByStatusAndProductFamily(UsageStatusEnum status, String productFamily);

    /**
     * Finds list of {@link Usage}s by specified {@link UsageStatusEnum} and product family.
     *
     * @param status        {@link UsageStatusEnum} instance
     * @param productFamily product family
     * @return the list of found {@link Usage}s
     */
    List<Usage> findByStatusAndProductFamily(UsageStatusEnum status, String productFamily);

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
     * Finds list of {@link Usage}s by their ids.
     *
     * @param usageIds list of {@link Usage}s identifiers
     * @return list of {@link Usage}s
     */
    List<Usage> findByIds(List<String> usageIds);

    /**
     * Finds {@link Usage} ids with belletristic classification or classified usages in
     * {@link UsageStatusEnum#UNCLASSIFIED} status.
     *
     * @return list of {@link Usage} ids
     */
    List<String> findUsageIdsForClassificationUpdate();

    /**
     * Finds count of usages based on set of Wr Wrk Insts and status.
     *
     * @param status     usage status
     * @param wrWrkInsts set of Wr Wrk Insts
     * @return count of usages
     */
    int findCountByStatusAndWrWrkInsts(UsageStatusEnum status, Set<Long> wrWrkInsts);

    /**
     * Finds {@link Usage}s for reconcile based on scenario identifier.
     *
     * @param scenarioId scenario identifier
     * @return the list of {@link Usage}
     */
    List<Usage> findForReconcile(String scenarioId);

    /**
     * Finds rightsholder information based on scenario identifier.
     *
     * @param scenarioId scenario id
     * @return map where key is rightsholder account number, value is {@link Usage} with rightsholder, participating
     * status and payee account number
     */
    Map<Long, Usage> findRightsholdersInformation(String scenarioId);

    /**
     * Deletes all {@link Usage}s from the batch with given id.
     *
     * @param batchId {@link com.copyright.rup.dist.foreign.domain.UsageBatch} id
     */
    void deleteByBatchId(String batchId);

    /**
     * Deletes {@link Usage} with given id.
     *
     * @param usageId usage identifier
     */
    void deleteById(String usageId);

    /**
     * Calculates total gross amount by standard number and batch identifier for PI matching.
     *
     * @param standardNumber standard number for calculation
     * @param batchId        batch identifier
     * @return total gross amount
     */
    BigDecimal getTotalAmountByStandardNumberAndBatchId(String standardNumber, String batchId);

    /**
     * Calculates total gross amount by title and batch identifier for PI matching.
     *
     * @param title   title for calculation
     * @param batchId batch identifier
     * @return total gross amount
     */
    BigDecimal getTotalAmountByTitleAndBatchId(String title, String batchId);

    /**
     * Deletes all {@link Usage}s for specified scenario.
     *
     * @param scenarioId scenario identifier
     */
    void deleteByScenarioId(String scenarioId);

    /**
     * Deletes all NTS {@link Usage}s for specified scenario.
     *
     * @param scenarioId scenario identifier
     */
    void deleteNtsByScenarioId(String scenarioId);

    /**
     * Finds the {@link Usage}s only with information about gross amount, net amount, reported value and rightsholder
     * based on {@link UsageFilter}.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the list of {@link Usage}s only with information about gross amount, net amount and reported value
     */
    List<Usage> findWithAmountsAndRightsholders(UsageFilter filter);

    /**
     * Finds rightsholders account numbers that are not presented in database based on {@link UsageFilter}.
     *
     * @param filter instance of {@link UsageFilter}
     * @return list of rightsholders account numbers
     */
    List<Long> findInvalidRightsholdersByFilter(UsageFilter filter);

    /**
     * Updates scenario id, updated user name, status to 'LOCKED', payee account number,
     * net amount, service fee amount and RH participating flag for {@link Usage}s.
     *
     * @param usages list of {@link Usage}s
     */
    void addToScenario(List<Usage> usages);

    /**
     * Deletes {@link Usage}s from scenario. Reverts status of {@link Usage}s to {@link UsageStatusEnum#ELIGIBLE},
     * sets scenario id, payee account number, service fee to {@code null}, sets rh participating flag to {@code false},
     * service fee amount and net amount to 0.
     *
     * @param scenarioId scenario identifier
     * @param updateUser name of user who performed this action
     */
    void deleteFromScenario(String scenarioId, String updateUser);

    /**
     * Deletes {@link Usage}s from scenario. Reverts status of {@link Usage}s to {@link UsageStatusEnum#ELIGIBLE},
     * sets scenario id, payee account number, service fee to {@code null}, sets rh and payee participating flags to
     * {@code false}, service fee amount and net amount to 0 for usages with payees from given list of account numbers.
     *
     * @param scenarioId     {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param accountNumbers set of payees' account numbers
     * @param userName       user name
     * @return set of excluded usages' identifiers
     */
    Set<String> deleteFromScenarioByPayees(String scenarioId, Set<Long> accountNumbers, String userName);

    /**
     * Redesignates {@link Usage}s. Sets status of {@link Usage}s to {@link UsageStatusEnum#NTS_WITHDRAWN},
     * sets product family to NTS, sets scenario id, payee account number, service fee to {@code null},
     * sets rh and payee participating flags to {@code false}, service fee amount and net amount to 0
     * for usages with payees from given list of account numbers and in given scenario.
     *
     * @param scenarioId     {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param accountNumbers set of payees' account numbers
     * @param userName       user name
     * @return set of redesignated usages' identifiers
     */
    Set<String> redesignateToNtsWithdrawnByPayees(String scenarioId, Set<Long> accountNumbers, String userName);

    /**
     * Deletes {@link Usage}s from scenario. Reverts status of {@link Usage}s to {@link UsageStatusEnum#ELIGIBLE},
     * sets scenario id, payee account number, service fee to {@code null}, sets rh participating flag to {@code false},
     * service fee amount and net amount to 0 for usages with given ids.
     *
     * @param usagesIds list of {@link Usage}s ids
     * @param userName  user name
     */
    void deleteFromScenario(List<String> usagesIds, String userName);

    /**
     * Finds count of all usages by usage id and status.
     *
     * @param usageId    usage id
     * @param statusEnum status of usage
     * @return count of usages
     */
    int findCountByUsageIdAndStatus(String usageId, UsageStatusEnum statusEnum);

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
     * Gets list of {@link PayeeTotalHolder}s by scenario identifier.
     *
     * @param scenarioId scenario identifier
     * @return list of {@link PayeeTotalHolder}s
     */
    List<PayeeTotalHolder> findPayeeTotalHoldersByScenarioId(String scenarioId);

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
     * Gets boolean result that shows whether scenario is empty or not.
     *
     * @param scenarioId {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @return boolean result that shows whether scenario is empty or not.
     */
    boolean isScenarioEmpty(String scenarioId);

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
     * Finds count of {@link UsageDto}s by specified {@link AuditFilter}.
     *
     * @param filter {@link AuditFilter}
     * @return count of {@link UsageDto}s matching filter
     */
    int findCountForAudit(AuditFilter filter);

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
     * Finds list of {@link Usage}s by array of {@link UsageStatusEnum} items.
     *
     * @param statuses the array of {@link UsageStatusEnum} items
     * @return the list of {@link Usage}s
     */
    List<Usage> findByStatuses(UsageStatusEnum... statuses);

    /**
     * Updates status of {@link Usage}s based on set of {@link Usage} identifiers.
     *
     * @param usageIds set of usages identifier
     * @param status   instance of {@link UsageStatusEnum}
     */
    void updateStatus(Set<String> usageIds, UsageStatusEnum status);

    /**
     * Updates status and RH account number of {@link Usage} based on set of {@link Usage} identifiers.
     *
     * @param usageIds        set of usage identifiers
     * @param status          instance of {@link UsageStatusEnum}
     * @param rhAccountNumber RH account number
     */
    void updateStatusAndRhAccountNumber(Set<String> usageIds, UsageStatusEnum status, Long rhAccountNumber);

    /**
     * Updates given list of {@link Usage}s. Update affects not all fields. For exact list of affected fields please
     * see implementation.
     *
     * @param usages list of {@link Usage}s
     */
    void update(List<Usage> usages);

    /**
     * Updates given {@link Usage} in case of its version is the same as in database.
     *
     * @param usage {@link Usage} to update
     * @return id of updated record, otherwise {@code null}
     */
    String updateProcessedUsage(Usage usage);

    /**
     * Updates researched usage details.
     *
     * @param researchedUsages collection of {@link ResearchedUsage}s
     */
    void updateResearchedUsages(Collection<ResearchedUsage> researchedUsages);

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
     * Updates usages with status NTS_WITHDRAWN from given batches to status TO_BE_DISTRIBUTED
     * and adds the usages to the pre-service fee fund.
     *
     * @param fundId   id of pre-service fee fund
     * @param batchIds set of ids of usage batches
     * @param userName user name
     */
    void addWithdrawnUsagesToPreServiceFeeFund(String fundId, Set<String> batchIds, String userName);

    /**
     * Updates status to UNCLASSIFIED for ELIGIBLE usages with defined works without classification.
     *
     * @param wrWrkInsts list of Wr Wrk Insts
     * @param userName   user name
     */
    void updateUsagesStatusToUnclassified(List<Long> wrWrkInsts, String userName);

    /**
     * Finds map of Wr Wrk Insts to usage ids related to specified batch and having specified status.
     *
     * @param batchName batch name
     * @param status    {@link UsageStatusEnum}
     * @return map where key - Wr Wrk Inst, value - set of usage ids related to Wr Wrk Inst
     */
    Map<Long, Set<String>> findWrWrkInstToUsageIdsByBatchNameAndUsageStatus(String batchName, UsageStatusEnum status);

    /**
     * Finds count of referenced usages in the df_usage_fas table by ids.
     *
     * @param usageIds set of usage ids
     * @return the count of usages
     */
    int findReferencedFasUsagesCountByIds(String... usageIds);
}
