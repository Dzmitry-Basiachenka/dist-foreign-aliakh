package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.ui.common.UdmBatchFilterWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.google.common.collect.ImmutableSet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Widget for filtering UDM usages.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/03/2021
 *
 * @author Dzmitry Basiachenka
 */
public class UdmUsageFilterWidget extends VerticalLayout implements IUdmUsageFilterWidget {

    private static final Set<UsageStatusEnum> ACL_STATUSES = ImmutableSet.of(UsageStatusEnum.NEW,
        UsageStatusEnum.INELIGIBLE);

    private Button applyButton;
    private UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
    private UdmUsageFilter appliedUdmUsageFilter = new UdmUsageFilter();
    private UdmBatchFilterWidget udmBatchFilterWidget;
    private ComboBox<UsageStatusEnum> statusComboBox;
    private ComboBox<Integer> periodComboBox;
    private ComboBox<UdmUsageOriginEnum> usageOriginComboBox;
    private IUdmUsageFilterController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUdmUsageFilterController}
     */
    public UdmUsageFilterWidget(IUdmUsageFilterController controller) {
        this.controller = controller;
    }

    @Override
    public UdmUsageFilter getFilter() {
        return udmUsageFilter;
    }

    @Override
    public UdmUsageFilter getAppliedFilter() {
        return appliedUdmUsageFilter;
    }

    @Override
    public void setController(IUdmUsageFilterController controller) {
        this.controller = controller;
    }

    @Override
    @SuppressWarnings("unchecked")
    public IUdmUsageFilterWidget init() {
        addComponents(initFiltersLayout(), initButtonsLayout());
        refreshFilter();
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "usages-filter-widget");
        return this;
    }

    /**
     * Handles filter change event.
     */
    protected void filterChanged() {
        applyButton.setEnabled(!udmUsageFilter.equals(appliedUdmUsageFilter));
    }

    /**
     * Applies specified filters.
     */
    protected void applyFilter() {
        appliedUdmUsageFilter = new UdmUsageFilter(udmUsageFilter);
        filterChanged();
        fireEvent(new FilterChangedEvent(this));
    }

    /**
     * Clears current filters.
     */
    protected void clearFilter() {
        clearFilterValues();
        refreshFilter();
        applyFilter();
    }

    private VerticalLayout initFiltersLayout() {
        initPeriodFilter();
        initStatusFilter();
        initUsageOriginFilter();
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel(), buildUdmUsageBatchFilter(),
            periodComboBox, statusComboBox, usageOriginComboBox);
        verticalLayout.setMargin(false);
        return verticalLayout;
    }

    private HorizontalLayout initButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
        applyButton.setEnabled(false);
        applyButton.addClickListener(event -> applyFilter());
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        HorizontalLayout horizontalLayout = new HorizontalLayout(applyButton, clearButton);
        VaadinUtils.setMaxComponentsWidth(horizontalLayout, applyButton, clearButton);
        VaadinUtils.addComponentStyle(horizontalLayout, "udm-filter-buttons");
        return horizontalLayout;
    }

    private void refreshFilter() {
        refreshFilterValues();
        udmUsageFilter = new UdmUsageFilter();
    }

    private void refreshFilterValues() {
        periodComboBox.setItems(controller.getPeriods());
        statusComboBox.setItems(ACL_STATUSES);
        usageOriginComboBox.setItems(UdmUsageOriginEnum.values());
    }

    private void clearFilterValues() {
        udmBatchFilterWidget.reset();
        statusComboBox.clear();
        periodComboBox.clear();
        usageOriginComboBox.clear();
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }

    private HorizontalLayout buildUdmUsageBatchFilter() {
        udmBatchFilterWidget = new UdmBatchFilterWidget(() -> controller.getUdmBatches());
        udmBatchFilterWidget.addFilterSaveListener((IFilterSaveListener<UdmBatch>) saveEvent -> {
            udmUsageFilter.setUdmBatchesIds(
                saveEvent.getSelectedItemsIds().stream().map(UdmBatch::getId).collect(Collectors.toSet()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(udmBatchFilterWidget, "udm-batches-filter");
        return udmBatchFilterWidget;
    }

    private void initPeriodFilter() {
        periodComboBox = new ComboBox<>(ForeignUi.getMessage("label.period"));
        VaadinUtils.setMaxComponentsWidth(periodComboBox);
        periodComboBox.addValueChangeListener(event -> {
            udmUsageFilter.setPeriod(periodComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(periodComboBox, "udm-period-filter");
    }

    private void initStatusFilter() {
        statusComboBox = new ComboBox<>(ForeignUi.getMessage("label.status"));
        VaadinUtils.setMaxComponentsWidth(statusComboBox);
        statusComboBox.addValueChangeListener(event -> {
            udmUsageFilter.setUsageStatus(statusComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(statusComboBox, "udm-status-filter");
    }

    private void initUsageOriginFilter() {
        usageOriginComboBox = new ComboBox<>(ForeignUi.getMessage("label.usage_origin"));
        VaadinUtils.setMaxComponentsWidth(usageOriginComboBox);
        usageOriginComboBox.addValueChangeListener(event -> {
            udmUsageFilter.setUdmUsageOrigin(usageOriginComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(usageOriginComboBox, "udm-usage-origin-filter");
    }
}
