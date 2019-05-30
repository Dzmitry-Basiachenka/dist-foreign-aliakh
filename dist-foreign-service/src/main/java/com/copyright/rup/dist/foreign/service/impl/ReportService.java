package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderDiscrepancyRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.PreServiceFeeFundBatchesCsvReportHandler;

import org.apache.ibatis.executor.result.DefaultResultContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private IRightsholderDiscrepancyRepository rightsholderDiscrepancyRepository;

    @Value("$RUP{dist.foreign.rro.default_estimated_service_fee}")
    private BigDecimal defaultEstimatedServiceFee;

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
    public void writeScenarioRightsholderTotalsCsvReport(Scenario scenario, PipedOutputStream outputStream) {
        if (FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())) {
            usageArchiveRepository.writeScenarioRightsholderTotalsCsvReport(scenario.getId(), outputStream);
        } else {
            usageRepository.writeScenarioRightsholderTotalsCsvReport(scenario.getId(), outputStream);
        }
    }

    @Override
    public void writeAuditCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        usageRepository.writeAuditCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeUndistributedLiabilitiesCsvReport(LocalDate paymentDate, OutputStream outputStream) {
        usageRepository.writeUndistributedLiabilitiesCsvReport(paymentDate, outputStream, defaultEstimatedServiceFee);
    }

    @Override
    public void writeFasBatchSummaryCsvReport(OutputStream outputStream) {
        usageRepository.writeFasBatchSummaryCsvReport(outputStream);
    }

    @Override
    public void writeResearchStatusCsvReport(OutputStream outputStream) {
        usageRepository.writeResearchStatusCsvReport(outputStream);
    }

    @Override
    public void writeServiceFeeTrueUpCsvReport(LocalDate fromDate, LocalDate toDate, LocalDate paymentDateTo,
                                               OutputStream outputStream) {
        usageRepository.writeServiceFeeTrueUpCsvReport(fromDate, toDate, paymentDateTo, outputStream,
            usageService.getClaAccountNumber(), defaultEstimatedServiceFee);
    }

    @Override
    public void writeSummaryMarkerCsvReport(List<UsageBatch> batches, OutputStream outputStream) {
        usageRepository.writeSummaryMarketCsvReport(
            batches.stream().map(UsageBatch::getId).collect(Collectors.toList()), outputStream);
    }

    @Override
    public void writePreServiceFeeFundBatchesCsvReport(List<UsageBatch> batches, BigDecimal totalGrossAmount,
                                                       OutputStream outputStream) {
        try (PreServiceFeeFundBatchesCsvReportHandler handler =
                 new PreServiceFeeFundBatchesCsvReportHandler(outputStream)) {
            batches.forEach(usageBatch -> {
                handleUsageBatch(handler, usageBatch);
            });
            UsageBatch usageBatch = new UsageBatch();
            usageBatch.setName("Total");
            usageBatch.setGrossAmount(totalGrossAmount);
            handleUsageBatch(handler, usageBatch);
        }
    }

    @Override
    public void writeOwnershipAdjustmentCsvReport(String scenarioId, List<RightsholderDiscrepancyStatusEnum> statuses,
                                                  OutputStream outputStream) {
        rightsholderDiscrepancyRepository.writeOwnershipAdjustmentCsvReport(scenarioId, statuses, outputStream);
    }

    private void handleUsageBatch(PreServiceFeeFundBatchesCsvReportHandler handler, UsageBatch usageBatch) {
        DefaultResultContext<UsageBatch> context = new DefaultResultContext<>();
        context.nextResultObject(usageBatch);
        handler.handleResult(context);
    }
}
