package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.common.util.CalculationUtils;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvErrorResultWriter;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvProcessingResult;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private IUsageAuditService usageAuditService;

    @Override
    public List<UsageDto> getUsages(UsageFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? usageRepository.findByFilter(filter, pageable, sort)
            : Collections.emptyList();
    }

    @Override
    public int getUsagesCount(UsageFilter filter) {
        return !filter.isEmpty() ? usageRepository.getCountByFilter(filter) : 0;
    }

    @Override
    public void writeUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        usageRepository.writeUsagesCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeScenarioUsagesCsvReport(String scenarioId, PipedOutputStream outputStream) {
        usageRepository.writeScenarioUsagesCsvReport(scenarioId, outputStream);
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
        usages.forEach(usage -> usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED,
            String.format("Uploaded in '%s' Batch", usageBatch.getName())));
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
        return usageRepository.findWithAmountsAndRightsholders(filter);
    }

    @Override
    public void addUsagesToScenario(List<Usage> usages, Scenario scenario) {
        usageRepository.addToScenario(usages.stream().map(Usage::getId).collect(Collectors.toList()),
            scenario.getId(), scenario.getCreateUser());
    }

    @Override
    public void deleteUsagesFromScenario(String scenarioId) {
        usageRepository.deleteFromScenario(scenarioId, RupContextUtils.getUserName());
    }

    @Override
    public Set<Long> getDuplicateDetailIds(List<Long> detailIds) {
        return CollectionUtils.isNotEmpty(detailIds)
            ? usageRepository.getDuplicateDetailIds(detailIds)
            : Collections.emptySet();
    }

    @Override
    public List<RightsholderTotalsHolder> getRightsholderTotalsHoldersByScenarioId(String scenarioId,
                                                                                   String searchValue,
                                                                                   Pageable pageable, Sort sort) {
        return usageRepository.getRightsholderTotalsHoldersByScenarioId(scenarioId, searchValue, pageable, sort);
    }

    @Override
    public int getRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue) {
        return usageRepository.getRightsholderTotalsHolderCountByScenarioId(scenarioId, searchValue);
    }

    @Override
    public int getCountByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue) {
        return usageRepository.getCountByScenarioIdAndRhAccountNumber(accountNumber, scenarioId, searchValue);
    }

    @Override
    public List<UsageDto> getByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId,
                                                            String searchValue, Pageable pageable, Sort sort) {
        return usageRepository.getByScenarioIdAndRhAccountNumber(accountNumber, scenarioId, searchValue, pageable,
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
