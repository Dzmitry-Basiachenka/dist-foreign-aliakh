package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenarioWidget;
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
 * Implementation of {@link INtsScenarioWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
public class NtsScenarioWidget extends CommonScenarioWidget implements INtsScenarioWidget {

    private static final long serialVersionUID = 5238463217370047408L;

    private final INtsScenarioController scenarioController;
    private Button exportDetailsButton;
    private Button exportButton;
    private Button excludeRhButton;
    private NtsScenarioMediator mediator;

    /**
     * Constructor.
     *
     * @param ntsScenarioController instance of {@link INtsScenarioController}
     */
    public NtsScenarioWidget(INtsScenarioController ntsScenarioController) {
        this.scenarioController = ntsScenarioController;
    }

    @Override
    public IMediator initMediator() {
        mediator = new NtsScenarioMediator();
        mediator.setExcludeRhButton(excludeRhButton);
        mediator.setExportButton(exportButton);
        mediator.setExportDetailsButton(exportDetailsButton);
        mediator.setEmptyUsagesLayout(getEmptyUsagesLayout());
        mediator.setRightsholderGrid(getRightsholdersGrid());
        mediator.setSearchWidget(getSearchWidget());
        return mediator;
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
    public void refreshTable() {
        getDataProvider().refreshAll();
        updateFooter();
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
        excludeRhButton = Buttons.createButton(ForeignUi.getMessage("button.exclude_by_rightsholder"));
        excludeRhButton.addClickListener(event -> scenarioController.onExcludeRhButtonClicked());
        VaadinUtils.setButtonsAutoDisabled(excludeRhButton);
        exportDetailsButton = Buttons.createButton(ForeignUi.getMessage("button.export_details"));
        OnDemandFileDownloader exportDetailsFileDownloader =
            new OnDemandFileDownloader(scenarioController.getExportScenarioUsagesStreamSource().getSource());
        exportDetailsFileDownloader.extend(exportDetailsButton);
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader exportScenarioFileDownloader = new OnDemandFileDownloader(
            scenarioController.getExportScenarioRightsholderTotalsStreamSource().getSource());
        exportScenarioFileDownloader.extend(exportButton);
        HorizontalLayout buttons =
            new HorizontalLayout(excludeRhButton, exportDetailsButton, exportButton, Buttons.createCloseButton(this));
        VaadinUtils.addComponentStyle(buttons, "scenario-buttons-layout");
        buttons.setMargin(new MarginInfo(false, true, true, false));
        return buttons;
    }

    @Override
    public void refresh() {
        mediator.onScenarioUpdated(scenarioController.isScenarioEmpty(), scenarioController.getScenario());
    }
}
