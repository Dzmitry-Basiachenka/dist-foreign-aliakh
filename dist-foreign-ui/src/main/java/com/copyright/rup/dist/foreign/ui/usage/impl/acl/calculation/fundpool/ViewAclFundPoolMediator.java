package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;

/**
 * Mediator for the view ACL fund pool widget.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/17/2022
 *
 * @author Anton Azarenka
 */
public class ViewAclFundPoolMediator implements IMediator {

    private Button deleteButton;

    @Override
    public void applyPermissions() {
        deleteButton.setVisible(ForeignSecurityUtils.hasSpecialistPermission());
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}
