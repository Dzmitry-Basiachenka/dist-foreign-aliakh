package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;

/**
 * Mediator for scenarios widget.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/14/17
 *
 * @author Aliaksandr Radkevich
 */
class ScenariosMediator implements IMediator {

    private Button deleteButton;
    private Button viewButton;
    private Button submitButton;
    private Button rejectButton;
    private Button approveButton;

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public void setViewButton(Button viewButton) {
        this.viewButton = viewButton;
    }

    public void setSubmitButton(Button submitButton) {
        this.submitButton = submitButton;
    }

    public void setRejectButton(Button rejectButton) {
        this.rejectButton = rejectButton;
    }

    public void setApproveButton(Button approveButton) {
        this.approveButton = approveButton;
    }

    @Override
    public void applyPermissions() {
        deleteButton.setVisible(ForeignSecurityUtils.hasDeleteScenarioPermission());
        submitButton.setVisible(ForeignSecurityUtils.hasSubmitScenarioPermission());
        rejectButton.setVisible(ForeignSecurityUtils.hasRejectScenarioPermission());
        approveButton.setVisible(ForeignSecurityUtils.hasApproveScenarioPermission());
        viewButton.setVisible(true);
    }

    /**
     * Handles buttons state depending on selected {@link Scenario}.
     *
     * @param scenario selected {@link Scenario}
     */
    void selectedScenarioChanged(Scenario scenario) {
        if (null != scenario) {
            viewButton.setEnabled(true);
            ScenarioStatusEnum status = scenario.getStatus();
            boolean isInProgressState = ScenarioStatusEnum.IN_PROGRESS == status;
            boolean isSubmittedState = ScenarioStatusEnum.SUBMITTED == status;
            deleteButton.setEnabled(isInProgressState);
            submitButton.setEnabled(isInProgressState);
            rejectButton.setEnabled(isSubmittedState);
            approveButton.setEnabled(isSubmittedState);
        } else {
            deleteButton.setEnabled(false);
            viewButton.setEnabled(false);
            submitButton.setEnabled(false);
            rejectButton.setEnabled(false);
            approveButton.setEnabled(false);
        }
    }
}
