package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.time.LocalDate;
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
     * Writes UDM Weekly Survey Report into csv output stream.
     *
     * @param channels         set of channels
     * @param usageOrigins     set of usage origins
     * @param periods          set of periods
     * @param dateReceivedFrom date received from
     * @param dateReceivedTo   date received to
     * @param outputStream     instance of {@link OutputStream}
     */
    void writeUdmWeeklySurveyCsvReport(Set<String> channels, Set<String> usageOrigins, Set<Integer> periods,
                                       LocalDate dateReceivedFrom, LocalDate dateReceivedTo, OutputStream outputStream);
}
