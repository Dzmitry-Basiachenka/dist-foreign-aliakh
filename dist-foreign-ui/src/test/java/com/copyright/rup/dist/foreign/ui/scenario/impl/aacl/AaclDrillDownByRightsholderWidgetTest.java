package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclDrillDownByRightsholderController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link AaclDrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/01/20
 *
 * @author Uladzislau Shalamitski
 */
public class AaclDrillDownByRightsholderWidgetTest {

    private AaclDrillDownByRightsholderWidget widget;

    @Before
    public void setUp() {
        IAaclDrillDownByRightsholderController controller = createMock(IAaclDrillDownByRightsholderController.class);
        widget = new AaclDrillDownByRightsholderWidget();
        widget.setController(controller);
        widget.init();
    }

    @Test
    public void testComponentStructure() {
        verifyWindow(widget, StringUtils.EMPTY, 1280, 600, Unit.PIXELS);
        assertEquals("drill-down-by-rightsholder-widget", widget.getId());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(new MarginInfo(false, true, true, true), content.getMargin());
        assertEquals(3, content.getComponentCount());
        verifySearchWidget(content.getComponent(0));
        verifyTable(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2), "Close");
    }

    private void verifySearchWidget(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
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
        assertThat(component, instanceOf(Grid.class));
        Grid<UsageDto> grid = (Grid<UsageDto>) component;
        verifyGrid(grid, Arrays.asList(
            Triple.of("Detail ID", 130.0, -1),
            Triple.of("Product Family", 125.0, -1),
            Triple.of("Usage Batch Name", 145.0, -1),
            Triple.of("Period End Date", 120.0, -1),
            Triple.of("Wr Wrk Inst", 110.0, -1),
            Triple.of("System Title", 300.0, -1),
            Triple.of("Standard Number", 140.0, -1),
            Triple.of("Standard Number Type", 155.0, -1),
            Triple.of("Gross Amt in USD", 130.0, -1),
            Triple.of("Service Fee Amount", 150.0, -1),
            Triple.of("Net Amt in USD", 120.0, -1),
            Triple.of("Service Fee %", 115.0, -1),
            Triple.of("Det LC ID", 80.0, -1),
            Triple.of("Det LC Enrollment", 140.0, -1),
            Triple.of("Det LC Discipline", 140.0, -1),
            Triple.of("Agg LC ID", 80.0, -1),
            Triple.of("Agg LC Enrollment", 140.0, -1),
            Triple.of("Agg LC Discipline", 140.0, -1),
            Triple.of("Pub Type", 140.0, -1),
            Triple.of("Pub Type Weight", 120.0, -1),
            Triple.of("Historical Pub Type", 140.0, -1),
            Triple.of("Institution", 115.0, -1),
            Triple.of("Usage Period", 100.0, -1),
            Triple.of("Usage Age Weight", 130.0, -1),
            Triple.of("Usage Source", 140.0, -1),
            Triple.of("Number of Copies", 140.0, -1),
            Triple.of("Number of Pages", 140.0, -1),
            Triple.of("Right Limitation", 140.0, -1),
            Triple.of("Comment", 200.0, -1)
        ));
        assertTrue(grid.getStyleName().contains("drill-down-by-rightsholder-table"));
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        List<Column<UsageDto, ?>> columns = grid.getColumns();
        columns.forEach(column -> {
            assertTrue(column.isSortable());
            assertTrue(column.isResizable());
        });
    }
}
