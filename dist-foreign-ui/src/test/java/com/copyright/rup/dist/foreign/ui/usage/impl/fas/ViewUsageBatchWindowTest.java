package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.fas.IFasUsageController;
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
 * Verifies {@link ViewUsageBatchWindow}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/27/19
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
public class ViewUsageBatchWindowTest {

    private static final String USAGE_BATCH_ID = RupPersistUtils.generateUuid();
    private static final String UNCHECKED = "unchecked";

    private Grid<UsageBatch> grid;
    private ViewUsageBatchWindow viewUsageBatchWindow;
    private IFasUsageController controller;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IFasUsageController.class);
        grid = createMock(Grid.class);
        expect(ForeignSecurityUtils.hasDeleteUsagePermission()).andReturn(true).once();
        expect(controller.getSelectedProductFamily()).andReturn("FAS").once();
        expect(controller.getUsageBatches("FAS")).andReturn(Collections.singletonList(new UsageBatch())).once();
        replay(controller, ForeignSecurityUtils.class);
        viewUsageBatchWindow = new ViewUsageBatchWindow(controller);
        Whitebox.setInternalState(viewUsageBatchWindow, "grid", grid);
        verify(controller, ForeignSecurityUtils.class);
        reset(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testStructure() {
        assertEquals("View Usage Batch", viewUsageBatchWindow.getCaption());
        verifySize(viewUsageBatchWindow);
        VerticalLayout content = (VerticalLayout) viewUsageBatchWindow.getContent();
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
    @SuppressWarnings(UNCHECKED)
    public void testDeleteClickListenerAssociatedFunds() {
        mockStatic(Windows.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(anyString()))
            .andReturn(Arrays.asList("Batch 1", "Batch 2")).once();
        Windows.showNotificationWindow("Usage batch cannot be deleted because it is associated with the following " +
            "additional funds:<ul><li>Batch 1</li><li>Batch 2</li></ul>");
        expectLastCall().once();
        replay(controller, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteClickListenerWithAssociatedScenarios() {
        mockStatic(Windows.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(Arrays.asList("Scenario 1", "Scenario 2")).once();
        Windows.showNotificationWindow("Usage batch cannot be deleted because it is associated with the following " +
            "scenarios:<ul><li>Scenario 1</li><li>Scenario 2</li></ul>");
        expectLastCall().once();
        replay(controller, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteClickListenerInProgressBatch() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(false).once();
        Windows.showNotificationWindow("'FAS batch' batch cannot be deleted because processing is not completed yet");
        expectLastCall().once();
        replay(controller, confirmWindowCapture, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteClickListenerEmptyAssociatedScenarios() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(true).once();
        expect(Windows.showConfirmDialog(eq("Are you sure you want to delete <i><b>'FAS batch'</b></i> usage batch?"),
            anyObject())).andReturn(confirmWindowCapture).once();
        replay(controller, confirmWindowCapture, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(viewUsageBatchWindow, searchWidget);
        expect(grid.getDataProvider()).andReturn(new ListDataProvider(Collections.EMPTY_LIST)).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        grid.recalculateColumnWidths();
        expectLastCall().once();
        replay(searchWidget, grid);
        viewUsageBatchWindow.performSearch();
        verify(searchWidget, grid);
    }

    private void verifySize(Component component) {
        assertEquals(1000, component.getWidth(), 0);
        assertEquals(550, component.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, component.getHeightUnits());
        assertEquals(Sizeable.Unit.PIXELS, component.getWidthUnits());
    }

    @SuppressWarnings(UNCHECKED)
    private void verifyGrid(Grid usagesGrid) {
        assertNull(usagesGrid.getCaption());
        List<Grid.Column> columns = usagesGrid.getColumns();
        assertEquals(Arrays.asList("Usage Batch Name", "RRO Account #", "RRO Name", "Payment Date", "Fiscal Year",
            "Batch Amt in USD", "Create User", "Create Date"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
        assertEquals(Arrays.asList(150.0, 120.0, 150.0, 100.0, 90.0, 130.0, 170.0, -1.0),
            columns.stream().map(Grid.Column::getWidth).collect(Collectors.toList()));
        Grid.Column createDateColumn = columns.get(7);
        assertNotNull(createDateColumn.getComparator(SortDirection.ASCENDING));
    }

    private Button.ClickListener getDeleteButtonClickListener() {
        VerticalLayout content = (VerticalLayout) viewUsageBatchWindow.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = deleteButton.getListeners(Button.ClickEvent.class);
        assertEquals(1, CollectionUtils.size(listeners));
        return (Button.ClickListener) listeners.iterator().next();
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(USAGE_BATCH_ID);
        usageBatch.setName("FAS batch");
        return usageBatch;
    }
}
