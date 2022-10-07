package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.service.api.acl.IAclReportService;

import org.springframework.stereotype.Service;

import java.io.OutputStream;

/**
 * Implementation of {@link IAclReportService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/07/2022
 *
 * @author Ihar Suvorau
 */
@Service
public class AclReportService implements IAclReportService {

    @Override
    public void writeAclLiabilitiesByAggLicClassReport(AclCalculationReportsInfoDto reportInfo,
                                                       OutputStream outputStream) {
        //TODO implement backend logic
    }
}
