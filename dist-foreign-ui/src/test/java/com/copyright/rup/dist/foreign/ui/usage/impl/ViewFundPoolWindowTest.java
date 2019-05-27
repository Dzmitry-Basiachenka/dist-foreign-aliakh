package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link ViewFundPoolWindow}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/27/19
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class ViewFundPoolWindowTest {

    private static final String USAGE_BATCH_ID = RupPersistUtils.generateUuid();

    private ViewFundPoolWindow viewFundPoolWindow;
    private IUsagesController controller;

    @Before
    public void setUp() {
        controller = createMock(IUsagesController.class);
        expect(controller.getSelectedProductFamily()).andReturn("NTS").once();
        expect(controller.getUsageBatches("NTS")).andReturn(Collections.singletonList(new UsageBatch())).once();
        replay(controller);
        viewFundPoolWindow = new ViewFundPoolWindow(controller);
        verify(controller);
        reset(controller);
    }

    @Test
    public void testStructure() {
        assertEquals("View Fund Pool", viewFundPoolWindow.getCaption());
        verifySize(viewFundPoolWindow);
        VerticalLayout content = (VerticalLayout) viewFundPoolWindow.getContent();
        assertEquals(3, content.getComponentCount());
        assertEquals(SearchWidget.class, content.getComponent(0).getClass());
        Component component = content.getComponent(1);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        assertEquals(2, buttonsLayout.getComponentCount());
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        Button closeButton = (Button) buttonsLayout.getComponent(1);
        assertEquals("Delete", deleteButton.getCaption());
        assertEquals("Close", closeButton.getCaption());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDeleteClickListenerAssociatedFunds() {
        mockStatic(Windows.class);
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewFundPoolWindow, "grid", grid);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(buildUsageBatch())).once();
        expect(controller.getPreServiceFeeFundNamesByUsageBatchId(anyString()))
            .andReturn(Arrays.asList("Fund 1", "Fund 2")).once();
        Windows.showNotificationWindow("Fund pool cannot be deleted because it is associated with the following " +
            "additional funds:<ul><li>Fund 1</li><li>Fund 2</li></ul>");
        expectLastCall().once();
        replay(controller, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, grid, Windows.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDeleteClickListenerWithAssociatedScenarios() {
        mockStatic(Windows.class);
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewFundPoolWindow, "grid", grid);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(buildUsageBatch())).once();
        expect(controller.getPreServiceFeeFundNamesByUsageBatchId(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(Arrays.asList("Scenario 1", "Scenario 2")).once();
        Windows.showNotificationWindow("Fund pool cannot be deleted because it is associated with the following " +
            "scenarios:<ul><li>Scenario 1</li><li>Scenario 2</li></ul>");
        expectLastCall().once();
        replay(controller, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, grid, Windows.class);
    }

    @Test
    public void testDeleteClickListenerEmptyAssociatedScenarios() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewFundPoolWindow, "grid", grid);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(buildUsageBatch())).once();
        expect(controller.getPreServiceFeeFundNamesByUsageBatchId(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        expect(Windows.showConfirmDialog(eq("Are you sure you want to delete <i><b>'NTS batch'</b></i> fund pool?"),
            anyObject())).andReturn(confirmWindowCapture).once();
        replay(controller, confirmWindowCapture, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, grid, Windows.class);
    }

    private void verifySize(Component component) {
        assertEquals(1000, component.getWidth(), 0);
        assertEquals(550, component.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, component.getHeightUnits());
        assertEquals(Sizeable.Unit.PIXELS, component.getWidthUnits());
    }

    @SuppressWarnings("unchecked")
    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        List<Grid.Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Fund Pool Name", "RRO Account #", "RRO Name", "Payment Date", "Fiscal Year",
            "STM Amount", "Non-STM Amount", "STM Minimum Amount", "Non-STM Minimum Amount", "Market(s)",
            "Market Period From", "Market Period To", "Create User", "Create Date"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
        assertEquals(Arrays.asList(150.0, 120.0, 150.0, 100.0, 90.0, 100.0, 115.0, 140.0, 160.0, 140.0, 140.0, 125.0,
            170.0, 145.0), columns.stream().map(Grid.Column::getWidth).collect(Collectors.toList()));
    }

    private Button.ClickListener getDeleteButtonClickListener() {
        VerticalLayout content = (VerticalLayout) viewFundPoolWindow.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = deleteButton.getListeners(Button.ClickEvent.class);
        assertEquals(1, CollectionUtils.size(listeners));
        return (Button.ClickListener) listeners.iterator().next();
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(USAGE_BATCH_ID);
        usageBatch.setName("NTS batch");
        return usageBatch;
    }
}
