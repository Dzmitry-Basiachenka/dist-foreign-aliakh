package com.copyright.rup.dist.foreign.vui.audit.impl;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterComponent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySize;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Verifies {@link UsageHistoryWindow}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/22/18
 *
 * @author Aliaksandr Radkevich
 */
public class UsageHistoryWindowTest {

    private final List<UsageAuditItem> usageAuditItems = loadExpectedUsageAuditItems("usage_audit_item_66d43472.json");

    @Test
    public void testStructure() {
        var item = new UsageAuditItem();
        item.setId("2ed0bab6-e7f6-4cac-b2a1-aa26041d5d7b");
        item.setActionReason("Action reason");
        item.setActionType(UsageActionTypeEnum.LOADED);
        var detailId = "c6d70fa7-1674-4ef9-848d-6ce1b32b164d";
        var window = new UsageHistoryWindow(detailId, List.of(item));
        verifyWindow(window, "History for usage detail " + detailId, "1000px", "400px", Unit.PIXELS, true);
        assertEquals("usage-history-window", window.getId().orElseThrow());
        assertEquals("usage-history-window", window.getClassName());
        verifyMainLayout(window);
    }

    @Test
    public void testGridValues() {
        var detailId = "1380eddb-e9de-48bf-a264-16dc1fd8f4ec";
        var window = new UsageHistoryWindow(detailId, usageAuditItems);
        Grid<?> grid = (Grid<?>) ((VerticalLayout) window.getChildren().findAny().get()).getComponentAt(0);
        Object[][] expectedCells = {{"LOADED", "user@copyright.com", "09/12/2022 2:25 AM","Usage was uploaded"}};
        verifyGridItems(grid, usageAuditItems, expectedCells);
    }

    private void verifyMainLayout(Dialog dialog) {
        var component = dialog.getChildren().findAny().get();
        assertNotNull(component);
        assertThat(component, instanceOf(VerticalLayout.class));
        var layout = (VerticalLayout) component;
        assertEquals(1, layout.getComponentCount());
        verifySize(layout, "100%", Unit.PERCENTAGE, "100%", Unit.PERCENTAGE);
        verifyGrid(layout.getComponentAt(0));
        verifyButton(getFooterComponent(dialog, 1), "Close", true);
    }

    @SuppressWarnings("unchecked")
    private void verifyGrid(Component component) {
        assertNotNull(component);
        assertThat(component, instanceOf(Grid.class));
        Grid<ScenarioAuditItem> grid = (Grid<ScenarioAuditItem>) component;
        UiTestHelper.verifyGrid(grid, List.of(
            Pair.of("Type", null),
            Pair.of("User", null),
            Pair.of("Date", null),
            Pair.of("Reason", null)
        ));
        assertEquals("usage-history-grid", grid.getClassName());
    }

    private List<UsageAuditItem> loadExpectedUsageAuditItems(String fileName) {
        try {
            var content = TestUtils.fileToString(this.getClass(), fileName);
            var mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
