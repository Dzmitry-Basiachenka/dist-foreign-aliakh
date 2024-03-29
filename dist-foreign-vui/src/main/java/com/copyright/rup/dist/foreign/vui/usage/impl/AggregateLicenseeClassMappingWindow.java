package com.copyright.rup.dist.foreign.vui.usage.impl;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.vui.common.IGridColumnAdder;
import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializableComparator;

import java.util.List;
import java.util.Objects;
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
public class AggregateLicenseeClassMappingWindow extends CommonScenarioParameterWindow<List<DetailLicenseeClass>>
    implements IGridColumnAdder<DetailLicenseeClass> {

    private static final long serialVersionUID = -5516628610762919061L;

    private final boolean isEditable;

    private List<DetailLicenseeClass> defaultValues;
    private List<DetailLicenseeClass> currentValues;
    private Grid<DetailLicenseeClass> grid;

    /**
     * Constructor.
     *
     * @param isEditable {@code true} if window should be in edit mode, otherwise {@code false}
     */
    public AggregateLicenseeClassMappingWindow(boolean isEditable) {
        this.isEditable = isEditable;
        super.setWidth("950px");
        super.setHeight("550px");
        super.setHeaderTitle(ForeignUi.getMessage("window.licensee_class_mapping"));
        super.add(initRootLayout());
        super.getFooter().add(initButtonsLayout());
        super.setModalWindowProperties("aacl-licensee-class-window", false);
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

    private VerticalLayout initRootLayout() {
        return VaadinUtils.initSizeFullVerticalLayout(initGrid());
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

    private Grid<DetailLicenseeClass> initGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.NONE);
        addGridColumns();
        return grid;
    }

    private void addGridColumns() {
        addColumn(grid, DetailLicenseeClass::getId, GridColumnEnum.DET_LC_ID);
        addColumn(grid, DetailLicenseeClass::getEnrollmentProfile, GridColumnEnum.DET_LC_ENROLLMENT);
        addColumn(grid, DetailLicenseeClass::getDiscipline, GridColumnEnum.DET_LC_DISCIPLINE);
        addAggregateLicenseeClassIdColumn();
        addColumn(grid, licenseeClass -> licenseeClass.getAggregateLicenseeClass().getEnrollmentProfile(),
            GridColumnEnum.AGGREGATE_LC_ENROLLMENT);
        addColumn(grid, licenseeClass -> licenseeClass.getAggregateLicenseeClass().getDiscipline(),
            GridColumnEnum.AGGREGATE_LC_DISCIPLINE);
        VaadinUtils.setGridProperties(grid, "aggregate-licensee-class-mapping-grid");
    }

    private void addAggregateLicenseeClassIdColumn() {
        if (isEditable) {
            grid.addComponentColumn(this::buildComboBox)
                .setHeader(ForeignUi.getMessage(GridColumnEnum.AGGREGATE_LICENSEE_CLASS_ID.getCaption()))
                .setFlexGrow(0)
                .setWidth(GridColumnEnum.AGGREGATE_LICENSEE_CLASS_ID.getWidth())
                .setSortable(true)
                .setResizable(true)
                .setComparator((SerializableComparator<DetailLicenseeClass>) (detail1, detail2) ->
                    detail1.getAggregateLicenseeClass().getId().compareTo(detail2.getAggregateLicenseeClass().getId()));
        } else {
            addColumn(grid, licenseeClass -> licenseeClass.getAggregateLicenseeClass().getId(),
                GridColumnEnum.AGGREGATE_LICENSEE_CLASS_ID);
        }
    }

    private ComboBox<AggregateLicenseeClass> buildComboBox(DetailLicenseeClass detailLicenseeClass) {
        var comboBox = new ComboBox<AggregateLicenseeClass>();
        comboBox.setItemLabelGenerator(aggregateLicenseeClass -> aggregateLicenseeClass.getId().toString());
        comboBox.setItems(defaultValues.stream().map(DetailLicenseeClass::getAggregateLicenseeClass));
        comboBox.setValue(detailLicenseeClass.getAggregateLicenseeClass());
        comboBox.setWidth("80px");
        comboBox.addValueChangeListener(event -> {
            detailLicenseeClass.setAggregateLicenseeClass(Objects.nonNull(event.getValue())
                ? event.getValue() : event.getOldValue());
            grid.setItems(currentValues);
        });
        return comboBox;
    }
}
