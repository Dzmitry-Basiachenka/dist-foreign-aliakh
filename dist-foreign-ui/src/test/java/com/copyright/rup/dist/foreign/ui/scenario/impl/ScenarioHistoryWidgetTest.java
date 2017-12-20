package com.copyright.rup.dist.foreign.ui.scenario.impl;


import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link ScenarioHistoryWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 12/18/2017
 *
 * @author Uladzislau Shalamitski
 */
public class ScenarioHistoryWidgetTest {

    private ScenarioHistoryWidget widget;

    @Before
    public void setUp() {
        widget = new ScenarioHistoryWidget();
        widget.setController(createMock(ScenarioHistoryController.class));
        widget.init();
    }

    @Test
    public void testStructure() {
        assertEquals("scenario-history-widget", widget.getId());
        assertEquals(350, widget.getHeight(), 0);
        assertEquals(700, widget.getWidth(), 0);
        assertEquals(Unit.PIXELS, widget.getHeightUnits());
        assertEquals(Unit.PIXELS, widget.getWidthUnits());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(new MarginInfo(true, true, true, true), content.getMargin());
        assertEquals(2, content.getComponentCount());
        verifyTable(content.getComponent(0));
        verifyButton(content.getComponent(1));
    }

    private void verifyButton(Component component) {
        assertTrue(component instanceof Button);
        Button closeButton = (Button) component;
        assertEquals("Close", closeButton.getCaption());
        assertEquals("Close", closeButton.getId());
        assertTrue(closeButton.isEnabled());
    }

    private void verifyTable(Component component) {
        assertTrue(component instanceof Table);
        Table table = (Table) component;
        assertFalse(table.isSortEnabled());
        assertArrayEquals(new Object[]{"Type", "User", "Date", "Reason"}, table.getColumnHeaders());
        assertArrayEquals(new Object[]{"actionType", "createUser", "createDate", "actionReason"},
            table.getVisibleColumns());
    }
}
