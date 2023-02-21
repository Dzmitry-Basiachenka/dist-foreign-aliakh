package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenarioWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

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

    private final IFasScenarioController scenarioController;
    private Button exportDetailsButton;
    private Button exportButton;
    private Button excludeByRroButton;
    private FasScenarioMediator mediator;

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
        mediator.onScenarioUpdated(scenarioController.isScenarioEmpty(), scenarioController.getScenario());
    }

    @Override
    public void refreshTable() {
        getDataProvider().refreshAll();
        updateFooter();
    }

    @Override
    public void attach() {
        super.attach();
        refresh();
    }

    @Override
    public void fireWidgetEvent(Event event) {
        fireEvent(event);
    }

    @Override
    public IMediator initMediator() {
        mediator = new FasScenarioMediator();
        mediator.setExcludeByRroButton(excludeByRroButton);
        mediator.setExportDetailsButton(exportDetailsButton);
        mediator.setExportButton(exportButton);
        mediator.setEmptyUsagesLayout(getEmptyUsagesLayout());
        mediator.setRightsholderGrid(getRightsholdersGrid());
        mediator.setSearchWidget(getSearchWidget());
        return mediator;
    }

    @Override
    protected VerticalLayout initLayout(VerticalLayout searchLayout, Grid<RightsholderTotalsHolder> grid,
                                        VerticalLayout emptyUsagesLayout, HorizontalLayout buttons) {
        VerticalLayout layout = new VerticalLayout(searchLayout, grid, emptyUsagesLayout, buttons);
        layout.setExpandRatio(grid, 1);
        layout.setExpandRatio(emptyUsagesLayout, 1);
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
        excludeByRroButton = new Button(ForeignUi.getMessage("button.exclude_by_rro"));
        excludeByRroButton.addClickListener(event -> scenarioController.onExcludeByRroClicked());
        VaadinUtils.setButtonsAutoDisabled(excludeByRroButton);
        HorizontalLayout buttons = new HorizontalLayout(excludeByRroButton, exportDetailsButton, exportButton,
            Buttons.createCloseButton(this));
        VaadinUtils.addComponentStyle(buttons, "scenario-buttons-layout");
        buttons.setMargin(new MarginInfo(false, true, true, false));
        return buttons;
    }
}
