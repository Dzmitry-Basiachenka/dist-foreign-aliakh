package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow.IListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.FooterRow;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Verifies {@link UdmValueWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ForeignSecurityUtils.class, Windows.class, RupContextUtils.class})
public class UdmValueWidgetTest {

    private static final String FORMATTED_PLUS_THOUSAND = "1,000.00";
    private static final String FORMATTED_MINUS_THOUSAND = "-1,000.00";
    private static final String USER = "user@copyright.com";
    private static final String UNCHECKED = "unchecked";

    private IUdmValueController controller;
    private UdmValueWidget valueWidget;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        mockStatic(RupContextUtils.class);
        controller = createMock(IUdmValueController.class);
        UdmValueFilterWidget filterWidget = new UdmValueFilterWidget(createMock(IUdmValueFilterController.class));
        expect(controller.initValuesFilterWidget()).andReturn(filterWidget).once();
        expect(RupContextUtils.getUserName()).andReturn(USER).once();
    }

    @Test
    public void testWidgetStructure() {
        setSpecialistExpectations();
        replay(controller, ForeignSecurityUtils.class, RupContextUtils.class);
        initWidget();
        verify(controller, ForeignSecurityUtils.class, RupContextUtils.class);
        assertTrue(valueWidget.isLocked());
        assertEquals(200, valueWidget.getSplitPosition(), 0);
        verifySize(valueWidget, 100, 100, Unit.PERCENTAGE);
        assertTrue(valueWidget.getFirstComponent() instanceof UdmValueFilterWidget);
        Component secondComponent = valueWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0));
        verifyGrid((Grid) layout.getComponent(1));
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    public void testFormatAmount() {
        setSpecialistExpectations();
        replay(controller, ForeignSecurityUtils.class, RupContextUtils.class);
        initWidget();
        verify(controller, ForeignSecurityUtils.class, RupContextUtils.class);
        assertEquals(FORMATTED_PLUS_THOUSAND, valueWidget.formatAmount(new BigDecimal("1000")));
        assertEquals(FORMATTED_PLUS_THOUSAND, valueWidget.formatAmount(new BigDecimal("1000.")));
        assertEquals(FORMATTED_PLUS_THOUSAND, valueWidget.formatAmount(new BigDecimal("1000.0")));
        assertEquals(FORMATTED_PLUS_THOUSAND, valueWidget.formatAmount(new BigDecimal("1000.00")));
        assertEquals(FORMATTED_PLUS_THOUSAND, valueWidget.formatAmount(new BigDecimal("1000.000")));
        assertEquals("1,000.10", valueWidget.formatAmount(new BigDecimal("1000.100")));
        assertEquals("1,000.01", valueWidget.formatAmount(new BigDecimal("1000.010")));
        assertEquals("1,000.001", valueWidget.formatAmount(new BigDecimal("1000.001")));
        assertEquals("1,000.0001", valueWidget.formatAmount(new BigDecimal("1000.0001")));
        assertEquals("1,000.00001", valueWidget.formatAmount(new BigDecimal("1000.00001")));
        assertEquals("1,000.000001", valueWidget.formatAmount(new BigDecimal("1000.000001")));
        assertEquals("1,000.0000001", valueWidget.formatAmount(new BigDecimal("1000.0000001")));
        assertEquals("1,000.00000001", valueWidget.formatAmount(new BigDecimal("1000.00000001")));
        assertEquals("1,000.000000001", valueWidget.formatAmount(new BigDecimal("1000.000000001")));
        assertEquals("1,000.0000000001", valueWidget.formatAmount(new BigDecimal("1000.0000000001")));
        assertEquals(FORMATTED_MINUS_THOUSAND, valueWidget.formatAmount(new BigDecimal("-1000")));
        assertEquals(FORMATTED_MINUS_THOUSAND, valueWidget.formatAmount(new BigDecimal("-1000.")));
        assertEquals(FORMATTED_MINUS_THOUSAND, valueWidget.formatAmount(new BigDecimal("-1000.0")));
        assertEquals(FORMATTED_MINUS_THOUSAND, valueWidget.formatAmount(new BigDecimal("-1000.00")));
        assertEquals(FORMATTED_MINUS_THOUSAND, valueWidget.formatAmount(new BigDecimal("-1000.000")));
        assertEquals("-1,000.10", valueWidget.formatAmount(new BigDecimal("-1000.100")));
        assertEquals("-1,000.01", valueWidget.formatAmount(new BigDecimal("-1000.010")));
        assertEquals("-1,000.001", valueWidget.formatAmount(new BigDecimal("-1000.001")));
        assertEquals("-1,000.0001", valueWidget.formatAmount(new BigDecimal("-1000.0001")));
        assertEquals("-1,000.00001", valueWidget.formatAmount(new BigDecimal("-1000.00001")));
        assertEquals("-1,000.000001", valueWidget.formatAmount(new BigDecimal("-1000.000001")));
        assertEquals("-1,000.0000001", valueWidget.formatAmount(new BigDecimal("-1000.0000001")));
        assertEquals("-1,000.00000001", valueWidget.formatAmount(new BigDecimal("-1000.00000001")));
        assertEquals("-1,000.000000001", valueWidget.formatAmount(new BigDecimal("-1000.000000001")));
        assertEquals("-1,000.0000000001", valueWidget.formatAmount(new BigDecimal("-1000.0000000001")));
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAssignMenuItem() {
        mockStatic(Windows.class);
        Window confirmWindowMock = createMock(Window.class);
        UdmValueDto udmValueDto = new UdmValueDto();
        udmValueDto.setId("a2f7d9a7-9665-4fb2-b3c1-1c87fe95bc8f");
        Capture<IListener> windowListenerCapture = newCapture();
        setSpecialistExpectations();
        expect(Windows.showConfirmDialog(eq("Are you sure that you want to assign 1 selected value(s) to yourself?"),
            capture(windowListenerCapture)))
            .andReturn(confirmWindowMock)
            .once();
        controller.assignValues(Collections.singleton("a2f7d9a7-9665-4fb2-b3c1-1c87fe95bc8f"));
        expectLastCall().once();
        Windows.showNotificationWindow("1 value(s) were successfully assigned to you");
        expectLastCall().once();
        replay(controller, confirmWindowMock, Windows.class, ForeignSecurityUtils.class, RupContextUtils.class);
        initWidget();
        Grid<UdmValueDto> grid =
            (Grid<UdmValueDto>) ((VerticalLayout) valueWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmValueDto);
        grid.select(udmValueDto);
        List<MenuBar.MenuItem> menuItems = getMenuBarItems(1);
        assertEquals(2, CollectionUtils.size(menuItems));
        MenuBar.MenuItem menuItemAssign = menuItems.get(0);
        menuItemAssign.getCommand().menuSelected(menuItemAssign);
        windowListenerCapture.getValue().onActionConfirmed();
        verify(controller, confirmWindowMock, Windows.class, ForeignSecurityUtils.class, RupContextUtils.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectedValuesNotAllowedForResearcher() {
        mockStatic(Windows.class);
        UdmValueDto udmValueDto = new UdmValueDto();
        udmValueDto.setId("861b9893-7797-47df-8e17-730e6d2da334");
        udmValueDto.setStatus(UdmValueStatusEnum.PUBLISHED);
        setResearcherExpectations();
        Windows.showNotificationWindow("You can assign only UDM values in statuses NEW, RSCHD_IN_THE_PREV_PERIOD");
        expectLastCall().once();
        replay(controller, Windows.class, ForeignSecurityUtils.class, RupContextUtils.class);
        initWidget();
        Grid<UdmValueDto> grid =
            (Grid<UdmValueDto>) ((VerticalLayout) valueWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmValueDto);
        grid.select(udmValueDto);
        List<MenuBar.MenuItem> menuItems = getMenuBarItems(1);
        assertEquals(2, CollectionUtils.size(menuItems));
        MenuBar.MenuItem menuItemAssign = menuItems.get(0);
        menuItemAssign.getCommand().menuSelected(menuItemAssign);
        verify(controller, Windows.class, ForeignSecurityUtils.class, RupContextUtils.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectUnassignMenuItem() {
        mockStatic(Windows.class);
        Window confirmWindowMock = createMock(Window.class);
        UdmValueDto udmValueDto = buildUdmValueDto("829ae9e7-38e4-48bc-a308-852ae0bf3888", USER);
        Capture<ConfirmDialogWindow.IListener> windowListenerCapture = newCapture();
        setSpecialistExpectations();
        expect(Windows.showConfirmDialog(eq("Are you sure that you want to unassign 1 selected value(s)?"),
            capture(windowListenerCapture)))
            .andReturn(confirmWindowMock)
            .once();
        controller.unassignValues(Collections.singleton("829ae9e7-38e4-48bc-a308-852ae0bf3888"));
        expectLastCall().once();
        Windows.showNotificationWindow("1 value(s) were successfully unassigned");
        expectLastCall().once();
        replay(controller, confirmWindowMock, Windows.class, ForeignSecurityUtils.class, RupContextUtils.class);
        initWidget();
        Grid<UdmValueDto> grid =
            (Grid<UdmValueDto>) ((VerticalLayout) valueWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmValueDto);
        grid.select(udmValueDto);
        List<MenuBar.MenuItem> menuItems = getMenuBarItems(1);
        assertEquals(2, CollectionUtils.size(menuItems));
        MenuBar.MenuItem menuItemUnassign = menuItems.get(1);
        menuItemUnassign.getCommand().menuSelected(menuItemUnassign);
        windowListenerCapture.getValue().onActionConfirmed();
        verify(controller, confirmWindowMock, Windows.class, ForeignSecurityUtils.class, RupContextUtils.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectUnassignMenuItemNotAllowed() {
        mockStatic(Windows.class);
        Window confirmWindowMock = createMock(Window.class);
        UdmValueDto udmValueDto1 = buildUdmValueDto("5080963b-c4f2-4f42-96ef-6dec2e20ee1f", USER);
        UdmValueDto udmValueDto2 = buildUdmValueDto("7947cf0c-b987-4ae9-92cc-0277ee281e3e", "jjohn@copyright.com");
        setSpecialistExpectations();
        Windows.showNotificationWindow("Only values that are assigned to you can be unassigned");
        expectLastCall().once();
        replay(controller, confirmWindowMock, Windows.class, ForeignSecurityUtils.class, RupContextUtils.class);
        initWidget();
        Grid<UdmValueDto> grid =
            (Grid<UdmValueDto>) ((VerticalLayout) valueWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmValueDto1, udmValueDto2);
        grid.select(udmValueDto1);
        grid.select(udmValueDto2);
        List<MenuBar.MenuItem> menuItems = getMenuBarItems(1);
        assertEquals(2, CollectionUtils.size(menuItems));
        MenuBar.MenuItem menuItemUnassign = menuItems.get(1);
        menuItemUnassign.getCommand().menuSelected(menuItemUnassign);
        verify(controller, confirmWindowMock, Windows.class, ForeignSecurityUtils.class, RupContextUtils.class);
    }

    private void verifyGrid(Grid grid) {
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Value Period", "Status", "Assignee", "RH Account #", "RH Name", "Wr Wrk Inst",
            "System Title", "System Standard Number", "Standard Number Type", "Last Value Period", "Last Pub Type",
            "Pub Type", "Last Price in USD", "Last Price Flag", "Last Price Source", "Last Price Comment", "Price",
            "Currency", "Price Type", "Price Access Type", "Price Year", "Price Comment", "Price in USD", "Price Flag",
            "Currency Exchange Rate", "Currency Exchange Rate Date", "Last Content", "Last Content Flag",
            "Last Content Source", "Last Content Comment", "Content", "Content Comment", "Content Flag",
            "Content Unit Price", "Comment", "Updated By", "Updated Date"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        verifySize(grid, 100, 100, Unit.PERCENTAGE);
        assertTrue(grid.isFooterVisible());
        FooterRow footerRow = grid.getFooterRow(0);
        assertEquals("Values Count: 0", footerRow.getCell("valuePeriod").getText());
    }

    private void verifySize(Component component, float width, float height, Unit heightUnit) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnit, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(3, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Populate Value Batch");
        verifyMenuBar(layout.getComponent(1), "Assignment", Arrays.asList("Assign", "Unassign"));
        verifyButton(layout.getComponent(2), "Edit Value");
    }

    private void verifyButton(Component component, String name) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals(name, button.getCaption());
        assertTrue(button.isVisible());
    }

    private void verifyMenuBar(Component component, String menuBarName, List<String> menuItems) {
        assertTrue(component instanceof MenuBar);
        MenuBar menuBar = (MenuBar) component;
        assertTrue(menuBar.isVisible());
        List<MenuBar.MenuItem> parentItems = menuBar.getItems();
        assertEquals(1, parentItems.size());
        MenuBar.MenuItem item = parentItems.get(0);
        assertEquals(menuBarName, item.getText());
        List<MenuBar.MenuItem> childItems = item.getChildren();
        assertEquals(CollectionUtils.size(menuItems), CollectionUtils.size(childItems));
        IntStream.range(0, menuItems.size())
            .forEach(index -> assertEquals(menuItems.get(index), childItems.get(index).getText()));
    }

    private UdmValueDto buildUdmValueDto(String valueId, String user) {
        UdmValueDto udmValueDto = new UdmValueDto();
        udmValueDto.setId(valueId);
        udmValueDto.setAssignee(user);
        return udmValueDto;
    }

    private void setSpecialistExpectations() {
        setPermissionsExpectations(true, false, false);
    }

    private void setResearcherExpectations() {
        setPermissionsExpectations(false, false, true);
    }

    private void setPermissionsExpectations(boolean isSpecialist, boolean isManager, boolean isResearcher) {
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andStubReturn(isSpecialist);
        expect(ForeignSecurityUtils.hasManagerPermission()).andStubReturn(isManager);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andStubReturn(isResearcher);
    }

    private void initWidget() {
        valueWidget = new UdmValueWidget();
        valueWidget.setController(controller);
        valueWidget.init();
        valueWidget.initMediator().applyPermissions();
    }

    private List<MenuBar.MenuItem> getMenuBarItems(int menuBarIndex) {
        HorizontalLayout buttonBar = (HorizontalLayout) (((VerticalLayout)
            valueWidget.getSecondComponent()).getComponent(0));
        MenuBar menuBar = (MenuBar) buttonBar.getComponent(menuBarIndex);
        return menuBar.getItems().get(0).getChildren();
    }
}
