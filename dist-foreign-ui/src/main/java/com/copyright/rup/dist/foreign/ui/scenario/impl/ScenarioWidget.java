package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.ExcludeRightsholdersWindow.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.ui.scenario.impl.ExcludeRightsholdersWindow.IExcludeUsagesListener;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.api.IMediator;
import com.copyright.rup.vaadin.widget.api.IMediatorProvider;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
public class ScenarioWidget extends Window implements IScenarioWidget, IMediatorProvider {

    private ScenarioController controller;
    private SearchWidget searchWidget;
    private RightsholderTotalsHolderTable table;
    private VerticalLayout emptyUsagesLayout;
    private Button exportButton;
    private Button excludeButton;
    private ScenarioMediator mediator;

    @Override
    @SuppressWarnings("unchecked")
    public ScenarioWidget init() {
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "view-scenario-widget");
        Scenario scenario = controller.getScenario();
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

    @Override
    public void fireWidgetEvent(Event event) {
        fireEvent(event);
    }

    @Override
    public void addListener(IExcludeUsagesListener listener) {
        addListener(ExcludeUsagesEvent.class, listener, IExcludeUsagesListener.EXCLUDE_DETAILS_HANDLER);
    }

    @Override
    public void refresh() {
        mediator.onScenarioUpdated(controller.isScenarioEmpty(), controller.getScenario().getStatus());
    }

    @Override
    public void refreshTable() {
        table.getContainerDataSource().refresh();
        updateFooter();
    }

    @Override
    public IMediator initMediator() {
        mediator = new ScenarioMediator();
        mediator.setExcludeButton(excludeButton);
        mediator.setExportButton(exportButton);
        mediator.setEmptyUsagesLayout(emptyUsagesLayout);
        mediator.setRightsholdersTable(table);
        mediator.setSearchWidget(searchWidget);
        return mediator;
    }

    @Override
    public void attach() {
        super.attach();
        refresh();
    }

    private VerticalLayout initContent() {
        table = new RightsholderTotalsHolderTable(controller, RightsholderTotalsHolderBeanQuery.class);
        updateFooter();
        initEmptyScenarioMessage();
        HorizontalLayout buttons = initButtons();
        VerticalLayout layout =
            new VerticalLayout(new VerticalLayout(initSearchWidget()), table, emptyUsagesLayout, buttons);
        layout.setSizeFull();
        layout.setExpandRatio(table, 1);
        layout.setExpandRatio(emptyUsagesLayout, 1);
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
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader =
            new OnDemandFileDownloader(controller.getExportScenarioUsagesStreamSource());
        fileDownloader.extend(exportButton);
        excludeButton = new Button(ForeignUi.getMessage("button.exclude.details"));
        excludeButton.addClickListener(event -> controller.onExcludeDetailsClicked());
        HorizontalLayout buttons = new HorizontalLayout(excludeButton, exportButton, Buttons.createCloseButton(this));
        VaadinUtils.addComponentStyle(buttons, "scenario-buttons-layout");
        buttons.setSpacing(true);
        buttons.setMargin(new MarginInfo(false, true, true, false));
        return buttons;
    }

    private void initEmptyScenarioMessage() {
        Label emptyScenarioMessage = new Label(ForeignUi.getMessage("label.content.empty_scenario"), ContentMode.HTML);
        emptyScenarioMessage.setSizeUndefined();
        emptyScenarioMessage.addStyleName(Cornerstone.LABEL_H2);
        emptyUsagesLayout = new VerticalLayout(emptyScenarioMessage);
        emptyUsagesLayout.setComponentAlignment(emptyScenarioMessage, Alignment.MIDDLE_CENTER);
        emptyUsagesLayout.setSizeFull();
    }

    private void updateFooter() {
        Scenario scenarioWithAmounts = controller.getScenarioWithAmounts();
        table.setColumnFooter("grossTotal", CurrencyUtils.formatAsHtml(scenarioWithAmounts.getGrossTotal()));
        table.setColumnFooter("serviceFeeTotal", CurrencyUtils.formatAsHtml(scenarioWithAmounts.getServiceFeeTotal()));
        table.setColumnFooter("netTotal", CurrencyUtils.formatAsHtml(scenarioWithAmounts.getNetTotal()));
    }
}
