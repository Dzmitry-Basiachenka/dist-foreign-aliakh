package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmReportFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmReportRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.Set;

import io.micrometer.core.annotation.Timed;

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
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
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

    @Override
    public void writeUdmVerifiedDetailsBySourceReport(UdmReportFilter reportFilter, OutputStream outputStream) {
        udmReportRepository.writeUdmVerifiedDetailsBySourceReport(reportFilter, outputStream);
    }

    @Override
    public void writeUdmCompletedAssignmentsCsvReport(UdmReportFilter reportFilter, OutputStream outputStream) {
        udmReportRepository.writeUdmCompletedAssignmentsCsvReport(reportFilter, outputStream);
    }

    @Override
    public void writeUdmUsageEditsInBaselineCsvReport(UdmReportFilter reportFilter, OutputStream outputStream) {
        udmReportRepository.writeUdmUsageEditsInBaselineCsvReport(reportFilter, outputStream);
    }

    @Override
    public void writeUdmUsableDetailsByCountryCsvReport(UdmReportFilter reportFilter, OutputStream outputStream) {
        udmReportRepository.writeUdmUsableDetailsByCountryCsvReport(reportFilter, outputStream);
    }

    @Override
    public void writeUdmValuesByStatusCsvReport(Integer period, OutputStream outputStream) {
        udmReportRepository.writeUdmValuesByStatusCsvReport(period, outputStream);
    }

    @Override
    public void writeUdmValuesCsvReport(UdmValueFilter udmValueFilter, OutputStream outputStream) {
        udmReportRepository.writeUdmValuesCsvReport(udmValueFilter, outputStream);
    }

    @Override
    public void writeUdmUsagesByStatusCsvReport(Integer period, OutputStream outputStream) {
        udmReportRepository.writeUdmUsagesByStatusCsvReport(period, outputStream);
    }

    @Override
    public void writeUdmBaselineValueUpdatesCsvReport(UdmReportFilter reportFilter, OutputStream outputStream) {
        udmReportRepository.writeUdmBaselineValueUpdatesCsvReport(reportFilter, outputStream);
    }

    @Override
    public void writeUdmSurveyDashboardCsvReport(Set<Integer> periods, OutputStream outputStream) {
        udmReportRepository.writeUdmSurveyDashboardCsvReport(periods, outputStream);
    }
}
