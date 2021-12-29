package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Verifies {@link UdmBaselineValueWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineValueWidgetTest {

    private UdmBaselineValueWidget udmBaselineValueWidget;
    private IUdmBaselineValueController controller;

    @Before
    public void setUp() {
        controller = createMock(IUdmBaselineValueController.class);
        IUdmBaselineValueFilterWidget filterWidget =
            new UdmBaselineValueFilterWidget(createMock(IUdmBaselineValueFilterController.class));
        expect(controller.initBaselineValuesFilterWidget()).andReturn(filterWidget).once();
    }

    @Test
    public void testWidgetStructure() {
        replay(controller);
        initWidget();
        verify(controller);
        assertTrue(udmBaselineValueWidget.isLocked());
        assertEquals(270, udmBaselineValueWidget.getSplitPosition(), 0);
        verifyWindow(udmBaselineValueWidget, null, 100, 100, Unit.PERCENTAGE);
        assertTrue(udmBaselineValueWidget.getFirstComponent() instanceof VerticalLayout);
        Component secondComponent = udmBaselineValueWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifyWindow(layout, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(1, layout.getComponentCount());
        verifyGrid((Grid) layout.getComponent(0), Arrays.asList(
            Triple.of("Value ID", 200.0, -1),
            Triple.of("Value Period", 120.0, -1),
            Triple.of("Wr Wrk Inst", 120.0, -1),
            Triple.of("System Title", 200.0, -1),
            Triple.of("Pub Type", 200.0, -1),
            Triple.of("Price", 120.0, -1),
            Triple.of("Price Flag", 120.0, -1),
            Triple.of("Content", 100.0, -1),
            Triple.of("Content Flag", 100.0, -1),
            Triple.of("Content Unit Price", 200.0, -1),
            Triple.of("Comment", 200.0, -1),
            Triple.of("Updated By", 150.0, -1),
            Triple.of("Updated Date", 110.0, -1)));
        assertEquals(1, layout.getExpandRatio(layout.getComponent(0)), 0);
    }

    private void initWidget() {
        udmBaselineValueWidget = new UdmBaselineValueWidget();
        udmBaselineValueWidget.setController(controller);
        udmBaselineValueWidget.init();
    }
}
