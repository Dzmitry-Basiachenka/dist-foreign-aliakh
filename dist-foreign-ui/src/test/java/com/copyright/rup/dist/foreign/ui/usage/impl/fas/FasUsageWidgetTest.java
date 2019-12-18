package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

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
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.FasNtsUsageFilterWidget;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.Windows;

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
 * Verifies {@link FasUsageWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FasUsageWidget.class, Windows.class, ForeignSecurityUtils.class})
public class FasUsageWidgetTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private FasUsageWidget usagesWidget;
    private IFasUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(IFasUsageController.class);
        FasNtsUsageFilterWidget filterWidget =
            new FasNtsUsageFilterWidget(createMock(IFasNtsUsageFilterController.class));
        filterWidget.getFilter().setUsageBatchesIds(Collections.singleton("3a070817-03ae-4ebd-b25f-dd3168a7ffb0"));
        usagesWidget = new FasUsageWidget(controller);
        usagesWidget.setController(controller);
        expect(controller.initUsagesFilterWidget()).andReturn(filterWidget).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).times(2);
        expect(controller.getExportUsagesStreamSource()).andReturn(streamSource).once();
        expect(controller.getSendForResearchUsagesStreamSource()).andReturn(streamSource).once();
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
    public void testSelectUsageBatchMenuItems() {
        mockStatic(Windows.class);
        mockStatic(ForeignSecurityUtils.class);
        Windows.showModalWindow(anyObject(UsageBatchUploadWindow.class));
        expectLastCall().once();
        expect(ForeignSecurityUtils.hasDeleteUsagePermission()).andReturn(true).once();
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.getUsageBatches(FAS_PRODUCT_FAMILY))
            .andReturn(Collections.singletonList(new UsageBatch())).once();
        Windows.showModalWindow(anyObject(ViewUsageBatchWindow.class));
        expectLastCall().once();
        replay(controller, Windows.class, ForeignSecurityUtils.class);
        List<MenuItem> menuItems = getUsageBatchMenuBarItems();
        assertEquals(2, CollectionUtils.size(menuItems));
        MenuItem menuItemLoad = menuItems.get(0);
        MenuItem menuItemView = menuItems.get(1);
        menuItemLoad.getCommand().menuSelected(menuItemLoad);
        menuItemView.getCommand().menuSelected(menuItemView);
        verify(controller, Windows.class, ForeignSecurityUtils.class);
    }

    @Test
    public void testLoadResearchedUsagesButtonClickListener() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button loadResearchedUsagesButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
                .getComponent(0)).getComponent(2);
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
    public void testAddToScenarioButtonEmptyUsagesTableClickListener() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(3);
        assertTrue(addToScenarioButton.isDisableOnClick());
        Windows.showNotificationWindow("Scenario cannot be created. There are no usages to include into scenario");
        expectLastCall().once();
        expect(controller.getBeansCount()).andReturn(0).once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonInvalidFilterSelectedClickListener() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(3);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidUsagesState(UsageStatusEnum.ELIGIBLE)).andReturn(false).once();
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
            .getComponent(0)).getComponent(3);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidUsagesState(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.singletonList(1000000001L)).once();
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
            .getComponent(0)).getComponent(3);
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidUsagesState(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.scenarioExists("FAS Distribution " + DATE)).andReturn(true).once();
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
    public void testSendForResearchInvalidUsagesState() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button sendForResearchButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(1);
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
    public void testRefresh() {
        DataProvider dataProvider = createMock(DataProvider.class);
        FasUsageMediator mediator = createMock(FasUsageMediator.class);
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
        FasUsageMediator mediator = createMock(FasUsageMediator.class);
        expectNew(FasUsageMediator.class).andReturn(mediator).once();
        mediator.setLoadResearchedUsagesButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setAddToScenarioButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setSendForResearchButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setLoadUsageBatchMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        replay(FasUsageMediator.class, mediator, controller);
        assertNotNull(usagesWidget.initMediator());
        verify(FasUsageMediator.class, mediator, controller);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(5, layout.getComponentCount());
        verifyUsageBatchMenuBar(layout.getComponent(0), "Usage Batch", Arrays.asList("Load", "View"));
        assertEquals("Send for Research", layout.getComponent(1).getCaption());
        assertEquals("Load Researched Details", layout.getComponent(2).getCaption());
        assertEquals("Add To Scenario", layout.getComponent(3).getCaption());
        Component component = layout.getComponent(4);
        assertEquals("Export", component.getCaption());
        Collection<Extension> extensions = component.getExtensions();
        assertTrue(CollectionUtils.isNotEmpty(extensions));
        assertEquals(1, extensions.size());
        assertTrue(extensions.iterator().next() instanceof OnDemandFileDownloader);
    }

    private void verifyUsageBatchMenuBar(Component component, String menuBarName, List<String> menuItems) {
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

    private List<MenuItem> getUsageBatchMenuBarItems() {
        HorizontalLayout buttonsBar =
            (HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(0);
        MenuBar menuBar = (MenuBar) buttonsBar.getComponent(0);
        return menuBar.getItems().get(0).getChildren();
    }

    private void verifyGrid(Grid grid) {
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Detail ID", "Detail Status", "Product Family", "Usage Batch Name",
            "RRO Account #", "RRO Name", "RH Account #", "RH Name", "Wr Wrk Inst", "System Title", "Standard Number",
            "Standard Number Type", "Fiscal Year", "Payment Date", "Title", "Article", "Publisher", "Pub Date",
            "Number of Copies", "Reported Value", "Amt in USD", "Batch Amt in USD", "Market", "Market Period From",
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
