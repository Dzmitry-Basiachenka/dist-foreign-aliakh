package com.copyright.rup.dist.foreign.ui.status.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusController;
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
 * Verifies {@link SalBatchStatusWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/15/21
 *
 * @author Darya Baraukova
 */
public class SalBatchStatusWidgetTest {

    private SalBatchStatusWidget batchStatusWidget;
    private ICommonBatchStatusController controller;

    @Before
    public void setUp() {
        controller = createMock(ICommonBatchStatusController.class);
        batchStatusWidget = new SalBatchStatusWidget();
        batchStatusWidget.setController(controller);
    }

    @Test
    public void testComponentStructure() {
        expect(controller.getBatchStatuses()).andReturn(Collections.emptyList()).once();
        replay(controller);
        batchStatusWidget.init();
        assertEquals(1, batchStatusWidget.getComponentCount());
        Component component = batchStatusWidget.getComponent(0);
        assertTrue(component instanceof Grid);
        verifyGrid((Grid) component);
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
            "Work Not Granted", "RH Not Found", "RH Found", "Eligible", "Status"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
        columns.forEach(column -> assertTrue(column.isSortable()));
    }
}
