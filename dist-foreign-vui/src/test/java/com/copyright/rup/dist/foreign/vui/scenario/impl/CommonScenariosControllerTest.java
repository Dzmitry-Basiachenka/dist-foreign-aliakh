package com.copyright.rup.dist.foreign.vui.scenario.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosWidget;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosWidget;

import org.junit.Before;
import org.junit.Test;
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
public class CommonScenariosControllerTest {

    private static final String SCENARIO_ID = "2fe241c4-7625-437b-9c8f-ff7ba4d3cb07";
    private static final String SCENARIO_NAME = "Scenario name";

    private final CommonScenariosController scenariosController = new CommonScenariosControllerMock();

    private IFasScenariosWidget scenariosWidget;
    private Scenario scenario;
    private IScenarioService scenarioService;

    @Before
    public void setUp() {
        scenarioService = createMock(IScenarioService.class);
        scenariosWidget = createMock(IFasScenariosWidget.class);
        buildScenario();
        Whitebox.setInternalState(scenariosController, "widget", scenariosWidget);
        Whitebox.setInternalState(scenariosController, "scenarioService", scenarioService);
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

    private void buildScenario() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName(SCENARIO_NAME);
    }

    private static class CommonScenariosControllerMock extends CommonScenariosController {

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
