package com.copyright.rup.dist.foreign.ui.audit.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UdmValueActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UdmValueAuditItem;

import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * Verifies {@link UdmValueHistoryWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/01/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueHistoryWindowTest {

    @Test
    public void testLayout() {
        UdmValueAuditItem auditItem = new UdmValueAuditItem();
        auditItem.setId(RupPersistUtils.generateUuid());
        auditItem.setActionReason("Action reason");
        auditItem.setActionType(UdmValueActionTypeEnum.CREATED);
        String udmValueId = RupPersistUtils.generateUuid();
        UdmValueHistoryWindow window = new UdmValueHistoryWindow(udmValueId, Collections.singletonList(auditItem));
        verifyWindow(window, "History for UDM value " + udmValueId, 700, 300, Sizeable.Unit.PIXELS);
        assertTrue(window.isResizable());
        Component content = window.getContent();
        assertThat(content, instanceOf(VerticalLayout.class));
        verifyContent((VerticalLayout) content);
    }

    private void verifyContent(VerticalLayout layout) {
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertTrue(layout.isSpacing());
        assertEquals(2, layout.getComponentCount());
        Component component = layout.getComponent(0);
        assertEquals(1f, layout.getExpandRatio(component), 0);
        assertThat(component, instanceOf(Grid.class));
        Grid grid = (Grid) layout.getComponent(0);
        verifyGrid(grid, Arrays.asList(
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
}
