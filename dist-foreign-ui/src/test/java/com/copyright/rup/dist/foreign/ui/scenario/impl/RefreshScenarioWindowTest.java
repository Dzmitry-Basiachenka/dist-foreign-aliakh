package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.vaadin.ui.LocalDateColumnGenerator;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
import com.copyright.rup.vaadin.ui.MoneyColumnGenerator;

import com.google.common.collect.Lists;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

/**
 * Verifies {@link RefreshScenarioWindow}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/12/18
 *
 * @author Ihar Suvorau
 */
public class RefreshScenarioWindowTest {

    private static final String DETAIL_ID_PROPERTY = "detailId";
    private static final String GROSS_AMOUNT_PROPERTY = "grossAmount";
    private static final String REPORTED_VALUE_PROPERTY = "reportedValue";
    private static final String BATCH_GROSS_AMOUNT_PROPERTY = "batchGrossAmount";

    @Test
    public void testStructure() {
        RefreshScenarioWindow window = new RefreshScenarioWindow(Lists.newArrayList());
        assertEquals("Refresh Scenario", window.getCaption());
        assertEquals(400, window.getHeight(), 0);
        assertEquals(800, window.getWidth(), 0);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        verifyTable(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2));
    }

    private void verifyTable(Component component) {
        assertTrue(component instanceof Table);
        Table table = (Table) component;
        assertArrayEquals(new Object[]{DETAIL_ID_PROPERTY, "status", "productFamily", "batchName", "fiscalYear",
            "rroAccountNumber", "rroName", "paymentDate", "workTitle", "article", "standardNumber", "wrWrkInst",
            "rhAccountNumber", "rhName", "publisher", "publicationDate", "numberOfCopies", REPORTED_VALUE_PROPERTY,
            GROSS_AMOUNT_PROPERTY, BATCH_GROSS_AMOUNT_PROPERTY, "market", "marketPeriodFrom", "marketPeriodTo",
            "author"}, table.getVisibleColumns());
        assertArrayEquals(new Object[]{"Detail ID", "Detail Status", "Product Family", "Usage Batch Name",
                "Fiscal Year", "RRO Account #", "RRO Name", "Payment Date", "Title", "Article", "Standard Number",
                "Wr Wrk Inst", "RH Account #", "RH Name", "Publisher", "Pub Date", "Number of Copies", "Reported value",
                "Amt in USD", "Gross Amt in USD", "Market", "Market Period From", "Market Period To", "Author"},
            table.getColumnHeaders());
        assertTrue(table.isColumnCollapsingAllowed());
        assertFalse(table.isColumnCollapsible(DETAIL_ID_PROPERTY));
        verifyGeneratedColumns(table);
        verifySize(table);
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        Button okButton = (Button) horizontalLayout.getComponent(0);
        assertEquals("Ok", okButton.getCaption());
        assertEquals("Ok", okButton.getId());
        Button cancelButton = (Button) horizontalLayout.getComponent(1);
        assertEquals("Cancel", cancelButton.getCaption());
        assertEquals("Cancel", cancelButton.getId());
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(new MarginInfo(false, false, false, false), horizontalLayout.getMargin());
    }

    private void verifyGeneratedColumns(Table table) {
        verifyColumnGenerator(table.getColumnGenerator(DETAIL_ID_PROPERTY), LongColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator("wrWrkInst"), LongColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator("rhAccountNumber"), LongColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator("rroAccountNumber"), LongColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator("publicationDate"), LocalDateColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator("paymentDate"), LocalDateColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(REPORTED_VALUE_PROPERTY), MoneyColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(GROSS_AMOUNT_PROPERTY), MoneyColumnGenerator.class);
    }

    private void verifyColumnGenerator(Table.ColumnGenerator columnGenerator, Class clazz) {
        assertNotNull(columnGenerator);
        assertEquals(clazz, columnGenerator.getClass());
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }
}
