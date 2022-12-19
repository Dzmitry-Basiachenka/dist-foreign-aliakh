package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyMenuBar;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageFilterController;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * Verifies {@link AclciUsageWidget}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/21/2022
 *
 * @author Aliaksanr Liakh
 */
public class AclciUsageWidgetTest {

    private AclciUsageWidget widget;
    private IAclciUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(IAclciUsageController.class);
        expect(controller.initUsagesFilterWidget()).andReturn(new AclciUsageFilterWidget(
            createMock(IAclciUsageFilterController.class))).once();
        widget = new AclciUsageWidget(controller);
        widget.setController(controller);
        replay(controller);
        widget.init();
    }

    @Test
    public void testWidgetStructure() {
        assertTrue(widget.isLocked());
        assertEquals(200, widget.getSplitPosition(), 0);
        verifyWindow(widget, null, 100, 100, Sizeable.Unit.PERCENTAGE);
        assertThat(widget.getFirstComponent(), instanceOf(AclciUsageFilterWidget.class));
        Component secondComponent = widget.getSecondComponent();
        assertThat(secondComponent, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifyWindow(layout, null, 100, 100, Sizeable.Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0));
        Grid grid = (Grid) layout.getComponent(1);
        verifyGrid(grid, Arrays.asList(
            Triple.of("Detail ID", 130.0, -1),
            Triple.of("Detail Status", 115.0, -1),
            Triple.of("License Type", 115.0, -1),
            Triple.of("Product Family", 125.0, -1),
            Triple.of("Usage Batch Name", 145.0, -1),
            Triple.of("Period End Date", 115.0, -1),
            Triple.of("Coverage Period", 300.0, -1),
            Triple.of("Licensee Account #", 150.0, -1),
            Triple.of("Licensee Name", 300.0, -1),
            Triple.of("RH Account #", 115.0, -1),
            Triple.of("RH Name", 300.0, -1),
            Triple.of("Wr Wrk Inst", 110.0, -1),
            Triple.of("System Title", 300.0, -1),
            Triple.of("Standard Number", 140.0, -1),
            Triple.of("Standard Number Type", 155.0, -1),
            Triple.of("Reported Title", 300.0, -1),
            Triple.of("Reported Media Type", 150.0, -1),
            Triple.of("Media Type Weight", 130.0, -1),
            Triple.of("Reported Article or Chapter Title", 240.0, -1),
            Triple.of("Reported Standard Number or Image ID Number", 315.0, -1),
            Triple.of("Reported Author", 150.0, -1),
            Triple.of("Reported Publisher", 150.0, -1),
            Triple.of("Reported Publication Date", 200.0, -1),
            Triple.of("Reported Grade", 150.0, -1),
            Triple.of("Comment", 115.0, -1)));
        assertEquals(1, layout.getExpandRatio(grid), 0);
        verify(controller);
    }

    @Test
    public void testGetController() {
        assertSame(controller, widget.getController());
    }

    @Test
    public void testRefresh() {
        //TODO: implement
    }

    @Test
    public void testInitMediator() {
        //TODO: implement
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertEquals(1, layout.getComponentCount());
        verifyMenuBar(layout.getComponent(0), "Usage Batch", true, Collections.singletonList("Load"));
    }
}
