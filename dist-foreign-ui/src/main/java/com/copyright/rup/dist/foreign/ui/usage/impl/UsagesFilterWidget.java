package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.ui.common.RightsholderFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.UsageBatchFilterWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.google.common.collect.Sets;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Widget for filtering usages.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/11/2017
 *
 * @author Mikita Hladkikh
 */
//TODO: split it into product specific usage filters
class UsagesFilterWidget extends VerticalLayout implements IUsagesFilterWidget {

    private Button applyButton;
    private LocalDateWidget paymentDateWidget;
    private ComboBox<Integer> fiscalYearComboBox;
    private ComboBox<UsageStatusEnum> statusComboBox;
    private IUsagesFilterController controller;
    private RightsholderFilterWidget rightsholderFilterWidget;
    private UsageBatchFilterWidget usageBatchFilterWidget;
    private UsageFilter usageFilter = new UsageFilter();
    private UsageFilter appliedUsageFilter = new UsageFilter();
    private static final Set<UsageStatusEnum> FAS_FAS2_STATUSES = Sets.newHashSet(UsageStatusEnum.NEW,
        UsageStatusEnum.WORK_NOT_FOUND, UsageStatusEnum.WORK_RESEARCH, UsageStatusEnum.WORK_FOUND,
        UsageStatusEnum.RH_NOT_FOUND, UsageStatusEnum.RH_FOUND, UsageStatusEnum.SENT_FOR_RA, UsageStatusEnum.ELIGIBLE);
    private static final Set<UsageStatusEnum> NTS_STATUSES = Sets.newHashSet(UsageStatusEnum.NTS_WITHDRAWN,
        UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_FOUND, UsageStatusEnum.UNCLASSIFIED, UsageStatusEnum.ELIGIBLE,
        UsageStatusEnum.TO_BE_DISTRIBUTED, UsageStatusEnum.NTS_EXCLUDED);

    @Override
    @SuppressWarnings("unchecked")
    public UsagesFilterWidget init() {
        addComponents(initFiltersLayout(), initButtonsLayout());
        refreshFilterValues();
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "usages-filter-widget");
        return this;
    }

    @Override
    public void applyFilter() {
        appliedUsageFilter = new UsageFilter(usageFilter);
        filterChanged();
        fireEvent(new FilterChangedEvent(this));
    }

    @Override
    public void clearFilter() {
        paymentDateWidget.clear();
        fiscalYearComboBox.clear();
        statusComboBox.clear();
        rightsholderFilterWidget.reset();
        usageBatchFilterWidget.reset();
        refreshFilterValues();
        applyFilter();
    }

    @Override
    public UsageFilter getFilter() {
        return usageFilter;
    }

    @Override
    public UsageFilter getAppliedFilter() {
        return appliedUsageFilter;
    }

    /**
     * @return instance of {@link IUsagesFilterController}.
     */
    IUsagesFilterController getController() {
        return controller;
    }

    @Override
    public void setController(IUsagesFilterController controller) {
        this.controller = controller;
    }

    private void refreshFilterValues() {
        String selectedProductFamily = controller.getSelectedProductFamily();
        fiscalYearComboBox.setItems(getController().getFiscalYears(selectedProductFamily));
        statusComboBox.setItems(getAvailableStatuses());
        usageFilter = new UsageFilter();
        usageFilter.setProductFamilies(Collections.singleton(selectedProductFamily));
    }

    private VerticalLayout initFiltersLayout() {
        initPaymentDateFilter();
        initFiscalYearFilter();
        initStatusFilter();
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel(), buildUsageBatchFilter(),
            buildRroAccountNumberFilter(), paymentDateWidget, statusComboBox, fiscalYearComboBox);
        verticalLayout.setMargin(false);
        return verticalLayout;
    }

    private HorizontalLayout buildUsageBatchFilter() {
        usageBatchFilterWidget = new UsageBatchFilterWidget(
            () -> controller.getUsageBatches(controller.getSelectedProductFamily()));
        usageBatchFilterWidget.addFilterSaveListener((IFilterSaveListener<UsageBatch>) saveEvent -> {
            usageFilter.setUsageBatchesIds(
                saveEvent.getSelectedItemsIds().stream().map(UsageBatch::getId).collect(Collectors.toSet()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(usageBatchFilterWidget, "batches-filter");
        return usageBatchFilterWidget;
    }

    private HorizontalLayout buildRroAccountNumberFilter() {
        rightsholderFilterWidget = new RightsholderFilterWidget(
            ForeignUi.getMessage("label.rros"),
            ForeignUi.getMessage("prompt.rro"),
            ForeignUi.getMessage("message.error.rro_not_found"),
            () -> controller.getRros(controller.getSelectedProductFamily()));
        rightsholderFilterWidget.addFilterSaveListener((IFilterSaveListener<Rightsholder>) saveEvent -> {
            usageFilter.setRhAccountNumbers(saveEvent.getSelectedItemsIds()
                .stream()
                .map(Rightsholder::getAccountNumber)
                .collect(Collectors.toSet()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(rightsholderFilterWidget, "rightsholders-filter");
        return rightsholderFilterWidget;
    }

    private void initStatusFilter() {
        statusComboBox = new ComboBox<>(ForeignUi.getMessage("label.status"));
        VaadinUtils.setMaxComponentsWidth(statusComboBox);
        statusComboBox.addValueChangeListener(event -> {
            usageFilter.setUsageStatus(statusComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(statusComboBox, "status-filter");
    }

    private void initFiscalYearFilter() {
        fiscalYearComboBox = new ComboBox<>(ForeignUi.getMessage("label.fiscal_year_to"));
        VaadinUtils.setMaxComponentsWidth(fiscalYearComboBox);
        fiscalYearComboBox.addValueChangeListener(event -> {
            usageFilter.setFiscalYear(fiscalYearComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(fiscalYearComboBox, "fiscal-year-filter");
    }

    private void initPaymentDateFilter() {
        paymentDateWidget = new LocalDateWidget(ForeignUi.getMessage("label.payment_date_to"));
        paymentDateWidget.addValueChangeListener(event -> {
            usageFilter.setPaymentDate(paymentDateWidget.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(paymentDateWidget, "payment-date-filter");
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }

    private HorizontalLayout initButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
        applyButton.setEnabled(false);
        applyButton.addClickListener(event -> applyFilter());
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        HorizontalLayout horizontalLayout = new HorizontalLayout(applyButton, clearButton);
        VaadinUtils.setMaxComponentsWidth(horizontalLayout, applyButton, clearButton);
        VaadinUtils.addComponentStyle(horizontalLayout, "filter-buttons");
        return horizontalLayout;
    }

    private Set<UsageStatusEnum> getAvailableStatuses() {
        return FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET.contains(controller.getSelectedProductFamily())
            ? FAS_FAS2_STATUSES
            : NTS_STATUSES;
    }

    private void filterChanged() {
        applyButton.setEnabled(!usageFilter.equals(appliedUsageFilter));
    }
}
