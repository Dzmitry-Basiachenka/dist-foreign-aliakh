package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;

/**
 * Mediator for the view ACL usage batch window.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/03/2022
 *
 * @author Mikita Maistrenka
 */
public class ViewAclUsageBatchMediator implements IMediator {

    private Button deleteButton;

    @Override
    public void applyPermissions() {
        deleteButton.setVisible(ForeignSecurityUtils.hasSpecialistPermission());
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}
