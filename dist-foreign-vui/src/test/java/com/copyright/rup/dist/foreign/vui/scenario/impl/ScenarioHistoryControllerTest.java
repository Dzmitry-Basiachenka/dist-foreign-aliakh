package com.copyright.rup.dist.foreign.vui.scenario.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link ScenarioHistoryController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/30/2022
 *
 * @author Aliaksandr Liakh
 */
public class ScenarioHistoryControllerTest {

    private ScenarioHistoryController controller;

    @Before
    public void setUp() {
        controller = new ScenarioHistoryController();
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(ScenarioHistoryWidget.class));
    }
}
