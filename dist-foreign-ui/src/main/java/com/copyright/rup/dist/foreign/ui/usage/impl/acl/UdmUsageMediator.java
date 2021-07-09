package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;
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

    private Button editButton;
    private MenuBar batchMenuBar;
    private MenuBar assignmentMenuBar;

    @Override
    public void applyPermissions() {
        boolean isSpecialist = ForeignSecurityUtils.hasSpecialistPermission();
        boolean isManager = ForeignSecurityUtils.hasManagerPermission();
        batchMenuBar.setVisible(isSpecialist);
        assignmentMenuBar.setVisible(isSpecialist || isManager || ForeignSecurityUtils.hasResearcherPermission());
        editButton.setVisible(isSpecialist || isManager);
    }

    public void setBatchMenuBar(MenuBar batchMenuBar) {
        this.batchMenuBar = batchMenuBar;
    }

    public void setAssignmentMenuBar(MenuBar assignmentMenuBar) {
        this.assignmentMenuBar = assignmentMenuBar;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
    }
}
