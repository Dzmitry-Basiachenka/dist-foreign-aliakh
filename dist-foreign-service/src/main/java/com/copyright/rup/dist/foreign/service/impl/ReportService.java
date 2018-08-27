package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.time.LocalDate;
import java.util.List;

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
        usageRepository.writeServiceFeeTrueUpCsvReport(fromDate, toDate, paymentDateTo, outputStream);
    }

    @Override
    public void writeSummaryMarkerReport(List<String> batchIds, OutputStream outputStream) {
        // TODO {isuvorau} call repository method after implementation
    }
}
