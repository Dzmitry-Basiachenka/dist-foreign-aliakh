package com.copyright.rup.dist.foreign.vui.usage.impl;

import com.copyright.rup.dist.foreign.domain.PublicationType;
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
        super.setWidth("525px");
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
        //TODO {aliakh} use GridColumnEnum and IGridColumnAdder
        grid.addColumn(PublicationType::getName)
            .setHeader(ForeignUi.getMessage("table.column.publication_type"))
            .setSortable(false);
        grid.addColumn(item -> CurrencyUtils.format(idsToDefaultWeights.get(item.getId()), null))
            .setHeader(ForeignUi.getMessage("table.column.default_weight"))
            .setSortable(false);
            grid.addColumn(item -> CurrencyUtils.format(item.getWeight(), null))
                .setHeader(ForeignUi.getMessage("table.column.scenario_weight"))
                .setSortable(false);
        //TODO {aliakh} implement if (isEditable)
        VaadinUtils.setGridProperties(grid, "aacl-publication-type-weight-grid");
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
