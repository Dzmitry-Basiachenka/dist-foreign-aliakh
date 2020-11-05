package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

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

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.impl.ScenarioHistoryController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
 * Verifies {@link NtsScenariosWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
public class NtsScenariosWidgetTest {

    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private static final String GRID_ID = "scenarioGrid";
    private static final String SELECTION_CRITERIA = "<b>Selection Criteria:</b>";

    private NtsScenariosWidget scenariosWidget;
    private INtsScenariosController controller;
    private Scenario scenario;

    @Before
    public void setUp() {
        controller = createMock(INtsScenariosController.class);
        scenariosWidget = new NtsScenariosWidget(controller, new ScenarioHistoryController());
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        NtsFields ntsFields = new NtsFields();
        ntsFields.setRhMinimumAmount(new BigDecimal("300.00"));
        ntsFields.setPreServiceFeeAmount(new BigDecimal("500.00"));
        ntsFields.setPostServiceFeeAmount(new BigDecimal("800.00"));
        ntsFields.setPreServiceFeeFundTotal(new BigDecimal("300.00"));
        ntsFields.setPreServiceFeeFundId("40f97da2-79f6-4917-b683-1cfa0fccd669");
        ntsFields.setPreServiceFeeFundName("test name");
        scenario.setNtsFields(ntsFields);
        scenario.setDescription("Description");
        scenario.setNetTotal(new BigDecimal("6800.00"));
        scenario.setServiceFeeTotal(new BigDecimal("3200.00"));
        scenario.setGrossTotal(new BigDecimal("10000.00"));
        scenario.setCreateUser("User@copyright.com");
        scenario.setAuditItem(buildScenarioAuditItem());
        expect(controller.getScenarios()).andReturn(Collections.singletonList(scenario)).once();
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
        assertTrue(scenariosWidget.initMediator() instanceof NtsScenariosMediator);
    }

    @Test
    public void testRefresh() {
        expect(controller.getScenarios()).andReturn(Collections.singletonList(scenario)).once();
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
        assertEquals(6, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "View");
        verifyButton(layout.getComponent(1), "Delete");
        verifyButton(layout.getComponent(2), "Submit for Approval");
        verifyButton(layout.getComponent(3), "Reject");
        verifyButton(layout.getComponent(4), "Approve");
        verifyButton(layout.getComponent(5), "Send to LM");
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
        assertEquals(11, layout.getComponentCount());
        verifyMetadataLabel(layout.getComponent(0), "<b>Owner: </b>User@copyright.com");
        verifyMetadataLabel(layout.getComponent(1),
            "<b>Gross Amt in USD: </b><span class='label-amount'>10,000.00</span>");
        verifyMetadataLabel(layout.getComponent(2),
            "<b>Service Fee Amt in USD: </b><span class='label-amount'>3,200.00</span>");
        verifyMetadataLabel(layout.getComponent(3),
            "<b>Net Amt in USD: </b><span class='label-amount'>6,800.00</span>");
        verifyMetadataLabel(layout.getComponent(4),
            "<b>RH Minimum Amt in USD: </b><span class='label-amount'>300.00</span>");
        verifyMetadataLabel(layout.getComponent(5),
            "<b>Pre-Service Fee Amount: </b><span class='label-amount'>500.00</span>");
        verifyMetadataLabel(layout.getComponent(6),
            "<b>Post-Service Fee Amount: </b><span class='label-amount'>800.00</span>");
        verifyMetadataLabel(layout.getComponent(7),
            "<b>Pre-Service Fee Fund: </b>test name (<span class='label-amount'>300.00</span>)");
        verifyMetadataLabel(layout.getComponent(8), "<b>Description: </b>Description");
        verifyMetadataLabel(layout.getComponent(9), SELECTION_CRITERIA);
        assertTrue(layout.getComponent(10) instanceof VerticalLayout);
        VerticalLayout lastActionLayout = (VerticalLayout) layout.getComponent(10);
        assertEquals(5, lastActionLayout.getComponentCount());
        verifyMetadataLabel(lastActionLayout.getComponent(0), "<b>Type:</b> ADDED_USAGES");
        verifyMetadataLabel(lastActionLayout.getComponent(1), "<b>User:</b> user@copyright.com");
        verifyMetadataLabel(lastActionLayout.getComponent(2), "<b>Date:</b> 12/24/2016 12:00 AM");
        verifyMetadataLabel(lastActionLayout.getComponent(3), "<b>Reason:</b> ");
        assertTrue(lastActionLayout.getComponent(4) instanceof Button);
        assertEquals("View All Actions", lastActionLayout.getComponent(4).getCaption());
    }

    private void verifyMetadataLabel(Component component, String expectedValue) {
        assertTrue(component instanceof Label);
        assertEquals(expectedValue, ((Label) component).getValue());
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
