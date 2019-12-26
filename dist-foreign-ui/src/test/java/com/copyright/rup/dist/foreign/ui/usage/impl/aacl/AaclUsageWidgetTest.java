package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
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

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
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
@PrepareForTest({AaclUsageWidget.class, Windows.class, ForeignSecurityUtils.class})
public class AaclUsageWidgetTest {

    private AaclUsageWidget usagesWidget;
    private IAaclUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(IAaclUsageController.class);
        AaclUsageFilterWidget filterWidget =
            new AaclUsageFilterWidget(createMock(IAaclUsageFilterController.class));
        filterWidget.getFilter().setUsageBatchesIds(Collections.singleton("3a070817-03ae-4ebd-b25f-dd3168a7ffb0"));
        usagesWidget = new AaclUsageWidget(controller);
        usagesWidget.setController(controller);
        expect(controller.initUsagesFilterWidget()).andReturn(filterWidget).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).times(2);
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
        replay(AaclUsageMediator.class, mediator, controller);
        assertNotNull(usagesWidget.initMediator());
        verify(AaclUsageMediator.class, mediator, controller);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(1, layout.getComponentCount());
        verifyUsageBatchMenuBar(layout.getComponent(0), "Usage Batch", Arrays.asList("Load"));
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

    private void verifyGrid(Grid grid) {
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Detail ID", "Detail Status", "Product Family", "Usage Batch Name",
            "Period End Date", "RH Account #", "RH Name", "Wr Wrk Inst", "System Title", "Standard Number",
            "Standard Number Type", "Detail Licensee Class ID", "Enrollment Profile", "Discipline", "Publication Type",
            "Institution", "Usage Period", "Usage Source", "Number of Copies", "Number of Pages", "Right Limitation",
            "Comment"), columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        verifySize(grid);
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }
}
