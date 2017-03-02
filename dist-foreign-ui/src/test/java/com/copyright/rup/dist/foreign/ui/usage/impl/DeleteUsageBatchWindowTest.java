package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.common.util.FiscalYearColumnGenerator;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.impl.DeleteUsageBatchWindow.ConfirmDeleteListener;
import com.copyright.rup.dist.foreign.ui.usage.impl.DeleteUsageBatchWindow.PaymentDateFilter;
import com.copyright.rup.dist.foreign.ui.usage.impl.DeleteUsageBatchWindow.SearchController;
import com.copyright.rup.vaadin.ui.LocalDateColumnGenerator;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.google.common.collect.Lists;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

/**
 * Verifies {@link DeleteUsageBatchWindow}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 2/17/17
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class DeleteUsageBatchWindowTest {

    private static final String USAGE_BATCH_ID = RupPersistUtils.generateUuid();
    private static final String USAGE_BATCH_NAME = "Batch Name";
    private static final String PAYMENT_DATE_PROPERTY = "paymentDate";
    private static final String NAME_PROPERTY = "name";
    private static final String FISCAL_YEAR_PROPERTY = "fiscalYear";
    private static final String DELETE_PROPERTY = "delete";

    private DeleteUsageBatchWindow usageBatchWindow;
    private IUsagesController controller;

    @Before
    public void setUp() {
        controller = createMock(IUsagesController.class);
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(USAGE_BATCH_ID);
        usageBatch.setName(USAGE_BATCH_NAME);
        usageBatch.setPaymentDate(LocalDate.now());
        expect(controller.getUsageBatches()).andReturn(Lists.newArrayList(usageBatch)).once();
        replay(controller);
        usageBatchWindow = new DeleteUsageBatchWindow(controller);
        verify(controller);
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        assertEquals("Delete Usage Batch", usageBatchWindow.getCaption());
        verifySize(usageBatchWindow, 700, Unit.PIXELS, 450, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) usageBatchWindow.getContent();
        assertEquals(new MarginInfo(true), content.getMargin());
        assertTrue(content.isSpacing());
        verifySize(content, 100, Unit.PERCENTAGE, 100, Unit.PERCENTAGE);
        assertEquals(3, content.getComponentCount());
        assertEquals(SearchWidget.class, content.getComponent(0).getClass());
        Component component = content.getComponent(1);
        assertEquals(Table.class, component.getClass());
        verifyTable((Table) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        component = content.getComponent(2);
        assertEquals(Button.class, component.getClass());
        assertEquals("Close", component.getCaption());
        assertEquals(Alignment.MIDDLE_RIGHT, content.getComponentAlignment(component));
    }

    @Test
    public void testPaymentDateFilterAppliesToProperty() {
        DeleteUsageBatchWindow.PaymentDateFilter filter = new PaymentDateFilter("value");
        assertTrue(filter.appliesToProperty(PAYMENT_DATE_PROPERTY));
        assertFalse(filter.appliesToProperty(NAME_PROPERTY));
        assertFalse(filter.appliesToProperty(FISCAL_YEAR_PROPERTY));
        assertFalse(filter.appliesToProperty(DELETE_PROPERTY));
    }

    @Test
    public void testDeleteClickListenerEmptyAssociatedScenarios() {
        mockStatic(Windows.class);
        Capture<ConfirmDeleteListener> listenerCapture = new Capture<>();
        Window confirmWindowCapture = createMock(Window.class);
        VerticalLayout content = (VerticalLayout) usageBatchWindow.getContent();
        Table table = (Table) content.getComponent(1);
        ColumnGenerator columnGenerator = table.getColumnGenerator(DELETE_PROPERTY);
        Object generatedCell = columnGenerator.generateCell(table, USAGE_BATCH_ID, DELETE_PROPERTY);
        assertEquals(Button.class, generatedCell.getClass());
        Button button = (Button) generatedCell;
        assertEquals("Delete", button.getCaption());
        Collection<?> listeners = button.getListeners(ClickEvent.class);
        assertEquals(1, listeners.size());
        ClickListener listener = (ClickListener) listeners.iterator().next();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        expect(Windows.showConfirmDialog(eq("Are you sure you want to delete usage batch 'Batch Name'?"),
            capture(listenerCapture))).andReturn(confirmWindowCapture).once();
        replay(controller, confirmWindowCapture, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, Windows.class);
    }

    @Test
    public void testDeleteClickListener() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        VerticalLayout content = (VerticalLayout) usageBatchWindow.getContent();
        Table table = (Table) content.getComponent(1);
        ColumnGenerator columnGenerator = table.getColumnGenerator(DELETE_PROPERTY);
        Object generatedCell = columnGenerator.generateCell(table, USAGE_BATCH_ID, DELETE_PROPERTY);
        assertEquals(Button.class, generatedCell.getClass());
        Button button = (Button) generatedCell;
        assertEquals("Delete", button.getCaption());
        Collection<?> listeners = button.getListeners(ClickEvent.class);
        assertEquals(1, listeners.size());
        ClickListener listener = (ClickListener) listeners.iterator().next();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(Lists.newArrayList("Scenario 1", "Scenario 2")).once();
        Windows.showNotificationWindow("Usage batch can not be deleted because it is associated with the following " +
            "scenarios:<ul><li>Scenario 1</li><li>Scenario 2</li></ul>");
        expectLastCall().once();
        replay(controller, confirmWindowCapture, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, Windows.class);
    }

    @Test
    public void testConfirmDeleteListener() {
        BeanContainer<String, UsageBatch> container = createMock(BeanContainer.class);
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(USAGE_BATCH_ID);
        ConfirmDeleteListener listener = new ConfirmDeleteListener(controller, usageBatch, container);
        controller.deleteUsageBatch(usageBatch);
        expectLastCall().once();
        expect(container.removeItem(USAGE_BATCH_ID)).andReturn(true).once();
        replay(container, controller);
        listener.onActionConfirmed();
        verify(container, controller);
    }

    @Test
    public void testSearchControllerEmptySearchValue() {
        SearchController searchController = usageBatchWindow.new SearchController();
        BeanContainer<String, UsageBatch> container = Whitebox.getInternalState(usageBatchWindow, "container");
        searchController.performSearch();
        assertTrue(container.getContainerFilters().isEmpty());
    }

    @Test
    public void testSearchController() {
        SearchController searchController = usageBatchWindow.new SearchController();
        SearchWidget searchWidget = Whitebox.getInternalState(usageBatchWindow, "searchWidget");
        searchWidget.setSearchValue("value");
        BeanContainer<String, UsageBatch> container = Whitebox.getInternalState(usageBatchWindow, "container");
        assertTrue(container.getContainerFilters().isEmpty());
        searchController.performSearch();
        Collection<Filter> containerFilters = container.getContainerFilters();
        assertFalse(containerFilters.isEmpty());
        assertEquals(1, containerFilters.size());
    }

    private void verifySize(Component component, float width, Unit widthUnit, float height, Unit heightUnit) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnit, component.getHeightUnits());
        assertEquals(widthUnit, component.getWidthUnits());
    }

    private void verifyTable(Table table) {
        assertNull(table.getCaption());
        verifySize(table, 100, Unit.PERCENTAGE, 100, Unit.PERCENTAGE);
        assertArrayEquals(new Object[]{NAME_PROPERTY, PAYMENT_DATE_PROPERTY, FISCAL_YEAR_PROPERTY, DELETE_PROPERTY},
            table.getVisibleColumns());
        assertArrayEquals(new Object[]{"Usage Batch Name", "Payment Date", "Fiscal Year", StringUtils.EMPTY},
            table.getColumnHeaders());
        assertEquals(65, table.getColumnWidth(DELETE_PROPERTY));
        assertEquals(100, table.getColumnWidth(PAYMENT_DATE_PROPERTY));
        assertEquals(80, table.getColumnWidth(FISCAL_YEAR_PROPERTY));
        assertEquals(1, table.getColumnExpandRatio(NAME_PROPERTY), 0);
        assertEquals(LocalDateColumnGenerator.class, table.getColumnGenerator(PAYMENT_DATE_PROPERTY).getClass());
        assertEquals(FiscalYearColumnGenerator.class, table.getColumnGenerator(FISCAL_YEAR_PROPERTY).getClass());
        assertNotNull(table.getColumnGenerator(DELETE_PROPERTY));
    }
}
