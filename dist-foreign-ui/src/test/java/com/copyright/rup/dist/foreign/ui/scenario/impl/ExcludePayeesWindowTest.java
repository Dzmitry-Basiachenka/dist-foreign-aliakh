package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeesController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link ExcludePayeesWindow}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
public class ExcludePayeesWindowTest {

    private ExcludePayeesWindow window;

    @Before
    public void setUp() {
        window = new ExcludePayeesWindow(createMock(IExcludePayeesController.class));
    }

    @Test
    public void testStructure() {
        assertEquals("Exclude Details By Payee", window.getCaption());
        assertEquals("exclude-details-by-payee-window", window.getId());
        assertEquals(500, window.getHeight(), 0);
        assertEquals(1200, window.getWidth(), 0);
        assertTrue(window.isDraggable());
        assertTrue(window.isResizable());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        verifySearchWidget(content.getComponent(0));
        verifyGrid(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2));
    }

    private void verifySearchWidget(Component component) {
        assertTrue(component instanceof SearchWidget);
        SearchWidget searchWidget = (SearchWidget) component;
        assertEquals("Enter Payee Name/Account #",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
    }

    private void verifyGrid(Component component) {
        assertTrue(component instanceof Grid);
        Grid grid = (Grid) component;
        List<Grid.Column> columns = grid.getColumns();
        assertEquals(Arrays.asList(
            "Payee Account #", "Payee Name", "Amt in USD", "Service Fee Amount", "Net Amt in USD", "Service Fee %"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(3, horizontalLayout.getComponentCount());
        verifyButton((Button) horizontalLayout.getComponent(0), "Exclude Details");
        verifyButton((Button) horizontalLayout.getComponent(1), "Redesignate Details");
        verifyButton((Button) horizontalLayout.getComponent(2), "Close");
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(new MarginInfo(false, false), horizontalLayout.getMargin());
    }

    private void verifyButton(Button button, String name) {
        assertEquals(name, button.getCaption());
        assertEquals(name.replaceAll(" ", "_"), button.getId());
    }
}
