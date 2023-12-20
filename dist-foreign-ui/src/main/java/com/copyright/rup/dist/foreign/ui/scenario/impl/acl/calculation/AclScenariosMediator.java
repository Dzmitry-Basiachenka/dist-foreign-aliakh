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
    private Button viewButton;
    private Button editNameButton;
    private Button deleteButton;
    private Button pubTypeWeights;
    private Button submitButton;
    private Button rejectButton;
    private Button approveButton;
    private Button sentToLmButton;

    @Override
    public void applyPermissions() {
        boolean hasSpecialistOrManagerPermission =
            ForeignSecurityUtils.hasSpecialistPermission() || ForeignSecurityUtils.hasManagerPermission();
        boolean hasSpecialistPermission = ForeignSecurityUtils.hasSpecialistPermission();
        createButton.setVisible(hasSpecialistOrManagerPermission);
        editNameButton.setVisible(hasSpecialistPermission);
        deleteButton.setVisible(hasSpecialistOrManagerPermission);
        pubTypeWeights.setVisible(hasSpecialistPermission);
        submitButton.setVisible(ForeignSecurityUtils.hasManagerPermission());
        rejectButton.setVisible(ForeignSecurityUtils.hasApproverPermission());
        approveButton.setVisible(ForeignSecurityUtils.hasApproverPermission());
        sentToLmButton.setVisible(hasSpecialistPermission);
    }

    /**
     * Called when selected scenario was changed.
     *
     * @param aclScenario a selected {@link AclScenario} or {@code null} - if no scenario selected
     */
    public void selectedScenarioChanged(AclScenario aclScenario) {
        if (Objects.nonNull(aclScenario)) {
            viewButton.setEnabled(true);
            ScenarioStatusEnum status = aclScenario.getStatus();
            boolean inProgressStatus = ScenarioStatusEnum.IN_PROGRESS == status;
            editNameButton.setEnabled(inProgressStatus);
            deleteButton.setEnabled(
                inProgressStatus && (ForeignSecurityUtils.hasSpecialistPermission() || aclScenario.isEditableFlag()));
            submitButton.setEnabled(inProgressStatus && !aclScenario.isEditableFlag());
            boolean isSubmittedState = ScenarioStatusEnum.SUBMITTED == status;
            rejectButton.setEnabled(isSubmittedState);
            approveButton.setEnabled(isSubmittedState);
            sentToLmButton.setEnabled(ScenarioStatusEnum.APPROVED == status);
        } else {
            viewButton.setEnabled(false);
            editNameButton.setEnabled(false);
            deleteButton.setEnabled(false);
            submitButton.setEnabled(false);
            rejectButton.setEnabled(false);
            approveButton.setEnabled(false);
            sentToLmButton.setEnabled(false);
        }
    }

    public void setCreateButton(Button createButton) {
        this.createButton = createButton;
    }

    public void setViewButton(Button viewButton) {
        this.viewButton = viewButton;
    }

    public void setEditNameButton(Button editNameButton) {
        this.editNameButton = editNameButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
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

    public void setSentToLmButton(Button sentToLmButton) {
        this.sentToLmButton = sentToLmButton;
    }
}
