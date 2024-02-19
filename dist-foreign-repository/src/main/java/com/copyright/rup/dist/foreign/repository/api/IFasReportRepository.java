package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Represents interface of repository for FAS product family reports.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/23/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IFasReportRepository {

    /**
     * Writes Undistributed Liabilities Reconciliation Report into the output stream in CSV format.
     *
     * @param paymentDate                payment date
     * @param outputStream               instance of {@link OutputStream}
     * @param defaultEstimatedServiceFee default estimated service fee
     * @param productFamilies            product families
     */
    void writeUndistributedLiabilitiesCsvReport(LocalDate paymentDate, OutputStream outputStream,
                                                BigDecimal defaultEstimatedServiceFee, Set<String> productFamilies);

    /**
     * Writes Service Fee True-up Report for FAS/FAS2 into the output stream in CSV format.
     *
     * @param fromDate                   from date
     * @param toDate                     to date
     * @param paymentDateTo              payment date to
     * @param outputStream               instance of {@link OutputStream}
     * @param claAccountNumber           CLA account number
     * @param defaultEstimatedServiceFee default estimated service fee
     */
    void writeFasServiceFeeTrueUpCsvReport(LocalDate fromDate, LocalDate toDate, LocalDate paymentDateTo,
                                           OutputStream outputStream, Long claAccountNumber,
                                           BigDecimal defaultEstimatedServiceFee);

    /**
     * Writes Summary of Market Report into the output stream in CSV format based on batch ids.
     *
     * @param batchIds     list of batch ids
     * @param outputStream instance of {@link OutputStream}
     */
    void writeSummaryMarketCsvReport(List<String> batchIds, OutputStream outputStream);

    /**
     * Writes FAS/FAS2 Batch Summary Report into the output stream in CSV format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeFasBatchSummaryCsvReport(OutputStream outputStream);

    /**
     * Writes Research Status Report into the output stream in CSV format.
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
     * Finds FAS or FAS2 usages according to given {@link UsageFilter} and writes them to the output stream
     * in CSV format.
     *
     * @param filter            instance of {@link UsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeFasUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes FAS usages for work research found by filter into the output stream in CSV format
     * and returns identifiers of those usages.
     *
     * @param filter       instance of {@link UsageFilter}
     * @param outputStream instance of {@link OutputStream}
     * @return identifiers of usages written into CSV report
     */
    Set<String> writeUsagesForResearchAndFindIds(UsageFilter filter, OutputStream outputStream);

    /**
     * Finds usages by FAS/FAS2 scenario id and writes them into the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeFasScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Finds archived usages by FAS/FAS2 scenario id and writes them into the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeArchivedFasScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Finds {@link com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder}s based on scenario id
     * and writes them to the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeScenarioRightsholderTotalsCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Finds {@link com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder}s based on archived scenario id and
     * writes them to the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeArchivedScenarioRightsholderTotalsCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Finds FAS and FAS2 usages by given {@link AuditFilter} and writes them to the given {@link PipedOutputStream}.
     *
     * @param filter            filter
     * @param pipedOutputStream stream
     * @throws com.copyright.rup.common.exception.RupRuntimeException if IOException appears during writing report
     */
    void writeAuditFasCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Finds FAS details by filter and writes them with payee exclude status into the output stream in CSV format.
     *
     * @param filter                 instance of {@link ExcludePayeeFilter}
     * @param selectedAccountNumbers set of account numbers of selected payees
     * @param pipedOutputStream      instance of {@link PipedOutputStream}
     */
    void writeFasExcludeDetailsByPayeeCsvReport(ExcludePayeeFilter filter, Set<Long> selectedAccountNumbers,
                                                PipedOutputStream pipedOutputStream);
}
