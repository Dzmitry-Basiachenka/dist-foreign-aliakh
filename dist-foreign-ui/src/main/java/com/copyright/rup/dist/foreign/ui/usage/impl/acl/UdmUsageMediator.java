package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Grid;
import com.vaadin.ui.MenuBar;
import org.apache.commons.collections4.CollectionUtils;

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
    private MenuBar.MenuItem assignItem;
    private MenuBar.MenuItem unassignItem;
    private Grid<UdmUsageDto> usageGrid;

    @Override
    public void applyPermissions() {
        boolean isSpecialist = ForeignSecurityUtils.hasSpecialistPermission();
        boolean isSelectionAvailable = isSpecialist || ForeignSecurityUtils.hasManagerPermission() ||
            ForeignSecurityUtils.hasResearcherPermission();
        batchMenuItem.setVisible(isSpecialist);
        assignmentMenuBar.setVisible(isSelectionAvailable);
        if (isSelectionAvailable) {
            usageGrid.setSelectionMode(Grid.SelectionMode.MULTI);
            usageGrid.addSelectionListener(event -> {
                boolean isEnabled = CollectionUtils.isNotEmpty(event.getAllSelectedItems());
                assignItem.setEnabled(isEnabled);
                unassignItem.setEnabled(isEnabled);
            });
        } else {
            usageGrid.setSelectionMode(Grid.SelectionMode.NONE);
        }
    }

    public void setBatchMenuItem(MenuBar batchMenuItem) {
        this.batchMenuItem = batchMenuItem;
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
}
