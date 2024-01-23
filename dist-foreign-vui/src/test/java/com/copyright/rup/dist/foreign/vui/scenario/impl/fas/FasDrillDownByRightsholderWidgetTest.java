package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterComponent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySize;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link FasDrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/9/19
 *
 * @author Stanislau Rudak
 */
public class FasDrillDownByRightsholderWidgetTest {

    private static final String WIDTH_130_PX = "130px";
    private static final String WIDTH_170_PX = "170px";
    private static final String WIDTH_150_PX = "150px";
    private static final String SEARCH_PROMPT =
        "Enter Detail ID or Standard Number or Wr Wrk Inst or RRO Name/Account #";

    private FasDrillDownByRightsholderWidget widget;

    @Before
    public void setUp() {
        widget = new FasDrillDownByRightsholderWidget();
        widget.setController(createMock(IFasDrillDownByRightsholderController.class));
        widget.init();
    }

    @Test
    public void testComponentStructure() {
        assertEquals("drill-down-by-rightsholder-widget", widget.getId().orElseThrow());
        assertEquals("drill-down-by-rightsholder-widget", widget.getClassName());
        verifyWindow(widget, StringUtils.EMPTY, "1280px", "600px", Unit.PIXELS, true);
        assertTrue(widget.isDraggable());
        assertFalse(widget.isCloseOnEsc());
        assertFalse(widget.isCloseOnOutsideClick());
        VerticalLayout content = (VerticalLayout) getDialogContent(widget);
        assertFalse(content.isPadding());
        assertFalse(content.isSpacing());
        assertEquals(2, content.getComponentCount());
        verifyToolbar(content.getComponentAt(0));
        verifyGrid(content.getComponentAt(1));
        verifyButton(getFooterComponent(widget, 1), "Close", true);
    }

    @Test
    public void testGetSearchPrompt() {
        assertEquals(SEARCH_PROMPT, widget.getSearchPrompt());
    }

    @Test
    public void testGetExcludedColumns() {
        assertEquals(new String[]{"Detail ID"}, widget.getExcludedColumns());
    }

    private void verifyToolbar(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(3, horizontalLayout.getComponentCount());
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(JustifyContentMode.BETWEEN, horizontalLayout.getJustifyContentMode());
        assertEquals("100%", horizontalLayout.getWidth());
        assertEquals(Unit.PERCENTAGE, horizontalLayout.getWidthUnit().orElseThrow());
        assertEquals("drill-down-by-rightsholder-toolbar", horizontalLayout.getId().orElseThrow());
        assertEquals("drill-down-by-rightsholder-toolbar", horizontalLayout.getClassName());
        Div div = (Div) horizontalLayout.getComponentAt(0);
        assertEquals("<div></div>", div.getElement().getOuterHTML());
        SearchWidget searchWidget = (SearchWidget) horizontalLayout.getComponentAt(1);
        assertEquals(SEARCH_PROMPT, Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
        assertEquals("60%", searchWidget.getWidth());
        Component menuComponent = horizontalLayout.getComponentAt(2);
        assertThat(menuComponent, IsInstanceOf.instanceOf(Button.class));
        Button menuButton = (Button) menuComponent;
        assertNotNull(menuComponent);
        assertEquals("Hide/Unhide", menuButton.getTooltip().getText());
    }

    private void verifyGrid(Component component) {
        assertNotNull(component);
        assertThat(component, instanceOf(Grid.class));
        Grid<UsageDto> grid = (Grid<UsageDto>) component;
        assertEquals("drill-down-by-rightsholder-table", grid.getId().orElseThrow());
        assertEquals("drill-down-by-rightsholder-table", grid.getClassName());
        verifySize(grid, "100%", Unit.PERCENTAGE, "100%", Unit.PERCENTAGE);
        UiTestHelper.verifyGrid(grid, List.of(
            Pair.of("Detail ID", WIDTH_130_PX),
            Pair.of("Product Family", WIDTH_170_PX),
            Pair.of("Usage Batch Name", "180px"),
            Pair.of("RRO Account #", WIDTH_150_PX),
            Pair.of("RRO Name", "140px"),
            Pair.of("Wr Wrk Inst", WIDTH_130_PX),
            Pair.of("System Title", "300px"),
            Pair.of("Reported Standard Number", "270px"),
            Pair.of("Standard Number", "190px"),
            Pair.of("Standard Number Type", "220px"),
            Pair.of("Fiscal Year", WIDTH_130_PX),
            Pair.of("Payment Date", WIDTH_150_PX),
            Pair.of("Reported Title", null),
            Pair.of("Article", null),
            Pair.of("Publisher", "135px"),
            Pair.of("Pub Date", WIDTH_130_PX),
            Pair.of("Number of Copies", "180px"),
            Pair.of("Reported Value", WIDTH_170_PX),
            Pair.of("Gross Amt in USD", WIDTH_170_PX),
            Pair.of("Batch Amt in USD", WIDTH_170_PX),
            Pair.of("Service Fee Amount", "200px"),
            Pair.of("Net Amt in USD", WIDTH_150_PX),
            Pair.of("Service Fee %", WIDTH_150_PX),
            Pair.of("Market", "115px"),
            Pair.of("Market Period From", "200px"),
            Pair.of("Market Period To", "190px"),
            Pair.of("Author", "105px"),
            Pair.of("Comment", "200px")
        ));
    }
}
