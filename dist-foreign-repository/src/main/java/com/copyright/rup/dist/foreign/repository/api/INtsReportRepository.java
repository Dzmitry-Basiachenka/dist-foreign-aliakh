package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Represents interface of repository for NTS product family reports.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/23/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface INtsReportRepository extends Serializable {

    /**
     * Writes Service Fee True-up Report for NTS into the output stream in CSV format.
     *
     * @param scenario                   a {@link Scenario}
     * @param outputStream               instance of {@link OutputStream}
     * @param defaultEstimatedServiceFee default estimated service fee
     */
    void writeNtsServiceFeeTrueUpCsvReport(Scenario scenario, OutputStream outputStream,
                                           BigDecimal defaultEstimatedServiceFee);

    /**
     * Finds NTS usages according to given {@link UsageFilter} and writes them to the output stream in CSV format.
     *
     * @param filter            instance of {@link UsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeNtsUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

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
     * Finds NTS usages by given {@link AuditFilter} and writes them to the given {@link PipedOutputStream}.
     *
     * @param filter            filter
     * @param pipedOutputStream stream
     * @throws com.copyright.rup.common.exception.RupRuntimeException if IOException appears during writing report
     */
    void writeAuditNtsCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream);

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
     * Writes NTS Withdrawn Batch Summary Report into the output stream in CSV format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeNtsWithdrawnBatchSummaryCsvReport(OutputStream outputStream);

    /**
     * Writes NTS Undistributed Liabilities Report into the output stream in CSV format.
     *
     * @param estimatedServiceFee estimated service fee
     * @param outputStream        instance of {@link OutputStream}
     */
    void writeNtsUndistributedLiabilitiesCsvReport(BigDecimal estimatedServiceFee, OutputStream outputStream);

    /**
     * Writes NTS Fund Pools Report into the output stream in CSV format.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    void writeNtsFundPoolsCsvReport(OutputStream outputStream);

    /**
     * Writes NTS Pre-Service Fee Fund Report into the output strean in CSV format.
     *
     * @param fundPoolId   fund pool id
     * @param outputStream instance of {@link OutputStream}
     */
    void writeNtsPreServiceFeeFundCsvReport(String fundPoolId, OutputStream outputStream);
}
