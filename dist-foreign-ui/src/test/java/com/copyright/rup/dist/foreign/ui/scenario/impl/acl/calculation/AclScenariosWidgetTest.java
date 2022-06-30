package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * Verifies {@link AclScenariosWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclScenariosWidgetTest {

    private static final String SCENARIO_UID = "29ca6de6-0496-49e8-8ff4-334ef1bab597";
    private static final String SELECTION_CRITERIA = "<b>Selection Criteria:</b>";

    private AclScenariosWidget scenariosWidget;
    private IAclScenariosController controller;
    private AclScenario scenario;

    @Before
    public void setUp() {
        controller = createMock(IAclScenariosController.class);
        scenario = buildAclScenario();
        scenariosWidget = new AclScenariosWidget(controller, createMock(IAclScenarioHistoryController.class));
        expect(controller.getScenarios()).andReturn(Collections.singletonList(scenario)).once();
        expect(controller.getAclScenarioWithAmountsAndLastAction(SCENARIO_UID)).andReturn(new AclScenarioDto()).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).once();
        replay(controller);
        scenariosWidget.init();
        verify(controller);
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        assertEquals(2, scenariosWidget.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) scenariosWidget.getComponent(0));
        Component component = scenariosWidget.getComponent(1);
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
    public void testRefresh() {
        expect(controller.getScenarios()).andReturn(Collections.singletonList(scenario)).once();
        expect(controller.getAclScenarioWithAmountsAndLastAction(SCENARIO_UID)).andReturn(new AclScenarioDto()).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).once();
        replay(controller);
        scenariosWidget.refresh();
        verify(controller);
    }

    @Test
    public void testSelectScenario() {
        // TODO {aliakh} implement
    }

    @Test
    public void testRefreshSelectedScenario() {
        // TODO {aliakh} implement
    }

    @Test
    public void testGetSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, "scenarioGrid", grid);
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(scenario)).once();
        replay(grid);
        assertEquals(scenario, scenariosWidget.getSelectedScenario());
        verify(grid);
    }

    @Test
    public void testGetNotSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, "scenarioGrid", grid);
        expect(grid.getSelectedItems()).andReturn(Collections.emptySet()).once();
        replay(grid);
        assertNull(scenariosWidget.getSelectedScenario());
        verify(grid);
    }

    private AclScenario buildAclScenario() {
        AclScenario aclScenario = new AclScenario();
        aclScenario.setId(SCENARIO_UID);
        aclScenario.setName("ACL Scenario name");
        aclScenario.setDescription("Description");
        aclScenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        aclScenario.setEditableFlag(false);
        aclScenario.setPeriodEndDate(202212);
        aclScenario.setLicenseType("ACL");
        aclScenario.setCreateDate(Date.from(LocalDate.of(2022, 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        aclScenario.setCreateUser("user@copyright.com");
        return aclScenario;
    }

    // TODO {aliakh} verify metadataPanel
    private void verifyPanel(Panel panel) {
        verifyWindow(panel, null, 100, 100, Unit.PERCENTAGE);
        assertNotNull(panel.getContent());
    }

    private void verifyGrid(Grid grid) {
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        assertEquals("acl-scenarios-table", grid.getId());
        UiTestHelper.verifyGrid(grid, Arrays.asList(
            Triple.of("Name", -1.0, 1),
            Triple.of("License Type", 110.0, -1),
            Triple.of("Period", 100.0, -1),
            Triple.of("Editable", 100.0, -1),
            Triple.of("Created Date", 120.0, -1),
            Triple.of("Status", 130.0, -1)
        ));
        assertNotNull(((Column) grid.getColumns().get(2)).getComparator(SortDirection.ASCENDING));
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertEquals("acl-scenario-buttons-layout", layout.getId());
        assertEquals(2, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Create", false, 1);
        verifyButton(layout.getComponent(1), "View", true, 2);
    }

    private void verifyButton(Component component, String caption, boolean isDisabled, int listenersCount) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        assertEquals(caption, button.getId());
        assertTrue(button.isEnabled());
        assertEquals(isDisabled, button.isDisableOnClick());
        assertEquals(listenersCount, button.getListeners(ClickEvent.class).size());
    }
}
