package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.vaadin.security.SecurityUtils;

import com.vaadin.ui.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link AclScenariosMediator}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/27/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class AclScenariosMediatorTest {

    private static final String FDA_SPECIALIST_PERMISSION = "FDA_SPECIALIST_PERMISSION";
    private static final String FDA_MANAGER_PERMISSION = "FDA_MANAGER_PERMISSION";

    private final Button createButton = new Button("Create");
    private final Button viewButton = new Button("View");
    private final Button pubTypeWeightsButton = new Button("Pub Type Weights");
    private final Button deleteButton = new Button("Delete");

    private AclScenariosMediator mediator;

    @Before
    public void setUp() {
        mediator = new AclScenariosMediator();
        mediator.setCreateButton(createButton);
        mediator.setViewButton(viewButton);
        mediator.setPubTypeWeights(pubTypeWeightsButton);
        mediator.setDeleteButton(deleteButton);
        mockStatic(SecurityUtils.class);
    }

    @Test
    public void testApplySpecialistPermissions() {
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(true).times(2);
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(createButton.isVisible());
        assertTrue(viewButton.isVisible());
        assertTrue(pubTypeWeightsButton.isVisible());
        assertTrue(deleteButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyManagerPermissions() {
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).times(2);
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(createButton.isVisible());
        assertTrue(viewButton.isVisible());
        assertFalse(pubTypeWeightsButton.isVisible());
        assertTrue(deleteButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyNotSpecialistManagerPermissions() {
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).times(2);
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(false).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(createButton.isVisible());
        assertTrue(viewButton.isVisible());
        assertFalse(pubTypeWeightsButton.isVisible());
        assertFalse(deleteButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testSelectedScenarioChangedNullScenario() {
        mediator.selectedScenarioChanged(null);
        assertTrue(createButton.isEnabled());
        assertFalse(viewButton.isEnabled());
        assertTrue(pubTypeWeightsButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
    }

    @Test
    public void testSelectedEditableScenarioChangedInProgressSpecialistPermissions() {
        AclScenario scenario = new AclScenario();
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setEditableFlag(true);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.selectedScenarioChanged(scenario);
        assertTrue(createButton.isEnabled());
        assertTrue(viewButton.isEnabled());
        assertTrue(pubTypeWeightsButton.isEnabled());
        assertTrue(deleteButton.isEnabled());
        verify(SecurityUtils.class);
    }

    @Test
    public void testSelectedEditableScenarioChangedInProgressManagerPermissions() {
        AclScenario scenario = new AclScenario();
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setEditableFlag(true);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).once();
        replay(SecurityUtils.class);
        mediator.selectedScenarioChanged(scenario);
        assertTrue(createButton.isEnabled());
        assertTrue(viewButton.isEnabled());
        assertTrue(pubTypeWeightsButton.isEnabled());
        assertTrue(deleteButton.isEnabled());
        verify(SecurityUtils.class);
    }

    @Test
    public void testSelectedNotEditableScenarioChangedInProgressSpecialistPermissions() {
        AclScenario scenario = new AclScenario();
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setEditableFlag(false);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.selectedScenarioChanged(scenario);
        assertTrue(createButton.isEnabled());
        assertTrue(viewButton.isEnabled());
        assertTrue(pubTypeWeightsButton.isEnabled());
        assertTrue(deleteButton.isEnabled());
        verify(SecurityUtils.class);
    }

    @Test
    public void testSelectedNotEditableScenarioChangedInProgressManagerPermissions() {
        AclScenario scenario = new AclScenario();
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setEditableFlag(false);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).once();
        replay(SecurityUtils.class);
        mediator.selectedScenarioChanged(scenario);
        assertTrue(createButton.isEnabled());
        assertTrue(viewButton.isEnabled());
        assertTrue(pubTypeWeightsButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
        verify(SecurityUtils.class);
    }
}
