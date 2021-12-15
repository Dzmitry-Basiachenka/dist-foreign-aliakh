package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmReportRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.time.LocalDate;
import java.util.Collections;
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
    public void testWriteUdmWeeklySurveyCsvReport() {
        Set<String> channels = Collections.singleton(UdmChannelEnum.CCC.name());
        Set<String> usageOrigins = Collections.singleton(UdmUsageOriginEnum.RFA.name());
        Set<Integer> periods = Collections.singleton(202112);
        LocalDate dateReceivedFrom = LocalDate.of(2021, 11, 21);
        LocalDate dateReceivedTo = LocalDate.of(2021, 11, 28);
        OutputStream outputStream = new ByteArrayOutputStream();
        udmReportRepository.writeUdmWeeklySurveyCsvReport(channels, usageOrigins, periods, dateReceivedFrom,
            dateReceivedTo, outputStream);
        expectLastCall().once();
        replay(udmReportRepository);
        udmReportRepository.writeUdmWeeklySurveyCsvReport(channels, usageOrigins, periods, dateReceivedFrom,
            dateReceivedTo, outputStream);
        verify(udmReportRepository);
    }
}
