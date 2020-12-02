package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.integration.rest.rms.RightsAssignmentResult;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeeProductFamilyHolder;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

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
     * Deletes all {@link Usage}s associated with the given {@link UsageBatch}.
     *
     * @param usageBatch {@link UsageBatch} to delete usages from
     */
    void deleteUsageBatchDetails(UsageBatch usageBatch);

    /**
     * Deletes all archived {@link Usage}s and its audit by provided batch id.
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
     * Deletes {@link Usage}s from scenario. Reverts status of {@link Usage}s
     * to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE}, sets scenario id, payee account
     * number, service fee to {@code null}, sets rh participating flag to {@code false}, service fee amount and net
     * amount to 0 for given scenario.
     *
     * @param scenarioId scenario identifier
     */
    void deleteFromScenario(String scenarioId);

    /**
     * Deletes {@link Usage}s from scenario. Reverts status of {@link Usage}s to {@link UsageStatusEnum#ELIGIBLE},
     * sets scenario id, payee account number, service fee to {@code null}, sets rh participating flag to {@code false},
     * service fee amount and net amount to 0 for usages with rightsholders from given list of account numbers for
     * specified RRO.
     *
     * @param scenarioId       {@link Scenario} identifier
     * @param rroAccountNumber RRO account number
     * @param accountNumbers   list of {@link com.copyright.rup.dist.common.domain.Rightsholder}s account numbers
     * @param reason           reason provided by user
     */
    void deleteFromScenario(String scenarioId, Long rroAccountNumber, List<Long> accountNumbers, String reason);

    /**
     * Checks if usage with usage id and status exists in database.
     *
     * @param usageId    usage id
     * @param statusEnum status of usage
     * @return {@code true} if detail id is present, {@code false} otherwise
     */
    boolean isUsageIdExists(String usageId, UsageStatusEnum statusEnum);

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
     * Gets list of {@link PayeeTotalHolder}s by filter.
     *
     * @param filter instance of {@link ExcludePayeeFilter}
     * @return list of {@link PayeeTotalHolder}s
     */
    List<PayeeTotalHolder> getPayeeTotalHoldersByFilter(ExcludePayeeFilter filter);

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
    int getCountForAudit(AuditFilter filter);

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
     * Updates paid infromation for {@link PaidUsage}s
     * and status to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#PAID}.
     *
     * @param usages list of {@link PaidUsage}s to update
     */
    void updatePaidInfo(List<PaidUsage> usages);

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
     * Gets list of archived {@link Usage}s for sending to LM by specified {@link Usage} ids.
     *
     * @param usageIds list of {@link Usage} ids
     * @return list of {@link Usage}s
     */
    List<Usage> getArchivedUsagesForSendToLmByIds(List<String> usageIds);

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
    boolean isValidFilteredUsageStatus(UsageFilter filter, UsageStatusEnum status);

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
     * Gets map of Wr Wrk Insts to usage ids related to specified batch
     * and having {@link UsageStatusEnum#RH_NOT_FOUND} status.
     *
     * @param batchName batch name
     * @return map where key - Wr Wrk Inst, value - set of usage ids related to Wr Wrk Inst
     */
    Map<Long, Set<String>> getWrWrkInstToUsageIdsForRightsAssignment(String batchName);

    /**
     * Sends list of usages on queue for PI matching process.
     *
     * @param usages list of {@link Usage} to be sent
     */
    void sendForMatching(List<Usage> usages);

    /**
     * Sends list of usages on queue for getting Rights process.
     *
     * @param usages    list of {@link Usage} to be sent
     * @param batchName batch name
     */
    void sendForGettingRights(List<Usage> usages, String batchName);

    /**
     * Updates paid infromation for {@link PaidUsage}s and status to {@link UsageStatusEnum#PAID}.
     *
     * @param paidUsages         list of {@link PaidUsage}s to update
     * @param findByIdsFunction  function convert paid usages to map with usage id and original usages
     * @param insertPaidConsumer consume information for update
     */
    void updatePaidUsages(List<PaidUsage> paidUsages, Function<List<String>, List<Usage>> findByIdsFunction,
                          Consumer<PaidUsage> insertPaidConsumer);

    /**
     * Gets unique combinations of RH, payee and product family from scenarios.
     *
     * @param scenarioIds set of scenario ids
     * @return list of {@link RightsholderPayeeProductFamilyHolder}s
     */
    List<RightsholderPayeeProductFamilyHolder> getRightsholderPayeeProductFamilyHoldersByScenarioIds(
        Set<String> scenarioIds);
}
