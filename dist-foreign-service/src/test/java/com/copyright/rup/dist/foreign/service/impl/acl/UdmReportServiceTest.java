package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmReportFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmReportRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link UdmReportService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 07/05/2021
 *
 * @author Dzmitry Basiachenka
 */
public class UdmReportServiceTest {

    private IUdmReportService udmReportService;
    private IUdmReportRepository udmReportRepository;

    @Before
    public void setUp() {
        udmReportService = new UdmReportService();
        udmReportRepository = createMock(IUdmReportRepository.class);
        Whitebox.setInternalState(udmReportService, udmReportRepository);
    }

    @Test
    public void testWriteUdmUsageCsvReportSpecialistManager() {
        UdmUsageFilter filter = createMock(UdmUsageFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        udmReportRepository.writeUdmUsageCsvReportSpecialistManager(filter, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportService.writeUdmUsageCsvReportSpecialistManager(filter, outputStream);
        verify(udmReportRepository);
    }

    @Test
    public void testWriteUdmProxyValueCsvReport() {
        UdmProxyValueFilter filter = createMock(UdmProxyValueFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        udmReportRepository.writeUdmProxyValueCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportService.writeUdmProxyValueCsvReport(filter, outputStream);
        verify(udmReportRepository);
    }

    @Test
    public void testWriteUdmUsageCsvReportResearcher() {
        UdmUsageFilter filter = createMock(UdmUsageFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        udmReportRepository.writeUdmUsageCsvReportResearcher(filter, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportService.writeUdmUsageCsvReportResearcher(filter, outputStream);
        verify(udmReportRepository);
    }

    @Test
    public void testWriteUdmUsageCsvReportView() {
        UdmUsageFilter filter = createMock(UdmUsageFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        udmReportRepository.writeUdmUsageCsvReportView(filter, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportService.writeUdmUsageCsvReportView(filter, outputStream);
        verify(udmReportRepository);
    }

    @Test
    public void testWriteUdmBaselineUsageCsvReport() {
        UdmBaselineFilter filter = createMock(UdmBaselineFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        udmReportRepository.writeUdmBaselineUsageCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportService.writeUdmBaselineUsageCsvReport(filter, outputStream);
        verify(udmReportRepository);
    }

    @Test
    public void testWriteUdmBaselineValuesCsvReport() {
        UdmBaselineValueFilter filter = createMock(UdmBaselineValueFilter.class);
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        udmReportRepository.writeUdmBaselineValuesCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportService.writeUdmBaselineValuesCsvReport(filter, outputStream);
        verify(udmReportRepository);
    }

    @Test
    public void testWriteUdmWeeklySurveyCsvReport() {
        OutputStream outputStream = new ByteArrayOutputStream();
        UdmReportFilter filter = new UdmReportFilter();
        filter.setDateTo(LocalDate.of(2021, 11, 28));
        filter.setUsageOrigin(UdmUsageOriginEnum.RFA);
        filter.setDateFrom(LocalDate.of(2021, 11, 21));
        filter.setChannel(UdmChannelEnum.CCC);
        filter.setPeriods(Set.of(202112));
        udmReportRepository.writeUdmWeeklySurveyCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportService.writeUdmWeeklySurveyCsvReport(filter, outputStream);
        verify(udmReportRepository);
    }

    @Test
    public void testWriteUdmSurveyLicenseeCsvReport() {
        OutputStream outputStream = new ByteArrayOutputStream();
        UdmReportFilter filter = new UdmReportFilter();
        filter.setPeriods(Set.of(202112));
        udmReportRepository.writeUdmSurveyLicenseeCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportService.writeUdmSurveyLicenseeCsvReport(filter, outputStream);
        verify(udmReportRepository);
    }

    @Test
    public void testWriteCompletedAssignmentsReport() {
        OutputStream outputStream = new ByteArrayOutputStream();
        UdmReportFilter filter = new UdmReportFilter();
        filter.setPeriods(Set.of(202112));
        udmReportRepository.writeUdmCompletedAssignmentsCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportService.writeUdmCompletedAssignmentsCsvReport(filter, outputStream);
        verify(udmReportRepository);
    }

    @Test
    public void testUdmVerifiedDetailsBySourceReport() {
        OutputStream outputStream = new ByteArrayOutputStream();
        UdmReportFilter filter = new UdmReportFilter();
        filter.setPeriods(Set.of(202112));
        udmReportRepository.writeUdmVerifiedDetailsBySourceReport(filter, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportRepository.writeUdmVerifiedDetailsBySourceReport(filter, outputStream);
        verify(udmReportRepository);
    }

    @Test
    public void testWriteUdmUsageEditsInBaselineCsvReport() {
        OutputStream outputStream = new ByteArrayOutputStream();
        UdmReportFilter filter = new UdmReportFilter();
        filter.setPeriods(Set.of(202112));
        filter.setDateFrom(LocalDate.of(2021, 11, 21));
        filter.setDateTo(LocalDate.of(2021, 11, 28));
        udmReportRepository.writeUdmUsageEditsInBaselineCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportService.writeUdmUsageEditsInBaselineCsvReport(filter, outputStream);
        verify(udmReportRepository);
    }

    @Test
    public void testWriteUdmUsableDetailsByCountryCsvReport() {
        OutputStream outputStream = new ByteArrayOutputStream();
        UdmReportFilter filter = new UdmReportFilter();
        filter.setPeriods(Set.of(202112));
        filter.setDateFrom(LocalDate.of(2021, 11, 21));
        filter.setDateTo(LocalDate.of(2021, 11, 28));
        udmReportRepository.writeUdmUsableDetailsByCountryCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportService.writeUdmUsableDetailsByCountryCsvReport(filter, outputStream);
        verify(udmReportRepository);
    }

    @Test
    public void testWriteUdmValueByStatusCsvReport() {
        OutputStream outputStream = new ByteArrayOutputStream();
        udmReportRepository.writeUdmValuesByStatusCsvReport(202212, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportService.writeUdmValuesByStatusCsvReport(202212, outputStream);
        verify(udmReportRepository);
    }

    @Test
    public void testWriteUdmUsagesByStatusCsvReport() {
        OutputStream outputStream = new ByteArrayOutputStream();
        udmReportRepository.writeUdmUsagesByStatusCsvReport(202212, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportService.writeUdmUsagesByStatusCsvReport(202212, outputStream);
        verify(udmReportRepository);
    }

    @Test
    public void testWriteUdmValuesCsvReport() {
        PipedOutputStream outputStream = createMock(PipedOutputStream.class);
        UdmValueFilter filter = new UdmValueFilter();
        filter.setPeriods(Set.of(202212));
        udmReportRepository.writeUdmValuesCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportService.writeUdmValuesCsvReport(filter, outputStream);
        verify(udmReportRepository);
    }

    @Test
    public void testWriteUdmBaselineValueUpdatesCsvRepor() {
        UdmReportFilter filter = new UdmReportFilter();
        filter.setPeriods(Set.of(202112));
        filter.setUserNames(Set.of("user@copyright.com"));
        filter.setDateFrom(LocalDate.of(2021, 11, 21));
        filter.setDateTo(LocalDate.of(2021, 11, 28));
        OutputStream outputStream = new ByteArrayOutputStream();
        udmReportRepository.writeUdmBaselineValueUpdatesCsvReport(filter, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportService.writeUdmBaselineValueUpdatesCsvReport(filter, outputStream);
        verify(udmReportRepository);
    }

    @Test
    public void testWriteSurveyDashboardCsvRepor() {
        Set<Integer> periods = new HashSet<>(List.of(202106, 202212));
        OutputStream outputStream = new ByteArrayOutputStream();
        udmReportRepository.writeUdmSurveyDashboardCsvReport(periods, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportService.writeUdmSurveyDashboardCsvReport(periods, outputStream);
        verify(udmReportRepository);
    }
}
