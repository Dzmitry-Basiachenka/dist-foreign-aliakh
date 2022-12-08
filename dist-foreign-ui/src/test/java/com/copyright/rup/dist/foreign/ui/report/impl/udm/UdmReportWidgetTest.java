package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonUserNamesReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmSurveyDashboardReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmSurveyLicenseeReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmUsableDetailsByCountryReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmUsageEditsInBaselineReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmUsagesByStatusReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmValuesByStatusReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmVerifiedDetailsBySourceReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmWeeklySurveyReportController;
import com.copyright.rup.dist.foreign.ui.report.impl.report.ReportStreamSource;
import com.copyright.rup.vaadin.ui.component.window.Windows;

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

    private static final String WEEKLY_SURVEY_REPORT = "Weekly Survey Report";
    private static final String SURVEY_LICENSEE_REPORT = "Survey Licensee Report";
    private static final String SURVEY_DASHBOARD_REPORT = "Survey Dashboard Report";
    private static final String VERIFIED_DETAILS_BY_SOURCE_REPORT = "Verified Details by Source Report";
    private static final String USABLE_DETAILS_BY_COUNTRY_REPORT = "Usable Details by Country Report";
    private static final String USAGE_EDITS_IN_BASELINE_REPORT = "Usage Edits in Baseline Report";
    private static final String BASELINE_VALUE_UPDATES_REPORT = "Baseline Value Updates Report";
    private static final String COMPLETED_ASSIGNMENTS_BY_EMPLOYEE_REPORT = "Completed Assignments by Employee Report";
    private static final String USAGE_DETAILS_BY_STATUS_REPORT = "Usage Details by Status Report";
    private static final String VALUES_BY_STATUS_REPORT = "Values by Status Report";

    private final IUdmReportController udmReportController = createMock(IUdmReportController.class);
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
        setSpecialistExpectations();
        replayAll();
        udmReportWidget.init();
        assertEquals("reports-menu", udmReportWidget.getStyleName());
        assertReportsMenu();
    }

    @Test
    public void testRefreshSpecialist() {
        setSpecialistExpectations();
        replayAll();
        udmReportWidget.refresh();
        verifyAll();
        assertReportsMenu();
    }

    @Test
    public void testRefreshManager() {
        setManagerExpectations();
        replayAll();
        udmReportWidget.refresh();
        verifyAll();
        assertReportsMenu();
    }

    @Test
    public void testRefreshResearcher() {
        setResearcherExpectations();
        replayAll();
        udmReportWidget.refresh();
        verifyAll();
        List<MenuItem> menuItems = udmReportWidget.getItems().get(0).getChildren();
        assertEquals(3, menuItems.size());
        assertEquals(COMPLETED_ASSIGNMENTS_BY_EMPLOYEE_REPORT, menuItems.get(0).getText());
        assertEquals(USAGE_DETAILS_BY_STATUS_REPORT, menuItems.get(1).getText());
        assertEquals(VALUES_BY_STATUS_REPORT, menuItems.get(2).getText());
    }

    @Test
    public void testRefreshViewOnly() {
        setViewOnlyExpectations();
        replayAll();
        udmReportWidget.refresh();
        verifyAll();
        assertReportsMenu();
    }

    @Test
    public void testRefreshApprover() {
        setApproverExpectations();
        replayAll();
        udmReportWidget.refresh();
        verifyAll();
        assertReportsMenu();
    }

    @Test
    public void testUdmWeeklySurveyReportSelected() {
        UdmCommonReportWidget widget = createMock(UdmCommonReportWidget.class);
        IUdmWeeklySurveyReportController controller = createMock(IUdmWeeklySurveyReportController.class);
        setSpecialistExpectations();
        expect(udmReportController.getUdmWeeklySurveyReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(widget).once();
        widget.setCaption(WEEKLY_SURVEY_REPORT);
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replayAll();
        selectMenuItem(0);
        verifyAll();
    }

    @Test
    public void testUdmSurveyLicenseeReportSelected() {
        UdmCommonReportWidget widget = createMock(UdmCommonReportWidget.class);
        IUdmSurveyLicenseeReportController controller = createMock(IUdmSurveyLicenseeReportController.class);
        setSpecialistExpectations();
        expect(udmReportController.getUdmSurveyLicenseeReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(widget).once();
        widget.setCaption(SURVEY_LICENSEE_REPORT);
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replayAll();
        selectMenuItem(1);
        verifyAll();
    }

    @Test
    public void testUdmSurveyDashboardReportSelected() {
        UdmSurveyDashboardReportWidget widget = createMock(UdmSurveyDashboardReportWidget.class);
        IUdmSurveyDashboardReportController controller = createMock(IUdmSurveyDashboardReportController.class);
        setSpecialistExpectations();
        expect(udmReportController.getUdmSurveyDashboardReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(widget).once();
        widget.setCaption(SURVEY_DASHBOARD_REPORT);
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replayAll();
        selectMenuItem(2);
        verifyAll();
    }

    @Test
    public void testUdmVerifiedDetailsBySourceReportSelected() {
        UdmCommonReportWidget widget = createMock(UdmCommonReportWidget.class);
        IUdmVerifiedDetailsBySourceReportController controller =
            createMock(IUdmVerifiedDetailsBySourceReportController.class);
        setSpecialistExpectations();
        expect(udmReportController.getUdmVerifiedDetailsBySourceReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(widget).once();
        widget.setCaption(VERIFIED_DETAILS_BY_SOURCE_REPORT);
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replayAll();
        selectMenuItem(3);
        verifyAll();
    }

    @Test
    public void testUdmUsableDetailsByCountryReportSelected() {
        UdmUsableDetailsByCountryReportWidget widget = createMock(UdmUsableDetailsByCountryReportWidget.class);
        IUdmUsableDetailsByCountryReportController controller =
            createMock(IUdmUsableDetailsByCountryReportController.class);
        setSpecialistExpectations();
        expect(udmReportController.getUdmUsableDetailsByCountryReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(widget).once();
        widget.setCaption(USABLE_DETAILS_BY_COUNTRY_REPORT);
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replayAll();
        selectMenuItem(4);
        verifyAll();
    }

    @Test
    public void testUdmUsageEditsInBaselineReportSelected() {
        UdmUsageEditsInBaselineReportWidget widget = createMock(UdmUsageEditsInBaselineReportWidget.class);
        IUdmUsageEditsInBaselineReportController controller =
            createMock(IUdmUsageEditsInBaselineReportController.class);
        setSpecialistExpectations();
        expect(udmReportController.getUdmUsageEditsInBaselineReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(widget).once();
        widget.setCaption(USAGE_EDITS_IN_BASELINE_REPORT);
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replayAll();
        selectMenuItem(5);
        verifyAll();
    }

    @Test
    public void testUdmBaselineValueUpdatesReportSelected() {
        UdmCommonUserNamesReportWidget widget = createMock(UdmCommonUserNamesReportWidget.class);
        IUdmCommonUserNamesReportController controller = createMock(IUdmCommonUserNamesReportController.class);
        setSpecialistExpectations();
        expect(udmReportController.getUdmBaselineValueUpdatesReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(widget).once();
        widget.setCaption(BASELINE_VALUE_UPDATES_REPORT);
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replayAll();
        selectMenuItem(6);
        verifyAll();
    }

    @Test
    public void testCompletedAssignmentReportSelected() {
        UdmCommonUserNamesReportWidget widget = createMock(UdmCommonUserNamesReportWidget.class);
        IUdmCommonUserNamesReportController controller = createMock(IUdmCommonUserNamesReportController.class);
        setSpecialistExpectations();
        expect(udmReportController.getCompletedAssignmentsReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(widget).once();
        widget.setCaption(COMPLETED_ASSIGNMENTS_BY_EMPLOYEE_REPORT);
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replayAll();
        selectMenuItem(7);
        verifyAll();
    }

    @Test
    public void testUsageDetailsByStatusReportSelected() {
        UdmCommonStatusReportWidget widget = createMock(UdmCommonStatusReportWidget.class);
        IUdmUsagesByStatusReportController controller = createMock(IUdmUsagesByStatusReportController.class);
        setSpecialistExpectations();
        expect(udmReportController.getUdmUsagesByStatusReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(widget).once();
        widget.setCaption(USAGE_DETAILS_BY_STATUS_REPORT);
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replayAll();
        selectMenuItem(8);
        verifyAll();
    }

    @Test
    public void testValuesByStatusReportSelected() {
        UdmCommonStatusReportWidget widget = createMock(UdmCommonStatusReportWidget.class);
        IUdmValuesByStatusReportController controller = createMock(IUdmValuesByStatusReportController.class);
        setSpecialistExpectations();
        expect(udmReportController.getUdmValuesByStatusReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(widget).once();
        widget.setCaption(VALUES_BY_STATUS_REPORT);
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replayAll();
        selectMenuItem(9);
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
        assertEquals(10, menuItems.size());
        assertEquals(WEEKLY_SURVEY_REPORT, menuItems.get(0).getText());
        assertEquals(SURVEY_LICENSEE_REPORT, menuItems.get(1).getText());
        assertEquals(SURVEY_DASHBOARD_REPORT, menuItems.get(2).getText());
        assertEquals(VERIFIED_DETAILS_BY_SOURCE_REPORT, menuItems.get(3).getText());
        assertEquals(USABLE_DETAILS_BY_COUNTRY_REPORT, menuItems.get(4).getText());
        assertEquals(USAGE_EDITS_IN_BASELINE_REPORT, menuItems.get(5).getText());
        assertEquals(BASELINE_VALUE_UPDATES_REPORT, menuItems.get(6).getText());
        assertEquals(COMPLETED_ASSIGNMENTS_BY_EMPLOYEE_REPORT, menuItems.get(7).getText());
        assertEquals(USAGE_DETAILS_BY_STATUS_REPORT, menuItems.get(8).getText());
        assertEquals(VALUES_BY_STATUS_REPORT, menuItems.get(9).getText());
    }

    private void setSpecialistExpectations() {
        setPermissionsExpectations(true, false, false, false, false);
    }

    private void setManagerExpectations() {
        setPermissionsExpectations(false, true, false, false, false);
    }

    private void setResearcherExpectations() {
        setPermissionsExpectations(false, false, true, false, false);
    }

    private void setViewOnlyExpectations() {
        setPermissionsExpectations(false, false, false, true, false);
    }

    private void setApproverExpectations() {
        setPermissionsExpectations(false, false, false, false, true);
    }

    private void setPermissionsExpectations(boolean isSpecialist, boolean isManager, boolean isResearcher,
                                            boolean isViewOnly, boolean isApprover) {
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andStubReturn(isSpecialist);
        expect(ForeignSecurityUtils.hasManagerPermission()).andStubReturn(isManager);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andStubReturn(isResearcher);
        expect(ForeignSecurityUtils.hasViewOnlyPermission()).andStubReturn(isViewOnly);
        expect(ForeignSecurityUtils.hasApproverPermission()).andStubReturn(isApprover);
    }
}
