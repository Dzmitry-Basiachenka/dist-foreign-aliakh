package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;

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
}
