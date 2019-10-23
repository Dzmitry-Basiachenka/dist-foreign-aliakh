package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.integration.rest.rms.RightsAssignmentResult;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
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
    List<UsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort);

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
     * Inserts NTS usages.
     *
     * @param usageBatch usage batch
     * @return list of NTS usages' ids
     */
    List<String> insertNtsUsages(UsageBatch usageBatch);

    /**
     * Deletes all {@link Usage}s associated with the given {@link UsageBatch}.
     *
     * @param usageBatch {@link UsageBatch} to delete usages from
     */
    void deleteUsageBatchDetails(UsageBatch usageBatch);

    /**
     * Deletes {@link Usage}s from Pre-Service fee fund.
     * Reverts status of {@link Usage}s to {@link UsageStatusEnum#NTS_WITHDRAWN}.
     *
     * @param fundPoolId identifier of fund pool
     */
    void deleteFromPreServiceFeeFund(String fundPoolId);

    /**
     * Deletes all archived {@link Usage}s and it's audit by provided batch id.
     *
     * @param batchId batch identifier
     */
    void deleteArchivedByBatchId(String batchId);

    /**
     * Deletes {@link Usage} with given id.
     *
     * @param usageId usage identifier
     */
    void deleteById(String usageId);

    /**
     * Deletes {@link Usage}s with Wr Wrk Insts that were classified as BELLETRISTIC by scenario identifier.
     *
     * @param scenarioId scenario identifier
     */
    void deleteBelletristicByScenarioId(String scenarioId);

    /**
     * Gets the {@link Usage}s based on {@link UsageFilter}.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the list of {@link Usage}s only with information about gross amount, net amount, reported value and
     * rightsholder
     */
    List<Usage> getUsagesWithAmounts(UsageFilter filter);

    /**
     * Gets count of archived {@link Usage}s based on fund pool information.
     *
     * @param usageBatch instance of {@link UsageBatch}
     * @return usages count
     */
    int getUsagesCountForNtsBatch(UsageBatch usageBatch);

    /**
     * Updates under minimum usages grouped by Wr Wrk Inst in {@link UsageStatusEnum#RH_NOT_FOUND} status.
     * Sets NTS product family and {@link UsageStatusEnum#NTS_WITHDRAWN} status.
     *
     * @return updated usages ids
     */
    List<String> updateNtsWithdrawnUsagesAndGetIds();

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
     * Populates payee and calculates amounts for usages of given NTS scenario.
     * Calculates service fee and service fee amount based on RHs participation status,
     * proportionally distributes Post Service Fee Amount among usages and updates gross and net amounts,
     * sets participation status.
     *
     * @param scenario scenario
     */
    void populatePayeeAndCalculateAmountsForNtsScenarioUsages(Scenario scenario);

    /**
     * Updates RH account number, payee account number, net amount, service fee amount and
     * RH and Payee participating flags for {@link Usage}s.
     *
     * @param usages list of {@link Usage}s
     */
    void updateRhPayeeAmountsAndParticipating(List<Usage> usages);

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
     * Deletes {@link Usage}s from NTS scenario. Reverts status of {@link Usage}s to {@link UsageStatusEnum#ELIGIBLE},
     * sets scenario id to {@code null}, sets gross amount to 0.
     *
     * @param scenarioId scenario identifier
     */
    void deleteFromNtsScenario(String scenarioId);

    /**
     * Deletes {@link Usage}s from scenario. Reverts status of {@link Usage}s to {@link UsageStatusEnum#ELIGIBLE},
     * sets scenario id, payee account number, service fee to {@code null}, sets rh participating flag to {@code false},
     * service fee amount and net amount to 0 for usages with rightsholders from given list of account numbers for
     * specified RRO.
     *
     * @param scenario         {@link Scenario}
     * @param rroAccountNumber RRO account number
     * @param accountNumbers   list of {@link com.copyright.rup.dist.common.domain.Rightsholder}s account numbers
     * @param reason           reason provided by user
     */
    void deleteFromScenario(Scenario scenario, Long rroAccountNumber, List<Long> accountNumbers, String reason);

    /**
     * Deletes {@link Usage}s from scenario. Reverts status of {@link Usage}s to {@link UsageStatusEnum#ELIGIBLE},
     * sets scenario id, payee account number, service fee to {@code null}, sets rh and payee participating flags to
     * {@code false}, service fee amount and net amount to 0 for usages with payees from given list of account numbers.
     *
     * @param scenario       {@link Scenario}
     * @param accountNumbers set of payees' account numbers
     * @param reason         reason provided by user
     */
    void deleteFromScenarioByPayees(Scenario scenario, Set<Long> accountNumbers, String reason);

    /**
     * Redesignates {@link Usage}s. Sets status of {@link Usage}s to {@link UsageStatusEnum#NTS_WITHDRAWN},
     * sets product family to NTS, sets scenario id, payee account number, service fee to {@code null},
     * sets rh and payee participating flags to {@code false}, service fee amount and net amount to 0
     * for usages with payees from given list of account numbers and in given scenario.
     *
     * @param scenario       {@link Scenario}
     * @param accountNumbers set of payees' account numbers
     * @param reason         reason provided by user
     */
    void redesignateByPayees(Scenario scenario, Set<Long> accountNumbers, String reason);

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
     * @return list of moved to archive {@link Usage}s ids
     */
    List<String> moveToArchive(Scenario scenario);

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
     * Gets list of {@link PayeeTotalHolder}s by scenario identifier.
     *
     * @param scenarioId scenario identifier
     * @return list of {@link PayeeTotalHolder}s
     */
    List<PayeeTotalHolder> getPayeeTotalHoldersByScenarioId(String scenarioId);

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
     * @return list of supported markets.
     */
    List<String> getMarkets();

    /**
     * @return CLA account number.
     */
    Long getClaAccountNumber();

    /**
     * Updates paid infromation for {@link PaidUsage}s
     * and status to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#PAID}.
     *
     * @param usages list of {@link PaidUsage}s to update
     */
    void updatePaidInfo(List<PaidUsage> usages);

    /**
     * Updates researched usage details.
     *
     * @param researchedUsages list of {@link ResearchedUsage}s
     */
    void loadResearchedUsages(List<ResearchedUsage> researchedUsages);

    /**
     * Updates researched usage details, sets WORK_FOUND status and adds log action.
     * Is used only by {@link IUsageService#loadResearchedUsages(List)}.
     * Due to the fact that default mechanisms of proxying in Spring are Dynamic Proxy and CGLIB,
     * it was implemented as public method to support declarative transaction.
     *
     * @param researchedUsages list of {@link ResearchedUsage}s
     */
    void markAsWorkFound(List<ResearchedUsage> researchedUsages);

    /**
     * Gets list of {@link PaidUsage} available for sending to CRM.
     * Sends {@link PaidUsage} to CRM and updates their status to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ARCHIVED}.
     * Also updates scenario status to {@link com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum#ARCHIVED}
     * if all usages from scenario were sent to CRM.
     *
     * @return {@link JobInfo} instance with information about job name, execution status and job result}
     */
    JobInfo sendToCrm();

    /**
     * Sends set of works related to specified batch name to RMS for Rights Assignment.
     * Updates status of usages related to specified batch and Wr Wrk Insts to {@link UsageStatusEnum#SENT_FOR_RA}.
     *
     * @param batchName  bacth name
     * @param wrWrkInsts set of Wr Wrk Insts
     * @param usageIds   set of usage ids
     * @return {@link RightsAssignmentResult} instance
     */
    RightsAssignmentResult sendForRightsAssignment(String batchName, Set<Long> wrWrkInsts, Set<String> usageIds);

    /**
     * Gets list of {@link Usage}s by specified {@link Usage} ids.
     *
     * @param usageIds list of {@link Usage} ids
     * @return list of {@link Usage}s
     */
    List<Usage> getUsagesByIds(List<String> usageIds);

    /**
     * Gets list of archived {@link Usage}s by specified {@link Usage} ids.
     *
     * @param usageIds list of {@link Usage} ids
     * @return list of {@link Usage}s
     */
    List<Usage> getArchivedUsagesByIds(List<String> usageIds);

    /**
     * Gets list of {@link Usage} ids by specified {@link UsageStatusEnum} and product family.
     *
     * @param status        {@link UsageStatusEnum} instance
     * @param productFamily product family
     * @return the list of found {@link Usage} ids
     */
    List<String> getUsageIdsByStatusAndProductFamily(UsageStatusEnum status, String productFamily);

    /**
     * Gets list of {@link Usage}s by specified {@link UsageStatusEnum} and product family.
     *
     * @param status        {@link UsageStatusEnum} instance
     * @param productFamily product family
     * @return the list of found {@link Usage}s
     */
    List<Usage> getUsagesByStatusAndProductFamily(UsageStatusEnum status, String productFamily);

    /**
     * Verifies whether {@link Usage}s found by defined {@link UsageFilter} have specified status or not.
     *
     * @param filter {@link UsageFilter} instance
     * @param status {@link UsageStatusEnum} instance
     * @return {@code true} - if all filtered {@link Usage}s have specified {@link UsageStatusEnum},
     * {@code false} - otherwise
     */
    boolean isValidUsagesState(UsageFilter filter, UsageStatusEnum status);

    /**
     * Updates {@link Usage} and verifies that version of {@link Usage} is the same as in database.
     * Throws an {@link com.copyright.rup.dist.foreign.service.impl.InconsistentUsageStateException} in case of
     * there are {@link Usage} version discrepancies.
     *
     * @param usage {@link Usage} to update
     */
    void updateProcessedUsage(Usage usage);

    /**
     * Updates status and RH account number of {@link Usage} based on set of {@link Usage} identifiers.
     *
     * @param usageIds        set of usage identifiers
     * @param status          instance of {@link UsageStatusEnum}
     * @param rhAccountNumber RH account number
     */
    void updateStatusAndRhAccountNumber(Set<String> usageIds, UsageStatusEnum status, Long rhAccountNumber);

    /**
     * Gets count of unclassified usages to be updated based on set of Wr Wrk Insts.
     *
     * @param wrWrkInsts set of Wr Wrk Insts
     * @return usages count
     */
    int getUnclassifiedUsagesCount(Set<Long> wrWrkInsts);

    /**
     * Gets map of Wr Wrk Insts to usage ids related to specified batch
     * and having {@link UsageStatusEnum#RH_NOT_FOUND} status.
     *
     * @param batchName batch name
     * @return map where key - Wr Wrk Inst, value - set of usage ids related to Wr Wrk Inst
     */
    Map<Long, Set<String>> getWrWrkInstToUsageIdsForRightsAssignment(String batchName);

    /**
     * Updates usages with status {@link UsageStatusEnum#NTS_WITHDRAWN} from given batches to status TO_BE_DISTRIBUTED
     * and adds the usages to the pre-service fee fund.
     *
     * @param fundId   id of pre-service fee fund
     * @param batchIds set of ids of usage batches
     * @param userName user name
     */
    void addWithdrawnUsagesToPreServiceFeeFund(String fundId, Set<String> batchIds, String userName);
}
