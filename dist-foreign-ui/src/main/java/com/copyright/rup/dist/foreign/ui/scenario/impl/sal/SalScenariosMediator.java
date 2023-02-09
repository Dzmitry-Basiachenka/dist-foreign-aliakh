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
    private Button editNameButton;
    private Button deleteButton;
    private Button submitButton;
    private Button rejectButton;
    private Button approveButton;
    private Button chooseScenariosButton;

    @Override
    public void applyPermissions() {
        editNameButton.setVisible(ForeignSecurityUtils.hasEditScenarioNamePermission());
        deleteButton.setVisible(ForeignSecurityUtils.hasDeleteScenarioPermission());
        submitButton.setVisible(ForeignSecurityUtils.hasSubmitScenarioPermission());
        rejectButton.setVisible(ForeignSecurityUtils.hasRejectScenarioPermission());
        approveButton.setVisible(ForeignSecurityUtils.hasApproveScenarioPermission());
        chooseScenariosButton.setVisible(ForeignSecurityUtils.hasSendScenarioToLmPermission());
    }

    @Override
    public void selectedScenarioChanged(Scenario scenario) {
        if (Objects.nonNull(scenario)) {
            viewButton.setEnabled(true);
            ScenarioStatusEnum status = scenario.getStatus();
            boolean isInProgressState = ScenarioStatusEnum.IN_PROGRESS == status;
            editNameButton.setEnabled(isInProgressState);
            deleteButton.setEnabled(isInProgressState);
            boolean isSubmittedState = ScenarioStatusEnum.SUBMITTED == status;
            rejectButton.setEnabled(isSubmittedState);
            approveButton.setEnabled(isSubmittedState);
        } else {
            viewButton.setEnabled(false);
            editNameButton.setEnabled(false);
            deleteButton.setEnabled(false);
            rejectButton.setEnabled(false);
            approveButton.setEnabled(false);
        }
    }

    void setChooseScenariosButton(Button chooseScenariosButton) {
        this.chooseScenariosButton = chooseScenariosButton;
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
}
