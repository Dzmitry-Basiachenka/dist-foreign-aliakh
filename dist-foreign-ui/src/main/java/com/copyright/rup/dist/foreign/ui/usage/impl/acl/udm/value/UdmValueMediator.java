package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;

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
    private MenuBar assignmentMenuBar;
    private Button calculateProxyValuesButton;
    private Button publishButton;

    @Override
    public void applyPermissions() {
        boolean isSpecialist = ForeignSecurityUtils.hasSpecialistPermission();
        boolean isManager = ForeignSecurityUtils.hasManagerPermission();
        boolean isResearcher = ForeignSecurityUtils.hasResearcherPermission();
        populateButton.setVisible(isSpecialist);
        assignmentMenuBar.setVisible(isSpecialist || isManager || isResearcher);
        calculateProxyValuesButton.setVisible(isSpecialist);
        publishButton.setVisible(isSpecialist);
    }

    public void setPopulateButton(Button populateButton) {
        this.populateButton = populateButton;
    }

    public void setAssignmentMenuBar(MenuBar assignmentMenuBar) {
        this.assignmentMenuBar = assignmentMenuBar;
    }

    public void setCalculateProxyValuesButton(Button calculateProxyValuesButton) {
        this.calculateProxyValuesButton = calculateProxyValuesButton;
    }

    public void setPublishButton(Button publishButton) {
        this.publishButton = publishButton;
    }
}
