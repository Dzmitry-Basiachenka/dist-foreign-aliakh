package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFooterItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyMenuBar;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
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
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.io.IOException;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
        List.of("Detail ID", "Period", "Usage Detail ID", "Detail Status", "Assignee", "RH Account #",
            "RH Name", "Wr Wrk Inst", "Reported Title", "System Title", "Reported Standard Number",
            "Standard Number", "Reported Pub Type", "Publication Format", "Article", "Language", "Action Reason",
            "Comment", "Research URL", "Det LC ID", "Det LC Name", "Channel", "Usage Date", "Survey Start Date",
            "Survey End Date", "Reported TOU", "TOU", "Ineligible Reason", "Load Date", "Updated By", "Updated Date");
    private static final List<String> VISIBLE_COLUMNS_FOR_MANAGER_AND_SPECIALIST =
        List.of("Detail ID", "Period", "Usage Origin", "Usage Detail ID", "Detail Status", "Assignee",
            "RH Account #", "RH Name", "Wr Wrk Inst", "Reported Title", "System Title", "Reported Standard Number",
            "Standard Number", "Reported Pub Type", "Publication Format", "Article", "Language", "Action Reason",
            "Comment", "Research URL", "Det LC ID", "Det LC Name", "Company ID", "Company Name", "Survey Respondent",
            "IP Address", "Survey Country", "Channel", "Usage Date", "Survey Start Date", "Survey End Date",
            "Annual Multiplier", "Statistical Multiplier", "Reported TOU", "TOU", "Quantity", "Annualized Copies",
            "Ineligible Reason", "Load Date", "Updated By", "Updated Date");
    private static final List<String> VISIBLE_COLUMNS_FOR_VIEW_ONLY =
        List.of("Detail ID", "Period", "Usage Origin", "Usage Detail ID", "Detail Status", "Assignee",
            "RH Account #", "RH Name", "Wr Wrk Inst", "Reported Title", "System Title", "Reported Standard Number",
            "Standard Number", "Reported Pub Type", "Publication Format", "Article", "Language", "Action Reason",
            "Comment", "Research URL", "Det LC ID", "Det LC Name", "Company ID", "Company Name", "Survey Respondent",
            "Survey Country", "Channel", "Usage Date", "Survey Start Date", "Survey End Date", "Annual Multiplier",
            "Statistical Multiplier", "Reported TOU", "TOU", "Quantity", "Annualized Copies", "Ineligible Reason",
            "Load Date", "Updated By", "Updated Date");
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
    private static final String USAGE_NOT_EDITED_ERROR_MESSAGE = "Selected UDM usage cannot be edited. Please " +
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
    public void testGridValues() {
        mockStatic(JavaScript.class);
        List<UdmUsageDto> udmUsages = loadExpectedUdmUsageDto("udm_usage_dto_b989e02b.json");
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, Integer.MAX_VALUE, List.of())).andReturn(udmUsages).once();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.getUdmRecordThreshold()).andReturn(UDM_RECORD_THRESHOLD).once();
        setSpecialistExpectations();
        replay(JavaScript.class, ForeignSecurityUtils.class, controller, streamSource);
        initWidget();
        Grid grid = (Grid) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        DataProvider dataProvider = grid.getDataProvider();
        dataProvider.refreshAll();
        Object[][] expectedCells = {
            {"b989e02b-1f1d-4637-b89e-dc99938a51b9", 202106, UdmUsageOriginEnum.SS, "OGN674GHHSB0108",
                UsageStatusEnum.RH_FOUND, "wjohn@copyright.com", 1000002612L, "CFC", 227738245L,
                "The Wall Street journal", "Wall Street journal", "9780470373606", "9780470373606", "Not Shared",
                "Digital", "Economics of strategy", "English", "action reason", "Assigned to wjohn for review",
                "google.com", 22, "Banks/Ins/RE/Holding Cos", 1136L, "Albany International Corp.",
                "c6615155-f82b-402c-8f22-77e2722ae448", "localhost", "United States", UdmChannelEnum.CCC,
                "05/10/2020", "04/20/2020", "05/15/2020", 25, "1.00", "COPY_FOR_MYSELF", "DIGITAL", 3L, "75.00",
                "ineligible reason", "09/01/2022", "uuser@copyright.com", "09/02/2022"}
        };
        verifyGridItems(grid, udmUsages, expectedCells);
        verify(JavaScript.class, ForeignSecurityUtils.class, controller, streamSource);
        Object[][] expectedFooterColumns = {{"detailId", "Usages Count: 1", null}};
        verifyFooterItems(grid, expectedFooterColumns);
    }

    @Test
    public void testWidgetStructureForSpecialist() {
        setSpecialistExpectations();
        replay(controller, ForeignSecurityUtils.class, RupContextUtils.class, streamSource);
        initWidget();
        verify(controller, ForeignSecurityUtils.class, RupContextUtils.class, streamSource);
        assertTrue(usagesWidget.isLocked());
        assertEquals(270, usagesWidget.getSplitPosition(), 0);
        verifyWindow(usagesWidget, null, 100, 100, Unit.PERCENTAGE);
        assertThat(usagesWidget.getFirstComponent(), instanceOf(UdmUsageFilterWidget.class));
        Component secondComponent = usagesWidget.getSecondComponent();
        assertThat(secondComponent, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifyWindow(layout, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyToolbarLayout(layout.getComponent(0), SEARCH_PLACEHOLDER, true, true, true, true, true);
        Grid grid = (Grid) layout.getComponent(1);
        assertThat(grid.getSelectionModel(), instanceOf(MultiSelectionModelImpl.class));
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
        assertEquals(270, usagesWidget.getSplitPosition(), 0);
        verifyWindow(usagesWidget, null, 100, 100, Unit.PERCENTAGE);
        assertThat(usagesWidget.getFirstComponent(), instanceOf(UdmUsageFilterWidget.class));
        Component secondComponent = usagesWidget.getSecondComponent();
        assertThat(secondComponent, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifyWindow(layout, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyToolbarLayout(layout.getComponent(0), SEARCH_PLACEHOLDER, false, true, true, false, true);
        Grid grid = (Grid) layout.getComponent(1);
        assertThat(grid.getSelectionModel(), instanceOf(MultiSelectionModelImpl.class));
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
        assertEquals(270, usagesWidget.getSplitPosition(), 0);
        verifyWindow(usagesWidget, null, 100, 100, Unit.PERCENTAGE);
        assertThat(usagesWidget.getFirstComponent(), instanceOf(UdmUsageFilterWidget.class));
        Component secondComponent = usagesWidget.getSecondComponent();
        assertThat(secondComponent, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifyWindow(layout, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyToolbarLayout(layout.getComponent(0), SEARCH_PLACEHOLDER, false, false, false, false, true);
        Grid grid = (Grid) layout.getComponent(1);
        assertThat(grid.getSelectionModel(), instanceOf(SingleSelectionModel.class));
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
        assertEquals(270, usagesWidget.getSplitPosition(), 0);
        verifyWindow(usagesWidget, null, 100, 100, Unit.PERCENTAGE);
        assertThat(usagesWidget.getFirstComponent(), instanceOf(UdmUsageFilterWidget.class));
        Component secondComponent = usagesWidget.getSecondComponent();
        assertThat(secondComponent, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifyWindow(layout, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyToolbarLayout(layout.getComponent(0), SEARCH_PLACEHOLDER_RESEARCHER, false, true, true, false, true);
        Grid grid = (Grid) layout.getComponent(1);
        assertThat(grid.getSelectionModel(), instanceOf(MultiSelectionModelImpl.class));
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
        controller.assignUsages(Set.of(udmUsageDto));
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
        controller.unassignUsages(Set.of(udmUsageDto));
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
    public void testMultipleEditButtonClickListenerSpecialistForbiddenUsageProcessingCompleted() {
        testMultipleEditButtonClickListenerSpecialistForbidden(UsageStatusEnum.NEW, USER,
            USAGE_PROCESSING_ERROR_MESSAGE);
    }

    @Test
    public void testMultipleEditButtonClickListenerManagerForbiddenUsageProcessingCompleted() {
        testMultipleEditButtonClickListenerManagerForbidden(UsageStatusEnum.NEW, USER, USAGE_PROCESSING_ERROR_MESSAGE);
    }

    @Test
    public void testMultipleEditButtonClickListenerResearcherForbiddenInvalidAssignee() {
        testMultipleEditButtonClickListenerResearcherForbidden(UsageStatusEnum.RH_NOT_FOUND, INVALID_ASSIGNEE, false,
            USAGE_NOT_EDITED_ERROR_MESSAGE);
    }

    @Test
    public void testMultipleEditButtonClickListenerResearcherForbiddenNullAssignee() {
        testMultipleEditButtonClickListenerResearcherForbidden(UsageStatusEnum.RH_NOT_FOUND, null, false,
            USAGE_NOT_EDITED_ERROR_MESSAGE);
    }

    @Test
    public void testMultipleEditButtonClickListenerResearcherForbiddenBaseline() {
        testMultipleEditButtonClickListenerResearcherForbidden(UsageStatusEnum.RH_NOT_FOUND, USER, true,
            NON_BASELINE_ERROR_MESSAGE);
    }

    @Test
    public void testMultipleEditButtonClickListenerResearcherForbiddenRhFound() {
        testMultipleEditButtonClickListenerResearcherForbidden(UsageStatusEnum.RH_FOUND, USER, false,
            RESEARCHER_STATUSES_ERROR_MESSAGE);
    }

    @Test
    public void testMultipleEditButtonClickListenerResearcherForbiddenEligible() {
        testMultipleEditButtonClickListenerResearcherForbidden(UsageStatusEnum.ELIGIBLE, USER, false,
            RESEARCHER_STATUSES_ERROR_MESSAGE);
    }

    @Test
    public void testMultipleEditButtonClickListenerResearcherForbiddenIneligible() {
        testMultipleEditButtonClickListenerResearcherForbidden(UsageStatusEnum.INELIGIBLE, USER, false,
            RESEARCHER_STATUSES_ERROR_MESSAGE);
    }

    @Test
    public void testMultipleEditButtonClickListenerResearcherForbiddenResearcherReview() {
        testMultipleEditButtonClickListenerResearcherForbidden(UsageStatusEnum.SPECIALIST_REVIEW, USER, false,
            RESEARCHER_STATUSES_ERROR_MESSAGE);
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
        Set<UdmUsageDto> udmUsages = Set.of(udmUsageDtoFirst, udmUsageDtoSecond);
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
        Button editButton = (Button) getButtonsLayout().getComponent(2);
        editButton.click();
        verify(controller, streamSource, Windows.class, UdmEditMultipleUsagesResearcherWindow.class,
            RupContextUtils.class, ForeignSecurityUtils.class);
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
        Button multipleEditButton = Whitebox.getInternalState(usagesWidget, "multipleEditButton");
        MenuBar assignmentMenuBar = Whitebox.getInternalState(usagesWidget, "assignmentMenuBar");
        MenuBar udmBatchMenuBar = Whitebox.getInternalState(usagesWidget, "udmBatchMenuBar");
        MenuBar.MenuItem assignItem = Whitebox.getInternalState(usagesWidget, "assignItem");
        MenuBar.MenuItem unassignItem = Whitebox.getInternalState(usagesWidget, "unassignItem");
        assertTrue(udmUsagesGrid.getSelectionModel().isUserSelectionAllowed());
        assertTrue(assignmentMenuBar.isEnabled());
        assertTrue(udmBatchMenuBar.isEnabled());
        assertFalse(multipleEditButton.isEnabled());
        assertFalse(assignItem.isEnabled());
        assertFalse(unassignItem.isEnabled());
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAllCheckBoxVisible() {
        mockStatic(JavaScript.class);
        setSpecialistExpectations();
        List<UdmUsageDto> udmUsageDtos = List.of(new UdmUsageDto(), new UdmUsageDto());
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, List.of())).andReturn(udmUsageDtos).once();
        expect(controller.getBeansCount()).andReturn(UDM_RECORD_THRESHOLD).once();
        expect(controller.getUdmRecordThreshold()).andReturn(UDM_RECORD_THRESHOLD).once();
        replay(controller, streamSource, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(udmUsageDtos, dataProvider.fetch(new Query<>(0, 2, List.of(), null,
            null)).collect(Collectors.toList()));
        assertEquals(UDM_RECORD_THRESHOLD, dataProvider.size(new Query<>()));
        assertThat(grid.getSelectionModel(), instanceOf(MultiSelectionModelImpl.class));
        assertTrue(((MultiSelectionModelImpl<?>) grid.getSelectionModel()).isSelectAllCheckBoxVisible());
        verify(controller, streamSource, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAllCheckBoxNotVisible() {
        mockStatic(JavaScript.class);
        setSpecialistExpectations();
        List<UdmUsageDto> udmUsageDtos = List.of(new UdmUsageDto(), new UdmUsageDto());
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, List.of())).andReturn(udmUsageDtos).once();
        expect(controller.getBeansCount()).andReturn(EXCEEDED_UDM_RECORD_THRESHOLD).once();
        expect(controller.getUdmRecordThreshold()).andReturn(UDM_RECORD_THRESHOLD).once();
        replay(controller, streamSource, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(udmUsageDtos, dataProvider.fetch(new Query<>(0, 2, List.of(), null,
            null)).collect(Collectors.toList()));
        assertEquals(EXCEEDED_UDM_RECORD_THRESHOLD, dataProvider.size(new Query<>()));
        assertThat(grid.getSelectionModel(), instanceOf(MultiSelectionModelImpl.class));
        assertFalse(((MultiSelectionModelImpl<?>) grid.getSelectionModel()).isSelectAllCheckBoxVisible());
        verify(controller, streamSource, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAllCheckBoxNotVisibleWhenGridEmpty() {
        mockStatic(JavaScript.class);
        setSpecialistExpectations();
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, List.of())).andReturn(List.of()).once();
        expect(controller.getBeansCount()).andReturn(0).once();
        replay(controller, streamSource, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(List.of(), dataProvider.fetch(new Query<>(0, 2, List.of(), null,
            null)).collect(Collectors.toList()));
        assertEquals(0, dataProvider.size(new Query<>()));
        assertThat(grid.getSelectionModel(), instanceOf(MultiSelectionModelImpl.class));
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
    public void testDoubleClickListenerResearcherBaseline() throws Exception {
        testResearcherViewWindowOnDoubleClick(UsageStatusEnum.RH_NOT_FOUND, USER, true);
    }

    @Test
    public void testDoubleClickListenerResearcherNewStatus() throws Exception {
        testResearcherViewWindowOnDoubleClick(UsageStatusEnum.NEW, USER, false);
    }

    @Test
    public void testDoubleClickListenerResearcherRhFoundStatus() throws Exception {
        testResearcherViewWindowOnDoubleClick(UsageStatusEnum.RH_FOUND, USER, false);
    }

    @Test
    public void testDoubleClickListenerResearcherEligibleStatus() throws Exception {
        testResearcherViewWindowOnDoubleClick(UsageStatusEnum.ELIGIBLE, USER, false);
    }

    @Test
    public void testDoubleClickListenerResearcherIneligibleStatus() throws Exception {
        testResearcherViewWindowOnDoubleClick(UsageStatusEnum.INELIGIBLE, USER, false);
    }

    @Test
    public void testDoubleClickListenerResearcherSpecialistReviewStatus() throws Exception {
        testResearcherViewWindowOnDoubleClick(UsageStatusEnum.SPECIALIST_REVIEW, USER, false);
    }

    @Test
    public void testDoubleClickListenerResearcherNullAssignee() throws Exception {
        testResearcherViewWindowOnDoubleClick(UsageStatusEnum.RH_NOT_FOUND, null, false);
    }

    @Test
    public void testDoubleClickListenerResearcherInvalidAssignee() throws Exception {
        testResearcherViewWindowOnDoubleClick(UsageStatusEnum.RH_NOT_FOUND, INVALID_ASSIGNEE, false);
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
        Button multipleEditButton = Whitebox.getInternalState(usagesWidget, "multipleEditButton");
        MenuBar assignmentMenuBar = Whitebox.getInternalState(usagesWidget, "assignmentMenuBar");
        MenuBar udmBatchMenuBar = Whitebox.getInternalState(usagesWidget, "udmBatchMenuBar");
        MenuBar.MenuItem assignItem = Whitebox.getInternalState(usagesWidget, "assignItem");
        MenuBar.MenuItem unassignItem = Whitebox.getInternalState(usagesWidget, "unassignItem");
        assertTrue(udmUsagesGrid.getSelectionModel().isUserSelectionAllowed());
        assertTrue(assignmentMenuBar.isEnabled());
        assertTrue(udmBatchMenuBar.isEnabled());
        assertFalse(multipleEditButton.isEnabled());
        assertFalse(assignItem.isEnabled());
        assertFalse(unassignItem.isEnabled());
        udmUsagesGrid.setItems(udmUsageDto1, udmUsageDto2);
        udmUsagesGrid.select(udmUsageDto1);
        udmUsagesGrid.select(udmUsageDto2);
        assertTrue(assignmentMenuBar.isEnabled());
        assertTrue(udmBatchMenuBar.isEnabled());
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
        Button multipleEditButton = Whitebox.getInternalState(usagesWidget, "multipleEditButton");
        MenuBar assignmentMenuBar = Whitebox.getInternalState(usagesWidget, "assignmentMenuBar");
        MenuBar udmBatchMenuBar = Whitebox.getInternalState(usagesWidget, "udmBatchMenuBar");
        MenuBar.MenuItem assignItem = Whitebox.getInternalState(usagesWidget, "assignItem");
        MenuBar.MenuItem unassignItem = Whitebox.getInternalState(usagesWidget, "unassignItem");
        assertTrue(udmUsagesGrid.getSelectionModel().isUserSelectionAllowed());
        assertTrue(assignmentMenuBar.isEnabled());
        assertTrue(udmBatchMenuBar.isEnabled());
        assertFalse(multipleEditButton.isEnabled());
        assertFalse(assignItem.isEnabled());
        assertFalse(unassignItem.isEnabled());
        udmUsagesGrid.setItems(udmUsageDto);
        udmUsagesGrid.select(udmUsageDto);
        assertTrue(assignmentMenuBar.isEnabled());
        assertTrue(udmBatchMenuBar.isEnabled());
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
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) component;
        assertTrue(layout.isSpacing());
        verifySize(layout, 100, -1, Unit.PIXELS);
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0), buttonsVisibility);
        verifySearchWidget(layout.getComponent(1), searchPlaceholder);
    }

    private void verifySearchWidget(Component component, String searchPlaceholder) {
        assertThat(component, instanceOf(SearchWidget.class));
        SearchWidget searchWidget = (SearchWidget) component;
        verifySize(searchWidget, 65, -1, Unit.PIXELS);
        assertEquals(searchPlaceholder, Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
    }

    private void verifyButtonsLayout(HorizontalLayout layout, boolean... buttonsVisibility) {
        assertTrue(layout.isSpacing());
        assertEquals(new MarginInfo(false), layout.getMargin());
        assertEquals(5, layout.getComponentCount());
        verifyMenuBar(layout.getComponent(0), "UDM Batch", buttonsVisibility[0], List.of("Load", "View"));
        verifyMenuBar(layout.getComponent(1), "Assignment", buttonsVisibility[1], List.of("Assign", "Unassign"));
        verifyButton(layout.getComponent(2), "Multiple Edit", buttonsVisibility[2]);
        verifyButton(layout.getComponent(3), "Publish", buttonsVisibility[3]);
        verifyButton(layout.getComponent(4), "Export", buttonsVisibility[4]);
    }

    private void verifyButton(Component component, String name, boolean isVisible) {
        assertThat(component, instanceOf(Button.class));
        Button button = (Button) component;
        assertEquals(name, button.getCaption());
        assertEquals(isVisible, button.isVisible());
    }

    private void verifyGrid(Grid grid, List<String> expectedColumns) {
        List<Column> columns = grid.getColumns();
        assertEquals(expectedColumns, columns.stream()
            .filter(column -> !column.isHidden())
            .map(Column::getCaption)
            .collect(Collectors.toList()));
        verifyWindow(usagesWidget, null, 100, 100, Unit.PERCENTAGE);
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

    private void testMultipleEditButtonClickListenerSpecialistForbidden(UsageStatusEnum status, String user,
                                                                        String message) {
        setSpecialistExpectations();
        testMultipleEditButtonClickListenerForbidden(status, user, false, message);
    }

    private void testMultipleEditButtonClickListenerManagerForbidden(UsageStatusEnum status, String user,
                                                                     String message) {
        setManagerExpectations();
        testMultipleEditButtonClickListenerForbidden(status, user, false, message);
    }

    private void testMultipleEditButtonClickListenerResearcherForbidden(UsageStatusEnum status, String user,
                                                                        boolean isBaselineFlag, String message) {
        setResearcherExpectations();
        testMultipleEditButtonClickListenerForbidden(status, user, isBaselineFlag, message);
    }

    @SuppressWarnings(UNCHECKED)
    private void testMultipleEditButtonClickListenerForbidden(UsageStatusEnum status, String user,
                                                              boolean isBaselineFlag, String message) {
        mockStatic(Windows.class);
        UdmUsageDto udmUsageDtoFirst = new UdmUsageDto();
        udmUsageDtoFirst.setId("188fead4-e929-4d68-bf05-113188f48a95");
        udmUsageDtoFirst.setAssignee(user);
        udmUsageDtoFirst.setStatus(status);
        udmUsageDtoFirst.setBaselineFlag(isBaselineFlag);
        UdmUsageDto udmUsageDtoSecond = new UdmUsageDto();
        udmUsageDtoSecond.setId("cdda8071-c84c-497e-9b30-4ba447c86373");
        udmUsageDtoSecond.setAssignee(USER);
        udmUsageDtoSecond.setStatus(UsageStatusEnum.RH_NOT_FOUND);
        udmUsageDtoSecond.setBaselineFlag(false);
        Set<UdmUsageDto> udmUsages = Set.of(udmUsageDtoFirst, udmUsageDtoSecond);
        Windows.showNotificationWindow(message);
        expectLastCall().once();
        replay(controller, streamSource, Windows.class, RupContextUtils.class, ForeignSecurityUtils.class);
        initWidget();
        Grid<UdmUsageDto> grid =
            (Grid<UdmUsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        grid.setItems(udmUsages);
        grid.select(udmUsageDtoFirst);
        grid.select(udmUsageDtoSecond);
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

    @SuppressWarnings(UNCHECKED)
    private void testResearcherViewWindowOnDoubleClick(UsageStatusEnum status, String user,
                                                       Boolean isBaseLineFlag) throws Exception {
        mockStatic(Windows.class);
        setResearcherExpectations();
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setId("ce65ac5a-46e4-4f1b-8674-9a6f998f234c");
        udmUsageDto.setAssignee(user);
        udmUsageDto.setStatus(status);
        udmUsageDto.setBaselineFlag(isBaseLineFlag);
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

    private List<UdmUsageDto> loadExpectedUdmUsageDto(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<List<UdmUsageDto>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
