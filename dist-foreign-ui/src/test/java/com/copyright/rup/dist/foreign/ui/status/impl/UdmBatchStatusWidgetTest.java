package com.copyright.rup.dist.foreign.ui.status.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;

import static org.easymock.EasyMock.createMock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.ui.status.api.IUdmBatchStatusController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.vaadin.data.provider.Query;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Verifies {@link UdmBatchStatusWidget}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/08/2023
 *
 * @author Darya Baraukova
 */
public class UdmBatchStatusWidgetTest {

    private UdmBatchStatusWidget batchStatusWidget;

    @Before
    public void setUp() {
        IUdmBatchStatusController controller = createMock(IUdmBatchStatusController.class);
        batchStatusWidget = new UdmBatchStatusWidget();
        batchStatusWidget.setController(controller);
    }

    @Test
    public void testComponentStructure() {
        batchStatusWidget.init();
        assertEquals(1, batchStatusWidget.getComponentCount());
        Component component = batchStatusWidget.getComponent(0);
        assertThat(component, instanceOf(Grid.class));
        Grid<?> grid = (Grid<?>) component;
        UiTestHelper.verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        assertEquals("batch-status-grid", grid.getId());
        verifyGrid(grid, List.of(
            Triple.of("Usage Batch Name", -1.0, -1),
            Triple.of("Total Count", 130.0, -1),
            Triple.of("New", 130.0, -1),
            Triple.of("Work Not Found", 140.0, -1),
            Triple.of("Work Found", 130.0, -1),
            Triple.of("RH Not Found", 130.0, -1),
            Triple.of("RH Found", 130.0, -1),
            Triple.of("Ineligible", 130.0, -1),
            Triple.of("Status", -1.0, -1)
        ));
        assertEquals(0, grid.getDataProvider().size(new Query<>()));
    }

    @Test
    public void testRefresh() {
        // TODO: {dbasiachenka} implement
    }
}
