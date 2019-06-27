package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.reset;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.ValueProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * Verifies {@link DrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/11/17
 *
 * @author Ihar Suvorau
 */
public class DrillDownByRightsholderWidgetTest {

    private DrillDownByRightsholderWidget widget;

    @Before
    public void setUp() {
        DrillDownByRightsholderController controller = createMock(DrillDownByRightsholderController.class);
        widget = new DrillDownByRightsholderWidget();
        widget.setController(controller);
        widget.init();
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        assertEquals("drill-down-by-rightsholder-widget", widget.getId());
        assertEquals(600, widget.getHeight(), 0);
        assertEquals(Unit.PIXELS, widget.getHeightUnits());
        assertEquals(1280, widget.getWidth(), 0);
        assertEquals(Unit.PIXELS, widget.getWidthUnits());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(new MarginInfo(false, true, true, true), content.getMargin());
        assertEquals(3, content.getComponentCount());
        verifySearchWidget(content.getComponent(0));
        verifyTable(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2));
    }

    private void verifySearchWidget(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(1, horizontalLayout.getComponentCount());
        SearchWidget searchWidget = (SearchWidget) horizontalLayout.getComponent(0);
        assertEquals(60, searchWidget.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, searchWidget.getWidthUnits());
        assertEquals(Alignment.MIDDLE_CENTER, horizontalLayout.getComponentAlignment(searchWidget));
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(100, horizontalLayout.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, horizontalLayout.getWidthUnits());
    }

    private void verifyTable(Component component) {
        assertNotNull(component);
        assertTrue(component instanceof Grid);
        Grid<UsageDto> grid = (Grid<UsageDto>) component;
        assertTrue(grid.getStyleName().contains("drill-down-by-rightsholder-table"));
        verifySize(grid);
        List<Grid.Column<UsageDto, ?>> columns = grid.getColumns();
        columns.forEach(column -> {
            assertTrue(column.isSortable());
            assertTrue(column.isResizable());
        });
        assertArrayEquals(new String[]{"Detail ID", "Usage Batch Name", "Product Family", "Fiscal Year",
                "RRO Account #", "RRO Name", "Payment Date", "Title", "Article", "Standard Number",
                "Standard Number Type", "Wr Wrk Inst", "System Title", "Publisher", "Pub Date", "Number of Copies",
                "Reported Value", "Amt in USD", "Gross Amt in USD", "Service Fee Amount", "Net Amt in USD",
                "Service Fee %", "Market", "Market Period From", "Market Period To", "Author", "Comment"},
            grid.getColumns().stream().map(Column::getCaption).toArray());
        verifyGrossAmountInUsdDataProvider(columns.get(18));
    }

    @SuppressWarnings("unchecked")
    private void verifyGrossAmountInUsdDataProvider(Column grossAmountInUsdColumn) {
        assertEquals("Gross Amt in USD", grossAmountInUsdColumn.getCaption());
        UsageDto usage = new UsageDto();
        ValueProvider<UsageDto, String> provider = grossAmountInUsdColumn.getValueProvider();
        assertNull(provider.apply(usage));
        usage.setBatchGrossAmount(new BigDecimal("100.00"));
        assertEquals("100.00", provider.apply(usage));
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(1, horizontalLayout.getComponentCount());
        Button closeButton = (Button) horizontalLayout.getComponent(0);
        assertEquals("Close", closeButton.getCaption());
        assertEquals("Close", closeButton.getId());
        assertTrue(horizontalLayout.isSpacing());
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }
}