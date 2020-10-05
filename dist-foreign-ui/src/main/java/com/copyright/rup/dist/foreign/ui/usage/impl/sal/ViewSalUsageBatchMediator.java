package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;

/**
 * Mediator for the SAL usage batches view.
 *
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/28/20
 *
 * @author Anton Azarenka
 */
public class ViewSalUsageBatchMediator implements IMediator {

    private Button deleteBatchButton;
    private Button deleteUsageDetailsButton;

    @Override
    public void applyPermissions() {
        deleteBatchButton.setVisible(ForeignSecurityUtils.hasDeleteUsagePermission());
        deleteUsageDetailsButton.setVisible(ForeignSecurityUtils.hasDeleteUsagePermission());
    }

    public void setDeleteBatchButton(Button deleteBatchButton) {
        this.deleteBatchButton = deleteBatchButton;
    }

    public void setDeleteUsageDetailsButton(Button deleteUsageDetailsButton) {
        this.deleteUsageDetailsButton = deleteUsageDetailsButton;
    }
}
