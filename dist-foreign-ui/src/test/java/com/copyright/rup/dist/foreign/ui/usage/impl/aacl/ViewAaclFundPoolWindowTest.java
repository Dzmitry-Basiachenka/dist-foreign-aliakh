package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

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

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.AaclFundPool;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.data.sort.SortDirection;
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
 * Verifies {@link ViewAaclFundPoolWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 02/05/20
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
public class ViewAaclFundPoolWindowTest {

    private static final String FUND_POOL_ID = RupPersistUtils.generateUuid();
    private static final String UNCHECKED = "unchecked";

    private ViewAaclFundPoolWindow viewAaclFundPoolWindow;
    private IAaclUsageController controller;
    private final AaclFundPool fundPool = buildFundPool();

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IAaclUsageController.class);
        expect(ForeignSecurityUtils.hasDeleteAaclFundPoolPermission()).andReturn(true).once();
        expect(controller.getFundPools()).andReturn(Collections.singletonList(fundPool)).once();
        replay(controller, ForeignSecurityUtils.class);
        viewAaclFundPoolWindow = new ViewAaclFundPoolWindow(controller);
        verify(controller, ForeignSecurityUtils.class);
        reset(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testStructure() {
        assertEquals("View Fund Pool", viewAaclFundPoolWindow.getCaption());
        verifySize(viewAaclFundPoolWindow);
        VerticalLayout content = (VerticalLayout) viewAaclFundPoolWindow.getContent();
        assertEquals(3, content.getComponentCount());
        assertEquals(SearchWidget.class, content.getComponent(0).getClass());
        Component component = content.getComponent(1);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        assertEquals(3, buttonsLayout.getComponentCount());
        Button viewButton = (Button) buttonsLayout.getComponent(0);
        Button deleteButton = (Button) buttonsLayout.getComponent(1);
        Button closeButton = (Button) buttonsLayout.getComponent(2);
        assertEquals("View", viewButton.getCaption());
        assertEquals("Delete", deleteButton.getCaption());
        assertEquals("Close", closeButton.getCaption());
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteClickListener() {
        mockStatic(Windows.class);
        Window confirmWindowMock = createMock(Window.class);
        Grid<AaclFundPool> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewAaclFundPoolWindow, "grid", grid);
        Button.ClickListener listener = getButtonClickListener(1);
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(fundPool)).once();
        expect(
            Windows.showConfirmDialog(
                eq("Are you sure you want to delete <i><b>'AACL Fund Pool'</b></i> fund pool?"), anyObject()))
            .andReturn(confirmWindowMock).once();
        replay(controller, confirmWindowMock, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowMock, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectionChangedListener() {
        Button viewButton = getButton(0);
        Button deleteButton = getButton(1);
        assertFalse(viewButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
        Grid<AaclFundPool> grid = (Grid) ((VerticalLayout) viewAaclFundPoolWindow.getContent()).getComponent(1);
        grid.select(fundPool);
        assertTrue(viewButton.isEnabled());
        assertTrue(deleteButton.isEnabled());
        grid.deselectAll();
        assertFalse(viewButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(viewAaclFundPoolWindow, searchWidget);
        Whitebox.setInternalState(viewAaclFundPoolWindow, grid);
        expect(grid.getDataProvider()).andReturn(new ListDataProvider(Collections.EMPTY_LIST)).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        grid.recalculateColumnWidths();
        expectLastCall().once();
        replay(searchWidget, grid);
        viewAaclFundPoolWindow.performSearch();
        verify(searchWidget, grid);
    }

    private void verifySize(Component component) {
        assertEquals(600, component.getWidth(), 0);
        assertEquals(550, component.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, component.getHeightUnits());
        assertEquals(Sizeable.Unit.PIXELS, component.getWidthUnits());
    }

    @SuppressWarnings(UNCHECKED)
    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        List<Grid.Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Fund Pool Name", "Create User", "Create Date"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
        assertEquals(Arrays.asList(150.0, 170.0, -1.0),
            columns.stream().map(Grid.Column::getWidth).collect(Collectors.toList()));
        Grid.Column createDateColumn = columns.get(2);
        assertNotNull(createDateColumn.getComparator(SortDirection.ASCENDING));
    }

    private Button getButton(int buttonIndex) {
        VerticalLayout content = (VerticalLayout) viewAaclFundPoolWindow.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        return (Button) buttonsLayout.getComponent(buttonIndex);
    }

    private Button.ClickListener getButtonClickListener(int buttonIndex) {
        Collection<?> listeners = getButton(buttonIndex).getListeners(Button.ClickEvent.class);
        assertEquals(1, CollectionUtils.size(listeners));
        return (Button.ClickListener) listeners.iterator().next();
    }

    private AaclFundPool buildFundPool() {
        AaclFundPool aaclFundPool = new AaclFundPool();
        aaclFundPool.setId(FUND_POOL_ID);
        aaclFundPool.setName("AACL Fund Pool");
        return aaclFundPool;
    }
}
