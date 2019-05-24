package com.copyright.rup.dist.foreign.ui.usage.impl;

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

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Verifies {@link UsagesWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 1/18/17
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({UsagesWidget.class, Windows.class})
public class UsagesWidgetTest {

    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private UsagesWidget usagesWidget;
    private IUsagesController controller;
    private UsagesFilterWidget filterWidget;
    private String batchId;

    @Before
    public void setUp() {
        controller = createMock(IUsagesController.class);
        filterWidget = new UsagesFilterWidget();
        batchId = RupPersistUtils.generateUuid();
        filterWidget.getFilter().setUsageBatchesIds(Collections.singleton(batchId));
        usagesWidget = new UsagesWidget();
        usagesWidget.setController(controller);
        expect(controller.initUsagesFilterWidget()).andReturn(filterWidget).once();
        expect(controller.getExportUsagesStreamSource()).andReturn(createMock(IStreamSource.class)).once();
        expect(controller.getSendForResearchUsagesStreamSource()).andReturn(createMock(IStreamSource.class)).once();
        replay(controller);
        usagesWidget.init();
        verify(controller);
        reset(controller);
    }

    @Test
    public void testWidgetStructure() {
        assertTrue(usagesWidget.isLocked());
        assertEquals(200, usagesWidget.getSplitPosition(), 0);
        verifySize(usagesWidget);
        assertTrue(usagesWidget.getFirstComponent() instanceof UsagesFilterWidget);
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
    public void testSelectUsageBatchMenuItems() {
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(UsageBatchUploadWindow.class));
        expectLastCall().once();
        replay(controller, Windows.class);
        List<MenuItem> menuItems = getMenuBarItems(0);
        MenuItem menuItemLoad = menuItems.get(0);
        menuItemLoad.getCommand().menuSelected(menuItemLoad);
        verify(controller, Windows.class);
    }

    @Test
    public void testSelectFundPoolMenuItems() {
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(UsageBatchUploadWindow.class));
        expectLastCall().once();
        replay(controller, Windows.class);
        List<MenuItem> menuItems = getMenuBarItems(1);
        MenuItem menuItemLoad = menuItems.get(0);
        menuItemLoad.getCommand().menuSelected(menuItemLoad);
        verify(controller, Windows.class);
    }

    @Test
    public void testSelectAdditionalFundsMenuItems() {
        mockStatic(Windows.class);
        expect(controller.getUsageBatchesForPreServiceFeeFunds()).andReturn(Collections.emptyList()).once();
        Windows.showModalWindow(anyObject(PreServiceFeeFundBatchesFilterWindow.class));
        expectLastCall().once();
        expect(controller.getPreServiceSeeFunds()).andReturn(Collections.emptyList()).once();
        Windows.showModalWindow(anyObject(DeleteAdditionalFundsWindow.class));
        expectLastCall().once();
        replay(controller, Windows.class);
        List<MenuItem> menuItems = getMenuBarItems(2);
        MenuItem menuItemCreate = menuItems.get(0);
        MenuItem menuItemDelete = menuItems.get(1);
        menuItemCreate.getCommand().menuSelected(menuItemCreate);
        menuItemDelete.getCommand().menuSelected(menuItemDelete);
        verify(controller, Windows.class);
    }

    @Test
    public void testLoadResearchedUsagesButtonClickListener() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button loadResearchedUsagesButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
                .getComponent(0)).getComponent(5);
        assertTrue(loadResearchedUsagesButton.isDisableOnClick());
        Windows.showModalWindow(anyObject(ResearchedUsagesUploadWindow.class));
        expectLastCall().once();
        replay(clickEvent, Windows.class, controller);
        Collection<?> listeners = loadResearchedUsagesButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, Windows.class, controller);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAddToScenarioButtonEmptyUsagesTableClickListener() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(6);
        assertTrue(addToScenarioButton.isDisableOnClick());
        Windows.showNotificationWindow("Scenario cannot be created. There are no usages to include into scenario");
        expectLastCall().once();
        expect(controller.getSize()).andReturn(0).once();
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAddToScenarioButtonInvalidFilterSelectedClickListener() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(6);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getSize()).andReturn(1).once();
        expect(controller.isValidUsagesState(UsageStatusEnum.ELIGIBLE)).andReturn(false).once();
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        Windows.showNotificationWindow("Only usages in ELIGIBLE status can be added to scenario");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerInvalidRightsholders() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(6);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getSize()).andReturn(1).once();
        expect(controller.isValidUsagesState(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.singletonList(1000000001L)).once();
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        Windows.showNotificationWindow("Scenario cannot be created. The following rightsholder(s) are absent " +
            "in PRM: <i><b>[1000000001]</b></i>");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerFasProductFamily() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(6);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getSize()).andReturn(1).once();
        expect(controller.isValidUsagesState(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).times(2);
        expect(controller.getScenarioService()).andReturn(null).once();
        Windows.showModalWindow(anyObject(CreateScenarioWindow.class));
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerNtsProductFamily() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(6);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getSize()).andReturn(1).once();
        expect(controller.isValidUsagesState(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        expect(controller.getProcessingBatchesNames(Collections.singleton(batchId)))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getBatchesNamesToScenariosNames(Collections.singleton(batchId)))
            .andReturn(Collections.emptyMap()).once();
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).times(2);
        expect(controller.getBatchNamesWithUnclassifiedWorks(filterWidget.getFilter().getUsageBatchesIds()))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getBatchNamesWithInvalidStmOrNonStmUsagesState(filterWidget.getFilter().getUsageBatchesIds()))
            .andReturn(ImmutableMap.of("STM", Collections.emptyList(), "NON-STM", Collections.emptyList())).once();
        expect(controller.getScenarioService()).andReturn(null).once();
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
    public void testAddToScenarioButtonClickListenerNtsProductFamilyProcessingBatches() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(6);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(controller.getSize()).andReturn(1).once();
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
    public void testAddToScenarioButtonClickListenerNtsProductFamilyBatchInScenario() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(6);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(controller.getSize()).andReturn(1).once();
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
    public void testAddToScenarioButtonClickListenerNtsProductFamilyUnclassifiedUsages() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(6);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(controller.getSize()).andReturn(1).once();
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
    public void testAddToScenarioButtonClickListenerNtsProductFamilyNoStmRhs() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(6);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(controller.getSize()).andReturn(1).once();
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
    public void testAddToScenarioButtonClickListenerNtsProductFamilyNoStmAndNonStmRhs() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(6);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(controller.getSize()).andReturn(1).once();
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
    public void testSendForResearchInvalidUsagesState() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button sendForResearchButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(4);
        expect(controller.isValidUsagesState(UsageStatusEnum.WORK_NOT_FOUND)).andReturn(false).once();
        Windows.showNotificationWindow("Only usages in WORK_NOT_FOUND status can be sent for research");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = sendForResearchButton.getListeners(ClickEvent.class);
        assertEquals(1, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testDeleteButtonClickListener() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button deleteButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(8);
        assertTrue(deleteButton.isDisableOnClick());
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.getUsageBatches(FAS_PRODUCT_FAMILY)).andReturn(Collections.emptyList()).once();
        Windows.showModalWindow(anyObject(DeleteUsageBatchWindow.class));
        expectLastCall().once();
        replay(clickEvent, Windows.class, controller);
        Collection<?> listeners = deleteButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, Windows.class, controller);
    }

    @Test
    public void testRefresh() {
        DataProvider dataProvider = createMock(DataProvider.class);
        UsagesMediator mediator = createMock(UsagesMediator.class);
        Whitebox.setInternalState(usagesWidget, dataProvider);
        Whitebox.setInternalState(usagesWidget, mediator);
        expect(controller.getSelectedProductFamily()).andReturn("NTS").once();
        mediator.onProductFamilyChanged("NTS");
        expectLastCall().once();
        dataProvider.refreshAll();
        expectLastCall().once();
        replay(dataProvider, controller, mediator);
        usagesWidget.refresh();
        verify(dataProvider, controller, mediator);
    }

    @Test
    public void testInitMediator() throws Exception {
        UsagesMediator mediator = createMock(UsagesMediator.class);
        expectNew(UsagesMediator.class).andReturn(mediator).once();
        mediator.setDeleteUsageButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setUsageBatchMenuBar(anyObject(MenuBar.class));
        expectLastCall().once();
        mediator.setFundPoolMenuBar(anyObject(MenuBar.class));
        expectLastCall().once();
        mediator.setLoadResearchedUsagesButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setAddToScenarioButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setSendForResearchButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setAssignClassificationButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setWithdrawnFundMenuBar(anyObject(MenuBar.class));
        expectLastCall().once();
        mediator.setLoadUsageBatchMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        mediator.setLoadFundPoolMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        replay(UsagesMediator.class, mediator, controller);
        assertNotNull(usagesWidget.initMediator());
        verify(UsagesMediator.class, mediator, controller);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(9, layout.getComponentCount());
        verifyMenuBar(layout.getComponent(0), "Usage Batch", Arrays.asList("Load", "View"));
        verifyMenuBar(layout.getComponent(1), "Fund Pool", Arrays.asList("Load", "View"));
        verifyMenuBar(layout.getComponent(2), "Additional Funds", Arrays.asList("Create", "Delete"));
        assertEquals("Assign Classification", layout.getComponent(3).getCaption());
        assertEquals("Send for Research", layout.getComponent(4).getCaption());
        assertEquals("Load Researched Details", layout.getComponent(5).getCaption());
        assertEquals("Add To Scenario", layout.getComponent(6).getCaption());
        Component component = layout.getComponent(7);
        assertEquals("Export", component.getCaption());
        Collection<Extension> extensions = component.getExtensions();
        assertTrue(CollectionUtils.isNotEmpty(extensions));
        assertEquals(1, extensions.size());
        assertTrue(extensions.iterator().next() instanceof OnDemandFileDownloader);
        assertEquals("Delete Usage Batch", layout.getComponent(8).getCaption());
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
            "Fiscal Year", "RRO Account #", "RRO Name", "Payment Date", "Title", "Article", "Standard Number",
            "Standard Number Type", "Wr Wrk Inst", "System Title", "RH Account #", "RH Name", "Publisher", "Pub Date",
            "Number of Copies", "Reported Value", "Amt in USD", "Gross Amt in USD", "Market", "Market Period From",
            "Market Period To", "Author", "Comment"),
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
