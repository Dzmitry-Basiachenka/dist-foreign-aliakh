package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.foreign.domain.AuditFilter;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.impl.RightsholderFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.UsageBatchFilterWidget;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Interface for audit filter widget.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/12/18
 *
 * @author Aliaksandr Radkevich
 */
public class AuditFilterWidget extends VerticalLayout implements IAuditFilterWidget {

    private IAuditFilterController controller;
    private RightsholderFilterWidget rightsholderFilterWidget;
    private UsageBatchFilterWidget usageBatchFilterWidget;
    private StatusFilterWidget statusFilterWidget;
    private AuditFilter filter = new AuditFilter();
    private AuditFilter appliedFilter = new AuditFilter();
    private Button applyButton;

    @SuppressWarnings("unchecked")
    @Override
    public AuditFilterWidget init() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        initRightsholdersFilter();
        initUsageBatchesFilter();
        initStatusesFilterWidget();
        addComponents(buildFiltersHeaderLabel(), rightsholderFilterWidget, usageBatchFilterWidget,
            statusFilterWidget, buttonsLayout);
        setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setMargin(true);
        setSpacing(true);
        VaadinUtils.addComponentStyle(this, "audit-filter-widget");
        return this;
    }

    @Override
    public void setController(IAuditFilterController controller) {
        this.controller = controller;
    }

    @Override
    public void applyFilter() {
        appliedFilter = new AuditFilter(filter);
        filterChanged();
        fireEvent(new FilterChangedEvent(this));
    }

    @Override
    public void clearFilter() {
        usageBatchFilterWidget.reset();
        rightsholderFilterWidget.reset();
        statusFilterWidget.reset();
        filter = new AuditFilter();
        applyFilter();
    }

    @Override
    public AuditFilter getAppliedFilter() {
        return appliedFilter;
    }

    private void initUsageBatchesFilter() {
        usageBatchFilterWidget = new UsageBatchFilterWidget(() -> controller.getUsageBatches());
        usageBatchFilterWidget.addFilterSaveListener(event -> {
            filter.setBatchesIds(event.getSelectedItemsIds());
            filterChanged();
        });
    }

    private void initRightsholdersFilter() {
        rightsholderFilterWidget = new RightsholderFilterWidget(ForeignUi.getMessage("label.rightsholders"),
            () -> controller.getRightsholders());
        rightsholderFilterWidget.addFilterSaveListener(event -> {
            filter.setRhAccountNumbers(event.getSelectedItemsIds());
            filterChanged();
        });
    }

    private void initStatusesFilterWidget() {
        statusFilterWidget = new StatusFilterWidget();
        statusFilterWidget.addFilterSaveListener(event -> {
            filter.setStatuses(event.getSelectedItemsIds());
            filterChanged();
        });
    }

    private HorizontalLayout initButtonsLayout() {
        applyButton = new Button(ForeignUi.getMessage("button.apply"));
        applyButton.addClickListener(event -> applyFilter());
        Button clearButton = new Button(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        HorizontalLayout layout = new HorizontalLayout(applyButton, clearButton);
        layout.setSpacing(true);
        VaadinUtils.setMaxComponentsWidth(layout, applyButton, clearButton);
        VaadinUtils.addComponentStyle(layout, "filter-buttons");
        return layout;
    }

    private void filterChanged() {
        applyButton.setEnabled(!filter.equals(appliedFilter));
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }
}
