package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;
import com.vaadin.ui.Button;

/**
 * Mediator for the view usage batches or fund pools widget.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 02/22/19
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
