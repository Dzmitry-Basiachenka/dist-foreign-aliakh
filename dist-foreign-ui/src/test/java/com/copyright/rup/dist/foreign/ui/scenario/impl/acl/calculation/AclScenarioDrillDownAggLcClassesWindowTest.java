package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;


import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

/**
 * Verifies {@link AclScenarioDrillDownAggLcClassesWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/24/2022
 *
 * @author Mikita Maistrenka
 */
public class AclScenarioDrillDownAggLcClassesWindowTest {

    private static final String SCENARIO_UID = "986a6b68-a8c1-41b9-b7fb-4002cd700337";
    private static final Long RH_ACCOUNT_NUMBER = 1000014424L;
    private static final String RH_NAME = "Oxford University Press - Books (US & UK)";

    private AclScenarioDrillDownAggLcClassesWindow window;

    @Before
    public void setUp() {
        IAclScenarioController controller = createMock(IAclScenarioController.class);
        Grid<AclRightsholderTotalsHolderDto> grid = createMock(Grid.class);
        RightsholderResultsFilter filter = buildRightsholderResultsParameter();
        expect(controller.getRightsholderAggLcClassResults(filter))
            .andReturn(Collections.singletonList(buildAclRightsholderTotalsHolderDto()));
        replay(controller);
        window = new AclScenarioDrillDownAggLcClassesWindow(controller, filter);
        Whitebox.setInternalState(window, grid);
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
            {1, "Food and Tobacco", "1.00", "2.95", "3.70", "4.57", "5.77", "6.00"}
        };
        verifyGridItems((Grid) content.getComponent(0),
            Collections.singletonList(buildAclRightsholderTotalsHolderDto()), expectedCells);
    }

    private RightsholderResultsFilter buildRightsholderResultsParameter() {
        RightsholderResultsFilter filter = new RightsholderResultsFilter();
        filter.setScenarioId(SCENARIO_UID);
        filter.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        filter.setRhName(RH_NAME);
        return filter;
    }

    private AclRightsholderTotalsHolderDto buildAclRightsholderTotalsHolderDto() {
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(1);
        aggregateLicenseeClass.setDescription("Food and Tobacco");
        AclRightsholderTotalsHolderDto holder = new AclRightsholderTotalsHolderDto();
        holder.setAggregateLicenseeClass(aggregateLicenseeClass);
        holder.setGrossTotalPrint(new BigDecimal("1.001"));
        holder.setNetTotalPrint(new BigDecimal("2.950"));
        holder.setGrossTotalDigital(new BigDecimal("3.700"));
        holder.setNetTotalDigital(new BigDecimal("4.567"));
        holder.setGrossTotal(new BigDecimal("5.766"));
        holder.setNetTotal(new BigDecimal("6.000"));
        return holder;
    }
}
