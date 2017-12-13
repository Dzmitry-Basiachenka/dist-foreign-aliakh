package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.vaadin.security.SecurityUtils;

import com.vaadin.ui.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link ScenariosMediator}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class ScenariosMediatorTest {

    private ScenariosMediator mediator;
    private Button deleteButton;
    private Button viewButton;
    private Button submitButton;
    private Button rejectButton;
    private Button approveButton;

    @Before
    public void setUp() {
        mockStatic(SecurityUtils.class);
        mediator = new ScenariosMediator();
        deleteButton = new Button("Delete");
        mediator.setDeleteButton(deleteButton);
        viewButton = new Button("View");
        mediator.setViewButton(viewButton);
        submitButton = new Button("Submit for approval");
        mediator.setSubmitButton(submitButton);
        rejectButton = new Button("Reject");
        mediator.setRejectButton(rejectButton);
        approveButton = new Button("Approve");
        mediator.setApproveButton(approveButton);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(deleteButton.isVisible());
        assertTrue(viewButton.isVisible());
        assertFalse(submitButton.isVisible());
        assertFalse(rejectButton.isVisible());
        assertFalse(approveButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionManager() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(deleteButton.isVisible());
        assertTrue(viewButton.isVisible());
        assertFalse(submitButton.isVisible());
        assertTrue(rejectButton.isVisible());
        assertTrue(approveButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionSpecialist() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(deleteButton.isVisible());
        assertTrue(viewButton.isVisible());
        assertTrue(submitButton.isVisible());
        assertFalse(rejectButton.isVisible());
        assertFalse(approveButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testSelectedScenarioChangedNullScenario() {
        mediator.selectedScenarioChanged(null);
        assertFalse(deleteButton.isEnabled());
        assertFalse(viewButton.isEnabled());
        assertFalse(submitButton.isEnabled());
        assertFalse(rejectButton.isEnabled());
        assertFalse(approveButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioChangedInProgress() {
        Scenario scenario = new Scenario();
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        mediator.selectedScenarioChanged(scenario);
        assertTrue(deleteButton.isEnabled());
        assertTrue(viewButton.isEnabled());
        assertTrue(submitButton.isEnabled());
        assertFalse(rejectButton.isEnabled());
        assertFalse(approveButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioChangedSubmitted() {
        Scenario scenario = new Scenario();
        scenario.setStatus(ScenarioStatusEnum.SUBMITTED);
        mediator.selectedScenarioChanged(scenario);
        assertFalse(deleteButton.isEnabled());
        assertTrue(viewButton.isEnabled());
        assertFalse(submitButton.isEnabled());
        assertTrue(rejectButton.isEnabled());
        assertTrue(approveButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioChangedApproved() {
        Scenario scenario = new Scenario();
        scenario.setStatus(ScenarioStatusEnum.APPROVED);
        mediator.selectedScenarioChanged(scenario);
        assertFalse(deleteButton.isEnabled());
        assertTrue(viewButton.isEnabled());
        assertFalse(submitButton.isEnabled());
        assertFalse(rejectButton.isEnabled());
        assertFalse(approveButton.isEnabled());
    }

    private void mockViewOnlyPermissions() {
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
    }

    private void mockManagerPermissions() {
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_REJECT_SCENARIO")).andReturn(true).once();
        expect(SecurityUtils.hasPermission("FDA_APPROVE_SCENARIO")).andReturn(true).once();
    }

    private void mockSpecialistPermissions() {
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_DELETE_SCENARIO")).andReturn(true).once();
        expect(SecurityUtils.hasPermission("FDA_SUBMIT_SCENARIO")).andReturn(true).once();
    }
}
