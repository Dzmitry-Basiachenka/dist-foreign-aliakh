package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenarioReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ISummaryMarketReportController;
import com.copyright.rup.dist.foreign.ui.report.impl.report.ReportStreamSource;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.IController;

import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import com.vaadin.server.DownloadStream;
import com.vaadin.server.Page;
import com.vaadin.server.ResourceReference;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.MenuBar.MenuItem;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Verifies {@link ReportWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, VaadinSession.class, ResourceReference.class, Page.class})
public class ReportWidgetTest {

    private ReportWidget reportWidget;
    private IReportController reportController;
    private VaadinSession vaadinSession;
    private ResourceReference resourceReference;
    private Page page;

    @Before
    public void setUp() {
        mockStatic(Page.class);
        mockStatic(Windows.class);
        mockStatic(VaadinSession.class);
        mockStatic(ResourceReference.class);
        reportController = createMock(IReportController.class);
        vaadinSession = createMock(VaadinSession.class);
        resourceReference = createMock(ResourceReference.class);
        page = createMock(Page.class);
        reportWidget = new ReportWidget();
        reportWidget.setController(reportController);
    }

    @Test
    public void testInit() {
        expectProductFamily(FdaConstants.FAS_PRODUCT_FAMILY);
        replayAll();
        reportWidget.init();
        assertEquals("reports-menu", reportWidget.getStyleName());
        assertReportsMenuFasFas2();
    }

    @Test
    public void testRefreshProductFamilyFas() {
        expectProductFamily(FdaConstants.FAS_PRODUCT_FAMILY);
        replayAll();
        reportWidget.refresh();
        verifyAll();
        assertReportsMenuFasFas2();
    }

    @Test
    public void testRefreshProductFamilyFas2() {
        expectProductFamily(FdaConstants.CLA_FAS_PRODUCT_FAMILY);
        replayAll();
        reportWidget.refresh();
        verifyAll();
        assertReportsMenuFasFas2();
    }

    @Test
    public void testRefreshProductFamilyNts() {
        expectProductFamily(FdaConstants.NTS_PRODUCT_FAMILY);
        replayAll();
        reportWidget.refresh();
        verifyAll();
        assertReportsMenuNts();
    }

    @Test
    public void testRefreshProductFamilyAacl() {
        expectProductFamily(FdaConstants.AACL_PRODUCT_FAMILY);
        replayAll();
        reportWidget.refresh();
        verifyAll();
        assertReportsMenuAacl();
    }

    @Test
    public void testUndistributedLiabilitiesReportSelected() {
        expect(reportController.getUndistributedLiabilitiesReportController())
            .andReturn(new UndistributedLiabilitiesReportController()).once();
        expect(ResourceReference.create(anyObject(), anyObject(), anyObject())).andReturn(resourceReference).once();
        Windows.showModalWindow(anyObject());
        expectLastCall().once();
        expectProductFamily(FdaConstants.FAS_PRODUCT_FAMILY);
        replayAll();
        selectMenuItem(4);
        verifyAll();
    }

    @Test
    public void testFasBatchSummaryReportSelected() {
        expectReportGenerated(reportController.getFasBatchSummaryReportStreamSource());
        expectProductFamily(FdaConstants.FAS_PRODUCT_FAMILY);
        replayAll();
        selectMenuItem(0);
        verifyAll();
    }

    @Test
    public void testNtsWithdrawnBatchSummaryReportSelected() {
        expectReportGenerated(reportController.getNtsWithdrawnBatchSummaryReportStreamSource());
        expectProductFamily(FdaConstants.NTS_PRODUCT_FAMILY);
        replayAll();
        selectMenuItem(0);
        verifyAll();
    }

    @Test
    public void testNtsUndistributedLiabilitiesReportSelected() {
        expectReportGenerated(reportController.getNtsUndistributedLiabilitiesReportStreamSource());
        expectProductFamily(FdaConstants.NTS_PRODUCT_FAMILY);
        replayAll();
        selectMenuItem(1);
        verifyAll();
    }

    @Test
    public void testSummaryMarketReportSelected() {
        ISummaryMarketReportController summaryMarketReportController = createMock(ISummaryMarketReportController.class);
        expect(reportController.getSummaryMarketReportController()).andReturn(summaryMarketReportController).once();
        expect(summaryMarketReportController.initWidget()).andReturn(new SummaryMarketReportWidget()).once();
        Windows.showModalWindow(anyObject());
        expectLastCall().once();
        expectProductFamily(FdaConstants.FAS_PRODUCT_FAMILY);
        replayAll();
        selectMenuItem(1);
        verifyAll();
    }

    @Test
    public void testResearchStatusReportSelected() {
        expectReportGenerated(reportController.getResearchStatusReportStreamSource());
        expectProductFamily(FdaConstants.FAS_PRODUCT_FAMILY);
        replayAll();
        selectMenuItem(2);
        verifyAll();
    }

    @Test
    public void testFasServiceFeeTrueUpReportSelected() {
        expect(reportController.getFasServiceFeeTrueUpReportController())
            .andReturn(new FasServiceFeeTrueUpReportController()).once();
        expect(ResourceReference.create(anyObject(), anyObject(), anyObject())).andReturn(resourceReference).once();
        Windows.showModalWindow(anyObject());
        expectLastCall().once();
        expectProductFamily(FdaConstants.FAS_PRODUCT_FAMILY);
        replayAll();
        selectMenuItem(3);
        verifyAll();
    }

