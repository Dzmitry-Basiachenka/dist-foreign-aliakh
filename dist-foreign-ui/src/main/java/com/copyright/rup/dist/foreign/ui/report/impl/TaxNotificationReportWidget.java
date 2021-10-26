package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.ITaxNotificationReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ITaxNotificationReportWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.SearchWidget.ISearchController;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ITaxNotificationReportWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/24/20
 *
 * @author Stanislau Rudak
 */
public class TaxNotificationReportWidget extends Window implements ITaxNotificationReportWidget, ISearchController {

    private static final Integer DAYS_MIN_VALUE = 0;
    private static final Integer DAYS_MAX_VALUE = 99999;

    private ITaxNotificationReportController controller;
    private SearchWidget searchWidget;
    private Grid<Scenario> grid;
    private ListDataProvider<Scenario> dataProvider;
    private TextField numberOfDaysField;
    private Binder<Integer> binder;
    private Button exportButton;

    @Override
    @SuppressWarnings("unchecked")
    public ITaxNotificationReportWidget init() {
        setHeight(400, Unit.PIXELS);
        setWidth(600, Unit.PIXELS);
        setResizable(true);
        VaadinUtils.addComponentStyle(this, "tax-notification-report-window");
        initSearchWidget();
        initGrid();
        initNumberOfDaysField();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout layout = new VerticalLayout(searchWidget, grid, numberOfDaysField, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(layout);
        return this;
    }

    @Override
    public void setController(ITaxNotificationReportController controller) {
        this.controller = controller;
    }

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
    public Set<String> getSelectedScenarioIds() {
        return grid.getSelectedItems().stream()
            .map(Scenario::getId)
            .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfDays() {
        return Integer.parseInt(numberOfDaysField.getValue().trim());
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
        grid.setSelectionMode(SelectionMode.MULTI);
        grid.addColumn(Scenario::getName)
            .setCaption(ForeignUi.getMessage("table.column.scenario_name"))
            .setComparator((scenario1, scenario2) ->
                Comparator.comparing(Scenario::getName, String::compareToIgnoreCase).compare(scenario1, scenario2));
        grid.addSelectionListener(event -> updateExportButtonState());
        VaadinUtils.addComponentStyle(grid, "tax-notification-report-scenarios-grid");
    }

    private void initNumberOfDaysField() {
        numberOfDaysField = new TextField(ForeignUi.getMessage("field.number_of_days"));
        numberOfDaysField.setValue("15");
        numberOfDaysField.setWidth(100, Sizeable.Unit.PERCENTAGE);
        numberOfDaysField.setRequiredIndicatorVisible(true);
        numberOfDaysField.addValueChangeListener(event -> updateExportButtonState());
        VaadinUtils.addComponentStyle(numberOfDaysField, "number-of-days-field");
        binder = new Binder<>();
        binder.forField(numberOfDaysField)
            .withValidator(new RequiredValidator())
            .withConverter(new StringToIntegerConverter(ForeignUi.getMessage("field.error.not_numeric")))
            .withValidator(new IntegerRangeValidator(
                ForeignUi.getMessage("field.error.positive_number_or_zero_and_max_value", DAYS_MAX_VALUE),
                DAYS_MIN_VALUE, DAYS_MAX_VALUE))
            .bind(s -> s, (s, v) -> s = v)
            .validate();
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

    private void updateExportButtonState() {
        exportButton.setEnabled(binder.isValid() && CollectionUtils.isNotEmpty(grid.getSelectedItems()));
    }

    private boolean scenarioMatches(Scenario scenario, String searchValue) {
        return StringUtils.containsIgnoreCase(scenario.getName(), searchValue);
    }
}
