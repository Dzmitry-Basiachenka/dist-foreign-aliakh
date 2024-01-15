package com.copyright.rup.dist.foreign.vui.usage.impl;

import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;

import com.vaadin.flow.component.button.Button;

/**
 * Mediator for the view usage batches or fund pools widget.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 02/22/2019
 *
 * @author Uladzislau Shalamitski
 */
public class ViewUsageBatchMediator implements IMediator {

    private Button deleteButton;

    @Override
    public void applyPermissions() {
        deleteButton.setVisible(ForeignSecurityUtils.hasDeleteUsagePermission());
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}
