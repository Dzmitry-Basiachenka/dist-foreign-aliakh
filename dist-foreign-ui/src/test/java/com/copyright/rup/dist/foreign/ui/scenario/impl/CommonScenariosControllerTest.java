package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.IActionHandler;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenariosWidget;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.window.ConfirmActionDialogWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.Validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link CommonScenariosController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 01/25/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, SecurityUtils.class})
public class CommonScenariosControllerTest {

    private static final String SCENARIO_ID = "2fe241c4-7625-437b-9c8f-ff7ba4d3cb07";
    private static final String SCENARIO_NAME = "Scenario name";

    private final CommonScenariosController scenariosController = new CommonScenariosControllerMock();

    private IAaclScenariosWidget scenariosWidget;
    private Scenario scenario;
    private IScenarioService scenarioService;
    private IUsageService usageService;

    @Before
    public void setUp() {
        scenarioService = createMock(IScenarioService.class);
        usageService = createMock(IUsageService.class);
        scenariosWidget = createMock(IAaclScenariosWidget.class);
        scenariosController.initActionHandlers();
        buildScenario();
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.getUserName()).andReturn("user@copyright.com").anyTimes();
        replay(SecurityUtils.class);
        Whitebox.setInternalState(scenariosController, "widget", scenariosWidget);
        Whitebox.setInternalState(scenariosController, "scenarioService", scenarioService);
        Whitebox.setInternalState(scenariosController, "usageService", usageService);
    }

    @Test
    public void testScenarioExists() {
        String newName = "new scenario name";
        expect(scenarioService.scenarioExists(scenario.getName())).andReturn(true).once();
        expect(scenarioService.scenarioExists(newName)).andReturn(false).once();
        replay(scenarioService);
        assertTrue(scenariosController.scenarioExists(scenario.getName()));
        assertFalse(scenariosController.scenarioExists(newName));
        verify(scenarioService);
    }

    @Test
    public void testGetScenarioWithAmountsAndLastAction() {
        Scenario fullScenario = new Scenario();
        expect(scenarioService.getScenarioWithAmountsAndLastAction(scenario)).andReturn(fullScenario).once();
        replay(scenarioService);
        assertSame(fullScenario, scenariosController.getScenarioWithAmountsAndLastAction(scenario));
        verify(scenarioService);
    }

    @Test
    public void testEditScenarioName() {
        String newName = "new scenario name";
        scenarioService.updateName(scenario.getId(), newName);
        expectLastCall().once();
        replay(scenarioService, scenariosWidget);
        scenariosController.editScenarioName(scenario.getId(), newName);
        verify(scenarioService, scenariosWidget);
    }

    @Test
    public void testRefreshScenario() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        scenarioService.refreshScenario(scenario);
        expectLastCall().once();
        scenariosWidget.refreshSelectedScenario();
        expectLastCall().once();
        replay(scenariosWidget, scenarioService);
        scenariosController.refreshScenario();
        verify(scenariosWidget, scenarioService);
    }

    @Test
    public void testHandleAction() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(usageService.isScenarioEmpty(scenario)).andReturn(false).once();
        mockStatic(Windows.class);
        Windows.showConfirmDialogWithReason(eq("Confirm action"), eq("Are you sure you want to perform action?"),
            eq("Yes"), eq("Cancel"), anyObject(ConfirmActionDialogWindow.IListener.class), anyObject(Validator.class));
        expectLastCall().once();
        replay(Windows.class, usageService, scenariosWidget);
        scenariosController.handleAction(ScenarioActionTypeEnum.SUBMITTED);
        verify(Windows.class, usageService, scenariosWidget);
    }

    @Test
    public void testHandleActionApproved() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(usageService.isScenarioEmpty(scenario)).andReturn(false).once();
        mockStatic(Windows.class);
        Windows.showConfirmDialogWithReason(eq("Confirm action"), eq("Are you sure you want to perform action?"),
            eq("Yes"), eq("Cancel"), anyObject(ConfirmActionDialogWindow.IListener.class), anyObject(Validator.class));
        expectLastCall().once();
        replay(Windows.class, usageService, scenariosWidget);
        scenariosController.handleAction(ScenarioActionTypeEnum.APPROVED);
        verify(Windows.class, usageService, scenariosWidget);
    }

    @Test
    public void testHandleActionRejected() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(usageService.isScenarioEmpty(scenario)).andReturn(false).once();
        mockStatic(Windows.class);
        Windows.showConfirmDialogWithReason(eq("Confirm action"), eq("Are you sure you want to perform action?"),
            eq("Yes"), eq("Cancel"), anyObject(ConfirmActionDialogWindow.IListener.class), anyObject(Validator.class));
        expectLastCall().once();
        replay(Windows.class, usageService, scenariosWidget);
        scenariosController.handleAction(ScenarioActionTypeEnum.REJECTED);
        verify(Windows.class, usageService, scenariosWidget);
    }

    @Test
    public void testHandleActionWithEmptyScenario() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(usageService.isScenarioEmpty(scenario)).andReturn(true).once();
        mockStatic(Windows.class);
        Windows.showNotificationWindow("This scenario cannot be submitted for approval as it does not contain " +
            "any usages");
        expectLastCall().once();
        replay(Windows.class, usageService, scenariosWidget);
        scenariosController.handleAction(ScenarioActionTypeEnum.SUBMITTED);
        verify(Windows.class, usageService, scenariosWidget);
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
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(usageService.isScenarioEmpty(scenario)).andReturn(false).once();
        replay(Windows.class, usageService, scenariosWidget);
        scenariosController.handleAction(null);
        verify(Windows.class, usageService, scenariosWidget);
    }

    private void buildScenario() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName(SCENARIO_NAME);
    }

    private static class CommonScenariosControllerMock extends CommonScenariosController {
        @Override
        protected ICommonScenarioController getScenarioController() {
            return null;
        }

        @Override
        protected ICommonScenarioWidget initScenarioWidget() {
            return null;
        }

        @Override
        public void onDeleteButtonClicked() {

        }

        @Override
        public String getCriteriaHtmlRepresentation() {
            return null;
        }

        @Override
        protected ICommonScenariosWidget instantiateWidget() {
            return null;
        }
    }
}
