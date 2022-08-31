package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;


import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;

import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclScenarioDrillDownAggLcClassesWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/24/2022
 *
 * @author Mikita Maistrenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AclScenarioDrillDownAggLcClassesWindow.class, Windows.class})
public class AclScenarioDrillDownAggLcClassesWindowTest {

    private static final String SCENARIO_UID = "986a6b68-a8c1-41b9-b7fb-4002cd700337";
    private static final Long RH_ACCOUNT_NUMBER = 1000010022L;
    private static final String RH_NAME = "Yale University Press";
    private static final Long WR_WRK_INST = 127778306L;
    private static final String SYSTEM_TITLE = "Adaptations";
    private static final Integer AGG_LIC_CLASS_ID = 1;
    private static final String AGG_LIC_CLASS_NAME = "Food and Tobacco";

    private AclScenarioDrillDownAggLcClassesWindow window;
    private IAclScenarioController controller;

    @Before
    public void setUp() {
        controller = createMock(IAclScenarioController.class);
        RightsholderResultsFilter filter = buildRightsholderResultsFilter(WR_WRK_INST, SYSTEM_TITLE);
        expect(controller.getRightsholderAggLcClassResults(filter)).andReturn(buildAclRightsholderTotalsHolderDtos())
            .once();
        replay(controller);
        window = new AclScenarioDrillDownAggLcClassesWindow(controller, filter);
        verify(controller);
        reset(controller);
    }

    @Test
    public void testStructure() {
        verifyWindow(window, "Results by Rightsholder: Aggregate Licensee Class", 1280, 600, Sizeable.Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(2, content.getComponentCount());
        Component component = content.getComponent(0);
        assertTrue(component instanceof Grid);
        verifyGrid((Grid) component, Arrays.asList(
            Triple.of("Agg LC ID", 110.0, -1),
            Triple.of("Agg LC Name", 256.0, -1),
            Triple.of("Print Gross Amt", 150.0, -1),
            Triple.of("Print Net Amt", 150.0, -1),
            Triple.of("Digital Gross Amt", 150.0, -1),
            Triple.of("Digital Net Amt", 150.0, -1),
            Triple.of("Total Gross Amt", 150.0, -1),
            Triple.of("Total Net Amt", 140.0, -1)
        ));
        verifyButton(content.getComponent(1), "Close", true);
        assertEquals("acl-scenario-drill-down-aggregate-licensee-class-window", window.getStyleName());
        assertEquals("acl-scenario-drill-down-aggregate-licensee-class-window", window.getId());
    }

    @Test
    public void testGridValues() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        Object[][] expectedCells = {
            {AGG_LIC_CLASS_ID, AGG_LIC_CLASS_NAME, "1.00", "2.95", "3.70", "4.57", "5.77", "6.00"}
        };
        verifyGridItems((Grid) content.getComponent(0), buildAclRightsholderTotalsHolderDtos(), expectedCells);
    }

    @Test
    public void testAggLcClassCellClickToDrillDownTitlesWindow() throws Exception {
        RightsholderResultsFilter filter = buildRightsholderResultsFilter(null, null);
        expect(controller.getRightsholderAggLcClassResults(filter)).andReturn(buildAclRightsholderTotalsHolderDtos())
            .once();
        AclScenarioDrillDownTitlesWindow mockWindow = createMock(AclScenarioDrillDownTitlesWindow.class);
        expectNew(AclScenarioDrillDownTitlesWindow.class, eq(controller), eq(filter)).andReturn(mockWindow).once();
        mockStatic(Windows.class);
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(Windows.class, AclScenarioDrillDownTitlesWindow.class, controller);
        window = new AclScenarioDrillDownAggLcClassesWindow(controller, filter);
        Grid grid = Whitebox.getInternalState(window, "grid");
        Grid.Column column = (Grid.Column) grid.getColumns().get(1);
        ValueProvider<AclRightsholderTotalsHolder, Button> provider = column.getValueProvider();
        Button button = provider.apply(buildAclRightsholderTotalsHolderDtos().get(0));
        button.click();
        verify(Windows.class, AclScenarioDrillDownTitlesWindow.class, controller);
    }

    @Test
    public void testAggLcClassCellClickToDrillDownUsageDetailsWindow() throws Exception {
        RightsholderResultsFilter filter = buildRightsholderResultsFilter(WR_WRK_INST, SYSTEM_TITLE);
        AclScenarioDrillDownUsageDetailsWindow mockWindow = createMock(AclScenarioDrillDownUsageDetailsWindow.class);
        expectNew(AclScenarioDrillDownUsageDetailsWindow.class, eq(controller), eq(filter)).andReturn(mockWindow)
            .once();
        mockStatic(Windows.class);
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(Windows.class, AclScenarioDrillDownUsageDetailsWindow.class, controller);
        Grid grid = Whitebox.getInternalState(window, "grid");
        Grid.Column column = (Grid.Column) grid.getColumns().get(1);
        ValueProvider<AclRightsholderTotalsHolder, Button> provider = column.getValueProvider();
        Button button = provider.apply(buildAclRightsholderTotalsHolderDtos().get(0));
        button.click();
        verify(Windows.class, AclScenarioDrillDownUsageDetailsWindow.class, controller);
    }

    private RightsholderResultsFilter buildRightsholderResultsFilter(Long wrWrkInst, String systemTitle) {
        RightsholderResultsFilter filter = new RightsholderResultsFilter();
        filter.setScenarioId(SCENARIO_UID);
        filter.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        filter.setRhName(RH_NAME);
        filter.setWrWrkInst(wrWrkInst);
        filter.setSystemTitle(systemTitle);
        filter.setAggregateLicenseeClassId(AGG_LIC_CLASS_ID);
        filter.setAggregateLicenseeClassName(AGG_LIC_CLASS_NAME);
        return filter;
    }

    private List<AclRightsholderTotalsHolderDto> buildAclRightsholderTotalsHolderDtos() {
        AclRightsholderTotalsHolderDto holder = new AclRightsholderTotalsHolderDto();
        holder.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        holder.getRightsholder().setName(RH_NAME);
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(AGG_LIC_CLASS_ID);
        aggregateLicenseeClass.setDescription(AGG_LIC_CLASS_NAME);
        holder.setAggregateLicenseeClass(aggregateLicenseeClass);
        holder.setGrossTotalPrint(new BigDecimal("1.001"));
        holder.setNetTotalPrint(new BigDecimal("2.950"));
        holder.setGrossTotalDigital(new BigDecimal("3.700"));
        holder.setNetTotalDigital(new BigDecimal("4.567"));
        holder.setGrossTotal(new BigDecimal("5.766"));
        holder.setNetTotal(new BigDecimal("6.000"));
        return Collections.singletonList(holder);
    }
}
