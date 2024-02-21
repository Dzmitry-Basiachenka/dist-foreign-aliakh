package com.copyright.rup.dist.foreign.vui.status.impl;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.vui.status.api.ICommonBatchStatusController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.Query;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Verifies {@link NtsBatchStatusWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Darya Baraukova
 */
public class NtsBatchStatusWidgetTest {

    private NtsBatchStatusWidget widget;
    private ICommonBatchStatusController controller;

    @Before
    public void setUp() {
        controller = createMock(ICommonBatchStatusController.class);
        widget = new NtsBatchStatusWidget();
        widget.setController(controller);
    }

    @Test
    public void testComponentStructure() {
        widget.init();
        assertEquals(1, widget.getComponentCount());
        var component = widget.getComponentAt(0);
        assertThat(component, instanceOf(Grid.class));
        var grid = (Grid<?>) component;
        assertEquals("batch-status-grid", grid.getId().get());
        verifyGrid(grid, List.of(
            Pair.of("Usage Batch Name", null),
            Pair.of("Total Count", "140px"),
            Pair.of("Work Found", "140px"),
            Pair.of("RH Found", "110px"),
            Pair.of("Non-STM RH", "130px"),
            Pair.of("US Tax Country", "160px"),
            Pair.of("Unclassified", "140px"),
            Pair.of("Eligible", "100px"),
            Pair.of("Excluded", "120px"),
            Pair.of("Status", null)
        ));
        assertEquals(0, grid.getDataProvider().size(new Query<>()));
    }

    @Test
    public void testGridValues() {
        widget.init();
        var batchStatuses = loadExpectedUsageBatchStatuses("nts_usage_batch_status.json");
        expect(controller.getBatchStatuses()).andReturn(batchStatuses).once();
        replay(controller);
        widget.refresh();
        var grid = (Grid<?>) widget.getComponentAt(0);
        Object[][] expectedCells = {{"Usage Batch", "50", "4", "5", "6", "7", "3", "15", "10", "COMPLETED"}};
        verifyGridItems(grid, batchStatuses, expectedCells);
        verify(controller);
    }

    @Test
    public void testRefresh() {
        widget.init();
        expect(controller.getBatchStatuses()).andReturn(List.of(new UsageBatchStatus())).once();
        replay(controller);
        widget.refresh();
        var grid = (Grid<?>) widget.getComponentAt(0);
        assertEquals(1, ((ListDataProvider<?>) grid.getDataProvider()).getItems().size());
        verify(controller);
    }

    private List<UsageBatchStatus> loadExpectedUsageBatchStatuses(String fileName) {
        try {
            var content = TestUtils.fileToString(this.getClass(), fileName);
            var mapper = new ObjectMapper();
            return mapper.readValue(content, new TypeReference<List<UsageBatchStatus>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
