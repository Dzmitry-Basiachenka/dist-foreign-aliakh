package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.time.LocalDate;

/**
 * Interface that provides ability to generate multiple reports.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 04/05/2018
 *
 * @author Nikita Levyankov
 */
public interface IReportService {

    /**
     * Writes usages found by {@link AuditFilter} into CSV output stream.
     *
     * @param filter            {@link AuditFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAuditCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes usages found by filter into csv output stream.
     *
     * @param filter            instance of {@link UsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes scenario usages into csv output stream.
     *
     * @param scenario          {@link Scenario}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream pipedOutputStream);

    /**
     * Writes Undistributed Liabilities Reconciliation Report into the output stream in csv format.
     *
     * @param paymentDate  payment date
     * @param outputStream instance of {@link OutputStream}
     */
    void writeUndistributedLiabilitiesCsvReport(LocalDate paymentDate, OutputStream outputStream);

    /**
     * Writes Batch Summary Report into the output stream in csv format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeBatchSummaryCsvReport(OutputStream outputStream);

    /**
     * Writes Research Status Report into the output stream in csv format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeResearchStatusCsvReport(OutputStream outputStream);
}
