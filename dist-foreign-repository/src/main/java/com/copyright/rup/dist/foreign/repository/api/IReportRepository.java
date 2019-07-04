package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.io.OutputStream;
import java.io.PipedOutputStream;
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

    /**
     * Finds usages according to given {@link UsageFilter} and writes them to the output stream in CSV format.
     *
     * @param filter            instance of {@link UsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUsagesCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes usages found by filter into the output stream in CSV format
     * and returns identifiers of those usages.
     *
     * @param filter       instance of {@link UsageFilter}
     * @param outputStream instance of {@link OutputStream}
     * @return identifiers of usages written into CSV report
     */
    Set<String> writeUsagesForResearchAndFindIds(UsageFilter filter, OutputStream outputStream);

    /**
     * Finds usages by scenario id and writes them into the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Finds {@link com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder}s based on scenario id
     * and writes them to the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeScenarioRightsholderTotalsCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Finds usages by given {@link AuditFilter} and writes them to the given {@link PipedOutputStream}.
     *
     * @param filter            filter
     * @param pipedOutputStream stream
     * @throws RupRuntimeException in case when IOException appears during writing report
     */
    void writeAuditCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) throws RupRuntimeException;

    /**
     * Writes {@link com.copyright.rup.dist.foreign.domain.WorkClassification}s by batches ids and search value.
     *
     * @param batchesIds        set of batches ids
     * @param searchValue       search value
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeWorkClassificationCsvReportByBatchIds(Set<String> batchesIds, String searchValue,
                                                    PipedOutputStream pipedOutputStream);

    /**
     * Writes {@link com.copyright.rup.dist.foreign.domain.WorkClassification}s by search value.
     *
     * @param searchValue       search value
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeWorkClassificationCsvReportBySearch(String searchValue, PipedOutputStream pipedOutputStream);
}
