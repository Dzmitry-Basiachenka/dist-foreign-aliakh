package com.copyright.rup.dist.foreign.vui.report.impl.fas;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verify;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonScenarioReportController;
import com.copyright.rup.dist.foreign.vui.report.api.fas.IFasReportController;
import com.copyright.rup.dist.foreign.vui.report.api.fas.IFasServiceFeeTrueUpReportController;
import com.copyright.rup.dist.foreign.vui.report.api.fas.ISummaryMarketReportController;
import com.copyright.rup.dist.foreign.vui.report.api.fas.IUndistributedLiabilitiesReportController;
import com.copyright.rup.dist.foreign.vui.report.impl.ReportController;
import com.copyright.rup.dist.foreign.vui.report.impl.ReportControllerProvider;

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
 * Verifies {@link IFasReportController}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/23/2024
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class FasReportControllerTest {

    private IProductFamilyProvider productFamilyProvider;
    private IFasReportController fasReportController;
    private ReportController reportController;
    private IUndistributedLiabilitiesReportController undistributedLiabilitiesReportController;
    private IFasServiceFeeTrueUpReportController fasServiceFeeTrueUpReportController;
    private ISummaryMarketReportController summaryMarketReportController;
    private ICommonScenarioReportController ownershipAdjustmentReportController;
    private IReportService reportService;

    @Before
    public void setUp() {
        ReportControllerProvider reportControllerProvider = createMock(ReportControllerProvider.class);
        reportController = new ReportController();
        fasReportController = new FasReportController();
        reportService = createMock(IReportService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        undistributedLiabilitiesReportController = createMock(IUndistributedLiabilitiesReportController.class);
        fasServiceFeeTrueUpReportController = createMock(IFasServiceFeeTrueUpReportController.class);
        summaryMarketReportController = createMock(ISummaryMarketReportController.class);
        ownershipAdjustmentReportController = createMock(ICommonScenarioReportController.class);
        Whitebox.setInternalState(reportController, productFamilyProvider);
        Whitebox.setInternalState(reportController, reportControllerProvider);
        Whitebox.setInternalState(fasReportController, undistributedLiabilitiesReportController);
        Whitebox.setInternalState(fasReportController, fasServiceFeeTrueUpReportController);
        Whitebox.setInternalState(fasReportController, summaryMarketReportController);
        Whitebox.setInternalState(fasReportController, ownershipAdjustmentReportController);
        Whitebox.setInternalState(fasReportController, reportService);
        expect(reportControllerProvider.getController(FdaConstants.FAS_PRODUCT_FAMILY)).andReturn(fasReportController)
            .once();
    }

    @Test
    public void testGetFasServiceFeeTrueUpReportController() {
        assertSame(fasServiceFeeTrueUpReportController,
            fasReportController.getFasServiceFeeTrueUpReportController());
    }

    @Test
    public void testGetUndistributedLiabilitiesReportController() {
        assertSame(undistributedLiabilitiesReportController,
            fasReportController.getUndistributedLiabilitiesReportController());
    }

    @Test
    public void testGetSummaryMarketReportController() {
        assertSame(summaryMarketReportController,
            fasReportController.getSummaryMarketReportController());
    }

    @Test
    public void testGetOwnershipAdjustmentReportController() {
        assertSame(ownershipAdjustmentReportController,
            fasReportController.getOwnershipAdjustmentReportController());
    }

    @Test
    public void testFasBatchSummaryStreamSourceFileName() {
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        expect(OffsetDateTime.now()).andReturn(now).once();
        replay(OffsetDateTime.class);
        assertEquals("fas_batch_summary_report_01_02_2019_03_04.csv",
            fasReportController.getFasBatchSummaryReportStreamSource().getSource().getKey().get());
        verify(OffsetDateTime.class);
    }

    @Test
    public void testFasBatchSummaryReportStreamSource() {
        reportService.writeFasBatchSummaryCsvReport(anyObject(OutputStream.class));
        expectLastCall().once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FdaConstants.FAS_PRODUCT_FAMILY).once();
        replayAll();
        reportController.initWidget();
        assertNotNull(fasReportController.getFasBatchSummaryReportStreamSource().getSource().getValue().get());
        verifyAll();
    }

    @Test
    public void testResearchStatusReportStreamSource() {
        reportService.writeResearchStatusCsvReport(anyObject(OutputStream.class));
        expectLastCall().once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FdaConstants.FAS_PRODUCT_FAMILY).once();
        replayAll();
        reportController.initWidget();
        assertNotNull(fasReportController.getResearchStatusReportStreamSource().getSource().getValue().get());
        verifyAll();
    }
}
