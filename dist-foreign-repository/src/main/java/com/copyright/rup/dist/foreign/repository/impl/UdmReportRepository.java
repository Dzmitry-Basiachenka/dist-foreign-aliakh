package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmReportFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmReportRepository;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.UdmAssigneesByStatusReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.UdmBaselineUsageCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.UdmCompletedAssignmentsReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.UdmProxyValueCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.UdmSurveyLicenseeReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.UdmUsableDetailsByCountryReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.UdmUsageCsvReportHandlerResearcher;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.UdmUsageCsvReportHandlerSpecialistManager;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.UdmUsageCsvReportHandlerView;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.UdmUsageEditsInBaselineReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.UdmVerifiedDetailsBySourceReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.UdmWeeklySurveyReportHandler;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link IUdmReportRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 07/01/2021
 *
 * @author Dzmitry Basiachenka
 */
@Repository
public class UdmReportRepository extends CommonReportRepository implements IUdmReportRepository {

    @Override
    public void writeUdmProxyValueCsvReport(UdmProxyValueFilter filter, PipedOutputStream pipedOutputStream) {
        try (UdmProxyValueCsvReportHandler handler =
                 new UdmProxyValueCsvReportHandler(Objects.requireNonNull(pipedOutputStream))) {
            if (!Objects.requireNonNull(filter).isEmpty()) {
                Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(1);
                parameters.put("filter", filter);
                getTemplate().select("IUdmReportMapper.findUdmProxyValueDtosByFilter", parameters, handler);
            }
        }
    }

    @Override
    public void writeUdmUsageCsvReportSpecialistManager(UdmUsageFilter filter, PipedOutputStream pipedOutputStream) {
        writeUdmUsageCsvReport(filter,
            new UdmUsageCsvReportHandlerSpecialistManager(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeUdmUsageCsvReportResearcher(UdmUsageFilter filter, PipedOutputStream pipedOutputStream) {
        writeUdmUsageCsvReport(filter,
            new UdmUsageCsvReportHandlerResearcher(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeUdmUsageCsvReportView(UdmUsageFilter filter, PipedOutputStream pipedOutputStream) {
        writeUdmUsageCsvReport(filter, new UdmUsageCsvReportHandlerView(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeUdmBaselineUsageCsvReport(UdmBaselineFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("filter", Objects.requireNonNull(filter));
        writeCsvReportByParts("IUdmReportMapper.findUdmBaselineUsagesCountByFilter",
            "IUdmReportMapper.findUdmBaselineUsageDtosByFilter", parameters, !filter.isEmpty(),
            () -> new UdmBaselineUsageCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeUdmWeeklySurveyCsvReport(UdmReportFilter filter, OutputStream outputStream) {
        try (UdmWeeklySurveyReportHandler handler =
                 new UdmWeeklySurveyReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IUdmReportMapper.findUdmWeeklySurveyReportDtos", filter, handler);
        }
    }

    @Override
    public void writeUdmSurveyLicenseeCsvReport(UdmReportFilter filter, OutputStream outputStream) {
        try (UdmSurveyLicenseeReportHandler handler =
                 new UdmSurveyLicenseeReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IUdmReportMapper.findUdmSurveyLicenseeReportDtos", filter, handler);
        }
    }

    @Override
    public void writeUdmVerifiedDetailsBySourceReport(UdmReportFilter filter, OutputStream outputStream) {
        try (UdmVerifiedDetailsBySourceReportHandler handler =
                 new UdmVerifiedDetailsBySourceReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IUdmReportMapper.findUdmVerifiedDetailsBySourceReportDtos", filter, handler);
        }
    }

    @Override
    public void writeUdmCompletedAssignmentsCsvReport(UdmReportFilter filter, OutputStream outputStream) {
        try (UdmCompletedAssignmentsReportHandler handler =
                 new UdmCompletedAssignmentsReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IUdmReportMapper.findUdmCompletedAssignmentsReportDtos", filter, handler);
        }
    }

    @Override
    public void writeUdmUsageEditsInBaselineCsvReport(UdmReportFilter reportFilter, OutputStream outputStream) {
        try (UdmUsageEditsInBaselineReportHandler handler =
                 new UdmUsageEditsInBaselineReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IUdmReportMapper.findUdmUsageEditsInBaselineReportDtos", reportFilter, handler);
        }
    }

    @Override
    public void writeUdmUsableDetailsByCountryCsvReport(UdmReportFilter filter, OutputStream outputStream) {
        try (UdmUsableDetailsByCountryReportHandler handler =
                 new UdmUsableDetailsByCountryReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IUdmReportMapper.findUdmUsableDetailsByCountryReportDtos", filter, handler);
        }
    }

    @Override
    public void writeUdmValuesByStatusCsvReport(Integer period, OutputStream outputStream) {
        try (UdmAssigneesByStatusReportHandler handler =
                 new UdmAssigneesByStatusReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IUdmReportMapper.findUdmValuesByStatusReportDtos", period, handler);
        }
    }

    @Override
    public void writeUdmUsagesByStatusCsvReport(Integer period, OutputStream outputStream) {
        try (UdmAssigneesByStatusReportHandler handler =
                 new UdmAssigneesByStatusReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IUdmReportMapper.findUdmUsagesByStatusReportDtos", period, handler);
        }
    }

    @Override
    public void writeUdmBaselineValueUpdatesCsvReport(UdmReportFilter reportFilter, OutputStream outputStream) {
        //TODO {dbasiachenka} implement
    }

    private void writeUdmUsageCsvReport(UdmUsageFilter filter, BaseCsvReportHandler handler) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("filter", escapeSqlLikePattern(Objects.requireNonNull(filter)));
        writeCsvReportByParts("IUdmReportMapper.findUdmUsagesCountByFilter",
            "IUdmReportMapper.findUdmUsageDtosByFilter", parameters, !filter.isEmpty(), () -> handler);
    }

    private UdmUsageFilter escapeSqlLikePattern(UdmUsageFilter udmUsageFilter) {
        UdmUsageFilter filterCopy = new UdmUsageFilter(udmUsageFilter);
        filterCopy.setSearchValue(escapeSqlLikePattern(filterCopy.getSearchValue()));
        return filterCopy;
    }
}
