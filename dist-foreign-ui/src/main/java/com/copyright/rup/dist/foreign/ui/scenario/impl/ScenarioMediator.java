package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
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
    private Grid<RightsholderTotalsHolder> rightsholderGrid;
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

    public void setRightsholderGrid(Grid<RightsholderTotalsHolder> rightsholderGrid) {
        this.rightsholderGrid = rightsholderGrid;
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
     * @param status        {@link ScenarioStatusEnum} of selected scenario
     */
    void onScenarioUpdated(boolean scenarioEmpty, ScenarioStatusEnum status) {
        excludeButton.setEnabled(!scenarioEmpty && ScenarioStatusEnum.IN_PROGRESS == status);
        exportButton.setEnabled(!scenarioEmpty);
        rightsholderGrid.setVisible(!scenarioEmpty);
        searchWidget.setVisible(!scenarioEmpty);
        emptyUsagesLayout.setVisible(scenarioEmpty);
    }
}
