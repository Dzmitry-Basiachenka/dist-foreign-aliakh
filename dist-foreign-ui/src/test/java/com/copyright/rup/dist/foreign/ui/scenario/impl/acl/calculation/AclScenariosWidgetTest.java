package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link AclScenariosWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclScenariosWidgetTest {

    private AclScenariosWidget aclScenariosWidget;

    @Before
    public void setUp() {
        aclScenariosWidget = new AclScenariosWidget();
        aclScenariosWidget.init();
    }

    @Test
    public void testComponentStructure() {
        assertEquals(1, aclScenariosWidget.getComponentCount());
        Component component = aclScenariosWidget.getComponent(0);
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        component = layout.getComponent(0);
        assertTrue(component instanceof Grid);
        verifyGrid((Grid) component);
        component = layout.getComponent(1);
        assertTrue(component instanceof Panel);
        verifyPanel((Panel) component);
    }

    @Test
    public void testRefresh() {
        // TODO {aliakh} implement
    }

    @Test
    public void testSelectScenario() {
        // TODO {aliakh} implement
    }

    @Test
    public void testRefreshSelectedScenario() {
        // TODO {aliakh} implement
    }

    @Test
    public void testGetSelectedScenario() {
        // TODO {aliakh} implement
    }

    @Test
    public void testGetNotSelectedScenario() {
        // TODO {aliakh} implement
    }

    private void verifyPanel(Panel panel) {
        verifyWindow(panel, null, 100, 100, Unit.PERCENTAGE);
        assertNull(panel.getContent());
    }

    private void verifyGrid(Grid grid) {
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        assertEquals("acl-scenarios-table", grid.getId());
    }
}
