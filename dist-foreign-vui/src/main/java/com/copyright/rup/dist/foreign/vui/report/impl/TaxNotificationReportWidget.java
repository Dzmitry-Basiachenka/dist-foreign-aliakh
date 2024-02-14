package com.copyright.rup.dist.foreign.vui.report.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredNumberValidator;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.report.api.ITaxNotificationReportController;
import com.copyright.rup.dist.foreign.vui.report.api.ITaxNotificationReportWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget.ISearchController;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.ValueProvider;

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
public class TaxNotificationReportWidget extends CommonDialog implements ITaxNotificationReportWidget,
    ISearchController {

    private static final long serialVersionUID = -7360779731865184201L;
    private static final Integer DAYS_MIN_VALUE = 0;
    private static final Integer DAYS_MAX_VALUE = 99999;
    private static final Integer DEFAULT_NUMBER_OF_DAYS = 15;

    private ITaxNotificationReportController controller;
    private SearchWidget searchWidget;
    private Grid<Scenario> grid;
    private ListDataProvider<Scenario> dataProvider;
    private IntegerField numberOfDaysField;
    private Binder<Integer> binder;
    private Button exportButton;
    private OnDemandFileDownloader downloader;

    @Override
    @SuppressWarnings("unchecked")
    public ITaxNotificationReportWidget init() {
        setHeight("400px");
        setWidth("600px");
        initSearchWidget();
        initGrid();
        initNumberOfDaysField();
        getFooter().add(initButtonsLayout());
        var layout = VaadinUtils.initSizeFullVerticalLayout(searchWidget, grid, numberOfDaysField);
        layout.setPadding(true);
        add(layout);
        setModalWindowProperties("tax-notification-report-window", true);
        return this;
    }

    @Override
    public void setController(ITaxNotificationReportController controller) {
        this.controller = controller;
    }

    @Override
    public void performSearch() {
        dataProvider.clearFilters();
        var searchValue = searchWidget.getSearchValue();
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
        return numberOfDaysField.getValue();
    }

    private void initSearchWidget() {
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("prompt.scenario"));
    }

    private void initGrid() {
        dataProvider = new ListDataProvider<>(controller.getScenarios());
        grid = new Grid<>();
        grid.setItems(dataProvider);
        grid.setSelectionMode(SelectionMode.MULTI);
        grid.addColumn(Scenario::getName)
            .setHeader(ForeignUi.getMessage("table.column.scenario_name"))
            .setComparator((scenario1, scenario2) ->
                Comparator.comparing(Scenario::getName, String::compareToIgnoreCase).compare(scenario1, scenario2));
        grid.addSelectionListener(event -> updateExportButtonState());
        VaadinUtils.setGridProperties(grid, "tax-notification-report-scenarios-grid");
    }

    private void initNumberOfDaysField() {
        numberOfDaysField = new IntegerField(ForeignUi.getMessage("field.number_of_days"));
        numberOfDaysField.setValue(DEFAULT_NUMBER_OF_DAYS);
        numberOfDaysField.setWidthFull();
        numberOfDaysField.setRequiredIndicatorVisible(true);
        numberOfDaysField.setValueChangeMode(ValueChangeMode.LAZY);
        numberOfDaysField.addValueChangeListener(event -> updateExportButtonState());
        VaadinUtils.addComponentStyle(numberOfDaysField, "number-of-days-field");
        binder = new Binder<>();
        binder.forField(numberOfDaysField)
            .withValidator(new RequiredNumberValidator())
            .withValidator(new IntegerRangeValidator(
                ForeignUi.getMessage("field.error.positive_number_or_zero_and_max_value", DAYS_MAX_VALUE),
                DAYS_MIN_VALUE, DAYS_MAX_VALUE))
            .bind(ValueProvider.identity(), (bean, fieldValue) -> bean = fieldValue)
            .validate();
    }

    private HorizontalLayout initButtonsLayout() {
        var closeButton = Buttons.createCloseButton(this);
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportButton.setEnabled(false);
        downloader = new OnDemandFileDownloader();
        downloader.extend(exportButton);
        return new HorizontalLayout(downloader, closeButton);
    }

    private void updateExportButtonState() {
        boolean isValidValues = binder.isValid() && CollectionUtils.isNotEmpty(grid.getSelectedItems());
        exportButton.setEnabled(isValidValues);
        if (isValidValues) {
            downloader.setResource(new CsvStreamSource(controller).getSource());
        } else {
            downloader.removeHref();
        }
    }

    private boolean scenarioMatches(Scenario scenario, String searchValue) {
        return StringUtils.containsIgnoreCase(scenario.getName(), searchValue);
    }
}
