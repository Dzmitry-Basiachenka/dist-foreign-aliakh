package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link AaclExcludePayeeWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/05/2020
 *
 * @author Ihar Suvorau
 */
public class AaclExcludePayeeWidgetTest {

    @Test
    public void testStructure() {
        AaclExcludePayeeWidget widget = new AaclExcludePayeeWidget();
        assertEquals("Exclude Details By Payee", widget.getCaption());
        assertEquals("exclude-details-by-payee-window", widget.getId());
        assertEquals(500, widget.getHeight(), 0);
        assertEquals(1200, widget.getWidth(), 0);
        assertTrue(widget.isDraggable());
        assertTrue(widget.isResizable());
        HorizontalSplitPanel splitPanel = (HorizontalSplitPanel) widget.getContent();
        VerticalLayout content = (VerticalLayout) splitPanel.getSecondComponent();
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
        assertEquals(
            Arrays.asList("Payee Account #", "Payee Name", "Gross Amt in USD", "Service Fee Amount", "Net Amt in USD"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(3, horizontalLayout.getComponentCount());
        verifyButton((Button) horizontalLayout.getComponent(0), "Exclude Details");
        verifyButton((Button) horizontalLayout.getComponent(1), "Clear");
        verifyButton((Button) horizontalLayout.getComponent(2), "Close");
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(new MarginInfo(false, false), horizontalLayout.getMargin());
    }

    private void verifyButton(Button button, String name) {
        assertEquals(name, button.getCaption());
        assertEquals(name.replaceAll(" ", "_"), button.getId());
    }
}
