package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmReportFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.Set;

/**
 * Interface that provides ability to generate multiple UDM reports.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 07/05/2021
 *
 * @author Dzmitry Basiachenka
 */
public interface IUdmReportService {

    /**
     * Writes UDM proxy values found by filter into csv output stream.
     *
     * @param filter            instance of {@link UdmProxyValueFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUdmProxyValueCsvReport(UdmProxyValueFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes UDM usages found by filter into csv output stream for Specialist and Manager roles.
     *
     * @param filter            instance of {@link UdmUsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUdmUsageCsvReportSpecialistManager(UdmUsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes UDM usages found by filter into csv output stream for Researcher role.
     *
     * @param filter            instance of {@link UdmUsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUdmUsageCsvReportResearcher(UdmUsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes UDM usages found by filter into csv output stream for View role.
     *
     * @param filter            instance of {@link UdmUsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUdmUsageCsvReportView(UdmUsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes UDM baseline usages found by filter into csv output stream.
     *
     * @param filter            instance of {@link UdmBaselineFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUdmBaselineUsageCsvReport(UdmBaselineFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes UDM baseline values found by filter into csv output stream.
     *
     * @param filter            instance of {@link UdmBaselineFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUdmBaselineValuesCsvReport(UdmBaselineValueFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes UDM Weekly Survey Report into csv output stream.
     *
     * @param reportFilter instance of {@link UdmReportFilter}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeUdmWeeklySurveyCsvReport(UdmReportFilter reportFilter, OutputStream outputStream);

    /**
     * Writes UDM Survey Licensee Report into csv output stream.
     *
     * @param reportFilter instance of {@link UdmReportFilter}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeUdmSurveyLicenseeCsvReport(UdmReportFilter reportFilter, OutputStream outputStream);

    /**
     * Writes UDM Verified Details By Source Report into csv output stream.
     *
     * @param reportFilter instance of {@link UdmReportFilter}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeUdmVerifiedDetailsBySourceReport(UdmReportFilter reportFilter, OutputStream outputStream);

    /**
     * Writes UDM Completed Assignments by Employee Report into csv output stream.
     *
     * @param reportFilter instance of {@link UdmReportFilter}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeUdmCompletedAssignmentsCsvReport(UdmReportFilter reportFilter, OutputStream outputStream);

    /**
     * Writes UDM Usage Edits in Baseline Report into csv output stream.
     *
     * @param reportFilter instance of {@link UdmReportFilter}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeUdmUsageEditsInBaselineCsvReport(UdmReportFilter reportFilter, OutputStream outputStream);

    /**
     * Writes UDM Usable Details by Country Report into csv output stream.
     *
     * @param reportFilter instance of {@link UdmReportFilter}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeUdmUsableDetailsByCountryCsvReport(UdmReportFilter reportFilter, OutputStream outputStream);

    /**
     * Writes UDM Values by Status Report into csv output stream.
     *
     * @param period       instance of {@link Integer}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeUdmValuesByStatusCsvReport(Integer period, OutputStream outputStream);

    /**
     * Writes UDM Values Report into csv output stream.
     *
     * @param udmValueFilter    instance of {@link UdmValueFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUdmValuesCsvReport(UdmValueFilter udmValueFilter, PipedOutputStream pipedOutputStream);

    /**
     * Writes UDM Usages by Status Report into csv output stream.
     *
     * @param period       instance of {@link Integer}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeUdmUsagesByStatusCsvReport(Integer period, OutputStream outputStream);

    /**
     * Writes UDM Baseline Value Updates Report into csv output stream.
     *
     * @param reportFilter instance of {@link UdmReportFilter}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeUdmBaselineValueUpdatesCsvReport(UdmReportFilter reportFilter, OutputStream outputStream);

    /**
     * Writes UDM Survey Dashboard Report into csv output stream.
     *
     * @param periods      list of periods
     * @param outputStream instance of {@link OutputStream}
     */
    void writeUdmSurveyDashboardCsvReport(Set<Integer> periods, OutputStream outputStream);
}
