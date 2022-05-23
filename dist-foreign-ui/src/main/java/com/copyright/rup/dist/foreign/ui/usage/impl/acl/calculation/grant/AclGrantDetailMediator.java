package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

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
    private MenuBar.MenuItem createMenuItem;
    private MenuBar.MenuItem viewMenuItem;
    private Button editButton;
    private Button uploadButton;

    @Override
    public void applyPermissions() {
        boolean isSpecialist = ForeignSecurityUtils.hasSpecialistPermission();
        boolean isManager = ForeignSecurityUtils.hasManagerPermission();
        boolean isViewOnly = ForeignSecurityUtils.hasViewOnlyPermission();
        grantSetMenuBar.setVisible(isSpecialist || isManager || isViewOnly);
        createMenuItem.setVisible(isSpecialist || isManager);
        viewMenuItem.setVisible(isSpecialist || isManager || isViewOnly);
        editButton.setVisible(isSpecialist);
        uploadButton.setVisible(isSpecialist);
    }

    public void setGrantSetMenuBar(MenuBar grantSetMenuBar) {
        this.grantSetMenuBar = grantSetMenuBar;
    }

    public void setCreateMenuItem(MenuItem createMenuItem) {
        this.createMenuItem = createMenuItem;
    }

    public void setViewMenuItem(MenuItem viewMenuItem) {
        this.viewMenuItem = viewMenuItem;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
    }

    public void setUploadButton(Button uploadButton) {
        this.uploadButton = uploadButton;
    }
}
