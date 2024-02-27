package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;

import com.vaadin.flow.component.button.Button;

/**
 * Mediator for the view AACL fund pool widget.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 02/05/2020
 *
 * @author Stanislau Rudak
 */
class ViewAaclFundPoolMediator implements IMediator {

    private Button deleteButton;

    @Override
    public void applyPermissions() {
        deleteButton.setVisible(ForeignSecurityUtils.hasDeleteFundPoolPermission());
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}
