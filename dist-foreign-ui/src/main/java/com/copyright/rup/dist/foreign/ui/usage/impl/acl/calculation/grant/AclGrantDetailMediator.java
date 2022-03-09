package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;

/**
 * Mediator for the ACL grant detail widget.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 09/02/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclGrantDetailMediator implements IMediator {

    private MenuBar grantSetMenuBar;
    private Button editButton;

    @Override
    public void applyPermissions() {
        grantSetMenuBar.setVisible(ForeignSecurityUtils.hasSpecialistPermission()
            || ForeignSecurityUtils.hasManagerPermission());
        editButton.setVisible(ForeignSecurityUtils.hasSpecialistPermission());
    }

    public void setGrantSetMenuBar(MenuBar grantSetMenuBar) {
        this.grantSetMenuBar = grantSetMenuBar;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
    }
}
