package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.Set;

/**
 * Represents interface of repository for AACL product family reports.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/23/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAaclReportRepository {

    /**
     * Finds AACL usages according to given {@link UsageFilter} and writes them to the output stream
     * in CSV format.
     *
     * @param filter            filter
     * @param pipedOutputStream stream
     */
    void writeAaclUsagesCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

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
     * Writes Work Shares by Aggregate Licensee Class Summary Report into the output stream in CSV format.
     *
     * @param scenarioId   scenario id
     * @param status       scenario status
     * @param outputStream instance of {@link OutputStream}
     */
    void writeWorkSharesByAggLcClassSummaryCsvReport(String scenarioId, ScenarioStatusEnum status,
                                                     OutputStream outputStream);

    /**
     * Writes Work Shares by Aggregate Licensee Class Report into the output stream in CSV format.
     *
     * @param scenarioId   scenario id
     * @param status       scenario status
     * @param outputStream instance of {@link OutputStream}
     */
    void writeWorkSharesByAggLcClassCsvReport(String scenarioId, ScenarioStatusEnum status, OutputStream outputStream);

    /**
     * Finds AACL usages by given {@link AuditFilter} and writes them to the given {@link PipedOutputStream}.
     *
     * @param filter            filter
     * @param pipedOutputStream stream
     * @throws com.copyright.rup.common.exception.RupRuntimeException if IOException appears during writing report
     */
    void writeAuditAaclCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream);

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
}
