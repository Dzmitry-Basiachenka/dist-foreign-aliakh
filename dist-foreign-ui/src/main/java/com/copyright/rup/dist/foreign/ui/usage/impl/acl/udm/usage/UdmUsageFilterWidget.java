package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.ui.common.UdmBatchFilterWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.google.common.collect.ImmutableSet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

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

    private static final long serialVersionUID = 6430443543720686514L;
    private static final Set<UsageStatusEnum> ACL_STATUSES = ImmutableSet.of(UsageStatusEnum.NEW,
        UsageStatusEnum.INELIGIBLE, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.WORK_NOT_FOUND,
        UsageStatusEnum.RH_FOUND, UsageStatusEnum.RH_NOT_FOUND, UsageStatusEnum.OPS_REVIEW,
        UsageStatusEnum.SPECIALIST_REVIEW, UsageStatusEnum.ELIGIBLE);

    private final UdmUsageAppliedFilterWidget appliedFilterWidget;
    private Button applyButton;
    private Button moreFiltersButton;
    private UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
    private UdmUsageFilter appliedUdmUsageFilter = new UdmUsageFilter();
    private UdmBatchFilterWidget udmBatchFilterWidget;
    private PeriodFilterWidget periodFilterWidget;
    private ComboBox<UsageStatusEnum> statusComboBox;
    private ComboBox<UdmUsageOriginEnum> usageOriginComboBox;
    private IUdmUsageFilterController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUdmUsageFilterController}
     */
    public UdmUsageFilterWidget(IUdmUsageFilterController controller) {
        this.controller = controller;
        this.appliedFilterWidget = new UdmUsageAppliedFilterWidget(this.controller);
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
        addComponents(initFiltersLayout(), initButtonsLayout(), buildAppliedFiltersHeaderLabel(),
            appliedFilterWidget);
        refreshFilter();
        setExpandRatio(appliedFilterWidget, 1f);
        setSizeFull();
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "udm-usages-filter-widget");
        return this;
    }

    @Override
    public void applyFilter() {
        appliedUdmUsageFilter = new UdmUsageFilter(udmUsageFilter);
        appliedFilterWidget.refreshFilterPanel(appliedUdmUsageFilter);
        filterChanged();
        fireEvent(new FilterChangedEvent(this));
    }

    @Override
    public void clearFilter() {
        clearFilterValues();
        refreshFilter();
        applyFilter();
    }

    /**
     * Handles filter change event.
     */
    protected void filterChanged() {
        applyButton.setEnabled(!udmUsageFilter.equals(appliedUdmUsageFilter));
    }

    private VerticalLayout initFiltersLayout() {
        initStatusFilter();
        initUsageOriginFilter();
        initMoreFiltersButton();
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel(), buildUdmUsageBatchFilter(),
            buildPeriodFilter(), statusComboBox, usageOriginComboBox, moreFiltersButton);
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
        statusComboBox.setItems(ACL_STATUSES);
        usageOriginComboBox.setItems(UdmUsageOriginEnum.values());
    }

    private void clearFilterValues() {
        udmBatchFilterWidget.reset();
        periodFilterWidget.reset();
        statusComboBox.clear();
        usageOriginComboBox.clear();
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }

    private Label buildAppliedFiltersHeaderLabel() {
        Label appliedFilterHeaderLabel = new Label(ForeignUi.getMessage("label.applied_filters"));
        appliedFilterHeaderLabel.addStyleNames(Cornerstone.LABEL_H2, "applied-filter-header");
        return appliedFilterHeaderLabel;
    }

    private void initMoreFiltersButton() {
        moreFiltersButton = new Button(ForeignUi.getMessage("label.more_filters"));
        moreFiltersButton.addStyleName(ValoTheme.BUTTON_LINK);
        moreFiltersButton.addClickListener(event -> {
            UdmUsageFiltersWindow udmFiltersWindow = new UdmUsageFiltersWindow(controller, udmUsageFilter);
            Windows.showModalWindow(udmFiltersWindow);
            udmFiltersWindow.addCloseListener(closeEvent -> filterChanged());
        });
        VaadinUtils.setButtonsAutoDisabled(moreFiltersButton);
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

    private PeriodFilterWidget buildPeriodFilter() {
        periodFilterWidget = new PeriodFilterWidget(() -> controller.getPeriods());
        periodFilterWidget.addFilterSaveListener((IFilterSaveListener<Integer>) saveEvent -> {
            udmUsageFilter.setPeriods(saveEvent.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(periodFilterWidget, "udm-periods-filter");
        return periodFilterWidget;
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
        usageOriginComboBox.setEnabled(!ForeignSecurityUtils.hasResearcherPermission());
        VaadinUtils.setMaxComponentsWidth(usageOriginComboBox);
        usageOriginComboBox.addValueChangeListener(event -> {
            udmUsageFilter.setUdmUsageOrigin(usageOriginComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(usageOriginComboBox, "udm-usage-origin-filter");
    }
}
