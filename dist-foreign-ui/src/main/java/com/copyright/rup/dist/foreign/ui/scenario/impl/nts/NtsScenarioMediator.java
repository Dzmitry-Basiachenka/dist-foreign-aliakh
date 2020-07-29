package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

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
 * Mediator class for {@link NtsScenarioWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/20
 *
 * @author Anton Azarenka
 */
public class NtsScenarioMediator implements IMediator {

    private Button exportDetailsButton;
    private Button exportButton;
    private Button excludeRhButton;
    private Grid<RightsholderTotalsHolder> rightsholderGrid;
    private SearchWidget searchWidget;
    private VerticalLayout emptyUsagesLayout;

    @Override
    public void applyPermissions() {
        boolean excludePermission = ForeignSecurityUtils.hasExcludeFromScenarioPermission();
        excludeRhButton.setVisible(excludePermission);
    }

    /**
     * Refreshes components state.
     *
     * @param scenarioEmpty is scenario empty or not
     * @param scenario      selected {@link Scenario}
     */
    void onScenarioUpdated(boolean scenarioEmpty, Scenario scenario) {
        boolean excludeEnabled = !scenarioEmpty && ScenarioStatusEnum.IN_PROGRESS == scenario.getStatus();
        excludeRhButton.setEnabled(excludeEnabled);
        exportDetailsButton.setEnabled(!scenarioEmpty);
        exportButton.setEnabled(!scenarioEmpty);
        rightsholderGrid.setVisible(!scenarioEmpty);
        searchWidget.setVisible(!scenarioEmpty);
        emptyUsagesLayout.setVisible(scenarioEmpty);
    }

    void setExportDetailsButton(Button exportDetailsButton) {
        this.exportDetailsButton = exportDetailsButton;
    }

    void setExportButton(Button exportButton) {
        this.exportButton = exportButton;
    }

    void setExcludeRhButton(Button excludeRhButton) {
        this.excludeRhButton = excludeRhButton;
    }

    void setRightsholderGrid(Grid<RightsholderTotalsHolder> rightsholderGrid) {
        this.rightsholderGrid = rightsholderGrid;
    }

    void setSearchWidget(SearchWidget searchWidget) {
        this.searchWidget = searchWidget;
    }

    void setEmptyUsagesLayout(VerticalLayout emptyUsagesLayout) {
        this.emptyUsagesLayout = emptyUsagesLayout;
    }
}
