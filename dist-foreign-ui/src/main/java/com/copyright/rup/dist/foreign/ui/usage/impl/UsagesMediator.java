package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;

/**
 * Mediator for the usages widget.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/22/17
 *
 * @author Ihar Suvorau
 */
class UsagesMediator implements IMediator {

    private Button loadUsageButton;
    private Button deleteUsageButton;

    /**
     * Sets load usage button.
     *
     * @param loadUsageButton load usage button
     */
    void setLoadUsageButton(Button loadUsageButton) {
        this.loadUsageButton = loadUsageButton;
    }

    /**
     * Sets delete usage button.
     *
     * @param deleteUsageButton delete usage button
     */
    void setDeleteUsageButton(Button deleteUsageButton) {
        this.deleteUsageButton = deleteUsageButton;
    }

    @Override
    public void applyPermissions() {
        loadUsageButton.setVisible(ForeignSecurityUtils.hasLoadUsagePermission());
        deleteUsageButton.setVisible(ForeignSecurityUtils.hasDeleteUsagePermission());
    }
}
