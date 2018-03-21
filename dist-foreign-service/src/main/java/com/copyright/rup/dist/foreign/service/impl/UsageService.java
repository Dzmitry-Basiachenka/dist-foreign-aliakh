package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.AuditFilter;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.common.util.CalculationUtils;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.integration.rms.api.IRmsIntegrationService;
import com.copyright.rup.dist.foreign.integration.rms.api.RightsAssignmentResult;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IRmsGrantsService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;
import com.copyright.rup.dist.foreign.service.impl.WorksMatchingJob.UsageGroupEnum;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.perf4j.StopWatch;
import org.perf4j.aop.Profiled;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
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

    private static final BigDecimal GROSS_AMOUNT_LIMIT = BigDecimal.valueOf(100L);
    private static final String CALCULATION_FINISHED_LOG_MESSAGE = "Calculated usages gross amount. " +
        "UsageBatchName={}, FundPoolAmount={}, TotalAmount={}, ConversionRate={}";
    private static final String UPDATE_PAID_INFO_FAILED_LOG_MESSAGE = "Update paid information. Not found usages. " +
        "UsagesCount={}, UpdatedCount={}, NotFoundDetailIds={}";
    private static final long UNIDENTIFIED_WR_WRK_INST = 123050824L;
    private static final Long CLA_PAYEE = 2000017000L;
    private static final Logger LOGGER = RupLogUtils.getLogger();
    @Value("$RUP{dist.foreign.service_fee.cla_payee}")
    private BigDecimal claPayeeServiceFee;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;
    @Autowired
    private IRmsIntegrationService rmsIntegrationService;
    @Autowired
    private IRmsGrantsService rmsGrantsService;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    private IWorkMatchingService workMatchingService;

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
    public void writeUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        usageRepository.writeUsagesCsvReport(filter, pipedOutputStream);
    }

    @Override
    @Transactional
    public void sendForResearch(UsageFilter filter, OutputStream outputStream) {
        StopWatch stopWatch = new Slf4JStopWatch();
        Set<String> usageIds = usageRepository.writeUsagesForResearchAndFindIds(filter, outputStream);
        stopWatch.lap("usage.sendForResearch.writeUsagesForResearchAndFindIds");
        if (CollectionUtils.isNotEmpty(usageIds)) {
            usageRepository.updateStatus(usageIds, UsageStatusEnum.WORK_RESEARCH);
        }
        stopWatch.lap("usage.sendForResearch.updateStatus");
        usageIds.forEach(usageId -> usageAuditService.logAction(usageId, UsageActionTypeEnum.WORK_RESEARCH,
            "Usage detail was sent for research"));
        stopWatch.stop("usage.sendForResearch.logAction");
    }

    @Override
    public void writeScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream outputStream) {
        if (ScenarioStatusEnum.SENT_TO_LM == scenario.getStatus()) {
            usageArchiveRepository.writeScenarioUsagesCsvReport(scenario.getId(), outputStream);
        } else {
            usageRepository.writeScenarioUsagesCsvReport(scenario.getId(), outputStream);
        }
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
                CLA_PAYEE.equals(usage.getPayee().getAccountNumber()) ? claPayeeServiceFee
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
            if (CLA_PAYEE.equals(usage.getPayee().getAccountNumber())) {
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
    public void deleteFromScenario(String scenarioId) {
        usageRepository.deleteFromScenario(scenarioId, RupContextUtils.getUserName());
    }

    @Override
    @Transactional
    @Profiled(tag = "service.UsageService.deleteFromScenario")
    public void deleteFromScenario(Scenario scenario, Long rroAccountNumber, List<Long> accountNumbers, String reason) {
        List<String> usagesIds =
            usageRepository.findIdsByScenarioIdRroAccountNumberRhAccountNumbers(scenario.getId(), rroAccountNumber,
                accountNumbers);
        usagesIds.forEach(usageId -> usageAuditService.logAction(usageId, scenario,
            UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, reason));
        usageRepository.deleteFromScenario(usagesIds, RupContextUtils.getUserName());
    }

    @Override
    public Set<Long> getDuplicateDetailIds(List<Long> detailIds) {
        return CollectionUtils.isNotEmpty(detailIds)
            ? usageRepository.findDuplicateDetailIds(detailIds)
            : Collections.emptySet();
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
    public List<RightsholderTotalsHolder> getRightsholderTotalsHoldersByScenario(Scenario scenario,
                                                                                 String searchValue,
                                                                                 Pageable pageable, Sort sort) {
        return ScenarioStatusEnum.SENT_TO_LM == scenario.getStatus()
            ? usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(scenario.getId(), searchValue, pageable,
            sort)
            : usageRepository.findRightsholderTotalsHoldersByScenarioId(scenario.getId(), searchValue, pageable, sort);
    }

    @Override
    public int getRightsholderTotalsHolderCountByScenario(Scenario scenario, String searchValue) {
        return ScenarioStatusEnum.SENT_TO_LM == scenario.getStatus()
            ? usageArchiveRepository.findRightsholderTotalsHolderCountByScenarioId(scenario.getId(), searchValue)
            : usageRepository.findRightsholderTotalsHolderCountByScenarioId(scenario.getId(), searchValue);
    }

    @Override
    public int getCountByScenarioAndRhAccountNumber(Long accountNumber, Scenario scenario, String searchValue) {
        return ScenarioStatusEnum.SENT_TO_LM == scenario.getStatus()
            ? usageArchiveRepository.findCountByScenarioIdAndRhAccountNumber(scenario.getId(), accountNumber,
            searchValue)
            : usageRepository.findCountByScenarioIdAndRhAccountNumber(accountNumber, scenario.getId(), searchValue);
    }

    @Override
    public List<UsageDto> getByScenarioAndRhAccountNumber(Long accountNumber, Scenario scenario,
                                                          String searchValue, Pageable pageable, Sort sort) {
        return ScenarioStatusEnum.SENT_TO_LM == scenario.getStatus()
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
    public void writeAuditCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        usageRepository.writeAuditCsvReport(filter, pipedOutputStream);
    }

    @Override
    @Transactional
    @Profiled(tag = "service.UsageService.sendForRightsAssignment")
    @Scheduled(cron = "$RUP{dist.foreign.service.schedule.send_for_ra}")
    public void sendForRightsAssignment() {
        List<Usage> usages = usageRepository.findByStatuses(UsageStatusEnum.RH_NOT_FOUND);
        LOGGER.info("Send for Rights Assignment. Started. UsagesCount={}", LogUtils.size(usages));
        if (CollectionUtils.isNotEmpty(usages)) {
            RightsAssignmentResult result = rmsIntegrationService.sendForRightsAssignment(
                usages.stream().map(Usage::getWrWrkInst).collect(Collectors.toSet()));
            if (result.isSuccessful()) {
                Set<String> usageIds = usages.stream()
                    .map(BaseEntity::getId)
                    .collect(Collectors.toSet());
                usageRepository.updateStatus(usageIds, UsageStatusEnum.SENT_FOR_RA);
                usageAuditService.logAction(usageIds, UsageActionTypeEnum.SENT_FOR_RA,
                    String.format("Sent for RA: job id '%s'", result.getJobId()));
                LOGGER.info("Send for Rights Assignment. Finished. UsagesCount={}, JobId={}", LogUtils.size(usages),
                    result.getJobId());
            } else {
                LOGGER.warn("Send for Rights Assignment. Failed. Reason={}, DetailsIds={}", result.getErrorMessage(),
                    usages.stream().map(Usage::getDetailId).collect(Collectors.toList()));
            }
        } else {
            LOGGER.info("Send for Rights Assignment. Skipped. Reason=There are no usages for sending");
        }
    }

    @Override
    @Profiled(tag = "service.UsageService.updateRightsholders")
    @Scheduled(cron = "$RUP{dist.foreign.service.schedule.get_rights}")
    //TODO {pliakh} adjust implementation to avoid copy-paste for WORK_FOUND and SENT_FOR_RA usages handling
    public void updateRightsholders() {
        List<Usage> usages = usageRepository.findByStatuses(UsageStatusEnum.WORK_FOUND);
        if (CollectionUtils.isNotEmpty(usages)) {
            LOGGER.info("Get usage rightsholders. Started. UsagesStatus={}, UsagesCount={}", UsageStatusEnum.WORK_FOUND,
                LogUtils.size(usages));
            long eligibleUsagesCount = updateWorkFoundUsagesRightsholders(usages);
            LOGGER.info("Get usage rightsholders. Finished. UsagesStatus={}, UsagesCount={}, EligibleUsagesCount={}",
                UsageStatusEnum.WORK_FOUND, LogUtils.size(usages), eligibleUsagesCount);
        } else {
            LOGGER.info("Get usage rightsholders. Skipped. Reason=There are no WORK_FOUND usages.");
        }
        usages = usageRepository.findByStatuses(UsageStatusEnum.SENT_FOR_RA);
        if (CollectionUtils.isNotEmpty(usages)) {
            LOGGER.info("Get usage rightsholders. Started. UsagesStatus={}, UsagesCount={}",
                UsageStatusEnum.SENT_FOR_RA, LogUtils.size(usages));
            long eligibleUsagesCount = updateSentForRaUsagesRightsholders(usages);
            LOGGER.info("Get usage rightsholders. Finished. UsagesStatus={}, UsagesCount={}, EligibleUsagesCount={}",
                UsageStatusEnum.SENT_FOR_RA, LogUtils.size(usages), eligibleUsagesCount);
        } else {
            LOGGER.info("Get usage rightsholders. Skipped. Reason=There are no SENT_FOR_RA usages.");
        }
    }

    @Override
    public List<String> getProductFamilies() {
        return usageRepository.findProductFamiliesForFilter();
    }

    @Override
    public List<String> getProductFamiliesForAudit() {
        return usageRepository.findProductFamiliesForAuditFilter();
    }

    @Override
    @Transactional
    public void updatePaidInfo(List<PaidUsage> usages) {
        LOGGER.info("Update paid information. Started. UsagesCount={}", LogUtils.size(usages));
        Set<Long> notFoundDetailsIds = Sets.newHashSet();
        Map<Long, String> detailIdToIdMap = usageArchiveRepository.findDetailIdToIdMap(
            Lists.newArrayList(usages.stream().map(Usage::getDetailId).collect(Collectors.toList())));
        if (MapUtils.isNotEmpty(detailIdToIdMap)) {
            for (PaidUsage paidUsage : usages) {
                String usageId = detailIdToIdMap.get(paidUsage.getDetailId());
                if (StringUtils.isNotBlank(usageId)) {
                    paidUsage.setId(usageId);
                    paidUsage.setStatus(UsageStatusEnum.PAID);
                    usageArchiveRepository.updatePaidInfo(paidUsage);
                    usageAuditService.logAction(usageId, UsageActionTypeEnum.PAID,
                        "Usage has been paid according to information from the LM");
                } else {
                    notFoundDetailsIds.add(paidUsage.getDetailId());
                }
            }
        } else {
            LOGGER.warn(UPDATE_PAID_INFO_FAILED_LOG_MESSAGE, LogUtils.size(usages), 0, notFoundDetailsIds);
        }
        if (CollectionUtils.isNotEmpty(notFoundDetailsIds)) {
            LOGGER.warn(UPDATE_PAID_INFO_FAILED_LOG_MESSAGE, LogUtils.size(usages),
                usages.size() - notFoundDetailsIds.size(), notFoundDetailsIds);
        }
        LOGGER.info("Update paid information. Finished. UsagesCount={}, UpdatedCount={}", LogUtils.size(usages),
            usages.size() - notFoundDetailsIds.size());
    }

    @Override
    public List<Usage> getUsagesWithBlankWrWrkInst() {
        return usageRepository.findUsagesWithBlankWrWrkInst();
    }

    @Override
    @Transactional
    public void matchByIdno(List<Usage> usages) {
        StopWatch stopWatch = new Slf4JStopWatch();
        List<Usage> matchedByIdno = workMatchingService.matchByIdno(usages);
        stopWatch.lap("matchWorks.byIdno_findByIdno");
        if (CollectionUtils.isNotEmpty(matchedByIdno)) {
            usageRepository.update(matchedByIdno);
            stopWatch.lap("matchWorks.byIdno_updateUsages");
            matchedByIdno.forEach(usage -> usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                String.format("Wr Wrk Inst %s was found by standard number %s", usage.getWrWrkInst(),
                    usage.getStandardNumber())));
            stopWatch.lap("matchWorks.byIdno_writeAudit");
        }
        updateUsagesStatusAndWriteAudit(usages, UsageGroupEnum.STANDARD_NUMBER);
        stopWatch.stop("matchWorks.byIdno_determineNtsAndUpdate");
    }

    @Override
    @Transactional
    public void matchByTitle(List<Usage> usages) {
        StopWatch stopWatch = new Slf4JStopWatch();
        List<Usage> matchedByTitle = workMatchingService.matchByTitle(usages);
        stopWatch.lap("matchWorks.byIdno_findByTitle");
        if (CollectionUtils.isNotEmpty(matchedByTitle)) {
            usageRepository.update(matchedByTitle);
            stopWatch.lap("matchWorks.byTitle_updateUsages");
            matchedByTitle.forEach(usage -> usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                String.format("Wr Wrk Inst %s was found by title \"%s\"", usage.getWrWrkInst(),
                    usage.getWorkTitle())));
            stopWatch.lap("matchWorks.byTitle_writeAudit");
        }
        updateUsagesStatusAndWriteAudit(usages, UsageGroupEnum.TITLE);
        stopWatch.stop("matchWorks.byTitle_determineNtsAndUpdate");
    }

    @Override
    @Transactional
    public void updateStatusForUsagesWithNoStandardNumberAndTitle(List<Usage> usages) {
        StopWatch stopWatch = new Slf4JStopWatch();
        updateUsagesStatusAndWriteAudit(usages, UsageGroupEnum.SINGLE_USAGE);
        stopWatch.stop("matchWorks.noIdnoNoTitle_determineNtsAndUpdate");
    }

    private long updateWorkFoundUsagesRightsholders(List<Usage> usages) {
        StopWatch stopWatch = new Slf4JStopWatch("service.UsageService.updateRightsholders(WORK_FOUND)");
        Map<Long, Set<String>> wrWrkInstToUsageIds = usages.stream().collect(
            Collectors.groupingBy(Usage::getWrWrkInst, HashMap::new, Collectors
                .mapping(Usage::getId, Collectors.toSet())));

        Map<Long, Long> wrWrkInstToAccountNumber =
            rmsGrantsService.getAccountNumbersByWrWrkInsts(Lists.newArrayList(wrWrkInstToUsageIds.keySet()));

        AtomicLong eligibleUsagesCount = new AtomicLong();
        wrWrkInstToAccountNumber.forEach((wrWrkInst, rhAccountNumber) -> {
            Set<String> usageIds = wrWrkInstToUsageIds.get(wrWrkInst);
            usageRepository.updateStatusAndRhAccountNumber(usageIds, UsageStatusEnum.ELIGIBLE,
                rhAccountNumber);
            eligibleUsagesCount.addAndGet(usageIds.size());
            usageAuditService.logAction(usageIds, UsageActionTypeEnum.RH_FOUND,
                String.format("Rightsholder account %s was found in RMS", rhAccountNumber));
        });
        rightsholderService.updateRightsholders(Sets.newHashSet(wrWrkInstToAccountNumber.values()));

        wrWrkInstToUsageIds.forEach((wrWrkInst, usageIds) -> {
            if (!wrWrkInstToAccountNumber.containsKey(wrWrkInst)) {
                usageRepository.updateStatus(usageIds, UsageStatusEnum.RH_NOT_FOUND);
                usageAuditService.logAction(usageIds, UsageActionTypeEnum.RH_NOT_FOUND,
                    String.format("Rightsholder account for %s was not found in RMS", wrWrkInst));
            }
        });
        stopWatch.stop();
        return eligibleUsagesCount.longValue();
    }

    private long updateSentForRaUsagesRightsholders(List<Usage> usages) {
        StopWatch stopWatch = new Slf4JStopWatch("service.UsageService.updateRightsholders(SENT_FOR_RA)");
        Map<Long, Set<String>> wrWrkInstToUsageIds = usages.stream().collect(
            Collectors.groupingBy(Usage::getWrWrkInst, HashMap::new, Collectors
                .mapping(Usage::getId, Collectors.toSet())));
        Map<Long, Long> wrWrkInstToAccountNumber =
            rmsGrantsService.getAccountNumbersByWrWrkInsts(Lists.newArrayList(wrWrkInstToUsageIds.keySet()));
        AtomicLong eligibleUsagesCount = new AtomicLong();
        wrWrkInstToAccountNumber.forEach((wrWrkInst, rhAccountNumber) -> {
            Set<String> usageIds = wrWrkInstToUsageIds.get(wrWrkInst);
            usageRepository.updateStatusAndRhAccountNumber(usageIds, UsageStatusEnum.ELIGIBLE,
                rhAccountNumber);
            eligibleUsagesCount.addAndGet(usageIds.size());
            usageAuditService.logAction(usageIds, UsageActionTypeEnum.RH_FOUND,
                String.format("Rightsholder account %s was found in RMS", rhAccountNumber));
        });
        rightsholderService.updateRightsholders(Sets.newHashSet(wrWrkInstToAccountNumber.values()));
        stopWatch.stop();
        return eligibleUsagesCount.longValue();
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

    private void updateUsagesStatusAndWriteAudit(List<Usage> usages, UsageGroupEnum usageGroup) {
        usages.stream()
            .filter(usage -> UsageStatusEnum.NEW == usage.getStatus())
            .collect(Collectors.groupingBy(usageGroup.getMappingFunction()))
            .forEach((key, usagesGroup) -> {
                if (GROSS_AMOUNT_LIMIT.compareTo(sumUsagesGrossAmount(usagesGroup)) > 0) {
                    usagesGroup.forEach(usage -> {
                        usage.setStatus(UsageStatusEnum.ELIGIBLE);
                        usage.setProductFamily("NTS");
                        usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.ELIGIBLE_FOR_NTS,
                            usageGroup.getNtsEligibleReason());
                    });
                } else {
                    usagesGroup.forEach(usage -> {
                        if (Objects.isNull(usage.getStandardNumber()) && Objects.isNull(usage.getWorkTitle())) {
                            usage.setWrWrkInst(UNIDENTIFIED_WR_WRK_INST);
                            usage.setStatus(UsageStatusEnum.WORK_FOUND);
                            usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                                "Usage assigned unidentified work due to blank standard number and title");
                        } else {
                            usage.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
                            usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_NOT_FOUND,
                                usageGroup.getWorkNotFoundReasonFunction().apply(usage));
                        }
                    });
                }
            });
        usageRepository.update(usages);
    }

    private void addScenarioInfo(Usage usage, Scenario scenario) {
        usage.setScenarioId(scenario.getId());
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.setUpdateUser(scenario.getCreateUser());
    }

    private BigDecimal sumUsagesGrossAmount(List<Usage> usages) {
        return usages.stream()
            .map(Usage::getGrossAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
