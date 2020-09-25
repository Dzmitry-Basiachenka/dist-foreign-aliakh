package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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
 * Verifies {@link ViewSalFundPoolWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/25/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class, ViewSalFundPoolWindow.class})
public class ViewSalFundPoolWindowTest {

    private static final String FUND_POOL_ID = "0c7cf263-26fc-4bbd-82d0-f438e8e04dda";

    private ViewSalFundPoolWindow viewSalFundPoolWindow;
    private ISalUsageController controller;
    private final FundPool fundPool = buildFundPool();

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(ISalUsageController.class);
        expect(ForeignSecurityUtils.hasDeleteFundPoolPermission()).andReturn(true).once();
        expect(controller.getFundPools()).andReturn(Collections.singletonList(fundPool)).once();
        replay(controller, ForeignSecurityUtils.class);
        viewSalFundPoolWindow = new ViewSalFundPoolWindow(controller);
        verify(controller, ForeignSecurityUtils.class);
        reset(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testStructure() {
        assertEquals("View Fund Pool", viewSalFundPoolWindow.getCaption());
        verifySize(viewSalFundPoolWindow);
        VerticalLayout content = (VerticalLayout) viewSalFundPoolWindow.getContent();
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
    public void testDeleteClickListener() {
        mockStatic(Windows.class);
        Window confirmWindowMock = createMock(Window.class);
        Grid<FundPool> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewSalFundPoolWindow, "grid", grid);
        Button.ClickListener listener = getButtonClickListener(0);
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(fundPool)).once();
        expect(controller.getScenarioNameAssociatedWithFundPool(FUND_POOL_ID)).andReturn(null).once();
        expect(Windows.showConfirmDialog(
            eq("Are you sure you want to delete <i><b>'SAL Fund Pool'</b></i> fund pool?"), anyObject()))
            .andReturn(confirmWindowMock).once();
        replay(controller, confirmWindowMock, grid, Windows.class);
        listener.buttonClick(createMock(ClickEvent.class));
        verify(controller, confirmWindowMock, grid, Windows.class);
    }

    @Test
    public void testDeleteClickListenerWithAssociatedScenarios() {
        mockStatic(Windows.class);
        Grid<FundPool> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewSalFundPoolWindow, "grid", grid);
        Button.ClickListener listener = getButtonClickListener(0);
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(fundPool)).once();
        expect(controller.getScenarioNameAssociatedWithFundPool(FUND_POOL_ID)).andReturn("Scenario 1").once();
        Windows.showNotificationWindow(
            eq("Fund pool cannot be deleted because it is associated with the following scenario: Scenario 1"));
        expectLastCall().once();
        replay(controller, grid, Windows.class);
        listener.buttonClick(createMock(ClickEvent.class));
        verify(controller, grid, Windows.class);
    }

    @Test
    public void testSelectionChangedListener() {
        Button deleteButton = getButton(0);
        assertFalse(deleteButton.isEnabled());
        Grid<FundPool> grid = (Grid) ((VerticalLayout) viewSalFundPoolWindow.getContent()).getComponent(1);
        grid.select(fundPool);
        assertTrue(deleteButton.isEnabled());
        grid.deselectAll();
        assertFalse(deleteButton.isEnabled());
    }

    @Test
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(viewSalFundPoolWindow, searchWidget);
        Whitebox.setInternalState(viewSalFundPoolWindow, grid);
        expect(grid.getDataProvider()).andReturn(new ListDataProvider(Collections.EMPTY_LIST)).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        grid.recalculateColumnWidths();
        expectLastCall().once();
        replay(searchWidget, grid);
        viewSalFundPoolWindow.performSearch();
        verify(searchWidget, grid);
    }

    private void verifySize(Component component) {
        assertEquals(900, component.getWidth(), 0);
        assertEquals(550, component.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, component.getHeightUnits());
        assertEquals(Sizeable.Unit.PIXELS, component.getWidthUnits());
    }

    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        List<Grid.Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Fund Pool Name", "Date Received", "Assessment Name", "Licensee Account #",
            "Licensee Name", "Gross Amount", "Service Fee %", "Item Bank Split %",
            "Grade K-5 Number of Students", "Grade 6-8 Number of Students", "Grade 9-12 Number of Students",
            "Item Bank Gross Amount", "Grade K-5 Gross Amount", "Grade 6-8 Gross Amount", "Grade 9-12 Gross Amount"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
        assertEquals(Arrays.asList(-1.0, -1.0, 180.0, 150.0, 300.0, 170.0, 115.0, 150.0, 190.0, 190.0, 190.0, 170.0,
            170.0, 170.0, 170.0),
            columns.stream().map(Grid.Column::getWidth).collect(Collectors.toList()));
        assertEquals(Arrays.asList(1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
            columns.stream().map(Grid.Column::getExpandRatio).collect(Collectors.toList()));
        Grid.Column createDateColumn = columns.get(3);
        assertNotNull(createDateColumn.getComparator(SortDirection.ASCENDING));
    }

    private Button getButton(int buttonIndex) {
        VerticalLayout content = (VerticalLayout) viewSalFundPoolWindow.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        return (Button) buttonsLayout.getComponent(buttonIndex);
    }

    private Button.ClickListener getButtonClickListener(int buttonIndex) {
        Collection<?> listeners = getButton(buttonIndex).getListeners(ClickEvent.class);
        assertEquals(1, CollectionUtils.size(listeners));
        return (Button.ClickListener) listeners.iterator().next();
    }

    private FundPool buildFundPool() {
        FundPool salFundPool = new FundPool();
        salFundPool.setId(FUND_POOL_ID);
        salFundPool.setName("SAL Fund Pool");
        return salFundPool;
    }
}
