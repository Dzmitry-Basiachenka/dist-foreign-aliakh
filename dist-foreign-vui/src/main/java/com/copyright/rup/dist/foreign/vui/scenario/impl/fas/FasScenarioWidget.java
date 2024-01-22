package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenarioWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonScenarioWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * Implementation of {@link IFasScenarioWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
public class FasScenarioWidget extends CommonScenarioWidget implements IFasScenarioWidget {

    private static final long serialVersionUID = -6382799539636718478L;

    private final IFasScenarioController scenarioController;

    /**
     * Constructor.
     *
     * @param scenarioController instance of {@link IFasScenarioController}
     */
    public FasScenarioWidget(IFasScenarioController scenarioController) {
        this.scenarioController = scenarioController;
    }

    @Override
    public void refresh() {
        //TODO: {dbasiachenka} implement
    }

    @Override
    public void refreshTable() {
        getDataProvider().refreshAll();
        updateFooter();
    }

    @Override
    protected HorizontalLayout initButtons() {
        var excludeByRroButton = Buttons.createButton(ForeignUi.getMessage("button.exclude_by_rro"));
        excludeByRroButton.addClickListener(event -> scenarioController.onExcludeByRroClicked());
        var exportDetailsButton = Buttons.createButton(ForeignUi.getMessage("button.export_details"));
        var exportDetailsFileDownloader =
            new OnDemandFileDownloader(scenarioController.getExportScenarioUsagesStreamSource().getSource());
        exportDetailsFileDownloader.extend(exportDetailsButton);
        var exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        var exportScenarioFileDownloader = new OnDemandFileDownloader(
            scenarioController.getExportScenarioRightsholderTotalsStreamSource().getSource());
        exportScenarioFileDownloader.extend(exportButton);
        var buttonsLayout = new HorizontalLayout(excludeByRroButton, exportDetailsFileDownloader,
            exportScenarioFileDownloader, Buttons.createCloseButton(this));
        VaadinUtils.addComponentStyle(buttonsLayout, "scenario-buttons-layout");
        VaadinUtils.setMaxComponentsWidth(buttonsLayout);
        buttonsLayout.setWidth("100%");
        buttonsLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        return buttonsLayout;
    }
}
