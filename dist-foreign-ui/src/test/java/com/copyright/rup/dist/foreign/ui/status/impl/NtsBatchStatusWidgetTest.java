package com.copyright.rup.dist.foreign.ui.status.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * Verifies {@link NtsBatchStatusWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/15/21
 *
 * @author Darya Baraukova
 */
public class NtsBatchStatusWidgetTest {

    private NtsBatchStatusWidget batchStatusWidget;
    private ICommonBatchStatusController controller;

    @Before
    public void setUp() {
        controller = createMock(ICommonBatchStatusController.class);
        batchStatusWidget = new NtsBatchStatusWidget();
        batchStatusWidget.setController(controller);
    }

    @Test
    public void testComponentStructure() {
        batchStatusWidget.init();
        assertEquals(1, batchStatusWidget.getComponentCount());
        Component component = batchStatusWidget.getComponent(0);
        assertTrue(component instanceof Grid);
        Grid<?> grid = (Grid<?>) component;
        UiTestHelper.verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        assertEquals("batch-status-grid", grid.getId());
        UiTestHelper.verifyGrid(grid, Arrays.asList(
            Triple.of("Usage Batch Name", -1.0, -1),
            Triple.of("Total Count", 130.0, -1),
            Triple.of("Work Found", 130.0, -1),
            Triple.of("RH Found", 130.0, -1),
            Triple.of("Non-STM RH", 130.0, -1),
            Triple.of("US Tax Country", 130.0, -1),
            Triple.of("Unclassified", 130.0, -1),
            Triple.of("Eligible", 130.0, -1),
            Triple.of("Excluded", 130.0, -1),
            Triple.of("Status", -1.0, -1)
        ));
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
        assertEquals(1, ((ListDataProvider<?>) grid.getDataProvider()).getItems().size());
        verify(controller);
    }
}
