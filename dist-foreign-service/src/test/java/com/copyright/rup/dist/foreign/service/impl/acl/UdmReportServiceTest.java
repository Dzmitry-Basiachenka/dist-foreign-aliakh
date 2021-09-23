package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmReportRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.PipedOutputStream;

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
}
