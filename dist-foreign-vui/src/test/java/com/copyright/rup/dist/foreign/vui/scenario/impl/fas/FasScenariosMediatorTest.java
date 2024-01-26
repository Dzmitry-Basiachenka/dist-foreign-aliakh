package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.vui.vaadin.common.security.SecurityUtils;

import com.vaadin.flow.component.button.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link FasScenariosMediator}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/12/19
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class FasScenariosMediatorTest {

    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private FasScenariosMediator mediator;
    private Button viewButton;
    private Button editNameButton;
    private Button deleteButton;
    private Button excludePayeesButton;
    private Button reconcileRightsholdersButton;
    private Button submitButton;
    private Button rejectButton;
    private Button approveButton;
    private Button sendToLmButton;
    private Button refreshScenarioButton;

    @Before
    public void setUp() {
        mockStatic(SecurityUtils.class);
        mediator = new FasScenariosMediator();
        editNameButton = new Button("Edit Name");
        mediator.setEditNameButton(editNameButton);
        deleteButton = new Button("Delete");
        mediator.setDeleteButton(deleteButton);
        viewButton = new Button("View");
        mediator.setViewButton(viewButton);
        excludePayeesButton = new Button("Exclude Payees");
        mediator.setExcludePayeesButton(excludePayeesButton);
        reconcileRightsholdersButton = new Button("Reconcile Rightsholders");
        mediator.setReconcileRightsholdersButton(reconcileRightsholdersButton);
        submitButton = new Button("Submit for approval");
        mediator.setSubmitButton(submitButton);
        rejectButton = new Button("Reject");
        mediator.setRejectButton(rejectButton);
        approveButton = new Button("Approve");
        mediator.setApproveButton(approveButton);
        sendToLmButton = new Button("Send to LM");
        mediator.setSendToLmButton(sendToLmButton);
        refreshScenarioButton = new Button("Refresh Scenario");
        mediator.setRefreshScenarioButton(refreshScenarioButton);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(viewButton.isVisible());
        assertFalse(editNameButton.isVisible());
        assertFalse(deleteButton.isVisible());
        assertFalse(excludePayeesButton.isVisible());
        assertFalse(reconcileRightsholdersButton.isVisible());
        assertFalse(submitButton.isVisible());
        assertFalse(rejectButton.isVisible());
        assertFalse(approveButton.isVisible());
        assertFalse(sendToLmButton.isVisible());
        assertFalse(refreshScenarioButton.isVisible());
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
        assertFalse(excludePayeesButton.isVisible());
        assertFalse(reconcileRightsholdersButton.isVisible());
        assertFalse(submitButton.isVisible());
        assertTrue(rejectButton.isVisible());
        assertTrue(approveButton.isVisible());
        assertFalse(sendToLmButton.isVisible());
        assertFalse(refreshScenarioButton.isVisible());
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
        assertTrue(excludePayeesButton.isVisible());
        assertTrue(reconcileRightsholdersButton.isVisible());
        assertTrue(submitButton.isVisible());
        assertFalse(rejectButton.isVisible());
        assertFalse(approveButton.isVisible());
        assertTrue(sendToLmButton.isVisible());
        assertTrue(refreshScenarioButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testSelectedScenarioChangedNullScenario() {
        mediator.selectedScenarioChanged(null);
        assertFalse(viewButton.isEnabled());
        assertFalse(editNameButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
        assertTrue(excludePayeesButton.isEnabled());
        assertFalse(reconcileRightsholdersButton.isEnabled());
        assertFalse(submitButton.isEnabled());
        assertFalse(rejectButton.isEnabled());
        assertFalse(approveButton.isEnabled());
        assertFalse(sendToLmButton.isEnabled());
        assertFalse(refreshScenarioButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioChangedInProgress() {
        var scenario = new Scenario();
        scenario.setProductFamily(FAS_PRODUCT_FAMILY);
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        mediator.selectedScenarioChanged(scenario);
        assertTrue(viewButton.isEnabled());
        assertTrue(editNameButton.isEnabled());
        assertTrue(deleteButton.isEnabled());
        assertTrue(excludePayeesButton.isEnabled());
        assertTrue(reconcileRightsholdersButton.isEnabled());
        assertTrue(submitButton.isEnabled());
        assertFalse(rejectButton.isEnabled());
        assertFalse(approveButton.isEnabled());
        assertFalse(sendToLmButton.isEnabled());
        assertTrue(refreshScenarioButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioChangedSubmitted() {
        var scenario = new Scenario();
        scenario.setProductFamily(FAS_PRODUCT_FAMILY);
        scenario.setStatus(ScenarioStatusEnum.SUBMITTED);
        mediator.selectedScenarioChanged(scenario);
        assertTrue(viewButton.isEnabled());
        assertFalse(editNameButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
        assertTrue(excludePayeesButton.isEnabled());
        assertFalse(reconcileRightsholdersButton.isEnabled());
        assertFalse(submitButton.isEnabled());
        assertTrue(rejectButton.isEnabled());
        assertTrue(approveButton.isEnabled());
        assertFalse(sendToLmButton.isEnabled());
        assertFalse(refreshScenarioButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioChangedApproved() {
        var scenario = new Scenario();
        scenario.setProductFamily(FAS_PRODUCT_FAMILY);
        scenario.setStatus(ScenarioStatusEnum.APPROVED);
        mediator.selectedScenarioChanged(scenario);
        assertTrue(viewButton.isEnabled());
        assertFalse(editNameButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
        assertTrue(excludePayeesButton.isEnabled());
        assertFalse(reconcileRightsholdersButton.isEnabled());
        assertFalse(submitButton.isEnabled());
        assertFalse(rejectButton.isEnabled());
        assertFalse(approveButton.isEnabled());
        assertTrue(sendToLmButton.isEnabled());
        assertFalse(refreshScenarioButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioChangedSentToLm() {
        var scenario = new Scenario();
        scenario.setProductFamily(FAS_PRODUCT_FAMILY);
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        mediator.selectedScenarioChanged(scenario);
        assertTrue(viewButton.isEnabled());
        assertFalse(editNameButton.isEnabled());
        assertFalse(deleteButton.isEnabled());
        assertTrue(excludePayeesButton.isEnabled());
        assertFalse(reconcileRightsholdersButton.isEnabled());
        assertFalse(submitButton.isEnabled());
        assertFalse(rejectButton.isEnabled());
        assertFalse(approveButton.isEnabled());
        assertFalse(sendToLmButton.isEnabled());
        assertFalse(refreshScenarioButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioFas2() {
        var scenario = new Scenario();
        scenario.setProductFamily("FAS2");
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        mediator.selectedScenarioChanged(scenario);
        assertTrue(refreshScenarioButton.isEnabled());
        assertTrue(reconcileRightsholdersButton.isEnabled());
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
        expect(SecurityUtils.hasPermission("FDA_EDIT_SCENARIO_NAME")).andReturn(true).once();
        expect(SecurityUtils.hasPermission("FDA_DELETE_SCENARIO")).andReturn(true).once();
        expect(SecurityUtils.hasPermission("FDA_EXCLUDE_FROM_SCENARIO")).andReturn(true).once();
        expect(SecurityUtils.hasPermission("FDA_RECONCILE_RIGHTSHOLDERS")).andReturn(true).once();
        expect(SecurityUtils.hasPermission("FDA_SUBMIT_SCENARIO")).andReturn(true).once();
        expect(SecurityUtils.hasPermission("FDA_DISTRIBUTE_SCENARIO")).andReturn(true).once();
        expect(SecurityUtils.hasPermission("FDA_REFRESH_SCENARIO")).andReturn(true).once();
    }
}
