package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
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
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageFilterController;
import com.copyright.rup.vaadin.ui.component.window.NotificationWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.Capture;
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
 * Verifies {@link AaclUsageWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AaclUsageWidget.class, Windows.class, ForeignSecurityUtils.class, ViewAaclFundPoolWindow.class})
public class AaclUsageWidgetTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String BATCH_ID = "3a070817-03ae-4ebd-b25f-dd3168a7ffb0";

    private AaclUsageWidget usagesWidget;
    private IAaclUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(IAaclUsageController.class);
        AaclUsageFilterWidget filterWidget =
            new AaclUsageFilterWidget(createMock(IAaclUsageFilterController.class));
        filterWidget.getAppliedFilter().setUsageBatchesIds(Collections.singleton(BATCH_ID));
        usagesWidget = new AaclUsageWidget(controller);
        usagesWidget.setController(controller);
        expect(controller.initUsagesFilterWidget()).andReturn(filterWidget).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).times(2);
        expect(controller.getExportUsagesStreamSource()).andReturn(streamSource).once();
        expect(controller.getSendForClassificationUsagesStreamSource()).andReturn(streamSource).once();
        replay(controller, streamSource);
        usagesWidget.init();
        verify(controller, streamSource);
        reset(controller, streamSource);
    }

    @Test
    public void testWidgetStructure() {
        assertTrue(usagesWidget.isLocked());
        assertEquals(200, usagesWidget.getSplitPosition(), 0);
        verifySize(usagesWidget);
        assertTrue(usagesWidget.getFirstComponent() instanceof AaclUsageFilterWidget);
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
    public void testAddToScenarioButtonClickListener() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(4);
        expect(controller.getSelectedProductFamily()).andReturn("AACL").once();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        expect(controller.getProcessingBatchesNames(Collections.singleton(BATCH_ID)))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getBatchesNamesToScenariosNames(Collections.singleton(BATCH_ID)))
            .andReturn(Collections.emptyMap()).once();
        expect(controller.getIneligibleBatchesNames(Collections.singleton(BATCH_ID)))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getFundPoolsNotAttachedToScenario())
            .andReturn(Collections.singletonList(new FundPool())).once();
        expect(controller.getUsageAges()).andReturn(Collections.singletonList(new UsageAge())).once();
        expect(controller.getPublicationTypes()).andReturn(Collections.singletonList(new PublicationType())).once();
        expect(controller.getDetailLicenseeClasses())
            .andReturn(Collections.singletonList(new DetailLicenseeClass())).once();
        expect(controller.scenarioExists("AACL Distribution " + DATE)).andReturn(true).once();
        Windows.showModalWindow(anyObject(CreateAaclScenarioWindow.class));
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithEmptyUsages() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(4);
        expect(controller.getBeansCount()).andReturn(0).once();
        Windows.showNotificationWindow("Scenario cannot be created. There are no usages to include into scenario");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithInvalidStatus() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(4);
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(false).once();
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
    public void testAddToScenarioButtonClickListenerWithInvalidRightsholders() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(4);
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.singletonList(700000000L)).once();
        Windows.showNotificationWindow(
            "Scenario cannot be created. The following rightsholder(s) are absent in PRM: <i><b>[700000000]</b></i>");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithProcessingBatches() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(4);
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        expect(controller.getProcessingBatchesNames(Collections.singleton(BATCH_ID)))
            .andReturn(Collections.singletonList("Batch Name")).once();
        Windows.showNotificationWindow("Please wait while batch(es) processing is completed:" +
            "<ul><li><i><b>Batch Name</b></i></ul>");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithAttachedToScenarioBatches() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(4);
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        expect(controller.getProcessingBatchesNames(Collections.singleton(BATCH_ID)))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getBatchesNamesToScenariosNames(Collections.singleton(BATCH_ID)))
            .andReturn(Collections.singletonMap("Batch Name", "Scenario Name")).once();
        Windows.showNotificationWindow("The following batch(es) already associated with scenario(s):" +
            "<ul><li><i><b>Batch Name : Scenario Name</b></i></ul>");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithIneligibleBatches() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(4);
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        expect(controller.getProcessingBatchesNames(Collections.singleton(BATCH_ID)))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getBatchesNamesToScenariosNames(Collections.singleton(BATCH_ID)))
            .andReturn(Collections.emptyMap()).once();
        expect(controller.getIneligibleBatchesNames(Collections.singleton(BATCH_ID)))
            .andReturn(Collections.singletonList("Batch Name")).once();
        Windows.showNotificationWindow("The following batches have usages that are not in ELIGIBLE status:" +
            "<ul><li><i><b>Batch Name</b></i></ul>");
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
        AaclUsageMediator mediator = createMock(AaclUsageMediator.class);
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
        AaclUsageMediator mediator = createMock(AaclUsageMediator.class);
        expectNew(AaclUsageMediator.class).andReturn(mediator).once();
        mediator.setLoadUsageBatchMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        mediator.setLoadFundPoolMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        mediator.setSendForClassificationButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setLoadClassifiedUsagesButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setAddToScenarioButton(anyObject(Button.class));
        expectLastCall().once();
        replay(AaclUsageMediator.class, mediator, controller);
        assertNotNull(usagesWidget.initMediator());
        verify(AaclUsageMediator.class, mediator, controller);
    }

    @Test
    public void testSendForClassification() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        CloseEvent closeEvent = createMock(CloseEvent.class);
        Capture<Window> notificationWindowCapture = new Capture<>();
        Button sendForClassificationButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
                .getComponent(0)).getComponent(2);
        expect(controller.isValidForClassification()).andReturn(true).once();
        Windows.showModalWindow(capture(notificationWindowCapture));
        expectLastCall().once();
        controller.clearFilter();
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = sendForClassificationButton.getListeners(ClickEvent.class);
        assertEquals(1, listeners.size());
        ((ClickListener) listeners.iterator().next()).buttonClick(clickEvent);
        Window notificationWindow = notificationWindowCapture.getValue();
        assertTrue(notificationWindow instanceof NotificationWindow);
        assertEquals("File download is in progress. Please wait",
            ((Label) ((VerticalLayout) notificationWindow.getContent()).getComponent(0)).getValue());
        Collection<?> notificationWindowListeners = notificationWindow.getListeners(CloseEvent.class);
        assertEquals(1, notificationWindowListeners.size());
        ((CloseListener) notificationWindowListeners.iterator().next()).windowClose(closeEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testViewFundPool() throws Exception {
        mockStatic(Windows.class);
        MenuItem viewFundPoolMenuItem =
            ((MenuBar) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
                .getComponent(0)).getComponent(1)).getItems().get(0).getChildren().get(1);
        ViewAaclFundPoolWindow viewFundPoolWindowMock = createMock(ViewAaclFundPoolWindow.class);
        expectNew(ViewAaclFundPoolWindow.class, controller).andReturn(viewFundPoolWindowMock).once();
        Windows.showModalWindow(viewFundPoolWindowMock);
        expectLastCall().once();
        replay(controller, Windows.class, ViewAaclFundPoolWindow.class);
        viewFundPoolMenuItem.getCommand().menuSelected(viewFundPoolMenuItem);
        verify(controller, Windows.class, ViewAaclFundPoolWindow.class);
    }

    @Test
    public void testSendForClassificationInvalidUsagesState() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button sendForClassificationButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
                .getComponent(0)).getComponent(2);
        expect(controller.isValidForClassification()).andReturn(false).once();
        Windows.showNotificationWindow("Only non baseline usages in RH_FOUND status can be sent for classification");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = sendForClassificationButton.getListeners(ClickEvent.class);
        assertEquals(1, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testLoadClassifiedUsagesButtonClickListener() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button loadClassifiedUsagesButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
                .getComponent(0)).getComponent(3);
        Windows.showModalWindow(anyObject(ClassifiedUsagesUploadWindow.class));
        expectLastCall().once();
        replay(clickEvent, Windows.class, controller);
        Collection<?> listeners = loadClassifiedUsagesButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, Windows.class, controller);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(6, layout.getComponentCount());
        verifyUsageBatchMenuBar(layout.getComponent(0), "Usage Batch", Arrays.asList("Load", "View"));
        verifyFundPoolMenuBar(layout.getComponent(1), "Fund Pool", Arrays.asList("Load", "View"));
        Button sendForClassificationButton = (Button) layout.getComponent(2);
        assertEquals("Send for Classification", sendForClassificationButton.getCaption());
        Button loadClassifiedButton = (Button) layout.getComponent(3);
        assertEquals("Load Classified Details", loadClassifiedButton.getCaption());
        assertTrue(loadClassifiedButton.isDisableOnClick());
        Button addToScenarioButton = (Button) layout.getComponent(4);
        assertEquals("Add To Scenario", addToScenarioButton.getCaption());
        assertTrue(addToScenarioButton.isDisableOnClick());
        assertEquals("Export", layout.getComponent(5).getCaption());
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

    private void verifyFundPoolMenuBar(Component component, String menuBarName, List<String> menuItems) {
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

    private void verifyGrid(Grid grid) {
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Detail ID", "Detail Status", "Product Family", "Usage Batch Name",
            "Period End Date", "RH Account #", "RH Name", "Wr Wrk Inst", "System Title", "Standard Number",
            "Standard Number Type", "Det LC ID", "Det LC Enrollment", "Det LC Discipline", "Pub Type", "Institution",
            "Usage Period", "Usage Source", "Number of Copies", "Number of Pages", "Right Limitation", "Comment"),
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
