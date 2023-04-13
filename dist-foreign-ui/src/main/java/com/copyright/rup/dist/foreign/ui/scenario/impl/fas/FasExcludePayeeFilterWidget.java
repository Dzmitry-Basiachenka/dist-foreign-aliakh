package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.ui.common.ScenarioFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountZeroValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

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

    private final Binder<String> binder = new Binder<>();
    private IFasExcludePayeeFilterController controller;
    private ExcludePayeeFilter filter = new ExcludePayeeFilter();
    private ExcludePayeeFilter appliedFilter = new ExcludePayeeFilter();
    private ComboBox<String> participatingComboBox;
    private TextField minimumNetThreshold;
    private ScenarioFilterWidget scenarioFilterWidget;
    private Button applyButton;

    @Override
    public ExcludePayeeFilter getAppliedFilter() {
        return appliedFilter;
    }

    @Override
    public void applyFilter() {
        appliedFilter = new ExcludePayeeFilter(filter);
        filterChanged();
        fireEvent(new FilterChangedEvent(this));
    }

    @Override
    public void clearFilter() {
        participatingComboBox.clear();
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
        initScenarioFilterWidget();
        initParticipatingFilter();
        initMinimumNetThresholdFilter();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        addComponents(
            buildFiltersHeaderLabel(), scenarioFilterWidget, participatingComboBox, minimumNetThreshold, buttonsLayout);
        setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setMargin(true);
        setSpacing(true);
        VaadinUtils.addComponentStyle(this, "audit-filter-widget");
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
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        HorizontalLayout layout = new HorizontalLayout(applyButton, clearButton);
        layout.setSpacing(true);
        VaadinUtils.setMaxComponentsWidth(layout, applyButton, clearButton);
        VaadinUtils.addComponentStyle(layout, "filter-buttons");
        return layout;
    }

    private void initParticipatingFilter() {
        Map<String, Boolean> participatingStatuses = controller.getParticipatingStatuses();
        participatingComboBox = new ComboBox<>(ForeignUi.getMessage("label.participating_status"));
        participatingComboBox.setItems(participatingStatuses.keySet());
        participatingComboBox.addValueChangeListener(event -> {
            filter.setPayeeParticipating(participatingStatuses.get(event.getValue()));
            filterChanged();
        });
        VaadinUtils.setMaxComponentsWidth(participatingComboBox);
        VaadinUtils.addComponentStyle(participatingComboBox, "participating-filter");
    }

    private void initMinimumNetThresholdFilter() {
        minimumNetThreshold = new TextField(ForeignUi.getMessage("label.minimum_net_threshold"));
        binder.forField(minimumNetThreshold)
            .withValidator(new AmountZeroValidator())
            .bind(ValueProvider.identity(), (beanValue, fieldValue) -> beanValue = fieldValue);
        minimumNetThreshold.addValueChangeListener(event -> {
            if (!binder.validate().hasErrors()) {
                filter.setNetAmountMinThreshold(StringUtils.isNotBlank(event.getValue())
                    ? new BigDecimal(StringUtils.trimToEmpty(event.getValue())) : null);
            }
            filterChanged();
        });
        VaadinUtils.setMaxComponentsWidth(minimumNetThreshold);
        VaadinUtils.addComponentStyle(minimumNetThreshold, "minimum-net-threshold-filter");
    }

    private void filterChanged() {
        applyButton.setEnabled(!binder.validate().hasErrors() && !filter.equals(appliedFilter));
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }
}
