package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import com.copyright.rup.dist.foreign.domain.AclciLicenseTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.ui.common.AclciLicenseTypeFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.UsageBatchFilterWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageAppliedFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.google.common.collect.ImmutableSet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IAclciUsageFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/18/2022
 *
 * @author Aliaksanr Liakh
 */
public class AclciUsageFilterWidget extends CommonUsageFilterWidget implements IAclciUsageFilterWidget {

    private static final long serialVersionUID = -8383184686127401514L;
    private static final Set<UsageStatusEnum> STATUSES =
        ImmutableSet.of(UsageStatusEnum.NEW, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.WORK_NOT_FOUND,
            UsageStatusEnum.RH_FOUND, UsageStatusEnum.WORK_NOT_GRANTED, UsageStatusEnum.RH_NOT_FOUND,
            UsageStatusEnum.ELIGIBLE);
    private static final Set<AclciLicenseTypeEnum> LICENSE_TYPES =
        ImmutableSet.of(AclciLicenseTypeEnum.CURR_REPUB_HE, AclciLicenseTypeEnum.CURR_REPUB_K12,
            AclciLicenseTypeEnum.CURR_REUSE_K12, AclciLicenseTypeEnum.CURR_SHARE_K12);

    private final IAclciUsageFilterController controller;
    private final AclciUsageAppliedFilterWidget appliedFilterWidget;
    private UsageBatchFilterWidget usageBatchFilterWidget;
    private ComboBox<UsageStatusEnum> statusComboBox;
    private AclciLicenseTypeFilterWidget licenseTypesFilterWidget;
    private UsageFilter usageFilter = new UsageFilter();
    private UsageFilter appliedUsageFilter = new UsageFilter();
    private Button applyButton;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclciUsageFilterController}
     */
    public AclciUsageFilterWidget(IAclciUsageFilterController controller) {
        this.controller = controller;
        appliedFilterWidget = new AclciUsageAppliedFilterWidget(this.controller);
    }

    @Override
    @SuppressWarnings("unchecked")
    public AclciUsageFilterWidget init() {
        addComponents(initFiltersLayout(), initFilterButtonsLayout(), buildAppliedFiltersHeaderLabel(),
            appliedFilterWidget);
        refreshFilterValues();
        setExpandRatio(appliedFilterWidget, 1f);
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "aclci-usages-filter-widget");
        return this;
    }

    @Override
    public void applyFilter() {
        appliedUsageFilter = new UsageFilter(usageFilter);
        appliedFilterWidget.refreshFilterPanel(appliedUsageFilter);
        filterChanged();
        fireEvent(new FilterChangedEvent(this));
    }

    @Override
    protected VerticalLayout initFiltersLayout() {
        initStatusFilter();
        VerticalLayout verticalLayout =
            new VerticalLayout(buildFiltersHeaderLabel(), buildUsageBatchFilter(), statusComboBox,
                buildLicenseTypesFilter());
        verticalLayout.setMargin(false);
        return verticalLayout;
    }

    @Override
    protected CommonUsageAppliedFilterWidget getAppliedFilterWidget() {
        return new AclciUsageAppliedFilterWidget(controller);
    }

    @Override
    protected void refreshFilterValues() {
        statusComboBox.setItems(STATUSES);
    }

    @Override
    protected void clearFilterValues() {
        usageBatchFilterWidget.reset();
        statusComboBox.clear();
        licenseTypesFilterWidget.reset();
        usageFilter = new UsageFilter();
        applyFilter();
    }

    @Override
    protected void filterChanged() {
        applyButton.setEnabled(!usageFilter.equals(appliedUsageFilter));
    }

    @Override
    public UsageFilter getFilter() {
        return usageFilter;
    }

    @Override
    public UsageFilter getAppliedFilter() {
        return appliedUsageFilter;
    }

    private HorizontalLayout buildUsageBatchFilter() {
        usageBatchFilterWidget = new UsageBatchFilterWidget(controller::getUsageBatches);
        usageBatchFilterWidget.addFilterSaveListener((FilterWindow.IFilterSaveListener<UsageBatch>) saveEvent -> {
            usageFilter.setUsageBatchesIds(saveEvent.getSelectedItemsIds().stream()
                .map(UsageBatch::getId)
                .collect(Collectors.toSet()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(usageBatchFilterWidget, "batches-filter");
        return usageBatchFilterWidget;
    }

    private void initStatusFilter() {
        statusComboBox = new ComboBox<>(ForeignUi.getMessage("label.status"));
        statusComboBox.addValueChangeListener(event -> {
            usageFilter.setUsageStatus(statusComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.setMaxComponentsWidth(statusComboBox);
        VaadinUtils.addComponentStyle(statusComboBox, "status-filter");
    }

    private AclciLicenseTypeFilterWidget buildLicenseTypesFilter() {
        licenseTypesFilterWidget = new AclciLicenseTypeFilterWidget(LICENSE_TYPES, usageFilter.getLicenseTypes());
        licenseTypesFilterWidget.addFilterSaveListener((IFilterSaveListener<AclciLicenseTypeEnum>) saveEvent -> {
            usageFilter.setLicenseTypes(saveEvent.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(licenseTypesFilterWidget, "license-types-filter");
        return licenseTypesFilterWidget;
    }

    private HorizontalLayout initFilterButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
        applyButton.setEnabled(false);
        applyButton.addClickListener(event -> applyFilter());
        var clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        var horizontalLayout = new HorizontalLayout(applyButton, clearButton);
        VaadinUtils.setMaxComponentsWidth(horizontalLayout, applyButton, clearButton);
        VaadinUtils.addComponentStyle(horizontalLayout, "filter-buttons");
        return horizontalLayout;
    }
}
