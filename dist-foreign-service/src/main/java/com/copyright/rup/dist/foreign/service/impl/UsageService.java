package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
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
    private static final String UPDATE_PAID_INFO_FAILED_LOG_MESSAGE = "Update paid information. Not found usages. " +
        "UsagesCount={}, UpdatedCount={}, NotFoundUsageIds={}";
    private static final String SEND_TO_CRM_FINISHED_INFO_LOG_MESSAGE = "Send to CRM. Finished. PaidUsagesCount={}, " +
        "ArchivedUsagesCount={}, NotReportedUsagesCount={}, ArchivedScenariosCount={}";
    private static final String SEND_TO_CRM_FINISHED_DEBUG_LOG_MESSAGE = "Send to CRM. Finished. PaidUsagesCount={}, " +
        "ArchivedUsagesCount={}, ArchivedScenariosCount={}, NotReportedUsageIds={}";
    private static final Logger LOGGER = RupLogUtils.getLogger();
    @Value("$RUP{dist.foreign.service_fee.cla_payee}")
    private BigDecimal claPayeeServiceFee;
    @Value("$RUP{dist.foreign.product_families}")
    private List<String> supportedProductFamilies;
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

    @Override
    public List<UsageDto> getUsages(UsageFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? usageRepository.findByFilter(filter, pageable, sort)
            : Collections.emptyList();
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
    public void deleteUsageBatchDetails(UsageBatch usageBatch) {
        usageAuditService.deleteActions(usageBatch.getId());
        usageRepository.deleteUsages(usageBatch.getId());
    }

    @Override
    public List<Usage> getUsagesWithAmounts(UsageFilter filter) {
        List<Usage> usages = usageRepository.findWithAmountsAndRightsholders(filter);
        usages.forEach(usage -> {
            boolean rhParticipatingFlag =
                prmIntegrationService.isRightsholderParticipating(usage.getRightsholder().getAccountNumber(),
                    usage.getProductFamily());
            CalculationUtils.recalculateAmounts(usage, rhParticipatingFlag,
                prmIntegrationService.getRhParticipatingServiceFee(rhParticipatingFlag));
        });
        return usages;
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
            final long rhAccountNumber = usage.getRightsholder().getAccountNumber();
            Usage scenarioUsage = rhToUsageMap.get(rhAccountNumber);
            usage.getPayee().setAccountNumber(null == scenarioUsage
                ? PrmRollUpService.getPayeeAccountNumber(rollUps, usage.getRightsholder(), usage.getProductFamily())
                : scenarioUsage.getPayee().getAccountNumber());
            boolean rhParticipating = null == scenarioUsage
                ? prmIntegrationService.isRightsholderParticipating(rhAccountNumber, usage.getProductFamily())
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
                prmIntegrationService.isRightsholderParticipating(usage.getRightsholder().getAccountNumber(),
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
    @Transactional
    public void updatePaidInfo(List<PaidUsage> paidUsages) {
        LOGGER.info("Update paid information. Started. UsagesCount={}", LogUtils.size(paidUsages));
        AtomicInteger postDistributionUsagesCount = new AtomicInteger();
        Set<String> notFoundUsageIds = Sets.newHashSet();
        Map<String, Usage> usageIdToUsageMap = usageArchiveRepository.findUsageInformationById(
            paidUsages.stream().map(PaidUsage::getId).collect(Collectors.toList()))
            .stream()
            .collect(Collectors.toMap(Usage::getId, usage -> usage));
        paidUsages.forEach(paidUsage -> {
            String paidUsageId = paidUsage.getId();
            if (Objects.nonNull(usageIdToUsageMap.get(paidUsageId))) {
                if (isPostDistributionUsage(paidUsage, usageIdToUsageMap)) {
                    PaidUsage postDistributionUsage =
                        buildPostDistributionUsage(usageIdToUsageMap.get(paidUsageId), paidUsage);
                    usageArchiveRepository.insertPaid(postDistributionUsage);
                    usageAuditService.logAction(postDistributionUsage.getId(), UsageActionTypeEnum.PAID,
                        "Usage has been created based on Post-Distribution process");
                    postDistributionUsagesCount.getAndIncrement();
                } else {
                    paidUsage.setStatus(UsageStatusEnum.PAID);
                    usageArchiveRepository.updatePaidInfo(paidUsage);
                    usageAuditService.logAction(paidUsage.getId(), UsageActionTypeEnum.PAID,
                        "Usage has been paid according to information from the LM");
                }
            } else {
                notFoundUsageIds.add(paidUsage.getId());
            }
        });
        if (CollectionUtils.isNotEmpty(notFoundUsageIds)) {
            LOGGER.warn(UPDATE_PAID_INFO_FAILED_LOG_MESSAGE, LogUtils.size(paidUsages),
                paidUsages.size() - notFoundUsageIds.size(), notFoundUsageIds);
        }
        LOGGER.info("Update paid information. Finished. UsagesCount={}, PostDistributionUsageCount={}, UpdatedCount={}",
            LogUtils.size(paidUsages), postDistributionUsagesCount, paidUsages.size() - notFoundUsageIds.size());
    }

    @Override
    public int getStandardNumbersCount() {
        return usageRepository.findStandardNumbersCount();
    }

    @Override
    public int getTitlesCount() {
        return usageRepository.findTitlesCount();
    }

    @Override
    public List<Usage> getUsagesWithStandardNumber(int limit, int offset) {
        return usageRepository.findWithStandardNumber(limit, offset);
    }

    @Override
    public List<Usage> getUsagesWithTitle(int limit, int offset) {
        return usageRepository.findWithTitle(limit, offset);
    }

    @Override
    public List<Usage> getUsagesWithoutStandardNumberAndTitle() {
        return usageRepository.findWithoutStandardNumberAndTitle();
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

    private boolean isPostDistributionUsage(PaidUsage paidUsage, Map<String, Usage> usageIdToUsageMap) {
        return UsageStatusEnum.SENT_TO_LM != usageIdToUsageMap.get(paidUsage.getId()).getStatus();
    }

    private PaidUsage buildPostDistributionUsage(Usage originalUsage, PaidUsage paidUsage) {
        PaidUsage postDistributionUsage = new PaidUsage();
        postDistributionUsage.setId(RupPersistUtils.generateUuid());
        postDistributionUsage.setStatus(UsageStatusEnum.PAID);
        postDistributionUsage.setWrWrkInst(originalUsage.getWrWrkInst());
        postDistributionUsage.setWorkTitle(originalUsage.getWorkTitle());
        postDistributionUsage.setRightsholder(originalUsage.getRightsholder());
        postDistributionUsage.setArticle(originalUsage.getArticle());
        postDistributionUsage.setStandardNumber(originalUsage.getStandardNumber());
        postDistributionUsage.setPublisher(originalUsage.getPublisher());
        postDistributionUsage.setPublicationDate(originalUsage.getPublicationDate());
        postDistributionUsage.setMarket(originalUsage.getMarket());
        postDistributionUsage.setMarketPeriodFrom(originalUsage.getMarketPeriodFrom());
        postDistributionUsage.setMarketPeriodTo(originalUsage.getMarketPeriodTo());
        postDistributionUsage.setAuthor(originalUsage.getAuthor());
        postDistributionUsage.setNumberOfCopies(originalUsage.getNumberOfCopies());
        postDistributionUsage.setReportedValue(originalUsage.getReportedValue());
        postDistributionUsage.setNetAmount(originalUsage.getNetAmount());
        postDistributionUsage.setServiceFee(originalUsage.getServiceFee());
        postDistributionUsage.setServiceFeeAmount(originalUsage.getServiceFeeAmount());
        postDistributionUsage.setGrossAmount(originalUsage.getGrossAmount());
        postDistributionUsage.setRhParticipating(originalUsage.isRhParticipating());
        postDistributionUsage.setProductFamily(originalUsage.getProductFamily());
        postDistributionUsage.setSystemTitle(originalUsage.getSystemTitle());
        postDistributionUsage.setPayee(paidUsage.getPayee());
        postDistributionUsage.setDistributionName(paidUsage.getDistributionName());
        postDistributionUsage.setDistributionDate(paidUsage.getDistributionDate());
        postDistributionUsage.setCccEventId(paidUsage.getCccEventId());
        postDistributionUsage.setCheckNumber(paidUsage.getCheckNumber());
        postDistributionUsage.setCheckDate(paidUsage.getCheckDate());
        postDistributionUsage.setLmDetailId(paidUsage.getLmDetailId());
        postDistributionUsage.setPeriodEndDate(paidUsage.getPeriodEndDate());
        return postDistributionUsage;
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
}
