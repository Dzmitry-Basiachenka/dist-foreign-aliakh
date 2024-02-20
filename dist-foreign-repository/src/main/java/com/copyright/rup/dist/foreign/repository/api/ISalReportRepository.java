package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Represents interface of repository for SAL product family reports.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/23/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface ISalReportRepository extends Serializable {

    /**
     * Finds SAL usages according to given {@link UsageFilter} and writes them to the output stream
     * in CSV format.
     *
     * @param filter            filter
     * @param pipedOutputStream stream
     */
    void writeSalUsagesCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Finds SAL usages by given {@link AuditFilter} and writes them to the given {@link PipedOutputStream}.
     *
     * @param filter            filter
     * @param pipedOutputStream stream
     * @throws com.copyright.rup.common.exception.RupRuntimeException if IOException appears during writing report
     */
    void writeAuditSalCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes SAL Liabilities by Rightsholer report into the output stream in CSV format.
     *
     * @param scenarios    list of scenarios
     * @param outputStream instance of {@link OutputStream}
     */
    void writeSalLiabilitiesByRhCsvReport(List<Scenario> scenarios, OutputStream outputStream);

    /**
     * Writes SAL Liabilities Summary by Rightsholder and Work Report into the output stream in CSV format.
     *
     * @param scenarios    list of scenarios
     * @param outputStream instance of {@link OutputStream}
     */
    void writeSalLiabilitiesSummaryByRhAndWorkCsvReport(List<Scenario> scenarios, OutputStream outputStream);

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

    /**
     * Writes SAL Undistributed Liabilities Reconciliation Report into the output stream in CSV format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeSalUndistributedLiabilitiesCsvReport(OutputStream outputStream);

    /**
     * Writes list of SAL Fund Pools into the output stream in CSV format.
     *
     * @param outputStream     instance of {@link OutputStream}
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
}
