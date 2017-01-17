package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.component.LocalDateWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

import java.time.LocalDate;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Widget for filtering usages.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/11/2017
 *
 * @author Mikita Hladkikh
 */
class UsagesFilterWidget extends VerticalLayout implements IUsagesFilterWidget {

    private static final EnumSet<UsageStatusEnum> FILTER_USAGE_STATES =
        EnumSet.of(UsageStatusEnum.ELIGIBLE, UsageStatusEnum.INELIGIBLE);
    private Property<LocalDate> paymentDateProperty = new ObjectProperty<>(null, LocalDate.class);
    private Button applyButton;
    private ComboBox statusComboBox;
    private ComboBox taxCountryComboBox;
    private IUsagesFilterController controller;

    @Override
    @SuppressWarnings("unchecked")
    public UsagesFilterWidget init() {
        addComponents(initFiltersLayout(), initButtonsLayout());
        VaadinUtils.setMaxComponentsWidth(this);
        setMargin(true);
        setSpacing(true);
        return this;
    }

    @Override
    public void applyFilter() {
        // TODO {mhladkikh} implement method
    }

    @Override
    public void clearFilter() {
        paymentDateProperty.setValue(null);
        statusComboBox.setValue(null);
        taxCountryComboBox.setValue(null);
        applyButton.setEnabled(false);
    }

    @Override
    public void setController(IUsagesFilterController controller) {
        this.controller = controller;
    }

    /**
     * @return instance of {@link IUsagesFilterController}.
     */
    IUsagesFilterController getController() {
        return controller;
    }

    private VerticalLayout initFiltersLayout() {
        statusComboBox = buildComboBox(ForeignUi.getMessage("label.caption.status"), FILTER_USAGE_STATES);
        // TODO {mhladkikh} fill in tax country values
        taxCountryComboBox = buildComboBox(ForeignUi.getMessage("label.caption.tax_country"), Collections.emptySet());
        Label batchesCountLabel = new Label(String.format(ForeignUi.getMessage("label.filter.items_count_format"), 0));
        Label rightsholdersCountLabel =
            new Label(String.format(ForeignUi.getMessage("label.filter.items_count_format"), 0));
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel(),
            buildItemsFilter(batchesCountLabel, ForeignUi.getMessage("label.caption.batches")),
            buildItemsFilter(rightsholdersCountLabel, ForeignUi.getMessage("label.caption.rightsholders")),
            statusComboBox,
            new LocalDateWidget(ForeignUi.getMessage("label.caption.payment_date"), paymentDateProperty),
            taxCountryComboBox);
        verticalLayout.setSpacing(true);
        return verticalLayout;
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.caption.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }

    private ComboBox buildComboBox(String caption, Set<?> values) {
        ComboBox comboBox = new ComboBox(caption);
        comboBox.addItems(values);
        VaadinUtils.setMaxComponentsWidth(comboBox);
        return comboBox;
    }

    private HorizontalLayout initButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.caption.apply"));
        applyButton.setEnabled(false);
        applyButton.addClickListener(event -> applyFilter());
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.caption.clear"));
        clearButton.addClickListener(event -> clearFilter());
        HorizontalLayout horizontalLayout = new HorizontalLayout(applyButton, clearButton);
        horizontalLayout.setSpacing(true);
        VaadinUtils.setMaxComponentsWidth(horizontalLayout, applyButton, clearButton);
        return horizontalLayout;
    }

    private HorizontalLayout buildItemsFilter(Label countLabel, String caption) {
        Button button = Buttons.createButton(caption);
        button.addStyleName(BaseTheme.BUTTON_LINK);
        HorizontalLayout layout = new HorizontalLayout(countLabel, button);
        layout.setSpacing(true);
        VaadinUtils.setButtonsAutoDisabled(button);
        return layout;
    }
}
