package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.WithdrawnBatchesCsvReportHandler;

import org.apache.ibatis.executor.result.DefaultResultContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements {@link IReportService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 04/05/2018
 *
 * @author Nikita Levyankov
 */
@Service
public class ReportService implements IReportService {

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private IUsageService usageService;

    @Override
    public void writeUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        usageRepository.writeUsagesCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream outputStream) {
        if (FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())) {
            usageArchiveRepository.writeScenarioUsagesCsvReport(scenario.getId(), outputStream);
        } else {
            usageRepository.writeScenarioUsagesCsvReport(scenario.getId(), outputStream);
        }
    }

    @Override
    public void writeAuditCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        usageRepository.writeAuditCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeUndistributedLiabilitiesCsvReport(LocalDate paymentDate, OutputStream outputStream) {
        usageRepository.writeUndistributedLiabilitiesCsvReport(paymentDate, outputStream);
    }

    @Override
    public void writeBatchSummaryCsvReport(OutputStream outputStream) {
        usageRepository.writeBatchSummaryCsvReport(outputStream);
    }

    @Override
    public void writeResearchStatusCsvReport(OutputStream outputStream) {
        usageRepository.writeResearchStatusCsvReport(outputStream);
    }

    @Override
    public void writeServiceFeeTrueUpCsvReport(LocalDate fromDate, LocalDate toDate, LocalDate paymentDateTo,
                                               OutputStream outputStream) {
        usageRepository.writeServiceFeeTrueUpCsvReport(fromDate, toDate, paymentDateTo, outputStream,
            usageService.getClaAccountNumber());
    }

    @Override
    public void writeSummaryMarkerCsvReport(List<UsageBatch> batches, OutputStream outputStream) {
        usageRepository.writeSummaryMarketCsvReport(
            batches.stream().map(UsageBatch::getId).collect(Collectors.toList()), outputStream);
    }

    @Override
    public void writeWithdrawnBatchesCsvReport(List<UsageBatch> batches, BigDecimal grossAmount,
                                               OutputStream outputStream) {
        try (WithdrawnBatchesCsvReportHandler handler = new WithdrawnBatchesCsvReportHandler(outputStream)) {
            batches.forEach(usageBatch -> {
                DefaultResultContext<UsageBatch> context = new DefaultResultContext<>();
                context.nextResultObject(usageBatch);
                handler.handleResult(context);
            });
            UsageBatch usageBatch = new UsageBatch();
            usageBatch.setName("Total");
            usageBatch.setGrossAmount(grossAmount);
            DefaultResultContext<UsageBatch> context = new DefaultResultContext<>();
            context.nextResultObject(usageBatch);
            handler.handleResult(context);
        }
    }
}
