package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    public void testStructure() {
        RefreshScenarioWindow window = new RefreshScenarioWindow(value -> null, value -> 0, null);
        assertEquals("Refresh Scenario", window.getCaption());
        assertEquals(400, window.getHeight(), 0);
        assertEquals(800, window.getWidth(), 0);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        verifyGrid(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2));
    }

    private void verifyGrid(Component component) {
        assertTrue(component instanceof Grid);
        Grid grid = (Grid) component;
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Detail ID", "Detail Status", "Product Family", "Usage Batch Name",
            "Fiscal Year", "RRO Account #", "RRO Name", "Payment Date", "Title", "Article", "Standard Number",
            "Wr Wrk Inst", "RH Account #", "RH Name", "Publisher", "Pub Date", "Number of Copies", "Reported value",
            "Amt in USD", "Gross Amt in USD", "Market", "Market Period From", "Market Period To", "Author"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        verifySize(grid);
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

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }
}
