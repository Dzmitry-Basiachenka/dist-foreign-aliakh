package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.common.util.CalculationUtils;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmResult;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmRightsDistributionRequest;
import com.copyright.rup.dist.foreign.integration.crm.api.ICrmIntegrationService;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
    private static final Logger LOGGER = RupLogUtils.getLogger();
    @Value("$RUP{dist.foreign.service_fee.cla_payee}")
    private BigDecimal claPayeeServiceFee;
    @Value("$RUP{dist.foreign.product_families}")
    private List<String> supportedProductFamilies;
    @Value("$RUP{dist.foreign.markets}")
    private List<String> supportedMarkets;
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
    private IScenarioAuditService scenarioAuditService;

    @Override
    public List<UsageDto> getUsages(UsageFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? usageRepository.findByFilter(filter, pageable, sort)
            : Collections.emptyList();
    }

    @Override
    public List<Usage> getUsagesByFilter(UsageFilter filter) {
        return usageRepository.findUsagesByFilter(filter);
    }

    @Override
    public int getUsagesCount(UsageFilter filter) {
        return !filter.isEmpty() ? usageRepository.findCountByFilter(filter) : 0;
    }

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
        String reason = "Uploaded in '" + usageBatch.getName() + "' Batch";
        usages.forEach(usage -> usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED, reason));
        LOGGER.info("Insert usages. Finished. UsageBatchName={}, UsagesCount={}, UserName={}", usageBatch.getName(),
            size, userName);
        return size;
    }

    @Override
    @Transactional
    public void insertNtsUsages(UsageBatch usageBatch, List<Usage> usages) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert NTS usages. Started. UsageBatchName={}, UsagesCount={}, UserName={}", usageBatch.getName(),
            LogUtils.size(usages), userName);
        usages.forEach(usage -> {
            usage.setId(RupPersistUtils.generateUuid());
            usage.setProductFamily(FdaConstants.NTS_PRODUCT_FAMILY);
            usage.setStatus(UsageStatusEnum.WORK_FOUND);
            usage.setBatchId(usageBatch.getId());
            usage.setCreateUser(userName);
            usage.setUpdateUser(userName);
            usageRepository.insert(usage);
        });
        String reason = buildNtsUsageReason(usageBatch);
        usageAuditService.logAction(usages.stream().map(Usage::getId).collect(Collectors.toSet()),
            UsageActionTypeEnum.CREATED, reason);
        LOGGER.info("Insert NTS usages. Finished. UsageBatchName={}, UsagesCount={}, UserName={}", usageBatch.getName(),
            LogUtils.size(usages), userName);
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
        usageAuditService.deleteActionsByBatchId(batchId);
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
        List<Usage> usages = usageRepository.findWithAmountsAndRightsholders(filter);
        usages.forEach(usage -> {
            boolean rhParticipatingFlag =
                prmIntegrationService.isRightsholderParticipating(usage.getRightsholder().getId(),
                    usage.getProductFamily());
            CalculationUtils.recalculateAmounts(usage, rhParticipatingFlag,
                prmIntegrationService.getRhParticipatingServiceFee(rhParticipatingFlag));
        });
        return usages;
    }

    @Override
    public int getUsagesCountForNtsBatch(UsageBatch usageBatch) {
        FundPool fundPool = usageBatch.getFundPool();
        return usageArchiveRepository.findCountForNtsBatch(fundPool.getFundPoolPeriodFrom(),
            fundPool.getFundPoolPeriodTo(), fundPool.getMarkets());
    }

    @Override
    public List<Usage> getUsagesForNtsBatch(UsageBatch usageBatch) {
        FundPool fundPool = usageBatch.getFundPool();
        return usageArchiveRepository.findForNtsBatch(fundPool.getFundPoolPeriodFrom(),
            fundPool.getFundPoolPeriodTo(), fundPool.getMarkets());
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
        Table<String, String, Long> rollUps = prmIntegrationService.getRollUps(rightsholdersIds);
        newUsages.forEach(usage -> {
            final String rightsholderId = usage.getRightsholder().getId();
            final long rhAccountNumber = usage.getRightsholder().getAccountNumber();
            Usage scenarioUsage = rhToUsageMap.get(rhAccountNumber);
            usage.getPayee().setAccountNumber(null == scenarioUsage
                ? PrmRollUpService.getPayeeAccountNumber(rollUps, usage.getRightsholder(), usage.getProductFamily())
                : scenarioUsage.getPayee().getAccountNumber());
            boolean rhParticipating = null == scenarioUsage
                ? prmIntegrationService.isRightsholderParticipating(rightsholderId, usage.getProductFamily())
                : scenarioUsage.isRhParticipating();
            addScenarioInfo(usage, scenario);
            CalculationUtils.recalculateAmounts(usage, rhParticipating,
                FdaConstants.CLA_ACCOUNT_NUMBER.equals(usage.getPayee().getAccountNumber()) ? claPayeeServiceFee
                    : prmIntegrationService.getRhParticipatingServiceFee(rhParticipating));
        });
        usageRepository.addToScenario(newUsages);
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
        Table<String, String, Long> rollUps = prmIntegrationService.getRollUps(
            usages.stream().map(usage -> usage.getRightsholder().getId()).collect(Collectors.toSet()));
        usages.forEach(usage -> {
            usage.getPayee().setAccountNumber(
                PrmRollUpService.getPayeeAccountNumber(rollUps, usage.getRightsholder(), usage.getProductFamily()));
            addScenarioInfo(usage, scenario);
            //usages that have CLA as Payee should get 10% service fee
            if (FdaConstants.CLA_ACCOUNT_NUMBER.equals(usage.getPayee().getAccountNumber())) {
                CalculationUtils.recalculateAmounts(usage, usage.isRhParticipating(), claPayeeServiceFee);
            }
        });
        usageRepository.addToScenario(usages);
    }

    @Override
    public void updateRhPayeeAndAmounts(List<Usage> usages) {
        String userName = RupContextUtils.getUserName();
        usages.forEach(usage -> {
            boolean rhParticipatingFlag =
                prmIntegrationService.isRightsholderParticipating(usage.getRightsholder().getId(),
                    usage.getProductFamily());
            CalculationUtils.recalculateAmounts(usage, rhParticipatingFlag,
                prmIntegrationService.getRhParticipatingServiceFee(rhParticipatingFlag));
            usage.setUpdateUser(userName);
        });
        usageRepository.update(usages);
    }

    @Override
    public void deleteFromScenario(String scenarioId) {
        usageRepository.deleteFromScenario(scenarioId, RupContextUtils.getUserName());
    }

    @Override
    @Transactional
    public void deleteFromScenario(Scenario scenario, Long rroAccountNumber, List<Long> accountNumbers, String reason) {
        List<String> usagesIds =
            usageRepository.findIdsByScenarioIdRroAccountNumberRhAccountNumbers(scenario.getId(), rroAccountNumber,
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
    public List<Usage> moveToArchive(Scenario scenario) {
        LOGGER.info("Move details to archive. Started. {}", ForeignLogUtils.scenario(scenario));
        List<Usage> usages = usageRepository.findByScenarioId(scenario.getId());
        usages.forEach(usage -> {
            usage.setStatus(UsageStatusEnum.SENT_TO_LM);
            usageArchiveRepository.insert(usage);
        });
        usageRepository.deleteByScenarioId(scenario.getId());
        LOGGER.info("Move details to archive. Finished. {}, UsagesCount={}", ForeignLogUtils.scenario(scenario),
            usages.size());
        return usages;
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
    public List<String> getProductFamilies() {
        return supportedProductFamilies;
    }

    @Override
    public List<String> getProductFamiliesForAudit() {
        return supportedProductFamilies;
    }

    @Override
    public List<String> getMarkets() {
        return supportedMarkets;
    }

    @Override
    @Transactional
    public void updatePaidInfo(List<PaidUsage> paidUsages) {
        LOGGER.info("Update paid information. Started. UsagesCount={}", LogUtils.size(paidUsages));
        AtomicInteger newUsagesCount = new AtomicInteger();
        Set<String> notFoundUsageIds = Sets.newHashSet();
        Map<String, Usage> usageIdToUsageMap = usageArchiveRepository.findUsageInformationById(
            paidUsages.stream().map(PaidUsage::getId).collect(Collectors.toList()))
            .stream()
            .collect(Collectors.toMap(Usage::getId, usage -> usage));
        paidUsages.forEach(paidUsage -> {
            String paidUsageId = paidUsage.getId();
            if (Objects.nonNull(usageIdToUsageMap.get(paidUsageId))) {
                if (paidUsage.isPostDistributionFlag()) {
                    String actionReason = Objects.nonNull(paidUsage.getSplitParentFlag())
                        ? "Usage has been created based on Post-Distribution and Split processes"
                        : "Usage has been created based on Post-Distribution process";
                    insertPaidUsage(buildPaidUsage(usageIdToUsageMap.get(paidUsageId), paidUsage), actionReason);
                    newUsagesCount.getAndIncrement();
                } else if (Objects.isNull(paidUsage.getSplitParentFlag())) {
                    updatePaidUsage(paidUsage, "Usage has been paid according to information from the LM");
                } else if (paidUsage.getSplitParentFlag()) {
                    updatePaidUsage(paidUsage, "Usage has been adjusted based on Split process");
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
        if (CollectionUtils.isNotEmpty(notFoundUsageIds)) {
            LOGGER.warn("Update paid information. Not found usages. UsagesCount={}, CreatedCount={}, " +
                    "UpdatedCount={}, NotFoundUsageIds={}", LogUtils.size(paidUsages), newUsagesCount,
                paidUsages.size() - newUsagesCount.get() - notFoundUsageIds.size(), notFoundUsageIds);
        }
        LOGGER.info("Update paid information. Finished. UsagesCount={}, CreatedCount={}, UpdatedCount={}",
            LogUtils.size(paidUsages), newUsagesCount,
            paidUsages.size() - newUsagesCount.get() - notFoundUsageIds.size());
    }

    @Override
    @Transactional
    public void loadResearchedUsages(Collection<ResearchedUsage> researchedUsages) {
        LOGGER.info("Load researched usages. Started. ResearchedUsagesCount={}", LogUtils.size(researchedUsages));
        usageRepository.updateResearchedUsages(researchedUsages);
        researchedUsages.forEach(
            researchedUsage -> usageAuditService.logAction(researchedUsage.getUsageId(),
                UsageActionTypeEnum.WORK_FOUND,
                String.format("Wr Wrk Inst %s was added based on research", researchedUsage.getWrWrkInst()))
        );
        LOGGER.info("Load researched usages. Finished. ResearchedUsagesCount={}", LogUtils.size(researchedUsages));
    }

    @Override
    public void sendToCrm() {
        List<String> paidUsagesIds = usageArchiveRepository.findPaidIds();
        LOGGER.info("Send to CRM. Started. PaidUsagesCount={}", LogUtils.size(paidUsagesIds));
        int archivedUsagesCount = 0;
        if (CollectionUtils.isNotEmpty(paidUsagesIds)) {
            Set<String> invalidUsageIds = Sets.newHashSet();
            for (List<String> ids : Iterables.partition(paidUsagesIds, 128)) {
                List<PaidUsage> paidUsages = usageArchiveRepository.findByIdAndStatus(ids, UsageStatusEnum.PAID);
                if (CollectionUtils.isNotEmpty(paidUsages)) {
                    CrmResult result =
                        crmIntegrationService.sendRightsDistributionRequests(
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
            LOGGER.info(SEND_TO_CRM_FINISHED_INFO_LOG_MESSAGE, LogUtils.size(paidUsagesIds), archivedUsagesCount,
                invalidUsageIds.size(), archivedScenariosCount);
            LOGGER.trace(SEND_TO_CRM_FINISHED_DEBUG_LOG_MESSAGE, LogUtils.size(paidUsagesIds), archivedUsagesCount,
                archivedScenariosCount, invalidUsageIds);
        } else {
            LOGGER.info("Send to CRM. Skipped. PaidUsagesCount={}, Reason=There are no usages to report",
                LogUtils.size(paidUsagesIds));
        }
    }

    @Override
    public List<Usage> getUsagesByStatus(UsageStatusEnum status, int limit, int offset) {
        return usageRepository.findByStatus(status, limit, offset);
    }

    @Override
    public boolean isValidUsagesState(UsageFilter filter, UsageStatusEnum status) {
        return usageRepository.isValidUsagesState(filter, status);
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

    private PaidUsage buildPaidUsage(Usage originalUsage, PaidUsage paidUsage) {
        PaidUsage resultUsage = new PaidUsage();
        resultUsage.setId(RupPersistUtils.generateUuid());
        resultUsage.setStatus(UsageStatusEnum.PAID);
        resultUsage.setWrWrkInst(originalUsage.getWrWrkInst());
        resultUsage.setWorkTitle(originalUsage.getWorkTitle());
        resultUsage.setArticle(originalUsage.getArticle());
        resultUsage.setStandardNumber(originalUsage.getStandardNumber());
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

    private String buildNtsUsageReason(UsageBatch usageBatch) {
        FundPool fundPool = usageBatch.getFundPool();
        StringBuilder reasonBuilder = new StringBuilder(64);
        reasonBuilder.append("Usage was created based on Market(s): ");
        fundPool.getMarkets().forEach(market -> reasonBuilder.append("'").append(market).append("', "));
        reasonBuilder.append(
            String.format("Fund Pool Period: %s-%s", fundPool.getFundPoolPeriodFrom(), fundPool.getFundPoolPeriodTo()));
        return reasonBuilder.toString();
    }
}
