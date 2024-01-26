package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenariosMediator;

import com.vaadin.flow.component.button.Button;

import java.util.Objects;

/**
 * Implementation of {@link IScenariosMediator} for FAS product family.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
class FasScenariosMediator implements IScenariosMediator {

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

    @Override
    public void applyPermissions() {
        editNameButton.setVisible(ForeignSecurityUtils.hasEditScenarioNamePermission());
        deleteButton.setVisible(ForeignSecurityUtils.hasDeleteScenarioPermission());
        excludePayeesButton.setVisible(ForeignSecurityUtils.hasExcludeFromScenarioPermission());
        reconcileRightsholdersButton.setVisible(ForeignSecurityUtils.hasReconcileRightsholdersPermission());
        submitButton.setVisible(ForeignSecurityUtils.hasSubmitScenarioPermission());
        rejectButton.setVisible(ForeignSecurityUtils.hasRejectScenarioPermission());
        approveButton.setVisible(ForeignSecurityUtils.hasApproveScenarioPermission());
        sendToLmButton.setVisible(ForeignSecurityUtils.hasSendScenarioToLmPermission());
        refreshScenarioButton.setVisible(ForeignSecurityUtils.hasRefreshScenarioPermission());
    }

    @Override
    public void selectedScenarioChanged(Scenario scenario) {
        if (Objects.nonNull(scenario)) {
            viewButton.setEnabled(true);
            ScenarioStatusEnum status = scenario.getStatus();
            boolean isInProgressState = ScenarioStatusEnum.IN_PROGRESS == status;
            editNameButton.setEnabled(isInProgressState);
            deleteButton.setEnabled(isInProgressState);
            submitButton.setEnabled(isInProgressState);
            refreshScenarioButton.setEnabled(isInProgressState);
            reconcileRightsholdersButton.setEnabled(isInProgressState);
            boolean isSubmittedState = ScenarioStatusEnum.SUBMITTED == status;
            rejectButton.setEnabled(isSubmittedState);
            approveButton.setEnabled(isSubmittedState);
            sendToLmButton.setEnabled(ScenarioStatusEnum.APPROVED == status);
        } else {
            viewButton.setEnabled(false);
            editNameButton.setEnabled(false);
            deleteButton.setEnabled(false);
            reconcileRightsholdersButton.setEnabled(false);
            submitButton.setEnabled(false);
            rejectButton.setEnabled(false);
            approveButton.setEnabled(false);
            sendToLmButton.setEnabled(false);
            refreshScenarioButton.setEnabled(false);
        }
    }

    void setViewButton(Button viewButton) {
        this.viewButton = viewButton;
    }

    void setEditNameButton(Button editNameButton) {
        this.editNameButton = editNameButton;
    }

    void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    void setExcludePayeesButton(Button excludePayeesButton) {
        this.excludePayeesButton = excludePayeesButton;
    }

    void setReconcileRightsholdersButton(Button reconcileRightsholdersButton) {
        this.reconcileRightsholdersButton = reconcileRightsholdersButton;
    }

    void setSubmitButton(Button submitButton) {
        this.submitButton = submitButton;
    }

    void setRejectButton(Button rejectButton) {
        this.rejectButton = rejectButton;
    }

    void setApproveButton(Button approveButton) {
        this.approveButton = approveButton;
    }

    void setSendToLmButton(Button sendToLmButton) {
        this.sendToLmButton = sendToLmButton;
    }

    void setRefreshScenarioButton(Button refreshScenarioButton) {
        this.refreshScenarioButton = refreshScenarioButton;
    }
}
