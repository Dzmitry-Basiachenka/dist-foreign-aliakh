package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
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
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.perf4j.StopWatch;
import org.perf4j.aop.Profiled;
import org.perf4j.slf4j.Slf4JStopWatch;
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
import java.util.Set;
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
    @Profiled(tag = "usage.getUsages")
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
        StopWatch stopWatch = new Slf4JStopWatch();
        String userName = RupContextUtils.getUserName();
        int size = usages.size();
        LOGGER.info("Insert usages. Started. UsageBatchName={}, UsagesCount={}, UserName={}", usageBatch.getName(),
            size, userName);
        calculateUsagesGrossAmount(usageBatch, usages);
        stopWatch.lap("usageBatch.load_3_1_calculateGrossAmount");
        usages.forEach(usage -> {
            usage.setBatchId(usageBatch.getId());
            usage.setCreateUser(userName);
            usage.setUpdateUser(userName);
            usageRepository.insert(usage);
        });
        stopWatch.lap("usageBatch.load_3_2.storedUsages");
        // Adding data to audit table in separate loop increases performance up to 3 times
        // while using batch with 200000 usages
        String reason = "Uploaded in '" + usageBatch.getName() + "' Batch";
        usages.forEach(usage -> usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED, reason));
        stopWatch.stop("usageBatch.load_3_3_logLoadedAction");
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
        StopWatch stopWatch = new Slf4JStopWatch();
        Map<Long, Usage> rhToUsageMap = getRightsholdersInformation(scenario.getId());
        stopWatch.lap("scenario.refresh_2_1_getRightsholdersInformation");
        List<Usage> newUsages = usageRepository.findWithAmountsAndRightsholders(filter);
        stopWatch.lap("scenario.refresh_2_2_findWithAmountsAndRightsholders");
        Set<String> rightsholdersIds = newUsages.stream().map(usage -> usage.getRightsholder().getId())
            .collect(Collectors.toSet());
        rightsholdersIds.removeAll(
            rhToUsageMap.values().stream().map(usage -> usage.getRightsholder().getId()).collect(Collectors.toSet()));
        Table<String, String, Long> rollUps = prmIntegrationService.getRollUps(rightsholdersIds);
        stopWatch.lap("scenario.refresh_2_3_getRollUps");
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
        stopWatch.stop("scenario.refresh_2_4_addToScenario");
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
        StopWatch stopWatch = new Slf4JStopWatch();
        Table<String, String, Long> rollUps = prmIntegrationService.getRollUps(
            usages.stream().map(usage -> usage.getRightsholder().getId()).collect(Collectors.toSet()));
        stopWatch.lap("scenario.create_3_1_getRollups");
        usages.forEach(usage -> {
            usage.getPayee().setAccountNumber(
                PrmRollUpService.getPayeeAccountNumber(rollUps, usage.getRightsholder(), usage.getProductFamily()));
            addScenarioInfo(usage, scenario);
            //usages that have CLA as Payee should get 10% service fee
            if (FdaConstants.CLA_ACCOUNT_NUMBER.equals(usage.getPayee().getAccountNumber())) {
                CalculationUtils.recalculateAmounts(usage, usage.isRhParticipating(), claPayeeServiceFee);
            }
        });
        stopWatch.lap("scenario.create_3_2_setPayeeAndStatus");
        usageRepository.addToScenario(usages);
        stopWatch.stop("scenario.create_3_3_addScenarioIdToUsages");
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
    @Profiled(tag = "usage.deleteFromScenario")
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
        StopWatch stopWatch = new Slf4JStopWatch();
        LOGGER.info("Move details to archive. Started. {}", ForeignLogUtils.scenario(scenario));
        List<Usage> usages = usageRepository.findByScenarioId(scenario.getId());
        stopWatch.lap("usage.moveToArchive_findByScenarioId");
        usages.forEach(usageArchiveRepository::insert);
        stopWatch.lap("usage.moveToArchive_insertIntoArchive");
        usageRepository.deleteByScenarioId(scenario.getId());
        LOGGER.info("Move details to archive. Finished. {}, UsagesCount={}", ForeignLogUtils.scenario(scenario),
            usages.size());
        stopWatch.stop("usage.moveToArchive_deleteByScenarioId");
        return usages;
    }

    @Override
    @Profiled(tag = "service.UsageService.getRightsholderTotalsHoldersByScenario")
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
    @Profiled(tag = "usage.getByScenarioAndRhAccountNumber")
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
    @Profiled(tag = "usage.getForAudit")
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
    public void updatePaidInfo(List<PaidUsage> usages) {
        LOGGER.info("Update paid information. Started. UsagesCount={}", LogUtils.size(usages));
        Set<String> notFoundUsageIds = Sets.newHashSet();
        usages.forEach(paidUsage -> {
            String usageId = paidUsage.getId();
            if (StringUtils.isNotBlank(usageId)) {
                paidUsage.setId(usageId);
                paidUsage.setStatus(UsageStatusEnum.PAID);
                usageArchiveRepository.updatePaidInfo(paidUsage);
                usageAuditService.logAction(usageId, UsageActionTypeEnum.PAID,
                    "Usage has been paid according to information from the LM");
            } else {
                notFoundUsageIds.add(usageId);
            }
        });
        if (CollectionUtils.isNotEmpty(notFoundUsageIds)) {
            LOGGER.warn(UPDATE_PAID_INFO_FAILED_LOG_MESSAGE, LogUtils.size(usages),
                usages.size() - notFoundUsageIds.size(), notFoundUsageIds);
        }
        LOGGER.info("Update paid information. Finished. UsagesCount={}, UpdatedCount={}", LogUtils.size(usages),
            usages.size() - notFoundUsageIds.size());
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
        StopWatch stopWatch = new Slf4JStopWatch();
        usageRepository.updateResearchedUsages(researchedUsages);
        stopWatch.lap("usage.loadResearchedUsages_1_updateResearchedUsage");
        researchedUsages.forEach(
            researchedUsage -> usageAuditService.logAction(researchedUsage.getUsageId(),
                UsageActionTypeEnum.WORK_FOUND,
                String.format("Wr Wrk Inst %s was added based on research", researchedUsage.getWrWrkInst()))
        );
        stopWatch.stop("usage.loadResearchedUsages_2_logAction");
        LOGGER.info("Load researched usages. Finished. ResearchedUsagesCount={}", LogUtils.size(researchedUsages));
    }

    @Override
    public void sendToCrm() {
        List<String> paidUsagesIds = usageArchiveRepository.findPaidIds();
        LOGGER.info("Send to CRM. Started. PaidUsagesCount={}", LogUtils.size(paidUsagesIds));
        int archivedUsagesCount = 0;
        if (CollectionUtils.isNotEmpty(paidUsagesIds)) {
            Set<String> invalidUsageIds = Sets.newHashSet();
            StopWatch stopWatch = new Slf4JStopWatch();
            for (List<String> ids : Iterables.partition(paidUsagesIds, 128)) {
                List<PaidUsage> paidUsages = usageArchiveRepository.findByIdAndStatus(ids, UsageStatusEnum.PAID);
                if (CollectionUtils.isNotEmpty(paidUsages)) {
                    CrmResult result =
                        crmIntegrationService.sendRightsDistributionRequests(
                            buildCrmRightsDistributionRequest(paidUsages));
                    stopWatch.lap("usage.sendToCrm_1_sendRightsDistributionRequests");
                    if (result.isSuccessful()) {
                        Set<String> usagesIds = paidUsages.stream().map(PaidUsage::getId).collect(Collectors.toSet());
                        updateReportedUsages(usagesIds, stopWatch);
                        archivedUsagesCount += usagesIds.size();
                    } else {
                        Set<String> invalidIds = result.getInvalidUsageIds();
                        if (CollectionUtils.isNotEmpty(invalidIds) && paidUsages.size() != invalidIds.size()) {
                            Set<String> usagesIds = paidUsages.stream()
                                .filter(paidUsage -> !invalidIds.contains(paidUsage.getId()))
                                .map(PaidUsage::getId)
                                .collect(Collectors.toSet());
                            if (CollectionUtils.isNotEmpty(usagesIds)) {
                                updateReportedUsages(usagesIds, stopWatch);
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

    private List<CrmRightsDistributionRequest> buildCrmRightsDistributionRequest(List<PaidUsage> paidUsages) {
        List<CrmRightsDistributionRequest> requests = Lists.newArrayListWithExpectedSize(paidUsages.size());
        paidUsages.forEach(paidUsage -> requests.add(new CrmRightsDistributionRequest(paidUsage)));
        return requests;
    }

    private void updateReportedUsages(Set<String> usagesIds, StopWatch stopWatch) {
        usageArchiveRepository.updateStatus(usagesIds, UsageStatusEnum.ARCHIVED);
        stopWatch.lap("usage.sendToCrm_2_updateStatus");
        usageAuditService.logAction(usagesIds, UsageActionTypeEnum.ARCHIVED, "Usage was sent to CRM");
        stopWatch.stop("usage.sendToCrm_3_logAction");
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
