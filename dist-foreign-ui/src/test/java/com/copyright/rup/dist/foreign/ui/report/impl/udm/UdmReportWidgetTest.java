package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.report.api.udm.ICompletedAssignmentsReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmSurveyLicenseeReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmVerifiedDetailsBySourceReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmWeeklySurveyReportController;
import com.copyright.rup.dist.foreign.ui.report.impl.report.ReportStreamSource;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.IController;

import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import com.vaadin.server.DownloadStream;
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
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
public class UdmReportWidgetTest {

    private static final String ACL_PRODUCT_FAMILY = "ACL";
    private static final String COMPLETED_ASSIGNMENT_REPORT = "Completed Assignments by Employee Report";
    private static final String VERIFIED_DETAILS_BY_SOURCE_REPORT = "Verified Details By Source Report";
    public static final String OPEN_REPORT_WINDOW = "openReportWindow";

    private final IUdmReportController udmReportController = createMock(IUdmReportController.class);
    private final IProductFamilyProvider productFamilyProvider = createMock(IProductFamilyProvider.class);
    private UdmReportWidget udmReportWidget;

    @Before
    public void setUp() {
        mockStatic(Windows.class);
        mockStatic(ForeignSecurityUtils.class);
        udmReportWidget = new UdmReportWidget();
        udmReportWidget.setController(udmReportController);
    }

    @Test
    public void testInit() {
        expect(udmReportController.getProductFamilyProvider()).andReturn(productFamilyProvider).once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(ACL_PRODUCT_FAMILY).once();
        setSpecialistExpectations();
        replayAll();
        udmReportWidget.init();
        assertEquals("reports-menu", udmReportWidget.getStyleName());
        assertReportsMenu();
    }

    @Test
    public void testRefreshSpecialist() {
        expect(udmReportController.getProductFamilyProvider()).andReturn(productFamilyProvider).once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(ACL_PRODUCT_FAMILY).once();
        setSpecialistExpectations();
        replayAll();
        udmReportWidget.refresh();
        verifyAll();
        assertReportsMenu();
    }

    @Test
    public void testRefreshManager() {
        expect(udmReportController.getProductFamilyProvider()).andReturn(productFamilyProvider).once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(ACL_PRODUCT_FAMILY).once();
        setManagerExpectations();
        replayAll();
        udmReportWidget.refresh();
        verifyAll();
        assertReportsMenu();
    }

    @Test
    public void testRefreshResearcher() {
        expect(udmReportController.getProductFamilyProvider()).andReturn(productFamilyProvider).once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(ACL_PRODUCT_FAMILY).once();
        setResearcherExpectations();
        replayAll();
        udmReportWidget.refresh();
        verifyAll();
        List<MenuItem> menuItems = udmReportWidget.getItems().get(0).getChildren();
        assertEquals(1, menuItems.size());
        assertEquals(COMPLETED_ASSIGNMENT_REPORT, menuItems.get(0).getText());
    }

    @Test
    public void testRefreshViewOnly() {
        expect(udmReportController.getProductFamilyProvider()).andReturn(productFamilyProvider).once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(ACL_PRODUCT_FAMILY).once();
        setViewOnlyExpectations();
        replayAll();
        udmReportWidget.refresh();
        verifyAll();
        assertReportsMenu();
    }

