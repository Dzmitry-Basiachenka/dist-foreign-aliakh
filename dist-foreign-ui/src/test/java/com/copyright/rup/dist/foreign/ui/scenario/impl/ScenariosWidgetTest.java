package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.vaadin.ui.DateColumnGenerator;

import com.google.common.collect.Lists;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Verifies {@link ScenariosWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
public class ScenariosWidgetTest {

    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();

    private ScenariosWidget scenariosWidget;
    private IScenariosController controller;
    private Scenario scenario;

    @Before
    public void setUp() {
        controller = createMock(IScenariosController.class);
        scenariosWidget = new ScenariosWidget();
        scenariosWidget.setController(controller);
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setDescription("Description");
        scenario.setNetTotal(new BigDecimal("10000.00"));
        scenario.setGrossTotal(new BigDecimal("20000.00"));
        scenario.setReportedTotal(new BigDecimal("30000.00"));
        scenario.setCreateUser("User@copyright.com");
        expect(controller.getScenarios()).andReturn(Lists.newArrayList(scenario)).once();
        replay(controller);
        scenariosWidget.init();
        scenariosWidget.initMediator();
        verify(controller);
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        assertEquals(2, scenariosWidget.getComponentCount());
        Component component = scenariosWidget.getComponent(0);
        assertTrue(component instanceof HorizontalLayout);
        verifyButtonsLayout((HorizontalLayout) component);
        component = scenariosWidget.getComponent(1);
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        component = layout.getComponent(0);
        assertTrue(component instanceof Table);
        verifyTable((Table) component);
        component = layout.getComponent(1);
        assertTrue(component instanceof Panel);
        verifyPanel((Panel) component);
    }

    @Test
    public void testInitMediator() {
        assertTrue(scenariosWidget.initMediator() instanceof ScenariosMediator);
    }

    @Test
    public void testRefresh() {
        expect(controller.getScenarios()).andReturn(Lists.newArrayList(scenario)).once();
        replay(controller);
        scenariosWidget.refresh();
        verifyScenarioMetadataPanel();
        verify(controller);
    }

    @Test
    public void testSelectScenario() {
        scenariosWidget.selectScenario(null);
        Table table = Whitebox.getInternalState(scenariosWidget, "table");
        assertNull(table.getValue());
        scenariosWidget.selectScenario(SCENARIO_ID);
        assertEquals(SCENARIO_ID, table.getValue());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetSelectedScenario() {
        Table scenariosTable = createMock(Table.class);
        Whitebox.setInternalState(scenariosWidget, "table", scenariosTable);
        BeanContainer<String, Scenario> container = createMock(BeanContainer.class);
        Whitebox.setInternalState(scenariosWidget, "container", container);
        expect(scenariosTable.getValue()).andReturn(SCENARIO_ID).once();
        expect(container.getItem(SCENARIO_ID)).andReturn(new BeanItem(scenario)).once();
        replay(scenariosTable, container);
        assertEquals(scenario, scenariosWidget.getSelectedScenario());
        verify(scenariosTable, container);
    }

    @Test
    public void testGetNotSelectedScenario() {
        Table scenariosTable = createMock(Table.class);
        Whitebox.setInternalState(scenariosWidget, "table", scenariosTable);
        expect(scenariosTable.getValue()).andReturn(null).once();
        replay(scenariosTable);
        assertEquals(null, scenariosWidget.getSelectedScenario());
        verify(scenariosTable);
    }

    private void verifyPanel(Panel panel) {
        verifySize(panel);
        Component content = panel.getContent();
        assertTrue(content instanceof Label);
        Label label = (Label) content;
        assertEquals(StringUtils.EMPTY, label.getValue());
    }

    private void verifyTable(Table table) {
        verifySize(table);
        assertEquals("scenarios-table", table.getId());
        assertArrayEquals(new Object[]{"name", "createDate", "status"}, table.getVisibleColumns());
        assertArrayEquals(new Object[]{"Name", "Create Date", "Status"}, table.getColumnHeaders());
        assertEquals(100, table.getColumnWidth("createDate"));
        assertEquals(130, table.getColumnWidth("status"));
        assertEquals(1, table.getColumnExpandRatio("name"), 0);
        assertTrue(table.getColumnGenerator("createDate") instanceof DateColumnGenerator);
        Collection<?> listeners = table.getListeners(ValueChangeEvent.class);
        assertEquals(1, listeners.size());
        ValueChangeListener listener = (ValueChangeListener) listeners.iterator().next();
        verifyValueChangeListener(listener);
        verifyValueChangeListenerNoSelectedItem(listener);
    }

    private void verifyValueChangeListenerNoSelectedItem(ValueChangeListener listener) {
        ValueChangeEvent eventMock = createMock(ValueChangeEvent.class);
        Property propertyMock = createMock(Property.class);
        expect(propertyMock.getValue()).andReturn(RupPersistUtils.generateUuid()).once();
        expect(eventMock.getProperty()).andReturn(propertyMock).once();
        replay(eventMock, propertyMock);
        listener.valueChange(eventMock);
        verify(eventMock, propertyMock);
        reset(eventMock, propertyMock);
    }

    private void verifyValueChangeListener(ValueChangeListener listener) {
        ValueChangeEvent eventMock = createMock(ValueChangeEvent.class);
        Property propertyMock = createMock(Property.class);
        expect(propertyMock.getValue()).andReturn(SCENARIO_ID).once();
        expect(eventMock.getProperty()).andReturn(propertyMock).once();
        replay(eventMock, propertyMock);
        listener.valueChange(eventMock);
        verifyScenarioMetadataPanel();
        Button deleteButton = (Button) ((HorizontalLayout) scenariosWidget.getComponent(0)).getComponent(0);
        assertTrue(deleteButton.isEnabled());
        verify(eventMock, propertyMock);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertEquals("scenarios-buttons", layout.getId());
        assertEquals(2, layout.getComponentCount());
        verifyViewButton(layout.getComponent(0));
        verifyDeleteButton(layout.getComponent(1));
    }

    private void verifyViewButton(Component component) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals("View", button.getCaption());
        assertEquals("View", button.getId());
        assertFalse(button.isEnabled());
        assertTrue(button.isDisableOnClick());
        assertEquals(2, button.getListeners(ClickEvent.class).size());
    }

    private void verifyDeleteButton(Component component) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals("Delete", button.getCaption());
        assertEquals("Delete", button.getId());
        assertFalse(button.isEnabled());
        assertTrue(button.isDisableOnClick());
        assertEquals(2, button.getListeners(ClickEvent.class).size());
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private void verifyScenarioMetadataPanel() {
        Panel panel = (Panel) ((HorizontalLayout) scenariosWidget.getComponent(1)).getComponent(1);
        assertEquals("scenarios-metadata", panel.getId());
        Component content = panel.getContent();
        assertTrue(content instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) content;
        assertEquals(new MarginInfo(false, true, false, true), layout.getMargin());
        assertEquals(100, layout.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, layout.getWidthUnits());
        assertEquals(5, layout.getComponentCount());
        verifyMetadataLabel(layout.getComponent(0), "<b>Owner: </b>User@copyright.com");
        verifyMetadataLabel(layout.getComponent(1),
            "<b>Distribution Total: </b><span class='label-amount'>10,000.00</span>");
        verifyMetadataLabel(layout.getComponent(2),
            "<b>Gross Total: </b><span class='label-amount'>20,000.00</span>");
        verifyMetadataLabel(layout.getComponent(3),
            "<b>Reported Total: </b><span class='label-amount'>30,000.00</span>");
        verifyMetadataLabel(layout.getComponent(4), "<b>Description: </b>Description");
    }

    private void verifyMetadataLabel(Component component, String expectedValue) {
        assertTrue(component instanceof Label);
        assertEquals(expectedValue, ((Label) component).getValue());
    }
}
