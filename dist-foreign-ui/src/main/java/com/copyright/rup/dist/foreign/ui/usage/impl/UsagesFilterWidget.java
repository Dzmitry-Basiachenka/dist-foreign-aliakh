package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.ui.common.ProductFamilyFilterWidget;
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

import com.google.common.collect.Lists;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.List;
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
class UsagesFilterWidget extends VerticalLayout implements IUsagesFilterWidget {

    private Button applyButton;
    private LocalDateWidget paymentDateWidget;
    private ComboBox<Integer> fiscalYearComboBox;
    private ComboBox<UsageStatusEnum> statusComboBox;
    private IUsagesFilterController controller;
    private RightsholderFilterWidget rightsholderFilterWidget;
    private UsageBatchFilterWidget usageBatchFilterWidget;
    private ProductFamilyFilterWidget productFamilyFilterWidget;
    private UsageFilter usageFilter = new UsageFilter();
    private UsageFilter appliedUsageFilter = new UsageFilter();

    @Override
    @SuppressWarnings("unchecked")
    public UsagesFilterWidget init() {
        addComponents(initFiltersLayout(), initButtonsLayout());
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
        usageFilter = new UsageFilter();
        rightsholderFilterWidget.reset();
        usageBatchFilterWidget.reset();
        productFamilyFilterWidget.reset();
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

    private VerticalLayout initFiltersLayout() {
        initPaymentDateFilter();
        initFiscalYearFilter();
        initStatusFilter();
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel(), buildProductFamiliesFilter(),
            buildUsageBatchFilter(), buildRroAccountNumberFilter(), paymentDateWidget, statusComboBox,
            fiscalYearComboBox);
        verticalLayout.setMargin(false);
        return verticalLayout;
    }

    private HorizontalLayout buildProductFamiliesFilter() {
        productFamilyFilterWidget = new ProductFamilyFilterWidget(() -> controller.getProductFamilies());
        productFamilyFilterWidget.addFilterSaveListener(saveEvent -> {
            usageFilter.setProductFamilies(saveEvent.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(productFamilyFilterWidget, "product-families-filter");
        return productFamilyFilterWidget;
    }

    private HorizontalLayout buildUsageBatchFilter() {
        usageBatchFilterWidget = new UsageBatchFilterWidget(() -> controller.getUsageBatchesNotIncludedIntoScenario());
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
            () -> controller.getRros());
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
        List<UsageStatusEnum> statuses = Lists.newArrayList(UsageStatusEnum.values());
        statuses.remove(UsageStatusEnum.LOCKED);
        statuses.remove(UsageStatusEnum.PAID);
        statusComboBox = new ComboBox<>(ForeignUi.getMessage("label.status"), statuses);
        VaadinUtils.setMaxComponentsWidth(statusComboBox);
        statusComboBox.addValueChangeListener(event -> {
            usageFilter.setUsageStatus(statusComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(statusComboBox, "status-filter");
    }

    private void initFiscalYearFilter() {
        fiscalYearComboBox =
            new ComboBox<>(ForeignUi.getMessage("label.fiscal_year_to"), getController().getFiscalYears());
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

    private void filterChanged() {
        applyButton.setEnabled(!usageFilter.equals(appliedUsageFilter));
    }
}
