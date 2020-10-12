package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosMediator;

import com.vaadin.ui.Button;

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

    private Button deleteButton;

    @Override
    public void applyPermissions() {
        deleteButton.setVisible(ForeignSecurityUtils.hasDeleteScenarioPermission());
    }

    @Override
    public void selectedScenarioChanged(Scenario scenario) {
        if (null != scenario) {
            ScenarioStatusEnum status = scenario.getStatus();
            boolean isInProgressState = ScenarioStatusEnum.IN_PROGRESS == status;
            deleteButton.setEnabled(isInProgressState);
        } else {
            deleteButton.setEnabled(false);
        }
    }

    void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}
