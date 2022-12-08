package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmSurveyDashboardReportWidget;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.OutputStream;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link UdmSurveyDashboardReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/07/2022
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class UdmSurveyDashboardReportControllerTest {

    private UdmSurveyDashboardReportController controller;
    private IUdmUsageService udmUsageService;
    private IUdmReportService udmReportService;

    @Before
    public void setUp() {
        controller = new UdmSurveyDashboardReportController();
        udmUsageService = createMock(IUdmUsageService.class);
        udmReportService = createMock(IUdmReportService.class);
        Whitebox.setInternalState(controller, udmUsageService);
        Whitebox.setInternalState(controller, udmReportService);
    }

    @Test
    public void testInstantiateWidget() {
        IUdmSurveyDashboardReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(UdmSurveyDashboardReportWidget.class, widget.getClass());
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = Collections.singletonList(202112);
        expect(udmUsageService.getPeriods()).andReturn(periods).once();
        replay(udmUsageService);
        assertEquals(periods, controller.getPeriods());
        verify(udmUsageService);
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2022, 3, 10, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        Set<Integer> periods = Collections.singleton(202112);
        IUdmSurveyDashboardReportWidget widget = createMock(IUdmSurveyDashboardReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        Capture<Set<Integer>> periodsCapture = newCapture();
        Capture<OutputStream> osCapture = newCapture();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(widget.getSelectedPeriods()).andReturn(periods).once();
        udmReportService.writeUdmSurveyDashboardCsvReport(capture(periodsCapture), capture(osCapture));
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, udmReportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("survey_dashboard_report_03_10_2022_03_04.csv", streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        assertSame(periods, periodsCapture.getValue());
        assertNotNull(osCapture.getValue());
        verify(OffsetDateTime.class, widget, udmReportService);
    }
}
