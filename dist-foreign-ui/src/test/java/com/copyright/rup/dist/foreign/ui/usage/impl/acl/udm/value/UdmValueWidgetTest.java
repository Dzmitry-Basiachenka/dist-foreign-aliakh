package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFooterItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyMenuBar;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow.IListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableList;
import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.components.grid.MultiSelectionModelImpl;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
@PrepareForTest(
    {UdmValueWidget.class, ForeignSecurityUtils.class, Windows.class, RupContextUtils.class, JavaScript.class})
public class UdmValueWidgetTest {

    private static final int DOUBLE_CLICK = 0x00002;
    private static final String USER = "user@copyright.com";
    private static final String UNCHECKED = "unchecked";
    private static final int UDM_RECORD_THRESHOLD = 10000;
    private static final int EXCEEDED_UDM_RECORD_THRESHOLD = 10001;
    private static final String INVALID_ASSIGNEE = "invalid_assignee";

    private final List<UdmValueDto> udmValues = loadExpectedUdmValueDto("udm_value_dto_43699543.json");
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
    public void testGridValues() {
        mockStatic(JavaScript.class);
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, Integer.MAX_VALUE, Collections.emptyList())).andReturn(udmValues).once();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.getUdmRecordThreshold()).andReturn(UDM_RECORD_THRESHOLD).once();
        setSpecialistExpectations();
        replay(controller, JavaScript.class, ForeignSecurityUtils.class, RupContextUtils.class);
        initWidget();
        Grid grid = (Grid) ((VerticalLayout) valueWidget.getSecondComponent()).getComponent(1);
        DataProvider dataProvider = grid.getDataProvider();
        dataProvider.refreshAll();
        Object[][] expectedCells = {
            {"43699543-3287-40e1-a4b8-553e7547deb9", 211012, UdmValueStatusEnum.RESEARCH_COMPLETE, "jjohn@copyright.com"
                , 100006859L, "West: A Thomson Business", 273337156L, "Bread and Butter", "1822-7773", 202712, "BK",
                "BK", "", "N", "", "http://google.com", "", "200.00", "USD", "Individual", "", 2021, "", "200.00", "Y",
                "1.00", "09/03/2022", "", "N", "", "Book", "", "23.1111", "Content comment", "Y", "150.00", "N", "",
                "Comment", "user@copyright.com", "09/05/2022"}
        };
        verifyGridItems(grid, udmValues, expectedCells);
        verify(controller, JavaScript.class, ForeignSecurityUtils.class, RupContextUtils.class);
        Object[][] expectedFooterColumns = {{"valueId", "Values Count: 1", null}};
        verifyFooterItems(grid, expectedFooterColumns);
    }

    @Test
    public void testWidgetStructure() {
        setSpecialistExpectations();
        replay(controller, ForeignSecurityUtils.class, RupContextUtils.class);
        initWidget();
        verify(controller, ForeignSecurityUtils.class, RupContextUtils.class);
        assertTrue(valueWidget.isLocked());
        assertEquals(270, valueWidget.getSplitPosition(), 0);
        verifySize(valueWidget, 100, 100, Unit.PERCENTAGE);
        assertThat(valueWidget.getFirstComponent(), instanceOf(UdmValueFilterWidget.class));
        Component secondComponent = valueWidget.getSecondComponent();
        assertThat(secondComponent, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0));
        verifyGrid((Grid) layout.getComponent(1));
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
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
        controller.assignValues(Collections.singleton(udmValueDto));
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
        udmValueDto.setStatus(UdmValueStatusEnum.RESEARCH_COMPLETE);
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
        controller.unassignValues(Collections.singleton(udmValueDto));
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

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAllCheckBoxVisible() {
        mockStatic(JavaScript.class);
        setSpecialistExpectations();
        List<UdmValueDto> udmValueDtos = ImmutableList.of(new UdmValueDto(), new UdmValueDto());
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, Collections.emptyList())).andReturn(udmValueDtos).once();
        expect(controller.getBeansCount()).andReturn(UDM_RECORD_THRESHOLD).once();
        expect(controller.getUdmRecordThreshold()).andReturn(UDM_RECORD_THRESHOLD).once();
        replay(controller, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
        initWidget();
        Grid<UdmValueDto> grid =
            (Grid<UdmValueDto>) ((VerticalLayout) valueWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(udmValueDtos, dataProvider.fetch(new Query<>(0, 2, Collections.emptyList(), null,
            null)).collect(Collectors.toList()));
        assertEquals(UDM_RECORD_THRESHOLD, dataProvider.size(new Query<>()));
        assertThat(grid.getSelectionModel(), instanceOf(MultiSelectionModelImpl.class));
        assertTrue(((MultiSelectionModelImpl<?>) grid.getSelectionModel()).isSelectAllCheckBoxVisible());
        verify(controller, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAllCheckBoxNotVisible() {
        mockStatic(JavaScript.class);
        setSpecialistExpectations();
        List<UdmValueDto> udmValueDtos = ImmutableList.of(new UdmValueDto(), new UdmValueDto());
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, Collections.emptyList())).andReturn(udmValueDtos).once();
        expect(controller.getBeansCount()).andReturn(EXCEEDED_UDM_RECORD_THRESHOLD).once();
        expect(controller.getUdmRecordThreshold()).andReturn(UDM_RECORD_THRESHOLD).once();
        replay(controller, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
        initWidget();
        Grid<UdmValueDto> grid =
            (Grid<UdmValueDto>) ((VerticalLayout) valueWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(udmValueDtos, dataProvider.fetch(new Query<>(0, 2, Collections.emptyList(), null,
            null)).collect(Collectors.toList()));
        assertEquals(EXCEEDED_UDM_RECORD_THRESHOLD, dataProvider.size(new Query<>()));
        assertThat(grid.getSelectionModel(), instanceOf(MultiSelectionModelImpl.class));
        assertFalse(((MultiSelectionModelImpl<?>) grid.getSelectionModel()).isSelectAllCheckBoxVisible());
        verify(controller, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAllCheckBoxNotVisibleWhenGridEmpty() {
        mockStatic(JavaScript.class);
        setSpecialistExpectations();
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, Collections.emptyList())).andReturn(Collections.emptyList()).once();
        expect(controller.getBeansCount()).andReturn(0).once();
        replay(controller, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
        initWidget();
        Grid<UdmValueDto> grid =
            (Grid<UdmValueDto>) ((VerticalLayout) valueWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(Collections.emptyList(), dataProvider.fetch(new Query<>(0, 2, Collections.emptyList(), null,
            null)).collect(Collectors.toList()));
        assertEquals(0, dataProvider.size(new Query<>()));
        assertThat(grid.getSelectionModel(), instanceOf(MultiSelectionModelImpl.class));
        assertFalse(((MultiSelectionModelImpl<?>) grid.getSelectionModel()).isSelectAllCheckBoxVisible());
        verify(controller, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
    }

    @Test
    public void testCalculateProxyValuesButtonClick() {
        mockStatic(Windows.class);
        setSpecialistExpectations();
        expect(controller.getPeriods()).andReturn(Collections.singletonList(202006)).once();
        Windows.showModalWindow(anyObject(UdmCalculateProxyValuesWindow.class));
        expectLastCall().once();
        replay(controller, Windows.class, ForeignSecurityUtils.class, RupContextUtils.class);
        initWidget();
        HorizontalLayout buttonsLayout =
            (HorizontalLayout) ((VerticalLayout) valueWidget.getSecondComponent()).getComponent(0);
        ((Button) buttonsLayout.getComponent(3)).click();
        verify(controller, Windows.class, ForeignSecurityUtils.class, RupContextUtils.class);
    }

    @Test
    public void testPublishButtonClick() {
        mockStatic(Windows.class);
        setSpecialistExpectations();
        expect(controller.getPeriods()).andReturn(Collections.singletonList(202006)).once();
        Windows.showModalWindow(anyObject(UdmPublishToBaselineWindow.class));
        expectLastCall().once();
        replay(controller, Windows.class, ForeignSecurityUtils.class, RupContextUtils.class);
        initWidget();
        HorizontalLayout buttonsLayout =
            (HorizontalLayout) ((VerticalLayout) valueWidget.getSecondComponent()).getComponent(0);
        ((Button) buttonsLayout.getComponent(3)).click();
        verify(controller, Windows.class, ForeignSecurityUtils.class, RupContextUtils.class);
    }

    @Test
    public void testViewValueWindowByDoubleClickValidAssignee() throws Exception {
        setViewOnlyExpectation();
        testViewWindowOnDoubleClick(USER);
    }

    @Test
    public void testViewValueWindowByDoubleClickInvalidAssignee() throws Exception {
        setViewOnlyExpectation();
        testViewWindowOnDoubleClick(INVALID_ASSIGNEE);
    }

    @Test
    public void testViewValueWindowByDoubleClickNullAssignee() throws Exception {
        setViewOnlyExpectation();
        testViewWindowOnDoubleClick(null);
    }

    @Test
    public void testViewValueWindowForResearcherByDoubleClickInvalidAssignee() throws Exception {
        setResearcherExpectations();
        testViewWindowOnDoubleClick(INVALID_ASSIGNEE);
    }

    @Test
    public void testViewValueWindowForResearcherByDoubleClickNullAssignee() throws Exception {
        setResearcherExpectations();
        testViewWindowOnDoubleClick(null);
    }

    @Test
    public void testDoubleClickListenerSpecialistAllowedValidAssignee() throws Exception {
        testDoubleClickListenerSpecialistAllowed(USER);
    }

    @Test
    public void testDoubleClickListenerSpecialistAllowedNullAssignee() throws Exception {
        testDoubleClickListenerSpecialistAllowed(null);
    }

    @Test
    public void testDoubleClickListenerSpecialistAllowedInvalidAssignee() throws Exception {
        testDoubleClickListenerSpecialistAllowed(INVALID_ASSIGNEE);
    }

    @Test
    public void testDoubleClickListenerManagerAllowedValidAssignee() throws Exception {
        testDoubleClickListenerManagerAllowed(USER);
    }

    @Test
    public void testDoubleClickListenerManagerAllowedNullAssignee() throws Exception {
        testDoubleClickListenerManagerAllowed(null);
    }

    @Test
    public void testDoubleClickListenerManagerAllowedInvalidAssignee() throws Exception {
        testDoubleClickListenerManagerAllowed(INVALID_ASSIGNEE);
    }

    @Test
    public void testDoubleClickListenerResearcherAllowedValidAssignee() throws Exception {
        setResearcherExpectations();
        testDoubleClickListenerAllowed(USER);
    }

    private void verifyGrid(Grid grid) {
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Value ID", "Value Period", "Status", "Assignee", "RH Account #", "RH Name",
            "Wr Wrk Inst", "System Title", "System Standard Number", "Last Value Period", "Last Pub Type", "Pub Type",
            "Last Price in USD", "Last Price Flag", "Last Price Source", "Price Source", "Last Price Comment", "Price",
            "Currency", "Price Type", "Price Access Type", "Price Year", "Price Comment", "Price in USD", "Price Flag",
            "Currency Exchange Rate", "Currency Exchange Rate Date", "Last Content", "Last Content Flag",
            "Last Content Source", "Content Source", "Last Content Comment", "Content", "Content Comment",
            "Content Flag", "Content Unit Price", "Content Unit Price Flag", "Last Comment", "Comment", "Updated By",
            "Updated Date"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        verifySize(grid, 100, 100, Unit.PERCENTAGE);
        assertThat(grid.getSelectionModel(), instanceOf(MultiSelectionModelImpl.class));
        assertTrue(grid.isFooterVisible());
        FooterRow footerRow = grid.getFooterRow(0);
        assertEquals("Values Count: 0", footerRow.getCell("valueId").getText());
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
        assertEquals(4, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Populate Value Batch");
        verifyMenuBar(layout.getComponent(1), "Assignment", true, Arrays.asList("Assign", "Unassign"));
        verifyButton(layout.getComponent(2), "Calculate Proxies");
        verifyButton(layout.getComponent(3), "Publish");
    }

    private void verifyButton(Component component, String name) {
        assertThat(component, instanceOf(Button.class));
        Button button = (Button) component;
        assertEquals(name, button.getCaption());
        assertTrue(button.isVisible());
    }

    private UdmValueDto buildUdmValueDto(String valueId, String assignee) {
        UdmValueDto udmValueDto = new UdmValueDto();
        udmValueDto.setId(valueId);
        udmValueDto.setAssignee(assignee);
        return udmValueDto;
    }

    private void setSpecialistExpectations() {
        setPermissionsExpectations(true, false, false);
    }

    private void setManagerExpectations() {
        setPermissionsExpectations(false, true, false);
    }

    private void setResearcherExpectations() {
        setPermissionsExpectations(false, false, true);
    }

    private void setViewOnlyExpectation() {
        setPermissionsExpectations(false, false, false);
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

    private MouseEventDetails createMouseEvent() {
        MouseEventDetails mouseEventDetails = new MouseEventDetails();
        mouseEventDetails.setType(DOUBLE_CLICK);
        mouseEventDetails.setButton(MouseButton.LEFT);
        return mouseEventDetails;
    }

    private void testDoubleClickListenerSpecialistAllowed(String user) throws Exception {
        setSpecialistExpectations();
        testDoubleClickListenerAllowed(user);
    }

    private void testDoubleClickListenerManagerAllowed(String user) throws Exception {
        setManagerExpectations();
        testDoubleClickListenerAllowed(user);
    }

    @SuppressWarnings(UNCHECKED)
    private void testDoubleClickListenerAllowed(String user) throws Exception {
        mockStatic(Windows.class);
        UdmValueDto udmValueDto = buildUdmValueDto("c219bbe1-4dde-41aa-9d9a-31563997ab7d", user);
        UdmEditValueWindow mockWindow = createMock(UdmEditValueWindow.class);
        expectNew(UdmEditValueWindow.class, eq(controller), eq(udmValueDto), anyObject(ClickListener.class))
            .andReturn(mockWindow).once();
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(controller, Windows.class, ForeignSecurityUtils.class, UdmEditValueWindow.class, RupContextUtils.class);
        initWidget();
        Grid<UdmValueDto> grid =
            (Grid<UdmValueDto>) ((VerticalLayout) valueWidget.getSecondComponent()).getComponent(1);
        ItemClickListener<UdmValueDto> listener =
            (ItemClickListener) new ArrayList<>(grid.getListeners(ItemClick.class)).get(0);
        Grid.ItemClick<UdmValueDto> usageDtoItemClick =
            new ItemClick<>(grid, grid.getColumns().get(0), udmValueDto, createMouseEvent(), 0);
        listener.itemClick(usageDtoItemClick);
        verify(controller, Windows.class, ForeignSecurityUtils.class, UdmEditValueWindow.class, RupContextUtils.class);
    }

    @SuppressWarnings(UNCHECKED)
    private void testViewWindowOnDoubleClick(String assignee) throws Exception {
        mockStatic(Windows.class);
        UdmValueDto udmValueDto = buildUdmValueDto("121e005a-3fc0-4f65-bc91-1ec3932a86c8", assignee);
        UdmViewValueWindow mockWindow = createMock(UdmViewValueWindow.class);
        expectNew(UdmViewValueWindow.class, eq(controller), eq(udmValueDto)).andReturn(mockWindow).once();
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(controller, Windows.class, ForeignSecurityUtils.class, UdmViewValueWindow.class, RupContextUtils.class);
        initWidget();
        Grid<UdmValueDto> grid =
            (Grid<UdmValueDto>) ((VerticalLayout) valueWidget.getSecondComponent()).getComponent(1);
        ItemClickListener<UdmValueDto> listener =
            (ItemClickListener) new ArrayList<>(grid.getListeners(ItemClick.class)).get(0);
        Grid.ItemClick<UdmValueDto> usageDtoItemClick =
            new ItemClick<>(grid, grid.getColumns().get(0), udmValueDto, createMouseEvent(), 0);
        listener.itemClick(usageDtoItemClick);
        verify(controller, Windows.class, ForeignSecurityUtils.class, UdmViewValueWindow.class, RupContextUtils.class);
    }

    private List<UdmValueDto> loadExpectedUdmValueDto(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<List<UdmValueDto>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
