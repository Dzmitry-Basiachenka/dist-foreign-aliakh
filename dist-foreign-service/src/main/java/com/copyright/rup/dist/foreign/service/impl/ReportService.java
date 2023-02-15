package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.RhTaxInformation;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAaclReportRepository;
import com.copyright.rup.dist.foreign.repository.api.IFasReportRepository;
import com.copyright.rup.dist.foreign.repository.api.INtsReportRepository;
import com.copyright.rup.dist.foreign.repository.api.ISalReportRepository;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IRhTaxService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.NtsWithdrawnBatchesCsvReportWriter;
import com.copyright.rup.dist.foreign.service.impl.csv.RhTaxInformationCsvReportWriter;

import org.apache.commons.collections4.CollectionUtils;
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
    private IAaclReportRepository aaclReportRepository;
    @Autowired
    private IFasReportRepository fasReportRepository;
    @Autowired
    private INtsReportRepository ntsReportRepository;
    @Autowired
    private ISalReportRepository salReportRepository;
    @Autowired
    private IFasUsageService fasUsageService;
    @Autowired
    private IRhTaxService rhTaxService;

    @Value("$RUP{dist.foreign.rro.default_estimated_service_fee}")
    private BigDecimal defaultEstimatedServiceFee;

    @Override
    public void writeFasUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        fasReportRepository.writeFasUsageCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeNtsUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        ntsReportRepository.writeNtsUsageCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeAaclUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        aaclReportRepository.writeAaclUsagesCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeSalUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        salReportRepository.writeSalUsagesCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeFasScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream outputStream) {
        if (FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())) {
            fasReportRepository.writeArchivedFasScenarioUsagesCsvReport(scenario.getId(), outputStream);
        } else {
            fasReportRepository.writeFasScenarioUsagesCsvReport(scenario.getId(), outputStream);
        }
    }

    @Override
    public void writeNtsScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream outputStream) {
        if (FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())) {
            ntsReportRepository.writeArchivedNtsScenarioUsagesCsvReport(scenario.getId(), outputStream);
        } else {
            ntsReportRepository.writeNtsScenarioUsagesCsvReport(scenario.getId(), outputStream);
        }
    }

    @Override
    public void writeAaclScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream pipedOutputStream) {
        if (FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())) {
            aaclReportRepository.writeArchivedAaclScenarioUsagesCsvReport(scenario.getId(), pipedOutputStream);
        } else {
            aaclReportRepository.writeAaclScenarioUsagesCsvReport(scenario.getId(), pipedOutputStream);
        }
    }

    @Override
    public void writeSalScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream pipedOutputStream) {
        if (FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())) {
            salReportRepository.writeArchivedSalScenarioUsagesCsvReport(scenario.getId(), pipedOutputStream);
        } else {
            salReportRepository.writeSalScenarioUsagesCsvReport(scenario.getId(), pipedOutputStream);
        }
    }

    @Override
    public void writeScenarioRightsholderTotalsCsvReport(Scenario scenario, PipedOutputStream outputStream) {
        if (FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())) {
            fasReportRepository.writeArchivedScenarioRightsholderTotalsCsvReport(scenario.getId(), outputStream);
        } else {
            fasReportRepository.writeScenarioRightsholderTotalsCsvReport(scenario.getId(), outputStream);
        }
    }

    @Override
    public void writeWorkSharesByAggLcClassSummaryCsvReport(Scenario scenario, OutputStream outputStream) {
        aaclReportRepository.
            writeWorkSharesByAggLcClassSummaryCsvReport(scenario.getId(), scenario.getStatus(), outputStream);
    }

    @Override
    public void writeWorkSharesByAggLcClassCsvReport(Scenario scenario, OutputStream outputStream) {
        aaclReportRepository.writeWorkSharesByAggLcClassCsvReport(scenario.getId(), scenario.getStatus(), outputStream);
    }

    @Override
    public void writeAuditFasCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        fasReportRepository.writeAuditFasCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeAuditAaclCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        aaclReportRepository.writeAuditAaclCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeAuditNtsCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        ntsReportRepository.writeAuditNtsCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeAuditSalCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        salReportRepository.writeAuditSalCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeUndistributedLiabilitiesCsvReport(LocalDate paymentDate, OutputStream outputStream,
                                                       Set<String> productFamilies) {
        fasReportRepository.writeUndistributedLiabilitiesCsvReport(paymentDate, outputStream,
            defaultEstimatedServiceFee, productFamilies);
    }

    @Override
    public void writeAaclBaselineUsagesCsvReport(int numberOfYears, OutputStream outputStream) {
        aaclReportRepository.writeAaclBaselineUsagesCsvReport(numberOfYears, outputStream);
    }

    @Override
    public void writeFasBatchSummaryCsvReport(OutputStream outputStream) {
        fasReportRepository.writeFasBatchSummaryCsvReport(outputStream);
    }

    @Override
    public void writeNtsWithdrawnBatchSummaryCsvReport(OutputStream outputStream) {
        ntsReportRepository.writeNtsWithdrawnBatchSummaryCsvReport(outputStream);
    }

    @Override
    public void writeResearchStatusCsvReport(OutputStream outputStream) {
        fasReportRepository.writeResearchStatusCsvReport(outputStream);
    }

    @Override
    public void writeFasServiceFeeTrueUpCsvReport(LocalDate fromDate, LocalDate toDate, LocalDate paymentDateTo,
                                                  OutputStream outputStream) {
        fasReportRepository.writeFasServiceFeeTrueUpCsvReport(fromDate, toDate, paymentDateTo, outputStream,
            fasUsageService.getClaAccountNumber(), defaultEstimatedServiceFee);
    }

    @Override
    public void writeNtsServiceFeeTrueUpCsvReport(Scenario scenario, OutputStream outputStream) {
        ntsReportRepository.writeNtsServiceFeeTrueUpCsvReport(scenario, outputStream, defaultEstimatedServiceFee);
    }

    @Override
    public void writeSummaryMarkerCsvReport(List<UsageBatch> batches, OutputStream outputStream) {
        fasReportRepository.writeSummaryMarketCsvReport(
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
        fasReportRepository.writeOwnershipAdjustmentCsvReport(scenarioId, statuses, outputStream);
    }

    @Override
    public void writeWorkClassificationCsvReport(Set<String> batchesIds, String searchValue,
                                                 PipedOutputStream pipedOutputStream) {
        if (CollectionUtils.isNotEmpty(batchesIds)) {
            ntsReportRepository.writeWorkClassificationCsvReport(batchesIds, searchValue, pipedOutputStream);
        } else {
            ntsReportRepository.writeWorkClassificationCsvReport(searchValue, pipedOutputStream);
        }
    }

    @Override
    public void writeFasExcludeDetailsByPayeeCsvReport(ExcludePayeeFilter filter, Set<Long> selectedAccountNumbers,
                                                       PipedOutputStream pipedOutputStream) {
        fasReportRepository.writeFasExcludeDetailsByPayeeCsvReport(filter, selectedAccountNumbers, pipedOutputStream);
    }

    @Override
    public void writeAaclExcludeDetailsByPayeeCsvReport(ExcludePayeeFilter filter, Set<Long> selectedAccountNumbers,
                                                        PipedOutputStream pipedOutputStream) {
        aaclReportRepository.writeAaclExcludeDetailsByPayeeCsvReport(filter, selectedAccountNumbers, pipedOutputStream);
    }

    @Override
    public void writeAaclUndistributedLiabilitiesCsvReport(OutputStream outputStream) {
        aaclReportRepository.writeAaclUndistributedLiabilitiesCsvReport(outputStream);
    }

    @Override
    public void writeNtsUndistributedLiabilitiesReport(OutputStream outputStream) {
        ntsReportRepository.writeNtsUndistributedLiabilitiesCsvReport(defaultEstimatedServiceFee, outputStream);
    }

    @Override
    public void writeSalLiabilitiesByRhReport(List<Scenario> scenarios, OutputStream outputStream) {
        salReportRepository.writeSalLiabilitiesByRhCsvReport(scenarios, outputStream);
    }

    @Override
    public void writeTaxNotificationCsvReport(String productFamily, Set<String> scenarioIds, int numberOfDays,
                                              OutputStream outputStream) {
        List<RhTaxInformation> rhTaxInformation =
            rhTaxService.getRhTaxInformation(productFamily, scenarioIds, numberOfDays);
        try (RhTaxInformationCsvReportWriter writer = new RhTaxInformationCsvReportWriter(outputStream)) {
            writer.writeAll(rhTaxInformation);
        }
    }

    @Override
    public void writeSalLiabilitiesSummaryByRhAndWorkCsvReport(List<Scenario> scenarios,
                                                               OutputStream outputStream) {
        salReportRepository.writeSalLiabilitiesSummaryByRhAndWorkCsvReport(scenarios, outputStream);
    }

    @Override
    public void writeSalUndistributedLiabilitiesCsvReport(OutputStream outputStream) {
        salReportRepository.writeSalUndistributedLiabilitiesCsvReport(outputStream);
    }

    @Override
    public void writeSalFundPoolsCsvReport(OutputStream outputStream) {
        salReportRepository.writeSalFundPoolsCsvReport(outputStream);
    }

    @Override
    public void writeSalFundPoolsCsvReport(int distributionYear, OutputStream outputStream) {
        salReportRepository.writeSalFundPoolsCsvReport(distributionYear, outputStream);
    }

    @Override
    public void writeSalHistoricalItemBankDetailsReport(Long licenseeAccountNumber, Integer periodEndYearFrom,
                                                        Integer periodEndYearTo, OutputStream outputStream) {
        salReportRepository.writeSalHistoricalItemBankDetailsReport(licenseeAccountNumber, periodEndYearFrom,
            periodEndYearTo, outputStream);
    }

    @Override
    public void writeNtsFundPoolsCsvReport(OutputStream outputStream) {
        //TODO will be implemented with backend logic
    }
}
