package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;

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

    @Override
    public void applyPermissions() {
        createButton.setVisible(
            ForeignSecurityUtils.hasSpecialistPermission() || ForeignSecurityUtils.hasManagerPermission());
    }

    public void setCreateButton(Button createButton) {
        this.createButton = createButton;
    }
}
