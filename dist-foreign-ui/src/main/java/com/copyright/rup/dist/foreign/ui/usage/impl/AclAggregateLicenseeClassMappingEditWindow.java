package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.server.SerializableComparator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Window for editing ACL licensee classes.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/13/2022
 *
 * @author Mikita Maistrenka
 */
public class AclAggregateLicenseeClassMappingEditWindow
    extends CommonScenarioParameterWindow<List<DetailLicenseeClass>> {

    private static final long serialVersionUID = -8088118012002362394L;

    private List<DetailLicenseeClass> defaultValues;
    private List<DetailLicenseeClass> currentValues;
    private Grid<DetailLicenseeClass> grid;
    private final List<AggregateLicenseeClass> aggregateLicenseeClasses;

    /**
     * Constructor.
     *
     * @param aggregateLicenseeClasses list of {@link AggregateLicenseeClass}es from a database
     */
    public AclAggregateLicenseeClassMappingEditWindow(List<AggregateLicenseeClass> aggregateLicenseeClasses) {
        super.setWidth(600, Unit.PIXELS);
        super.setHeight(550, Unit.PIXELS);
        this.aggregateLicenseeClasses = aggregateLicenseeClasses;
        initGrid();
        HorizontalLayout buttonsLayout = initButtons();
        VerticalLayout layout = new VerticalLayout(grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        super.setContent(layout);
        super.setCaption(ForeignUi.getMessage("window.licensee_class_mapping"));
    }

    @Override
    void setDefault(List<DetailLicenseeClass> params) {
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
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        Label placeholderLabel = new Label();
        Button defaultButton = Buttons.createButton(ForeignUi.getMessage("button.default"));
        defaultButton.addClickListener(event -> setAppliedParameters(defaultValues));
        saveButton.addClickListener(event -> {
            fireParametersSaveEvent(new ParametersSaveEvent<>(this, currentValues));
            close();
        });
        VaadinUtils.setButtonsAutoDisabled(saveButton);
        HorizontalLayout layout = new HorizontalLayout(saveButton, Buttons.createCloseButton(this), placeholderLabel,
            defaultButton);
        layout.setSpacing(true);
        VaadinUtils.addComponentStyle(layout, "view-acl-licensee-classes-mapping-buttons");
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
            .setCaption(ForeignUi.getMessage("table.column.det_lc_id"))
            .setExpandRatio(1);
        grid.addColumn(DetailLicenseeClass::getDescription)
            .setCaption(ForeignUi.getMessage("table.column.det_lc_name"))
            .setExpandRatio(2);
        grid.addComponentColumn(this::buildComboBox)
            .setCaption(ForeignUi.getMessage("table.column.aggregate_licensee_class_id"))
            .setStyleGenerator(licensee -> "combobox-column")
            .setComparator((SerializableComparator<DetailLicenseeClass>) (detail1, detail2) ->
                detail1.getAggregateLicenseeClass().getId().compareTo(detail2.getAggregateLicenseeClass().getId()))
            .setExpandRatio(1);
        grid.addColumn(licenseeClass -> licenseeClass.getAggregateLicenseeClass().getDescription())
            .setCaption(ForeignUi.getMessage("table.column.aggregate_licensee_class_name"))
            .setExpandRatio(2);
        grid.getColumns().forEach(column -> column.setSortable(true));
        VaadinUtils.addComponentStyle(grid, "acl-aggregate-licensee-class-mapping-grid");
    }

    private ComboBox<AggregateLicenseeClass> buildComboBox(DetailLicenseeClass detailLicenseeClass) {
        ComboBox<AggregateLicenseeClass> comboBox = new ComboBox<>();
        comboBox.setEmptySelectionAllowed(false);
        comboBox.setItemCaptionGenerator(aggregateLicenseeClass -> aggregateLicenseeClass.getId().toString());
        comboBox.setItems(aggregateLicenseeClasses);
        comboBox.setSelectedItem(detailLicenseeClass.getAggregateLicenseeClass());
        comboBox.setWidth("70");
        comboBox.addValueChangeListener(listener -> {
            if (Objects.nonNull(listener.getValue())) {
                detailLicenseeClass.setAggregateLicenseeClass(listener.getValue());
            } else {
                detailLicenseeClass.setAggregateLicenseeClass(listener.getOldValue());
            }
            grid.setItems(currentValues);
        });
        return comboBox;
    }
}
