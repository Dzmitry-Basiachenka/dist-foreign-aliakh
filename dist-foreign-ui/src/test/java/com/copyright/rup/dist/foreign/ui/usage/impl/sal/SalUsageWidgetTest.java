package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyMenuBar;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageWidget;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow.IListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.MultiSelectionModelImpl;

import org.apache.commons.lang3.tuple.Triple;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Verifies {@link SalUsageWidget}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 07/28/2020
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SalUsageWidget.class, ForeignSecurityUtils.class, JavaScript.class, Windows.class})
public class SalUsageWidgetTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String BATCH_ID = "3a070817-03ae-4ebd-b25f-dd3168a7ffb0";
    private static final String SAL_SCENARIO_NAME_PREFIX = "SAL Distribution ";
    private static final String SAL_PRODUCT_FAMILY = "SAL";
    private static final int RECORD_THRESHOLD = 10000;
    private static final int EXCEEDED_RECORD_THRESHOLD = 10001;
    private SalUsageWidget usagesWidget;
    private ISalUsageController controller;
    private SalUsageFilterWidget filterWidget;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true).once();
        controller = createMock(ISalUsageController.class);
        filterWidget = new SalUsageFilterWidget(createMock(ISalUsageFilterController.class));
        filterWidget.getAppliedFilter().setUsageBatchesIds(Set.of(BATCH_ID));
        expect(controller.initUsagesFilterWidget()).andReturn(filterWidget).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource())
            .andReturn(new SimpleImmutableEntry(createMock(Supplier.class), createMock(Supplier.class))).once();
        expect(controller.getExportUsagesStreamSource()).andReturn(streamSource).once();
        replay(controller, ForeignSecurityUtils.class, streamSource);
        usagesWidget = new SalUsageWidget(controller);
        usagesWidget.setController(controller);
        usagesWidget.init();
        verify(controller, ForeignSecurityUtils.class, streamSource);
        reset(controller);
    }

    @Test
    public void testWidgetStructure() {
        assertTrue(usagesWidget.isLocked());
        assertEquals(200, usagesWidget.getSplitPosition(), 0);
        verifySize(usagesWidget);
        assertThat(usagesWidget.getFirstComponent(), instanceOf(SalUsageFilterWidget.class));
        Component secondComponent = usagesWidget.getSecondComponent();
        assertThat(secondComponent, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0));
        Grid grid = (Grid) layout.getComponent(1);
        verifyGrid(grid, List.of(
            Triple.of("Detail ID", 130.0, -1),
            Triple.of("Detail Status", 115.0, -1),
            Triple.of("Detail Type", 115.0, -1),
            Triple.of("Product Family", 125.0, -1),
            Triple.of("Usage Batch Name", 145.0, -1),
            Triple.of("Period End Date", 115.0, -1),
            Triple.of("Licensee Account #", 150.0, -1),
            Triple.of("Licensee Name", 300.0, -1),
            Triple.of("RH Account #", 115.0, -1),
            Triple.of("RH Name", 300.0, -1),
            Triple.of("Wr Wrk Inst", 110.0, -1),
            Triple.of("System Title", 300.0, -1),
            Triple.of("Standard Number", 140.0, -1),
            Triple.of("Standard Number Type", 155.0, -1),
            Triple.of("Assessment Name", 180.0, -1),
            Triple.of("Assessment Type", 150.0, -1),
            Triple.of("Date of Scored Assessment", 200.0, -1),
            Triple.of("Reported Work Portion ID", 180.0, -1),
            Triple.of("Reported Title", 170.0, -1),
            Triple.of("Reported Article or Chapter Title", 240.0, -1),
            Triple.of("Reported Standard Number or Image ID Number", 315.0, -1),
            Triple.of("Reported Author", 150.0, -1),
            Triple.of("Reported Publisher", 150.0, -1),
            Triple.of("Reported Publication Date", 200.0, -1),
            Triple.of("Reported Page Range", 150.0, -1),
            Triple.of("Reported Vol/Number/Series", 200.0, -1),
            Triple.of("Reported Media Type", 150.0, -1),
            Triple.of("Coverage Year", 115.0, -1),
            Triple.of("Question Identifier", 150.0, -1),
            Triple.of("Grade", 115.0, -1),
            Triple.of("Grade Group", 115.0, -1),
            Triple.of("States", 115.0, -1),
            Triple.of("Number of Views", 150.0, -1),
            Triple.of("Comment", 115.0, -1)));
        assertEquals(1, layout.getExpandRatio(grid), 0);
    }

    @Test
    @SuppressWarnings("UNCHECKED")
    public void testSelectAllCheckBoxVisible() {
        mockStatic(JavaScript.class);
        List<UsageDto> usageDtos = List.of(new UsageDto(), new UsageDto());
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, List.of())).andReturn(usageDtos).once();
        expect(controller.getBeansCount()).andReturn(RECORD_THRESHOLD).once();
        expect(controller.getGridRecordThreshold()).andReturn(RECORD_THRESHOLD).once();
        replay(controller, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
        Grid<UsageDto> grid =
            (Grid<UsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(usageDtos, dataProvider.fetch(new Query<>(0, 2, List.of(), null,
            null)).collect(Collectors.toList()));
        assertEquals(RECORD_THRESHOLD, dataProvider.size(new Query<>()));
        assertThat(grid.getSelectionModel(), instanceOf(MultiSelectionModelImpl.class));
        assertTrue(((MultiSelectionModelImpl<?>) grid.getSelectionModel()).isSelectAllCheckBoxVisible());
        verify(controller, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
    }

    @Test
    @SuppressWarnings("UNCHECKED")
    public void testSelectAllCheckBoxNotVisible() {
        mockStatic(JavaScript.class);
        List<UsageDto> usageDtos = List.of(new UsageDto(), new UsageDto());
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, List.of())).andReturn(usageDtos).once();
        expect(controller.getBeansCount()).andReturn(EXCEEDED_RECORD_THRESHOLD).once();
        expect(controller.getGridRecordThreshold()).andReturn(RECORD_THRESHOLD).once();
        replay(controller, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
        Grid<UsageDto> grid =
            (Grid<UsageDto>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(usageDtos, dataProvider.fetch(new Query<>(0, 2, List.of(), null,
            null)).collect(Collectors.toList()));
        assertEquals(EXCEEDED_RECORD_THRESHOLD, dataProvider.size(new Query<>()));
        assertThat(grid.getSelectionModel(), instanceOf(MultiSelectionModelImpl.class));
        assertFalse(((MultiSelectionModelImpl<?>) grid.getSelectionModel()).isSelectAllCheckBoxVisible());
        verify(controller, RupContextUtils.class, ForeignSecurityUtils.class, JavaScript.class);
    }

    @Test
    public void testGetController() {
        assertSame(controller, usagesWidget.getController());
    }

    @Test
    @SuppressWarnings("UNCHECKED")
    public void testRefresh() {
        DataProvider dataProvider = createMock(DataProvider.class);
        Grid usagesGrid = createMock(Grid.class);
        SalUsageMediator mediator = createMock(SalUsageMediator.class);
        Whitebox.setInternalState(usagesWidget, usagesGrid, CommonUsageWidget.class);
        Whitebox.setInternalState(usagesWidget, mediator);
        usagesGrid.deselectAll();
        expectLastCall().once();
        expect(usagesGrid.getDataProvider()).andReturn(dataProvider).once();
        dataProvider.refreshAll();
        expectLastCall().once();
        replay(dataProvider, usagesGrid, controller, mediator);
        usagesWidget.refresh();
        verify(dataProvider, usagesGrid, controller, mediator);
    }

    @Test
    public void testInitMediator() throws Exception {
        SalUsageMediator mediator = createMock(SalUsageMediator.class);
        expectNew(SalUsageMediator.class).andReturn(mediator).once();
        mediator.setLoadItemBankMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        mediator.setLoadUsageDataMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        mediator.setLoadFundPoolMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        mediator.setUpdateRightsholdersButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setExcludeDetailsButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setAddToScenarioButton(anyObject(Button.class));
        expectLastCall().once();
        replay(SalUsageMediator.class, mediator, controller);
        assertNotNull(usagesWidget.initMediator());
        verify(SalUsageMediator.class, mediator, controller);
    }

    @Test
    public void testUpdateRightsholdersButtonClickListener() {
        mockStatic(Windows.class);
        expect(controller.isValidStatusFilterApplied()).andReturn(true).once();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.getUsageDtosForRhUpdate()).andReturn(List.of(new UsageDto())).once();
        Windows.showModalWindow(anyObject(SalDetailForRightsholderUpdateWindow.class));
        expectLastCall().once();
        replay(controller, Windows.class);
        applyUpdateRightsholdersButtonClick();
        verify(controller, Windows.class);
    }

    @Test
    public void testUpdateRightsholdersButtonClickListenerNoUsages() {
        mockStatic(Windows.class);
        expect(controller.isValidStatusFilterApplied()).andReturn(true).once();
        expect(controller.getBeansCount()).andReturn(0).once();
        Windows.showNotificationWindow("There are no usages for RH update");
        expectLastCall().once();
        replay(controller, Windows.class);
        applyUpdateRightsholdersButtonClick();
        verify(controller, Windows.class);
    }

    @Test
    public void testUpdateRightsholdersButtonClickListenerStatusNotApplied() {
        mockStatic(Windows.class);
        expect(controller.isValidStatusFilterApplied()).andReturn(false).once();
        Windows.showNotificationWindow("RH_NOT_FOUND or WORK_NOT_GRANTED status filter should be applied");
        expectLastCall().once();
        replay(controller, Windows.class);
        applyUpdateRightsholdersButtonClick();
        verify(controller, Windows.class);
    }

    @Test
    public void testExcludeDetailsButtonClickListenerInvalidFilter() {
        mockStatic(Windows.class);
        ISalUsageFilterController salUsageFilterController = createMock(ISalUsageFilterController.class);
        ICommonUsageFilterWidget usageFilterWidget = createMock(ICommonUsageFilterWidget.class);
        expect(controller.getUsageFilterController()).andReturn(salUsageFilterController).once();
        expect(salUsageFilterController.getWidget()).andReturn(usageFilterWidget).once();
        expect(usageFilterWidget.getAppliedFilter()).andReturn(new UsageFilter());
        Windows.showNotificationWindow("Please apply IB Detail Type Filter to exclude details");
        expectLastCall().once();
        replay(controller, salUsageFilterController, usageFilterWidget, Windows.class);
        applyExcludeDetailsButtonClick();
        verify(controller, salUsageFilterController, usageFilterWidget, Windows.class);
    }

    @Test
    public void testExcludeDetailsButtonClickListenerNoSelectedUsages() {
        mockStatic(Windows.class);
        Grid<UsageDto> usagesGrid = createMock(Grid.class);
        Whitebox.setInternalState(usagesWidget, usagesGrid);
        ISalUsageFilterController salUsageFilterController = createMock(ISalUsageFilterController.class);
        ICommonUsageFilterWidget usageFilterWidget = createMock(ICommonUsageFilterWidget.class);
        expect(controller.getUsageFilterController()).andReturn(salUsageFilterController).once();
        expect(salUsageFilterController.getWidget()).andReturn(usageFilterWidget).once();
        UsageFilter filter = new UsageFilter();
        filter.setSalDetailType(SalDetailTypeEnum.IB);
        expect(usageFilterWidget.getAppliedFilter()).andReturn(filter);
        expect(usagesGrid.getSelectedItems()).andReturn(Set.of()).once();
        Windows.showNotificationWindow("Please select at least one usage detail");
        expectLastCall().once();
        replay(controller, salUsageFilterController, usageFilterWidget, usagesGrid, Windows.class);
        applyExcludeDetailsButtonClick();
        verify(controller, salUsageFilterController, usageFilterWidget, usagesGrid, Windows.class);
    }

    @Test
    public void testExcludeDetailsButtonClickListenerValid() {
        mockStatic(Windows.class);
        Grid<UsageDto> usagesGrid = createMock(Grid.class);
        Whitebox.setInternalState(usagesWidget, usagesGrid);
        Capture<IListener> windowListenerCapture = newCapture();
        ISalUsageFilterController salUsageFilterController = createMock(ISalUsageFilterController.class);
        ICommonUsageFilterWidget usageFilterWidget = createMock(ICommonUsageFilterWidget.class);
        expect(controller.getUsageFilterController()).andReturn(salUsageFilterController).once();
        expect(salUsageFilterController.getWidget()).andReturn(usageFilterWidget).once();
        UsageFilter filter = new UsageFilter();
        filter.setSalDetailType(SalDetailTypeEnum.IB);
        expect(usageFilterWidget.getAppliedFilter()).andReturn(filter);
        Set<UsageDto> usages = Set.of(new UsageDto());
        expect(usagesGrid.getSelectedItems()).andReturn(usages).once();
        expect(controller.usageDataExists(usages)).andReturn(false).once();
        expect(Windows.showConfirmDialog(eq("Are you sure you want to exclude selected detail(s)?"),
            capture(windowListenerCapture))).andReturn(new Window()).once();
        controller.excludeUsageDetails(usages);
        expectLastCall().once();
        replay(controller, salUsageFilterController, usageFilterWidget, usagesGrid, Windows.class);
        applyExcludeDetailsButtonClick();
        windowListenerCapture.getValue().onActionConfirmed();
        verify(controller, salUsageFilterController, usageFilterWidget, usagesGrid, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListener() {
        mockStatic(Windows.class);
        prepareCreateScenarioExpectation();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        expect(controller.getProcessingBatchesNames(Set.of(BATCH_ID))).andReturn(List.of()).once();
        expect(controller.getIneligibleBatchesNames(Set.of(BATCH_ID))).andReturn(List.of()).once();
        Windows.showModalWindow(anyObject(CreateSalScenarioWindow.class));
        expectLastCall().once();
        replay(controller, Windows.class);
        applyAddToScenarioButtonClick();
        verify(controller, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithEmptyUsages() {
        mockStatic(Windows.class);
        expect(controller.getBeansCount()).andReturn(0).once();
        prepareCreateScenarioExpectation();
        Windows.showNotificationWindow("Scenario cannot be created. There are no usages to include into scenario");
        expectLastCall().once();
        replay(controller, Windows.class);
        applyAddToScenarioButtonClick();
        verify(controller, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithInvalidStatus() {
        mockStatic(Windows.class);
        expect(controller.getBeansCount()).andReturn(1).once();
        prepareCreateScenarioExpectation();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(false).once();
        Windows.showNotificationWindow("Only usages in ELIGIBLE status can be added to scenario");
        expectLastCall().once();
        replay(controller, Windows.class);
        applyAddToScenarioButtonClick();
        verify(controller, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithInvalidRightsholders() {
        mockStatic(Windows.class);
        expect(controller.getBeansCount()).andReturn(1).once();
        prepareCreateScenarioExpectation();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of(700000000L)).once();
        Windows.showNotificationWindow(
            "Scenario cannot be created. The following rightsholder(s) are absent in PRM: <i><b>[700000000]</b></i>");
        expectLastCall().once();
        replay(controller, Windows.class);
        applyAddToScenarioButtonClick();
        verify(controller, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithEmptyBatchFilter() {
        mockStatic(Windows.class);
        filterWidget.getAppliedFilter().setUsageBatchesIds(Set.of());
        expect(controller.getBeansCount()).andReturn(1).once();
        prepareCreateScenarioExpectation();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        Windows.showNotificationWindow("Please apply Batches Filter to create a scenario");
        expectLastCall().once();
        replay(controller, Windows.class);
        applyAddToScenarioButtonClick();
        verify(controller, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithMultipleBatchFilter() {
        mockStatic(Windows.class);
        filterWidget.getAppliedFilter().setUsageBatchesIds(Set.of(BATCH_ID, "437d1f1d-c165-404d-8617-b10ef53426be"));
        expect(controller.getBeansCount()).andReturn(1).once();
        prepareCreateScenarioExpectation();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        Windows.showNotificationWindow("Only one usage batch can be associated with scenario");
        expectLastCall().once();
        replay(controller, Windows.class);
        applyAddToScenarioButtonClick();
        verify(controller, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithDetailTypeFilter() {
        mockStatic(Windows.class);
        filterWidget.getAppliedFilter().setSalDetailType(SalDetailTypeEnum.IB);
        expect(controller.getBeansCount()).andReturn(1).once();
        prepareCreateScenarioExpectation();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        Windows.showNotificationWindow("Detail Type filter should not be applied to create scenario");
        expectLastCall().once();
        replay(controller, Windows.class);
        applyAddToScenarioButtonClick();
        verify(controller, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithProcessingBatches() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        expect(controller.getProcessingBatchesNames(Set.of(BATCH_ID))).andReturn(List.of("Batch Name")).once();
        prepareCreateScenarioExpectation();
        Windows.showNotificationWindow("Please wait while batch(es) processing is completed:" +
            "<ul><li><i><b>Batch Name</b></i></ul>");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        applyAddToScenarioButtonClick();
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithIneligibleBatches() {
        mockStatic(Windows.class);
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        expect(controller.getProcessingBatchesNames(Set.of(BATCH_ID))).andReturn(List.of()).once();
        expect(controller.getIneligibleBatchesNames(Set.of(BATCH_ID))).andReturn(List.of("Batch Name")).once();
        prepareCreateScenarioExpectation();
        Windows.showNotificationWindow("The following batches have usages that are not in ELIGIBLE status:" +
            "<ul><li><i><b>Batch Name</b></i></ul>");
        expectLastCall().once();
        replay(controller, Windows.class);
        applyAddToScenarioButtonClick();
        verify(controller, Windows.class);
    }

    private void applyUpdateRightsholdersButtonClick() {
        applyButtonClick(2);
    }

    private void applyExcludeDetailsButtonClick() {
        applyButtonClick(3);
    }

    private void applyAddToScenarioButtonClick() {
        applyButtonClick(4);
    }

    private void applyButtonClick(int buttonIndex) {
        Button excludeDetailsButton = (Button) ((HorizontalLayout)
            ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(0)).getComponent(buttonIndex);
        Collection<?> listeners = excludeDetailsButton.getListeners(ClickEvent.class);
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(null);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(6, layout.getComponentCount());
        verifyMenuBar(layout.getComponent(0), "Usage Batch", true,
            List.of("Load Item Bank", "Load Usage Data", "View"));
        verifyMenuBar(layout.getComponent(1), "Fund Pool", true, List.of("Load", "View"));
        Button updateRightsholdersButton = (Button) layout.getComponent(2);
        assertEquals("Update Rightsholders", updateRightsholdersButton.getCaption());
        Button excludeDetailsButton = (Button) layout.getComponent(3);
        assertEquals("Exclude Details", excludeDetailsButton.getCaption());
        Button addToScenarioButton = (Button) layout.getComponent(4);
        assertEquals("Add To Scenario", addToScenarioButton.getCaption());
        Button exportButton = (Button) layout.getComponent(5);
        assertEquals("Export", exportButton.getCaption());
    }

    private void prepareCreateScenarioExpectation() {
        expect(controller.getSelectedProductFamily()).andReturn(SAL_PRODUCT_FAMILY).once();
        expect(controller.getFundPoolsNotAttachedToScenario()).andReturn(List.of(new FundPool())).once();
        expect(controller.scenarioExists(SAL_SCENARIO_NAME_PREFIX + DATE)).andReturn(true).once();
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }
}
