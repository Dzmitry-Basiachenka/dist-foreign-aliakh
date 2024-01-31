package com.copyright.rup.dist.foreign.ui.report.impl;

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
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenarioReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenariosReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IFasServiceFeeTrueUpReportController;
import com.copyright.rup.dist.foreign.ui.report.api.INtsPreServiceFeeFundReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IReportWidget;
import com.copyright.rup.dist.foreign.ui.report.api.ISalFundPoolsReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IUndistributedLiabilitiesReportController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class ReportControllerTest {

    private IUndistributedLiabilitiesReportController undistributedLiabilitiesReportController;
    private IFasServiceFeeTrueUpReportController fasServiceFeeTrueUpReportController;
    private ICommonScenariosReportController salLiabilitiesByRhReportController;
    private ICommonScenariosReportController salLiabilitiesSummaryByRhAndWorkReportController;
    private ICommonScenarioReportController ntsServiceFeeTrueUpReportController;
    private ISalFundPoolsReportController salFundPoolsReportController;
    private INtsPreServiceFeeFundReportController ntsPreServiceFeeFundReportController;
    private ReportController reportController;
    private IReportService reportService;
    private IProductFamilyProvider productFamilyProvider;

    @Before
    public void setUp() {
        reportController = new ReportController();
        reportService = createMock(IReportService.class);
        undistributedLiabilitiesReportController = createMock(IUndistributedLiabilitiesReportController.class);
        salLiabilitiesByRhReportController = createMock(ICommonScenariosReportController.class);
        salLiabilitiesSummaryByRhAndWorkReportController = createMock(ICommonScenariosReportController.class);
        fasServiceFeeTrueUpReportController = createMock(IFasServiceFeeTrueUpReportController.class);
        ntsServiceFeeTrueUpReportController = createMock(ICommonScenarioReportController.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        salFundPoolsReportController = createMock(ISalFundPoolsReportController.class);
        ntsPreServiceFeeFundReportController = createMock(INtsPreServiceFeeFundReportController.class);
        Whitebox.setInternalState(reportController, undistributedLiabilitiesReportController);
        Whitebox.setInternalState(reportController, fasServiceFeeTrueUpReportController);
        Whitebox.setInternalState(reportController, "salLiabilitiesByRhReportController",
            salLiabilitiesByRhReportController);
        Whitebox.setInternalState(reportController, "salLiabilitiesSummaryByRhAndWorkReportController",
            salLiabilitiesSummaryByRhAndWorkReportController);
        Whitebox.setInternalState(reportController, "ntsServiceFeeTrueUpReportController",
            ntsServiceFeeTrueUpReportController);
        Whitebox.setInternalState(reportController, reportService);
        Whitebox.setInternalState(reportController, productFamilyProvider);
        Whitebox.setInternalState(reportController, salFundPoolsReportController);
        Whitebox.setInternalState(reportController, ntsPreServiceFeeFundReportController);
    }

    @Test
    public void testProductFamilyProvider() {
        assertSame(productFamilyProvider, reportController.getProductFamilyProvider());
    }

    @Test
    public void testGetSalLiabilitiesByRhReportController() {
        assertSame(salLiabilitiesByRhReportController, reportController.getSalLiabilitiesByRhReportController());
    }

    @Test
    public void testGetLiabilitiesSummaryByRhAndWorkReportController() {
        assertSame(salLiabilitiesSummaryByRhAndWorkReportController,
            reportController.getSalLiabilitiesSummaryByRhAndWorkReportController());
    }

    @Test
    public void testGetFasServiceFeeTrueUpReportController() {
        assertSame(fasServiceFeeTrueUpReportController, reportController.getFasServiceFeeTrueUpReportController());
    }

    @Test
    public void testGetNtsServiceFeeTrueUpReportController() {
        assertSame(ntsServiceFeeTrueUpReportController, reportController.getNtsServiceFeeTrueUpReportController());
    }

    @Test
    public void testGetUndistributedLiabilitiesReportController() {
        assertSame(undistributedLiabilitiesReportController,
            reportController.getUndistributedLiabilitiesReportController());
    }

    @Test
    public void testGetSalFundPoolsReportController() {
        assertSame(salFundPoolsReportController,
            reportController.getSalFundPoolsReportController());
    }

    @Test
    public void testInstantiateWidget() {
        IReportWidget widget = reportController.instantiateWidget();
        assertNotNull(reportController.instantiateWidget());
        assertEquals(ReportWidget.class, widget.getClass());
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
    public void testFasBatchSummaryReportStreamSource() {
        reportService.writeFasBatchSummaryCsvReport(anyObject(OutputStream.class));
        expectLastCall().once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FdaConstants.FAS_PRODUCT_FAMILY).once();
        replay(reportService, productFamilyProvider);
        reportController.initWidget();
        assertNotNull(reportController.getFasBatchSummaryReportStreamSource().getSource().getValue().get());
        verify(reportService, productFamilyProvider);
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

    @Test
    public void testResearchStatusReportStreamSource() {
        reportService.writeResearchStatusCsvReport(anyObject(OutputStream.class));
        expectLastCall().once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FdaConstants.FAS_PRODUCT_FAMILY).once();
        replay(reportService, productFamilyProvider);
        reportController.initWidget();
        assertNotNull(reportController.getResearchStatusReportStreamSource().getSource().getValue().get());
        verify(reportService, productFamilyProvider);
    }

    @Test
    public void testGetAaclUndistributedLiabilitiesReportStreamSource() {
        reportService.writeAaclUndistributedLiabilitiesCsvReport(anyObject(OutputStream.class));
        expectLastCall().once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FdaConstants.NTS_PRODUCT_FAMILY).once();
        replay(reportService, productFamilyProvider);
        reportController.initWidget();
        assertNotNull(
            reportController.getAaclUndistributedLiabilitiesReportStreamSource().getSource().getValue().get());
        verify(reportService, productFamilyProvider);
    }

    @Test
    public void testGetSalUndistributedLiabilitiesReportStreamSource() {
        reportService.writeSalUndistributedLiabilitiesCsvReport(anyObject(OutputStream.class));
        expectLastCall().once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FdaConstants.SAL_PRODUCT_FAMILY).once();
        replay(reportService, productFamilyProvider);
        reportController.initWidget();
        assertNotNull(
            reportController.getSalUndistributedLiabilitiesReportStreamSource().getSource().getValue().get());
        verify(reportService, productFamilyProvider);
    }

    @Test
    public void testGetNtsFundPoolsReportStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2023, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        expect(OffsetDateTime.now()).andReturn(now).once();
        replay(OffsetDateTime.class);
        assertEquals("nts_fund_pools_01_02_2023_03_04.csv",
            reportController.getNtsFundPoolsReportStreamSource().getSource().getKey().get());
        verify(OffsetDateTime.class);
    }

    @Test
    public void testGetNtsPreServiceFeeFundReportController() {
        assertSame(ntsPreServiceFeeFundReportController, reportController.getNtsPreServiceFeeFundReportController());
    }
}
