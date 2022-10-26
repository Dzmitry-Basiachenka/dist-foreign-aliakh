package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.filter.UdmReportFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonUserNamesReportWidget;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link CompletedAssignmentsReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/06/2022
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class CompletedAssignmentsReportControllerTest {

    private CompletedAssignmentsReportController controller;
    private IUdmReportService udmReportService;
    private IUdmUsageService udmUsageService;

    @Before
    public void setUp() {
        controller = new CompletedAssignmentsReportController();
        udmUsageService = createMock(IUdmUsageService.class);
        udmReportService = createMock(IUdmReportService.class);
        Whitebox.setInternalState(controller, udmUsageService);
        Whitebox.setInternalState(controller, udmReportService);
    }

    @Test
    public void testInstantiateWidget() {
        IUdmCommonUserNamesReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(UdmCommonUserNamesReportWidget.class, widget.getClass());
    }

    @Test
    public void testGetAllPeriods() {
        List<Integer> periods = Collections.singletonList(202112);
        expect(udmUsageService.getPeriods()).andReturn(periods).once();
        replay(udmUsageService);
        assertEquals(periods, controller.getAllPeriods());
        verify(udmUsageService);
    }

    @Test
    public void testGetUserNames() {
        List<String> userNames = Collections.singletonList("user@copyrigt.com");
        expect(udmUsageService.getUserNames()).andReturn(userNames).once();
        replay(udmUsageService);
        assertEquals(userNames, controller.getUserNames());
        verify(udmUsageService);
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2021, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        UdmReportFilter filter = new UdmReportFilter();
        filter.setDateTo(LocalDate.of(2021, 11, 28));
        filter.setDateFrom(LocalDate.of(2021, 11, 21));
        filter.setPeriods(Collections.singleton(202112));
        IUdmCommonUserNamesReportWidget widget = createMock(IUdmCommonUserNamesReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        Capture<UdmReportFilter> reportFilterCapture = newCapture();
        Capture<OutputStream> osCapture = newCapture();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(widget.getReportFilter()).andReturn(filter).once();
        udmReportService.writeUdmCompletedAssignmentsCsvReport(capture(reportFilterCapture), capture(osCapture));
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, udmReportService, udmUsageService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("completed_assignments_by_employee_report_01_02_2021_03_04.csv",
            streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        assertEquals(filter, reportFilterCapture.getValue());
        assertNotNull(osCapture.getValue());
        verify(OffsetDateTime.class, widget, udmReportService, udmUsageService);
    }
}
