package com.copyright.rup.dist.foreign.vui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.CurrencyUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Modal window to show and modify usage age weights.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/12/2020
 *
 * @author Uladzislau Shalamitski
 */
public class UsageAgeWeightWindow extends CommonScenarioParameterWindow<List<UsageAge>> {

    private static final long serialVersionUID = 3724004273636105011L;

    private final boolean isEditable;

    private List<UsageAge> defaultValues;
    private Map<Integer, BigDecimal> periodsToDefaultWeights;
    private List<UsageAge> currentValues;
    private Grid<UsageAge> grid;

    /**
     * Constructor.
     *
     * @param isEditable {@code true} if window should be in edit mode, otherwise {@code false}
     */
    public UsageAgeWeightWindow(boolean isEditable) {
        this.isEditable = isEditable;
        super.setWidth("525px");
        super.setHeight("300px");
        super.setHeaderTitle(ForeignUi.getMessage("window.usage_age_weights"));
        super.add(initRootLayout());
        super.getFooter().add(initButtonsLayout());
        super.setModalWindowProperties("aacl-usage-age-weight-window", false);
    }

    @Override
    void setDefault(List<UsageAge> params) {
        defaultValues = params;
        periodsToDefaultWeights = defaultValues
            .stream()
            .collect(Collectors.toMap(UsageAge::getPeriod, UsageAge::getWeight));
    }

    @Override
    void setAppliedParameters(List<UsageAge> params) {
        currentValues = params.stream().map(UsageAge::new).collect(Collectors.toList());
        grid.setItems(currentValues);
    }

    @Override
    void fireParametersSaveEvent(ParametersSaveEvent<List<UsageAge>> parametersSaveEvent) {
        fireEvent(parametersSaveEvent);
    }

    private VerticalLayout initRootLayout() {
        return VaadinUtils.initSizeFullVerticalLayout(initGrid());
    }

    private Grid<UsageAge> initGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.NONE);
        //TODO {aliakh} use GridColumnEnum and IGridColumnAdder
        grid.addColumn(UsageAge::getPeriod)
            .setHeader(ForeignUi.getMessage("label.usage_period"))
            .setSortable(false);
        grid.addColumn(item -> CurrencyUtils.format(periodsToDefaultWeights.get(item.getPeriod()), null))
            .setHeader(ForeignUi.getMessage("table.column.default_weight"))
            .setSortable(false);
        grid.addColumn(item -> CurrencyUtils.format(item.getWeight(), null))
            .setHeader(ForeignUi.getMessage("table.column.scenario_weight"))
            .setSortable(false);
        //TODO {aliakh} implement if (isEditable)
        VaadinUtils.setGridProperties(grid, "aacl-usage-age-weight-grid");
        return grid;
    }

    private HorizontalLayout initButtonsLayout() {
        var saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.setVisible(isEditable);
        saveButton.addClickListener(event -> {
            fireParametersSaveEvent(new ParametersSaveEvent<>(this, currentValues));
            close();
        });
        var placeholderLabel = new Label();
        placeholderLabel.setVisible(isEditable);
        var defaultButton = Buttons.createButton(ForeignUi.getMessage("button.default"));
        defaultButton.setVisible(isEditable);
        defaultButton.addClickListener(event -> setAppliedParameters(defaultValues));
        return new HorizontalLayout(saveButton, Buttons.createCloseButton(this), placeholderLabel, defaultButton);
    }
}
