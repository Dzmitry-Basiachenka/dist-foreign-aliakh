package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.anyObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.ui.report.api.IReportWidget;
import com.copyright.rup.dist.foreign.ui.report.api.IUndistributedLiabilitiesReportController;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.OutputStream;
import java.time.LocalDate;

/**
 * Verifies {@link ReportController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class ReportControllerTest {

    private IUndistributedLiabilitiesReportController undistributedLiabilitiesReportController;
    private ReportController reportController;
    private IReportService reportService;

    @Before
    public void setUp() {
        reportController = new ReportController();
        reportService = createMock(IReportService.class);
        undistributedLiabilitiesReportController = new UndistributedLiabilitiesReportController();
        Whitebox.setInternalState(reportController, "undistributedLiabilitiesReportController",
            undistributedLiabilitiesReportController);
        Whitebox.setInternalState(reportController, "reportService", reportService);
    }

    @Test
    public void testGetUndistributedLiabilitiesReportController() {
        assertSame(undistributedLiabilitiesReportController,
            reportController.getUndistributedLiabilitiesReportController());
    }

    @Test
    public void testInstantiateWidget() {
        IReportWidget widget = reportController.instantiateWidget();
        assertNotNull(reportController.instantiateWidget());
        assertEquals(ReportWidget.class, widget.getClass());
    }

    @Test
    public void testSendForResearchUsagesStreamSourceFileName() {
        assertEquals("batch_summary_" + CommonDateUtils.format(LocalDate.now(), "MM_dd_YYYY") + ".csv",
            reportController.getBatchSummaryReportStreamSource().getFileName());
    }

    @Test
    public void testBatchSummaryReportStreamSource() {
        reportService.writeBatchSummaryCsvReport(anyObject(OutputStream.class));
        expectLastCall().once();
        replay(reportService);
        reportController.initWidget();
        assertNotNull(reportController.getBatchSummaryReportStreamSource().getStream());
        verify(reportService);
    }

    @Test
    public void testResearchStatusReportStreamSource() {
        reportService.writeResearchStatusCsvReport(anyObject(OutputStream.class));
        expectLastCall().once();
        replay(reportService);
        reportController.initWidget();
        assertNotNull(reportController.getResearchStatusReportStreamSource().getStream());
        verify(reportService);
    }
}
