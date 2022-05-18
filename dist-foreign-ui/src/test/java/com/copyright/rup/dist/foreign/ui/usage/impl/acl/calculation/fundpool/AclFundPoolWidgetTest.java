package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyMenuBar;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Supplier;

/**
 * Verifies {@link AclFundPoolWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/18/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolWidgetTest {

    private AclFundPoolWidget widget;
    private IAclFundPoolController controller;
    private IStreamSource streamSource;

    @Before
    public void setUp() {
        controller = createMock(IAclFundPoolController.class);
        widget = new AclFundPoolWidget();
        AclFundPoolFilterWidget filterWidget =
            new AclFundPoolFilterWidget(createMock(IAclFundPoolFilterController.class));
        Whitebox.setInternalState(widget, controller);
        expect(controller.initAclFundPoolFilterWidget()).andReturn(filterWidget).once();
        expect(controller.getDtos()).andReturn(Collections.emptyList()).once();
        streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getExportAclFundPoolDetailsStreamSource()).andReturn(streamSource).once();
    }

    @Test
    public void testWidgetStructure() {
        replay(controller, streamSource);
        widget.init();
        assertTrue(widget.isLocked());
        assertEquals(270, widget.getSplitPosition(), 0);
        verifyWindow(widget, null, 100, 100, Unit.PERCENTAGE);
        VerticalLayout layout = (VerticalLayout) widget.getSecondComponent();
        verifyMenuBar(((HorizontalLayout) layout.getComponent(0)).getComponent(0), "Fund Pool", true,
            Arrays.asList("Create", "View"));
        Grid grid = (Grid) layout.getComponent(1);
        verifyGrid(grid, Arrays.asList(
            Triple.of("Fund Pool Name", 250.0, -1),
            Triple.of("Period", 100.0, -1),
            Triple.of("License Type", 100.0, -1),
            Triple.of("Source", 100.0, -1),
            Triple.of("Det LC ID", 150.0, -1),
            Triple.of("Det LC Name", 200.0, -1),
            Triple.of("Agg LC ID", 150.0, -1),
            Triple.of("Agg LC Name", 200.0, -1),
            Triple.of("Fund Pool Type", 150.0, -1),
            Triple.of("Gross Amount", 150.0, -1),
            Triple.of("Net Amount", 150.0, -1)));
        assertEquals(1, layout.getExpandRatio(grid), 0);
        verify(controller, streamSource);
    }
}
