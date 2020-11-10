package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenarioWidget;
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
    private Button excludeByPayeeButton;
    private AaclScenarioMediator mediator;

    /**
     * Constructor.
     *
     * @param aaclScenarioController instance of {@link IAaclScenarioController}
     */
    public AaclScenarioWidget(IAaclScenarioController aaclScenarioController) {
        this.scenarioController = aaclScenarioController;
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
    public void refresh() {
        mediator.onScenarioUpdated(scenarioController.isScenarioEmpty(), scenarioController.getScenario());
    }

    @Override
    public void refreshTable() {
        getDataProvider().refreshAll();
        updateFooter();
    }

    @Override
    public IMediator initMediator() {
        mediator = new AaclScenarioMediator();
        mediator.setExcludeByPayeeButton(excludeByPayeeButton);
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
        excludeByPayeeButton = Buttons.createButton(ForeignUi.getMessage("button.exclude_by_payee"));
        excludeByPayeeButton.addClickListener(event -> scenarioController.onExcludeByPayeeClicked());
        HorizontalLayout buttons = new HorizontalLayout(excludeByPayeeButton, exportDetailsButton, exportButton,
            Buttons.createCloseButton(this));
        VaadinUtils.addComponentStyle(buttons, "scenario-buttons-layout");
        buttons.setMargin(new MarginInfo(false, true, true, false));
        return buttons;
    }
}
