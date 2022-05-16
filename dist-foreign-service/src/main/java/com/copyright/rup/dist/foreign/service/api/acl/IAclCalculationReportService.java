package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;

import java.io.PipedOutputStream;

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
}
