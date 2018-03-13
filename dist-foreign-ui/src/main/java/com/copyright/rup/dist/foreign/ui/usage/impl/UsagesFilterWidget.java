package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.common.ProductFamilyFilterWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.google.common.collect.Lists;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

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
    private ComboBox fiscalYearComboBox;
    private ComboBox statusComboBox;
    private IUsagesFilterController controller;
    private RightsholderFilterWidget rightsholderFilterWidget;
    private UsageBatchFilterWidget usageBatchFilterWidget;
    private ProductFamilyFilterWidget productFamilyFilterWidget;
    private UsageFilter usageFilter = new UsageFilter();
    private UsageFilter appliedUsageFilter = new UsageFilter();
    private BeanItemContainer<Integer> fiscalYearContainer;

    @Override
    @SuppressWarnings("unchecked")
    public UsagesFilterWidget init() {
        addComponents(initFiltersLayout(), initButtonsLayout());
        VaadinUtils.setMaxComponentsWidth(this);
        setMargin(true);
        setSpacing(true);
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
        paymentDateWidget.setInternalValue(null);
        fiscalYearComboBox.setValue(null);
        statusComboBox.setValue(null);
        refreshFiscalYearComboBox();
        usageFilter = new UsageFilter();
        rightsholderFilterWidget.reset();
        usageBatchFilterWidget.reset();
        productFamilyFilterWidget.reset();
        applyFilter();
    }

    @Override
    public void setController(IUsagesFilterController controller) {
        this.controller = controller;
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

    private VerticalLayout initFiltersLayout() {
        initPaymentDateFilter();
        initFiscalYearFilter();
        initStatusFilter();
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel(), buildProductFamiliesFilter(),
            buildUsageBatchFilter(), buildRroAccountNumberFilter(), paymentDateWidget, statusComboBox,
            fiscalYearComboBox);
        verticalLayout.setSpacing(true);
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
        usageBatchFilterWidget.addFilterSaveListener(saveEvent -> {
            usageFilter.setUsageBatchesIds(saveEvent.getSelectedItemsIds());
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
        rightsholderFilterWidget.addFilterSaveListener(saveEvent -> {
            usageFilter.setRhAccountNumbers(saveEvent.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(rightsholderFilterWidget, "rightsholders-filter");
        return rightsholderFilterWidget;
    }

    private void initStatusFilter() {
        List<UsageStatusEnum> statuses = Lists.newArrayList(UsageStatusEnum.values());
        statuses.remove(UsageStatusEnum.LOCKED);
        statuses.remove(UsageStatusEnum.PAID);
        statusComboBox = new ComboBox(ForeignUi.getMessage("label.status"),
            new BeanItemContainer<>(UsageStatusEnum.class, statuses));
        statusComboBox.setItemCaptionMode(ItemCaptionMode.ID_TOSTRING);
        VaadinUtils.setMaxComponentsWidth(statusComboBox);
        statusComboBox.addValueChangeListener((ValueChangeListener) event -> {
            usageFilter.setUsageStatus((UsageStatusEnum) statusComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(statusComboBox, "status-filter");
    }

    private void initFiscalYearFilter() {
        fiscalYearContainer = new BeanItemContainer<>(Integer.class, getController().getFiscalYears());
        fiscalYearComboBox = new ComboBox(ForeignUi.getMessage("label.fiscal_year_to"), fiscalYearContainer);
        fiscalYearComboBox.setItemCaptionMode(ItemCaptionMode.ID_TOSTRING);
        VaadinUtils.setMaxComponentsWidth(fiscalYearComboBox);
        fiscalYearComboBox.addValueChangeListener((ValueChangeListener) event -> {
            usageFilter.setFiscalYear((Integer) fiscalYearComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(fiscalYearComboBox, "fiscal-year-filter");
    }

    private void initPaymentDateFilter() {
        paymentDateWidget = new LocalDateWidget(ForeignUi.getMessage("label.payment_date_to"));
        paymentDateWidget.addValueChangeListener((ValueChangeListener) event -> {
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
        horizontalLayout.setSpacing(true);
        VaadinUtils.setMaxComponentsWidth(horizontalLayout, applyButton, clearButton);
        VaadinUtils.addComponentStyle(horizontalLayout, "filter-buttons");
        return horizontalLayout;
    }

    private void filterChanged() {
        applyButton.setEnabled(!usageFilter.equals(appliedUsageFilter));
    }

    private void refreshFiscalYearComboBox() {
        fiscalYearContainer.removeAllItems();
        fiscalYearContainer.addAll(getController().getFiscalYears());
    }
}
