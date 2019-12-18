package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

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
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasNtsUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.FasNtsUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.fas.UsageBatchUploadWindow;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.Extension;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
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
 * Verifies {@link NtsUsageWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({NtsUsageWidget.class, Windows.class, ForeignSecurityUtils.class})
public class NtsUsageWidgetTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private NtsUsageWidget usagesWidget;
    private INtsUsageController controller;
    private IFasNtsUsageFilterWidget filterWidget;
    private String batchId;

    @Before
    public void setUp() {
        controller = createMock(INtsUsageController.class);
        filterWidget = new FasNtsUsageFilterWidget(createMock(IFasNtsUsageFilterController.class));
        batchId = RupPersistUtils.generateUuid();
        filterWidget.getFilter().setUsageBatchesIds(Collections.singleton(batchId));
        usagesWidget = new NtsUsageWidget(controller);
        usagesWidget.setController(controller);
        expect(controller.initUsagesFilterWidget()).andReturn(filterWidget).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).times(2);
        expect(controller.getExportUsagesStreamSource()).andReturn(streamSource).once();
        replay(controller, streamSource);
        usagesWidget.init();
        verify(controller);
        reset(controller, streamSource);
    }

    @Test
    public void testWidgetStructure() {
        assertTrue(usagesWidget.isLocked());
        assertEquals(200, usagesWidget.getSplitPosition(), 0);
        verifySize(usagesWidget);
        assertTrue(usagesWidget.getFirstComponent() instanceof FasNtsUsageFilterWidget);
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
    public void testSelectFundPoolMenuItems() {
        mockStatic(Windows.class);
        mockStatic(ForeignSecurityUtils.class);
        Windows.showModalWindow(anyObject(UsageBatchUploadWindow.class));
        expectLastCall().once();
        expect(ForeignSecurityUtils.hasDeleteUsagePermission()).andReturn(true).once();
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(controller.getUsageBatches(NTS_PRODUCT_FAMILY))
            .andReturn(Collections.singletonList(new UsageBatch())).once();
        Windows.showModalWindow(anyObject(ViewFundPoolWindow.class));
        expectLastCall().once();
        replay(controller, Windows.class, ForeignSecurityUtils.class);
        List<MenuItem> menuItems = getMenuBarItems(0);
        assertEquals(2, CollectionUtils.size(menuItems));
        MenuItem menuItemLoad = menuItems.get(0);
        MenuItem menuItemView = menuItems.get(1);
        menuItemLoad.getCommand().menuSelected(menuItemLoad);
        menuItemView.getCommand().menuSelected(menuItemView);
        verify(controller, Windows.class, ForeignSecurityUtils.class);
    }

    @Test
    public void testSelectAdditionalFundsMenuItems() {
        mockStatic(Windows.class);
        expect(controller.getPreServiceSeeFunds()).andReturn(Collections.emptyList()).once();
        Windows.showModalWindow(anyObject(PreServiceFeeFundBatchesFilterWindow.class));
        expectLastCall().once();
        expect(controller.getUsageBatchesForPreServiceFeeFunds()).andReturn(Collections.emptyList()).once();
        Windows.showModalWindow(anyObject(DeleteAdditionalFundsWindow.class));
        expectLastCall().once();
        replay(controller, Windows.class);
        List<MenuItem> menuItems = getMenuBarItems(1);
        MenuItem menuItemCreate = menuItems.get(0);
        MenuItem menuItemDelete = menuItems.get(1);
        menuItemCreate.getCommand().menuSelected(menuItemCreate);
        menuItemDelete.getCommand().menuSelected(menuItemDelete);
        verify(controller, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListener() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(3);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidUsagesState(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        expect(controller.getProcessingBatchesNames(Collections.singleton(batchId)))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getBatchesNamesToScenariosNames(Collections.singleton(batchId)))
            .andReturn(Collections.emptyMap()).once();
        expect(controller.getBatchNamesWithUnclassifiedWorks(filterWidget.getFilter().getUsageBatchesIds()))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getBatchNamesWithInvalidStmOrNonStmUsagesState(filterWidget.getFilter().getUsageBatchesIds()))
            .andReturn(ImmutableMap.of("STM", Collections.emptyList(), "NON-STM", Collections.emptyList())).once();
        expect(controller.getPreServiceFeeFundsNotAttachedToScenario()).andReturn(Collections.emptyList()).once();
        expect(controller.scenarioExists("NTS Distribution " + DATE)).andReturn(true).once();
        Windows.showModalWindow(anyObject(CreateNtsScenarioWindow.class));
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerProcessingBatches() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(3);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidUsagesState(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        expect(controller.getProcessingBatchesNames(Collections.singleton(batchId)))
            .andReturn(ImmutableList.of("batch name 1", "batch name 2")).once();
        Windows.showNotificationWindow("Please wait while batch(es) processing is completed:" +
            "<ul><li><i><b>batch name 1<br><li>batch name 2</b></i></ul>");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerBatchInScenario() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(3);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidUsagesState(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        expect(controller.getProcessingBatchesNames(Collections.singleton(batchId)))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getBatchesNamesToScenariosNames(Collections.singleton(batchId)))
            .andReturn(ImmutableMap.of("batch name", "scenario name")).once();
        Windows.showNotificationWindow("The following batch(es) already associated with scenario(s):" +
            "<ul><li><i><b>batch name : scenario name</b></i></ul>");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerUnclassifiedUsages() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(3);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidUsagesState(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        expect(controller.getBatchNamesWithUnclassifiedWorks(filterWidget.getFilter().getUsageBatchesIds()))
            .andReturn(Collections.singletonList("Batch with unclassified usages")).once();
        expect(controller.getProcessingBatchesNames(Collections.singleton(batchId)))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getBatchesNamesToScenariosNames(Collections.singleton(batchId)))
            .andReturn(Collections.emptyMap()).once();
        Windows.showNotificationWindow(
            "The following batches have unclassified works:<ul><li><i><b>Batch with unclassified usages</b></i></ul>");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerNoStmRhs() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(3);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidUsagesState(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        expect(controller.getBatchNamesWithUnclassifiedWorks(filterWidget.getFilter().getUsageBatchesIds()))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getProcessingBatchesNames(Collections.singleton(batchId)))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getBatchesNamesToScenariosNames(Collections.singleton(batchId)))
            .andReturn(Collections.emptyMap()).once();
        expect(controller.getBatchNamesWithInvalidStmOrNonStmUsagesState(filterWidget.getFilter().getUsageBatchesIds()))
            .andReturn(ImmutableMap.of("STM", Collections.singletonList("Batch without STM RHs"))).once();
        Windows.showNotificationWindow("There are no STM rightsholders in the following batches: " +
            "<ul><li><i><b>Batch without STM RHs</b></i></ul>");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerNoStmAndNonStmRhs() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(3);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidUsagesState(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        expect(controller.getProcessingBatchesNames(Collections.singleton(batchId)))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getBatchesNamesToScenariosNames(Collections.singleton(batchId)))
            .andReturn(Collections.emptyMap()).once();
        expect(controller.getBatchNamesWithUnclassifiedWorks(filterWidget.getFilter().getUsageBatchesIds()))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getBatchNamesWithInvalidStmOrNonStmUsagesState(filterWidget.getFilter().getUsageBatchesIds()))
            .andReturn(ImmutableMap.of("STM", Collections.singletonList("Batch without STM RHs"),
                "NON-STM", Collections.singletonList("Batch without NON-STM RHs"))).once();
        Windows.showNotificationWindow("There are no STM rightsholders in the following batches: " +
            "<ul><li><i><b>Batch without STM RHs</b></i></ul>There are no NON-STM rightsholders " +
            "in the following batches: <ul><li><i><b>Batch without NON-STM RHs</b></i></ul>");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testRefresh() {
        DataProvider dataProvider = createMock(DataProvider.class);
        NtsUsageMediator mediator = createMock(NtsUsageMediator.class);
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
        NtsUsageMediator mediator = createMock(NtsUsageMediator.class);
        expectNew(NtsUsageMediator.class).andReturn(mediator).once();
        mediator.setAddToScenarioButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setAssignClassificationButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setWithdrawnFundMenuBar(anyObject(MenuBar.class));
        expectLastCall().once();
        mediator.setLoadFundPoolMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        replay(NtsUsageMediator.class, mediator, controller);
        assertNotNull(usagesWidget.initMediator());
        verify(NtsUsageMediator.class, mediator, controller);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(5, layout.getComponentCount());
        verifyMenuBar(layout.getComponent(0), "Fund Pool", Arrays.asList("Load", "View"));
        verifyMenuBar(layout.getComponent(1), "Additional Funds", Arrays.asList("Create", "Delete"));
        assertEquals("Assign Classification", layout.getComponent(2).getCaption());
        assertEquals("Add To Scenario", layout.getComponent(3).getCaption());
        Component component = layout.getComponent(4);
        assertEquals("Export", component.getCaption());
        Collection<Extension> extensions = component.getExtensions();
        assertTrue(CollectionUtils.isNotEmpty(extensions));
        assertEquals(1, extensions.size());
        assertTrue(extensions.iterator().next() instanceof OnDemandFileDownloader);
    }

    private void verifyMenuBar(Component component, String menuBarName, List<String> menuItems) {
        assertTrue(component instanceof MenuBar);
        MenuBar menuBar = (MenuBar) component;
        List<MenuItem> parentItems = menuBar.getItems();
        assertEquals(1, parentItems.size());
        MenuItem item = parentItems.get(0);
        assertEquals(menuBarName, item.getText());
        List<MenuItem> childItems = item.getChildren();
        assertEquals(CollectionUtils.size(menuItems), CollectionUtils.size(childItems));
        IntStream.range(0, menuItems.size())
            .forEach(index -> assertEquals(menuItems.get(index), childItems.get(index).getText()));
    }

    private List<MenuItem> getMenuBarItems(int menuBarIndex) {
        HorizontalLayout buttonsBar =
            (HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(0);
        MenuBar menuBar = (MenuBar) buttonsBar.getComponent(menuBarIndex);
        return menuBar.getItems().get(0).getChildren();
    }

    private void verifyGrid(Grid grid) {
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Detail ID", "Detail Status", "Product Family", "Usage Batch Name",
            "RRO Account #", "RRO Name", "RH Account #", "RH Name", "Wr Wrk Inst", "System Title", "Standard Number",
            "Standard Number Type", "Fiscal Year", "Payment Date", "Title", "Article", "Publisher", "Pub Date",
            "Number of Copies", "Reported Value", "Amt in USD", "Market", "Market Period From", "Market Period To",
            "Author", "Comment"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        verifySize(grid);
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }
}
