package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

/**
 * Verifies {@link AclScenarioDrillDownTitlesWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/24/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclScenarioDrillDownTitlesWindowTest {

    private static final String SCENARIO_UID = "1236249b-0063-4f7e-888a-3b1051d7a898";
    private static final Long RH_ACCOUNT_NUMBER = 1000010022L;
    private static final String RH_NAME = "Yale University Press";

    private AclScenarioDrillDownTitlesWindow window;
    private Grid<AclRightsholderTotalsHolderDto> grid;

    @Before
    public void setUp() {
        IAclScenarioController controller = createMock(IAclScenarioController.class);
        grid = createMock(Grid.class);
        RightsholderResultsFilter rightsholderResultsFilter = buildRightsholderResultsFilter();
        expect(controller.getRightsholderTitleResults(rightsholderResultsFilter))
            .andReturn(Collections.singletonList(buildAclRightsholderTotalsHolderDto()));
        replay(controller);
        window = new AclScenarioDrillDownTitlesWindow(controller, rightsholderResultsFilter);
        Whitebox.setInternalState(window, grid);
        verify(controller);
        reset(controller);
    }

    @Test
    public void testStructure() {
        verifyWindow(window, "Results by Rightsholder: Title", 1280, 600, Sizeable.Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        verifySearchWidget(content.getComponent(0));
        Component component = content.getComponent(1);
        assertTrue(component instanceof Grid);
        verifyGrid((Grid) component, Arrays.asList(
            Triple.of("Wr Wrk Inst", 110.0, -1),
            Triple.of("System Title", 256.0, -1),
            Triple.of("Print Gross Amt", 150.0, -1),
            Triple.of("Print Net Amt", 150.0, -1),
            Triple.of("Digital Gross Amt", 150.0, -1),
            Triple.of("Digital Net Amt", 150.0, -1),
            Triple.of("Total Gross Amt", 150.0, -1),
            Triple.of("Total Net Amt", 140.0, -1)
        ));
        verifyButton(content.getComponent(2), "Close", true);
        assertEquals("acl-scenario-drill-down-titles-window", window.getStyleName());
        assertEquals("acl-scenario-drill-down-titles-window", window.getId());
    }

    @Test
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(window, searchWidget);
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
            {127778306L, "Adaptations", "2,000.00", "8,300.00", "1,000.00", "2,000.00", "3,000.00", "10,300.00"}
        };
        verifyGridItems((Grid) content.getComponent(1),
            Collections.singletonList(buildAclRightsholderTotalsHolderDto()), expectedCells);
    }

    private void verifySearchWidget(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(1, horizontalLayout.getComponentCount());
        SearchWidget searchWidget = (SearchWidget) horizontalLayout.getComponent(0);
        assertEquals(60, searchWidget.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, searchWidget.getWidthUnits());
        assertEquals(Alignment.MIDDLE_CENTER, horizontalLayout.getComponentAlignment(searchWidget));
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(100, horizontalLayout.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, horizontalLayout.getWidthUnits());
        assertEquals("Enter Wr Wrk Inst or System Title",
            ((TextField) searchWidget.getComponent(0)).getPlaceholder());
    }

    private RightsholderResultsFilter buildRightsholderResultsFilter() {
        RightsholderResultsFilter filter = new RightsholderResultsFilter();
        filter.setScenarioId(SCENARIO_UID);
        filter.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        filter.setRhName(RH_NAME);
        return filter;
    }

    private AclRightsholderTotalsHolderDto buildAclRightsholderTotalsHolderDto() {
        AclRightsholderTotalsHolderDto holder = new AclRightsholderTotalsHolderDto();
        holder.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        holder.getRightsholder().setName(RH_NAME);
        holder.setGrossTotalPrint(new BigDecimal("2000.0000000001"));
        holder.setNetTotalPrint(new BigDecimal("8300.0000000001"));
        holder.setGrossTotalDigital(new BigDecimal("1000.0000000001"));
        holder.setNetTotalDigital(new BigDecimal("2000.0000000001"));
        holder.setWrWrkInst(127778306L);
        holder.setSystemTitle("Adaptations");
        holder.setGrossTotal(new BigDecimal("3000.0000000002"));
        holder.setNetTotal(new BigDecimal("10300.0000000002"));
        return holder;
    }
}
