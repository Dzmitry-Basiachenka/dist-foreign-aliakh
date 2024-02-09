package com.copyright.rup.dist.foreign.vui.report.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verify;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.report.api.IReportController;
import com.copyright.rup.dist.foreign.vui.report.api.IUndistributedLiabilitiesReportController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IController;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentUtil;
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
    private IReportController reportController;

    @Before
    public void setUp() {
        reportWidget = new ReportWidget();
        reportController = createMock(IReportController.class);
        reportWidget.setController(reportController);
    }

    @Test
    public void testInit() {
        expectProductFamily(FdaConstants.FAS_PRODUCT_FAMILY);
        replayAll();
        reportWidget.init();
        assertEquals("reports-menu-root", reportWidget.getClassName());
        assertReportsMenuFasFas2();
    }

    @Test
    public void testRefreshProductFamilyFas() {
        expectProductFamily(FdaConstants.FAS_PRODUCT_FAMILY);
        replayAll();
        reportWidget.refresh();
        verifyAll();
    }

    @Test
    public void testRefreshProductFamilyFas2() {
        expectProductFamily(FdaConstants.CLA_FAS_PRODUCT_FAMILY);
        replayAll();
        reportWidget.refresh();
        verifyAll();
    }

    @Test
    public void testFasBatchSummaryReportSelected() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).once();
        expect(ui.getUIId()).andReturn(1).once();
        expect(reportController.getFasBatchSummaryReportStreamSource()).andReturn(
            new ByteArrayStreamSource("name", outputStream -> {
            })).once();
        IProductFamilyProvider productFamilyProvider = createMock(IProductFamilyProvider.class);
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FdaConstants.FAS_PRODUCT_FAMILY).once();
        expect(reportController.getProductFamilyProvider()).andReturn(productFamilyProvider).once();
        replay(reportController, UI.class, ui, productFamilyProvider);
        selectMenuItem(0);
        verify(reportController, UI.class, ui, productFamilyProvider);
    }

    @Test
    public void testResearchStatusReportSelected() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).once();
        expect(ui.getUIId()).andReturn(1).once();
        expect(reportController.getResearchStatusReportStreamSource()).andReturn(
            new ByteArrayStreamSource("name", outputStream -> {
            })).once();
        IProductFamilyProvider productFamilyProvider = createMock(IProductFamilyProvider.class);
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FdaConstants.FAS_PRODUCT_FAMILY).once();
        expect(reportController.getProductFamilyProvider()).andReturn(productFamilyProvider).once();
        replay(reportController, UI.class, ui, productFamilyProvider);
        selectMenuItem(1);
        verify(reportController, UI.class, ui, productFamilyProvider);
    }

    @Test
    public void testUndistributedLiabilitiesReportSelected() {
        IUndistributedLiabilitiesReportController liabilitiesReportController =
            createMock(IUndistributedLiabilitiesReportController.class);
        expect(reportController.getUndistributedLiabilitiesReportController()).andReturn(liabilitiesReportController)
            .once();
        UndistributedLiabilitiesReportWidget widget = createMock(UndistributedLiabilitiesReportWidget.class);
        expect(liabilitiesReportController.initWidget()).andReturn(widget).once();
        widget.setHeaderTitle(UNDISTRIBUTED_LIABILITIES_REPORT);
        expectLastCall().once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        IProductFamilyProvider productFamilyProvider = createMock(IProductFamilyProvider.class);
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FdaConstants.FAS_PRODUCT_FAMILY).once();
        expect(reportController.getProductFamilyProvider()).andReturn(productFamilyProvider).once();
        replay(reportController, productFamilyProvider, widget, liabilitiesReportController);
        selectMenuItem(2);
        verify(reportController, productFamilyProvider, widget, liabilitiesReportController);
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
        Whitebox.invokeMethod(reportWidget, "openReportWindow", UNDISTRIBUTED_LIABILITIES_REPORT,
            controller);
        verifyAll();
    }

    private void selectMenuItem(int index) {
        reportWidget.init();
        MenuItem menuItem = reportWidget.getItems().get(0).getSubMenu().getItems().get(index);
        ComponentUtil.fireEvent(menuItem, new ClickEvent<>(menuItem));
    }

    private void expectProductFamily(String productFamily) {
        IProductFamilyProvider productFamilyProvider = createMock(IProductFamilyProvider.class);
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(productFamily).once();
        expect(reportController.getProductFamilyProvider()).andReturn(productFamilyProvider).once();
    }

    private void assertReportsMenuFasFas2() {
        assertEquals(1, CollectionUtils.size(reportWidget.getItems()));
        List<MenuItem> menuItems = reportWidget.getItems().get(0).getSubMenu().getItems();
        assertEquals(3, CollectionUtils.size(menuItems));
        assertEquals("FAS Batch Summary Report", menuItems.get(0).getText());
        assertEquals("Research Status Report", menuItems.get(1).getText());
        assertEquals(UNDISTRIBUTED_LIABILITIES_REPORT, menuItems.get(2).getText());
    }
}
