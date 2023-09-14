package com.copyright.rup.dist.foreign.service.impl.nts;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.INtsUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.Iterables;
import com.google.common.collect.Table;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link INtsUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/28/2020
 *
 * @author Aliaksandr Liakh
 */
@Service
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class NtsUsageService implements INtsUsageService {

    private static final String GET_RIGHTS_FINISHED_LOG_MESSAGE =
        "Send usages for getting rights. Finished. UsageBatchName={}, UsagesCount={}, WorkFoundUsagesCount={}";
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Value("$RUP{dist.foreign.markets}")
    private List<String> supportedMarkets;

    @Autowired
    private INtsUsageRepository ntsUsageRepository;
    @Autowired
    private IUsageAuditService usageAuditService;
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
    public List<String> insertUsages(UsageBatch usageBatch) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert NTS usages. Started. UsageBatchName={}, UserName={}", usageBatch.getName(), userName);
        List<String> usageIds = ntsUsageRepository.insertUsages(usageBatch, userName);
        LOGGER.info("Insert NTS usages. Finished. UsageBatchName={}, UserName={}, InsertedUsageCount={}",
            usageBatch.getName(), userName, LogUtils.size(usageIds));
        return usageIds;
    }

    @Override
    public int getUsagesCountForBatch(UsageBatch usageBatch) {
        NtsFields ntsFields = usageBatch.getNtsFields();
        return ntsUsageRepository.findCountForBatch(ntsFields.getFundPoolPeriodFrom(),
            ntsFields.getFundPoolPeriodTo(), ntsFields.getMarkets());
    }

    @Override
    public int getUnclassifiedUsagesCount(Set<Long> wrWrkInsts) {
        return ntsUsageRepository.findUnclassifiedUsagesCountByWrWrkInsts(wrWrkInsts);
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void populatePayeeAndCalculateAmountsForScenarioUsages(Scenario scenario) {
        String userName = RupContextUtils.getUserName();
        Set<Long> payeeAccountNumbers = new HashSet<>();
        List<Rightsholder> rightsholders = rightsholderService.getByScenarioId(scenario.getId());
        Set<String> rightsholdersIds = rightsholders.stream().map(BaseEntity::getId).collect(Collectors.toSet());
        Map<String, Map<String, Rightsholder>> rollUps = prmIntegrationService.getRollUps(rightsholdersIds);
        Map<String, Table<String, String, Object>> preferences = prmIntegrationService.getPreferences(rightsholdersIds);
        rightsholders.forEach(rightsholder -> {
            Long payeeAccountNumber = PrmRollUpService.getPayee(rollUps, rightsholder, FdaConstants.NTS_PRODUCT_FAMILY)
                .getAccountNumber();
            payeeAccountNumbers.add(payeeAccountNumber);
            boolean rhParticipating = prmIntegrationService.isRightsholderParticipating(preferences,
                rightsholder.getId(), FdaConstants.NTS_PRODUCT_FAMILY);
            BigDecimal serviceFee = prmIntegrationService.getRhParticipatingServiceFee(rhParticipating);
            ntsUsageRepository.calculateAmountsAndUpdatePayeeByAccountNumber(rightsholder.getAccountNumber(),
                scenario.getId(), serviceFee, rhParticipating, payeeAccountNumber, userName);
        });
        if (0 != BigDecimal.ZERO.compareTo(scenario.getNtsFields().getPostServiceFeeAmount())) {
            ntsUsageRepository.applyPostServiceFeeAmount(scenario.getId());
        }
        rightsholderService.updateRighstholdersAsync(payeeAccountNumbers);
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public List<String> moveToArchive(Scenario scenario) {
        LOGGER.info("Move details to archive. Started. {}", ForeignLogUtils.scenario(scenario));
        List<String> usageIds =
            usageArchiveRepository.copyNtsToArchiveByScenarioId(scenario.getId(), RupContextUtils.getUserName());
        usageArchiveRepository.moveFundUsagesToArchive(scenario.getId());
        usageAuditService.deleteActionsByScenarioId(scenario.getId());
        ntsUsageRepository.deleteByScenarioId(scenario.getId());
        LOGGER.info("Move details to archive. Finished. {}, UsagesCount={}", ForeignLogUtils.scenario(scenario),
            LogUtils.size(usageIds));
        return usageIds;
    }

    @Override
    public List<String> getMarkets() {
        return supportedMarkets;
    }

    @Override
    public void addWithdrawnUsagesToNtsFundPool(String fundPoolId, Set<String> batchIds, String userName) {
        ntsUsageRepository.addWithdrawnUsagesToNtsFundPool(fundPoolId, batchIds, userName);
    }

    @Override
    public void deleteFromNtsFundPool(String fundPoolId) {
        ntsUsageRepository.deleteFromNtsFundPool(fundPoolId, RupContextUtils.getUserName());
    }

    @Override
    public void deleteBelletristicByScenarioId(String scenarioId) {
        ntsUsageRepository.deleteBelletristicByScenarioId(scenarioId);
    }

    @Override
    public void deleteFromScenario(String scenarioId) {
        ntsUsageRepository.deleteFromScenario(scenarioId, RupContextUtils.getUserName());
    }

    @Override
    public void sendForGettingRights(List<String> usageIds, String batchName) {
        AtomicInteger usageIdsCount = new AtomicInteger(0);
        IChainExecutor<Usage> currentChainExecutor = chainExecutor;
        currentChainExecutor.execute(() ->
            Iterables.partition(usageIds, usagesBatchSize)
                .forEach(partition -> {
                    usageIdsCount.addAndGet(partition.size());
                    LOGGER.info("Send usages for getting rights. Started. UsageBatchName={}, UsagesCount={}",
                        batchName, usageIdsCount);
                    List<Usage> workFoundUsages = usageService.getUsagesByIds(partition)
                        .stream()
                        .filter(usage -> UsageStatusEnum.WORK_FOUND == usage.getStatus())
                        .collect(Collectors.toList());
                    currentChainExecutor.execute(workFoundUsages, ChainProcessorTypeEnum.RIGHTS);
                    LOGGER.info(GET_RIGHTS_FINISHED_LOG_MESSAGE, batchName, usageIdsCount,
                        LogUtils.size(workFoundUsages));
                }));
    }

    @Override
    @Transactional
    public void deleteFromScenarioByRightsholders(String scenarioId, Set<Long> accountNumbers, String reason) {
        ntsUsageRepository.recalculateAmountsFromExcludedRightshoders(scenarioId, accountNumbers);
        Set<String> usageIds = ntsUsageRepository.deleteFromScenarioByRightsholder(scenarioId, accountNumbers,
            RupContextUtils.getUserName());
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, reason);
    }
}
