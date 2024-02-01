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
 * Verifies {@link FasBatchStatusWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Darya Baraukova
 */
public class FasBatchStatusWidgetTest {

    private FasBatchStatusWidget widget;
    private ICommonBatchStatusController controller;

    @Before
    public void setUp() {
        controller = createMock(ICommonBatchStatusController.class);
        widget = new FasBatchStatusWidget();
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
            Pair.of("New", "70px"),
            Pair.of("Work Not Found", "170px"),
            Pair.of("Work Found", "140px"),
            Pair.of("Sent For Research", "190px"),
            Pair.of("RH Not Found", "150px"),
            Pair.of("RH Found", "120px"),
            Pair.of("Sent For RA", "130px"),
            Pair.of("NTS Withdrawn", "160px"),
            Pair.of("Eligible", "100px"),
            Pair.of("Status", null)
        ));
        assertEquals(0, grid.getDataProvider().size(new Query<>()));
    }

    @Test
    public void testGridValues() {
        widget.init();
        var batchStatuses = loadExpectedUsageBatchStatuses("usage_batch_status.json");
        expect(controller.getBatchStatuses()).andReturn(batchStatuses).once();
        replay(controller);
        widget.refresh();
        var grid = (Grid<?>) widget.getComponentAt(0);
        Object[][] expectedCells =
            {{"FAS Usage Batch", "51", "8", "6", "4", "2", "7", "5", "3", "1", "15", "COMPLETED"}};
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
