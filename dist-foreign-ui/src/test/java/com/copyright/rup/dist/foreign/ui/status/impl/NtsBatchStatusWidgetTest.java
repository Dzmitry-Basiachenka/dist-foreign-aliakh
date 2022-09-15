package com.copyright.rup.dist.foreign.ui.status.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    private final List<UsageBatchStatus> usageBatchStatuses =
        loadExpectedUsageBatchStatuses("nts_usage_batch_status.json");
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
    public void testGridValues() {
        batchStatusWidget.init();
        expect(controller.getBatchStatuses()).andReturn(usageBatchStatuses).once();
        replay(controller);
        batchStatusWidget.refresh();
        Grid<?> grid = (Grid<?>) batchStatusWidget.getComponent(0);
        Object[][] expectedCells = {{"NTS Usage Batch 1", 50, 4, 5, 6, 7, 3, 15, 10, "status"}};
        verifyGridItems(grid, usageBatchStatuses, expectedCells);
        verify(controller);
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

    private List<UsageBatchStatus> loadExpectedUsageBatchStatuses(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, new TypeReference<List<UsageBatchStatus>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
