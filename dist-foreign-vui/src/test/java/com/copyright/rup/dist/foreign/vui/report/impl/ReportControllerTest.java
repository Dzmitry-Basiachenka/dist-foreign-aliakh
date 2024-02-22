package com.copyright.rup.dist.foreign.vui.report.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.report.api.IUndistributedLiabilitiesReportController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.OutputStream;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Verifies {@link ReportController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class ReportControllerTest {

    private ReportController reportController;
    private IProductFamilyProvider productFamilyProvider;
    private IUndistributedLiabilitiesReportController undistributedLiabilitiesReportController;
    private IReportService reportService;

    @Before
    public void setUp() {
        reportController = new ReportController();
        reportService = createMock(IReportService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        undistributedLiabilitiesReportController = createMock(IUndistributedLiabilitiesReportController.class);
        Whitebox.setInternalState(reportController, undistributedLiabilitiesReportController);
        Whitebox.setInternalState(reportController, productFamilyProvider);
        Whitebox.setInternalState(reportController, reportService);
    }

    @Test
    public void testProductFamilyProvider() {
        assertSame(productFamilyProvider, reportController.getProductFamilyProvider());
    }

    @Test
    public void testInstantiateWidget() {
        var widget = reportController.instantiateWidget();
        assertNotNull(reportController.instantiateWidget());
        assertEquals(ReportWidget.class, widget.getClass());
    }

    @Test
    public void testGetUndistributedLiabilitiesReportController() {
        assertSame(undistributedLiabilitiesReportController,
            reportController.getUndistributedLiabilitiesReportController());
    }

    @Test
    public void testFasBatchSummaryStreamSourceFileName() {
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        expect(OffsetDateTime.now()).andReturn(now).once();
        replay(OffsetDateTime.class);
        assertEquals("fas_batch_summary_report_01_02_2019_03_04.csv",
            reportController.getFasBatchSummaryReportStreamSource().getSource().getKey().get());
        verify(OffsetDateTime.class);
    }

    @Test
    public void testNtsWithdrawnBatchSummaryReportStreamSourceFileName() {
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        expect(OffsetDateTime.now()).andReturn(now).once();
        replay(OffsetDateTime.class);
        assertEquals("nts_withdrawn_batch_summary_report_01_02_2019_03_04.csv",
            reportController.getNtsWithdrawnBatchSummaryReportStreamSource().getSource().getKey().get());
        verify(OffsetDateTime.class);
    }

    @Test
    public void testNtsWithdrawnBatchSummaryReportStreamSource() {
        reportService.writeNtsWithdrawnBatchSummaryCsvReport(anyObject(OutputStream.class));
        expectLastCall().once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FdaConstants.NTS_PRODUCT_FAMILY).once();
        replay(reportService, productFamilyProvider);
        reportController.initWidget();
        assertNotNull(reportController.getNtsWithdrawnBatchSummaryReportStreamSource().getSource().getValue().get());
        verify(reportService, productFamilyProvider);
    }
}
