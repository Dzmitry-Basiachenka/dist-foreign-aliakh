package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenariosWidget;

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
//TODO move all tests for common methods inside from product specific tests.
public class CommonScenariosControllerTest {

    private final CommonScenariosController scenariosController = new CommonScenariosControllerMock();

    private static final String SCENARIO_ID = "2fe241c4-7625-437b-9c8f-ff7ba4d3cb07";
    private static final String SCENARIO_NAME = "Scenario name";
    private IAaclScenariosWidget scenariosWidget;
    private Scenario scenario;
    private IScenarioService scenarioService;

    @Before
    public void setUp() {
        scenarioService = createMock(IScenarioService.class);
        buildScenario();
        scenariosWidget = createMock(IAaclScenariosWidget.class);
        Whitebox.setInternalState(scenariosController, "widget", scenariosWidget);
        Whitebox.setInternalState(scenariosController, "scenarioService", scenarioService);
    }

    @Test
    public void testEditScenarioName() {
        String newName = "new scenario name";
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        scenarioService.updateName(scenario.getId(), newName);
        expectLastCall().once();
        replay(scenarioService, scenariosWidget);
        scenariosController.editScenarioName(newName);
        verify(scenarioService, scenariosWidget);
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
