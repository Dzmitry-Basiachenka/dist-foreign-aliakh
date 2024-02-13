package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

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
import com.copyright.rup.dist.foreign.vui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.vui.usage.impl.FasNtsUsageFilterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
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
 * Verifies {@link FasUsageWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/2019
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FasUsageWidget.class, Dialog.class, ForeignSecurityUtils.class, Windows.class, UI.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class FasUsageWidgetTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String FAS_SCENARIO_NAME_PREFIX = "FAS Distribution ";
    private static final int RECORD_THRESHOLD = 10000;
    private static final String WIDTH_300 = "300px";

    private FasUsageWidget widget;
    private IFasUsageController controller;

    @Before
    public void setUp() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(2);
        expect(ui.getUIId()).andReturn(1).times(2);
        controller = createMock(IFasUsageController.class);
        widget = new FasUsageWidget(controller);
        widget.setController(controller);
        var filterWidget = new FasNtsUsageFilterWidget(createMock(IFasNtsUsageFilterController.class));
        filterWidget.getFilter().setUsageBatchesIds(Set.of("3a070817-03ae-4ebd-b25f-dd3168a7ffb0"));
        expect(controller.initUsagesFilterWidget()).andReturn(filterWidget).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        Map.Entry<Supplier<String>, Supplier<InputStream>> source =
            new SimpleImmutableEntry<>(() -> "file_name.txt", () -> new ByteArrayInputStream(new byte[]{}));
        expect(streamSource.getSource()).andReturn(source).times(2);
        expect(controller.getExportUsagesStreamSource()).andReturn(streamSource).once();
        expect(controller.getSendForResearchUsagesStreamSource()).andReturn(streamSource).once();
        replay(UI.class, ui, controller, streamSource);
        widget.init();
        verify(controller);
        reset(UI.class, ui, controller, streamSource);
    }

    @Test
    public void testWidgetStructure() {
        assertThat(widget.getPrimaryComponent(), instanceOf(FasNtsUsageFilterWidget.class));
        var secondComponent = widget.getSecondaryComponent();
        assertThat(secondComponent, instanceOf(VerticalLayout.class));
        var contentLayout = (VerticalLayout) secondComponent;
        assertEquals(2, contentLayout.getComponentCount());
        var toolbarLayout = (HorizontalLayout) contentLayout.getComponentAt(0);
        verifyButtonsLayout((HorizontalLayout) toolbarLayout.getComponentAt(0));
        var grid = (Grid<?>) contentLayout.getComponentAt(1);
        verifyGrid(grid, List.of(
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
            Pair.of("Reported Standard Number", "260px"),
            Pair.of("Standard Number", "180px"),
            Pair.of("Standard Number Type", "225px"),
            Pair.of("Fiscal Year", "130px"),
            Pair.of("Payment Date", "145px"),
            Pair.of("Reported Title", WIDTH_300),
            Pair.of("Article", "135px"),
            Pair.of("Publisher", "135px"),
            Pair.of("Pub Date", "110px"),
            Pair.of("Number of Copies", "185px"),
            Pair.of("Reported Value", "170px"),
            Pair.of("Gross Amt in USD", "170px"),
            Pair.of("Batch Amt in USD", "170px"),
            Pair.of("Market", "120px"),
            Pair.of("Market Period From", "200px"),
            Pair.of("Market Period To", "185px"),
            Pair.of("Author", WIDTH_300),
            Pair.of("Comment", "200px")
        ));
    }

    @Test
    public void testGridValues() {
        var usages = loadExpectedUsageDtos("usage_dto_05f0385c.json");
        expect(controller.loadBeans(0, Integer.MAX_VALUE, List.of())).andReturn(usages).once();
        expect(controller.getBeansCount()).andReturn(1).once();
        replay(controller);
        var secondComponent = widget.getSecondaryComponent();
        var contentLayout = (VerticalLayout) secondComponent;
        var grid = (Grid<?>) contentLayout.getComponentAt(1);
        Object[][] expectedCells = {{
            "05f0385c-798d-4c96-8278-8f5ff873b15f", "PAID", "FAS", "Paid batch", "1000000004",
            "Computers for Design and Construction", "1000002859", "John Wiley & Sons - Books", "243904752",
            "100 ROAD MOVIES", "1008902112317555XX", "1008902112317555XX", "VALISBN13", "FY2021", "02/12/2021",
            "100 ROAD MOVIES", "some article", "some publisher", "02/13/2021", "2", "3,000.00", "500.00", "1,000.00",
            "lib", "1980", "2000", "author", "usage from usages.csv"
        }};
        verifyGridItems(grid, usages, expectedCells);
        verify(controller);
    }

    @Test
    public void testSelectLoadUsageBatchMenuItem() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).anyTimes();
        expect(ui.getLocale()).andReturn(null).anyTimes();
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(UsageBatchUploadWindow.class));
        expectLastCall().once();
        replay(UI.class, ui, controller, Windows.class);
        var menuItems = getMenuBar(0).getItems();
        assertEquals(1, menuItems.size());
        var menuItem = menuItems.get(0);
        assertEquals("Usage Batch", menuItem.getText());
        var subMenuItems = menuItem.getSubMenu().getItems();
        assertEquals(2, subMenuItems.size());
        var subMenuItem = subMenuItems.get(0);
        assertEquals("Load", subMenuItem.getText());
        clickMenuItem(subMenuItem);
        verify(UI.class, ui, controller, Windows.class);
    }

    @Test
    public void testSelectViewUsageBatchMenuItem() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).anyTimes();
        expect(ui.getLocale()).andReturn(null).anyTimes();
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.getUsageBatches(FAS_PRODUCT_FAMILY)).andReturn(List.of()).once();
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasDeleteUsagePermission()).andReturn(true).once();
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(ViewUsageBatchWindow.class));
        expectLastCall().once();
        replay(UI.class, ui, controller, ForeignSecurityUtils.class, Windows.class);
        var menuItems = getMenuBar(0).getItems();
        assertEquals(1, menuItems.size());
        var menuItem = menuItems.get(0);
        assertEquals("Usage Batch", menuItem.getText());
        var subMenuItems = menuItem.getSubMenu().getItems();
        assertEquals(2, subMenuItems.size());
        var subMenuItem = subMenuItems.get(1);
        assertEquals("View", subMenuItem.getText());
        clickMenuItem(subMenuItem);
        verify(UI.class, ui, controller, ForeignSecurityUtils.class, Windows.class);
    }


    @Test
    public void testLoadResearchedUsagesButtonClickListener() {
        mockStatic(Windows.class);
        Button loadResearchedUsagesButton = Whitebox.getInternalState(widget, "loadResearchedUsagesButton");
        assertTrue(loadResearchedUsagesButton.isDisableOnClick());
        Windows.showModalWindow(anyObject(ResearchedUsagesUploadWindow.class));
        expectLastCall().once();
        replay(Windows.class, controller);
        loadResearchedUsagesButton.click();
        verify(Windows.class, controller);
    }

    @Test
    public void testUpdateUsagesButtonClickListener() {
        mockStatic(Windows.class);
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.getUsageDtosToUpdate()).andReturn(List.of(new UsageDto())).once();
        expect(controller.getRecordsThreshold()).andReturn(RECORD_THRESHOLD).once();
        Windows.showModalWindow(anyObject(FasUpdateUsageWindow.class));
        expectLastCall().once();
        replay(Windows.class, controller);
        Button updateUsagesButton = Whitebox.getInternalState(widget, "updateUsagesButton");
        updateUsagesButton.click();
        verify(Windows.class, controller);
    }

    @Test
    public void testUpdateUsagesButtonClickListenerNoUsages() {
        mockStatic(Windows.class);
        expect(controller.getBeansCount()).andReturn(0).once();
        Windows.showNotificationWindow("There are no usages to update");
        expectLastCall().once();
        replay(Windows.class, controller);
        Button updateUsagesButton = Whitebox.getInternalState(widget, "updateUsagesButton");
        updateUsagesButton.click();
        verify(Windows.class, controller);
    }

    @Test
    public void testAddToScenarioButtonEmptyUsagesTableClickListener() {
        mockStatic(Windows.class);
        Grid<?> grid = new Grid<>();
        Whitebox.setInternalState(widget, grid);
        Button addToScenarioButton = getAddToScenarioButton();
        assertTrue(addToScenarioButton.isDisableOnClick());
        Windows.showNotificationWindow("Scenario cannot be created. There are no usages to include into scenario");
        expectLastCall().once();
        expect(controller.getBeansCount()).andReturn(0).once();
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.scenarioExists(FAS_SCENARIO_NAME_PREFIX + DATE)).andReturn(true).once();
        replay(Windows.class, controller);
        addToScenarioButton.click();
        verify(Windows.class, controller);
    }

    @Test
    public void testAddToScenarioButtonInvalidFilterSelectedClickListener() {
        mockStatic(Windows.class);
        Grid<?> grid = new Grid<>();
        Whitebox.setInternalState(widget, grid);
        Button addToScenarioButton = getAddToScenarioButton();
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.scenarioExists(FAS_SCENARIO_NAME_PREFIX + DATE)).andReturn(true).once();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(false).once();
        Windows.showNotificationWindow("Only usages in ELIGIBLE status can be added to scenario");
        expectLastCall().once();
        replay(Windows.class, controller);
        addToScenarioButton.click();
        verify(Windows.class, controller);
    }

    @Test
    public void testAddToScenarioButtonClickListenerInvalidRightsholders() {
        mockStatic(Windows.class);
        Grid<?> grid = new Grid<>();
        Whitebox.setInternalState(widget, grid);
        Button addToScenarioButton = getAddToScenarioButton();
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.scenarioExists(FAS_SCENARIO_NAME_PREFIX + DATE)).andReturn(true).once();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of(1000000001L)).once();
        Windows.showNotificationWindow("Scenario cannot be created. The following rightsholder(s) are absent " +
            "in PRM: <i><b>[1000000001]</b></i>");
        expectLastCall().once();
        replay(Windows.class, controller);
        addToScenarioButton.click();
        verify(Windows.class, controller);
    }

    @Test
    public void testAddToScenarioButtonClickListenerFasProductFamily() {
        mockStatic(Windows.class);
        Grid<?> grid = new Grid<>();
        Whitebox.setInternalState(widget, grid);
        Button addToScenarioButton = getAddToScenarioButton();
        assertTrue(addToScenarioButton.isDisableOnClick());
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.scenarioExists(FAS_SCENARIO_NAME_PREFIX + DATE)).andReturn(true).once();
        Windows.showModalWindow(anyObject(CreateScenarioWindow.class));
        expectLastCall().once();
        replay(Windows.class, controller);
        addToScenarioButton.click();
        verify(Windows.class, controller);
    }

    @Test
    public void testSendForResearchInvalidUsagesState() {
        mockStatic(Windows.class);
        Button sendForResearchButton = Whitebox.getInternalState(widget, "sendForResearchButton");
        Grid<?> grid = new Grid<>();
        Whitebox.setInternalState(widget, grid);
        expect(controller.isValidFilteredUsageStatus(UsageStatusEnum.WORK_NOT_FOUND)).andReturn(false).once();
        Windows.showNotificationWindow("Only usages in WORK_NOT_FOUND status can be sent for research");
        expectLastCall().once();
        replay(Windows.class, controller);
        sendForResearchButton.click();
        verify(Windows.class, controller);
    }

    @Test
    public void testInitMediator() throws Exception {
        FasUsageMediator mediator = createMock(FasUsageMediator.class);
        expectNew(FasUsageMediator.class).andReturn(mediator).once();
        mediator.setLoadUsageBatchMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        mediator.setSendForResearchButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setSendForResearchDownloader(anyObject(OnDemandFileDownloader.class));
        expectLastCall().once();
        mediator.setLoadResearchedUsagesButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setUpdateUsagesButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setAddToScenarioButton(anyObject(Button.class));
        expectLastCall().once();
        replay(FasUsageMediator.class, mediator, controller);
        assertNotNull(widget.initMediator());
        verify(FasUsageMediator.class, mediator, controller);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertEquals(6, layout.getComponentCount());
        verifyMenuBar(layout.getComponentAt(0), "Usage Batch", true, List.of("Load", "View"));
        verifyFileDownloader(layout.getComponentAt(1), "Send for Research", true, true);
        verifyButton(layout.getComponentAt(2), "Load Researched Details", true, true);
        verifyButton(layout.getComponentAt(3), "Update Usages", true, true);
        verifyButton(layout.getComponentAt(4), "Add To Scenario", true, true);
        verifyFileDownloader(layout.getComponentAt(5), "Export", true, true);
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
            return mapper.readValue(content, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
