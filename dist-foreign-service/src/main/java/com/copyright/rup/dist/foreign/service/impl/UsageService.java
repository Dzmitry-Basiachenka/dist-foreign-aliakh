package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
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
import com.copyright.rup.dist.foreign.integration.prm.impl.PrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvErrorResultWriter;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvProcessingResult;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import com.google.common.collect.Table;

import org.apache.commons.collections.CollectionUtils;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
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
 */
@Service
public class UsageService implements IUsageService {

    private static final String CALCULATION_FINISHED_LOG_MESSAGE = "Calculated usages gross amount. " +
        "UsageBatchName={}, FundPoolAmount={}, TotalAmount={}, ConversionRate={}";
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;

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
                prmIntegrationService.isRightsholderParticipating(usage.getRightsholder().getAccountNumber());
            usage.setRhParticipating(rhParticipatingFlag);
            usage.setServiceFee(prmIntegrationService.getRhParticipatingServiceFee(rhParticipatingFlag));
            usage.setServiceFeeAmount(
                CalculationUtils.calculateServiceFeeAmount(usage.getGrossAmount(), usage.getServiceFee()));
            usage.setNetAmount(
                CalculationUtils.calculateNetAmount(usage.getGrossAmount(), usage.getServiceFeeAmount()));
        });
        return usages;
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
            usage.getPayee()
                .setAccountNumber(PrmRollUpService.getPayeeAccountNumber(rollUps, usage.getRightsholder(),
                    PrmIntegrationService.FAS_PRODUCT_FAMILY));
        });
        usageRepository.addToScenario(usages);
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
