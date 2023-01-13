package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.ui.common.converter.BigDecimalConverter;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;
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
 * Window for Publication Type Weights.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/12/2020
 *
 * @author Aliaksandr Liakh
 */
public class PublicationTypeWeightsWindow extends CommonScenarioParameterWindow<List<PublicationType>> {

    private List<PublicationType> defaultValues;
    private Map<String, BigDecimal> idsToDefaultWeights;
    private List<PublicationType> currentValues;
    private Grid<PublicationType> grid;
    private final boolean isEditable;

    /**
     * Constructor.
     *
     * @param isEditable {@code true} if window should be in edit mode, otherwise {@code false}
     */
    public PublicationTypeWeightsWindow(boolean isEditable) {
        this.isEditable = isEditable;
        setWidth(525, Unit.PIXELS);
        setHeight(250, Unit.PIXELS);
        setResizable(false);
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
    void setDefault(List<PublicationType> params) {
        defaultValues = params;
        idsToDefaultWeights = defaultValues
            .stream()
            .collect(Collectors.toMap(PublicationType::getId, PublicationType::getWeight));
    }

    @Override
    void setAppliedParameters(List<PublicationType> params) {
        currentValues = params.stream().map(PublicationType::new).collect(Collectors.toList());
        grid.setItems(currentValues);
    }

    @Override
    void fireParametersSaveEvent(ParametersSaveEvent<List<PublicationType>> parametersSaveEvent) {
        fireEvent(parametersSaveEvent);
    }

    private void initGrid() {
        grid = new Grid<>();
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addColumn(PublicationType::getName)
            .setCaption(ForeignUi.getMessage("table.column.publication_type"))
            .setSortable(false);
        grid.addColumn(item -> CurrencyUtils.format(idsToDefaultWeights.get(item.getId()), null))
            .setCaption(ForeignUi.getMessage("table.column.default_weight"))
            .setSortable(false);
        Grid.Column<PublicationType, String> weightColumn =
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
        VaadinUtils.addComponentStyle(grid, "aacl-publication-type-weight-grid");
    }

    private Binder.Binding<PublicationType, BigDecimal> initEditorBinding() {
        TextField textField = new TextField();
        textField.addStyleName("editable-field");
        Binder<PublicationType> binder = grid.getEditor().getBinder();
        return binder.forField(textField)
            .withValidator(new RequiredValidator())
            .withValidator(new AmountValidator(ForeignUi.getMessage("field.error.positive_number_or_zero")))
            .withConverter(new BigDecimalConverter())
            .bind(PublicationType::getWeight, PublicationType::setWeight);
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
}
