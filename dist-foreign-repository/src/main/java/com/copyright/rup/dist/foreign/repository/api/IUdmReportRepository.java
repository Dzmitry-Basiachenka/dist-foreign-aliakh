package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;

import java.io.PipedOutputStream;

/**
 * Represents interface of repository for UDM reports.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 07/01/2021
 *
 * @author Dzmitry Basiachenka
 */
public interface IUdmReportRepository {

    /**
     * Finds UDM usages according to given {@link UdmUsageFilter} and writes them to the output stream in CSV format
     * for Specialist and Manager roles.
     *
     * @param filter            instance of {@link UdmUsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUdmUsageCsvReportSpecialistManagerRoles(UdmUsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Finds UDM usages according to given {@link UdmUsageFilter} and writes them to the output stream in CSV format
     * for Researcher role.
     *
     * @param filter            instance of {@link UdmUsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUdmUsageCsvReportResearcherRole(UdmUsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Finds UDM usages according to given {@link UdmUsageFilter} and writes them to the output stream in CSV format
     * for View role.
     *
     * @param filter            instance of {@link UdmUsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUdmUsageCsvReportViewRole(UdmUsageFilter filter, PipedOutputStream pipedOutputStream);
}
