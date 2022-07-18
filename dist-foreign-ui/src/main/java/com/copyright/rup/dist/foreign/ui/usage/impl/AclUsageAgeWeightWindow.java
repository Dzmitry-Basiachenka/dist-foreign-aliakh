package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.BigDecimalConverter;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Modal window to show and modify ACL usage age weights.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/14/2022
 *
 * @author Anton Azarenka
 */
public class AclUsageAgeWeightWindow extends CommonScenarioParameterWindow<List<UsageAge>> {

    private static final String AMOUNT_SCALE_REGEX = "(0|[1](\\d{0,9}))(\\.\\d{1,2})?";

    private List<UsageAge> defaultValues;
    private Map<Integer, BigDecimal> periodsToDefaultWeights;
    private List<UsageAge> currentValues;
    private Grid<UsageAge> grid;
    private final boolean isEditable;

    /**
     * Constructor.
     *
     * @param isEditable {@code true} if window should be in edit mode, otherwise {@code false}
     */
    public AclUsageAgeWeightWindow(boolean isEditable) {
        this.isEditable = isEditable;
        setWidth(600, Unit.PIXELS);
        setHeight(300, Unit.PIXELS);
        setResizable(false);
        initGrid();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout mainLayout = new VerticalLayout(grid, buttonsLayout);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        mainLayout.setExpandRatio(grid, 1);
        mainLayout.setSizeFull();
        setContent(mainLayout);
        setCaption(ForeignUi.getMessage("window.usage_age_weights"));
        VaadinUtils.addComponentStyle(this, "acl-usage-age-weight-window");
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
    protected void fireParametersSaveEvent(ParametersSaveEvent<List<UsageAge>> parametersSaveEvent) {
        fireEvent(parametersSaveEvent);
    }

    private void initGrid() {
        grid = new Grid<>();
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addColumn(UsageAge::getPeriod)
            .setCaption(ForeignUi.getMessage("label.period_prior"))
            .setSortable(false);
        grid.addColumn(item -> CurrencyUtils.format(periodsToDefaultWeights.get(item.getPeriod()), null))
            .setCaption(ForeignUi.getMessage("table.column.default_weight"))
            .setSortable(false);
        Grid.Column<UsageAge, String> weightColumn =
            grid.addColumn(item -> CurrencyUtils.format(item.getWeight(), null))
                .setCaption(ForeignUi.getMessage("table.column.scenario_weight"))
                .setSortable(false);
        if (isEditable) {
            weightColumn.setStyleGenerator(item -> "editable-cell")
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
        VaadinUtils.addComponentStyle(grid, "acl-usage-age-weight-grid");
    }

    private Binder.Binding<UsageAge, BigDecimal> initEditorBinding() {
        TextField textField = new TextField();
        textField.addStyleName("editable-field");
        Binder<UsageAge> binder = grid.getEditor().getBinder();
        return binder.forField(textField)
            .withValidator(new RequiredValidator())
            .withValidator(getUsageAgeWeightValidator(), ForeignUi.getMessage("field.error.number_not_in_range", 0, 1))
            .withValidator(getUsageAgeWeightScaleValidator(), ForeignUi.getMessage("field.error.number_scale", 2))
            .withConverter(new BigDecimalConverter())
            .bind(value -> value.getWeight().setScale(2, RoundingMode.HALF_UP), UsageAge::setWeight);
    }

    private HorizontalLayout initButtonsLayout() {
        Button saveButton = new Button(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            fireParametersSaveEvent(new ScenarioParameterWidget.ParametersSaveEvent<>(this, currentValues));
            close();
        });
        saveButton.setVisible(isEditable);
        Label placeholderLabel = new Label();
        placeholderLabel.setVisible(isEditable);
        Button defaultButton = new Button(ForeignUi.getMessage("button.default"));
        defaultButton.addClickListener(event -> setAppliedParameters(defaultValues));
        defaultButton.setVisible(isEditable);
        return new HorizontalLayout(saveButton, Buttons.createCloseButton(this), placeholderLabel, defaultButton);
    }

    private SerializablePredicate<? super String> getUsageAgeWeightValidator() {
        return value -> new BigDecimal(value.trim()).compareTo(BigDecimal.ZERO) >= 0
            && new BigDecimal(value).compareTo(BigDecimal.ONE) <= 0;
    }

    private SerializablePredicate<? super String> getUsageAgeWeightScaleValidator() {
        return value -> value.trim().matches(AMOUNT_SCALE_REGEX);
    }
}
