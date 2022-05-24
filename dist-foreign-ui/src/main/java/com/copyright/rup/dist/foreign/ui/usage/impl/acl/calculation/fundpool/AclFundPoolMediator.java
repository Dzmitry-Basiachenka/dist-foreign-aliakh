package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * Mediator for the ACL fund pool widget.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/29/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolMediator implements IMediator {

    private MenuBar fundPoolMenuBar;
    private MenuBar.MenuItem createMenuItem;
    private MenuBar.MenuItem viewMenuItem;

    @Override
    public void applyPermissions() {
        boolean isSpecialist = ForeignSecurityUtils.hasSpecialistPermission();
        boolean isManager = ForeignSecurityUtils.hasManagerPermission();
        boolean isViewOnly = ForeignSecurityUtils.hasViewOnlyPermission();
        fundPoolMenuBar.setVisible(isSpecialist || isManager || isViewOnly);
        createMenuItem.setVisible(isSpecialist);
        viewMenuItem.setVisible(isSpecialist || isManager || isViewOnly);
    }

    public void setFundPoolMenuBar(MenuBar fundPoolMenuBar) {
        this.fundPoolMenuBar = fundPoolMenuBar;
    }

    public void setCreateMenuItem(MenuItem createMenuItem) {
        this.createMenuItem = createMenuItem;
    }

    public void setViewMenuItem(MenuItem viewMenuItem) {
        this.viewMenuItem = viewMenuItem;
    }
}
