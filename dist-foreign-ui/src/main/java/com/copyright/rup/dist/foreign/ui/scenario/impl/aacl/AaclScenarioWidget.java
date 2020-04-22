package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenarioWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * Implementation of {@link IAaclScenarioWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/27/20
 *
 * @author Stanislau Rudak
 */
public class AaclScenarioWidget extends CommonScenarioWidget implements IAaclScenarioWidget {

    private final IAaclScenarioController scenarioController;
    private Button exportDetailsButton;
    private Button exportButton;

    /**
     * Constructor.
     *
     * @param aaclScenarioController instance of {@link IAaclScenarioController}
     */
    public AaclScenarioWidget(IAaclScenarioController aaclScenarioController) {
        this.scenarioController = aaclScenarioController;
    }

    @Override
    protected VerticalLayout initLayout(VerticalLayout searchLayout, Grid<RightsholderTotalsHolder> grid,
                                        VerticalLayout emptyUsagesLayout, HorizontalLayout buttons) {
        VerticalLayout layout = new VerticalLayout(searchLayout, grid, emptyUsagesLayout, buttons);
        layout.setExpandRatio(grid, 1);
        layout.setExpandRatio(emptyUsagesLayout, 1);
        updateLayouts();
        return layout;
    }

    @Override
    protected HorizontalLayout initButtons() {
        exportDetailsButton = Buttons.createButton(ForeignUi.getMessage("button.export_details"));
        OnDemandFileDownloader exportDetailsFileDownloader =
            new OnDemandFileDownloader(scenarioController.getExportScenarioUsagesStreamSource().getSource());
        exportDetailsFileDownloader.extend(exportDetailsButton);
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader exportScenarioFileDownloader = new OnDemandFileDownloader(
            scenarioController.getExportScenarioRightsholderTotalsStreamSource().getSource());
        exportScenarioFileDownloader.extend(exportButton);
        HorizontalLayout buttons =
            new HorizontalLayout(exportDetailsButton, exportButton, Buttons.createCloseButton(this));
        VaadinUtils.addComponentStyle(buttons, "scenario-buttons-layout");
        buttons.setMargin(new MarginInfo(false, true, true, false));
        return buttons;
    }

    private void updateLayouts() {
        boolean scenarioEmpty = scenarioController.isScenarioEmpty();
        exportDetailsButton.setEnabled(!scenarioEmpty);
        exportButton.setEnabled(!scenarioEmpty);
        getRightsholdersGrid().setVisible(!scenarioEmpty);
        getSearchWidget().setVisible(!scenarioEmpty);
        getEmptyUsagesLayout().setVisible(scenarioEmpty);
    }
}