    @Test
    public void testNtsServiceFeeTrueUpReportSelected() {
        ICommonScenarioReportController controller = createMock(ICommonScenarioReportController.class);
        expect(reportController.getNtsServiceFeeTrueUpReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(new CommonScenarioReportWidget()).once();
        Windows.showModalWindow(anyObject());
        expectLastCall().once();
        expectProductFamily(FdaConstants.NTS_PRODUCT_FAMILY);
        replayAll();
        selectMenuItem(3);
        verifyAll();
    }

    @Test
    public void testOpenReportWindow() throws Exception {
        mockStatic(Windows.class);
        IController controller = createMock(IController.class);
        UndistributedLiabilitiesReportWidget widget = createMock(UndistributedLiabilitiesReportWidget.class);
        expect(controller.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        widget.setCaption("Undistributes Liabilities Reconciliation Report");
        expectLastCall().once();
        replayAll();
        Whitebox.invokeMethod(reportWidget, "openReportWindow", "Undistributes Liabilities Reconciliation Report",
            controller);
        verifyAll();
    }

    @Test
    public void testReportStreamSource() {
        String fileName = "batch_summary_05_30_2018_12_30.csv";
        InputStream is = createMock(InputStream.class);
        IStreamSource streamSource = createMock(IStreamSource.class);
        Map.Entry<Supplier<String>, Supplier<InputStream>> source =
            new SimpleImmutableEntry<>(() -> fileName, () -> is);
        expect(streamSource.getSource()).andReturn(source).times(2);
        replayAll();
        ReportStreamSource reportStreamSource = new ReportStreamSource(streamSource);
        DownloadStream stream = reportStreamSource.getStream();
        assertEquals(0, stream.getCacheTime());
        assertEquals(fileName, stream.getFileName());
        assertEquals(MediaType.OCTET_STREAM.withCharset(StandardCharsets.UTF_8).toString(), stream.getContentType());
        assertEquals("private,no-cache,no-store", stream.getParameter(HttpHeaders.CACHE_CONTROL));
        assertEquals(String.format("attachment; filename=\"%s\"", fileName),
            stream.getParameter(HttpHeaders.CONTENT_DISPOSITION));
        verifyAll();
    }

    private void selectMenuItem(int index) {
        reportWidget.init();
        reportWidget.getItems().get(0).getChildren().get(index).getCommand().menuSelected(null);
    }

    private void expectReportGenerated(IStreamSource source) {
        expect(source).andReturn(new ByteArrayStreamSource("name", outputStream -> {
        })).once();
        expect(VaadinSession.getCurrent()).andReturn(vaadinSession).once();
        vaadinSession.lock();
        expectLastCall().once();
        vaadinSession.unlock();
        expectLastCall().once();
        expect(ResourceReference.create(anyObject(), anyObject(), anyObject())).andReturn(resourceReference).times(2);
        expect(Page.getCurrent()).andReturn(page).once();
        expect(resourceReference.getURL()).andReturn(StringUtils.EMPTY).once();
        page.open(anyObject(), anyObject());
        expectLastCall().once();
    }

    private void expectProductFamily(String productFamily) {
        IProductFamilyProvider productFamilyProvider = createMock(IProductFamilyProvider.class);
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(productFamily).once();
        expect(reportController.getProductFamilyProvider()).andReturn(productFamilyProvider).once();
    }

    private void assertReportsMenuFasFas2() {
        assertEquals(1, CollectionUtils.size(reportWidget.getItems()));
        List<MenuItem> menuItems = reportWidget.getItems().get(0).getChildren();
        assertEquals(7, CollectionUtils.size(menuItems));
        assertEquals("FAS Batch Summary Report", menuItems.get(0).getText());
        assertEquals("Summary of Market Report", menuItems.get(1).getText());
        assertEquals("Research Status Report", menuItems.get(2).getText());
        assertEquals("Service Fee True-up Report", menuItems.get(3).getText());
        assertEquals("Undistributed Liabilities Reconciliation Report", menuItems.get(4).getText());
        assertEquals("Ownership Adjustment Report", menuItems.get(5).getText());
        assertEquals("Tax Notification Report", menuItems.get(6).getText());
    }

    private void assertReportsMenuNts() {
        assertEquals(1, CollectionUtils.size(reportWidget.getItems()));
        List<MenuItem> menuItems = reportWidget.getItems().get(0).getChildren();
        assertEquals(4, CollectionUtils.size(menuItems));
        assertEquals("NTS Withdrawn Batch Summary Report", menuItems.get(0).getText());
        assertEquals("Undistributed Liabilities Reconciliation Report", menuItems.get(1).getText());
        assertEquals("Tax Notification Report", menuItems.get(2).getText());
        assertEquals("Service Fee True-up Report", menuItems.get(3).getText());
    }

    private void assertReportsMenuAacl() {
        assertEquals(1, CollectionUtils.size(reportWidget.getItems()));
        List<MenuItem> menuItems = reportWidget.getItems().get(0).getChildren();
        assertEquals(5, CollectionUtils.size(menuItems));
        assertEquals("Work Shares by Aggregate Licensee Class Report", menuItems.get(0).getText());
        assertEquals("Work Shares by Aggregate Licensee Class Summary Report", menuItems.get(1).getText());
        assertEquals("Baseline Usages Report", menuItems.get(2).getText());
        assertEquals("Undistributed Liabilities Reconciliation Report", menuItems.get(3).getText());
        assertEquals("Tax Notification Report", menuItems.get(4).getText());
    }
}
