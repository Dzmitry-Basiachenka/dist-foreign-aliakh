package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosMediator;
import com.vaadin.ui.Button;

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
    private Button submitButton;
    private Button rejectButton;
    private Button approveButton;
    private Button sendToLmButton;
    private Button reconcileRightsholdersButton;
    private Button excludePayeesButton;
    private Button refreshScenarioButton;

    @Override
    public void applyPermissions() {
        deleteButton.setVisible(ForeignSecurityUtils.hasDeleteScenarioPermission());
        submitButton.setVisible(ForeignSecurityUtils.hasSubmitScenarioPermission());
        rejectButton.setVisible(ForeignSecurityUtils.hasRejectScenarioPermission());
        approveButton.setVisible(ForeignSecurityUtils.hasApproveScenarioPermission());
        sendToLmButton.setVisible(ForeignSecurityUtils.hasSendScenarioToLmPermission());
        reconcileRightsholdersButton.setVisible(ForeignSecurityUtils.hasReconcileRightsholdersPermission());
        excludePayeesButton.setVisible(ForeignSecurityUtils.hasExcludeFromScenarioPermission());
        refreshScenarioButton.setVisible(ForeignSecurityUtils.hasRefreshScenarioPermission());
        viewButton.setVisible(ForeignSecurityUtils.hasViewScenarioPermission());
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
            deleteButton.setEnabled(false);
            editNameButton.setEnabled(false);
            viewButton.setEnabled(false);
            submitButton.setEnabled(false);
            rejectButton.setEnabled(false);
            approveButton.setEnabled(false);
            sendToLmButton.setEnabled(false);
            reconcileRightsholdersButton.setEnabled(false);
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

    void setReconcileRightsholdersButton(Button reconcileRightsholdersButton) {
        this.reconcileRightsholdersButton = reconcileRightsholdersButton;
    }

    void setExcludePayeesButton(Button excludePayeesButton) {
        this.excludePayeesButton = excludePayeesButton;
    }

    void setRefreshScenarioButton(Button refreshScenarioButton) {
        this.refreshScenarioButton = refreshScenarioButton;
    }
}
