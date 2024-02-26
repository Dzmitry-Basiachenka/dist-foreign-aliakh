package com.copyright.rup.dist.foreign.vui.report.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonScenarioReportController;
import com.copyright.rup.dist.foreign.vui.report.api.IReportController;
import com.copyright.rup.dist.foreign.vui.report.api.ISummaryMarketReportController;
import com.copyright.rup.dist.foreign.vui.report.api.IUndistributedLiabilitiesReportController;
import com.copyright.rup.dist.foreign.vui.report.api.fas.IFasReportController;
import com.copyright.rup.dist.foreign.vui.report.impl.report.ReportControllerProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * Verifies FAS Report menu.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/23/2024
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UI.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class FasReportMenuTest {

    private static final String FILE_NAME = "report-name_03_12_2004.csv";
    private static final String UNDISTRIBUTED_LIABILITIES_REPORT = "Undistributed Liabilities Reconciliation Report";
    private ReportWidget reportWidget;
    private ReportControllerProvider controllerProvider;
    private IFasReportController fasReportController;
    private IProductFamilyProvider productFamilyProvider;

    @Before
    public void setUp() {
        reportWidget = new ReportWidget();
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        controllerProvider = createMock(ReportControllerProvider.class);
        IReportController controller = new ReportController();
        fasReportController = createMock(IFasReportController.class);
        reportWidget.setController(controller);
        Whitebox.setInternalState(controller, productFamilyProvider);
        Whitebox.setInternalState(controller, controllerProvider);
    }

    @Test
    public void testFasBatchSummaryReportSelected() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).once();
        expect(ui.getUIId()).andReturn(1).once();
        expect(fasReportController.getFasBatchSummaryReportStreamSource()).andReturn(
            new ByteArrayStreamSource(FILE_NAME, outputStream -> {
            })).once();
        expectProductFamily();
        replayAll();
        selectMenuItem(0);
        verifyAll();
    }

    @Test
    public void testSummaryMarketReportSelected() {
        ISummaryMarketReportController summaryMarketReportController = createMock(ISummaryMarketReportController.class);
        expect(fasReportController.getSummaryMarketReportController()).andReturn(summaryMarketReportController).once();
        SummaryMarketReportWidget widget = createMock(SummaryMarketReportWidget.class);
        expect(summaryMarketReportController.initWidget()).andReturn(widget).once();
        widget.setHeaderTitle("Summary of Market Report");
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        expectProductFamily();
        replayAll();
        selectMenuItem(1);
        verifyAll();
    }

    @Test
    public void testResearchStatusReportSelected() {
        setUiExpectation();
        expect(fasReportController.getResearchStatusReportStreamSource()).andReturn(
            new ByteArrayStreamSource(FILE_NAME, outputStream -> {
            })).once();
        expectProductFamily();
        replayAll();
        selectMenuItem(2);
        verifyAll();
    }

    @Test
    public void testUndistributedLiabilitiesReportSelected() {
        IUndistributedLiabilitiesReportController liabilitiesReportController =
            createMock(IUndistributedLiabilitiesReportController.class);
        expect(fasReportController.getUndistributedLiabilitiesReportController()).andReturn(liabilitiesReportController)
            .once();
        UndistributedLiabilitiesReportWidget widget = createMock(UndistributedLiabilitiesReportWidget.class);
        expect(liabilitiesReportController.initWidget()).andReturn(widget).once();
        widget.setHeaderTitle(UNDISTRIBUTED_LIABILITIES_REPORT);
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        expectProductFamily();
        replayAll();
        selectMenuItem(4);
        verifyAll();
    }

    @Test
    public void testOwnershipAdjustmentReportSelected() {
        ICommonScenarioReportController scenarioReportController = createMock(ICommonScenarioReportController.class);
        expect(fasReportController.getOwnershipAdjustmentReportController()).andReturn(scenarioReportController).once();
        CommonScenarioReportWidget widget = createMock(CommonScenarioReportWidget.class);
        expect(scenarioReportController.initWidget()).andReturn(widget).once();
        widget.setHeaderTitle("Ownership Adjustment Report");
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        expectProductFamily();
        replayAll();
        selectMenuItem(5);
        verifyAll();
    }

    @Test
    public void testFasServiceFeeTrueUpReportSelected() {
        FasServiceFeeTrueUpReportController fasServiceFeeTrueUpReportController =
            createMock(FasServiceFeeTrueUpReportController.class);
        expect(fasReportController.getFasServiceFeeTrueUpReportController())
            .andReturn(fasServiceFeeTrueUpReportController).once();
        FasServiceFeeTrueUpReportWidget widget = createMock(FasServiceFeeTrueUpReportWidget.class);
        expect(fasServiceFeeTrueUpReportController.initWidget()).andReturn(widget).once();
        widget.setHeaderTitle("Service Fee True-up Report");
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        expectProductFamily();
        replayAll();
        selectMenuItem(3);
        verifyAll();
    }

    @Test
    public void testFasTaxNotificationReportSelected() {
        taxNotificationReportExpectation();
        replayAll();
        selectMenuItem(6);
        verifyAll();
    }

    private void taxNotificationReportExpectation() {
        TaxNotificationReportController taxNotificationReportController =
            createMock(TaxNotificationReportController.class);
        expect(fasReportController.getTaxNotificationReportController())
            .andReturn(taxNotificationReportController).once();
        TaxNotificationReportWidget widget = createMock(TaxNotificationReportWidget.class);
        expect(taxNotificationReportController.initWidget()).andReturn(widget).once();
        widget.setHeaderTitle("Tax Notification Report");
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        expectProductFamily();
    }

    private void expectProductFamily() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FdaConstants.FAS_PRODUCT_FAMILY).once();
        expect(controllerProvider.getController(FdaConstants.FAS_PRODUCT_FAMILY)).andReturn(fasReportController).once();
    }

    private void setUiExpectation() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).once();
        expect(ui.getUIId()).andReturn(1).once();
    }

    private void selectMenuItem(int index) {
        reportWidget.init();
        MenuItem menuItem = reportWidget.getItems().get(0).getSubMenu().getItems().get(index);
        ComponentUtil.fireEvent(menuItem, new ClickEvent<>(menuItem));
    }
}
