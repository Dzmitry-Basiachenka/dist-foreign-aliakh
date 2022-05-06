package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
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
 * Verifies {@link SalDrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/14/20
 *
 * @author Anton Azarenka
 */
public class SalDrillDownByRightsholderWidgetTest {

    private SalDrillDownByRightsholderWidget widget;

    @Before
    public void setUp() {
        ISalDrillDownByRightsholderController controller = createMock(ISalDrillDownByRightsholderController.class);
        widget = new SalDrillDownByRightsholderWidget();
        widget.setController(controller);
        widget.init();
    }

    @Test
    public void testComponentStructure() {
        assertEquals("drill-down-by-rightsholder-widget", widget.getId());
        verifyWindow(widget, StringUtils.EMPTY, 1280, 600, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(new MarginInfo(false, true, true, true), content.getMargin());
        assertEquals(3, content.getComponentCount());
        verifySearchWidget(content.getComponent(0));
        verifyTable(content.getComponent(1));
        UiTestHelper.verifyButtonsLayout(content.getComponent(2), "Close");
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
        Grid<UsageDto> grid = (Grid<UsageDto>) component;
        assertTrue(grid.getStyleName().contains("drill-down-by-rightsholder-table"));
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        List<Column<UsageDto, ?>> columns = grid.getColumns();
        columns.forEach(column -> {
            assertTrue(column.isSortable());
            assertTrue(column.isResizable());
        });
        verifyGrid(grid, Arrays.asList(
            Triple.of("Detail ID", 130.0, -1),
            Triple.of("Detail Type", 115.0, -1),
            Triple.of("Product Family", 125.0, -1),
            Triple.of("Usage Batch Name", 145.0, -1),
            Triple.of("Period End Date", 115.0, -1),
            Triple.of("Licensee Account #", 150.0, -1),
            Triple.of("Licensee Name", 300.0, -1),
            Triple.of("Wr Wrk Inst", 110.0, -1),
            Triple.of("System Title", 300.0, -1),
            Triple.of("Standard Number", 140.0, -1),
            Triple.of("Standard Number Type", 155.0, -1),
            Triple.of("Gross Amt in USD", 130.0, -1),
            Triple.of("Service Fee Amount", 150.0, -1),
            Triple.of("Net Amt in USD", 120.0, -1),
            Triple.of("Service Fee %", 115.0, -1),
            Triple.of("Assessment Name", 180.0, -1),
            Triple.of("Assessment Type", 150.0, -1),
            Triple.of("Date of Scored Assessment", 200.0, -1),
            Triple.of("Reported Work Portion ID", 180.0, -1),
            Triple.of("Reported Title", 170.0, -1),
            Triple.of("Reported Article or Chapter Title", 240.0, -1),
            Triple.of("Reported Standard Number", 150.0, -1),
            Triple.of("Reported Author", 150.0, -1),
            Triple.of("Reported Publisher", 150.0, -1),
            Triple.of("Reported Publication Date", 200.0, -1),
            Triple.of("Reported Page Range", 150.0, -1),
            Triple.of("Reported Vol/Number/Series", 200.0, -1),
            Triple.of("Reported Media Type", 150.0, -1),
            Triple.of("Coverage Year", 115.0, -1),
            Triple.of("Question Identifier", 150.0, -1),
            Triple.of("Grade", 115.0, -1),
            Triple.of("Grade Group", 115.0, -1),
            Triple.of("States", 115.0, -1),
            Triple.of("Number of Views", 150.0, -1),
            Triple.of("Comment", 115.0, -1)
        ));
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
}
