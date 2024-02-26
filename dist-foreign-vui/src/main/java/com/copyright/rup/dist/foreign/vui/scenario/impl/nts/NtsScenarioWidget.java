package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenarioController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenarioWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonScenarioWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * Implementation of {@link INtsScenarioWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/2019
 *
 * @author Stanislau Rudak
 */
public class NtsScenarioWidget extends CommonScenarioWidget implements INtsScenarioWidget {

    private static final long serialVersionUID = -1182854995445399950L;

    private final INtsScenarioController scenarioController;

    private OnDemandFileDownloader exportDetailsFileDownloader;
    private OnDemandFileDownloader exportScenarioFileDownloader;
    private Button excludeRhButton;
    private NtsScenarioMediator mediator;

    /**
     * Constructor.
     *
     * @param ntsScenarioController instance of {@link INtsScenarioController}
     */
    public NtsScenarioWidget(INtsScenarioController ntsScenarioController) {
        this.scenarioController = ntsScenarioController;
        super.addAttachListener(event -> refresh());
    }

    @Override
    public IMediator initMediator() {
        mediator = new NtsScenarioMediator();
        mediator.setExcludeRhButton(excludeRhButton);
        mediator.setExportDetailsFileDownloader(exportDetailsFileDownloader);
        mediator.setExportScenarioFileDownloader(exportScenarioFileDownloader);
        mediator.setEmptyUsagesLayout(getEmptyUsagesLayout());
        mediator.setRightsholderGrid(getRightsholdersGrid());
        mediator.setSearchWidget(getSearchWidget());
        mediator.setMenuButton(getMenuButton());
        return mediator;
    }

    @Override
    public void refreshTable() {
        getDataProvider().refreshAll();
        updateFooter();
    }

    @Override
    public void refresh() {
        mediator.onScenarioUpdated(scenarioController.isScenarioEmpty(), scenarioController.getScenario());
    }

    @Override
    public void fireWidgetEvent(ComponentEvent<Component> event) {
        fireEvent(event);
    }

    @Override
    protected HorizontalLayout initButtons() {
        excludeRhButton = Buttons.createButton(ForeignUi.getMessage("button.exclude_by_rightsholder"));
        excludeRhButton.addClickListener(event -> scenarioController.onExcludeRhButtonClicked());
        var exportDetailsButton = Buttons.createButton(ForeignUi.getMessage("button.export_details"));
        exportDetailsFileDownloader =
            new OnDemandFileDownloader(scenarioController.getExportScenarioUsagesStreamSource().getSource());
        exportDetailsFileDownloader.extend(exportDetailsButton);
        var exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportScenarioFileDownloader = new OnDemandFileDownloader(
            scenarioController.getExportScenarioRightsholderTotalsStreamSource().getSource());
        exportScenarioFileDownloader.extend(exportButton);
        var buttonsLayout = new HorizontalLayout(excludeRhButton, exportDetailsFileDownloader,
            exportScenarioFileDownloader, Buttons.createCloseButton(this));
        VaadinUtils.addComponentStyle(buttonsLayout, "scenario-buttons-layout");
        buttonsLayout.setWidthFull();
        buttonsLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        return buttonsLayout;
    }
}
