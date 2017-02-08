package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.ui.component.LocalDateWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

import java.time.LocalDate;
import java.util.Collection;
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
class UsagesFilterWidget extends VerticalLayout implements IUsagesFilterWidget {

    private Property<LocalDate> paymentDateProperty = new ObjectProperty<>(null, LocalDate.class);
    private Button applyButton;
    private ComboBox fiscalYearComboBox;
    private IUsagesFilterController controller;
    private Label batchesCountLabel;
    private Label rightsholdersCountLabel;
    private UsageFilter usageFilter = new UsageFilter();

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
        applyButton.setEnabled(false);
        fireEvent(new FilterChangedEvent(this, usageFilter));
    }

    @Override
    public void clearFilter() {
        paymentDateProperty.setValue(null);
        fiscalYearComboBox.setValue(null);
        applyButton.setEnabled(false);
        usageFilter = new UsageFilter();
        setCountLabelValue(batchesCountLabel, 0);
        setCountLabelValue(rightsholdersCountLabel, 0);
    }

    @Override
    public void setController(IUsagesFilterController controller) {
        this.controller = controller;
    }

    @Override
    public void setSelectedUsageBatches(Set<String> usageBatchesIds) {
        setCountLabelValue(batchesCountLabel, usageBatchesIds.size());
        usageFilter.setUsageBatchesIds(usageBatchesIds);
        filterChanged();
    }

    @Override
    public void setSelectedRightsholders(Set<Long> accountNumbers) {
        setCountLabelValue(rightsholdersCountLabel, accountNumbers.size());
        usageFilter.setRhAccountNumbers(accountNumbers);
        filterChanged();
    }

    @Override
    public UsageFilter getFilter() {
        return usageFilter;
    }

    /**
     * @return instance of {@link IUsagesFilterController}.
     */
    IUsagesFilterController getController() {
        return controller;
    }

    private VerticalLayout initFiltersLayout() {
        fiscalYearComboBox =
            buildComboBox(ForeignUi.getMessage("label.fiscal_year_to"), getController().getFiscalYears());
        VaadinUtils.addComponentStyle(fiscalYearComboBox, "fiscal-year-filter");
        batchesCountLabel = new Label(String.format(ForeignUi.getMessage("label.filter.items_count_format"), 0));
        rightsholdersCountLabel = new Label(String.format(ForeignUi.getMessage("label.filter.items_count_format"), 0));
        HorizontalLayout batchesFilter =
            buildItemsFilter(batchesCountLabel, ForeignUi.getMessage("label.batches"),
                (ClickListener) event -> controller.onUsageBatchFilterClick());
        VaadinUtils.addComponentStyle(batchesFilter, "batches-filter");
        HorizontalLayout rightsholdersFilter =
            buildItemsFilter(rightsholdersCountLabel, ForeignUi.getMessage("label.rightsholders"),
                (ClickListener) event -> controller.onRightsholderFilterClick());
        VaadinUtils.addComponentStyle(rightsholdersFilter, "rightsholders-filter");
        LocalDateWidget paymentDateWidget =
            new LocalDateWidget(ForeignUi.getMessage("label.payment_date_to"), paymentDateProperty);
        VaadinUtils.addComponentStyle(paymentDateWidget, "payment-date-filter");
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel(), batchesFilter,
            rightsholdersFilter, paymentDateWidget, fiscalYearComboBox);
        verticalLayout.setSpacing(true);
        return verticalLayout;
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }

    private ComboBox buildComboBox(String caption, Collection<?> values) {
        ComboBox comboBox = new ComboBox(caption);
        comboBox.addItems(values.stream().map(Object::toString).collect(Collectors.toList()));
        comboBox.setConverter(String.class);
        VaadinUtils.setMaxComponentsWidth(comboBox);
        return comboBox;
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

    private HorizontalLayout buildItemsFilter(Label countLabel, String caption, ClickListener clickListener) {
        Button button = Buttons.createButton(caption);
        button.addStyleName(BaseTheme.BUTTON_LINK);
        button.addClickListener(clickListener);
        HorizontalLayout layout = new HorizontalLayout(countLabel, button);
        layout.setSpacing(true);
        VaadinUtils.setButtonsAutoDisabled(button);
        return layout;
    }

    private void filterChanged() {
        applyButton.setEnabled(!usageFilter.isEmpty());
    }

    private void setCountLabelValue(Label label, int count) {
        label.setValue(String.format(ForeignUi.getMessage("label.filter.items_count_format"), count));
    }
}
