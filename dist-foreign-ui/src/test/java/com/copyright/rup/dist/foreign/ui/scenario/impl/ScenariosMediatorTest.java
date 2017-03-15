package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Scenario;

import com.vaadin.ui.Button;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link ScenariosMediator}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/15/17
 *
 * @author Aliaksandr Radkevich
 */
public class ScenariosMediatorTest {

    private ScenariosMediator mediator;
    private Button deleteButton;

    @Before
    public void setUp() {
        mediator = new ScenariosMediator();
        deleteButton = new Button("Delete");
        mediator.setDeleteButton(deleteButton);
    }

    @Test
    public void testApplyPermissions() {
        mediator.applyPermissions();
        assertTrue(deleteButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioChangedNullScenario() {
        mediator.selectedScenarioChanged(null);
        assertFalse(deleteButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioChanged() {
        mediator.selectedScenarioChanged(new Scenario());
        assertTrue(deleteButton.isEnabled());
    }
}
