package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

/**
 * Mediator class for {@link ScenarioWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/13/17
 *
 * @author Aliaksandr Radkevich
 */
class ScenarioMediator implements IMediator {

    private Button excludeButton;
    private Button exportButton;
    private Table rightsholdersTable;
    private SearchWidget searchWidget;
    private VerticalLayout emptyUsagesLayout;

    @Override
    public void applyPermissions() {
        excludeButton.setVisible(ForeignSecurityUtils.hasExcludeFromScenarioPermission());
    }

    void setExcludeButton(Button excludeButton) {
        this.excludeButton = excludeButton;
    }

    void setExportButton(Button exportButton) {
        this.exportButton = exportButton;
    }

    void setRightsholdersTable(Table rightsholdersTable) {
        this.rightsholdersTable = rightsholdersTable;
    }

    void setSearchWidget(SearchWidget searchWidget) {
        this.searchWidget = searchWidget;
    }

    void setEmptyUsagesLayout(VerticalLayout emptyUsagesLayout) {
        this.emptyUsagesLayout = emptyUsagesLayout;
    }

    /**
     * Refreshes components state.
     *
     * @param scenarioEmpty is scenario empty or not
     */
    void onScenarioUpdated(boolean scenarioEmpty) {
        excludeButton.setEnabled(excludeButton.isVisible() && !scenarioEmpty);
        exportButton.setEnabled(!scenarioEmpty);
        rightsholdersTable.setVisible(!scenarioEmpty);
        searchWidget.setVisible(!scenarioEmpty);
        emptyUsagesLayout.setVisible(scenarioEmpty);
    }
}
