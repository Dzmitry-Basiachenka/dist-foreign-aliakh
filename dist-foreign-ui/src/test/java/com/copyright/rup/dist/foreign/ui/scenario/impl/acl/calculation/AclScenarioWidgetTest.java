package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFooterItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Verifies {@link AclScenarioWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/28/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AclScenarioWidget.class, Windows.class})
public class AclScenarioWidgetTest {

    private static final String SCENARIO_ID = "24b0a5a9-6380-4519-91f2-779c98ed45cc";
    private static final Long RH_ACCOUNT_NUMBER = 7000447306L;
    private static final String RH_NAME = "Longhouse";
    private static final String GROSS_TOTAL_PRINT = "20000.00";
    private static final String SERVICE_FEE_TOTAL_PRINT = "6400.00";
    private static final String NET_TOTAL_PRINT = "13600.00";
    private static final String GROSS_TOTAL_DIGITAL = "1000.00";
    private static final String SERVICE_FEE_TOTAL_DIGITAL = "600.00";
    private static final String NET_TOTAL_DIGITAL = "130.00";
    private static final String STYLE_ALIGN_RIGHT = "v-align-right";

    private AclScenarioWidget scenarioWidget;
    private IAclScenarioController controller;

    @Before
    public void setUp() {
        controller = createMock(IAclScenarioController.class);
        scenarioWidget = new AclScenarioWidget(controller);
        scenarioWidget.setController(controller);
        AclScenarioDto scenario = buildAclScenarioDto();
        IStreamSource streamSource = createMock(IStreamSource.class);
        List<AclRightsholderTotalsHolder> holders = Collections.singletonList(buildAclRightsholderTotalsHolder());
        expect(controller.getAclRightsholderTotalsHolders()).andReturn(holders).once();
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).times(2);
        expect(controller.getExportAclScenarioDetailsStreamSource()).andReturn(streamSource).once();
        expect(controller.getExportAclScenarioRightsholderTotalsStreamSource()).andReturn(streamSource).once();
        expect(controller.getScenario()).andReturn(scenario).once();
        replay(controller, streamSource);
        scenarioWidget.init();
        verify(controller, streamSource);
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        verifyWindow(scenarioWidget, "Scenario name", 100, 95, Unit.PERCENTAGE);
        assertFalse(scenarioWidget.isDraggable());
        assertFalse(scenarioWidget.isResizable());
        VerticalLayout content = (VerticalLayout) scenarioWidget.getContent();
        assertEquals(3, content.getComponentCount());
        verifySearchWidget(content.getComponent(0));
        verifyGrid((Grid) content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2), "Export Details", "Export", "Close");
    }

    @Test
    public void testGridValues() {
        replay(controller);
        Grid grid = (Grid) ((VerticalLayout) scenarioWidget.getContent()).getComponent(1);
        DataProvider dataProvider = grid.getDataProvider();
        dataProvider.refreshAll();
        Object[][] expectedCells = {
            {"7000447306", RH_NAME, "20,000.00", "6,400.00", "13,600.00", "1,000.00",
                SERVICE_FEE_TOTAL_DIGITAL, NET_TOTAL_DIGITAL, "1", "1", "ACL"}
        };
        verifyGridItems(grid, Collections.singletonList(buildAclRightsholderTotalsHolder()), expectedCells);
        verify(controller);
        Object[][] expectedFooterColumns = {
            {"grossTotalPrint", "20,000.00", STYLE_ALIGN_RIGHT},
            {"serviceFeeTotalPrint", "6,400.00", STYLE_ALIGN_RIGHT},
            {"netTotalPrint", "13,600.00", STYLE_ALIGN_RIGHT},
            {"grossTotalDigital", "1,000.00", STYLE_ALIGN_RIGHT},
            {"serviceFeeTotalDigital", SERVICE_FEE_TOTAL_DIGITAL, STYLE_ALIGN_RIGHT},
            {"netTotalDigital", NET_TOTAL_DIGITAL, STYLE_ALIGN_RIGHT},
        };
        verifyFooterItems(grid, expectedFooterColumns);
    }

    @Test
    public void testGetSearchValue() {
        SearchWidget searchWidget = new SearchWidget(scenarioWidget);
        searchWidget.setSearchValue("search");
        Whitebox.setInternalState(scenarioWidget, searchWidget);
        assertEquals("search", scenarioWidget.getSearchValue());
    }

    @Test
    public void testNumberOfTitlesCellClick() throws Exception {
        RightsholderResultsFilter filter = buildRightsholderResultsFilter();
        AclScenarioDrillDownTitlesWindow mockWindow = createMock(AclScenarioDrillDownTitlesWindow.class);
        expectNew(AclScenarioDrillDownTitlesWindow.class, eq(controller), eq(filter)).andReturn(mockWindow).once();
        mockStatic(Windows.class);
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(Windows.class, AclScenarioDrillDownTitlesWindow.class, controller);
        Grid grid = Whitebox.getInternalState(scenarioWidget, "rightsholdersGrid");
        Grid.Column column = (Grid.Column) grid.getColumns().get(8);
        ValueProvider<AclRightsholderTotalsHolder, Button> provider = column.getValueProvider();
        Button button = provider.apply(buildAclRightsholderTotalsHolder());
        button.click();
        verify(Windows.class, AclScenarioDrillDownTitlesWindow.class, controller);
    }

    @Test
    public void testNumberOfAggLcClassesCellClick() throws Exception {
        RightsholderResultsFilter filter = buildRightsholderResultsFilter();
        AclScenarioDrillDownAggLcClassesWindow mockWindow = createMock(AclScenarioDrillDownAggLcClassesWindow.class);
        expectNew(AclScenarioDrillDownAggLcClassesWindow.class, eq(controller), eq(filter)).andReturn(mockWindow)
            .once();
        mockStatic(Windows.class);
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(Windows.class, AclScenarioDrillDownAggLcClassesWindow.class, controller);
        Grid grid = Whitebox.getInternalState(scenarioWidget, "rightsholdersGrid");
        Grid.Column column = (Grid.Column) grid.getColumns().get(9);
        ValueProvider<AclRightsholderTotalsHolder, Button> provider = column.getValueProvider();
        Button button = provider.apply(buildAclRightsholderTotalsHolder());
        button.click();
        verify(Windows.class, AclScenarioDrillDownAggLcClassesWindow.class, controller);
    }

    private AclScenarioDto buildAclScenarioDto() {
        AclScenarioDto scenario = new AclScenarioDto();
        scenario.setId(SCENARIO_ID);
        scenario.setName("Scenario name");
        scenario.setGrossTotalPrint(new BigDecimal(GROSS_TOTAL_PRINT));
        scenario.setServiceFeeTotalPrint(new BigDecimal(SERVICE_FEE_TOTAL_PRINT));
        scenario.setNetTotalPrint(new BigDecimal(NET_TOTAL_PRINT));
        scenario.setGrossTotalDigital(new BigDecimal(GROSS_TOTAL_DIGITAL));
        scenario.setServiceFeeTotalDigital(new BigDecimal(SERVICE_FEE_TOTAL_DIGITAL));
        scenario.setNetTotalDigital(new BigDecimal(NET_TOTAL_DIGITAL));
        return scenario;
    }

    private RightsholderResultsFilter buildRightsholderResultsFilter() {
        RightsholderResultsFilter filter = new RightsholderResultsFilter();
        filter.setScenarioId(SCENARIO_ID);
        filter.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        filter.setRhName(RH_NAME);
        return filter;
    }

    private AclRightsholderTotalsHolder buildAclRightsholderTotalsHolder() {
        AclRightsholderTotalsHolder holder = new AclRightsholderTotalsHolder();
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(RH_ACCOUNT_NUMBER);
        rightsholder.setName(RH_NAME);
        holder.setRightsholder(rightsholder);
        holder.setGrossTotalPrint(new BigDecimal(GROSS_TOTAL_PRINT));
        holder.setServiceFeeTotalPrint(new BigDecimal(SERVICE_FEE_TOTAL_PRINT));
        holder.setNetTotalPrint(new BigDecimal(NET_TOTAL_PRINT));
        holder.setGrossTotalDigital(new BigDecimal(GROSS_TOTAL_DIGITAL));
        holder.setServiceFeeTotalDigital(new BigDecimal(SERVICE_FEE_TOTAL_DIGITAL));
        holder.setNetTotalDigital(new BigDecimal(NET_TOTAL_DIGITAL));
        holder.setNumberOfTitles(1);
        holder.setNumberOfAggLcClasses(1);
        holder.setLicenseType("ACL");
        return holder;
    }

    private void verifySearchWidget(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(0);
        assertEquals(1, horizontalLayout.getComponentCount());
        SearchWidget searchWidget = (SearchWidget) horizontalLayout.getComponent(0);
        assertEquals("Enter Rightsholder Name/Account #",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
        assertEquals(60, searchWidget.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, searchWidget.getWidthUnits());
        assertEquals(Alignment.MIDDLE_CENTER, horizontalLayout.getComponentAlignment(searchWidget));
        assertTrue(horizontalLayout.isSpacing());
        verifyWindow(horizontalLayout, null, 100, 100, Unit.PERCENTAGE);
    }

    private void verifyGrid(Grid grid) {
        UiTestHelper.verifyGrid(grid, Arrays.asList(
            Triple.of("RH Account #", -1.0, 1),
            Triple.of("RH Name", -1.0, 2),
            Triple.of("Print Gross Amt in USD", -1.0, 1),
            Triple.of("Print Service Fee Amt", -1.0, 1),
            Triple.of("Print Net Amt in USD", -1.0, 1),
            Triple.of("Digital Gross Amt in USD", -1.0, 1),
            Triple.of("Digital Service Fee Amt", -1.0, 1),
            Triple.of("Digital Net Amt in USD", -1.0, 1),
            Triple.of("# of Titles", -1.0, 2),
            Triple.of("# of Agg Lic Classes", -1.0, 2),
            Triple.of("License Type", -1.0, 2)
        ));
        FooterRow footerRow = grid.getFooterRow(0);
        assertEquals("20,000.00", footerRow.getCell("grossTotalPrint").getText());
        assertEquals("6,400.00", footerRow.getCell("serviceFeeTotalPrint").getText());
        assertEquals("13,600.00", footerRow.getCell("netTotalPrint").getText());
        assertEquals("1,000.00", footerRow.getCell("grossTotalDigital").getText());
        assertEquals(SERVICE_FEE_TOTAL_DIGITAL, footerRow.getCell("serviceFeeTotalDigital").getText());
        assertEquals(NET_TOTAL_DIGITAL, footerRow.getCell("netTotalDigital").getText());
    }
}
