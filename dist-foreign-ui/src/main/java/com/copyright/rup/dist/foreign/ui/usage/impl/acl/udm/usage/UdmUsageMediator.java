package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

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
    private Button multipleEditButton;
    private Button publishButton;
    private MenuBar batchMenuBar;
    private MenuBar assignmentMenuBar;

    @Override
    public void applyPermissions() {
        boolean isSpecialist = ForeignSecurityUtils.hasSpecialistPermission();
        boolean isManager = ForeignSecurityUtils.hasManagerPermission();
        boolean isResearcher = ForeignSecurityUtils.hasResearcherPermission();
        batchMenuBar.setVisible(isSpecialist);
        assignmentMenuBar.setVisible(isSpecialist || isManager || isResearcher);
        editButton.setVisible(isSpecialist || isManager || isResearcher);
        multipleEditButton.setVisible(isSpecialist || isManager || isResearcher);
        publishButton.setVisible(isSpecialist);
    }

    public void setPublishButton(Button publishButton) {
        this.publishButton = publishButton;
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

    public void setMultipleEditButton(Button multipleEditButton) {
        this.multipleEditButton = multipleEditButton;
    }
}
