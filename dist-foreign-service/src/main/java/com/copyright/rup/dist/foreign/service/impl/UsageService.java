package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.integration.rest.rms.RightsAssignmentResult;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeeProductFamilyHolder;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmResult;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmRightsDistributionRequest;
import com.copyright.rup.dist.foreign.integration.crm.api.ICrmIntegrationService;
import com.copyright.rup.dist.foreign.integration.rms.api.IRmsIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
@Service
public class UsageService implements IUsageService {

    private static final String SEND_TO_CRM_FINISHED_INFO_LOG_MESSAGE = "Send to CRM. Finished. PaidUsagesCount={}, " +
        "ArchivedUsagesCount={}, NotReportedUsagesCount={}, ArchivedScenariosCount={}";
    private static final String SEND_TO_CRM_FINISHED_DEBUG_LOG_MESSAGE = "Send to CRM. Finished. PaidUsagesCount={}, " +
        "ArchivedUsagesCount={}, ArchivedScenariosCount={}, NotReportedUsageIds={}";
    private static final String UPDATE_PAID_NOT_FOUND_WARN_LOG_MESSAGE = "Update paid information. Not found usages. " +
        "UsagesCount={}, CreatedCount={}, UpdatedCount={}, NotFoundUsageIds={}";
    private static final Logger LOGGER = RupLogUtils.getLogger();
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private ICrmIntegrationService crmIntegrationService;
    @Autowired
    private IRmsIntegrationService rmsIntegrationService;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    @Qualifier("usageChainChunkExecutor")
    private IChainExecutor<Usage> chainExecutor;

    @Override
    @Transactional
    public void deleteUsageBatchDetails(UsageBatch usageBatch) {
        usageAuditService.deleteActionsByBatchId(usageBatch.getId());
        usageRepository.deleteByBatchId(usageBatch.getId());
    }

    @Override
    @Transactional
    public void deleteArchivedByBatchId(String batchId) {
        usageAuditService.deleteActionsForArchivedByBatchId(batchId);
        usageArchiveRepository.deleteByBatchId(batchId);
    }

    @Override
    @Transactional
    public void deleteById(String usageId) {
        usageAuditService.deleteActionsByUsageId(usageId);
        usageRepository.deleteById(usageId);
    }

    @Override
    public List<Long> getInvalidRightsholdersByFilter(UsageFilter filter) {
        return usageRepository.findInvalidRightsholdersByFilter(filter);
    }

    @Override
    public List<Usage> getUsagesByScenarioId(String scenarioId) {
        return usageRepository.findByScenarioId(scenarioId);
    }

    @Override
    public void deleteFromScenario(String scenarioId) {
        usageRepository.deleteFromScenario(scenarioId, RupContextUtils.getUserName());
    }

    @Override
    @Transactional
    public void deleteFromScenario(String scenarioId, Long rroAccountNumber, List<Long> accountNumbers, String reason) {
        List<String> usagesIds =
            usageRepository.findIdsByScenarioIdRroAccountNumberRhAccountNumbers(scenarioId, rroAccountNumber,
                accountNumbers);
        usagesIds.forEach(
            usageId -> usageAuditService.logAction(usageId, UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, reason));
        usageRepository.deleteFromScenario(usagesIds, RupContextUtils.getUserName());
    }

    @Override
    public boolean isUsageIdExists(String usageId, UsageStatusEnum statusEnum) {
        return 0 != usageRepository.findCountByUsageIdAndStatus(usageId, statusEnum);
    }

