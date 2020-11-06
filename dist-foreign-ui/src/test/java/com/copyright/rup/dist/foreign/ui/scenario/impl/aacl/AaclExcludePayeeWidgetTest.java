package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeFilterWidget;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.GridSelectionModel;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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

    private AaclExcludePayeeWidget widget;
    private IAaclExcludePayeeController controller;
    private IAaclExcludePayeeFilterController filterController;
    private IAaclExcludePayeeFilterWidget filterWidget;
    private Grid<PayeeTotalHolder> payeesGrid;
    private GridSelectionModel<PayeeTotalHolder> selectionModel;

    @Before
    public void setUp() {
        selectionModel = createMock(GridSelectionModel.class);
        controller = createMock(IAaclExcludePayeeController.class);
        payeesGrid = createMock(Grid.class);
        widget = new AaclExcludePayeeWidget();
        widget.setController(controller);
        filterController = createMock(IAaclExcludePayeeFilterController.class);
        filterWidget = new AaclExcludePayeeFilterWidget();
        filterWidget.setController(filterController);
        initWidget();
        Whitebox.setInternalState(widget, payeesGrid);
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

    @Test
    public void testClearButtonClick() {
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        expect(payeesGrid.getSelectionModel()).andReturn(selectionModel).once();
        selectionModel.deselectAll();
        expectLastCall().once();
        replay(clickEvent, payeesGrid);
        buttonClick(1, clickEvent);
        verify(clickEvent, payeesGrid);
    }

    private void buttonClick(int buttonIndex, Button.ClickEvent clickEvent) {
        HorizontalSplitPanel splitPanel = (HorizontalSplitPanel) widget.getContent();
        VerticalLayout content = (VerticalLayout) splitPanel.getSecondComponent();
        HorizontalLayout horizontalLayout = (HorizontalLayout) content.getComponent(2);
        Button button = (Button) horizontalLayout.getComponent(buttonIndex);
        Collection<?> listeners = button.getListeners(Button.ClickEvent.class);
        assertEquals(1, listeners.size());
        Button.ClickListener clickListener = (Button.ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
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

    private void initWidget() {
        expect(controller.getExcludePayeesFilterController()).andReturn(filterController).once();
        expect(controller.getPayeeTotalHolders()).andReturn(Collections.emptyList()).once();
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        replay(controller, filterController);
        widget.init();
        verify(controller, filterController);
        reset(controller, filterController);
    }
}
