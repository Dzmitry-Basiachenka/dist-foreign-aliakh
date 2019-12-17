package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludeUsagesListener;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenarioWidget;
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
 * Implementation of {@link INtsScenarioWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
public class NtsScenarioWidget extends CommonScenarioWidget implements INtsScenarioWidget {

    private final INtsScenarioController scenarioController;

    /**
     * Constructor.
     *
     * @param ntsScenarioController instance of {@link INtsScenarioController}
     */
    public NtsScenarioWidget(INtsScenarioController ntsScenarioController) {
        this.scenarioController = ntsScenarioController;
    }

    @Override
    public void fireWidgetEvent(Event event) {
        fireEvent(event);
    }

    @Override
    public void addListener(IExcludeUsagesListener listener) {
        addListener(ExcludeUsagesEvent.class, listener, IExcludeUsagesListener.EXCLUDE_DETAILS_HANDLER);
    }

    @Override
    protected VerticalLayout initLayout(VerticalLayout searchLayout, Grid<RightsholderTotalsHolder> grid,
                                        HorizontalLayout buttons) {
        VerticalLayout layout = new VerticalLayout(searchLayout, grid, buttons);
        layout.setExpandRatio(grid, 1);
        return layout;
    }

    @Override
    protected HorizontalLayout initButtons() {
        Button exportDetailsButton = Buttons.createButton(ForeignUi.getMessage("button.export_details"));
        OnDemandFileDownloader exportDetailsFileDownloader =
            new OnDemandFileDownloader(scenarioController.getExportScenarioUsagesStreamSource().getSource());
        exportDetailsFileDownloader.extend(exportDetailsButton);
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader exportScenarioFileDownloader = new OnDemandFileDownloader(
            scenarioController.getExportScenarioRightsholderTotalsStreamSource().getSource());
        exportScenarioFileDownloader.extend(exportButton);
        HorizontalLayout buttons =
            new HorizontalLayout(exportDetailsButton, exportButton, Buttons.createCloseButton(this));
        VaadinUtils.addComponentStyle(buttons, "scenario-buttons-layout");
        buttons.setMargin(new MarginInfo(false, true, true, false));
        return buttons;
    }
}
