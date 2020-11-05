package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosMediator;

import com.vaadin.ui.Button;

import java.util.Objects;

/**
 * Implementation of {@link IScenariosMediator} for SAL product family.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/22/2020
 *
 * @author Aliaksandr Liakh
 */
class SalScenariosMediator implements IScenariosMediator {

    private Button viewButton;
    private Button deleteButton;
    private Button submitButton;
    private Button rejectButton;
    private Button approveButton;
    private Button sendToLmButton;

    @Override
    public void applyPermissions() {
        viewButton.setVisible(ForeignSecurityUtils.hasViewScenarioPermission());
        deleteButton.setVisible(ForeignSecurityUtils.hasDeleteScenarioPermission());
        submitButton.setVisible(ForeignSecurityUtils.hasSubmitScenarioPermission());
        rejectButton.setVisible(ForeignSecurityUtils.hasRejectScenarioPermission());
        approveButton.setVisible(ForeignSecurityUtils.hasApproveScenarioPermission());
        sendToLmButton.setVisible(ForeignSecurityUtils.hasSendScenarioToLmPermission());
    }

    @Override
    public void selectedScenarioChanged(Scenario scenario) {
        if (Objects.nonNull(scenario)) {
            ScenarioStatusEnum status = scenario.getStatus();
            boolean isInProgressState = ScenarioStatusEnum.IN_PROGRESS == status;
            viewButton.setEnabled(true);
            deleteButton.setEnabled(isInProgressState);
            submitButton.setEnabled(isInProgressState);
            boolean isSubmittedState = ScenarioStatusEnum.SUBMITTED == status;
            rejectButton.setEnabled(isSubmittedState);
            approveButton.setEnabled(isSubmittedState);
        } else {
            viewButton.setEnabled(false);
            deleteButton.setEnabled(false);
            submitButton.setEnabled(false);
            rejectButton.setEnabled(false);
            approveButton.setEnabled(false);
        }
    }

    void setSendToLmButton(Button sendToLmButton) {
        this.sendToLmButton = sendToLmButton;
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
}
