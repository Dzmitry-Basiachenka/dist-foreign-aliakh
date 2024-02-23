package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyFileDownloader;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyMenuBar;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.vui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.vui.usage.api.IFasNtsUsageFilterWidget;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.vui.usage.impl.FasNtsUsageFilterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.FilterWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Verifies {@link NtsUsageWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/2019
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({NtsUsageWidget.class, Dialog.class, ForeignSecurityUtils.class, Windows.class, UI.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class NtsUsageWidgetTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String NTS_SCENARIO_NAME_PREFIX = "NTS Distribution ";
    private static final Set<String> BATCHES_IDS = Set.of("e0f2287a-f7f4-437f-95ad-56bd1b1c51cf");
    private static final String VIEW = "View";
    private static final String WIDTH_300 = "300px";

    private NtsUsageWidget widget;
    private INtsUsageController controller;
    private IFasNtsUsageFilterWidget filterWidget;

    @Before
    public void setUp() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).once();
        expect(ui.getUIId()).andReturn(1).once();
        controller = createMock(INtsUsageController.class);
        filterWidget = new FasNtsUsageFilterWidget(createMock(IFasNtsUsageFilterController.class));
        filterWidget.getAppliedFilter().setUsageBatchesIds(BATCHES_IDS);
        ICommonUsageFilterController filterController = createMock(ICommonUsageFilterController.class);
        expect(controller.getUsageFilterController()).andReturn(filterController).once();
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        Map.Entry<Supplier<String>, Supplier<InputStream>> source =
            new SimpleImmutableEntry<>(() -> "file_name.txt", () -> new ByteArrayInputStream(new byte[]{}));
        expect(streamSource.getSource()).andReturn(source).once();
        expect(controller.getExportUsagesStreamSource()).andReturn(streamSource).once();
        replay(UI.class, ui, controller, filterController, streamSource);
        widget = new NtsUsageWidget(controller);
        widget.setController(controller);
        widget.init();
        verify(UI.class, ui, controller, filterController, streamSource);
        reset(UI.class, ui, controller, filterController, streamSource);
    }

    @Test
    public void testWidgetStructure() {
        assertThat(widget.getPrimaryComponent(), instanceOf(FasNtsUsageFilterWidget.class));
        var secondaryComponent = widget.getSecondaryComponent();
        assertThat(secondaryComponent, instanceOf(VerticalLayout.class));
        var contentLayout = (VerticalLayout) secondaryComponent;
        assertEquals(2, contentLayout.getComponentCount());
        var toolbarLayout = (HorizontalLayout) contentLayout.getComponentAt(0);
        verifyButtonsLayout((HorizontalLayout) toolbarLayout.getComponentAt(0));
        verifyGrid((Grid<?>) contentLayout.getComponentAt(1), List.of(
            Pair.of("Detail ID", WIDTH_300),
            Pair.of("Detail Status", "180px"),
            Pair.of("Product Family", "160px"),
            Pair.of("Usage Batch Name", "200px"),
            Pair.of("RRO Account #", "160px"),
            Pair.of("RRO Name", WIDTH_300),
            Pair.of("RH Account #", "150px"),
            Pair.of("RH Name", WIDTH_300),
            Pair.of("Wr Wrk Inst", "140px"),
            Pair.of("System Title", WIDTH_300),
            Pair.of("Standard Number", "180px"),
            Pair.of("Standard Number Type", "225px"),
            Pair.of("Fiscal Year", "130px"),
            Pair.of("Payment Date", "145px"),
            Pair.of("Title", WIDTH_300),
            Pair.of("Article", "135px"),
            Pair.of("Publisher", "135px"),
            Pair.of("Pub Date", "110px"),
            Pair.of("Number of Copies", "185px"),
            Pair.of("Reported Value", "170px"),
            Pair.of("Gross Amt in USD", "170px"),
            Pair.of("Market", "120px"),
            Pair.of("Market Period From", "200px"),
            Pair.of("Market Period To", "185px"),
            Pair.of("Author", WIDTH_300),
            Pair.of("Comment", "200px")
        ));
    }

    @Test
    public void testGridValues() {
        var usages = loadExpectedUsageDtos("usage_dto_926720c0.json");
        expect(controller.loadBeans(0, Integer.MAX_VALUE, List.of())).andReturn(usages).once();
        expect(controller.getBeansCount()).andReturn(1).once();
        replay(controller);
        Grid<?> grid = Whitebox.getInternalState(widget, "usagesGrid");
        Object[][] expectedCells = {{
            "926720c0-d83a-4339-b21c-e62d3ea20b76", "PAID", "NTS", "Paid batch", "1000000004",
            "Computers for Design and Construction", "1000002859", "John Wiley & Sons - Books", "243904752",
            "100 ROAD MOVIES", "1008902112317555XX", "VALISBN13", "FY2021", "02/12/2021", "100 ROAD MOVIES",
            "some article", "some publisher", "02/13/2021", "2", "3,000.00", "500.00", "lib", "1980", "2000",
            "author", "usage from usages_10.csv"
        }};
        verifyGridItems(grid, usages, expectedCells);
        verify(controller);
    }

    @Test
    public void testSelectLoadFundPoolMenuItem() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).anyTimes();
        expect(ui.getLocale()).andReturn(null).anyTimes();
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(FundPoolLoadWindow.class));
        expectLastCall().once();
        replay(UI.class, ui, controller, Windows.class);
        var menuItems = getMenuBar(0).getItems();
        assertEquals(1, menuItems.size());
        var menuItem = menuItems.get(0);
        assertEquals("Fund Pool", menuItem.getText());
        var subMenuItems = menuItem.getSubMenu().getItems();
        assertEquals(2, subMenuItems.size());
        var subMenuItem = subMenuItems.get(0);
        assertEquals("Load", subMenuItem.getText());
        clickMenuItem(subMenuItem);
        verify(UI.class, ui, controller, Windows.class);
    }

    @Test
    public void testSelectViewFundPoolMenuItem() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).anyTimes();
        expect(ui.getLocale()).andReturn(null).anyTimes();
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(controller.getUsageBatches(NTS_PRODUCT_FAMILY)).andReturn(List.of()).once();
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasDeleteUsagePermission()).andReturn(true).once();
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(ViewFundPoolWindow.class));
        expectLastCall().once();
        replay(UI.class, ui, controller, ForeignSecurityUtils.class, Windows.class);
        var menuItems = getMenuBar(0).getItems();
        assertEquals(1, menuItems.size());
        var menuItem = menuItems.get(0);
        assertEquals("Fund Pool", menuItem.getText());
        var subMenuItems = menuItem.getSubMenu().getItems();
        assertEquals(2, subMenuItems.size());
        var subMenuItem = subMenuItems.get(1);
        assertEquals(VIEW, subMenuItem.getText());
        clickMenuItem(subMenuItem);
        verify(UI.class, ui, controller, ForeignSecurityUtils.class, Windows.class);
    }

    @Test
    public void testSelectCreateAdditionalFundsMenuItem() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).anyTimes();
        expect(ui.getLocale()).andReturn(null).anyTimes();
        expect(controller.getUsageBatchesForAdditionalFunds()).andReturn(List.of()).once();
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(FilterWindow.class));
        expectLastCall().once();
        replay(UI.class, ui, controller, Windows.class);
        var menuItems = getMenuBar(1).getItems();
        assertEquals(1, menuItems.size());
        var menuItem = menuItems.get(0);
        assertEquals("Additional Funds", menuItem.getText());
        var subMenuItems = menuItem.getSubMenu().getItems();
        assertEquals(2, subMenuItems.size());
        var subMenuItem = subMenuItems.get(0);
        assertEquals("Create", subMenuItem.getText());
        clickMenuItem(subMenuItem);
        verify(UI.class, ui, controller, Windows.class);
    }

    @Test
    public void testSelectViewAdditionalFundsMenuItem() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).anyTimes();
        expect(ui.getLocale()).andReturn(null).anyTimes();
        expect(controller.getAdditionalFunds()).andReturn(List.of()).once();
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(ViewAdditionalFundsWindow.class));
        expectLastCall().once();
        replay(UI.class, ui, controller, Windows.class);
        var menuItems = getMenuBar(1).getItems();
        assertEquals(1, menuItems.size());
        var menuItem = menuItems.get(0);
        assertEquals("Additional Funds", menuItem.getText());
        var subMenuItems = menuItem.getSubMenu().getItems();
        assertEquals(2, subMenuItems.size());
        var subMenuItem = subMenuItems.get(1);
        assertEquals(VIEW, subMenuItem.getText());
        clickMenuItem(subMenuItem);
        verify(UI.class, ui, controller, Windows.class);
    }

    @Test
    public void testAddToScenarioButtonClickListener() {
        mockStatic(Windows.class);
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).anyTimes();
        expect(ui.getLocale()).andReturn(null).anyTimes();
        Grid<?> grid = new Grid<>();
        Whitebox.setInternalState(widget, grid);
        Button addToScenarioButton = getAddToScenarioButton();
        assertTrue(addToScenarioButton.isDisableOnClick());
        prepareCreateScenarioExpectation();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        expect(controller.getProcessingBatchesNames(BATCHES_IDS)).andReturn(List.of()).once();
        expect(controller.getBatchesNamesToScenariosNames(BATCHES_IDS)).andReturn(Map.of()).once();
        expect(controller.getBatchNamesWithUnclassifiedWorks(filterWidget.getAppliedFilter().getUsageBatchesIds()))
            .andReturn(List.of()).once();
        expect(controller.getBatchNamesWithInvalidStmOrNonStmUsagesState(
            filterWidget.getAppliedFilter().getUsageBatchesIds()))
            .andReturn(Map.of("STM", List.of(), "NON-STM", List.of())).once();
        Windows.showModalWindow(anyObject(CreateNtsScenarioWindow.class));
        expectLastCall().once();
        replay(Windows.class, UI.class, ui, controller);
        addToScenarioButton.click();
        verify(Windows.class, UI.class, ui, controller);
    }

    @Test
    public void testAddToScenarioButtonClickListenerProcessingBatches() {
        mockStatic(Windows.class);
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).anyTimes();
        expect(ui.getLocale()).andReturn(null).anyTimes();
        Grid<?> grid = new Grid<>();
        Whitebox.setInternalState(widget, grid);
        Button addToScenarioButton = getAddToScenarioButton();
        assertTrue(addToScenarioButton.isDisableOnClick());
        prepareCreateScenarioExpectation();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        expect(controller.getProcessingBatchesNames(BATCHES_IDS))
            .andReturn(List.of("Usage Batch 1", "Usage Batch 2")).once();
        Windows.showNotificationWindow("Please wait while batch(es) processing is completed:" +
            "<ul><li><i><b>Usage Batch 1<br><li>Usage Batch 2</b></i></ul>");
        expectLastCall().once();
        replay(Windows.class, UI.class, ui, controller);
        addToScenarioButton.click();
        verify(Windows.class, UI.class, ui, controller);
    }

    @Test
    public void testAddToScenarioButtonClickListenerBatchInScenario() {
        mockStatic(Windows.class);
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).anyTimes();
        expect(ui.getLocale()).andReturn(null).anyTimes();
        Grid<?> grid = new Grid<>();
        Whitebox.setInternalState(widget, grid);
        Button addToScenarioButton = getAddToScenarioButton();
        assertTrue(addToScenarioButton.isDisableOnClick());
        prepareCreateScenarioExpectation();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        expect(controller.getProcessingBatchesNames(BATCHES_IDS)).andReturn(List.of()).once();
        expect(controller.getBatchesNamesToScenariosNames(BATCHES_IDS))
            .andReturn(Map.of("Usage Batch", "Scenario")).once();
        Windows.showNotificationWindow("The following batch(es) already associated with scenario(s):" +
            "<ul><li><i><b>Usage Batch : Scenario</b></i></ul>");
        expectLastCall().once();
        replay(Windows.class, UI.class, ui, controller);
        addToScenarioButton.click();
        verify(Windows.class, UI.class, ui, controller);
    }

    @Test
    public void testAddToScenarioButtonClickListenerUnclassifiedUsages() {
        mockStatic(Windows.class);
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).anyTimes();
        expect(ui.getLocale()).andReturn(null).anyTimes();
        Grid<?> grid = new Grid<>();
        Whitebox.setInternalState(widget, grid);
        Button addToScenarioButton = getAddToScenarioButton();
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getBeansCount()).andReturn(1).once();
        prepareCreateScenarioExpectation();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        expect(controller.getBatchNamesWithUnclassifiedWorks(filterWidget.getAppliedFilter().getUsageBatchesIds()))
            .andReturn(List.of("Batch with unclassified usages")).once();
        expect(controller.getProcessingBatchesNames(BATCHES_IDS)).andReturn(List.of()).once();
        expect(controller.getBatchesNamesToScenariosNames(BATCHES_IDS)).andReturn(Map.of()).once();
        Windows.showNotificationWindow(
            "The following batches have unclassified works:<ul><li><i><b>Batch with unclassified usages</b></i></ul>");
        expectLastCall().once();
        replay(Windows.class, UI.class, ui, controller);
        addToScenarioButton.click();
        verify(Windows.class, UI.class, ui, controller);
    }

    @Test
    public void testAddToScenarioButtonClickListenerNoStmRhs() {
        mockStatic(Windows.class);
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).anyTimes();
        expect(ui.getLocale()).andReturn(null).anyTimes();
        Grid<?> grid = new Grid<>();
        Whitebox.setInternalState(widget, grid);
        Button addToScenarioButton = getAddToScenarioButton();
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getBeansCount()).andReturn(1).once();
        prepareCreateScenarioExpectation();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        expect(controller.getBatchNamesWithUnclassifiedWorks(filterWidget.getAppliedFilter().getUsageBatchesIds()))
            .andReturn(List.of()).once();
        expect(controller.getProcessingBatchesNames(BATCHES_IDS)).andReturn(List.of()).once();
        expect(controller.getBatchesNamesToScenariosNames(BATCHES_IDS)).andReturn(Map.of()).once();
        expect(controller.getBatchNamesWithInvalidStmOrNonStmUsagesState(
            filterWidget.getAppliedFilter().getUsageBatchesIds()))
            .andReturn(Map.of("STM", List.of("Batch without STM RHs"))).once();
        Windows.showNotificationWindow("There are no STM rightsholders in the following batches: " +
            "<ul><li><i><b>Batch without STM RHs</b></i></ul>");
        expectLastCall().once();
        replay(Windows.class, UI.class, ui, controller);
        addToScenarioButton.click();
        verify(Windows.class, UI.class, ui, controller);
    }

    @Test
    public void testAddToScenarioButtonClickListenerNoStmAndNonStmRhs() {
        mockStatic(Windows.class);
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).anyTimes();
        expect(ui.getLocale()).andReturn(null).anyTimes();
        Grid<?> grid = new Grid<>();
        Whitebox.setInternalState(widget, grid);
        Button addToScenarioButton = getAddToScenarioButton();
        assertTrue(addToScenarioButton.isDisableOnClick());
        prepareCreateScenarioExpectation();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        expect(controller.getProcessingBatchesNames(BATCHES_IDS)).andReturn(List.of()).once();
        expect(controller.getBatchesNamesToScenariosNames(BATCHES_IDS)).andReturn(Map.of()).once();
        expect(controller.getBatchNamesWithUnclassifiedWorks(filterWidget.getAppliedFilter().getUsageBatchesIds()))
            .andReturn(List.of()).once();
        expect(controller.getBatchNamesWithInvalidStmOrNonStmUsagesState(
            filterWidget.getAppliedFilter().getUsageBatchesIds()))
            .andReturn(Map.of("STM", List.of("Batch without STM RHs"),
                "NON-STM", List.of("Batch without NON-STM RHs"))).once();
        Windows.showNotificationWindow("There are no STM rightsholders in the following batches: " +
            "<ul><li><i><b>Batch without STM RHs</b></i></ul>There are no NON-STM rightsholders " +
            "in the following batches: <ul><li><i><b>Batch without NON-STM RHs</b></i></ul>");
        expectLastCall().once();
        replay(Windows.class, UI.class, ui, controller);
        addToScenarioButton.click();
        verify(Windows.class, UI.class, ui, controller);
    }

    @Test
    public void testInitMediator() throws Exception {
        NtsUsageMediator mediator = createMock(NtsUsageMediator.class);
        expectNew(NtsUsageMediator.class).andReturn(mediator).once();
        mediator.setAddToScenarioButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setAssignClassificationButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setAdditionalFundsMenuBar(anyObject(MenuBar.class));
        expectLastCall().once();
        mediator.setLoadFundPoolMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        replay(NtsUsageMediator.class, mediator, controller);
        assertNotNull(widget.initMediator());
        verify(NtsUsageMediator.class, mediator, controller);
    }

    private void verifyButtonsLayout(HorizontalLayout buttonsLayout) {
        assertEquals(5, buttonsLayout.getComponentCount());
        verifyMenuBar(buttonsLayout.getComponentAt(0), "Fund Pool", true, List.of("Load", VIEW));
        verifyMenuBar(buttonsLayout.getComponentAt(1), "Additional Funds", true, List.of("Create", VIEW));
        verifyButton(buttonsLayout.getComponentAt(2), "Assign Classification", true, true);
        verifyButton(buttonsLayout.getComponentAt(3), "Add To Scenario", true, true);
        verifyFileDownloader(buttonsLayout.getComponentAt(4), "Export", true, true);
    }

    private void prepareCreateScenarioExpectation() {
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(controller.scenarioExists(NTS_SCENARIO_NAME_PREFIX + DATE)).andReturn(true).once();
        expect(controller.getAdditionalFundsNotAttachedToScenario()).andReturn(List.of()).once();
    }

    private MenuBar getMenuBar(int toolbarIdx) {
        var secondaryComponent = (VerticalLayout) widget.getSecondaryComponent();
        var contentLayout = (HorizontalLayout) secondaryComponent.getComponentAt(0);
        var toolbarLayout = (HorizontalLayout) contentLayout.getComponentAt(0);
        return (MenuBar) toolbarLayout.getComponentAt(toolbarIdx);
    }

    private void clickMenuItem(MenuItem menuItem) {
        var eventListeners = (List<ComponentEventListener<ClickEvent<MenuItem>>>)
            ComponentUtil.getListeners(menuItem, ClickEvent.class);
        assertEquals(1, eventListeners.size());
        var eventListener = eventListeners.get(0);
        eventListener.onComponentEvent(createMock(ClickEvent.class));
    }

    private Button getAddToScenarioButton() {
        return Whitebox.getInternalState(widget, "addToScenarioButton");
    }

    private List<UsageDto> loadExpectedUsageDtos(String fileName) {
        try {
            var content = TestUtils.fileToString(this.getClass(), fileName);
            var mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<List<UsageDto>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
