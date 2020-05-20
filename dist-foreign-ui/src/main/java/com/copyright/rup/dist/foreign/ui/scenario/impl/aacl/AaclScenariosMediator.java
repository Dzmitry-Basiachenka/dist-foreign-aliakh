package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosMediator;

import com.vaadin.ui.Button;

/**
 * Implementation of {@link IScenariosMediator} for AACL product family.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/19/20
 *
 * @author Uladzislau Shalamitski
 */
class AaclScenariosMediator implements IScenariosMediator {

    private Button viewButton;
    private Button deleteButton;
    private Button submitButton;
    private Button rejectButton;
    private Button approveButton;
    private Button sendToLmButton;

    @Override
    public void applyPermissions() {
        deleteButton.setVisible(ForeignSecurityUtils.hasDeleteScenarioPermission());
        viewButton.setVisible(ForeignSecurityUtils.hasViewScenarioPermission());
        submitButton.setVisible(ForeignSecurityUtils.hasSubmitScenarioPermission());
        rejectButton.setVisible(ForeignSecurityUtils.hasRejectScenarioPermission());
        approveButton.setVisible(ForeignSecurityUtils.hasApproveScenarioPermission());
        sendToLmButton.setVisible(ForeignSecurityUtils.hasSendScenarioToLmPermission());
    }

    @Override
    public void selectedScenarioChanged(Scenario scenario) {
        if (null != scenario) {
            viewButton.setEnabled(true);
            ScenarioStatusEnum status = scenario.getStatus();
            boolean isInProgressState = ScenarioStatusEnum.IN_PROGRESS == status;
            deleteButton.setEnabled(isInProgressState);
            submitButton.setEnabled(isInProgressState);
            boolean isSubmittedState = ScenarioStatusEnum.SUBMITTED == status;
            rejectButton.setEnabled(isSubmittedState);
            approveButton.setEnabled(isSubmittedState);
            sendToLmButton.setEnabled(ScenarioStatusEnum.APPROVED == status);
        } else {
            viewButton.setEnabled(false);
            deleteButton.setEnabled(false);
            submitButton.setEnabled(false);
            rejectButton.setEnabled(false);
            approveButton.setEnabled(false);
            sendToLmButton.setEnabled(false);
        }
    }

    void setViewButton(Button viewButton) {
        this.viewButton = viewButton;
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
}
