package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeeFilterWidget;
import com.copyright.rup.vaadin.ui.component.window.ConfirmActionDialogWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.Validator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.GridSelectionModel;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link ExcludePayeeWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class ExcludePayeeWidgetTest {

    private static final String REASON = "reason";

    private ExcludePayeeWidget widget;
    private IExcludePayeeController controller;
    private IExcludePayeeFilterController filterController;
    private IExcludePayeeFilterWidget filterWidget;
    private Grid<PayeeTotalHolder> payeesGrid;
    private GridSelectionModel<PayeeTotalHolder> selectionModel;

    @Before
    public void setUp() {
        selectionModel = createMock(GridSelectionModel.class);
        controller = createMock(IExcludePayeeController.class);
        payeesGrid = createMock(Grid.class);
        widget = new ExcludePayeeWidget();
        widget.setController(controller);
        filterController = PowerMock.createMock(IExcludePayeeFilterController.class);
        filterWidget = new ExcludePayeeFilterWidget();
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
    public void testExcludeDetailsButtonClick() {
        mockStatic(Windows.class);
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        Capture<ConfirmActionDialogWindow.IListener> actionDialogListenerCapture = new Capture<>();
        expect(payeesGrid.getSelectedItems()).andReturn(Collections.singleton(new PayeeTotalHolder())).once();
        Windows.showConfirmDialogWithReason(eq("Confirm action"),
            eq("Are you sure you want to exclude details with selected Payees?"),
            eq("Yes"), eq("Cancel"), capture(actionDialogListenerCapture), anyObject(Validator.class));
        expectLastCall().once();
        controller.excludeDetails(anyObject(), eq(REASON));
        expectLastCall().once();
        replay(clickEvent, controller, payeesGrid, Windows.class);
        buttonClick(0, clickEvent);
        actionDialogListenerCapture.getValue().onActionConfirmed(REASON);
        verify(clickEvent, controller, payeesGrid, Windows.class);
    }

    @Test
    public void testExcludeDetailsButtonClickWithoutSelectionPayees() {
        mockStatic(Windows.class);
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        Windows.showNotificationWindow("Please select at least one Payee");
        expectLastCall().once();
        replay(clickEvent, controller, Windows.class);
        buttonClick(0, clickEvent);
        verify(clickEvent, controller, Windows.class);
    }

    @Test
    public void testRedesignateDetailsButtonClick() {
        mockStatic(Windows.class);
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        Capture<ConfirmActionDialogWindow.IListener> actionDialogListenerCapture = new Capture<>();
        expect(payeesGrid.getSelectedItems()).andReturn(Collections.singleton(new PayeeTotalHolder())).once();
        Windows.showConfirmDialogWithReason(eq("Confirm action"),
            eq("Are you sure you want to redesignate details with selected Payees?"),
            eq("Yes"), eq("Cancel"), capture(actionDialogListenerCapture), anyObject(Validator.class));
        expectLastCall().once();
        controller.redesignateDetails(anyObject(), eq(REASON));
        expectLastCall().once();
        replay(clickEvent, controller, payeesGrid, Windows.class);
        buttonClick(1, clickEvent);
        actionDialogListenerCapture.getValue().onActionConfirmed(REASON);
        verify(clickEvent, controller, payeesGrid, Windows.class);
    }

    @Test
    public void testRedesignateDetailsButtonClickWithoutSelectionPayees() {
        mockStatic(Windows.class);
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        Windows.showNotificationWindow("Please select at least one Payee");
        expectLastCall().once();
        replay(clickEvent, controller, Windows.class);
        buttonClick(1, clickEvent);
        verify(clickEvent, controller, Windows.class);
    }

    @Test
    public void testClearButtonClick() {
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        expect(payeesGrid.getSelectionModel()).andReturn(selectionModel).once();
        selectionModel.deselectAll();
        expectLastCall().once();
        replay(clickEvent, payeesGrid);
        buttonClick(2, clickEvent);
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
        assertEquals(Arrays.asList(
            "Payee Account #", "Payee Name", "Amt in USD", "Service Fee Amount", "Net Amt in USD", "Participating"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(4, horizontalLayout.getComponentCount());
        verifyButton((Button) horizontalLayout.getComponent(0), "Exclude Details");
        verifyButton((Button) horizontalLayout.getComponent(1), "Redesignate Details");
        verifyButton((Button) horizontalLayout.getComponent(2), "Clear");
        verifyButton((Button) horizontalLayout.getComponent(3), "Close");
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
        expect(controller.getPayeeTotalHolders()).andReturn(Collections.emptyList()).once();
        replay(controller, filterController);
        widget.init();
        verify(controller, filterController);
        reset(controller, filterController);
    }
}
