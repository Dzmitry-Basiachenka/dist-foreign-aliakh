package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.RhTaxInformation;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IReportRepository;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IRhTaxService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.NtsWithdrawnBatchesCsvReportWriter;
import com.copyright.rup.dist.foreign.service.impl.csv.RhTaxInformationCsvReportWriter;

import org.apache.commons.collections.CollectionUtils;
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
    private IFasUsageService fasUsageService;
    @Autowired
    private IRhTaxService rhTaxService;

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
    public void writeSalUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        reportRepository.writeSalUsagesCsvReport(filter, pipedOutputStream);
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
    public void writeAaclScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream pipedOutputStream) {
        if (FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())) {
            reportRepository.writeArchivedAaclScenarioUsagesCsvReport(scenario.getId(), pipedOutputStream);
        } else {
            reportRepository.writeAaclScenarioUsagesCsvReport(scenario.getId(), pipedOutputStream);
        }
    }

    @Override
    public void writeSalScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream pipedOutputStream) {
        if (FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())) {
            reportRepository.writeArchivedSalScenarioUsagesCsvReport(scenario.getId(), pipedOutputStream);
        } else {
            reportRepository.writeSalScenarioUsagesCsvReport(scenario.getId(), pipedOutputStream);
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
    public void writeWorkSharesByAggLcClassSummaryCsvReport(Scenario scenario, OutputStream outputStream) {
        reportRepository.
            writeWorkSharesByAggLcClassSummaryCsvReport(scenario.getId(), scenario.getStatus(), outputStream);
    }

    @Override
    public void writeWorkSharesByAggLcClassCsvReport(Scenario scenario, OutputStream outputStream) {
        reportRepository.writeWorkSharesByAggLcClassCsvReport(scenario.getId(), scenario.getStatus(), outputStream);
    }

    @Override
    public void writeAuditFasCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        reportRepository.writeAuditFasCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeAuditAaclCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        reportRepository.writeAuditAaclCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeAuditNtsCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        reportRepository.writeAuditNtsCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeAuditSalCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        reportRepository.writeAuditSalCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeUndistributedLiabilitiesCsvReport(LocalDate paymentDate, OutputStream outputStream,
                                                       Set<String> productFamilies) {
        reportRepository.writeUndistributedLiabilitiesCsvReport(paymentDate, outputStream, defaultEstimatedServiceFee,
            productFamilies);
    }

    @Override
    public void writeAaclBaselineUsagesCsvReport(int numberOfYears, OutputStream outputStream) {
        reportRepository.writeAaclBaselineUsagesCsvReport(numberOfYears, outputStream);
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
    public void writeFasServiceFeeTrueUpCsvReport(LocalDate fromDate, LocalDate toDate, LocalDate paymentDateTo,
                                                  OutputStream outputStream) {
        reportRepository.writeFasServiceFeeTrueUpCsvReport(fromDate, toDate, paymentDateTo, outputStream,
            fasUsageService.getClaAccountNumber(), defaultEstimatedServiceFee);
    }

    @Override
    public void writeNtsServiceFeeTrueUpCsvReport(Scenario scenario, OutputStream outputStream) {
        reportRepository.writeNtsServiceFeeTrueUpCsvReport(scenario, outputStream, defaultEstimatedServiceFee);
    }

    @Override
    public void writeSummaryMarkerCsvReport(List<UsageBatch> batches, OutputStream outputStream) {
        reportRepository.writeSummaryMarketCsvReport(
            batches.stream().map(UsageBatch::getId).collect(Collectors.toList()), outputStream);
    }

    @Override
    public void writeNtsWithdrawnBatchesCsvReport(List<UsageBatch> batches, BigDecimal totalGrossAmount,
                                                  OutputStream outputStream) {
        try (NtsWithdrawnBatchesCsvReportWriter writer = new NtsWithdrawnBatchesCsvReportWriter(outputStream)) {
            writer.writeAll(batches);
            UsageBatch usageBatch = new UsageBatch();
            usageBatch.setName("Total");
            usageBatch.setGrossAmount(totalGrossAmount);
            writer.write(usageBatch);
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

    @Override
    public void writeFasExcludeDetailsByPayeeCsvReport(ExcludePayeeFilter filter, Set<Long> selectedAccountNumbers,
                                                       PipedOutputStream pipedOutputStream) {
        reportRepository.writeFasExcludeDetailsByPayeeCsvReport(filter, selectedAccountNumbers, pipedOutputStream);
    }

    @Override
    public void writeAaclExcludeDetailsByPayeeCsvReport(ExcludePayeeFilter filter, Set<Long> selectedAccountNumbers,
                                                        PipedOutputStream pipedOutputStream) {
        reportRepository.writeAaclExcludeDetailsByPayeeCsvReport(filter, selectedAccountNumbers, pipedOutputStream);
    }

    @Override
    public void writeAaclUndistributedLiabilitiesCsvReport(OutputStream outputStream) {
        reportRepository.writeAaclUndistributedLiabilitiesCsvReport(outputStream);
    }

    @Override
    public void writeNtsUndistributedLiabilitiesReport(OutputStream outputStream) {
        reportRepository.writeNtsUndistributedLiabilitiesCsvReport(defaultEstimatedServiceFee, outputStream);
    }

    @Override
    public void writeSalLiabilitiesByRhReport(List<Scenario> scenarios, OutputStream outputStream) {
        reportRepository.writeSalLiabilitiesByRhCsvReport(scenarios, outputStream);
    }

    @Override
    public void writeTaxNotificationCsvReport(Set<String> scenarioIds, int numberOfDays, OutputStream outputStream) {
        List<RhTaxInformation> rhTaxInformation = rhTaxService.getRhTaxInformation(scenarioIds, numberOfDays);
        try (RhTaxInformationCsvReportWriter writer = new RhTaxInformationCsvReportWriter(outputStream)) {
            writer.writeAll(rhTaxInformation);
        }
    }

    @Override
    public void writeSalLiabilitiesSummaryByRhAndWorkCsvReport(List<Scenario> scenarios,
                                                               OutputStream outputStream) {
        reportRepository.writeSalLiabilitiesSummaryByRhAndWorkCsvReport(scenarios, outputStream);
    }

    @Override
    public void writeSalUndistributedLiabilitiesCsvReport(OutputStream outputStream) {
        reportRepository.writeSalUndistributedLiabilitiesCsvReport(outputStream);
    }

    @Override
    public void writeSalFundPoolsCsvReport(int distributionYear, OutputStream outputStream) {
        reportRepository.writeSalFundPoolsCsvReport(distributionYear, outputStream);
    }

    @Override
    public void writeSalHistoricalItemBankDetailsReport(Long licenseeAccountNumber, Integer periodEndYearFrom,
                                                        Integer periodEndYearTo, OutputStream outputStream) {
        reportRepository.writeSalHistoricalItemBankDetailsReport(licenseeAccountNumber, periodEndYearFrom,
            periodEndYearTo, outputStream);
    }
}
