package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;

import java.util.Objects;

/**
 * Mediator class for {@link AclScenariosWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/23/2022
 *
 * @author Anton Azarenka
 */
class AclScenariosMediator implements IMediator {

    private Button createButton;
    private Button deleteButton;
    private Button viewButton;
    private Button pubTypeWeights;
    private Button submitButton;
    private Button rejectButton;
    private Button approveButton;

    @Override
    public void applyPermissions() {
        boolean hasSpecialistOrManagerPermission =
            ForeignSecurityUtils.hasSpecialistPermission() || ForeignSecurityUtils.hasManagerPermission();
        createButton.setVisible(hasSpecialistOrManagerPermission);
        deleteButton.setVisible(hasSpecialistOrManagerPermission);
        pubTypeWeights.setVisible(ForeignSecurityUtils.hasSpecialistPermission());
        submitButton.setVisible(ForeignSecurityUtils.hasManagerPermission());
        rejectButton.setVisible(ForeignSecurityUtils.hasApproverPermission());
        approveButton.setVisible(ForeignSecurityUtils.hasApproverPermission());
    }

    /**
     * Called when selected scenario was changed.
     *
     * @param aclScenario a selected {@link AclScenario} or {@code null} - if no scenario selected
     */
    public void selectedScenarioChanged(AclScenario aclScenario) {
        if (Objects.nonNull(aclScenario)) {
            boolean inProgressStatus = ScenarioStatusEnum.IN_PROGRESS == aclScenario.getStatus();
            viewButton.setEnabled(true);
            deleteButton.setEnabled(
                inProgressStatus && (ForeignSecurityUtils.hasSpecialistPermission() || aclScenario.isEditableFlag()));
            submitButton.setEnabled(inProgressStatus && !aclScenario.isEditableFlag());
            boolean isSubmittedState = ScenarioStatusEnum.SUBMITTED == aclScenario.getStatus();
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

    public void setCreateButton(Button createButton) {
        this.createButton = createButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public void setViewButton(Button viewButton) {
        this.viewButton = viewButton;
    }

    public void setPubTypeWeights(Button pubTypeWeights) {
        this.pubTypeWeights = pubTypeWeights;
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
}
