package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IReportService;

import org.perf4j.aop.Profiled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.time.LocalDate;
import java.util.EnumSet;

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

    private static final EnumSet<ScenarioStatusEnum> ARCHIVED_SCENARIO_STATUSES =
        EnumSet.of(ScenarioStatusEnum.SENT_TO_LM, ScenarioStatusEnum.ARCHIVED);

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;

    @Override
    @Profiled(tag = "usage.writeUsageCsvReport")
    public void writeUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        usageRepository.writeUsagesCsvReport(filter, pipedOutputStream);
    }

    @Override
    @Profiled(tag = "scenario.writeScenarioUsagesCsvReport")
    public void writeScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream outputStream) {
        if (ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())) {
            usageArchiveRepository.writeScenarioUsagesCsvReport(scenario.getId(), outputStream);
        } else {
            usageRepository.writeScenarioUsagesCsvReport(scenario.getId(), outputStream);
        }
    }

    @Override
    @Profiled(tag = "audit.writeAuditCsvReport")
    public void writeAuditCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        usageRepository.writeAuditCsvReport(filter, pipedOutputStream);
    }

    @Override
    @Profiled(tag = "report.undistributedLiabilitiesCsvReport")
    public void writeUndistributedLiabilitiesCsvReport(LocalDate paymentDate, OutputStream outputStream) {
        usageRepository.writeUndistributedLiabilitiesCsvReport(paymentDate, outputStream);
    }
}
