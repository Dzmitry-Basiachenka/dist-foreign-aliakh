package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterComponent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySize;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.io.IOException;
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

    private static final String WIDTH_300 = "300px";
    private static final String WIDTH_200 = "200px";
    private static final String WIDTH_170 = "170px";
    private static final String SEARCH_PROMPT =
        "Enter Detail ID or Standard Number or Wr Wrk Inst or RRO Name/Account #";

    private final List<UsageDto> usages = loadExpectedUsageDto("usage_dto_c2c29b7b.json");

    private FasDrillDownByRightsholderWidget widget;
    private IFasDrillDownByRightsholderController controller;

    @Before
    public void setUp() {
        controller = createMock(IFasDrillDownByRightsholderController.class);
        widget = new FasDrillDownByRightsholderWidget();
        widget.setController(controller);
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
        var content = (VerticalLayout) getDialogContent(widget);
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
        assertArrayEquals(new String[]{"Detail ID"}, widget.getExcludedColumns());
    }

    @Test
    public void testGridValues() {
        expect(controller.loadBeans(0, Integer.MAX_VALUE, List.of())).andReturn(usages).once();
        expect(controller.getSize()).andReturn(1).once();
        replay(controller);
        Grid<?> grid = (Grid<?>) ((VerticalLayout) getDialogContent(widget)).getComponentAt(1);
        Object[][] expectedCells = {
            {"c2c29b7b-13e1-4f02-afc0-ed1684253e20", "FAS", "Paid batch", "1000000008", "Intercept Limited [T]",
                "340415946", "100 ROAD MOVIES", "1046-9055", "2046-9055", "VALISSN", "FY2021", "02/12/2021",
                "Paediatrics and international child health", "some article", "some publisher", "02/13/2021", "2",
                "3,000.00", "500.00", "1,000.00", "1,866.67", "9,800.00", "16.0", "Univ", "2015", "2016", "author",
                "usage from usages_10.csv"}
        };
        verifyGridItems(grid, usages, expectedCells);
        verify(controller);
    }

    private void verifyToolbar(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var horizontalLayout = (HorizontalLayout) component;
        assertEquals(3, horizontalLayout.getComponentCount());
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(JustifyContentMode.BETWEEN, horizontalLayout.getJustifyContentMode());
        assertEquals("100%", horizontalLayout.getWidth());
        assertEquals(Unit.PERCENTAGE, horizontalLayout.getWidthUnit().orElseThrow());
        assertEquals("drill-down-by-rightsholder-toolbar", horizontalLayout.getId().orElseThrow());
        assertEquals("drill-down-by-rightsholder-toolbar", horizontalLayout.getClassName());
        var div = (Div) horizontalLayout.getComponentAt(0);
        assertEquals("<div></div>", div.getElement().getOuterHTML());
        SearchWidget searchWidget = (SearchWidget) horizontalLayout.getComponentAt(1);
        assertEquals(SEARCH_PROMPT, Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
        assertEquals("60%", searchWidget.getWidth());
        var menuComponent = horizontalLayout.getComponentAt(2);
        assertThat(menuComponent, IsInstanceOf.instanceOf(Button.class));
        var menuButton = (Button) menuComponent;
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
            Pair.of("Detail ID", WIDTH_300),
            Pair.of("Product Family", "160px"),
            Pair.of("Usage Batch Name", WIDTH_200),
            Pair.of("RRO Account #", "160px"),
            Pair.of("RRO Name", WIDTH_300),
            Pair.of("Wr Wrk Inst", "140px"),
            Pair.of("System Title", WIDTH_300),
            Pair.of("Reported Standard Number", "260px"),
            Pair.of("Standard Number", "180px"),
            Pair.of("Standard Number Type", "225px"),
            Pair.of("Fiscal Year", "130px"),
            Pair.of("Payment Date", "145px"),
            Pair.of("Reported Title", WIDTH_300),
            Pair.of("Article", "135px"),
            Pair.of("Publisher", "135px"),
            Pair.of("Pub Date", "110px"),
            Pair.of("Number of Copies", "185px"),
            Pair.of("Reported Value", WIDTH_170),
            Pair.of("Gross Amt in USD", WIDTH_170),
            Pair.of("Batch Amt in USD", WIDTH_170),
            Pair.of("Service Fee Amount", WIDTH_200),
            Pair.of("Net Amt in USD", "150px"),
            Pair.of("Service Fee %", "145px"),
            Pair.of("Market", "120px"),
            Pair.of("Market Period From", WIDTH_200),
            Pair.of("Market Period To", "185px"),
            Pair.of("Author", WIDTH_200),
            Pair.of("Comment", WIDTH_200)
        ));
    }

    private List<UsageDto> loadExpectedUsageDto(String fileName) {
        try {
            var content = TestUtils.fileToString(this.getClass(), fileName);
            var mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
