package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

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
 * Verifies {@link AaclScenariosMediator}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/27/20
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class AaclScenariosMediatorTest {

    private AaclScenariosMediator mediator;
    private Button viewButton;
    private Button deleteButton;
    private Button submitButton;
    private Button rejectButton;
    private Button approveButton;

    @Before
    public void setUp() {
        mockStatic(SecurityUtils.class);
        mediator = new AaclScenariosMediator();
        viewButton = new Button("View");
        deleteButton = new Button("Delete");
        submitButton = new Button("Submit for Approval");
        rejectButton = new Button("Reject");
        approveButton = new Button("Approve");
        mediator.setViewButton(viewButton);
        mediator.setDeleteButton(deleteButton);
        mediator.setSubmitButton(submitButton);
        mediator.setRejectButton(rejectButton);
        mediator.setApproveButton(approveButton);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(viewButton.isVisible());
        assertFalse(deleteButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionManager() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(viewButton.isVisible());
        assertFalse(deleteButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionSpecialist() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(viewButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testSelectedScenarioChanged() {
        mediator.selectedScenarioChanged(new Scenario());
        assertTrue(viewButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioChangedNullScenario() {
        mediator.selectedScenarioChanged(null);
        assertFalse(viewButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
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
        expect(SecurityUtils.hasPermission("FDA_VIEW_SCENARIO")).andReturn(true).once();
    }

    private void mockManagerPermissions() {
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_VIEW_SCENARIO")).andReturn(true).once();
        expect(SecurityUtils.hasPermission("FDA_APPROVE_SCENARIO")).andReturn(true).once();
        expect(SecurityUtils.hasPermission("FDA_REJECT_SCENARIO")).andReturn(true).once();
    }

    private void mockSpecialistPermissions() {
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_VIEW_SCENARIO")).andReturn(true).once();
        expect(SecurityUtils.hasPermission("FDA_DELETE_SCENARIO")).andReturn(true).once();
        expect(SecurityUtils.hasPermission("FDA_SUBMIT_SCENARIO")).andReturn(true).once();
    }
}
