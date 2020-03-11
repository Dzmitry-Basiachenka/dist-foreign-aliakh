package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AaclScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.server.SerializableComparator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Window for working with aggregate licensee classes.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/11/2020
 *
 * @author Ihar Suvorau
 */
public class AggregateLicenseeClassMappingWindow extends AaclCommonScenarioParameterWindow<List<DetailLicenseeClass>> {

    private List<DetailLicenseeClass> defaultValues;
    private List<DetailLicenseeClass> currentValues;
    private Grid<DetailLicenseeClass> grid;

    /**
     * Constructor.
     */
    public AggregateLicenseeClassMappingWindow() {
        setWidth(1000, Unit.PIXELS);
        setHeight(550, Unit.PIXELS);
        initGrid();
        HorizontalLayout buttonsLayout = initButtons();
        VerticalLayout layout = new VerticalLayout(grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(layout);
        setCaption(ForeignUi.getMessage("window.licensee_class_mapping"));
    }

    @Override
    void setDefaultParameters(List<DetailLicenseeClass> params) {
        defaultValues = params;
    }

    @Override
    void setAppliedParameters(List<DetailLicenseeClass> params) {
        currentValues = params.stream().map(DetailLicenseeClass::new).collect(Collectors.toList());
        grid.setItems(currentValues);
    }

    @Override
    void fireParametersSaveEvent(ParametersSaveEvent<List<DetailLicenseeClass>> parametersSaveEvent) {
        fireEvent(parametersSaveEvent);
    }

    private HorizontalLayout initButtons() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        Button defaultButton = Buttons.createButton(ForeignUi.getMessage("button.default"));
        defaultButton.addClickListener(event -> setAppliedParameters(defaultValues));
        saveButton.addClickListener(event -> {
            fireParametersSaveEvent(new ParametersSaveEvent<>(this, currentValues));
            close();
        });
        HorizontalLayout layout = new HorizontalLayout(saveButton, defaultButton, closeButton);
        layout.setSpacing(true);
        VaadinUtils.addComponentStyle(layout, "view-aacl-fund-pool-buttons");
        return layout;
    }

    private void initGrid() {
        grid = new Grid<>();
        grid.setSizeFull();
        grid.setSelectionMode(SelectionMode.NONE);
        addGridColumns();
    }

    private void addGridColumns() {
        grid.addColumn(DetailLicenseeClass::getId)
            .setCaption(ForeignUi.getMessage("table.column.detail_licensee_class_id"))
            .setWidth(160);
        grid.addColumn(DetailLicenseeClass::getEnrollmentProfile)
            .setCaption(ForeignUi.getMessage("table.column.enrollment_profile"))
            .setWidth(140);
        grid.addColumn(DetailLicenseeClass::getDiscipline)
            .setCaption(ForeignUi.getMessage("table.column.discipline"))
            .setWidth(210);
        grid.addComponentColumn(this::buildComboBox)
            .setCaption(ForeignUi.getMessage("table.column.aggregate_licensee_class_id"))
            .setStyleGenerator(licensee -> "combobox-column")
            .setComparator((SerializableComparator<DetailLicenseeClass>) (detail1, detail2) ->
                detail1.getAggregateLicenseeClass().getId().compareTo(detail2.getAggregateLicenseeClass().getId()))
            .setWidth(190);
        grid.addColumn(licenseeClass -> licenseeClass.getAggregateLicenseeClass().getName())
            .setCaption(ForeignUi.getMessage("table.column.aggregate_licensee_class_name"))
            .setExpandRatio(1);
        grid.getColumns().forEach(column -> column.setSortable(true));
        VaadinUtils.addComponentStyle(grid, "aggregate-licensee-class-mapping-grid");
    }

    private ComboBox<AggregateLicenseeClass> buildComboBox(DetailLicenseeClass detailLicenseeClass) {
        ComboBox<AggregateLicenseeClass> comboBox = new ComboBox<>();
        comboBox.setEmptySelectionAllowed(false);
        comboBox.setItemCaptionGenerator(aggregateLicenseeClass -> aggregateLicenseeClass.getId().toString());
        comboBox.setItems(defaultValues.stream().map(DetailLicenseeClass::getAggregateLicenseeClass));
        comboBox.setSelectedItem(detailLicenseeClass.getAggregateLicenseeClass());
        comboBox.setWidth("40");
        comboBox.addValueChangeListener(listener -> {
            detailLicenseeClass.setAggregateLicenseeClass(listener.getValue());
            grid.getDataProvider().refreshAll();
        });
        return comboBox;
    }
}
