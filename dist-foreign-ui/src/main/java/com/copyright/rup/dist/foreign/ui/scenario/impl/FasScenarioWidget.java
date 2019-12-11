package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IFasScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IFasScenarioWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
public class FasScenarioWidget extends CommonScenarioWidget<IFasScenarioWidget, IFasScenarioController>
    implements IFasScenarioWidget {

    private VerticalLayout emptyUsagesLayout;
    private Button exportDetailsButton;
    private Button exportButton;
    private Button excludeByRroButton;
    private Button excludeByPayeeButton;
    private FasScenarioMediator mediator;

    @Override
    public void refresh() {
        mediator.onScenarioUpdated(getController().isScenarioEmpty(), getController().getScenario());
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
        mediator.setExcludeByPayeeButton(excludeByPayeeButton);
        mediator.setExportDetailsButton(exportDetailsButton);
        mediator.setExportButton(exportButton);
        mediator.setEmptyUsagesLayout(emptyUsagesLayout);
        mediator.setRightsholderGrid(getRightsholdersGrid());
        mediator.setSearchWidget(getSearchWidget());
        return mediator;
    }

    @Override
    protected VerticalLayout initLayout(VerticalLayout searchLayout, Grid<RightsholderTotalsHolder> grid,
                                        HorizontalLayout buttons) {
        initEmptyScenarioMessage();
        VerticalLayout layout = new VerticalLayout(searchLayout, grid, emptyUsagesLayout, buttons);
        layout.setExpandRatio(grid, 1);
        layout.setExpandRatio(emptyUsagesLayout, 1);
        return layout;
    }

    @Override
    protected HorizontalLayout initButtons() {
        exportDetailsButton = Buttons.createButton(ForeignUi.getMessage("button.export_details"));
        OnDemandFileDownloader exportDetailsFileDownloader =
            new OnDemandFileDownloader(getController().getExportScenarioUsagesStreamSource().getSource());
        exportDetailsFileDownloader.extend(exportDetailsButton);
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader exportScenarioFileDownloader =
            new OnDemandFileDownloader(getController().getExportScenarioRightsholderTotalsStreamSource().getSource());
        exportScenarioFileDownloader.extend(exportButton);
        excludeByRroButton = new Button(ForeignUi.getMessage("button.exclude_by_rro"));
        excludeByRroButton.addClickListener(event -> getController().onExcludeByRroClicked());
        excludeByPayeeButton = new Button(ForeignUi.getMessage("button.exclude_by_payee"));
        excludeByPayeeButton.addClickListener(event -> getController().onExcludeByPayeeClicked());
        HorizontalLayout buttons = new HorizontalLayout(excludeByRroButton, excludeByPayeeButton, exportDetailsButton,
            exportButton, Buttons.createCloseButton(this));
        VaadinUtils.addComponentStyle(buttons, "scenario-buttons-layout");
        buttons.setMargin(new MarginInfo(false, true, true, false));
        return buttons;
    }

    private void initEmptyScenarioMessage() {
        Label emptyScenarioMessage =
            new Label(ForeignUi.getMessage("label.content.empty_scenario"), ContentMode.HTML);
        emptyScenarioMessage.setSizeUndefined();
        emptyScenarioMessage.addStyleName(Cornerstone.LABEL_H2);
        emptyUsagesLayout = new VerticalLayout(emptyScenarioMessage);
        emptyUsagesLayout.setComponentAlignment(emptyScenarioMessage, Alignment.MIDDLE_CENTER);
        emptyUsagesLayout.setSizeFull();
        emptyUsagesLayout.setVisible(false);
    }
}
