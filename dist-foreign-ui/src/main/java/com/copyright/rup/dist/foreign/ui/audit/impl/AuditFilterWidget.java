package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.ProductFamilyFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.RightsholderFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.UsageBatchFilterWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.Collectors;

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
    private TextField cccEventIdField;
    private TextField distributionNameField;
    private ProductFamilyFilterWidget productFamilyFilterWidget;
    private AuditFilter filter = new AuditFilter();
    private AuditFilter appliedFilter = new AuditFilter();
    private Button applyButton;

    @SuppressWarnings("unchecked")
    @Override
    public AuditFilterWidget init() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        initRightsholdersFilter();
        initProductFamiliesFilter();
        initUsageBatchesFilter();
        initStatusesFilterWidget();
        initEventIdField();
        initDistributionNameField();
        addComponents(buildFiltersHeaderLabel(), productFamilyFilterWidget, rightsholderFilterWidget,
            usageBatchFilterWidget, statusFilterWidget, cccEventIdField, distributionNameField, buttonsLayout);
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
        productFamilyFilterWidget.reset();
        cccEventIdField.setValue(StringUtils.EMPTY);
        distributionNameField.setValue(StringUtils.EMPTY);
        filter = new AuditFilter();
        applyFilter();
    }

    @Override
    public AuditFilter getAppliedFilter() {
        return appliedFilter;
    }

    private HorizontalLayout initProductFamiliesFilter() {
        productFamilyFilterWidget = new ProductFamilyFilterWidget(() -> controller.getProductFamilies());
        productFamilyFilterWidget.addFilterSaveListener(saveEvent -> {
            filter.setProductFamilies(saveEvent.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(productFamilyFilterWidget, "product-families-filter");
        return productFamilyFilterWidget;
    }

    private void initUsageBatchesFilter() {
        usageBatchFilterWidget = new UsageBatchFilterWidget(() -> controller.getUsageBatches());
        usageBatchFilterWidget.addFilterSaveListener((IFilterSaveListener<UsageBatch>) event -> {
            filter.setBatchesIds(
                event.getSelectedItemsIds().stream().map(UsageBatch::getId).collect(Collectors.toSet()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(usageBatchFilterWidget, "audit-batches-filter");
    }

    private void initRightsholdersFilter() {
        rightsholderFilterWidget = new RightsholderFilterWidget(
            ForeignUi.getMessage("label.rightsholders"),
            ForeignUi.getMessage("prompt.rightsholder"),
            ForeignUi.getMessage("message.error.rh_not_found"),
            () -> controller.getRightsholders());
        rightsholderFilterWidget.addFilterSaveListener((IFilterSaveListener<Rightsholder>) event -> {
            filter.setRhAccountNumbers(event.getSelectedItemsIds()
                .stream()
                .map(Rightsholder::getAccountNumber)
                .collect(Collectors.toSet()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(rightsholderFilterWidget, "audit-rightsholders-filter");
    }

    private void initStatusesFilterWidget() {
        statusFilterWidget = new StatusFilterWidget();
        statusFilterWidget.addFilterSaveListener(event -> {
            filter.setStatuses(event.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(statusFilterWidget, "audit-statuses-filter");
    }

    private HorizontalLayout initButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
        applyButton.addClickListener(event -> trimFilterValues());
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
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

    private void initEventIdField() {
        cccEventIdField = createTextField(ForeignUi.getMessage("label.event_id"));
        cccEventIdField.addValueChangeListener(event -> {
            filter.setCccEventId(StringUtils.trimToNull(event.getValue()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(cccEventIdField, "ccc-event-id-filter");
    }

    private void initDistributionNameField() {
        distributionNameField = createTextField(ForeignUi.getMessage("label.distribution_name"));
        distributionNameField.addValueChangeListener(event -> {
            filter.setDistributionName(StringUtils.trimToNull(event.getValue()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(distributionNameField, "distribution-name-filter");
    }

    private TextField createTextField(String caption) {
        TextField textField = new TextField(caption);
        VaadinUtils.setMaxComponentsWidth(textField);
        return textField;
    }

    private void trimFilterValues() {
        cccEventIdField.setValue(StringUtils.trim(cccEventIdField.getValue()));
        distributionNameField.setValue(StringUtils.trim(distributionNameField.getValue()));
        applyFilter();
    }
}
