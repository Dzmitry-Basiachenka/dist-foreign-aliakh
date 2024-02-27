package com.copyright.rup.dist.foreign.vui.report.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonScenarioReportController;
import com.copyright.rup.dist.foreign.vui.report.api.INtsPreServiceFeeFundReportController;
import com.copyright.rup.dist.foreign.vui.report.api.nts.INtsReportController;
import com.copyright.rup.dist.foreign.vui.report.impl.report.ReportControllerProvider;
import com.copyright.rup.dist.foreign.vui.report.impl.report.nts.NtsReportController;

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
 * Verifies {@link INtsReportController}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/26/2024
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class NtsReportControllerTest {

    private IProductFamilyProvider productFamilyProvider;
    private INtsReportController ntsReportController;
    private ReportController reportController;
    private IReportService reportService;
    private ICommonScenarioReportController ntsServiceFeeTrueUpReportController;
    private INtsPreServiceFeeFundReportController ntsPreServiceFeeFundReportController;

    @Before
    public void setUp() {
        reportController = new ReportController();
        ntsReportController = new NtsReportController();
        ReportControllerProvider reportControllerProvider = createMock(ReportControllerProvider.class);
        reportService = createMock(IReportService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        ntsServiceFeeTrueUpReportController = createMock(ICommonScenarioReportController.class);
        ntsPreServiceFeeFundReportController = createMock(INtsPreServiceFeeFundReportController.class);
        Whitebox.setInternalState(reportController, productFamilyProvider);
        Whitebox.setInternalState(reportController, reportControllerProvider);
        Whitebox.setInternalState(ntsReportController, reportService);
        Whitebox.setInternalState(ntsReportController, ntsServiceFeeTrueUpReportController);
        expect(reportControllerProvider.getController(FdaConstants.FAS_PRODUCT_FAMILY)).andReturn(ntsReportController)
            .once();
        Whitebox.setInternalState(ntsReportController, ntsPreServiceFeeFundReportController);
    }

    @Test
    public void testNtsWithdrawnBatchSummaryReportStreamSourceFileName() {
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        expect(OffsetDateTime.now()).andReturn(now).once();
        replay(OffsetDateTime.class);
        assertEquals("nts_withdrawn_batch_summary_report_01_02_2019_03_04.csv",
            ntsReportController.getNtsWithdrawnBatchSummaryReportStreamSource().getSource().getKey().get());
        verify(OffsetDateTime.class);
    }

    @Test
    public void testNtsWithdrawnBatchSummaryReportStreamSource() {
        reportService.writeNtsWithdrawnBatchSummaryCsvReport(anyObject(OutputStream.class));
        expectLastCall().once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FdaConstants.NTS_PRODUCT_FAMILY).once();
        replay(reportService, productFamilyProvider);
        reportController.initWidget();
        assertNotNull(ntsReportController.getNtsWithdrawnBatchSummaryReportStreamSource().getSource().getValue().get());
        verify(reportService, productFamilyProvider);
    }

    @Test
    public void testGetNtsServiceFeeTrueUpReportController() {
        assertSame(ntsServiceFeeTrueUpReportController, ntsReportController.getNtsServiceFeeTrueUpReportController());
    }

    @Test
    public void testGetNtsFundPoolsReportStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2023, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        expect(OffsetDateTime.now()).andReturn(now).once();
        replay(OffsetDateTime.class);
        assertEquals("nts_fund_pools_01_02_2023_03_04.csv",
            ntsReportController.getNtsFundPoolsReportStreamSource().getSource().getKey().get());
        verify(OffsetDateTime.class);
    }

    @Test
    public void testGetNtsPreServiceFeeFundReportController() {
        assertSame(ntsPreServiceFeeFundReportController, ntsReportController.getNtsPreServiceFeeFundReportController());
    }
}
