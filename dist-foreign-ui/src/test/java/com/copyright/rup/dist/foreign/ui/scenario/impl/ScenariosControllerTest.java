package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

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

    @Before
    public void setUp() {
        scenariosController = new ScenariosController();
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(scenariosController.instantiateWidget());
    }

    @Test
    public void testGetScenarios() {
        assertEquals(Collections.emptyList(), scenariosController.getScenarios());
    }
}
