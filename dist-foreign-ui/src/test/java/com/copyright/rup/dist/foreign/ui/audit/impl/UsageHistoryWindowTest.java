package com.copyright.rup.dist.foreign.ui.audit.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;

import com.google.common.collect.Lists;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Test;

import java.util.Arrays;

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

    @Test
    public void testLayout() {
        UsageAuditItem item = new UsageAuditItem();
        item.setId(RupPersistUtils.generateUuid());
        item.setActionReason("Action reason");
        item.setActionType(UsageActionTypeEnum.LOADED);
        String detailId = RupPersistUtils.generateUuid();
        UsageHistoryWindow window = new UsageHistoryWindow(detailId, Lists.newArrayList(item));
        verifyWindow(window, "History for usage detail " + detailId, 700, 300, Unit.PIXELS);
        assertTrue(window.isResizable());
        Component content = window.getContent();
        assertTrue(content instanceof VerticalLayout);
        verifyContent((VerticalLayout) content);
    }

    private void verifyContent(VerticalLayout layout) {
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertTrue(layout.isSpacing());
        verifyWindow(layout, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        Component component = layout.getComponent(0);
        assertEquals(1f, layout.getExpandRatio(component), 0);
        assertTrue(component instanceof Grid);
        verifyGrid((Grid) component, Arrays.asList(
            Triple.of("Type", -1.0, -1),
            Triple.of("User", -1.0, -1),
            Triple.of("Date", -1.0, -1),
            Triple.of("Reason", -1.0, 1)
        ));
        component = layout.getComponent(1);
        assertTrue(component instanceof Button);
        verifyButton((Button) component);
    }

    private void verifyButton(Button button) {
        assertEquals("Close", button.getCaption());
        assertEquals(1, button.getListeners(ClickEvent.class).size());
    }

}
