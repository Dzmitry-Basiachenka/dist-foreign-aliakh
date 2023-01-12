package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFooterItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLabel;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.ValueProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Verifies {@link AclScenarioDrillDownTitlesWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/24/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AclScenarioDrillDownTitlesWindow.class, Windows.class})
public class AclScenarioDrillDownTitlesWindowTest {

    private static final String SCENARIO_UID = "1236249b-0063-4f7e-888a-3b1051d7a898";
    private static final Long RH_ACCOUNT_NUMBER = 1000010022L;
    private static final String RH_NAME = "Yale University Press";
    private static final Long WR_WRK_INST = 127778306L;
    private static final String SYSTEM_TITLE = "Adaptations";
    private static final Integer AGG_LIC_CLASS_ID = 1;
    private static final String AGG_LIC_CLASS_NAME = "Food and Tobacco";
    private static final String STYLE_ALIGN_RIGHT = "v-align-right";

    private AclScenarioDrillDownTitlesWindow window;
    private IAclScenarioController controller;

    @Before
    public void setUp() {
        controller = createMock(IAclScenarioController.class);
        RightsholderResultsFilter filter = buildRightsholderResultsFilter(AGG_LIC_CLASS_ID, AGG_LIC_CLASS_NAME);
        expect(controller.getRightsholderTitleResults(filter)).andReturn(buildAclRightsholderTotalsHolderDtos()).once();
        replay(controller);
        window = new AclScenarioDrillDownTitlesWindow(controller, filter);
        verify(controller);
        reset(controller);
    }

    @Test
    public void testStructure() {
        assertEquals("Results by Rightsholder: Title", window.getCaption());
        assertEquals(1280, window.getWidth(), 0);
        assertEquals(Sizeable.Unit.PIXELS, window.getWidthUnits());
        assertEquals(75, window.getHeight(), 0);
        assertEquals(Sizeable.Unit.PERCENTAGE, window.getHeightUnits());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(4, content.getComponentCount());
        verifyMetaInfoLayout(content.getComponent(0));
        verifySearchWidget(content.getComponent(1));
        Component component = content.getComponent(2);
        assertThat(component, instanceOf(Grid.class));
        verifyGrid((Grid) component, List.of(
            Triple.of("Wr Wrk Inst", 110.0, -1),
            Triple.of("System Title", 400.0, -1),
            Triple.of("Print Gross Amt", -1.0, 1),
            Triple.of("Print Net Amt", -1.0, 1),
            Triple.of("Digital Gross Amt", -1.0, 1),
            Triple.of("Digital Net Amt", -1.0, 1),
            Triple.of("Total Gross Amt", -1.0, 1),
            Triple.of("Total Net Amt", -1.0, 1)
        ));
        verifyButton(content.getComponent(3), "Close", true);
        assertEquals("acl-scenario-drill-down-titles-window", window.getStyleName());
        assertEquals("acl-scenario-drill-down-titles-window", window.getId());
    }

    @Test
    public void testPerformSearch() {
        Grid grid = createMock(Grid.class);
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(window, searchWidget);
        Whitebox.setInternalState(window, grid);
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        grid.recalculateColumnWidths();
        expectLastCall().once();
        replay(searchWidget, grid);
        window.performSearch();
        verify(searchWidget, grid);
    }

