package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.dist.common.service.impl.csv.validator.AmountValidator;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.data.Binder;
import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Modal window to show and modify AACL usage age weights.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/12/20
 *
 * @author Uladzislau Shalamitski
 */
public class AaclUsageAgeWeightWindow extends AaclCommonScenarioParameterWindow<List<UsageAge>> {

    private List<UsageAge> defaultValues;
    private List<UsageAge> currentValues;
    private Grid<UsageAge> grid;
    private final boolean isEditable;

    /**
     * Constructor.
     *
     * @param isEditable {@code true} if window should be in edit mode, otherwice {@code false}
     */
    public AaclUsageAgeWeightWindow(boolean isEditable) {
        this.isEditable = isEditable;
        setWidth(525, Unit.PIXELS);
        setHeight(300, Unit.PIXELS);
        initGrid();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout mainLayout = new VerticalLayout(grid, buttonsLayout);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        mainLayout.setExpandRatio(grid, 1);
        mainLayout.setSizeFull();
        setContent(mainLayout);
        setCaption(ForeignUi.getMessage("window.usage_age_weights"));
        VaadinUtils.addComponentStyle(this, "aacl-usage-age-weight-window");
    }

    @Override
    void setDefaultParameters(List<UsageAge> params) {
        defaultValues = params;
    }

    @Override
    void setAppliedParameters(List<UsageAge> params) {
        currentValues = params.stream().map(UsageAge::new).collect(Collectors.toList());
        grid.setItems(currentValues);
    }

    @Override
    void fireParametersSaveEvent(AaclScenarioParameterWidget.ParametersSaveEvent parametersSaveEvent) {
        fireEvent(parametersSaveEvent);
    }

    private void initGrid() {
        grid = new Grid<>();
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addColumn(UsageAge::getPeriod)
            .setCaption(ForeignUi.getMessage("label.usage_period"))
            .setSortable(false);
        Grid.Column<UsageAge, String> weightColumn =
            grid.addColumn(item -> CurrencyUtils.format(item.getWeight(), null))
                .setCaption(ForeignUi.getMessage("table.column.weight"))
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
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage("field.error.empty"))
            .withValidator(value -> new AmountValidator(true).isValid(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("field.error.positive_number_or_zero"))
            .withConverter(new BigDecimalConverter())
            .bind(UsageAge::getWeight, UsageAge::setWeight);
    }

    private HorizontalLayout initButtonsLayout() {
        Button saveButton = new Button(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            fireParametersSaveEvent(new AaclScenarioParameterWidget.ParametersSaveEvent<>(this, currentValues));
            close();
        });
        saveButton.setVisible(isEditable);
        Button defaultButton = new Button(ForeignUi.getMessage("button.default"));
        defaultButton.addClickListener(event -> setAppliedParameters(defaultValues));
        defaultButton.setVisible(isEditable);
        return new HorizontalLayout(saveButton, defaultButton, Buttons.createCloseButton(this));
    }

    private static class BigDecimalConverter implements Converter<String, BigDecimal> {

        @Override
        public Result<BigDecimal> convertToModel(String value, ValueContext context) {
            return Result.ok(new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP));
        }

        @Override
        public String convertToPresentation(BigDecimal value, ValueContext context) {
            return String.valueOf(value);
        }
    }
}
