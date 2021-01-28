package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.reset;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenarioController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Verifies {@link AaclScenarioWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/07/17
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ForeignSecurityUtils.class})
public class AaclScenarioWidgetTest {

    private AaclScenarioWidget scenarioWidget;
    private IAaclScenarioController controller;
    private AaclScenarioMediator mediator;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IAaclScenarioController.class);
        mediator = createMock(AaclScenarioMediator.class);
        scenarioWidget = new AaclScenarioWidget(controller);
        scenarioWidget.setController(controller);
        Whitebox.setInternalState(scenarioWidget, "mediator", mediator);
        Scenario scenario = buildScenario();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource())
            .andReturn(new SimpleImmutableEntry(createMock(Supplier.class), createMock(Supplier.class))).times(2);
        expect(controller.getExportScenarioUsagesStreamSource()).andReturn(streamSource).once();
        expect(controller.getExportScenarioRightsholderTotalsStreamSource()).andReturn(streamSource).once();
        expect(controller.getScenario()).andReturn(scenario).once();
        expect(controller.getScenarioWithAmountsAndLastAction()).andReturn(scenario).once();
        replay(controller, streamSource, ForeignSecurityUtils.class, mediator);
        scenarioWidget.init();
        verify(controller, streamSource, ForeignSecurityUtils.class, mediator);
        reset(controller, mediator);
    }

    @Test
    public void testComponentStructure() {
        assertEquals("Scenario name", scenarioWidget.getCaption());
        assertEquals("view-scenario-widget", scenarioWidget.getId());
        assertEquals(95, scenarioWidget.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, scenarioWidget.getHeightUnits());
        assertFalse(scenarioWidget.isDraggable());
        assertFalse(scenarioWidget.isResizable());
        VerticalLayout content = (VerticalLayout) scenarioWidget.getContent();
        assertEquals(4, content.getComponentCount());
        verifySearchWidget(content.getComponent(0));
        verifyGrid(content.getComponent(1));
        verifyEmptyScenarioLabel(((VerticalLayout) content.getComponent(2)).getComponent(0));
        verifyButtonsLayout(content.getComponent(3));
    }

    @Test
    public void testGetSearchValue() {
        SearchWidget searchWidget = new SearchWidget(controller);
        searchWidget.setSearchValue("search");
        Whitebox.setInternalState(scenarioWidget, searchWidget);
        assertEquals("search", scenarioWidget.getSearchValue());
    }

    @Test
    public void testRefresh() {
        Scenario scenario = new Scenario();
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        expect(controller.isScenarioEmpty()).andReturn(false).once();
        expect(controller.getScenario()).andReturn(scenario).once();
        mediator.onScenarioUpdated(false, scenario);
        expectLastCall().once();
        replay(mediator, controller);
        scenarioWidget.refresh();
        verify(mediator, controller);
    }

    private void verifySearchWidget(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(0);
        assertEquals(1, horizontalLayout.getComponentCount());
        SearchWidget searchWidget = (SearchWidget) horizontalLayout.getComponent(0);
        assertEquals("Enter Rightsholder Name/Account # or Payee Name/Account #",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
        assertEquals(60, searchWidget.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, searchWidget.getWidthUnits());
        assertEquals(Alignment.MIDDLE_CENTER, horizontalLayout.getComponentAlignment(searchWidget));
        assertTrue(horizontalLayout.isSpacing());
        verifySize(horizontalLayout);
    }

    private void verifyGrid(Component component) {
        assertTrue(component instanceof Grid);
        Grid grid = (Grid) component;
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("RH Account #", "RH Name", "Payee Account #", "Payee Name", "Gross Amt in USD",
            "Service Fee Amount", "Net Amt in USD", "Service Fee %"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        assertTrue(grid.isFooterVisible());
        FooterRow footerRow = grid.getFooterRow(0);
        assertEquals("20,000.00", footerRow.getCell("grossTotal").getText());
        assertEquals("6,400.00", footerRow.getCell("serviceFeeTotal").getText());
        assertEquals("13,600.00", footerRow.getCell("netTotal").getText());
    }

    private void verifyEmptyScenarioLabel(Component component) {
        assertEquals(Label.class, component.getClass());
        assertEquals("Scenario is empty due to all usages were excluded", ((Label) component).getValue());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(4, horizontalLayout.getComponentCount());
        Button excludeByPayeeButton = (Button) horizontalLayout.getComponent(0);
        assertEquals("Exclude By Payee", excludeByPayeeButton.getCaption());
        assertEquals("Exclude_By_Payee", excludeByPayeeButton.getId());
        Button exportDetailsButton = (Button) horizontalLayout.getComponent(1);
        assertEquals("Export Details", exportDetailsButton.getCaption());
        assertEquals("Export_Details", exportDetailsButton.getId());
        Button exportButton = (Button) horizontalLayout.getComponent(2);
        assertEquals("Export", exportButton.getCaption());
        assertEquals("Export", exportButton.getId());
        Button closeButton = (Button) horizontalLayout.getComponent(3);
        assertEquals("Close", closeButton.getCaption());
        assertEquals("Close", closeButton.getId());
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(new MarginInfo(false, true, true, false), horizontalLayout.getMargin());
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private Scenario buildScenario() {
        Scenario scenario = new Scenario();
        scenario.setId("4a531e7f-dbd5-41f4-96e9-d8450275f0eb");
        scenario.setName("Scenario name");
        scenario.setGrossTotal(new BigDecimal("20000.00"));
        scenario.setServiceFeeTotal(new BigDecimal("6400.00"));
        scenario.setNetTotal(new BigDecimal("13600.00"));
        return scenario;
    }
}
