package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Window for viewing ACL licensee classes.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/11/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclAggregateLicenseeClassMappingViewWindow
    extends CommonScenarioParameterWindow<List<DetailLicenseeClass>> {

    private static final long serialVersionUID = -1495182788325466966L;

    private Map<Integer, AggregateLicenseeClass> idsToDefaultAggLicClasses;
    private Grid<DetailLicenseeClass> grid;

    /**
     * Constructor.
     */
    public AclAggregateLicenseeClassMappingViewWindow() {
        super.setWidth(850, Unit.PIXELS);
        super.setHeight(550, Unit.PIXELS);
        initGrid();
        var buttonsLayout = initButtons();
        var layout = new VerticalLayout(grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        super.setContent(layout);
        super.setCaption(ForeignUi.getMessage("window.licensee_class_mapping"));
    }

    @Override
    void setDefault(List<DetailLicenseeClass> params) {
        idsToDefaultAggLicClasses = params
            .stream()
            .collect(Collectors.toMap(DetailLicenseeClass::getId, DetailLicenseeClass::getAggregateLicenseeClass));
    }

    @Override
    void setAppliedParameters(List<DetailLicenseeClass> params) {
        grid.setItems(params.stream().map(DetailLicenseeClass::new).collect(Collectors.toList()));
    }

    @Override
    void fireParametersSaveEvent(ParametersSaveEvent<List<DetailLicenseeClass>> parametersSaveEvent) {
    }

    private HorizontalLayout initButtons() {
        HorizontalLayout layout = new HorizontalLayout(Buttons.createCloseButton(this));
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
        grid.addColumn(detLicClass -> idsToDefaultAggLicClasses.get(detLicClass.getId()).getId())
            .setCaption(ForeignUi.getMessage("table.column.default_aggregate_licensee_class_id"))
            .setExpandRatio(1);
        grid.addColumn(detLicClass -> idsToDefaultAggLicClasses.get(detLicClass.getId()).getDescription())
            .setCaption(ForeignUi.getMessage("table.column.default_aggregate_licensee_class_name"))
            .setExpandRatio(2);
        grid.addColumn(detLicClass -> detLicClass.getAggregateLicenseeClass().getId())
            .setCaption(ForeignUi.getMessage("table.column.scenario_aggregate_licensee_class_id"))
            .setExpandRatio(1);
        grid.addColumn(detLicClass -> detLicClass.getAggregateLicenseeClass().getDescription())
            .setCaption(ForeignUi.getMessage("table.column.scenario_aggregate_licensee_class_name"))
            .setExpandRatio(2);
        grid.getColumns().forEach(column -> column.setSortable(true));
        VaadinUtils.addComponentStyle(grid, "acl-aggregate-licensee-class-mapping-grid");
    }
}
