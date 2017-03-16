package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.service.api.IScenarioService;

import org.junit.Before;
import org.junit.Test;
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
 */
public class ScenariosControllerTest {

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
}
