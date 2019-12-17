package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

/**
 * Mediator class for {@link FasScenarioWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
class FasScenarioMediator implements IMediator {

    private Button excludeByRroButton;
    private Button excludeByPayeeButton;
    private Button exportDetailsButton;
    private Button exportButton;
    private Grid<RightsholderTotalsHolder> rightsholderGrid;
    private SearchWidget searchWidget;
    private VerticalLayout emptyUsagesLayout;

    @Override
    public void applyPermissions() {
        boolean excludePermission = ForeignSecurityUtils.hasExcludeFromScenarioPermission();
        excludeByRroButton.setVisible(excludePermission);
        excludeByPayeeButton.setVisible(excludePermission);
    }

    void setRightsholderGrid(Grid<RightsholderTotalsHolder> rightsholderGrid) {
        this.rightsholderGrid = rightsholderGrid;
    }

    /**
     * Refreshes components state.
     *
     * @param scenarioEmpty is scenario empty or not
     * @param scenario      selected {@link Scenario}
     */
    void onScenarioUpdated(boolean scenarioEmpty, Scenario scenario) {
        boolean excludeEnabled = !scenarioEmpty && ScenarioStatusEnum.IN_PROGRESS == scenario.getStatus();
        excludeByRroButton.setEnabled(excludeEnabled);
        excludeByPayeeButton.setEnabled(excludeEnabled);
        exportDetailsButton.setEnabled(!scenarioEmpty);
        exportButton.setEnabled(!scenarioEmpty);
        rightsholderGrid.setVisible(!scenarioEmpty);
        searchWidget.setVisible(!scenarioEmpty);
        emptyUsagesLayout.setVisible(scenarioEmpty);
    }

    void setExcludeByRroButton(Button excludeByRroButton) {
        this.excludeByRroButton = excludeByRroButton;
    }

    void setExcludeByPayeeButton(Button excludeByPayeeButton) {
        this.excludeByPayeeButton = excludeByPayeeButton;
    }

    void setExportDetailsButton(Button exportDetailsButton) {
        this.exportDetailsButton = exportDetailsButton;
    }

    void setExportButton(Button exportButton) {
        this.exportButton = exportButton;
    }

    void setSearchWidget(SearchWidget searchWidget) {
        this.searchWidget = searchWidget;
    }

    void setEmptyUsagesLayout(VerticalLayout emptyUsagesLayout) {
        this.emptyUsagesLayout = emptyUsagesLayout;
    }
}
