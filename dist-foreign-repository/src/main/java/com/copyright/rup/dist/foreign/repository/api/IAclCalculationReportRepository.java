package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;

import java.io.OutputStream;
import java.io.PipedOutputStream;

/**
 * Represents interface of repository for ACL calculation reports.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/15/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclCalculationReportRepository {

    /**
     * Finds ACL grant details according to given {@link AclGrantDetailFilter} and writes them to the output stream in
     * CSV format.
     *
     * @param filter            instance of {@link AclGrantDetailFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAclGrantDetailCsvReport(AclGrantDetailFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Finds ACL usages according to given {@link AclUsageFilter} and writes them to the output stream in
     * CSV format.
     *
     * @param filter            instance of {@link AclUsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAclUsageCsvReport(AclUsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Finds ACL fund pool details according to given {@link AclFundPoolDetailFilter} and writes them to the output
     * stream in CSV format.
     *
     * @param filter            instance of {@link AclFundPoolDetailFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAclFundPoolDetailsCsvReport(AclFundPoolDetailFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Finds ACL scenario details by scenario id and writes them to the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAclScenarioDetailsCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Finds ACL scenario rightsholder totals by scenario id and writes them to the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAclScenarioRightsholderTotalsCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Finds AclSummaryOfWorkSharesByAggLcReportDto and writes Summary of Work Shares by Agg LC Report
     * into csv output stream.
     *
     * @param reportInfo   report info
     * @param outputStream instance of {@link OutputStream}
     */
    void writeSummaryOfWorkSharesByAggLcCsvReport(AclCalculationReportsInfoDto reportInfo, OutputStream outputStream);

    /**
     * Finds ACL Liability details by scenario ids and writes them to the output stream in CSV format.
     *
     * @param reportInfo   meta information regarding report
     * @param outputStream instance of {@link OutputStream}
     */
    void writeAclLiabilityDetailsReport(AclCalculationReportsInfoDto reportInfo, OutputStream outputStream);
}
