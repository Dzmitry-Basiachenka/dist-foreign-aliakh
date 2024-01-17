package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
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

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.vui.usage.impl.FasNtsUsageFilterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
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
import java.io.InputStream;
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
        var layout = (VerticalLayout) secondComponent;
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponentAt(0));
        Grid<?> grid = (Grid<?>) layout.getComponentAt(1);
        verifyGrid(grid, List.of(
            Pair.of("Detail ID", "130px"),
            Pair.of("Detail Status", "115px"),
            Pair.of("Product Family", "125px"),
            Pair.of("Usage Batch Name", "145px"),
            Pair.of("RRO Account #", "125px"),
            Pair.of("RRO Name", "135px"),
            Pair.of("RH Account #", "115px"),
            Pair.of("RH Name", "300px"),
            Pair.of("Wr Wrk Inst", "110px"),
            Pair.of("System Title", "300px"),
            Pair.of("Reported Standard Number", "190px"),
            Pair.of("Standard Number", "140px"),
            Pair.of("Standard Number Type", "155px"),
            Pair.of("Fiscal Year", "105px"),
            Pair.of("Payment Date", "115px"),
            Pair.of("Reported Title", "300px"),
            Pair.of("Article", "135px"),
            Pair.of("Publisher", "135px"),
            Pair.of("Pub Date", "90px"),
            Pair.of("Number of Copies", "140px"),
            Pair.of("Reported Value", "130px"),
            Pair.of("Gross Amt in USD", "155px"),
            Pair.of("Batch Amt in USD", "155px"),
            Pair.of("Market", "120px"),
            Pair.of("Market Period From", "150px"),
            Pair.of("Market Period To", "145px"),
            Pair.of("Author", "305px"),
            Pair.of("Comment", "200px")
        ));
    }

    @Test
    public void testSelectUsageBatchMenuItems() {
        replay(controller);
        var layout = (HorizontalLayout) ((VerticalLayout) widget.getSecondaryComponent()).getComponentAt(0);
        MenuBar menuBar = (MenuBar) layout.getComponentAt(0);
        List<MenuItem> menuItems = menuBar.getItems();
        assertEquals(1, menuItems.size());
        MenuItem menuItem = menuItems.get(0);
        assertEquals("Usage Batch", menuItem.getText());
        SubMenu subMenu = menuItem.getSubMenu();
        List<MenuItem> subMenuItems = subMenu.getItems();
        assertEquals(2, subMenuItems.size());
        assertEquals("Load", subMenuItems.get(0).getText());
        assertEquals("View", subMenuItems.get(1).getText());
        verify(controller);
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
        //TODO {aliakh} implement
    }

    @Test
    public void testUpdateUsagesButtonClickListenerNoUsages() {
        //TODO {aliakh} implement
    }

    @Test
    public void testAddToScenarioButtonEmptyUsagesTableClickListener() {
        //TODO {aliakh} implement
    }

    @Test
    public void testAddToScenarioButtonInvalidFilterSelectedClickListener() {
        //TODO {aliakh} implement
    }

    @Test
    public void testAddToScenarioButtonClickListenerInvalidRightsholders() {
        //TODO {aliakh} implement
    }

    @Test
    public void testAddToScenarioButtonClickListenerFasProductFamily() {
        //TODO {aliakh} implement
    }

    @Test
    public void testSendForResearchInvalidUsagesState() {
        //TODO {aliakh} implement
    }

    @Test
    public void testInitMediator() throws Exception {
        FasUsageMediator mediator = createMock(FasUsageMediator.class);
        expectNew(FasUsageMediator.class).andReturn(mediator).once();
        mediator.setLoadUsageBatchMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        mediator.setSendForResearchButton(anyObject(Button.class));
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
        assertEquals(7, layout.getComponentCount());
        verifyMenuBar(layout.getComponentAt(0), "Usage Batch", true, List.of("Load", "View"));
        assertEquals("Send for Research", ((Button) layout.getComponentAt(1)).getText());
        assertEquals("Load Researched Details", ((Button) layout.getComponentAt(2)).getText());
        assertEquals("Update Usages", ((Button) layout.getComponentAt(3)).getText());
        assertEquals("Add To Scenario", ((Button) layout.getComponentAt(4)).getText());
        assertEquals("Export", ((Button) layout.getComponentAt(5)).getText());
    }
}
