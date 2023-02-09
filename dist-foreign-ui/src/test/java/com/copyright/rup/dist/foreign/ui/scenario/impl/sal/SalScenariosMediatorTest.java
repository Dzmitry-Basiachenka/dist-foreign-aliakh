package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

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
 * Verifies {@link SalScenariosMediator}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/22/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class SalScenariosMediatorTest {

    private SalScenariosMediator mediator;
    private Button viewButton;
    private Button editNameButton;
    private Button deleteButton;
    private Button submitButton;
    private Button rejectButton;
    private Button approveButton;
    private Button chooseScenariosButton;

    @Before
    public void setUp() {
        mockStatic(SecurityUtils.class);
        mediator = new SalScenariosMediator();
        viewButton = new Button("View");
        editNameButton = new Button("Edit Name");
        deleteButton = new Button("Delete");
        submitButton = new Button("Submit for Approval");
        rejectButton = new Button("Reject");
        approveButton = new Button("Approve");
        chooseScenariosButton = new Button("Choose Scenarios");
        mediator.setViewButton(viewButton);
        mediator.setEditNameButton(editNameButton);
        mediator.setDeleteButton(deleteButton);
        mediator.setSubmitButton(submitButton);
        mediator.setRejectButton(rejectButton);
        mediator.setApproveButton(approveButton);
        mediator.setChooseScenariosButton(chooseScenariosButton);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(viewButton.isVisible());
        assertFalse(deleteButton.isVisible());
        assertFalse(submitButton.isVisible());
        assertFalse(rejectButton.isVisible());
        assertFalse(approveButton.isVisible());
        assertFalse(chooseScenariosButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionManager() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(viewButton.isVisible());
        assertFalse(editNameButton.isVisible());
        assertFalse(deleteButton.isVisible());
        assertFalse(submitButton.isVisible());
        assertTrue(rejectButton.isVisible());
        assertTrue(approveButton.isVisible());
        assertFalse(chooseScenariosButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionSpecialist() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(viewButton.isVisible());
        assertTrue(editNameButton.isVisible());
        assertTrue(deleteButton.isVisible());
        assertTrue(submitButton.isVisible());
        assertFalse(rejectButton.isVisible());
        assertFalse(approveButton.isVisible());
        assertTrue(chooseScenariosButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testSelectedScenarioChangedNullScenario() {
        mediator.selectedScenarioChanged(null);
        assertFalse(viewButton.isEnabled());
        assertFalse(editNameButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
        assertTrue(submitButton.isEnabled());
        assertFalse(rejectButton.isEnabled());
        assertFalse(approveButton.isEnabled());
        assertTrue(chooseScenariosButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioChangedInProgress() {
        Scenario scenario = new Scenario();
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        mediator.selectedScenarioChanged(scenario);
        assertTrue(viewButton.isEnabled());
        assertTrue(editNameButton.isEnabled());
        assertTrue(deleteButton.isEnabled());
        assertTrue(submitButton.isEnabled());
        assertFalse(rejectButton.isEnabled());
        assertFalse(approveButton.isEnabled());
        assertTrue(chooseScenariosButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioChangedSubmitted() {
        Scenario scenario = new Scenario();
        scenario.setStatus(ScenarioStatusEnum.SUBMITTED);
        mediator.selectedScenarioChanged(scenario);
        assertTrue(viewButton.isEnabled());
        assertFalse(editNameButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
        assertTrue(submitButton.isEnabled());
        assertTrue(rejectButton.isEnabled());
        assertTrue(approveButton.isEnabled());
        assertTrue(chooseScenariosButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioChangedApproved() {
        Scenario scenario = new Scenario();
        scenario.setStatus(ScenarioStatusEnum.APPROVED);
        mediator.selectedScenarioChanged(scenario);
        assertTrue(viewButton.isEnabled());
        assertFalse(editNameButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
        assertTrue(submitButton.isEnabled());
        assertFalse(rejectButton.isEnabled());
        assertFalse(approveButton.isEnabled());
        assertTrue(chooseScenariosButton.isEnabled());
    }

    private void mockViewOnlyPermissions() {
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
    }

    private void mockManagerPermissions() {
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_APPROVE_SCENARIO")).andReturn(true).once();
        expect(SecurityUtils.hasPermission("FDA_REJECT_SCENARIO")).andReturn(true).once();
    }

    private void mockSpecialistPermissions() {
        expect(SecurityUtils.hasPermission("FDA_EDIT_SCENARIO_NAME")).andReturn(true).once();
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_DELETE_SCENARIO")).andReturn(true).once();
        expect(SecurityUtils.hasPermission("FDA_SUBMIT_SCENARIO")).andReturn(true).once();
        expect(SecurityUtils.hasPermission("FDA_DISTRIBUTE_SCENARIO")).andReturn(true).once();
    }
}
