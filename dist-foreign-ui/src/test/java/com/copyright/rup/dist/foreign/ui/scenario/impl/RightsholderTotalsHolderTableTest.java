package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.impl.RightsholderTotalsHolderTable.RightsholderAccountNumberColumnGenerator;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
import com.copyright.rup.vaadin.ui.MoneyColumnGenerator;
import com.copyright.rup.vaadin.ui.component.lazytable.LazyTable;

import com.vaadin.server.Sizeable.Unit;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * Verifies {@link RightsholderTotalsHolderTable}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/07/17
 *
 * @author Ihar Suvorau
 */
public class RightsholderTotalsHolderTableTest {

    private static final String RIGHTSHOLDER_NAME_PROPERTY = "rightsholder.name";
    private static final String RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY = "rightsholder.accountNumber";
    private static final String PAYEE_NAME_PROPERTY = "payee.name";
    private static final String PAYEE_ACCOUNT_NUMBER_PROPERTY = "payee.accountNumber";
    private static final String GROSS_TOTAL_PROPERTY = "grossTotal";
    private static final String SERVICE_FEE_TOTAL_PROPERTY = "serviceFeeTotal";
    private static final String NET_TOTAL_PROPERTY = "netTotal";
    private static final String SERVICE_FEE_PROPERTY = "serviceFee";

    private RightsholderTotalsHolderTable table;

    @Before
    public void setUp() {
        table = new RightsholderTotalsHolderTable(createMock(IScenarioController.class),
            RightsholderTotalsHolderBeanQuery.class);
    }

    @Test
    public void testGetIdProperty() {
        assertEquals(RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY, table.getIdProperty());
    }

    @Test
    public void testTableStructure() {
        verifySize();
        assertTrue(table.getStyleName().contains("rightsholders-totals-table"));
        assertTrue(table.isColumnCollapsingAllowed());
        assertFalse(table.isColumnCollapsible(RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY));
        assertArrayEquals(
            new Object[]{RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY, RIGHTSHOLDER_NAME_PROPERTY,
                PAYEE_ACCOUNT_NUMBER_PROPERTY, PAYEE_NAME_PROPERTY, GROSS_TOTAL_PROPERTY, SERVICE_FEE_TOTAL_PROPERTY,
                NET_TOTAL_PROPERTY, SERVICE_FEE_PROPERTY},
            table.getVisibleColumns());
        assertArrayEquals(new Object[]{"RH Account #", "RH Name", "Payee Account #", "Payee Name", "Amt in USD",
                "Service Fee Amount", "Net Amt in USD", "Service Fee %"},
            table.getColumnHeaders());
        verifyProperties();
        verifyColumnsSizes();
        verifyGeneratedColumns();
        verifyFooter();
    }

    private void verifyFooter() {
        assertTrue(table.getStyleName().contains("table-ext-footer"));
        assertTrue(table.isFooterVisible());
        assertEquals("Totals", table.getColumnFooter(RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY));
    }

    private void verifyProperties() {
        Collection<?> containerPropertyIds = table.getContainerPropertyIds();
        assertTrue(containerPropertyIds.contains(RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY));
        assertTrue(containerPropertyIds.contains(RIGHTSHOLDER_NAME_PROPERTY));
        assertTrue(containerPropertyIds.contains(PAYEE_ACCOUNT_NUMBER_PROPERTY));
        assertTrue(containerPropertyIds.contains(PAYEE_NAME_PROPERTY));
        assertTrue(containerPropertyIds.contains(GROSS_TOTAL_PROPERTY));
        assertTrue(containerPropertyIds.contains(SERVICE_FEE_TOTAL_PROPERTY));
        assertTrue(containerPropertyIds.contains(NET_TOTAL_PROPERTY));
        assertTrue(containerPropertyIds.contains(SERVICE_FEE_PROPERTY));
    }

    private void verifyColumnsSizes() {
        double delta = 0.0000001;
        assertEquals(0.1, table.getColumnExpandRatio(RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY), delta);
        assertEquals(0.2, table.getColumnExpandRatio(RIGHTSHOLDER_NAME_PROPERTY), delta);
        assertEquals(0.1, table.getColumnExpandRatio(PAYEE_ACCOUNT_NUMBER_PROPERTY), delta);
        assertEquals(0.2, table.getColumnExpandRatio(PAYEE_NAME_PROPERTY), delta);
        assertEquals(0.1, table.getColumnExpandRatio(GROSS_TOTAL_PROPERTY), delta);
        assertEquals(0.1, table.getColumnExpandRatio(SERVICE_FEE_TOTAL_PROPERTY), delta);
        assertEquals(0.1, table.getColumnExpandRatio(NET_TOTAL_PROPERTY), delta);
        assertEquals(0.1, table.getColumnExpandRatio(SERVICE_FEE_PROPERTY), delta);
    }

    private void verifyGeneratedColumns() {
        verifyColumnGenerator(table.getColumnGenerator(RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY),
            RightsholderAccountNumberColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(PAYEE_ACCOUNT_NUMBER_PROPERTY), LongColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(GROSS_TOTAL_PROPERTY), MoneyColumnGenerator.class);
    }

    private void verifyColumnGenerator(LazyTable.ColumnGenerator columnGenerator, Class clazz) {
        assertNotNull(columnGenerator);
        assertEquals(clazz, columnGenerator.getClass());
    }

    private void verifySize() {
        assertEquals(100, table.getWidth(), 0);
        assertEquals(100, table.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, table.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, table.getWidthUnits());
    }
}
