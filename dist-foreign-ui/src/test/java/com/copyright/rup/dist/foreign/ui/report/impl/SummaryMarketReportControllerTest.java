package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.ISummaryMarketReportWidget;

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

/**
 * Verifies {@link SummaryMarketReportController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 8/27/2018
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class SummaryMarketReportControllerTest {

    private IReportService reportService;
    private SummaryMarketReportController controller;

    @Before
    public void setUp() {
        controller = new SummaryMarketReportController();
        reportService = createMock(IReportService.class);
        Whitebox.setInternalState(controller, reportService);
    }

    @Test
    public void testInstantiateWidget() {
        ISummaryMarketReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(SummaryMarketReportWidget.class, widget.getClass());
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        List<UsageBatch> batches = Collections.emptyList();
        ISummaryMarketReportWidget widget = createMock(ISummaryMarketReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        Capture<List<UsageBatch>> batchesCapture = new Capture<>();
        Capture<OutputStream> osCapture = new Capture<>();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(widget.getBatches()).andReturn(batches).once();
        reportService.writeSummaryMarkerCsvReport(capture(batchesCapture), capture(osCapture));
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, reportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("summary_of_market_report_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        assertEquals(batches, batchesCapture.getValue());
        assertNotNull(osCapture.getValue());
        verify(OffsetDateTime.class, widget, reportService);
    }
}
