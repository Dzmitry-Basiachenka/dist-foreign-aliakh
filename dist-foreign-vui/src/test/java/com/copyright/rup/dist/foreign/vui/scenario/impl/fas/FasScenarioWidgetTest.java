package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterComponent;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.FooterRow.FooterCell;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link FasScenarioWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/07/17
 *
 * @author Ihar Suvorau
 */
public class FasScenarioWidgetTest {

    private FasScenarioWidget scenarioWidget;
    private IFasScenarioController controller;

    @Before
    public void setUp() {
        controller = createMock(IFasScenarioController.class);
        scenarioWidget = new FasScenarioWidget(controller);
        scenarioWidget.setController(controller);
        Scenario scenario = buildScenario();
        expect(controller.getScenario()).andReturn(scenario).once();
        expect(controller.getScenarioWithAmountsAndLastAction()).andReturn(scenario).once();
        replay(controller);
        scenarioWidget.init();
        verify(controller);
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        assertFalse(scenarioWidget.isResizable());
        assertFalse(scenarioWidget.isDraggable());
        assertFalse(scenarioWidget.isResizable());
        verifyContent(getDialogContent(scenarioWidget));
    }

    @Test
    public void testGetSearchValue() {
        var searchWidget = new SearchWidget(controller);
        searchWidget.setSearchValue("search");
        Whitebox.setInternalState(scenarioWidget, searchWidget);
        assertEquals("search", scenarioWidget.getSearchValue());
    }

    @Test
    public void testRefresh() {
        //TODO: {dbasiachenka} implement
    }

    private void verifyContent(Component content) {
        assertEquals(VerticalLayout.class, content.getClass());
        VerticalLayout layout = (VerticalLayout) content;
        assertEquals(3, layout.getComponentCount());
        verifySearchWidget(layout.getComponentAt(0));
        verifyGrid(layout.getComponentAt(1));
        verifyEmptyScenarioLabel(((VerticalLayout) layout.getComponentAt(2)).getComponentAt(0));
        verifyButtonsLayout(getFooterComponent(scenarioWidget, 1));
    }

    private void verifySearchWidget(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(JustifyContentMode.CENTER, horizontalLayout.getJustifyContentMode());
        assertEquals("100%", horizontalLayout.getWidth());
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(1, horizontalLayout.getComponentCount());
        SearchWidget searchWidget = (SearchWidget) horizontalLayout.getComponentAt(0);
        assertEquals("Enter Rightsholder Name/Account # or Payee Name/Account #",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
        assertEquals("60%", searchWidget.getWidth());
        assertEquals(Unit.PERCENTAGE, searchWidget.getWidthUnit().orElseThrow());
    }

    private void verifyGrid(Component component) {
        assertThat(component, instanceOf(Grid.class));
        Grid grid = (Grid) component;
        List<Column> columns = grid.getColumns();
        assertEquals(List.of("RH Account #", "RH Name", "Payee Account #", "Payee Name", "Gross Amt in USD",
                "Service Fee Amount", "Net Amt in USD", "Service Fee %"),
            columns.stream().map(column -> column.getHeaderText()).collect(Collectors.toList()));
        FooterRow footerRow = (FooterRow) grid.getFooterRows().get(1);
        verifyTotalCellValue(footerRow.getCell(grid.getColumnByKey("grossTotal")), "20,000.00");
        verifyTotalCellValue(footerRow.getCell(grid.getColumnByKey("serviceFeeTotal")), "6,400.00");
        verifyTotalCellValue(footerRow.getCell(grid.getColumnByKey("netTotal")), "13,600.00");
    }

    private void verifyTotalCellValue(FooterCell cell, String expectedValue) {
        assertTrue(StringUtils.contains(cell.getComponent().getElement().getOuterHTML(), expectedValue));
    }

    private void verifyEmptyScenarioLabel(Component component) {
        assertEquals(Label.class, component.getClass());
        assertTrue(StringUtils.contains(component.getElement().getOuterHTML(),
            "Scenario is empty due to all usages were excluded"));
    }

    private void verifyButtonsLayout(Component component) {
        UiTestHelper.verifyButtonsLayout(component, true, "Exclude By RRO", "Export Details", "Export", "Close");
        assertTrue(((HorizontalLayout) component).isSpacing());
    }

    private Scenario buildScenario() {
        var scenario = new Scenario();
        scenario.setId("65d4cdde-eeff-4b31-885f-78170d9e7790");
        scenario.setName("Scenario name");
        scenario.setGrossTotal(new BigDecimal("20000.00"));
        scenario.setServiceFeeTotal(new BigDecimal("6400.00"));
        scenario.setNetTotal(new BigDecimal("13600.00"));
        return scenario;
    }
}
