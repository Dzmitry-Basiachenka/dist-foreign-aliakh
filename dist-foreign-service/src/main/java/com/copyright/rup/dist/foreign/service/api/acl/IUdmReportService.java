package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;

import java.io.PipedOutputStream;

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
     * Writes UDM usages found by filter into csv output stream for Specialist and Manager roles.
     *
     * @param filter            instance of {@link UdmUsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUdmUsageCsvReportSpecialistManagerRoles(UdmUsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes UDM usages found by filter into csv output stream for Researcher role.
     *
     * @param filter            instance of {@link UdmUsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUdmUsageCsvReportResearcherRole(UdmUsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes UDM usages found by filter into csv output stream for View role.
     *
     * @param filter            instance of {@link UdmUsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUdmUsageCsvReportViewRole(UdmUsageFilter filter, PipedOutputStream pipedOutputStream);
}
