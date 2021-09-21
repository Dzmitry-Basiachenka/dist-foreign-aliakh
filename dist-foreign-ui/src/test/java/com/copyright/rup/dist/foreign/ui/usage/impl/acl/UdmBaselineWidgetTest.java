package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link UdmBaselineWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Dzmitry Basiachenka
 */
public class UdmBaselineWidgetTest {

    private UdmBaselineWidget udmBaselineWidget;

    @Before
    public void setUp() {
        IUdmBaselineController controller = createMock(IUdmBaselineController.class);
        UdmBaselineFilterWidget filterWidget =
            new UdmBaselineFilterWidget(createMock(IUdmBaselineFilterController.class));
        udmBaselineWidget = new UdmBaselineWidget();
        Whitebox.setInternalState(udmBaselineWidget, controller);
        expect(controller.initBaselineFilterWidget()).andReturn(filterWidget).once();
        replay(controller);
        udmBaselineWidget.init();
        verify(controller);
    }

    @Test
    public void testWidgetStructure() {
        assertTrue(udmBaselineWidget.isLocked());
        assertEquals(200, udmBaselineWidget.getSplitPosition(), 0);
        verifySize(udmBaselineWidget);
        assertTrue(udmBaselineWidget.getFirstComponent() instanceof VerticalLayout);
        Component secondComponent = udmBaselineWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0));
        verifyGrid((Grid) layout.getComponent(1));
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    private void verifyGrid(Grid grid) {
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Detail ID", "Period", "Usage Origin", "Usage Detail ID", "Wr Wrk Inst",
            "System Title", "Det LC ID", "Det LC Name", "Agg LC ID", "Agg LC Name", "Survey Country", "Channel",
            "TOU", "Annualized Copies", "Created By", "Created Date", "Updated By", "Updated Date"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        verifySize(grid);
        assertTrue(grid.isFooterVisible());
        FooterRow footerRow = grid.getFooterRow(0);
        assertEquals("Usages Count: 0", footerRow.getCell("detailId").getText());
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(1, layout.getComponentCount());
        Component component = layout.getComponent(0);
        assertEquals("Export", component.getCaption());
    }
}
