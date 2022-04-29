package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.MenuBar;

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

    @Override
    public void applyPermissions() {
        fundPoolMenuBar.setVisible(ForeignSecurityUtils.hasSpecialistPermission());
    }

    public void setFundPoolMenuBar(MenuBar fundPoolMenuBar) {
        this.fundPoolMenuBar = fundPoolMenuBar;
    }
}
