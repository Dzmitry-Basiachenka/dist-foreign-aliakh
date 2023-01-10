package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static org.easymock.EasyMock.anyObject;
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

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Verifies {@link ViewSalUsageBatchWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/28/20
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class, ViewSalUsageBatchWindow.class})
public class ViewSalUsageBatchWindowTest {

    private static final String USAGE_BATCH_ID = "1abeb252-8c2e-4921-80e3-29abfa808ae5";
    private static final String UNCHECKED = "unchecked";
    private static final String GRID = "grid";

    private ViewSalUsageBatchWindow viewSalUsageBatchWindow;
    private ISalUsageController controller;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(ISalUsageController.class);
        expect(ForeignSecurityUtils.hasDeleteUsagePermission()).andReturn(true).times(2);
        expect(controller.getSelectedProductFamily()).andReturn("SAL").once();
        expect(controller.getUsageBatches("SAL")).andReturn(List.of(new UsageBatch())).once();
        replay(controller, ForeignSecurityUtils.class);
        viewSalUsageBatchWindow = new ViewSalUsageBatchWindow(controller);
        verify(controller, ForeignSecurityUtils.class);
        reset(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testStructure() {
        replay(controller, ForeignSecurityUtils.class);
        assertEquals("View Usage Batch", viewSalUsageBatchWindow.getCaption());
        verifySize(viewSalUsageBatchWindow);
        VerticalLayout content = (VerticalLayout) viewSalUsageBatchWindow.getContent();
        assertEquals(3, content.getComponentCount());
        assertEquals(SearchWidget.class, content.getComponent(0).getClass());
        Component component = content.getComponent(1);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        assertEquals(3, buttonsLayout.getComponentCount());
        Button deleteUsageBatchButton = (Button) buttonsLayout.getComponent(0);
        Button deleteUsageDetailsButton = (Button) buttonsLayout.getComponent(1);
        Button closeButton = (Button) buttonsLayout.getComponent(2);
        assertEquals("Delete Usage Batch", deleteUsageBatchButton.getCaption());
        assertEquals("Delete Usage Details", deleteUsageDetailsButton.getCaption());
        assertEquals("Close", closeButton.getCaption());
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(viewSalUsageBatchWindow, searchWidget);
        Whitebox.setInternalState(viewSalUsageBatchWindow, grid);
        expect(grid.getDataProvider()).andReturn(new ListDataProvider(Collections.EMPTY_LIST)).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        grid.recalculateColumnWidths();
        expectLastCall().once();
        replay(searchWidget, grid);
        viewSalUsageBatchWindow.performSearch();
        verify(searchWidget, grid);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteUsageBatchClickListener() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewSalUsageBatchWindow, GRID, grid);
        Button.ClickListener listener = getDeleteButtonClickListener(0);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(true).once();
        expect(Windows.showConfirmDialog(eq("Are you sure you want to delete <i><b>'SAL batch'</b></i> usage batch?"),
            anyObject())).andReturn(confirmWindowCapture).once();
        replay(controller, confirmWindowCapture, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteWithInProgressUsageBatch() {
        mockStatic(Windows.class);
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewSalUsageBatchWindow, GRID, grid);
        Button.ClickListener listener = getDeleteButtonClickListener(0);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(false).once();
        Windows.showNotificationWindow("'SAL batch' batch cannot be deleted because processing is not completed yet");
        expectLastCall().once();
        replay(controller, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteUsageBatchClickListenerWithAssociatedScenarios() {
        mockStatic(Windows.class);
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewSalUsageBatchWindow, GRID, grid);
        Button.ClickListener listener = getDeleteButtonClickListener(0);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
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
    public void testDeleteUsageDataWithInProgressUsageBatch() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewSalUsageBatchWindow, GRID, grid);
        Button.ClickListener listener = getDeleteButtonClickListener(1);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.usageDataExists(USAGE_BATCH_ID)).andReturn(true).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(false).once();
        Windows.showNotificationWindow("'SAL batch' batch cannot be deleted because processing is not completed yet");
        expectLastCall().once();
        replay(controller, confirmWindowCapture, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteUsageDetailsClickListener() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewSalUsageBatchWindow, GRID, grid);
        Button.ClickListener listener = getDeleteButtonClickListener(1);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.usageDataExists(USAGE_BATCH_ID))
            .andReturn(true).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(true).once();
        expect(Windows.showConfirmDialog(eq("Are you sure you want to delete <i><b>'SAL batch'</b></i> usage details?"),
            anyObject())).andReturn(confirmWindowCapture).once();
        replay(controller, confirmWindowCapture, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteUsageDetailsClickListenerWithAssociatedScenarios() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewSalUsageBatchWindow, GRID, grid);
        Button.ClickListener listener = getDeleteButtonClickListener(1);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.usageDataExists(USAGE_BATCH_ID))
            .andReturn(true).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(Arrays.asList("Scenario 1", "Scenario 2")).once();
        Windows.showNotificationWindow(
            "Usage details cannot be deleted because usage batch is associated with the following " +
                "scenarios:<ul><li>Scenario 1</li><li>Scenario 2</li></ul>");
        replay(controller, confirmWindowCapture, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteUsageDetailsClickListenerNotExist() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewSalUsageBatchWindow, GRID, grid);
        Button.ClickListener listener = getDeleteButtonClickListener(1);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.usageDataExists(USAGE_BATCH_ID))
            .andReturn(false).once();
        Windows.showNotificationWindow("There are no usage details in the Item Bank");
        replay(controller, confirmWindowCapture, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, grid, Windows.class);
    }

    private Button.ClickListener getDeleteButtonClickListener(int numberOfButton) {
        VerticalLayout content = (VerticalLayout) viewSalUsageBatchWindow.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button deleteUsageBatchButton = (Button) buttonsLayout.getComponent(numberOfButton);
        Collection<?> listeners = deleteUsageBatchButton.getListeners(Button.ClickEvent.class);
        assertEquals(1, CollectionUtils.size(listeners));
        return (Button.ClickListener) listeners.iterator().next();
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(USAGE_BATCH_ID);
        usageBatch.setName("SAL batch");
        return usageBatch;
    }

    private void verifySize(Component component) {
        assertEquals(1000, component.getWidth(), 0);
        assertEquals(550, component.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, component.getHeightUnits());
        assertEquals(Sizeable.Unit.PIXELS, component.getWidthUnits());
    }

    @SuppressWarnings(UNCHECKED)
    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Usage Batch Name", "Licensee Account #", "Licensee Name", "Period End Date",
            "Created By", "Created Date"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
        assertEquals(Arrays.asList(-1.0, 180.0, 180.0, 120.0, 170.0, 170.0),
            columns.stream().map(Grid.Column::getWidth).collect(Collectors.toList()));
    }
}
