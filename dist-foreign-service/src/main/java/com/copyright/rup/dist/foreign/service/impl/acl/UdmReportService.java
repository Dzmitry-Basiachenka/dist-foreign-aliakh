package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmReportFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmReportRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.io.PipedOutputStream;

/**
 * Implements {@link IUdmReportService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 07/05/2021
 *
 * @author Dzmitry Basiachenka
 */
@Service
public class UdmReportService implements IUdmReportService {

    @Autowired
    private IUdmReportRepository udmReportRepository;

    @Override
    public void writeUdmProxyValueCsvReport(UdmProxyValueFilter filter, PipedOutputStream pipedOutputStream) {
        udmReportRepository.writeUdmProxyValueCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeUdmUsageCsvReportSpecialistManager(UdmUsageFilter filter, PipedOutputStream pipedOutputStream) {
        udmReportRepository.writeUdmUsageCsvReportSpecialistManager(filter, pipedOutputStream);
    }

    @Override
    public void writeUdmUsageCsvReportResearcher(UdmUsageFilter filter, PipedOutputStream pipedOutputStream) {
        udmReportRepository.writeUdmUsageCsvReportResearcher(filter, pipedOutputStream);
    }

    @Override
    public void writeUdmUsageCsvReportView(UdmUsageFilter filter, PipedOutputStream pipedOutputStream) {
        udmReportRepository.writeUdmUsageCsvReportView(filter, pipedOutputStream);
    }

    @Override
    public void writeUdmBaselineUsageCsvReport(UdmBaselineFilter filter, PipedOutputStream pipedOutputStream) {
        udmReportRepository.writeUdmBaselineUsageCsvReport(filter, pipedOutputStream);
    }

    @Override
    public void writeUdmWeeklySurveyCsvReport(UdmReportFilter reportFilter, OutputStream outputStream) {
        udmReportRepository.writeUdmWeeklySurveyCsvReport(reportFilter, outputStream);
    }

    @Override
    public void writeUdmSurveyLicenseeCsvReport(UdmReportFilter reportFilter, OutputStream outputStream) {
        udmReportRepository.writeUdmSurveyLicenseeCsvReport(reportFilter, outputStream);
    }
}
