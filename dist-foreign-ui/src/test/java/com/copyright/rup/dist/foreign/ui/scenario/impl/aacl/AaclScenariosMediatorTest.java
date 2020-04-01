package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Scenario;

import com.vaadin.ui.Button;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link AaclScenariosMediator}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/27/20
 *
 * @author Stanislau Rudak
 */
public class AaclScenariosMediatorTest {

    private AaclScenariosMediator mediator;
    private Button viewButton;

    @Before
    public void setUp() {
        mediator = new AaclScenariosMediator();
        viewButton = new Button("View");
        mediator.setViewButton(viewButton);
    }

    @Test
    public void testApplyPermissions() {
        mediator.applyPermissions();
        assertTrue(viewButton.isVisible());
    }

    @Test
    public void testSelectedScenarioChanged() {
        mediator.selectedScenarioChanged(new Scenario());
        assertTrue(viewButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioChangedNullScenario() {
        mediator.selectedScenarioChanged(null);
        assertFalse(viewButton.isEnabled());
    }
}
