package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IReportRepository;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.PreServiceFeeFundBatchesCsvReportHandler;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.result.DefaultResultContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
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
    private IReportRepository reportRepository;
    @Autowired
    private IUsageService usageService;

    @Value("$RUP{dist.foreign.rro.default_estimated_service_fee}")
    private BigDecimal defaultEstimatedServiceFee;

    @Override
    public void writeFasUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        reportRepository.writeFasUsageCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeNtsUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        reportRepository.writeNtsUsageCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeAaclUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        reportRepository.writeAaclUsagesCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeFasScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream outputStream) {
        if (FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())) {
            reportRepository.writeArchivedFasScenarioUsagesCsvReport(scenario.getId(), outputStream);
        } else {
            reportRepository.writeFasScenarioUsagesCsvReport(scenario.getId(), outputStream);
        }
    }

    @Override
    public void writeNtsScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream outputStream) {
        if (FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())) {
            reportRepository.writeArchivedNtsScenarioUsagesCsvReport(scenario.getId(), outputStream);
        } else {
            reportRepository.writeNtsScenarioUsagesCsvReport(scenario.getId(), outputStream);
        }
    }

    @Override
    public void writeScenarioRightsholderTotalsCsvReport(Scenario scenario, PipedOutputStream outputStream) {
        if (FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())) {
            reportRepository.writeArchivedScenarioRightsholderTotalsCsvReport(scenario.getId(), outputStream);
        } else {
            reportRepository.writeScenarioRightsholderTotalsCsvReport(scenario.getId(), outputStream);
        }
    }

    @Override
    public void writeAuditFasCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        reportRepository.writeAuditFasCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeAuditNtsCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        reportRepository.writeAuditNtsCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeUndistributedLiabilitiesCsvReport(LocalDate paymentDate, OutputStream outputStream,
                                                       Set<String> productFamilies) {
        reportRepository.writeUndistributedLiabilitiesCsvReport(paymentDate, outputStream, defaultEstimatedServiceFee,
            productFamilies);
    }

    @Override
    public void writeFasBatchSummaryCsvReport(OutputStream outputStream) {
        reportRepository.writeFasBatchSummaryCsvReport(outputStream);
    }

    @Override
    public void writeNtsWithdrawnBatchSummaryCsvReport(OutputStream outputStream) {
        reportRepository.writeNtsWithdrawnBatchSummaryCsvReport(outputStream);
    }

    @Override
    public void writeResearchStatusCsvReport(OutputStream outputStream) {
        reportRepository.writeResearchStatusCsvReport(outputStream);
    }

    @Override
    public void writeServiceFeeTrueUpCsvReport(LocalDate fromDate, LocalDate toDate, LocalDate paymentDateTo,
                                               OutputStream outputStream) {
        reportRepository.writeServiceFeeTrueUpCsvReport(fromDate, toDate, paymentDateTo, outputStream,
            usageService.getClaAccountNumber(), defaultEstimatedServiceFee);
    }

    @Override
    public void writeSummaryMarkerCsvReport(List<UsageBatch> batches, OutputStream outputStream) {
        reportRepository.writeSummaryMarketCsvReport(
            batches.stream().map(UsageBatch::getId).collect(Collectors.toList()), outputStream);
    }

    @Override
    public void writePreServiceFeeFundBatchesCsvReport(List<UsageBatch> batches, BigDecimal totalGrossAmount,
                                                       OutputStream outputStream) {
        try (PreServiceFeeFundBatchesCsvReportHandler handler =
                 new PreServiceFeeFundBatchesCsvReportHandler(outputStream)) {
            batches.forEach(usageBatch -> handleUsageBatch(handler, usageBatch));
            UsageBatch usageBatch = new UsageBatch();
            usageBatch.setName("Total");
            usageBatch.setGrossAmount(totalGrossAmount);
            handleUsageBatch(handler, usageBatch);
        }
    }

    @Override
    public void writeOwnershipAdjustmentCsvReport(String scenarioId, Set<RightsholderDiscrepancyStatusEnum> statuses,
                                                  OutputStream outputStream) {
        reportRepository.writeOwnershipAdjustmentCsvReport(scenarioId, statuses, outputStream);
    }

    @Override
    public void writeWorkClassificationCsvReport(Set<String> batchesIds, String searchValue,
                                                 PipedOutputStream pipedOutputStream) {
        if (CollectionUtils.isNotEmpty(batchesIds)) {
            reportRepository.writeWorkClassificationCsvReport(batchesIds, searchValue, pipedOutputStream);
        } else {
            reportRepository.writeWorkClassificationCsvReport(searchValue, pipedOutputStream);
        }
    }

    private void handleUsageBatch(PreServiceFeeFundBatchesCsvReportHandler handler, UsageBatch usageBatch) {
        DefaultResultContext<UsageBatch> context = new DefaultResultContext<>();
        context.nextResultObject(usageBatch);
        handler.handleResult(context);
    }
}
