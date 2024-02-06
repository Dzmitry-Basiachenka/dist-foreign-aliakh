package com.copyright.rup.dist.foreign.vui.main.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditController;
import com.copyright.rup.dist.foreign.vui.main.api.IControllerProvider;
import com.copyright.rup.dist.foreign.vui.main.api.IMainWidget;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.report.api.IReportController;
import com.copyright.rup.dist.foreign.vui.report.impl.ReportWidget;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageController;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.TabSheet;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link MainWidget}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/09/2023
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ForeignSecurityUtils.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class MainWidgetTest {

    private MainWidget mainWidget;
    private MainWidgetController controller;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(MainWidgetController.class);
        mainWidget = new MainWidget();
        mainWidget.setController(controller);
    }

    @Test
    public void testInit() {
        IControllerProvider<ICommonUsageController> commonUsageController = createMock(IControllerProvider.class);
        IControllerProvider<ICommonScenariosController> commonScenariosController =
            createMock(IControllerProvider.class);
        IControllerProvider<ICommonAuditController> commonAuditController =
            createMock(IControllerProvider.class);
        IReportController reportController = createMock(IReportController.class);
        IProductFamilyProvider productFamilyProvider = createMock(IProductFamilyProvider.class);
        expect(controller.getUsagesControllerProvider()).andReturn(commonUsageController).once();
        expect(controller.getScenariosControllerProvider()).andReturn(commonScenariosController).once();
        expect(controller.getAuditControllerProvider()).andReturn(commonAuditController).once();
        expect(controller.getReportController()).andReturn(reportController).times(2);
        expect(controller.getProductFamilyProvider()).andReturn(productFamilyProvider).times(2);
        expect(controller.getBatchStatusControllerProvider()).andReturn(
            createMock(BatchStatusControllerProvider.class)).once();
        controller.refreshWidget();
        expectLastCall().once();
        controller.onProductFamilyChanged();
        expectLastCall().once();
        reportController.onProductFamilyChanged();
        expectLastCall().once();
        var reportWidget = new ReportWidget();
        expect(reportController.initWidget()).andReturn(reportWidget).once();
        replay(controller, reportController);
        var widget = mainWidget.init();
        mainWidget.initMediator().applyPermissions();
        verify(controller, reportController);
        verifyTabSheetComponents(widget);
    }

    private void verifyTabSheetComponents(IMainWidget widget) {
        assertThat(widget, instanceOf(TabSheet.class));
        var tabSheet = (TabSheet) widget;
        assertEquals("100%", tabSheet.getHeight());
        assertEquals(Unit.PERCENTAGE, tabSheet.getHeightUnit().orElseThrow());
        assertEquals("100%", tabSheet.getWidth());
        assertEquals(Unit.PERCENTAGE, tabSheet.getWidthUnit().orElseThrow());
        assertEquals(3, tabSheet.getChildren().count());
        verifySuffixComponent(tabSheet.getSuffixComponent());
    }

    private void verifySuffixComponent(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        assertEquals(2, component.getChildren().count());
        var reportsButton = component.getChildren().collect(Collectors.toList()).get(0);
        assertThat(reportsButton, instanceOf(MenuBar.class));
        //TODO: {aazarenka} add test for report menu
        var productFamilyRefreshButtonLayout = component.getChildren().collect(Collectors.toList()).get(1);
        assertThat(productFamilyRefreshButtonLayout, instanceOf(HorizontalLayout.class));
        assertEquals(2, ((HorizontalLayout) productFamilyRefreshButtonLayout).getComponentCount());
        verifyProductFamilySelect(((HorizontalLayout) productFamilyRefreshButtonLayout).getComponentAt(0));
        verifyRefreshButton(((HorizontalLayout) productFamilyRefreshButtonLayout).getComponentAt(1));
    }

    private void verifyProductFamilySelect(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout productFamilyLayout = (HorizontalLayout) component;
        assertEquals(2, productFamilyLayout.getComponentCount());
        assertThat(productFamilyLayout.getComponentAt(0), instanceOf(Label.class));
        var productFamilyLabel = (Label) productFamilyLayout.getComponentAt(0);
        assertEquals("Product Family", productFamilyLabel.getText());
        assertThat(productFamilyLayout.getComponentAt(1), instanceOf(Select.class));
        Select<String> productFamilySelect = (Select) productFamilyLayout.getComponentAt(1);
        List<String> list = productFamilySelect.getListDataView().getItems().collect(Collectors.toList());
        assertEquals(FdaConstants.FAS_PRODUCT_FAMILY, list.get(0));
        assertEquals(FdaConstants.CLA_FAS_PRODUCT_FAMILY, list.get(1));
    }

    private void verifyRefreshButton(Component component) {
        assertThat(component, instanceOf(Button.class));
        var button = (Button) component;
        assertEquals("button-refresh", button.getClassName());
        assertEquals("button-refresh", button.getId().orElseThrow());
        assertNotNull(button.getIcon());
        assertEquals(StringUtils.EMPTY, button.getText());
        assertEquals("Refresh", button.getTooltip().getText());
    }
}
