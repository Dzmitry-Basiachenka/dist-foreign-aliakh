package com.copyright.rup.dist.foreign.ui.status.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusController;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link FasBatchStatusWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/21
 *
 * @author Darya Baraukova
 */
public class FasBatchStatusWidgetTest {

    private FasBatchStatusWidget batchStatusWidget;
    private ICommonBatchStatusController controller;

    @Before
    public void setUp() {
        controller = createMock(ICommonBatchStatusController.class);
        batchStatusWidget = new FasBatchStatusWidget();
        batchStatusWidget.setController(controller);
    }

    @Test
    public void testComponentStructure() {
        batchStatusWidget.init();
        assertEquals(1, batchStatusWidget.getComponentCount());
        Component component = batchStatusWidget.getComponent(0);
        assertTrue(component instanceof Grid);
        Grid<?> grid = (Grid<?>) component;
        verifyGrid(grid);
        assertEquals(0, grid.getDataProvider().size(new Query<>()));
    }

    @Test
    public void testRefresh() {
        batchStatusWidget.init();
        expect(controller.getBatchStatuses()).andReturn(Collections.singletonList(new UsageBatchStatus())).once();
        replay(controller);
        batchStatusWidget.refresh();
        assertEquals(1, batchStatusWidget.getComponentCount());
        Component component = batchStatusWidget.getComponent(0);
        assertTrue(component instanceof Grid);
        Grid<?> grid = (Grid<?>) component;
        verifyGrid(grid);
        assertEquals(1, ((ListDataProvider<?>) grid.getDataProvider()).getItems().size());
        verify(controller);
    }

    private void verifyGrid(Grid grid) {
        assertEquals(100, grid.getWidth(), 0);
        assertEquals(100, grid.getHeight(), 0);
        assertEquals(Sizeable.Unit.PERCENTAGE, grid.getHeightUnits());
        assertEquals(Sizeable.Unit.PERCENTAGE, grid.getWidthUnits());
        assertEquals("batch-status-grid", grid.getId());
        List<Grid.Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Usage Batch Name", "Total Count", "New", "Work Not Found", "Work Found",
            "Sent For Research", "RH Not Found", "RH Found", "Sent For RA", "NTS Withdrawn", "Eligible", "Status"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
        columns.forEach(column -> assertTrue(column.isSortable()));
    }
}
