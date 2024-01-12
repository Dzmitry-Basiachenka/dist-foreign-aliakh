package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySize;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.impl.ScenarioHistoryController;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout.Orientation;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
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
 * Verifies {@link FasScenariosWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/2017
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
public class FasScenariosWidgetTest {

    private static final String SCENARIO_ID = "526f0f5f-8fe9-4c9c-8f46-dea1236ba259";
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
        verify(controller);
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        assertEquals(2, scenariosWidget.getComponentCount());
        Component component = scenariosWidget.getComponentAt(0);
        assertThat(component, instanceOf(HorizontalLayout.class));
        verifyButtonsLayout((HorizontalLayout) component);
        component = scenariosWidget.getComponentAt(1);
        assertThat(component, instanceOf(SplitLayout.class));
        SplitLayout splitLayout = (SplitLayout) component;
        assertEquals(Orientation.HORIZONTAL, splitLayout.getOrientation());
        assertEquals("scenarios-tables-panel-split-layout", splitLayout.getClassName());
        component = splitLayout.getPrimaryComponent();
        assertThat(component, instanceOf(Grid.class));
        verifyGrid((Grid) component);
        verifyScroller(splitLayout.getSecondaryComponent());
    }

    @Test
    public void testInitMediator() {
        //TODO: {dbasiachenka} implement
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
        expect(grid.getSelectedItems()).andReturn(Set.of(scenario)).once();
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

    private void verifyScroller(Component component) {
        assertThat(component, instanceOf(Scroller.class));
        Scroller scroller = (Scroller) component;
        assertEquals("scenarios-metadata-panel-scroller", scroller.getClassName());
        assertEquals(ScrollDirection.VERTICAL, scroller.getScrollDirection());
        assertThat(scroller.getContent(), instanceOf(Section.class));
    }

    private void verifyGrid(Grid grid) {
        verifySize(grid, "100%", Unit.PERCENTAGE, "100%", Unit.PERCENTAGE);
        assertEquals("scenarios-table", grid.getId().orElseThrow());
        assertEquals("scenarios-table", grid.getClassName());
        UiTestHelper.verifyGrid(grid, List.of(
            Pair.of("Name", null),
            Pair.of("Created Date", "150px"),
            Pair.of("Status", "150px")
        ));
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertEquals("scenarios-buttons", layout.getId().orElseThrow());
        assertEquals("scenarios-buttons", layout.getClassName());
        assertEquals(10, layout.getComponentCount());
        UiTestHelper.verifyButtonsLayout(layout, true, "View", "Edit Name", "Delete", "Exclude Payees",
            "Reconcile Rightsholders", "Submit for Approval", "Reject", "Approve", "Send to LM", "Refresh Scenario");
    }

    private void verifyScenarioMetadataPanel() {
        Scroller scroller = (Scroller) ((SplitLayout) scenariosWidget.getComponentAt(1)).getSecondaryComponent();
        assertEquals("scenarios-metadata-panel-scroller", scroller.getClassName());
        Section section = (Section) scroller.getContent();
        assertEquals("scenarios-metadata", section.getId().orElseThrow());
        assertEquals("scenarios-metadata", section.getClassName());
        Component content = section.getComponentAt(0);
        assertThat(content, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) content;
        assertFalse(layout.isMargin());
        assertTrue(layout.isSpacing());
        assertEquals(7, layout.getComponentCount());
        verifyDiv(layout.getComponentAt(0), "<b>Owner: </b>User@copyright.com");
        verifyDiv(layout.getComponentAt(1),
            "<b>Gross Amt in USD: </b><span class='label-amount'>10,000.00</span>");
        verifyDiv(layout.getComponentAt(2),
            "<b>Service Fee Amt in USD: </b><span class='label-amount'>3,200.00</span>");
        verifyDiv(layout.getComponentAt(3),
            "<b>Net Amt in USD: </b><span class='label-amount'>6,800.00</span>");
        verifyDiv(layout.getComponentAt(4), "<b>Description: </b>Description");
        verifyDiv(layout.getComponentAt(5), SELECTION_CRITERIA);
        assertThat(layout.getComponentAt(6), instanceOf(VerticalLayout.class));
        VerticalLayout lastActionLayout = (VerticalLayout) layout.getComponentAt(6);
        assertEquals(3, lastActionLayout.getComponentCount());
        assertEquals("<b>Last Action:</b>", ((Html) lastActionLayout.getComponentAt(0)).getInnerHtml());
        VerticalLayout actionMetadataLayout = (VerticalLayout) lastActionLayout.getComponentAt(1);
        assertEquals(4, actionMetadataLayout.getComponentCount());
        verifyDiv(actionMetadataLayout.getComponentAt(0), "<b>Type:</b> ADDED_USAGES");
        verifyDiv(actionMetadataLayout.getComponentAt(1), "<b>User:</b> user@copyright.com");
        verifyDiv(actionMetadataLayout.getComponentAt(2), "<b>Date:</b> 12/24/2016 12:00 AM");
        verifyDiv(actionMetadataLayout.getComponentAt(3), "<b>Reason:</b> ");
        Component viewAllActionsButton = lastActionLayout.getComponentAt(2);
        assertThat(viewAllActionsButton, instanceOf(Button.class));
        assertEquals("View All Actions", ((Button) viewAllActionsButton).getText());
    }

    private Scenario buildScenario() {
        var fasScenario = new Scenario();
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
        var scenarioAuditItem = new ScenarioAuditItem();
        scenarioAuditItem.setActionType(ScenarioActionTypeEnum.ADDED_USAGES);
        scenarioAuditItem.setActionReason(StringUtils.EMPTY);
        scenarioAuditItem.setCreateUser("user@copyright.com");
        scenarioAuditItem.setCreateDate(
            Date.from(LocalDate.of(2016, 12, 24).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return scenarioAuditItem;
    }

    private void verifyDiv(Component component, String expectedValue) {
        assertNotNull(component);
        assertEquals(Div.class, component.getClass());
        assertEquals(expectedValue, component.getElement().getProperty("innerHTML"));
    }
}
