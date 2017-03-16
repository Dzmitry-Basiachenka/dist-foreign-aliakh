package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;

/**
 * Mediator for scenarios widget.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/14/17
 *
 * @author Aliaksandr Radkevich
 */
class ScenariosMediator implements IMediator {

    private Button deleteButton;

    /**
     * Sets 'Delete' button.
     *
     * @param deleteButton {@link Button} instance
     */
    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    @Override
    public void applyPermissions() {
       deleteButton.setVisible(ForeignSecurityUtils.hasDeleteScenarioPermission());
    }

    /**
     * Handles buttons state sepending on selected {@link Scenario}.
     *
     * @param scenario selected {@link Scenario}
     */
    void selectedScenarioChanged(Scenario scenario) {
        deleteButton.setEnabled(null != scenario);
    }
}
