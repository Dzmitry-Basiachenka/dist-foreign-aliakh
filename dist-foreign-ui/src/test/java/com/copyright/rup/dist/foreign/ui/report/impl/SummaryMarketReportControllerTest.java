package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.ui.report.api.ISummaryMarketReportWidget;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.OutputStream;
import java.time.OffsetDateTime;
import java.util.Collections;

/**
 * Verifies {@link SummaryMarketReportController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 8/27/2018
 *
 * @author Ihar Suvorau
 */
public class SummaryMarketReportControllerTest {

    private IReportService reportService;
    private IUsageBatchService usageBatchService;
    private SummaryMarketReportController controller;

    @Before
    public void setUp() {
        controller = new SummaryMarketReportController();
        reportService = createMock(IReportService.class);
        usageBatchService = createMock(IUsageBatchService.class);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, usageBatchService);
    }

    @Test
    public void testInstantiateWidget() {
        ISummaryMarketReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(SummaryMarketReportWidget.class, widget.getClass());
    }

    @Test
    public void testSendForResearchUsagesStreamSourceFileName() {
        assertEquals(
            "summary_of_market_report_" + CommonDateUtils.format(OffsetDateTime.now(), "MM_dd_YYYY_HH_mm") + ".csv",
            controller.getSummaryMarketReportStreamSource().getFileName());
    }

    @Test
    public void testGetSummaryMarketReportStreamSource() {
        reportService.writeSummaryMarkerCsvReport(anyObject(), anyObject(OutputStream.class));
        expectLastCall().once();
        expect(usageBatchService.getUsageBatches()).andReturn(Collections.emptyList()).once();
        replay(reportService, usageBatchService);
        controller.initWidget();
        assertNotNull(controller.getSummaryMarketReportStreamSource().getStream());
        verify(reportService, usageBatchService);
    }
}
