package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

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
    private OnDemandFileDownloader exportDetailsFileDownloader;
    private OnDemandFileDownloader exportScenarioFileDownloader;
    private Grid<RightsholderTotalsHolder> rightsholderGrid;
    private SearchWidget searchWidget;
    private VerticalLayout emptyUsagesLayout;

    @Override
    public void applyPermissions() {
        boolean excludePermission = ForeignSecurityUtils.hasExcludeFromScenarioPermission();
        excludeByRroButton.setVisible(excludePermission);
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
        exportDetailsFileDownloader.setEnabled(!scenarioEmpty);
        exportScenarioFileDownloader.setEnabled(!scenarioEmpty);
        rightsholderGrid.setVisible(!scenarioEmpty);
        emptyUsagesLayout.setVisible(scenarioEmpty);
        searchWidget.setVisible(!scenarioEmpty);
    }

    void setExcludeByRroButton(Button excludeByRroButton) {
        this.excludeByRroButton = excludeByRroButton;
    }

    void setExportDetailsFileDownloader(OnDemandFileDownloader exportDetailsFileDownloader) {
        this.exportDetailsFileDownloader = exportDetailsFileDownloader;
    }

    void setExportScenarioFileDownloader(OnDemandFileDownloader exportScenarioFileDownloader) {
        this.exportScenarioFileDownloader = exportScenarioFileDownloader;
    }

    void setEmptyUsagesLayout(VerticalLayout emptyUsagesLayout) {
        this.emptyUsagesLayout = emptyUsagesLayout;
    }

    void setRightsholderGrid(Grid<RightsholderTotalsHolder> rightsholderGrid) {
        this.rightsholderGrid = rightsholderGrid;
    }

    void setSearchWidget(SearchWidget searchWidget) {
        this.searchWidget = searchWidget;
    }
}
