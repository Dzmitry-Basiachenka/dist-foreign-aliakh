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

    @Override
    public void applyPermissions() {
        deleteButton.setVisible(ForeignSecurityUtils.hasDeleteScenarioPermission());
        viewButton.setVisible(ForeignSecurityUtils.hasViewScenarioPermission());
    }

    @Override
    public void selectedScenarioChanged(Scenario scenario) {
        if (null != scenario) {
            viewButton.setEnabled(true);
            ScenarioStatusEnum status = scenario.getStatus();
            boolean isInProgressState = ScenarioStatusEnum.IN_PROGRESS == status;
            deleteButton.setEnabled(isInProgressState);
        } else {
            viewButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }
    }

    void setViewButton(Button viewButton) {
        this.viewButton = viewButton;
    }

    void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}
