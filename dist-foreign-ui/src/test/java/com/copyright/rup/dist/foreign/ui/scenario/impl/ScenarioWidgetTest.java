package com.copyright.rup.dist.foreign.ui.scenario.impl;

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

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;

/**
 * Verifies {@link ScenarioWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/07/17
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ForeignSecurityUtils.class})
public class ScenarioWidgetTest {

    private ScenarioWidget scenarioWidget;
    private ScenarioController controller;
    private ScenarioMediator mediator;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(ScenarioController.class);
        mediator = createMock(ScenarioMediator.class);
        scenarioWidget = new ScenarioWidget();
        scenarioWidget.setController(controller);
        Whitebox.setInternalState(scenarioWidget, "mediator", mediator);
        Scenario scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setName("Scenario name");
        scenario.setGrossTotal(new BigDecimal("20000.00"));
        scenario.setServiceFeeTotal(new BigDecimal("6400.00"));
        scenario.setNetTotal(new BigDecimal("13600.00"));
        expect(controller.getScenario()).andReturn(scenario).once();
        expect(controller.getExportScenarioUsagesStreamSource()).andReturn(createMock(IStreamSource.class)).once();
        expect(controller.getScenarioWithAmounts()).andReturn(scenario).once();
        replay(controller, ForeignSecurityUtils.class, mediator);
        scenarioWidget.init();
        verify(controller, ForeignSecurityUtils.class);
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
        verifyTable(content.getComponent(1));
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
        mediator.onScenarioUpdated(false, ScenarioStatusEnum.IN_PROGRESS);
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
        assertEquals(60, searchWidget.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, searchWidget.getWidthUnits());
        assertEquals(Alignment.MIDDLE_CENTER, horizontalLayout.getComponentAlignment(searchWidget));
        assertTrue(horizontalLayout.isSpacing());
        verifySize(horizontalLayout);
    }

    private void verifyTable(Component component) {
        assertEquals(RightsholderTotalsHolderTable.class, component.getClass());
        RightsholderTotalsHolderTable table = (RightsholderTotalsHolderTable) component;
        assertEquals("<span class='label-amount'>20,000.00</span>", table.getColumnFooter("grossTotal"));
        assertEquals("<span class='label-amount'>6,400.00</span>", table.getColumnFooter("serviceFeeTotal"));
        assertEquals("<span class='label-amount'>13,600.00</span>", table.getColumnFooter("netTotal"));
    }

    private void verifyEmptyScenarioLabel(Component component) {
        assertEquals(Label.class, component.getClass());
        assertEquals("Scenario is empty due to all usages were excluded", ((Label) component).getValue());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(3, horizontalLayout.getComponentCount());
        Button excludeButton = (Button) horizontalLayout.getComponent(0);
        assertEquals("Exclude Details", excludeButton.getCaption());
        Button exportButton = (Button) horizontalLayout.getComponent(1);
        assertEquals("Export", exportButton.getCaption());
        assertEquals("Export", exportButton.getId());
        Button closeButton = (Button) horizontalLayout.getComponent(2);
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
}
