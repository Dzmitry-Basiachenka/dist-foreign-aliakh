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
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;
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
 * Verifies {@link UdmBaselineValueUpdatesReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/26/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class UdmBaselineValueUpdatesReportControllerTest {

    private static final String USER_NAME = "user@copyright.com";

    private UdmBaselineValueUpdatesReportController controller;
    private IUdmValueService udmValueService;
    private IUdmValueAuditService udmValueAuditService;
    private IUdmReportService udmReportService;

    @Before
    public void setUp() {
        controller = new UdmBaselineValueUpdatesReportController();
        udmValueService = createMock(IUdmValueService.class);
        udmValueAuditService = createMock(IUdmValueAuditService.class);
        udmReportService = createMock(IUdmReportService.class);
        Whitebox.setInternalState(controller, udmValueService);
        Whitebox.setInternalState(controller, udmValueAuditService);
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
        List<Integer> periods = List.of(202112);
        expect(udmValueService.getPeriods()).andReturn(periods).once();
        replay(udmValueService);
        assertEquals(periods, controller.getAllPeriods());
        verify(udmValueService);
    }

    @Test
    public void testGetUserNames() {
        List<String> userNames = List.of(USER_NAME);
        expect(udmValueAuditService.getUserNames()).andReturn(userNames).once();
        replay(udmValueAuditService);
        assertEquals(userNames, controller.getUserNames());
        verify(udmValueAuditService);
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2021, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        UdmReportFilter filter = new UdmReportFilter();
        filter.setPeriods(Collections.singleton(202112));
        filter.setUserNames(Collections.singleton(USER_NAME));
        filter.setDateFrom(LocalDate.of(2021, 11, 21));
        filter.setDateTo(LocalDate.of(2021, 11, 28));
        IUdmCommonUserNamesReportWidget widget = createMock(IUdmCommonUserNamesReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        Capture<UdmReportFilter> reportFilterCapture = newCapture();
        Capture<OutputStream> osCapture = newCapture();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(widget.getReportFilter()).andReturn(filter).once();
        udmReportService.writeUdmBaselineValueUpdatesCsvReport(capture(reportFilterCapture), capture(osCapture));
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, udmReportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("baseline_value_updates_report_01_02_2021_03_04.csv", streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        assertEquals(filter, reportFilterCapture.getValue());
        assertNotNull(osCapture.getValue());
        verify(OffsetDateTime.class, widget, udmReportService);
    }
}
