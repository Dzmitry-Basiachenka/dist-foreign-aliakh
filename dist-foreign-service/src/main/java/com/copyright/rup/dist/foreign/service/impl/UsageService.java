package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.common.integration.rest.rms.RightsAssignmentResult;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.domain.common.util.CalculationUtils;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmResult;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmRightsDistributionRequest;
import com.copyright.rup.dist.foreign.integration.crm.api.ICrmIntegrationService;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
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
import com.google.common.collect.Table;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
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
    private static final String CALCULATION_FINISHED_LOG_MESSAGE = "Calculated usages gross amount. " +
        "UsageBatchName={}, FundPoolAmount={}, TotalAmount={}, ConversionRate={}";
    private static final String SEND_TO_CRM_FINISHED_INFO_LOG_MESSAGE = "Send to CRM. Finished. PaidUsagesCount={}, " +
        "ArchivedUsagesCount={}, NotReportedUsagesCount={}, ArchivedScenariosCount={}";
    private static final String SEND_TO_CRM_FINISHED_DEBUG_LOG_MESSAGE = "Send to CRM. Finished. PaidUsagesCount={}, " +
        "ArchivedUsagesCount={}, ArchivedScenariosCount={}, NotReportedUsageIds={}";
    private static final String UPDATE_PAID_NOT_FOUND_WARN_LOG_MESSAGE = "Update paid information. Not found usages. " +
        "UsagesCount={}, CreatedCount={}, UpdatedCount={}, NotFoundUsageIds={}";
    private static final Logger LOGGER = RupLogUtils.getLogger();
    @Value("$RUP{dist.foreign.service_fee.cla_payee}")
    private BigDecimal claPayeeServiceFee;
    @Value("$RUP{dist.foreign.markets}")
    private List<String> supportedMarkets;
    @Value("$RUP{dist.foreign.cla_account_number}")
    private Long claAccountNumber;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;
    @Autowired
    private ICrmIntegrationService crmIntegrationService;
    @Autowired
    private IRmsIntegrationService rmsIntegrationService;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private IChainExecutor<Usage> chainExecutor;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    @Qualifier("df.integration.piIntegrationCacheService")
    private IPiIntegrationService piIntegrationService;

    @Override
    public int insertUsages(UsageBatch usageBatch, Collection<Usage> usages) {
        String userName = RupContextUtils.getUserName();
        int size = usages.size();
        LOGGER.info("Insert usages. Started. UsageBatchName={}, UsagesCount={}, UserName={}", usageBatch.getName(),
            size, userName);
        calculateUsagesGrossAmount(usageBatch, usages);
        usages.forEach(usage -> {
            usage.setBatchId(usageBatch.getId());
            usage.setCreateUser(userName);
            usage.setUpdateUser(userName);
            usageRepository.insert(usage);
        });
        // Adding data to audit table in separate loop increases performance up to 3 times
        // while using batch with 200000 usages
        String loadedReason = "Uploaded in '" + usageBatch.getName() + "' Batch";
        String eligibleReason = "Usage was uploaded with Wr Wrk Inst and RH account number";
        String workFoundReason = "Usage was uploaded with Wr Wrk Inst";
        usages.forEach(usage -> {
            usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED, loadedReason);
            if (UsageStatusEnum.ELIGIBLE == usage.getStatus()) {
                usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.ELIGIBLE, eligibleReason);
            }
            if (UsageStatusEnum.WORK_FOUND == usage.getStatus()) {
                usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND, workFoundReason);
            }
        });
        LOGGER.info("Insert usages. Finished. UsageBatchName={}, UsagesCount={}, UserName={}", usageBatch.getName(),
            size, userName);
        return size;
    }

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
    public List<Usage> getUsagesWithAmounts(UsageFilter filter) {
        return usageRepository.findWithAmountsAndRightsholders(filter);
    }

    @Override
    public int getUsagesCountForNtsBatch(UsageBatch usageBatch) {
        FundPool fundPool = usageBatch.getFundPool();
        return usageArchiveRepository.findCountForNtsBatch(fundPool.getFundPoolPeriodFrom(),
            fundPool.getFundPoolPeriodTo(), fundPool.getMarkets());
    }

    @Override
    public List<String> updateNtsWithdrawnUsagesAndGetIds() {
        return usageRepository.updateNtsWithdrawnUsagesAndGetIds();
    }

    @Override
    public List<Long> getInvalidRightsholdersByFilter(UsageFilter filter) {
        return usageRepository.findInvalidRightsholdersByFilter(filter);
    }

    @Override
    public void recalculateUsagesForRefresh(UsageFilter filter, Scenario scenario) {
        Map<Long, Usage> rhToUsageMap = getRightsholdersInformation(scenario.getId());
        List<Usage> newUsages = usageRepository.findWithAmountsAndRightsholders(filter);
        Set<String> rightsholdersIds = newUsages.stream().map(usage -> usage.getRightsholder().getId())
            .collect(Collectors.toSet());
        rightsholdersIds.removeAll(
            rhToUsageMap.values().stream().map(usage -> usage.getRightsholder().getId()).collect(Collectors.toSet()));
        Map<String, Map<String, Rightsholder>> rollUps = prmIntegrationService.getRollUps(rightsholdersIds);
        rightsholdersIds.addAll(getPayeeAndRhIds(rollUps));
        Map<String, Table<String, String, Object>> preferencesMap =
            prmIntegrationService.getPreferences(rightsholdersIds);
        newUsages.forEach(usage -> {
            final long rhAccountNumber = usage.getRightsholder().getAccountNumber();
            Usage scenarioUsage = rhToUsageMap.get(rhAccountNumber);
            Rightsholder payee = Objects.isNull(scenarioUsage)
                ? PrmRollUpService.getPayee(rollUps, usage.getRightsholder(), usage.getProductFamily())
                : scenarioUsage.getPayee();
            usage.setPayee(payee);
            addScenarioInfo(usage, scenario);
            calculateAmounts(usage, Objects.isNull(scenarioUsage)
                ? prmIntegrationService.isRightsholderParticipating(preferencesMap,
                usage.getRightsholder().getId(), usage.getProductFamily())
                : scenarioUsage.isRhParticipating());
            fillPayeeParticipatingForRefresh(preferencesMap, usage, scenarioUsage);
        });
        usageRepository.addToScenario(newUsages);
        rightsholderService.updateUsagesPayeesAsync(newUsages);
    }

    @Override
    public List<Usage> getUsagesByScenarioId(String scenarioId) {
        return usageRepository.findByScenarioId(scenarioId);
    }

    @Override
    public List<Usage> getUsagesForReconcile(String scenarioId) {
        return usageRepository.findForReconcile(scenarioId);
    }

    @Override
    public Map<Long, Usage> getRightsholdersInformation(String scenarioId) {
        return usageRepository.findRightsholdersInformation(scenarioId);
    }

    @Override
    public void addUsagesToScenario(List<Usage> usages, Scenario scenario) {
        Set<String> rightsholdersIds =
            usages.stream().map(usage -> usage.getRightsholder().getId()).collect(Collectors.toSet());
        Map<String, Map<String, Rightsholder>> rollUps = prmIntegrationService.getRollUps(rightsholdersIds);
        rightsholdersIds.addAll(getPayeeAndRhIds(rollUps));
        Map<String, Table<String, String, Object>> preferencesMap =
            prmIntegrationService.getPreferences(rightsholdersIds);
        usages.forEach(usage -> {
            usage.setPayee(PrmRollUpService.getPayee(rollUps, usage.getRightsholder(), usage.getProductFamily()));
            addScenarioInfo(usage, scenario);
            calculateAmounts(usage,
                prmIntegrationService.isRightsholderParticipating(preferencesMap, usage.getRightsholder().getId(),
                    usage.getProductFamily()));
            fillPayeeParticipating(preferencesMap, usage);
        });
        usageRepository.addToScenario(usages);
        rightsholderService.updateUsagesPayeesAsync(usages);
    }

    @Override
    @Transactional
    public void populatePayeeAndCalculateAmountsForNtsScenarioUsages(Scenario scenario) {
        String userName = RupContextUtils.getUserName();
        Set<Long> payeeAccountNumbers = new HashSet<>();
        List<Rightsholder> rightsholders = rightsholderService.getByScenarioId(scenario.getId());
        Set<String> rightsholdersIds = rightsholders.stream().map(BaseEntity::getId).collect(Collectors.toSet());
        Map<String, Map<String, Rightsholder>> rollUps = prmIntegrationService.getRollUps(rightsholdersIds);
        Map<String, Table<String, String, Object>> preferencesMap =
            prmIntegrationService.getPreferences(rightsholdersIds);
        rightsholders.forEach(rightsholder -> {
            Long payeeAccountNumber = PrmRollUpService.getPayee(rollUps, rightsholder, FdaConstants.NTS_PRODUCT_FAMILY)
                .getAccountNumber();
            payeeAccountNumbers.add(payeeAccountNumber);
            boolean rhParticipating =
                prmIntegrationService.isRightsholderParticipating(preferencesMap, rightsholder.getId(),
                    FdaConstants.NTS_PRODUCT_FAMILY);
            BigDecimal serviceFee = prmIntegrationService.getRhParticipatingServiceFee(rhParticipating);
            usageRepository.calculateAmountsAndUpdatePayeeByAccountNumber(rightsholder.getAccountNumber(),
                scenario.getId(), serviceFee, rhParticipating, payeeAccountNumber, userName);
        });
        if (0 != BigDecimal.ZERO.compareTo(scenario.getNtsFields().getPostServiceFeeAmount())) {
            usageRepository.applyPostServiceFeeAmount(scenario.getId());
        }
        rightsholderService.updateRighstholdersAsync(payeeAccountNumbers);
    }

    @Override
    public void updateRhPayeeAmountsAndParticipating(List<Usage> usages) {
        String userName = RupContextUtils.getUserName();
        Map<String, Table<String, String, Object>> preferencesMap =
            prmIntegrationService.getPreferences(usages.stream()
                .flatMap(usage -> Stream.of(usage.getRightsholder().getId(), usage.getPayee().getId()))
                .collect(Collectors.toSet()));
        usages.forEach(usage -> {
            calculateAmounts(usage,
                prmIntegrationService.isRightsholderParticipating(preferencesMap, usage.getRightsholder().getId(),
                    usage.getProductFamily()));
            usage.setUpdateUser(userName);
            fillPayeeParticipating(preferencesMap, usage);
        });
        usageRepository.update(usages);
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
    @Transactional
    public void deleteFromScenarioByPayees(String scenarioId, Set<Long> accountNumbers, String reason) {
        Set<String> usageIds = usageRepository
            .deleteFromScenarioByPayees(scenarioId, accountNumbers, RupContextUtils.getUserName());
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, reason);
    }

    @Override
    @Transactional
    public void redesignateToNtsWithdrawnByPayees(String scenarioId, Set<Long> accountNumbers, String reason) {
        Set<String> usageIds = usageRepository
            .redesignateToNtsWithdrawnByPayees(scenarioId, accountNumbers, RupContextUtils.getUserName());
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, reason);
    }

    @Override
    public boolean isUsageIdExists(String usageId, UsageStatusEnum statusEnum) {
        return 0 != usageRepository.findCountByUsageIdAndStatus(usageId, statusEnum);
    }

    @Override
    @Transactional
    public List<String> moveToArchive(Scenario scenario) {
        LOGGER.info("Move details to archive. Started. {}", ForeignLogUtils.scenario(scenario));
        List<String> usageIds;
        if (FdaConstants.NTS_PRODUCT_FAMILY.equals(scenario.getProductFamily())) {
            usageIds =
                usageArchiveRepository.copyNtsToArchiveByScenarioId(scenario.getId(), RupContextUtils.getUserName());
            usageArchiveRepository.moveFundUsagesToArchive(scenario.getId());
            usageAuditService.deleteActionsByScenarioId(scenario.getId());
            usageRepository.deleteNtsByScenarioId(scenario.getId());
        } else {
            usageIds =
                usageArchiveRepository.copyToArchiveByScenarioId(scenario.getId(), RupContextUtils.getUserName());
            usageRepository.deleteByScenarioId(scenario.getId());
        }
        LOGGER.info("Move details to archive. Finished. {}, UsagesCount={}", ForeignLogUtils.scenario(scenario),
            LogUtils.size(usageIds));
        return usageIds;
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
    public List<PayeeTotalHolder> getPayeeTotalHoldersByScenarioId(String scenarioId) {
        return usageRepository.findPayeeTotalHoldersByScenarioId(scenarioId);
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
    public int getAuditItemsCount(AuditFilter filter) {
        return usageRepository.findCountForAudit(filter);
    }

    @Override
    public List<UsageDto> getForAudit(AuditFilter filter, Pageable pageable, Sort sort) {
        return usageRepository.findForAudit(filter, pageable, sort);
    }

    @Override
    public List<String> getMarkets() {
        return supportedMarkets;
    }

    @Override
    public Long getClaAccountNumber() {
        return claAccountNumber;
    }

    @Override
    @Transactional
    public void updatePaidInfo(List<PaidUsage> paidUsages) {
        LogUtils.ILogWrapper paidUsagesCount = LogUtils.size(paidUsages);
        LOGGER.info("Update paid information. Started. UsagesCount={}", paidUsagesCount);
        populateAccountNumbers(paidUsages);
        AtomicInteger newUsagesCount = new AtomicInteger();
        Set<String> notFoundUsageIds = Sets.newHashSet();
        Map<String, Usage> usageIdToUsageMap = usageArchiveRepository.findByIds(
            paidUsages.stream().map(PaidUsage::getId).collect(Collectors.toList()))
            .stream()
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
                    insertPaidUsage(buildPaidUsage(usageIdToUsageMap.get(paidUsageId), paidUsage), actionReason);
                    newUsagesCount.getAndIncrement();
                } else if (Objects.isNull(paidUsage.getSplitParentFlag())) {
                    updatePaidUsage(paidUsage, buildActionReason(oldAccountNumber, newAccountNumber,
                        "Usage has been paid according to information from the LM"));
                } else if (paidUsage.getSplitParentFlag()) {
                    updatePaidUsage(paidUsage, buildActionReason(oldAccountNumber, newAccountNumber,
                        "Usage has been adjusted based on Split process"));
                    scenarioAuditService.logAction(usageIdToUsageMap.get(paidUsageId).getScenarioId(),
                        ScenarioActionTypeEnum.UPDATED_AFTER_SPLIT,
                        "Scenario has been updated after Split process");
                } else {
                    insertPaidUsage(buildPaidUsage(usageIdToUsageMap.get(paidUsageId), paidUsage),
                        "Usage has been created based on Split process");
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
    public void loadResearchedUsages(List<ResearchedUsage> researchedUsages) {
        LogUtils.ILogWrapper researchedUsagesCount = LogUtils.size(researchedUsages);
        LOGGER.info("Load researched usages. Started. ResearchedUsagesCount={}", researchedUsagesCount);
        populateTitlesStandardNumberAndType(researchedUsages);
        markAsWorkFound(researchedUsages);
        List<String> usageIds = researchedUsages.stream()
            .map(ResearchedUsage::getUsageId)
            .collect(Collectors.toList());
        chainExecutor.execute(usageRepository.findByIds(usageIds), ChainProcessorTypeEnum.RIGHTS);
        LOGGER.info("Load researched usages. Finished. ResearchedUsagesCount={}", researchedUsagesCount);
    }

    @Override
    @Transactional
    public void markAsWorkFound(List<ResearchedUsage> researchedUsages) {
        usageRepository.updateResearchedUsages(researchedUsages);
        researchedUsages.forEach(
            researchedUsage -> usageAuditService.logAction(researchedUsage.getUsageId(),
                UsageActionTypeEnum.WORK_FOUND,
                String.format("Wr Wrk Inst %s was added based on research", researchedUsage.getWrWrkInst()))
        );
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
                        crmIntegrationService.insertRightsDistribution(
                            buildCrmRightsDistributionRequest(paidUsages));
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
    public int getUnclassifiedUsagesCount(Set<Long> wrWrkInsts) {
        return usageRepository.findCountByStatusAndWrWrkInsts(UsageStatusEnum.UNCLASSIFIED, wrWrkInsts);
    }

    @Override
    public Map<Long, Set<String>> getWrWrkInstToUsageIdsForRightsAssignment(String batchName) {
        return usageRepository.findWrWrkInstToUsageIdsByBatchNameAndUsageStatus(batchName,
            UsageStatusEnum.RH_NOT_FOUND);
    }

    @Override
    public void addWithdrawnUsagesToPreServiceFeeFund(String fundId, Set<String> batchIds, String userName) {
        usageRepository.addWithdrawnUsagesToPreServiceFeeFund(fundId, batchIds, userName);
    }

    private Set<String> getPayeeAndRhIds(Map<String, Map<String, Rightsholder>> rollUps) {
        return rollUps.values()
            .stream()
            .flatMap(map -> map.values().stream().map(Rightsholder::getId))
            .collect(Collectors.toSet());
    }

    private void populateTitlesStandardNumberAndType(Collection<ResearchedUsage> researchedUsages) {
        researchedUsages.forEach(researchedUsage -> {
            Work work = piIntegrationService.findWorkByWrWrkInst(researchedUsage.getWrWrkInst());
            researchedUsage.setStandardNumberType(work.getMainIdnoType());
            researchedUsage.setStandardNumber(work.getMainIdno());
            researchedUsage.setSystemTitle(work.getMainTitle());
        });
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

    private void insertPaidUsage(PaidUsage paidUsage, String actionReason) {
        usageArchiveRepository.insertPaid(paidUsage);
        usageAuditService.logAction(paidUsage.getId(), UsageActionTypeEnum.PAID, actionReason);
    }

    private void calculateAmounts(Usage usage, boolean rhParticipatingFlag) {
        //usages that have CLA as Payee should get 10% service fee
        CalculationUtils.recalculateAmounts(usage, rhParticipatingFlag,
            claAccountNumber.equals(usage.getPayee().getAccountNumber())
                && FdaConstants.CLA_FAS_PRODUCT_FAMILY.equals(usage.getProductFamily())
                ? claPayeeServiceFee
                : prmIntegrationService.getRhParticipatingServiceFee(rhParticipatingFlag));
    }

    private PaidUsage buildPaidUsage(Usage originalUsage, PaidUsage paidUsage) {
        PaidUsage resultUsage = new PaidUsage();
        resultUsage.setId(RupPersistUtils.generateUuid());
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

    private void calculateUsagesGrossAmount(UsageBatch usageBatch, Collection<Usage> usages) {
        BigDecimal fundPoolAmount = usageBatch.getGrossAmount();
        BigDecimal totalAmount = usages.stream().map(Usage::getReportedValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal conversionRate = CalculationUtils.calculateConversionRate(fundPoolAmount, totalAmount);
        usages.forEach(usage -> usage.setGrossAmount(
            CalculationUtils.calculateUsdAmount(usage.getReportedValue(), conversionRate)));
        LOGGER.info(CALCULATION_FINISHED_LOG_MESSAGE, usageBatch.getName(), usageBatch.getGrossAmount(), totalAmount,
            conversionRate);
    }

    private void addScenarioInfo(Usage usage, Scenario scenario) {
        usage.setScenarioId(scenario.getId());
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.setUpdateUser(scenario.getCreateUser());
    }

    private String buildActionReason(Long oldAccountNumber, Long newAccountNumber, String actionReason) {
        return oldAccountNumber.equals(newAccountNumber)
            ? actionReason
            : actionReason + String.format(" with RH change from %d to %d", oldAccountNumber, newAccountNumber);
    }

    private void fillPayeeParticipating(Map<String, Table<String, String, Object>> preferencesMap, Usage usage) {
        boolean payeeParticipating = !usage.getRightsholder().getId().equals(usage.getPayee().getId())
            ? prmIntegrationService.isRightsholderParticipating(preferencesMap, usage.getPayee().getId(),
            usage.getProductFamily())
            : usage.isRhParticipating();
        usage.setPayeeParticipating(payeeParticipating);
    }

    private void fillPayeeParticipatingForRefresh(Map<String, Table<String, String, Object>> preferencesMap,
                                                  Usage newUsage, Usage scenarioUsage) {
        if (Objects.nonNull(scenarioUsage)) {
            newUsage.setPayeeParticipating(scenarioUsage.isPayeeParticipating());
        } else {
            fillPayeeParticipating(preferencesMap, newUsage);
        }
    }
}
