package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AclScenarioFilter;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.ui.common.LicenseTypeFilterWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;
import java.util.Objects;

/**
 * Widget for filtering ACL scenarios.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/31/2022
 *
 * @author Mikita Maistrenka
 */
public class AclScenariosFilterWidget extends VerticalLayout implements IAclScenariosFilterWidget {

    private static final long serialVersionUID = 2932740831815178381L;

    private IAclScenariosFilterController controller;
    private Button applyButton;
    private AclScenarioFilter aclScenarioFilter = new AclScenarioFilter();
    private AclScenarioFilter appliedAclScenarioFilter = new AclScenarioFilter();
    private PeriodFilterWidget periodFilterWidget;
    private LicenseTypeFilterWidget licenseTypeFilterWidget;
    private ComboBox<FilterOperatorEnum> editableComboBox;
    private ComboBox<ScenarioStatusEnum> statusTypeComboBox;
    private final AclScenariosAppliedFilterWidget appliedFilterWidget;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclScenariosFilterController}
     */
    public AclScenariosFilterWidget(IAclScenariosFilterController controller) {
        this.controller = controller;
        appliedFilterWidget = new AclScenariosAppliedFilterWidget();
    }

    @Override
    public AclScenarioFilter getFilter() {
        return aclScenarioFilter;
    }

    @Override
    public AclScenarioFilter getAppliedFilter() {
        return appliedAclScenarioFilter;
    }

    @Override
    public void applyFilter() {
        appliedAclScenarioFilter = new AclScenarioFilter(aclScenarioFilter);
        appliedFilterWidget.refreshFilterPanel(appliedAclScenarioFilter);
        filterChanged();
        fireEvent(new FilterChangedEvent(this));
    }

    @Override
    public void clearFilter() {
        periodFilterWidget.reset();
        licenseTypeFilterWidget.reset();
        editableComboBox.clear();
        statusTypeComboBox.clear();
        aclScenarioFilter = new AclScenarioFilter();
        applyFilter();
    }

    @Override
    @SuppressWarnings("unchecked")
    public AclScenariosFilterWidget init() {
        addComponents(initFiltersLayout(), initButtonsLayout(),  buildAppliedFiltersHeaderLabel(),
            appliedFilterWidget);
        setExpandRatio(appliedFilterWidget, 1f);
        editableComboBox.setItems(FilterOperatorEnum.Y, FilterOperatorEnum.N);
        statusTypeComboBox.setItems(ScenarioStatusEnum.IN_PROGRESS, ScenarioStatusEnum.SUBMITTED,
            ScenarioStatusEnum.APPROVED, ScenarioStatusEnum.ARCHIVED);
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "acl-scenarios-filter-widget");
        return this;
    }

    @Override
    public void setController(IAclScenariosFilterController controller) {
        this.controller = controller;
    }

    private VerticalLayout initFiltersLayout() {
        initEditableFilter();
        initStatusFilter();
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel(), buildPeriodFilter(),
             buildLicenseTypeFilter(), editableComboBox, statusTypeComboBox);
        verticalLayout.setMargin(false);
        return verticalLayout;
    }

    private PeriodFilterWidget buildPeriodFilter() {
        periodFilterWidget = new PeriodFilterWidget(() -> controller.getPeriods());
        periodFilterWidget.addFilterSaveListener((IFilterSaveListener<Integer>) saveEvent -> {
            aclScenarioFilter.setPeriods(saveEvent.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(periodFilterWidget, "acl-scenarios-periods-filter");
        return periodFilterWidget;
    }

    private LicenseTypeFilterWidget buildLicenseTypeFilter() {
        licenseTypeFilterWidget = new LicenseTypeFilterWidget(() -> List.of("ACL", "MACL", "VGW", "JACDCL"),
            aclScenarioFilter.getLicenseTypes());
        licenseTypeFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent -> {
            aclScenarioFilter.setLicenseTypes(saveEvent.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(licenseTypeFilterWidget, "acl-scenarios-license-types-filter");
        return licenseTypeFilterWidget;
    }

    private void initEditableFilter() {
        editableComboBox = new ComboBox<>(ForeignUi.getMessage("label.editable"));
        VaadinUtils.setMaxComponentsWidth(editableComboBox);
        editableComboBox.addValueChangeListener(event -> {
            aclScenarioFilter.setEditable(
                Objects.isNull(editableComboBox.getValue())
                    ? null
                    : editableOperatorToBoolean(editableComboBox.getValue()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(editableComboBox, "acl-scenarios-editable-filter");
    }

    private void initStatusFilter() {
        statusTypeComboBox = new ComboBox<>(ForeignUi.getMessage("label.status"));
        VaadinUtils.setMaxComponentsWidth(statusTypeComboBox);
        statusTypeComboBox.addValueChangeListener(event -> {
            aclScenarioFilter.setStatus(statusTypeComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(statusTypeComboBox, "acl-scenarios-status-filter");
    }

    private HorizontalLayout initButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
        applyButton.setEnabled(false);
        applyButton.addClickListener(event -> applyFilter());
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        HorizontalLayout horizontalLayout = new HorizontalLayout(applyButton, clearButton);
        VaadinUtils.setMaxComponentsWidth(horizontalLayout, applyButton, clearButton);
        VaadinUtils.addComponentStyle(horizontalLayout, "acl-scenarios-filter-buttons");
        return horizontalLayout;
    }

    private void filterChanged() {
        applyButton.setEnabled(!aclScenarioFilter.equals(appliedAclScenarioFilter));
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(ValoTheme.LABEL_H2);
        return filterHeaderLabel;
    }

    private Label buildAppliedFiltersHeaderLabel() {
        Label appliedFilterHeaderLabel = new Label(ForeignUi.getMessage("label.applied_filters"));
        appliedFilterHeaderLabel.addStyleNames(ValoTheme.LABEL_H2, "applied-filter-header");
        return appliedFilterHeaderLabel;
    }

    private static Boolean editableOperatorToBoolean(FilterOperatorEnum filterOperator) {
        return filterOperator.equals(FilterOperatorEnum.Y);
    }
}
