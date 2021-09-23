package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link UdmValueWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueWidgetTest {

    private static final String FORMATTED_PLUS_THOUSAND = "1,000.00";
    private static final String FORMATTED_MINUS_THOUSAND = "-1,000.00";

    private IUdmValueController controller;

    @Before
    public void setUp() {
        controller = createMock(IUdmValueController.class);
        UdmValueFilterWidget filterWidget = new UdmValueFilterWidget(createMock(IUdmValueFilterController.class));
        expect(controller.initValuesFilterWidget()).andReturn(filterWidget).once();
    }

    @Test
    public void testWidgetStructure() {
        replay(controller);
        UdmValueWidget widget = new UdmValueWidget();
        widget.setController(controller);
        widget.init();
        verify(controller);
        assertTrue(widget.isLocked());
        assertEquals(200, widget.getSplitPosition(), 0);
        verifySize(widget, 100, 100, Unit.PERCENTAGE);
        assertTrue(widget.getFirstComponent() instanceof UdmValueFilterWidget);
        Component secondComponent = widget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout, 100, 100, Unit.PERCENTAGE);
        assertEquals(1, layout.getComponentCount());
        verifyGrid((Grid) layout.getComponent(0));
        assertEquals(1, layout.getExpandRatio(layout.getComponent(0)), 0);
    }

    @Test
    public void testFormatAmount() {
        UdmValueWidget widget = new UdmValueWidget();
        assertEquals(FORMATTED_PLUS_THOUSAND, widget.formatAmount(new BigDecimal("1000")));
        assertEquals(FORMATTED_PLUS_THOUSAND, widget.formatAmount(new BigDecimal("1000.")));
        assertEquals(FORMATTED_PLUS_THOUSAND, widget.formatAmount(new BigDecimal("1000.0")));
        assertEquals(FORMATTED_PLUS_THOUSAND, widget.formatAmount(new BigDecimal("1000.00")));
        assertEquals(FORMATTED_PLUS_THOUSAND, widget.formatAmount(new BigDecimal("1000.000")));
        assertEquals("1,000.10", widget.formatAmount(new BigDecimal("1000.100")));
        assertEquals("1,000.01", widget.formatAmount(new BigDecimal("1000.010")));
        assertEquals("1,000.001", widget.formatAmount(new BigDecimal("1000.001")));
        assertEquals("1,000.0001", widget.formatAmount(new BigDecimal("1000.0001")));
        assertEquals("1,000.00001", widget.formatAmount(new BigDecimal("1000.00001")));
        assertEquals("1,000.000001", widget.formatAmount(new BigDecimal("1000.000001")));
        assertEquals("1,000.0000001", widget.formatAmount(new BigDecimal("1000.0000001")));
        assertEquals("1,000.00000001", widget.formatAmount(new BigDecimal("1000.00000001")));
        assertEquals("1,000.000000001", widget.formatAmount(new BigDecimal("1000.000000001")));
        assertEquals("1,000.0000000001", widget.formatAmount(new BigDecimal("1000.0000000001")));
        assertEquals(FORMATTED_MINUS_THOUSAND, widget.formatAmount(new BigDecimal("-1000")));
        assertEquals(FORMATTED_MINUS_THOUSAND, widget.formatAmount(new BigDecimal("-1000.")));
        assertEquals(FORMATTED_MINUS_THOUSAND, widget.formatAmount(new BigDecimal("-1000.0")));
        assertEquals(FORMATTED_MINUS_THOUSAND, widget.formatAmount(new BigDecimal("-1000.00")));
        assertEquals(FORMATTED_MINUS_THOUSAND, widget.formatAmount(new BigDecimal("-1000.000")));
        assertEquals("-1,000.10", widget.formatAmount(new BigDecimal("-1000.100")));
        assertEquals("-1,000.01", widget.formatAmount(new BigDecimal("-1000.010")));
        assertEquals("-1,000.001", widget.formatAmount(new BigDecimal("-1000.001")));
        assertEquals("-1,000.0001", widget.formatAmount(new BigDecimal("-1000.0001")));
        assertEquals("-1,000.00001", widget.formatAmount(new BigDecimal("-1000.00001")));
        assertEquals("-1,000.000001", widget.formatAmount(new BigDecimal("-1000.000001")));
        assertEquals("-1,000.0000001", widget.formatAmount(new BigDecimal("-1000.0000001")));
        assertEquals("-1,000.00000001", widget.formatAmount(new BigDecimal("-1000.00000001")));
        assertEquals("-1,000.000000001", widget.formatAmount(new BigDecimal("-1000.000000001")));
        assertEquals("-1,000.0000000001", widget.formatAmount(new BigDecimal("-1000.0000000001")));
    }

    private void verifyGrid(Grid grid) {
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Value Period", "Status", "Assignee", "RH Account #", "RH Name", "Wr Wrk Inst",
            "System Title", "System Standard Number", "Standard Number Type", "Last Value Period", "Last Pub Type",
            "Pub Type", "Last Price in USD", "Last Price Flag", "Last Price Source", "Last Price Comment", "Price",
            "Currency", "Price Type", "Price Access Type", "Price Year", "Price Comment", "Price in USD", "Price Flag",
            "Currency Exchange Rate", "Currency Exchange Rate Date", "Last Content", "Last Content Flag",
            "Last Content Source", "Last Content Comment", "Content", "Content Comment", "Content Flag",
            "Content Unit Price", "Comment", "Updated By", "Updated Date"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        verifySize(grid, 100, 100, Unit.PERCENTAGE);
        assertTrue(grid.isFooterVisible());
        FooterRow footerRow = grid.getFooterRow(0);
        assertEquals("Values Count: 0", footerRow.getCell("valuePeriod").getText());
    }

    private void verifySize(Component component, float width, float height, Unit heightUnit) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnit, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }
}
