package com.copyright.rup.dist.foreign.ui.audit.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
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
    public void testGridValues() {
        String detailId = "1380eddb-e9de-48bf-a264-16dc1fd8f4ec";
        UsageHistoryWindow window = new UsageHistoryWindow(detailId, usageAuditItems);
        Grid<?> grid = (Grid<?>) ((VerticalLayout) window.getContent()).getComponent(0);
        Object[][] expectedCells = {{"LOADED", "user@copyright.com", "09/12/2022 2:25 AM","Usage was uploaded"}};
        verifyGridItems(grid, usageAuditItems, expectedCells);
    }

    @Test
    public void testLayout() {
        UsageAuditItem item = new UsageAuditItem();
        item.setId(RupPersistUtils.generateUuid());
        item.setActionReason("Action reason");
        item.setActionType(UsageActionTypeEnum.LOADED);
        String detailId = RupPersistUtils.generateUuid();
        UsageHistoryWindow window = new UsageHistoryWindow(detailId, List.of(item));
        verifyWindow(window, "History for usage detail " + detailId, 700, 300, Unit.PIXELS);
        assertTrue(window.isResizable());
        Component content = window.getContent();
        assertThat(content, instanceOf(VerticalLayout.class));
        verifyContent((VerticalLayout) content);
    }

    private void verifyContent(VerticalLayout layout) {
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertTrue(layout.isSpacing());
        verifyWindow(layout, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        Component component = layout.getComponent(0);
        assertEquals(1f, layout.getExpandRatio(component), 0);
        assertThat(component, instanceOf(Grid.class));
        verifyGrid((Grid) component, List.of(
            Triple.of("Type", -1.0, -1),
            Triple.of("User", -1.0, -1),
            Triple.of("Date", -1.0, -1),
            Triple.of("Reason", -1.0, 1)
        ));
        component = layout.getComponent(1);
        assertThat(component, instanceOf(Button.class));
        verifyButton((Button) component);
    }

    private void verifyButton(Button button) {
        assertEquals("Close", button.getCaption());
        assertEquals(1, button.getListeners(ClickEvent.class).size());
    }

    private List<UsageAuditItem> loadExpectedUsageAuditItems(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<List<UsageAuditItem>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
