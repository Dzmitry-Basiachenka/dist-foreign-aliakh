package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

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
 * Mediator class for {@link AaclScenarioWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/05/2020
 *
 * @author Ihar Suvorau
 */
class AaclScenarioMediator implements IMediator {

    private Button excludeByPayeeButton;
    private Button exportDetailsButton;
    private Button exportButton;
    private Grid<RightsholderTotalsHolder> rightsholderGrid;
    private SearchWidget searchWidget;
    private VerticalLayout emptyUsagesLayout;

    @Override
    public void applyPermissions() {
        boolean excludePermission = ForeignSecurityUtils.hasExcludeFromScenarioPermission();
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
        excludeByPayeeButton.setEnabled(excludeEnabled);
        exportDetailsButton.setEnabled(!scenarioEmpty);
        exportButton.setEnabled(!scenarioEmpty);
        rightsholderGrid.setVisible(!scenarioEmpty);
        searchWidget.setVisible(!scenarioEmpty);
        emptyUsagesLayout.setVisible(scenarioEmpty);
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
