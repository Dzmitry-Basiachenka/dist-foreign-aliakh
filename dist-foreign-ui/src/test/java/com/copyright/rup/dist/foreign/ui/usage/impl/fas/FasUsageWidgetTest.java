package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyMenuBar;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
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
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.FasNtsUsageFilterWidget;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.Extension;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

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
@PrepareForTest({FasUsageWidget.class, Windows.class, ForeignSecurityUtils.class, JavaScript.class})
public class FasUsageWidgetTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String FAS_SCENARIO_NAME_PREFIX = "FAS Distribution ";

    private final List<UsageDto> usages = loadExpectedUsageDtos("usage_dto_05f0385c.json");
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
        verifyWindow(usagesWidget, null, 100, 100, Unit.PERCENTAGE);
        assertThat(usagesWidget.getFirstComponent(), instanceOf(FasNtsUsageFilterWidget.class));
        Component secondComponent = usagesWidget.getSecondComponent();
        assertThat(secondComponent, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifyWindow(layout, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0));
        Grid grid = (Grid) layout.getComponent(1);
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        verifyGrid(grid, Arrays.asList(
            Triple.of("Detail ID", 130.0, -1),
            Triple.of("Detail Status", 115.0, -1),
            Triple.of("Product Family", 125.0, -1),
            Triple.of("Usage Batch Name", 145.0, -1),
            Triple.of("RRO Account #", 125.0, -1),
            Triple.of("RRO Name", 135.0, -1),
            Triple.of("RH Account #", 115.0, -1),
            Triple.of("RH Name", 300.0, -1),
            Triple.of("Wr Wrk Inst", 110.0, -1),
            Triple.of("System Title", 300.0, -1),
            Triple.of("Standard Number", 140.0, -1),
            Triple.of("Standard Number Type", 155.0, -1),
            Triple.of("Fiscal Year", 105.0, -1),
            Triple.of("Payment Date", 115.0, -1),
            Triple.of("Title", 300.0, -1),
            Triple.of("Article", 135.0, -1),
            Triple.of("Publisher", 135.0, -1),
            Triple.of("Pub Date", 90.0, -1),
            Triple.of("Number of Copies", 140.0, -1),
            Triple.of("Reported Value", 130.0, -1),
            Triple.of("Gross Amt in USD", 130.0, -1),
            Triple.of("Batch Amt in USD", 130.0, -1),
            Triple.of("Market", 115.0, -1),
            Triple.of("Market Period From", 150.0, -1),
            Triple.of("Market Period To", 145.0, -1),
            Triple.of("Author", 300.0, -1),
            Triple.of("Comment", 200.0, -1)
        ));
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    public void testGridValues() {
        mockStatic(JavaScript.class);
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, Integer.MAX_VALUE, Collections.emptyList())).andReturn(usages).once();
        expect(controller.getBeansCount()).andReturn(1).once();
        replay(JavaScript.class, controller);
        Grid<?> grid = (Grid<?>) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(1);
        Object[][] expectedCells = {
            {"05f0385c-798d-4c96-8278-8f5ff873b15f", UsageStatusEnum.PAID, "FAS", "Paid batch", 1000000004L,
                "Computers for Design and Construction", 1000002859L, "John Wiley & Sons - Books", 243904752L,
                "100 ROAD MOVIES", "1008902112317555XX", "VALISBN13", "FY2021", "02/12/2021", "100 ROAD MOVIES",
                "some article", "some publisher", "02/13/2021", 2, "3,000.00", "500.00", "1,000.00", "lib", 1980, 2000,
                "author", "usage from usages_10.csv"}
        };
        verifyGridItems(grid, usages, expectedCells);
        verify(JavaScript.class, controller);
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
        expect(controller.getUsageBatches(FAS_PRODUCT_FAMILY)).andReturn(List.of(new UsageBatch())).once();
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
        prepareCreateScenarioExpectation();
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
        prepareCreateScenarioExpectation();
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
    public void testAddToScenarioButtonClickListenerInvalidRightsholders() {
        mockStatic(Windows.class);
        Grid grid = new Grid();
        Whitebox.setInternalState(usagesWidget, grid);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(3);
        assertTrue(addToScenarioButton.isDisableOnClick());
        prepareCreateScenarioExpectation();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of(1000000001L)).once();
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
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        prepareCreateScenarioExpectation();
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
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.WORK_NOT_FOUND)).andReturn(false).once();
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
        verifyMenuBar(layout.getComponent(0), "Usage Batch", true, Arrays.asList("Load", "View"));
        assertEquals("Send for Research", layout.getComponent(1).getCaption());
        assertEquals("Load Researched Details", layout.getComponent(2).getCaption());
        assertEquals("Add To Scenario", layout.getComponent(3).getCaption());
        Component component = layout.getComponent(4);
        assertEquals("Export", component.getCaption());
        Collection<Extension> extensions = component.getExtensions();
        assertTrue(CollectionUtils.isNotEmpty(extensions));
        assertEquals(1, extensions.size());
        assertThat(extensions.iterator().next(), instanceOf(OnDemandFileDownloader.class));
    }

    private void prepareCreateScenarioExpectation() {
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.scenarioExists(FAS_SCENARIO_NAME_PREFIX + DATE)).andReturn(true).once();
    }

    private List<MenuItem> getUsageBatchMenuBarItems() {
        HorizontalLayout buttonsBar =
            (HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent()).getComponent(0);
        MenuBar menuBar = (MenuBar) buttonsBar.getComponent(0);
        return menuBar.getItems().get(0).getChildren();
    }

    private List<UsageDto> loadExpectedUsageDtos(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<List<UsageDto>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
