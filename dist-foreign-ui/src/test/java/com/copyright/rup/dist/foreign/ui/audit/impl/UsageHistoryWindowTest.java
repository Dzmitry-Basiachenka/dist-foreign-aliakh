package com.copyright.rup.dist.foreign.ui.audit.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.vaadin.ui.DateColumnGenerator;

import com.google.common.collect.Lists;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

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

    private UsageAuditItem item;

    @Test
    public void testLayout() {
        item = new UsageAuditItem();
        item.setId(RupPersistUtils.generateUuid());
        item.setActionReason("Action reason");
        item.setActionType(UsageActionTypeEnum.LOADED);
        String detailId = RupPersistUtils.generateUuid();
        UsageHistoryWindow window = new UsageHistoryWindow(detailId, Lists.newArrayList(item));
        assertEquals("History for usage detail " + detailId, window.getCaption());
        verifySize(window, Unit.PIXELS, 700, Unit.PIXELS, 300);
        assertTrue(window.isResizable());
        Component content = window.getContent();
        assertTrue(content instanceof VerticalLayout);
        verifyContent((VerticalLayout) content);
    }

    private void verifyContent(VerticalLayout layout) {
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertTrue(layout.isSpacing());
        verifySize(layout, Unit.PERCENTAGE, 100, Unit.PERCENTAGE, 100);
        assertEquals(2, layout.getComponentCount());
        Component component = layout.getComponent(0);
        assertEquals(1f, layout.getExpandRatio(component), 0);
        assertTrue(component instanceof Table);
        verifyTable((Table) component);
        component = layout.getComponent(1);
        assertTrue(component instanceof Button);
        verifyButton((Button) component);
    }

    private void verifyTable(Table table) {
        assertNull(table.getCaption());
        verifySize(table, Unit.PERCENTAGE, 100, Unit.PERCENTAGE, 100);
        assertArrayEquals(new Object[]{"actionType", "createDate", "actionReason", "createUser"},
            table.getVisibleColumns());
        assertArrayEquals(new Object[]{"Type", "Date", "Reason", "User"}, table.getColumnHeaders());
        assertTrue(table.getColumnGenerator("createDate") instanceof DateColumnGenerator);
        Container dataSource = table.getContainerDataSource();
        assertTrue(dataSource instanceof BeanContainer);
        BeanContainer beanContainer = (BeanContainer) dataSource;
        assertNotNull(beanContainer.getBeanIdResolver());
        assertEquals(1, beanContainer.size());
        assertEquals(item, beanContainer.getItem(item.getId()).getBean());
    }

    private void verifyButton(Button button) {
        assertEquals("Close", button.getCaption());
        assertEquals(1, button.getListeners(ClickEvent.class).size());
    }

    private void verifySize(Component component, Unit widthUnit, float width, Unit heightUnit, float height) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnit, component.getHeightUnits());
        assertEquals(widthUnit, component.getWidthUnits());
    }
}
