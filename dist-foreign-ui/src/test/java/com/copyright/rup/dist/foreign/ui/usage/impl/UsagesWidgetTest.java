package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.lazytable.LazyTable;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;
import org.vaadin.addons.lazyquerycontainer.QueryView;

import java.util.Collection;
import java.util.Collections;

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
public class UsagesWidgetTest {

    private UsagesWidget usagesWidget;
    private UsagesController controller;

    @Before
    public void setUp() {
        controller = createMock(UsagesController.class);
        UsagesFilterWidget filterWidget = new UsagesFilterWidget();
        usagesWidget = new UsagesWidget();
        usagesWidget.setController(controller);
        expect(controller.initUsagesFilterWidget()).andReturn(filterWidget).once();
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
        verifyTable((Table) layout.getComponent(1));
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    public void testGetController() {
        assertSame(controller, usagesWidget.getController());
    }

    @Test
    @PrepareForTest(Windows.class)
    public void testLoadButtonClickListener() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button loadButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(0);
        Collection<?> listeners = loadButton.getListeners(ClickEvent.class);
        assertEquals(1, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        Windows.showModalWindow(isA(UsageUploadWindow.class));
        expectLastCall().once();
        replay(clickEvent, Windows.class);
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, Windows.class);
    }

    @Test
    @PrepareForTest(Windows.class)
    public void testAddToScenarioButtonClickListener() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(1);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        assertEquals(1, listeners.size());
        Windows.showNotificationWindow("Add to scenario button clicked");
        expectLastCall().once();
        replay(clickEvent, Windows.class);
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, Windows.class);
    }

    @Test
    public void testRefresh() {
        LazyTable usagesTableMock = createMock(LazyTable.class);
        LazyQueryContainer containerMock = createMock(LazyQueryContainer.class);
        QueryView queryView = createMock(QueryView.class);
        Whitebox.setInternalState(containerMock, "itemSetChangeListeners", Collections.emptyList());
        Whitebox.setInternalState(containerMock, "queryView", queryView);
        Whitebox.setInternalState(usagesWidget, "usagesTable", usagesTableMock);
        expect(usagesTableMock.getContainerDataSource()).andReturn(containerMock).once();
        containerMock.refresh();
        expectLastCall().once();
        replay(usagesTableMock, containerMock, queryView);
        usagesWidget.refresh();
        verify(usagesTableMock, containerMock, queryView);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(2, layout.getComponentCount());
        assertEquals("Load", layout.getComponent(0).getCaption());
        assertEquals("Add To Scenario", layout.getComponent(1).getCaption());
    }

    private void verifyTable(Table table) {
        assertArrayEquals(new Object[]{"wrWrkInst", "workTitle", "rightsholder.accountNumber", "rightsholder.name",
            "paymentDate", "grossAmount", "rro.accountNumber", "eligible"}, table.getVisibleColumns());
        assertArrayEquals(
            new Object[]{"Wr Wrk Inst", "Work Title", "RH Acct #", "RH Name", "Payment Date", "Gross Amount",
                "RRO", "Eligible"}, table.getColumnHeaders());
        Collection<?> containerPropertyIds = table.getContainerPropertyIds();
        assertTrue(containerPropertyIds.contains("id"));
        assertTrue(containerPropertyIds.contains("wrWrkInst"));
        assertTrue(containerPropertyIds.contains("workTitle"));
        assertTrue(containerPropertyIds.contains("rightsholder.accountNumber"));
        assertTrue(containerPropertyIds.contains("rightsholder.name"));
        assertTrue(containerPropertyIds.contains("paymentDate"));
        assertTrue(containerPropertyIds.contains("grossAmount"));
        assertTrue(containerPropertyIds.contains("rro.accountNumber"));
        assertTrue(containerPropertyIds.contains("eligible"));
        verifySize(table);
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }
}
