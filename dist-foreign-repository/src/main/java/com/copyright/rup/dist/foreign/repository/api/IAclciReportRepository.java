package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.io.PipedOutputStream;

/**
 * Interface for ACLCI report repository.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 04/13/2023
 *
 * @author Aliaksandr Liakh
 */
public interface IAclciReportRepository {

    /**
     * Finds ACLCI usages according to given {@link UsageFilter} and writes them to the output stream in CSV format.
     *
     * @param filter            filter
     * @param pipedOutputStream stream
     */
    void writeAclciUsagesCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);
}
