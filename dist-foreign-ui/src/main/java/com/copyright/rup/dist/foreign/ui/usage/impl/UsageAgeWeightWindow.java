package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.ui.common.converter.BigDecimalConverter;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Modal window to show and modify usage age weights.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/12/20
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
        super.setWidth(525, Unit.PIXELS);
        super.setHeight(300, Unit.PIXELS);
        super.setResizable(false);
        initGrid();
        var buttonsLayout = initButtonsLayout();
        var mainLayout = new VerticalLayout(grid, buttonsLayout);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        mainLayout.setExpandRatio(grid, 1);
        mainLayout.setSizeFull();
        super.setContent(mainLayout);
        super.setCaption(ForeignUi.getMessage("window.usage_age_weights"));
        VaadinUtils.addComponentStyle(this, "aacl-usage-age-weight-window");
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
    void fireParametersSaveEvent(ScenarioParameterWidget.ParametersSaveEvent parametersSaveEvent) {
        fireEvent(parametersSaveEvent);
    }

    private void initGrid() {
        grid = new Grid<>();
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addColumn(UsageAge::getPeriod)
            .setCaption(ForeignUi.getMessage("label.usage_period"))
            .setSortable(false);
        grid.addColumn(item -> CurrencyUtils.format(periodsToDefaultWeights.get(item.getPeriod()), null))
            .setCaption(ForeignUi.getMessage("table.column.default_weight"))
            .setSortable(false);
        Grid.Column<UsageAge, String> weightColumn =
            grid.addColumn(item -> CurrencyUtils.format(item.getWeight(), null))
                .setCaption(ForeignUi.getMessage("table.column.scenario_weight"))
                .setSortable(false);
        if (isEditable) {
            weightColumn
                .setStyleGenerator(item -> "editable-cell")
                .setEditorBinding(initEditorBinding());
            grid.getEditor().setEnabled(true);
            grid.getEditor().setSaveCaption(ForeignUi.getMessage("button.update"));
            grid.getEditor().addSaveListener(event -> {
                // Workaround for https://github.com/vaadin/framework/issues/9678
                grid.setItems(currentValues);
            });
            grid.addItemClickListener(event -> grid.getEditor().editRow(event.getRowIndex()));
        } else {
            grid.getEditor().setEnabled(false);
        }
        VaadinUtils.addComponentStyle(grid, "aacl-usage-age-weight-grid");
    }

    private Binder.Binding<UsageAge, BigDecimal> initEditorBinding() {
        TextField textField = new TextField();
        textField.addStyleName("editable-field");
        Binder<UsageAge> binder = grid.getEditor().getBinder();
        return binder.forField(textField)
            .withValidator(new RequiredValidator())
            .withValidator(new AmountValidator(ForeignUi.getMessage("field.error.positive_number_or_zero")))
            .withConverter(new BigDecimalConverter())
            .bind(UsageAge::getWeight, UsageAge::setWeight);
    }

    private HorizontalLayout initButtonsLayout() {
        Button saveButton = new Button(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            fireParametersSaveEvent(new ScenarioParameterWidget.ParametersSaveEvent<>(this, currentValues));
            close();
        });
        VaadinUtils.setButtonsAutoDisabled(saveButton);
        saveButton.setVisible(isEditable);
        Label placeholderLabel = new Label();
        placeholderLabel.setVisible(isEditable);
        Button defaultButton = new Button(ForeignUi.getMessage("button.default"));
        defaultButton.addClickListener(event -> setAppliedParameters(defaultValues));
        defaultButton.setVisible(isEditable);
        return new HorizontalLayout(saveButton, Buttons.createCloseButton(this), placeholderLabel, defaultButton);
    }
}
