package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.MenuBar;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;

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

    private static final int EXPECTED_SELECTED_SIZE = 1;
    private Button editButton;
    private MenuBar batchMenuBar;
    private MenuBar assignmentMenuBar;
    private MenuBar.MenuItem assignItem;
    private MenuBar.MenuItem unassignItem;
    private Grid<UdmUsageDto> usageGrid;

    @Override
    public void applyPermissions() {
        boolean isSpecialist = ForeignSecurityUtils.hasSpecialistPermission();
        boolean isManager = ForeignSecurityUtils.hasManagerPermission();
        boolean isSelectionAvailable = isSpecialist || isManager || ForeignSecurityUtils.hasResearcherPermission();
        batchMenuBar.setVisible(isSpecialist);
        assignmentMenuBar.setVisible(isSelectionAvailable);
        editButton.setVisible(isSpecialist || isManager);
        if (isSelectionAvailable) {
            usageGrid.setSelectionMode(Grid.SelectionMode.MULTI);
            usageGrid.addSelectionListener(event -> {
                Set<UdmUsageDto> usageDtos = event.getAllSelectedItems();
                boolean isAssignmentEnabled = CollectionUtils.isNotEmpty(usageDtos);
                assignItem.setEnabled(isAssignmentEnabled);
                unassignItem.setEnabled(isAssignmentEnabled);
                editButton.setEnabled(EXPECTED_SELECTED_SIZE == usageDtos.size());
            });
        } else {
            usageGrid.setSelectionMode(Grid.SelectionMode.NONE);
        }
    }

    public void setBatchMenuBar(MenuBar batchMenuBar) {
        this.batchMenuBar = batchMenuBar;
    }

    public void setAssignmentMenuBar(MenuBar assignmentMenuBar) {
        this.assignmentMenuBar = assignmentMenuBar;
    }

    public void setUsageGrid(Grid<UdmUsageDto> usageGrid) {
        this.usageGrid = usageGrid;
    }

    public void setAssignItem(MenuBar.MenuItem assignItem) {
        this.assignItem = assignItem;
    }

    public void setUnassignItem(MenuBar.MenuItem unassignItem) {
        this.unassignItem = unassignItem;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
    }
}
