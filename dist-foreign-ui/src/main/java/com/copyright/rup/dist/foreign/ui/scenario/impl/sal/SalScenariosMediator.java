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

    @Override
    public void applyPermissions() {
        viewButton.setVisible(ForeignSecurityUtils.hasViewScenarioPermission());
        deleteButton.setVisible(ForeignSecurityUtils.hasDeleteScenarioPermission());
    }

    @Override
    public void selectedScenarioChanged(Scenario scenario) {
        if (Objects.nonNull(scenario)) {
            ScenarioStatusEnum status = scenario.getStatus();
            boolean isInProgressState = ScenarioStatusEnum.IN_PROGRESS == status;
            viewButton.setEnabled(true);
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
