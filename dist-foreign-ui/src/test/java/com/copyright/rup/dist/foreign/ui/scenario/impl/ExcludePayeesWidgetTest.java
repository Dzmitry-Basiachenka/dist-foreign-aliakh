package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeesController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeesFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeesFilterWidget;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link ExcludePayeesWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
public class ExcludePayeesWidgetTest {

    private ExcludePayeesWidget widget;
    private IExcludePayeesController controller;
    private IExcludePayeesFilterController filterController;
    private IExcludePayeesFilterWidget filterWidget;

    @Before
    public void setUp() {
        controller = createMock(IExcludePayeesController.class);
        widget = new ExcludePayeesWidget();
        widget.setController(controller);
        filterController = PowerMock.createMock(IExcludePayeesFilterController.class);
        filterWidget = new ExcludePayeesFilterWidget();
        filterWidget.setController(filterController);
        initWidget();
    }

    @Test
    public void testStructure() {
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

    private void initWidget() {
        expect(controller.getExcludePayeesFilterController()).andReturn(filterController).once();
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        expect(controller.findPayeeTotalsHolders()).andReturn(Collections.emptyList()).once();
        replay(controller, filterController);
        widget.init();
        verify(controller, filterController);
        reset(controller, filterController);
    }
}
