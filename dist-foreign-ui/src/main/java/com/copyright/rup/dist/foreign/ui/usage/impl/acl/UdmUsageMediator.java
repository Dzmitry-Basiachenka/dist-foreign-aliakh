package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.MenuBar;

/**
 * Mediator for the UDM usages widget.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/11/21
 *
 * @author Anton Azarenka
 */
public class UdmUsageMediator implements IMediator {

    private MenuBar batchMenuItem;
    private MenuBar assignmentMenuBar;

    @Override
    public void applyPermissions() {
        boolean isSpecialist = ForeignSecurityUtils.hasSpecialistPermission();
        batchMenuItem.setVisible(isSpecialist);
        assignmentMenuBar.setVisible(isSpecialist || ForeignSecurityUtils.hasManagerPermission() ||
            ForeignSecurityUtils.hasResearcherPermission());
    }

    public void setBatchMenuItem(MenuBar batchMenuItem) {
        this.batchMenuItem = batchMenuItem;
    }

    public void setAssignmentMenuBar(MenuBar assignmentMenuBar) {
        this.assignmentMenuBar = assignmentMenuBar;
    }
}
