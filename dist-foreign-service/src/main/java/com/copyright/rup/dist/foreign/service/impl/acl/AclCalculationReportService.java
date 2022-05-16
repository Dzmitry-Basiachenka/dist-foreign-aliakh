package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclCalculationReportRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PipedOutputStream;

/**
 * Implements {@link IAclCalculationReportService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/15/2022
 *
 * @author Dzmitry Basiachenka
 */
@Service
public class AclCalculationReportService implements IAclCalculationReportService {

    @Autowired
    private IAclCalculationReportRepository aclCalculationReportRepository;

    @Override
    public void writeAclGrantDetailCsvReport(AclGrantDetailFilter filter, PipedOutputStream pipedOutputStream) {
        aclCalculationReportRepository.writeAclGrantDetailCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeAclUsageCsvReport(AclUsageFilter filter, PipedOutputStream pipedOutputStream) {
        aclCalculationReportRepository.writeAclUsageCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeAclFundPoolDetailsCsvReport(AclFundPoolDetailFilter filter, PipedOutputStream pipedOutputStream) {
        aclCalculationReportRepository.writeAclFundPoolDetailsCsvReport(filter, pipedOutputStream);
    }
}
