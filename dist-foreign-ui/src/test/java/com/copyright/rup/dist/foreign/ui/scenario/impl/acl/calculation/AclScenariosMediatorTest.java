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
    private static final String FDA_APPROVER_PERMISSION = "FDA_APPROVER_PERMISSION";

    private final Button createButton = new Button("Create");
    private final Button viewButton = new Button("View");
    private final Button editNameButton = new Button("Edit Name");
    private final Button pubTypeWeightsButton = new Button("Pub Type Weights");
    private final Button deleteButton = new Button("Delete");
    private final Button submitButton = new Button("Submit for Approval");
    private final Button rejectButton = new Button("Reject");
    private final Button approveButton = new Button("Approve");
    private final Button sendToLmButton = new Button("Send to LM");

    private AclScenariosMediator mediator;

    @Before
    public void setUp() {
        mediator = new AclScenariosMediator();
        mediator.setCreateButton(createButton);
        mediator.setViewButton(viewButton);
        mediator.setEditNameButton(editNameButton);
        mediator.setPubTypeWeights(pubTypeWeightsButton);
        mediator.setDeleteButton(deleteButton);
        mediator.setApproveButton(approveButton);
        mediator.setRejectButton(rejectButton);
        mediator.setSubmitButton(submitButton);
        mediator.setSentToLmButton(sendToLmButton);
        mockStatic(SecurityUtils.class);
    }

    @Test
    public void testApplySpecialistPermissions() {
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(true).times(2);
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_APPROVER_PERMISSION)).andReturn(false).times(2);
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(createButton.isVisible());
        assertTrue(viewButton.isVisible());
        assertTrue(editNameButton.isVisible());
        assertTrue(pubTypeWeightsButton.isVisible());
        assertTrue(deleteButton.isVisible());
        assertFalse(submitButton.isVisible());
        assertFalse(rejectButton.isVisible());
        assertFalse(approveButton.isVisible());
        assertTrue(sendToLmButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyManagerPermissions() {
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).times(2);
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(true).times(2);
        expect(SecurityUtils.hasPermission(FDA_APPROVER_PERMISSION)).andReturn(false).times(2);
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(createButton.isVisible());
        assertTrue(viewButton.isVisible());
        assertFalse(editNameButton.isVisible());
        assertFalse(pubTypeWeightsButton.isVisible());
        assertTrue(deleteButton.isVisible());
        assertTrue(submitButton.isVisible());
        assertFalse(rejectButton.isVisible());
        assertFalse(approveButton.isVisible());
        assertFalse(sendToLmButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyNotSpecialistManagerPermissions() {
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).times(2);
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(false).times(2);
        expect(SecurityUtils.hasPermission(FDA_APPROVER_PERMISSION)).andReturn(false).times(2);
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(createButton.isVisible());
        assertTrue(viewButton.isVisible());
        assertFalse(editNameButton.isVisible());
        assertFalse(pubTypeWeightsButton.isVisible());
        assertFalse(deleteButton.isVisible());
        assertFalse(submitButton.isVisible());
        assertFalse(rejectButton.isVisible());
        assertFalse(approveButton.isVisible());
        assertFalse(sendToLmButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyApproverPermissions() {
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).times(2);
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(false).times(2);
        expect(SecurityUtils.hasPermission(FDA_APPROVER_PERMISSION)).andReturn(true).times(2);
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(createButton.isVisible());
        assertTrue(viewButton.isVisible());
        assertFalse(editNameButton.isVisible());
        assertFalse(pubTypeWeightsButton.isVisible());
        assertFalse(deleteButton.isVisible());
        assertFalse(submitButton.isVisible());
        assertTrue(rejectButton.isVisible());
        assertTrue(approveButton.isVisible());
        assertFalse(sendToLmButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testSelectedScenarioChangedNullScenario() {
        mediator.selectedScenarioChanged(null);
        assertTrue(createButton.isEnabled());
        assertFalse(viewButton.isEnabled());
        assertFalse(editNameButton.isEnabled());
        assertTrue(pubTypeWeightsButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
        assertFalse(sendToLmButton.isEnabled());
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
        assertTrue(editNameButton.isEnabled());
        assertTrue(pubTypeWeightsButton.isEnabled());
        assertTrue(deleteButton.isEnabled());
        assertFalse(submitButton.isEnabled());
        assertFalse(rejectButton.isEnabled());
        assertFalse(approveButton.isEnabled());
        assertFalse(sendToLmButton.isEnabled());
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
        assertTrue(editNameButton.isEnabled());
        assertTrue(pubTypeWeightsButton.isEnabled());
        assertTrue(deleteButton.isEnabled());
        assertFalse(submitButton.isEnabled());
        assertFalse(rejectButton.isEnabled());
        assertFalse(approveButton.isEnabled());
        assertFalse(sendToLmButton.isEnabled());
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
        assertTrue(editNameButton.isEnabled());
        assertTrue(pubTypeWeightsButton.isEnabled());
        assertTrue(deleteButton.isEnabled());
        assertTrue(submitButton.isEnabled());
        assertFalse(rejectButton.isEnabled());
        assertFalse(approveButton.isEnabled());
        assertFalse(sendToLmButton.isEnabled());
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
        assertTrue(editNameButton.isEnabled());
        assertTrue(pubTypeWeightsButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
        assertTrue(submitButton.isEnabled());
        assertFalse(rejectButton.isEnabled());
        assertFalse(approveButton.isEnabled());
        assertFalse(sendToLmButton.isEnabled());
        verify(SecurityUtils.class);
    }

    @Test
    public void testSelectedScenarioChangedSubmittedStatus() {
        AclScenario scenario = new AclScenario();
        scenario.setStatus(ScenarioStatusEnum.SUBMITTED);
        scenario.setEditableFlag(false);
        mediator.selectedScenarioChanged(scenario);
        assertTrue(createButton.isEnabled());
        assertTrue(viewButton.isEnabled());
        assertFalse(editNameButton.isEnabled());
        assertTrue(pubTypeWeightsButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
        assertFalse(submitButton.isEnabled());
        assertTrue(rejectButton.isEnabled());
        assertTrue(approveButton.isEnabled());
        assertFalse(sendToLmButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioChangedApprovedStatus() {
        AclScenario scenario = new AclScenario();
        scenario.setStatus(ScenarioStatusEnum.APPROVED);
        scenario.setEditableFlag(false);
        mediator.selectedScenarioChanged(scenario);
        assertTrue(createButton.isEnabled());
        assertTrue(viewButton.isEnabled());
        assertFalse(editNameButton.isEnabled());
        assertTrue(pubTypeWeightsButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
        assertFalse(submitButton.isEnabled());
        assertFalse(rejectButton.isEnabled());
        assertFalse(approveButton.isEnabled());
        assertTrue(sendToLmButton.isEnabled());
    }
}
