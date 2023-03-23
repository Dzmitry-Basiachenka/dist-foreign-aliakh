package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.repository.api.IAclCalculationReportRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.Set;

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

    @Override
    public void writeAclScenarioDetailsCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        aclCalculationReportRepository.writeAclScenarioDetailsCsvReport(scenarioId, pipedOutputStream);
    }

    @Override
    public void writeAclScenarioRightsholderTotalsCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        aclCalculationReportRepository.writeAclScenarioRightsholderTotalsCsvReport(scenarioId, pipedOutputStream);
    }

    @Override
    public void writeSummaryOfWorkSharesByAggLcCsvReport(AclCalculationReportsInfoDto reportInfo,
                                                         OutputStream outputStream) {
        aclCalculationReportRepository.writeSummaryOfWorkSharesByAggLcCsvReport(reportInfo, outputStream);
    }

    @Override
    public void writeWorkSharesByAggLcCsvReport(AclCalculationReportsInfoDto reportInfo, OutputStream outputStream) {
        aclCalculationReportRepository.writeWorkSharesByAggLcCsvReport(reportInfo, outputStream);
    }

    @Override
    public void writeAclLiabilityDetailsReport(AclCalculationReportsInfoDto reportInfo, OutputStream outputStream) {
        aclCalculationReportRepository.writeAclLiabilityDetailsReport(reportInfo, outputStream);
    }

    @Override
    public void writeAclLiabilitiesByAggLicClassReport(AclCalculationReportsInfoDto reportInfo,
                                                       OutputStream outputStream) {
        aclCalculationReportRepository.writeAclLiabilitiesByAggLicClassReport(reportInfo, outputStream);
    }

    @Override
    public void writeAclLiabilitiesByRhReport(AclCalculationReportsInfoDto reportInfo, OutputStream outputStream) {
        aclCalculationReportRepository.writeAclLiabilitiesByRhReport(reportInfo, outputStream);
    }

    @Override
    public void writeAclComparisonByAggLcClassAndTitleReport(AclCalculationReportsInfoDto reportInfo,
                                                             OutputStream outputStream) {
        //TODO will be implemented with backend logic
    }

    @Override
    public void writeAclFundPoolByAggLcReport(Set<String> fundPoolIds, OutputStream outputStream) {
        //TODO implement backend
    }
}
