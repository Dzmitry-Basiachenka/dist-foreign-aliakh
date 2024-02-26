package com.copyright.rup.dist.foreign.vui.report.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonReportController;
import com.copyright.rup.dist.foreign.vui.report.api.IReportController;
import com.copyright.rup.dist.foreign.vui.report.api.fas.IFasReportController;
import com.copyright.rup.dist.foreign.vui.report.api.nts.INtsReportController;
import com.copyright.rup.dist.foreign.vui.report.impl.report.ReportControllerProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IController;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dialog.Dialog;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

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
@PrepareForTest(UI.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class ReportWidgetTest {

    private static final String UNDISTRIBUTED_LIABILITIES_REPORT = "Undistributed Liabilities Reconciliation Report";
    private ReportWidget reportWidget;
    private ReportControllerProvider controllerProvider;
    private IFasReportController fasReportController;
    private INtsReportController ntsReportController;
    private IProductFamilyProvider productFamilyProvider;

    @Before
    public void setUp() {
        reportWidget = new ReportWidget();
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        controllerProvider = createMock(ReportControllerProvider.class);
        IReportController reportController = new ReportController();
        fasReportController = createMock(IFasReportController.class);
        ntsReportController = createMock(INtsReportController.class);
        reportWidget.setController(reportController);
        Whitebox.setInternalState(reportController, productFamilyProvider);
        Whitebox.setInternalState(reportController, controllerProvider);
    }

    @Test
    public void testInit() {
        expectProductFamily(FdaConstants.FAS_PRODUCT_FAMILY, fasReportController);
        replayAll();
        reportWidget.init();
        assertEquals("reports-menu-root", reportWidget.getClassName());
        assertReportsMenuFasFas2();
        verifyAll();
    }

    @Test
    public void testRefreshProductFamilyFas() {
        expectProductFamily(FdaConstants.FAS_PRODUCT_FAMILY, fasReportController);
        replayAll();
        reportWidget.refresh();
        verifyAll();
    }

    @Test
    public void testRefreshProductFamilyFas2() {
        expectProductFamily(FdaConstants.CLA_FAS_PRODUCT_FAMILY, fasReportController);
        replayAll();
        reportWidget.refresh();
        verifyAll();
    }

    @Test
    public void testRefreshProductFamilyNts() {
        expectProductFamily(FdaConstants.NTS_PRODUCT_FAMILY, ntsReportController);
        replayAll();
        reportWidget.refresh();
        assertReportsMenuNts();
        verifyAll();
    }

    @Test
    public void testOpenReportWindow() throws Exception {
        mockStatic(Dialog.class);
        IController controller = createMock(IController.class);
        UndistributedLiabilitiesReportWidget widget = createMock(UndistributedLiabilitiesReportWidget.class);
        expect(controller.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        widget.setHeaderTitle(UNDISTRIBUTED_LIABILITIES_REPORT);
        expectLastCall().once();
        replayAll();
        Whitebox.invokeMethod(reportWidget, "openReportWindow", UNDISTRIBUTED_LIABILITIES_REPORT, controller);
        verifyAll();
    }

    private void expectProductFamily(String productFamily, ICommonReportController commonReportController) {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(productFamily).once();
        expect(controllerProvider.getController(productFamily)).andReturn(commonReportController).once();
    }

    private void assertReportsMenuFasFas2() {
        assertEquals(1, CollectionUtils.size(reportWidget.getItems()));
        List<MenuItem> menuItems = reportWidget.getItems().get(0).getSubMenu().getItems();
        assertEquals(7, CollectionUtils.size(menuItems));
        assertEquals("FAS Batch Summary Report", menuItems.get(0).getText());
        assertEquals("Summary of Market Report", menuItems.get(1).getText());
        assertEquals("Research Status Report", menuItems.get(2).getText());
        assertEquals("Service Fee True-up Report", menuItems.get(3).getText());
        assertEquals(UNDISTRIBUTED_LIABILITIES_REPORT, menuItems.get(4).getText());
        assertEquals("Ownership Adjustment Report", menuItems.get(5).getText());
        assertEquals("Tax Notification Report", menuItems.get(6).getText());
    }

    private void assertReportsMenuNts() {
        assertEquals(1, CollectionUtils.size(reportWidget.getItems()));
        List<MenuItem> menuItems = reportWidget.getItems().get(0).getSubMenu().getItems();
        assertEquals(4, CollectionUtils.size(menuItems));
        assertEquals("NTS Withdrawn Batch Summary Report", menuItems.get(0).getText());
        assertEquals("Undistributed Liabilities Reconciliation Report", menuItems.get(1).getText());
        assertEquals("Tax Notification Report", menuItems.get(2).getText());
        assertEquals("Service Fee True-up Report", menuItems.get(3).getText());
    }
}
