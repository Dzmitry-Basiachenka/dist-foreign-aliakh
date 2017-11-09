package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioController;

import com.google.common.collect.Lists;
import com.vaadin.data.Item;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link ExcludeSourceRroWindow}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/2/2017
 *
 * @author Uladzislau_Shalamitski
 */
public class ExcludeSourceRroWindowTest {

    private ExcludeSourceRroWindow window;

    @Before
    public void setUp() {
        IScenarioController scenarioController = createMock(ScenarioController.class);
        expect(scenarioController.getSourceRros())
            .andReturn(Lists.newArrayList(
                buildRightsholder(2000017004L, "Access Copyright, The Canadian Copyright Agency"),
                buildRightsholder(2000017006L, "CAL, Copyright Agency Limited")))
            .once();
        replay(scenarioController);
        window = new ExcludeSourceRroWindow(scenarioController);
        verify(scenarioController);
    }

    @Test
    public void testStructure() {
        assertEquals("Exclude Details by Source RRO", window.getCaption());
        assertEquals(500, window.getHeight(), 0);
        assertEquals(830, window.getWidth(), 0);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        verifyTable(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2));
    }

    @Test
    public void testContent() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        Table table = (Table) content.getComponent(1);
        verifyItem(table.getItem(2000017006L), "CAL, Copyright Agency Limited", 2000017006L);
        verifyItem(table.getItem(2000017004L), "Access Copyright, The Canadian Copyright Agency", 2000017004L);
    }

    private void verifyTable(Component component) {
        assertEquals(Table.class, component.getClass());
        Table table = (Table) component;
        assertArrayEquals(new Object[]{"accountNumber", "name", "exclude"}, table.getVisibleColumns());
        assertArrayEquals(new Object[]{"Source RRO Account #", "Source RRO Name", ""}, table.getColumnHeaders());
    }

    private void verifyItem(Item item, String name, Long accountNumber) {
        assertEquals(name, item.getItemProperty("name").getValue());
        assertEquals(accountNumber, item.getItemProperty("accountNumber").getValue());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(1, horizontalLayout.getComponentCount());
        Button cancelButton = (Button) horizontalLayout.getComponent(0);
        assertEquals("Cancel", cancelButton.getCaption());
        assertEquals("Cancel", cancelButton.getId());
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(new MarginInfo(false, false, false, false), horizontalLayout.getMargin());
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
