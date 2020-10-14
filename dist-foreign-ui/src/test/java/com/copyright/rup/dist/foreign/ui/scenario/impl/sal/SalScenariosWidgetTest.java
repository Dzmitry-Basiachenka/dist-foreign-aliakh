package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.impl.ScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link SalScenariosWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/22/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalScenariosWidgetTest {

    private static final String GRID_ID = "scenarioGrid";
    private static final String SCENARIO_ID = "01abf4a3-d7a2-45cb-af79-e217dfe0ac1a";

    private SalScenariosWidget scenariosWidget;
    private ISalScenariosController controller;
    private ISalUsageController usageController;
    private Scenario scenario;

    @Before
    public void setUp() {
        controller = createMock(ISalScenariosController.class);
        usageController = createMock(ISalUsageController.class);
        scenariosWidget = new SalScenariosWidget(controller, new ScenarioHistoryController());
        scenariosWidget.setController(controller);
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setDescription("Description");
        scenario.setNetTotal(new BigDecimal("6800.00"));
        scenario.setServiceFeeTotal(new BigDecimal("3200.00"));
        scenario.setGrossTotal(new BigDecimal("10000.00"));
        scenario.setCreateUser("user@copyright.com");
        scenario.setAuditItem(buildScenarioAuditItem());
        expect(controller.getScenarios()).andReturn(Collections.singletonList(scenario)).once();
        replay(controller, usageController);
        scenariosWidget.init();
        scenariosWidget.initMediator();
        verify(controller, usageController);
        reset(controller, usageController);
    }

    @Test
    public void testComponentStructure() {
        assertEquals(2, scenariosWidget.getComponentCount());
        Component component = scenariosWidget.getComponent(0);
        assertTrue(component instanceof HorizontalLayout);
        verifyButtonsLayout((HorizontalLayout) component);
        component = scenariosWidget.getComponent(1);
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        component = layout.getComponent(0);
        assertTrue(component instanceof Grid);
        verifyGrid((Grid) component);
        component = layout.getComponent(1);
        assertTrue(component instanceof Panel);
        verifyPanel((Panel) component);
    }

    @Test
    public void testInitMediator() {
        assertTrue(scenariosWidget.initMediator() instanceof SalScenariosMediator);
    }

    @Test
    public void testRefresh() {
        expect(controller.getScenarios()).andReturn(Collections.singletonList(scenario)).once();
        expect(controller.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).times(2);
        replay(controller, usageController);
        scenariosWidget.refresh();
        verifyScenarioMetadataPanel();
        verify(controller, usageController);
    }

    @Test
    public void testSelectScenario() {
        Grid grid = Whitebox.getInternalState(scenariosWidget, GRID_ID);
        assertTrue(CollectionUtils.isEmpty(grid.getSelectedItems()));
        expect(controller.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(StringUtils.EMPTY).once();
        replay(controller, usageController);
        scenariosWidget.selectScenario(scenario);
        assertEquals(scenario, grid.getSelectedItems().iterator().next());
        // TODO assert the scenario
    }

    @Test
    public void testRefreshSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, GRID_ID, grid);
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(scenario)).once();
        expect(controller.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).once();
        replay(controller, usageController, grid);
        scenariosWidget.refreshSelectedScenario();
        verifyScenarioMetadataPanel();
        verify(controller, usageController, grid);
    }

    @Test
    public void testGetSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, GRID_ID, grid);
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(scenario)).once();
        replay(grid);
        assertEquals(scenario, scenariosWidget.getSelectedScenario());
        verify(grid);
    }

    @Test
    public void testGetNotSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, GRID_ID, grid);
        expect(grid.getSelectedItems()).andReturn(Collections.EMPTY_SET).once();
        replay(grid);
        assertNull(scenariosWidget.getSelectedScenario());
        verify(grid);
    }

    private void verifyPanel(Panel panel) {
        verifySize(panel);
        assertNull(panel.getContent());
    }

    private void verifyGrid(Grid grid) {
        verifySize(grid);
        assertEquals("scenarios-table", grid.getId());
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Name", "Create Date", "Status"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        Column createDateColumn = columns.get(2);
        assertNotNull(createDateColumn.getComparator(SortDirection.ASCENDING));
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertEquals("scenarios-buttons", layout.getId());
        assertEquals(1, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Delete");
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        assertEquals(caption.replaceAll(StringUtils.SPACE, "_"), button.getId());
        assertFalse(button.isEnabled());
        assertTrue(button.isDisableOnClick());
        assertEquals(2, button.getListeners(ClickEvent.class).size());
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private void verifyScenarioMetadataPanel() {
        Panel panel = (Panel) ((HorizontalLayout) scenariosWidget.getComponent(1)).getComponent(1);
        assertEquals("scenarios-metadata", panel.getId());
        Component content = panel.getContent();
        assertTrue(content instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) content;
        assertEquals(new MarginInfo(false, true, false, true), layout.getMargin());
        assertEquals(100, layout.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, layout.getWidthUnits());
    }

    private ScenarioAuditItem buildScenarioAuditItem() {
        ScenarioAuditItem scenarioAuditItem = new ScenarioAuditItem();
        scenarioAuditItem.setActionType(ScenarioActionTypeEnum.ADDED_USAGES);
        scenarioAuditItem.setActionReason(StringUtils.EMPTY);
        scenarioAuditItem.setCreateUser("user@copyright.com");
        scenarioAuditItem.setCreateDate(
            Date.from(LocalDate.of(2016, 12, 24).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return scenarioAuditItem;
    }
}
