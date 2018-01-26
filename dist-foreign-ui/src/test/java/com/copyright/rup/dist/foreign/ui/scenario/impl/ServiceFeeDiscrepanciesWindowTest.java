package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.common.util.PercentColumnGenerator;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

import org.junit.Test;

import java.util.Collections;

/**
 * Verifies {@link RightsholderDiscrepanciesWindow}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/25/18
 *
 * @author Ihar Suvorau
 */
public class ServiceFeeDiscrepanciesWindowTest {

    @Test
    public void testConstructor() {
        ServiceFeeDiscrepanciesWindow window = new ServiceFeeDiscrepanciesWindow(Collections.emptyList());
        verifySize(window);
        assertEquals(1, window.getComponentCount());
        verifyTable(window.getComponent(0));
    }

    private void verifyTable(Component component) {
        assertTrue(component instanceof Table);
        Table table = (Table) component;
        verifySize(table);
        assertEquals("service-fee-discrepancies-table", table.getId());
        assertArrayEquals(new Object[]{"oldRightsholder.accountNumber", "oldRightsholder.name", "oldServiceFee",
            "newServiceFee"}, table.getVisibleColumns());
        assertArrayEquals(new Object[]{"RH Account #", "RH Name", "Service Fee %", "New Service Fee %"},
            table.getColumnHeaders());
        assertTrue(table.getColumnGenerator("oldRightsholder.accountNumber") instanceof LongColumnGenerator);
        assertTrue(table.getColumnGenerator("oldServiceFee") instanceof PercentColumnGenerator);
        assertTrue(table.getColumnGenerator("newServiceFee") instanceof PercentColumnGenerator);
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }
}
