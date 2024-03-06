package com.copyright.rup.dist.foreign.vui.usage.impl;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.vui.common.validator.AmountRangeValidator;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredNumberValidator;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.CurrencyUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.function.ValueProvider;

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

    private static final long serialVersionUID = -4975358029914222102L;

    private final boolean isEditable;

    private List<PublicationType> defaultValues;
    private Map<String, BigDecimal> idsToDefaultWeights;
    private List<PublicationType> currentValues;
    private Grid<PublicationType> grid;

    /**
     * Constructor.
     *
     * @param isEditable {@code true} if window should be in edit mode, otherwise {@code false}
     */
    public PublicationTypeWeightsWindow(boolean isEditable) {
        this.isEditable = isEditable;
        super.setWidth("500px");
        super.setHeight("300px");
        super.setHeaderTitle(ForeignUi.getMessage("window.publication_type_weights"));
        super.add(initRootLayout());
        super.getFooter().add(initButtonsLayout());
        super.setModalWindowProperties("aacl-publication-type-weight-window", false);
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

    private VerticalLayout initRootLayout() {
        return VaadinUtils.initSizeFullVerticalLayout(initGrid());
    }

    private Grid<PublicationType> initGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.NONE);
        addColumn(PublicationType::getName, "table.column.publication_type", "188px");
        addColumn(pubType -> CurrencyUtils.format(idsToDefaultWeights.get(pubType.getId()), null),
            "table.column.default_weight", "150px");
        var scenarioWeightColumn = addColumn(pubType -> CurrencyUtils.format(pubType.getWeight(), null),
            "table.column.scenario_weight", "160px");
        if (isEditable) {
            var editor = grid.getEditor();
            var binder = new Binder<>(PublicationType.class);
            editor.setBinder(binder);
            scenarioWeightColumn.setEditorComponent(initScenarioWeightField(binder));
            grid.addItemClickListener(event -> {
                editor.editItem(event.getItem());
                ((BigDecimalField) scenarioWeightColumn.getEditorComponent()).focus();
            });
        }
        VaadinUtils.setGridProperties(grid, "aacl-publication-type-weight-grid");
        return grid;
    }

    private Column<PublicationType> addColumn(ValueProvider<PublicationType, ?> provider, String captionProperty,
                                              String width) {
        return grid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortable(false)
            .setWidth(width)
            .setFlexGrow(0)
            .setResizable(false);
    }

    private BigDecimalField initScenarioWeightField(Binder<PublicationType> binder) {
        var scenarioWeightField = new BigDecimalField();
        scenarioWeightField.addBlurListener(event -> grid.getDataProvider().refreshAll());
        scenarioWeightField.setWidthFull();
        binder.forField(scenarioWeightField)
            .withValidator(new RequiredNumberValidator())
            .withValidator(AmountRangeValidator.zeroAmountValidator())
            .bind(PublicationType::getWeight, PublicationType::setWeight);
        return scenarioWeightField;
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
