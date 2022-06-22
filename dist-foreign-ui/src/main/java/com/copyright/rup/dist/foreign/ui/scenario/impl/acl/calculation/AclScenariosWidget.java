package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosWidget;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * Implementation of {@link IAclScenariosWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclScenariosWidget extends VerticalLayout implements IAclScenariosWidget {

    private Grid<Scenario> scenarioGrid;
    private Panel metadataPanel;

    @Override
    public void refresh() {
        //TODO implement
    }

    @Override
    @SuppressWarnings("unchecked")
    public IAclScenariosWidget init() {
        setSizeFull();
        initGrid();
        initMetadataPanel();
        HorizontalLayout horizontalLayout = new HorizontalLayout(scenarioGrid, metadataPanel);
        horizontalLayout.setSizeFull();
        horizontalLayout.setExpandRatio(scenarioGrid, 0.7f);
        horizontalLayout.setExpandRatio(metadataPanel, 0.3f);
        addComponents(horizontalLayout);
        setExpandRatio(horizontalLayout, 1);
        setSpacing(false);
        setMargin(false);
        return this;
    }

    @Override
    public void setController(IAclScenariosController controller) {
    }

    private void initGrid() {
        //TODO {dbasiachenka} implement
        scenarioGrid = new Grid<>();
        addColumns();
        scenarioGrid.setSizeFull();
        VaadinUtils.addComponentStyle(scenarioGrid, "acl-scenarios-table");
    }

    private void addColumns() {
        //TODO {dbasiachenka} implement
    }

    private void initMetadataPanel() {
        //TODO {aliakh} implement
        metadataPanel = new Panel();
        metadataPanel.setSizeFull();
        VaadinUtils.addComponentStyle(metadataPanel, "acl-scenarios-metadata");
    }
}
