package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyMenuBar;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;

/**
 * Verifies {@link AclUsageWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/30/2022
 *
 * @author Dzmitry Basiachenka
 */
// TODO verify permissions
public class AclUsageWidgetTest {

    private AclUsageWidget aclUsageWidget;
    private IAclUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(IAclUsageController.class);
        aclUsageWidget = new AclUsageWidget();
        Whitebox.setInternalState(aclUsageWidget, controller);
        expect(controller.initAclUsageFilterWidget()).andReturn(new AclUsageFilterWidget()).once();
    }

    @Test
    public void testWidgetStructure() {
        replay(controller);
        aclUsageWidget.init();
        verify(controller);
        assertTrue(aclUsageWidget.isLocked());
        assertEquals(270, aclUsageWidget.getSplitPosition(), 0);
        verifyWindow(aclUsageWidget, null, 100, 100, Unit.PERCENTAGE);
        assertTrue(aclUsageWidget.getFirstComponent() instanceof VerticalLayout);
        Component secondComponent = aclUsageWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifyWindow(layout, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0));
        Grid grid = (Grid) layout.getComponent(1);
        verifyGrid(grid, Arrays.asList(
            Triple.of("Detail ID", 200.0, -1),
            Triple.of("Period", 100.0, -1),
            Triple.of("Usage Origin", 100.0, -1),
            Triple.of("Channel", 100.0, -1),
            Triple.of("Usage Detail ID", 130.0, -1),
            Triple.of("Wr Wrk Inst", 100.0, -1),
            Triple.of("System Title", 200.0, -1),
            Triple.of("Det LC ID", 100.0, -1),
            Triple.of("Det LC Name", 100.0, -1),
            Triple.of("Agg LC ID", 100.0, -1),
            Triple.of("Agg LC Name", 100.0, -1),
            Triple.of("Survey Country", 120.0, -1),
            Triple.of("Pub Type", 150.0, -1),
            Triple.of("Content Unit Price", 200.0, -1),
            Triple.of("TOU", 120.0, -1),
            Triple.of("Annualized Copies", 130.0, -1),
            Triple.of("Updated By", 150.0, -1),
            Triple.of("Updated Date", 110.0, -1)));
        verifyGridFooter(grid);
        assertEquals(1, layout.getExpandRatio(grid), 0);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        verifyMenuBar(layout.getComponent(0), "Usage Batch", true, Collections.singletonList("Create"));
    }

    private void verifyGridFooter(Grid grid) {
        assertTrue(grid.isFooterVisible());
        FooterRow footerRow = grid.getFooterRow(0);
        assertEquals("Usages Count: 0", footerRow.getCell("detailId").getText());
    }
}
