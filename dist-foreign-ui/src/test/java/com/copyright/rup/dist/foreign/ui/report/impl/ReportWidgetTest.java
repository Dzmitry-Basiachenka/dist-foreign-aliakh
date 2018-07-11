package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verify;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.dist.foreign.ui.report.impl.ReportWidget.ReportStreamSource;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
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
import java.util.List;

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
        reportWidget.init();
        assertEquals("reports-menu", reportWidget.getStyleName());
        assertEquals(1, CollectionUtils.size(reportWidget.getItems()));
        List<MenuItem> menuItems = reportWidget.getItems().get(0).getChildren();
        assertEquals(4, CollectionUtils.size(menuItems));
        assertEquals("Batch Summary Report", menuItems.get(0).getText());
        assertEquals("Research Status Report", menuItems.get(1).getText());
        assertEquals("Service Fee True-up Report", menuItems.get(2).getText());
        assertEquals("Undistributed Liabilities Reconciliation Report", menuItems.get(3).getText());
    }

    @Test
    public void testUndistributedLiabilitiesReportSelected() {
        expect(reportController.getUndistributedLiabilitiesReportController())
            .andReturn(new UndistributedLiabilitiesReportController()).once();
        expect(ResourceReference.create(anyObject(), anyObject(), anyObject())).andReturn(resourceReference).once();
        Windows.showModalWindow(anyObject());
        expectLastCall().once();
        replayAll();
        selectMenuItem(3);
        verifyAll();
    }

    @Test
    public void testBatchSummaryReportSelected() {
        expectReportGenerated(reportController.getBatchSummaryReportStreamSource());
        replayAll();
        selectMenuItem(0);
        verifyAll();
    }

    @Test
    public void testResearchStatusReportSelected() {
        expectReportGenerated(reportController.getResearchStatusReportStreamSource());
        replayAll();
        selectMenuItem(1);
        verifyAll();
    }

    @Test
    public void testServiceFeeTrueUpReportSelected() {
        expect(reportController.getServiceFeeTrueUpReportController())
            .andReturn(new ServiceFeeTrueUpReportController()).once();
        expect(ResourceReference.create(anyObject(), anyObject(), anyObject())).andReturn(resourceReference).once();
        Windows.showModalWindow(anyObject());
        expectLastCall().once();
        replayAll();
        selectMenuItem(2);
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
        replay(controller, widget, Windows.class);
        Whitebox.invokeMethod(reportWidget, "openReportWindow", "Undistributes Liabilities Reconciliation Report",
            controller);
        verify(controller, widget, Windows.class);
    }

    @Test
    public void testReportStreamSource() {
        IStreamSource streamSource = createMock(IStreamSource.class);
        InputStream inputStream = createMock(InputStream.class);
        expect(streamSource.getFileName()).andReturn("batch_summary_05_30_2018_12_30.csv").once();
        expect(streamSource.getStream()).andReturn(inputStream).once();
        replay(streamSource, inputStream);
        ReportStreamSource reportStreamSource = new ReportStreamSource(streamSource);
        DownloadStream stream = reportStreamSource.getStream();
        assertEquals(0, stream.getCacheTime());
        assertEquals("batch_summary_05_30_2018_12_30.csv", stream.getFileName());
        assertEquals(MediaType.OCTET_STREAM.withCharset(StandardCharsets.UTF_8).toString(), stream.getContentType());
        assertEquals("private,no-cache,no-store", stream.getParameter(HttpHeaders.CACHE_CONTROL));
        assertEquals("attachment; filename=\"batch_summary_05_30_2018_12_30.csv\"",
            stream.getParameter(HttpHeaders.CONTENT_DISPOSITION));
        verify(streamSource, inputStream);
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
}
