package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFooterItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.reset;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
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
@RunWith(PowerMockRunner.class)
@PrepareForTest({ForeignSecurityUtils.class, JavaScript.class})
public class FasScenarioWidgetTest {

    private static final String STYLE_ALIGN_RIGHT = "v-align-right";

    private final List<RightsholderTotalsHolder> rightsholderTotalsHolders =
        loadExpectedRightsholderTotalsHolder("rightsholder_total_holder_1000010022.json");
    private FasScenarioWidget scenarioWidget;
    private IFasScenarioController controller;
    private FasScenarioMediator mediator;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IFasScenarioController.class);
        mediator = createMock(FasScenarioMediator.class);
        scenarioWidget = new FasScenarioWidget(controller);
        scenarioWidget.setController(controller);
        Whitebox.setInternalState(scenarioWidget, "mediator", mediator);
        Scenario scenario = buildScenario();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).times(2);
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
        verifyWindow(scenarioWidget, "Scenario name", 100, 95, Unit.PERCENTAGE);
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
    public void testGridValues() {
        mockStatic(JavaScript.class);
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, Integer.MAX_VALUE, List.of())).andReturn(rightsholderTotalsHolders).once();
        expect(controller.getSize()).andReturn(1).once();
        replay(JavaScript.class, controller);
        Grid<?> grid = (Grid<?>) ((VerticalLayout) scenarioWidget.getContent()).getComponent(1);
        Object[][] expectedCells = {
            {"2000197554", "Andrew Goodwin", 2000017003L, "ProLitteris", "20,000.00", "6,400.00", "13,600.00", "16.0"}
        };
        verifyGridItems(grid, rightsholderTotalsHolders, expectedCells);
        verify(JavaScript.class, controller);
        Object[][] expectedFooterColumns = {
            {"grossTotal", "20,000.00", STYLE_ALIGN_RIGHT},
            {"serviceFeeTotal", "6,400.00", STYLE_ALIGN_RIGHT},
            {"netTotal", "13,600.00", STYLE_ALIGN_RIGHT}
        };
        verifyFooterItems(grid, expectedFooterColumns);
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
        assertThat(component, instanceOf(VerticalLayout.class));
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
        verifyWindow(horizontalLayout, null, 100, 100, Unit.PERCENTAGE);
    }

    private void verifyGrid(Component component) {
        assertThat(component, instanceOf(Grid.class));
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
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(4, horizontalLayout.getComponentCount());
        Button excludeByRroButton = (Button) horizontalLayout.getComponent(0);
        assertEquals("Exclude By RRO", excludeByRroButton.getCaption());
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

    private Scenario buildScenario() {
        Scenario scenario = new Scenario();
        scenario.setId("68fed0a7-b277-432e-a8cd-ed312d841658");
        scenario.setName("Scenario name");
        scenario.setGrossTotal(new BigDecimal("20000.00"));
        scenario.setServiceFeeTotal(new BigDecimal("6400.00"));
        scenario.setNetTotal(new BigDecimal("13600.00"));
        return scenario;
    }

    private List<RightsholderTotalsHolder> loadExpectedRightsholderTotalsHolder(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, new TypeReference<List<RightsholderTotalsHolder>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
