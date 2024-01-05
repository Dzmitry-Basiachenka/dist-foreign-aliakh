package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenariosReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenariosReportWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.SerializableComparator;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ICommonScenariosReportWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/14/2020
 *
 * @author Uladzislau Shalamitski
 */
public class CommonScenariosReportWidget extends Window
    implements ICommonScenariosReportWidget, SearchWidget.ISearchController {

    private static final long serialVersionUID = -7281942894519453374L;
    private static final SerializableComparator<Scenario> SCENARIOS_COMPARATOR = (scenario1, scenario2) ->
        Comparator.comparing(Scenario::getName, String::compareToIgnoreCase).compare(scenario1, scenario2);

    private ICommonScenariosReportController controller;
    private SearchWidget searchWidget;
    private Grid<Scenario> grid;
    private ListDataProvider<Scenario> dataProvider;
    private Button exportButton;

    @Override
    public void performSearch() {
        dataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotEmpty(searchValue)) {
            grid.getSelectedItems().stream()
                .filter(scenario -> !scenarioMatches(scenario, searchValue))
                .forEach(grid::deselect);
            dataProvider.setFilter(scenario -> scenarioMatches(scenario, searchValue));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ICommonScenariosReportWidget init() {
        setHeight(400, Unit.PIXELS);
        setWidth(500, Unit.PIXELS);
        setResizable(true);
        initSearchWidget();
        initGrid();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout layout = new VerticalLayout(searchWidget, grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(layout);
        VaadinUtils.addComponentStyle(this, "common-scenarios-report-window");
        return this;
    }

    @Override
    public List<Scenario> getSelectedScenarios() {
        return grid.getSelectedItems()
            .stream()
            .sorted(SCENARIOS_COMPARATOR)
            .collect(Collectors.toList());
    }

    @Override
    public void setController(ICommonScenariosReportController controller) {
        this.controller = controller;
    }

    private void initSearchWidget() {
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("prompt.scenario"));
        searchWidget.setWidth(100, Sizeable.Unit.PERCENTAGE);
    }

    private void initGrid() {
        dataProvider = new ListDataProvider<>(controller.getScenarios());
        grid = new Grid<>(dataProvider);
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(Scenario::getName)
            .setCaption(ForeignUi.getMessage("table.column.scenario_name"))
            .setComparator(SCENARIOS_COMPARATOR);
        grid.addSelectionListener(event ->
            exportButton.setEnabled(CollectionUtils.isNotEmpty(grid.getSelectedItems())));
        VaadinUtils.addComponentStyle(grid, "common-report-scenarios-grid");
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportButton.setEnabled(false);
        OnDemandFileDownloader downloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        downloader.extend(exportButton);
        HorizontalLayout layout = new HorizontalLayout(exportButton, closeButton);
        layout.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);
        layout.setComponentAlignment(exportButton, Alignment.MIDDLE_LEFT);
        layout.setMargin(new MarginInfo(true, false, false, false));
        layout.setSpacing(true);
        VaadinUtils.setMaxComponentsWidth(layout);
        return layout;
    }

    private boolean scenarioMatches(Scenario scenario, String searchValue) {
        return StringUtils.containsIgnoreCase(scenario.getName(), searchValue);
    }
}
