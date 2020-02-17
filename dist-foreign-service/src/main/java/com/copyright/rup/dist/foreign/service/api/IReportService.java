package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
     * Writes FAS and FAS2 usages found by {@link AuditFilter} into CSV output stream.
     *
     * @param filter            {@link AuditFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAuditFasCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes NTS usages found by {@link AuditFilter} into CSV output stream.
     *
     * @param filter            {@link AuditFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAuditNtsCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes FAS and FAS2 usages found by filter into csv output stream.
     *
     * @param filter            instance of {@link UsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeFasUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes NTS usages found by filter into csv output stream.
     *
     * @param filter            instance of {@link UsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeNtsUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes AACL usages found by filter into CSV output stream.
     *
     * @param filter            instance of {@link UsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAaclUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes FAS scenario usages into csv output stream.
     *
     * @param scenario          {@link Scenario}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeFasScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream pipedOutputStream);

    /**
     * Writes NTS scenario usages into csv output stream.
     *
     * @param scenario          {@link Scenario}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeNtsScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream pipedOutputStream);

    /**
     * Writes scenario rightsholders into csv output stream.
     *
     * @param scenario          {@link Scenario}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeScenarioRightsholderTotalsCsvReport(Scenario scenario, PipedOutputStream pipedOutputStream);

    /**
     * Writes Undistributed Liabilities Reconciliation Report into the output stream in csv format.
     *
     * @param paymentDate     payment date
     * @param outputStream    instance of {@link OutputStream}
     * @param productFamilies product families
     */
    void writeUndistributedLiabilitiesCsvReport(LocalDate paymentDate, OutputStream outputStream,
                                                Set<String> productFamilies);

    /**
     * Writes FAS/FAS2 Batch Summary Report into the output stream in csv format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeFasBatchSummaryCsvReport(OutputStream outputStream);

    /**
     * Writes NTS Withdrawn Batch Summary Report into the output stream in csv format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeNtsWithdrawnBatchSummaryCsvReport(OutputStream outputStream);

    /**
     * Writes Research Status Report into the output stream in csv format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeResearchStatusCsvReport(OutputStream outputStream);

    /**
     * Writes Service Fee True-up Report into the output stream in csv format.
     *
     * @param fromDate      from date
     * @param toDate        to date
     * @param paymentDateTo payment date to
     * @param outputStream  instance of {@link OutputStream}
     */
    void writeServiceFeeTrueUpCsvReport(LocalDate fromDate, LocalDate toDate, LocalDate paymentDateTo,
                                        OutputStream outputStream);

    /**
     * Writes Summary of Market Report into the output stream in csv format based on batches.
     *
     * @param batches      list of batches
     * @param outputStream instance of {@link OutputStream}
     */
    void writeSummaryMarkerCsvReport(List<UsageBatch> batches, OutputStream outputStream);

    /**
     * Writes FAS filtered batches with NTS withdrawn usages into the output stream in CSV format.
     *
     * @param batches          list of batches
     * @param totalGrossAmount total gross amount
     * @param outputStream     instance of {@link OutputStream}
     */
    void writeNtsWithdrawnBatchesCsvReport(List<UsageBatch> batches, BigDecimal totalGrossAmount,
                                           OutputStream outputStream);

    /**
     * Writes Ownership Adjustment Report into a {@link OutputStream} in CSV format.
     *
     * @param scenarioId   the scenario identifier
     * @param statuses     set of {@link RightsholderDiscrepancyStatusEnum}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeOwnershipAdjustmentCsvReport(String scenarioId, Set<RightsholderDiscrepancyStatusEnum> statuses,
                                           OutputStream outputStream);

    /**
     * Writes {@link com.copyright.rup.dist.foreign.domain.WorkClassification}s by batches ids and search value.
     *
     * @param batchesIds        set of batches ids
     * @param searchValue       search value
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeWorkClassificationCsvReport(Set<String> batchesIds, String searchValue,
                                          PipedOutputStream pipedOutputStream);
}
