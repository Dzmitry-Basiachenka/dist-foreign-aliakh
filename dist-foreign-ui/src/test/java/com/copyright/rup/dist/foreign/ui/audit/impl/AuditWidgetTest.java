package com.copyright.rup.dist.foreign.ui.audit.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.audit.api.IAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.util.PercentColumnGenerator;
import com.copyright.rup.vaadin.ui.LocalDateColumnGenerator;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
import com.copyright.rup.vaadin.ui.MoneyColumnGenerator;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.ui.component.lazytable.LazyTable;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link AuditWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/23/18
 *
 * @author Aliaksandr Radkevich
 */
public class AuditWidgetTest {

    private AuditWidget widget;
    private IAuditController controller;

    @Before
    public void setUp() {
        controller = createMock(IAuditController.class);
        widget = new AuditWidget();
        widget.setController(controller);
    }

    @Test
    public void testInit() {
        IAuditFilterController filterController = createMock(IAuditFilterController.class);
        IAuditFilterWidget filterWidget = new AuditFilterWidget();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(controller.getAuditFilterController()).andReturn(filterController).once();
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        expect(controller.getExportUsagesStreamSource()).andReturn(streamSource).once();
        replay(controller, filterController, streamSource);
        widget.init();
        verify(controller, filterController, streamSource);
        reset(controller, filterController, streamSource);
        assertEquals(200, widget.getSplitPosition(), 0);
        assertTrue(widget.isLocked());
        assertEquals("audit-widget", widget.getStyleName());
        Component component = widget.getFirstComponent();
        assertTrue(component instanceof IAuditFilterWidget);
        component = widget.getSecondComponent();
        assertTrue(component instanceof VerticalLayout);
        verifyTableLayout((VerticalLayout) component);
    }

    private void verifyTableLayout(VerticalLayout layout) {
        verifySize(layout, Unit.PERCENTAGE, 100, Unit.PERCENTAGE, 100);
        assertTrue(layout.isSpacing());
        assertEquals(2, layout.getComponentCount());
        Component component = layout.getComponent(0);
        assertTrue(component instanceof HorizontalLayout);
        verifyToolbar((HorizontalLayout) component);
        component = layout.getComponent(1);
        assertTrue(component instanceof LazyTable);
        verifyTable((LazyTable) component);
    }

    private void verifyToolbar(HorizontalLayout layout) {
        verifySize(layout, Unit.PERCENTAGE, 100, Unit.PIXELS, -1);
        assertEquals(new MarginInfo(false, true, false, true), layout.getMargin());
        assertEquals(2, layout.getComponentCount());
        Component component = layout.getComponent(0);
        assertTrue(component instanceof Button);
        verifyExportButton((Button) component);
        component = layout.getComponent(1);
        assertTrue(component instanceof SearchWidget);
        verifySearchWidget((SearchWidget) component);
    }

    private void verifySearchWidget(SearchWidget searchWidget) {
        verifySize(searchWidget, Unit.PERCENTAGE, 75, Unit.PIXELS, -1);
        assertEquals("Enter Detail ID or Wr Wrk Inst or Work Title",
            Whitebox.getInternalState(searchWidget, TextField.class).getInputPrompt());
    }

    private void verifyExportButton(Button button) {
        assertEquals("Export", button.getCaption());
        assertEquals(1, button.getExtensions().size());
    }

    private void verifyTable(LazyTable table) {
        verifySize(table, Unit.PERCENTAGE, 100, Unit.PERCENTAGE, 100);
        assertArrayEquals(new Object[]{"detailId", "status", "batchName", "paymentDate", "rhAccountNumber", "rhName",
                "wrWrkInst", "workTitle", "standardNumber", "grossAmount", "serviceFee", "scenarioName"},
            table.getVisibleColumns());
        assertArrayEquals(new Object[]{"Detail ID", "Detail Status", "Usage Batch Name", "Payment Date", "RH Account #",
                "RH Name", "Wr Wrk Inst", "Title", "Standard Number", "Amt in USD", "Service Fee %", "Scenario Name"},
            table.getColumnHeaders());
        assertNotNull(table.getColumnGenerator("detailId"));
        assertTrue(table.getColumnGenerator("wrWrkInst") instanceof LongColumnGenerator);
        assertTrue(table.getColumnGenerator("rhAccountNumber") instanceof LongColumnGenerator);
        assertTrue(table.getColumnGenerator("grossAmount") instanceof MoneyColumnGenerator);
        assertTrue(table.getColumnGenerator("paymentDate") instanceof LocalDateColumnGenerator);
        assertTrue(table.getColumnGenerator("serviceFee") instanceof PercentColumnGenerator);
        assertTrue(table.isColumnCollapsingAllowed());
    }

    private void verifySize(Component component, Unit widthUnit, float width, Unit heightUnit, float height) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnit, component.getHeightUnits());
        assertEquals(widthUnit, component.getWidthUnits());
    }
}
