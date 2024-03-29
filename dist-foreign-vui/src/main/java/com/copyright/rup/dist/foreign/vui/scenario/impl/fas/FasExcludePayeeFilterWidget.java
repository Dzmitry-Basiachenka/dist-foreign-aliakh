package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.vui.common.ScenarioFilterWidget;
import com.copyright.rup.dist.foreign.vui.common.validator.AmountRangeValidator;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeFilterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.IFilterSaveAction;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.ValueProvider;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Interface for exclude payees filter widget.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
public class FasExcludePayeeFilterWidget extends VerticalLayout implements IFasExcludePayeeFilterWidget {

    private static final long serialVersionUID = 4800535657480115497L;

    private final Binder<BigDecimal> binder = new Binder<>();
    private IFasExcludePayeeFilterController controller;
    private ExcludePayeeFilter filter = new ExcludePayeeFilter();
    private ExcludePayeeFilter appliedFilter = new ExcludePayeeFilter();
    private Select<String> participatingSelectField;
    private BigDecimalField minimumNetThreshold;
    private ScenarioFilterWidget scenarioFilterWidget;
    private Button applyButton;
    private IFilterSaveAction filterSaveAction;

    @Override
    public ExcludePayeeFilter getAppliedFilter() {
        return appliedFilter;
    }

    @Override
    public void setFilterSaveAction(IFilterSaveAction action) {
        this.filterSaveAction = action;
    }

    @Override
    public void applyFilter() {
        appliedFilter = new ExcludePayeeFilter(filter);
        filterChanged();
        filterSaveAction.onFilterSaveAction();
    }

    @Override
    public void clearFilter() {
        participatingSelectField.clear();
        minimumNetThreshold.clear();
        scenarioFilterWidget.reset();
        filter = new ExcludePayeeFilter();
        applyFilter();
    }

    @Override
    public void setController(IFasExcludePayeeFilterController controller) {
        this.controller = controller;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FasExcludePayeeFilterWidget init() {
        super.setSizeFull();
        super.setMinWidth("150px");
        super.setSpacing(false);
        super.setWidthFull();
        VaadinUtils.setPadding(this, 0, 10, 0, 10);
        initScenarioFilterWidget();
        initParticipatingFilter();
        initMinimumNetThresholdFilter();
        var buttonsLayout = initButtonsLayout();
        add(buildFiltersHeaderLabel(), scenarioFilterWidget, participatingSelectField, minimumNetThreshold,
            buttonsLayout);
        VaadinUtils.addComponentStyle(this, "fas-exclude payee-filter-widget");
        return this;
    }

    private void initScenarioFilterWidget() {
        scenarioFilterWidget = new ScenarioFilterWidget(controller::getScenarios);
        scenarioFilterWidget.addFilterSaveListener((IFilterSaveListener<Scenario>) saveEvent -> {
            filter.setScenarioIds(
                saveEvent.getSelectedItemsIds().stream().map(Scenario::getId).collect(Collectors.toSet()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(scenarioFilterWidget, "scenarios-filter");
    }

    private HorizontalLayout initButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
        applyButton.addClickListener(event -> applyFilter());
        applyButton.setEnabled(false);
        applyButton.setWidthFull();
        var clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        clearButton.setWidthFull();
        var layout = new HorizontalLayout(applyButton, clearButton);
        layout.setSpacing(true);
        layout.setWidthFull();
        VaadinUtils.addComponentStyle(layout, "filter-buttons");
        return layout;
    }

    private void initParticipatingFilter() {
        Map<String, Boolean> participatingStatuses = controller.getParticipatingStatuses();
        participatingSelectField = new Select<>();
        participatingSelectField.setWidthFull();
        participatingSelectField.setEmptySelectionAllowed(true);
        participatingSelectField.setLabel(ForeignUi.getMessage("label.participating_status"));
        participatingSelectField.setItems(participatingStatuses.keySet());
        participatingSelectField.addValueChangeListener(event -> {
            filter.setPayeeParticipating(participatingStatuses.get(event.getValue()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(participatingSelectField, "participating-filter");
    }

    private void initMinimumNetThresholdFilter() {
        minimumNetThreshold = new BigDecimalField(ForeignUi.getMessage("label.minimum_net_threshold"));
        minimumNetThreshold.setWidthFull();
        minimumNetThreshold.setValueChangeMode(ValueChangeMode.LAZY);
        minimumNetThreshold.addValueChangeListener(event -> {
            if (!binder.validate().hasErrors()) {
                filter.setNetAmountMinThreshold(event.getValue());
            }
            filterChanged();
        });
        binder.forField(minimumNetThreshold)
            .withValidator(AmountRangeValidator.amountValidator())
            .bind(ValueProvider.identity(), (beanValue, fieldValue) -> beanValue = fieldValue);
        VaadinUtils.addComponentStyle(minimumNetThreshold, "minimum-net-threshold-filter");
    }

    private void filterChanged() {
        applyButton.setEnabled(!binder.validate().hasErrors() && !filter.equals(appliedFilter));
    }

    private Label buildFiltersHeaderLabel() {
        var filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addClassName("filter-label");
        return filterHeaderLabel;
    }
}
