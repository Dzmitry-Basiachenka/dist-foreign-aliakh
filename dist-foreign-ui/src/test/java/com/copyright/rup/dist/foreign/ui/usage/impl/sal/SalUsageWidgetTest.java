package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
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
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
@PrepareForTest({SalUsageWidget.class, Windows.class})
public class SalUsageWidgetTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String BATCH_ID = "3a070817-03ae-4ebd-b25f-dd3168a7ffb0";
    private static final String SAL_SCENARIO_NAME_PREFIX = "SAL Distribution ";
    private static final String SAL_PRODUCT_FAMILY = "SAL";
    private SalUsageWidget usagesWidget;
    private ISalUsageController controller;
    private SalUsageFilterWidget filterWidget;

    @Before
    public void setUp() {
        controller = createMock(ISalUsageController.class);
        usagesWidget = new SalUsageWidget(controller);
        usagesWidget.setController(controller);
        filterWidget = new SalUsageFilterWidget(createMock(ISalUsageFilterController.class));
        filterWidget.getAppliedFilter().setUsageBatchesIds(Collections.singleton(BATCH_ID));
        expect(controller.initUsagesFilterWidget()).andReturn(filterWidget).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getExportUsagesStreamSource()).andReturn(streamSource).once();
        replay(controller, streamSource);
        usagesWidget.init();
        verify(controller, streamSource);
        reset(controller);
    }

    @Test
    public void testWidgetStructure() {
        assertTrue(usagesWidget.isLocked());
        assertEquals(200, usagesWidget.getSplitPosition(), 0);
        verifySize(usagesWidget);
        assertTrue(usagesWidget.getFirstComponent() instanceof SalUsageFilterWidget);
        Component secondComponent = usagesWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0));
        verifyGrid((Grid) layout.getComponent(1));
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    public void testGetController() {
        assertSame(controller, usagesWidget.getController());
    }

    @Test
    public void testRefresh() {
        DataProvider dataProvider = createMock(DataProvider.class);
        SalUsageMediator mediator = createMock(SalUsageMediator.class);
        Whitebox.setInternalState(usagesWidget, dataProvider);
        Whitebox.setInternalState(usagesWidget, mediator);
        dataProvider.refreshAll();
        expectLastCall().once();
        replay(dataProvider, controller, mediator);
        usagesWidget.refresh();
        verify(dataProvider, controller, mediator);
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
        mediator.setAddToScenarioButton(anyObject(Button.class));
        expectLastCall().once();
        replay(SalUsageMediator.class, mediator, controller);
        assertNotNull(usagesWidget.initMediator());
        verify(SalUsageMediator.class, mediator, controller);
    }

    @Test
    public void testUpdateRightsholdersButtonClickListener() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.isValidStatusFilterApplied()).andReturn(true).once();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.getUsageDtosForRhUpdate()).andReturn(Lists.newArrayList(new UsageDto()));
        Windows.showModalWindow(anyObject(SalDetailForRightsholderUpdateWindow.class));
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Button updateRightsholdersButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
                .getComponent(0)).getComponent(2);
        Collection<?> listeners = updateRightsholdersButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testUpdateRightsholdersButtonClickListenerNoUsages() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.isValidStatusFilterApplied()).andReturn(true).once();
        expect(controller.getBeansCount()).andReturn(0).once();
        Windows.showNotificationWindow("There are no usages for RH update");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Button updateRightsholdersButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
                .getComponent(0)).getComponent(2);
        Collection<?> listeners = updateRightsholdersButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testUpdateRightsholdersButtonClickListenerStatusNotApplied() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.isValidStatusFilterApplied()).andReturn(false).once();
        Windows.showNotificationWindow("RH_NOT_FOUND or WORK_NOT_GRANTED status filter should be applied");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Button updateRightsholdersButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
                .getComponent(0)).getComponent(2);
        Collection<?> listeners = updateRightsholdersButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListener() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        prepareCreateScenarioExpectation();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.areValidFilteredUsageStatuses(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        expect(controller.getProcessingBatchesNames(Collections.singleton(BATCH_ID)))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getIneligibleBatchesNames(Collections.singleton(BATCH_ID)))
            .andReturn(Collections.emptyList()).once();
        Windows.showModalWindow(anyObject(CreateSalScenarioWindow.class));
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        applyAddToScenarioButtonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithEmptyUsages() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.getBeansCount()).andReturn(0).once();
        prepareCreateScenarioExpectation();
        Windows.showNotificationWindow("Scenario cannot be created. There are no usages to include into scenario");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        applyAddToScenarioButtonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithInvalidStatus() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.getBeansCount()).andReturn(1).once();
        prepareCreateScenarioExpectation();
        expect(controller.areValidFilteredUsageStatuses(UsageStatusEnum.ELIGIBLE)).andReturn(false).once();
        Windows.showNotificationWindow("Only usages in ELIGIBLE status can be added to scenario");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        applyAddToScenarioButtonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithInvalidRightsholders() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.getBeansCount()).andReturn(1).once();
        prepareCreateScenarioExpectation();
        expect(controller.areValidFilteredUsageStatuses(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.singletonList(700000000L)).once();
        Windows.showNotificationWindow(
            "Scenario cannot be created. The following rightsholder(s) are absent in PRM: <i><b>[700000000]</b></i>");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        applyAddToScenarioButtonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithEmptyBatchFilter() {
        mockStatic(Windows.class);
        filterWidget.getAppliedFilter().setUsageBatchesIds(Collections.emptySet());
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.getBeansCount()).andReturn(1).once();
        prepareCreateScenarioExpectation();
        expect(controller.areValidFilteredUsageStatuses(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        Windows.showNotificationWindow("Please apply Batches Filter to create a scenario");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        applyAddToScenarioButtonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithMultipleBatchFilter() {
        mockStatic(Windows.class);
        filterWidget.getAppliedFilter()
            .setUsageBatchesIds(Sets.newHashSet(BATCH_ID, "437d1f1d-c165-404d-8617-b10ef53426be"));
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.getBeansCount()).andReturn(1).once();
        prepareCreateScenarioExpectation();
        expect(controller.areValidFilteredUsageStatuses(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        Windows.showNotificationWindow("Only one usage batch can be associated with scenario");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        applyAddToScenarioButtonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithDetailTypeFilter() {
        mockStatic(Windows.class);
        filterWidget.getAppliedFilter().setSalDetailType(SalDetailTypeEnum.IB);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.getBeansCount()).andReturn(1).once();
        prepareCreateScenarioExpectation();
        expect(controller.areValidFilteredUsageStatuses(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        Windows.showNotificationWindow("Detail Type filter should not be applied to create scenario");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        applyAddToScenarioButtonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithProcessingBatches() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.areValidFilteredUsageStatuses(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        expect(controller.getProcessingBatchesNames(Collections.singleton(BATCH_ID)))
            .andReturn(Collections.singletonList("Batch Name")).once();
        prepareCreateScenarioExpectation();
        Windows.showNotificationWindow("Please wait while batch(es) processing is completed:" +
            "<ul><li><i><b>Batch Name</b></i></ul>");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        applyAddToScenarioButtonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithIneligibleBatches() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.areValidFilteredUsageStatuses(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        expect(controller.getProcessingBatchesNames(Collections.singleton(BATCH_ID)))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getIneligibleBatchesNames(Collections.singleton(BATCH_ID)))
            .andReturn(Collections.singletonList("Batch Name")).once();
        prepareCreateScenarioExpectation();
        Windows.showNotificationWindow("The following batches have usages that are not in ELIGIBLE status:" +
            "<ul><li><i><b>Batch Name</b></i></ul>");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        applyAddToScenarioButtonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    private void applyAddToScenarioButtonClick(ClickEvent event) {
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(3);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(event);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(5, layout.getComponentCount());
        verifyMenuBar(layout.getComponent(0), "Usage Batch",
            Arrays.asList("Load Item Bank", "Load Usage Data", "View"));
        verifyMenuBar(layout.getComponent(1), "Fund Pool", Arrays.asList("Load", "View"));
        Button updateRightsholdersButton = (Button) layout.getComponent(2);
        assertEquals("Update Rightsholders", updateRightsholdersButton.getCaption());
        Button addToScenarioButton = (Button) layout.getComponent(3);
        assertEquals("Add To Scenario", addToScenarioButton.getCaption());
        Button exportButton = (Button) layout.getComponent(4);
        assertEquals("Export", exportButton.getCaption());
    }

    private void verifyMenuBar(Component component, String menuName, List<String> menuItems) {
        assertTrue(component instanceof MenuBar);
        MenuBar menuBar = (MenuBar) component;
        List<MenuItem> parentItems = menuBar.getItems();
        assertEquals(1, parentItems.size());
        MenuItem item = parentItems.get(0);
        assertEquals(menuName, item.getText());
        List<MenuItem> childItems = item.getChildren();
        assertEquals(CollectionUtils.size(menuItems), CollectionUtils.size(childItems));
        IntStream.range(0, menuItems.size())
            .forEach(index -> assertEquals(menuItems.get(index), childItems.get(index).getText()));
    }

    private void verifyGrid(Grid grid) {
        List<Grid.Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Detail ID", "Detail Status", "Detail Type", "Product Family", "Usage Batch Name",
            "Period End Date", "Licensee Account #", "Licensee Name", "RH Account #", "RH Name", "Wr Wrk Inst",
            "System Title", "Standard Number", "Standard Number Type", "Assessment Name", "Assessment Type",
            "Date of Scored Assessment", "Reported Work Portion ID", "Reported Title",
            "Reported Article or Chapter Title", "Reported Standard Number", "Reported Author", "Reported Publisher",
            "Reported Publication Date", "Reported Page Range", "Reported Vol/Number/Series", "Reported Media Type",
            "Coverage Year", "Question Identifier", "Grade", "Grade Group", "States", "Number of Views", "Comment"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
        verifySize(grid);
    }

    private void prepareCreateScenarioExpectation() {
        expect(controller.getSelectedProductFamily()).andReturn(SAL_PRODUCT_FAMILY).once();
        expect(controller.getFundPoolsNotAttachedToScenario())
            .andReturn(Collections.singletonList(new FundPool())).once();
        expect(controller.scenarioExists(SAL_SCENARIO_NAME_PREFIX + DATE)).andReturn(true).once();
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }
}
