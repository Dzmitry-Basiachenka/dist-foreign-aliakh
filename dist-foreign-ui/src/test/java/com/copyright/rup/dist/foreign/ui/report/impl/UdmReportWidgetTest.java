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
import com.copyright.rup.dist.foreign.ui.report.api.IUdmReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IUdmWeeklySurveyReportController;
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
 * Verifies {@link UdmReportWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/16/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class UdmReportWidgetTest {

    private UdmReportWidget udmReportWidget;
    private IUdmReportController udmReportController;

    @Before
    public void setUp() {
        mockStatic(Page.class);
        mockStatic(Windows.class);
        mockStatic(VaadinSession.class);
        mockStatic(ResourceReference.class);
        udmReportController = createMock(IUdmReportController.class);
        udmReportWidget = new UdmReportWidget();
        udmReportWidget.setController(udmReportController);
    }

    @Test
    public void testInit() {
        replayAll();
        udmReportWidget.init();
        assertEquals("reports-menu", udmReportWidget.getStyleName());
        assertReportsMenu();
    }

    @Test
    public void testRefresh() {
        replayAll();
        udmReportWidget.refresh();
        verifyAll();
        assertReportsMenu();
    }

    @Test
    public void testUdmWeeklySurveyReportSelected() {
        IUdmWeeklySurveyReportController controller = createMock(IUdmWeeklySurveyReportController.class);
        expect(udmReportController.getUdmWeeklySurveyReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(new UdmWeeklySurveyReportWidget()).once();
        Windows.showModalWindow(anyObject());
        expectLastCall().once();
        replayAll();
        selectMenuItem(0);
        verifyAll();
    }

    @Test
    public void testOpenReportWindow() throws Exception {
        mockStatic(Windows.class);
        IController controller = createMock(IController.class);
        UdmWeeklySurveyReportWidget widget = createMock(UdmWeeklySurveyReportWidget.class);
        expect(controller.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        widget.setCaption("Weekly Survey Report");
        expectLastCall().once();
        replayAll();
        Whitebox.invokeMethod(udmReportWidget, "openReportWindow", "Weekly Survey Report", controller);
        verifyAll();
    }

    @Test
    public void testReportStreamSource() {
        String fileName = "weekly_survey_report_01_02_2021_03_04.csv";
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
        udmReportWidget.init();
        udmReportWidget.getItems().get(0).getChildren().get(index).getCommand().menuSelected(null);
    }

    private void assertReportsMenu() {
        assertEquals(1, CollectionUtils.size(udmReportWidget.getItems()));
        List<MenuItem> menuItems = udmReportWidget.getItems().get(0).getChildren();
        assertEquals(1, menuItems.size());
        assertEquals("Weekly Survey Report", menuItems.get(0).getText());
    }
}