    @Override
    public List<RightsholderTotalsHolder> getRightsholderTotalsHoldersByScenario(Scenario scenario,
                                                                                 String searchValue,
                                                                                 Pageable pageable, Sort sort) {
        return FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())
            ? usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(scenario.getId(), searchValue, pageable,
            sort)
            : usageRepository.findRightsholderTotalsHoldersByScenarioId(scenario.getId(), searchValue, pageable, sort);
    }

    @Override
    public List<PayeeTotalHolder> getPayeeTotalHoldersByFilter(ExcludePayeeFilter filter) {
        return !filter.isEmpty()
            ? usageRepository.findPayeeTotalHoldersByFilter(filter)
            : Collections.emptyList();
    }

    @Override
    public int getRightsholderTotalsHolderCountByScenario(Scenario scenario, String searchValue) {
        return FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())
            ? usageArchiveRepository.findRightsholderTotalsHolderCountByScenarioId(scenario.getId(), searchValue)
            : usageRepository.findRightsholderTotalsHolderCountByScenarioId(scenario.getId(), searchValue);
    }

    @Override
    public boolean isScenarioEmpty(Scenario scenario) {
        return !FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())
            && usageRepository.isScenarioEmpty(scenario.getId());
    }

    @Override
    public int getCountByScenarioAndRhAccountNumber(Long accountNumber, Scenario scenario, String searchValue) {
        return FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())
            ? usageArchiveRepository.findCountByScenarioIdAndRhAccountNumber(scenario.getId(), accountNumber,
            searchValue)
            : usageRepository.findCountByScenarioIdAndRhAccountNumber(accountNumber, scenario.getId(), searchValue);
    }

    @Override
    public List<UsageDto> getByScenarioAndRhAccountNumber(Long accountNumber, Scenario scenario,
                                                          String searchValue, Pageable pageable, Sort sort) {
        return FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())
            ? usageArchiveRepository.findByScenarioIdAndRhAccountNumber(scenario.getId(), accountNumber, searchValue,
            pageable, sort)
            : usageRepository.findByScenarioIdAndRhAccountNumber(accountNumber, scenario.getId(), searchValue, pageable,
            sort);
    }

    @Override
    public int getCountForAudit(AuditFilter filter) {
        return usageRepository.findCountForAudit(filter);
    }

    @Override
    public List<UsageDto> getForAudit(AuditFilter filter, Pageable pageable, Sort sort) {
        return usageRepository.findForAudit(filter, pageable, sort);
    }

    @Override
    @Transactional
    public void updatePaidInfo(List<PaidUsage> paidUsages) {
        updatePaidUsages(paidUsages, ids -> usageArchiveRepository.findByIds(ids),
            usage -> usageArchiveRepository.insertPaid(usage));
    }

    @Override
    @Transactional
    public void updatePaidUsages(List<PaidUsage> paidUsages, Function<List<String>, List<Usage>> findByIdsFunction,
                                 Consumer<PaidUsage> insertPaidConsumer) {
        LogUtils.ILogWrapper paidUsagesCount = LogUtils.size(paidUsages);
        LOGGER.info("Update paid information. Started. UsagesCount={}", paidUsagesCount);
        populateAccountNumbers(paidUsages);
        AtomicInteger newUsagesCount = new AtomicInteger();
        Set<String> notFoundUsageIds = Sets.newHashSet();
        Map<String, Usage> usageIdToUsageMap = findByIdsFunction.apply(paidUsages.stream()
            .map(PaidUsage::getId)
            .collect(Collectors.toList())).stream()
            .collect(Collectors.toMap(Usage::getId, usage -> usage));
        paidUsages.forEach(paidUsage -> {
            String paidUsageId = paidUsage.getId();
            if (Objects.nonNull(usageIdToUsageMap.get(paidUsageId))) {
                final Long oldAccountNumber = usageIdToUsageMap.get(paidUsageId).getRightsholder().getAccountNumber();
                final Long newAccountNumber = paidUsage.getRightsholder().getAccountNumber();
                if (paidUsage.isPostDistributionFlag()) {
                    String actionReason = Objects.nonNull(paidUsage.getSplitParentFlag())
                        ? "Usage has been created based on Post-Distribution and Split processes"
                        : "Usage has been created based on Post-Distribution process";
                    insertPaidUsage(buildPaidUsage(usageIdToUsageMap.get(paidUsageId), paidUsage), actionReason,
                        insertPaidConsumer);
                    newUsagesCount.getAndIncrement();
                } else if (Objects.isNull(paidUsage.getSplitParentFlag())) {
                    updatePaidUsage(paidUsage, buildActionReason(oldAccountNumber, newAccountNumber,
                        "Usage has been paid according to information from the LM"));
                } else if (paidUsage.getSplitParentFlag()) {
                    updatePaidUsage(paidUsage, buildActionReason(oldAccountNumber, newAccountNumber,
                        "Usage has been adjusted based on Split process"));
                    logScenarioSplitAction(usageIdToUsageMap.get(paidUsageId).getScenarioId());
                } else {
                    insertPaidUsage(buildPaidUsage(usageIdToUsageMap.get(paidUsageId), paidUsage),
                        "Usage has been created based on Split process", insertPaidConsumer);
                    newUsagesCount.getAndIncrement();
                }
            } else {
                notFoundUsageIds.add(paidUsage.getId());
            }
        });
        int updatedUsagesCount =
            CollectionUtils.size(paidUsages) - newUsagesCount.get() - CollectionUtils.size(notFoundUsageIds);
        if (CollectionUtils.isNotEmpty(notFoundUsageIds)) {
            LOGGER.warn(UPDATE_PAID_NOT_FOUND_WARN_LOG_MESSAGE, paidUsagesCount, newUsagesCount, updatedUsagesCount,
                notFoundUsageIds);
        }
        LOGGER.info("Update paid information. Finished. UsagesCount={}, CreatedCount={}, UpdatedCount={}",
            paidUsagesCount, newUsagesCount, updatedUsagesCount);
    }

    @Override
    public JobInfo sendToCrm() {
        List<String> paidUsagesIds = usageArchiveRepository.findPaidIds();
        LogUtils.ILogWrapper paidUsagesCount = LogUtils.size(paidUsagesIds);
        LOGGER.info("Send to CRM. Started. PaidUsagesCount={}", paidUsagesCount);
        int archivedUsagesCount = 0;
        JobInfo jobInfo;
        if (CollectionUtils.isNotEmpty(paidUsagesIds)) {
            Set<String> invalidUsageIds = Sets.newHashSet();
            for (List<String> ids : Iterables.partition(paidUsagesIds, 128)) {
                List<PaidUsage> paidUsages = usageArchiveRepository.findByIdAndStatus(ids, UsageStatusEnum.PAID);
                if (CollectionUtils.isNotEmpty(paidUsages)) {
                    CrmResult result =
                        crmIntegrationService.insertRightsDistribution(buildCrmRightsDistributionRequest(paidUsages));
                    if (result.isSuccessful()) {
                        Set<String> usagesIds = paidUsages.stream().map(PaidUsage::getId).collect(Collectors.toSet());
                        updateReportedUsages(usagesIds);
                        archivedUsagesCount += usagesIds.size();
                    } else {
                        Set<String> invalidIds = result.getInvalidUsageIds();
                        if (CollectionUtils.isNotEmpty(invalidIds) && paidUsages.size() != invalidIds.size()) {
                            Set<String> usagesIds = paidUsages.stream()
                                .filter(paidUsage -> !invalidIds.contains(paidUsage.getId()))
                                .map(PaidUsage::getId)
                                .collect(Collectors.toSet());
                            if (CollectionUtils.isNotEmpty(usagesIds)) {
                                updateReportedUsages(usagesIds);
                                archivedUsagesCount += usagesIds.size();
                            }
                        }
                        invalidUsageIds.addAll(result.getInvalidUsageIds());
                    }
                }
            }
            int archivedScenariosCount = scenarioService.archiveScenarios();
            LOGGER.info(SEND_TO_CRM_FINISHED_INFO_LOG_MESSAGE, paidUsagesCount, archivedUsagesCount,
                invalidUsageIds.size(), archivedScenariosCount);
            LOGGER.trace(SEND_TO_CRM_FINISHED_DEBUG_LOG_MESSAGE, paidUsagesCount, archivedUsagesCount,
                archivedScenariosCount, invalidUsageIds);
            jobInfo = new JobInfo(JobStatusEnum.FINISHED, "PaidUsagesCount=" + paidUsagesCount +
                ", ArchivedUsagesCount=" + archivedUsagesCount + ", NotReportedUsagesCount=" +
                invalidUsageIds.size() + ", ArchivedScenariosCount=" + archivedScenariosCount);
        } else {
            String message = "PaidUsagesCount=" + paidUsagesCount + ", Reason=There are no usages";
            LOGGER.info("Send to CRM. Skipped. {}", message);
            jobInfo = new JobInfo(JobStatusEnum.SKIPPED, message);
        }
        return jobInfo;
    }

    @Override
    @Transactional
    public RightsAssignmentResult sendForRightsAssignment(String batchName, Set<Long> wrWrkInsts,
                                                          Set<String> usageIds) {
        LOGGER.info("Send for Rights Assignment. Started. BatchName={}, WrWrkInstsCount={}, UsagesCount={}",
            batchName, LogUtils.size(wrWrkInsts), LogUtils.size(usageIds));
        RightsAssignmentResult result = rmsIntegrationService.sendForRightsAssignment(batchName, wrWrkInsts);
        if (result.isSuccessful()) {
            usageRepository.updateStatus(usageIds, UsageStatusEnum.SENT_FOR_RA);
            usageAuditService.logAction(usageIds, UsageActionTypeEnum.SENT_FOR_RA,
                String.format("Sent for RA: job name '%s'", result.getJobName()));
            LOGGER.info("Send for Rights Assignment. Finished. BatchName={}, WrWrkInstsCount={}, UpdatedUsagesCount={}",
                batchName, LogUtils.size(wrWrkInsts), LogUtils.size(usageIds));
        } else {
            LOGGER.warn("Send for Rights Assignment. Failed. Reason={}, UsagesIds={}", result.getErrorMessage(),
                usageIds);
        }
        return result;
    }

    @Override
    public List<Usage> getUsagesByIds(List<String> usageIds) {
        return CollectionUtils.isNotEmpty(usageIds)
            ? usageRepository.findByIds(usageIds)
            : Collections.emptyList();
    }

    @Override
    public List<Usage> getArchivedUsagesByIds(List<String> usageIds) {
        return CollectionUtils.isNotEmpty(usageIds)
            ? usageArchiveRepository.findByIds(usageIds)
            : Collections.emptyList();
    }

    @Override
    public List<Usage> getArchivedUsagesForSendToLmByIds(List<String> usageIds) {
        return CollectionUtils.isNotEmpty(usageIds)
            ? usageArchiveRepository.findForSendToLmByIds(usageIds)
            : Collections.emptyList();
    }

    @Override
    public List<String> getUsageIdsByStatusAndProductFamily(UsageStatusEnum status, String productFamily) {
        return usageRepository.findIdsByStatusAndProductFamily(status, productFamily);
    }

    @Override
    public List<Usage> getUsagesByStatusAndProductFamily(UsageStatusEnum status, String productFamily) {
        return usageRepository.findByStatusAndProductFamily(status, productFamily);
    }

    @Override
    public boolean isValidFilteredUsageStatus(UsageFilter filter, UsageStatusEnum status) {
        return usageRepository.isValidFilteredUsageStatus(filter, status);
    }

    @Override
    public void updateProcessedUsage(Usage usage) {
        String usageId = usageRepository.updateProcessedUsage(usage);
        if (Objects.isNull(usageId)) {
            // throws an exception and stops usage processing when such usage has been already consumed and processed
            throw new InconsistentUsageStateException(usage);
        }
        usage.setVersion(usage.getVersion() + 1);
    }

    @Override
    public void updateStatusAndRhAccountNumber(Set<String> usageIds, UsageStatusEnum status, Long rhAccountNumber) {
        usageRepository.updateStatusAndRhAccountNumber(usageIds, status, rhAccountNumber);
    }

    @Override
    public Map<Long, Set<String>> getWrWrkInstToUsageIdsForRightsAssignment(String batchName) {
        return usageRepository.findWrWrkInstToUsageIdsByBatchNameAndUsageStatus(batchName,
            UsageStatusEnum.RH_NOT_FOUND);
    }

    @Override
    public void sendForMatching(List<Usage> usages) {
        IChainExecutor<Usage> currentChainExecutor = chainExecutor;
        currentChainExecutor.execute(() -> {
            List<Usage> usagesInNewStatus =
                usages.stream().filter(usage -> UsageStatusEnum.NEW == usage.getStatus()).collect(Collectors.toList());
            currentChainExecutor.execute(usagesInNewStatus, ChainProcessorTypeEnum.MATCHING);
        });
    }

    @Override
    public void sendForGettingRights(List<Usage> usages, String batchName) {
        IChainExecutor<Usage> currentChainExecutor = chainExecutor;
        currentChainExecutor.execute(() -> {
            LOGGER.info("Send usages for getting rights. Started. UsageBatchName={}, UsagesCount={}", batchName,
                LogUtils.size(usages));
            List<Usage> workFoundUsages =
                usages.stream()
                    .filter(usage -> UsageStatusEnum.WORK_FOUND == usage.getStatus())
                    .collect(Collectors.toList());
            currentChainExecutor.execute(workFoundUsages, ChainProcessorTypeEnum.RIGHTS);
            LOGGER.info(
                "Send usages for getting rights. Finished. UsageBatchName={}, UsagesCount={}, WorkFoundUsagesCount={}",
                batchName, LogUtils.size(usages), LogUtils.size(workFoundUsages));
        });
    }

    @Override
    public List<RightsholderPayeeProductFamilyHolder> getRightsholderPayeeProductFamilyHoldersByScenarioIds(
        Set<String> scenarioIds) {
        return usageRepository.findRightsholderPayeeProductFamilyHoldersByScenarioIds(scenarioIds);
    }

    private void logScenarioSplitAction(String scenarioId) {
        if (!scenarioAuditService.isAuditItemExist(scenarioId, ScenarioActionTypeEnum.UPDATED_AFTER_SPLIT)) {
            scenarioAuditService.logAction(scenarioId, ScenarioActionTypeEnum.UPDATED_AFTER_SPLIT,
                "Scenario has been updated after Split process");
        }
    }

    private void populateAccountNumbers(List<PaidUsage> paidUsages) {
        Set<String> rhIds = paidUsages.stream()
            .flatMap(usage -> Stream.of(usage.getRightsholder().getId(), usage.getPayee().getId()))
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(rhIds)) {
            Map<String, Long> rhRhIdToAccountNumberMap = CollectionUtils.isNotEmpty(rhIds)
                ? rightsholderService.findAccountNumbersByRightsholderIds(rhIds)
                : new HashMap<>();
            paidUsages.forEach(usage -> {
                usage.getRightsholder().setAccountNumber(rhRhIdToAccountNumberMap.get(usage.getRightsholder().getId()));
                usage.getPayee().setAccountNumber(rhRhIdToAccountNumberMap.get(usage.getPayee().getId()));
            });
        }
    }

    private void updatePaidUsage(PaidUsage paidUsage, String actionReason) {
        paidUsage.setStatus(UsageStatusEnum.PAID);
        usageArchiveRepository.updatePaidInfo(paidUsage);
        usageAuditService.logAction(paidUsage.getId(), UsageActionTypeEnum.PAID, actionReason);
    }

    private void insertPaidUsage(PaidUsage paidUsage, String actionReason, Consumer<PaidUsage> consumer) {
        consumer.accept(paidUsage);
        usageAuditService.logAction(paidUsage.getId(), UsageActionTypeEnum.PAID, actionReason);
    }

    private PaidUsage buildPaidUsage(Usage originalUsage, PaidUsage paidUsage) {
        PaidUsage resultUsage = new PaidUsage();
        resultUsage.setId(RupPersistUtils.generateUuid());
        resultUsage.setBatchId(originalUsage.getBatchId());
        resultUsage.setStatus(UsageStatusEnum.PAID);
        resultUsage.setWrWrkInst(originalUsage.getWrWrkInst());
        resultUsage.setWorkTitle(originalUsage.getWorkTitle());
        resultUsage.setArticle(originalUsage.getArticle());
        resultUsage.setStandardNumber(originalUsage.getStandardNumber());
        resultUsage.setStandardNumberType(originalUsage.getStandardNumberType());
        resultUsage.setPublisher(originalUsage.getPublisher());
        resultUsage.setPublicationDate(originalUsage.getPublicationDate());
        resultUsage.setMarket(originalUsage.getMarket());
        resultUsage.setMarketPeriodFrom(originalUsage.getMarketPeriodFrom());
        resultUsage.setMarketPeriodTo(originalUsage.getMarketPeriodTo());
        resultUsage.setAuthor(originalUsage.getAuthor());
        resultUsage.setNumberOfCopies(originalUsage.getNumberOfCopies());
        resultUsage.setReportedValue(originalUsage.getReportedValue());
        resultUsage.setRhParticipating(originalUsage.isRhParticipating());
        resultUsage.setProductFamily(originalUsage.getProductFamily());
        resultUsage.setSystemTitle(originalUsage.getSystemTitle());
        resultUsage.setServiceFee(originalUsage.getServiceFee());
        resultUsage.setComment(originalUsage.getComment());
        resultUsage.setAaclUsage(originalUsage.getAaclUsage());
        resultUsage.setNetAmount(paidUsage.getNetAmount());
        resultUsage.setServiceFeeAmount(paidUsage.getServiceFeeAmount());
        resultUsage.setGrossAmount(paidUsage.getGrossAmount());
        resultUsage.setRightsholder(paidUsage.getRightsholder());
        resultUsage.setPayee(paidUsage.getPayee());
        resultUsage.setDistributionName(paidUsage.getDistributionName());
        resultUsage.setDistributionDate(paidUsage.getDistributionDate());
        resultUsage.setCccEventId(paidUsage.getCccEventId());
        resultUsage.setCheckNumber(paidUsage.getCheckNumber());
        resultUsage.setCheckDate(paidUsage.getCheckDate());
        resultUsage.setLmDetailId(paidUsage.getLmDetailId());
        resultUsage.setPeriodEndDate(paidUsage.getPeriodEndDate());
        return resultUsage;
    }

    private List<CrmRightsDistributionRequest> buildCrmRightsDistributionRequest(List<PaidUsage> paidUsages) {
        List<CrmRightsDistributionRequest> requests = Lists.newArrayListWithExpectedSize(paidUsages.size());
        paidUsages.forEach(paidUsage -> requests.add(new CrmRightsDistributionRequest(paidUsage)));
        return requests;
    }

    private void updateReportedUsages(Set<String> usagesIds) {
        usageArchiveRepository.updateStatus(usagesIds, UsageStatusEnum.ARCHIVED);
        usageAuditService.logAction(usagesIds, UsageActionTypeEnum.ARCHIVED, "Usage was sent to CRM");
    }

    private String buildActionReason(Long oldAccountNumber, Long newAccountNumber, String actionReason) {
        return oldAccountNumber.equals(newAccountNumber)
            ? actionReason
            : actionReason + String.format(" with RH change from %d to %d", oldAccountNumber, newAccountNumber);
    }
}
