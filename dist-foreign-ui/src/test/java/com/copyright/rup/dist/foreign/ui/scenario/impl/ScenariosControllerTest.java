package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.scenario.api.IActionHandler;
import com.copyright.rup.dist.foreign.ui.scenario.api.IReconcileRightsholdersController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosWidget;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.ConfirmActionDialogWindow;
import com.copyright.rup.vaadin.ui.ConfirmDialogWindow;
import com.copyright.rup.vaadin.ui.Windows;

import com.google.common.collect.Sets;
import com.vaadin.ui.Window;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.Set;

/**
 * Verifies {@link ScenariosController}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, SecurityUtils.class})
public class ScenariosControllerTest {

    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private static final String SCENARIO_NAME = "Scenario name";
    private ScenariosController scenariosController;
    private IScenarioService scenarioService;
    private ScenarioController scenarioController;
    private Scenario scenario;
    private IScenariosWidget scenariosWidget;
    private IScenarioWidget scenarioWidget;

    @Before
    public void setUp() {
        scenarioService = createMock(IScenarioService.class);
        scenariosController = new ScenariosController();
        buildScenario();
        scenarioController = createMock(ScenarioController.class);
        Whitebox.setInternalState(scenariosController, "scenarioController", scenarioController);
        scenariosWidget = createMock(IScenariosWidget.class);
        scenarioWidget = new ScenarioWidget();
        Whitebox.setInternalState(scenariosController, "widget", scenariosWidget);
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.getUserName()).andReturn("user@copyright.com").anyTimes();
        replay(SecurityUtils.class);
        scenariosController.initActionHandlers();
        Whitebox.setInternalState(scenariosController, "scenarioService", scenarioService);
        verify(SecurityUtils.class);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(scenariosController.instantiateWidget());
    }

    @Test
    public void testGetScenarios() {
        expect(scenarioService.getScenarios()).andReturn(Collections.emptyList()).once();
        replay(scenarioService);
        assertEquals(Collections.emptyList(), scenariosController.getScenarios());
        verify(scenarioService);
    }

    @Test
    public void testOnDeleteButtonClicked() {
        mockStatic(Windows.class);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(Windows.showConfirmDialog(
            eq("Are you sure you want to delete <i><b>'" + SCENARIO_NAME + "'</b></i> scenario?"),
            anyObject(ConfirmDialogWindow.IListener.class))).andReturn(null).once();
        replay(scenariosWidget, Windows.class);
        scenariosController.onDeleteButtonClicked();
        verify(scenariosWidget, Windows.class);
    }

    @Test
    public void testOnViewButtonClicked() {
        mockStatic(Windows.class);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        scenarioController.setScenario(scenario);
        expectLastCall().once();
        expect(scenarioController.initWidget()).andReturn(scenarioWidget).once();
        Windows.showModalWindow((Window) scenarioWidget);
        expectLastCall().once();
        replay(scenariosWidget, scenarioController, Windows.class);
        scenariosController.onViewButtonClicked();
        verify(scenariosWidget, scenarioController, Windows.class);
    }

    @Test
    public void testOnReconcileRightsholdersButtonClickedNoDiscrepancies() {
        mockStatic(Windows.class);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(scenarioService.getRightsholderDiscrepancies(scenario)).andReturn(Collections.emptySet());
        expect(Windows.showConfirmDialog(eq("There are no rightsholders updates for scenario " +
                "<i><b>Scenario name</b></i>. Do you want to update service fee?"),
            anyObject(ConfirmDialogWindow.IListener.class))).andReturn(null).once();
        scenariosWidget.refreshSelectedScenario();
        expectLastCall().once();
        replay(scenariosWidget, scenarioService, Windows.class);
        scenariosController.onReconcileRightsholdersButtonClicked();
        verify(scenariosWidget, scenarioService, Windows.class);
    }

    @Test
    public void testOnReconcileRightsholdersButtonClickedWithDiscrepancies() {
        mockStatic(Windows.class);
        IReconcileRightsholdersController reconcileRightsholdersController =
            createMock(IReconcileRightsholdersController.class);
        Whitebox.setInternalState(scenariosController, "reconcileRightsholdersController",
            reconcileRightsholdersController);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        Set<RightsholderDiscrepancy> discrepancies = Sets.newHashSet(new RightsholderDiscrepancy());
        expect(scenarioService.getRightsholderDiscrepancies(scenario)).andReturn(discrepancies);
        Windows.showModalWindow(anyObject(RightsholderDiscrepanciesWindow.class));
        expectLastCall().once();
        reconcileRightsholdersController.setDiscrepancies(discrepancies);
        expectLastCall().once();
        reconcileRightsholdersController.setScenario(scenario);
        expectLastCall().once();
        scenariosWidget.refreshSelectedScenario();
        expectLastCall().once();
        expect(reconcileRightsholdersController.getDiscrepancies()).andReturn(discrepancies).once();
        replay(scenariosWidget, scenarioService, reconcileRightsholdersController, Windows.class);
        scenariosController.onReconcileRightsholdersButtonClicked();
        verify(scenariosWidget, scenarioService, reconcileRightsholdersController, Windows.class);
    }

    @Test
    public void testHandleAction() throws Exception {
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        scenarioController.setScenario(scenario);
        expectLastCall().once();
        expect(scenarioController.isScenarioEmpty()).andReturn(false).once();
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(ConfirmActionDialogWindow.class));
        expectLastCall().once();
        replay(Windows.class, scenarioController, scenariosWidget);
        scenariosController.handleAction(ScenarioActionTypeEnum.SUBMITTED);
        verify(Windows.class, scenarioController, scenariosWidget);
    }

    @Test
    public void testHandleActionApproved() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        scenarioController.setScenario(scenario);
        expectLastCall().once();
        expect(scenarioController.isScenarioEmpty()).andReturn(false).once();
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(ConfirmActionDialogWindow.class));
        expectLastCall().once();
        replay(Windows.class, scenarioController, scenariosWidget);
        scenariosController.handleAction(ScenarioActionTypeEnum.APPROVED);
        verify(Windows.class, scenarioController, scenariosWidget);
    }

    @Test
    public void testHandleActionRejected() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        scenarioController.setScenario(scenario);
        expectLastCall().once();
        expect(scenarioController.isScenarioEmpty()).andReturn(false).once();
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(ConfirmActionDialogWindow.class));
        expectLastCall().once();
        replay(Windows.class, scenarioController, scenariosWidget);
        scenariosController.handleAction(ScenarioActionTypeEnum.REJECTED);
        verify(Windows.class, scenarioController, scenariosWidget);
    }

    @Test
    public void testApplyScenarioAction() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        scenariosWidget.refresh();
        expectLastCall().once();
        IActionHandler handler = createMock(IActionHandler.class);
        handler.handleAction(scenario, "reason");
        expectLastCall().once();
        replay(scenariosWidget, handler);
        scenariosController.applyScenarioAction(handler, "reason");
        verify(scenariosWidget, handler);
    }

    @Test
    public void testHandleActionNull() {
        mockStatic(Windows.class);
        replay(Windows.class);
        scenariosController.handleAction(null);
        verify(Windows.class);
    }

    @Test
    public void testSendToLm() {
        mockStatic(Windows.class);
        scenario.setName("Scenario");
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(Windows.showConfirmDialog(
            eq("Are you sure that you want to send scenario <i><b>Scenario</b></i> to Liability Manager?"),
            anyObject(ConfirmDialogWindow.IListener.class))).andReturn(new Window()).once();
        replay(Windows.class, scenariosWidget);
        scenariosController.sendToLm();
        verify(Windows.class, scenariosWidget);
    }

    @Test
    public void testSendScenarioToLm() {
        scenariosWidget.refresh();
        expectLastCall().once();
        scenarioService.sendToLm(scenario);
        expectLastCall().once();
        replay(scenariosWidget, scenarioService);
        scenariosController.sendToLM(scenario);
        verify(scenariosWidget, scenarioService);
    }

    private void buildScenario() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName(SCENARIO_NAME);
    }
}
