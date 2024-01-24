package com.copyright.rup.dist.foreign.vui.scenario.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.vui.scenario.api.IActionHandler;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenarioController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosWidget;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.security.SecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.ConfirmDialogWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.ConfirmWindows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


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
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
@PrepareForTest({Windows.class, SecurityUtils.class, ConfirmWindows.class, ConfirmDialogWindow.class})
public class CommonScenariosControllerTest {

    private static final String SCENARIO_ID = "2fe241c4-7625-437b-9c8f-ff7ba4d3cb07";
    private static final String SCENARIO_NAME = "Scenario name";

    private final CommonScenariosController scenariosController = new CommonScenariosControllerMock();

    private Map<ScenarioActionTypeEnum, IActionHandler> actionHandlers;
    private IFasScenariosWidget scenariosWidget;
    private Scenario scenario;
    private IScenarioService scenarioService;
    private IUsageService usageService;

    @Before
    public void setUp() {
        scenarioService = createMock(IScenarioService.class);
        usageService = createMock(IUsageService.class);
        scenariosWidget = createMock(IFasScenariosWidget.class);
        buildScenario();
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.getUserName()).andReturn("user@copyright.com").anyTimes();
        initActionHandlers();
        replay(SecurityUtils.class);
        Whitebox.setInternalState(scenariosController, "widget", scenariosWidget);
        Whitebox.setInternalState(scenariosController, "scenarioService", scenarioService);
        Whitebox.setInternalState(scenariosController, "usageService", usageService);
        Whitebox.setInternalState(scenariosController, "actionHandlers", actionHandlers);
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
        var fullScenario = new Scenario();
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
        Capture<Consumer<String>> actionDialogListenerCapture = newCapture();
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).times(2);
        expect(usageService.isScenarioEmpty(scenario)).andReturn(false).once();
        scenariosWidget.refresh();
        expectLastCall().once();
        mockStatic(Windows.class);
        createPartialMock(ConfirmWindows.class, "showConfirmDialogWithReason", String.class, String.class,
            String.class, String.class, Consumer.class, List.class);
        ConfirmWindows.showConfirmDialogWithReason(
            eq("Confirm action"),
            eq("Are you sure you want to perform action?"),
            eq("Yes"),
            eq("Cancel"),
            capture(actionDialogListenerCapture),
            anyObject(List.class));
        expectLastCall().once();
        replay(Windows.class, usageService, scenariosWidget, ConfirmWindows.class, ConfirmDialogWindow.class);
        scenariosController.handleAction(ScenarioActionTypeEnum.SUBMITTED);
        actionDialogListenerCapture.getValue().accept("REASON");
        verify(Windows.class, usageService, scenariosWidget, ConfirmWindows.class, ConfirmDialogWindow.class);
    }

    @Test
    public void testHandleActionApproved() {
        Capture<Consumer<String>> actionDialogListenerCapture = newCapture();
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).times(2);
        expect(usageService.isScenarioEmpty(scenario)).andReturn(false).once();
        scenariosWidget.refresh();
        expectLastCall().once();
        mockStatic(Windows.class);
        createPartialMock(ConfirmWindows.class, "showConfirmDialogWithReason", String.class, String.class,
            String.class, String.class, Consumer.class, List.class);
        ConfirmWindows.showConfirmDialogWithReason(
            eq("Confirm action"),
            eq("Are you sure you want to perform action?"),
            eq("Yes"),
            eq("Cancel"),
            capture(actionDialogListenerCapture),
            anyObject(List.class));
        expectLastCall().once();
        replay(Windows.class, usageService, scenariosWidget, ConfirmWindows.class, ConfirmDialogWindow.class);
        scenariosController.handleAction(ScenarioActionTypeEnum.APPROVED);
        actionDialogListenerCapture.getValue().accept("REASON");
        verify(Windows.class, usageService, scenariosWidget, ConfirmWindows.class, ConfirmDialogWindow.class);
    }

    @Test
    public void testHandleActionRejected() {
        Capture<Consumer<String>> actionDialogListenerCapture = newCapture();
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).times(2);
        expect(usageService.isScenarioEmpty(scenario)).andReturn(false).once();
        scenariosWidget.refresh();
        expectLastCall().once();
        mockStatic(Windows.class);
        createPartialMock(ConfirmWindows.class, "showConfirmDialogWithReason", String.class, String.class,
            String.class, String.class, Consumer.class, List.class);
        ConfirmWindows.showConfirmDialogWithReason(
            eq("Confirm action"),
            eq("Are you sure you want to perform action?"),
            eq("Yes"),
            eq("Cancel"),
            capture(actionDialogListenerCapture),
            anyObject(List.class));
        expectLastCall().once();
        replay(Windows.class, usageService, scenariosWidget, ConfirmWindows.class, ConfirmDialogWindow.class);
        scenariosController.handleAction(ScenarioActionTypeEnum.REJECTED);
        actionDialogListenerCapture.getValue().accept("REASON");
        verify(Windows.class, usageService, scenariosWidget, ConfirmWindows.class, ConfirmDialogWindow.class);
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

    private void initActionHandlers() {
        actionHandlers = new EnumMap<>(ScenarioActionTypeEnum.class);
        actionHandlers.put(ScenarioActionTypeEnum.SUBMITTED,
            (scen, reason) -> scenarioService.submit(scen, reason));
        actionHandlers.put(ScenarioActionTypeEnum.APPROVED,
            (scen, reason) -> scenarioService.approve(scen, reason));
        actionHandlers.put(ScenarioActionTypeEnum.REJECTED,
            (scen, reason) -> scenarioService.reject(scen, reason));
    }

    private static class CommonScenariosControllerMock extends CommonScenariosController {

        @Override
        public String getCriteriaHtmlRepresentation() {
            return null;
        }

        @Override
        public void onDeleteButtonClicked() {}

        @Override
        protected ICommonScenarioController getScenarioController() {
            return null;
        }

        @Override
        protected ICommonScenarioWidget initScenarioWidget() {
            return null;
        }

        @Override
        protected ICommonScenariosWidget instantiateWidget() {
            return null;
        }
    }
}
