package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * Mediator for the ACL usage widget.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/07/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclUsageMediator implements IMediator {

    private MenuBar.MenuItem createUsageBatchMenuItem;
    private Button editButton;

    @Override
    public void applyPermissions() {
        boolean isSpecialist = ForeignSecurityUtils.hasSpecialistPermission();
        createUsageBatchMenuItem.setVisible(isSpecialist);
        editButton.setVisible(isSpecialist);
    }

    public void setCreateUsageBatchMenuItem(MenuItem createUsageBatchMenuItem) {
        this.createUsageBatchMenuItem = createUsageBatchMenuItem;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
    }
}
