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
import com.copyright.rup.dist.foreign.vui.report.api.INtsPreServiceFeeFundReportController;
import com.copyright.rup.dist.foreign.vui.report.api.nts.INtsReportController;
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
 * Verifies NTS Report menu.
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
public class NtsReportMenuTest {

    private static final String FILE_NAME = "report-name_03_12_2004.csv";
    private ReportWidget reportWidget;
    private ReportControllerProvider controllerProvider;
    private INtsReportController ntsReportController;
    private IProductFamilyProvider productFamilyProvider;

    @Before
    public void setUp() {
        reportWidget = new ReportWidget();
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        controllerProvider = createMock(ReportControllerProvider.class);
        var controller = new ReportController();
        ntsReportController = createMock(INtsReportController.class);
        reportWidget.setController(controller);
        Whitebox.setInternalState(controller, productFamilyProvider);
        Whitebox.setInternalState(controller, controllerProvider);
        Whitebox.setInternalState(controller, controllerProvider);
    }

    @Test
    public void testNtsTaxNotificationReportSelected() {
        taxNotificationReportExpectation();
        replayAll();
        selectMenuItem(2);
        verifyAll();
    }

    @Test
    public void testNtsWithdrawnBatchSummaryReportSelected() {
        setUiExpectation();
        expect(ntsReportController.getNtsWithdrawnBatchSummaryReportStreamSource()).andReturn(
            new ByteArrayStreamSource(FILE_NAME, outputStream -> {
            })).once();
        expectProductFamily();
        replayAll();
        selectMenuItem(0);
        verifyAll();
    }

    @Test
    public void testNtsUndistributedLiabilitiesReportSelected() {
        setUiExpectation();
        expect(ntsReportController.getNtsUndistributedLiabilitiesReportStreamSource()).andReturn(
            new ByteArrayStreamSource(FILE_NAME, outputStream -> {
            })).once();
        expectProductFamily();
        replayAll();
        selectMenuItem(1);
        verifyAll();
    }

    @Test
    public void testNtsServiceFeeTrueUpReportSelected() {
        NtsServiceFeeTrueUpReportController ntsServiceFeeTrueUpReportController =
            createMock(NtsServiceFeeTrueUpReportController.class);
        expect(ntsReportController.getNtsServiceFeeTrueUpReportController())
            .andReturn(ntsServiceFeeTrueUpReportController).once();
        CommonScenarioReportWidget widget = createMock(CommonScenarioReportWidget.class);
        expect(ntsServiceFeeTrueUpReportController.initWidget()).andReturn(widget).once();
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
    public void testNtsFundPoolsReportSelected() {
        setUiExpectation();
        expect(ntsReportController.getNtsFundPoolsReportStreamSource()).andReturn(
            new ByteArrayStreamSource(FILE_NAME, outputStream -> {
            })).once();
        expectProductFamily();
        replayAll();
        selectMenuItem(4);
        verifyAll();
    }

    @Test
    public void testNtsPreServiceFeeFundReportSelected() {
        INtsPreServiceFeeFundReportController controller = createMock(INtsPreServiceFeeFundReportController.class);
        NtsPreServiceFeeFundReportWidget widget = createMock(NtsPreServiceFeeFundReportWidget.class);
        expect(ntsReportController.getNtsPreServiceFeeFundReportController()).andReturn(controller).once();
        expect(controller.initWidget()).andReturn(widget).once();
        widget.setHeaderTitle("NTS Pre-Service Fee Fund Report");
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        expectProductFamily();
        replayAll();
        selectMenuItem(5);
        verifyAll();
    }

    private void taxNotificationReportExpectation() {
        TaxNotificationReportController taxNotificationReportController =
            createMock(TaxNotificationReportController.class);
        expect(ntsReportController.getTaxNotificationReportController())
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
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FdaConstants.NTS_PRODUCT_FAMILY).once();
        expect(controllerProvider.getController(FdaConstants.NTS_PRODUCT_FAMILY)).andReturn(ntsReportController).once();
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
