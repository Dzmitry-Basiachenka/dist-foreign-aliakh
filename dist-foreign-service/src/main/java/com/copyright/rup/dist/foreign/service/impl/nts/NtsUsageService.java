package com.copyright.rup.dist.foreign.service.impl.nts;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.NtsFundPool;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.INtsUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;

import com.google.common.collect.Table;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
public class NtsUsageService implements INtsUsageService {

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

    @Override
    @Transactional
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
        NtsFundPool ntsFundPool = usageBatch.getNtsFundPool();
        return ntsUsageRepository.findCountForBatch(ntsFundPool.getFundPoolPeriodFrom(),
            ntsFundPool.getFundPoolPeriodTo(), ntsFundPool.getMarkets());
    }

    @Override
    public int getUnclassifiedUsagesCount(Set<Long> wrWrkInsts) {
        return ntsUsageRepository.findUnclassifiedUsagesCountByWrWrkInsts(wrWrkInsts);
    }

    @Override
    public List<String> updateNtsWithdrawnUsagesAndGetIds() {
        return ntsUsageRepository.updateNtsWithdrawnUsagesAndGetIds();
    }

    @Override
    @Transactional
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
    public void addWithdrawnUsagesToFundPool(String fundPoolId, Set<String> batchIds, String userName) {
        ntsUsageRepository.addWithdrawnUsagesToFundPool(fundPoolId, batchIds, userName);
    }

    @Override
    public void deleteFromPreServiceFeeFund(String fundPoolId) {
        ntsUsageRepository.deleteFromPreServiceFeeFund(fundPoolId, RupContextUtils.getUserName());
    }

    @Override
    public void deleteBelletristicByScenarioId(String scenarioId) {
        ntsUsageRepository.deleteBelletristicByScenarioId(scenarioId);
    }

    @Override
    public void deleteFromScenario(String scenarioId) {
        ntsUsageRepository.deleteFromScenario(scenarioId, RupContextUtils.getUserName());
    }
}
