package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
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
     * @param productFamilies            product families
     */
    void writeUndistributedLiabilitiesCsvReport(LocalDate paymentDate, OutputStream outputStream,
                                                BigDecimal defaultEstimatedServiceFee, Set<String> productFamilies);

    /**
     * Writes Service Fee True-up Report for FAS/FAS2 into the output stream in csv format.
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
     * Writes Service Fee True-up Report for NTS into the output stream in csv format.
     *
     * @param scenario                   a {@link Scenario}
     * @param outputStream               instance of {@link OutputStream}
     * @param defaultEstimatedServiceFee default estimated service fee
     */
    void writeNtsServiceFeeTrueUpCsvReport(Scenario scenario, OutputStream outputStream,
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
     * Finds FAS or FAS2 usages according to given {@link UsageFilter} and writes them to the output stream
     * in CSV format.
     *
     * @param filter            instance of {@link UsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeFasUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Finds NTS usages according to given {@link UsageFilter} and writes them to the output stream in CSV format.
     *
     * @param filter            instance of {@link UsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeNtsUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Finds AACL usages according to given {@link UsageFilter} and writes them to the output stream
     * in CSV format.
     *
     * @param filter            filter
     * @param pipedOutputStream stream
     */
    void writeAaclUsagesCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Finds SAL usages according to given {@link UsageFilter} and writes them to the output stream
     * in CSV format.
     *
     * @param filter            filter
     * @param pipedOutputStream stream
     */
    void writeSalUsagesCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

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
     * Writes AACL usages for classification found by filter into the output stream in CSV format
     * and returns identifiers of those usages.
     *
     * @param filter       instance of {@link UsageFilter}
     * @param outputStream instance of {@link OutputStream}
     * @return identifiers of usages written into CSV report
     */
    Set<String> writeUsagesForClassificationAndFindIds(UsageFilter filter, OutputStream outputStream);

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
     * Finds usages by NTS scenario id and writes them into the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeNtsScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Finds archived usages by NTS scenario id and writes them into the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeArchivedNtsScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

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
     * Writes Work Shares by Aggregate Licensee Class Summary Report into the output stream in csv format.
     *
     * @param scenarioId   scenario id
     * @param status       scenario status
     * @param outputStream instance of {@link OutputStream}
     */
    void writeWorkSharesByAggLcClassSummaryCsvReport(String scenarioId, ScenarioStatusEnum status,
                                                     OutputStream outputStream);

    /**
     * Writes Work Shares by Aggregate Licensee Class Report into the output stream in csv format.
     *
     * @param scenarioId   scenario id
     * @param status       scenario status
     * @param outputStream instance of {@link OutputStream}
     */
    void writeWorkSharesByAggLcClassCsvReport(String scenarioId, ScenarioStatusEnum status, OutputStream outputStream);

    /**
     * Finds FAS and FAS2 usages by given {@link AuditFilter} and writes them to the given {@link PipedOutputStream}.
     *
     * @param filter            filter
     * @param pipedOutputStream stream
     * @throws RupRuntimeException in case when IOException appears during writing report
     */
    void writeAuditFasCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) throws RupRuntimeException;

    /**
     * Finds NTS usages by given {@link AuditFilter} and writes them to the given {@link PipedOutputStream}.
     *
     * @param filter            filter
     * @param pipedOutputStream stream
     * @throws RupRuntimeException in case when IOException appears during writing report
     */
    void writeAuditNtsCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) throws RupRuntimeException;

    /**
     * Finds AACL usages by given {@link AuditFilter} and writes them to the given {@link PipedOutputStream}.
     *
     * @param filter            filter
     * @param pipedOutputStream stream
     * @throws RupRuntimeException in case when IOException appears during writing report
     */
    void writeAuditAaclCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) throws RupRuntimeException;

    /**
     * Writes {@link com.copyright.rup.dist.foreign.domain.WorkClassification}s by batches ids and search value.
     *
     * @param batchesIds        set of batches ids
     * @param searchValue       search value
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeWorkClassificationCsvReport(Set<String> batchesIds, String searchValue,
                                          PipedOutputStream pipedOutputStream);

    /**
     * Writes {@link com.copyright.rup.dist.foreign.domain.WorkClassification}s by search value.
     *
     * @param searchValue       search value
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeWorkClassificationCsvReport(String searchValue, PipedOutputStream pipedOutputStream);

    /**
     * Writes NTS Withdrawn Batch Summary Report into the output stream in csv format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeNtsWithdrawnBatchSummaryCsvReport(OutputStream outputStream);

    /**
     * Finds archived usages by AACL scenario id and writes them into the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeArchivedAaclScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Finds usages by AACL scenario id and writes them into the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAaclScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Finds AACL baseline usages and writes them into the output stream in CSV format.
     *
     * @param numberOfYears number of baseline years
     * @param outputStream  instance of {@link OutputStream}
     */
    void writeAaclBaselineUsagesCsvReport(int numberOfYears, OutputStream outputStream);

    /**
     * Finds details by filter and writes them with payee exclude status into the output stream in CSV format.
     *
     * @param filter                 instance of {@link ExcludePayeeFilter}
     * @param selectedAccountNumbers set of account numbers of selected payees
     * @param pipedOutputStream      instance of {@link PipedOutputStream}
     */
    void writeExcludeDetailsByPayeeCsvReport(ExcludePayeeFilter filter, Set<Long> selectedAccountNumbers,
                                             PipedOutputStream pipedOutputStream);

    /**
     * Writes AACL Undistributed Liabilities Report into the output stream in csv format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeAaclUndistributedLiabilitiesCsvReport(OutputStream outputStream);

    /**
     * Writes NTS Undistributed Liabilities Report into the output stream in csv format.
     *
     * @param estimatedServiceFee estimated service fee
     * @param outputStream        instance of {@link OutputStream}
     */
    void writeNtsUndistributedLiabilitiesCsvReport(BigDecimal estimatedServiceFee, OutputStream outputStream);

    /**
     * Writes SAL Liabilities by Rightsholer report into the output stream in csv format.
     *
     * @param scenarios    list of scenarios
     * @param outputStream instance of {@link OutputStream}
     */
    void writeLiabilitiesByRhCsvReport(List<Scenario> scenarios, OutputStream outputStream);

    /**
     * Writes SAL Liabilities Summary by Rightsholder and Work Report into the output stream in CSV format.
     *
     * @param scenarios    list of scenarios
     * @param outputStream instance of {@link OutputStream}
     */
    void writeLiabilitiesSummaryByRhAndWorkReportCsvReport(List<Scenario> scenarios, OutputStream outputStream);

    /**
     * Finds archived usages by SAL scenario id and writes them into the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeArchivedSalScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Finds usages by SAL scenario id and writes them into the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeSalScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);
}
