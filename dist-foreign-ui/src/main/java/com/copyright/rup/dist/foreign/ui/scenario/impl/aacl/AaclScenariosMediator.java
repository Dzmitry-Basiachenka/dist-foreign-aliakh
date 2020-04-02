package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import com.copyright.rup.dist.foreign.domain.Scenario;
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

    @Override
    public void applyPermissions() {
        viewButton.setVisible(ForeignSecurityUtils.hasViewScenarioPermission());
    }

    @Override
    public void selectedScenarioChanged(Scenario scenario) {
        viewButton.setEnabled(null != scenario);
    }

    void setViewButton(Button viewButton) {
        this.viewButton = viewButton;
    }
}
