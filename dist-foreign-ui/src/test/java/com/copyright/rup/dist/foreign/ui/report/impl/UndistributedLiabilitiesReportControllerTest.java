package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.ui.report.api.IUndistributedLiabilitiesReportWidget;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * Verifies {@link UndistributedLiabilitiesReportController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class UndistributedLiabilitiesReportControllerTest {

    private IReportService reportService;
    private UndistributedLiabilitiesReportController controller;

    @Before
    public void setUp() {
        controller = new UndistributedLiabilitiesReportController();
        reportService = createMock(IReportService.class);
        Whitebox.setInternalState(controller, "reportService", reportService);
    }

    @Test
    public void testInstantiateWidget() {
        IUndistributedLiabilitiesReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(UndistributedLiabilitiesReportWidget.class, widget.getClass());
    }

    @Test
    public void testGetCsvStreamSource() {
        LocalDate paymentDate = LocalDate.now();
        IUndistributedLiabilitiesReportWidget widget = createMock(IUndistributedLiabilitiesReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        String fileName = "undistributed_liabilities_";
        Capture<LocalDate> paymentDateCapture = new Capture<>();
        Capture<OutputStream> osCapture = new Capture<>();
        expect(widget.getPaymentDate()).andReturn(paymentDate).once();
        reportService.writeUndistributedLiabilitiesCsvReport(capture(paymentDateCapture), capture(osCapture));
        expectLastCall().once();
        replay(widget, reportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals(fileName + CommonDateUtils.format(OffsetDateTime.now(), "MM_dd_YYYY_HH_mm") + ".csv",
            streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        assertEquals(paymentDate, paymentDateCapture.getValue());
        assertNotNull(osCapture.getValue());
        verify(widget, reportService);
    }
}
