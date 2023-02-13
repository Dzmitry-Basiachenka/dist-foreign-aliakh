package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLabel;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.SalFields;
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
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    private static final String FUND_POOL_ID = "15ad787b-da14-45d9-aed7-6b1eb119b4dd";
    private static final String FUND_POOL_NAME = "SAL fund pool";
    private static final String SELECTION_CRITERIA = "<b>Selection Criteria:</b>";

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
        SalFields salFields = new SalFields();
        salFields.setFundPoolId(FUND_POOL_ID);
        scenario.setSalFields(salFields);
        expect(controller.getScenarios()).andReturn(List.of(scenario)).once();
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
        assertThat(component, instanceOf(HorizontalLayout.class));
        verifyButtonsLayout((HorizontalLayout) component);
        component = scenariosWidget.getComponent(1);
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        component = layout.getComponent(0);
        assertThat(component, instanceOf(Grid.class));
        Grid grid = (Grid) component;
        assertEquals("scenarios-table", grid.getId());
        verifyGrid(grid, List.of(
            Triple.of("Name", -1.0, 1),
            Triple.of("Created Date", 100.0, -1),
            Triple.of("Status", 130.0, -1)
        ));
        assertNotNull(((Column) grid.getColumns().get(2)).getComparator(SortDirection.ASCENDING));
        component = layout.getComponent(1);
        assertThat(component, instanceOf(Panel.class));
        verifyWindow((Panel) component, null, 100, 100, Unit.PERCENTAGE);
        assertNull(((Panel) component).getContent());
    }

    @Test
    public void testInitMediator() {
        assertThat(scenariosWidget.initMediator(), instanceOf(SalScenariosMediator.class));
    }

    @Test
    public void testRefresh() {
        expect(controller.getScenarios()).andReturn(List.of(scenario)).once();
        expect(controller.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).times(2);
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).times(2);
        expect(controller.getFundPoolName(FUND_POOL_ID)).andReturn(FUND_POOL_NAME).times(2);
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
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).once();
        expect(controller.getFundPoolName(FUND_POOL_ID)).andReturn(FUND_POOL_NAME).once();
        replay(controller, usageController);
        scenariosWidget.selectScenario(scenario);
        assertEquals(scenario, grid.getSelectedItems().iterator().next());
    }

    @Test
    public void testRefreshSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, GRID_ID, grid);
        expect(grid.getSelectedItems()).andReturn(Set.of(scenario)).once();
        expect(controller.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).once();
        expect(controller.getFundPoolName(FUND_POOL_ID)).andReturn(FUND_POOL_NAME).once();
        replay(controller, usageController, grid);
        scenariosWidget.refreshSelectedScenario();
        verifyScenarioMetadataPanel();
        verify(controller, usageController, grid);
    }

    @Test
    public void testGetSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, GRID_ID, grid);
        expect(grid.getSelectedItems()).andReturn(Set.of(scenario)).once();
        replay(grid);
        assertEquals(scenario, scenariosWidget.getSelectedScenario());
        verify(grid);
    }

    @Test
    public void testGetNotSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, GRID_ID, grid);
        expect(grid.getSelectedItems()).andReturn(Set.of()).once();
        replay(grid);
        assertNull(scenariosWidget.getSelectedScenario());
        verify(grid);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertEquals("scenarios-buttons", layout.getId());
        assertEquals(7, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "View", false);
        verifyButton(layout.getComponent(1), "Edit Name", false);
        verifyButton(layout.getComponent(2), "Delete", false);
        verifyButton(layout.getComponent(3), "Submit for Approval", true);
        verifyButton(layout.getComponent(4), "Reject", true);
        verifyButton(layout.getComponent(5), "Approve", true);
        verifyButton(layout.getComponent(6), "Send to LM", true);
    }

    private void verifyButton(Component component, String caption, boolean isEnabled) {
        assertThat(component, instanceOf(Button.class));
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        assertEquals(caption.replaceAll(StringUtils.SPACE, "_"), button.getId());
        assertEquals(isEnabled, button.isEnabled());
        assertTrue(button.isDisableOnClick());
        assertEquals(2, button.getListeners(ClickEvent.class).size());
    }

    private void verifyScenarioMetadataPanel() {
        Panel panel = (Panel) ((HorizontalLayout) scenariosWidget.getComponent(1)).getComponent(1);
        assertEquals("scenarios-metadata", panel.getId());
        Component content = panel.getContent();
        assertThat(content, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) content;
        assertEquals(new MarginInfo(false, true, false, true), layout.getMargin());
        assertEquals(100, layout.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, layout.getWidthUnits());
        assertEquals(8, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), "<b>Owner: </b>user@copyright.com");
        verifyLabel(layout.getComponent(1),
            "<b>Gross Amt in USD: </b><span class='label-amount'>10,000.00</span>");
        verifyLabel(layout.getComponent(2),
            "<b>Service Fee Amt in USD: </b><span class='label-amount'>3,200.00</span>");
        verifyLabel(layout.getComponent(3),
            "<b>Net Amt in USD: </b><span class='label-amount'>6,800.00</span>");
        verifyLabel(layout.getComponent(4), "<b>Description: </b>Description");
        verifyLabel(layout.getComponent(5), SELECTION_CRITERIA);
        verifyLabel(layout.getComponent(6), "<b>Fund Pool Name: </b>SAL fund pool");
        VerticalLayout lastActionLayout = (VerticalLayout) layout.getComponent(7);
        assertEquals(5, lastActionLayout.getComponentCount());
        verifyLabel(lastActionLayout.getComponent(0), "<b>Type:</b> ADDED_USAGES");
        verifyLabel(lastActionLayout.getComponent(1), "<b>User:</b> user@copyright.com");
        verifyLabel(lastActionLayout.getComponent(2), "<b>Date:</b> 12/24/2016 12:00 AM");
        verifyLabel(lastActionLayout.getComponent(3), "<b>Reason:</b> ");
        assertThat(lastActionLayout.getComponent(4), instanceOf(Button.class));
        assertEquals("View All Actions", lastActionLayout.getComponent(4).getCaption());
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
