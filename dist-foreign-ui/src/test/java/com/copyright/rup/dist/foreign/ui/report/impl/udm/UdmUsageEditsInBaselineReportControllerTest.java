package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
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
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmUsageEditsInBaselineReportWidget;

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
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link UdmUsageEditsInBaselineReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 03/11/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class UdmUsageEditsInBaselineReportControllerTest {

    private UdmUsageEditsInBaselineReportController controller;
    private IUdmReportService udmReportService;
    private IUdmUsageService udmUsageService;

    @Before
    public void setUp() {
        controller = new UdmUsageEditsInBaselineReportController();
        udmReportService = createMock(IUdmReportService.class);
        udmUsageService = createMock(IUdmUsageService.class);
        Whitebox.setInternalState(controller, udmReportService);
        Whitebox.setInternalState(controller, udmUsageService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(UdmUsageEditsInBaselineReportWidget.class));
    }

    @Test
    public void testGetAllPeriods() {
        List<Integer> periods = List.of(202112);
        expect(udmUsageService.getPeriods()).andReturn(periods).once();
        replay(udmReportService, udmUsageService);
        assertEquals(periods, controller.getAllPeriods());
        verify(udmReportService, udmUsageService);
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2021, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        UdmReportFilter filter = new UdmReportFilter();
        filter.setPeriods(Set.of(202112));
        filter.setDateFrom(LocalDate.of(2021, 11, 21));
        filter.setDateTo(LocalDate.of(2021, 11, 28));
        IUdmUsageEditsInBaselineReportWidget widget = createMock(IUdmUsageEditsInBaselineReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        Capture<UdmReportFilter> reportFilterCapture = newCapture();
        Capture<OutputStream> osCapture = newCapture();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(widget.getReportFilter()).andReturn(filter).once();
        udmReportService.writeUdmUsageEditsInBaselineCsvReport(capture(reportFilterCapture), capture(osCapture));
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, udmReportService, udmUsageService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("usage_edits_in_baseline_report_01_02_2021_03_04.csv", streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        assertEquals(filter, reportFilterCapture.getValue());
        assertNotNull(osCapture.getValue());
        verify(OffsetDateTime.class, widget, udmReportService, udmUsageService);
    }
}
