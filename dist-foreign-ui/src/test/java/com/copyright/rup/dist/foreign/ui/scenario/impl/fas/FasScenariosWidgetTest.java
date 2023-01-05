package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
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

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.impl.ScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Verifies {@link FasScenariosWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
public class FasScenariosWidgetTest {

    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private static final String GRID_ID = "scenarioGrid";
    private static final String SELECTION_CRITERIA = "<b>Selection Criteria:</b>";

    private FasScenariosWidget scenariosWidget;
    private IFasScenariosController controller;
    private Scenario scenario;

    @Before
    public void setUp() {
        controller = createMock(IFasScenariosController.class);
        scenariosWidget = new FasScenariosWidget(controller, new ScenarioHistoryController());
        scenariosWidget.setController(controller);
        scenario = buildScenario();
        expect(controller.getScenarios()).andReturn(List.of(scenario)).once();
        replay(controller);
        scenariosWidget.init();
        scenariosWidget.initMediator();
        verify(controller);
        reset(controller);
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
        verifyGrid((Grid) component);
        component = layout.getComponent(1);
        assertThat(component, instanceOf(Panel.class));
        verifyPanel((Panel) component);
    }

    @Test
    public void testGridValues() {
        Grid<?> grid = (Grid<?>) ((HorizontalLayout) scenariosWidget.getComponent(1)).getComponent(0);
        Object[][] expectedCells = {{"FAS Distribution 04/07/2022", "04/07/2022", "IN_PROGRESS"}};
        verifyGridItems(grid, List.of(scenario), expectedCells);
    }

    @Test
    public void testInitMediator() {
        assertThat(scenariosWidget.initMediator(), instanceOf(FasScenariosMediator.class));
    }

    @Test
    public void testRefresh() {
        expect(controller.getScenarios()).andReturn(List.of(scenario)).once();
        expect(controller.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).times(2);
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).times(2);
        replay(controller);
        scenariosWidget.refresh();
        verifyScenarioMetadataPanel();
        verify(controller);
    }

    @Test
    public void testSelectScenario() {
        Grid grid = Whitebox.getInternalState(scenariosWidget, GRID_ID);
        assertTrue(CollectionUtils.isEmpty(grid.getSelectedItems()));
        expect(controller.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(StringUtils.EMPTY).once();
        replay(controller);
        scenariosWidget.selectScenario(scenario);
        assertEquals(scenario, grid.getSelectedItems().iterator().next());
        verify(controller);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRefreshSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, GRID_ID, grid);
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(scenario)).once();
        expect(controller.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).once();
        replay(controller, grid);
        scenariosWidget.refreshSelectedScenario();
        verifyScenarioMetadataPanel();
        verify(controller, grid);
    }

    @Test
    @SuppressWarnings("unchecked")
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
        verifyWindow(panel, null, 100, 100, Unit.PERCENTAGE);
        assertNull(panel.getContent());
    }

    private void verifyGrid(Grid grid) {
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        assertEquals("scenarios-table", grid.getId());
        UiTestHelper.verifyGrid(grid, Arrays.asList(
            Triple.of("Name", -1.0, 1),
            Triple.of("Created Date", 100.0, -1),
            Triple.of("Status", 130.0, -1)
        ));
        assertNotNull(((Column) grid.getColumns().get(2)).getComparator(SortDirection.ASCENDING));
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertEquals("scenarios-buttons", layout.getId());
        assertEquals(10, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "View", false);
        verifyButton(layout.getComponent(1), "Edit Name", false);
        verifyButton(layout.getComponent(2), "Delete", false);
        verifyButton(layout.getComponent(3), "Exclude Payees", true);
        verifyButton(layout.getComponent(4), "Reconcile Rightsholders", false);
        verifyButton(layout.getComponent(5), "Submit for Approval", false);
        verifyButton(layout.getComponent(6), "Reject", false);
        verifyButton(layout.getComponent(7), "Approve", false);
        verifyButton(layout.getComponent(8), "Send to LM", false);
        verifyButton(layout.getComponent(9), "Refresh Scenario", false);
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
        assertEquals(7, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), "<b>Owner: </b>User@copyright.com");
        verifyLabel(layout.getComponent(1),
            "<b>Gross Amt in USD: </b><span class='label-amount'>10,000.00</span>");
        verifyLabel(layout.getComponent(2),
            "<b>Service Fee Amt in USD: </b><span class='label-amount'>3,200.00</span>");
        verifyLabel(layout.getComponent(3),
            "<b>Net Amt in USD: </b><span class='label-amount'>6,800.00</span>");
        verifyLabel(layout.getComponent(4), "<b>Description: </b>Description");
        verifyLabel(layout.getComponent(5), SELECTION_CRITERIA);
        assertThat(layout.getComponent(6), instanceOf(VerticalLayout.class));
        VerticalLayout lastActionLayout = (VerticalLayout) layout.getComponent(6);
        assertEquals(5, lastActionLayout.getComponentCount());
        verifyLabel(lastActionLayout.getComponent(0), "<b>Type:</b> ADDED_USAGES");
        verifyLabel(lastActionLayout.getComponent(1), "<b>User:</b> user@copyright.com");
        verifyLabel(lastActionLayout.getComponent(2), "<b>Date:</b> 12/24/2016 12:00 AM");
        verifyLabel(lastActionLayout.getComponent(3), "<b>Reason:</b> ");
        assertThat(lastActionLayout.getComponent(4), instanceOf(Button.class));
        assertEquals("View All Actions", lastActionLayout.getComponent(4).getCaption());
    }

    private Scenario buildScenario() {
        Scenario fasScenario = new Scenario();
        fasScenario.setId(SCENARIO_ID);
        fasScenario.setName("FAS Distribution 04/07/2022");
        fasScenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        fasScenario.setDescription("Description");
        fasScenario.setNetTotal(new BigDecimal("6800.00"));
        fasScenario.setServiceFeeTotal(new BigDecimal("3200.00"));
        fasScenario.setGrossTotal(new BigDecimal("10000.00"));
        fasScenario.setCreateUser("User@copyright.com");
        fasScenario.setCreateDate(
            Date.from(LocalDate.of(2022, 4, 7).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        fasScenario.setAuditItem(buildScenarioAuditItem());
        return fasScenario;
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
