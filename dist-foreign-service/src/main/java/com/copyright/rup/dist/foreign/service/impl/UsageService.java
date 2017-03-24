package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.common.util.CalculationUtils;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvErrorResultWriter;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvProcessingResult;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
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
    public void writeUsageCsvReport(UsageFilter filter, OutputStream outputStream) {
        usageRepository.writeUsagesCsvReport(filter, outputStream);
    }

    @Override
    public void writeErrorsCsvReport(CsvProcessingResult csvProcessingResult, OutputStream outputStream) {
        CsvErrorResultWriter downloader = new CsvErrorResultWriter();
        downloader.writeErrorsResult(outputStream, csvProcessingResult);
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
        LOGGER.info("Insert usages. Finished. UsageBatchName={}, UsagesCount={}, UserName={}", usageBatch.getName(),
            size, userName);
        return size;
    }

    @Override
    public void deleteUsageBatchDetails(UsageBatch usageBatch) {
        usageRepository.deleteUsages(usageBatch.getId());
    }

    @Override
    public List<Usage> getUsagesWithAmounts(UsageFilter filter) {
        return usageRepository.findWithAmounts(filter);
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
    public boolean detailIdExists(Long detailId) {
        return 0 < usageRepository.getCountByDetailId(detailId);
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
