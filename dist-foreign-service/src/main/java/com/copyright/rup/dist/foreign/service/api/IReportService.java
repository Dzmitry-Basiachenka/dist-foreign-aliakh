package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.io.Serializable;
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
public interface IReportService extends Serializable {

    /**
     * Writes FAS and FAS2 usages found by {@link AuditFilter} into CSV output stream.
     *
     * @param filter            {@link AuditFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAuditFasCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes AACL usages found by {@link AuditFilter} into CSV output stream.
     *
     * @param filter            {@link AuditFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAuditAaclCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes NTS usages found by {@link AuditFilter} into CSV output stream.
     *
     * @param filter            {@link AuditFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAuditNtsCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes SAL usages found by {@link AuditFilter} into CSV output stream.
     *
     * @param filter            {@link AuditFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAuditSalCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream);

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
     * Writes ACLCI usages found by filter into CSV output stream.
     *
     * @param filter            instance of {@link UsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAclciUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes SAL usages found by filter into CSV output stream.
     *
     * @param filter            instance of {@link UsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeSalUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

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
     * Writes AACL scenario usages into csv output stream.
     *
     * @param scenario          {@link Scenario}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAaclScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream pipedOutputStream);

    /**
     * Writes SAL scenario usages into csv output stream.
     *
     * @param scenario          {@link Scenario}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeSalScenarioUsagesCsvReport(Scenario scenario, PipedOutputStream pipedOutputStream);

    /**
     * Writes scenario rightsholders into csv output stream.
     *
     * @param scenario          {@link Scenario}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeScenarioRightsholderTotalsCsvReport(Scenario scenario, PipedOutputStream pipedOutputStream);

    /**
     * Writes Work Shares by Aggregate Licensee Class Summary Report into csv output stream.
     *
     * @param scenario     {@link Scenario}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeWorkSharesByAggLcClassSummaryCsvReport(Scenario scenario, OutputStream outputStream);

    /**
     * Writes Work Shares by Aggregate Licensee Class Report into csv output stream.
     *
     * @param scenario     {@link Scenario}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeWorkSharesByAggLcClassCsvReport(Scenario scenario, OutputStream outputStream);

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
     * Writes AACL Baseline usages Report into the output stream in csv format.
     *
     * @param numberOfYears number of years to export
     * @param outputStream  instance of {@link OutputStream}
     */
    void writeAaclBaselineUsagesCsvReport(int numberOfYears, OutputStream outputStream);

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
     * Writes NTS Undistributed Liabilities Report into the output stream in CSV format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeNtsUndistributedLiabilitiesReport(OutputStream outputStream);

    /**
     * Writes SAL Liabilities by Rightsholder Report into the output stream in CSV format.
     *
     * @param scenarios    list of {@link Scenario}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeSalLiabilitiesByRhReport(List<Scenario> scenarios, OutputStream outputStream);

    /**
     * Writes Research Status Report into the output stream in csv format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeResearchStatusCsvReport(OutputStream outputStream);

    /**
     * Writes Service Fee True-up Report for FAS/FAS2 into the output stream in csv format.
     *
     * @param fromDate      from date
     * @param toDate        to date
     * @param paymentDateTo payment date to
     * @param outputStream  instance of {@link OutputStream}
     */
    void writeFasServiceFeeTrueUpCsvReport(LocalDate fromDate, LocalDate toDate, LocalDate paymentDateTo,
                                           OutputStream outputStream);

    /**
     * Writes Service Fee True-up Report for NTS into the output stream in csv format.
     *
     * @param scenario     a {@link Scenario}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeNtsServiceFeeTrueUpCsvReport(Scenario scenario, OutputStream outputStream);

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

    /**
     * Finds FAS details by filter and writes them with payee exclude status into the output stream in CSV format.
     *
     * @param filter                 instance of {@link ExcludePayeeFilter}
     * @param selectedAccountNumbers set of account numbers of selected payees
     * @param pipedOutputStream      instance of {@link PipedOutputStream}
     */
    void writeFasExcludeDetailsByPayeeCsvReport(ExcludePayeeFilter filter, Set<Long> selectedAccountNumbers,
                                                PipedOutputStream pipedOutputStream);

    /**
     * Finds AACL details by filter and writes them with payee exclude status into the output stream in CSV format.
     *
     * @param filter                 instance of {@link ExcludePayeeFilter}
     * @param selectedAccountNumbers set of account numbers of selected payees
     * @param pipedOutputStream      instance of {@link PipedOutputStream}
     */
    void writeAaclExcludeDetailsByPayeeCsvReport(ExcludePayeeFilter filter, Set<Long> selectedAccountNumbers,
                                                 PipedOutputStream pipedOutputStream);

    /**
     * Writes AACL Undistributed Liabilities Report into the output stream in CSV format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeAaclUndistributedLiabilitiesCsvReport(OutputStream outputStream);

    /**
     * Writes Tax Notification Report into the output stream in CSV format.
     *
     * @param productFamily product family
     * @param scenarioIds   set of scenario ids
     * @param numberOfDays  TBOs who received notification within last {@code numberOfDays} won't be included in the
     *                      result
     * @param outputStream  instance of {@link OutputStream}
     */
    void writeTaxNotificationCsvReport(String productFamily, Set<String> scenarioIds, int numberOfDays,
                                       OutputStream outputStream);

    /**
     * Writes SAL Liabilities Summary by Rightsholder and Work Report into the output stream in CSV format.
     *
     * @param scenarioIds  list of scenario ids
     * @param outputStream instance of {@link OutputStream}
     */
    void writeSalLiabilitiesSummaryByRhAndWorkCsvReport(List<Scenario> scenarioIds, OutputStream outputStream);

    /**
     * Writes SAL Undistributed Liabilities Report into the output stream in CSV format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeSalUndistributedLiabilitiesCsvReport(OutputStream outputStream);

    /**
     * Writes list of SAL Fund Pools into the output stream in CSV format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeSalFundPoolsCsvReport(OutputStream outputStream);

    /**
     * Writes SAL Fund Pools Report into the output stream in CSV format.
     *
     * @param distributionYear distribution year to export
     * @param outputStream     instance of {@link OutputStream}
     */
    void writeSalFundPoolsCsvReport(int distributionYear, OutputStream outputStream);

    /**
     * Writes SAL Historical Item Bank Details Report into the output stream in CSV format.
     *
     * @param licenseeAccountNumber licensee account number
     * @param periodEndYearFrom     period end year from
     * @param periodEndYearTo       period end year to
     * @param outputStream          instance of {@link OutputStream}
     */
    void writeSalHistoricalItemBankDetailsReport(Long licenseeAccountNumber, Integer periodEndYearFrom,
                                                 Integer periodEndYearTo, OutputStream outputStream);

    /**
     * Writes NTS Fund Pools Report into the output stream in csv format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeNtsFundPoolsCsvReport(OutputStream outputStream);

    /**
     * Writes NTS Pre-Service Fee Fund Report into csv output stream.
     *
     * @param fundPool     {@link FundPool}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeNtsPreServiceFeeFundCsvReport(FundPool fundPool, OutputStream outputStream);
}
