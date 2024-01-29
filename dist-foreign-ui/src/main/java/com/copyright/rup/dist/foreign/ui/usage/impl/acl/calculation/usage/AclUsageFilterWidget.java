package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Widget for filtering ACL usages.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageFilterWidget extends VerticalLayout implements IAclUsageFilterWidget {

    private static final long serialVersionUID = -4815434985886270445L;

    private final AclUsageAppliedFilterWidget appliedFilterWidget;

    private IAclUsageFilterController controller;
    private Button applyButton;
    private Button moreFiltersButton;
    private ComboBox<String> usageBatchNameComboBox;
    private AclUsageFilter aclUsageFilter = new AclUsageFilter();
    private AclUsageFilter appliedAclUsageFilter = new AclUsageFilter();

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclUsageFilterController}
     */
    public AclUsageFilterWidget(IAclUsageFilterController controller) {
        this.controller = controller;
        appliedFilterWidget = new AclUsageAppliedFilterWidget();
    }

    @Override
    public AclUsageFilter getFilter() {
        return aclUsageFilter;
    }

    @Override
    public AclUsageFilter getAppliedFilter() {
        return appliedAclUsageFilter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public IAclUsageFilterWidget init() {
        addComponents(initFiltersLayout(), initButtonsLayout(), buildAppliedFiltersHeaderLabel(),
            appliedFilterWidget);
        refreshFilter();
        super.setExpandRatio(appliedFilterWidget, 1f);
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "acl-usage-filter-widget");
        return this;
    }

    @Override
    public void applyFilter() {
        appliedAclUsageFilter = new AclUsageFilter(aclUsageFilter);
        appliedFilterWidget.refreshFilterPanel(appliedAclUsageFilter);
        filterChanged();
        fireEvent(new FilterChangedEvent(this));
    }

    @Override
    public void clearFilter() {
        usageBatchNameComboBox.clear();
        refreshFilter();
        applyFilter();
    }

    @Override
    public void setController(IAclUsageFilterController controller) {
        this.controller = controller;
    }

    private void filterChanged() {
        applyButton.setEnabled(
            Objects.nonNull(aclUsageFilter.getUsageBatchName()) && !aclUsageFilter.equals(appliedAclUsageFilter));
        moreFiltersButton.setEnabled(Objects.nonNull(aclUsageFilter.getUsageBatchName()));
    }

    private void refreshFilter() {
        refreshFilterValues();
        aclUsageFilter = new AclUsageFilter();
    }

    private void refreshFilterValues() {
        usageBatchNameComboBox.setItems(controller.getAllAclUsageBatches().stream()
            .map(AclUsageBatch::getName)
            .collect(Collectors.toList()));
    }

    private VerticalLayout initFiltersLayout() {
        initUsageBatchNameFilter();
        initMoreFiltersButton();
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel(), usageBatchNameComboBox,
            moreFiltersButton);
        verticalLayout.setMargin(false);
        return verticalLayout;
    }

    private void initUsageBatchNameFilter() {
        usageBatchNameComboBox = new ComboBox<>(ForeignUi.getMessage("label.usage_batch_name"));
        VaadinUtils.setMaxComponentsWidth(usageBatchNameComboBox);
        usageBatchNameComboBox.addValueChangeListener(event -> {
            aclUsageFilter.setUsageBatchName(usageBatchNameComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(usageBatchNameComboBox, "acl-usage-batch-name-filter");
    }

    private void initMoreFiltersButton() {
        moreFiltersButton = new Button(ForeignUi.getMessage("label.more_filters"));
        moreFiltersButton.setEnabled(false);
        moreFiltersButton.addStyleName(ValoTheme.BUTTON_LINK);
        moreFiltersButton.addClickListener(event -> {
            AclUsageFiltersWindow aclUsageFiltersWindow = new AclUsageFiltersWindow(controller, aclUsageFilter);
            Windows.showModalWindow(aclUsageFiltersWindow);
            aclUsageFiltersWindow.addCloseListener(closeEvent -> {
                aclUsageFilter = aclUsageFiltersWindow.getAppliedUsageFilter();
                filterChanged();
            });
        });
        VaadinUtils.setButtonsAutoDisabled(moreFiltersButton);
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(ValoTheme.LABEL_H2);
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
        VaadinUtils.addComponentStyle(horizontalLayout, "acl-usage-filter-buttons");
        return horizontalLayout;
    }

    private Label buildAppliedFiltersHeaderLabel() {
        Label appliedFilterHeaderLabel = new Label(ForeignUi.getMessage("label.applied_filters"));
        appliedFilterHeaderLabel.addStyleNames(ValoTheme.LABEL_H2, "applied-filter-header");
        return appliedFilterHeaderLabel;
    }
}
