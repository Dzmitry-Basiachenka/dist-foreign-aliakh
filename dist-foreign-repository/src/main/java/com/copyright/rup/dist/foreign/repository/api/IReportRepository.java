package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Represents interface of repository for reports.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 06/25/2019
 *
 * @author Ihar Suvorau
 */
public interface IReportRepository {

    /**
     * Writes Undistributed Liabilities Reconciliation Report into the output stream in csv format.
     *
     * @param paymentDate                payment date
     * @param outputStream               instance of {@link OutputStream}
     * @param defaultEstimatedServiceFee default estimated service fee
     */
    void writeUndistributedLiabilitiesCsvReport(LocalDate paymentDate, OutputStream outputStream,
                                                BigDecimal defaultEstimatedServiceFee);

    /**
     * Writes Service Fee True-up Report into the output stream in csv format.
     *
     * @param fromDate                   from date
     * @param toDate                     to date
     * @param paymentDateTo              payment date to
     * @param outputStream               instance of {@link OutputStream}
     * @param claAccountNumber           CLA account number
     * @param defaultEstimatedServiceFee default estimated service fee
     */
    void writeServiceFeeTrueUpCsvReport(LocalDate fromDate, LocalDate toDate, LocalDate paymentDateTo,
                                        OutputStream outputStream, Long claAccountNumber,
                                        BigDecimal defaultEstimatedServiceFee);

    /**
     * Writes Summary of Market Report into the output stream in csv format based on batch ids.
     *
     * @param batchIds     list of batch ids
     * @param outputStream instance of {@link OutputStream}
     */
    void writeSummaryMarketCsvReport(List<String> batchIds, OutputStream outputStream);

    /**
     * Writes FAS/FAS2 Batch Summary Report into the output stream in csv format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeFasBatchSummaryCsvReport(OutputStream outputStream);

    /**
     * Writes Research Status Report into the output stream in csv format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeResearchStatusCsvReport(OutputStream outputStream);

    /**
     * Writes Ownership Adjustment Report into a {@link OutputStream} in CSV format.
     *
     * @param scenarioId   the scenario identifier
     * @param statuses     set of {@link RightsholderDiscrepancyStatusEnum}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeOwnershipAdjustmentCsvReport(String scenarioId, Set<RightsholderDiscrepancyStatusEnum> statuses,
                                           OutputStream outputStream);
}
