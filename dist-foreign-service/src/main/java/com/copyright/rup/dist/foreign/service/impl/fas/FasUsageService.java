package com.copyright.rup.dist.foreign.service.impl.fas;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.domain.common.util.CalculationUtils;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IFasUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.Table;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of {@link IFasUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/22/2020
 *
 * @author Ihar Suvorau
 */
@Service
public class FasUsageService implements IFasUsageService {

    private static final String CALCULATION_FINISHED_LOG_MESSAGE = "Calculated usages gross amount. " +
        "UsageBatchName={}, FundPoolAmount={}, TotalAmount={}, ConversionRate={}";
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Value("$RUP{dist.foreign.service_fee.cla_payee}")
    private BigDecimal claPayeeServiceFee;
    @Value("$RUP{dist.foreign.cla_account_number}")
    private Long claAccountNumber;
    @Value("$RUP{dist.foreign.grid.multi.select.record.threshold}")
    private int recordsThreshold;
    @Autowired
    private IFasUsageRepository fasUsageRepository;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    @Qualifier("usageChainExecutor")
    private IChainExecutor<Usage> chainExecutor;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;
    @Autowired
    @Qualifier("df.integration.piIntegrationCacheService")
    private IPiIntegrationService piIntegrationService;
    @Autowired
    private IRightsholderService rightsholderService;

    @Override
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
            fasUsageRepository.insert(usage);
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
    public List<UsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? usageRepository.findDtosByFilter(filter, pageable, sort)
            : List.of();
    }

    @Override
    public int getUsagesCount(UsageFilter filter) {
        return !filter.isEmpty() ? usageRepository.findCountByFilter(filter) : 0;
    }

    @Override
    @Transactional
    public void deleteFromScenarioByPayees(Set<String> scenarioIds, Set<Long> accountNumbers, String reason) {
        Set<String> usageIds =
            fasUsageRepository.deleteFromScenarioByPayees(scenarioIds, accountNumbers, RupContextUtils.getUserName());
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, reason);
    }

    @Override
    @Transactional
    public void redesignateToNtsWithdrawnByPayees(Set<String> scenarioIds, Set<Long> accountNumbers, String reason) {
        Set<String> usageIds = fasUsageRepository.redesignateToNtsWithdrawnByPayees(scenarioIds, accountNumbers,
            RupContextUtils.getUserName());
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, reason);
    }

    @Override
    public Set<Long> getAccountNumbersInvalidForExclude(Set<String> scenarioIds, Set<Long> accountNumbers) {
        return fasUsageRepository.findAccountNumbersInvalidForExclude(scenarioIds, accountNumbers);
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
        fasUsageRepository.updateResearchedUsages(researchedUsages);
        researchedUsages.forEach(
            researchedUsage -> usageAuditService.logAction(researchedUsage.getUsageId(),
                UsageActionTypeEnum.WORK_FOUND,
                String.format("Wr Wrk Inst %s was added based on research", researchedUsage.getWrWrkInst()))
        );
    }

    @Override
    @Transactional
    public List<String> moveToArchive(Scenario scenario) {
        LOGGER.info("Move details to archive. Started. {}", ForeignLogUtils.scenario(scenario));
        List<String> usageIds =
            usageArchiveRepository.copyToArchiveByScenarioId(scenario.getId(), RupContextUtils.getUserName());
        usageRepository.deleteByScenarioId(scenario.getId());
        LOGGER.info("Move details to archive. Finished. {}, UsagesCount={}", ForeignLogUtils.scenario(scenario),
            LogUtils.size(usageIds));
        return usageIds;
    }

    @Override
    public List<Usage> getUsagesWithAmounts(UsageFilter filter) {
        return fasUsageRepository.findWithAmountsAndRightsholders(filter);
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
    public void recalculateUsagesForRefresh(UsageFilter filter, Scenario scenario) {
        Map<Long, Usage> rhToUsageMap = getRightsholdersInformation(scenario.getId());
        List<Usage> newUsages = fasUsageRepository.findWithAmountsAndRightsholders(filter);
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
    public List<Usage> getUsagesForReconcile(String scenarioId) {
        return fasUsageRepository.findForReconcile(scenarioId);
    }

    @Override
    public Map<Long, Usage> getRightsholdersInformation(String scenarioId) {
        return fasUsageRepository.findRightsholdersInformation(scenarioId);
    }

    @Override
    public List<String> updateNtsWithdrawnUsagesAndGetIds() {
        return fasUsageRepository.updateNtsWithdrawnUsagesAndGetIds();
    }

    @Override
    public Long getClaAccountNumber() {
        return claAccountNumber;
    }

    @Override
    public int getRecordsThreshold() {
        return recordsThreshold;
    }

    @Override
    @Transactional
    public void updateUsages(List<String> usageIds, Long wrWrkInst, String reason) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Update FAS usages. Started. UsageIds={}, Reason={}, UserName={}",
            usageIds, reason, userName);
        fasUsageRepository.updateUsagesWrWrkInstAndStatus(usageIds, wrWrkInst, userName);
        //TODO: implement PI matching and getting rights
        LOGGER.info("Update FAS usages. Finished. UsageIds={}, Reason={}, UserName={}",
            usageIds, reason, userName);
    }

    private void populateTitlesStandardNumberAndType(List<ResearchedUsage> researchedUsages) {
        researchedUsages.forEach(researchedUsage -> {
            Work work = piIntegrationService.findWorkByWrWrkInst(researchedUsage.getWrWrkInst());
            researchedUsage.setStandardNumberType(work.getMainIdnoType());
            researchedUsage.setStandardNumber(work.getMainIdno());
            researchedUsage.setSystemTitle(work.getMainTitle());
        });
    }

    private void fillPayeeParticipatingForRefresh(Map<String, Table<String, String, Object>> preferencesMap,
                                                  Usage newUsage, Usage scenarioUsage) {
        if (Objects.nonNull(scenarioUsage)) {
            newUsage.setPayeeParticipating(scenarioUsage.isPayeeParticipating());
        } else {
            fillPayeeParticipating(preferencesMap, newUsage);
        }
    }

    private Set<String> getPayeeAndRhIds(Map<String, Map<String, Rightsholder>> rollUps) {
        return rollUps.values()
            .stream()
            .flatMap(map -> map.values().stream().map(Rightsholder::getId))
            .collect(Collectors.toSet());
    }

    private void calculateAmounts(Usage usage, boolean rhParticipatingFlag) {
        //usages that have CLA as Payee should get 10% service fee
        CalculationUtils.recalculateAmounts(usage, rhParticipatingFlag,
            claAccountNumber.equals(usage.getPayee().getAccountNumber())
                && FdaConstants.CLA_FAS_PRODUCT_FAMILY.equals(usage.getProductFamily())
                ? claPayeeServiceFee
                : prmIntegrationService.getRhParticipatingServiceFee(rhParticipatingFlag));
    }

    private void addScenarioInfo(Usage usage, Scenario scenario) {
        usage.setScenarioId(scenario.getId());
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.setUpdateUser(scenario.getCreateUser());
    }

    private void fillPayeeParticipating(Map<String, Table<String, String, Object>> preferencesMap, Usage usage) {
        boolean payeeParticipating = !usage.getRightsholder().getId().equals(usage.getPayee().getId())
            ? prmIntegrationService.isRightsholderParticipating(preferencesMap, usage.getPayee().getId(),
            usage.getProductFamily())
            : usage.isRhParticipating();
        usage.setPayeeParticipating(payeeParticipating);
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
}
