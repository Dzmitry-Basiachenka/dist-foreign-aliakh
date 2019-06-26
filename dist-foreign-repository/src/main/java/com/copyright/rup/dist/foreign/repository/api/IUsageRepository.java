package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.io.OutputStream;
import java.io.PipedOutputStream;
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
     * Finds usages according to given {@link UsageFilter} and writes them to the output stream in CSV format.
     *
     * @param filter            instance of {@link UsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUsagesCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes usages found by filter into the output stream in CSV format
     * and returns identifiers of those usages.
     *
     * @param filter       instance of {@link UsageFilter}
     * @param outputStream instance of {@link OutputStream}
     * @return identifiers of usages written into CSV report
     */
    Set<String> writeUsagesForResearchAndFindIds(UsageFilter filter, OutputStream outputStream);

    /**
     * Finds usages by scenario id and writes them into the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Finds {@link RightsholderTotalsHolder}s based on scenario id and writes them to the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeScenarioRightsholderTotalsCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

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
     * Deletes {@link Usage}s from Pre-Service fee fund.
     * Updates {@link Usage}s status to {@link UsageStatusEnum#NTS_WITHDRAWN}.
     *
     * @param fundPoolId identifier of fund pool
     * @param userName   user name
     */
    void deleteFromPreServiceFeeFund(String fundPoolId, String userName);

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
     * Updates under minimum usages grouped by Wr Wrk Inst in {@link UsageStatusEnum#RH_NOT_FOUND} status.
     * Sets NTS product family and {@link UsageStatusEnum#NTS_WITHDRAWN} status.
     *
     * @return updated usages ids
     */
    List<String> updateNtsWithdrawnUsagesAndGetIds();

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
     * Deletes {@link Usage}s with Wr Wrk Insts that were classified as BELLETRISTIC by scenario identifier.
     *
     * @param scenarioId scenario identifier
     */
    void deleteBelletristicByScenarioId(String scenarioId);

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
     * Deletes {@link Usage}s from NTS scenario. Updates {@link Usage}s associated with scenario in
     * {@link UsageStatusEnum#NTS_EXCLUDED} and {@link UsageStatusEnum#LOCKED} statuses.
     * Reverts status to {@link UsageStatusEnum#ELIGIBLE}, sets scenario id to {@code null}, sets gross amount to 0.
     *
     * @param scenarioId scenario identifier
     * @param userName   user name
     */
    void deleteFromNtsScenario(String scenarioId, String userName);

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
     * Finds usages by given {@link AuditFilter} and writes them to the given {@link PipedOutputStream}.
     *
     * @param filter            filter
     * @param pipedOutputStream stream
     * @throws RupRuntimeException in case when IOException appears during writing report
     */
    void writeAuditCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) throws RupRuntimeException;

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
     * Updates given {@link Usage} in case of its version is the same as in database .
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
    boolean isValidUsagesState(UsageFilter filter, UsageStatusEnum status);

    /**
     * Inserts usages from archived FAS usages based on NTS Batch criteria (Market Period From/To, Markets).
     * Belletristic usages and usages that do not meet minimum Cutoff Amounts will not be inserted.
     *
     * @param usageBatch instance of {@link UsageBatch}
     * @param userName   user name
     * @return list of inserted usage uids
     */
    List<String> insertNtsUsages(UsageBatch usageBatch, String userName);

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
     * Calculates service fee and net amounts for usages with given RH account number and scenario uid.
     * Sets payee account number, participating flag and service fee percent.
     *
     * @param rhAccountNumber    rightsholder account number
     * @param scenarioId         identifier of scenario
     * @param serviceFee         service fee
     * @param participatingFlag  participating flag
     * @param payeeAccountNumber payee account number
     * @param userName           user name
     */
    void calculateAmountsAndUpdatePayeeByAccountNumber(Long rhAccountNumber, String scenarioId, BigDecimal serviceFee,
                                                       boolean participatingFlag, Long payeeAccountNumber,
                                                       String userName);

    /**
     * Proportionally distributes Post Service Fee Amount among scenario usages above minimum.
     *
     * @param scenarioId identifier of scenario
     */
    void applyPostServiceFeeAmount(String scenarioId);
}
