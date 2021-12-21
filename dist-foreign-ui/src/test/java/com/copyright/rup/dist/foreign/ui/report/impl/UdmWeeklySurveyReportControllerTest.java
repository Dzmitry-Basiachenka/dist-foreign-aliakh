package com.copyright.rup.dist.foreign.ui.report.impl;

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
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.IUdmWeeklySurveyReportWidget;
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
import java.util.Set;

/**
 * Verifies {@link UdmWeeklySurveyReportController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/14/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class UdmWeeklySurveyReportControllerTest {

    private UdmWeeklySurveyReportController controller;
    private IUdmReportService udmReportService;
    private IUdmUsageService udmUsageService;

    @Before
    public void setUp() {
        controller = new UdmWeeklySurveyReportController();
        udmReportService = createMock(IUdmReportService.class);
        udmUsageService = createMock(IUdmUsageService.class);
        Whitebox.setInternalState(controller, udmReportService);
        Whitebox.setInternalState(controller, udmUsageService);
    }

    @Test
    public void testInstantiateWidget() {
        IUdmWeeklySurveyReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(UdmWeeklySurveyReportWidget.class, widget.getClass());
    }

    @Test
    public void testGetAllPeriods() {
        List<Integer> periods = Collections.singletonList(202112);
        expect(udmUsageService.getPeriods()).andReturn(periods).once();
        replay(udmReportService, udmUsageService);
        assertEquals(periods, controller.getAllPeriods());
        verify(udmReportService, udmUsageService);
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2021, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        String channels = UdmChannelEnum.CCC.name();
        String usageOrigins = UdmUsageOriginEnum.RFA.name();
        Set<Integer> periods = Collections.singleton(202112);
        LocalDate dateReceivedFrom = LocalDate.of(2021, 11, 21);
        LocalDate dateReceivedTo = LocalDate.of(2021, 11, 28);
        IUdmWeeklySurveyReportWidget widget = createMock(IUdmWeeklySurveyReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        Capture<String> channelCapture = newCapture();
        Capture<String> usageOriginCapture = newCapture();
        Capture<Set<Integer>> periodsCapture = newCapture();
        Capture<LocalDate> dateReceivedFromCapture = newCapture();
        Capture<LocalDate> dateReceivedToCapture = newCapture();
        Capture<OutputStream> osCapture = newCapture();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(widget.getChannel()).andReturn(channels).once();
        expect(widget.getUsageOrigin()).andReturn(usageOrigins).once();
        expect(widget.getPeriods()).andReturn(periods).once();
        expect(widget.getDateReceivedFrom()).andReturn(dateReceivedFrom).once();
        expect(widget.getDateReceivedTo()).andReturn(dateReceivedTo).once();
        udmReportService.writeUdmWeeklySurveyCsvReport(capture(channelCapture), capture(usageOriginCapture),
            capture(periodsCapture), capture(dateReceivedFromCapture), capture(dateReceivedToCapture),
            capture(osCapture));
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, udmReportService, udmUsageService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("weekly_survey_report_01_02_2021_03_04.csv", streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        assertEquals(channels, channelCapture.getValue());
        assertEquals(usageOrigins, usageOriginCapture.getValue());
        assertEquals(periods, periodsCapture.getValue());
        assertEquals(dateReceivedFrom, dateReceivedFromCapture.getValue());
        assertEquals(dateReceivedTo, dateReceivedToCapture.getValue());
        assertNotNull(osCapture.getValue());
        verify(OffsetDateTime.class, widget, udmReportService, udmUsageService);
    }
}
