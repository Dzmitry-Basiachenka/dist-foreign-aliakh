package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link FasScenariosController}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
public class FasScenariosControllerTest {

    private FasScenariosController scenariosController;

    @Before
    public void setUp() {
        scenariosController = new FasScenariosController();
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(scenariosController.instantiateWidget(), instanceOf(FasScenariosWidget.class));
    }
}
