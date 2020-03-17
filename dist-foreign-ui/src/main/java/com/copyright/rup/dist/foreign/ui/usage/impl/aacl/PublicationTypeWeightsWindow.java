package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.dist.common.service.impl.csv.validator.AmountValidator;
import com.copyright.rup.dist.foreign.domain.PublicationType;
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
 * Window for Publication Type Weights.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/12/2020
 *
 * @author Aliaksandr Liakh
 */
public class PublicationTypeWeightsWindow extends AaclCommonScenarioParameterWindow<List<PublicationType>> {

    private List<PublicationType> defaultValues;
    private List<PublicationType> currentValues;
    private Grid<PublicationType> grid;

    /**
     * Constructor.
     */
    public PublicationTypeWeightsWindow() {
        setWidth(600, Unit.PIXELS);
        setHeight(250, Unit.PIXELS);
        initGrid();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout mainLayout = new VerticalLayout(grid, buttonsLayout);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        mainLayout.setExpandRatio(grid, 1);
        mainLayout.setSizeFull();
        setContent(mainLayout);
        setCaption(ForeignUi.getMessage("window.publication_type_weights"));
        VaadinUtils.addComponentStyle(this, "aacl-publication-type-weight-window");
    }

    @Override
    void setDefaultParameters(List<PublicationType> params) {
        defaultValues = params;
    }

    @Override
    void setAppliedParameters(List<PublicationType> params) {
        currentValues = params.stream().map(PublicationType::new).collect(Collectors.toList());
        grid.setItems(currentValues);
    }

    @Override
    void fireParametersSaveEvent(AaclScenarioParameterWidget.ParametersSaveEvent parametersSaveEvent) {
        fireEvent(parametersSaveEvent);
    }

    private void initGrid() {
        grid = new Grid<>();
        TextField textField = new TextField();
        textField.addStyleName("editable-field");
        Binder<PublicationType> binder = grid.getEditor().getBinder();
        Binder.Binding<PublicationType, BigDecimal> editorBinder = binder.forField(textField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage("field.error.empty"))
            .withValidator(value -> new AmountValidator(true).isValid(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("field.error.positive_number_or_zero"))
            .withConverter(new BigDecimalConverter())
            .bind(PublicationType::getWeight, PublicationType::setWeight);
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addColumn(PublicationType::getName)
            .setCaption(ForeignUi.getMessage("table.column.name"))
            .setSortable(false);
        grid.addColumn(item -> CurrencyUtils.format(item.getWeight(), null))
            .setCaption(ForeignUi.getMessage("table.column.weight"))
            .setStyleGenerator(item -> "editable-cell")
            .setEditorBinding(editorBinder)
            .setSortable(false);
        grid.getEditor().setEnabled(true);
        grid.getEditor().setSaveCaption(ForeignUi.getMessage("button.update"));
        grid.getEditor().addSaveListener(event -> {
            // Workaround for https://github.com/vaadin/framework/issues/9678
            grid.setItems(currentValues);
        });
        grid.addItemClickListener(event -> grid.getEditor().editRow(event.getRowIndex()));
        VaadinUtils.addComponentStyle(grid, "aacl-publication-type-weight-grid");
    }

    private HorizontalLayout initButtonsLayout() {
        Button saveButton = new Button(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            fireParametersSaveEvent(new AaclScenarioParameterWidget.ParametersSaveEvent<>(this, currentValues));
            close();
        });
        Button defaultButton = new Button(ForeignUi.getMessage("button.default"));
        defaultButton.addClickListener(event -> setAppliedParameters(defaultValues));
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