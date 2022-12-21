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
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.IFasServiceFeeTrueUpReportWidget;

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

/**
 * Verifies {@link FasServiceFeeTrueUpReportController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 7/11/2018
 *
 * @author Uladzislau_Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class FasServiceFeeTrueUpReportControllerTest {

    private IReportService reportService;
    private FasServiceFeeTrueUpReportController controller;

    @Before
    public void setUp() {
        controller = new FasServiceFeeTrueUpReportController();
        reportService = createMock(IReportService.class);
        Whitebox.setInternalState(controller, reportService);
    }

    @Test
    public void testInstantiateWidget() {
        IFasServiceFeeTrueUpReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(FasServiceFeeTrueUpReportWidget.class, widget.getClass());
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now().plusDays(1);
        LocalDate paymentDateTo = LocalDate.now().plusDays(2);
        IFasServiceFeeTrueUpReportWidget widget = createMock(IFasServiceFeeTrueUpReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        Capture<LocalDate> fromDateCapture = newCapture();
        Capture<LocalDate> toDateCapture = newCapture();
        Capture<LocalDate> paymentDateToCapture = newCapture();
        Capture<OutputStream> osCapture = newCapture();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(widget.getFromDate()).andReturn(fromDate).once();
        expect(widget.getToDate()).andReturn(toDate).once();
        expect(widget.getPaymentDateTo()).andReturn(paymentDateTo).once();
        reportService.writeFasServiceFeeTrueUpCsvReport(capture(fromDateCapture), capture(toDateCapture),
            capture(paymentDateToCapture), capture(osCapture));
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, reportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("service_fee_true_up_report_01_02_2019_03_04.csv",
            streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        assertEquals(fromDate, fromDateCapture.getValue());
        assertEquals(toDate, toDateCapture.getValue());
        assertEquals(paymentDateTo, paymentDateToCapture.getValue());
        assertNotNull(osCapture.getValue());
        verify(OffsetDateTime.class, widget, reportService);
    }
}
