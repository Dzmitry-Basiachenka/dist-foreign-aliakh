package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosWidget;
import com.copyright.rup.vaadin.ui.ConfirmDialogWindow;
import com.copyright.rup.vaadin.ui.Windows;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;

/**
 * Verifies {@link ScenariosController}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/15/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class ScenariosControllerTest {

    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private static final String SCENARIO_NAME = "Scenario name";
    private ScenariosController scenariosController;
    private IScenarioService scenarioService;

    @Before
    public void setUp() {
        scenarioService = createMock(IScenarioService.class);
        scenariosController = new ScenariosController();
        Whitebox.setInternalState(scenariosController, "scenarioService", scenarioService);
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
        IScenariosWidget scenariosWidget = createMock(IScenariosWidget.class);
        Whitebox.setInternalState(scenariosController, "widget", scenariosWidget);
        Scenario scenario = buildScenario();
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(Windows.showConfirmDialog(
            eq("Are you sure you want to delete <i><b>'" + SCENARIO_NAME + "'</b></i> scenario?"),
            anyObject(ConfirmDialogWindow.IListener.class))).andReturn(null).once();
        replay(scenariosWidget, Windows.class);
        scenariosController.onDeleteButtonClicked();
        verify(scenariosWidget, Windows.class);
    }

    private Scenario buildScenario() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName(SCENARIO_NAME);
        return scenario;
    }
}