    @Test
    public void testGridValues() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        Object[][] expectedCells = {
            {WR_WRK_INST.toString(), SYSTEM_TITLE, "1,500.00", "8,300.00", "1,000.00", "2,000.00", "2,500.00",
                "10,300.00"}
        };
        Grid grid = (Grid) content.getComponent(2);
        verifyGridItems(grid, buildAclRightsholderTotalsHolderDtos(), expectedCells);
        Object[][] expectedFooterColumns = {
            {"grossTotalPrint", "1,500.00", STYLE_ALIGN_RIGHT},
            {"netTotalPrint", "8,300.00", STYLE_ALIGN_RIGHT},
            {"grossTotalDigital", "1,000.00", STYLE_ALIGN_RIGHT},
            {"netTotalDigital", "2,000.00", STYLE_ALIGN_RIGHT},
            {"grossTotal", "2,500.00", STYLE_ALIGN_RIGHT},
            {"netTotal", "10,300.00", STYLE_ALIGN_RIGHT},

        };
        verifyFooterItems(grid, expectedFooterColumns);
    }

    @Test
    public void testWrWrkInstCellClickToDrillDownAggLcClassesWindow() throws Exception {
        RightsholderResultsFilter filter = buildRightsholderResultsFilter(null, null);
        expect(controller.getRightsholderTitleResults(filter)).andReturn(buildAclRightsholderTotalsHolderDtos()).once();
        AclScenarioDrillDownAggLcClassesWindow mockWindow = createMock(AclScenarioDrillDownAggLcClassesWindow.class);
        expectNew(AclScenarioDrillDownAggLcClassesWindow.class, eq(controller), eq(filter)).andReturn(mockWindow)
            .once();
        mockStatic(Windows.class);
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(Windows.class, AclScenarioDrillDownAggLcClassesWindow.class, controller);
        window = new AclScenarioDrillDownTitlesWindow(controller, filter);
        Grid grid = Whitebox.getInternalState(window, "grid");
        Grid.Column column = (Grid.Column) grid.getColumns().get(0);
        ValueProvider<AclRightsholderTotalsHolder, Button> provider = column.getValueProvider();
        Button button = provider.apply(buildAclRightsholderTotalsHolderDtos().get(0));
        button.click();
        verify(Windows.class, AclScenarioDrillDownAggLcClassesWindow.class, controller);
    }

    @Test
    public void testWrWrkInstCellClickToDrillDownUsageDetailsWindow() throws Exception {
        RightsholderResultsFilter filter = buildRightsholderResultsFilter(AGG_LIC_CLASS_ID, AGG_LIC_CLASS_NAME);
        AclScenarioDrillDownUsageDetailsWindow mockWindow = createMock(AclScenarioDrillDownUsageDetailsWindow.class);
        expectNew(AclScenarioDrillDownUsageDetailsWindow.class, eq(controller), eq(filter)).andReturn(mockWindow)
            .once();
        mockStatic(Windows.class);
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(Windows.class, AclScenarioDrillDownUsageDetailsWindow.class, controller);
        Grid grid = Whitebox.getInternalState(window, "grid");
        Grid.Column column = (Grid.Column) grid.getColumns().get(0);
        ValueProvider<AclRightsholderTotalsHolder, Button> provider = column.getValueProvider();
        Button button = provider.apply(buildAclRightsholderTotalsHolderDtos().get(0));
        button.click();
        verify(Windows.class, AclScenarioDrillDownUsageDetailsWindow.class, controller);
    }

    private RightsholderResultsFilter buildRightsholderResultsFilter(Integer aggLicClassId, String aggLicClassName) {
        RightsholderResultsFilter filter = new RightsholderResultsFilter();
        filter.setScenarioId(SCENARIO_UID);
        filter.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        filter.setRhName(RH_NAME);
        filter.setWrWrkInst(WR_WRK_INST);
        filter.setSystemTitle(SYSTEM_TITLE);
        filter.setAggregateLicenseeClassId(aggLicClassId);
        filter.setAggregateLicenseeClassName(aggLicClassName);
        return filter;
    }

    private List<AclRightsholderTotalsHolderDto> buildAclRightsholderTotalsHolderDtos() {
        AclRightsholderTotalsHolderDto holder = new AclRightsholderTotalsHolderDto();
        holder.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        holder.getRightsholder().setName(RH_NAME);
        holder.setWrWrkInst(WR_WRK_INST);
        holder.setSystemTitle(SYSTEM_TITLE);
        holder.setGrossTotalPrint(new BigDecimal("1500.0000000001"));
        holder.setNetTotalPrint(new BigDecimal("8300.0000000001"));
        holder.setGrossTotalDigital(new BigDecimal("1000.0000000001"));
        holder.setNetTotalDigital(new BigDecimal("2000.0000000001"));
        holder.setGrossTotal(new BigDecimal("2500.0000000002"));
        holder.setNetTotal(new BigDecimal("10300.0000000002"));
        return List.of(holder);
    }

    private void verifyMetaInfoLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(4, verticalLayout.getComponentCount());
        String[][] expectedMetaInfoCaptions = {
            {"RH Account #:", "1000010022"},
            {"RH Name:", RH_NAME},
            {"Agg LC Id:", AGG_LIC_CLASS_ID.toString()},
            {"Agg LC Name:", AGG_LIC_CLASS_NAME}
        };
        verifyLabelLayout(verticalLayout, expectedMetaInfoCaptions, verticalLayout.getComponentCount());
    }

    private void verifyLabelLayout(VerticalLayout verticalLayout, String[][] expectedCaptions, int componentCount) {
        IntStream.range(0, componentCount).forEach(i -> {
            Component horizontalComponent = verticalLayout.getComponent(i);
            assertThat(horizontalComponent, instanceOf(HorizontalLayout.class));
            HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(i);
            assertEquals(2, horizontalLayout.getComponentCount());
            verifyLabel(horizontalLayout.getComponent(0), expectedCaptions[i][0], ContentMode.TEXT, 90);
            verifyLabel(horizontalLayout.getComponent(1), expectedCaptions[i][1], ContentMode.TEXT, 100.0f,
                Unit.PERCENTAGE);
        });
    }

    private void verifySearchWidget(Component component) {
        assertThat(component, instanceOf(SearchWidget.class));
        SearchWidget searchWidget = (SearchWidget) component;
        assertEquals("Enter Wr Wrk Inst or System Title",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
    }
}
