package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.ui.report.api.IServiceFeeTrueUpReportWidget;
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
    public void testSendForResearchUsagesStreamSourceFileName() {
        assertEquals(
            "service_fee_true_up_report_" + CommonDateUtils.format(OffsetDateTime.now(), "MM_dd_YYYY_HH_mm") + ".csv",
            controller.getServiceFeeTrueUpReportStreamSource().getFileName());
    }

    @Test
    public void testGetServiveFeeTrueUpReportStreamSource() {
        reportService.writeServiceFeeTrueUpCsvReport(anyObject(LocalDate.class), anyObject(LocalDate.class),
            anyObject(LocalDate.class), anyObject(OutputStream.class));
        expectLastCall().once();
        replay(reportService);
        controller.initWidget();
        assertNotNull(controller.getServiceFeeTrueUpReportStreamSource().getStream());
        verify(reportService);
    }
}
