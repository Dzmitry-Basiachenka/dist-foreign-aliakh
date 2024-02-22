package com.copyright.rup.dist.foreign.vui.usage.impl;

import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageFilterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.IFilterSaveAction;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.HashSet;
import java.util.Set;

/**
 * Common widget for filtering usages.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/12/2019
 *
 * @author Uladzislau Shalamitski
 */
public abstract class CommonUsageFilterWidget extends VerticalLayout implements ICommonUsageFilterWidget {

    private static final long serialVersionUID = 9089378281952722930L;

    private final Set<IFilterSaveAction> filterSaveActions = new HashSet<>();
    private ICommonUsageFilterController controller;
    private Button applyButton;
    private UsageFilter usageFilter = new UsageFilter();
    private UsageFilter appliedUsageFilter = new UsageFilter();
    private CommonUsageAppliedFilterWidget appliedFilterWidget;

    @Override
    @SuppressWarnings("unchecked")
    public CommonUsageFilterWidget init() {
        setMinWidth("150px");
        setSpacing(false);
        setWidthFull();
        initContent();
        VaadinUtils.setPadding(this, 0, 10, 0, 10);
        VaadinUtils.addComponentStyle(this, "usages-filter-widget");
        return this;
    }

    @Override
    public void addFilterSaveAction(IFilterSaveAction action) {
        this.filterSaveActions.add(action);
    }

    @Override
    public void applyFilter() {
        appliedUsageFilter = new UsageFilter(usageFilter);
        appliedFilterWidget.refreshFilterPanel(appliedUsageFilter);
        filterSaveActions.forEach(IFilterSaveAction::onFilterSaveAction);
        filterChanged();
    }

    @Override
    public void clearFilter() {
        clearFilterValues();
        refreshFilter();
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

    @Override
    public void setController(ICommonUsageFilterController controller) {
        this.controller = controller;
    }

    /**
     * Refreshes filter values.
     */
    protected abstract void refreshFilterValues();

    /**
     * Clears filter values.
     */
    protected abstract void clearFilterValues();

    /**
     * @return instantiated filter layout.
     */
    protected abstract VerticalLayout initFiltersLayout();

    /**
     * @return instantiated applied filter widget.
     */
    protected abstract CommonUsageAppliedFilterWidget getAppliedFilterWidget();

    /**
     * @return built filters header label.
     */
    protected Label buildFiltersHeaderLabel() {
        var label = new Label(ForeignUi.getMessage("label.filters"));
        label.addClassName("filter-label");
        return label;
    }

    /**
     * Handles filter change event.
     */
    protected void filterChanged() {
        applyButton.setEnabled(!usageFilter.equals(appliedUsageFilter));
    }

    /**
     * Builds applied filters header label.
     *
     * @return instance of {@link Label}
     */
    protected Label buildAppliedFiltersHeaderLabel() {
        var label = new Label(ForeignUi.getMessage("label.applied_filters"));
        label.addClassNames("filter-label");
        return label;
    }

    private void initContent() {
        appliedFilterWidget = getAppliedFilterWidget();
        add(initFiltersLayout(), initButtonsLayout(), buildAppliedFiltersHeaderLabel(), appliedFilterWidget);
        refreshFilter();
    }

    private void refreshFilter() {
        refreshFilterValues();
        usageFilter = new UsageFilter();
        usageFilter.setProductFamily(controller.getSelectedProductFamily());
    }

    private HorizontalLayout initButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
        applyButton.setEnabled(false);
        applyButton.addClickListener(event -> applyFilter());
        var clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        var buttonsLayout = new HorizontalLayout(applyButton, clearButton);
        VaadinUtils.setFullComponentsWidth(buttonsLayout, applyButton, clearButton);
        VaadinUtils.addComponentStyle(buttonsLayout, "filter-buttons");
        return buttonsLayout;
    }
}
