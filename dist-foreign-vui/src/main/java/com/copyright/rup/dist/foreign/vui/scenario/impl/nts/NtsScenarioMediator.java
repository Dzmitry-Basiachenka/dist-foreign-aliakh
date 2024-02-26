package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

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
 * Mediator class for {@link NtsScenarioWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Anton Azarenka
 */
public class NtsScenarioMediator implements IMediator {

    private Button excludeRhButton;
    private OnDemandFileDownloader exportDetailsFileDownloader;
    private OnDemandFileDownloader exportScenarioFileDownloader;
    private Grid<RightsholderTotalsHolder> rightsholderGrid;
    private SearchWidget searchWidget;
    private VerticalLayout emptyUsagesLayout;
    private Button menuButton;

    @Override
    public void applyPermissions() {
        excludeRhButton.setVisible(ForeignSecurityUtils.hasExcludeFromScenarioPermission());
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
        exportDetailsFileDownloader.setEnabled(!scenarioEmpty);
        exportScenarioFileDownloader.setEnabled(!scenarioEmpty);
        rightsholderGrid.setVisible(!scenarioEmpty);
        searchWidget.setVisible(!scenarioEmpty);
        emptyUsagesLayout.setVisible(scenarioEmpty);
        menuButton.setVisible(!scenarioEmpty);
    }

    void setExcludeRhButton(Button excludeRhButton) {
        this.excludeRhButton = excludeRhButton;
    }

    void setExportDetailsFileDownloader(OnDemandFileDownloader exportDetailsFileDownloader) {
        this.exportDetailsFileDownloader = exportDetailsFileDownloader;
    }

    void setExportScenarioFileDownloader(OnDemandFileDownloader exportScenarioFileDownloader) {
        this.exportScenarioFileDownloader = exportScenarioFileDownloader;
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

    void setMenuButton(Button menuButton) {
        this.menuButton = menuButton;
    }
}
