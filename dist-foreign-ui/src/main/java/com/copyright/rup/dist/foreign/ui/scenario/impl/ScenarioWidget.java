package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Widget for viewing information about rightsholders, payee and their associated amounts grouped by rightsholder.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/31/17
 *
 * @author Ihar Suvorau
 */
public class ScenarioWidget extends Window implements IScenarioWidget {

    private ScenarioController controller;
    private SearchWidget searchWidget;
    private RightsholderTotalsHolderTable table;
    private Scenario scenario;

    @Override
    @SuppressWarnings("unchecked")
    public ScenarioWidget init() {
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "view-scenario-widget");
        scenario = controller.getScenario();
        setCaption(scenario.getName());
        setHeight(95, Unit.PERCENTAGE);
        setDraggable(false);
        setResizable(false);
        setContent(initContent());
        return this;
    }

    @Override
    public void setController(ScenarioController controller) {
        this.controller = controller;
    }

    @Override
    public void applySearch() {
        table.getContainerDataSource().refresh();
    }

    @Override
    public String getSearchValue() {
        return searchWidget.getSearchValue();
    }

    private VerticalLayout initContent() {
        table = new RightsholderTotalsHolderTable(controller, RightsholderTotalsHolderBeanQuery.class);
        table.setColumnFooter("grossTotal", CurrencyUtils.formatAsHtml(scenario.getGrossTotal()));
        table.setColumnFooter("netTotal", CurrencyUtils.formatAsHtml(scenario.getNetTotal()));
        HorizontalLayout buttons = initButtons();
        VerticalLayout layout = new VerticalLayout(new VerticalLayout(initSearchWidget()), table, buttons);
        layout.setSizeFull();
        layout.setExpandRatio(table, 1);
        layout.setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);
        layout.setSpacing(true);
        return layout;
    }

    private HorizontalLayout initSearchWidget() {
        searchWidget = new SearchWidget(controller);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.scenario.search_widget"));
        HorizontalLayout toolbar = new HorizontalLayout(searchWidget);
        VaadinUtils.setMaxComponentsWidth(toolbar);
        searchWidget.setWidth(60, Unit.PERCENTAGE);
        toolbar.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        toolbar.setSpacing(true);
        toolbar.setSizeFull();
        toolbar.setExpandRatio(searchWidget, 1);
        return toolbar;
    }

    private HorizontalLayout initButtons() {
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader =
            new OnDemandFileDownloader(controller.getExportScenarioUsagesStreamSource());
        fileDownloader.extend(exportButton);
        HorizontalLayout buttons = new HorizontalLayout(exportButton, Buttons.createCloseButton(this));
        VaadinUtils.addComponentStyle(buttons, "scenario-buttons-layout");
        buttons.setSpacing(true);
        buttons.setMargin(new MarginInfo(false, true, true, false));
        return buttons;
    }
}