    @Test
    public void testUdmWeeklySurveyReportSelected() {
        expect(udmReportController.getProductFamilyProvider()).andReturn(productFamilyProvider).once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(ACL_PRODUCT_FAMILY).once();
        setSpecialistExpectations();
        IUdmWeeklySurveyReportController controller = createMock(IUdmWeeklySurveyReportController.class);
        expect(udmReportController.getUdmWeeklySurveyReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(new UdmCommonReportWidget("Received")).once();
        Windows.showModalWindow(anyObject());
        expectLastCall().once();
        replayAll();
        selectMenuItem(0);
        verifyAll();
    }

    @Test
    public void testUdmSurveyLicenseeReportSelected() {
        expect(udmReportController.getProductFamilyProvider()).andReturn(productFamilyProvider).once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(ACL_PRODUCT_FAMILY).once();
        setSpecialistExpectations();
        IUdmSurveyLicenseeReportController controller = createMock(IUdmSurveyLicenseeReportController.class);
        expect(udmReportController.getUdmSurveyLicenseeReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(new UdmCommonReportWidget("Survey Start")).once();
        Windows.showModalWindow(anyObject());
        expectLastCall().once();
        replayAll();
        selectMenuItem(1);
        verifyAll();
    }

    @Test
    public void testUdmVerifiedDetailsBySourceReportSelected() {
        expect(udmReportController.getProductFamilyProvider()).andReturn(productFamilyProvider).once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(ACL_PRODUCT_FAMILY).once();
        setSpecialistExpectations();
        IUdmVerifiedDetailsBySourceReportController controller =
            createMock(IUdmVerifiedDetailsBySourceReportController.class);
        expect(udmReportController.getUdmVerifiedDetailsBySourceReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(new UdmCommonReportWidget("Received")).once();
        Windows.showModalWindow(anyObject());
        expectLastCall().once();
        replayAll();
        selectMenuItem(2);
        verifyAll();
    }

    @Test
    public void testCompletedAssignmentReportSelected() {
        expect(udmReportController.getProductFamilyProvider()).andReturn(productFamilyProvider).once();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(ACL_PRODUCT_FAMILY).once();
        setSpecialistExpectations();
        ICompletedAssignmentsReportController controller = createMock(ICompletedAssignmentsReportController.class);
        expect(udmReportController.getCompletedAssignmentsReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(new CompletedAssignmentsReportWidget()).once();
        Windows.showModalWindow(anyObject());
        expectLastCall().once();
        replayAll();
        selectMenuItem(3);
        verifyAll();
    }

    @Test
    public void testOpenReportWindowForWeeklySurveyReport() throws Exception {
        setSpecialistExpectations();
        mockStatic(Windows.class);
        IController controller = createMock(IController.class);
        UdmCommonReportWidget widget = createMock(UdmCommonReportWidget.class);
        expect(controller.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        widget.setCaption("Weekly Survey Report");
        expectLastCall().once();
        replayAll();
        Whitebox.invokeMethod(udmReportWidget, OPEN_REPORT_WINDOW, "Weekly Survey Report", controller);
        verifyAll();
    }

    @Test
    public void testOpenReportWindowForSurveyLicenseeReport() throws Exception {
        setSpecialistExpectations();
        mockStatic(Windows.class);
        IController controller = createMock(IController.class);
        UdmCommonReportWidget widget = createMock(UdmCommonReportWidget.class);
        expect(controller.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        widget.setCaption("Survey Licensee Report");
        expectLastCall().once();
        replayAll();
        Whitebox.invokeMethod(udmReportWidget, OPEN_REPORT_WINDOW, "Survey Licensee Report", controller);
        verifyAll();
    }

    @Test
    public void testOpenReportWindowForVerifiedDetailsBySourceReport() throws Exception {
        setSpecialistExpectations();
        mockStatic(Windows.class);
        IController controller = createMock(IController.class);
        UdmCommonReportWidget widget = createMock(UdmCommonReportWidget.class);
        expect(controller.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        widget.setCaption(VERIFIED_DETAILS_BY_SOURCE_REPORT);
        expectLastCall().once();
        replayAll();
        Whitebox.invokeMethod(udmReportWidget, OPEN_REPORT_WINDOW, VERIFIED_DETAILS_BY_SOURCE_REPORT, controller);
        verifyAll();
    }

    @Test
    public void testOpenReportWindowForCompletedAssignmentReport() throws Exception {
        setSpecialistExpectations();
        mockStatic(Windows.class);
        IController controller = createMock(IController.class);
        UdmCommonReportWidget widget = createMock(UdmCommonReportWidget.class);
        expect(controller.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        widget.setCaption(COMPLETED_ASSIGNMENT_REPORT);
        expectLastCall().once();
        replayAll();
        Whitebox.invokeMethod(udmReportWidget, OPEN_REPORT_WINDOW, COMPLETED_ASSIGNMENT_REPORT, controller);
        verifyAll();
    }

    @Test
    public void testReportStreamSource() {
        setSpecialistExpectations();
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
        assertEquals(4, menuItems.size());
        assertEquals("Weekly Survey Report", menuItems.get(0).getText());
        assertEquals("Survey Licensee Report", menuItems.get(1).getText());
        assertEquals(VERIFIED_DETAILS_BY_SOURCE_REPORT, menuItems.get(2).getText());
        assertEquals(COMPLETED_ASSIGNMENT_REPORT, menuItems.get(3).getText());
    }

    private void setSpecialistExpectations() {
        setPermissionsExpectations(true, false, false, false);
    }

    private void setManagerExpectations() {
        setPermissionsExpectations(false, true, false, false);
    }

    private void setResearcherExpectations() {
        setPermissionsExpectations(false, false, true, false);
    }

    private void setViewOnlyExpectations() {
        setPermissionsExpectations(false, false, false, true);
    }

    private void setPermissionsExpectations(boolean isSpecialist, boolean isManager, boolean isResearcher,
                                            boolean isViewOnly) {
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andStubReturn(isSpecialist);
        expect(ForeignSecurityUtils.hasManagerPermission()).andStubReturn(isManager);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andStubReturn(isResearcher);
        expect(ForeignSecurityUtils.hasViewOnlyPermission()).andStubReturn(isViewOnly);
    }
}
