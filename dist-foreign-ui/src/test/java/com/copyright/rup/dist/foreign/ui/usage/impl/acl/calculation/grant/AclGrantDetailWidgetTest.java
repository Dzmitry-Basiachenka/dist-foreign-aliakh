package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Verifies {@link AclGrantDetailWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/28/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclGrantDetailWidgetTest {

    private AclGrantDetailWidget aclGrantDetailWidget;
    private IAclGrantDetailController controller;

    @Before
    public void setUp() {
        controller = createMock(IAclGrantDetailController.class);
        expect(controller.initAclGrantDetailFilterWidget()).andReturn(new AclGrantDetailFilterWidget()).once();
    }

    @Test
    public void testWidgetStructure() {
        replay(controller);
        initWidget();
        verify(controller);
        assertTrue(aclGrantDetailWidget.isLocked());
        assertEquals(200, aclGrantDetailWidget.getSplitPosition(), 0);
        verifyWindow(aclGrantDetailWidget, null, 100, 100, Unit.PERCENTAGE);
        assertTrue(aclGrantDetailWidget.getFirstComponent() instanceof AclGrantDetailFilterWidget);
        Component secondComponent = aclGrantDetailWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifyWindow(layout, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyToolbarLayout(layout.getComponent(0));
        Grid grid = (Grid) layout.getComponent(1);
        verifyGrid(grid, Arrays.asList(
            Triple.of("License Type", 200.0, -1),
            Triple.of("TOU Status", 150.0, -1),
            Triple.of("Grant Status", 120.0, -1),
            Triple.of("Eligible", 100.0, -1),
            Triple.of("Wr Wrk Inst", 100.0, -1),
            Triple.of("System Title", 200.0, -1),
            Triple.of("RH Account #", 150.0, -1),
            Triple.of("RH Name", 260.0, -1),
            Triple.of("TOU", 120.0, -1),
            Triple.of("Created Date", 100.0, -1),
            Triple.of("Updated Date", 100.0, -1),
            Triple.of("Grant Period", 100.0, -1)));
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    private void verifyToolbarLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isSpacing());
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(1, layout.getComponentCount());
        verifyMenuBar(layout.getComponent(0), "Grant Set", Collections.singletonList("Create"));
    }

    private void verifyMenuBar(Component component, String menuBarName, List<String> menuItems) {
        assertTrue(component instanceof MenuBar);
        MenuBar menuBar = (MenuBar) component;
        assertTrue(menuBar.isVisible());
        List<MenuBar.MenuItem> parentItems = menuBar.getItems();
        assertEquals(1, parentItems.size());
        MenuBar.MenuItem item = parentItems.get(0);
        assertEquals(menuBarName, item.getText());
        List<MenuBar.MenuItem> childItems = item.getChildren();
        assertEquals(CollectionUtils.size(menuItems), CollectionUtils.size(childItems));
        IntStream.range(0, menuItems.size())
            .forEach(index -> assertEquals(menuItems.get(index), childItems.get(index).getText()));
    }

    private void initWidget() {
        aclGrantDetailWidget = new AclGrantDetailWidget();
        aclGrantDetailWidget.setController(controller);
        aclGrantDetailWidget.init();
    }
}
