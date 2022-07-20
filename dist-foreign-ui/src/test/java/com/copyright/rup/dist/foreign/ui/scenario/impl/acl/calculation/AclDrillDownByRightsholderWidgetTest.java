package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.reset;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link AclDrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/14/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclDrillDownByRightsholderWidgetTest {

    private AclDrillDownByRightsholderWidget widget;

    @Before
    public void setUp() {
        IAclDrillDownByRightsholderController controller = createMock(IAclDrillDownByRightsholderController.class);
        widget = new AclDrillDownByRightsholderWidget();
        widget.setController(controller);
        widget.init();
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        assertEquals("acl-drill-down-by-rightsholder-widget", widget.getId());
        verifyWindow(widget, StringUtils.EMPTY, 1280, 600, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(new MarginInfo(false, true, true, true), content.getMargin());
        assertEquals(3, content.getComponentCount());
        verifySearchWidget(content.getComponent(0));
        verifyTable(content.getComponent(1));
        UiTestHelper.verifyButtonsLayout(content.getComponent(2), "Close");
    }

    @Test
    public void testGetSearchValue() {
        SearchWidget searchWidget = new SearchWidget(() -> {/*stub*/});
        searchWidget.setSearchValue("search");
        Whitebox.setInternalState(widget, searchWidget);
        assertEquals("search", widget.getSearchValue());
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
        assertEquals("Enter Usage Detail ID or Wr Wrk Inst or System Title or Rightsholder Name/Account #",
            ((TextField) searchWidget.getComponent(0)).getPlaceholder());
    }

    private void verifyTable(Component component) {
        assertNotNull(component);
        assertTrue(component instanceof Grid);
        Grid<UsageDto> grid = (Grid<UsageDto>) component;
        assertTrue(grid.getStyleName().contains("acl-drill-down-by-rightsholder-table"));
        verifyGrid(grid, Arrays.asList(
            Triple.of("Detail ID", 130.0, -1),
            Triple.of("Usage Detail ID", 130.0, -1),
            Triple.of("Product Family", 125.0, -1),
            Triple.of("Usage Batch Name", 145.0, -1),
            Triple.of("Period End Date", 125.0, -1),
            Triple.of("Wr Wrk Inst", 110.0, -1),
            Triple.of("System Title", 250.0, -1),
            Triple.of("Print RH Account #", 140.0, -1),
            Triple.of("Print RH Name", 150.0, -1),
            Triple.of("Digital RH Account #", 140.0, -1),
            Triple.of("Digital RH Name", 150.0, -1),
            Triple.of("Usage Period", 100.0, -1),
            Triple.of("Usage Age Weight", 130.0, -1),
            Triple.of("Det LC ID", 100.0, -1),
            Triple.of("Det LC Name", 200.0, -1),
            Triple.of("Agg LC ID", 100.0, -1),
            Triple.of("Agg LC Name", 200.0, -1),
            Triple.of("Survey Country", 120.0, -1),
            Triple.of("TOU", 120.0, -1),
            Triple.of("# of Copies", 125.0, -1),
            Triple.of("# of Weighted Copies", 150.0, -1),
            Triple.of("Pub Type", 120.0, -1),
            Triple.of("Pub Type Weight", 120.0, -1),
            Triple.of("Price", 100.0, -1),
            Triple.of("Price Flag", 110.0, -1),
            Triple.of("Content", 100.0, -1),
            Triple.of("Content Flag", 110.0, -1),
            Triple.of("Content Unit Price", 150.0, -1),
            Triple.of("Content Unit Price Flag", 160.0, -1),
            Triple.of("Print Value Share", 140.0, -1),
            Triple.of("Print Volume Share", 140.0, -1),
            Triple.of("Print Detail Share", 140.0, -1),
            Triple.of("Print Net Amt in USD", 150.0, -1),
            Triple.of("Digital Value Share", 150.0, -1),
            Triple.of("Digital Volume Share", 150.0, -1),
            Triple.of("Digital Detail Share", 150.0, -1),
            Triple.of("Digital Net Amt in USD", 150.0, -1),
            Triple.of("Combined Net Amt in USD", 170.0, -1)
        ));
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        List<Column<UsageDto, ?>> columns = grid.getColumns();
        columns.forEach(column -> {
            assertTrue(column.isSortable());
            assertTrue(column.isResizable());
        });
    }
}
