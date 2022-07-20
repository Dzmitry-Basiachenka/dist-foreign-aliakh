package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
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
public class AclScenarioWidgetTest {

    private AclScenarioWidget scenarioWidget;
    private IAclScenarioController controller;

    @Before
    public void setUp() {
        controller = createMock(IAclScenarioController.class);
        scenarioWidget = new AclScenarioWidget(controller);
        scenarioWidget.setController(controller);
        AclScenarioDto scenario = buildAclScenarioDto();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getExportAclScenarioDetailsStreamSource()).andReturn(streamSource).once();
        expect(controller.getScenario()).andReturn(scenario).once();
        expect(controller.getAclScenarioWithAmountsAndLastAction()).andReturn(scenario).once();
        replay(controller, streamSource);
        scenarioWidget.init();
        verify(controller, streamSource);
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
        verifyButtonsLayout((HorizontalLayout) content.getComponent(2));
    }

    @Test
    public void testGetSearchValue() {
        SearchWidget searchWidget = new SearchWidget(controller);
        searchWidget.setSearchValue("search");
        Whitebox.setInternalState(scenarioWidget, searchWidget);
        assertEquals("search", scenarioWidget.getSearchValue());
    }

    private AclScenarioDto buildAclScenarioDto() {
        AclScenarioDto scenario = new AclScenarioDto();
        scenario.setId("24b0a5a9-6380-4519-91f2-779c98ed45cc");
        scenario.setName("Scenario name");
        scenario.setGrossTotalPrint(new BigDecimal("20000.00"));
        scenario.setServiceFeeTotalPrint(new BigDecimal("6400.00"));
        scenario.setNetTotalPrint(new BigDecimal("13600.00"));
        scenario.setGrossTotalDigital(new BigDecimal("1000.00"));
        scenario.setServiceFeeTotalDigital(new BigDecimal("600.00"));
        scenario.setNetTotalDigital(new BigDecimal("130.00"));
        return scenario;
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
        assertEquals("600.00", footerRow.getCell("serviceFeeTotalDigital").getText());
        assertEquals("130.00", footerRow.getCell("netTotalDigital").getText());
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        verifyButton(layout.getComponent(0), "Export Details", true);
        verifyButton(layout.getComponent(1), "Close", true);
    }
}
