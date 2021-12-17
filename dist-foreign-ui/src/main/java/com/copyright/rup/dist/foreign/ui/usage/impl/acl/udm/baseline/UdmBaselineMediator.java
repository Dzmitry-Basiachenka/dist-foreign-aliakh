package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;
import com.vaadin.ui.Button;

/**
 * Mediator for the UDM baseline usages widget.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 15/12/21
 *
 * @author Uladzislau Shalamitski
 */
public class UdmBaselineMediator implements IMediator {

    private Button deleteButton;

    @Override
    public void applyPermissions() {
        deleteButton.setVisible(ForeignSecurityUtils.hasSpecialistPermission());
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}
