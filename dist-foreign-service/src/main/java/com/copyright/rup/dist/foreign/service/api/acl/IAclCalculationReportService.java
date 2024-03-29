package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.Set;

/**
 * Interface that provides ability to generate multiple reports for ACL calculations.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/15/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclCalculationReportService {

    /**
     * Writes ACL grant details found by filter into csv output stream.
     *
     * @param filter            instance of {@link AclGrantDetailFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAclGrantDetailCsvReport(AclGrantDetailFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes ACL usages found by filter into csv output stream.
     *
     * @param filter            instance of {@link AclUsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAclUsageCsvReport(AclUsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes ACL fund pool details found by filter into csv output stream.
     *
     * @param filter            instance of {@link AclFundPoolDetailFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAclFundPoolDetailsCsvReport(AclFundPoolDetailFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes ACL scenario details found by scenario id into csv output stream.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAclScenarioDetailsCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Writes ACL scenario rightsholder totals found by scenario id into csv output stream.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeAclScenarioRightsholderTotalsCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Writes Summary of Work Shares by Aggregate Licensee Class report into csv output stream.
     *
     * @param reportInfo   instance of {@link AclCalculationReportsInfoDto}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeSummaryOfWorkSharesByAggLcCsvReport(AclCalculationReportsInfoDto reportInfo, OutputStream outputStream);

    /**
     * Writes Work Shares by Aggregate Licensee Class report into csv output stream.
     *
     * @param reportInfo   instance of {@link AclCalculationReportsInfoDto}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeWorkSharesByAggLcCsvReport(AclCalculationReportsInfoDto reportInfo, OutputStream outputStream);

    /**
     * Writes ACL Liability Details Report into csv output stream.
     *
     * @param reportInfo   meta information regarding report
     * @param outputStream instance of {@link OutputStream}
     */
    void writeAclLiabilityDetailsReport(AclCalculationReportsInfoDto reportInfo, OutputStream outputStream);

    /**
     * Writes ACL Liabilities by Aggregate Licensee Class report.
     *
     * @param reportInfo   instance of {@link AclCalculationReportsInfoDto}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeAclLiabilitiesByAggLicClassReport(AclCalculationReportsInfoDto reportInfo, OutputStream outputStream);

    /**
     * Writes ACL Liabilities by Rightsholder report.
     *
     * @param reportInfo   instance of {@link AclCalculationReportsInfoDto}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeAclLiabilitiesByRhReport(AclCalculationReportsInfoDto reportInfo, OutputStream outputStream);

    /**
     * Writes ACL Comparison by Aggregate Licensee Class and Title report.
     *
     * @param reportInfo   instance of {@link AclCalculationReportsInfoDto}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeAclComparisonByAggLcClassAndTitleReport(AclCalculationReportsInfoDto reportInfo,
                                                      OutputStream outputStream);

    /**
     * Writes ACL Fund Pool by Aggregate Licensee Class report.
     *
     * @param fundPoolIds  set of fund pool ids
     * @param outputStream instance of {@link OutputStream}
     */
    void writeAclFundPoolByAggLcReport(Set<String> fundPoolIds, OutputStream outputStream);
}
