package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.ui.report.api.IServiceFeeTrueUpReportWidget;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * Verifies {@link ServiceFeeTrueUpReportController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 7/11/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class ServiceFeeTrueUpReportControllerTest {

    private IReportService reportService;
    private ServiceFeeTrueUpReportController controller;

    @Before
    public void setUp() {
        controller = new ServiceFeeTrueUpReportController();
        reportService = createMock(IReportService.class);
        Whitebox.setInternalState(controller, "reportService", reportService);
    }

    @Test
    public void testInstantiateWidget() {
        IServiceFeeTrueUpReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(ServiceFeeTrueUpReportWidget.class, widget.getClass());
    }

    @Test
    public void testGetCsvStreamSource() {
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now().plusDays(1);
        LocalDate paymentDateTo = LocalDate.now().plusDays(2);
        IServiceFeeTrueUpReportWidget widget = createMock(IServiceFeeTrueUpReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        String fileName = "service_fee_true_up_report_";
        Capture<LocalDate> fromDateCapture = new Capture<>();
        Capture<LocalDate> toDateCapture = new Capture<>();
        Capture<LocalDate> paymentDateToCapture = new Capture<>();
        Capture<OutputStream> osCapture = new Capture<>();
        expect(widget.getFromDate()).andReturn(fromDate).once();
        expect(widget.getToDate()).andReturn(toDate).once();
        expect(widget.getPaymentDateTo()).andReturn(paymentDateTo).once();
        reportService.writeServiceFeeTrueUpCsvReport(capture(fromDateCapture), capture(toDateCapture),
            capture(paymentDateToCapture), capture(osCapture));
        expectLastCall().once();
        replay(widget, reportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals(fileName + CommonDateUtils.format(OffsetDateTime.now(), "MM_dd_YYYY_HH_mm") + ".csv",
            streamSource.getFileName());
        assertNotNull(streamSource.getStream());
        assertEquals(fromDate, fromDateCapture.getValue());
        assertEquals(toDate, toDateCapture.getValue());
        assertEquals(paymentDateTo, paymentDateToCapture.getValue());
        assertNotNull(osCapture.getValue());
        verify(widget, reportService);
    }
}
