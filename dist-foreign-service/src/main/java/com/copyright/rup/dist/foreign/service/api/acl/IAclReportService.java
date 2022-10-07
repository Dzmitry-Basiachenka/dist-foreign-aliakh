package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;

import java.io.OutputStream;

/**
 * Interface that provides ability to generate calculation ACL reports.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/07/2022
 *
 * @author Ihar Suvorau
 */
public interface IAclReportService {

    /**
     * Writes ACL Liabilities by Aggregate Licensee Class report.
     *
     * @param reportInfo   instance of {@link AclCalculationReportsInfoDto}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeAclLiabilitiesByAggLicClassReport(AclCalculationReportsInfoDto reportInfo, OutputStream outputStream);
}
