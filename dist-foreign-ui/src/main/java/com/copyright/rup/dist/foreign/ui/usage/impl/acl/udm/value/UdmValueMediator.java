package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;

/**
 * Mediator for the UDM values widget.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/21
 *
 * @author Uladzislau Shalamitski
 */
public class UdmValueMediator implements IMediator {

    private Button populateButton;

    @Override
    public void applyPermissions() {
        populateButton.setVisible(ForeignSecurityUtils.hasSpecialistPermission());
    }

    public void setPopulateButton(Button populateButton) {
        this.populateButton = populateButton;
    }
}
