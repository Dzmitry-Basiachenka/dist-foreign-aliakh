package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.reset;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
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
@RunWith(PowerMockRunner.class)
@PrepareForTest({JavaScript.class})
public class FasDrillDownByRightsholderWidgetTest {

    private final List<UsageDto> usages = loadExpectedUsageDto("usage_dto_c2c29b7b.json");
    private FasDrillDownByRightsholderWidget widget;
    private IFasDrillDownByRightsholderController controller;

    @Before
    public void setUp() {
        controller = createMock(IFasDrillDownByRightsholderController.class);
        widget = new FasDrillDownByRightsholderWidget();
        widget.setController(controller);
        widget.init();
        reset(controller);
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
    }

    @Test
    public void testGridValues() {
        mockStatic(JavaScript.class);
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, Integer.MAX_VALUE, List.of())).andReturn(usages).once();
        expect(controller.getSize()).andReturn(1).once();
        replay(JavaScript.class, controller);
        Grid<?> grid = (Grid<?>) ((VerticalLayout) widget.getContent()).getComponent(1);
        Object[][] expectedCells = {
            {"c2c29b7b-13e1-4f02-afc0-ed1684253e20", "FAS", "Paid batch", 1000000008L, "Intercept Limited [T]",
                340415946L, "100 ROAD MOVIES", "2046-9055", "VALISSN", "FY2021", "02/12/2021",
                "Paediatrics and international child health", "some article", "some publisher", "02/13/2021", 2,
                "3,000.00", "500.00", "1,000.00", "1,866.67", "9,800.00", "16.0", "Univ", 2015, 2016, "author",
                "usage from usages_10.csv"}
        };
        verifyGridItems(grid, usages, expectedCells);
        verify(JavaScript.class, controller);
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
        assertTrue(grid.getStyleName().contains("drill-down-by-rightsholder-table"));
        verifyGrid(grid, Arrays.asList(
            Triple.of("Detail ID", 130.0, -1),
            Triple.of("Product Family", 125.0, -1),
            Triple.of("Usage Batch Name", 145.0, -1),
            Triple.of("RRO Account #", 125.0, -1),
            Triple.of("RRO Name", 135.0, -1),
            Triple.of("Wr Wrk Inst", 110.0, -1),
            Triple.of("System Title", 300.0, -1),
            Triple.of("Standard Number", 140.0, -1),
            Triple.of("Standard Number Type", 155.0, -1),
            Triple.of("Fiscal Year", 105.0, -1),
            Triple.of("Payment Date", 115.0, -1),
            Triple.of("Title", -1.0, -1),
            Triple.of("Article", -1.0, -1),
            Triple.of("Publisher", 135.0, -1),
            Triple.of("Pub Date", 90.0, -1),
            Triple.of("Number of Copies", 140.0, -1),
            Triple.of("Reported Value", 130.0, -1),
            Triple.of("Gross Amt in USD", 130.0, -1),
            Triple.of("Batch Amt in USD", 130.0, -1),
            Triple.of("Service Fee Amount", 150.0, -1),
            Triple.of("Net Amt in USD", 120.0, -1),
            Triple.of("Service Fee %", 115.0, -1),
            Triple.of("Market", 115.0, -1),
            Triple.of("Market Period From", 150.0, -1),
            Triple.of("Market Period To", 145.0, -1),
            Triple.of("Author", 90.0, -1),
            Triple.of("Comment", 200.0, -1)
        ));
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        List<Column<UsageDto, ?>> columns = grid.getColumns();
        columns.forEach(column -> {
            assertTrue(column.isSortable());
            assertTrue(column.isResizable());
        });
        verifyBatchGrossAmountDataProvider(columns.get(18));
    }

    @SuppressWarnings("unchecked")
    private void verifyBatchGrossAmountDataProvider(Column batchGrossAmountColumn) {
        assertEquals("Batch Amt in USD", batchGrossAmountColumn.getCaption());
        UsageDto usage = new UsageDto();
        ValueProvider<UsageDto, String> provider = batchGrossAmountColumn.getValueProvider();
        assertEquals(StringUtils.EMPTY, provider.apply(usage));
        usage.setBatchGrossAmount(new BigDecimal("100.00"));
        assertEquals("100.00", provider.apply(usage));
    }

    private List<UsageDto> loadExpectedUsageDto(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<List<UsageDto>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
