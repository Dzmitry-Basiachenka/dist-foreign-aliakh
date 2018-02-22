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
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvErrorResultWriter;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvProcessingResult;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import org.apache.commons.collections4.CollectionUtils;
import org.perf4j.StopWatch;
import org.perf4j.aop.Profiled;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
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
        "UsagesCount={}, UpdatedCount={}, NotFoundDetailIds:{}";
    private static final Logger LOGGER = RupLogUtils.getLogger();

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
    public void writeScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream outputStream) {
        if (ScenarioStatusEnum.SENT_TO_LM == scenario.getStatus()) {
            usageArchiveRepository.writeScenarioUsagesCsvReport(scenario.getId(), outputStream);
        } else {
            usageRepository.writeScenarioUsagesCsvReport(scenario.getId(), outputStream);
        }
    }

    @Override
    public void writeErrorsToFile(CsvProcessingResult<Usage> csvProcessingResult, OutputStream outputStream) {
        new CsvErrorResultWriter().writeErrorsResult(outputStream, csvProcessingResult);
    }

    @Override
    @Profiled(tag = "service.UsageService.insertUsages")
    public int insertUsages(UsageBatch usageBatch, List<Usage> usages) {
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
    public List<Usage> getUsagesByScenarioId(String scenarioId) {
        return usageRepository.findByScenarioId(scenarioId);
    }

    @Override
    @Profiled(tag = "service.UsageService.addToScenario")
    public void addUsagesToScenario(List<Usage> usages, Scenario scenario) {
        Table<String, String, Long> rollUps = prmIntegrationService.getRollUps(
            usages.stream().map(usage -> usage.getRightsholder().getId()).collect(Collectors.toSet()));
        usages.forEach(usage -> {
            usage.setScenarioId(scenario.getId());
            usage.setStatus(UsageStatusEnum.LOCKED);
            usage.setUpdateUser(scenario.getCreateUser());
            usage.getPayee().setAccountNumber(
                PrmRollUpService.getPayeeAccountNumber(rollUps, usage.getRightsholder(), usage.getProductFamily()));
        });
        usageRepository.addToScenario(usages);
    }

    @Override
    public void updateRhPayeeAndAmounts(List<Usage> usages) {
        usages.forEach(usage -> {
            boolean rhParticipatingFlag =
                prmIntegrationService.isRightsholderParticipating(usage.getRightsholder().getAccountNumber(),
                    usage.getProductFamily());
            CalculationUtils.recalculateAmounts(usage, rhParticipatingFlag,
                prmIntegrationService.getRhParticipatingServiceFee(rhParticipatingFlag));
            usage.setUpdateUser(RupContextUtils.getUserName());
        });
        usageRepository.updateRhPayeeAndAmounts(usages);
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
        LOGGER.info("Move details to archive. Started. {}", ForeignLogUtils.scenario(scenario));
        List<Usage> usages = usageRepository.findByScenarioId(scenario.getId());
        usages.forEach(usageArchiveRepository::insert);
        usageRepository.deleteByScenarioId(scenario.getId());
        LOGGER.info("Move details to archive. Finished. {}, UsagesCount={}", ForeignLogUtils.scenario(scenario),
            usages.size());
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
    public void sendForRightsAssignment() {
        List<Usage> usages = usageRepository.findByStatuses(UsageStatusEnum.RH_NOT_FOUND);
        LOGGER.info("Send for Rights Assignment. Started. UsagesCount={}", LogUtils.size(usages));
        if (CollectionUtils.isNotEmpty(usages)) {
            RightsAssignmentResult result = rmsIntegrationService.sendForRightsAssignment(
                usages.stream().map(Usage::getWrWrkInst).collect(Collectors.toSet()));
            if (result.isSuccessful()) {
                String jobId = result.getJobId();
                usages.forEach(usage -> {
                    usageRepository.updateStatus(usage.getId(), UsageStatusEnum.SENT_FOR_RA);
                    usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.SENT_FOR_RA,
                        String.format("Sent for RA: job id '%s'", jobId));
                });
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
        int updated = 0;
        Set<Long> notFoundDetailsIds = Sets.newHashSet();
        for (PaidUsage paidUsage : usages) {
            Usage usage = usageRepository.findByDetailId(paidUsage.getDetailId());
            if (null != usage) {
                String usageId = usage.getId();
                paidUsage.setId(usageId);
                paidUsage.setStatus(UsageStatusEnum.PAID);
                usageArchiveRepository.updatePaidInfo(paidUsage);
                usageAuditService.logAction(usageId, UsageActionTypeEnum.PAID,
                    "Usage has been paid according to information from the LM");
                updated++;
            } else {
                notFoundDetailsIds.add(paidUsage.getDetailId());
            }
        }
        if (CollectionUtils.isNotEmpty(notFoundDetailsIds)) {
            LOGGER.warn(UPDATE_PAID_INFO_FAILED_LOG_MESSAGE, LogUtils.size(usages), updated, notFoundDetailsIds);
        }
        LOGGER.info("Update paid information. Finished. UsagesCount={}, UpdatedCount={}", LogUtils.size(usages),
            updated);
    }

    @Override
    @Transactional
    public void findWorksAndUpdateStatuses() {
        List<Usage> usages = ImmutableList.copyOf(usageRepository.findUsagesWithBlankWrWrkInst());
        if (CollectionUtils.isNotEmpty(usages)) {
            List<Usage> matchedByIdno = findWorksByIdno(usages);
            List<Usage> matchedByTitle = findWorksByTitle(usages);
            Set<String> matchedUsagesIds = Sets.newHashSet();
            matchedUsagesIds.addAll(matchedByIdno.stream().map(BaseEntity::getId).collect(Collectors.toSet()));
            matchedUsagesIds.addAll(matchedByTitle.stream().map(BaseEntity::getId).collect(Collectors.toSet()));
            List<Usage> nonMatchedUsages = Lists.newArrayList(usages)
                .stream()
                .filter(usage -> !matchedUsagesIds.contains(usage.getId()))
                .collect(Collectors.toList());
            makeUsagesEligibleForNts(ImmutableList.copyOf(nonMatchedUsages));
        }
    }

    /**
     * Updates eligible for NTS {@link Usage}s: changes status to {@link UsageStatusEnum#ELIGIBLE} and
     * product family to NTS if gross amount is less than $100.
     *
     * @param usages list of {@link Usage}s
     */
    void makeUsagesEligibleForNts(List<Usage> usages) {
        makeUsagesEligibleForNts(UsageGroup.STANDARD_NUMBER.group(usages),
            "Detail was made eligible for NTS because sum of gross amounts, " +
                "grouped by standard number, is less than $100");
        makeUsagesEligibleForNts(UsageGroup.WORK_TITLE.group(usages),
            "Detail was made eligible for NTS because sum of gross amounts, " +
                "grouped by work title, is less than $100");
        makeUsagesEligibleForNts(UsageGroup.SELF.group(usages),
            "Detail was made eligible for NTS because gross amount is less than $100");
    }

    private List<Usage> findWorksByIdno(List<Usage> usages) {
        List<Usage> matchedByIdno = workMatchingService.matchByIdno(usages);
        if (CollectionUtils.isNotEmpty(matchedByIdno)) {
            usageRepository.updateStatusAndWrWrkInst(matchedByIdno);
            matchedByIdno.forEach(usage -> usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                String.format("Wr Wrk Inst %s was found in PI by standard number %s", usage.getWrWrkInst(),
                    usage.getStandardNumber())));
        }
        return matchedByIdno;
    }

    private List<Usage> findWorksByTitle(List<Usage> usages) {
        List<Usage> matchedByTitle = workMatchingService.matchByTitle(usages);
        if (CollectionUtils.isNotEmpty(matchedByTitle)) {
            usageRepository.updateStatusAndWrWrkInst(matchedByTitle);
            matchedByTitle.forEach(usage -> usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                String.format("Wr Wrk Inst %s was found in PI by title '%s'", usage.getWrWrkInst(),
                    usage.getWorkTitle())));
        }
        return matchedByTitle;
    }

    private void makeUsagesEligibleForNts(List<Usage> usages, String actionReason) {
        if (CollectionUtils.isNotEmpty(usages)) {
            usageRepository.updateStatusAndProductFamily(usages);
            usages.forEach(usage ->
                usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.ELIGIBLE_FOR_NTS, actionReason));
        }
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

    private void calculateUsagesGrossAmount(UsageBatch usageBatch, List<Usage> usages) {
        BigDecimal fundPoolAmount = usageBatch.getGrossAmount();
        BigDecimal totalAmount = usages.stream().map(Usage::getReportedValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal conversionRate = CalculationUtils.calculateConversionRate(fundPoolAmount, totalAmount);
        usages.forEach(usage -> usage.setGrossAmount(
            CalculationUtils.calculateUsdAmount(usage.getReportedValue(), conversionRate)));
        LOGGER.info(CALCULATION_FINISHED_LOG_MESSAGE, usageBatch.getName(), usageBatch.getGrossAmount(), totalAmount,
            conversionRate);
    }

    private static class UsageGroup<T> {

        private static final BigDecimal GROSS_AMOUNT_LIMIT = BigDecimal.valueOf(100L);

        private static final UsageGroup<String> STANDARD_NUMBER = new UsageGroup<>(
            Usage::getStandardNumber,
            usage -> null != usage.getStandardNumber()
        );
        private static final UsageGroup<String> WORK_TITLE = new UsageGroup<>(
            Usage::getWorkTitle,
            usage -> null == usage.getStandardNumber() && null != usage.getWorkTitle()
        );
        private static final UsageGroup<Usage> SELF = new UsageGroup<>(
            usage -> usage,
            usage -> null == usage.getStandardNumber() && null == usage.getWorkTitle()
        );

        private final Function<Usage, T> mapper;
        private final Function<Usage, Boolean> predicate;

        UsageGroup(Function<Usage, T> mapper, Function<Usage, Boolean> predicate) {
            this.mapper = mapper;
            this.predicate = predicate;
        }

        List<Usage> group(List<Usage> usages) {
            Map<T, BigDecimal> valueToGrossAmount = Maps.newHashMap();
            usages.forEach(usage -> {
                if (predicate.apply(usage)) {
                    T value = mapper.apply(usage);
                    BigDecimal grossAmount = valueToGrossAmount.computeIfAbsent(value, key -> BigDecimal.ZERO);
                    valueToGrossAmount.put(value, grossAmount.add(usage.getGrossAmount()));
                }
            });
            Set<T> filteredValues = valueToGrossAmount
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().compareTo(GROSS_AMOUNT_LIMIT) < 0)
                .map(Entry::getKey)
                .collect(Collectors.toSet());
            return usages
                .stream()
                .filter(usage -> filteredValues.contains(mapper.apply(usage)))
                .peek(usage -> usage.setStatus(UsageStatusEnum.ELIGIBLE))
                .peek(usage -> usage.setProductFamily("NTS"))
                .collect(Collectors.toList());
        }
    }
}
