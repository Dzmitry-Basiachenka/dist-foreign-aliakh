package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.TextField;
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
import org.powermock.reflect.Whitebox;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Verifies {@link UdmUsageWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/28/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({UdmUsageWidget.class, ForeignSecurityUtils.class, Windows.class})
public class UdmUsageWidgetTest {

    private static final List<String> VISIBLE_COLUMNS_FOR_RESEARCHER =
        Arrays.asList("Detail ID", "Period", "Usage Detail ID", "Detail Status", "Assignee", "RH Account #",
            "RH Name", "Wr Wrk Inst", "Reported Title", "System Title", "Reported Standard Number",
            "Standard Number", "Reported Pub Type", "Publication Format", "Article", "Language", "Action Reason",
            "Comment", "Research URL", "Det LC ID", "Det LC Name", "Channel", "Usage Date", "Survey Start Date",
            "Survey End Date", "Reported TOU", "Ineligible Reason", "Load Date", "Updated By", "Updated Date");
    private static final List<String> VISIBLE_COLUMNS_FOR_MANAGER_AND_SPECIALIST =
        Arrays.asList("Detail ID", "Period", "Usage Origin", "Usage Detail ID", "Detail Status", "Assignee",
            "RH Account #", "RH Name", "Wr Wrk Inst", "Reported Title", "System Title", "Reported Standard Number",
            "Standard Number", "Reported Pub Type", "Publication Format", "Article", "Language", "Action Reason",
            "Comment", "Research URL", "Det LC ID", "Det LC Name", "Company ID", "Company Name", "Survey Respondent",
            "IP Address", "Survey Country", "Channel", "Usage Date", "Survey Start Date", "Survey End Date",
            "Annual Multiplier", "Statistical Multiplier", "Reported TOU", "Quantity", "Annualized Copies",
            "Ineligible Reason", "Load Date", "Updated By", "Updated Date");
    private static final List<String> VISIBLE_COLUMNS_FOR_VIEW_ONLY =
        Arrays.asList("Detail ID", "Period", "Usage Origin", "Usage Detail ID", "Detail Status", "Assignee",
            "RH Account #", "RH Name", "Wr Wrk Inst", "Reported Title", "System Title", "Reported Standard Number",
            "Standard Number", "Reported Pub Type", "Publication Format", "Article", "Language", "Action Reason",
            "Comment", "Research URL", "Det LC ID", "Det LC Name", "Company ID", "Company Name", "Survey Respondent",
            "Survey Country", "Channel", "Usage Date", "Survey Start Date", "Survey End Date", "Annual Multiplier",
            "Statistical Multiplier", "Reported TOU", "Quantity", "Annualized Copies", "Ineligible Reason", "Load Date",
            "Updated By", "Updated Date");
    private static final String SEARCH_PLACEHOLDER =
        "Enter Reported/System Title or Usage Detail ID or Standard Number or Article or Survey Respondent or Comment";
    private static final String SEARCH_PLACEHOLDER_RESEARCHER =
        "Enter Reported/System Title or Usage Detail ID or Standard Number or Article or Comment";
    private UdmUsageWidget usagesWidget;
    private IUdmUsageController controller;
    private IStreamSource streamSource;

    @Before
    public void setUp() {
        controller = createMock(IUdmUsageController.class);
        UdmUsageFilterWidget filterWidget = new UdmUsageFilterWidget(createMock(IUdmUsageFilterController.class));
        expect(controller.initUsagesFilterWidget()).andReturn(filterWidget).once();
        mockStatic(ForeignSecurityUtils.class);
        streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
    }

    @Test
    public void testWidgetStructureForSpecialist() {
        expect(ForeignSecurityUtils.hasResearcherPermission()).andStubReturn(false);
        expect(ForeignSecurityUtils.hasManagerPermission()).andStubReturn(false);
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andStubReturn(true);
        expect(controller.getExportUdmUsagesStreamSourceSpecialistManagerRoles()).andReturn(streamSource).once();
        replay(controller, ForeignSecurityUtils.class, streamSource);
        usagesWidget = new UdmUsageWidget();
        usagesWidget.setController(controller);
        usagesWidget.init();
        usagesWidget.initMediator().applyPermissions();
        verify(controller, ForeignSecurityUtils.class, streamSource);
        assertTrue(usagesWidget.isLocked());
        assertEquals(200, usagesWidget.getSplitPosition(), 0);
        verifySize(usagesWidget, 100, 100, Unit.PERCENTAGE);
        assertTrue(usagesWidget.getFirstComponent() instanceof UdmUsageFilterWidget);
        Component secondComponent = usagesWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyToolbarLayout(layout.getComponent(0), SEARCH_PLACEHOLDER, true, true, true, true);
        verifyGrid((Grid) layout.getComponent(1), VISIBLE_COLUMNS_FOR_MANAGER_AND_SPECIALIST);
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    public void testWidgetStructureForManager() {
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(false).once();
        expect(ForeignSecurityUtils.hasManagerPermission()).andReturn(true).times(2);
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(false).times(2);
        expect(controller.getExportUdmUsagesStreamSourceSpecialistManagerRoles()).andReturn(streamSource).once();
        replay(controller, ForeignSecurityUtils.class, streamSource);
        usagesWidget = new UdmUsageWidget();
        usagesWidget.setController(controller);
        usagesWidget.init();
        usagesWidget.initMediator().applyPermissions();
        verify(controller, ForeignSecurityUtils.class, streamSource);
        assertTrue(usagesWidget.isLocked());
        assertEquals(200, usagesWidget.getSplitPosition(), 0);
        verifySize(usagesWidget, 100, 100, Unit.PERCENTAGE);
        assertTrue(usagesWidget.getFirstComponent() instanceof UdmUsageFilterWidget);
        Component secondComponent = usagesWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyToolbarLayout(layout.getComponent(0), SEARCH_PLACEHOLDER, false, true, true, true);
        verifyGrid((Grid) layout.getComponent(1), VISIBLE_COLUMNS_FOR_MANAGER_AND_SPECIALIST);
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    public void testWidgetStructureForViewOnly() {
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(false).times(2);
        expect(ForeignSecurityUtils.hasManagerPermission()).andReturn(false).times(2);
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(false).times(2);
        expect(controller.getExportUdmUsagesStreamSourceViewRole()).andReturn(streamSource).once();
        replay(controller, ForeignSecurityUtils.class, streamSource);
        usagesWidget = new UdmUsageWidget();
        usagesWidget.setController(controller);
        usagesWidget.init();
        usagesWidget.initMediator().applyPermissions();
        verify(controller, ForeignSecurityUtils.class, streamSource);
        assertTrue(usagesWidget.isLocked());
        assertEquals(200, usagesWidget.getSplitPosition(), 0);
        verifySize(usagesWidget, 100, 100, Unit.PERCENTAGE);
        assertTrue(usagesWidget.getFirstComponent() instanceof UdmUsageFilterWidget);
        Component secondComponent = usagesWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyToolbarLayout(layout.getComponent(0), SEARCH_PLACEHOLDER, false, false, false, true);
        verifyGrid((Grid) layout.getComponent(1), VISIBLE_COLUMNS_FOR_VIEW_ONLY);
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    public void testWidgetStructureForResearcher() {
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(true).times(2);
        expect(ForeignSecurityUtils.hasManagerPermission()).andReturn(false).times(2);
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(false).times(2);
        expect(controller.getExportUdmUsagesStreamSourceResearcherRole()).andReturn(streamSource).once();
        replay(controller, ForeignSecurityUtils.class, streamSource);
        usagesWidget = new UdmUsageWidget();
        usagesWidget.setController(controller);
        usagesWidget.init();
        usagesWidget.initMediator().applyPermissions();
        verify(controller, ForeignSecurityUtils.class, streamSource);
        assertTrue(usagesWidget.isLocked());
        assertEquals(200, usagesWidget.getSplitPosition(), 0);
        verifySize(usagesWidget, 100, 100, Unit.PERCENTAGE);
        assertTrue(usagesWidget.getFirstComponent() instanceof UdmUsageFilterWidget);
        Component secondComponent = usagesWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyToolbarLayout(layout.getComponent(0), SEARCH_PLACEHOLDER_RESEARCHER, false, true, false, true);
        verifyGrid((Grid) layout.getComponent(1), VISIBLE_COLUMNS_FOR_RESEARCHER);
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSelectAssignMenuItem() {
        mockStatic(Windows.class);
        Window confirmWindowMock = createMock(Window.class);
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setId("efdf6040-d130-4ae4-a6b1-a9a807873a1e");
        Capture<ConfirmDialogWindow.IListener> windowListenerCapture = newCapture();
        expect(ForeignSecurityUtils.hasResearcherPermission()).andStubReturn(false);
        expect(ForeignSecurityUtils.hasManagerPermission()).andStubReturn(false);
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andStubReturn(true);
        expect(controller.getExportUdmUsagesStreamSourceSpecialistManagerRoles()).andReturn(streamSource).once();
        expect(Windows.showConfirmDialog(eq("Are you sure that you want to assign 1 selected usage(s) to yourself?"),
            capture(windowListenerCapture)))
            .andReturn(confirmWindowMock)
            .once();
        controller.assignUsages(Collections.singleton("efdf6040-d130-4ae4-a6b1-a9a807873a1e"));
        expectLastCall().once();
        Windows.showNotificationWindow("1 usage(s) were successfully assigned to you");
        expectLastCall().once();
        replay(controller, streamSource, confirmWindowMock, Windows.class, ForeignSecurityUtils.class);
        usagesWidget = new UdmUsageWidget();
        usagesWidget.setController(controller);
        usagesWidget.init();
        usagesWidget.initMediator().applyPermissions();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmUsageDto);
        grid.select(udmUsageDto);
        List<MenuBar.MenuItem> menuItems = getMenuBarItems(1);
        assertEquals(2, CollectionUtils.size(menuItems));
        MenuBar.MenuItem menuItemAssign = menuItems.get(0);
        menuItemAssign.getCommand().menuSelected(menuItemAssign);
        windowListenerCapture.getValue().onActionConfirmed();
        verify(controller, streamSource, confirmWindowMock, Windows.class, ForeignSecurityUtils.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSelectUnassignMenuItem() {
        mockStatic(Windows.class);
        Window confirmWindowMock = createMock(Window.class);
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setId("afdf6040-d130-4ae4-a6b1-a9a807873a1e");
        Capture<ConfirmDialogWindow.IListener> windowListenerCapture = newCapture();
        expect(ForeignSecurityUtils.hasResearcherPermission()).andStubReturn(false);
        expect(ForeignSecurityUtils.hasManagerPermission()).andStubReturn(false);
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andStubReturn(true);
        expect(controller.getExportUdmUsagesStreamSourceSpecialistManagerRoles()).andReturn(streamSource).once();
        expect(Windows.showConfirmDialog(eq("Are you sure that you want to unassign 1 selected usage(s)?"),
            capture(windowListenerCapture)))
            .andReturn(confirmWindowMock)
            .once();
        controller.unassignUsages(Collections.singleton("afdf6040-d130-4ae4-a6b1-a9a807873a1e"));
        expectLastCall().once();
        Windows.showNotificationWindow("1 usage(s) were successfully unassigned");
        expectLastCall().once();
        replay(controller, streamSource, confirmWindowMock, Windows.class, ForeignSecurityUtils.class);
        usagesWidget = new UdmUsageWidget();
        usagesWidget.setController(controller);
        usagesWidget.init();
        usagesWidget.initMediator().applyPermissions();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmUsageDto);
        grid.select(udmUsageDto);
        List<MenuBar.MenuItem> menuItems = getMenuBarItems(1);
        assertEquals(2, CollectionUtils.size(menuItems));
        MenuBar.MenuItem menuItemUnassign = menuItems.get(1);
        menuItemUnassign.getCommand().menuSelected(menuItemUnassign);
        windowListenerCapture.getValue().onActionConfirmed();
        verify(controller, streamSource, confirmWindowMock, Windows.class, ForeignSecurityUtils.class);
    }

    @Test
    public void testEditButtonClickListener() throws Exception {
        mockStatic(Windows.class);
        createMock(UdmEditUsageWindow.class);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andStubReturn(false);
        expect(ForeignSecurityUtils.hasManagerPermission()).andStubReturn(false);
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andStubReturn(true);
        expect(controller.getExportUdmUsagesStreamSourceSpecialistManagerRoles()).andReturn(streamSource).once();
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setId("afdf6040-d130-4ae4-a6b1-a9a807873a1e");
        UdmEditUsageWindow mockWindow = createMock(UdmEditUsageWindow.class);
        expectNew(UdmEditUsageWindow.class, controller, udmUsageDto).andReturn(mockWindow);
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(controller, streamSource, Windows.class, UdmEditUsageWindow.class, ForeignSecurityUtils.class);
        usagesWidget = new UdmUsageWidget();
        usagesWidget.setController(controller);
        usagesWidget.init();
        usagesWidget.initMediator().applyPermissions();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmUsageDto);
        grid.select(udmUsageDto);
        Button editButton = (Button) getButtonsLayout().getComponent(2);
        editButton.click();
        verify(controller, streamSource, Windows.class, UdmEditUsageWindow.class, ForeignSecurityUtils.class);
    }

    private void verifyToolbarLayout(Component component, String searchPlaceholder, boolean... buttonsVisibility) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isSpacing());
        verifySize(layout, 100, -1, Unit.PIXELS);
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0), buttonsVisibility);
        verifySearchWidget(layout.getComponent(1), searchPlaceholder);
    }

    private void verifySearchWidget(Component component, String searchPlaceholder) {
        assertTrue(component instanceof SearchWidget);
        SearchWidget searchWidget = (SearchWidget) component;
        verifySize(searchWidget, 65, -1, Unit.PIXELS);
        assertEquals(searchPlaceholder, Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
    }

    private void verifyButtonsLayout(HorizontalLayout layout, boolean... buttonsVisibility) {
        assertTrue(layout.isSpacing());
        assertEquals(new MarginInfo(false), layout.getMargin());
        assertEquals(4, layout.getComponentCount());
        verifyMenuBar(layout.getComponent(0), "UDM Batch", buttonsVisibility[0], Arrays.asList("Load", "View"));
        verifyMenuBar(layout.getComponent(1), "Assignment", buttonsVisibility[1], Arrays.asList("Assign", "Unassign"));
        verifyButton(layout.getComponent(2), "Edit Usage", buttonsVisibility[2]);
        verifyButton(layout.getComponent(3), "Export", buttonsVisibility[3]);
    }

    private void verifyButton(Component component, String name, boolean isVisible) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals(name, button.getCaption());
        assertEquals(isVisible, button.isVisible());
    }

    private void verifyMenuBar(Component component, String menuBarName, boolean isVisible, List<String> menuItems) {
        assertTrue(component instanceof MenuBar);
        MenuBar menuBar = (MenuBar) component;
        assertEquals(isVisible, menuBar.isVisible());
        List<MenuBar.MenuItem> parentItems = menuBar.getItems();
        assertEquals(1, parentItems.size());
        MenuBar.MenuItem item = parentItems.get(0);
        assertEquals(menuBarName, item.getText());
        List<MenuBar.MenuItem> childItems = item.getChildren();
        assertEquals(CollectionUtils.size(menuItems), CollectionUtils.size(childItems));
        IntStream.range(0, menuItems.size())
            .forEach(index -> assertEquals(menuItems.get(index), childItems.get(index).getText()));
    }

    private void verifyGrid(Grid grid, List<String> expectedColumns) {
        List<Column> columns = grid.getColumns();
        assertEquals(expectedColumns, columns.stream()
            .filter(column -> !column.isHidden())
            .map(Column::getCaption)
            .collect(Collectors.toList()));
        verifySize(grid, 100, 100, Unit.PERCENTAGE);
        assertTrue(grid.isFooterVisible());
        FooterRow footerRow = grid.getFooterRow(0);
        assertEquals("Usages Count: 0", footerRow.getCell("detailId").getText());
    }

    private void verifySize(Component component, float width, float height, Unit heightUnit) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnit, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private List<MenuBar.MenuItem> getMenuBarItems(int menuBarIndex) {
        HorizontalLayout buttonsBar = getButtonsLayout();
        MenuBar menuBar = (MenuBar) buttonsBar.getComponent(menuBarIndex);
        return menuBar.getItems().get(0).getChildren();
    }

    private HorizontalLayout getButtonsLayout() {
        return (HorizontalLayout) ((HorizontalLayout) ((VerticalLayout)
            usagesWidget.getSecondComponent()).getComponent(0)).getComponent(0);
    }
}
