package com.copyright.rup.dist.foreign.service.impl.aacl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.PayeeAccountAggregateLicenseeClassesPair;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.impl.InconsistentUsageStateException;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IAaclUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/22/2020
 *
 * @author Ihar Suvorau
 */
@Service
public class AaclUsageService implements IAaclUsageService, Serializable {

    private static final long serialVersionUID = -6646458765805807371L;
    private static final List<BigDecimal> DEFAULT_USAGES_AGE_WEIGHTS =
        List.of(new BigDecimal("1.00"), new BigDecimal("0.75"), new BigDecimal("0.50"), new BigDecimal("0.25"));
    private static final BigDecimal USAGES_AGE_WEIGHT_ZERO = new BigDecimal("0.00");
    private static final String INSERT_BASELINE_FINISHED_LOG_MESSAGE_FORMAT = "Insert AACL usages from baseline. " +
        "Finished. UsageBatchName={}, Period={}, NumberOfYears={}, UsagesCount={}, UserName={}";
    private static final String INSERT_BASELINE_SKIPPED_LOG_MESSAGE_FORMAT = "Insert AACL usages from baseline. " +
        "Skipped. Reason=No usages to pull, UsageBatchName={}, Period={}, NumberOfYears={}, UserName={}";
    private static final String DISQUALIFIED_USAGE = "disqualified";
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IFundPoolService fundPoolService;
    @Autowired
    private ILicenseeClassService licenseeClassService;
    @Autowired
    private IAaclUsageRepository aaclUsageRepository;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    @Qualifier("usageChainExecutor")
    private IChainExecutor<Usage> chainExecutor;
    @Value("$RUP{dist.foreign.usages.batch_size}")
    private int usagesBatchSize;

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void insertUsages(UsageBatch usageBatch, List<Usage> usages) {
        String userName = RupContextUtils.getUserName();
        int size = usages.size();
        LOGGER.info("Insert AACL usages. Started. UsageBatchName={}, UsagesCount={}, UserName={}", usageBatch.getName(),
            size, userName);
        int period = usageBatch.getPaymentDate().getYear();
        usages.forEach(usage -> {
            usage.setBatchId(usageBatch.getId());
            usage.getAaclUsage().getUsageAge().setPeriod(period);
            usage.getAaclUsage().setBatchPeriodEndDate(usageBatch.getPaymentDate());
            usage.setCreateUser(userName);
            usage.setUpdateUser(userName);
            aaclUsageRepository.insert(usage);
        });
        String loadedReason = "Uploaded in '" + usageBatch.getName() + "' Batch";
        usages.forEach(usage -> usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED, loadedReason));
        LOGGER.info("Insert AACL usages. Finished. UsageBatchName={}, UsagesCount={}, UserName={}",
            usageBatch.getName(), size, userName);
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public List<String> insertUsagesFromBaseline(UsageBatch usageBatch) {
        String userName = RupContextUtils.getUserName();
        int period = usageBatch.getPaymentDate().getYear();
        int numberOfYears = Objects.requireNonNull(usageBatch.getNumberOfBaselineYears());
        LOGGER.info(
            "Insert AACL usages from baseline. Started. UsageBatchName={}, Period={}, NumberOfYears={}, UserName={}",
            usageBatch.getName(), period, numberOfYears, userName);
        Set<Integer> baselinePeriods = aaclUsageRepository.findBaselinePeriods(period, numberOfYears);
        List<String> usageIds;
        if (CollectionUtils.isNotEmpty(baselinePeriods)) {
            usageIds = aaclUsageRepository.insertFromBaseline(baselinePeriods, usageBatch.getId(), userName);
            usageAuditService.logAction(new HashSet<>(usageIds), UsageActionTypeEnum.LOADED,
                "Pulled from baseline for '" + usageBatch.getName() + "' Batch");
            LOGGER.info(INSERT_BASELINE_FINISHED_LOG_MESSAGE_FORMAT,
                usageBatch.getName(), period, numberOfYears, LogUtils.size(usageIds), userName);
        } else {
            usageIds = List.of();
            LOGGER.info(INSERT_BASELINE_SKIPPED_LOG_MESSAGE_FORMAT,
                usageBatch.getName(), period, numberOfYears, userName);
        }
        return usageIds;
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public int updateClassifiedUsages(List<AaclClassifiedUsage> usages) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Update Classified AACL usages. Started. UsagesCount={}, UserName={}", LogUtils.size(usages),
            userName);
        long disqualifiedCount = usages.stream()
            .filter(this::isUsageDisqualified)
            .peek(usage -> {
                aaclUsageRepository.deleteById(usage.getDetailId());
                usageAuditService.deleteActionsByUsageId(usage.getDetailId());
            }).count();
        List<AaclClassifiedUsage> updateUsages = usages.stream()
            .filter(usage -> !isUsageDisqualified(usage))
            .collect(Collectors.toList());
        int updatedCount = updateUsages.size();
        aaclUsageRepository.updateClassifiedUsages(updateUsages, userName);
        updateUsages.forEach(usage -> usageAuditService.logAction(usage.getDetailId(), UsageActionTypeEnum.ELIGIBLE,
            "Usages has become eligible after classification"));
        LOGGER.info("Update Classified AACL usages. Finished. UsagesCount={}, UserName={}, DisqualifiedCount={}",
            updatedCount, userName, disqualifiedCount);
        return updatedCount;
    }

    @Override
    public List<Usage> getUsagesByIds(List<String> usageIds) {
        return CollectionUtils.isNotEmpty(usageIds)
            ? aaclUsageRepository.findByIds(usageIds)
            : List.of();
    }

    @Override
    public List<UsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? aaclUsageRepository.findDtosByFilter(filter, pageable, sort)
            : List.of();
    }

    @Override
    public void updateProcessedUsage(Usage usage) {
        String usageId = aaclUsageRepository.updateProcessedUsage(usage);
        if (Objects.isNull(usageId)) {
            // throws an exception and stops usage processing when such usage has been already consumed and processed
            throw new InconsistentUsageStateException(usage);
        }
        usage.setVersion(usage.getVersion() + 1);
    }

    @Override
    public int getUsagesCount(UsageFilter filter) {
        return !filter.isEmpty() ? aaclUsageRepository.findCountByFilter(filter) : 0;
    }

    @Override
    public List<Integer> getUsagePeriods() {
        return aaclUsageRepository.findUsagePeriods();
    }

    @Override
    public List<UsageAge> getDefaultUsageAges(List<Integer> periods) {
        List<UsageAge> usageAges = new ArrayList<>();
        IntStream.range(0, CollectionUtils.size(periods))
            .forEach(index -> {
                UsageAge usageAge = new UsageAge();
                usageAge.setPeriod(periods.get(index));
                usageAge.setWeight(getAgeWeight(index));
                usageAges.add(usageAge);
            });
        return usageAges;
    }

    @Override
    public List<UsageAge> getUsageAges(UsageFilter filter) {
        return getDefaultUsageAges(aaclUsageRepository.findUsagePeriodsByFilter(filter));
    }

    @Override
    @Transactional
    public void deleteById(String usageId) {
        usageAuditService.deleteActionsByUsageId(usageId);
        aaclUsageRepository.deleteById(usageId);
    }

    @Override
    public boolean isValidFilteredUsageStatus(UsageFilter filter, UsageStatusEnum status) {
        return aaclUsageRepository.isValidFilteredUsageStatus(filter, status);
    }

    @Override
    public boolean isValidForClassification(UsageFilter filter) {
        return aaclUsageRepository.isValidForClassification(filter);
    }

    @Override
    public List<Long> getInvalidRightsholdersByFilter(UsageFilter filter) {
        return aaclUsageRepository.findInvalidRightsholdersByFilter(filter);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void calculateAmounts(String scenarioId, String userName) {
        aaclUsageRepository.calculateAmounts(scenarioId, userName);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void excludeZeroAmountUsages(String scenarioId, String userName) {
        aaclUsageRepository.excludeZeroAmountUsages(scenarioId, userName);
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void deleteUsageBatchDetails(UsageBatch usageBatch) {
        usageAuditService.deleteActionsByBatchId(usageBatch.getId());
        aaclUsageRepository.deleteByBatchId(usageBatch.getId());
    }

    @Override
    public void sendForMatching(List<String> usageIds, String batchName) {
        AtomicInteger usageIdsCount = new AtomicInteger(0);
        chainExecutor.execute(() ->
            Iterables.partition(usageIds, usagesBatchSize)
                .forEach(partition -> {
                    usageIdsCount.addAndGet(partition.size());
                    LOGGER.info("Send usages for PI matching. Started. UsageBatchName={}, UsagesCount={}", batchName,
                        usageIdsCount);
                    chainExecutor.execute(getUsagesByIds(partition), ChainProcessorTypeEnum.MATCHING);
                    LOGGER.info("Send usages for PI matching. Finished. UsageBatchName={}, UsagesCount={}", batchName,
                        usageIdsCount);
                }));
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void addUsagesToScenario(Scenario scenario, UsageFilter filter) {
        String scenarioId = scenario.getId();
        String updateUser = scenario.getUpdateUser();
        aaclUsageRepository.addToScenario(scenarioId, filter, updateUser);
        scenario.getAaclFields().getPublicationTypes().forEach(pubType ->
            aaclUsageRepository.updatePublicationTypeWeight(scenarioId, pubType.getId(),
                pubType.getWeight(), updateUser));
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void populatePayees(String scenarioId) {
        String userName = RupContextUtils.getUserName();
        Set<Long> payeeAccountNumbers = new HashSet<>();
        List<Rightsholder> rightsholders = rightsholderService.getByScenarioId(scenarioId);
        Set<String> rightsholdersIds = rightsholders.stream().map(BaseEntity::getId).collect(Collectors.toSet());
        Map<String, Map<String, Rightsholder>> rollUps = prmIntegrationService.getRollUps(rightsholdersIds);
        rightsholders.forEach(rightsholder -> {
            Long payeeAccountNumber = PrmRollUpService.getPayee(rollUps, rightsholder, FdaConstants.AACL_PRODUCT_FAMILY)
                .getAccountNumber();
            payeeAccountNumbers.add(payeeAccountNumber);
            aaclUsageRepository.updatePayeeByAccountNumber(rightsholder.getAccountNumber(), scenarioId,
                payeeAccountNumber, userName);
        });
        rightsholderService.updateRighstholdersAsync(payeeAccountNumbers);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public List<AggregateLicenseeClass> getAggregateClassesNotToBeDistributed(String fundPoolId, UsageFilter filter,
                                                                              List<DetailLicenseeClass> mapping) {
        Set<Integer> fundPoolAggregateClassIds = getAggregateClassIdsWithAmountsFromFundPool(fundPoolId);
        Map<Integer, Set<Integer>> aggregateToDetailClassIds = mapping.stream()
            .collect(Collectors.groupingBy(detailClass -> detailClass.getAggregateLicenseeClass().getId(),
                Collectors.mapping(DetailLicenseeClass::getId, Collectors.toSet())));
        Set<Integer> unmappedAggregateClassIds =
            Sets.difference(fundPoolAggregateClassIds, aggregateToDetailClassIds.keySet());
        Set<Integer> aggregateClassIdsWithNoUsagesFor = aggregateToDetailClassIds.entrySet().stream()
            .filter(entry -> fundPoolAggregateClassIds.contains(entry.getKey()))
            .filter(entry -> entry.getValue()
                .stream()
                .noneMatch(detailClassId ->
                    aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(filter, detailClassId)))
            .map(Entry::getKey)
            .collect(Collectors.toSet());
        return getAggregateClassesByIds(Sets.union(unmappedAggregateClassIds, aggregateClassIdsWithNoUsagesFor));
    }

    @Override
    public List<PayeeAccountAggregateLicenseeClassesPair> getPayeeAggClassesPairsByScenarioId(String scenarioId) {
        return aaclUsageRepository.findPayeeAggClassesPairsByScenarioId(scenarioId);
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void excludeDetailsFromScenarioByPayees(String scenarioId, Set<Long> accountNumbers, String reason) {
        String userName = RupContextUtils.getUserName();
        Set<String> usageIds = aaclUsageRepository.excludeFromScenarioByPayees(scenarioId, accountNumbers, userName);
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, reason);
        calculateAmounts(scenarioId, userName);
    }

    @Override
    public List<PayeeTotalHolder> getPayeeTotalHoldersByFilter(ExcludePayeeFilter filter) {
        return aaclUsageRepository.findPayeeTotalHoldersByFilter(filter);
    }

    @Override
    public List<UsageDto> getForAudit(AuditFilter filter, Pageable pageable, Sort sort) {
        return aaclUsageRepository.findForAudit(filter, pageable, sort);
    }

    @Override
    public int getCountForAudit(AuditFilter filter) {
        return aaclUsageRepository.findCountForAudit(filter);
    }

    @Override
    public int getCountByScenarioAndRhAccountNumber(Scenario scenario, Long accountNumber, String searchValue) {
        return FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())
            ? usageArchiveRepository.findAaclCountByScenarioIdAndRhAccountNumber(scenario.getId(), accountNumber,
            searchValue)
            : aaclUsageRepository.findCountByScenarioIdAndRhAccountNumber(scenario.getId(), accountNumber, searchValue);
    }

    @Override
    public List<UsageDto> getByScenarioAndRhAccountNumber(Scenario scenario, Long accountNumber, String searchValue,
                                                          Pageable pageable, Sort sort) {
        return FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())
            ? usageArchiveRepository.findAaclByScenarioIdAndRhAccountNumber(scenario.getId(), accountNumber,
            searchValue, pageable, sort)
            : aaclUsageRepository.findByScenarioIdAndRhAccountNumber(scenario.getId(), accountNumber, searchValue,
            pageable, sort);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void deleteFromScenario(String scenarioId) {
        aaclUsageRepository.deleteFromScenario(scenarioId, RupContextUtils.getUserName());
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public List<String> moveToArchive(Scenario scenario) {
        LOGGER.info("Move details to archive. Started. {}", ForeignLogUtils.scenario(scenario));
        String scenarioId = scenario.getId();
        String userName = RupContextUtils.getUserName();
        aaclUsageRepository.addToBaselineByScenarioId(scenarioId, userName);
        List<String> usageIds = usageArchiveRepository.copyToArchiveByScenarioId(scenarioId, userName);
        usageAuditService.deleteActionsForExcludedByScenarioId(scenarioId);
        aaclUsageRepository.deleteLockedByScenarioId(scenarioId);
        aaclUsageRepository.deleteExcludedByScenarioId(scenarioId);
        LOGGER.info("Move details to archive. Finished. {}, UsagesCount={}", ForeignLogUtils.scenario(scenario),
            LogUtils.size(usageIds));
        return usageIds;
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void updatePaidInfo(List<PaidUsage> paidUsages) {
        usageService.updatePaidUsages(paidUsages, ids -> usageArchiveRepository.findAaclByIds(ids),
            usage -> usageArchiveRepository.insertAaclPaid(usage));
    }

    private Set<Integer> getAggregateClassIdsWithAmountsFromFundPool(String fundPoolId) {
        return fundPoolService.getDetailsByFundPoolId(fundPoolId).stream()
            .filter(detail -> 0 > BigDecimal.ZERO.compareTo(detail.getGrossAmount()))
            .map(FundPoolDetail::getAggregateLicenseeClass)
            .map(AggregateLicenseeClass::getId)
            .collect(Collectors.toSet());
    }

    private List<AggregateLicenseeClass> getAggregateClassesByIds(Set<Integer> aggregateClassIds) {
        Map<Integer, AggregateLicenseeClass> aggregateClassIdToClass =
            licenseeClassService.getAggregateLicenseeClasses(FdaConstants.AACL_PRODUCT_FAMILY).stream()
                .collect(Collectors.toMap(AggregateLicenseeClass::getId, Function.identity()));
        return aggregateClassIds.stream()
            .map(aggregateClassIdToClass::get)
            .sorted(Comparator.comparing(AggregateLicenseeClass::getId))
            .collect(Collectors.toList());
    }

    private boolean isUsageDisqualified(AaclClassifiedUsage usage) {
        return DISQUALIFIED_USAGE.equalsIgnoreCase(usage.getDiscipline())
            || DISQUALIFIED_USAGE.equalsIgnoreCase(usage.getEnrollmentProfile())
            || DISQUALIFIED_USAGE.equalsIgnoreCase(usage.getPublicationType());
    }

    private BigDecimal getAgeWeight(int index) {
        return DEFAULT_USAGES_AGE_WEIGHTS.size() > index
            ? DEFAULT_USAGES_AGE_WEIGHTS.get(index) : USAGES_AGE_WEIGHT_ZERO;
    }
}
