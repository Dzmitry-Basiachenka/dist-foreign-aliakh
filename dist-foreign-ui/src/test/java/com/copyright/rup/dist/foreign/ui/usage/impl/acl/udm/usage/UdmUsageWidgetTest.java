package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.google.common.collect.ImmutableList;
import com.vaadin.data.provider.CallbackDataProvider;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.components.grid.MultiSelectionModelImpl;
import com.vaadin.ui.components.grid.SingleSelectionModel;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
@PrepareForTest({UdmUsageWidget.class, ForeignSecurityUtils.class, Windows.class, RupContextUtils.class,
    JavaScript.class})
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
    private static final String USER = "user@copyright.com";
    private static final String UNCHECKED = "unchecked";
    private static final int UDM_RECORD_THRESHOLD = 10000;
    private static final int EXCEEDED_UDM_RECORD_THRESHOLD = 10001;
    private static final int DOUBLE_CLICK = 0x00002;
    private static final String INVALID_ASSIGNEE = "invalid_assignee";
    private static final String USAGE_PROCESSING_ERROR_MESSAGE = "Please wait while usage processing is completed";
    private static final String NON_BASELINE_ERROR_MESSAGE = "Only non baseline usages can be edited";
    private static final String RESEARCHER_STATUSES_ERROR_MESSAGE = "You can edit only UDM usages in statuses " +
        "WORK_NOT_FOUND, RH_NOT_FOUND, OPS_REVIEW";
    private static final String USAGE_NOT_EDITED_ERROR_MESSAGE  = "Selected UDM usage cannot be edited. Please " +
        "assign it to yourself first";
    private UdmUsageWidget usagesWidget;
    private IUdmUsageController controller;
    private IStreamSource streamSource;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        mockStatic(RupContextUtils.class);
        controller = createMock(IUdmUsageController.class);
        UdmUsageFilterWidget filterWidget = new UdmUsageFilterWidget(createMock(IUdmUsageFilterController.class));
        expect(controller.initUsagesFilterWidget()).andReturn(filterWidget).once();
        streamSource = createMock(IStreamSource.class);
        expect(RupContextUtils.getUserName()).andReturn(USER).once();
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
    }

    @Test
    public void testWidgetStructureForSpecialist() {
        setSpecialistExpectations();
        replay(controller, ForeignSecurityUtils.class, RupContextUtils.class, streamSource);
        initWidget();
        verify(controller, ForeignSecurityUtils.class, RupContextUtils.class, streamSource);
        assertTrue(usagesWidget.isLocked());
        assertEquals(200, usagesWidget.getSplitPosition(), 0);
        verifySize(usagesWidget, 100, 100, Unit.PERCENTAGE);
        assertTrue(usagesWidget.getFirstComponent() instanceof UdmUsageFilterWidget);
        Component secondComponent = usagesWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyToolbarLayout(layout.getComponent(0), SEARCH_PLACEHOLDER, true, true, true, true, true, true);
        Grid grid = (Grid) layout.getComponent(1);
        assertTrue(grid.getSelectionModel() instanceof MultiSelectionModelImpl);
        verifyGrid(grid, VISIBLE_COLUMNS_FOR_MANAGER_AND_SPECIALIST);
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    public void testWidgetStructureForManager() {
        setManagerExpectations();
        replay(controller, ForeignSecurityUtils.class, RupContextUtils.class, streamSource);
        initWidget();
        verify(controller, ForeignSecurityUtils.class, RupContextUtils.class, streamSource);
        assertTrue(usagesWidget.isLocked());
        assertEquals(200, usagesWidget.getSplitPosition(), 0);
        verifySize(usagesWidget, 100, 100, Unit.PERCENTAGE);
        assertTrue(usagesWidget.getFirstComponent() instanceof UdmUsageFilterWidget);
        Component secondComponent = usagesWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyToolbarLayout(layout.getComponent(0), SEARCH_PLACEHOLDER, false, true, true, true, false, true);
        Grid grid = (Grid) layout.getComponent(1);
        assertTrue(grid.getSelectionModel() instanceof MultiSelectionModelImpl);
        verifyGrid(grid, VISIBLE_COLUMNS_FOR_MANAGER_AND_SPECIALIST);
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    public void testWidgetStructureForViewOnly() {
        setViewOnlyExpectations();
        replay(controller, ForeignSecurityUtils.class, RupContextUtils.class, streamSource);
        initWidget();
        verify(controller, ForeignSecurityUtils.class, RupContextUtils.class, streamSource);
        assertTrue(usagesWidget.isLocked());
        assertEquals(200, usagesWidget.getSplitPosition(), 0);
        verifySize(usagesWidget, 100, 100, Unit.PERCENTAGE);
        assertTrue(usagesWidget.getFirstComponent() instanceof UdmUsageFilterWidget);
        Component secondComponent = usagesWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyToolbarLayout(layout.getComponent(0), SEARCH_PLACEHOLDER, false, false, false, false, false, true);
        Grid grid = (Grid) layout.getComponent(1);
        assertTrue(grid.getSelectionModel() instanceof SingleSelectionModel);
        verifyGrid(grid, VISIBLE_COLUMNS_FOR_VIEW_ONLY);
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    public void testWidgetStructureForResearcher() {
        setResearcherExpectations();
        replay(controller, ForeignSecurityUtils.class, RupContextUtils.class, streamSource);
        initWidget();
        verify(controller, ForeignSecurityUtils.class, RupContextUtils.class, streamSource);
        assertTrue(usagesWidget.isLocked());
        assertEquals(200, usagesWidget.getSplitPosition(), 0);
        verifySize(usagesWidget, 100, 100, Unit.PERCENTAGE);
        assertTrue(usagesWidget.getFirstComponent() instanceof UdmUsageFilterWidget);
        Component secondComponent = usagesWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyToolbarLayout(layout.getComponent(0), SEARCH_PLACEHOLDER_RESEARCHER, false, true, true, true, false,
            true);
        Grid grid = (Grid) layout.getComponent(1);
        assertTrue(grid.getSelectionModel() instanceof MultiSelectionModelImpl);
        verifyGrid(grid, VISIBLE_COLUMNS_FOR_RESEARCHER);
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAssignMenuItem() {
        mockStatic(Windows.class);
        Window confirmWindowMock = createMock(Window.class);
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setId("efdf6040-d130-4ae4-a6b1-a9a807873a1e");
        Capture<ConfirmDialogWindow.IListener> windowListenerCapture = newCapture();
        setSpecialistExpectations();
        expect(Windows.showConfirmDialog(eq("Are you sure that you want to assign 1 selected usage(s) to yourself?"),
            capture(windowListenerCapture)))
            .andReturn(confirmWindowMock)
            .once();
        controller.assignUsages(Collections.singleton(udmUsageDto));
        expectLastCall().once();
        Windows.showNotificationWindow("1 usage(s) were successfully assigned to you");
        expectLastCall().once();
        replay(controller, streamSource, confirmWindowMock, Windows.class, RupContextUtils.class,
            ForeignSecurityUtils.class);
        initWidget();
        List<MenuBar.MenuItem> menuItems = getMenuBarItems(1);
        assertEquals(2, CollectionUtils.size(menuItems));
        MenuBar.MenuItem menuItemAssign = menuItems.get(0);
        assertFalse(menuItemAssign.isEnabled());
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmUsageDto);
        grid.select(udmUsageDto);
        assertTrue(menuItemAssign.isEnabled());
        menuItemAssign.getCommand().menuSelected(menuItemAssign);
        windowListenerCapture.getValue().onActionConfirmed();
        verify(controller, streamSource, confirmWindowMock, Windows.class, RupContextUtils.class,
            ForeignSecurityUtils.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectUnassignMenuItem() {
        mockStatic(Windows.class);
        Window confirmWindowMock = createMock(Window.class);
        UdmUsageDto udmUsageDto = buildUdmUsageDto("27bdc476-9cd8-44e0-ac50-597819f93f9a", USER);
        Capture<ConfirmDialogWindow.IListener> windowListenerCapture = newCapture();
        setSpecialistExpectations();
        expect(Windows.showConfirmDialog(eq("Are you sure that you want to unassign 1 selected usage(s)?"),
            capture(windowListenerCapture)))
            .andReturn(confirmWindowMock)
            .once();
        controller.unassignUsages(Collections.singleton("27bdc476-9cd8-44e0-ac50-597819f93f9a"));
        expectLastCall().once();
        Windows.showNotificationWindow("1 usage(s) were successfully unassigned");
        expectLastCall().once();
        replay(controller, streamSource, confirmWindowMock, Windows.class, ForeignSecurityUtils.class,
            RupContextUtils.class);
        initWidget();
        List<MenuBar.MenuItem> menuItems = getMenuBarItems(1);
        assertEquals(2, CollectionUtils.size(menuItems));
        MenuBar.MenuItem menuItemUnassign = menuItems.get(1);
        assertFalse(menuItemUnassign.isEnabled());
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmUsageDto);
        grid.select(udmUsageDto);
        assertTrue(menuItemUnassign.isEnabled());
        menuItemUnassign.getCommand().menuSelected(menuItemUnassign);
        windowListenerCapture.getValue().onActionConfirmed();
        verify(controller, streamSource, confirmWindowMock, Windows.class, ForeignSecurityUtils.class,
            RupContextUtils.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectUnassignMenuItemNotAllowed() {
        mockStatic(Windows.class);
        Window confirmWindowMock = createMock(Window.class);
        UdmUsageDto udmUsageDto1 = buildUdmUsageDto("351ee998-1b0b-4f29-842f-2efb00cbead8", USER);
        UdmUsageDto udmUsageDto2 = buildUdmUsageDto("e2468b9e-f89c-480b-8f3c-c13ca1012cdb", "jjohn@copyright.com");
        setSpecialistExpectations();
        Windows.showNotificationWindow("Only usages that are assigned to you can be unassigned");
        expectLastCall().once();
        replay(controller, streamSource, confirmWindowMock, Windows.class, ForeignSecurityUtils.class,
            RupContextUtils.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmUsageDto1, udmUsageDto2);
        grid.select(udmUsageDto1);
        grid.select(udmUsageDto2);
        List<MenuBar.MenuItem> menuItems = getMenuBarItems(1);
        assertEquals(2, CollectionUtils.size(menuItems));
        MenuBar.MenuItem menuItemUnassign = menuItems.get(1);
        menuItemUnassign.getCommand().menuSelected(menuItemUnassign);
        verify(controller, streamSource, confirmWindowMock, Windows.class, ForeignSecurityUtils.class,
            RupContextUtils.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testIsUsageProcessingCompleted() {
        mockStatic(Windows.class);
        Window confirmWindowMock = createMock(Window.class);
        UdmUsageDto udmUsageDto = buildUdmUsageDto("351ee998-1b0b-4f29-842f-2efb00cbead8", USER);
        udmUsageDto.setStatus(UsageStatusEnum.NEW);
        udmUsageDto.setAssignee(USER);
        setSpecialistExpectations();
        Windows.showNotificationWindow(USAGE_PROCESSING_ERROR_MESSAGE);
        expectLastCall().once();
        replay(controller, streamSource, confirmWindowMock, Windows.class, ForeignSecurityUtils.class,
            RupContextUtils.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmUsageDto);
        grid.select(udmUsageDto);
        Button editButton = (Button) getButtonsLayout().getComponent(2);
        editButton.click();
        verify(controller, streamSource, confirmWindowMock, Windows.class, ForeignSecurityUtils.class,
            RupContextUtils.class);
    }

    @Test
    public void testEditButtonClickListenerSpecialistAllowedWorkNotFound() throws Exception {
        testEditButtonClickListenerSpecialistAllowed(UsageStatusEnum.WORK_NOT_FOUND, USER);
    }

    @Test
    public void testEditButtonClickListenerSpecialistNullAssigneeAllowed() throws Exception {
        testEditButtonClickListenerSpecialistAllowed(UsageStatusEnum.WORK_NOT_FOUND, null);
    }

    @Test
    public void testEditButtonClickListenerSpecialistInvalidAssigneeAllowed() throws Exception {
        testEditButtonClickListenerSpecialistAllowed(UsageStatusEnum.WORK_NOT_FOUND, INVALID_ASSIGNEE);
    }

    @Test
    public void testEditButtonClickListenerSpecialistAllowedRhNotFound() throws Exception {
        testEditButtonClickListenerSpecialistAllowed(UsageStatusEnum.RH_NOT_FOUND, USER);
    }

    @Test
    public void testEditButtonClickListenerSpecialistAllowedRhFound() throws Exception {
        testEditButtonClickListenerSpecialistAllowed(UsageStatusEnum.RH_FOUND, USER);
    }

    @Test
    public void testEditButtonClickListenerSpecialistAllowedEligible() throws Exception {
        testEditButtonClickListenerSpecialistAllowed(UsageStatusEnum.ELIGIBLE, USER);
    }

    @Test
    public void testEditButtonClickListenerSpecialistAllowedIneligible() throws Exception {
        testEditButtonClickListenerSpecialistAllowed(UsageStatusEnum.INELIGIBLE, USER);
    }

    @Test
    public void testEditButtonClickListenerSpecialistAllowedOpsReview() throws Exception {
        testEditButtonClickListenerSpecialistAllowed(UsageStatusEnum.OPS_REVIEW, USER);
    }

    @Test
    public void testEditButtonClickListenerSpecialistAllowedSpecialistReview() throws Exception {
        testEditButtonClickListenerSpecialistAllowed(UsageStatusEnum.SPECIALIST_REVIEW, USER);
    }

    @Test
    public void testEditButtonClickListenerManagerAllowedWorkNotFound() throws Exception {
        testEditButtonClickListenerManagerAllowed(UsageStatusEnum.WORK_NOT_FOUND, USER);
    }

    @Test
    public void testEditButtonClickListenerManagerNullAssigneeAllowed() throws Exception {
        testEditButtonClickListenerManagerAllowed(UsageStatusEnum.WORK_NOT_FOUND, null);
    }

    @Test
    public void testEditButtonClickListenerManagerInvalidAssigneeAllowed() throws Exception {
        testEditButtonClickListenerManagerAllowed(UsageStatusEnum.WORK_NOT_FOUND, INVALID_ASSIGNEE);
    }

    @Test
    public void testEditButtonClickListenerManagerAllowedRhNotFound() throws Exception {
        testEditButtonClickListenerManagerAllowed(UsageStatusEnum.RH_NOT_FOUND, USER);
    }

    @Test
    public void testEditButtonClickListenerManagerAllowedRhFound() throws Exception {
        testEditButtonClickListenerManagerAllowed(UsageStatusEnum.RH_FOUND, USER);
    }

    @Test
    public void testEditButtonClickListenerManagerAllowedEligible() throws Exception {
        testEditButtonClickListenerManagerAllowed(UsageStatusEnum.ELIGIBLE, USER);
    }

    @Test
    public void testEditButtonClickListenerManagerAllowedIneligible() throws Exception {
        testEditButtonClickListenerManagerAllowed(UsageStatusEnum.INELIGIBLE, USER);
    }

    @Test
    public void testEditButtonClickListenerManagerAllowedOpsReview() throws Exception {
        testEditButtonClickListenerManagerAllowed(UsageStatusEnum.OPS_REVIEW, USER);
    }

    @Test
    public void testEditButtonClickListenerManagerAllowedManagerReview() throws Exception {
        testEditButtonClickListenerManagerAllowed(UsageStatusEnum.SPECIALIST_REVIEW, USER);
    }

    @Test
    public void testEditButtonClickListenerResearcherAllowedWorkNotFound() throws Exception {
        testEditButtonClickListenerResearcherAllowed(UsageStatusEnum.WORK_NOT_FOUND);
    }

    @Test
    public void testEditButtonClickListenerResearcherAllowedRhNotFound() throws Exception {
        testEditButtonClickListenerResearcherAllowed(UsageStatusEnum.RH_NOT_FOUND);
    }

    @Test
    public void testEditButtonClickListenerResearcherForbiddenRhFound() {
        testEditButtonClickListenerResearcherForbidden(UsageStatusEnum.RH_FOUND);
    }

    @Test
    public void testEditButtonClickListenerResearcherForbiddenEligible() {
        testEditButtonClickListenerResearcherForbidden(UsageStatusEnum.ELIGIBLE);
    }

    @Test
    public void testEditButtonClickListenerResearcherForbiddenIneligible() {
        testEditButtonClickListenerResearcherForbidden(UsageStatusEnum.INELIGIBLE);
    }

    @Test
    public void testEditButtonClickListenerResearcherAllowedOpsReview() throws Exception {
        testEditButtonClickListenerResearcherAllowed(UsageStatusEnum.OPS_REVIEW);
    }

    @Test
    public void testEditButtonClickListenerResearcherForbiddenResearcherReview() {
        testEditButtonClickListenerResearcherForbidden(UsageStatusEnum.SPECIALIST_REVIEW);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testMultipleEditButtonClickListenerResearcherForbidden() {
        mockStatic(Windows.class);
        setResearcherExpectations();
        UdmUsageDto udmUsageDtoFirst = new UdmUsageDto();
        udmUsageDtoFirst.setId("188fead4-e929-4d68-bf05-113188f48a95");
        udmUsageDtoFirst.setAssignee(USER);
        udmUsageDtoFirst.setStatus(UsageStatusEnum.RH_FOUND);
        UdmUsageDto udmUsageDtoSecond = new UdmUsageDto();
        udmUsageDtoSecond.setId("cdda8071-c84c-497e-9b30-4ba447c86373");
        udmUsageDtoSecond.setAssignee(USER);
        udmUsageDtoSecond.setStatus(UsageStatusEnum.RH_NOT_FOUND);
        Set<UdmUsageDto> udmUsages = new HashSet<>(Arrays.asList(udmUsageDtoFirst, udmUsageDtoSecond));
        Windows.showNotificationWindow("You can edit only UDM usages in statuses WORK_NOT_FOUND, RH_NOT_FOUND, " +
            "OPS_REVIEW");
        expectLastCall().once();
        replay(controller, streamSource, Windows.class, RupContextUtils.class, ForeignSecurityUtils.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmUsages);
        grid.select(udmUsageDtoFirst);
        grid.select(udmUsageDtoSecond);
        Button editButton = (Button) getButtonsLayout().getComponent(3);
        editButton.click();
        verify(controller, streamSource, Windows.class, RupContextUtils.class, ForeignSecurityUtils.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testViewUsageWindowByDoubleClick() throws Exception {
        mockStatic(Windows.class);
        setViewOnlyExpectations();
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setId("121e005a-3fc0-4f65-bc91-1ec3932a86c8");
        udmUsageDto.setAssignee(USER);
        udmUsageDto.setStatus(UsageStatusEnum.RH_NOT_FOUND);
        UdmViewUsageWindow mockWindow = createMock(UdmViewUsageWindow.class);
        expectNew(UdmViewUsageWindow.class, eq(udmUsageDto)).andReturn(mockWindow).once();
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(controller, streamSource, Windows.class, UdmViewUsageWindow.class, RupContextUtils.class,
            ForeignSecurityUtils.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        ItemClickListener<UdmUsageDto> listener =
            (ItemClickListener) new ArrayList<>(grid.getListeners(ItemClick.class)).get(0);
        Grid.ItemClick<UdmUsageDto> usageDtoItemClick =
            new ItemClick<>(grid, grid.getColumns().get(0), udmUsageDto, createMouseEvent(), 0);
        listener.itemClick(usageDtoItemClick);
        verify(controller, streamSource, Windows.class, UdmViewUsageWindow.class, RupContextUtils.class,
            ForeignSecurityUtils.class);
    }

    @Test
    public void testMultipleEditButtonClickListenerResearcherAllowed() throws Exception {
        mockStatic(Windows.class);
        setResearcherExpectations();
        UdmUsageDto udmUsageDtoFirst = new UdmUsageDto();
        udmUsageDtoFirst.setId("121e005a-3fc0-4f65-bc91-1ec3932a86c8");
        udmUsageDtoFirst.setAssignee(USER);
        udmUsageDtoFirst.setStatus(UsageStatusEnum.RH_NOT_FOUND);
        UdmUsageDto udmUsageDtoSecond = new UdmUsageDto();
        udmUsageDtoSecond.setId("7a0df3f5-78c9-47ef-b027-9b19b88a7221");
        udmUsageDtoSecond.setAssignee(USER);
        udmUsageDtoSecond.setStatus(UsageStatusEnum.RH_NOT_FOUND);
        UdmEditMultipleUsagesResearcherWindow mockWindow = createMock(UdmEditMultipleUsagesResearcherWindow.class);
        Set<UdmUsageDto> udmUsages = new HashSet<>(Arrays.asList(udmUsageDtoFirst, udmUsageDtoSecond));
        expectNew(UdmEditMultipleUsagesResearcherWindow.class, eq(controller), eq(udmUsages),
            anyObject(ClickListener.class)).andReturn(mockWindow).once();
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(controller, streamSource, Windows.class, UdmEditMultipleUsagesResearcherWindow.class,
            RupContextUtils.class, ForeignSecurityUtils.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmUsages);
        grid.select(udmUsageDtoFirst);
        grid.select(udmUsageDtoSecond);
        Button editButton = (Button) getButtonsLayout().getComponent(3);
        editButton.click();
        verify(controller, streamSource, Windows.class, UdmEditMultipleUsagesResearcherWindow.class,
            RupContextUtils.class, ForeignSecurityUtils.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testEditButtonClickListenerBaselineResearcher() {
        mockStatic(Windows.class);
        setResearcherExpectations();
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setId("8020f228-c307-4c23-940d-5da727b9c80d");
        udmUsageDto.setStatus(UsageStatusEnum.OPS_REVIEW);
        udmUsageDto.setBaselineFlag(true);
        Windows.showNotificationWindow(NON_BASELINE_ERROR_MESSAGE);
        expectLastCall().once();
        replay(controller, streamSource, Windows.class, RupContextUtils.class, ForeignSecurityUtils.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmUsageDto);
        grid.select(udmUsageDto);
        Button editButton = (Button) getButtonsLayout().getComponent(2);
        editButton.click();
        verify(controller, streamSource, Windows.class, RupContextUtils.class, ForeignSecurityUtils.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testEditButtonClickListenerInvalidAssignee() {
        mockStatic(Windows.class);
        setResearcherExpectations();
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setId("8020f228-c307-4c23-940d-5da727b9c80d");
        udmUsageDto.setStatus(UsageStatusEnum.RH_NOT_FOUND);
        Windows.showNotificationWindow(USAGE_NOT_EDITED_ERROR_MESSAGE);
        expectLastCall().once();
        replay(controller, streamSource, Windows.class, RupContextUtils.class, ForeignSecurityUtils.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmUsageDto);
        grid.select(udmUsageDto);
        Button editButton = (Button) getButtonsLayout().getComponent(2);
        editButton.click();
        verify(controller, streamSource, Windows.class, RupContextUtils.class, ForeignSecurityUtils.class);
    }

    @Test
    public void testSelectSingleUsageSpecialist() {
        setSpecialistExpectations();
        testSelectionOfSingleUsage();
    }

    @Test
    public void testSelectMultipleUsagesSpecialist() {
        setSpecialistExpectations();
        testSelectionOfMultipleUsages();
    }

    @Test
    public void testSelectSingleUsageManager() {
        setManagerExpectations();
        testSelectionOfSingleUsage();
    }

    @Test
    public void testSelectMultipleUsagesManager() {
        setManagerExpectations();
        testSelectionOfMultipleUsages();
    }

    @Test
    public void testSelectSingleUsageResearcher() {
        setResearcherExpectations();
        testSelectionOfSingleUsage();
    }

    @Test
    public void testSelectMultipleUsagesResearcher() {
        setResearcherExpectations();
        testSelectionOfMultipleUsages();
    }

    @Test
    public void testSelectionIsNotAvailableForViewOnly() {
        setViewOnlyExpectations();
        replay(controller, streamSource, ForeignSecurityUtils.class);
        initWidget();
        verify(controller, streamSource, ForeignSecurityUtils.class);
        Grid<UdmUsageDto> udmUsagesGrid = Whitebox.getInternalState(usagesWidget, "udmUsagesGrid");
        Button editButton = Whitebox.getInternalState(usagesWidget, "editButton");
        Button multipleEditButton = Whitebox.getInternalState(usagesWidget, "multipleEditButton");
        MenuBar assignmentMenuBar = Whitebox.getInternalState(usagesWidget, "assignmentMenuBar");
        MenuBar udmBatchMenuBar = Whitebox.getInternalState(usagesWidget, "udmBatchMenuBar");
        MenuBar.MenuItem assignItem = Whitebox.getInternalState(usagesWidget, "assignItem");
        MenuBar.MenuItem unassignItem = Whitebox.getInternalState(usagesWidget, "unassignItem");
        assertTrue(udmUsagesGrid.getSelectionModel().isUserSelectionAllowed());
        assertTrue(assignmentMenuBar.isEnabled());
        assertTrue(udmBatchMenuBar.isEnabled());
        assertFalse(editButton.isEnabled());
        assertFalse(multipleEditButton.isEnabled());
        assertFalse(assignItem.isEnabled());
        assertFalse(unassignItem.isEnabled());
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAllCheckBoxVisible() {
        mockStatic(JavaScript.class);
        setSpecialistExpectations();
        List<UdmUsageDto> udmUsageDtos = ImmutableList.of(new UdmUsageDto(), new UdmUsageDto());
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, Collections.emptyList())).andReturn(udmUsageDtos).once();
        expect(controller.getBeansCount()).andReturn(UDM_RECORD_THRESHOLD).once();
        expect(controller.getUdmRecordThreshold()).andReturn(UDM_RECORD_THRESHOLD).once();
        replay(controller, streamSource, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(udmUsageDtos, dataProvider.fetch(new Query<>(0, 2, Collections.emptyList(), null,
            null)).collect(Collectors.toList()));
        assertEquals(UDM_RECORD_THRESHOLD, dataProvider.size(new Query<>()));
        assertTrue(grid.getSelectionModel() instanceof MultiSelectionModelImpl);
        assertTrue(((MultiSelectionModelImpl<?>) grid.getSelectionModel()).isSelectAllCheckBoxVisible());
        verify(controller, streamSource, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAllCheckBoxNotVisible() {
        mockStatic(JavaScript.class);
        setSpecialistExpectations();
        List<UdmUsageDto> udmUsageDtos = ImmutableList.of(new UdmUsageDto(), new UdmUsageDto());
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, Collections.emptyList())).andReturn(udmUsageDtos).once();
        expect(controller.getBeansCount()).andReturn(EXCEEDED_UDM_RECORD_THRESHOLD).once();
        expect(controller.getUdmRecordThreshold()).andReturn(UDM_RECORD_THRESHOLD).once();
        replay(controller, streamSource, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(udmUsageDtos, dataProvider.fetch(new Query<>(0, 2, Collections.emptyList(), null,
            null)).collect(Collectors.toList()));
        assertEquals(EXCEEDED_UDM_RECORD_THRESHOLD, dataProvider.size(new Query<>()));
        assertTrue(grid.getSelectionModel() instanceof MultiSelectionModelImpl);
        assertFalse(((MultiSelectionModelImpl<?>) grid.getSelectionModel()).isSelectAllCheckBoxVisible());
        verify(controller, streamSource, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAllCheckBoxNotVisibleWhenGridEmpty() {
        mockStatic(JavaScript.class);
        setSpecialistExpectations();
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, Collections.emptyList())).andReturn(Collections.emptyList()).once();
        expect(controller.getBeansCount()).andReturn(0).once();
        replay(controller, streamSource, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(Collections.emptyList(), dataProvider.fetch(new Query<>(0, 2, Collections.emptyList(), null,
            null)).collect(Collectors.toList()));
        assertEquals(0, dataProvider.size(new Query<>()));
        assertTrue(grid.getSelectionModel() instanceof MultiSelectionModelImpl);
        assertFalse(((MultiSelectionModelImpl<?>) grid.getSelectionModel()).isSelectAllCheckBoxVisible());
        verify(controller, streamSource, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
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
    public void testDoubleClickListenerResearcherAllowed() throws Exception {
        setResearcherExpectations();
        testDoubleClickListenerAllowed(USER);
    }

    @Test
    public void testDoubleClickListenerSpecialistForbiddenUsageProcessingCompleted() {
        testDoubleClickListenerSpecialistForbidden(UsageStatusEnum.NEW, USER, false);
    }

    @Test
    public void testDoubleClickListenerSpecialistNullAssigneeForbiddenUsageProcessingCompleted() {
        testDoubleClickListenerSpecialistForbidden(UsageStatusEnum.WORK_FOUND, null, true);
    }

    @Test
    public void testDoubleClickListenerSpecialistInvalidAssigneeForbiddenUsageProcessingCompleted() {
        testDoubleClickListenerSpecialistForbidden(UsageStatusEnum.WORK_FOUND, INVALID_ASSIGNEE, false);
    }

    @Test
    public void testDoubleClickListenerManagerForbiddenUsageProcessingCompleted() {
        testDoubleClickListenerManagerForbidden(UsageStatusEnum.NEW, USER, false);
    }

    @Test
    public void testDoubleClickListenerManagerNullAssigneeForbiddenUsageProcessingCompleted() {
        testDoubleClickListenerManagerForbidden(UsageStatusEnum.NEW, null, true);
    }

    @Test
    public void testDoubleClickListenerManagerInvalidAssigneeForbiddenUsageProcessingCompleted() {
        testDoubleClickListenerManagerForbidden(UsageStatusEnum.WORK_FOUND, INVALID_ASSIGNEE, false);
    }

    @Test
    public void testDoubleClickListenerResearcherForbiddenNonBaseline() {
        testDoubleClickListenerResearcherForbidden(UsageStatusEnum.RH_NOT_FOUND, USER, true,
            NON_BASELINE_ERROR_MESSAGE);
    }

    @Test
    public void testDoubleClickListenerResearcherForbiddenNewStatus() {
        testDoubleClickListenerResearcherForbidden(UsageStatusEnum.NEW, USER, false,
            RESEARCHER_STATUSES_ERROR_MESSAGE);
    }

    @Test
    public void testDoubleClickListenerResearcherForbiddenRhFoundStatus() {
        testDoubleClickListenerResearcherForbidden(UsageStatusEnum.RH_FOUND, USER, false,
            RESEARCHER_STATUSES_ERROR_MESSAGE);
    }

    @Test
    public void testDoubleClickListenerResearcherForbiddenEligibleStatus() {
        testDoubleClickListenerResearcherForbidden(UsageStatusEnum.ELIGIBLE, USER, false,
            RESEARCHER_STATUSES_ERROR_MESSAGE);
    }

    @Test
    public void testDoubleClickListenerResearcherForbiddenIneligibleStatus() {
        testDoubleClickListenerResearcherForbidden(UsageStatusEnum.INELIGIBLE, USER, false,
            RESEARCHER_STATUSES_ERROR_MESSAGE);
    }

    @Test
    public void testDoubleClickListenerResearcherForbiddenSpecialistReviewStatus() {
        testDoubleClickListenerResearcherForbidden(UsageStatusEnum.SPECIALIST_REVIEW, USER, false,
            RESEARCHER_STATUSES_ERROR_MESSAGE);
    }

    @Test
    public void testDoubleClickListenerResearcherNullAssigneeForbidden() {
        testDoubleClickListenerResearcherForbidden(UsageStatusEnum.RH_NOT_FOUND, null, false,
            USAGE_NOT_EDITED_ERROR_MESSAGE);
    }

    @Test
    public void testDoubleClickListenerResearcherInvalidAssigneeForbidden() {
        testDoubleClickListenerResearcherForbidden(UsageStatusEnum.RH_NOT_FOUND, INVALID_ASSIGNEE, false,
            USAGE_NOT_EDITED_ERROR_MESSAGE);
    }

    private void testSelectionOfMultipleUsages() {
        replay(controller, streamSource, ForeignSecurityUtils.class);
        initWidget();
        verify(controller, streamSource, ForeignSecurityUtils.class);
        UdmUsageDto udmUsageDto1 =
            buildUdmUsageDto("3ff90df9-4655-4bc5-838f-77a0e37db88d", "user@copyright.com");
        UdmUsageDto udmUsageDto2 =
            buildUdmUsageDto("dd035171-8942-44e3-9eb2-829f1a9c6f76", "user@copyright.com");
        Grid<UdmUsageDto> udmUsagesGrid = Whitebox.getInternalState(usagesWidget, "udmUsagesGrid");
        Button editButton = Whitebox.getInternalState(usagesWidget, "editButton");
        Button multipleEditButton = Whitebox.getInternalState(usagesWidget, "multipleEditButton");
        MenuBar assignmentMenuBar = Whitebox.getInternalState(usagesWidget, "assignmentMenuBar");
        MenuBar udmBatchMenuBar = Whitebox.getInternalState(usagesWidget, "udmBatchMenuBar");
        MenuBar.MenuItem assignItem = Whitebox.getInternalState(usagesWidget, "assignItem");
        MenuBar.MenuItem unassignItem = Whitebox.getInternalState(usagesWidget, "unassignItem");
        assertTrue(udmUsagesGrid.getSelectionModel().isUserSelectionAllowed());
        assertTrue(assignmentMenuBar.isEnabled());
        assertTrue(udmBatchMenuBar.isEnabled());
        assertFalse(multipleEditButton.isEnabled());
        assertFalse(editButton.isEnabled());
        assertFalse(assignItem.isEnabled());
        assertFalse(unassignItem.isEnabled());
        udmUsagesGrid.setItems(udmUsageDto1, udmUsageDto2);
        udmUsagesGrid.select(udmUsageDto1);
        udmUsagesGrid.select(udmUsageDto2);
        assertTrue(assignmentMenuBar.isEnabled());
        assertTrue(udmBatchMenuBar.isEnabled());
        assertFalse(editButton.isEnabled());
        assertTrue(multipleEditButton.isEnabled());
        assertTrue(assignItem.isEnabled());
        assertTrue(unassignItem.isEnabled());
    }

    private void testSelectionOfSingleUsage() {
        replay(controller, streamSource, ForeignSecurityUtils.class);
        initWidget();
        verify(controller, streamSource, ForeignSecurityUtils.class);
        UdmUsageDto udmUsageDto =
            buildUdmUsageDto("3ff90df9-4655-4bc5-838f-77a0e37db88d", "user_specialist@copyright.com");
        Grid<UdmUsageDto> udmUsagesGrid = Whitebox.getInternalState(usagesWidget, "udmUsagesGrid");
        Button editButton = Whitebox.getInternalState(usagesWidget, "editButton");
        Button multipleEditButton = Whitebox.getInternalState(usagesWidget, "multipleEditButton");
        MenuBar assignmentMenuBar = Whitebox.getInternalState(usagesWidget, "assignmentMenuBar");
        MenuBar udmBatchMenuBar = Whitebox.getInternalState(usagesWidget, "udmBatchMenuBar");
        MenuBar.MenuItem assignItem = Whitebox.getInternalState(usagesWidget, "assignItem");
        MenuBar.MenuItem unassignItem = Whitebox.getInternalState(usagesWidget, "unassignItem");
        assertTrue(udmUsagesGrid.getSelectionModel().isUserSelectionAllowed());
        assertTrue(assignmentMenuBar.isEnabled());
        assertTrue(udmBatchMenuBar.isEnabled());
        assertFalse(editButton.isEnabled());
        assertFalse(multipleEditButton.isEnabled());
        assertFalse(assignItem.isEnabled());
        assertFalse(unassignItem.isEnabled());
        udmUsagesGrid.setItems(udmUsageDto);
        udmUsagesGrid.select(udmUsageDto);
        assertTrue(assignmentMenuBar.isEnabled());
        assertTrue(udmBatchMenuBar.isEnabled());
        assertTrue(editButton.isEnabled());
        assertTrue(multipleEditButton.isEnabled());
        assertTrue(assignItem.isEnabled());
        assertTrue(unassignItem.isEnabled());
    }

    private void initWidget() {
        usagesWidget = new UdmUsageWidget();
        usagesWidget.setController(controller);
        usagesWidget.init();
        usagesWidget.initMediator().applyPermissions();
    }

    private void setSpecialistExpectations() {
        setPermissionsExpectations(true, false, false);
        expect(controller.getExportUdmUsagesStreamSourceSpecialistManagerRoles()).andReturn(streamSource).once();
    }

    private void setManagerExpectations() {
        setPermissionsExpectations(false, true, false);
        expect(controller.getExportUdmUsagesStreamSourceSpecialistManagerRoles()).andReturn(streamSource).once();
    }

    private void setResearcherExpectations() {
        setPermissionsExpectations(false, false, true);
        expect(controller.getExportUdmUsagesStreamSourceResearcherRole()).andReturn(streamSource).once();
    }

    private void setViewOnlyExpectations() {
        setPermissionsExpectations(false, false, false);
        expect(controller.getExportUdmUsagesStreamSourceViewRole()).andReturn(streamSource).once();
    }

    private void setPermissionsExpectations(boolean isSpecialist, boolean isManager, boolean isResearcher) {
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andStubReturn(isSpecialist);
        expect(ForeignSecurityUtils.hasManagerPermission()).andStubReturn(isManager);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andStubReturn(isResearcher);
    }

    private void verifyToolbarLayout(Component component, String searchPlaceholder, boolean... buttonsVisibility) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) component;
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
        assertEquals(6, layout.getComponentCount());
        verifyMenuBar(layout.getComponent(0), "UDM Batch", buttonsVisibility[0], Arrays.asList("Load", "View"));
        verifyMenuBar(layout.getComponent(1), "Assignment", buttonsVisibility[1], Arrays.asList("Assign", "Unassign"));
        verifyButton(layout.getComponent(2), "Edit Usage", buttonsVisibility[2]);
        verifyButton(layout.getComponent(3), "Multiple Edit", buttonsVisibility[3]);
        verifyButton(layout.getComponent(4), "Publish", buttonsVisibility[4]);
        verifyButton(layout.getComponent(5), "Export", buttonsVisibility[5]);
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
        return (HorizontalLayout) ((VerticalLayout) ((VerticalLayout)
            usagesWidget.getSecondComponent()).getComponent(0)).getComponent(0);
    }

    private UdmUsageDto buildUdmUsageDto(String usageId, String user) {
        UdmUsageDto udmUsage = new UdmUsageDto();
        udmUsage.setId(usageId);
        udmUsage.setAssignee(user);
        return udmUsage;
    }

    private void testEditButtonClickListenerSpecialistAllowed(UsageStatusEnum status, String user) throws Exception {
        setSpecialistExpectations();
        testEditButtonClickListenerAllowed(status, user);
    }

    private void testEditButtonClickListenerManagerAllowed(UsageStatusEnum status, String user) throws Exception {
        setManagerExpectations();
        testEditButtonClickListenerAllowed(status, user);
    }

    private void testEditButtonClickListenerResearcherAllowed(UsageStatusEnum status) throws Exception {
        setResearcherExpectations();
        testEditButtonClickListenerAllowed(status, USER);
    }

    @SuppressWarnings(UNCHECKED)
    private void testEditButtonClickListenerAllowed(UsageStatusEnum status, String user) throws Exception {
        mockStatic(Windows.class);
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setId("00ce418f-4a5d-459a-8e23-b164b83e2a60");
        udmUsageDto.setAssignee(user);
        udmUsageDto.setStatus(status);
        UdmEditUsageWindow mockWindow = createMock(UdmEditUsageWindow.class);
        expectNew(UdmEditUsageWindow.class, eq(controller), eq(udmUsageDto), anyObject(ClickListener.class))
            .andReturn(mockWindow).once();
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(controller, streamSource, Windows.class, UdmEditUsageWindow.class, RupContextUtils.class,
            ForeignSecurityUtils.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmUsageDto);
        grid.select(udmUsageDto);
        Button editButton = (Button) getButtonsLayout().getComponent(2);
        editButton.click();
        verify(controller, streamSource, Windows.class, UdmEditUsageWindow.class, RupContextUtils.class,
            ForeignSecurityUtils.class);
    }

    private void testEditButtonClickListenerResearcherForbidden(UsageStatusEnum status) {
        mockStatic(Windows.class);
        setResearcherExpectations();
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setId("8020f228-c307-4c23-940d-5da727b9c80d");
        udmUsageDto.setAssignee(USER);
        udmUsageDto.setStatus(status);
        Windows.showNotificationWindow(RESEARCHER_STATUSES_ERROR_MESSAGE);
        expectLastCall().once();
        replay(controller, streamSource, Windows.class, RupContextUtils.class, ForeignSecurityUtils.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmUsageDto);
        grid.select(udmUsageDto);
        Button editButton = (Button) getButtonsLayout().getComponent(2);
        editButton.click();
        verify(controller, streamSource, Windows.class, RupContextUtils.class, ForeignSecurityUtils.class);
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

    private void testDoubleClickListenerSpecialistForbidden(UsageStatusEnum status, String user,
                                                            Boolean isBaseLineFlag) {
        setSpecialistExpectations();
        testDoubleClickListenerForbidden(status, user, isBaseLineFlag, USAGE_PROCESSING_ERROR_MESSAGE);
    }

    private void testDoubleClickListenerManagerForbidden(UsageStatusEnum status, String user, Boolean isBaseLineFlag) {
        setManagerExpectations();
        testDoubleClickListenerForbidden(status, user, isBaseLineFlag, USAGE_PROCESSING_ERROR_MESSAGE);
    }

    private void testDoubleClickListenerResearcherForbidden(UsageStatusEnum status, String user,
                                                            Boolean isBaseLineFlag, String message) {
        setResearcherExpectations();
        testDoubleClickListenerForbidden(status, user, isBaseLineFlag, message);
    }

    @SuppressWarnings(UNCHECKED)
    private void testDoubleClickListenerAllowed(String user) throws Exception {
        mockStatic(Windows.class);
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setId("f4477145-58ab-44e8-915d-423dcee0977b");
        udmUsageDto.setAssignee(user);
        udmUsageDto.setStatus(UsageStatusEnum.RH_NOT_FOUND);
        UdmEditUsageWindow mockWindow = createMock(UdmEditUsageWindow.class);
        expectNew(UdmEditUsageWindow.class, eq(controller), eq(udmUsageDto), anyObject(ClickListener.class))
            .andReturn(mockWindow).once();
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(controller, streamSource, Windows.class, UdmEditUsageWindow.class, RupContextUtils.class,
            ForeignSecurityUtils.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        ItemClickListener<UdmUsageDto> listener =
            (ItemClickListener) new ArrayList<>(grid.getListeners(ItemClick.class)).get(0);
        Grid.ItemClick<UdmUsageDto> usageDtoItemClick =
            new ItemClick<>(grid, grid.getColumns().get(0), udmUsageDto, createMouseEvent(), 0);
        listener.itemClick(usageDtoItemClick);
        verify(controller, streamSource, Windows.class, UdmViewUsageWindow.class, RupContextUtils.class,
            ForeignSecurityUtils.class);
    }

    @SuppressWarnings(UNCHECKED)
    private void testDoubleClickListenerForbidden(UsageStatusEnum status, String user, Boolean isBaseLineFlag,
                                                  String message) {
        mockStatic(Windows.class);
        Window confirmWindowMock = createMock(Window.class);
        UdmUsageDto udmUsageDto = buildUdmUsageDto("305eaae8-2ee1-45db-aca5-17d2661df434", user);
        udmUsageDto.setStatus(status);
        udmUsageDto.setBaselineFlag(isBaseLineFlag);
        Windows.showNotificationWindow(message);
        expectLastCall().once();
        replay(controller, streamSource, confirmWindowMock, Windows.class, RupContextUtils.class,
            ForeignSecurityUtils.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        ItemClickListener<UdmUsageDto> listener =
            (ItemClickListener) new ArrayList<>(grid.getListeners(ItemClick.class)).get(0);
        Grid.ItemClick<UdmUsageDto> usageDtoItemClick =
            new ItemClick<>(grid, grid.getColumns().get(0), udmUsageDto, createMouseEvent(), 0);
        listener.itemClick(usageDtoItemClick);
        verify(controller, streamSource, confirmWindowMock, Windows.class, RupContextUtils.class,
            ForeignSecurityUtils.class);
    }
}
